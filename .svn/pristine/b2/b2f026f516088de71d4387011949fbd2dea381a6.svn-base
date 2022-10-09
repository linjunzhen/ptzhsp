/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.FlowEventDao;

import java.util.List;

/**
 * 描述  流程事件操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("flowEventDao")
public class FlowEventDaoImpl extends BaseDaoImpl implements FlowEventDao {

    /**
     * 
     * 描述 判断是否存在记录
     * @author Flex Hu
     * @created 2015年8月12日 下午5:08:55
     * @param defId
     * @param eventType
     * @param nodeName
     * @return
     */
    public boolean isExists(String defId,String eventType,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM JBPM6_EVENT J");
        sql.append(" WHERE J.DEF_ID=? AND J.EVENT_TYPE=? AND J.NODE_NAME=? AND J.DEF_VERSION=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{defId,eventType,nodeName,flowVersion});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称删除事件
     * @author Flex Hu
     * @created 2015年8月13日 上午10:01:24
     * @param defId
     * @param nodeName
     */
    public void deleteEvent(String defId,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("delete from JBPM6_EVENT E");
        sql.append(" WHERE E.DEF_ID=? AND E.NODE_NAME=? AND E.DEF_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(),new Object[]{defId,nodeName,flowVersion});
    }
    
//    /**
//     *
//     * 描述 获取事件配置代码
//     * @author Flex Hu
//     * @created 2015年8月18日 上午11:21:25
//     * @param defId
//     * @param nodeName
//     * @param eventType
//     * @return
//     */
//    //2020年4月27日Adrian Bian 注掉
//    public String getEventCode(String defId,String nodeName,String eventType,int flowVersion){
//        StringBuffer sql = new StringBuffer("select E.EVENT_CODE FROM JBPM6_EVENT E");
//        sql.append(" WHERE E.EVENT_TYPE=? AND E.DEF_ID=? AND E.NODE_NAME=? ");
//        sql.append(" AND E.DEF_VERSION=? ");
//        String eventCode = (String) this.getUniqueBySql(sql.toString(),new Object[]{eventType,defId,
//            nodeName,flowVersion});
//        return eventCode;
//    }
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_EVENT R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
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
        StringBuffer sql = new StringBuffer("delete from JBPM6_EVENT R");
        sql.append(" WHERE R.DEF_ID=? AND R.DEF_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId,flowVersion});
    }

    /**
     *
     * 描述 获取事件配置代码
     * @author Adrian Bian
     * @created 2020年03月19日 上午11:21:25
     * @param defId
     * @param nodeName
     * @param eventType
     * @param flowVersion
     * @return
     */
    @Override
    public List<String> findEventCodeList(String defId, String nodeName, String eventType, int flowVersion){
        StringBuffer sql = new StringBuffer("select E.EVENT_CODE FROM JBPM6_EVENT E");
        sql.append(" WHERE E.EVENT_TYPE=? AND E.DEF_ID=? AND E.NODE_NAME=? ");
        sql.append(" AND E.DEF_VERSION=? ");
        List<String> eventCodeList = this.jdbcTemplate.queryForList(sql.toString(),new Object[]{eventType,defId,
                nodeName,flowVersion},String.class);
        return eventCodeList;
    }
}
