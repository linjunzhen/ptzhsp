/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 栏目操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ModuleService extends BaseService {

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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 根据sqlfilter获取到查看数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findViewBySqlFilter(SqlFilter sqlFilter,String userId);
    
    /**
     * 更新或者保存树形数据
     * @param parentId
     * @param treeData
     */
    public String saveOrUpdateTreeDataModule(String parentId,Map<String,Object> treeData
            ,String tableName,String seqName);
    /**
     * 
     * 描述 根据表名称,列名称,列对应值移除掉记录
     * @author Flex Hu
     * @created 2014年9月7日 上午11:40:43
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void removeModule(String tableName,String colName,Object[] colValues);
    

    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public Map<String,Object> getByJdbcModule(String tableName,String[] colNames,Object[] colValues);
    
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
    
    /**
     * 根据sqlfilter获取到标准化数据数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBzhjsDatagrid(SqlFilter sqlFilter);
}
