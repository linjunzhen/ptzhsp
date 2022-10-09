/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class WechatProcess {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WechatProcess.class);
    /** 
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口） 
     * @param xml 接收到的微信数据 
     * @return  最终的解析结果（xml格式数据） 
     */  
    public String processWechatMag(String xml){  
        /** 解析xml数据 */  
        String result = ""; 
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml); 
        log.info(xmlEntity.getMsgType());
        if("event".equals(xmlEntity.getMsgType())&&"subscribe".equals(xmlEntity.getEvent())){//首次关注
            result="欢迎使用“微平潭”办事服务平台，您还未绑定平潭行政服务中心官网账号，请<a>点这里</a>进行绑定！";
        }else if("event".equals(xmlEntity.getMsgType())&&"unsubscribe".equals(xmlEntity.getEvent())){//取消关注
        }else if("event".equals(xmlEntity.getMsgType())&&"CLICK".equals(xmlEntity.getEvent())){
            //点击菜单跳转链接时的事件推送，EventKey，事件KEY值，设置的跳转URL 
            //log.info("menu click");
            result="请回复数字选择服务：\n"
                +"1 特种服务号码\n"
                +"2 通讯服务号码\n"
                +"3 银行服务号码\n"
                +"4 用户反馈";
        }else if("text".equals(xmlEntity.getMsgType())){//文本消息
            //关键字回复
            result=KeyWordReply.replyUserByKeys(xmlEntity.getContent());
            
//            /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */  
//            log.info(xmlEntity.getContent());
//            if("text".endsWith(xmlEntity.getMsgType())){  
//                result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());  
//            }  
//            log.info(result); 
        }
        /** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容  
         *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息 
         * */  
        //回复文字消息
//        result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName()
//                , xmlEntity.getToUserName(), result);
        //回复图片消息
        result = new FormatXmlProcess().formatXmlPicAnswer(xmlEntity.getFromUserName()
                , xmlEntity.getToUserName(), result);   
        log.info(result);
        return result;  
    }  
}   
