/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.project.dao.ProjectFullTestDao;
import net.evecom.platform.project.service.ProjectFullTestService;

/**
 * 描述 全测合一service实现类
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2021年6月18日 上午9:38:13
 */
@SuppressWarnings("rawtypes")
@Service("projectFullTestService")
public class ProjectFullTestServiceImpl extends BaseServiceImpl implements ProjectFullTestService {

    /**
     * 所引入的dao
     */
    @Resource
    private ProjectFullTestDao dao;

    /**
     * 描述 实体dao方法 
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_PROJECT_FULLTEST T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
    }
}
