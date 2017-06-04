package com.dt.derbyday.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.derbyday.mapper.GameInfoMapper;
import com.dt.derbyday.model.GameInfo;
import com.dt.derbyday.service.ManageService;
@Service("manageService")
public class ManageServiceImpl implements ManageService{
	@Autowired
	GameInfoMapper gameMapper;

	@Override
	public void createGame(GameInfo game) {
		gameMapper.insert(game);
		
	}

	@Override
	public List<GameInfo> getGames() {
		return gameMapper.selectAllGame();
	}

}
