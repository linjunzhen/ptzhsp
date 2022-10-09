/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.bsfw.dao.BdcApplyDao;

/**
 * 描述 不动产业务相关的dao
 * @author Keravon Feng
 * @created 2018年11月23日 下午4:11:45
 */
@Repository("bdcApplyDao")
public class BdcApplyDaoImpl extends BaseDaoImpl implements BdcApplyDao {
    
    /**
     * 
     * 描述 判断字典的名称是否存在
     * @author Keravon Feng
     * @created 2019年3月22日 下午5:15:37
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId, String dicName) {
        StringBuffer sql = new StringBuffer("select count(*) from")
                .append(" T_MSJW_SYSTEM_DICTIONARY WHERE TYPE_ID=? AND DIC_NAME=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[] { typeId, dicName });
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }

}
