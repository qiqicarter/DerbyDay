package com.dt.derbyday.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dt.derbyday.dto.UserScoreDisplay;
import com.dt.derbyday.mapper.ChoiceMapper;
import com.dt.derbyday.mapper.GameInfoMapper;
import com.dt.derbyday.mapper.QuestionMapper;
import com.dt.derbyday.mapper.UserScoreMapper;
import com.dt.derbyday.model.Choice;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.model.Question;
import com.dt.derbyday.service.ManageService;
@Service("manageService")
public class ManageServiceImpl implements ManageService{
	@Autowired
	GameInfoMapper gameMapper;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	ChoiceMapper choiceMapper;
	
	@Autowired
	UserScoreMapper userScoreMapper;

	@Override
	public void createGame(GameInfo game) {
		gameMapper.insert(game);
		
	}

	@Override
	public List<GameInfo> getGames(String sort) {
		return gameMapper.selectAllGame(sort);
	}

	@Override
	public void deleteGame(int id) {
		gameMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void createQuestion(Question question) {
		questionMapper.insert(question);
	}

	@Override
	public void createChoice(Choice choice) {
		choiceMapper.insert(choice);
		
	}

	@Override
	@Transactional
	public void createWholeQuestion(Question question, List<Choice> choices) {
		questionMapper.insert(question);
		for(Choice c : choices){
			c.setQuestion(question.getId());
			choiceMapper.insert(c);
		}
		
	}

	@Override
	public List<UserScoreDisplay> getUserScoreRank(String game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteQuestion(int id) {
		questionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteChoice(int id) {
		choiceMapper.deleteByPrimaryKey(id);
	}

}
