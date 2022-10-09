/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.req;

/**
 * 描述  请求消息之 语音消息
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class VoiceMessage extends BaseMessage{

    /**媒体ID **/
    private String MediaId;
    /** 语音格式 **/
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
