/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.mas;

/**
 * 
 * 描述
 * @author Kester Chen
 * @created 2020年2月1日 下午1:08:39
 */
public class SendRes {
    /**
     * 
     */
    private String rspcod;//响应码
    /**
     * 
     */
    private String msgGroup;//消息批次号，由云MAS平台生成，
    //用于验证短信提交报告和状态报告的一致性（取值msgGroup）注:如果数据验证不通过msgGroup为空
    /**
     * 
     */
    private boolean success;

    public String getRspcod() {
        return rspcod;
    }

    public void setRspcod(String rspcod) {
        this.rspcod = rspcod;
    }

    public String getMsgGroup() {
        return msgGroup;
    }

    public void setMsgGroup(String msgGroup) {
        this.msgGroup = msgGroup;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
