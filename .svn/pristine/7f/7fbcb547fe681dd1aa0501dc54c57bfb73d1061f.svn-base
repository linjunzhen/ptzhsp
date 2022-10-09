/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import net.evecom.core.command.HqlFilter;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableColumn;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.GenericService;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:06:00
 */
public abstract class GenericServiceImpl<T, PK extends Serializable> implements GenericService<T, PK> {
    /**
     * 获取实体Dao
     * 描述
     * @author Flex Hu
     * @created 2014年9月6日 上午8:06:21
     * @return
     */
    protected abstract GenericDao<T, Serializable> getEntityDao();
    
    /**
     * 根据数据库表名称获取主键名称
     * 
     * @author Flex Hu
     * @param tableName
     * @return
     */
    public List<String> getPrimaryKeyName(String tableName){
        return getEntityDao().getPrimaryKeyName(tableName);
    }
    /**
     * 根据表名称获取到表详细实体类
     * 
     * @param tableName
     * @return
     */
    public TableInfo getTableInfoByName(String tableName){
        return getEntityDao().getTableInfoByName(tableName);
    }
    /**
     * 根据数据库表名称获取到数据库列信息列表
     * 
     * @param tableName
     * @return
     */
    public List<TableColumn> findColumns(String tableName){
        return getEntityDao().findColumns(tableName);
    }
    /**
     * 根据表名称获取所有数据
     * @param tableName
     * @return
     */
    public List<Map<String,Object>> findAllByTableName(String tableName){
        return getEntityDao().findAllByTableName(tableName);
    }
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
    public String saveOrUpdate(Map<String,Object> colValues,String tableName,String entityId){
        return getEntityDao().saveOrUpdate(colValues,tableName,entityId);
    }
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public Map<String,Object> getByJdbc(String tableName,String[] colNames,Object[] colValues){
        return getEntityDao().getByJdbc(tableName, colNames, colValues);
    }
    /**
     * 根据表名和列相关值获取到数据
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     */
    public List<Map<String,Object>> getAllByJdbc(String tableName,String[] colNames,Object[] colValues){
        return getEntityDao().getAllByJdbc(tableName, colNames, colValues);
    }
    /**
     * 
     * 描述 根据表名称,列名称,列对应值移除掉记录
     * @author Flex Hu
     * @created 2014年9月7日 上午11:40:43
     * @param tableName
     * @param colName
     * @param colValues
     */
    public void remove(String tableName,String colName,Object[] colValues){
        getEntityDao().remove(tableName, colName, colValues);
    }
    
    /**
     * 
     * 描述 根据表名称,列名称,列对应值伪删除记录
     * @author Roy Li
     * @created 2014-11-13 下午4:04:51
     * @param tableName
     * @param colName
     * @param colValues
     * @see net.evecom.core.service.GenericService#pseudoDel(java.lang.String, java.lang.String, java.lang.Object[])
     */
    public void pseudoDel(String tableName,String colName,Object[] colValues){
        getEntityDao().pseudoDel(tableName, colName, colValues);
    }
    
