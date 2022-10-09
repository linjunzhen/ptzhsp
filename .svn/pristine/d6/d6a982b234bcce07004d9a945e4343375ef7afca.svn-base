/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.QueryButtonAuthDao;

/**
 * 描述  查询按钮权限操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("queryButtonAuthDao")
public class QueryButtonAuthDaoImpl extends BaseDaoImpl implements QueryButtonAuthDao {

    /**
     * 
     * 描述 判断数据是否存在
     * @author Flex Hu
     * @created 2016年1月12日 上午10:21:02
     * @param defId
     * @param flowVersion
     * @param buttonKey
     * @return
     */
    public boolean isExists(String defId,int flowVersion,String buttonKey){
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append("JBPM6_QUERYBUTTONAUTH J WHERE J.DEF_ID=? ");
        sql.append("AND J.BUTTON_KEY=? AND J.FLOW_VERSION=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{defId,buttonKey,flowVersion});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
}
