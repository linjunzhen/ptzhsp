/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuperclient.model;

/**
 * 描述  电子监察系统预警接收接口wsdl生成代码
 * @author Derek Zhang
 * @created 2016年2月19日 下午4:53:11
 */
public class SupervisionBusInfoRs {

    /**
     * cfmResult
     */
    protected String cfmResult;
    /**
     * msgTransactionID
     */
    protected String msgTransactionID;
    /**
     * oprTime
     */
    protected String oprTime;
    /**
     * rsReason
     */
    protected String rsReason;

    /**
     * Gets the value of the cfmResult property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCfmResult() {
        return cfmResult;
    }

    /**
     * Sets the value of the cfmResult property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCfmResult(String value) {
        this.cfmResult = value;
    }

    /**
     * Gets the value of the msgTransactionID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMsgTransactionID() {
        return msgTransactionID;
    }

    /**
     * Sets the value of the msgTransactionID property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMsgTransactionID(String value) {
        this.msgTransactionID = value;
    }

    /**
     * Gets the value of the oprTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOprTime() {
        return oprTime;
    }

    /**
     * Sets the value of the oprTime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOprTime(String value) {
        this.oprTime = value;
    }

    /**
     * Gets the value of the rsReason property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRsReason() {
        return rsReason;
    }

    /**
     * Sets the value of the rsReason property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRsReason(String value) {
        this.rsReason = value;
    }

}
