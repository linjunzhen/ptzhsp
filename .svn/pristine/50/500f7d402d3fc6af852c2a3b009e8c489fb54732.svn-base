/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.mas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.evecom.platform.call.controller.AppointmentController;

/**
 * 
 * 描述 12345短信
 * @author Kester Chen
 * @created 2020年2月1日 下午1:08:12
 */
public class SMSClient {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SMSClient.class);

    /**
     * 
     */
    private static String apId = "ptzhsp";
    /**
     * 
     */
    private static String secretKey = "evecom@123";
    /**
     * 
     */
    private static String ecName = "平潭综合实验区社会服务管理指挥中心";//集团名称
    /**
     * 
     */
    private static String sign = "0A5LDapsR";//网关签名编码
    /**
     * 
     */
    private static String addSerial = "";//拓展码 填空
    /**
     * 
     */
    public static String msg = "这是发送短信的内容！";
    /**
     * 
     */
    public static String url = "http://112.35.1.155:1992/sms/norsubmit";//请求url

    public static int sendMsg(String mobiles, String content) {
        SendReq sendReq = new SendReq();
        sendReq.setApId(apId);
        sendReq.setEcName(ecName);
        sendReq.setSecretKey(secretKey);
        sendReq.setContent(content);
        sendReq.setMobiles(mobiles);
        sendReq.setAddSerial(addSerial);
        sendReq.setSign(sign);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sendReq.getEcName());
        stringBuffer.append(sendReq.getApId());
        stringBuffer.append(sendReq.getSecretKey());
        stringBuffer.append(sendReq.getMobiles());
        stringBuffer.append(sendReq.getContent());
        stringBuffer.append(sendReq.getSign());
        stringBuffer.append(sendReq.getAddSerial());

        sendReq.setMac(Md5Util.mD5(stringBuffer.toString()).toLowerCase());

        String reqText = obj2json(sendReq);
        String encode;
        int result = 0;
        try {
            encode = Base64.encodeBase64String(reqText.getBytes("utf-8"));
    
            String resStr = sendPost(url, encode);
    
            //System.out.println("发送短信结果：" + resStr);
            log.info("发送短信结果：" + resStr);
    
            SendRes sendRes = json2pojo(resStr, SendRes.class);    
            if (sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())) {
                result = 1;
            } else {
                result = 0;
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    /**
     * 
     */
    private final static ObjectMapper OBJECTMAPPER = new ObjectMapper();
    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {

        try {
            return OBJECTMAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
        try {
            return OBJECTMAPPER.readValue(jsonStr, clazz);
        } catch (JsonParseException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    /**
     * main方法测试发送短信，返回1表示成功，0表示失败
     */
    /*public static void main(String[] args) {
        int code = (int) (Math.random() * 8998) + 1000 + 1;
        int result = sendMsg("15806036557", String.valueOf(code));
        System.out.println(result);
    }*/

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    private static String sendPost(String url, String param) {
        OutputStreamWriter out = null;

        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(param);
            out.flush();


            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("\n").append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return result.toString();
    }

}
