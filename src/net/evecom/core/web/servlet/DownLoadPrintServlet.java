/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.platform.wsbs.service.PrintAttachService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月27日 上午10:45:46
 */
public class DownLoadPrintServlet extends HttpServlet {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DownLoadPrintServlet.class);

    /**
     * Constructor of the object.
     */
    public DownLoadPrintServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        ///response.setCharacterEncoding("utf-8");
        PrintAttachService printAttachService = (PrintAttachService) AppUtil.getBean("printAttachService");
        String fileId =  request.getParameter("fileId");
        Map<String,Object> fileAttach = null;
        String attachFileFolderPath = "";
        String fileName = "";
        String fileRullPath = "";
        fileAttach = printAttachService.getByJdbc("T_WSBS_PRINTATTACH",
                    new String[]{"FILE_ID"},new Object[]{fileId});
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("AttachFilePath");
        // pdf文件
        if(fileAttach.get("FILE_PATH") != null && fileAttach.get("FILE_PATH").toString().indexOf(".pdf") > -1) {
            fileName= (String) fileAttach.get("FILE_NAME")+".pdf";
        } else {
            fileName= (String) fileAttach.get("FILE_NAME")+".doc";
        }
        fileRullPath= attachFileFolderPath+fileAttach.get("FILE_PATH");
        response.setHeader("Cache-Control", "no-cache");   
        response.setContentType("application/octet-stream");
        OutputStream os = null;
        InputStream in = null;
        try {
            if(BrowserUtils.checkBrowse(request).contains("IE")){
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + java.net.URLEncoder.encode(fileName,"utf-8")+"\"");
            }else{
                response.setHeader("Content-Disposition", "attachment;filename=\""
                        + new String(fileName.getBytes("gb2312"), "ISO8859-1")+"\"");
            }
            os = response.getOutputStream();

            int platType = Integer.parseInt(fileAttach.get("PLAT_TYPE").toString());
            if (platType == 0 && fileAttach.get("FILE_PATH") != null
                    && StringUtils.isNotEmpty(fileAttach.get("FILE_PATH").toString())) {
                File fi = new File(fileRullPath);
                in = new FileInputStream(fi);

            } else if (platType == 1 && fileAttach.get("FILE_PATH") != null
                    && StringUtils.isNotEmpty(fileAttach.get("FILE_PATH").toString())) {
                Properties projectProperties = FileUtil.readProperties("project.properties");
                String fileServer = projectProperties.getProperty("uploadFileUrlIn");
                in = HttpRequestUtil.getStreamDownloadOutFile(fileServer + fileAttach.get("FILE_PATH"));

            }
            int n = 0;// 每次读取的字节长度
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = in.read(bb)) != -1) {
                os.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }
            os.close();// 关闭输入输出流
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if (os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
    

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
