package com.education.poi;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class ExcelReadTest {

    private String path = "D:/Workspace/IdeaProjects/OnlineEducation/demo_poi/target/";

    @Test
    public void testRead03() throws Exception {


        // 获取一个文件流
        InputStream is = new FileInputStream(path + "test.xls");

        Workbook workbook = new HSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 读取第一行第一列
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);

        // 输出单元内容
        System.out.println(cell.getStringCellValue());

        // 操作结束，关闭文件
        is.close();
    }

    @Test
    public void testRead07() throws Exception {


        // 获取一个文件流
        InputStream is = new FileInputStream(path + "test.xlsx");
        Workbook workbook = new XSSFWorkbook(is);

        Sheet sheet = workbook.getSheetAt(0);

        // 读取第一行第一列
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(0);

        // 输出单元内容
        System.out.println(cell.getStringCellValue());

        // 操作结束，关闭文件
        is.close();
    }

    @Test
    public void testRead2007() throws Exception{
        InputStream is = new FileInputStream(path + "test.xlsx");
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        // 读取第一行第一列
        Row row = sheet.getRow(0);
        Cell cell1 = row.getCell(0);
        Cell cell2 = row.getCell(1);
        // 输出单元内容
        System.out.println(cell1.getStringCellValue());
        System.out.println(cell2.getNumericCellValue());
        // 操作结束，关闭文件
        is.close();
    }

    @Test
    public void testFormula() throws Exception{
        InputStream is = new FileInputStream("d:/test.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        // 读取第三行第二列
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(1);
        // 公式计算器
        FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        // 输出单元内容
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_FORMULA) {//2
            //得到公式
            String formula = cell.getCellFormula();
            System.out.print(formula);
            CellValue evaluate = formulaEvaluator.evaluate(cell);
            //String cellValue = String.valueOf(evaluate.getNumberValue());
            String cellValue = evaluate.formatAsString();
            System.out.println(cellValue);
        }
    }


}
