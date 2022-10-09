/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;
import net.evecom.platform.flowchange.model.FlowChangeView;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface FlowChangeDao extends BaseDao{
    /**
     * 获取视图信息
     * @param tachecode
     * @return
     */
    public FlowChangeView getFlowChange(String tachecode);
    /**
     * save flow data
     * 
     * @param tachecode
     * @param flowdata
     */
    public void updateFlowInfo(String tacheId, String flowdata,
            String flowheight);
    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getFlowByBusiCode(String busiCode,String applyId);
    /**
     * 根据环节编码获取变更对象
     * 
     * @param tachecode
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
     * @param busCode yewu
     */
    public void copyTaches(String busCode,String date);
    /**
     * 将变更后的流程图更新到原来的流程图表
     * 
     * @param applyId
     *            yewu
     */
    public void coverOldTaches(String applyId);
    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getChangeFlowByBusId(String busId,String applyId);
    
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
    public void submitAudit(String tacheCode, String busCode,String userId);
    /**
     * 流程图变更审批完成(供审批流程完成时调用)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     * @param busCode业务专项
     */
    public void endAudit(String state, String applyId, String userId,String busCode,String busName);
    /**
     * 监察点变更后提交审核，审批完成(审批工作流完成时进行的内容，只对应提交监察点)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     * @param busCode业务专项
     */
    public void monitorNodePassAudit(String state, String busCode, String userId);
    
    /**
     * 批量保存
     * @param plist
     */
    public void saveProcessBatch(List plist,String unitcode,String user,String tacheCode);
}
