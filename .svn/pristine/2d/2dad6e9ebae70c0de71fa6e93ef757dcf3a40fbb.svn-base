/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.project.dao.ProjectFinishManageDao;
/**
 * 描述 竣工验收备案信息Dao
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月17日 上午10:43:15
 */
@SuppressWarnings("rawtypes")
@Repository("projectFinishManageDao")
public class ProjectFinishManageDaoImpl extends BaseDaoImpl implements ProjectFinishManageDao {
    public Map<String, Object> findExeInfo(String projectCode, String itemCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM JBPM6_EXECUTION T ");
        sql.append(" WHERE T.PROJECT_CODE = ? ");
        params.add(projectCode);
        if (itemCode != null && !"".equals(itemCode)) {
            sql.append(" AND T.ITEM_CODE = ? ");
            params.add(itemCode);
        }
        //强制取工程建设施工许可表信息
        sql.append(" AND T.BUS_TABLENAME = 'T_BSFW_GCJSSGXK' ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Map<String, Object>> resultList = this.jdbcTemplate.queryForList(sql.toString(), params.toArray());
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);// 取最新的一条数据
        }
        return null;
    }
}
