/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.platform.flowchart.model.BusProcessInfo;

/**
 * 描述  process service
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface BusProcessService extends BaseService{
    /**
     * 查询业务环节的过程点
     * @param tacheCode
     * @return
     */
    public List findProcessForTache(String tacheCode);
    /**
     * 保存过程信息
     * @param pro
     */
    public void saveProcess(Map map,String tablename,String processId) ;
    /**
     * 批量保存
     * @param plist
     */
    public void saveBatch(List plist,String unitcode,String user,String tacheCode);
    /**
     * 根据业务专项删除关联过程信息
     * @param busCode
     */
    public void deleteProcessByBus(String busCode);
    /**
     * 根据过程编码查询过程
     * @param processCode
     * @return
     */
    public BusProcessInfo getProcessByCode(String processCode);
    /**
     * 根据过程编码更新要素 
     * @param processCode
     */
    public void updateByProcessCode(String processCode,String userId,String state);
    
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
}
