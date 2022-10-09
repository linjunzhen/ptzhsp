/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.flowconfig.dao.FlowConfigUserDao;
import net.evecom.platform.flowconfig.service.FlowConfigUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 工程建设预审环节审核人员配置操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowConfigUserService")
public class FlowConfigUserServiceImpl extends BaseServiceImpl implements FlowConfigUserService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowConfigUserDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        // TODO Auto-generated method stub

        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select *  from T_FLOW_CONFIG_USER T ");
        sql.append(" where 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
}
