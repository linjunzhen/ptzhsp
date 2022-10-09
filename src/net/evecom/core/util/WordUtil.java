/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

/**
 * 
 * 描述 word文档输出
 * 
 * @author Danto Huang
 * @created 2015-1-8 上午9:09:29
 */
public class WordUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WordUtil.class);
    /**
     * 根据指定的参数值、模板，生成 word 文档
     * 
     * @param param
     *            需要替换的变量
     * @param template
     *            模板
     */
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> undertakeList = (List<Map<String, Object>>) param.get("undertake");
        List<Map<String, Object>> supportList = (List<Map<String, Object>>) param.get("supportDelegate");
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc, null);
                // 处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                int i=0;
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    int j = 0;
                    if(i==1&&undertakeList!=null&&undertakeList.size()>0){                        
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> undertake = new HashMap<String, Object>();
                            if(j!=0&&j<=undertakeList.size()){
                                undertake = undertakeList.get(j-1);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            undertakeWritePropose(cells,param,undertake,doc,table,j);
                            j++;
                        }
                    }else if(i==3&&supportList!=null&&supportList.size()>0){
                        XWPFTableRow tmpRow = rows.get(4);
                        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
                        insertRow(tmpRow, supportList, 15, 7,table);
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> support = new HashMap<String, Object>();
                            if(j>3&&j<supportList.size()+4){
                                support = supportList.get(j-4);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            int k=0;
                            for (XWPFTableCell cell : cells) {
                                if(j<=3||j>=supportList.size()+4){
                                    List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                                    processParagraphs(paragraphListTable, param, doc, table);
                                }else{
                                    if(k==0){
                                        cell.setText(support.get("personnel_name").toString());
                                    }else if(k==1){
                                        cell.setText(support.get("delegate_code").toString());
                                    }else if(k==2){
                                        cell.setText(support.get("unit_address") != null ? support.get("unit_address")
                                                .toString() : "");
                                    }else if(k==3){
                                        cell.setText(support.get("unit_postcode") != null ? support
                                                .get("unit_postcode").toString() : "");
                                    }else if(k==4){
                                        cell.setText(support.get("phone")!=null?support.get("phone").toString():"");
                                    }
                                }
                                k++;
                            }
                            j++;
                        }
                    }else if(i==4&&undertakeList!=null&&undertakeList.size()>5){
                        XWPFTableRow tmpRow = rows.get(1);
                        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
                        insertRow(tmpRow, undertakeList, 20, 5,table);
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> undertake = new HashMap<String, Object>();
                            if(j>0&&j<undertakeList.size()-4){
                                undertake = undertakeList.get(j+4);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            undertakeWritePropose(cells,param,undertake,doc,table,j);
                            j++;
                        }
                    }else{
                        for (XWPFTableRow row : rows) {
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                                processParagraphs(paragraphListTable, param, doc, table);
                            }
                        }
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return doc;
    }
    /**
     * 
     * 描述   承办单位写入表格
     * @author Danto Huang
     * @created 2015-1-13 下午3:24:24
     * @param cells
     * @param param
     * @param undertake
     * @param doc
     * @param table
     * @param j
     */
    private static void undertakeWritePropose(List<XWPFTableCell> cells, Map<String, Object> param,
            Map<String, Object> undertake, CustomXWPFDocument doc, XWPFTable table, int j) {
        int k=0;
        for (XWPFTableCell cell : cells) {
            if(j==0){
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                processParagraphs(paragraphListTable, param, doc, table);
            }else{
                if (k == 0) {
                    cell.setText(undertake.get("gkdw") != null ? undertake.get("gkdw").toString()
                            : "");
                } else if (k == 1) {
                    cell.setText(undertake.get("DEPART_CODE") != null ? undertake
                            .get("DEPART_CODE").toString() : "");
                } else if (k == 2) {
                    cell.setText(undertake.get("undertake_units") != null ? undertake.get(
                            "undertake_units").toString() : "");
                } else if (k == 3) {
                    cell.setText(undertake.get("cblb") != null ? undertake.get("cblb").toString()
                            : "");
                } else if (k == 4) {
                    cell.setText(undertake.get("cbxz") != null ? undertake.get("cbxz").toString()
                            : "");
                }
            }
            k++;
        }
    }
    
    /**
     * 根据指定的参数值、模板，生成议案 word 文档(根据后续需求待完善)
     * 
     * @param param
     *            需要替换的变量
     * @param template
     *            模板
     */
    public static CustomXWPFDocument generateBillWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> undertakeList = (List<Map<String, Object>>) param.get("undertake");
        List<Map<String, Object>> supportList = (List<Map<String, Object>>) param.get("supportList");
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc, null);
                // 处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                int i = 0;
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    int j = 0;
                    if(i==2&&undertakeList!=null&&undertakeList.size()>0){
                        XWPFTableRow tmpRow = rows.get(1);
                        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
                        insertRow(tmpRow, undertakeList, 3, 2,table);
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> undertake = new HashMap<String, Object>();
                            if(j!=0&&j<=undertakeList.size()){
                                undertake = undertakeList.get(j-1);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            undertakeWriteBill(cells,param,undertake,doc,table,j);
                            j++;
                        }
                    }else if(i==1&&supportList!=null&&supportList.size()>0){
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> support = new HashMap<String, Object>();
                            if(j!=0&&j<=supportList.size()){
                                support = supportList.get(j-1);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            supportWriteBill(cells,param,support,doc,table,j);
                            j++;
                        }
                    }else if(i==4&&supportList!=null&&supportList.size()>7){
                        XWPFTableRow tmpRow = rows.get(4);
                        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
                        insertRow(tmpRow, supportList, 22, 5,table);
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> support = new HashMap<String, Object>();
                            if(j>3&&j<supportList.size()-3){
                                support = supportList.get(j+3);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            supportWriteBill(cells,param,support,doc,table,j);
                            j++;
                        }
                    }else if(i==5&&undertakeList!=null&&undertakeList.size()>3){
                        XWPFTableRow tmpRow = rows.get(1);
                        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
                        insertRow(tmpRow, undertakeList, 22, 5,table);
                        for (XWPFTableRow row : rows) {
                            Map<String, Object> undertake = new HashMap<String, Object>();
                            if(j>0&&j<undertakeList.size()-2){
                                undertake = undertakeList.get(j+2);
                            }
                            List<XWPFTableCell> cells = row.getTableCells();
                            undertakeWriteBill(cells,param,undertake,doc,table,j);
                            j++;
                        }
                    }else{
                        for (XWPFTableRow row : rows) {
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                                processParagraphs(paragraphListTable, param, doc, table);
                            }
                        }
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return doc;
    }
    
    /**
     * 
     * 描述   承办单位写入表格
     * @author Danto Huang
     * @created 2015-1-13 下午3:24:24
     * @param cells
     * @param param
     * @param undertake
     * @param doc
     * @param table
     * @param j
     */
    private static void undertakeWriteBill(List<XWPFTableCell> cells, Map<String, Object> param,
            Map<String, Object> undertake, CustomXWPFDocument doc, XWPFTable table, int j) {
        int k=0;
        for (XWPFTableCell cell : cells) {
            if(j==0){
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                processParagraphs(paragraphListTable, param, doc, table);
            }else{
                if(k==1){
                    cell.setText(undertake.get("DEPART_CODE") != null ? undertake
                            .get("DEPART_CODE").toString() : "");
                }else if(k==2){
                    cell.setText(undertake.get("undertake_units") != null ? undertake.get(
                            "undertake_units").toString() : "");
                } else if (k == 3) {
                    cell.setText(undertake.get("jycblb_name") != null ? undertake
                            .get("jycblb_name").toString() : "");
                } else if (k == 4) {
                    cell.setText(undertake.get("jycbxz_name") != null ? undertake
                            .get("jycbxz_name").toString() : "");
                }
            }
            k++;
        }
    }

    /**
     * 
     * 描述   附议代表写入表格
     * @author Danto Huang
     * @created 2015-1-13 下午3:24:24
     * @param cells
     * @param param
     * @param undertake
     * @param doc
     * @param table
     * @param j
     */
    private static void supportWriteBill(List<XWPFTableCell> cells, Map<String, Object> param,
            Map<String, Object> support, CustomXWPFDocument doc, XWPFTable table, int j) {
        int k=0;
        for (XWPFTableCell cell : cells) {
            if(j==0){
                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                processParagraphs(paragraphListTable, param, doc, table);
            }else{
                if(k==0){
                    cell.setText(support.get("personnel_name") != null ? support
                            .get("personnel_name").toString() : "");
                }else if(k==1){
                    cell.setText(support.get("delegate_code") != null ? support.get(
                            "delegate_code").toString() : "");
                } else if (k == 2) {
                    cell.setText(support.get("UNIT_ADDRESS") != null ? support.get("UNIT_ADDRESS")
                            .toString() : "");
                } else if (k == 3) {
                    cell.setText(support.get("UNIT_POSTCODE") != null ? support
                            .get("UNIT_POSTCODE").toString() : "");
                } else if (k == 4) {
                    cell.setText(support.get("PHONE") != null ? support.get("PHONE").toString()
                            : "");
                }
            }
            k++;
        }
    }
    
    /**
     * 
     * 描述    表格插入行
     * @author Danto Huang
     * @created 2015-1-9 下午3:44:54
     * @param tmpRow
     * @param resultList
     * @param rownum
     * @param table
     */
    public static void insertRow(XWPFTableRow tmpRow,List resultList,int rownum,int inpos,XWPFTable table){
        List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
        if(resultList.size()>rownum){
            int count=0;
            while(count<resultList.size()-rownum){
                XWPFTableRow row = table.insertNewTableRow(inpos);
                row.setHeight(tmpRow.getHeight());
                List<XWPFTableCell> cells = row.getTableCells();
                for(int k=0,klen=tmpCells.size();k<klen;k++){
                    XWPFTableCell tmpCell = tmpCells.get(k);
                    XWPFTableCell cell = row.createCell();;
                    try {
                        setCellText(tmpCell, cell);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                count++;
            }                            
        }
    }
    /**
     * 处理段落
     * 
     * @param paragraphList
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param,
            CustomXWPFDocument doc, XWPFTable table) {
        if (paragraphList != null && paragraphList.size() > 0) {
            int i = 0;
            Iterator<XWPFParagraph> iterator1 = paragraphList.iterator();

            Map<String, XWPFParagraph> map = new HashMap<String, XWPFParagraph>();

            while (iterator1.hasNext()) {
                XWPFParagraph paragraph = iterator1.next();
                i++;
                List<XWPFRun> runs = paragraph.getRuns();

                Iterator<XWPFRun> iterator = runs.iterator();
                while (iterator.hasNext()) {
                    XWPFRun run = iterator.next();
                    String text = run.getText(0);
//                    log.info("第" + i + "段");
//                    log.info(text);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {// 文本替换
                                    text = text.replace(key, value.toString());
                                    if (isSetText) {

                                        run.setText(text, 0);
                                    }
                                } else if (value instanceof Map) {// 图片替换
                                    text = text.replace(key, "");

                                    Map pic = (Map) value;
                                    int width = Integer.parseInt(pic.get("width").toString());
                                    int height = Integer.parseInt(pic.get("height").toString());
                                    int picType = getPictureType(pic.get("type").toString());
                                    byte[] byteArray = (byte[]) pic.get("content");
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);

                                    try {
                                        String picId = doc.addPictureData(byteInputStream,
                                                XWPFDocument.PICTURE_TYPE_PNG);
                                        map.put(picId, paragraph);

                                    } catch (Exception e) {
                                        log.error(e.getMessage());
                                    }
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
//            log.info("##########");
//            log.info(i);
//            log.info("##########");
            for (String key : map.keySet()) {
                try {
//                    log.info("^^^^^^^^^^^^^^^^");
                    doc.createPicture(key, doc.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG), 158, 158,
                            map.get(key));
                } catch (InvalidFormatException e) {
                    log.error(e.getMessage());
                }
            }

        }
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     * 
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     * 
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    log.info("关闭流失败");
                }
            }
        }
        return byteArray;
    }
    
    /**
     * 读取html文件到word
     * 
     * @param content
     *            html内容
     * @return
     * @throws Exception
     */
    public boolean writeWordFile(String content,String reladir,String fileName) throws Exception {
        boolean flag = false;
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("outFilePath");
        String relativeFolderPath = reladir + "/";
        String dir = attachFileFolderPath + relativeFolderPath;
        try {
            if (!"".equals(dir)) {
                File fileDir = new File(dir);
                if(!fileDir.exists()){
                    fileDir.mkdirs();
                }
                File relativeFolder = new File(relativeFolderPath);
                if(!relativeFolder.exists()){
                    relativeFolder.mkdirs();
                }
                byte b[] = content.getBytes();
                bais = new ByteArrayInputStream(b);
                POIFSFileSystem poifs = new POIFSFileSystem();
                //DirectoryEntry directory = poifs.getRoot();
                fos = new FileOutputStream(dir + fileName+".doc");
                poifs.writeFilesystem(fos);
                bais.close();
                fos.close();
                flag = true;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (fos != null)
                fos.close();
            if (bais != null)
                bais.close();
        }
        return flag;
    }

    /**
     * 读取html文件到字符串
     * 
     * @param filename
     * @return
     * @throws Exception
     */
    public String readFile(String filename) throws Exception {
        StringBuffer buffer = new StringBuffer("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)),
                    Charset.forName("UTF8")));
            buffer = new StringBuffer();
            while (br.ready())
                buffer.append((char) br.read());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (br != null)
                br.close();
        }
        return buffer.toString();
    }

    /**
     * 
     * 描述  表格单元格格式复制
     * @author Danto Huang
     * @created 2015-1-9 下午2:19:53
     * @param tmpCell
     * @param cell
     * @throws Exception
     */
    public static void setCellText(XWPFTableCell tmpCell,XWPFTableCell cell) throws Exception{
        CTTc cttc2 = tmpCell.getCTTc();
        CTTcPr ctPr2=cttc2.getTcPr();
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        cell.setColor(tmpCell.getColor());
        if(ctPr2.getTcW()!=null)ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
        if(ctPr2.getVAlign()!=null)ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
        if(cttc2.getPList().size()>0){
            CTP ctp=cttc2.getPList().get(0);
            if(ctp.getPPr()!=null){
                if(ctp.getPPr().getJc()!=null){
                    cttc.getPList().get(0).addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
                }
            }
        }
        if(ctPr2.getTcBorders()!=null)ctPr.setTcBorders(ctPr2.getTcBorders());
        XWPFParagraph tmpP=tmpCell.getParagraphs().get(0);
        XWPFParagraph cellP=cell.getParagraphs().get(0);
        XWPFRun tmpR =null;
        if(tmpP.getRuns()!=null&&tmpP.getRuns().size()>0){
            tmpR=tmpP.getRuns().get(0);
        }
        XWPFRun cellR = cellP.createRun();
        //复制字体信息
        if(tmpR!=null){
            cellR.setBold(tmpR.isBold());
            cellR.setItalic(tmpR.isItalic());
            cellR.setStrike(tmpR.isStrike());
            cellR.setUnderline(tmpR.getUnderline());
            cellR.setColor(tmpR.getColor());
            cellR.setTextPosition(tmpR.getTextPosition());
            if(tmpR.getFontSize()!=-1){
                cellR.setFontSize(tmpR.getFontSize());
            }
            if(tmpR.getFontFamily()!=null){
                cellR.setFontFamily(tmpR.getFontFamily());
            }
            if(tmpR.getCTR()!=null){
                if(tmpR.getCTR().isSetRPr()){
                    CTRPr tmpRPr =tmpR.getCTR().getRPr();
                    if(tmpRPr.isSetRFonts()){
                        CTFonts tmpFonts=tmpRPr.getRFonts();
                        CTRPr cellRPr=cellR.getCTR().isSetRPr() ? cellR.getCTR().getRPr() : cellR.getCTR().addNewRPr();
                        CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr.getRFonts() : cellRPr.addNewRFonts();
                        cellFonts.setAscii(tmpFonts.getAscii());
                        cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
                        cellFonts.setCs(tmpFonts.getCs());
                        cellFonts.setCstheme(tmpFonts.getCstheme());
                        cellFonts.setEastAsia(tmpFonts.getEastAsia());
                        cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
                        cellFonts.setHAnsi(tmpFonts.getHAnsi());
                        cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
                    }
                }
            }
        }
        //复制段落信息
        cellP.setAlignment(tmpP.getAlignment());
        cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
        cellP.setBorderBetween(tmpP.getBorderBetween());
        cellP.setBorderBottom(tmpP.getBorderBottom());
        cellP.setBorderLeft(tmpP.getBorderLeft());
        cellP.setBorderRight(tmpP.getBorderRight());
        cellP.setBorderTop(tmpP.getBorderTop());
        cellP.setPageBreak(tmpP.isPageBreak());
        if(tmpP.getCTP()!=null){
            if(tmpP.getCTP().getPPr()!=null){
                CTPPr tmpPPr = tmpP.getCTP().getPPr();
                CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP.getCTP().getPPr() : cellP.getCTP().addNewPPr();
                //复制段落间距信息
                CTSpacing tmpSpacing =tmpPPr.getSpacing();
                if(tmpSpacing!=null){
                    CTSpacing cellSpacing= cellPPr.getSpacing()!=null?cellPPr.getSpacing():cellPPr.addNewSpacing();
                    if(tmpSpacing.getAfter()!=null)cellSpacing.setAfter(tmpSpacing.getAfter());
                    if(tmpSpacing.getAfterAutospacing()!=null){
                        cellSpacing.setAfterAutospacing(tmpSpacing.getAfterAutospacing());
                    }
                    if(tmpSpacing.getAfterLines()!=null)cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
                    if(tmpSpacing.getBefore()!=null)cellSpacing.setBefore(tmpSpacing.getBefore());
                    if(tmpSpacing.getBeforeAutospacing()!=null){
                        cellSpacing.setBeforeAutospacing(tmpSpacing.getBeforeAutospacing());
                    }
                    if(tmpSpacing.getBeforeLines()!=null)cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
                    if(tmpSpacing.getLine()!=null)cellSpacing.setLine(tmpSpacing.getLine());
                    if(tmpSpacing.getLineRule()!=null)cellSpacing.setLineRule(tmpSpacing.getLineRule());
                }
                //复制段落缩进信息
                CTInd tmpInd=tmpPPr.getInd();
                if(tmpInd!=null){
                    CTInd cellInd=cellPPr.getInd()!=null?cellPPr.getInd():cellPPr.addNewInd();
                    if(tmpInd.getFirstLine()!=null)cellInd.setFirstLine(tmpInd.getFirstLine());
                    if(tmpInd.getFirstLineChars()!=null)cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
                    if(tmpInd.getHanging()!=null)cellInd.setHanging(tmpInd.getHanging());
                    if(tmpInd.getHangingChars()!=null)cellInd.setHangingChars(tmpInd.getHangingChars());
                    if(tmpInd.getLeft()!=null)cellInd.setLeft(tmpInd.getLeft());
                    if(tmpInd.getLeftChars()!=null)cellInd.setLeftChars(tmpInd.getLeftChars());
                    if(tmpInd.getRight()!=null)cellInd.setRight(tmpInd.getRight());
                    if(tmpInd.getRightChars()!=null)cellInd.setRightChars(tmpInd.getRightChars());
                }
            }
        }
    }
    /**
     * 根据指定的参数值、模板，生成 word 文档
     * 
     * @param param
     *            需要替换的变量
     * @param template
     *            模板
     */
    public static CustomXWPFDocument generateFeedbackWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        List<Map<String, Object>> feedbackList = (List<Map<String, Object>>) param.get("feedbackList");
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc, null);
                // 处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    int j = 0;

                    XWPFTableRow tmpRow = rows.get(2);
                    List<XWPFTableCell> tmpCells = tmpRow.getTableCells();
                    insertRow(tmpRow, feedbackList, 13, 3, table);
                    for (XWPFTableRow row : rows) {
                        Map<String, Object> undertake = new HashMap<String, Object>();
                        if (j != 0 && j <= feedbackList.size()) {
                            undertake = feedbackList.get(j - 1);
                        }
                        if(j >feedbackList.size())break;
                        List<XWPFTableCell> cells = row.getTableCells();
                        int k = 0;
                        for (XWPFTableCell cell : cells) {
                            if (j == 0) {
                                List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                                processParagraphs(paragraphListTable, param, doc, table);
                            } else {
                                if (k == 0) {
                                    cell.setText(undertake.get("feedback_date") != null ? undertake
                                            .get("feedback_date").toString() : "");
                                } else if (k == 1) {
                                    cell.setText(undertake.get("undertake_units") != null ? undertake.get(
                                            "undertake_units").toString() : "");
                                } else if (k == 2) {
                                    cell.setText(undertake.get("delegate_satisfaction") != null ? undertake.get(
                                            "delegate_satisfaction").toString() : "未反馈");
                                } else if (k == 3) {
                                    cell.setText(undertake.get("feedback_content") != null ? undertake.get(
                                            "feedback_content").toString() : "");
                                }
                            }
                            k++;
                        }
                        j++;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return doc;
    }

}