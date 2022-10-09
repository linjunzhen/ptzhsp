/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.StringUtil;

/**
 * 描述 jssdk工具类
 * @author Laura Song
 */
public class JSSDKHelper {
    /**
     * log4J声明
     */
    private static Logger log = LoggerFactory.getLogger(JSSDKHelper.class);
    
    /**
     * 描述 获取JsapiTicket
     * @author Laura Song
     * @param access_token
     * @return
     */
    public static String getJsapiTicket(String access_token){
        // 设置url 参数等
        //Properties properties = FileUtil.readProperties("conf/config.properties");
        //String appId=properties.getProperty("APP_ID");
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        String param = "access_token="+access_token+ "&type=jsapi";
        
        String jsonStr = HttpRequestUtil.sendGet(url, param);
        Map<String, Object> map = JsonUtil.parseJSON2Map(jsonStr);
        String jsapi_ticket = (String) map.get("ticket");
        return jsapi_ticket;
    }
    
    /**
     * 获取随机字符串
     * @author Laura Song
     * @return
     */
    public static String getNoncestr() {
        String currT = getCurrTime();
        String strT = currT.substring(8, currT.length());
        String strRandom = buildRandom(4)+"";
        return strT + strRandom;
    }
    
    /**
     * 生成随机数
     * @author Laura Song
     * @param length
     * @return
     */
    public static int buildRandom(int length){
        int mm = 1;
        double random = Math.random();
        if(random < 0.1){
            random = random + 0.1;
        }
        for(int i=0; i<length; i++){
            mm = mm*10;
        }
        return (int)(random*mm);
    }
    
    /**
     * 获取当前时间
     * 描述
     * @author Laura Song
     * @return
     */
    public static String getCurrTime(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = df.format(date);
        return s;
    }
    
    /**
     * 获取时间戳
     * @author Laura Song
     * @return
     */
    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis()/1000);
    }
    
    /**
     * 生成签名
     * @author Laura Song
     * @param params
     * @return
     */
    public static String createSignBySha1(SortedMap<Object, Object> params){
        StringBuffer sb = new StringBuffer();
        Set es = params.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()){
            Map.Entry entry = (Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if(v!=null && !v.equals("")){
                sb.append(k+"="+v+"&");
            }
        }
        String result = sb.toString().substring(0, 
                sb.toString().length()-1);
        return getSHA1(result);
    }
    
    /**
     * 描述 SHA1签名生成
     * @author Laura Song
     * @param str
     * @return
     */
    public static String getSHA1(String str){
        StringBuffer hexstr = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            String shaHex = "";
            for(int i=0; i<digest.length; i++){
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if(shaHex.length() < 2){
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
        }catch(NoSuchAlgorithmException e){
            log.error("", e);
        }
        return hexstr.toString();
    }
    
    
}
