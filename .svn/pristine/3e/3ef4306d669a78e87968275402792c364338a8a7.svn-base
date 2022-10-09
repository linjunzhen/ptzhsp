/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.statis.job;

import java.util.Calendar;
import java.util.Date;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.statis.service.StatisticsService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述  定时同步JBPM6_STATIST表数据(同步最近三个月数据，主要为待办统计更新)
 * @author Danto Huang
 * @version 1.0
 * @created 2016-7-19 下午3:15:06
 */
public class StatisDataRefreshDay implements Job {


    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(StatisDataRefreshDay.class);

    /**
     * statisticsService
     */
    private StatisticsService statisticsService;

    /**
     * 方法_构造方法
     * 
     */
    public StatisDataRefreshDay() {
        super();
        if (statisticsService == null) {
            statisticsService = (StatisticsService) AppUtil.getBean("statisticsService");
        }
    }
    
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date  formNow3Month = calendar.getTime();
        //三个月前日期
        String beforeDate = DateTimeUtil.getStrOfDate(formNow3Month, "yyyy-MM-dd");
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");

        log.info("开始同步:"+beforeDate+" 至 "+currentDate+"的JBPM6_STATIST表数据");
        long count = DateTimeUtil.getIntervalTime(beforeDate, currentDate, "yyyy-MM-dd", 4);
        Date nowdate = DateTimeUtil.getDateOfStr(beforeDate, "yyyy-MM-dd");
        for (int i = 0; i <= count; i++) {
            Date nextDay = DateTimeUtil.getNextDay(nowdate, i);
            String targetDate = DateTimeUtil.getStrOfDate(nextDay, "yyyy-MM-dd");
            statisticsService.saveOrUpdateStatis(targetDate);
        }
        log.info("结束同步:"+beforeDate+" 至 "+currentDate+"的JBPM6_STATIST表数据");
    }

}
