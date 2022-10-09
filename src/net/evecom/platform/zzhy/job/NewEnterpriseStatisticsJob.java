/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.zzhy.service.StatistCommercialService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述  数据对接定时任务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class NewEnterpriseStatisticsJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(NewEnterpriseStatisticsJob.class);
    
    /**
     * statistCommercialService
     */
    private StatistCommercialService statistCommercialService;

    /**
     * 方法_构造方法
     * 
     */
    public NewEnterpriseStatisticsJob() {
        super();
        if (statistCommercialService == null) {
            statistCommercialService = (StatistCommercialService) AppUtil.getBean("statistCommercialService");
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
        statistCommercialService.statistNewEnterpriseNum(log);
    }
}
