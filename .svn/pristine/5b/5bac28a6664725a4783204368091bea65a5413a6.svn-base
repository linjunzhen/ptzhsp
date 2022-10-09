/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.esuper.service.EsuperServerService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 接收监察预警数据的JOB
 * 
 * @author Derek Zhang
 * @created 2016年2月1日 下午2:33:10
 */
public class EsuperReceiveJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(EsuperReceiveJob.class);
    /**
     * swbProvDataService
     */
    private EsuperServerService esuperServerService;

    /**
     * 方法_构造方法
     * 
     */
    public EsuperReceiveJob() {
        super();
        if (esuperServerService == null) {
            esuperServerService = (EsuperServerService) AppUtil.getBean("esuperServerService");
        }
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年2月1日 下午2:34:00
     * @param arg0
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        esuperServerService.receiveEsupers(log);
    }

}
