package com.task.service;

import java.util.List;

import com.task.entity.Player;

public interface PlayerWebService {
	public List<Player> findPlayer(Player player);
}
