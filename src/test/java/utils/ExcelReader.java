package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static Object[][] getData(String filePath, String sheetName) {
        List<Object[]> records = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows(); // total rows (including header)
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells(); // based on header

            for (int i = 1; i < rowCount; i++) { // bỏ qua dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue; // skip null row

                Object[] rowData = new Object[colCount];
                boolean isRowEmpty = true;

                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        rowData[j] = "";
                    } else {
                        cell.setCellType(CellType.STRING);
                        String value = cell.getStringCellValue().trim();
                        rowData[j] = value;

                        if (!value.isEmpty()) {
                            isRowEmpty = false;
                        }
                    }
                }

                if (!isRowEmpty) {
                    records.add(rowData); // chỉ thêm dòng không trống
                }
            }

            // Chuyển List sang Object[][]
            Object[][] finalData = new Object[records.size()][colCount];
            for (int i = 0; i < records.size(); i++) {
                finalData[i] = records.get(i);
            }

            return finalData;

        } catch (IOException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}