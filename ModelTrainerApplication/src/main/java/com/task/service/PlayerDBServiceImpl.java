package com.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.common.CommonUtil;
import com.task.entity.Player;
import com.task.exception.ErrorFileException;
import com.task.exception.PlayerDataAccessException;
import com.task.repository.PlayerRepository;

@Component
public class PlayerDBServiceImpl implements PlayerDBService {
	private static final Logger logger = LoggerFactory.getLogger(PlayerDBServiceImpl.class);

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	FileDirectoryService fileDirectoryService;
	@Autowired
	ExcelFileService excelFileService;

	@Override
	public Map<String, List<Player>> savePlayers(List<String> jsonList, List<String> xmlList) {
		logger.debug("inside of savePlayers(List<String> jsonList, List<String> xmlList) method");
		List<Player> players = new ArrayList<>();
		List<Player> ErrorRecords = new ArrayList<>();

		jsonList.forEach(jsonString -> {
			try {
				players.add(CommonUtil.FileToObject(jsonString));
			} catch (ErrorFileException e1) {
				ErrorRecords.add(new Player(Integer.valueOf(jsonString), null, null, null, null, null));
				logger.error("Json file processing error");
			}
		});

		try {
			playerRepository.saveAll(players);
		} catch (Exception e) {
			logger.error("Error while saving players :", e);
		}

		Map<String, List<Player>> records = new HashMap<>();
		records.put("correctRecords", players);
		records.put("errorRecords", ErrorRecords);

		return records;
	}

	@Override
	public List<Player> FindAllPlayers() {
		return (List<Player>) playerRepository.findAll();
	}

}
