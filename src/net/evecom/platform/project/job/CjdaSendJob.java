/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.project.service.CjdaClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 发送监察数据基本信息的JOB
 * 
 * @author  Adrian Bian
 * @created 2019年12月26日 上午09:33:10
 */
public class CjdaSendJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CjdaSendJob.class);
    /**
     * CjdaClientService
     */
    private CjdaClientService cjdaClientService;

    /**
     * 方法_构造方法
     *
     */
    public CjdaSendJob() {
        super();
        if (cjdaClientService == null) {
            cjdaClientService = (CjdaClientService) AppUtil.getBean("cjdaClientService");
        }
    }

    /**
     * 描述
     *
     * @param arg0
     * @throws JobExecutionException
     * @see Job#execute(JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        cjdaClientService.sendCjda(log);
    }

}
