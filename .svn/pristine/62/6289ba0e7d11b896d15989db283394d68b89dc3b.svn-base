/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.efile.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.efile.service.ElectronicFileService;

/**
 * 描述电子文件信息提交定时器
 * 
 * @author Luffy Cai
 *
 */
public class SaveEfileInfoJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(SaveEfileInfoJob.class);

    /**
     * ElectronicFileService
     */
    private ElectronicFileService electronicFileService;

    /**
     * 方法_构造方法
     * 
     */
    public SaveEfileInfoJob() {
        super();
        if (electronicFileService == null) {
            electronicFileService = (ElectronicFileService) AppUtil.getBean("electronicFileService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("开始执行可信电子文件信息提交定时任务");
        try {
            electronicFileService.submitEfileInfo();
            electronicFileService.saveEfileProjectInfo();
            electronicFileService.saveEfileProcessInfo();
            electronicFileService.submitResultEfileInfo();
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("结束执行可信电子文件信息提交定时任务");
    }

}
