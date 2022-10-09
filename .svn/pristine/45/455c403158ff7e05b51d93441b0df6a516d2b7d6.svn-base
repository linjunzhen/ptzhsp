/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.HistDeployDao;

/**
 * 描述  流程历史部署操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("histDeployDao")
public class HistDeployDaoImpl extends BaseDaoImpl implements HistDeployDao {
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_HIST_DEPLOY R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
    }
    
    /**
     * 
     * 描述 删除掉数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:49:23
     * @param defId
     * @param flowVersion
     */
    public void delete(String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("delete from JBPM6_HIST_DEPLOY R");
        sql.append(" WHERE R.DEF_ID=? AND R.DEF_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId,flowVersion});
    }
}
