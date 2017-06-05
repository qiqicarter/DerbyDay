package com.dt.derbyday.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dt.derbyday.dto.AddScore;
import com.dt.derbyday.dto.UserChoiceDisplay;
import com.dt.derbyday.dto.UserScoreDisplay;
import com.dt.derbyday.mapper.ChoiceMapper;
import com.dt.derbyday.mapper.GameInfoMapper;
import com.dt.derbyday.mapper.GameResultMapper;
import com.dt.derbyday.mapper.QuestionMapper;
import com.dt.derbyday.mapper.UserChoiceMapper;
import com.dt.derbyday.mapper.UserScoreMapper;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.GameResult;
import com.dt.derbyday.model.Question;
import com.dt.derbyday.model.UserChoice;
import com.dt.derbyday.model.UserScore;
import com.dt.derbyday.service.GameService;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	GameInfoMapper gameMapper;

	@Autowired
	QuestionMapper questionMapper;

	@Autowired
	ChoiceMapper choiceMapper;

	@Autowired
	UserChoiceMapper userChoiceMapper;

	@Autowired
	UserScoreMapper userScoreMapper;

	@Autowired
	GameResultMapper gameResultMapper;

	public GameInfo getGameInfoByGame(String code) {
		return gameMapper.selectByGame(code);
	}
	
	public GameInfo getGameInfoByTime(){
		return gameMapper.selectByTime();
	}

	public List<Question> getQuestionByGame(String game) {
		return questionMapper.selectByGame(game);
	}

	public List<Choice> getChoiceByQuestionId(Integer questionId) {
		return choiceMapper.selectByQuestionId(questionId);
	}

	@Transactional
	public void addUserChoice(List<UserChoice> list) {
		for (UserChoice uc : list) {
			userChoiceMapper.insert(uc);
		}
	}

	public List<UserChoiceDisplay> getUserChoiceHistory(String game, int userId) {
		UserChoice uc = new UserChoice();
		uc.setGame(game);
		uc.setUserId(userId);
		return userChoiceMapper.selectHistory(uc);
	}

	public List<UserScoreDisplay> getRankByGame(String game) {
		return userScoreMapper.selectRank(game);
	}

	public void addUserScore(UserScore us) {
		userScoreMapper.insert(us);

	}

	public List<UserChoice> getUserChoiceByResult(UserChoice uc) {
		return userChoiceMapper.selectByGameAndQuestionAndChoice(uc);
	}

	public List<UserChoice> getUserByMutiChoice(UserChoiceDisplay uc) {
		return userChoiceMapper.selectByGameAndQuestion(uc);
	}
	
	public List<UserChoice> getUserByMutiWrongChoice(UserChoiceDisplay uc) {
		return userChoiceMapper.selectByGameAndNoQuestion(uc);
	}

	public Choice getChoiceById(Integer id) {
		return choiceMapper.selectByPrimaryKey(id);
	}

	public void updateUserScore(AddScore as) {
		userScoreMapper.updateScoreBatch(as);
	}

	public void addGameResult(GameResult game) {
		gameResultMapper.insert(game);
	}

	@Transactional
	public void updateUserScore(List<UserChoice> list, int point) {
		for (UserChoice u : list) {
			UserScore record = new UserScore();
			record.setUser(u.getUserId());
			record.setScore(point);
			userScoreMapper.updateScoreByUserId(record);
		}

	}
}
