/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 流程节点操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FlowNodeDao extends BaseDao {
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
     * 描述 获取节点的名称
     * @author Flex Hu
     * @created 2015年8月17日 上午10:20:40
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeName(String defId,int flowVersion,String nodeType);
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
}
