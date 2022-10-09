/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.call.job;

import java.util.Date;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.call.service.CallService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-12-9 下午2:05:48
 */
public class AppointmentPassJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(AppointmentPassJob.class);
    /**
     * callService
     */
    private CallService callService;
    
    /**
     * 方法_构造方法
     * 
     */
    public AppointmentPassJob() {
        super();
        if (callService == null) {
            callService = (CallService) AppUtil.getBean("callService");
        }
    }
    
    /**
     * 
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("开始自动作废:"+currentDate+"的T_BESPEAK_APPLY表数据");
        callService.passAppointment(currentDate);
        log.info("结束自动作废:"+currentDate+"的T_BESPEAK_APPLY表数据");

    }
}
