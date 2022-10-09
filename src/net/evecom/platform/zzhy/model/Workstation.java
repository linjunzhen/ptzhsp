/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

/**
 * 描述   工商数据接口创建任务实体类
 * @author Flex Hu
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class Workstation {
    /**
     *
     */
    private String workstationTag;
    /**
     *
     */
    private String workstationId;
    /**
     *
     */
    private String clusterId;
    /**
     *
     */
    public String getWorkstationTag() {
        return workstationTag;
    }
    /**
     *
     */
    public void setWorkstationTag(String workstationTag) {
        this.workstationTag = workstationTag;
    }
    /**
     *
     */
    public String getWorkstationId() {
        return workstationId;
    }
    /**
     *
     */
    public void setWorkstationId(String workstationId) {
        this.workstationId = workstationId;
    }
    /**
     *
     */
    public String getClusterId() {
        return clusterId;
    }
    /**
     *
     */
    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }
}
