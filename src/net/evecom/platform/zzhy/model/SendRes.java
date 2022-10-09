/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;
/**
 * 描述  http短信发送返回结果实体类
 * @author Flex Hu
 * @version 1.0
 * @created 2020年10月12日 下午5:10:02
 */
public class SendRes {
    /**
     * 响应状态码
     */
    private String rspcod;
    /**
     * 消息批次号，由云MAS平台生成，用于验证短信提交报告和
     * 状态报告的一致性（取值msgGroup）注:如果数据验证不通过msgGroup为空
     */
    private String msgGroup;
    /**
     * 数据校验结果
     */
    private boolean success;

    /**
     *
     * @return
     */
    public String getRspcod() {
        return rspcod;
    }
    /**
     *
     * @return
     */
    public void setRspcod(String rspcod) {
        this.rspcod = rspcod;
    }
    /**
     *
     * @return
     */
    public String getMsgGroup() {
        return msgGroup;
    }
    /**
     *
     * @return
     */
    public void setMsgGroup(String msgGroup) {
        this.msgGroup = msgGroup;
    }
    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     *
     * @return
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
