/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.platform.ems.waybillone.BASE64Encoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 描述:签章文件工具类
 *
 * @author Madison You
 * @created 2020/11/3 9:26:00
 * @param
 * @return
 */
public class MaterDownloadUtil {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterDownloadUtil.class);

    /**
     * 描述:下载文件
     *
     * @author Madison You
     * @created 2020/11/2 15:52:00
     * @param response
     * @param filePath  本地文件全路径
     * @param fileName  文件真实名称
     * @return
     */
    public static void downLoadFile(HttpServletResponse response, String filePath , String fileName) {
        OutputStream toClient = null;
        InputStream fis  = null;
        try {
            String newFileName = fileName.split("\\.")[0] + filePath.substring(filePath.lastIndexOf("."));
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(newFileName.getBytes("gbk"), "iso8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-msdownload");
            toClient.write(buffer);
            toClient.flush();
        } catch (Exception e) {
            log.error("下载异常，文件路径为" + filePath);
            log.error(e.getMessage(),e);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 描述:下载base64文件
     *
     * @author Madison You
     * @created 2021/1/13 10:38:00
     * @param response 响应
     * @param content base64编码
     * @return
     */
    public static void downLoadFileBase64(HttpServletResponse response, String content , String fileName) {
        OutputStream toClient = null;
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(content);
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(fileName.getBytes("gbk"), "iso8859-1") + "\"");
            toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-msdownload");
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException e) {
            log.error("下载base64文件失败", e);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 描述:保存base64文件到磁盘
     *
     * @author Madison You
     * @created 2021/2/1 15:08:00
     * @param filePath 文件保存路径
     * @param content base64编码
     * @return
     */
    public static void saveFileBase64(String filePath, String content) {
        FileOutputStream ous = null;
        try{
            byte[] buffer = new BASE64Decoder().decodeBuffer(content);
            ous = new FileOutputStream(filePath);
            ous.write(buffer);
            ous.flush();
        } catch (Exception e) {
            log.error("保存base64文件到磁盘出错", e);
        } finally {
            if (ous != null) {
                try {
                    ous.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

    }

    /**
     * 描述:材料上传至文件服务器
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/7/17 15:12:00
     */
    public static Map<String, Object> uploadFileToServer(String filePath, String fileName){
        Map<String, Object> resMap = null;
        String respResult = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String attachmentFilePath = properties.getProperty("uploadFileUrlIn");
        String url = attachmentFilePath + "upload/file";
        File file = new File(filePath);
        // 文件服务器上传
        String app_id = "0001";// 分配的用户名
        String password = "bingo666";// 分配的密码
        String responesCode = "UTF-8";// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "402881e65127d9ea015127d9ea830000");// 上传人ID
        param.put("uploaderName", "商事材料系统自动上传"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        String newFileName = fileName.split("\\.")[0] + filePath.substring(filePath.lastIndexOf("."));
        param.put("name", newFileName);// 上传附件名
        param.put("busTableName", "T_COMMERCIAL_COMPANY");// 业务表名 param.put("busRecordId",busRecordId);// 业务表ID\
        respResult = HttpRequestUtil.sendFilePost(url, file, responesCode, app_id, password, param);
        if (StringUtils.isNotEmpty(respResult)) {
            resMap = JSON.parseObject(respResult);
        }
        return resMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/18 11:57:00
     * @param
     * @return
     */
    public static void saveFileToDisk(String oldFilePath , String newFilePath) {
        InputStream in = null;
        FileOutputStream fos = null;
        try{
            if (oldFilePath.contains("http://") || oldFilePath.contains("https://")) {
                in = new URL(oldFilePath).openConnection().getInputStream();
                fos = new FileOutputStream(newFilePath);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
                fos.flush();
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(e);
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    /**
     * 描述:根据url文件获取base64code字符串
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/11/26 11:29:00
     */
    public static String getBase64CodeByUrlFile(String url) {
        InputStream inputStream = null;
        String encode = null;
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            inputStream = urlConnection.getInputStream();
            byte[] data = null;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
            encode = BASE64Encoder.encode(data);
        } catch (IOException e) {
            log.info(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
        return encode;
    }

    /**
     * 描述:下载文件返回错误信息
     *
     * @author Madison You
     * @created 2021/1/13 10:57:00
     * @param
     * @return
     */
    public static void downloadReturnMsg(HttpServletResponse response , String msg) {
        try{
            response.getWriter().write(msg);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
