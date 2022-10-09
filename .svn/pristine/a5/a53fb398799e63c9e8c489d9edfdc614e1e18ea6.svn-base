/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

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
import net.evecom.platform.hflow.dao.MsgTemplateDao;
import net.evecom.platform.hflow.service.MsgTemplateService;
import net.evecom.platform.system.service.impl.DepartmentServiceImpl;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
@Service("msgTemplateService")
public class MsgTemplateServiceImpl extends BaseServiceImpl implements MsgTemplateService{
    /**
     * dao
     */
    @Resource
    private MsgTemplateDao dao;
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MsgTemplateServiceImpl.class);
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select "
                + "* from JBPM6_SENDMSG_TEMPLATE T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public void removeCascade(String selectColNames) {
        this.remove("JBPM6_SENDMSG_TEMPLATE", "TEMPLATE_ID", selectColNames.split(","));
    }

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

}
