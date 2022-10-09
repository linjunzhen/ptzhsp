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
import net.evecom.platform.system.service.DataPWDChanceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 定时提醒修改密码
 * @author Water Guo
 * @created 2017-4-24 上午10:44:45
 */
public class DataPWDChanceJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(StatisDataRefresh.class);

    /**
     * 引入dataPWDChanceService
     */
    private DataPWDChanceService dataPWDChanceService;
    
    /**
     * 方法_构造方法
     * 
     */
    public DataPWDChanceJob() {
        super();
        if (dataPWDChanceService == null) {
            dataPWDChanceService = (DataPWDChanceService) AppUtil.getBean("dataPWDChanceService");
        }
    }
    
    /**
     * 提示修改密码job
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("提示修改密码:"+currentDate);
        dataPWDChanceService.dataPWDChanceMsg();
        log.info("修改密码，提示完毕:"+currentDate);
    }
}
