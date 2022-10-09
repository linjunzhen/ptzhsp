/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.resp;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class Video {
    /**媒体ID**/
    private String MediaId;
    /**视频消息的标题**/
    private String Title;
    /**视频消息的描述**/
    private String Description;
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
    /**
     * @return the title
     */
    public String getTitle() {
        return Title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        Title = title;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return Description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        Description = description;
    }
    
    
}
