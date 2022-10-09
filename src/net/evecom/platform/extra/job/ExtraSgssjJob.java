/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.extra.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.extra.service.ExtraSgssjService;

/**
 * 
 * 描述 双公示数据
 * @author Rider Chen
 * @created 2021年4月22日 下午6:05:06
 */
public class ExtraSgssjJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ExtraSgssjJob.class);
    /**
     * 引入flowWebService
     */
    private ExtraSgssjService extraSgssjService;
    /**
     * 方法_构造方法
     * 
     */
    public ExtraSgssjJob() {
        super();
        if (extraSgssjService == null) {
            extraSgssjService = (ExtraSgssjService) AppUtil.getBean("extraSgssjService");
        }
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("双公示数据解密保存开始");
        extraSgssjService.saveSgssj();
        log.info("双公示数据解密保存结束");
    }

}
