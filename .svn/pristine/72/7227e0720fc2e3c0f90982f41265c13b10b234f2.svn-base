/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.hflow.service.ComEffiService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月29日 上午10:54:13
 */
public class ComEffiScheduleJob implements Job{
    /**
     * 日志写入
     */
    private static Log log = LogFactory.getLog(ComEffiScheduleJob.class);
    /**
     * 引入 CreditDataInputService
     */
    private ComEffiService comEffiService;

    /**
     * 方法_构造方法
     * 
     */
    public ComEffiScheduleJob() {
        super();
        if (comEffiService == null) {
            comEffiService = (ComEffiService) AppUtil.getBean("comEffiService");
        }
    }

    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        comEffiService.comEffiStatistics(log);
    }
}
