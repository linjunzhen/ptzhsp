/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.IpFilterConfigDao;
import net.evecom.platform.system.service.IpFilterConfigService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年9月11日 上午10:08:10
 */
@Service("ipFilterConfigService")
public class IpFilterConfigServiceImpl extends BaseServiceImpl implements IpFilterConfigService {

    /**
     * 
     */
    @Resource
    private IpFilterConfigDao dao;
    /**
     * 描述
     * @author Danto Huang
     * @created 2020年9月11日 上午10:08:10
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年9月11日 上午10:33:02
     * @param sqlFilter
     * @return
     * @see net.evecom.platform.system.service.IpFilterConfigService#findBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM T_SYSTEM_IPFILTER_CONFIG T ");
        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年9月11日 上午10:42:14
     * @param userIds
     * @param status
     * @see net.evecom.platform.system.service.IpFilterConfigService#updateConfigStatus(java.lang.String, int)
     */
    public void updateConfigStatus(String userIds, int status){
        StringBuffer sql = new StringBuffer("update T_SYSTEM_IPFILTER_CONFIG T SET "
                + "").append(" T.STATUS=? WHERE T.CONFIG_ID in ");
        String newUserIds = StringUtil.getValueArray(userIds);
        sql.append(newUserIds);
        dao.executeSql(sql.toString(), new Object[]{status});
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年9月11日 下午3:00:58
     * @return
     * @see net.evecom.platform.system.service.IpFilterConfigService#findEnableIpFilter()
     */
    public List<Map<String,Object>> findEnableIpFilter(){
        String sql = "select t.*, t.rowid from T_SYSTEM_IPFILTER_CONFIG t where t.status='1'";
        return dao.findBySql(sql, null, null);
    }
}
