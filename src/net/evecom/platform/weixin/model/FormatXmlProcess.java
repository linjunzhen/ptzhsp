/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

import java.util.Date;

/**
 * 描述  封装最终的xml格式结果
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class FormatXmlProcess {
    /** 
     * 封装文字类的返回消息 
     * @param to 
     * @param from 
     * @param content 
     * @return 
     */  
    public String formatXmlAnswer(String to, String from, String content) {  
        StringBuffer sb = new StringBuffer();  
        Date date = new Date();  
        sb.append("<xml><ToUserName><![CDATA[");  
        sb.append(to);  
        sb.append("]]></ToUserName><FromUserName><![CDATA[");  
        sb.append(from);  
        sb.append("]]></FromUserName><CreateTime>");  
        sb.append(date.getTime());  
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");  
        sb.append(content);  
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");  
        return sb.toString();  
    } 
    
    /** 
     * 封装图片类的返回消息 
     * @param to 
     * @param from 
     * @param content 
     * @return 
     */  
    public String formatXmlPicAnswer(String to, String from, String content) {  
        StringBuffer sb = new StringBuffer();  
        Date date = new Date();  
        sb.append("<xml><ToUserName><![CDATA[");  
        sb.append(to);  
        sb.append("]]></ToUserName><FromUserName><![CDATA[");  
        sb.append(from);  
        sb.append("]]></FromUserName><CreateTime>");  
        sb.append(date.getTime());  
        sb.append("</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[");  
        sb.append("meinvtu01");  
        sb.append("]]></MediaId></Image></xml>");  
        return sb.toString();  
    } 
}
