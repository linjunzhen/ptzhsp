/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.util;

import net.evecom.platform.zzhy.model.AddrPara;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公安地址接口查询工具类
 * @author Flex Hu
 * @version 1.0
 * @created 2020年9月11日 上午9:13:33
 */
public class AddrUtils {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(AddrUtils.class);
    /**
     *
     */
    protected static MessageDigest messageDigest = null;

    public static String findAddrInfo(String jsonData){
        log.info("商事秒批内资公安地址接口查询请求数据："+jsonData);
        String url=AddrPara.ADDR_URL;
        String appid=AddrPara.ADDR_APP_ID;
        String appsecre=AddrPara.ADDR_SECRET_KEY;
        String aes_pswd=AddrPara.ADDR_AES_PSWD;
        //加密后的json串
        String json = AESUtil.encryptStr(jsonData, aes_pswd);
        String result = httpPOST2(url,json,appid,appsecre,AddrPara.A07_SERVICE_ID,AddrPara.A07_SERVICE_ID);
        log.info("商事秒批内资公安地址接口查询返回数据："+result);
        //返回结果
        return result;
    }

//    public static void main(String[] args) {
//        String jsonData="{\"pages\":[{\"pno\":\"3\",\"psize\":\"15\",\"tcount\":\"1\",\"tsize\":\"1\"}],\"datas\":[{\"dsbm\":\"350100\",\"ALL_FULL_ADDR\":\"世界城一期 11\"}]}";
//        System.out.println(findAddrInfo(jsonData));
//    }
    /**
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        }catch (Exception e) {
            return str;
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString().toUpperCase();
    }

    /**
     *
     * @param url
     * @param json
     * @param appid
     * @param secre
     * @return
     */
    private static String httpPOST2(String url, String json,String appid,String secre,String serviceId,String  serviceValue){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("appid", appid);
        String currdate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String token = md5(appid+secre+currdate+json.replaceAll("\r\n", ""));
        long epoch = System.currentTimeMillis()/1000;
        String tranId = String.valueOf(epoch);
        post.setHeader("token", token);
        post.setHeader("tranId", tranId);
        post.setHeader("serviceId", serviceId);
        post.setHeader("serviceValue", serviceValue);
        post.setHeader("versionCode", "");
        String body = "";
        try {
            StringEntity s = new StringEntity(json,"UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity, "utf-8");
            }
            String result=URLDecoder.decode(body,"utf-8");
            return  result;
        } catch (Exception e) {
           log.error("",e);
        }
        return "";
    }
}
