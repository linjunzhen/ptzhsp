/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.platform.flowchart.model.BusProcessInfo;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
@SuppressWarnings("unchecked")
public interface ProcessChangeDao extends BaseDao{
    /**
     * 批量保存
     * @param plist
     */
    public void saveBatch(List plist,String unitcode,String user,String tacheCode,String applyId);
    /**
     * 从正式环境copy新的副本到监察点副本表
     * @param busCode
     * @param date
     */
    public void copyMonitorNode(String busCode,String userId,String applyId);
    /**
     * 根据过程编码查询过程
     * @param processCode
     * @return
     */
    public BusProcessInfo getProcessByCode(String processCode,String applyId);
}
