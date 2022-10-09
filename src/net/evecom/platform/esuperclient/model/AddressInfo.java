/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuperclient.model;

/**
 * 
 * 描述 电子监察系统预警接收接口wsdl生成代码
 * 
 * @author Derek Zhang
 * @created 2016年2月19日 下午4:53:11
 */
public class AddressInfo {
    /**
     * 平台编码
     */
    protected String platType;
    /**
     * 单位编码
     */
    protected String provType;

    /**
     * Gets the value of the platType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPlatType() {
        return platType;
    }

    /**
     * Sets the value of the platType property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPlatType(String value) {
        this.platType = value;
    }

    /**
     * Gets the value of the provType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getProvType() {
        return provType;
    }

    /**
     * Sets the value of the provType property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setProvType(String value) {
        this.provType = value;
    }

}
