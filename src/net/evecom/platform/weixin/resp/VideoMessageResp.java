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
public class VideoMessageResp extends BaseMessageResp{
    /**
     * 视频媒体对象
     */
    private Video Video;

    /**
     * @return the video
     */
    public Video getVideo() {
        return Video;
    }

    /**
     * @param video the video to set
     */
    public void setVideo(Video video) {
        Video = video;
    }
    
    
}
