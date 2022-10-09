/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.statis.dao.StatisticsDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年3月8日 上午11:17:37
 */
@Repository("statisticsDao")
public class StatisticsDaoImpl extends BaseDaoImpl implements StatisticsDao {
    
    /**
     * 
     * 描述 获取办件统计数量
     * @author Flex Hu
     * @created 2016年3月8日 下午1:59:50
     * @param filter
     * @return
     */
    public int getStaticCount(SqlFilter filter){
        List params = new ArrayList();
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM JBPM6_EXECUTION E ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE=S.ITEM_CODE ");
        sql.append(" LEFT JOIN JBPM6_EFFICINFO I ON I.EFLOW_EXEID=E.EXE_ID ");
        //sql.append(" LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON C.CATALOG_CODE = S.CATALOG_CODE ");
        //sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON C.CHILD_DEPART_ID = D.DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON S.ZBCS_ID = D.DEPART_ID ");
        sql.append(" WHERE E.RUN_STATUS<>0 ");
        String finalSql = this.getQuerySql(filter, sql.toString(), params);
        Object count = this.getObjectBySql(finalSql, params.toArray());
        return Integer.parseInt(count.toString());
    }
    
    /**
     * 
     * 描述 获取待办数量
     * @author Flex Hu
     * @created 2016年3月8日 下午3:12:40
     * @param filter
     * @return
     */
    public int getHandingCount(SqlFilter filter){
        List params = new ArrayList();
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM JBPM6_EXECUTION E ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE=S.ITEM_CODE ");
        //sql.append(" LEFT JOIN T_WSBS_SERVICEITEM_CATALOG C ON C.CATALOG_CODE = S.CATALOG_CODE ");
        //sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON C.CHILD_DEPART_ID = D.DEPART_ID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON S.ZBCS_ID = D.DEPART_ID ");
        String finalSql = this.getQuerySql(filter, sql.toString(), params);
        Object count = this.getObjectBySql(finalSql, params.toArray());
        return Integer.parseInt(count.toString());
    }
    
}
