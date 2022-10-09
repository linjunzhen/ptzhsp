/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;

/**
 * 描述 负责业务梳理材料审核过程各个业务材料的加载业务层
 * @author Water Guo
 * @created 2015-8-31 下午5:15:35
 */
public interface AllMaterialsService extends BaseService {

    /***
     * 描述 根据申请编号和所属业务专项流程图和环节相关信息
     * @author Water Guo
     * @created 2015-8-31 下午5:36:26
     * @param applyId
     * @param buscode
     * @return
     */
    public List<TacheFlow> getFlowByApplyId(String applyId, String buscode);

    /**
     * 从历史纪录表中和操作申报号找到该对接专项的基本信息
     * @author Water Guo
     * @created 2015-9-8 下午4:24:40
     * @param buscode
     * @param applyId
     * @return
     */
    public BusSpecialInfo getBusByBusCode(String buscode, String applyId);

    /**
     * 描述 根据操作申报号和过程编号查询该过程节点的监察点相关信息（要素和过程）
     * @author Water Guo
     * @created 2015-9-10 上午9:40:01
     * @param filter
     * @param appliyId
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> listMaterialsRuleDatagrid(SqlFilter filter, String appliyId, String processCode);

    /**
     * 描述获得该专项第一个环节的第一个监察节点的过程编码
     * @author Water Guo
     * @created 2015-9-11 下午4:25:44
     * @param tacheCode
     * @param applyId
     * @return
     */
    public Map<String, Object> getFirstProccess(String tacheCode, String applyId);
    
    /**
     * 描述获得该专项第一个环节的第一个监察节点的过程编码
     * @author Sundy Sun
     * @param tacheCode
     * @param applyId
     * @return
     */
    public Map<String, Object> queryFirstProccess(String tacheCode, String applyId);

    /**
     * 描述 (业务变更中)根据申请编号和所属业务专项流程图和环节相关信息（从变更表中获取相应的环节数据）
     * @author Water Guo
     * @created 2015-10-10 下午11:11:17
     * @param applyId
     * @param buscode
     * @return
     */
    public List<TacheFlow> getChangeFlowByApplyId(String applyId, String buscode);

    /**
     * 描述 从变更表中和操作申报号找到该对接专项的基本信息（业务变更）
     * @author Water Guo
     * @created 2015-10-10 下午11:24:55
     * @param buscode
     * @param applyId
     * @return
     */
    public BusSpecialInfo getChangeBusByBusCode(String buscode, String applyId);

    /**
     * 描述（数据源变更表）根据操作申报号和过程编号查询该过程节点的监察点相关信息（要素和过程）
     * @author Water Guo
     * @created 2015-10-11 下午12:56:03
     * @param filter
     * @param appliyId
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> listMaterialsChangeRuleDatagrid(SqlFilter filter, String appliyId,
            String processCode);

    /**
     * 描述 从变更表中获得该专项第一个环节的第一个监察节点的过程编码
     * @author Water Guo
     * @created 2015-10-11 下午4:54:26
     * @param tacheCode
     * @param applyId
     * @return
     */
    public Map<String, Object> getFirstChangeProccess(String tacheCode, String applyId);
    
    /**
     * 描述 从变更表中获得该专项第一个环节的第一个监察节点的变更过程编码
     * @param tacheCode
     * @param applyId
     * @return
     */
    public Map<String, Object> queryFirstChangeProccess(String tacheCode, String applyId);

    /**
     * 描述 从变更表中获得当前申报号当前环节的流程图信息
     * @author Water Guo
     * @created 2015-10-20 下午10:36:36
     * @param tacheCode
     * @param applyId
     * @return
     */
    public TacheFlow getFlowByTacheCodeFromChange(String tacheCode, String applyId);

    /**
     * 描述 从历史表中获得当前申报号当前环节的流程图信息
     * @author Water Guo
     * @created 2015-10-20 下午10:37:47
     * @param tacheCode
     * @param lastApplyId
     * @return
     */
    public TacheFlow getFlowByTacheCodeFromHistory(String tacheCode, String lastApplyId);

    /**
     * 描述 根据环节编码查询过程 (变更表)
     * @author Water Guo
     * @created 2015-10-20 下午10:54:17
     * @param tacheCode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> findMonitorNodeByTCodeFromChange(String tacheCode, String applyId);

    /**
     * 描述 根据环节编码查询过程 (历史表) 
     * @author Water Guo
     * @created 2015-10-20 下午10:54:47
     * @param tacheCode
     * @param lastApplyId
     * @return
     */
    public List<Map<String, Object>> findMonitorNodeByTCodeFromHistory(String tacheCode, String lastApplyId);

}
