/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

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
import net.evecom.platform.zzhy.dao.IndividualDao;
import net.evecom.platform.zzhy.service.IndividualService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 个体商户操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("individualService")
public class IndividualServiceImpl extends BaseServiceImpl implements IndividualService {
    /**
     * 所引入的dao
     */
    @Resource
    private IndividualDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.exe_id,t.subject,t.run_status,t.creator_name,t.cur_stepnames, ");
        sql.append(
                " t.cur_stepauditnames,t.create_time,t.end_time,i.dealer_name,i.pt_name,i.is_test,i.push_status_inter,i.push_inter_time ");
        sql.append(" from JBPM6_EXECUTION t join T_COMMERCIAL_INDIVIDUAL i on t.bus_recordid = i.individual_id ");
        sql.append(" and t.isneedsign = 1 and t.run_status<>0 ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sql.append(whereSql);
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
}
