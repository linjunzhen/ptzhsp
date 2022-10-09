/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.platform.hd.dao.OsQuestionDao;

/**
 * 描述  调查问题操作dao
 * @author  Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("osQuestionDao")
public class OsQuestionDaoImpl extends BaseDaoImpl implements OsQuestionDao {

    @Override
    public void updateSn(String[] ids, String table, String columnName,String keyId) {
        int[] oldSns = new int[ids.length];
        StringBuffer sql = new StringBuffer("select "+columnName+" FROM "+table+" ").append(" WHERE "+keyId+"=? ");
        for (int i = 0; i < ids.length; i++) {
            int sn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { ids[i] });
            oldSns[i] = sn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer("update "+table+" T ")
                .append(" SET T."+columnName+"=? WHERE T."+keyId+"=? ");
        for (int i = 0; i < ids.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], ids[i] });
        }
    }

}
