/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.job;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.project.service.ProjectApplyService;
/**
 * 
 * 描述：
 * @author Rider Chen
 * @created 2020年6月18日 下午12:50:08
 */
public class ProjectDataTimerJob  implements Job{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectDataTimerJob.class);
    /**
     * projectApplyService
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * 
     * 描述： 构造方法
     * @author Rider Chen
     * @created 2020年6月18日 下午12:51:01
     */
    public ProjectDataTimerJob(){
        super();
        if (projectApplyService == null) {
            projectApplyService = (ProjectApplyService) AppUtil.getBean("projectApplyService");
        }
        log.info("开始执行工程建设项目数据共享定时任务");
        try {
            projectApplyService.saveDfxmsplcxxb(null);
            projectApplyService.saveDfxmsplcjdxxb(null);
            projectApplyService.saveDfxmsplcjdsxxxb(null);
            projectApplyService.saveXmjbxxb(null);
            //projectApplyService.pushChangeXmjbxxb(null);//赋码信息变更推送
            projectApplyService.saveXmdwxxb(null);
            projectApplyService.saveXmspsxblxxb(null);
            projectApplyService.saveXmspsxblxxxxb(null);
            projectApplyService.saveXmqtfjxxb(null);
            projectApplyService.saveXmspsxbltbcxxxb(null);
            projectApplyService.saveXmspsxpfwjxxb(null);
            projectApplyService.saveXmspsxzqyjxxb(null);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        log.info("结束执行工程建设项目数据共享定时任务");
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        
    }

}
