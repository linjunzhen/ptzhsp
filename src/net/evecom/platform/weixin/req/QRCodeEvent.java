/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.req;

/**
 * 描述  扫描带参数二维码事件
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class QRCodeEvent extends BaseEvent{
    /**事件key值**/
    private String EventKey;
    /**用于换取二维码图片**/
    private String Ticket;
    /**
     * @return the eventKey
     */
    public String getEventKey() {
        return EventKey;
    }
    /**
     * @param eventKey the eventKey to set
     */
    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
    /**
     * @return the ticket
     */
    public String getTicket() {
        return Ticket;
    }
    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket) {
        Ticket = ticket;
    }
    
    
}
