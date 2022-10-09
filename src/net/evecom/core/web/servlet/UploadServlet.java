/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.CompressPic;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月26日 上午11:33:01
 */
public class UploadServlet extends HttpServlet {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(UploadServlet.class);
    /**
     * 禁止上传的文件类型
     */
    public static List<String> noAllowFileTypes = Arrays.asList("exe", "bat", "jsp", "html");
    /**
     * 图片类型定义
     */
    public static List<String> imgFileTypes = Arrays.asList("jpeg","jpg","gif","bmp","png");
    /**
     * Constructor of the object.
     */
    public UploadServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }
    
    private Map<String,String> makeFileFolder(File tempFileFolder,String uploadPath,String relativeFolderPath){
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        // 定义缓存临时文件的路径
        String tempFileFolderPath = attachFileFolderPath + "/temp/";
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        relativeFolderPath = uploadPath + "/" + currentDate + "/";
        // 定义上传文件目录的全路径
        String uploadFullPath = attachFileFolderPath + relativeFolderPath;
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        tempFileFolder = new File(tempFileFolderPath);
        if (!tempFileFolder.exists()) {
            tempFileFolder.mkdirs();
        }
        // 建立相对路径目录
        File relativeFolder = new File(relativeFolderPath);
        if (!relativeFolder.exists()) {
            relativeFolder.mkdirs();
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("uploadFullPath",uploadFullPath);
        map.put("relativeFolderPath",relativeFolderPath);
        return map;
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // 获取业务表名称
        String busTableName = request.getParameter("busTableName");
        // 获取业务表记录ID
        String busTableRecordId = request.getParameter("busRecordId");
        // 获取上传用户ID
        String uploadUserId = request.getParameter("uploadUserId");
        // 获取上传用户的名称
        String uploadUserName = request.getParameter("uploadUserName");
        // 获取服务器端保存路径
        String uploadPath = request.getParameter("uploadPath");
        // 获取附件KEY
        String attachKey = request.getParameter("attachKey");
        String FLOW_EXEID= request.getParameter("FLOW_EXEID");
        String FLOW_TASKID = request.getParameter("FLOW_TASKID");
        String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");
        String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");
        String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");
        String SFHZD = "-1";
        if(StringUtils.isNotEmpty(uploadUserName)){
            uploadUserName= URLDecoder.decode(uploadUserName,"UTF-8");
        }
        if(StringUtils.isNotEmpty(FLOW_TASKNAME)){
            FLOW_TASKNAME= URLDecoder.decode(FLOW_TASKNAME,"UTF-8");
        }
        if(StringUtils.isNotEmpty(UPLOADER_DEPNAME)){
            UPLOADER_DEPNAME= URLDecoder.decode(UPLOADER_DEPNAME,"UTF-8");
        }
        if(StringUtils.isNotEmpty(request.getParameter("SFHZD"))){
            SFHZD = request.getParameter("SFHZD");
        }
        //开始建立必要目录
        File tempFileFolder = null;
        String relativeFolderPath = "";
        String uploadFullPath = "";
        Map<String,String> pathMap = this.makeFileFolder(tempFileFolder, uploadPath, relativeFolderPath);
        relativeFolderPath= pathMap.get("relativeFolderPath");
        uploadFullPath = pathMap.get("uploadFullPath"); 
        //结束建立必要目录
        // 判断本次表单是否是一个multipart表单
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // 获取工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置缓冲区大小,单位字节
            factory.setSizeThreshold(4 * 1024 * 1024);
            // 设置缓冲区位置
            factory.setRepository(tempFileFolder);
            // 产生servlet上传对象
            ServletFileUpload uploader = new ServletFileUpload(factory);
            // //设置上传文件的最大大小，位置字节
            // uploader.setSizeMax(10*1024*1024);
            // 获取表单项
            try {
                List<FileItem> fileItems = uploader.parseRequest(request);
                for (FileItem item : fileItems) {
                    saveFileItem(response, busTableName, busTableRecordId, uploadUserId, uploadUserName, FLOW_EXEID,
                            FLOW_TASKID, FLOW_TASKNAME, UPLOADER_DEPID, UPLOADER_DEPNAME, SFHZD, attachKey,
                            relativeFolderPath, uploadFullPath, item);
                }
            } catch (FileUploadException e) {
                PrintWriter out = response.getWriter();
                out.print("{\"success\":false}");
                out.close();
                log.error(e.getMessage());
            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.print("{\"success\":false}");
                out.close();
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 描述
     * @author Flex Hu
     * @created 2016年1月4日 下午2:59:11
     * @param response
     * @param busTableName
     * @param busTableRecordId
     * @param uploadUserId
     * @param uploadUserName
     * @param FLOW_EXEID
     * @param FLOW_TASKID
     * @param FLOW_TASKNAME
     * @param UPLOADER_DEPID
     * @param UPLOADER_DEPNAME
     * @param SFHZD
     * @param attachKey
     * @param relativeFolderPath
     * @param uploadFullPath
     * @param item
     * @throws IOException
     * @throws Exception
     */
    private void saveFileItem(HttpServletResponse response, String busTableName, String busTableRecordId,
            String uploadUserId, String uploadUserName, String FLOW_EXEID, String FLOW_TASKID, String FLOW_TASKNAME,
            String UPLOADER_DEPID, String UPLOADER_DEPNAME, String SFHZD, String attachKey, String relativeFolderPath,
            String uploadFullPath, FileItem item) throws IOException, Exception {

        
        if (StringUtils.isNotEmpty(item.getContentType())) {
            Map<String, Object> fileAttach = new HashMap<String, Object>();
            String uuId = UUIDGenerator.getUUID();
            String fileName = item.getName();
            fileName = fileName.replace(" ", "");
            if(fileName.contains("/")){
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
            }else if(fileName.contains("\\")){
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
            }
            String fileType = FileUtil.getFileExtension(fileName);
            String filePath = "attachFiles/" + relativeFolderPath + uuId + "." + fileType;
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            long fileLength = item.getSize();
            String fileSize = FileUtil.getFormatFileSize(fileLength);
            if (noAllowFileTypes.contains(fileType)) {
                PrintWriter writer = response.getWriter();
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("success", false);
                result.put("msg", "上传的文件格式非法");
                String jsonResult = JSON.toJSONString(result);
                writer.println(jsonResult);
                writer.close();
            } else {
                //定义文件的全路径
                String fileFullPath = uploadFullPath + uuId + "." + fileType;
                // 写入文件
                File uploadFile = new File(fileFullPath);
                item.write(uploadFile);
                if(imgFileTypes.contains(fileType)){
                    //进行图片的压缩
                    CompressPic mypic = new CompressPic();
                    mypic.compressPic(fileFullPath,fileFullPath,true,1);
                }
                fileAttach.put("FILE_NAME", fileName);
                fileAttach.put("FILE_PATH", filePath);
                fileAttach.put("CREATE_TIME", createTime);
                fileAttach.put("FILE_TYPE", fileType);
                fileAttach.put("UPLOADER_ID", uploadUserId);
                fileAttach.put("UPLOADER_NAME", uploadUserName);
                fileAttach.put("FILE_LENGTH", fileLength);
                fileAttach.put("FILE_SIZE", fileSize);
                fileAttach.put("BUS_TABLENAME", busTableName);
                fileAttach.put("BUS_TABLERECORDID", busTableRecordId);
                fileAttach.put("ATTACH_KEY", attachKey);
                fileAttach.put("FLOW_EXEID", FLOW_EXEID);
                fileAttach.put("FLOW_TASKID", FLOW_TASKID);
                fileAttach.put("FLOW_TASKNAME", FLOW_TASKNAME);
                fileAttach.put("UPLOADER_DEPID", UPLOADER_DEPID);
                fileAttach.put("UPLOADER_DEPNAME", UPLOADER_DEPNAME);
                fileAttach.put("SFHZD", SFHZD);
                fileAttach.put("PLAT_TYPE", 0);
                //fileAttach.put("FILE_CONTENT", FileUtil.convertFileToBytes(uploadFile));
                if(imgFileTypes.contains(fileType)){
                    fileAttach.put("IS_IMG","1");
                }else{
                    fileAttach.put("IS_IMG","-1");
                }
                FileAttachService fileAttachService = (FileAttachService) AppUtil
                        .getBean("fileAttachService");
                String fileId = fileAttachService
                        .saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
                PrintWriter writer = response.getWriter();
                writer.println("{\"success\":true,\"fileId\":\"" + fileId + "\",\"filePath\":\"" + filePath
                        + "\",\"fileName\":\"" + fileName+ "\",\"fileType\":\"" + fileType 
                        + "\",\"isImg\":\"" + fileAttach.get("IS_IMG") 
                        + "\",\"attachKey\":\"" + attachKey + "\"}");
                writer.close();
            }
        }
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
