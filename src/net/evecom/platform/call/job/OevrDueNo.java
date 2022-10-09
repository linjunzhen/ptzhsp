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
import net.evecom.platform.call.service.NewCallService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 超期排队号自动过号
 * @author Danto Huang
 * @version 1.0
 * @created 2016-5-22 下午1:05:55
 */
public class OevrDueNo implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(OevrDueNo.class);
    /**
     * callService
     */
    private CallService callService;
    /**
     * callService
     */
    private NewCallService newCallService;
    
    /**
     * 方法_构造方法
     * 
     */
    public OevrDueNo() {
        super();
        if (callService == null) {
            callService = (CallService) AppUtil.getBean("callService");
        }
        if (newCallService == null) {
            newCallService = (NewCallService) AppUtil.getBean("newCallService");
        }
    }

    /**
     * 
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        //获取当前日期
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("开始自动过号:"+currentDate+"的T_CKBS_NUMRECORD表数据");
        callService.overDueNo(currentDate);
        log.info("结束自动过号:"+currentDate+"的T_CKBS_NUMRECORD表数据");
        log.info("开始自动过号:"+currentDate+"的T_CKBS_QUEUERECORD表数据");
        newCallService.overDueQueue(currentDate);
        log.info("结束自动过号:"+currentDate+"的T_CKBS_QUEUERECORD表数据");

    }

}
