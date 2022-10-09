/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.platform.system.dao.FormValidateDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2015-3-23 上午11:21:31
 */
@Repository("formValidateDao")
public class FormValidateDaoImpl extends BaseDaoImpl implements FormValidateDao {

    /**
     * 描述
     * @author Danto Huang
     * @created 2015-3-23 上午11:21:31
     * @param ruleId
     */
    @Override
    public void removeByFormId(String fromId) {
        StringBuffer deleteDic = new StringBuffer("delete from T_MSJW_SYSTEM_VALIDATE_RULE D")
                .append(" WHERE D.FORM_ID=? ");
        this.jdbcTemplate.update(deleteDic.toString(), new Object[] { fromId });
        this.remove("T_MSJW_SYSTEM_VALIDATE_FORM", "FORM_ID", new Object[] { fromId });
    }

    /**
     * 
     * 描述 获取某个表单验证规则的最大排序
     * @author Danto Huang
     * @created 2015-7-22 下午4:15:27
     * @param fromId
     * @return
     * @see net.evecom.platform.system.dao.FormValidateDao#getMaxSn(java.lang.String)
     */
    public int getMaxSn(String fromId){
        StringBuffer sql = new StringBuffer("select max(t.RULE_SN) FROM T_MSJW_SYSTEM_VALIDATE_RULE t"
                + "").append(" WHERE t.FORM_ID=? ");
        return this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{fromId});
    }
    /**
     * 
     * 描述  更新排序字段
     * @author Danto Huang
     * @created 2015-7-22 下午4:19:47
     * @param ruleIds
     */
    public void updateSn(String[] ruleIds){
        int[] oldSns = new int[ruleIds.length];
        StringBuffer sql = new StringBuffer("select RULE_SN FROM T_MSJW_SYSTEM_VALIDATE_RULE ")
                .append(" WHERE RULE_ID=? ");
        for (int i = 0; i < ruleIds.length; i++) {
            int ruleSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { ruleIds[i] });
            oldSns[i] = ruleSn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer("update T_MSJW_SYSTEM_VALIDATE_RULE T ")
                .append(" SET T.RULE_SN=? WHERE T.RULE_ID=? ");
        for (int i = 0; i < ruleIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], ruleIds[i] });
        }
    }
}
