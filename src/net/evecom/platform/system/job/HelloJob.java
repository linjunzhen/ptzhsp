/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月29日 上午11:16:48
 */
public class HelloJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(HelloJob.class);

    /**
     * 调用定时器任务
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("调用了定时器");
    }

}
