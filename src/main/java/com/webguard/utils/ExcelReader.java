package com.webguard.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    /**
     * Reads Excel data and returns it as a 2D Object array for TestNG DataProvider.
     * Starts reading from row index 1 (skipping headers at row 0).
     */
    public static Object[][] getTestData(String filePath, String sheetName) {
        // Auto-generate sample file if missing
        ExcelGenerator.generateSampleExcel(filePath);

        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " not found in " + filePath);
            }

            int rowCount = sheet.getLastRowNum(); // Returns 0-based index of last row
            int colCount = sheet.getRow(0).getLastCellNum(); // Returns number of cells in the first row

            data = new Object[rowCount][colCount];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    if (row != null && row.getCell(j) != null) {
                        data[i - 1][j] = formatter.formatCellValue(row.getCell(j));
                    } else {
                        data[i - 1][j] = "";
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + filePath);
            e.printStackTrace();
        }
        return data;
    }
}
