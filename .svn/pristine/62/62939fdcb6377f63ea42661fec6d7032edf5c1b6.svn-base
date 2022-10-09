/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.SysSendMsgService;
import net.evecom.platform.wsbs.service.ApasInfoService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
public class ApasInfoData {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ApasInfoData.class);
    
    /**style**/
    private XSSFCellStyle head_Style;
    /**style**/
    private SXSSFWorkbook workbook;
    /**当前sheet**/
    private SXSSFSheet sheet;
    /**style**/
    private SXSSFRow row = null;// 创建一行
    /**style**/
    private SXSSFCell cell = null;
    /**style**/
    private String headers[][];
    /**style**/
    private int currentRow = 0;
    /**style**/
    private XSSFCellStyle date_Style ;
    /**style**/
    private XSSFCellStyle time_Style ;
    /**style**/
    private XSSFCellStyle string_style;
    /**style**/
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**
     * 
     */
    private static int pagenum=10000;
    /**
     * 构造函数初始化参数
     * @param out
     * @param title
     * @param headers
     * @param sheeatName
     */
    public void initData(String title,String[][] headers,String sheeatName,String tplPath,int sheetnum){
        this.headers = headers;
        InputStream inputStream = null;
        try{
                
            //String tplPath = "D://test.xlsx";
            
            inputStream = new FileInputStream(tplPath);
            XSSFWorkbook xbook = new XSSFWorkbook(inputStream);//new BufferedInputStream(inputStream));
            for (int i =1; i <sheetnum; i++) {
                Sheet xsheet=xbook.cloneSheet(0);
                xbook.setSheetName(i, "Sheet"+i+2);
            }
            inputStream.close();  
            
            // 写入文件，采用分批写入的方式进行写入
            workbook=new SXSSFWorkbook(xbook,1000);
            workbook.setCompressTempFiles(true);//// temp files will be gzipped
            this.head_Style=(XSSFCellStyle) this.workbook.createCellStyle();
            head_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            head_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            head_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            head_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            //head_Style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            head_Style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            head_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            XSSFFont head_font = (XSSFFont) workbook.createFont();
            head_font.setFontName("宋体");// 设置头部字体为宋体
            head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
            head_font.setFontHeightInPoints((short) 11);
            this.head_Style.setFont(head_font);// 单元格样式使用字体
            
            XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
            
            XSSFFont data_font = (XSSFFont) workbook.createFont();
            data_font.setFontName("宋体");// 设置头部字体为宋体
            data_font.setFontHeightInPoints((short) 10);
            
            this.date_Style = (XSSFCellStyle) this.workbook.createCellStyle();
            date_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            date_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            date_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            date_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            date_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            date_Style.setFont(data_font);// 单元格样式使用字体
            date_Style.setDataFormat(format.getFormat("yyyy-m-d"));
            
            this.time_Style = (XSSFCellStyle) this.workbook.createCellStyle();
            time_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            time_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            time_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            time_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            time_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            time_Style.setFont(data_font);// 单元格样式使用字体
            time_Style.setDataFormat(format.getFormat("yyyy-m-d h:mm:s"));
            
            this.string_style = (XSSFCellStyle) this.workbook.createCellStyle();
            string_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            string_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            string_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            string_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            string_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            string_style.setFont(data_font);// 单元格样式使用字体
             
            currentRow=1;
            //createSheet( sheeatName,headers);
        }catch(Exception e){
            log.error(e.getMessage());
        }finally{
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        
    }
    /**
     * 创建表头
     * @param sheetName
     * @param headers
     */
    private  void createSheet(String sheetName,String headers[][])  {
        sheet = (SXSSFSheet) workbook.createSheet(sheetName);
        row = (SXSSFRow) sheet.createRow(currentRow);
        for (int i = 0; i < headers.length; i++) {
            cell = (SXSSFCell) row.createCell(i);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(headers[i][0]);
            cell.setCellStyle(head_Style);
        }
        currentRow++;
    }
    /**
     * 导出excel
     * @param listRows
     * @throws ParseException
     */
    public synchronized void writeExcelTo2007(List listRows, OutputStream out,
            SXSSFSheet newsheet) throws ParseException {
        sheet = newsheet;//(SXSSFSheet) workbook.getSheetAt(0);
        //log.info(workbook.getActiveSheetIndex());
        //log.info(listRows.size());
        for (int i = 0; i < listRows.size(); i++) {
            row = (SXSSFRow) sheet.createRow(currentRow);
            ArrayList ListCells = (ArrayList)listRows.get(i);
            for (int j = 0; j < ListCells.size(); j++) {
                Object obj = ListCells.get(j);
                cell = (SXSSFCell) row.createCell(j);
                if(obj instanceof Integer){
                    cell.setCellValue((String)obj);
                    cell.setCellStyle(string_style);
                }else if(obj instanceof Date){
                    String type = headers[j][1];
                    if("DATE".equals(type)){
                        cell.setCellValue((Date)obj);
                        cell.setCellStyle(date_Style);
                    }else if("TIME".equals(type)){
                        cell.setCellValue((Date)obj);
                        cell.setCellStyle(time_Style);
                    }else{
                        if(!"".equals(obj.toString()))
                            cell.setCellValue(sdf.format((Date)obj));
                        cell.setCellValue("");
                        cell.setCellStyle(string_style);
                    }
                }else{
                    if(!"".equals((String)obj)){
                        cell.setCellValue((String)obj);
                    }else{
                        cell.setCellValue("");
                    }   
                    cell.setCellStyle(string_style);
                }
            }
            currentRow ++;
        }
        try {
            sheet.flushRows();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        //workbook.cloneSheet(2);

//        try {
//            workbook.write(out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
    }
    
    public static void exportApasBySql(String tplPath,
            List<Map<String, Object>> dataList, String fileName,
            Set<String> excludeFields, HttpServletResponse response,
            int startRow, int startCol,SqlFilter sqlFilter){
        ApasInfoService apasService = (ApasInfoService) AppUtil.getBean("apasInfoService");
        int count=apasService.getCount(sqlFilter);
        List<Map<String, Object>> countlist=new ArrayList<Map<String,Object>>(count);
        int sheetnum=0;
        if(count%pagenum==0){
            sheetnum=count/pagenum;
        }else if(count>pagenum){
            sheetnum=count/pagenum+1;
        }
        
        if(count>pagenum){
            for (int i = 0; i < sheetnum; i++) {
                List<Map<String, Object>> subdata=apasService.findExByBatch(sqlFilter, i*pagenum, (i+1)*pagenum);
                countlist.addAll(subdata);
                subdata.clear();
            }
        }
        exportApasDuo(tplPath, countlist, fileName.toString(), excludeFields, response, startRow, startCol);
    }
    /**
     * multi sheet
     * @param tplPath
     * @param dataList
     * @param fileName
     * @param excludeFields
     * @param response
     * @param startRow
     * @param startCol
     */
    public static void exportApasDuo(String tplPath,
            List<Map<String, Object>> dataList, String fileName,
            Set<String> excludeFields, HttpServletResponse response,
            int startRow, int startCol){
        int sheetnum=0;
        if(dataList.size()%pagenum==0){
            sheetnum=dataList.size()/pagenum;
        }else if(dataList.size()>pagenum){
            sheetnum=dataList.size()/pagenum+1;
        }
        
        ApasInfoData exportData=new ApasInfoData();
        exportData.initData("test", exportData.headers, "sheet",tplPath,sheetnum);
        
        ArrayList<ArrayList<?>> data = new ArrayList<ArrayList<?>>();
        for (int i = 0; i <dataList.size(); i++) {
            Map<String, Object> results = dataList.get(i);
            ArrayList<String> cellList = new ArrayList<String>();
            // 开始向右创建单元格
            Iterator it = results.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                if (!excludeFields.contains(fieldName)) {
                    Object val = entry.getValue();
                    String value = "";
                    if (val != null) {
                        value = val.toString();
                    }
                    cellList.add(value);
                }
            }
            data.add(cellList);
        }
        try {
            String tx = new String(fileName.getBytes("GBK"), "ISO8859-1");
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setHeader("Content-disposition", "attachment;filename=" + tx);
            OutputStream out = resp.getOutputStream();
            //1.用多个sheet来存放,每页放4万；
           if(sheetnum>0){
                for (int i = 0; i < sheetnum; i++) {
                    SXSSFSheet nsheet = (SXSSFSheet) exportData.workbook.getSheetAt(i);
                    exportData.workbook.setActiveSheet(i);
                    //exportData.workbook.setSheetName(i, "sheet"+i);
                    if(i==sheetnum-1){
                        exportData.writeExcelTo2007(data.subList(i*pagenum+1,data.size()),out,nsheet);
                    }else{
                        exportData.writeExcelTo2007(data.subList(i*pagenum, (i+1)*pagenum),out,nsheet);
                    }
                    exportData.currentRow=1;
                }
            }else{
                SXSSFSheet nsheet = (SXSSFSheet) exportData.workbook.getSheetAt(0);
                exportData.writeExcelTo2007(data,out,nsheet);
            }

            exportData.workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    /**
     * test
     * @param tplPath
     * @param dataList
     * @param fileName
     * @param excludeFields
     * @param response
     * @param startRow
     * @param startCol
     */
    public static void exportApas(String tplPath,
            List<Map<String, Object>> dataList, String fileName,
            Set<String> excludeFields, HttpServletResponse response,
            int startRow, int startCol){
        /*String headers[][] = {{"事项名称","String"},{"办件名称","String"},{"申请人机构","String"},{"联系人","String"}
                            ,{"联系电话","String"},{"联系地址","String"}
                ,{"收件人","String"},{"收件时间","String"},{"承诺办结时间","String"},{"办结时间","String"},
                {"办件状态","String"},{"部门","String"}} ; */
        //ApasInfoData exportData = new ApasInfoData("test", headers, "test",tplPath);
        ApasInfoData exportData=new ApasInfoData();
        exportData.initData("test", exportData.headers, "test",tplPath,1);
        
        ArrayList<ArrayList<?>> data = new ArrayList<ArrayList<?>>();
        for (int i = 0; i <dataList.size(); i++) {
            Map<String, Object> results = dataList.get(i);
            ArrayList<String> cellList = new ArrayList<String>();
            // 开始向右创建单元格
            Iterator it = results.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                //if (!excludeFields.contains(fieldName)) {
                    Object val = entry.getValue();
                    String value = "";
                    if (val != null) {
                        value = val.toString();
                    }
                    cellList.add(value);
                //}
            }
            data.add(cellList);
        }
        try {
            String tx = new String(fileName.getBytes("GBK"), "ISO8859-1");
            HttpServletResponse resp = response;
            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setHeader("Content-disposition", "attachment;filename=" + tx);
            OutputStream out = resp.getOutputStream();
            //1.用多个sheet来存放,每页放4万；
            exportData.sheet = (SXSSFSheet) exportData.workbook.getSheetAt(0);
            exportData.writeExcelTo2007(data,out,exportData.sheet); 
            exportData.workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 测试导出
     * @param args
     * @throws IOException
     * @throws ParseException
     */
  /*  public static void main(String[] args) throws IOException, ParseException {
        String headers[][] = {{"日期","DATE"},{"标题","TIME"},{"其他",null}} ; 
        String tplPath = "D://test.xlsx";
        File file = new File("d://apasInfoda11.xlsx");
        if (file.exists())
            file.delete();
        file.createNewFile();
        
        //ApasInfoData exportData = new ApasInfoData();//"test", headers, "test",tplPath);
        
        ArrayList<ArrayList<?>> data = new ArrayList<ArrayList<?>>();
        for (int i = 0; i < 200000; i++) {
            ArrayList<Date> cellList = new ArrayList<Date>();
            for (int j = 0; j < 3; j++) {
                cellList.add(new Date());
            }
            data.add(cellList);
        }
        //OutputStream out = new FileOutputStream(file);
        //exportData.writeExcelTo2007(data,out);
    }*/
}
