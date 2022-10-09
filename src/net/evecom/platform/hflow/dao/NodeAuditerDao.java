/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 节点审核操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface NodeAuditerDao extends BaseDao {

    /**
     * 
     * 描述 获取审核人配置
     * @author Flex Hu
     * @created 2015年8月19日 上午11:17:01
     * @param nodeName
     * @param nextNodeName
     * @param defId
     * @return
     */
    public Map<String,Object> getNodeAuditer(String nodeName,String nextNodeName,String defId,int flowVersion);
}
