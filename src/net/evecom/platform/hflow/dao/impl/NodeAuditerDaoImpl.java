/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.NodeAuditerDao;

/**
 * 描述  节点审核操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("nodeAuditerDao")
public class NodeAuditerDaoImpl extends BaseDaoImpl implements NodeAuditerDao {

    /**
     * 
     * 描述 获取审核人配置
     * @author Flex Hu
     * @created 2015年8月19日 上午11:17:01
     * @param nodeName
     * @param nextNodeName
     * @param defId
     * @return
     */
    public Map<String,Object> getNodeAuditer(String nodeName,String nextNodeName,String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("select T.VARS_VALUE,T.AUDITER_RULE,A.CONFIG_NAME,");
        sql.append("A.CONFIG_CODE,A.AUDITER_TYPE,A.SELECTOR_URL,");
        sql.append("A.SELECTOR_HEIGHT,A.SELECTOR_WIDTH from JBPM6_NODEAUDITERCONF T ");
        sql.append("LEFT JOIN JBPM6_AUDITCONFIG A ON T.CONFIG_ID=A.CONFIG_ID ");
        sql.append(" WHERE T.NODE_NAME=? AND T.NEXT_NODE_NAME=? ");
        sql.append(" AND T.DEF_ID=? AND T.DEF_VERSION=? ");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[]{nodeName,nextNodeName,defId,flowVersion});
    }
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_NODEAUDITERCONF R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
    }
}
