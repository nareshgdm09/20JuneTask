package com.task.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.entity.Player;
import com.task.repository.PlayerRepository;

@Service
public class PlayerWebServiceImpl implements PlayerWebService {
	private static final Logger logger = LoggerFactory.getLogger(PlayerWebServiceImpl.class);
	@Autowired
	private PlayerRepository playerRepository;

	public List<Player> findPlayer(Player player) {
		logger.debug("inside findPlayer(Player player) method :");
		List<Player> players = null;
		try {
			players = (List<Player>) playerRepository.findCustomPayer(player);
		} catch (Exception e) {
			logger.error("error :",e);
		}
		return players;
	}

}
