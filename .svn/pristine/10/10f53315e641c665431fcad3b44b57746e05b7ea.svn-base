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
import net.evecom.platform.project.service.ProjectApplyService;
/**
 * 
 * 描述： 工程建设项目接口数据定时失效
 * @author Rider Chen
 * @created 2019年9月23日 上午9:09:54
 */
public class ProjectInvalidJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectInvalidJob.class);

    /**
     * LicenseService
     */
    private ProjectApplyService projectApplyService;
    /**
     * 方法_构造方法
     * 
     */
    public ProjectInvalidJob() {
        super();
        if (projectApplyService == null) {
            projectApplyService = (ProjectApplyService) AppUtil.getBean("projectApplyService");
        }
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("开始执行工程建设项目设置前置库数据失效");
        try {
            projectApplyService.setProjectInvalid();
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        log.info("结束执行工程建设项目设置前置库数据失效");
    }

}
