package com.dt.derbyday.service;

import java.util.List;

import com.dt.derbyday.dto.UserScoreDisplay;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.Question;

public interface ManageService {

	void createGame(GameInfo game);
	
	void deleteGame(int id);
	
	List<GameInfo> getGames(String sort);
	
	void createQuestion(Question question);
	
	void deleteQuestion(int id);
	
	void createChoice(Choice choice);
	
	void deleteChoice(int id);
	
	void createWholeQuestion(Question question,List<Choice> choices);
	
	List<UserScoreDisplay> getUserScoreRank(String game);
}
