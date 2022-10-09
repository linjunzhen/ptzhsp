/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.fjfda.dao.FoodManagementDao;

/**
 * 描述  食品经营操作dao
 * @author  Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("foodManagementDao")
public class FoodManagementDaoImpl extends BaseDaoImpl implements FoodManagementDao {

    /**
     * 描述 根据经营场所获取条数
     * @author Faker Li
     * @created 2016年7月15日 下午1:18:59
     * @param jYCS
     * @return
     */
    @Override
    public int getCountNumByJycs(String jYCS) {
        StringBuffer sql =  new StringBuffer("select count(*) from T_FJFDA_SPJYXKXX t where t.jycs = ? ");
        return this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{jYCS});
    }

    /**
     * 描述 根据经营地址获取结果数据
     * @author Faker Li
     * @created 2016年7月29日 下午1:03:17
     * @param string
     * @return
     */
    @Override
    public Map<String, Object> getSpjyxkxxByJycs(String jycs,String exe_id) {
        List<String> params = new ArrayList<String>();
        StringBuffer sql =  new StringBuffer("select t.* from T_FJFDA_SPJYXKXX t where t.jycs = ? and t.XKZZT = '10'");
        sql.append("  AND T.COME_FROM IN ('0','1')  ");
        params.add(jycs);
        if(StringUtils.isNotEmpty(exe_id)){
            sql.append(" and t.JBXX_ID not in (select e.BUS_RECORDID from JBPM6_EXECUTION e where e.EXE_ID = ? )");
            params.add(exe_id);
        }
        return this.getByJdbc(sql.toString(), params.toArray());
    }

}