    /**
     * 根据表名,父亲ID,目标列获取到对应数值列表
     * @param tableName
     * @param parentId
     * @param targetCols
     * @return
     */
    public List<Map<String,Object>> findTreeData(HttpServletRequest request,
            String tableName,String parentId,String targetCols){
        StringBuffer sql = new StringBuffer("select ").append(targetCols);
        sql.append(" from ").append(tableName).append(" WHERE PATH like ? ");
        String path = null;
        if(parentId.equals("0")){
            path = "0.";
        }else{
            String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
            StringBuffer querySql = new StringBuffer("select PATH FROM ").append(tableName).append(" WHERE ")
                    .append(primaryKeyName).append("=? ");
            Map<String,Object> map = this.getEntityDao().getByJdbc(querySql.toString(),new Object[]{parentId});
            path = (String) map.get("PATH");
        }
        List params = new ArrayList();
        params.add(path+"%");
        Enumeration paramEnu = request.getParameterNames();
        while (paramEnu.hasMoreElements()) {
            String paramName = (String) paramEnu.nextElement();
            if (paramName.startsWith("Q_")) {
                String paramValue = (String) request.getParameter(paramName);
                if (StringUtils.isNotEmpty(paramValue)) {
                    SqlFilter.addRequestQueryParam(paramName, paramValue, sql, params);
                }
            }
        }
        if(tableName.equals("T_MEETING_FILETYPE")){
            sql.append(" ORDER BY TREE_SN DESC,CREATE_TIME DESC ");
        }else{
            sql.append(" ORDER BY TREE_SN ASC,CREATE_TIME DESC ");
        }
        return this.getEntityDao().findBySql(sql.toString(),params.toArray(),null);
    }
    
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
    public String saveOrUpdateForAssignId(Map<String,Object> colValues,String tableName,String assignId){
        return this.getEntityDao().saveOrUpdateForAssignId(colValues, tableName, assignId);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年10月16日 上午11:19:41
     * @param parentId
     * @param treeData
     * @param tableName
     * @param seqName
     * @param assignId
     * @return
     */
    private String saveOrUpdateTreeDateCode(String parentId,Map<String,Object> treeData,
            String tableName,String seqName,String assignId){
        String path = "";
        int level = 0;
        //获取私有主键名称
        String primaryKeyName = this.getPrimaryKeyName(tableName).get(0);
        if (StringUtils.isNotEmpty(parentId) && !parentId.equals("0")) {
            StringBuffer sql = new StringBuffer("select * from ").append(tableName).append(" WHERE ")
                    .append(primaryKeyName).append("=? ");
            Map<String,Object> parentData = this.getEntityDao().getByJdbc(sql.toString(), new Object[]{parentId});
            path = (String) parentData.get("PATH");
            level = Integer.parseInt(parentData.get("TREE_LEVEL").toString());
        } else {
            parentId = "0";
            path = "0.";
        }
        if (level < 1) {
            level = 1;
        }
        treeData.put("TREE_LEVEL", level + 1);
        treeData.put("PARENT_ID", parentId);
        int maxSn = this.getEntityDao().getMaxTreeSortSn(tableName);
        if (maxSn == 0) {
            maxSn = 1;
        }
        // 获取主键值
        String primaryKeyValue = (String) treeData.get(primaryKeyName); 
        if (StringUtils.isEmpty(primaryKeyValue)) {
            // 说明是新增操作
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            treeData.put("CREATE_TIME", createTime);
            treeData.put("TREE_SN", maxSn + 1);
            treeData.put("PATH",path);
        }
        String entityId = null;
        if (StringUtils.isNotEmpty(primaryKeyValue)) {
            if(StringUtils.isNotEmpty(seqName)){
                entityId = this.saveOrUpdate(treeData, tableName, primaryKeyValue,seqName);
            }else if(StringUtils.isNotEmpty(assignId)){
                entityId = this.saveOrUpdateForAssignId(treeData, tableName, assignId);
            }else{
                entityId = this.saveOrUpdate(treeData, tableName, primaryKeyValue);
            }
        } else {
            if(StringUtils.isNotEmpty(seqName)){
                entityId = this.saveOrUpdate(treeData, tableName, null,seqName);
            }else if(StringUtils.isNotEmpty(assignId)){
                entityId = this.saveOrUpdateForAssignId(treeData, tableName, assignId);
            }else{
                entityId = this.saveOrUpdate(treeData, tableName, null);
            }
        }
        if (!entityId.equals("-1")) {
            path = path + entityId + ".";
            treeData.put("PATH", path);
            this.saveOrUpdate(treeData, tableName, entityId);
        }
        return entityId;
    }
    
    /**
     * 更新或者保存树形数据
     * @param parentId
     * @param treeData
     */
    public String saveOrUpdateTreeDataForAssignId(String parentId,Map<String,Object> treeData
            ,String tableName,String assignId){
        return this.saveOrUpdateTreeDateCode(parentId, treeData, tableName, null, assignId);
    }
    /**
     * 更新或者保存树形数据
     * @param parentId
     * @param treeData
     */
    public String saveOrUpdateTreeData(String parentId,Map<String,Object> treeData,
            String tableName,String seqName){
        return this.saveOrUpdateTreeDateCode(parentId, treeData, tableName, seqName, null);
    }
    /**
     * 更新树形排序字段
     * @param tableName
     * @param dragTreeNodeId
     * @param dragTreeNodeNewLevel
     * @param targetNodeId
     * @param targetNodeLevel
     */
    public void updateTreeSn(String tableName,String dragTreeNodeId,
            int dragTreeNodeNewLevel,String targetNodeId, int targetNodeLevel,String moveType){
        getEntityDao().updateTreeSn(tableName, dragTreeNodeId,
                dragTreeNodeNewLevel, targetNodeId, targetNodeLevel,moveType);
    }
    
    /**
     * 
     * 描述 删除树形数据,包括子孙数据
     * @author Flex Hu
     * @created 2014年9月24日 下午2:43:20
     * @param tableName
     * @param recordId
     */
    public void removeTreeDataCascadeChild(String tableName,String recordId){
        getEntityDao().removeTreeDataCascadeChild(tableName, recordId);
    }
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
    public String saveOrUpdate(Map<String,Object> colValues,String tableName,String entityId,String seqName){
        return getEntityDao().saveOrUpdate(colValues, tableName, entityId, seqName);
    }
    
    /**
     * 
     * 描述 删除掉数据
     * @author Flex Hu
     * @created 2015年8月27日 上午11:00:47
     * @param tableName
     * @param colNames
     * @param colValues
     */
    public void remove(String tableName,String[] colNames,Object[]colValues){
        getEntityDao().remove(tableName, colNames, colValues);
    }
    
    /**
     * 
     * 描述 根据sql语句获取对象
     * @author Flex Hu
     * @created 2015年10月19日 下午2:47:15
     * @param sql
     * @param values
     * @return
     */
    public Object getObjectBySql(final String sql,final Object[] values){
        return getEntityDao().getObjectBySql(sql, values);
    }
}
