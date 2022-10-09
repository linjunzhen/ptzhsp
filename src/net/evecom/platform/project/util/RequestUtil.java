/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.util;

import com.google.gson.JsonObject;
import com.mzlion.easyokhttp.HttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 签章相关接口
 * 
 * @author Scolder Lin
 * @version 2021-12-30
 */

public class RequestUtil {
    /**
     * log
     */
    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);
    /**
     * 服务地址
     */
    private static String baseUrl = "http://112.54.44.145:9002";
    /**
     * 测试应用id
     */
    private static String appid = "3c1f5e2c7714";
    /**
     * 测试应用密钥
     */
    private static String appsecret = "c5ed9d3ceab24dedbdcb4a77";
    /**
     * 默认编码
     */
    private static String defalutCharset = "utf-8";

    public static String file2Base64(File file) {
        if (file == null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encodeBase64String(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    private static File base64ToFile(String base64, String str) {
        if (base64 == null || "".equals(base64)) {
            return null;
        }
        byte[] buff = Base64.decodeBase64(base64);
        File file = null;
        FileOutputStream fout = null;
        try {
            file = new File(str);
            fout = new FileOutputStream(file);
            fout.write(buff);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private static String commonHttp(String url, String data) {
        // https请求参数
        JsonObject reqParams = new JsonObject();
        // 应用id
        reqParams.addProperty("appid", appid);
        // 业务数据
        reqParams.addProperty("data", data);
        // 系统当前时间戳字符串（格式yyyyMMddHHmmss）
        String timestamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");
        reqParams.addProperty("timestamp", timestamp);
        // 计算摘要参数
        String digest = Sm3Utils.getInstance().encrypt((appid + data + timestamp + appsecret));
        reqParams.addProperty("digest", digest);
        // 请求参数Json格式
        String reqBodyJson = reqParams.toString();
        // 发起请求 Content-Type application/json
        return HttpClient.textBody(url).json(reqBodyJson).asString();
    }

    /**
     * 1、上传
     *
     * @throws Exception
     */
    public static String upload(String type, String base64) throws Exception {
        String url = baseUrl + "/caofd/v1/uploadFile";

        // 业务参数处理
        JsonObject dataObj = new JsonObject();
        dataObj.addProperty("fileBase64Str", base64.substring(base64.indexOf(",") + 1));
        dataObj.addProperty("fileType", type);

        // 业务参数json
        String dataJson = dataObj.toString();

        // 业务数据转Base64编码
        String data = Base64.encodeBase64String(dataJson.getBytes(defalutCharset));

        // 返回结果
        return commonHttp(url, data);
    }

    /**
     * 2、下载
     *
     * @throws Exception
     */
    public static String download(String fileId) throws Exception {
        String url = baseUrl + "/caofd/v1/downloadFile";

        // 业务参数处理
        JsonObject dataObj = new JsonObject();
        dataObj.addProperty("fileId", fileId);

        // 业务参数json
        String dataJson = dataObj.toString();

        // 业务数据转Base64编码
        String data = org.apache.commons.codec.binary.Base64.encodeBase64String(dataJson.getBytes(defalutCharset));

        // 发起请求 Content-Type application/json
        return commonHttp(url, data);
    }

    /**
     * 3、删除文件
     *
     * @throws Exception
     */
    public static String delete(String fileId) throws Exception {
        String url = baseUrl + "/caofd/v1/deleteFile";

        // 业务参数处理
        JsonObject dataObj = new JsonObject();
        dataObj.addProperty("fileId", fileId);

        // 业务参数json
        String dataJson = dataObj.toString();

        // 业务数据转Base64编码
        String data = org.apache.commons.codec.binary.Base64.encodeBase64String(dataJson.getBytes(defalutCharset));

        // 发起请求 Content-Type application/json
        return commonHttp(url, data);
        // 返回结果
    }

    /**
     * 向浏览器发送文件下载，支持断点续传
     *
     * @param base64
     *            要下载的文件base64
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param fileName
     *            指定下载的文件名
     * @return 返回错误信息，无错误信息返回null
     */
    public static String downFile(String base64, HttpServletRequest request, HttpServletResponse response,
            String fileName) {
        // String error = null;
        // if (file != null && file.exists()) {
        // if (file.isFile()) {
        // if (file.length() <= 0) {
        // error = "该文件是一个空文件。";
        // }
        // if (!file.canRead()) {
        // error = "该文件没有读取权限。";
        // }
        // } else {
        // error = "该文件是一个文件夹。";
        // }
        // } else {
        // error = "文件已丢失或不存在！";
        // }
        // if (error != null) {
        // return error;
        // }
        base64StringToFile(base64, "D:\\temp");
        File file = new File("D:\\temp");
        long fileLength = file.length(); // 记录文件大小
        long pastLength = 0; // 记录已下载文件大小
        int rangeSwitch = 0; // 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
        long toLength = 0; // 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
        long contentLength = 0; // 客户端请求的字节总量
        String rangeBytes = ""; // 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
        RandomAccessFile raf = null; // 负责读取数据
        OutputStream os = null; // 写出数据
        OutputStream out = null; // 缓冲
        byte b[] = new byte[1024]; // 暂存容器

        if (request.getHeader("Range") != null) { // 客户端请求的下载的文件块的开始字节
            response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
            rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
            if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {// bytes=969998336-
                rangeSwitch = 1;
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
                contentLength = fileLength - pastLength; // 客户端请求的是 969998336
                                                         // 之后的字节
            } else { // bytes=1275856879-1275877358
                rangeSwitch = 2;
                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
                pastLength = Long.parseLong(temp0.trim()); // bytes=1275856879-1275877358，从第
                                                           // 1275856879 个字节开始下载
                toLength = Long.parseLong(temp2); // bytes=1275856879-1275877358，到第
                                                  // 1275877358 个字节结束
                contentLength = toLength - pastLength; // 客户端请求的是
                                                       // 1275856879-1275877358
                                                       // 之间的字节
            }
        } else { // 从开始进行下载
            contentLength = fileLength; // 客户端要求全文下载
        }

        // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。 响应的格式是:
        // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
        // ServletActionContext.getResponse().setHeader("Content- Length", new
        // Long(file.length() - p).toString());
        response.reset(); // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        if (pastLength != 0) {
            response.setHeader("Accept-Ranges", "bytes");// 如果是第一次下,还没有断点续传,状态是默认的
                                                         // 200,无需显式设置;响应的格式是:HTTP/1.1
                                                         // 200 OK
            // 不是从最开始下载, 响应的格式是: Content-Range: bytes [文件块的开始字节]-[文件的总大小 -
            // 1]/[文件的总大小]
            logger.debug("---------------不是从开始进行下载！服务器即将开始断点续传...");
            switch (rangeSwitch) {
                case 1: { // 针对 bytes=27000- 的请求
                    String contentRange = new StringBuffer("bytes ").append(new Long(pastLength).toString()).append("-")
                            .append(new Long(fileLength - 1).toString()).append("/")
                            .append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                case 2: { // 针对 bytes=27000-39000 的请求
                    String contentRange = rangeBytes + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                default: {
                    break;
                }
            }
        } else {
            // 是从开始下载
            logger.debug("---------------是从开始进行下载！");
        }

        try {
            response.addHeader("Content-Disposition", "attachment; filename=\""
                    + Encodes.urlEncode(StringUtils.isBlank(fileName) ? file.getName() : fileName) + "\"");
            response.setContentType(getContentType(file.getName())); // set the
                                                                     // MIME
                                                                     // type.
            response.addHeader("Content-Length", String.valueOf(contentLength));
            os = response.getOutputStream();
            out = new BufferedOutputStream(os);
            raf = new RandomAccessFile(file, "r");
            try {
                switch (rangeSwitch) {
                    case 0: { // 普通下载，或者从头开始的下载 同1
                    }
                    case 1: { // 针对 bytes=27000- 的请求
                        raf.seek(pastLength); // 形如 bytes=969998336- 的客户端请求，跳过
                                              // 969998336 个字节
                        int n = 0;
                        while ((n = raf.read(b, 0, 1024)) != -1) {
                            out.write(b, 0, n);
                        }
                        break;
                    }
                    case 2: { // 针对 bytes=27000-39000 的请求
                        raf.seek(pastLength); // 形如 bytes=1275856879-1275877358
                                              // 的客户端请求，找到第 1275856879 个字节
                        int n = 0;
                        long readLength = 0; // 记录已读字节数
                        while (readLength <= contentLength - 1024) {// 大部分字节在这里读取
                            n = raf.read(b, 0, 1024);
                            readLength += 1024;
                            out.write(b, 0, n);
                        }
                        if (readLength <= contentLength) { // 余下的不足 1024
                                                           // 个字节在这里读取
                            n = raf.read(b, 0, (int) (contentLength - readLength));
                            out.write(b, 0, n);
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
                out.flush();
                logger.debug("---------------下载完成！");
            } catch (IOException ie) {
                /**
                 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
                 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
                 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
                 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
                 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
                 * 所以，我们忽略这种异常
                 */
                logger.debug("提醒：向客户端传输时出现IO异常，但此异常是允许的，有可能客户端取消了下载，导致此异常，不用关心！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 根据“文件名的后缀”获取文件内容类型（而非根据File.getContentType()读取的文件类型）
     *
     * @param returnFileName
     *            带验证的文件名
     * @return 返回文件类型
     */
    public static String getContentType(String returnFileName) {
        String contentType = "application/octet-stream";
        if (returnFileName.lastIndexOf(".") < 0)
            return contentType;
        returnFileName = returnFileName.toLowerCase();
        returnFileName = returnFileName.substring(returnFileName.lastIndexOf(".") + 1);
        if (returnFileName.equals("html") || returnFileName.equals("htm") || returnFileName.equals("shtml")) {
            contentType = "text/html";
        } else if (returnFileName.equals("apk")) {
            contentType = "application/vnd.android.package-archive";
        } else if (returnFileName.equals("sis")) {
            contentType = "application/vnd.symbian.install";
        } else if (returnFileName.equals("sisx")) {
            contentType = "application/vnd.symbian.install";
        } else if (returnFileName.equals("exe")) {
            contentType = "application/x-msdownload";
        } else if (returnFileName.equals("msi")) {
            contentType = "application/x-msdownload";
        } else if (returnFileName.equals("css")) {
            contentType = "text/css";
        } else if (returnFileName.equals("xml")) {
            contentType = "text/xml";
        } else if (returnFileName.equals("gif")) {
            contentType = "image/gif";
        } else if (returnFileName.equals("jpeg") || returnFileName.equals("jpg")) {
            contentType = "image/jpeg";
        } else if (returnFileName.equals("static/js")) {
            contentType = "application/x-javascript";
        } else if (returnFileName.equals("atom")) {
            contentType = "application/atom+xml";
        } else if (returnFileName.equals("rss")) {
            contentType = "application/rss+xml";
        } else if (returnFileName.equals("mml")) {
            contentType = "text/mathml";
        } else if (returnFileName.equals("txt")) {
            contentType = "text/plain";
        } else if (returnFileName.equals("jad")) {
            contentType = "text/vnd.sun.j2me.app-descriptor";
        } else if (returnFileName.equals("wml")) {
            contentType = "text/vnd.wap.wml";
        } else if (returnFileName.equals("htc")) {
            contentType = "text/x-component";
        } else if (returnFileName.equals("png")) {
            contentType = "image/png";
        } else if (returnFileName.equals("tif") || returnFileName.equals("tiff")) {
            contentType = "image/tiff";
        } else if (returnFileName.equals("wbmp")) {
            contentType = "image/vnd.wap.wbmp";
        } else if (returnFileName.equals("ico")) {
            contentType = "image/x-icon";
        } else if (returnFileName.equals("jng")) {
            contentType = "image/x-jng";
        } else if (returnFileName.equals("bmp")) {
            contentType = "image/x-ms-bmp";
        } else if (returnFileName.equals("svg")) {
            contentType = "image/svg+xml";
        } else if (returnFileName.equals("jar") || returnFileName.equals("var") || returnFileName.equals("ear")) {
            contentType = "application/java-archive";
        } else if (returnFileName.equals("doc")) {
            contentType = "application/msword";
        } else if (returnFileName.equals("pdf")) {
            contentType = "application/pdf";
        } else if (returnFileName.equals("rtf")) {
            contentType = "application/rtf";
        } else if (returnFileName.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (returnFileName.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (returnFileName.equals("7z")) {
            contentType = "application/x-7z-compressed";
        } else if (returnFileName.equals("rar")) {
            contentType = "application/x-rar-compressed";
        } else if (returnFileName.equals("swf")) {
            contentType = "application/x-shockwave-flash";
        } else if (returnFileName.equals("rpm")) {
            contentType = "application/x-redhat-package-manager";
        } else if (returnFileName.equals("der") || returnFileName.equals("pem") || returnFileName.equals("crt")) {
            contentType = "application/x-x509-ca-cert";
        } else if (returnFileName.equals("xhtml")) {
            contentType = "application/xhtml+xml";
        } else if (returnFileName.equals("zip")) {
            contentType = "application/zip";
        } else if (returnFileName.equals("mid") || returnFileName.equals("midi") || returnFileName.equals("kar")) {
            contentType = "audio/midi";
        } else if (returnFileName.equals("mp3")) {
            contentType = "audio/mpeg";
        } else if (returnFileName.equals("ogg")) {
            contentType = "audio/ogg";
        } else if (returnFileName.equals("m4a")) {
            contentType = "audio/x-m4a";
        } else if (returnFileName.equals("ra")) {
            contentType = "audio/x-realaudio";
        } else if (returnFileName.equals("3gpp") || returnFileName.equals("3gp")) {
            contentType = "video/3gpp";
        } else if (returnFileName.equals("mp4")) {
            contentType = "video/mp4";
        } else if (returnFileName.equals("mpeg") || returnFileName.equals("mpg")) {
            contentType = "video/mpeg";
        } else if (returnFileName.equals("mov")) {
            contentType = "video/quicktime";
        } else if (returnFileName.equals("flv")) {
            contentType = "video/x-flv";
        } else if (returnFileName.equals("m4v")) {
            contentType = "video/x-m4v";
        } else if (returnFileName.equals("mng")) {
            contentType = "video/x-mng";
        } else if (returnFileName.equals("asx") || returnFileName.equals("asf")) {
            contentType = "video/x-ms-asf";
        } else if (returnFileName.equals("wmv")) {
            contentType = "video/x-ms-wmv";
        } else if (returnFileName.equals("avi")) {
            contentType = "video/x-msvideo";
        }
        return contentType;
    }

    public static void base64StringToFile(String base64String, String filepath) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            byte[] bytes = java.util.Base64.getDecoder().decode(base64String);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(byteArrayInputStream);
            File file = new File(filepath);
            File path = file.getParentFile();
            if (!path.exists()) {
                path.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                bos.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            bos.flush();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                bis.close();
                bos.close();
                fos.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
}
