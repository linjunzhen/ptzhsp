/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.ButtonRightDao;

/**
 * 描述  按钮权限操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("buttonRightDao")
public class ButtonRightDaoImpl extends BaseDaoImpl implements ButtonRightDao {
    /**
     * 
     * 描述 判断记录是否存在
     * @author Flex Hu
     * @created 2015年8月13日 下午3:27:00
     * @param defId
     * @param nodeName
     * @param buttonKey
     * @return
     */
    public boolean isExist(String defId,String nodeName,String buttonKey,int flowVersion){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_BUTTONRIGHT J");
        sql.append(" WHERE J.DEF_ID=? AND J.NODE_NAME=? AND J.BUTTON_KEY=? AND J.DEF_VERSION=?");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{defId,nodeName,buttonKey,flowVersion});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 根据按钮KEY删除掉权限配置记录
     * @author Flex Hu
     * @created 2015年8月13日 下午3:52:05
     * @param buttonKey
     */
    public void deleteByButtonKey(String buttonKey){
        String sql = "delete from JBPM6_BUTTONRIGHT B WHERE B.BUTTON_KEY=?";
        this.jdbcTemplate.update(sql,new Object[]{buttonKey});
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称删除掉数据
     * @author Flex Hu
     * @created 2015年8月13日 下午4:51:50
     * @param defId
     * @param nodeName
     */
    public void delete(String defId,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("delete from JBPM6_BUTTONRIGHT R");
        sql.append(" WHERE R.DEF_ID=? AND R.NODE_NAME=? AND R.DEF_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId,nodeName,flowVersion});
    }
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_BUTTONRIGHT R");
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
        StringBuffer sql = new StringBuffer("delete from JBPM6_BUTTONRIGHT R");
        sql.append(" WHERE R.DEF_ID=? AND R.DEF_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId,flowVersion});
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称获取权限配置
     * @author Flex Hu
     * @created 2015年8月13日 下午4:10:32
     * @param nodeName
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findList(String nodeName,String defId,boolean filterAuth,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT B.BUTTON_NAME,R.IS_AUTH,B.BUTTON_KEY ");
        sql.append(",B.BUTTON_ICON,B.BUTTON_FUN,R.AUTH_INTERFACECODE ");
        sql.append(" FROM JBPM6_BUTTON B LEFT JOIN JBPM6_BUTTONRIGHT R ON B.BUTTON_KEY=R.BUTTON_KEY ");
        sql.append("WHERE R.NODE_NAME=? AND R.DEF_ID=? ");
        sql.append(" AND R.DEF_VERSION=? ");
        if(filterAuth){
            sql.append(" AND R.IS_AUTH=1 ");
        }
        sql.append(" ORDER BY B.CREATE_TIME ASC ");
        return this.findBySql(sql.toString(), new Object[]{nodeName,defId,flowVersion},null);
    }
}
