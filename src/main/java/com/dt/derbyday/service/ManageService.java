package com.dt.derbyday.service;

import java.util.List;

import com.dt.derbyday.model.GameInfo;

public interface ManageService {

	void createGame(GameInfo game);
	
	void deleteGame(int id);
	
	List<GameInfo> getGames();
}
