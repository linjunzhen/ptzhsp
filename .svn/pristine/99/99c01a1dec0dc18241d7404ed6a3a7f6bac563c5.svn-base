/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.ptwg.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.UUIDGenerator;

/**
 * 
 * 描述
 * 
 * @author Rider Chen
 * @created 2016年9月7日 下午2:56:10
 */
public class PtwgUploadServlet extends HttpServlet {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(PtwgUploadServlet.class);

    /**
     * Constructor of the object.
     */
    public PtwgUploadServlet() {
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
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @SuppressWarnings("rawtypes")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // String url = request.getParameter("URL");
        String localFilePath = request.getParameter("localFilePath");
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath");

        String url = properties.getProperty("AttachFileUrl") + "UploadServlet";
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/image/" + currentDate;
        String uuId = UUIDGenerator.getUUID();
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(attachFileFolderPath + uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        uploadFullPath += "/" + uuId + ".jpg";
        // 把base64文件保存到本地
        FileUtil.decodeBase64File(localFilePath, attachFileFolderPath + uploadFullPath);

        Map<String, String> map = new HashMap<String, String>();
        Enumeration enu = request.getParameterNames();
        StringBuffer param = new StringBuffer("");
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if (!paraName.equals("URL") && !paraName.equals("localFilePath")) {
                map.put(paraName, request.getParameter(paraName));
                if (paraName.equals("uploadUserName")) {
                    param.append("&").append(paraName).append("=")
                            .append(URLEncoder.encode(request.getParameter(paraName), "utf-8"));
                    // param
                    // +="&"+paraName+"="+URLEncoder.encode(request.getParameter(paraName),
                    // "utf-8");
                } else {
                    param.append("&").append(paraName).append("=").append(request.getParameter(paraName));
                    // param +="&"+paraName+"="+request.getParameter(paraName);
                }
            }
        }
        String paramStr = param.toString();
        if (StringUtils.isNotEmpty(paramStr)) {
            paramStr = paramStr.substring(1, paramStr.length());
        }

        String json = HttpRequestUtil.sendFilePost(url, paramStr, attachFileFolderPath + uploadFullPath, "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("", e);
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException
     *             if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
