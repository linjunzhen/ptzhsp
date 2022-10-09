/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.BdcDataToSwbService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述  生成要推送到省网的不动产数据（办理数据和办结数据）
 * @version 1.0
 * @author Yanisin Shi
 */
public class BdcProDataToSwbJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcProDataToSwbJob.class);

    /**
     * bdcDataToSwbService
     */
    private BdcDataToSwbService bdcDataToSwbService;

    /**
     * 方法_构造方法
     * 
     */
    public BdcProDataToSwbJob() {
        super();
        if (bdcDataToSwbService == null) {
            bdcDataToSwbService = (BdcDataToSwbService) AppUtil.getBean("bdcDataToSwbService");
        }
    }

    /**
     * 
     * 描述 生成要推送到省网的不动产数据 包括办理和办结的数据
     * @author Yanisin Shi
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("《---------------------开始生成要推送到省网的不动产业务数据（办理、办结）---------------------------------》");
        bdcDataToSwbService.processData();
    }

}
