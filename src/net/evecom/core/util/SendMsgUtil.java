/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.util.HashMap;
import java.util.Map;

import net.evecom.platform.system.service.SysSendMsgService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;

/**
 * 
 * 描述 发送短信Util
 * 
 * @author Derek Zhang
 * @created 2015年11月24日 下午3:29:10
 * @version 1.1
 * 
 */
@SuppressWarnings("unused")
public class SendMsgUtil {
    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(SendMsgUtil.class);

    /**
     * 发送短信 并存入发送短信记录表(立即发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:29:10
     * @param owner
     * @param fieldName
     * @param args
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public static Map<String, Object> immediatelySendMsg(String content, String receiverId,
            String receiverMob) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (StringUtil.isBlank(content)) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的信息内容不能为空！");
            return returnMap;
        }
        if (StringUtil.isBlank(receiverMob)) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的手机号码不能为空！");
            return returnMap;
        }
        if (content.length() > 400) {
            returnMap.put("result", false);
            returnMap.put("msg", "发送的内容不能超过400个字符！");
            return returnMap;
        }
        SysSendMsgService sysSendMsgService = (SysSendMsgService) AppUtil
                .getBean("sysSendMsgService");
        // 调用发送接口
        sysSendMsgService.sendMsg(content, receiverMob);
        // 保存发送情况数据
        return sysSendMsgService.saveSendMsg(content, receiverId, receiverMob, 1, "");
    }

    /**
     * 保存到短信发送队列单条(延迟发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:29:10
     * @param content
     *            发送的内容
     * @param receiverName
     *            接收人
     * @param receiverMob
     *            接收人手机号
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public static Map<String, Object> saveSendMsg(String content, String receiverName,
            String receiverMob) {
        SysSendMsgService sysSendMsgService = (SysSendMsgService) AppUtil
                .getBean("sysSendMsgService");
        return sysSendMsgService.saveSendMsg(content, receiverName, receiverMob, 0, "");
    }
    
    /**
     * 保存到短信发送队列单条(延迟发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:29:10
     * @param content
     *            发送的内容
     * @param receiverName
     *            接收人
     * @param receiverMob
     *            接收人手机号
     * @param type
     *            工程建设标识  1：工程建设  0：非工程建设
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public static Map<String, Object> saveSendMsg(String content, String receiverName,
            String receiverMob,String type) {
        SysSendMsgService sysSendMsgService = (SysSendMsgService) AppUtil
                .getBean("sysSendMsgService");
        return sysSendMsgService.saveSendMsg(content, receiverName, receiverMob, 0, "",type);
    }
    

    /**
     * 保存到短信发送队列单条(延迟发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:29:10
     * @param content
     *            发送的内容
     * @param receiverMob
     *            接入人手机号
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public static Map<String, Object> saveSendMsg(String content, String receiverMob) {
        return saveSendMsg(content, "", receiverMob);
    }
    
    /**
     * 保存到短信发送队列单条(延迟发送)
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 下午3:29:10
     * @param content
     *            发送的内容
     * @param receiverMob
     *            接入人手机号
     * @param type
     *            工程建设标识  1：工程建设  0：非工程建设
     * @return Map<String,Object> 参数result true:保存成功 false:操作失败 msg:失败原因
     *         result为false时有值
     */
    public static Map<String, Object> saveSendMsgByType(String content, String receiverMob,String type) {
        return saveSendMsg(content, "", receiverMob,type);
    }
}
