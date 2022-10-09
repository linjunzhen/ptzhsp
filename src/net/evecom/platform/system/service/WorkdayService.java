/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 工作日操作service
 * @author Roy Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface WorkdayService extends BaseService {
    
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据开始日期和传入的工作日数量计算截止日期
     * @author Flex Hu
     * @created 2015年11月7日 上午11:58:39
     * @param beginDate
     * @param workDayCount
     * @return
     */
    public String getDeadLineDate(String beginDate,int workDayCount);
    /**
     * 
     * 描述 根据开始日期和传入的自然日数量计算截止日期
     * @author Flex Hu
     * @created 2015年11月7日 上午11:58:39
     * @param beginDate
     * @param workDayCount
     * @return
     */
    public String getDeadLineDateNatural(String beginTime, int i);
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年11月10日 上午11:11:52
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findDataBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述 根据开始日期和结束日期获取工作日数量
     * @author Flex Hu
     * @created 2016年3月2日 下午3:39:03
     * @param beginDate
     * @param endDate
     * @return
     */
    public int getWorkDayCount(String beginDate,String endDate);
    /**
     * 
     * 描述：根据开始日期和传入的工作日获取剩余工作日
     * @author Water Guo
     * @created 2017-3-23 上午11:12:00
     * @param beginDate
     * @param workDay
     * @return
     */
    public int getWorkDayRest(String beginDate,int workDay);
    /**
     * 
     * 描述 根据开始日期和结束日期获取工作日列表
     * @author Faker Li
     * @created 2016年3月9日 下午5:07:06
     * @param sTime
     * @param eTime
     * @return
     */
    public List<Map<String, Object>> findWorkDayByseTime(String sTime, String eTime);

    /**
     * 描述:判断是否是休息日
     *
     * @author Madison You
     * @created 2021/4/26 16:00:00
     * @param
     * @return
     */
    boolean isHoliDay(String createTime);
}
