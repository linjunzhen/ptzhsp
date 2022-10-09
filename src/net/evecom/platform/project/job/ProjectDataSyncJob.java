/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.job;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import net.evecom.core.util.AppUtil;
import net.evecom.platform.project.service.ProjectApplyService;
/**
 * 
 * 描述 工程建设项目数据同步定时器
 * @author Danto Huang
 * @created 2021年11月17日 上午9:59:35
 */
public class ProjectDataSyncJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectDataSyncJob.class);

    /**
     * LicenseService
     */
    private ProjectApplyService projectApplyService;

    /**
     * 方法_构造方法
     * 
     */
    public ProjectDataSyncJob() {
        super();
        if (projectApplyService == null) {
            projectApplyService = (ProjectApplyService) AppUtil.getBean("projectApplyService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("开始执行工程建设项目数据共享定时任务");
        try {
            projectApplyService.saveDfxmsplcxxb(null);
            projectApplyService.saveDfxmsplcjdxxb(null);
            projectApplyService.saveDfxmsplcjdsxxxb(null);
            projectApplyService.saveXmjbxxb(null);
            projectApplyService.pushChangeXmjbxxb(null);//赋码信息变更推送
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
        
        log.info("开始执行工程建设项目是否办结定时任务");
        List<Map<String, Object>> jbxxList = projectApplyService.findNoEndXmjbxxb("0");
        for (Map<String, Object> map : jbxxList) {
            String id = map.get("ID").toString();
            String projectCode = map.get("PROJECT_CODE").toString();
            boolean flag = projectApplyService.isNoEndXmjbxxb(projectCode);
            if(flag){//已办结 ，更改项目状态
                projectApplyService.updateXmsfwqbj(id);
            }
        }
        log.info("结束执行工程建设项目是否办结定时任务");
    }

}
