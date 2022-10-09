/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.model;

/**
 * 描述  关键字回复
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class KeyWordReply {
    /**df**/
    private static final String HUOJING="特种服务";
    /**df**/
    private static final String TONGXUN="通讯";
    /**df**/
    private static final String BANKHAO="银行";
    public static String replyUserByKeys(String content){
        if(content.contains(HUOJING)){
            return "常用特种服务号码列表：\n匪警110 \n火警119 \n急救中心120";
        }else if(content.contains(TONGXUN)){
            return "常用通讯服务号码列表：\n"+
                "中国移动客服10086\n"+
                "中国联通客服10010\n"+
                "中国电信客服10000";
        }else if(content.contains(BANKHAO)){
            return "常用银行服务号码列表：\n"
                +"建设银行客服95533\n"
                +"工商银行客服95588\n"
                +"农业银行客服95599";
        }
        return null;
    }
    
//    public static String picReplyByKeys(String content){
//        
//    }
}
