/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.statis.dao.StatisticalReportDao;
import net.evecom.platform.statis.service.StatisticalReportService;
/**
 * 
 * 描述
 * @author Faker Li
 * @created 2016年3月11日 上午9:41:48
 */
@Service("statisticalReportService")
public class StatisticalReportServiceImpl extends BaseServiceImpl implements StatisticalReportService {
    /**
     * 所引入的dao
     */
    @Resource
    private StatisticalReportDao dao;
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月11日 上午9:41:35
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
     * @author Faker Li
     * @created 2016年3月11日 上午10:11:59
     * @param time
     * @param sqfs
     * @return
     */
    public int getBjsByTime(String time, String sqfs) {
        return dao.getBjsByTime(time,sqfs);
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月11日 下午4:20:15
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_WSBS_INSPEEDREPORT t");
        String realTotalSql = dao.getQuerySql(filter, sql.toString(), params);
        return dao.findBySql(realTotalSql.toString(), params.toArray(), null);
    }

}
