/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

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
import net.evecom.platform.system.dao.SysNoticeDao;
import net.evecom.platform.system.service.SysNoticeService;

/**
 * 描述 公告service impl
 * @author Sundy Sun
 * @version v1.0
 */
@Service("sysNoticeService")
public class SysNotcieServiceImpl extends BaseServiceImpl implements SysNoticeService{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DepartmentServiceImpl.class);

    /**
     * 所引入的dao
     */
    @Resource
    private SysNoticeDao dao;
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select "
                + "* from T_MSJW_SYSTEM_NOTICE T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public void removeCascade(String selectColNames) {
        this.remove("T_MSJW_SYSTEM_NOTICE", "NOTICE_ID", selectColNames.split(","));
    }
}
