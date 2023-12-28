package com.example.memberManagement.Util;

import com.example.memberManagement.dto.MemberRenderDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelGeneratorUtil {
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    public static void writeHeader() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Member");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Membership Number", style);
        createCell(row, 1, "ID", style);
        createCell(row, 2, "Name", style);
        createCell(row, 3, "Mobile phone number", style);
        createCell(row, 4, "Email", style);
        createCell(row, 5, "Join date", style);

    }

    private static void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell == null) {
            cell.setCellValue("");
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private static void write(List<MemberRenderDTO> listMember) {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (MemberRenderDTO record : listMember) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, String.valueOf(record.getMemberNo()), style);
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getMobilePhone(), style);
            createCell(row, columnCount++, record.getEmail(), style);
            createCell(row, columnCount++, String.valueOf(record.getJoinDate()), style);
        }
    }

    public static void generateExcelFile(HttpServletResponse response, List<MemberRenderDTO> listMember) throws IOException {
        writeHeader();
        write(listMember);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
