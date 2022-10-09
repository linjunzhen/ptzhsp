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
import net.evecom.platform.flowchange.model.FlowChangeView;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
public interface FlowChangeService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findNewestBySqlFilter(SqlFilter sqlFilter);

    /**
     * update flowinfo
     * 
     * @param tache环节编码
     * @param flowInfo流程信息
     */
    public void updateFlow(String tacheId, String flowInfo, String height,String applyId,String userId,String flowSvg);

    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getFlowByBusiCode(String busiCode,String applyId);

    /**
     * 根据环节编码获取变更记录对象
     * 
     * @param flowId
     * @return
     */
    public TacheFlow getFlowByTacheCode(String tacheCode,String applyId);

    /**
     * copy special
     * 
     * @param busCode
     *            yewu
     */
    public void copyBusItem(String busCode,String date);

    /**
     * copy CHANGE
     * 
     * @param busCode
     *            yewu
     */
    public void copyTaches(String busCode,String date);

    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getChangeFlowByBusId(String busid,String applyId);
    
    /**
     * 根据业务编码查询实体信息
     * 
     * @param buscode
     * @return
     */
    public List<Map<String, Object>> getBusByBusCode(String buscode,String applyId);
    
    /**
     * 根据申报号查询实体信息
     * 
     * @param buscode
     * @return
     */
    public List<Map<String, Object>> getBusByApplyId(String applyId);

    /**
     * 流程图变更申请审核通过
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void applyAuditPass(String applyId,String userId);
    /**
     * 提交审核变更流程
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void submitAudit(String tacheCode, String busCode, String userId,String applyId);

    /**
     * 流程图变更审批完成(供审批流程完成时调用)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     */
    public void endAudit(String state, String applyId, String userId);
    /**
     * copy CHANGE
     * 
     * @param applyId
     *            yewu
     */
    public void coverOldTaches(String applyId);
    /**
     * 监察点变更后提交审核，审批完成(审批工作流完成时进行的内容，只对应提交监察点)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     * @param busCode业务专项
     */
    public void monitorNodePassAudit(String state, String busCode, String userId);
}
