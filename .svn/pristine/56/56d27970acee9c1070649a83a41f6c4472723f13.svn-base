/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.controller.MaterConfigController;
import net.evecom.platform.project.util.RequestUtil;


/**
 * 描述 工程建设项目签章Controller
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2021/12/28 15:37:23
 */
@Controller
@RequestMapping("/projectSignController")
public class ProjectSignController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);
    
    /**
     * 描述:上传附件信息
     *
     * @author Scolder Lin
     * @created 2021/12/28 15:40:20
     * @param
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/uploadFile")
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String inFileType = request.getParameter("inFileType");
        String inFileDataBase64 = request.getParameter("inFileDataBase64");
        String resultJson = RequestUtil.upload(inFileType,inFileDataBase64);
        Map<String, Object> result = JSON.parseObject(resultJson, Map.class);
        if("200".equals(result.get("code").toString())) {
            result.put("success", true);
        }else {
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述:下载附件信息
     *
     * @author Scolder Lin
     * @created 2021/12/29 9:51:20
     * @param
     * @return
     * @throws Exception 
     */
    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String fileId = request.getParameter("fileId");
        String result = RequestUtil.download(fileId);
        JSONObject resultJson = JSON.parseObject(result);
        if(resultJson.getInteger("code") == 200 && resultJson.getJSONObject("data").getInteger("resultCode") ==0) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            //可以捕获内存缓冲区【生成的图片在缓冲区里面】的数据，将数据装换成字节数组  ,输出流的缓冲区的大小会随着数据的不断写入而自动增加  使用toByteArray()  toString()获得生成字节数组的数据
            String base64Data = resultJson.getJSONObject("data").getString("fileBase64Str");
            String fileType = resultJson.getJSONObject("data").getString("fileType");
            byte[] decode = Base64.getDecoder().decode(base64Data);
            ServletOutputStream out = null;//servlet程序想servletOutputStream或PrintWriter对象中写入数据将被servlet引擎从response中获得

            out = response.getOutputStream();
            output.writeTo(out);//将byte数组输出流的全部内容写到指定的输出流参数中

            ByteArrayInputStream inputStream = new ByteArrayInputStream(decode);
            // 设置返回内容格式
            //获得浏览器代理信息
            final String userAgent = request.getHeader("USER-AGENT");
            //判断浏览器代理并分别设置响应给浏览器的编码格式
            String finalFileName = fileId + "." + fileType;
            if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {//IE浏览器
                finalFileName = URLEncoder.encode(finalFileName.toLowerCase(), "UTF8");
                System.out.println("IE浏览器");
            } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
                finalFileName = new String(finalFileName.toLowerCase().getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(finalFileName.toLowerCase(), "UTF8");//其他浏览器
            }
            //设置HTTP响应头
            response.reset();//重置 响应头
            response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.addHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");//下载文件的名称
            // 循环取出流中的数据
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inputStream.close();
            response.getOutputStream().close();
            output.write(decode);
            output.close();
        }else {
            resultMap.put("success", false);
        }
    }
    
    /**
     * 描述:上传签章后的base64文件
     *
     * @author Madison You
     * @created 2019/11/21 20:24:00
     * @param
     * @return
     */
    @RequestMapping("/uploadProjectSignFile")
    @ResponseBody
    public Map<String, Object> decodeSealStr(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String uploadUserId = request.getParameter("uploadUserId");// 上传人ID
        String uploadUserName = request.getParameter("uploadUserName");// 上传人姓名
        String busTableName = request.getParameter("busTableName");// 获取业务表名称
        String busRecordId = request.getParameter("busRecordId");// 获取业务表记录ID
        String attachKey = request.getParameter("attachKey");// 材料编码
        String FLOW_EXEID = request.getParameter("FLOW_EXEID");// 流程实例ID
        String FLOW_TASKID = request.getParameter("FLOW_TASKID");// 流程任务ID
        String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");// 流程任务名称
        String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");// 上传人员部门ID
        String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");// 上传人员部门名称
        String name = request.getParameter("name");// 文件名称
        String SFHZD = "-1";// 是否回执单
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String fileId = request.getParameter("fileId");// 文件ID
        String fileResult = RequestUtil.download(fileId);
        JSONObject resultJson = JSON.parseObject(fileResult);
        String base64Code = resultJson.getJSONObject("data").getString("fileBase64Str");
        
        /*String base64Code = request.getParameter("base64Code");// 文件base64
        base64Code = base64Code.substring(base64Code.indexOf(",")+1);*/
        // formData参数
        Map<String, Object> resultMap = null;
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            String result = "";
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                String uuId = StringUtils.isNotEmpty(name) ? name : UUIDGenerator.getUUID();
                String fileName = uuId + ".ofd";// 文件名称
                param = new HashMap<String, Object>();
                param.put("uploaderId", uploadUserId);// 上传人ID
                param.put("uploaderName", uploadUserName); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName);// 上传附件名
                param.put("attachKey", attachKey);// 材料编码
                param.put("busTableName", busTableName);// 业务表名
                param.put("busRecordId", busRecordId);// 业务表ID
                param.put("flowExeId", FLOW_EXEID);// 流程实例ID
                param.put("flowTaskId", FLOW_TASKID);// 流程任务ID
                param.put("flowTaskName", FLOW_TASKNAME);// 流程任务名称
                param.put("uploaderDepId", UPLOADER_DEPID);// 上传人部门ID
                param.put("uploaderDepName", UPLOADER_DEPNAME);// 上传人部门名称
                param.put("SFHZD", SFHZD);// 是否回执单
                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
                if (StringUtils.isNotEmpty(result)) {
                    resultMap = JSON.parseObject(result, Map.class);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return resultMap;
    }
}
