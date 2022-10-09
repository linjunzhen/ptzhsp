/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * 描述 Excel模板替换
 * 
 * @author Rider Chen
 * @created 2016-3-8 上午10:55:00
 */
public class ExcelUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(StringUtil.class);
    /**
     * 
     * 描述 替换Excel模板文件内容
     * 
     * @author Rider Chen
     * @created 2016-3-8 上午10:57:37
     * @param datas
     * @param sourceFilePath
     * @param response
     * @param xlsName
     * @return
     */
    public static boolean replaceModelXSSF(List<ExcelReplaceDataVO> datas,
            String sourceFilePath, HttpServletResponse response, String xlsName) {
        boolean bool = true;
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(sourceFilePath);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (ExcelReplaceDataVO data : datas) {
                // 获取单元格内容
                XSSFRow row = sheet.getRow(data.getRow());
                XSSFCell cell = row.getCell((short) data.getColumn());
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String str = cell.getStringCellValue();
                // 替换单元格内容
                str = str.replace(data.getKey(), data.getValue());
                // 写入单元格内容
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(str);
            }

            // 输出文件
            // FileOutputStream fileOut = new FileOutputStream(targetFilePath);
            // workbook.write(fileOut);
            // fileOut.close();

            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-disposition", "attachment;filename="
                    + xlsName);
            OutputStream ouputStream = resp.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            bool = false;
            log.error(e.getMessage());
        }
        return bool;
    }

    /**
     * 
     * 描述 替换Excel模板文件内容
     * 
     * @author Rider Chen
     * @created 2016-3-8 上午10:57:48
     * @param datas
     * @param sourceFilePath
     * @param response
     * @param xlsName
     * @return
     */
    public static boolean replaceModelHSSF(List<ExcelReplaceDataVO> datas,
            String sourceFilePath, HttpServletResponse response, String xlsName) {
        boolean bool = true;
        try {

            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
                    sourceFilePath));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            HSSFSheet sheet = workbook.getSheetAt(0);

            for (ExcelReplaceDataVO data : datas) {
                // 获取单元格内容
                HSSFRow row = sheet.getRow(data.getRow());
                HSSFCell cell = row.getCell((short) data.getColumn());
                String str = cell.getStringCellValue();

                // 替换单元格内容
                str = str.replace(data.getKey(), data.getValue());

                // 写入单元格内容
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(str);
            }

            // 输出文件
            // FileOutputStream fileOut = new FileOutputStream(targetFilePath);
            // workbook.write(fileOut);
            // fileOut.close();
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-disposition", "attachment;filename="
                    + xlsName);
            OutputStream ouputStream = resp.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            bool = false;
            log.error(e.getMessage());
        }
        return bool;
    }
}
