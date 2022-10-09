/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.project.dao.ProjectWebsiteApplyDao;

/**
 * 描述  投资项目申报操作dao
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年6月19日 上午17:49:12
 */
@Repository("projectWebsiteApplyDao")
public class ProjectWebsiteApplyDaoImpl extends BaseDaoImpl implements ProjectWebsiteApplyDao {

    public List<Map<String, Object>> findProjectList(String textValue, String projectName, String projectCode) { 
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql=new StringBuffer("SELECT T.ID, T.PROJECT_CODE, T.PROJECT_NAME, T.CREATE_TIME, PROJECT_TYPE ");
        sql.append(" FROM SPGL_XMJBXXB T WHERE 1=1 "); 
        if(textValue!=null && !"".equals(textValue)) {
            sql.append(" AND (T.PROJECT_CODE LIKE ? OR T.PROJECT_NAME LIKE ?) ");
            params.add("%"+textValue+"%");
            params.add("%"+textValue+"%");
        }
        if(projectName !=null && !"".equals(projectName)) {
            sql.append(" AND T.PROJECT_NAME LIKE ? ");
            params.add("%"+projectName+"%");
        }
        if(projectCode !=null && !"".equals(projectCode)) {
            sql.append(" AND T.PROJECT_CODE LIKE ? ");
            params.add("%"+projectCode+"%");
        }
        List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
        return resultList;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findProjectMap(String page, String rows, String textValue, String projectName,
            String projectCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows)
                , Integer.parseInt(rows));
        
        StringBuffer sql=new StringBuffer("SELECT T.ID, T.PROJECT_CODE, T.PROJECT_NAME, T.CREATE_TIME, PROJECT_TYPE ");
        sql.append(" FROM SPGL_XMJBXXB T WHERE 1=1 "); 
        if(textValue!=null && !"".equals(textValue)) {
            sql.append(" AND (T.PROJECT_CODE LIKE ? OR T.PROJECT_NAME LIKE ?) ");
            params.add("%"+textValue+"%");
            params.add("%"+textValue+"%");
        }
        if(projectName !=null && !"".equals(projectName)) {
            sql.append(" AND T.PROJECT_NAME LIKE ? ");
            params.add("%"+projectName+"%");
        }
        if(projectCode !=null && !"".equals(projectCode)) {
            sql.append(" AND T.PROJECT_CODE LIKE ? ");
            params.add("%"+projectCode+"%");
        }
        list = this.findBySql(sql.toString(), params.toArray(), pb);
        map.put("total", pb.getTotalItems());
        map.put("list", list);
        return map;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMaterListByProjectCode(String projectCode, String type) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_MSJW_PROJECT_FILE T ");
        sql.append(" WHERE T.PROJECT_CODE = ? ");
        sql.append(" AND T.FILE_TYPE = ? ");
        sql.append(" ORDER BY T.CATEGORY_ID ");
        params.add(projectCode);
        params.add(type);
        return this.findBySql(sql.toString(), params.toArray(), null);
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findSgxkMaterListByProjectCode(String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_BSFW_GCJSSGXK T ");
        sql.append(" WHERE T.PROJECT_CODE = ? ");
        sql.append(" AND T.CERTIFICATEIDENTIFIERFILEID is not null ");
        params.add(projectCode);
        return this.findBySql(sql.toString(), params.toArray(), null);
    }    
    
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMaterList(String categoryId, String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_MSJW_PROJECT_FILE T ");
        sql.append(" WHERE T.CATEGORY_ID = ? ");
        sql.append(" AND T.PROJECT_CODE = ? ");
        sql.append(" AND T.FILE_TYPE = 1 ");
        sql.append(" ORDER BY T.CATEGORY_ID ");
        params.add(categoryId);
        params.add(projectCode);
        return this.findBySql(sql.toString(), params.toArray(), null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findExecuteInfo(String itemCode, String projectCode) {
        String sql = "SELECT * FROM JBPM6_EXECUTION T WHERE T.ITEM_CODE = ? AND T.PROJECT_CODE = ?";
        Map<String, Object> map =  this.getByJdbc(sql, new Object[]{itemCode, projectCode});
        if(null ==map){
            sql = "SELECT * FROM JBPM6_EXECUTION_EVEHIS T WHERE T.ITEM_CODE = ? AND T.PROJECT_CODE = ?";
            map =  this.getByJdbc(sql, new Object[]{itemCode, projectCode});
        }
        return map;
    }
    
    public String findMaxTokenSortNumber() {
        String sql = "SELECT COUNT(T.SORT_NUMBER) FROM T_MSJW_SYSTEM_TOKEN T ";
        BigDecimal count = (BigDecimal)this.getObjectBySql(sql, null);
        return String.valueOf(count);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findStageInfo(String exeId, String projectCode) {
        StringBuffer sql = new StringBuffer("SELECT S.* FROM T_FLOW_CONFIG_STAGE S ");
        sql.append(" LEFT JOIN T_FLOW_CONFIG_ITEM I ON I.STAGE_ID = S.STAGE_ID ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM SI ON SI.ITEM_ID = I.ITEM_ID ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON E.ITEM_CODE = SI.ITEM_CODE ");
        sql.append(" LEFT JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = S.TYPE_ID ");
        sql.append(" LEFT JOIN SPGL_XMJBXXB X ON X.FLOW_CATE_ID = C.ID ");
        sql.append(" WHERE E.EXE_ID = ? ");
        sql.append(" AND X.PROJECT_CODE = ? ");
        return this.getByJdbc(sql.toString(), new Object[]{exeId, projectCode});
    }

}
