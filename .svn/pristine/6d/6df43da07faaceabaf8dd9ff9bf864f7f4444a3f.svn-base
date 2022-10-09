/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.platform.bespeak.dao.BespeakOnlineDao;

/**
 * 描述
 * @author Panda Chen
 * @created 2016-12-1 下午05:15:43
 */
@Repository("bespeakOnlineDaoImpl")
public class BespeakOnlineDaoImpl extends BaseDaoImpl implements BespeakOnlineDao {
    /**
     * 
     * 描述更新排序字段
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:33:27
     * @param column
     * @param tablename
     * @param configIds
     */
    public void updateSn(String column, String tablename, String[] configIds) {
        int[] oldSns = new int[configIds.length];
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(column).append(" FROM  ").append(tablename);
        sql.append(" WHERE ID=? ");
        for (int i = 0; i < configIds.length; i++) {
            int configSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { configIds[i] });
            oldSns[i] = configSn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer();
        updateSql.append("update ").append(tablename).append("  T ");
        updateSql.append(" SET T.");
        updateSql.append(column);
        updateSql.append("=? WHERE T.ID=? ");
        for (int i = 0; i < configIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], configIds[i] });
        }
    }
    /**
     * 
     * 描述获取最大排序
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:44:04
     * @param tablename
     * @param column
     * @return
     */
    public int getMaxSn(String tablename, String column) {
        StringBuffer sql = new StringBuffer();
        sql.append("select max(").append(column).append(") FROM  ").append(tablename);
        return this.jdbcTemplate.queryForInt(sql.toString(), null);
    }
}
