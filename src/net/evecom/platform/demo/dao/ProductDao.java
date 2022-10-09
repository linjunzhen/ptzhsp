/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.demo.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 产品操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ProductDao extends BaseDao {
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年1月12日 下午5:33:02
     * @param parentId
     * @param treeData
     * @param tableName
     * @param seqName
     * @return
     */
    public String saveOrUpdateTreeData(String parentId,Map<String,Object> treeData
            ,String tableName,String seqName);
}
