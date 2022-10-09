/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.req;

/**
 * 描述  请求消息之文本消息
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class TextMessage extends BaseMessage{

    /**消息内容**/
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
