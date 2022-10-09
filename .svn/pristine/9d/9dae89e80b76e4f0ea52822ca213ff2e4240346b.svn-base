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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.GlobalUrlDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.GlobalUrlService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年6月22日 上午11:36:42
 */
@Service("globalUrlService")
public class GlobalUrlServiceImpl extends BaseServiceImpl implements GlobalUrlService {

    /**
     * 
     */
    @Resource
    private GlobalUrlDao dao;
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年6月22日 上午11:38:29
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
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM T_SYSTEM_GLOBALURL T ");
        
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 下午3:36:29
     * @return
     */
    public List<String> findByFilterType(String filterType){
        return dao.findByFilterType(filterType);
    }
}
