/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 流程映射类操作dao
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FlowMappedDao extends BaseDao {
    /**
     *  
     * 描述 保存映射子表
     * @author Faker Li
     * @created 2015年12月28日 下午2:24:37
     * @param entityId
     * @param split
     */
    public void saveYsNode(String ysId, String[] defNames);
    /**
     * 
     * 描述 根据流程定义ID和节点名称和映射类型获取映射信息
     * @author Flex Hu
     * @created 2015年12月28日 下午3:27:16
     * @param defId
     * @param nodeName
     * @param mapType
     * @return
     */
    public Map<String,Object> getFlowMapInfo(String defId,String nodeName,String mapType);

}
