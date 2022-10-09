/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.project.service.ProjectXFService;

/**
 * 
 * 描述：工程建设项目消防事项数据共享定时器
 * @author Scolder Lin
 * @created 2020年1月8日 下午5:30:39
 */
public class ProjectXFDataJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectDataJob.class);
    
    /**
     * projectXFService
     */
    private ProjectXFService projectXFService;
    
    /**
     * 方法_构造方法
     * 
     */
    public ProjectXFDataJob() {
        super();
        if (projectXFService == null) {
            projectXFService = (ProjectXFService) AppUtil.getBean("projectXFService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("开始执行工程建设项目消防事项数据共享定时任务");
        try {
            projectXFService.saveXFBaseInfo();
            projectXFService.saveXFUnitInfo();
            projectXFService.saveXFCorpInfo();
            projectXFService.saveXFStorageInfo();
            projectXFService.saveXFYardInfo();
            projectXFService.saveXFInsuInfo();
            projectXFService.saveXFDecorateInfo();
            projectXFService.saveFinishManageInfo();
            projectXFService.saveXFYsbaInfo();
            projectXFService.saveXFYsqkInfo();
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("结束执行工程建设项目消防事项数据共享定时任务");
    }

}
