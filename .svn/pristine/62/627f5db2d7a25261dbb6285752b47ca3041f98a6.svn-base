/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.website.dao.XFDesignDao;
/**
 * 描述 消防设计dao层
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月9日 下午15:20:10
 */
@SuppressWarnings("rawtypes")
@Repository("xfDesignDao")
public class XFDesignDaoImpl extends BaseDaoImpl implements XFDesignDao {

    @Override
    public List<Map<String, Object>> findZrztxxList(String prj_code, String prj_num) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * ");
        sql.append(" FROM TB_FC_PROJECT_CORP_INFO T  ");
        sql.append(" WHERE 1=1 ");
        if(prj_code!=null && !"".equals(prj_code)) {
            sql.append(" AND T.PRJ_CODE = ? ");
            params.add(prj_code);
        }
        if(prj_num!=null && !"".equals(prj_num)) {
            sql.append(" AND T.PRJ_NUM = ? ");
            params.add(prj_num);
        }
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public List<Map<String, Object>> findDtjzwxxList(String prj_code, String prj_num) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * ");
        sql.append(" FROM TB_FC_UNIT_PROJECT_INFO T ");
        sql.append(" WHERE 1=1 ");
        if(prj_code!=null && !"".equals(prj_code)) {
            sql.append(" AND T.PRJ_CODE = ? ");
            params.add(prj_code);
        }
        if(prj_num!=null && !"".equals(prj_num)) {
            sql.append(" AND T.PRJ_NUM = ? ");
            params.add(prj_num);
        }
        return this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
    }

    @Override
    public String getPrjNum(String prjCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * ");
        sql.append(" FROM T_BSFW_GCJSSGXK T ");
        sql.append(" WHERE T.PROJECTCODE = ? ");
        params.add(prjCode);
        List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
        if(resultList!=null && resultList.size()>0) {
            Map<String, Object> map = resultList.get(0);
            if(map.get("PRJNUM")!=null) {
                return map.get("PRJNUM").toString();
            }
            return null;
        }
        return null;
    }

    @Override
    public Map<String, Object> findXfProjectInfo(String projectCode, String prjNum) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM TB_FC_PROJECT_INFO T ");
        sql.append(" WHERE T.PRJ_CODE = ? ");
        params.add(projectCode);
        if (prjNum != null && !"".equals(prjNum)) {
            sql.append(" AND T.PRJ_NUM = ? ");
            params.add(prjNum);
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);// 取最新的一条数据
        }
        return null;
    }
    
    

}
