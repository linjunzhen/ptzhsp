/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.model;

import net.evecom.platform.base.model.BaseModel;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
@SuppressWarnings("serial")
public class FlowChangeView extends BaseModel{
    /**
     * 序号
     */
    private String tacheId;
    /**
     * 环节编码
     */
    private String tacheCode;
    /**
     * 环节名称
     */
    private String tacheName;
    /**
     * 所属业务
     */
    private String busCode;
    /**
     * 所属单位
     */
    private String unitCode;
    /**
     * 流程数据
     */
    private String flowInfo;
    /**
     * 排序
     */
    private Integer treeSn;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 更新者
     */
    private String updateUser;
    /**
     * 版本
     */
    private String version;
    /**
     * 状态 1-启用 2-删除
     */
    private Integer status;
    /**高度**/
    private String flowHeight;
    /**old flow info**/
    private String oldFlow;

    /**
     * @return the tacheId
     */
    public String getTacheId() {
        return tacheId;
    }

    /**
     * @param tacheId
     *            the tacheId to set
     */
    public void setTacheId(String tacheId) {
        this.tacheId = tacheId;
    }

    /**
     * @return the tacheCode
     */
    public String getTacheCode() {
        return tacheCode;
    }

    /**
     * @param tacheCode
     *            the tacheCode to set
     */
    public void setTacheCode(String tacheCode) {
        this.tacheCode = tacheCode;
    }

    /**
     * @return the tacheName
     */
    public String getTacheName() {
        return tacheName;
    }

    /**
     * @param tacheName
     *            the tacheName to set
     */
    public void setTacheName(String tacheName) {
        this.tacheName = tacheName;
    }

    /**
     * @return the busCode
     */
    public String getBusCode() {
        return busCode;
    }

    /**
     * @param busCode
     *            the busCode to set
     */
    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    /**
     * @return the unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * @param unitCode
     *            the unitCode to set
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * @return the flowInfo
     */
    public String getFlowInfo() {
        return flowInfo;
    }

    /**
     * @param flowInfo
     *            the flowInfo to set
     */
    public void setFlowInfo(String flowInfo) {
        this.flowInfo = flowInfo;
    }

    /**
     * @return the treeSn
     */
    public Integer getTreeSn() {
        return treeSn;
    }

    /**
     * @param treeSn
     *            the treeSn to set
     */
    public void setTreeSn(Integer treeSn) {
        this.treeSn = treeSn;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     *            the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return the updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     *            the updateDate to set
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the updateUser
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     *            the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the flowHeight
     */
    public String getFlowHeight() {
        return flowHeight;
    }

    /**
     * @param flowHeight
     *            the flowHeight to set
     */
    public void setFlowHeight(String flowHeight) {
        this.flowHeight = flowHeight;
    }

    /**
     * @return the oldFlow
     */
    public String getOldFlow() {
        return oldFlow;
    }

    /**
     * @param oldFlow the oldFlow to set
     */
    public void setOldFlow(String oldFlow) {
        this.oldFlow = oldFlow;
    }

    
}

