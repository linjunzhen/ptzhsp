/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bsfw.dao.BdcDyrlxrDao;
import net.evecom.platform.bsfw.service.BdcDyrlxrService;

/**
 * 描述 不动产-抵押权人联系人service()
 * @author Allin Lin
 * @created 2020年12月16日 上午11:01:07
 */
@Service("bdcDyrlxrService")
public class BdcDyrlxrServiceImpl extends BaseServiceImpl implements BdcDyrlxrService{
    
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcDyrlxrServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private BdcDyrlxrDao dao;

    /**
     * 描述  覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年12月16日 上午11:10:28
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 根据sqlfilter获取抵押权人联系人数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_BDC_DYRLXB T ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFileterExport(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.LXR_NAME,a.DYQRMC,a.TYSHXYDM,a.LXR_PHONE,a.CREATE_TIME from T_BDC_DYRLXB a");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
}
