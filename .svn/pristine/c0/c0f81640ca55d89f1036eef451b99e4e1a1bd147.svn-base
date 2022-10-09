/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.MaterConfigDao;

/**
 * 描述  审批材料操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("materConfigDao")
public class MaterConfigDaoImpl extends BaseDaoImpl implements MaterConfigDao {
    
    /**
     * 
     * 描述 根据流程定义ID和环节名称获取最大排序值
     * @author Flex Hu
     * @created 2016年1月29日 上午9:58:32
     * @param defId
     * @param nodeName
     * @return
     */
    public int getMaxSn(String defId,String nodeName){
        StringBuffer sql = new StringBuffer("SELECT MAX(T.CONFIG_SN) FROM ");
        sql.append("JBPM6_MATERCONFIG T WHERE T.DEF_ID=? AND T.NODE_NAME=? ");
        return this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{defId,nodeName});
    }
    
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] configIds){
        int[] oldSns = new int[configIds.length];
        StringBuffer sql = new StringBuffer("select CONFIG_SN FROM JBPM6_MATERCONFIG ")
            .append(" WHERE CONFIG_ID=? ");
        for (int i = 0; i < configIds.length; i++) {
            int dicSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { configIds[i] });
            oldSns[i] = dicSn;
        }
        int[] newSns = ArrayUtil.sortByAsc(oldSns);
        StringBuffer updateSql = new StringBuffer("update JBPM6_MATERCONFIG T ")
                .append(" SET T.CONFIG_SN=? WHERE T.CONFIG_ID=? ");
        for (int i = 0; i < configIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], configIds[i] });
        }
    }

    /**
     * 
     * 描述 设置是否回填
     * @author Faker Li
     * @created 2016年4月1日 下午3:39:31
     * @param isBackfill
     * @param configIds
     */
    public void updateIsBackfill(String isBackfill, String configIds) {
        StringBuffer sql = new StringBuffer("update JBPM6_MATERCONFIG");
        sql.append(" M SET M.IS_BACKFILL = ? WHERE  M.CONFIG_ID IN ");
        sql.append(StringUtil.getValueArray(configIds));
        this.jdbcTemplate.update(sql.toString(),new Object[]{Integer.parseInt(isBackfill)});
    }
}
