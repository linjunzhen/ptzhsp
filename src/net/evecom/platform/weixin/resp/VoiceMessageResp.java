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
public class VoiceMessageResp extends BaseMessageResp{
    /**语音媒体**/
    private Voice Voice;

    /**
     * @return the voice
     */
    public Voice getVoice() {
        return Voice;
    }

    /**
     * @param voice the voice to set
     */
    public void setVoice(Voice voice) {
        Voice = voice;
    }
    
    
}
