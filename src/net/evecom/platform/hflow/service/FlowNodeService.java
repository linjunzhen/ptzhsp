/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程节点操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowNodeService extends BaseService {
    /**
     * 
     * 描述 保存流程节点信息
     * @author Flex Hu
     * @created 2015年8月14日 下午5:23:14
     * @param defId
     * @param flowVersion
     * @param defJson
     */
    public void saveFlowNodes(String defId,int flowVersion,String defJson);
    /**
     * 
     * 描述 获取节点的KEY
     * @author Flex Hu
     * @created 2015年8月14日 下午5:38:38
     * @param nodeName
     * @param defId
     * @param flowVersion
     * @return
     */
    public int getKey(String nodeName,String defId,int flowVersion);
    /**
     * 
     * 描述 获取节点下一环节节点,只获取属于任务节点
     * @author Flex Hu
     * @created 2015年8月15日 下午12:30:59
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findNextNodes(String defId,String nodeName,int flowVersion);
    /**
     * 
     * 描述 获取下一环节节点,递归查找是属于任务节点的节点
     * @author Flex Hu
     * @created 2015年8月15日 下午12:50:55
     * @param toNodes
     * @param fromKey
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findByToNodes(List<Map<String,Object>> toNodes,
            int fromKey,String defId,int flowVersion);
    /**
     * 
     * 描述 获取下一环节的节点列表面向下拉框
     * @author Flex Hu
     * @created 2015年8月15日 下午1:06:28
     * @param defIdAndNodeNameValue
     * @return
     */
    public List<Map<String,Object>> findNextTaskNodesForSelect(String defIdAndNodeNameValue);
    /**
     * 
     * 描述 获取节点的名称
     * @author Flex Hu
     * @created 2015年8月17日 上午10:20:40
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeName(String defId,int flowVersion,String nodeType);
    /***
     * 
     * 描述 获取某个节点的指向节点信息
     * @author Flex Hu
     * @created 2015年8月19日 上午10:24:11
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findNextFlowNodes(String defId,String nodeName,int flowVersion);
    /***
     * 
     * 描述 获取某个节点的前个节点信息
     * @author Flex Hu
     * @created 2015年8月19日 上午10:24:11
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findPreFlowNodes(String defId,String nodeName,int flowVersion);
    /**
     * 
     * 描述 获取任务节点的名称
     * @author Flex Hu
     * @created 2015年8月23日 上午10:03:36
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<String> findTaskNodeNames(String defId,int flowVersion);
    /**
     * 
     * 描述 获取来源任务名称
     * @author Flex Hu
     * @created 2015年8月24日 上午9:29:40
     * @param defId
     * @param flowVersion
     * @param toTaskNodeName
     * @return
     */
    public List<String> findFromTaskNodeNames(String defId,int flowVersion,String toTaskNodeName);
    
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion);
    /**
     * 
     * 描述 获取节点信息
     * @author Flex Hu
     * @created 2015年11月10日 下午3:25:27
     * @param defId
     * @param flowVersion
     * @param nodeName
     * @return
     */
    public Map<String,Object> getFlowNode(String defId,int flowVersion,String nodeName);
    /**
     * 
     * 描述 拷贝流程节点数据
     * @author Flex Hu
     * @created 2016年3月31日 下午3:45:35
     * @param targetDefId
     * @param targetVersion
     * @param newDefId
     */
    public void copyFlowNodes(String targetDefId,int targetVersion,String newDefId);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-23 上午11:48:49
     * @param defId
     * @param flowVersion
     * @param key
     */
    public List<Map<String,Object>> getFlowNodeByKey(String defId,String flowVersion,String key);
    /**
     * 
     * 描述   获取特定节点友权限审核人员信息
     * @author Corliss Chen
     * @created 2017年3月17日 下午2:40:15
     * @param defId
     * @param flowVersion
     * @param key
     * @param nodeName
     * @return
     */
    public Map<String,Object> getFlowNodeReviewerByKey(String defId,int flowVersion,String key,String nodeName);
    
}
