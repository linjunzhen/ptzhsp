/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpClientUtil;

/**
 * 描述 获取接口的认证token
 * 
 * @author Keravon Feng
 * @created 2019年2月20日 下午3:47:57
 */
public class TokenUtil {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(TokenUtil.class);

    /**
     * 描述 获取TOKEN信息
     * 
     * @author Keravon Feng
     * @created 2019年2月20日 下午3:48:38
     * @return
     */
    public static String reLoadToken() {
        String result = "false";
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("fjfdaURL");
        String id = properties.getProperty("ID");
        String secret = properties.getProperty("SECRET");
        CloseableHttpClient httpClient = HttpClientUtil.getPlainHttpClient();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("id", id);
        paramsMap.put("secret", secret);
        try {
            Map<String, Object> json = doGetMap(httpClient, url + "service/getToken.do", paramsMap);
            if (json != null && "true".equals(String.valueOf(json.get("result")))) {
                Integer expires_in = Integer.valueOf(String.valueOf(json.get("expires_in")));
                //失效的时间搓
                Long expire = new Date().getTime() + expires_in * 1000 - 1000 * 100;
                Map<String, Object> jsonkey = new HashMap<String, Object>();
                jsonkey.put("expire", expire);
                jsonkey.put("token", json.get("token"));
                AppUtil.getServletContext().setAttribute("fjfda_token", JSON.toJSONString(jsonkey));
                result = JSON.toJSONString(jsonkey);
            } else {
                AppUtil.getServletContext().setAttribute("fjfda_token", "false");
                result = "false";
            } 
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e)); 
        }
        return result;
    }

    /**
     * 描述 getToken
     * @author Keravon Feng
     * @created 2019年2月26日 下午5:30:01
     * @return
     */
    public static String getToken() {
        String result = null;
        Object obj = null;
        try {
            obj = AppUtil.getServletContext().getAttribute("fjfda_token");
            if (obj != null && !"false".equals(String.valueOf(obj))) {
                result = String.valueOf(obj);
                Long curr = new Date().getTime();
                JSONObject json = JSON.parseObject(result);
                Long expire = json.getLong("expire");
                if((curr+60*1000) > expire){
                    result = reLoadToken();
                }
            }else{
                result = reLoadToken();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e)); 
        }
        return result;
    }

    /**
     * 描述 Post方法
     * @author Keravon Feng
     * @created 2019年2月28日 上午10:42:33
     * @param postUrl
     * @param paramsMap
     * @return
     */
    public static Map<String, Object> doPost(String postUrl,
            Map<String, Object> paramsMap){
        CloseableHttpClient httpClient = HttpClientUtil.getPlainHttpClient();
        return doPostJson(httpClient,postUrl,paramsMap);
    }
    
    /**
     * 描述 
     * @author Keravon Feng
     * @created 2019年3月4日 下午3:05:32
     * @param getUrl
     * @param paramsMap
     * @return
     */
    public static Map<String, Object> doGet(String getUrl,
            Map<String, Object> paramsMap){
        CloseableHttpClient httpClient = HttpClientUtil.getPlainHttpClient();
        return doGetMap(httpClient,getUrl,paramsMap);
    }
    /**
     * 描述 PostJSON方法
     * 
     * @author Keravon Feng
     * @created 2019年2月20日 下午5:01:47
     * @param httpClient
     * @param postUrl
     * @param params
     * @return
     */
    public static Map<String, Object> doPostJson(CloseableHttpClient httpClient, String postUrl,
            Map<String, Object> paramsMap) {
        Map<String, Object> respJson = null;
        HttpPost httpPost = new HttpPost(postUrl);
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (paramsMap != null) {
                for (Entry<String, Object> entry : paramsMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
            }
            // 设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            // 使用ResponseHandler接口,处理http响应
            ResponseHandler<Map<String, Object>> rh = new ResponseHandler<Map<String, Object>>() {
                @Override
                public Map<String, Object> handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() >= 300) {
                        throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                    }
                    HttpEntity entity = response.getEntity();
                    // 无响应内容
                    if (entity == null) {
                        throw new ClientProtocolException("Response contains no content");
                    }
                    // 读取并处理响应流
                    BufferedReader in = null;
                    StringBuffer buffer = new StringBuffer();
                    try {
                        in = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                        String line = "";
                        while ((line = in.readLine()) != null) {
                            buffer.append(line);
                        }
                    } catch (IOException e) {
                        log.error(ExceptionUtils.getStackTrace(e));
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Exception e2) {
                                log.error(ExceptionUtils.getStackTrace(e2));
                            }
                        }
                    }
                    return JSON.parseObject(buffer.toString(), Map.class);
                }
            };
            respJson = httpClient.execute(httpPost, rh);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        //token 过期重新刷新一下token
        if(respJson != null && "false".equals(String.valueOf(respJson.get("result")))){
            if(respJson.get("errcode") != null && ("40".equals(String.valueOf(respJson.get("errcode"))) 
                    || "10".equals(String.valueOf(respJson.get("errcode"))))){ 
                reLoadToken();
            }
        }
        return respJson;
    }

    /**
     * 描述 get 方式请求
     * 
     * @author Keravon Feng
     * @created 2019年2月21日 上午9:23:10
     * @param httpClient
     * @param postUrl
     * @param params
     * @return
     */
    public static Map<String, Object> doGetMap(CloseableHttpClient httpClient, String url,
            Map<String, Object> paramsMap) {
        Map<String, Object> respJson = null;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Entry<String, Object> entry : paramsMap.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        try {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters(pairs);
            HttpGet get = new HttpGet(builder.build());
            // 设置请求头
            get.addHeader(HTTP.CONTENT_TYPE, "application/json");
            // 使用ResponseHandler接口,处理http响应
            ResponseHandler<Map<String, Object>> rh = new ResponseHandler<Map<String, Object>>() {
                @Override
                public Map<String, Object> handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    StatusLine statusLine = response.getStatusLine();
                    if (statusLine.getStatusCode() >= 300) {
                        throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                    }
                    HttpEntity entity = response.getEntity();
                    // 无响应内容
                    if (entity == null) {
                        throw new ClientProtocolException("Response contains no content");
                    }
                    // 读取并处理响应流
                    BufferedReader in = null;
                    StringBuffer buffer = new StringBuffer();
                    try {
                        in = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                        String line = "";
                        while ((line = in.readLine()) != null) {
                            buffer.append(line);
                        }
                    } catch (IOException e) {
                        log.error(ExceptionUtils.getStackTrace(e));
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Exception e2) {
                                log.error(ExceptionUtils.getStackTrace(e2));
                            }
                        }
                    }
                    return JSON.parseObject(buffer.toString(), Map.class);
                }
            };
            respJson = httpClient.execute(get, rh);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        //token 过期重新刷新一下token
        if(respJson != null && "false".equals(String.valueOf(respJson.get("result")))){
            if(respJson.get("errcode") != null && ("40".equals(String.valueOf(respJson.get("errcode"))) 
                    || "10".equals(String.valueOf(respJson.get("errcode"))))){ 
                reLoadToken();
            }
        }
        return respJson;
    }

    public static Map<String, Object> uploadFile(String localFile) {
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("fjfdaURL");
        Map<String, Object> respJson = new HashMap<String, Object>();
        respJson.put("success", false);
        String url_str = url + "service/uploadFile.do";
        File file = new File(localFile);
        PostMethod filePost = new PostMethod(url_str);
        HttpClient client = new HttpClient();
        try {
            Part[] parts = { new FilePart(file.getName(), file) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK) {
                respJson.put("success", true);
                respJson.put("msg", "上传成功。");
            } else {
                respJson.put("success", false);
                respJson.put("msg", "上传失败。");
            }
        } catch (Exception ex) {
            respJson.put("msg", "系统异常!");
            log.error(ExceptionUtils.getStackTrace(ex));
        } finally {
            filePost.releaseConnection();
        }
        return respJson;
    }
}
