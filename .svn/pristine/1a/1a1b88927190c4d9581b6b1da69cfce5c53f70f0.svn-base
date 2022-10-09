/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.declare.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述在线申报
 * @author Panda Chen
 * @created 2017年12月5日 上午9:46:58
 */
public class DeclareUtil{
    
    /**
     * Logger log
     */
    private static Logger logger = LoggerFactory.getLogger(DeclareUtil .class);
    
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
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return null;
    }
}
