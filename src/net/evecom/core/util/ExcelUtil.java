/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.evecom.core.poi.ExcelReplaceDataVO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * EXCEL导出工具类
 * 
 * @author Flex Hu
 * 
 * @version 1.1
 */
public class ExcelUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExcelUtil.class);
    /**
     * 单元格INT类型
     */
    public static final int CELL_TYPE_INT = 7;
    /**
     * 单元格LONG类型
     */
    public static final int CELL_TYPE_LONG = 8;

    /**
     * 获取自定颜色 create by chensheng at 2010-9-19
     * 
     * @param colorStr
     *            颜色字符串(例如:#CCFFCC)
     * @return
     */
    public static int[] getCustomizeCellColor(HSSFWorkbook workbook,
            String colorStr) {
        int[] color = new int[3];

        color[0] = Integer.parseInt(colorStr.substring(1, 3), 16);

        color[1] = Integer.parseInt(colorStr.substring(3, 5), 16);

        color[2] = Integer.parseInt(colorStr.substring(5, 7), 16);

        return color;
    }

    /**
     * 功能描述： 设置字体属性
     * 
     * @param workbook
     *            要设置字体的工作簿
     * @param fontName
     *            字体名称
     * @param fontSize
     *            字体大小
     * @param ifBold
     *            是否粗体显示(0:否 1:是)
     * @return
     * @创建人 陈晟
     * @创建日期 2010.11.1
     */
    public static HSSFFont setFontProperty(HSSFWorkbook workbook,
            String fontName, short fontSize, int ifBold) {
        HSSFFont font = workbook.createFont();

        font.setFontName(fontName);

        font.setFontHeightInPoints(fontSize);

        if (ifBold == 1) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        } else {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        }

        return font;
    }

    /**
     * 功能描述： 设置样式
     * 
     * @param workbook
     *            工作簿对象
     * @param font
     *            字体对象
     * @param alignMethod
     *            水平对齐方式
     * @param vAlignMethod
     *            垂直对齐方式
     * @param ifWrap
     *            是否换行
     * @param foreGroundColorIndex
     *            背景色索引
     * @param fillPattern
     *            填充方式
     * @param borderStyle
     *            边框样式
     * @创建人 陈晟
     * @创建日期 2010.11.1
     * @return
     */
    public static HSSFCellStyle setStyle(HSSFWorkbook workbook, HSSFFont font,
            short alignMethod, short vAlignMethod, boolean ifWrap,
            short foreGroundColorIndex, short fillPattern, short borderStyle) {
        HSSFCellStyle style = workbook.createCellStyle();

        if (font != null) {
            style.setFont(font);
        }

        if (alignMethod != -99) {
            style.setAlignment(alignMethod);
        }

        if (vAlignMethod != -99) {
            style.setVerticalAlignment(vAlignMethod);
        }

        if (foreGroundColorIndex != -99) {
            style.setFillForegroundColor(foreGroundColorIndex);
        }

        if (fillPattern != -99) {
            style.setFillPattern(fillPattern);
        }

        if (borderStyle != -99) {
            style.setBorderLeft(borderStyle);

            style.setBorderRight(borderStyle);

            style.setBorderTop(borderStyle);

            style.setBorderBottom(borderStyle);
        }

        style.setWrapText(ifWrap);

        return style;
    }

    /**
     * 功能描述： 创建标题
     * 
     * @param title
     *            要显示的标题
     * @param sheet
     *            工作簿中的工作表
     * @param titleRowNum
     *            标题所在行
     * @param titleCellNum
     *            标题所在列
     * @param cellRangeAddress
     *            合并单元格对象
     * @param style
     *            样式
     * @return
     * @创建人 陈晟
     * @创建日期 2010.11.1
     */
    public static void createTitle(String title, HSSFSheet sheet,
            Integer titleRowNum, short rowHeight, Integer titleCellNum,
            CellRangeAddress cellRangeAddress, HSSFCellStyle style) {
        HSSFRow titleRow = sheet.createRow(titleRowNum);

        titleRow.setHeight(rowHeight);

        HSSFCell titleCell = titleRow.createCell(titleCellNum);

        titleCell.setCellValue(new HSSFRichTextString(title));

        sheet.addMergedRegion(cellRangeAddress);

        titleCell.setCellStyle(style);
    }

    /**
     * 功能描述： 创建表头
     * 
     * @param sheet
     *            工作簿中的工作表
     * @param headerColumns
     *            表头名称集合
     * @param startRowNum
     *            开始输出的行号
     * @param style
     *            单元格样式
     * @param rowHeight
     *            行高
     * @param headerLength
     *            列宽
     * @return
     * @创建人 陈晟
     * @创建日期 2010.11.1
     */
    public static void createHeaderColumns(HSSFSheet sheet,
            List<String> headerColumns, Integer startRowNum,
            HSSFCellStyle style, short rowHeight, List<Integer> headerLength) {
        if (headerColumns.size() > 0) {
            HSSFRow headerRow = sheet.createRow(startRowNum);

            headerRow.setHeight(rowHeight);

            for (int i = 0; i < headerColumns.size(); i++) {
                HSSFCell headerCell = headerRow.createCell(i);

                sheet.setColumnWidth(i, headerLength.get(i));

                headerCell.setCellValue(new HSSFRichTextString(headerColumns
                        .get(i)));

                headerCell.setCellStyle(style);
            }
        }
    }

    /**
     * 功能描述： 创建内容部分
     * 
     * @param sheet
     *            工作簿中的工作表
     * @param valueToDisplayList
     *            要显示的数据的集合
     * @param startRowNum
     *            开始输出的行号
     * @return
     * @创建人 陈晟
     * @创建日期 2010.11.1
     */
    public static void createContent(HSSFSheet sheet,
            List<Object> valueToDisplayList, Integer startRowNum,
            HSSFCellStyle contentStyle, short rowHeight) {
        for (int a = 0; a < valueToDisplayList.size(); a++) {
            HSSFRow contentRow = sheet.createRow(a + startRowNum);
            contentRow.setHeight(rowHeight);
            List<Object> list = (List<Object>) valueToDisplayList.get(a);
            for (int b = 0; b < list.size(); b++) {
                HSSFCell contentCell = contentRow.createCell(b);
                contentCell.setCellStyle(contentStyle);
                contentCell.setCellValue(new HSSFRichTextString(list.get(b)
                        .toString()));
            }
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月19日 下午3:50:13
     * @param xlsPath
     * @param sheetNumber
     * @param rowNum
     * @param colNum
     * @return
     */
    public static Object getCellValue(String xlsPath,int sheetNumber,int rowNum,int colNum){
        POIFSFileSystem fs;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(xlsPath));
            // 取得相应工作簿
            workbook = new HSSFWorkbook(fs);
            sheet = workbook.getSheetAt(sheetNumber);
            sheet.setActive(true);
            HSSFRow row = sheet.getRow(rowNum);
            Object cellValue = ExcelUtil.getCellValue(row, colNum);
            return cellValue;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取单元格的值
     * 
     * @param row
     *            :当前行
     * @param cellIndex
     *            :单元格的索引
     * @return
     */
    public static Object getCellValue(HSSFRow row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            // 获取单元格类型
            int cellType = cell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    return row.getCell(cellIndex).getStringCellValue().trim();
                case Cell.CELL_TYPE_BLANK:
                    return "";
                case Cell.CELL_TYPE_BOOLEAN:
                    return row.getCell(cellIndex).getBooleanCellValue();
                case Cell.CELL_TYPE_FORMULA:
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if(HSSFDateUtil.isCellDateFormatted(cell)){                        
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
                        return sdf.format(cell.getDateCellValue()); 
                    }else{
                        DecimalFormat df = new DecimalFormat("0");
                        return df.format(cell.getNumericCellValue());
                    }
                    /*
                     * Long longVal = Math.round(cell.getNumericCellValue());
                     * Double doubleVal = cell.getNumericCellValue(); if
                     * (Double.parseDouble(longVal + ".0") == doubleVal) {
                     * return longVal.intValue(); } else { return doubleVal; }
                     */
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 获取单元格的值
     * 
     * @author Flex Hu
     * @param row
     *            :当前行
     * @param cellIndex
     *            :单元格的索引
     * @param cellType
     *            :想要转换的单元格类型,如果不要转换,则传-1,
     * 
     * @return
     */
    public static Object getCellValue(HSSFRow row, int cellIndex, int cellType) {
        if (cellType != ExcelUtil.CELL_TYPE_INT
                && cellType != ExcelUtil.CELL_TYPE_LONG) {
            row.getCell(cellIndex).setCellType(cellType);
        } else if (cellType == ExcelUtil.CELL_TYPE_INT
                || cellType == ExcelUtil.CELL_TYPE_LONG) {
            row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_NUMERIC);
        }
        Cell cell = row.getCell(cellIndex);
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                return row.getCell(cellIndex).getStringCellValue().trim();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_BOOLEAN:
                return row.getCell(cellIndex).getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                break;
            case Cell.CELL_TYPE_NUMERIC:
                return row.getCell(cellIndex).getNumericCellValue();
            case ExcelUtil.CELL_TYPE_INT:
                Long longVal = Math.round(cell.getNumericCellValue());
                Double doubleVal = cell.getNumericCellValue();
                if (Double.parseDouble(longVal + ".0") == doubleVal) {
                    return longVal.intValue();
                } else {
                    return doubleVal;
                }
            case ExcelUtil.CELL_TYPE_LONG:
                Long longVal2 = Math.round(cell.getNumericCellValue());
                Double doubleVal2 = cell.getNumericCellValue();
                if (Double.parseDouble(longVal2 + ".0") == doubleVal2) {
                    return longVal2;
                } else {
                    return doubleVal2;
                }
            default:
                break;
        }
        return null;
    }
    
    /**
     * 获取单元格的值
     * 
     * @param row
     *            :当前行
     * @param cellIndex
     *            :单元格的索引
     * @return
     */
    public static Object getCellValue(XSSFRow row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            // 获取单元格类型
            int cellType = cell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_STRING:
                    return row.getCell(cellIndex).getStringCellValue().trim();
                case Cell.CELL_TYPE_BLANK:
                    return "";
                case Cell.CELL_TYPE_BOOLEAN:
                    return row.getCell(cellIndex).getBooleanCellValue();
                case Cell.CELL_TYPE_FORMULA:
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if(HSSFDateUtil.isCellDateFormatted(cell)){                        
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
                        return sdf.format(cell.getDateCellValue()); 
                    }else{
                        DecimalFormat df = new DecimalFormat("0");
                        return df.format(cell.getNumericCellValue());
                    }
                    /*
                     * Long longVal = Math.round(cell.getNumericCellValue());
                     * Double doubleVal = cell.getNumericCellValue(); if
                     * (Double.parseDouble(longVal + ".0") == doubleVal) {
                     * return longVal.intValue(); } else { return doubleVal; }
                     */
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 
     * 描述 将数据写入到excel当中
     * 
     * @author Flex Hu
     * @created 2014年11月27日 下午5:08:07
     * @param dataList
     *            :数据列表
     * @param startRow
     *            :开始写入行
     * @param startCol
     *            :开始写入列
     * @param tplPath
     *            :模版路径
     * @param outFilePath
     *            :输出文件路径
     */
    public static void writeDataToDisk(List dataList, int startRow,
            int startCol, String tplPath, String outFilePath) {
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(tplPath));
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        // 创建sheet页
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);
        // 内容样式
        HSSFCellStyle contentStyle = hssfworkbook.createCellStyle();
        for (int i = 0; i < dataList.size(); i++) {
            // Object[] results = (Object[]) dataList.get(i);
            List results = (List) dataList.get(i);
            // 取得第一行
            HSSFRow hssfrow = hssfsheet.createRow(startRow - 1 + i);
            hssfrow.setHeight((short) 1000);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 靠右
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            contentStyle.setWrapText(true);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            // contentStyle.set
            // 开始向右创建单元格
            for (int j = 0; j < results.size(); j++) {
                String value = "";
                HSSFCell cell = hssfrow.createCell(startCol - 1 + j);
                cell.setCellStyle(contentStyle); // 设置数据单元格的样式
                if (results.get(j) != null) {
                    value = results.get(j).toString();
                }
                cell.setCellValue(new HSSFRichTextString(value));
            }
        }
        // 输出
        FileOutputStream fileoutputstream = null;
        try {
            fileoutputstream = new FileOutputStream(outFilePath);
            hssfworkbook.write(fileoutputstream);
            fileoutputstream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }finally{
            if (fileoutputstream!=null) {
                try {
                    fileoutputstream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 获取EXCEL行数据
     * 
     * @param excelPath
     *            :excel的路径
     * @param startRowNum
     *            :起始读取行数字
     * @param startColNum
     *            :起始读取列数字
     * @return
     */
    public static List<List<Object>> getExcelRowValues(String excelPath,
            int startRowNum, int startColNum) {
        POIFSFileSystem fs;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        List<List<Object>> rowValues = new ArrayList<List<Object>>();
        try {
            fs = new POIFSFileSystem(new FileInputStream(excelPath));
            // 取得相应工作簿
            workbook = new HSSFWorkbook(fs);
            sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int k = startRowNum; k < rowCount; k++) { // 获取到第K行 HSSFRow
                List<Object> rowValue = new ArrayList<Object>();
                HSSFRow row = sheet.getRow(k);
                // 获取列数量值
                int colCount = row.getPhysicalNumberOfCells();
                for (int j = startColNum; j < colCount; j++) {
                    Object cellValue = ExcelUtil.getCellValue(row, j);
                    if (cellValue != null) {
                        rowValue.add(cellValue);
                    } else {
                        rowValue.add("");
                    }
                }
                if (rowValue.size() > 0) {
                    rowValues.add(rowValue);
                }
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return rowValues;
    }
    
    /**
     * 
     * 描述 读取行数据
     * @author Flex Hu
     * @created 2016年3月18日 下午6:46:46
     * @param excelPath
     * @param startRowNum
     * @param startColNum
     * @param sheetNum
     * @return
     */
    public static List<List<Object>> getExcelRowValues(String excelPath,
            int startRowNum, int startColNum,int sheetNum) {
        POIFSFileSystem fs;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        List<List<Object>> rowValues = new ArrayList<List<Object>>();
        try {
            fs = new POIFSFileSystem(new FileInputStream(excelPath));
            // 取得相应工作簿
            workbook = new HSSFWorkbook(fs);
            sheet = workbook.getSheetAt(sheetNum);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int k = startRowNum; k < rowCount; k++) { // 获取到第K行 HSSFRow
                List<Object> rowValue = new ArrayList<Object>();
                HSSFRow row = sheet.getRow(k);
                if(row!=null){
                   // 获取列数量值
                    int colCount = row.getPhysicalNumberOfCells();
                    for (int j = startColNum; j < colCount; j++) {
                        Object cellValue = ExcelUtil.getCellValue(row, j);
                        if (cellValue != null) {
                            rowValue.add(cellValue);
                        } else {
                            rowValue.add("");
                        }
                    }
                    if (rowValue.size() > 0) {
                        rowValues.add(rowValue);
                    }
                }
                
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return rowValues;
    }

    /**
     * 
     * 描述 创建文本单元格
     * 
     * @author Flex Hu
     * @created 2015年1月16日 上午11:28:11
     * @param hssfrow
     * @param cellStyle
     * @param colNum
     * @param value
     * @return
     */
    public static HSSFCell createTextCell(HSSFRow hssfrow,
            HSSFCellStyle cellStyle, int colNum, Object value) {
        HSSFCell cell = hssfrow.createCell(colNum);
        cell.setCellStyle(cellStyle); // 设置数据单元格的样式
        if (value != null) {
            cell.setCellValue(new HSSFRichTextString(value.toString()));
        } else {
            cell.setCellValue("");
        }
        return cell;
    }

    /**
     * 
     * 描述 创建链接单元格
     * 
     * @author Flex Hu
     * @created 2015年1月16日 下午2:29:43
     * @param hssfrow
     * @param cellStyle
     * @param colNum
     * @param lableValue
     * @param linkPath
     * @return
     */
    public static HSSFCell createLinkCell(HSSFRow hssfrow,
            HSSFCellStyle cellStyle, int colNum, String lableValue,
            String linkPath) {
        HSSFCell cell = hssfrow.createCell(colNum);
        cell.setCellStyle(cellStyle); // 设置数据单元格的样式
        if (StringUtils.isNotEmpty(lableValue)) {
            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula("HYPERLINK(\"" + linkPath + "\",\""
                    + lableValue + "\")");
        } else {
            cell.setCellValue("");
        }
        return cell;
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    log.info(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    } 
    /**
     * 
     * 描述：生成excel文件
     * @author Rider Chen
     * @created 2020年8月16日 下午3:46:27
     * @param tplPath
     *              模版路径
     * @param dataList
     *              数据列表
     * @param fileName
     *              文件名称
     * @param excludeFields
     *              需要排除的字段
     * @param outFilePath
     *              生成文件的路径
     * @param startRow
     *              开始行
     * @param startCol
     *              开始列
     */
    public static void exportXls(String tplPath,
            List<Map<String, Object>> dataList, String fileName,
            Set<String> excludeFields,String outFilePath,
            int startRow, int startCol) {
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(tplPath));
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        // 创建sheet页
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);
        
        setNewCell(dataList, excludeFields, startRow, startCol, hssfworkbook,
                hssfsheet);

        // 输出
        FileOutputStream fileoutputstream = null;
        try {
            // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(outFilePath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            fileoutputstream = new FileOutputStream(outFilePath+fileName);
            hssfworkbook.write(fileoutputstream);
            fileoutputstream.flush();
            fileoutputstream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("",e);
        } finally {
            if(null != fileoutputstream){
                try {
                    fileoutputstream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("",e);
                }
            }
        }
    }

    /**
     * 
     * 描述 导出excel文件
     * 
     * @author Flex Hu
     * @created 2016年3月8日 下午8:27:56
     * @param tplPath
     *            :模版路径
     * @param dataList
     *            :数据列表
     * @param fileName
     *            :文件名称
     * @param excludeFields
     *            :需要排除的字段
     * @param response
     */
    public static void exportXls(String tplPath,
            List<Map<String, Object>> dataList, String fileName,
            Set<String> excludeFields, HttpServletResponse response,
            int startRow, int startCol) {
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(tplPath));
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        // 创建sheet页
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);
        
        setNewCell(dataList, excludeFields, startRow, startCol, hssfworkbook,
                hssfsheet);
        OutputStream ouputStream = null;
        try {
            String tx = new String(fileName.getBytes("GBK"), "ISO8859-1");
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.ms-excel");
            // log.info("fielName:"+tx);
            resp.setHeader("Content-disposition", "attachment;filename=" + tx);
            ouputStream = resp.getOutputStream();
            hssfworkbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("",e);
        } finally {
            if(null != ouputStream){
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("",e);
                }
            }
        }
    }

    /**
     * 
     * 描述 导出excel文件
     * 
     * @author Rider Chen
     * @created 2016-3-9 下午03:00:12
     * @param tplPath
     *            模版路径
     * @param dataList
     *            数据列表
     * @param fileName
     *            文件名称
     * @param excludeFields
     *            需要排除的字段
     * @param response
     * @param startRow
     *            开始行
     * @param startCol
     *            开始列
     * @param datas
     *            需要替换的内容列表
     * @param endStr
     *            最后内容
     * @param isMergeCells
     *            是否合并列
     * @param startMergeCell
     *            合并列
     */
    public static void exportXls(String tplPath,
            List<Map<String, Object>> dataList, String fileName,
            Set<String> excludeFields, HttpServletResponse response,
            int startRow, int startCol, List<ExcelReplaceDataVO> datas
            ,String endStr,boolean isMergeCells,int[] startMergeCell) {
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        InputStream in = null;
        try {
            if (tplPath.contains("http")) {
                in = new URL(tplPath).openConnection().getInputStream();
            } else {
                in = new FileInputStream(tplPath);
            }
            fs = new POIFSFileSystem(in);
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        // 创建sheet页
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0); 

        setOldCell(datas, hssfsheet);
        for(int i=0;i<startMergeCell.length;i++){
            setNewCell(dataList, excludeFields, startRow, startCol, hssfworkbook,
                    hssfsheet,isMergeCells,startMergeCell[i]);
        }
        if(StringUtils.isNotEmpty(endStr)){
            setEndCell(dataList,  startRow, startCol, hssfworkbook,hssfsheet,endStr);
        }
        OutputStream ouputStream = null;
        try {
            String tx = new String(fileName.getBytes("GBK"), "ISO8859-1");
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.ms-excel");
            // log.info("fielName:"+tx);
            resp.setHeader("Content-disposition", "attachment;filename=" + tx);
            ouputStream = resp.getOutputStream();
            hssfworkbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("",e);
        } finally {
            if(null != ouputStream){
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("",e);
                }
            }
        }
    }
    /**
     * 
     * 描述 导出多标签excel文件
     * 
     * @author Rider Chen
     * @created 2016-3-9 下午03:00:12
     * @param tplPath
     *            模版路径
     * @param dataList
     *            数据列表
     * @param fileName
     *            文件名称
     * @param excludeFields
     *            需要排除的字段
     * @param response
     * @param startRow
     *            开始行
     * @param startCol
     *            开始列
     * @param datas
     *            需要替换的内容列表
     * @param endStr
     *            最后内容
     * @param isMergeCells
     *            是否合并列
     * @param startMergeCell
     *            合并列
     */
    public static void exportSheet(String tplPath,
            List<List<Map<String, Object>>> dataList, String fileName,
            Set<String> excludeFields, HttpServletResponse response,
            int startRow, int startCol, List<ExcelReplaceDataVO> datas
            ,String endStr,boolean isMergeCells,int[] startMergeCell) {
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(tplPath));
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        //遍历数据
        for (int i = 0; i < dataList.size(); i++) {
            List<Map<String, Object>> list= dataList.get(i);
            // 创建sheet页
            HSSFSheet hssfsheet = hssfworkbook.getSheetAt(i); 
            if(null!=datas){
                setOldCell(datas, hssfsheet);                
            }
            for(int j=0;j<startMergeCell.length;j++){
                setNewCell(list, excludeFields, startRow, startCol, hssfworkbook,
                        hssfsheet,isMergeCells,startMergeCell[j]);
            }
            if(StringUtils.isNotEmpty(endStr)){
                setEndCell(list,  startRow, startCol, hssfworkbook,hssfsheet,endStr);
            }
        }
        try {
            String tx = new String(fileName.getBytes("GBK"), "ISO8859-1");
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.ms-excel");
            // log.info("fielName:"+tx);
            resp.setHeader("Content-disposition", "attachment;filename=" + tx);
            OutputStream ouputStream = resp.getOutputStream();
            hssfworkbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
    }
    /**
     * 
     * 描述 替换旧单元数据
     * 
     * @author Rider Chen
     * @created 2016-3-9 下午03:12:47
     * @param datas
     * @param hssfsheet
     */
    private static void setOldCell(List<ExcelReplaceDataVO> datas,
            HSSFSheet hssfsheet) {
        for (ExcelReplaceDataVO data : datas) {
            // 获取单元格内容
            HSSFRow row = hssfsheet.getRow(data.getRow());
            HSSFCell cell = row.getCell((short) data.getColumn());
            String str = cell.getStringCellValue();

            // 替换单元格内容
            str = str.replace(data.getKey(), data.getValue());

            // 写入单元格内容
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(str);
        }
    }

    /**
     * 
     * 描述 新增模版内容
     * 
     * @author Rider Chen
     * @created 2016-3-9 下午03:03:33
     * @param dataList
     * @param excludeFields
     * @param startRow
     * @param startCol
     * @param hssfworkbook
     * @param hssfsheet
     */
    private static void setNewCell(List<Map<String, Object>> dataList,
            Set<String> excludeFields, int startRow, int startCol,
            HSSFWorkbook hssfworkbook, HSSFSheet hssfsheet) {
        // 内容样式
        HSSFCellStyle contentStyle = hssfworkbook.createCellStyle();
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> results = dataList.get(i);
            // 取得第一行
            HSSFRow hssfrow = hssfsheet.createRow(startRow - 1 + i);
            //hssfrow.setHeight((short) 350);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 靠右
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            contentStyle.setWrapText(true);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            // contentStyle.set
            // 开始向右创建单元格
            Iterator it = results.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                if (!excludeFields.contains(fieldName)) {
                    Object val = entry.getValue();
                    String value = "";
                    HSSFCell cell = hssfrow.createCell(startCol - 1 + j);
                    cell.setCellStyle(contentStyle); // 设置数据单元格的样式
                    if (val != null) {
                        value = val.toString();
                    }
                    cell.setCellValue(new HSSFRichTextString(value));
                    j++;
                }
            }
        }
    }
    /**
     * 
     * 描述
     * @author Rider Chen
     * @created 2016-3-16 上午09:58:56
     * @param dataList
     * @param excludeFields
     * @param startRow
     * @param startCol
     * @param hssfworkbook
     * @param hssfsheet
     * @param isMergeCells
     * @param startMergeCell
     */
    private static void setNewCell(List<Map<String, Object>> dataList,
            Set<String> excludeFields, int startRow, int startCol,
            HSSFWorkbook hssfworkbook, HSSFSheet hssfsheet,boolean isMergeCells,int startMergeCell) {
        // 内容样式
        HSSFCellStyle contentStyle = hssfworkbook.createCellStyle();

        String cellValue = "";
        int mergeStartRow = 0;//合并开始行
        int mergeEndRow = 0 ;//合并结束行
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> results = dataList.get(i);
            // 取得第一行
            HSSFRow hssfrow = hssfsheet.createRow(startRow - 1 + i);
            //hssfrow.setHeight((short) 350);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 靠右
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            contentStyle.setWrapText(true);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            // contentStyle.set
            // 开始向右创建单元格
            Iterator it = results.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                if (!excludeFields.contains(fieldName)) {
                    Object val = entry.getValue();
                    String value = "";
                    HSSFCell cell = hssfrow.createCell(startCol - 1 + j);
                    
                    cell.setCellStyle(contentStyle); // 设置数据单元格的样式
                    if (val != null) {
                        value = val.toString();
                    }
                    if(isMergeCells&(j+1)==startMergeCell){
                        if(!"".equals(value)&&cellValue.equals(value)){
                            mergeEndRow = startRow - 1 + i;
                        }else{
                            if(mergeEndRow-mergeStartRow>0){
                                hssfsheet.addMergedRegion(new Region(mergeStartRow, 
                                        (short) (startMergeCell-1), mergeEndRow,
                                        (short)(startMergeCell-1)));
                                
                            }
                            mergeStartRow = startRow - 1 + i;
                        }
                        cellValue = value;
                    }
                    cell.setCellValue(new HSSFRichTextString(value));
                    j++;
                }
            }
            
        }
    }
    /**
     * 
     * 描述 补充结尾内容并合并
     * @author Rider Chen
     * @created 2016-3-15 上午11:13:06
     * @param dataList
     * @param excludeFields
     * @param startRow
     * @param startCol
     * @param hssfworkbook
     * @param hssfsheet
     * @param endStr
     */
    private static void setEndCell(List<Map<String, Object>> dataList,int startRow,
            int startCol,HSSFWorkbook hssfworkbook, HSSFSheet hssfsheet,String endStr) {
        // 内容样式
        HSSFCellStyle contentStyle = hssfworkbook.createCellStyle();
        if(null!=dataList&&dataList.size()>0){

            // 取得第一行
            HSSFRow hssfrow = hssfsheet.createRow(startRow -1+ dataList.size());
            //hssfrow.setHeight((short) 350);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 靠右
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            contentStyle.setWrapText(true);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            Map<String, Object> results = dataList.get(0);

            // contentStyle.set
            // 开始向右创建单元格
            Iterator it = results.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                HSSFCell cell = hssfrow.createCell(startCol - 1 + j);
                cell.setCellStyle(contentStyle); // 设置数据单元格的样式
                if(j==0){
                    cell.setCellValue(new HSSFRichTextString(endStr));
                }
                j++;
            }
            hssfsheet.addMergedRegion(new Region(startRow - 1 + dataList.size(),
                    (short) 0, startRow - 1 + dataList.size(),
                    (short) (results.size() - 1)));
        }
        
    }
}
