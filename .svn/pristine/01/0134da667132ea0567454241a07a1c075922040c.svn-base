/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.command.HqlFilter;
import net.evecom.core.model.TableColumn;
import net.evecom.core.model.TableInfo;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月5日 上午11:59:26
 */
public interface GenericDao<T, PK extends Serializable> {
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
    /**
     * 根据sql语句查询出结果集合,如果你要查询的是实体结果,那么要往 entityClassMap
     * 存放class对象,如果查询的列结果,那么无需传递该参数
     * 
     * @author Flex Hu
     * @param sql
     *            :查询sql语句
     * @param queryParams
     *            :查询参数,可选
     * @param entityClassMap
     *            :当查询的是实体对象的时候,要用到该参数,也是可选的
     * @param pb
     *            :分页对象,可选参数,不传代表不进行分页
     * @return
     * @Example1: String sql = "select {t.*} from t_sys_menu t";
     *            Map<String,Class> map = new TreeMap<String,Class>();
     *            map.put("t", SysMenu.class); return findBySql(sql, null, map,
     *            null);
     * 
     * @Example2: String sql =
     *            "select username,account from t_system_user where age>? ";
     *            return findBySql(sql,new Object[]{20},null,new PagingBean());
     * 
     */
    public List findBySql(String sql, Object[] queryParams,
            Map<String, Class> entityClassMap, PagingBean pb);

    /**
     * 根据sql语句和查询参数获取到结果集合
     * 
     * @author Flex Hu
     * @param sql
     *            :sql语句
     * @param queryParams
     *            :查询参数
     * @param pb
     *            :分页对象
     * @return
     */
    public List<Map<String, Object>> findBySql(String sql,
            Object[] queryParams, PagingBean pb);
    
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
     * 描述 根据sql语句获取所查询的表名称
     * @author Flex Hu
     * @created 2014年8月29日 下午3:08:45
     * @param sql
     * @return
     */
    public Map<String,String> getTableNamesBySql(String sql);
    /**
     * 
     * 描述 根据sql语句获取查询结果keys
     * @author Flex Hu
     * @created 2014年8月29日 下午3:21:58
     * @param sql
     * @return
     */
    public List<String> findQueryResultKeysBySql(String sql);
    /**
     * 
     * 描述 根据SQL语句获取查询结果集合中的列集合
     * @author Flex Hu
     * @created 2014年8月29日 下午4:59:45
     * @param sql
     * @return
     */
    public List<TableColumn> findQueryResultColsBySql(String sql);
    /**
     * 把传递进来的SQL语句进行重新构建 构建成查询的SQL语句
     * 
     * @author Flex Hu
     * @param sqlFilter
     *            :sql过滤器
     * @param oldSql
     *            :原本的SQL语句
     * @param params
     *            :查询参数
     * @return
     */
    public String getQuerySql(SqlFilter sqlFilter, String oldSql,
            List<Object> params) ;
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
     * 描述 根据表名称,列名称,列对应值伪删除记录
     * @author Roy Li
     * @created 2014-11-13 下午3:59:55
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void pseudoDel(String tableName,String colName,Object[] colValues);
    
    /**
     * 根据SQL获取到唯一结果MAP
     * @param sql
     * @param objs
     * @return
     */
    public Map<String,Object> getByJdbc(String sql,Object[] objs);
    /**
     * 获取树形结构的最大排序值
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public int getMaxTreeSortSn(String tableName);
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
     * 描述 伪删除树形数据,包括子孙数据，伪删除字段需为IS_DELETE，0未删除，1为删除
     * @author Roy Li
     * @created 2014-10-28 下午3:10:28
     * @param tableName
     * @param recordId
     */
    public void pseudoDelTreeDataCascadeChild(String tableName,String recordId);
    /**
     * 
     * 描述 执行SQL语句
     * @author Flex Hu
     * @created 2014年10月3日 下午4:47:56
     * @param sql
     * @param params
     */
    public void executeSql(String sql,Object[] params);
    /**
     * 
     * 描述 获取SqlServer数据库下的所有数据库表名称
     * @author Flex Hu
     * @created 2014年10月13日 下午2:45:57
     * @return
     */
    public List<String> findSqlServerTables();
    /**
     * 
     * 描述 获取SqlServer表的注释
     * @author Flex Hu
     * @created 2014年10月15日 下午3:31:47
     * @param tableName
     * @return
     */
    public String getSqlServerTableComment(String tableName);
    /**
     * 
     * 描述 根据表名称获取表格信息
     * @author Flex Hu
     * @created 2014年10月13日 下午3:55:54
     * @param tableName
     * @return
     */
    public TableInfo getSqlServerTableInfo(String tableName);
    /**
     * 
     * 描述 根据表名称获取列集合
     * @author Flex Hu
     * @created 2014年10月15日 下午3:58:49
     * @param tableName
     * @return
     */
    public List<TableColumn> findSqlServerTableColumns(String tableName);
    /**
     * 
     * 描述 获取sqlserver的主键名称
     * @author Flex Hu
     * @created 2014年10月13日 下午4:46:13
     * @param tableName
     * @return
     */
    public List<String> getSqlServerPrimaryKeys(String tableName);
    /**
     * 
     * 描述 拼接获取查询带有数据权限的SQL语句
     * @author Flex Hu
     * @created 2014年10月23日 上午9:49:10
     * @param oldSql
     * @param tableAliasName
     * @param queryParams
     * @return
     */
    public String getDataPermissionSql(StringBuffer oldSql,String tableAliasName,List<Object> queryParams);
    /**
     * 
     * 描述 根据表名称和列名称获取列的注释
     * @author Flex Hu
     * @created 2015年4月24日 下午4:40:26
     * @param tableName
     * @param colName
     * @return
     */
    public String getColComment(String tableName,String colName);
    /**
     * 
     * 描述 根据表名称获取到列、注释键值对
     * @author Flex Hu
     * @created 2014年9月2日 上午9:20:32
     * @param tableNames
     * @return
     */
    public Map<String,String> getColumnCommentByTableNames(Set<String> tableNames);
    /**
     * 
     * 描述 根据SQL和参数获取MAP数据
     * @author Flex Hu
     * @created 2015年11月25日 上午11:03:23
     * @param sql
     * @param params
     * @return
     */
    public Map<String,Object> getMapBySql(String sql,Object[] params);
}
