/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.system.dao.GlobalUrlDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年6月22日 上午11:35:01
 */
@Repository("globalUrlDao")
public class GlobalUrlDaoImpl extends BaseDaoImpl implements GlobalUrlDao {

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 下午3:36:29
     * @return
     */
    public List<String> findByFilterType(String filterType){
        StringBuffer sql = new StringBuffer("select T.URL_ADDRESS from");
        sql.append(" T_SYSTEM_GLOBALURL T WHERE T.URL_FILTERTYPE=? ORDER BY T.URL_CREATETIME DESC");
        List<String> urls = this.jdbcTemplate.queryForList(sql.toString(),new Object[]{filterType}, String.class);
        return urls;
    }
}
