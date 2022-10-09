/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.weixin.req.ImageMessage;
import net.evecom.platform.weixin.req.LinkMessage;
import net.evecom.platform.weixin.req.LocationMessage;
import net.evecom.platform.weixin.req.VoiceMessage;
import net.evecom.platform.weixin.resp.Article;
import net.evecom.platform.weixin.resp.Image;
import net.evecom.platform.weixin.resp.ImageMessageResp;
import net.evecom.platform.weixin.resp.NewsMessageResp;
import net.evecom.platform.weixin.resp.TextMessageResp;
import net.evecom.platform.weixin.resp.Video;
import net.evecom.platform.weixin.resp.VideoMessageResp;
import net.evecom.platform.weixin.resp.Voice;
import net.evecom.platform.weixin.resp.VoiceMessageResp;
import net.evecom.platform.weixin.util.MessageUtil;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
public class WeixinCoreService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WeixinCoreService.class);
    /**
     * 处理微信发来的请求
     * 
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String serviceUrl = properties.getProperty("serviceUrl");
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 创建时间
            String createD = requestMap.get("CreateTime");
            log.info(createD + ";;;"
                    + MessageUtil.formatTime(createD));
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 回复文本消息
            TextMessageResp textMessage = new TextMessageResp();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
            textMessage
                    .setContent("欢迎访问/微笑 /::)<a href=\"http://blog.csdn.net/lyq8479\">youyou的博客</a>!");
            // 将文本消息对象转换成xml字符串
            respMessage = MessageUtil.textMessageToXml(textMessage);
            log.info(msgType);
            /******************* 1.文本消息 *********************/
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                // respContent = "您发送的是文本消息！";
                // 接收用户发送的文本消息内容
                String content = requestMap.get("Content");
                // 创建图文消息
                NewsMessageResp newsMessage = new NewsMessageResp();
                newsMessage.setToUserName(fromUserName);
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                newsMessage.setFuncFlag(0);
                //List<Article> articleList = new ArrayList<Article>();
                // 判断用户发送的是否是单个QQ表情
                if (MessageUtil.isQqFace(content)) {
                    // 用户发什么QQ表情，就返回什么QQ表情
                    textMessage.setContent(content);
                    // 将文本消息对象转换成xml字符串
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
               
            }
            
            /*************** 3.事件推送 ***************/
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 首次关注，订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "欢迎使用“微平潭”办事服务平台，您还未绑定平潭行政服务中心官网账号" +
                        "，请<a href=\""+serviceUrl+"/userBindController.do?toBind\">点这里</a>进行绑定！";
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    //String eventKey = requestMap.get("EventKey");
                    
                }
            }

            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return respMessage;
    }

    /**
     * emoji表情转换(hex -> utf-16)
     * 
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

}
