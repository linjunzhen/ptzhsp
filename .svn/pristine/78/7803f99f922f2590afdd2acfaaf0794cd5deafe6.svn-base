/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.data;


import net.evecom.platform.zzhy.model.Robot;

import java.util.Objects;

/**
 * 描述   提交业务数据实体类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public abstract class SubmitDataTable {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 真实表名
     */
    private String relTableName;
    /**
     * 要提交的的环节名称
     */
    private String curStepName;
    /**
     * 业务表主键名称
     */
    private String busRecordColName;

    /**
     *
     * @return
     */
    public String getTableName() {
        return tableName;
    }
    /**
     *
     * @return
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
        setRelTableName(tableName);
    }
    /**
     *
     * @return
     */
    public String getRelTableName() {
        return relTableName;
    }
    /**
     *
     * @return
     */
    private void setRelTableName(String tableName) {
        if(Objects.equals("T_COMMERCIAL_DOMESTIC",tableName)){
            tableName="T_COMMERCIAL_COMPANY";
        }
        this.relTableName = tableName;
    }
    /**
     *
     * @return
     */
    public String getCurStepName() {
        return curStepName;
    }
    /**
     *
     * @return
     */
    public void setCurStepName(String curStepName) {
        this.curStepName = curStepName;
    }
    /**
     *
     * @return
     */
    public String getBusRecordColName() {
        return busRecordColName;
    }
    /**
     *
     * @return
     */
    public void setBusRecordColName(String busRecordColName) {
        this.busRecordColName = busRecordColName;
    }

    /**
     * 获取机器人、
     *
     * @return
     */
    public abstract  Robot getRobotOfSubmit();
}
