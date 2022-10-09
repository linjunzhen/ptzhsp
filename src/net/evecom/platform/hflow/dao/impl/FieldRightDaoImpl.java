/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.FieldRightDao;

/**
 * 描述  字段权限操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("fieldRightDao")
public class FieldRightDaoImpl extends BaseDaoImpl implements FieldRightDao {

    /**
     * 
     * 描述 根据流程定义ID判断是否已经配置权限
     * @author Flex Hu
     * @created 2015年8月12日 上午10:39:15
     * @param defId
     * @return
     */
    public boolean isExists(String defId,int flowDefVersion){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_FIELDRIGHT F");
        sql.append(" WHERE F.DEF_ID=? AND F.DEF_VERSION=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{defId,flowDefVersion});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 判断是否存在记录
     * @author Flex Hu
     * @created 2015年8月12日 上午11:25:38
     * @param defId
     * @param nodeName
     * @param fieldName
     * @return
     */
    public boolean isExists(String defId,String nodeName,String fieldName,int flowDefVersion){
        StringBuffer sql = new StringBuffer("select count(*) from JBPM6_FIELDRIGHT F");
        sql.append(" WHERE F.DEF_ID=? AND F.FIELD_NAME=? AND F.NODE_NAME=? AND F.DEF_VERSION=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),
                new Object[]{defId,fieldName,nodeName,flowDefVersion});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 更新字段的权限值
     * @author Flex Hu
     * @created 2015年8月12日 下午3:53:51
     * @param rightId
     * @param rightFlag
     */
    public void updateRightFlag(String rightId,int rightFlag){
        StringBuffer sql = new StringBuffer("update JBPM6_FIELDRIGHT J");
        sql.append(" SET J.RIGHTFLAG=? WHERE J.RIGHT_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{rightFlag,rightId});
    }
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_FIELDRIGHT R");
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
        StringBuffer sql = new StringBuffer("delete from JBPM6_FIELDRIGHT R");
        sql.append(" WHERE R.DEF_ID=? AND R.DEF_VERSION=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId,flowVersion});
    }
}
