/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchange.dao.BusSpecialChangeDao;

/**
 * 描述 业务专项
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busSpecialChangeDao")
public class BusSpecialChangeDaoImpl extends BaseDaoImpl implements BusSpecialChangeDao {
    /**
     * 描述 根据业务编码查询业务环节列表
     * 
     * @author Toddle Chen
     * @created Aug 5, 2015 11:07:34 AM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBuscode(String busCode) {
        StringBuffer sql = new StringBuffer("select * FROM T_LCJC_BUS_TACHE_CHANGE ");
        sql.append(" WHERE BUS_CODE=? ORDER BY CREATE_TIME DESC");
        return this.findBySql(sql.toString(), new Object[] { busCode }, null);
    }

    /**
     * 描述 根据sqlfilter获取到业务专项列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:28 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByUnitCode(String unitCode) {
        StringBuffer sql = new StringBuffer("select T.BUS_ID,T.BUS_CODE,T.BUS_NAME ");
        sql.append(" FROM T_LCJC_BUS_SPECIAL_CHANGE T ");
        if (StringUtils.isNotBlank(unitCode)) {
            sql.append(" WHERE UNIT_CODE=? ORDER BY TREE_SN ASC");
            return this.findBySql(sql.toString(), new Object[] { unitCode }, null);
        } else {
            sql.append(" ORDER BY TREE_SN ASC");
            return this.findBySql(sql.toString(), null, null);
        }
    }

    /**
     * 描述 根据业务编码查询业务对象
     * 
     * @author Toddle Chen
     * @created Aug 8, 2015 10:07:54 PM
     * @param busCode
     * @return
     */
    public Map<String, Object> getBusByBuscode(String busCode) {
        Map<String, Object> busMap = null;
        StringBuffer sql = new StringBuffer("select T.* FROM T_LCJC_BUS_SPECIAL_CHANGE T ");
        sql.append(" where T.BUS_CODE=? ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[] { busCode }, null);
        if (list.size() > 0) {
            busMap = list.get(0);
        }
        return busMap;
    }

    /**
     * 发起变更
     * 
     * @author John Zhang
     * @created 2015-9-2 上午11:30:19
     * @param busCode
     * @param changeCode
     */
    public void goChange(String applyId, String busCode, String changeCode) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_GoChange('" + applyId + "','" + busCode + "','"
                + changeCode + "')");
    }
}
