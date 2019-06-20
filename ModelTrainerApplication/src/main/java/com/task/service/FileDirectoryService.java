package com.task.service;

import java.util.List;

import com.task.entity.Player;
import com.task.exception.FileMoveException;

public interface FileDirectoryService {

	List<String> getTypeFiles(String dirPath, String fileType);

	void moveCompletedFiles(List<Player> playerRecords) throws FileMoveException;
	
	void moveErrorFiles(List<String> errorRecords) throws FileMoveException;
}
