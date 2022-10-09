/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;     
import java.io.File;
import java.io.FileOutputStream;     
import java.io.FileReader;     
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;     
import com.lowagie.text.DocumentException;     
import com.lowagie.text.Element;     
import com.lowagie.text.Font;     
import com.lowagie.text.PageSize;     
import com.lowagie.text.Paragraph;     
import com.lowagie.text.pdf.BaseFont;     
import com.lowagie.text.pdf.PdfWriter;


/**
 * 描述 text格式转换
 * 
 * @author Danto Huang
 * @created 2014-11-11 下午4:32:13
 */
public class TxtConverter {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(TxtConverter.class);
    /**
     * 运行环境
     */
    private static final int ENVIRONMENT = 1;// 环境1：windows，2：Linux
    /**
     * 文件路径
     */
    private String fileString;
    /**
     * 输出路径
     */
    private String outputPath = "";
    /**
     * 文件名
     */
    private String fileName;
    /**
     * pdf文件
     */
    private File pdfFile;
    /**
     * swf文件
     */
    private File swfFile;
    /**
     * txt文件
     */
    private File txtFile;
    
    /**
     * 构造
     * 
     * @param fileString
     */
    public TxtConverter(String fileString) {
        ini(fileString);
    }

    /**
     * 重新设置file
     * 
     * @param fileString
     */
    public void setFile(String fileString) {
        ini(fileString);
    }

    /**
     * 初始化
     * 
     * @param fileString
     */
    private void ini(String fileString) {
        this.fileString = fileString;
        fileName = fileString.substring(0, fileString.lastIndexOf("."));
        txtFile = new File(fileString);
        pdfFile = new File(fileName + ".pdf");
        swfFile = new File(fileName + ".swf");
    }
    
    /**
     * txt转换pdf
     * 
     * @throws Exception
     */
    private void txtToPdf() throws Exception {
        if (txtFile.exists()) {
            Document document = new Document(PageSize.A4, 80, 80, 60, 30);
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 18, Font.NORMAL);
            Paragraph t = new Paragraph(null,FontChinese);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setLeading(30.0f);
            document.add(t);
            FontChinese = new Font(bfChinese, 11, Font.NORMAL);
            BufferedReader read = null;
            try {
                read = new BufferedReader(new FileReader(fileString));
                String line = null;
                while ((line = read.readLine()) != null) {
                    t = new Paragraph(line, FontChinese);
                    t.setAlignment(Element.ALIGN_LEFT);
                    t.setLeading(20.0f);
                    document.add(t);
                }
            } catch (Exception e) {
                log.info("目标文件不存，或者不可读！");
                log.error(e.getMessage());
            } finally {
                try {
                    read.close();
                    document.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            log.info("****完成PDF转换****");
        } else {
            log.info("****swf转换器异常，需要转换的文档不存在，无法转换****");
        }

    }
    
    /**
     * pdf转换swf
     * 
     * @throws Exception
     */
    private void pdfToSwf() throws Exception {
//        Runtime r = Runtime.getRuntime();
//        Properties properties = FileUtil.readProperties("project.properties");
//        String swftoolsPath = properties.getProperty("swftoolsPath");
        if (!swfFile.exists()) {
            if (pdfFile.exists()) {
                if (ENVIRONMENT == 1) {
                    try {
                        /*Process p = r.exec(swftoolsPath+ pdfFile.getPath() + " -o "
                                + swfFile.getPath() + " -T 9");*/
                        //System.out.print(loadStream(p.getInputStream()));
                        //System.err.print(loadStream(p.getErrorStream()));
                        //System.out.print(loadStream(p.getInputStream()));
                        //System.err.println("****swf转换成功，文件输出：" + swfFile.getPath() + "****");
                        if (pdfFile.exists()) {
                            pdfFile.delete();
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw e;
                    }
//                } else if (ENVIRONMENT == 2) {// linux环境处理
//                    try {
//                        Process p = r.exec("pdf2swf " + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
//                        //System.out.print(loadStream(p.getInputStream()));
//                        //System.err.print(loadStream(p.getErrorStream()));
//                        //System.err.println("****swf转换成功，文件输出：" + swfFile.getPath() + "****");
//                        if (pdfFile.exists()) {
//                            pdfFile.delete();
//                        }
//                    } catch (Exception e) {
//                        log.error(e.getMessage());
//                        throw e;
//                    }
                }
            } else {
                log.info("****pdf不存在,无法转换****");
            }
        } else {
            log.info("****swf已经存在不需要转换****");
        }
    }
    
    /**
     * 流处理
     * 
     * @param in
     * @return
     * @throws IOException
     */
    static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }
    
    /**
     * 转换主方法
     */
    @SuppressWarnings("unused")
    public boolean conver() {

        if (swfFile.exists()) {
            log.info("****swf转换器开始工作，该文件已经转换为swf****");
            return true;
        }

        if (ENVIRONMENT == 1) {
            log.info("****swf转换器开始工作，当前设置运行环境windows****");
        } else {
            log.info("****swf转换器开始工作，当前设置运行环境linux****");
        }
        try {
            txtToPdf();
            pdfToSwf();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        if (swfFile.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回文件路径
     * 
     * @param s
     */
    public String getswfPath() {
        if (swfFile.exists()) {
            String tempString = swfFile.getPath();
            tempString = tempString.replaceAll("\\\\", "/");
            return tempString;
        } else {
            return "";
        }

    }

    /**
     * 设置输出路径
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
        if (!outputPath.equals("")) {
            String realName = fileName.substring(fileName.lastIndexOf("/"), fileName.lastIndexOf("."));
            if (outputPath.charAt(outputPath.length()) == '/') {
                swfFile = new File(outputPath + realName + ".swf");
            } else {
                swfFile = new File(outputPath + realName + ".swf");
            }
        }
    }
//    private final static String READFILEPATH = "D:\\盘龙.txt";     
//    private final static String WRITEFILEPATH = "D:\\盘龙.pdf";    
//    
//    public static void main(String[] args) throws DocumentException,     
//            IOException {     
//        Document document = new Document(PageSize.A4, 80, 80, 60, 30);     
//        PdfWriter.getInstance(document, new FileOutputStream(WRITEFILEPATH));     
//        document.open();     
//        BaseFont bfChinese = BaseFont.createFont("STSong-Light",     
//                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);     
//        Font FontChinese = new Font(bfChinese, 18, Font.NORMAL);     
//        Paragraph t = new Paragraph("oracle手册", FontChinese); //起一个别名，上班老板都不会发现，呵呵。     
//        t.setAlignment(Element.ALIGN_CENTER);     
//        t.setLeading(30.0f);     
//        document.add(t);     
//        FontChinese = new Font(bfChinese, 11, Font.NORMAL);     
//        BufferedReader read = null;     
//        try {     
//            read = new BufferedReader(new FileReader(READFILEPATH));     
//            String line = null;     
//            while ((line = read.readLine()) != null) {     
//                t = new Paragraph(line, FontChinese);     
//                t.setAlignment(Element.ALIGN_LEFT);     
//                t.setLeading(20.0f);     
//                document.add(t);     
//            }     
//        } catch (Exception e) {     
//            log.info("目标文件不存，或者不可读！");     
//            log.error(e.getMessage());     
//        } finally {     
//            try {     
//                read.close();     
//                document.close();     
//            } catch (IOException e) {     
//                log.error(e.getMessage());     
//            }     
//        }     
//        log.info("============执行成功！===========");     
//    }     
}
