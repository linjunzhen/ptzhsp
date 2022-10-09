/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.List;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.FlowNodeDao;

import org.springframework.stereotype.Repository;

/**
 * 描述  流程节点操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("flowNodeDao")
public class FlowNodeDaoImpl extends BaseDaoImpl implements FlowNodeDao {

    /**
     * 
     * 描述 获取节点的KEY
     * @author Flex Hu
     * @created 2015年8月14日 下午5:38:38
     * @param nodeName
     * @param defId
     * @param flowVersion
     * @return
     */
    public int getKey(String nodeName,String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT N.NODE_KEY FROM JBPM6_FLOWNODE N");
        sql.append(" WHERE N.DEF_ID=? AND N.FLOW_VERSION=? AND N.NODE_NAME=?");
        List<Integer> keys = this.jdbcTemplate.
            queryForList(sql.toString(), new Object[]{defId,flowVersion,nodeName},Integer.class);
        int key = 0;
        if(keys!=null&&keys.size()>0){
            return keys.get(0);
        }else{
            return key;
        }
    }
    
    /**
     * 
     * 描述 获取节点的名称
     * @author Flex Hu
     * @created 2015年8月17日 上午10:20:40
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeName(String defId,int flowVersion,String nodeType){
        StringBuffer sql = new StringBuffer("select J.NODE_NAME FROM JBPM6_FLOWNODE J");
        sql.append(" WHERE J.DEF_ID=? AND J.FLOW_VERSION=? AND J.NODE_TYPE=? ");
        List<String> nodeNames = this.jdbcTemplate.queryForList(sql.toString(), 
                new Object[]{defId,flowVersion,nodeType}, String.class);
        if(nodeNames!=null&&nodeNames.size()>0){
            return nodeNames.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_FLOWNODE R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
    }
    
    /**
     * 
     * 描述 获取任务节点的名称
     * @author Flex Hu
     * @created 2015年8月23日 上午10:03:36
     * @param defId
     * @param flowVersion
     * @return
     */
    public List<String> findTaskNodeNames(String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("select distinct(t.node_name) from JBPM6_FLOWNODE t");
        sql.append(" where t.flow_version=? AND t.DEF_ID=? AND t.NODE_TYPE='task' ");
        return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{flowVersion,defId}, String.class);
    }
    
    /**
     * 
     * 描述 获取来源任务名称
     * @author Flex Hu
     * @created 2015年8月24日 上午9:29:40
     * @param defId
     * @param flowVersion
     * @param toTaskNodeName
     * @return
     */
    public List<String> findFromTaskNodeNames(String defId,int flowVersion,String toTaskNodeName){
        StringBuffer sql = new StringBuffer("select T.NODE_NAME from JBPM6_FLOWNODE T WHERE T.NODE_KEY in");
        sql.append("(select N.FROMNODE_KEY from JBPM6_FLOWNODE N WHERE N.FLOW_VERSION=? ");
        sql.append(" AND N.NODE_NAME=? AND N.DEF_ID=? ");
        sql.append(" )AND T.FLOW_VERSION=? AND T.DEF_ID=? ");
        List<String> fromTaskNodeNames = this.jdbcTemplate.queryForList(sql.toString(), 
                new Object[]{flowVersion,toTaskNodeName,defId,flowVersion,defId}, String.class);
        return fromTaskNodeNames;
    }
    
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("delete from JBPM6_FLOWNODE R");
        sql.append(" WHERE R.DEF_ID=? AND R.FLOW_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId,flowVersion});
    }
}
