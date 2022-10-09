/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
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

import net.evecom.core.web.tag.ExRadioGroup;

/**
 * @author Flex Hu
 * 统一支撑平台请求工具类
 * 
 */
public class GenPlatReqUtil {
    /**
     * logger
     */
    private static Log logger = LogFactory.getLog(GenPlatReqUtil.class);
    
    /**
     * POST参数
     * @param url
     * @param params
     * @return
     */
    public static String sendPostParams(String url,Map<String,Object> params){
        DefaultHttpClient httpclient = new DefaultHttpClient(
                new ThreadSafeClientConnManager());
        HttpPost httpost = new HttpPost(url);
        BasicResponseHandler responseHandler = new BasicResponseHandler();
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String,Object> entry = (Entry<String,Object>) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(),
                        (String) entry.getValue())); 
            }
        }
        httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        try {
            String result =  httpclient.execute(httpost,responseHandler).toString();
            httpclient.getConnectionManager().shutdown();
            return result;
        } catch (ClientProtocolException e) {
//            e.printStackTrace();
            logger.info(e);
        } catch (IOException e) {
//            e.printStackTrace();
            logger.info(e);
        }
        return null;
    }
    
    /**
     * 获取单条结果记录
     * @param serviceCode
     * @param params
     * @return
     */
    public static Map getData(String serviceCode,Map<String,Object> params){
        List<Map> list = findDatas(serviceCode,params);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 获取请求的URL地址
     * @param serviceCode
     * @return
     */
    public static String getSendUrl(String serviceCode){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String url =properties.getProperty("genplatrequrl");
        url+="?servicecode="+serviceCode;
        return url;
    }
    
    /**
     * 获取列表数据
     * @param serviceCode
     * @param params
     * @return
     */
    public static List<Map> findDatas(String serviceCode,Map<String,Object> params){
        Map<String,Object> postParam = new HashMap<String,Object>();
        postParam.put("grantcode", "BFbhXSKZXvjii1bMaLWg");
        String url = getSendUrl(serviceCode);
        if(params!=null&&params.size()>0){
            postParam.putAll(params);
        }
        String result = sendPostParams(url, postParam);
        if(StringUtils.isNotEmpty(result)){
            Map<String,Object> datas = JSON.parseObject(result,Map.class);
            Boolean success = (Boolean) datas.get("success");
            if(success){
                List<Map> list = (List<Map>) datas.get("datas");
                return list;
            }else{
                return new ArrayList<Map>();
            }
        }else{
            return new ArrayList<Map>();
        }
    }
}
