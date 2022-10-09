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
import net.evecom.platform.flowchart.dao.EstimateDao;
import net.evecom.platform.flowchart.service.EstimateService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述：
 * 
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-6
 */
@Service("estimateService")
public class EstimateServiceImpl extends BaseServiceImpl implements EstimateService {

    /**
     * @Resource dao
     */
    @Resource
    private EstimateDao dao;

    /**
     * @Override GenericDao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * @Override datagrid
     */
    @Override
    public List<Map<String, Object>> datagrid(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT L.* FROM T_LCJC_BUS_ESTIMATE L");
        //判断当前登录的权限
        SysUser curLoginUser = AppUtil.getLoginUser();
        //根据专项的归属进行权限约束
        if(!SysUser.ADMIN_RESKEY.equals(curLoginUser.getResKeys())){
            sql.append(" WHERE L.ESTIMATE_DEPTCODE like '").append(curLoginUser.getDepCode()).append("%' ");
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述 通过部门和操作申报号获得业务估算信息相关的信息
     * @author Water Guo
     * @created 2015-9-10 下午3:09:32
     * @param unitCode
     * @param applyId
     * @return
     */
    @Override
    public List<Map<String, Object>> getListHistoryByApply(String unitCode, String applyId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_LCJC_BUS_ESTIMATE_HISTORY T "); 
        sql.append(" WHERE T.ESTIMATE_DEPTCODE = ? and T.APPLY_ID = ? ORDER BY T.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), new Object[]{unitCode,applyId}, null);
    }

}
