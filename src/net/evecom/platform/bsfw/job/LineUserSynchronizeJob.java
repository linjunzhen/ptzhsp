/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.bsfw.service.impl.BjxxServiceImpl;
import net.evecom.platform.statis.service.StatisticsService;

/**
 * 描述   每天定时同步办件用户信息（经办人）至办事（取号）用户信息表
 * @author Allin Lin
 * @created 2021年3月16日 上午9:48:13
 */
public class LineUserSynchronizeJob implements Job{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(LineUserSynchronizeJob.class);
    
    
    /**
     * 
     */
    private BjxxService bjxxService;
    
    /**
     * 方法_构造方法
     * 
     */
    public LineUserSynchronizeJob() {
        super();
        if (bjxxService == null) {
            bjxxService = (BjxxService) AppUtil.getBean("bjxxService");
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("开始同步:"+currentDate+"的JBPM6_EXECUTION表数据至取号用户信息表中");
        bjxxService.saveOrUpdateLineUsers(currentDate);
        log.info("结束同步:"+currentDate+"的JBPM6_EXECUTION表数据至取号用户信息表中");
    }

}
