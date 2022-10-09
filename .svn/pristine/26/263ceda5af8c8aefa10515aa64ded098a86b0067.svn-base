/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;

import com.swetake.util.Qrcode;

import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.UUIDGenerator;

/**
 * 
 * 描述 Word模板数据回填工具类
 * 
 * @author Roger Li
 * @created 2020年1月15日 上午9:41:10
 */
public class WordRedrawUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WordRedrawUtil.class);
    /**
     * 不动产申请表二维码宽度
     */
    public static int bdcQRCODEWIDTH = 100;
    /**
     * 不动产申请表二维码高度
     */
    public static int bdcQRCODEHEIGHT = 90;
    /**
     * 不动产签名宽度
     */
    public static int bdcSIGNWIDTH = 100;
    /**
     * 不动产签名高度
     */
    public static int bdcSIGNHEIGHT = 50;
    /**
     * 二维码文件后缀
     */
    public static String pICEXT = "jpg";

    /**
     * 
     * 描述 处理表格类型的数据回填
     * 
     * @author Roger Li
     * @created 2020年1月15日 上午9:51:57
     */
    public static void processNormalTable07(String path, Map<String, Object> returnMap) {
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            in = new FileInputStream(new File(AppUtil.getRealPath(path)));
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            List<XWPFTableCell> cells = new ArrayList<XWPFTableCell>();
            for (XWPFTableRow row : xwpfDocument.getTables().get(0).getRows()) {
                cells.addAll(row.getTableCells());
            }
            for (XWPFTableCell cell1 : cells) {
                String tag = cell1.getText().trim();
                if (StringUtils.isNotBlank(tag) && tag.contains("【") && tag.contains("】")) {
                    List<XWPFParagraph> paragraphs = cell1.getParagraphs();
                    WordRedrawUtil.processParagraphs(paragraphs, returnMap, xwpfDocument);
                }
            }
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            xwpfDocument.write(fos);
            returnMap.put("bdcPath", savePath);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 
     * 描述 处理列表类型的表格(动态增加行)
     * 
     * @author Roger Li
     * @created 2020年1月15日 上午11:49:04
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void processTableList(String path, Map<String, Object> returnMap) {
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            in = new FileInputStream(new File(AppUtil.getRealPath(path)));
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            XWPFTable table = xwpfDocument.getTables().get(0);
            List<List> rowsData = (List<List>) returnMap.getOrDefault("table", new ArrayList<List>());
            doProcessTableList(table, rowsData);
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            returnMap.put("bdcPath", savePath);
            xwpfDocument.write(fos);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 
     * 描述 处理列表类型的表格(动态增加行)
     * 
     * @author Roger Li
     * @created 2020年1月17日 上午9:03:10
     * @param templateTable
     * @param rows
     */
    @SuppressWarnings("rawtypes")
    private static void doProcessTableList(XWPFTable templateTable, List<List> rows) {
        List<XWPFTableRow> templateTableRows = templateTable.getRows();// 获取模板表格所有行
        int tagRowsIndex = 0;// 标签行indexs
        for (int i = 0, size = templateTableRows.size(); i < size; i++) {
            String rowText = templateTableRows.get(i).getCell(0).getText();// 获取到表格行的第一个单元格
            if (rowText.indexOf("##{foreachRows}##") > -1) {
                tagRowsIndex = i;
                break;
            }
        }
        XWPFTableRow templateRow = templateTable.getRow(tagRowsIndex + 1);
        for (int i = 0; i < rows.size(); i++) {
            List rowData = rows.get(i);
            if (i > 0) {
                XWPFTableRow newCreateRow = templateTable.createRow();
                for (int j = 0; j < rowData.size(); j++) {
                    newCreateRow.getCtRow().setTrPr(templateRow.getCtRow().getTrPr());
                    List<XWPFTableCell> sList = templateRow.getTableCells();
                    // 设置单元格样式
                    for (int n = 0; n < sList.size(); n++) {
                        newCreateRow.getCell(n).getCTTc().setTcPr(templateRow.getCell(n).getCTTc().getTcPr());
                    }
                    newCreateRow.getCell(j).setText(rowData.get(j).toString()); //
                }
            } else {
                for (int j = 0; j < rowData.size(); j++) {
                    templateRow.getCell(j).setText(rowData.get(j).toString());
                }
            }
        }
        templateTable.removeRow(tagRowsIndex);
    }

    /**
     * 处理段落
     * 
     * @param paragraphList
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param,
            CustomXWPFDocument doc) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key = modifyKey(entry.getKey());
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {// 文本替换
                                    text = text.replace(key, value.toString());
                                } else if (value instanceof CTSym) {// 特殊符号
                                    isSetText = false;
                                    CTSym ctsym = (CTSym) value;
                                    run.setText(text.replace(key, ""), 0);
                                    try {
                                        CTRPr pRpr = getRunCTRPr(paragraph, run);
                                        // 设置字体大小
                                        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
                                        sz.setVal(new BigInteger("24"));
                                        List<CTSym> symList = new ArrayList<CTSym>();
                                        symList.add(ctsym);
                                        int size = symList.size();
                                        run.getCTR().setSymArray((CTSym[]) symList.toArray(new CTSym[size]));
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        log.error(e.getMessage());
                                    }
                                } else if (value instanceof List) {// 列表段落
                                    isSetText = false;
                                    List<Object> list = (LinkedList<Object>) value;
                                    run.setText("", 0);
                                    for (int i = 0; i < list.size(); i++) {
                                        String s = (String) list.get(i);
                                        run.setText(s);
                                        run.addBreak();
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
                                            String ind = doc.addPictureData(byteInputStream, picType);
                                            doc.createPicture(ind,
                                                    doc.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG), width,
                                                    height, run);
                                        } catch (Exception e) {
                                            log.error(e.getMessage());
                                        }
                                } else {
                                    if (null != value) {
                                        text = text.replace(key, value.toString());
                                    } else {
                                        text = text.replace(key, "");
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
        }
    }

    /**
     * 
     * 描述 得到XWPFRun的CTRPr
     * 
     * @author Rider Chen
     * @created 2016年12月20日 下午2:18:09
     * @param p
     * @param pRun
     * @return
     */
    public static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
        CTRPr pRpr = null;
        if (pRun.getCTR() != null) {
            pRpr = pRun.getCTR().getRPr();
            if (pRpr == null) {
                pRpr = pRun.getCTR().addNewRPr();
            }
        } else {
            pRpr = p.getCTP().addNewR().addNewRPr();
        }
        return pRpr;
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
     * 
     * 描述 为map中的key添加tag(【key】)
     * 
     * @author Roger Li
     * @created 2020年1月15日 上午9:33:19
     * @param key
     * @return
     */
    private static String modifyKey(String key) {
        return new StringBuilder("【").append(key).append("】").toString();
    }

    /**
     * 
     * 描述 生成的打印文件的保存路径
     * 
     * @author Roger Li
     * @created 2019年12月23日 下午4:47:18
     * @param fileExt
     * @return
     */
    public static String getSavePath(String fileExt) {
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        String uploadPath = "PrintAttach/";
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        String str = "";
        String uuId = UUIDGenerator.getUUID();
        str = uuId + "." + fileExt;
        String filename = uploadFullPath + str;
        // filename 包含了配置文件中配置的attachFileFolderPath
        // filePath 只有attachFiles/开始的路径 因为他上传到服务器的那部分代码 又加了一遍配置文件中的地址。
        return filename;
    }

    /**
     * 
     * 描述 生成二维码 最大数据长度106
     * 
     * @author Roger Li
     * @created 2020年1月17日 上午10:26:04
     * @param srcValue
     * @param qrcodePicfilePath
     * @return
     */
    public static boolean encode(ByteArrayOutputStream byteArrayOut, String srcValue) {
        int MAX_DATA_LENGTH = 106; // 限制生成二维码的数据最大大小
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageWidth = 128; /*
                               * 113是预先计算出来的.
                               * 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
                               */
        int imageHeight = imageWidth;
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            /* 生成二维码 */
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认
            qrcode.setQrcodeEncodeMode('B'); // A, N, 默认
            qrcode.setQrcodeVersion(6); // 37字节, (37-1)*3+2+3-1+1 = 113
            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                        /*
                         * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen -1)*3+2) + 3 -1 ;
                         * 生成的image的宽度大小必须 >=该值, 外围的1个像素用来标识此块区域为二维码
                         */
                        /*
                         * fillRect(int x, int y, int width, int height) 函数作用：
                         * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
                         * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
                         * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
                         * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
                         * 
                         * 参考：http://bk.chinaar.com/index.php?doc-view-2999
                         */
                    }
                }
            }
        } else {
            return false;
        }
        g.dispose();
        bi.flush();
        try {
            ImageIO.write(bi, pICEXT, byteArrayOut);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }
    
    /**
     * 
     * 描述    通用文档生成（不含动态表格）
     * @author Danto Huang
     * @created 2020年8月24日 下午10:47:59
     * @param returnMap
     * @param path
     */
    public static void generateWord(Map<String, Object> returnMap,String path) {
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            in = new FileInputStream(new File(path));
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            // 处理段落
            List<XWPFParagraph> paragraphList = xwpfDocument.getParagraphs();
            WordReplaceUtil.processParagraphs(paragraphList, returnMap, xwpfDocument);
            Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                        
                        WordReplaceUtil.processParagraphs(paragraphListTable, returnMap, xwpfDocument);
                    }
                }
            }
            
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            xwpfDocument.write(fos);
            returnMap.put("bdcPath", savePath);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }
    
    /**
     * 
     * 描述    通用文档生成（动态表格）
     * @author Allin Lin
     * @created 2021年10月15日 上午10:37:03
     * @param returnMap
     * @param path
     * @param listKey
     * @param tableKey
     */
    public static void generateWordForDnamicTable(Map<String, Object> returnMap,String path,
            String listKey, String tableKey){
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            in = new FileInputStream(new File(path));
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            List<XWPFTableCell> cells = new ArrayList<XWPFTableCell>();
            for (XWPFTableRow row : xwpfDocument.getTables().get(0).getRows()) {
                cells.addAll(row.getTableCells());
            }
            for (XWPFTableCell cell1 : cells) {
                String tag = cell1.getText().trim();
                List<XWPFParagraph> paragraphs = cell1.getParagraphs();
                WordRedrawUtil.processParagraphs(paragraphs, returnMap, xwpfDocument);
            }
            // 处理动态表格
            List<Map<String, Object>> equipList = (List<Map<String, Object>>) returnMap.get(listKey);
            Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                // table.addRow(rows.get(0));
                XWPFTableRow oldRow = null;
     
                WordReplaceUtil.addTableRow3(returnMap, table, rows, oldRow, equipList, tableKey);
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> celllists = row.getTableCells();
                    for (XWPFTableCell cell : celllists) {
                        List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                        WordReplaceUtil.setTableRow(returnMap, xwpfDocument, celllists, paragraphListTable, equipList,
                                tableKey);
                        WordReplaceUtil.processParagraphs(paragraphListTable, returnMap, xwpfDocument);
                    }
                }
            }
            
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            xwpfDocument.write(fos);
            returnMap.put("bdcPath", savePath);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }
}
