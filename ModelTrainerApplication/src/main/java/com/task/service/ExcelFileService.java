package com.task.service;

import java.util.List;

import com.task.entity.Player;
import com.task.exception.ExcelFileCreationException;


public interface ExcelFileService {
	public void generateExcelFile(List<Player> players) throws ExcelFileCreationException;
}
