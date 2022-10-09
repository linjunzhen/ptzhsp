/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

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
import net.evecom.platform.system.dao.ScheduleDao;
import net.evecom.platform.system.service.ScheduleService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 定时器操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("scheduleService")
public class ScheduleServiceImpl extends BaseServiceImpl implements ScheduleService {
    /**
     * 所引入的dao
     */
    @Resource
    private ScheduleDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
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
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_MSJW_SYSTEM_SCHEDULE T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据定时器状态获取数据列表
     * @author Flex Hu
     * @created 2014年9月29日 上午10:59:16
     * @param status
     * @return
     */
    public List<Map<String,Object>> findByStatus(int status){
        return dao.findByStatus(status);
    }
    /**
     * 
     * 描述 更新定时器状态
     * @author Flex Hu
     * @created 2014年9月29日 上午11:56:46
     * @param jobIds
     * @param status
     */
    public void updateStatus(String jobIds,int status){
        dao.updateStatus(jobIds, status);
    }
}
