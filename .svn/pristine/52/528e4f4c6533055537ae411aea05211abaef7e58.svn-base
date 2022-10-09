/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.system.dao.SysLogDao;

import org.springframework.stereotype.Repository;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:22:58
 */
@Repository("sysLogDao")
public class SysLogDaoImpl extends BaseDaoImpl implements SysLogDao {

    @Override
    public List<Map<String, Object>> getListByJdbc(String uploadUserId) {
        String sql ="select t.* from T_BSFW_USERINFO t where t.user_id = '"+uploadUserId+"'";
        return this.jdbcTemplate.queryForList(sql.toString());
    }
    /**
     * 
     * 描述：联合查询获取目的表变更表信息
     * @author Water Guo
     * @created 2017-11-10 下午4:29:41
     * @param indexColName 索引列
     * @param busTableName 联合查询表
     * @param colValues 查询的列值
     */
    public List<Map<String,Object>> getBusTableLogs(String indexColName,String busTableName,String[] colNames,
          Object[] colValues ){
        StringBuffer sql=new StringBuffer("SELECT LOG.* FROM ");
        sql.append(busTableName).append(" BUS ,T_MSJW_SYSTEM_BUSLOG LOG ");
        sql.append("WHERE LOG.BUS_INDEX=BUS.").append(indexColName);
        for(int i=0;i<colNames.length;i++){
            sql.append(" AND BUS.").append(colNames[i]).append("=?");
        }
        return this.jdbcTemplate.queryForList(sql.toString(), colValues);
    }
    /**
     * 
     * 描述：查询表更新信息
     * @author Water Guo
     * @created 2017-11-15 下午5:16:34
     * @param indexColName
     * @param busTableName
     * @return
     */
    public List<Map<String,Object>> getChBusTableLogs(String indexColVal,String busTableName){
        StringBuffer sql=new StringBuffer("SELECT LOG.* FROM ");
        sql.append(" T_MSJW_SYSTEM_BUSLOG LOG WHERE LOG.BUS_TABLENAME='");
        sql.append(busTableName).append("' AND LOG.BUS_INDEX='");
        sql.append(indexColVal).append("'");
        return this.jdbcTemplate.queryForList(sql.toString());
    }
}
