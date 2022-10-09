/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 描述
 * @author Toddle Chen
 * @created 2018年4月17日 上午9:39:44
 */
public class HttpSendUtil {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(HttpSendUtil.class);
    /**
     * 描述
     * @author Toddle Chen
     * @created 2018年9月27日 上午11:30:25
     * @param url
     * @param params
     * @return
     */
    public static String sendPostParams(String url, Map<String, Object> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            return httpClient.execute(httpost, responseHandler).toString();
        } catch (Exception e) {
            log.error(url);
            log.error(e.getMessage(),e);
        }finally{
            if(httpost != null){
                httpost.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    /**
     * 描述:带请求头的post请求
     *
     * @author Madison You
     * @created 2019/12/13 15:42:00
     * @param
     * @return
     */
    public static String sendPostParamsH(String url, Map<String,String> headMap, Map<String, Object> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        httpost.addHeader("Content-type", "application/x-www-form-urlencoded");
        if(headMap != null){
            for(Entry<String, String> head : headMap.entrySet()){//设置报文头参数
                httpost.setHeader(head.getKey(), head.getValue());
            }
        }
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            return httpClient.execute(httpost, responseHandler).toString();
        } catch (Exception e) {
            log.error(url);
            log.error(e.getMessage(),e);
        }finally{
            if(httpost != null){
                httpost.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return null;
    }
    
    /**
     * 描述
     * @author Toddle Chen
     * @created 2018年9月27日 上午11:14:58
     * @param url
     * @param infoJson
     * @param charset
     * @return
     */
    public static String sendHttpsPostJson(String url, Map<String,String> headMap, String infoJson, String charset){
        HttpPost httpPost = null;
        String result = null;
        SSLClient httpClient = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json;charset=" + charset);
            httpPost.setHeader("Accept", "application/json");
            if(headMap != null){
                for(Entry<String, String> head : headMap.entrySet()){//设置报文头参数
                    httpPost.setHeader(head.getKey(), head.getValue());
                }
            }
            httpPost.setEntity(new org.apache.http.entity.StringEntity(infoJson, Charset.forName(charset)));
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
            httpClient.close();
        } catch (Exception e) {
            log.error(url);
            log.error(e.getMessage(),e);
        }finally{
            if(httpPost!=null){
                httpPost.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }

    /**
     * 描述:发送http请求带json参数和请求头
     *
     * @author Madison You
     * @created 2020/6/18 18:22:00
     * @param
     * @return
     */
    public static String sendHttpPostJson(String url, Map<String, String> headMap, String infoJson, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json;charset=" + charset);
            httpPost.setHeader("Accept", "application/json");
            if (headMap != null) {
                for(Entry<String, String> head : headMap.entrySet()){//设置报文头参数
                    httpPost.setHeader(head.getKey(), head.getValue());
                }
            }
            httpPost.setEntity(new org.apache.http.entity.StringEntity(infoJson, Charset.forName(charset)));
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
            httpClient.close();
        } catch (Exception e) {
            log.error(url);
            log.error(e.getMessage(), e);
        } finally {
            if(httpPost!=null){
                httpPost.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }

    /**
     * 描述:发送http请求带json参数和请求头的put请求
     *
     * @author Madison You
     * @created 2020/6/18 18:22:00
     * @param
     * @return
     */
    public static String sendHttpPutJson(String url, Map<String, String> headMap, String infoJson, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPut httpPut = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            httpPut = new HttpPut(url);
            httpPut.addHeader("Content-type", "application/json;charset=" + charset);
            httpPut.setHeader("Accept", "application/json");
            if (headMap != null) {
                for(Entry<String, String> head : headMap.entrySet()){//设置报文头参数
                    httpPut.setHeader(head.getKey(), head.getValue());
                }
            }
            httpPut.setEntity(new org.apache.http.entity.StringEntity(infoJson, Charset.forName(charset)));
            HttpResponse response = httpClient.execute(httpPut);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
            httpClient.close();
        } catch (Exception e) {
            log.error(url);
            log.error(e.getMessage(), e);
        } finally {
            if(httpPut!=null){
                httpPut.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }
    
    /**
     * 发送https请求
     * 
     * @param url
     * @param params
     * @param sslClient
     * @return
     */
    public static String sendHttpsPostParams(String url, Map<String,String> headMap, Map<String, Object> params, 
        String charset) {
        HttpPost httpPost = null;
        String result = null;
        SSLClient httpClient = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json;charset=" + charset);
            httpPost.setHeader("Accept", "application/json");
            if(headMap != null){
                for(Entry<String, String> head : headMap.entrySet()){//设置报文头参数
                    httpPost.setHeader(head.getKey(), head.getValue());
                }
            }
            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
            httpClient.close();
        } catch (Exception e) {
            log.error(url);
            log.error(e.getMessage(),e);
        }finally{
            if(httpPost!=null){
                httpPost.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return result;
    }
    
    /**
     * 描述 第三方特殊调用https
     * @author Toddle Chen
     * @created 2018年9月27日 上午11:39:24
     * @param httpUrl
     * @param headMap
     * @param entity
     * @return
     */
    public static Map<String, Object> sendHttpsPostThird(String httpUrl, Map<String,String> headMap, String entity){
        HttpPost httpPost = null;
        SSLClient httpClient = null;
        Map<String,Object> resMap = new HashMap<String,Object>();
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(httpUrl);
            httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("Accept", "application/json");
            if(headMap != null){
                for(Entry<String, String> head:headMap.entrySet()){//设置报文头参数
                    httpPost.setHeader(head.getKey(), head.getValue());
                }
            }
            httpPost.setEntity(new ByteArrayEntity(entity.getBytes(),
                ContentType.create("application/octet-stream", "UTF-8")));
            //发起请求
            HttpResponse response = httpClient.execute(httpPost);
            resMap.put("headers", response.getAllHeaders());
            HttpEntity resEntity = response.getEntity();
            if (entity != null) {
                resMap.put("bodyBytes", EntityUtils.toByteArray(resEntity));
            }
            httpPost.releaseConnection();
            httpClient.close();
        }catch (Exception e) {
            log.error(httpUrl);
            log.error(e.getMessage(),e);
        }finally{
            if(httpPost!=null){
                httpPost.abort();
            }
            if(httpClient!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }
        return resMap;
    }

    /**
     * 描述:发送字节码文件
     *
     * @param
     * @return
     * @author Madison You
     * @created 2021/3/16 16:00:00
     */
    public static String sendByteFilePost(String url, byte[] buffer, String responesCode,
                                          Map<String, String> headMap, Map<String, Object> param) {
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
            conn.setRequestProperty("time_stamp", time_stamp);

            // 设置边界
            String BOUNDARY = "----------" + time_stamp;
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            if (headMap != null && !headMap.isEmpty()) {
                Set<String> headMapKeySet = headMap.keySet();
                for (String headMapKey : headMapKeySet) {
                    conn.setRequestProperty(headMapKey, headMap.get(headMapKey));
                }
            }
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
}
