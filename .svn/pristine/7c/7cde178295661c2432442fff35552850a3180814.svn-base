/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.job;


import net.evecom.core.util.AppUtil;
import net.evecom.platform.project.service.ProjectApplyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述：工程建设项目赋码信息变更推送定时器
 * @author Rider Chen
 * @created 2019年6月19日 下午4:40:39
 */
public class ProjectCodePushJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectCodePushJob.class);

    /**
     * LicenseService
     */
    private ProjectApplyService projectApplyService;

    /**
     * 方法_构造方法
     *
     */
    public ProjectCodePushJob() {
        super();
        if (projectApplyService == null) {
            projectApplyService = (ProjectApplyService) AppUtil.getBean("projectApplyService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("开始执行赋码信息变更推送定时任务");
        try {
            projectApplyService.pushChangeXmjbxxb(null);//赋码信息变更推送
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        log.info("结束执行赋码信息变更推送定时任务");
    }

}
