/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.model;

import java.util.Date;

import net.evecom.platform.base.model.BaseModel;

/**
 * 描述 监察流程实体
 * 
 * @author Sundy Sun
 * @version 2.0
 */
public class FlowChartInfo extends BaseModel {
    /**
     * 编号
     */
    private String flowId;
    /**
     * 环节
     */
    private String coureseId;
    /**
     * 流程数据
     */
    private String flowdata;
    /**
     * create date
     */
    private Date createdate;
    /**
     * 业务专项
     */
    private String busiItem;

    /**
     * @return the flowId
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * @return the coureseId
     */
    public String getCoureseId() {
        return coureseId;
    }

    /**
     * @param coureseId
     *            the coureseId to set
     */
    public void setCoureseId(String coureseId) {
        this.coureseId = coureseId;
    }

    /**
     * @return the flowdata
     */
    public String getFlowdata() {
        return flowdata;
    }

    /**
     * @param flowdata
     *            the flowdata to set
     */
    public void setFlowdata(String flowdata) {
        this.flowdata = flowdata;
    }

    /**
     * @return the createdate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     *            the createdate to set
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return the busiItem
     */
    public String getBusiItem() {
        return busiItem;
    }

    /**
     * @param busiItem
     *            the busiItem to set
     */
    public void setBusiItem(String busiItem) {
        this.busiItem = busiItem;
    }

}
