/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.dao.impl;


import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.ArrayUtil;
import net.evecom.platform.commercial.dao.ExtraDictionaryDao;

import org.springframework.stereotype.Repository;

/**
 * 
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 下午3:16:58
 */
@Repository("extraDictionaryDao")
public class ExtraDictionaryDaoImpl extends BaseDaoImpl implements ExtraDictionaryDao {

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:28:12
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId, String dicCode) {
        StringBuffer sql = new StringBuffer("select count(*) from")
                .append(" T_COMMERCIAL_DIC WHERE TYPE_ID=? AND DIC_CODE=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[] { typeId, dicCode });
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:30:36
     * @param typeId
     * @return
     */
    public int getMaxSn(String typeId){
        StringBuffer sql = new StringBuffer("select max(DIC_SN) FROM T_COMMERCIAL_DIC "
                + "").append(" WHERE TYPE_ID=? ");
        return this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{typeId});
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午4:29:13
     * @param dicIds
     */
    public void updateSn(String[] dicIds){
        int[] oldSns = new int[dicIds.length];
        StringBuffer sql = new StringBuffer("select DIC_SN FROM T_COMMERCIAL_DIC ").append(" WHERE DIC_ID=? ");
        for (int i = 0; i < dicIds.length; i++) {
            int dicSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { dicIds[i] });
            oldSns[i] = dicSn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer("update T_COMMERCIAL_DIC T ")
                .append(" SET T.DIC_SN=? WHERE T.DIC_ID=? ");
        for (int i = 0; i < dicIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], dicIds[i] });
        }
    }
}
