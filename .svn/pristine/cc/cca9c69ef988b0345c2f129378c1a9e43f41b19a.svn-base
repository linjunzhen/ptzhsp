/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tzxm.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.tzxm.dao.GovIvestDao;
/**
 * @author Scolder Lin
 *
 */
@SuppressWarnings("rawtypes")
@Repository("govIvestDaoImpl")
public class GovIvestDaoImpl extends BaseDaoImpl implements GovIvestDao{
    public List<Map<String, Object>> getGovIvestTopList() {
        StringBuffer sql = new StringBuffer("SELECT T.ID, T.NAME ");
        sql.append(" FROM T_FLOW_CONFIG_CATEGORY T WHERE T.PARENT_ID= ");
        sql.append(" (SELECT C.ID FROM T_FLOW_CONFIG_CATEGORY C WHERE C.PARENT_ID = '0')");
        sql.append(" ORDER BY T.TREE_SN ");
        return this.jdbcTemplate.queryForList(sql.toString());
    }
    
    public List<Map<String, Object>> getTitleListById(String id) {
        StringBuffer sql = new StringBuffer("SELECT T.ID, T.NAME, T.PARENT_ID ");
        sql.append(" FROM T_FLOW_CONFIG_CATEGORY T WHERE T.PARENT_ID= ?");
        sql.append(" ORDER BY T.TREE_SN ");
        return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{id});
    }

    public List<Map<String, Object>> findCategoryChildList(String categoryId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.STAGE_ID AS ID, T.NAME ");
        sql.append(" FROM T_FLOW_CONFIG_STAGE T  ");
        sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = T.TYPE_ID ");
        sql.append(" WHERE 1=1 AND C.ID = ? ");
        params.add(categoryId);
        sql.append(" ORDER BY T.TREE_SN ASC, T.CREATE_TIME DESC ");
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    public List<Map<String, Object>> findItemList(String configLinkId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT R.*, ROWNUM RN FROM( ");
        sql.append("SELECT T.ID, T.ITEM_ID, F.ITEM_CODE, F.ITEM_NAME, D.DEPART_NAME AS SSZTMC ");
        sql.append(" FROM T_FLOW_CONFIG_ITEM T ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM F ON F.ITEM_ID = T.ITEM_ID");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=F.SSBMBM ");
        sql.append(" WHERE T.STAGE_ID = ? AND T.IS_OFF_SHELVES='0' ");
        sql.append(" ORDER BY T.SORT ASC, T.CREATE_TIME DESC) R ");
        params.add(configLinkId);
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    public List<Map<String, Object>> findHandleResultList(String textValue) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.PROJECT_CODE, X.PROJECT_NAME, ");
        sql.append(" S.ITEM_NAME, T.RUN_STATUS, T.END_TIME ");
        sql.append(" FROM JBPM6_EXECUTION T ");
        sql.append(" LEFT JOIN SPGL_XMJBXXB X ON T.PROJECT_CODE = X.PROJECT_CODE ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON T.ITEM_CODE = S.ITEM_CODE ");
        sql.append(" WHERE T.PROJECT_CODE IS NOT NULL ");
        sql.append(" AND T.RUN_STATUS NOT IN(0, 1) ");
        if(textValue != null && !"".equals(textValue)) {
            sql.append(" AND (X.PROJECT_CODE like ? OR X.PROJECT_NAME like ?) ");
            params.add("%"+textValue+"%");
            params.add("%"+textValue+"%");
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findHandleResultMap(String page, String rows, String textValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows)
                , Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.PROJECT_CODE, X.PROJECT_NAME, ");
        sql.append(" S.ITEM_NAME, T.RUN_STATUS, T.END_TIME ");
        sql.append(" FROM SPGL_XMJBXXB X ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION T ON T.PROJECT_CODE = X.PROJECT_CODE ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON T.ITEM_CODE = S.ITEM_CODE ");
        sql.append(" WHERE T.PROJECT_CODE IS NOT NULL ");
        sql.append(" AND T.RUN_STATUS NOT IN(0, 1) ");
        if(textValue != null && !"".equals(textValue)) {
            sql.append(" AND (X.PROJECT_CODE like ? OR X.PROJECT_NAME like ?) ");
            params.add("%"+textValue+"%");
            params.add("%"+textValue+"%");
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = this.findBySql(sql.toString(), params.toArray(), pb);
        map.put("total", pb.getTotalItems());
        map.put("list", list);
        return map;
    }

    public String findPoliciesRegulationsModuleId(String muduleName, String parentId) {
        StringBuffer sql = new StringBuffer("SELECT M.MODULE_ID ");
        sql.append(" FROM T_CMS_ARTICLE_MODULE M ");
        sql.append(" WHERE M.PARENT_ID = (SELECT T.MODULE_ID ");
        sql.append(" FROM T_CMS_ARTICLE_MODULE T ");
        sql.append(" WHERE T.MODULE_NAME = ?) ");
        sql.append(" AND M.MODULE_NAME = ? ");
        return this.jdbcTemplate.queryForObject(sql.toString(),new Object[]{parentId, muduleName},String.class);
    }

    public List<Map<String, Object>> findMenuListByModuleId(String moduleId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.MODULE_ID, T.MODULE_NAME ");
        sql.append(" FROM T_CMS_ARTICLE_MODULE T ");
        sql.append(" WHERE T.MODULE_ID = ? ");
        params.add(moduleId);
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findModuleInfo(String page,
            String rows, String moduleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows)
                , Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT TID, CONTENT_ID AS ITEMID, ");
        sql.append(" CONTENT_TITLE AS ITEMTITLE, ");
        sql.append(" TO_CHAR(TO_DATE(RELEASE_TIME, 'yyyy-mm-dd hh24:mi:ss'), ");
        sql.append(" 'yyyy-mm-dd') AS ITEMRELDATE  ");
        sql.append(" FROM VIEW_ARTICLE_CONTENT ");
        sql.append(" WHERE MODULE_ID IN( ");
        sql.append(" SELECT m1.MODULE_ID ");
        sql.append(" FROM T_CMS_ARTICLE_MODULE m1 ");
        sql.append(" WHERE m1.MODULE_STATUS = 1 ");
        sql.append(" AND m1.MODULE_DELETE = 0 ");
        sql.append(" START WITH m1.MODULE_ID = ? ");
        sql.append(" CONNECT BY PRIOR m1.MODULE_ID = m1.PARENT_ID) ");
        sql.append(" AND CONTENT_STATUS = 1 ");
        sql.append(" AND CONTENT_DELETE = 0 ");
        sql.append(" ORDER BY ISTOP DESC, RELEASE_TIME DESC ");
        params.add(moduleId);
        
        list = this.findBySql(sql.toString(), params.toArray(), pb);
        map.put("total", pb.getTotalItems());
        map.put("list", list);
        return map;
    }

}
