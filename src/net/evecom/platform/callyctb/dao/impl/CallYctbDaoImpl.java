/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyctb.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.callyctb.dao.CallYctbDao;

/**
 * 描述 一窗通办操作dao
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("callYctbDao")
public class CallYctbDaoImpl extends BaseDaoImpl implements CallYctbDao {

    @Override
    public Map<String, Object> getBusinessCode(String yctbqhId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select WMSYS.WM_CONCAT(T.BUSINESS_CODE) BUSINESS_CODE ");
        sql.append(" from T_CKBS_YCTBQH_BUS T ");
        sql.append("WHERE T.YCTBQH_ID=? ");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { yctbqhId });
    }

}
