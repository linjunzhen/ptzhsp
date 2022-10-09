/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.system.service.PushDataToSJJXXZXService;

/**
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年12月2日 上午10:58:05
 */
public class PushDataToSJJXXZXJOB3 implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(PushDataToSJJXXZXJOB3.class);
    /**
     * 引入service
     */
    @Resource
    private PushDataToSJJXXZXService pushDataToSJJXXZXService;

    /**
     * 方法_构造方法
     * 
     */
    public PushDataToSJJXXZXJOB3() {
        super();
        if (pushDataToSJJXXZXService == null) {
            pushDataToSJJXXZXService = (PushDataToSJJXXZXService) AppUtil.getBean("pushDataToSJJXXZXService");
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
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        pushDataToSJJXXZXService.pollingWithAddCounterSourceInfo();
    }
}
