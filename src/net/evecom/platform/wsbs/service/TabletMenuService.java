/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 清单service
 * @author Sundy Sun
 * @version v1.0
 */
public interface TabletMenuService extends BaseService{

    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Faker Li
     * @created 2015年10月27日 下午2:23:42
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Faker Li
     * @created 2015年10月27日 下午2:23:42
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBSDeptBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述  根据ID删除
     * @param tabletIds
     */
    public void removeCascade(String[] tabletIds);
    
    /**
     * 根据表名,父亲ID,目标列获取到对应数值列表
     * @param tableName
     * @param parentId
     * @param targetCols
     * @return
     */
    public List<Map<String,Object>> findTree(HttpServletRequest request,
            String tableName,String parentId,String targetCols);
    /**
     * 根据ID查找列表信息
     * @param tabletId
     * @return
     */
    public List<Map<String,Object>> findMenuById(String tabletId,String code);
    
    /**
     * 根据ID查找列表信息
     * @param tabletId
     * @return
     */
    public List<Map<String,Object>> findWorkOfficeById();
    
    /**
     * 
     * 描述 保存中间表
     */
    public void saveMenuItem(String[] itemIds,String[] itemNames,String[] itemCodes,String tabletId,String catalogCode);
    /**
     * 
     * 描述 保存办事部门表
     * @param itemIds
     * @param itemNames
     */
    public void saveWorkDept(String[] itemIds,String[] itemNames,String[] itemCodes,String[] treeSns);
    /**
     * 查询办事部门
     * @return
     */
    public List<Map<String,Object>> findBSDeptForWebSite();
    /**
     * 
     * 描述 获取前台权利清单列表
     * @param page
     * @param rows
     * @param sxxz
     * @param busTypeIds
     * @return
     */
    public Map<String, Object> findfrontQlqdList(String page, String rows, String sxxz, String busTypeIds);
    /**
     * 查找服务事项
     * @param itemIds
     * @return
     */
    public List<Map<String, Object>> queryItemsByTablet(String itemIds);
    /**
     * 
     * 描述 根据sqlfilter获取到目录树数据列表
     * @author Faker Li
     * @created 2015年10月27日 下午2:23:42
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findCatalogBySqlFilter(SqlFilter sqlFilter);
}
