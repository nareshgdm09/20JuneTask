package com.task.scheduletask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.task.common.CommonUtil;
import com.task.common.Constants;
import com.task.entity.Player;
import com.task.exception.DirectoriesNotFoundException;
import com.task.exception.ExcelFileCreationException;
import com.task.exception.FileMoveException;
import com.task.service.ExcelFileService;
import com.task.service.FileDirectoryService;
import com.task.service.PlayerDBService;

@Component
public class FileScanScheduler {
	private static final Logger logger = LoggerFactory.getLogger(FileScanScheduler.class);
	@Autowired
	FileDirectoryService fileDirectoryService;

	@Autowired
	PlayerDBService PlayerDBService;

	@Autowired
	ExcelFileService excelFileService;

	@SuppressWarnings("unchecked")
	@Scheduled(initialDelay = 1000, fixedRate = 8000)
	public void DirectoryScanScheduledMethod() throws Exception {
		System.out.println("inside modelTrainerscheduledMethod()");
		logger.debug("inside modelTrainerscheduledMethod()");
		List<String> jsonFiles = new ArrayList<String>();
		List<String> xmlFiles = new ArrayList<String>();
		try {
			jsonFiles = fileDirectoryService.getTypeFiles(Constants.JSONPATH, Constants.JSONEXT);
			xmlFiles = fileDirectoryService.getTypeFiles(Constants.XMLPATH, Constants.XMLEXT);
		} catch (Exception e) {
			logger.error("Json or Xml direcetory not exist");
			throw new DirectoriesNotFoundException("Json or Xml direcetory not exist at specifie path");
		}

		if (!jsonFiles.isEmpty() && !xmlFiles.isEmpty()) {
			CommonUtil.findCommonFiles(jsonFiles, xmlFiles);

			Map<String, List<Player>> validAndErrorRecords = PlayerDBService.savePlayers(jsonFiles, xmlFiles);

			List<Player> validRecords = validAndErrorRecords.get("correctRecords");
			List<Player> errorRecords = validAndErrorRecords.get("errorRecords");

			try {
				if (!validRecords.isEmpty())
					fileDirectoryService.moveCompletedFiles(validRecords);
				if (!errorRecords.isEmpty())
					fileDirectoryService.moveErrorFiles(errorRecords);
			} catch (FileMoveException e) {
				logger.error("Error while moving File");
				e.printStackTrace();
			}

			excelFileService.generateExcelFile(validRecords);
		}
	}
}
