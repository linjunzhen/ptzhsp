/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.BusSysDao;
import net.evecom.platform.flowchart.service.BusSysService;

/**
 * 描述 业务系统
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 3:11:52 PM
 */
@Service("busSysService")
public class BusSysServiceImpl extends BaseServiceImpl implements BusSysService {
    /**
     * 引入dao
     */
    @Resource
    private BusSysDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @author Water Guo
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,B.DEPART_NAME UNIT_NAME from T_LCJC_SYSTEM_BUSSYS T");
        sql.append(" LEFT JOIN t_msjw_system_department A ON T.UNIT_ID=A.DEPART_ID ");
        sql.append(" LEFT JOIN t_msjw_system_department B ON T.DEVELOP_ID=B.DEPART_ID ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 根据单位ID获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitId(String UnitId) {
        return dao.findSysByUnitId(UnitId);
    }
    /**
     * 描述 根据单位编码获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitCode(String unitCode){
        return dao.findSysByUnitCode(unitCode);
    }
    /***
     * 描述：（超管权限）获取系统列表
     * 
     * @author Water Guo
     * @date 2015-08-07 11:45 AM
     */
    @Override
    public List<Map<String, Object>> findAllSystems() {
        return dao.findAllSystems();
    }

}
