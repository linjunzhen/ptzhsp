/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 描述 doc/docx/txt/pdf格式转换swf
 * 
 * @author Danto Huang
 * @created 2014-11-11 下午4:32:13
 */
public class DocConverter {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DocConverter.class);
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
     * doc/txt文件
     */
    private File docFile;
    /**
     * odt文件
     */
    private File odtFile;
    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 构造
     * 
     * @param fileString
     */
    public DocConverter(String fileString) {
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
        fileType = fileString.substring(fileString.lastIndexOf(".")+1);
        if(fileType.equals("pdf")){
            pdfFile = new File(fileString);
            swfFile = new File(fileName + ".swf");
        }else{
            docFile = new File(fileString);
            pdfFile = new File(fileName + ".pdf");
            swfFile = new File(fileName + ".swf");
        }
    }

    /**
     * doc/txt转换pdf
     * 
     * @throws Exception
     */
    private void docToPdf() throws Exception {
        if (docFile.exists()) {
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            try {
                connection.connect();
                DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                if(fileType.equals("txt")){
                    odtFile = new File(fileName + ".odt");
                    this.copyFile(docFile, odtFile);
                    converter.convert(odtFile, pdfFile);
                }else{
                    converter.convert(docFile, pdfFile);
                }
                connection.disconnect();
            } catch (ConnectException e) {
                log.error(e.getMessage());
                log.info("****swf转换器异常，openoffice服务未启动！****");
                throw e;
            } catch (OpenOfficeException e) {
                log.error(e.getMessage());
                log.info("****swf转换器异常，读取转换文件失败****");
                throw e;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw e;
            }
        }else {
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
     /*                   Process p = r.exec(swftoolsPath + " " + pdfFile.getPath() + " -o " + " " + swfFile.getPath()
                                + " -T 9 -f");*/
                        //System.out.print(loadStream(p.getInputStream()));
                        //System.err.print(loadStream(p.getErrorStream()));
                        //System.out.print(loadStream(p.getInputStream()));
                        //System.err.println("****swf转换成功，文件输出：" + swfFile.getPath() + "****");
                        if (pdfFile.exists()&&!fileType.equals("pdf")) {
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
//                        if (pdfFile.exists()&&!fileType.equals("pdf")) {
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
            if(!fileType.equals("pdf")){
                docToPdf();
            }
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
    /**
     * 
     * 描述 文件复制
     * @author Danto Huang
     * @created 2014-12-2 下午6:31:55
     * @param sourceFile
     * @param targetFile
     */
    public void copyFile(File sourceFile,File targetFile) {
        FileOutputStream output = null;
        BufferedOutputStream outbuff = null;
        FileInputStream input = null;
        BufferedInputStream inbuff = null;
        try {
            input = new FileInputStream(sourceFile);
            inbuff = new BufferedInputStream(input);
            output = new FileOutputStream(targetFile);
            outbuff = new BufferedOutputStream(output);
            byte[] b = new byte[1024*5];
            int len;
            while((len=inbuff.read(b))!=-1){
                outbuff.write(b, 0, len);
            }
            
            outbuff.flush();
            
            inbuff.close();
            outbuff.close();
            input.close();
            output.close();
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage());
        }finally{
            if (input!=null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (output!=null) {
                try {
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (inbuff!=null) {
                try {
                    inbuff.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (outbuff!=null) {
                try {
                    outbuff.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
}
