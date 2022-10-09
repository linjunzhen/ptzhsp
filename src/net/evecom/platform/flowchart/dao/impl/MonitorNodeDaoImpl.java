/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.model.TableInfo;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.flowchart.dao.MonitorNodeDao;

/**
 * 描述 监察点dao实现类
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Repository("monitorNodeDao")
public class MonitorNodeDaoImpl extends BaseDaoImpl implements MonitorNodeDao {

    /**
     * table name
     */
    private static final String TABLE_NAME = "T_LCJC_BUS_MONITOR_NODE";

    @Override
    public void factorSubmit(String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        //更新监察点数据状态
        StringBuffer updateSql2 = new StringBuffer("update ")
                .append(" t_lcjc_bus_process set status=1,update_user=?,update_time=?"
                        + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql2.toString(), new Object[] { userId,
            date, busCode });
    }

    @Override
    public void changeFactorSubmit(String busCode, String userId) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update ")
                .append(" T_LCJC_BUS_MONITOR_NODE set status=1,update_user=?,update_time=? "
                        + " where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql.toString(), new Object[] { userId,
            date, busCode });
        
        StringBuffer updateSql2 = new StringBuffer("update ")
                .append(" t_lcjc_bus_process set status=1,update_user=?,update_time=?"
                        + "where tache_code in (select tache_code from T_LCJC_BUS_TACHE where bus_code=?)");
        this.jdbcTemplate.update(updateSql2.toString(), new Object[] { userId,
            date, busCode });
    }

    @Override
    public List<Map<String, Object>> findByProcessCode(String processCode) {
        String sql="select r.*,a.dic_code,case when a.dic_name is null then '' else a.dic_name end ";
        sql+=" from t_lcjc_bus_ruleconfig r left join ";
        sql+="(select dic_code,dic_name from t_lcjc_system_dictionary t where t.type_id in(";
        sql+="select ty.type_id from t_lcjc_system_dictype ty where type_code='EsuperType')) a ";
        sql+="on r.analysis_type=a.dic_code  where r.PROCESS_CODE=?  ORDER BY update_TIME ASC";
        return this.jdbcTemplate.queryForList(sql,new Object[]{processCode});
    }

}
