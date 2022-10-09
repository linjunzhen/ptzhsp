/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
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
import net.evecom.platform.system.dao.SysMsgDao;
import net.evecom.platform.system.service.SysMsgService;

/**
 * 描述：
 * @author Water Guo
 * @created 2017-3-16 下午1:53:46
 */
@Service("sysMsgService")
public class SysMsgServiceImpl extends BaseServiceImpl implements SysMsgService{
    /**
     * 所引入的dao
     */
    @Resource
    private SysMsgDao dao;
    
    /**
     * 
     * 描述：根据sqlFilter获得信息
     * @author Water Guo
     * @created 2017-3-16 下午1:55:13
     * @param sqlFilter
     * @return
     * @see net.evecom.platform.system.service.SysMsgService#findBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer  sql=new StringBuffer("SELECT * FROM T_MSJW_SYSTEM_MSGSEND M WHERE 1=1  ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述：获得
     * @author Water Guo
     * @created 2017-3-16 下午2:00:06
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        // TODO Auto-generated method stub
        return null;
    }
}
