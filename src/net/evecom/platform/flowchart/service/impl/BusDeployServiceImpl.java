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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowchart.dao.BusDeployDao;
import net.evecom.platform.flowchart.service.BusDeployService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述：BusDeployServiceImpl
 * 
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-9
 */
@Service("busDeployService")
public class BusDeployServiceImpl extends BaseServiceImpl implements BusDeployService {

    /**
     * @Resource BusDeployDao
     */
    @Resource
    private BusDeployDao dao;

    /**
     * @Override GenericDao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public List<Map<String, Object>> datagrid(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT L.* FROM T_LCJC_BUS_DEPLOY L");
        //判断当前登录的权限
        SysUser curLoginUser = AppUtil.getLoginUser();
        //根据专项的归属进行权限约束
        if(!SysUser.ADMIN_RESKEY.equals(curLoginUser.getResKeys())){
            sql.append(" WHERE L.BUSSYS_UNITCODE like '").append(curLoginUser.getDepCode()).append("%' ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public List<Map<String, Object>> getListHistoryByApply(String unitCode, String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_DEPLOY_HISTORY T "); 
        sql.append(" WHERE T.BUSSYS_UNITCODE = ? and T.APPLY_ID = ? ORDER BY T.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), new Object[]{unitCode,applyId}, null);
    }

}
