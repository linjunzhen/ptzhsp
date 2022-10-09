/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.NodeConfigDao;

/**
 * 描述  节点配置操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("nodeConfigDao")
public class NodeConfigDaoImpl extends BaseDaoImpl implements NodeConfigDao {
    
    /**
     * 
     * 描述 根据流程定义删除掉数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:48:37
     * @param defId
     */
    public void deleteByDefId(String defId){
        StringBuffer sql = new StringBuffer("delete from JBPM6_NODECONFIG R");
        sql.append(" WHERE R.DEF_ID=? ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{defId});
    }
    
    
    /**
     * 
     * 描述 根据流程定义ID和流程节点名称获取配置的决策代码
     * @author Flex Hu
     * @created 2015年8月20日 下午4:31:56
     * @param defId
     * @param nodeName
     * @return
     */
    public String getTaskDecideCode(String defId,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT A.CONFIG_CODE FROM JBPM6_NODECONFIG N");
        sql.append(" LEFT JOIN JBPM6_AUDITCONFIG A ON N.CONFIG_ID=A.CONFIG_ID");
        sql.append(" WHERE N.DEF_ID=? AND N.NODE_NAME=? AND N.DEF_VERSION=?");
        String code = this.jdbcTemplate.queryForObject(sql.toString(), new Object[]
        {defId,nodeName,flowVersion}, String.class);
        return code;
    }
    
    /**
     * 
     * 描述 获取省网办的发起节点的名称
     * @author Flex Hu
     * @created 2016年1月26日 下午3:06:05
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeNameForProvinceStart(String defId,int flowVersion){
        StringBuffer sql = new StringBuffer("SELECT N.NODE_NAME FROM ");
        sql.append(" JBPM6_NODECONFIG N WHERE N.DEF_ID=? AND N.DEF_VERSION=? ");
        sql.append(" AND N.IS_PROVINCE_START=1 ");
        List<String> list = this.jdbcTemplate.queryForList(sql.toString(),
                new Object[]{defId,flowVersion}, String.class);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
