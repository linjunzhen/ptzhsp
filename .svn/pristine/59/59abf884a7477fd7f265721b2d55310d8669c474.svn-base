/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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


import sun.misc.BASE64Decoder;

/**
 * 描述 http请求服务类
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年12月02日 上午11:22:20
 */
public class HttpRequestUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(HttpRequestUtil.class);
    /**
     * 
     * 向指定URL发送GET方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendGet(String url, String param, String responesCode) {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 
     * 向指定URL发送GET方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, "UTF-8");
    }

    /**
     * 
     * 向指定URL发送POST方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendPost(String url, String param, String responesCode) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流 
            //2016年8月11日 14:53:01 增加输出流编码Rider Chen
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), responesCode));
            // 发送请求参数
            out.print(param);
            
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 
     * 向指定URL发送POST方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendPost(String url, String param) {
        return sendPost(url, param, "UTF-8");
    }
    
    /**
     * 
     * 向指定URL发送GET方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendBdcGet(String url, String param, String responesCode) {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = "";
            if(StringUtils.isNotEmpty(param)){
                urlNameString = url + "?" + param;
            }else{
                urlNameString = url;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                log.info(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 
     * 向指定URL发送GET方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendBdcGet(String url, String param) {
        return sendBdcGet(url, param, "UTF-8");
    }

    /**
     * 
     * 向指定URL发送POST方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendBdcPost(String url, String param, String responesCode) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流 
            //2016年8月11日 14:53:01 增加输出流编码Rider Chen
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), responesCode));
            // 发送请求参数
            out.print(param);
            
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("",e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 
     * 向指定URL发送POST方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendBdcPost(String url, String param) {
        return sendBdcPost(url, param, "UTF-8");
    }

    /**
     * 
     * 向指定URL发送POST方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendFilePost(String url, String param, String filePath, String responesCode) {
        OutputStream out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                return result.toString();
            }
            URL realUrl = new URL(url+"?"+param);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
           // out = new PrintWriter(conn.getOutputStream());
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            out = new DataOutputStream(conn.getOutputStream());
            // 发送请求参数
            out.write(head);
            inFile = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = inFile.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            inFile.close();
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),responesCode));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 
     * 描述 文件上传到文件服务器
     * 
     * @author Rider Chen
     * @created 2019年4月11日 上午11:10:32
     * @param url
     *            上传接口地址
     * @param filePath
     *            上传文件路径
     * @param responesCode
     *            编码
     * @param app_id
     *            文件服务器分配的用户名
     * @param password
     *            文件服务器分配的密码
     * @param param
     *            formData参数
     */
    public static String sendFilePost(String url, String filePath, String responesCode, String app_id, String password,
            Map<String, Object> param) {
        OutputStream out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                return result.toString();
            }
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            String time_stamp = String.valueOf(System.currentTimeMillis());
            String key = "app_id" + app_id + "password" + password + "time_stamp" + time_stamp;
            conn.setRequestProperty("app_id", app_id);
            conn.setRequestProperty("access_token", StringUtil.getMd5Encode(key));
            conn.setRequestProperty("time_stamp", time_stamp);

            // 设置边界
            String BOUNDARY = "----------" + time_stamp;
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            out = new DataOutputStream(conn.getOutputStream());

            //文字形式的post流
            StringBuffer resSB = new StringBuffer();
            resSB.append("--");
            resSB.append(BOUNDARY);
            resSB.append("\r\n");
            if (param != null) {
                Iterator<Map.Entry<String, Object>> iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    resSB.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n")
                            .append("\r\n").append(inputValue).append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                }
            }
            String boundaryMessage = resSB.toString();
            // 写出流
            out.write(boundaryMessage.getBytes("utf-8"));

            // 请求正文信息
            StringBuilder sb = new StringBuilder();
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            
            byte[] head = sb.toString().getBytes("utf-8");
            // 发送请求参数
            out.write(head);

            inFile = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = inFile.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            inFile.close();
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            log.error("", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        }
        return result.toString();
    }

    /**
     * 
     * 描述 base64Code文件上传到文件服务器
     * 
     * @author Rider Chen
     * @created 2019年4月11日 上午11:10:32
     * @param url
     *            上传接口地址
     * @param base64Code
     *            上传文件base64Code
     * @param responesCode
     *            编码
     * @param app_id
     *            文件服务器分配的用户名
     * @param password
     *            文件服务器分配的密码
     * @param param
     *            formData参数
     */
    public static String sendBase64FilePost(String url, String base64Code, String responesCode, String app_id,
            String password, Map<String, Object> param) {
        OutputStream out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            if (StringUtils.isEmpty(base64Code)) {// base64Code为空返回
                return result.toString();
            }
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String time_stamp = String.valueOf(System.currentTimeMillis());
            String key = "app_id" + app_id + "password" + password + "time_stamp" + time_stamp;
            conn.setRequestProperty("app_id", app_id);
            conn.setRequestProperty("access_token", StringUtil.getMd5Encode(key));
            conn.setRequestProperty("time_stamp", time_stamp);

            // 设置边界
            String BOUNDARY = "----------" + time_stamp;
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            out = new DataOutputStream(conn.getOutputStream());

            //文字形式的post流
            StringBuffer resSB = new StringBuffer();
            resSB.append("--");
            resSB.append(BOUNDARY);
            resSB.append("\r\n");
            if (param != null) {
                Iterator<Map.Entry<String, Object>> iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    resSB.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n")
                            .append("\r\n").append(inputValue).append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                }
            }
            String boundaryMessage = resSB.toString();
            // 写出流
            out.write(boundaryMessage.getBytes("utf-8"));

            // 请求正文信息
            StringBuilder sb = new StringBuilder();
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + param.get("name") + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            
            byte[] head = sb.toString().getBytes("utf-8");
            // 发送请求参数
            out.write(head);
            //base64Code转byte数组
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out.write(buffer);//文件输出流

            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            log.error("", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        }
        return result.toString();
    }

    /**
     * 描述:字节流文件上传文件服务器
     *
     * @author Madison You
     * @created 2019/12/2 16:54:00
     * @param
     * @return
     */
    public static String sendByteFilePost(String url, byte[] buffer, String responesCode, String app_id,
                                            String password, Map<String, Object> param) {
        OutputStream out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String time_stamp = String.valueOf(System.currentTimeMillis());
            String key = "app_id" + app_id + "password" + password + "time_stamp" + time_stamp;
            conn.setRequestProperty("app_id", app_id);
            conn.setRequestProperty("access_token", StringUtil.getMd5Encode(key));
            conn.setRequestProperty("time_stamp", time_stamp);

            // 设置边界
            String BOUNDARY = "----------" + time_stamp;
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            out = new DataOutputStream(conn.getOutputStream());

            //文字形式的post流
            StringBuffer resSB = new StringBuffer();
            resSB.append("--");
            resSB.append(BOUNDARY);
            resSB.append("\r\n");
            if (param != null) {
                Iterator<Map.Entry<String, Object>> iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    resSB.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n")
                            .append("\r\n").append(inputValue).append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                }
            }
            String boundaryMessage = resSB.toString();
            // 写出流
            out.write(boundaryMessage.getBytes("utf-8"));

            // 请求正文信息
            StringBuilder sb = new StringBuilder();
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + param.get("name") + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");
            // 发送请求参数
            out.write(head);
            out.write(buffer);//文件输出流

            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            log.error("", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        }
        return result.toString();
    }

    /**
     * 
     * 描述：下载远程文件到本地
     * @author Rider Chen
     * @created 2019年9月2日 下午4:22:52
     * @param httpUrl
     * @param saveFile
     * @return
     */
    public static boolean httpDownload(String httpUrl, String saveFile) {
        int byteread = 0;

        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            log.error("", e1);
            return false;
        }

        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            log.error("", e);
            return false;
        } catch (IOException e) {
            log.error("", e);
            return false;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }

        }
    }
    

    /**
     * 
     * 描述 文件上传到文件服务器
     * 
     * @author Rider Chen
     * @created 2019年4月11日 上午11:10:32
     * @param url
     *            上传接口地址
     * @param file
     *            上传文件
     * @param responesCode
     *            编码
     * @param app_id
     *            文件服务器分配的用户名
     * @param password
     *            文件服务器分配的密码
     * @param param
     *            formData参数
     */
    public static String sendFilePost(String url, File file, String responesCode, String app_id, String password,
            Map<String, Object> param) {
        OutputStream out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String time_stamp = String.valueOf(System.currentTimeMillis());
            String key = "app_id" + app_id + "password" + password + "time_stamp" + time_stamp;
            conn.setRequestProperty("app_id", app_id);
            conn.setRequestProperty("access_token", StringUtil.getMd5Encode(key));
            conn.setRequestProperty("time_stamp", time_stamp);

            // 设置边界
            String BOUNDARY = "----------" + time_stamp;
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            out = new DataOutputStream(conn.getOutputStream());

            // 文字形式的post流
            StringBuffer resSB = new StringBuffer();
            resSB.append("--");
            resSB.append(BOUNDARY);
            resSB.append("\r\n");
            if (param != null) {
                Iterator<Map.Entry<String, Object>> iter = param.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();

                    resSB.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n")
                            .append("\r\n").append(inputValue).append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                }
            }
            String boundaryMessage = resSB.toString();
            // 写出流
            out.write(boundaryMessage.getBytes("utf-8"));

            // 请求正文信息
            StringBuilder sb = new StringBuilder();
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");
            // 发送请求参数
            out.write(head);

            inFile = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = inFile.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            inFile.close();
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            log.error("", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        }
        return result.toString();
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
}
