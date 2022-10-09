/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.command.HqlFilter;
import net.evecom.core.model.TableColumn;
import net.evecom.core.model.TableInfo;
import net.evecom.core.web.paging.PagingBean;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:00:29
 */
public interface GenericService<T, PK extends Serializable> {
    /**
     * 根据数据库表名称获取主键名称
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public List<String> getPrimaryKeyName(String tableName);
    /**
     * 根据表名称获取到表详细实体类
     * 
     * @param tableName
     * @return
     */
    public TableInfo getTableInfoByName(String tableName);
    /**
     * 根据数据库表名称获取到数据库列信息列表
     * 
     * @param tableName
     * @return
     */
    public List<TableColumn> findColumns(String tableName);
    /**
     * 根据表名称获取所有数据
     * @param tableName
     * @return
     */
    public List<Map<String,Object>> findAllByTableName(String tableName);
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @return
     */
    public String saveOrUpdate(Map<String,Object> colValues,String tableName,String entityId);
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public Map<String,Object> getByJdbc(String tableName,String[] colNames,Object[] colValues);
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public List<Map<String,Object>> getAllByJdbc(String tableName,String[] colNames,Object[] colValues);
    /**
     * 
     * 描述 根据表名称,列名称,列对应值移除掉记录
     * @author Flex Hu
     * @created 2014年9月7日 上午11:40:43
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void remove(String tableName,String colName,Object[] colValues);
    
    /**
     * 
     * 描述 根据表名称,列名称,列对应值伪删除记录
     * @author Roy Li
     * @created 2014-11-13 下午3:59:55
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void pseudoDel(String tableName,String colName,Object[] colValues);
    
    /**
     * 根据表名,父亲ID,目标列获取到对应数值列表
     * @param tableName
     * @param parentId
     * @param targetCols
     * @return
     */
    public List<Map<String,Object>> findTreeData(HttpServletRequest request,
            String tableName,String parentId,String targetCols);
    /**
     * 更新或者保存树形数据
     * @param parentId
     * @param treeData
     */
    public String saveOrUpdateTreeData(String parentId,Map<String,Object> treeData
            ,String tableName,String seqName);
    
    /**
     * 更新或者保存树形数据
     * @param parentId
     * @param treeData
     */
    public String saveOrUpdateTreeDataForAssignId(String parentId,Map<String,Object> treeData
            ,String tableName,String assignId);
    /**
     * 更新树形排序字段
     * @param tableName
     * @param dragTreeNodeId
     * @param dragTreeNodeNewLevel
     * @param targetNodeId
     * @param targetNodeLevel
     */
    public void updateTreeSn(String tableName,String dragTreeNodeId,
            int dragTreeNodeNewLevel,String targetNodeId, int targetNodeLevel,String moveType);
    /**
     * 
     * 描述 删除树形数据,包括子孙数据
     * @author Flex Hu
     * @created 2014年9月24日 下午2:43:20
     * @param tableName
     * @param recordId
     */
    public void removeTreeDataCascadeChild(String tableName,String recordId);
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @param seqName:序列名称
     * @return
     */
    public String saveOrUpdate(Map<String,Object> colValues,String tableName,String entityId,String seqName);
    /**
     * 
     * 描述 保存或者更新记录,基于分配的ID
     * @author Flex Hu
     * @created 2014年10月16日 上午11:14:08
     * @param colValues
     * @param tableName
     * @param assignId
     * @return
     */
    public String saveOrUpdateForAssignId(Map<String,Object> colValues,String tableName,String assignId);
    /**
     * 
     * 描述 删除掉数据
     * @author Flex Hu
     * @created 2015年8月27日 上午11:00:47
     * @param tableName
     * @param colNames
     * @param colValues
     */
    public void remove(String tableName,String[] colNames,Object[]colValues);
    /**
     * 
     * 描述 根据sql语句获取对象
     * @author Flex Hu
     * @created 2015年10月19日 下午2:47:15
     * @param sql
     * @param values
     * @return
     */
    public Object getObjectBySql(final String sql,final Object[] values);
}
