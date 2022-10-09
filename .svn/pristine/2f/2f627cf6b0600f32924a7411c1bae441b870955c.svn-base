/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchart.dao.BusColumnBasicDao;

/**
 * 描述  字段配置的Dao实现
 * @author Water Guo
 * @version 2.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("busColumnBasicDao")
public class BusColumnBasicDaoImpl extends BaseDaoImpl implements BusColumnBasicDao{

    /***
     * 描述 获取最大的排序号
     * @author Water Guo
     * @created 2015-8-18 下午3:52:50
     * @param processCode
     * @return int default 0
     */
    public int getMaxSn(String processCode,String platCode,String tableName) {
        StringBuffer sql = new StringBuffer("SELECT NVL(MAX(T.BUSSYS_SN),0) from "+tableName+" T");
        sql.append(" WHERE T.PROCESS_CODE=? AND BUSSYS_PLATCODE = ?  AND T.BUSSYS_TYPE = 1");
        return jdbcTemplate.queryForInt(sql.toString(), new Object[]{processCode,platCode});
    }

    
    /***
     * 描述 根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-8-28 上午11:36:47
     * @param busCode,applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCode(String busCode,String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM (");
        sql.append(" SELECT BUSSYS_PLATCODE FROM T_LCJC_BUS_COLUMN_HISTORY T  WHERE T.BUSSYS_TYPE=1 ");
        sql.append(" AND T.PROCESS_CODE = ? AND T.APPLY_ID= ? GROUP BY T.BUSSYS_PLATCODE ");
        sql.append(" ) T1 INNER JOIN T_LCJC_SYSTEM_BUSSYS T2 ON T1.BUSSYS_PLATCODE = T2.SYSTEM_CODE ");
        sql.append(" ORDER BY T2.SYSTEM_CODE ASC ");
        return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{busCode,applyId});
    }


    /**
     * 描述 （变更业务）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-10-9 下午5:13:26
     * @param buscode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCodeChange(String buscode, String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM (");
        sql.append(" SELECT BUSSYS_PLATCODE FROM T_LCJC_BUS_COLUMN_CHANGE T  WHERE T.BUSSYS_TYPE=1 ");
        sql.append(" AND T.PROCESS_CODE = ? AND T.APPLY_ID= ? GROUP BY T.BUSSYS_PLATCODE ");
        sql.append(" ) T1 INNER JOIN T_LCJC_SYSTEM_BUSSYS T2 ON T1.BUSSYS_PLATCODE = T2.SYSTEM_CODE ");
        sql.append(" ORDER BY T2.SYSTEM_CODE ASC ");
        return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{buscode,applyId});
    }

}
