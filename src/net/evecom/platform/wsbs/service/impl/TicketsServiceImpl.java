/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.TicketsDao;
import net.evecom.platform.wsbs.service.TicketsService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 单据配置类操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("ticketsService")
public class TicketsServiceImpl extends BaseServiceImpl implements TicketsService {
    /**
     * 所引入的dao
     */
    @Resource
    private TicketsDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.TICKETS_ID,T.TICKETS_NAME,");
        sql.append("T.TICKETS_TYPE,S.SERIAL_NAME from T_WSBS_TICKETS T ");
        sql.append("LEFT JOIN t_wsbs_serialnumber S ON S.SERIAL_ID=T.SERIAL_ID");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据ticketsId获得票单模板
     * @author Faker Li
     * @created 2015年10月19日 上午8:56:24
     * @param ticketsIds
     * @return
     * @see net.evecom.platform.wsbs.service.TicketsService#findByTicketsId(java.lang.String)
     */
    public List<Map<String, Object>> findByTicketsId(String ticketsIds) {
        StringBuffer sql = new StringBuffer("select U.TICKETS_ID,U.TICKETS_NAME,U.TICKETS_DOC");
        sql.append(" FROM T_WSBS_TICKETS U WHERE U.TICKETS_ID IN　");
        sql.append(StringUtil.getValueArray(ticketsIds));
        sql.append(" ORDER BY U.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
}
