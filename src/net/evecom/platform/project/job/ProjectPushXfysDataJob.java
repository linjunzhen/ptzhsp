/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
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
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.project.service.ProjectFinishManageService;

/**
 * 
 * 描述： 向住建上报消防验收（备案）申请信息数据定时器
 * 
 * @author Luffy Cai
 * @created 2020年5月8日 下午3:25:34
 */
public class ProjectPushXfysDataJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectDataJob.class);

    /**
     * LicenseService
     */
    private ProjectFinishManageService projectFinishManageService;

    /**
     * 方法_构造方法
     * 
     */
    public ProjectPushXfysDataJob() {
        super();
        if (projectFinishManageService == null) {
            projectFinishManageService = (ProjectFinishManageService) AppUtil.getBean("projectFinishManageService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("开始执行向住建上报数据定时任务");
        try {
            projectFinishManageService.pushXfysFlowinfo();
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        log.info("结束执行向住建上报数据定时任务");
    }

}
