/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.projectflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.projectflow.dao.ProjectFlowDao;
import net.evecom.platform.projectflow.service.ProjectFlowService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述 工程建设项目流程管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("projectFlowService")
public class ProjectFlowServiceImpl extends BaseServiceImpl implements ProjectFlowService {
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectFlowDao dao;
    
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        // TODO Auto-generated method stub

        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,CT.TYPE_NAME,S.NAME AS STAGE_NAME from SPGL_XMJBXXB T ");
        sql.append(" JOIN T_FLOW_CONFIG_TYPE CT ON T.TYPE_ID = CT.TYPE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_STAGE S ON T.STAGE_ID=S.STAGE_ID ");
        sql.append(" JOIN SPGL_XMDWXXB X ON T.ID = X.JBXX_ID ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findConfigType() {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TYPE_ID, T.TYPE_NAME ");
        sql.append(" FROM T_FLOW_CONFIG_TYPE T ");
        sql.append(" ORDER BY T.SPLCLX ASC ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findExeIdListByProjectId(String projectId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.EXE_ID ");
        sql.append(" FROM JBPM6_EXECUTION T ");
        sql.append(" LEFT JOIN SPGL_XMJBXXB E ");
        sql.append(" ON T.PROJECT_CODE = E.PROJECT_CODE ");
        sql.append(" WHERE E.ID = ? ");
        params.add(projectId);
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findGeneralityMaterList(String entityId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_MSJW_PROJECT_FILE T ");
        sql.append(" LEFT JOIN SPGL_XMJBXXB X ");
        sql.append(" ON T.PROJECT_CODE = X.PROJECT_CODE ");
        sql.append(" WHERE X.ID = ? ");
        params.add(entityId);
        sql.append(" AND T.FILE_TYPE = '1' ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findElectronicMaterList(String entityId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_BSFW_GCJSSGXK T ");
        sql.append(" LEFT JOIN SPGL_XMJBXXB X ");
        sql.append(" ON T.PROJECTCODE = X.PROJECT_CODE ");
        sql.append(" WHERE X.ID = ? ");
        params.add(entityId);
        sql.append(" AND T.CERTIFICATEIDENTIFIERFILENAME IS NOT NULL ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findApplyMaterList(String entityId) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<Object> exeParams = new ArrayList<Object>();
        StringBuffer exeSql = new StringBuffer("SELECT E.EXE_ID ");
        exeSql.append(" FROM JBPM6_EXECUTION E ");
        exeSql.append(" LEFT JOIN SPGL_XMJBXXB X ");
        exeSql.append(" ON E.PROJECT_CODE = X.PROJECT_CODE ");
        exeSql.append(" WHERE X.ID = ? ");
        exeParams.add(entityId);
        List<Map<String, Object>> exeIdList = dao.findBySql(exeSql.toString(), exeParams.toArray(), null);
        if(exeIdList!=null && exeIdList.size()>0) {
            for(Map<String, Object> exeMap : exeIdList) {
                if(exeMap.get("EXE_ID")!=null) {
                    String exeId = (String)exeMap.get("EXE_ID");
                    List<Object> params = new ArrayList<Object>();
                    StringBuffer sql = new StringBuffer("SELECT T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.CREATE_TIME ");
                    sql.append(" FROM T_WSBS_APPLYMATER T ");
                    sql.append(" WHERE T.MATER_ID IN ");
                    sql.append("(SELECT M.MATER_ID FROM JBPM6_FLOW_USER_MATER M ");
                    sql.append(" WHERE M.EXEID= ? ");
                    sql.append(" AND M.STATUS = 1 ) ");
                    sql.append(" ORDER BY T.CREATE_TIME DESC ");
                    params.add(exeId);
                    List<Map<String, Object>> applyMaterList = dao.findBySql(sql.toString(), params.toArray(), null);
                    if(applyMaterList!=null && applyMaterList.size()>0) {
                        for(Map<String, Object> applyMaterMap : applyMaterList) {
                            resultList.add(applyMaterMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findFlowMaterList(String entityId) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<Object> exeParams = new ArrayList<Object>();
        StringBuffer exeSql = new StringBuffer("SELECT E.EXE_ID ");
        exeSql.append(" FROM JBPM6_EXECUTION E ");
        exeSql.append(" LEFT JOIN SPGL_XMJBXXB X ");
        exeSql.append(" ON E.PROJECT_CODE = X.PROJECT_CODE ");
        exeSql.append(" WHERE X.ID = ? ");
        exeParams.add(entityId);
        List<Map<String, Object>> exeIdList = dao.findBySql(exeSql.toString(), exeParams.toArray(), null);
        if(exeIdList!=null && exeIdList.size()>0) {
            for(Map<String, Object> exeMap : exeIdList) {
                if(exeMap.get("EXE_ID")!=null) {
                    String exeId = (String)exeMap.get("EXE_ID");
                    List<Map<String, Object>> flowMaterList = fileAttachService.findByExeId(exeId,"-1");
                    if(flowMaterList!=null && flowMaterList.size()>0) {
                        for(Map<String, Object> flowMaterMap : flowMaterList) {
                            resultList.add(flowMaterMap);
                        }
                    }
                }
            }
        }
        return resultList;
    }
}
