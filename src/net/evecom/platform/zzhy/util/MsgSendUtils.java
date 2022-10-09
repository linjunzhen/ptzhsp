/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.evaluate.utils.Md5Util;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.MsgConfig;
import net.evecom.platform.zzhy.model.MsgConfigFactory;
import net.evecom.platform.zzhy.model.SendReq;
import net.evecom.platform.zzhy.model.SendRes;
import org.apache.axis.encoding.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 中国移动http短信发送工具类
 * 
 * @author Water Guo
 * @version 1.0
 * @created 2020年12月11日 上午9:13:33
 */
public class MsgSendUtils {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(MsgSendUtils.class);

    /**
     * 多用户发送短信信息(更改为联通短信通道)
     * 
     * @param mobiles
     *            手机号码逗号分隔
     * @param content
     *            短信内容
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static boolean sendMsg(String mobiles, String content) {
        if (StringUtils.isEmpty(mobiles) || StringUtils.isEmpty(content)) {
            return false;
        }
        boolean sendSuccess = false;
        Properties properties = FileUtil.readProperties("conf/messageConfig.properties");
        String templetId = properties.getProperty("messageTemplate2");

        String[] mobileArray = mobiles.split(",");
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        Map<String, Object> msgInfo = Maps.newHashMap();
        msgInfo.put("MSG_INFO", content);
        msgInfo.put("ADD_SERIAL", "");
        msgInfo.put("SEND_STATUS", "1");
        for (String mobile : mobileArray) {
            try {
                SmsSendUtil.sendSms(content.toString(), mobile, templetId);
                sendSuccess = true;
                msgInfo.put("SEND_SUCCESS", 1);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                log.error("", e);
                sendSuccess = false;
                msgInfo.put("SEND_SUCCESS", 0);
            }
            msgInfo.put("RECEIVER_MOB", mobile);
            dictionaryService.saveOrUpdate(msgInfo, "T_MESSAGE_INFO", "");

        }
        return sendSuccess;
    }

    /**
     * 多用户发送短信信息
     * 
     * @param mobiles
     *            手机号码逗号分隔
     * @param content
     *            短信内容
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static boolean sendMsgToYd(String mobiles, String content) {
        if (StringUtils.isEmpty(mobiles) || StringUtils.isEmpty(content)) {
            return false;
        }
        boolean sendSuccess;
        // 初始化短信配置参数
        MsgConfig msgConfig = MsgConfigFactory.getMsgConfig();
        SendReq sendReq = new SendReq();
        sendReq.setApId(msgConfig.getUserAccount());
        sendReq.setEcName(msgConfig.getEcName());
        sendReq.setSecretKey(msgConfig.getPassword());
        sendReq.setContent(content);
        sendReq.setMobiles(mobiles);
        sendReq.setAddSerial(sendReq.getAddSerial());
        sendReq.setSign(msgConfig.getMsgSign());
        // Mac加密
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sendReq.getEcName());
        stringBuffer.append(sendReq.getApId());
        stringBuffer.append(sendReq.getSecretKey());
        stringBuffer.append(sendReq.getMobiles());
        stringBuffer.append(sendReq.getContent());
        stringBuffer.append(sendReq.getSign());
        stringBuffer.append(sendReq.getAddSerial());

        sendReq.setMac(Md5Util.md5(stringBuffer.toString()).toLowerCase());
        String reqText = JSON.toJSONString(sendReq);
        // 发送前参数
        String encode = null;
        try {
            encode = Base64.encode(reqText.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        // 返回结果信息
        String resStr = HttpSendUtil.sendHttpPostJson(msgConfig.getMsgUrl(), Maps.newHashMap(), encode, "UTF-8");
        SendRes sendRes = JSON.parseObject(resStr, SendRes.class);
        if (sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())) {
            log.info(String.format("mobiles=====%s,content====%s,MsgGroup===%s,Rspcod=====%s", mobiles, content,
                    sendRes.getMsgGroup(), sendRes.getRspcod()));
            sendSuccess = true;
        } else {
            log.error(String.format("mobiles=====%s,content====%s,MsgGroup===%s,Rspcod=====%s", mobiles, content,
                    sendRes.getMsgGroup(), sendRes.getRspcod()));
            sendSuccess = false;
        }
        // 存储到数据库短信信息表
        Map<String, Object> msgInfo = Maps.newHashMap();
        msgInfo.put("MSG_INFO", content);
        msgInfo.put("ADD_SERIAL", sendReq.getAddSerial());
        msgInfo.put("SEND_STATUS", "1");
        msgInfo.put("SEND_SUCCESS", sendSuccess);
        String[] mobileArray = mobiles.split(",");
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        for (String mobile : mobileArray) {
            msgInfo.put("RECEIVER_MOB", mobile);
            dictionaryService.saveOrUpdate(msgInfo, "T_MESSAGE_INFO", "");
        }
        return sendSuccess;
    }

    /**
     * 多用户发送短信信息(互联网短信转发功能)
     * 
     * @param mobiles
     *            手机号码逗号分隔
     * @param content
     *            短信内容
     * @throws IOException
     */
    public static boolean sendSmsTypeTwo(String mobiles, String content) {
        try {
            Properties properties = FileUtil.readProperties("project.properties");
            String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
            if (StringUtils.isEmpty(mobiles) || StringUtils.isEmpty(content)) {
                return false;
            }
            ;
            boolean sendSuccess;
            // 初始化短信配置参数
            MsgConfig msgConfig = MsgConfigFactory.getMsgConfig();
            SendReq sendReq = new SendReq();
            sendReq.setAddSerial(sendReq.getAddSerial());
            Map<String, Object> variables = new HashMap<>();
            variables.put("apId", msgConfig.getUserAccount());
            variables.put("ecName", msgConfig.getEcName());
            variables.put("secretKey", msgConfig.getPassword());
            variables.put("content", content);
            variables.put("mobiles", mobiles);
            variables.put("addSerial", sendReq.getAddSerial());
            variables.put("sign", msgConfig.getMsgSign());

            Map<String, Object> params = new HashMap<>();
            params.put("url", msgConfig.getMsgUrl());
            params.put("method", "sendHttpPostJson");
            params.putAll(variables);
            JSONObject json = new JSONObject(params);
            log.info("发送定时器轮询短信，参数为" + json);
            // 返回结果信息
            String resStr = HttpSendUtil.sendPostParams(forwardUrl, params);
            SendRes sendRes = JSON.parseObject(resStr, SendRes.class);
            if (sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())) {
                log.info(String.format("mobiles=====%s,content====%s,MsgGroup===%s,Rspcod=====%s", mobiles, content,
                        sendRes.getMsgGroup(), sendRes.getRspcod()));
                sendSuccess = true;
            } else {
                log.error(String.format("mobiles=====%s,content====%s,MsgGroup===%s,Rspcod=====%s", mobiles, content,
                        sendRes.getMsgGroup(), sendRes.getRspcod()));
                sendSuccess = false;
            }
            // 存储到数据库短信信息表
            Map<String, Object> msgInfo = Maps.newHashMap();
            msgInfo.put("MSG_INFO", content);
            msgInfo.put("ADD_SERIAL", sendReq.getAddSerial());
            msgInfo.put("SEND_STATUS", "1");
            msgInfo.put("SEND_SUCCESS", sendSuccess);
            String[] mobileArray = mobiles.split(",");
            DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
            for (String mobile : mobileArray) {
                msgInfo.put("RECEIVER_MOB", mobile);
                dictionaryService.saveOrUpdate(msgInfo, "T_MESSAGE_INFO", "");
            }
            return sendSuccess;
        } catch (Exception e) {
            log.error("互联网短信转发功能异常" + e);
        }
        return false;
    }
}
