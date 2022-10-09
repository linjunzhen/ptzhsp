/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 监察流程绘制dao
 * 
 * @author Sundy Sun
 * @version 2.0
 */
public interface TacheFlowDao extends BaseDao {
    /**
     * 根据ID获取对象
     * 
     * @param flowId
     * @return
     */
    public TacheFlow getFlowByTacheId(String tacheId);

    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getFlowByBusiCode(String busiCode);

    /**
     * 根据环节编码获取对象
     * 
     * @param tachecode
     * @return
     */
    public TacheFlow getFlowByTacheCode(String tacheCode);

    /**
     * 根据业务编码获取对象
     * 
     * @param buscode
     * @return
     */
    public BusSpecialInfo getBusByBusCode(String buscode);
    
    /**
     * 根据业务专项ID获取对象
     * 
     * @param itemId
     * @return
     */
    public List<TacheFlow> getFlowByBusId(String busid);

    /**
     * save flow data
     * 
     * @param tachecode
     * @param flowdata
     */
    public void updateFlowInfo(String tachecode, String flowdata,
            String flowheight);

    /**
     * update flow data
     * 
     * @param tachecode
     * @param busCode yewu
     */
    public void updateBusiState(String state, String busCode,String busId,String userId);
    
    /**
     * submit 环节流程
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void submitAudit(String tacheCode, String busCode);
    /**
     * 流程图绘制审批完成(审批工作流完成时进行的内容；对应同时提交流程图和监察点)
     * 
     * @param state
     *            ：2-审核通过3-审核不通过
     * @param busCode业务专项
     */
    public void flowPassAudit(String state, String busCode, String userId);
}
