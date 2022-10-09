/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.com.flaginfo.sdk.cmc.api.ApiProvider;
import cn.com.flaginfo.sdk.cmc.api.request.ApiConfig;
import cn.com.flaginfo.sdk.cmc.api.result.ComResult;
import cn.com.flaginfo.sdk.cmc.api.sms.send.SMSApi;
import cn.com.flaginfo.sdk.cmc.api.sms.send.SMSSendDataResult;
import cn.com.flaginfo.sdk.cmc.api.sms.send.SMSSendRequest;
import cn.com.flaginfo.sdk.cmc.common.ApiEnum;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述 短信发送工具类
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年11月23日 上午09:11:00
 */
@Component
public class SmsSendUtil  {
    
    /**
     * logger
     */
    private static Log logger = LogFactory.getLog(SmsSendUtil.class);
    /**
     * 字典表service
     */
    @Autowired
    private  DictionaryService dictionaryService;
   /**
    * 短信发送工具类
    */
    private static SmsSendUtil smsSendUtil;
    /**
     * 短信发送api
     */
    private static ApiProvider provider;
    /**
     * 联通短信发送接口地址
     */
    private static String sendServer = "http://api.ums86.com";
/**
 * 
 * 描述 初始化化service已便于在静态方法中调用
 * @author Yanisin Shi
 */
    @PostConstruct
    public void init(){
        smsSendUtil = this;
        smsSendUtil.dictionaryService=this.dictionaryService;
    }

    /**
     * 描述:md5加密
     *
     * @author Madison You
     * @created 2020/11/23 9:24:00
     * @param
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String makeMD5(String plainText) throws UnsupportedEncodingException {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
        return re_md5;
    }

    /**
     * 描述:发送短信
     *
     * @author Madison You
     * @created 2020/11/23 10:21:00
     * @param
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static Map<String,Object> sendSms(String msg , String mobiles , String templetId) throws UnsupportedEncodingException {
        Properties properties = FileUtil.readProperties("project.properties");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
        Map<String,Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("mobiles", mobiles);
        params.put("templetId", templetId);
        String result = HttpSendUtil.sendPostParams(forwardUrl, params);
        if (StringUtils.isNotEmpty(result)) {
            returnMap = JSON.parseObject(result, Map.class);
        }
        return returnMap;
    }
    /**
     * 
     * 描述   联通短信接口（新）
     * @author Yanisin Shi
     * @param msg
     * @param mobiles
     * @param templetId
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String,Object> sendSmsNew(String msg , String mobiles , String templetId) throws UnsupportedEncodingException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
         String content="";
         //全量模板单独判断
        if("282223".equals(templetId)){
            content=msg;
        }else{
            //根据模板id获取模板内容
            String templete=smsSendUtil.dictionaryService.getDicCode(templetId,"msgTemplate");
            if(templete!=null){
                content = MessageFormat.format(templete, msg.split(","));
            }
        }
       //调用联通短信发送接口
       // ApiConfig apiConfig = new ApiConfig("您的企业编号", "用户名", "秘钥");
        ApiConfig apiConfig = new ApiConfig("265402", "fz_pixz", "f7d100ee1de4ff896192820835ca469a");
        provider = ApiProvider.getInstance(apiConfig);
        //请求接口
        SMSApi api = (SMSApi) provider.getApi(ApiEnum.SENDSMS);
        api.setRequestUrl(sendServer + "/api/sms/send");
        //请求参数
        SMSSendRequest sendRequest = new SMSSendRequest();
        sendRequest.setMessageContent(content);
        sendRequest.setTemplateId("0");
        sendRequest.setUserNumber(mobiles);
        //获取当前时间
        sendRequest.setSerialNumber(""+DateTimeUtil.getNowTime("yyyyMMddhhmmss")+new Date().getTime());
        ComResult<SMSSendDataResult> request = api.request(sendRequest);
        String result=JSON.toJSONString(request);
        if (StringUtils.isNotEmpty(result)) {
            returnMap = JSON.parseObject(result, Map.class);
            if(returnMap.get("code") != null){
                returnMap.put("resultcode",returnMap.get("code"));
            }
        }
        return returnMap;
    }
}
    