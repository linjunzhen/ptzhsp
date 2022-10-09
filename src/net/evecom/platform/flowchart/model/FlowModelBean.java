/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.model;

import java.util.List;

import net.evecom.platform.base.model.BaseModel;

/**
 * 描述 监察流程
 * 
 * @author Sundy Sun
 * @version 2.0
 */
public class FlowModelBean extends BaseModel {
    /**
     * 模型类型
     */
    private String modelClass;
    /**
     * 线条from
     */
    private String linkFromPortIdProperty;
    /**
     * link to
     */
    private String linkToPortIdProperty;
    /**
     * node data
     */
    private List nodeDataArray;
    /**
     * link data
     */
    private List linkDataArray;

    /**
     * @return the modelClass
     */
    public String getModelClass() {
        return modelClass;
    }

    /**
     * @param modelClass
     *            the modelClass to set
     */
    public void setModelClass(String modelClass) {
        this.modelClass = modelClass;
    }

    /**
     * @return the linkFromPortIdProperty
     */
    public String getLinkFromPortIdProperty() {
        return linkFromPortIdProperty;
    }

    /**
     * @param linkFromPortIdProperty
     *            the linkFromPortIdProperty to set
     */
    public void setLinkFromPortIdProperty(String linkFromPortIdProperty) {
        this.linkFromPortIdProperty = linkFromPortIdProperty;
    }

    /**
     * @return the linkToPortIdProperty
     */
    public String getLinkToPortIdProperty() {
        return linkToPortIdProperty;
    }

    /**
     * @param linkToPortIdProperty
     *            the linkToPortIdProperty to set
     */
    public void setLinkToPortIdProperty(String linkToPortIdProperty) {
        this.linkToPortIdProperty = linkToPortIdProperty;
    }

    /**
     * @return the nodeDataArray
     */
    public List getNodeDataArray() {
        return nodeDataArray;
    }

    /**
     * @param nodeDataArray
     *            the nodeDataArray to set
     */
    public void setNodeDataArray(List nodeDataArray) {
        this.nodeDataArray = nodeDataArray;
    }

    /**
     * @return the linkDataArray
     */
    public List getLinkDataArray() {
        return linkDataArray;
    }

    /**
     * @param linkDataArray
     *            the linkDataArray to set
     */
    public void setLinkDataArray(List linkDataArray) {
        this.linkDataArray = linkDataArray;
    }

}
