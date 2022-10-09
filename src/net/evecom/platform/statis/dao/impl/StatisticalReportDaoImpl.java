/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.statis.dao.StatisticalReportDao;

/**
 * 
 * 描述
 * @author Faker Li
 * @created 2016年3月11日 上午9:38:19
 */
@Repository("statisticalReportDao")
public class StatisticalReportDaoImpl extends BaseDaoImpl implements StatisticalReportDao{

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月11日 上午10:12:55
     * @param time
     * @param sqfs
     * @return
     */
    public int getBjsByTime(String time, String sqfs) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_EXECUTION e ");
        sql.append(" left join t_Wsbs_Serviceitem s on e.item_code = s.item_code ");
        sql.append(" where (substr(e.create_time,0,10) = substr(e.end_time,0,10) or s.sxxz = '1') ");
        sql.append(" and e.item_code is not null ");
        if(StringUtils.isNotEmpty(time)){
            sql.append("AND  e.CREATE_TIME LIKE '"+time+"%'");
        }
        if(StringUtils.isNotEmpty(sqfs)){
            sql.append("AND  e.SQFS = ?" );
            params.add(sqfs);
        }
        sql.append("AND E.ITEM_CODE IS NOT NULL");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),params.toArray());
        return count;
    }

}
