package com.task.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.common.Constants;
import com.task.entity.Player;
import com.task.exception.ExcelFileCreationException;

@Component
public class ExcelFileServiceImpl implements ExcelFileService {

	public void generateExcelFile(List<Player> players) throws ExcelFileCreationException {

		final String excelpath = Constants.Excelpath;

		for (Player player : players) {

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet(" Player Info ");
			XSSFRow row;

			try {

				Map<String, Object[]> empinfo = generateExcelData(player);

				Set<String> keyid = empinfo.keySet();
				int rowid = 0;

				for (String key : keyid) {
					row = spreadsheet.createRow(rowid++);
					Object[] objectArr = empinfo.get(key);
					int cellid = 0;

					for (Object obj : objectArr) {
						Cell cell = row.createCell(cellid++);
						if (obj instanceof String)
							cell.setCellValue((String) obj);
						else if (obj instanceof Integer)
							cell.setCellValue((Integer) obj);
					}
				}

				createExcelDir(excelpath);

				FileOutputStream out = new FileOutputStream(new File(excelpath + player.getId() + ".xlsx"));
				workbook.write(out);
				out.close();
				File file = new File(excelpath + player.getId() + ".xml");
				byte[] xmlString = player.getXmlFile();
				FileUtils.writeByteArrayToFile(file, xmlString);

			} catch (Exception e) {
				try {
					workbook.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				throw new ExcelFileCreationException("Excel file generating error");
			}

		}
	}

	public static void createExcelDir(String excelpath) {
		File file1 = new File(excelpath);
		if (!file1.exists()) {
			file1.mkdir();
		}
	}

	public static Map<String, Object[]> generateExcelData(Player player) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = new String(player.getJsonFile());

		JsonNode playerNode = null;
		try {
			playerNode = objectMapper.readTree(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Object[]> playerinfo = new TreeMap<String, Object[]>();
		playerinfo.put("1", new Object[] { "ID", "NAME", "ADDRESS", "DOB", "RUNS", "WICKETS" });
		playerinfo.put("2", new Object[] { player.getId(), playerNode.get("name"), playerNode.get("address"),
				playerNode.get("dob"), playerNode.get("runs"), playerNode.get("wickets") });
		return playerinfo;
	}
}
