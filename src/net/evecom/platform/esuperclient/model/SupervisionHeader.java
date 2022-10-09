/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.platform.esuperclient.model;

/**
 * 
 * 描述  电子监察系统预警接收接口wsdl生成代码
 * @author Derek Zhang
 * @created 2016年2月19日 下午4:53:11
 */
public class SupervisionHeader {
    /**
     * 目标平台编码
     */
    protected AddressInfo destinationID;
    /**
     * 接入码
     */
    protected String loginKey;
    /**
     * 业务类型名
     */
    protected String msgName;
    /**
     * 发送文平台编码
     */
    protected AddressInfo originalID;
    /**
     * 加密码
     */
    protected String security;
    /**
     * 数据源平台编码
     */
    protected AddressInfo sourceID;
    /**
     * 时间载戳
     */
    protected String timeStamp;
    /**
     * 报文ID    
     */
    protected String transactionID;
    /**
     * 版本号
     */
    protected String version;

    /**
     * Gets the value of the destinationID property.
     * 
     * @return possible object is {@link AddressInfo }
     * 
     */
    public AddressInfo getDestinationID() {
        return destinationID;
    }

    /**
     * Sets the value of the destinationID property.
     * 
     * @param value
     *            allowed object is {@link AddressInfo }
     * 
     */
    public void setDestinationID(AddressInfo value) {
        this.destinationID = value;
    }

    /**
     * Gets the value of the loginKey property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLoginKey() {
        return loginKey;
    }

    /**
     * Sets the value of the loginKey property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLoginKey(String value) {
        this.loginKey = value;
    }

    /**
     * Gets the value of the msgName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMsgName() {
        return msgName;
    }

    /**
     * Sets the value of the msgName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMsgName(String value) {
        this.msgName = value;
    }

    /**
     * Gets the value of the originalID property.
     * 
     * @return possible object is {@link AddressInfo }
     * 
     */
    public AddressInfo getOriginalID() {
        return originalID;
    }

    /**
     * Sets the value of the originalID property.
     * 
     * @param value
     *            allowed object is {@link AddressInfo }
     * 
     */
    public void setOriginalID(AddressInfo value) {
        this.originalID = value;
    }

    /**
     * Gets the value of the security property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSecurity() {
        return security;
    }

    /**
     * Sets the value of the security property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSecurity(String value) {
        this.security = value;
    }

    /**
     * Gets the value of the sourceID property.
     * 
     * @return possible object is {@link AddressInfo }
     * 
     */
    public AddressInfo getSourceID() {
        return sourceID;
    }

    /**
     * Sets the value of the sourceID property.
     * 
     * @param value
     *            allowed object is {@link AddressInfo }
     * 
     */
    public void setSourceID(AddressInfo value) {
        this.sourceID = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTimeStamp(String value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the transactionID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
