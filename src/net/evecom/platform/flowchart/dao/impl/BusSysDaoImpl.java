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
import net.evecom.platform.flowchart.dao.BusSysDao;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 业务系统
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 3:09:43 PM
 */
@Repository("busSysDao")
public class BusSysDaoImpl extends BaseDaoImpl implements BusSysDao {
    /**
     * 描述 根据单位ID获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitId(String UnitId) {
        StringBuffer sql = new StringBuffer("select * FROM T_LCJC_SYSTEM_BUSSYS ");
        sql.append(" WHERE UNIT_ID=? ORDER BY SYSTEM_CODE ASC,CREATE_TIME DESC");
        return this.findBySql(sql.toString(), new Object[] { UnitId }, null);
    }
    /**
     * 描述 根据单位编码获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitCode(String unitCode) {
        StringBuffer sql = new StringBuffer("select * FROM T_LCJC_SYSTEM_BUSSYS T ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT A ON T.UNIT_ID=A.DEPART_ID");
        sql.append(" WHERE DEPART_CODE=? ORDER BY T.SYSTEM_CODE ASC,T.CREATE_TIME DESC");
        return this.findBySql(sql.toString(), new Object[] { unitCode }, null);
    }

    /***
     * 描述：（超管权限）获取系统列表
     * 
     * @author Water Guo
     * @date 2015-08-07 11:45 AM
     * @return list
     */
    @Override
    public List<Map<String, Object>> findAllSystems() {
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM T_LCJC_SYSTEM_BUSSYS ORDER BY SYSTEM_CODE ASC,SYSTEM_CODE ASC");
        return this.findBySql(sql.toString(), null, null);
    }

}
