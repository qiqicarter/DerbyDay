package com.dt.derbyday.service;

import java.util.List;

import com.dt.derbyday.dto.AddScore;
import com.dt.derbyday.dto.ChoiceQueryDto;
import com.dt.derbyday.dto.UserChoiceDisplay;
import com.dt.derbyday.dto.UserScoreDisplay;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.GameResult;
import com.dt.derbyday.model.Question;
import com.dt.derbyday.model.UserChoice;
import com.dt.derbyday.model.UserScore;

public interface GameService {

	GameInfo getGameInfoByGame(String game);
	
	GameInfo getGameInfoByTime();
	
	List<Question> getQuestionByGame(String game);
	
	Question getQuestionById(Integer questionId);
	
	List<Choice> getChoiceByQuestionId(Integer questionId);
	
	Choice getChoiceById(Integer id);
	
	void addUserChoice(List<UserChoice> list);
	
	List<UserChoiceDisplay> getUserChoiceHistory(String game,int userId);
	
	List<UserScoreDisplay> getRankByGame(String game);
	
	List<UserChoice> getUserChoiceByResult(UserChoice uc);
	
	List<UserChoice> getUserByMutiChoice(UserChoiceDisplay uc);
	
	List<UserChoice> getUserByMutiWrongChoice(UserChoiceDisplay uc);
	
	void addUserScore(UserScore us);
	
	void updateUserScore(AddScore as);
	
	void updateUserScore(List<UserChoice> list,int point);
	
	void addGameResult(GameResult game);
	
	List<ChoiceQueryDto> getChoiceStatistics(String game);
	
	int getQuestionCount(Integer question);
    
    int getChoiceCount(Integer choice);
}
