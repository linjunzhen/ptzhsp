/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.GenPlatReqUtil;

/**
 * 描述 闽政通单点登录跳转
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年5月23日 上午15:21:44
 */
public class MztSample {
    /**
     * logger
     */
    private static Log logger = LogFactory.getLog(GenPlatReqUtil.class);
    /**
     * 获取用户信息接口
     * 
     * @param callerCode
     * @param trustTicket
     */
    public static String getUserInfo(String callerCode, String trustTicket) {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String mztPort = properties.getProperty("MZT_PORT");
        String url = mztPort + "/dataset/AppSerController/invokeservice.do";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("INVOKESERVICE_CODE", "103");
        paramsMap.put("INVOKECALLER_CODE", callerCode);// 授权码
        paramsMap.put("TRUST_TICKET", trustTicket);// 票据
        String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
        Map<String, Object> clientParam = new HashMap<String, Object>();
        clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);
        String result = sendPostParams(url, clientParam);
        return result;
    }
    
    /**
     * token获取用户信息接口
     * 
     * @param token
     * @param userId
     */
    public static String getUserInfoByToken(String token, String userId) {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String callerCode = properties.getProperty("callerCode");
        String mztPort = properties.getProperty("MZT_PORT");
        String url = mztPort + "/dataset/AppSerController/invokeservice.do";
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("INVOKESERVICE_CODE", "033");
        paramsMap.put("INVOKECALLER_CODE", callerCode);// 授权码
        paramsMap.put("USER_ID", userId);// 闽政通用户ID
        paramsMap.put("USER_TOKEN", token);// 闽政通用户TOKEN
        String POSTPARAM_JSON = JSON.toJSONString(paramsMap);
        Map<String, Object> clientParam = new HashMap<String, Object>();
        clientParam.put("POSTPARAM_JSON", POSTPARAM_JSON);
        String result = sendPostParams(url, clientParam);
        return result;
    }

    /**
     * POST参数
     * 
     * @param url
     * @param params
     * @return
     */
    @SuppressWarnings({ "deprecation", "unchecked", "resource", "rawtypes" })
    public static String sendPostParams(String url, Map<String, Object> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            String result = httpclient.execute(httpost, responseHandler).toString();
            httpclient.getConnectionManager().shutdown();
            return result;
        } catch (ClientProtocolException e) {
            logger.info(e);
        } catch (IOException e) {
            logger.info(e);
        }
        return null;
    }
}
