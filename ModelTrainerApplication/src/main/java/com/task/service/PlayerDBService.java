package com.task.service;

import java.util.List;

import com.task.entity.Player;

public interface PlayerDBService {
	public List<Object> savePlayers(List<String> jsonList, List<String> xmlList);

	public List<Player> FindAllPlayers();
}
