package com.dt.derbyday.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dt.derbyday.constant.WechatData;
import com.dt.derbyday.dto.AddScore;
import com.dt.derbyday.dto.UserChoiceDisplay;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.GameResult;
import com.dt.derbyday.model.Question;
import com.dt.derbyday.model.UserChoice;
import com.dt.derbyday.model.UserInfo;
import com.dt.derbyday.model.UserScore;
import com.dt.derbyday.service.GameService;
import com.dt.derbyday.service.UserService;
import com.dt.derbyday.utils.ConfigUtil;
import com.dt.derbyday.utils.WeixinUtils;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userSerivce;

	@GetMapping("/getOpenIdByCode")
	public Map<String, Object> getOpenIdByCode(@RequestParam(required = false) String code, @RequestParam(required = false) String game, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String openId = WeixinUtils.getXcxOpenIdByCode(code);
		if (openId != null) {
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
			resultMap.put("token", openId);
		} else {
			resultMap.put("code", 500);
			resultMap.put("message", "error");
		}

		return resultMap;
	}

	@GetMapping("/startGame")
	public void startGame(@RequestParam(required = false) String game, HttpServletRequest request, HttpServletResponse response) {
		ConfigUtil config = new ConfigUtil();
		String backUri = config.getProperty("local") + "/game/guessing?game=" + game;
		backUri = URLEncoder.encode(backUri);
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + WechatData.APP_ID + "&redirect_uri=" + backUri
				+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";

		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// @GetMapping("/guessing")
	public Map<String, Object> getGame(@RequestParam(required = false) String code, @RequestParam(required = false) String game, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			GameInfo gameInfo = gameService.getGameInfoByGame(game);

			if (gameInfo == null) {
				resultMap.put("code", 500);
				resultMap.put("message", "活动不存在！");

				return resultMap;
			}
			if (gameInfo.getStartDate().after(new Date())) {
				resultMap.put("code", 500);
				resultMap.put("message", "活动尚未开始！");

				return resultMap;
			}
			if (gameInfo.getEndDate().before(new Date())) {
				resultMap.put("code", 501);
				resultMap.put("message", "活动已经结束！");

				return resultMap;
			}

			// String openId = WeixinUtils.getWechatOpenIdByCode(code);
			String openId = null;
			String act = null;
			if ("test".equals(code)) {
				openId = "testopenid";
			} else {
				Map<String, String> params = WeixinUtils.getOpenIdAndTokenByCode(code);
				if (params != null) {
					openId = params.get("openId");
					act = params.get("token");
				}
			}

			UserInfo user = userSerivce.queryUserByOpenId(openId);
			if (user == null) {
				user = WeixinUtils.getUserinfoByOpenId(openId, act);
				user.setOpen(openId);
				userSerivce.addUser(user);
			}

			List<UserChoiceDisplay> historyList = gameService.getUserChoiceHistory(game, user.getId());
			if (historyList != null && historyList.size() > 0) {
				resultMap.put("code", 502);// 502的时候，去getGameHistory
				resultMap.put("message", "您已经参与过活动了！");
				resultMap.put("token", openId);
				return resultMap;
			}

			List<Question> questions = gameService.getQuestionByGame(game);

			resultMap.put("code", 200);
			resultMap.put("message", "OK");
			resultMap.put("token", openId);
			resultMap.put("info", gameInfo);
			resultMap.put("questions", questions);

		} catch (Exception e) {
			e.printStackTrace();
			return resultMap;
		}
		return resultMap;
	}

	@GetMapping("/guessing")
	public Map<String, Object> wxAppGetGame(@RequestParam(required = false) String code, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			// GameInfo gameInfo = gameService.getGameInfoByGame(game);
			GameInfo gameInfo = gameService.getGameInfoByTime();

			if (gameInfo == null) {
				resultMap.put("code", 500);
				resultMap.put("message", "活动不存在！");

				return resultMap;
			}
			// if (gameInfo.getStartDate().after(new Date())) {
			// resultMap.put("code", 500);
			// resultMap.put("message", "活动尚未开始！");
			//
			// return resultMap;
			// }
			// if (gameInfo.getEndDate().before(new Date())) {
			// resultMap.put("code", 501);
			// resultMap.put("message", "活动已经结束！");
			//
			// return resultMap;
			// }

			// String openId = WeixinUtils.getWechatOpenIdByCode(code);
			String openId = code;

			UserInfo user = userSerivce.queryUserByOpenId(openId);
			if (user == null) {
				// user = WeixinUtils.getUserinfoByOpenId(openId, act);
				// user = new UserInfo();
				// user.setOpen(openId);
				// userSerivce.addUser(user);
				resultMap.put("code", 503);
				resultMap.put("message", "用户不存在！");

				return resultMap;
			}

			List<UserChoiceDisplay> historyList = gameService.getUserChoiceHistory(gameInfo.getGame(), user.getId());
			if (historyList != null && historyList.size() > 0) {
				resultMap.put("info", gameInfo);
				resultMap.put("code", 502);// 502的时候，去getGameHistory
				resultMap.put("message", "您已经参与过活动了！");
				resultMap.put("token", openId);
				return resultMap;
			}

			List<Question> questions = gameService.getQuestionByGame(gameInfo.getGame());

			resultMap.put("code", 200);
			resultMap.put("message", "OK");
			// resultMap.put("token", openId);
			resultMap.put("info", gameInfo);
			resultMap.put("questions", questions);

		} catch (Exception e) {
			e.printStackTrace();
			return resultMap;
		}
		return resultMap;
	}

	@GetMapping("/getChoice")
	public Map<String, Object> getChoice(@RequestParam(required = false) Integer questionId, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Choice> choices = gameService.getChoiceByQuestionId(questionId);

		resultMap.put("code", 200);
		resultMap.put("message", "OK");
		resultMap.put("choice", choices);

		return resultMap;
	}

	@GetMapping("/submitGame")
	public Map<String, Object> submitGame(@RequestParam(required = false) String c, @RequestParam(required = false) String token, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		// {"game":"shanghai","choices":[{"question":1,"choice":1},{"question":2,"choice":8}]}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserInfo user = userSerivce.queryUserByOpenId(token);
		if (user == null) {
			resultMap.put("code", 500);
			resultMap.put("message", "非法提交：token无效！");
			return resultMap;
		}

		List<UserChoice> ucl = new ArrayList<UserChoice>();
		JSONObject jsonObj = JSON.parseObject(c);
		String game = jsonObj.getString("game");
		JSONArray jsonArray = jsonObj.getJSONArray("choices");

		// 看看是不是已经提交过了
		List<UserChoiceDisplay> history = gameService.getUserChoiceHistory(game, user.getId());
		if (history != null && history.size() > 0) {
			resultMap.put("code", 500);
			resultMap.put("message", "您已经参加过此竞猜！");
			return resultMap;
		}

		if ("undefined".equals(game)) {
			JSONObject jo = (JSONObject) jsonArray.get(0);
			int qid = jo.getInteger("question");
			Question tempQuestion = gameService.getQuestionById(qid);
			game = tempQuestion.getGame();
		}

		for (int i = 0; i < jsonArray.size(); i++) {
			UserChoice uc = new UserChoice();
			JSONObject jo = (JSONObject) jsonArray.get(i);
			int q = jo.getInteger("question");
			int ch = jo.getInteger("choice");

			uc.setChoice(ch);
			uc.setQuestion(q);
			uc.setGame(game);
			uc.setUserId(user.getId());

			ucl.add(uc);
		}
		{
			gameService.addUserChoice(ucl);
			UserScore us = new UserScore();
			us.setGame(game);
			us.setNick(user.getNick());
			us.setUser(user.getId());
			us.setScore(0);
			gameService.addUserScore(us);
		}

		resultMap.put("code", 200);
		resultMap.put("message", "OK");
		return resultMap;
	}

	@GetMapping("/history")
	public Map<String, Object> getGameHistory(@RequestParam(required = false) String token, @RequestParam(required = false) String game, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserInfo user = userSerivce.queryUserByOpenId(token);
		if (user == null) {
			resultMap.put("code", 500);
			resultMap.put("message", "非法的用户！");
			return resultMap;
		}
		List<UserChoiceDisplay> list = gameService.getUserChoiceHistory(game, user.getId());

		resultMap.put("code", 200);
		resultMap.put("message", "OK");
		resultMap.put("questions", list);
		return resultMap;
	}

	@GetMapping("/statistics")
	public Map<String, Object> getGameStatistics(@RequestParam(required = false) String game, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
	}

	@GetMapping("/rank")
	public Map<String, Object> getGameRank(@RequestParam(required = false) String token, @RequestParam(required = false) String game, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		GameInfo gameInfo = gameService.getGameInfoByGame(game);
		if (gameInfo == null) {
			resultMap.put("code", 500);
			resultMap.put("message", "活动不存在！");

			return resultMap;
		}

		resultMap.put("code", 200);
		resultMap.put("info", gameInfo);
		resultMap.put("rank", gameService.getRankByGame(game));
		return resultMap;
	}

	@GetMapping("/gameResult")
	public Map<String, Object> gameResult(@RequestParam(required = false) String c, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			JSONObject jsonObj = JSON.parseObject(c);
			String game = jsonObj.getString("game");
			JSONArray jsonArray = jsonObj.getJSONArray("choices");
			for (int i = 0; i < jsonArray.size(); i++) {

				JSONObject jo = (JSONObject) jsonArray.get(i);
				int q = jo.getInteger("question");
				if (jo.getString("choice").indexOf("/") > 0) {// 多选的逻辑
					String s = jo.getString("choice");
					String[] ss = s.split("/");
					for (String ts : ss) {
						int tch = Integer.parseInt(ts);
						// this.updateUserScore(game, tch, q);
						this.saveGameResult(game, tch, q);
					}
					{// 因为多选涉及扣分，单独处理用户的分数
						this.updateMutiChoice(ss, q, game);
						this.updateMutiWrongChoice(ss, q, game);
					}
				} else {
					int ch = jo.getInteger("choice");
					this.updateUserScore(game, ch, q);
					this.saveGameResult(game, ch, q);
				}
			}
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (NumberFormatException e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}

	@GetMapping("/checkUser")
	public Map<String, Object> checkUser(@RequestParam(required = false) String token, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			ConfigUtil config = new ConfigUtil();
			String color = config.getProperty("bgc");

			resultMap.put("color", color);

			UserInfo user = userSerivce.queryUserByOpenId(token);
			if (user != null) {
				resultMap.put("code", 200);
				resultMap.put("message", true);
			} else {
				resultMap.put("code", 200);
				resultMap.put("message", false);
			}
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}

	@GetMapping("/regUser")
	public Map<String, Object> regUser(@RequestParam(required = false) String token, @RequestParam(required = false) String nick, @RequestParam(required = false) String head,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String address, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		System.out.println(nick + ":" + phone);
		try {
			UserInfo user = userSerivce.queryUserByOpenId(token);
			if (user == null) {
				UserInfo newUser = new UserInfo();
				newUser.setAddress(address);
				newUser.setNick(filterEmoji(nick));
				newUser.setOpen(token);
				newUser.setPhone(phone);
				newUser.setHead(head);
				userSerivce.addUser(newUser);

				resultMap.put("code", 200);
				resultMap.put("message", "OK");
			} else {
				resultMap.put("code", 500);
				resultMap.put("message", "用户已经存在");
			}
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}

	private String getAddScoreUser(String game, int question, int choice) {
		try {
			String inStr = "";
			UserChoice uc = new UserChoice();
			uc.setGame(game);
			uc.setQuestion(question);
			uc.setChoice(choice);

			List<UserChoice> ul = gameService.getUserChoiceByResult(uc);

			for (UserChoice c : ul) {
				inStr = inStr + c.getUserId() + ",";
			}

			return inStr.substring(0, inStr.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void updateUserScore(String game, int choiceId, int questionId) {
		Choice tempChoice = gameService.getChoiceById(choiceId);
		int tempScore = tempChoice.getScore();
		String inStr = this.getAddScoreUser(game, questionId, choiceId);

		AddScore as = new AddScore();
		as.setInStr(inStr);
		as.setScore(tempScore);
		as.setGame(game);

		gameService.updateUserScore(as);
	}

	private void updateMutiChoice(String[] ss, int questionId, String game) {
		UserChoiceDisplay uc = new UserChoiceDisplay();
		uc.setQuestionId(questionId);
		String choice = "";
		for (String s : ss) {
			choice = choice + s + ",";
		}
		uc.setChoice(choice.substring(0, choice.length() - 1));
		List<UserChoice> list = gameService.getUserByMutiChoice(uc);
		List<Choice> choices = gameService.getChoiceByQuestionId(questionId);
		if (choices != null && choices.size() > 0)
			gameService.updateUserScore(list, choices.get(0).getScore());
	}

	private void updateMutiWrongChoice(String[] ss, int questionId, String game) {
		UserChoiceDisplay uc = new UserChoiceDisplay();
		uc.setQuestionId(questionId);
		String choice = "";
		for (String s : ss) {
			choice = choice + s + ",";
		}
		uc.setChoice(choice.substring(0, choice.length() - 1));
		List<UserChoice> list = gameService.getUserByMutiWrongChoice(uc);
		gameService.updateUserScore(list, -3);
	}

	private void saveGameResult(String game, int choiceId, int questionId) {
		GameResult gr = new GameResult();
		gr.setChoice(choiceId);
		gr.setQuestion(questionId);
		gr.setGame(game);

		gameService.addGameResult(gr);
	}

	public String filterEmoji(String source) {
		if (source != null) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		} else {
			return source;
		}
	}

	public static void main(String[] args) {
		Question q = new Question();
		q.setGame("111");
		q.setMaxChoice(5);
		q.setQuestion("test");
		q.setSeq(1);
		List<Choice> choices = new ArrayList<Choice>();
		Choice c = new Choice();
		c.setChoice("c1");
		c.setScore(2);
		c.setSeq(1);

		Choice cc = new Choice();
		cc.setChoice("c2");
		cc.setScore(2);
		cc.setSeq(3);

		Choice ccc = new Choice();
		ccc.setChoice("c3");
		ccc.setScore(2);
		ccc.setSeq(2);
		choices.add(cc);
		choices.add(c);
		choices.add(ccc);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("question", q);
		resultMap.put("choices", choices);

		String jsonString = JSON.toJSONString(resultMap);
		System.out.println(jsonString + "");
	}
}
