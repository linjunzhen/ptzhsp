/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.evecom.core.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 描述 调用图灵机器人api接口，获取智能回复内容
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class TulingApiProcess {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(TulingApiProcess.class);
    /** 
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果 
     * @param content 
     * @return 
     */  
    public String getTulingResult(String content){  
        /** 此处为图灵api接口 */  
        String apiUrl = "http://www.tuling123.com/openapi/api?key=75c16de3e53b6f10fa6aceda1ed10c48&info=";  
        String param = "";  
        try {  
            param = apiUrl+URLEncoder.encode(content,"utf-8");  
        } catch (UnsupportedEncodingException e1) {  
            log.error(e1.getMessage());  
        } //将参数转为url编码  
          
        /** 发送httpget请求 */  
        HttpGet request = new HttpGet(param);  
        String result = "";  
        try {  
            HttpResponse response = HttpClients.createDefault().execute(request);  
            if(response.getStatusLine().getStatusCode()==200){  
                result = EntityUtils.toString(response.getEntity());  
            }  
        } catch (ClientProtocolException e) {  
            log.error(e.getMessage());  
        } catch (IOException e) {  
            log.error(e.getMessage());  
        }  
        /** 请求失败处理 */  
        if(null==result){  
            return "对不起，你说的话真是太高深了……";  
        }  
          
        try {  
            JSONObject json = new JSONObject(result);  
            //以code=100000为例，参考图灵机器人api文档  
            if(100000==json.getInt("code")){  
                result = json.getString("text");  
            }  
        } catch (JSONException e) {  
            // TODO Auto-generated catch block  
            log.error(e.getMessage());  
        }  
        return result;  
    }  
}
