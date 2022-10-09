/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.model.BusProcessInfo;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface ProcessChangeService extends BaseService{
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 从正式环境copy新的副本到监察点副本表
     * @param busCode
     * @param date
     */
    public void copyMonitorNode(String busCode,String userId,String applyId);
    /**
     * 批量保存
     * @param plist
     */
    public void saveBatch(List plist,String unitcode,String user,String tacheCode,String applyId);
    /**
     * 根据过程编码查询过程
     * @param processCode
     * @return
     */
    public BusProcessInfo getProcessByCode(String processCode,String applyId);
    /**
     * 根据过程编码更新要素 
     * @param processCode
     */
    public void updateByProcessCode(String processCode,String userId,String state,String applyId);
    /**
     * 根据过程编码查询要素
     * @param processCode
     * @return
     */
    public List<Map<String,Object>> findByProcessCode(String processCode,String applyId);
    
    /**
     * 根据环节编码查询过程
     * @param processCode
     * @return
     */
    public List<Map<String,Object>> findMonitorNodeByTCode(String tacheCode,String applyId);
    /***
     * 描述 : 针对可编剧表格的更新与保存
     * @author Water Guo
     * @created 2015-8-18 下午3:10:54
     * @param variables
     */
    public void saveOrUpdateBatch(Map<String, Object> variables);
    /**
     * change factor
     * 
     * @param tachecode
     * @param busCode yewu
     */
    public void changeFactorSubmit(String busCode,String userId,String applyId);
    /**
     * 监察点变更后提交审核，审批完成(审批工作流完成时进行的内容，只对应提交监察点)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     * @param busCode业务专项
     */
    public void monitorNodePassAudit(String state, String busCode, String userId,String applyId);
}
