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
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.statis.job.StatisDataRefresh;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-10-18 下午3:29:41
 */
public class TzxmBjxlSynchJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(StatisDataRefresh.class);

    /**
     * 引入flowTaskService
     */
    private ExecutionService executionService;
    
    /**
     * 方法_构造方法
     * 
     */
    public TzxmBjxlSynchJob() {
        super();
        if (executionService == null) {
            executionService = (ExecutionService) AppUtil.getBean("executionService");
        }
    }
    
    /**
     * 投资项目办结效率结果生成
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("开始同步:"+currentDate+"的投资项目办结效率分析数据");
        executionService.tzxmBjxl(currentDate,currentDate);
        log.info("结束同步:"+currentDate+"的投资项目办结效率分析数据");
    }
}
