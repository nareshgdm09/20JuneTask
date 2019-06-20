package com.task.service;

import java.util.List;

import com.task.entity.Player;
import com.task.exception.PlayerDataAccessException;

public interface PlayerWebService {
	public List<Player> findPlayer(Player player);
}
