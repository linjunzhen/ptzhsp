/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.job;

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
 * 描述 定时同步JBPM6_STATIST表数据
 * @author Flex Hu
 * @version 1.0
 * @created 2016年3月10日 上午10:40:32
 */
public class StatisDataRefresh implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(StatisDataRefresh.class);

    /**
     * statisticsService
     */
    private StatisticsService statisticsService;

    /**
     * 方法_构造方法
     * 
     */
    public StatisDataRefresh() {
        super();
        if (statisticsService == null) {
            statisticsService = (StatisticsService) AppUtil.getBean("statisticsService");
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("开始同步:"+currentDate+"的JBPM6_STATIST表数据");
        statisticsService.saveOrUpdateStatis(currentDate);
        log.info("结束同步:"+currentDate+"的JBPM6_STATIST表数据");
    }

}
