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
 * 描述：定时查询工程建设项目是否办结
 * @author Rider Chen
 * @created 2019年6月19日 下午4:42:14
 */
public class ProjectIsEndJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectIsEndJob.class);

    /**
     * LicenseService
     */
    private ProjectApplyService projectApplyService;

    /**
     * 方法_构造方法
     * 
     */
    public ProjectIsEndJob() {
        super();
        if (projectApplyService == null) {
            projectApplyService = (ProjectApplyService) AppUtil.getBean("projectApplyService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
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
