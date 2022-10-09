/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyqyz.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.callyqyz.dao.CallYqyzDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 描述:
 *
 * @author Madison You
 * @created 2019-12-26 17:30:00
 */
@Repository("callYqyzDao")
public class CallYqyzDaoImpl extends BaseDaoImpl implements CallYqyzDao {


    /**
     * 描述:一企一证添加业务
     *
     * @author Madison You
     * @created 2019/12/27 8:58:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getYqyzBusinessCode(String yqyzqhId) {
        StringBuffer sql = new StringBuffer("select WMSYS.WM_CONCAT(T.BUSINESS_CODE) BUSINESS_CODE ");
            sql.append(" from T_CKBS_YQYZQH_BUS T ");
        sql.append("WHERE T.YQYZQH_ID=? ");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { yqyzqhId });
    }
}
