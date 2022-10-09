/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;
import net.evecom.platform.flowchart.model.BusProcessInfo;

/**
 * 描述 业务过程dao
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface BusProcessDao extends BaseDao{
    /**
     * 查询环节对应过程
     * @param tacheCode
     * @return
     */
    public List queryProcessByTache(String tacheCode);
    /**
     * 批量添加过程点
     * @param plist
     */
    public void saveBatch(List plist,String unitcode,String user,String tacheCode);
    /**
     * 根据过程编码查询过程
     * @param processCode
     * @return
     */
    public BusProcessInfo getProcessByCode(String processCode);
    /**
     * update process state
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void submitAudit(String tacheCode, String busCode,String userId);
    /**
     * cancel process state
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void cancelNodeAduit(String tacheCode, String busCode,String userId);
    /**
     * 根据业务专项删除关联过程信息
     * @param busCode
     */
    public void deleteProcessByBus(String busCode);
}
