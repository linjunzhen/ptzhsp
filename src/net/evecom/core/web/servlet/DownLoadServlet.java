/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.servlet;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.system.service.FileAttachService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月27日 上午10:45:46
 */
public class DownLoadServlet extends HttpServlet {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(DownLoadServlet.class);

    /**
     * Constructor of the object.
     */
    public DownLoadServlet() {
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
    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        ///response.setCharacterEncoding("utf-8");
        FileAttachService fileAttachService = (FileAttachService) AppUtil.getBean("fileAttachService");
        String fileId =  request.getParameter("fileId");
        String isForExt = request.getParameter("isForExt");
        String path=request.getParameter("path");
        Map<String,Object> fileAttach = null;
        String attachFileFolderPath = "";
        String fileName = "";
        String fileRullPath = "";
        String filePath = "";
        if(StringUtils.isNotEmpty(isForExt)){
            fileAttach = fileAttachService.getByJdbc("T_SYSTEM_FILEATTACH",
                    new String[]{"FILEID"},new Object[]{fileId});
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty("HlwAttachFilePath");
            fileName= (String) fileAttach.get("FILENAME");
            fileRullPath = attachFileFolderPath+fileAttach.get("FILEPATH");
            filePath = (String) fileAttach.get("FILE_PATH");
        }else if(StringUtils.isNotEmpty(path)){
            fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"},new Object[]{fileId});
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty(path);
            fileName= (String) fileAttach.get("FILE_NAME");
            fileRullPath= attachFileFolderPath+fileAttach.get("FILE_PATH");
            filePath = (String) fileAttach.get("FILE_PATH");
        }else{
            fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"},new Object[]{fileId});
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty("AttachFilePath");
            fileName= (String) fileAttach.get("FILE_NAME");
            fileRullPath= attachFileFolderPath+fileAttach.get("FILE_PATH");   
            filePath = (String) fileAttach.get("FILE_PATH");
        }
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
            in = null;
            int platType = Integer.parseInt(fileAttach.get("PLAT_TYPE").toString());
            if (StringUtils.isNotEmpty(filePath) && (filePath.contains("http://") || filePath.contains("https://"))) {
                in = getStreamDownloadOutFile(filePath);
            } else if (platType == 0 && fileAttach.get("FILE_PATH") != null
                    && StringUtils.isNotEmpty(fileAttach.get("FILE_PATH").toString())) {
                File fi = new File(fileRullPath);
                in = new FileInputStream(fi);

            } else if (platType == 1 && fileAttach.get("FILE_PATH") != null
                    && StringUtils.isNotEmpty(fileAttach.get("FILE_PATH").toString())) {
                Properties projectProperties = FileUtil.readProperties("project.properties");
                String fileServer = projectProperties.getProperty("uploadFileUrlIn");
                in = getStreamDownloadOutFile(fileServer + filePath);

            } else if (fileAttach.get("FILE_CONTENT") != null) {
                byte[] bys = (byte[]) fileAttach.get("FILE_CONTENT");
                in = new ByteArrayInputStream(bys);
            }
            int n = 0;// 每次读取的字节长度
            byte[] bb = new byte[1024];// 存储每次读取的内容
            while ((n = in.read(bb)) != -1) {
                os.write(bb, 0, n);// 将读取的内容，写入到输出流当中
            }
            /*os.close();// 关闭输入输出流
            in.close();*/
        } catch (Exception e) {
            // TODO: handle exception
            log.error("下载异常",e);
        }finally{
            if (os!=null) {
                try {
                    //os.flush();
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭os流异常",e);
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭in流异常",e);
                }
            }
        }
       
    }

    /**
     * 
     * 描述： 下载文件，返回输入流。
     * 
     * @author Rider Chen
     * @created 2019年6月5日 10:15:36
     * @param apiUrl
     * @return
     * @throws Exception
     */
    public static InputStream getStreamDownloadOutFile(String apiUrl) throws Exception {
        InputStream is = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();// 创建默认http客户端
        RequestConfig requestConfig = RequestConfig.DEFAULT;// 采用默认请求配置
        HttpGet request = new HttpGet(apiUrl);// 通过get方法下载文件流
        request.setConfig(requestConfig);// 设置请头求配置
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(request);// 执行请求，接收返回信息
            int statusCode = httpResponse.getStatusLine().getStatusCode();// 获取执行状态
            if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
                request.abort();
            } else {
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    is = entity.getContent();// 获取返回内容
                }
            }
        } catch (Exception e) {
            log.error("远程下载文件异常",e);
            request.abort();
        }
        return is;
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
