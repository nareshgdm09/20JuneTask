package com.task.service;

import java.util.List;
import java.util.Map;

import com.task.entity.Player;

public interface PlayerDBService {
	public Map<String, List<Player>> savePlayers(List<String> jsonList, List<String> xmlList);

	public List<Player> FindAllPlayers();
}
