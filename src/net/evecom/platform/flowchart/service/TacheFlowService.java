/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.FlowChartInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 监察流程service
 * 
 * @author Sundy Sun
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public interface TacheFlowService extends BaseService {
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 根据sqlfilter获取到模板数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findTemplateBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 根据ID获取对象
     * 
     * @param flowId
     * @return
     */
    public TacheFlow getFlowByTacheId(String tacheId);

    /**
     * 根据业务专项code获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getFlowByBusiCode(String busiCode);
    
    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getFlowByBusId(String busid);

    /**
     * 根据环节编码获取对象
     * 
     * @param flowId
     * @return
     */
    public TacheFlow getFlowByTacheCode(String tacheCode);

    /**
     * save对象
     * 
     * @param courseId
     * @return
     */
    public void updateAuditInfo(String status,String userId,String buscode,String configName);
    /**
     * save对象
     * 
     * @param courseId
     * @return
     */
    public void saveFlow(String tacheId, String tacheCode, String busiCode,
            String flowInfo, String height);

    /**
     * update flowinfo
     * 
     * @param tacheCode环节编码
     * @param flowInfo流程信息
     */
    public void updateFlow(String tacheCode, String flowInfo, String height);

    /**
     * update bus state
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void submitAudit(String tacheCode, String busCode,String userId);
    /**
     * 流程图绘制审批完成(审批工作流完成时进行的内容；对应同时提交流程图和监察点)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     * @param busCode业务专项
     */
    public void flowPassAudit(String state, String busCode, String userId);

    /**
     * 根据业务编码查询实体信息
     * 
     * @param buscode
     * @return
     */
    public BusSpecialInfo getBusByBusCode(String buscode);
    
    /**
     * update flow data
     * 
     * @param tachecode
     * @param busCode yewu
     */
    public void updateBusiState(String state, String busCode,String busId,String userId);
    /**
     * 查询流程图模板
     * @param templatecode
     * @return
     */
    public List<Map<String,Object>> findTemplate(String templatecode);
}
