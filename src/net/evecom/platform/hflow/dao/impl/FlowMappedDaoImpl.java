/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.FlowMappedDao;

/**
 * 描述  流程映射类操作dao
 * @author  Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("flowMappedDao")
public class FlowMappedDaoImpl extends BaseDaoImpl implements FlowMappedDao {

    /**
     * 
     * 描述 保存映射子表
     * @author Faker Li
     * @created 2015年12月28日 下午2:26:23
     * @param entityId
     * @param split
     * @see net.evecom.platform.hflow.dao.FlowMappedDao#saveYsNode(java.lang.String, java.lang.String[])
     */
    public void saveYsNode(String ysId, String[] defNames) {
        StringBuffer sql = new StringBuffer("insert into ");
        sql.append("T_WSBS_FLOWYSNODES(YS_ID,DEF_NAME) values(?,?)");
        for(String defName:defNames){
            this.jdbcTemplate.update(sql.toString(),new Object[]{ysId,defName});
        }
        
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称和映射类型获取映射信息
     * @author Flex Hu
     * @created 2015年12月28日 下午3:27:16
     * @param defId
     * @param nodeName
     * @param mapType
     * @return
     */
    public Map<String,Object> getFlowMapInfo(String defId,String nodeName,String mapType){
        StringBuffer sql = new StringBuffer("SELECT FY.* FROM T_WSBS_FLOWYS FY");
        sql.append(" LEFT JOIN T_WSBS_FLOWYSNODES N ON FY.YS_ID=N.YS_ID");
        sql.append(" WHERE FY.DEF_ID=? AND N.DEF_NAME=? AND FY.YS_TYPE=? ");
        return this.getByJdbc(sql.toString(),new Object[]{defId,nodeName,mapType});
    }

}
