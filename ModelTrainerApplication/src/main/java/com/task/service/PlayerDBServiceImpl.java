package com.task.service;

import java.util.ArrayList;
import java.util.List;

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
	public List<Object> savePlayers(List<String> jsonList, List<String> xmlList) {
		logger.debug("inside of savePlayers(List<String> jsonList, List<String> xmlList) method");
		List<Player> players = new ArrayList<>();
		List<String> ErrorRecords = new ArrayList<>();

		jsonList.forEach(jsonString -> {
			try {
				players.add(CommonUtil.FileToObject(jsonString));
			} catch (ErrorFileException e1) {
				ErrorRecords.add(jsonString);
				logger.error("Json file processing error");
			}
		});

		try {
			playerRepository.saveAll(players);
		} catch (PlayerDataAccessException e) {
			logger.error("Error while saving players :", e);
		}

		
		List<Object> records = new ArrayList<>();
		records.add(players);
		records.add(ErrorRecords);

		return records;
	}

	@Override
	public List<Player> FindAllPlayers() {
		return (List<Player>) playerRepository.findAll();
	}

}
