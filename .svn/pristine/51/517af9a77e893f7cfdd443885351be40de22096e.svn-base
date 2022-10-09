/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.hflow.service.ComEffiService;
import net.evecom.platform.zzhy.service.StatistCommercialService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 商事统计相关定时任务
 * @author Danto Huang
 * @created 2021年11月16日 下午4:18:22
 */
public class CommercialStatisticsJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CommercialStatisticsJob.class);
    
    /**
     * statistCommercialService
     */
    private StatistCommercialService statistCommercialService;
    /**
     * 引入 CreditDataInputService
     */
    private ComEffiService comEffiService;

    /**
     * 方法_构造方法
     * 
     */
    public CommercialStatisticsJob() {
        super();
        if (statistCommercialService == null) {
            statistCommercialService = (StatistCommercialService) AppUtil.getBean("statistCommercialService");
        }
        if (comEffiService == null) {
            comEffiService = (ComEffiService) AppUtil.getBean("comEffiService");
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("商事统计相关任务......Start");
        //商事领导层报表统计
        statistCommercialService.statistNewEnterpriseNum(log);
        //商事效率统计
        comEffiService.comEffiStatistics(log);
        log.info("商事统计相关任务......End");
    }
}
