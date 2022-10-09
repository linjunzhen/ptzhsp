/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;
import net.evecom.core.dao.BaseDao;

/**
 * 描述 节点配置操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface NodeConfigDao extends BaseDao {
    /**
     * 
     * 描述 根据流程定义ID和流程节点名称获取配置的决策代码
     * @author Flex Hu
     * @created 2015年8月20日 下午4:31:56
     * @param defId
     * @param nodeName
     * @return
     */
    public String getTaskDecideCode(String defId,String nodeName,int flowVersion);
    /**
     * 
     * 描述 获取省网办的发起节点的名称
     * @author Flex Hu
     * @created 2016年1月26日 下午3:06:05
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeNameForProvinceStart(String defId,int flowVersion);
}
