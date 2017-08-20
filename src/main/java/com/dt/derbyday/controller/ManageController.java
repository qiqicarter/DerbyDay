package com.dt.derbyday.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dt.derbyday.dto.QuestionDto;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.Question;
import com.dt.derbyday.service.GameService;
import com.dt.derbyday.service.ManageService;

@RestController
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	ManageService manageService;

	@Autowired
	private GameService gameService;
	
	@PostMapping("/createGame")
	public Map<String, Object> createGame(@RequestParam(required = true) String game,
			@RequestParam(required = true) String title, @RequestParam(required = true) String picUrl,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date sdate = sdf.parse(start);
			Date edate = sdf.parse(end);
			
			GameInfo gi = new GameInfo();
			gi.setTitle(title);
			gi.setGame(game);
			gi.setPicUrl(picUrl);
			gi.setStartDate(sdate);
			gi.setEndDate(edate);
			
			manageService.createGame(gi);
			
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (ParseException e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@PostMapping("/getGames")
	public Map<String, Object> getGames(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			List<GameInfo> list = manageService.getGames();
			
			resultMap.put("code", 200);
			resultMap.put("games", list);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@PostMapping("/delGame")
	public Map<String, Object> delGame(@RequestParam(required = true) String gameId,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			manageService.deleteGame(Integer.parseInt(gameId));
			
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@PostMapping("/addQuestion")
	public Map<String, Object> addQuestion(@RequestParam(required = true) String json,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		//{"question":{"game":"111","maxChoice":5,"question":"问题问题问题","seq":1},"choices":[{"choice":"选项内容1","score":2,"seq":3},{"choice":"选项内容2","score":2,"seq":1},{"choice":"选项内容3","score":2,"seq":2}]}
		System.out.println(json);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JSON.parseObject(
					json,new TypeReference<Map<String, Object>>(){} );
			JSONObject q = (JSONObject)map.get("question");
			Question qq = q.toJavaObject(Question.class);
			List<JSONObject> list = (List<JSONObject>)map.get("choices");
			List<Choice> choices = new ArrayList<Choice>();
			
			for(JSONObject jo : list){
				Choice choice = jo.toJavaObject(Choice.class);
				choices.add(choice);
			}
			
			manageService.createWholeQuestion(qq, choices);
			
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@PostMapping("/getQuestions")
	public Map<String, Object> getQuestions(@RequestParam(required = true) String game,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<QuestionDto> list = new ArrayList<QuestionDto>();
		
		try {
			List<Question> questions = gameService.getQuestionByGame(game);
			for(Question q : questions) {
				List<Choice> choices = gameService.getChoiceByQuestionId(q.getId());
				QuestionDto dto =  new QuestionDto();
				dto.setChoices(choices);
				dto.setGame(game);
				dto.setId(q.getId());
				dto.setMaxChoice(q.getMaxChoice());
				dto.setQuestion(q.getQuestion());
				dto.setSeq(q.getSeq());
				
				list.add(dto);
			}
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
			resultMap.put("data", list);
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@PostMapping("/delQuestion")
	public Map<String, Object> delQuestion(@RequestParam(required = true) String guestionId,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			manageService.deleteQuestion(Integer.parseInt(guestionId));
			List<Choice> choices = gameService.getChoiceByQuestionId(Integer.parseInt(guestionId));
			for(Choice c : choices) {
				manageService.deleteChoice(c.getId());
			}
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestParam(required = true) String user,@RequestParam(required = true) String pass,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if(user.equals("admin") && pass.toUpperCase().equals("0192023A7BBD73250516F069DF18B500")) {
				resultMap.put("data", true);
				resultMap.put("token", "0192023A7B");
			}else {
				resultMap.put("data", false);
			}
			resultMap.put("code", 200);
			resultMap.put("message", "OK");
		} catch (Exception e) {
			resultMap.put("code", 500);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}
}
