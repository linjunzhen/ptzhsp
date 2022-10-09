/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.req;

/**
 * 描述 请求消息之 图片消息
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class ImageMessage extends BaseMessage{
   /**
    * 图片链接
    */
    private String PicUrl;
    /**
     * 图片消息媒体ID
     */
    private String MediaId;
    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    /**
     * @return the mediaId
     */
    public String getMediaId() {
        return MediaId;
    }

    /**
     * @param mediaId the mediaId to set
     */
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    
    
}
