/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.wsbs.dao.UserProjectDao;
import net.evecom.platform.wsbs.service.UserProjectService;
/**
 * @author Scolder Lin
 *
 */
@SuppressWarnings("rawtypes")
@Service("userProjectService")
public class UserProjectServiceImpl extends BaseServiceImpl implements UserProjectService{
    /**
     * 引入dao
     */
    @Resource
    private UserProjectDao dao;
    
    /**
     * 属性jdbcTemplate
     */
    protected JdbcTemplate jdbcTemplate;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findfrontList(String page, String rows, String yhzh) {
        Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("select T.ID, T.PROJECT_CODE, T.PROJECT_NAME, T.FLOW_CATE_ID, ");
        sql.append(" T.FLOW_CATE_NAME, T.CREATE_TIME ");
        sql.append(" FROM SPGL_XMJBXXB t ");
        sql.append(" WHERE T.CREATOR_ID = ? ");
        params.add(yhzh);
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select S.STAGE_ID AS ID, S.NAME,T.ITEM_ID,T.ITEM_CODE,T.ITEM_NAME,T.CNQXGZR,T.THYJ,T.AUDITING_NAMES,");
        sql.append(" T.BACKOR_NAME,T.C_SN,D.DEPART_NAME,DIC1.DIC_NAME AS SXXZ,DIC2.DIC_NAME AS SXLX,");
        sql.append(" T.FWSXZT,J.DEF_KEY,T.BUSINESS_CODE,L.IS_KEY_ITEM,T.ITEM_REMARK from T_WSBS_SERVICEITEM T ");
        sql.append(" JOIN T_FLOW_CONFIG_ITEM L ON T.ITEM_ID = L.ITEM_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_STAGE S   ON S.STAGE_ID = L.STAGE_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_CATEGORY C ON C.FLOW_TYPE_ID = S.TYPE_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=T.SSBMBM ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC1 ON DIC1.DIC_CODE=T.SXXZ ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC2 ON DIC2.DIC_CODE=T.SXLX ");
        sql.append("LEFT JOIN JBPM6_FLOWDEF J ON J.DEF_ID=T.BDLCDYID ");
        sql.append(" LEFT JOIN (select CATALOG_CODE,D1.DEPART_ID,D1.DEPART_CODE from t_wsbs_serviceitem_catalog C ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D1 ON D1.DEPART_ID=C.CHILD_DEPART_ID) DC ");
        sql.append("ON DC.CATALOG_CODE=T.CATALOG_CODE ");
        sql.append(
                "WHERE DIC1.TYPE_CODE='ServiceItemXz' AND DIC2.TYPE_CODE='ServiceItemType' AND L.IS_OFF_SHELVES='0' ");
        sql.append("AND T.FWSXZT='1'");

        String PROJECT_CODE = sqlFilter.getRequest().getParameter("PROJECT_CODE");

        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        
        //2021年11月24日 15:17:50  修改获取方式
        String projectSql = "select * from (select t.exe_id,t.RUN_STATUS,r.check_status,"
                + "0 as isFiled,t.create_time,t.HANDLE_OVERTIME,t.ITEM_CODE from JBPM6_EXECUTION t "
                + " left join T_WSBS_PROJECT_RECALL r on t.exe_id = r.exe_id " + "where t.PROJECT_CODE = ? "
                + " union all " + "select t.exe_id,t.RUN_STATUS,r.check_status,1 as isFiled,t.create_time,"
                + "t.HANDLE_OVERTIME,t.ITEM_CODE from JBPM6_EXECUTION_EVEHIS t "
                + " left join T_WSBS_PROJECT_RECALL r on t.exe_id = r.exe_id "
                + "where t.PROJECT_CODE = ? ) a  order by a.create_time desc";
        List<Map<String, Object>> exeList = dao.findBySql(projectSql, new Object[] { PROJECT_CODE, PROJECT_CODE },
                null);
        for (Map<String, Object> map : list) {
            String ITEM_CODE = map.get("ITEM_CODE").toString();
            boolean flag = false;
            String EXE_ID = "";
            String RUN_STATUS = "";
            String ISFILED = "";
            for (Map<String, Object> exeMap : exeList) {
                String EXE_ITEM_CODE = exeMap.get("ITEM_CODE").toString();
                if (EXE_ITEM_CODE.equals(ITEM_CODE)) {
                    flag = true;
                    EXE_ID = StringUtil.getString(exeMap.get("EXE_ID"));
                    RUN_STATUS = StringUtil.getString(exeMap.get("RUN_STATUS"));
                    ISFILED = StringUtil.getString(exeMap.get("ISFILED"));
                    break;
                }
            }
            if (flag) {
                map.put("EXE_ID", EXE_ID);
                map.put("STATE", RUN_STATUS);
                map.put("ISFILED", ISFILED);
            } else {
                map.put("EXE_ID", "");
                map.put("STATE", "-1");
            }
        }
        return list;
    }

    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findProjectItemList(String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.ITEM_ID FROM T_MSJW_PROJECTITEM T ");
        sql.append(" WHERE T.PROJECT_CODE = ?");
        sql.append(" AND T.IS_CHECK = ?");
        params.add(projectCode);
        params.add("1");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    
    public void deleteProjectItemInfo(String itemId, String projectCode) {
        String sql = "DELETE FROM T_MSJW_PROJECTITEM T WHERE T.ITEM_ID=? AND T.PROJECT_CODE=?";
        this.jdbcTemplate.update(sql.toString(), new Object[] { itemId, projectCode });
    }

    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findItemIdList(String categoryId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.ITEM_ID FROM T_WSBS_SERVICEITEM T ");
        sql.append(" JOIN T_FLOW_CONFIG_ITEM L ON T.ITEM_ID = L.ITEM_ID ");
        sql.append(" JOIN T_FLOW_CONFIG_STAGE S ON S.STAGE_ID = L.STAGE_ID ");
        sql.append(" WHERE T.FWSXZT = '1' ");
        sql.append(" AND T.FWSXMXLB != 2 ");
        sql.append(" AND L.IS_KEY_ITEM = 1 ");
        sql.append(" AND S.STAGE_ID = ? ");
        params.add(categoryId);
        
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    
    @SuppressWarnings("unchecked")
    public boolean findFileIsExist(String projectFileId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FILE_ID FROM T_MSJW_PROJECT_FILE T ");
        sql.append(" WHERE T.PROJECT_FILE_ID = ? ");
        params.add(projectFileId);
        List<Map<String, Object>> resultList = dao.findBySql(sql.toString(), params.toArray(), null);
        if(resultList!=null && resultList.size()>0) {
            return true;
        }
        return false;
    }

}
