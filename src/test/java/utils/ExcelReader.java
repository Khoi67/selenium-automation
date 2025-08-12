package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    public static String getCellData(String filePath, String sheetName, int rowIndex, int colIndex) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook;

            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IllegalArgumentException("Unsupported file format: " + filePath);
            }

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found.");
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) return "";

            Cell cell = row.getCell(colIndex);
            if (cell == null) return "";

            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> String.valueOf(cell.getNumericCellValue());
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                case FORMULA -> cell.getCellFormula();
                default -> "";
            };

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
    }

}
