package com.webguard.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    /**
     * Generates a sample testdata.xlsx file if it doesn't already exist.
     */
    public static void generateSampleExcel(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("Excel test data file already exists at: " + filePath);
            return;
        }

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        System.out.println("Generating sample Excel test data file at: " + filePath);

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {
            
            Sheet sheet = workbook.createSheet("LoginData");

            // Define rows
            String[][] testCases = {
                {"Username", "Password", "ExpectedStatus"}, // Header
                {"admin", "admin123", "success"},
                {"testuser", "password123", "success"},
                {"invalid_user", "wrongpass", "failure"},
                {"admin", "wrong_pass", "failure"}
            };

            for (int r = 0; r < testCases.length; r++) {
                Row row = sheet.createRow(r);
                for (int c = 0; c < testCases[r].length; c++) {
                    Cell cell = row.createCell(c);
                    cell.setCellValue(testCases[r][c]);
                }
            }

            // Auto-size columns
            for (int c = 0; c < 3; c++) {
                sheet.autoSizeColumn(c);
            }

            workbook.write(fos);
            System.out.println("Successfully generated Excel test data.");
        } catch (IOException e) {
            System.err.println("Failed to write sample Excel file.");
            e.printStackTrace();
        }
    }
}
