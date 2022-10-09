/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.system.dao.WorkdayDao;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 工作日操作service
 * @author Roy Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("workdayService")
public class WorkdayServiceImpl extends BaseServiceImpl implements WorkdayService {
    /**
     * 所引入的dao
     */
    @Resource
    private WorkdayDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Roy Li
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
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_SYSTEM_WORKDAY ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        exeSql+=" order by w_id";
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述 根据开始日期和传入的工作日数量计算截止日期
     * @author Flex Hu
     * @created 2015年11月7日 上午11:58:39
     * @param beginDate
     * @param workDayCount
     * @return
     */
    public String getDeadLineDate(String beginDate,int workDayCount){
        return dao.getDeadLineDate(beginDate, workDayCount);
    }
    /**
     * 
     * 描述 根据开始日期和传入的工作日数量计算截止日期
     * @author Flex Hu
     * @created 2015年11月7日 上午11:58:39
     * @param beginDate
     * @param workDayCount
     * @return
     */
    public String getDeadLineDateNatural(String beginDate,int naturalDayCount){
        return dao.getDeadLineDateNatural(beginDate, naturalDayCount);
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findDataBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_WORKDAY T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }
    
    /**
     * 
     * 描述 根据开始日期和结束日期获取工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午3:39:03
     * @param beginDate
     * @param endDate
     * @return
     */
    public int getWorkDayCount(String beginDate,String endDate){
        return dao.getWorkDayCount(beginDate, endDate);
    }
    /**
     * 
     * 描述：根据开始日期和结束日期获取工作日数量
     * @author Water Guo
     * @created 2017-3-23 上午11:09:29
     * @param beginDate
     * @param wordDay
     * @return
     */
    public int getWorkDayRest(String beginDate,int workDay){
        return dao.getWorkDayRest(beginDate,workDay);
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月9日 下午5:07:48
     * @param sTime
     * @param eTime
     * @return
     * 
     */
    public List<Map<String, Object>> findWorkDayByseTime(String sTime, String eTime) {
        return dao.findWorkDayByseTime(sTime, eTime);
    }

    /**
     * 描述:判断是否是休息日
     *
     * @author Madison You
     * @created 2021/4/26 16:01:00
     * @param
     * @return
     */
    @Override
    public boolean isHoliDay(String createTime) {
        boolean flag = false;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count(*) COUNT FROM T_MSJW_SYSTEM_WORKDAY T WHERE T.W_DATE = ? AND T.W_SETID = 1 ");
        Map<String,Object> countMap = dao.getMapBySql(sql.toString(), new Object[]{createTime.substring(0, 10)});
        if (Objects.nonNull(countMap) && Integer.parseInt(StringUtil.getValue(countMap, "COUNT")) != 0) {
            flag = true;
        }
        return flag;
    }
}
