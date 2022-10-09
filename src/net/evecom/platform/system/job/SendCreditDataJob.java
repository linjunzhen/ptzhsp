/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.system.job;

import java.util.Date;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.statis.job.StatisDataRefresh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 定时器发送商事信息到信用平台
 * 
 * @author Water Guo
 * @created 2017-4-24 上午10:44:45
 */
public class SendCreditDataJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(StatisDataRefresh.class);

    /**
     * 引入dataPWDChanceService
     */
    private CommercialService commercialService;

    /**
     * 方法_构造方法
     * 
     */
    public SendCreditDataJob() {
        super();
        if (commercialService == null) {
            commercialService = (CommercialService) AppUtil.getBean("commercialService");
        }
    }

    /**
     * 定时器发送商事信息到信用平台
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("定时器开始发送商事信息到信用平台:" + currentDate);
        commercialService.sendToCreditByTimer();
        log.info("定时器结束发送商事信息到信用平台:" + currentDate);
    }
}
