/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 栏目操作dao
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface ModuleDao extends BaseDao {

    /**
     * 
     * 描述 根据父亲ID获取类别数据
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:26:06
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId);
    /**
     * 
     * 描述 根据栏目ID获取文章数据
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:26:06
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByContentId(String id);
    
    /**
     * 
     * 描述 根据父亲ID获取类别数据(有权限)
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:26:06
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findRoleByParentId(String parentId);
}
