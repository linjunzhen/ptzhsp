/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.esuper.service.EsuperClientService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 发送监察数据基本信息的JOB
 * 
 * @author Derek Zhang
 * @created 2016年2月1日 下午2:33:10
 */
public class EsuperSendJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(EsuperSendJob.class);
    /**
     * swbProvDataService
     */
    private EsuperClientService esuperClientService;

    /**
     * 方法_构造方法
     * 
     */
    public EsuperSendJob() {
        super();
        if (esuperClientService == null) {
            esuperClientService = (EsuperClientService) AppUtil.getBean("esuperClientService");
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
        esuperClientService.sendEsupers(log);
    }

}
