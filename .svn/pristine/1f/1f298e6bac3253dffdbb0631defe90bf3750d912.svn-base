/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.project.service.ProvinceInterfaceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述： 消防设计审核向住建上报数据定时器
 * @author Rider Chen
 * @created 2020年5月8日 下午3:25:34
 */
public class ProjectOfXfsjshPushFlowJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectDataJob.class);

    /**
     * LicenseService
     */
    private ProvinceInterfaceService provinceInterfaceService;

    /**
     * 方法_构造方法
     *
     */
    public ProjectOfXfsjshPushFlowJob() {
        super();
        if (provinceInterfaceService == null) {
            provinceInterfaceService = (ProvinceInterfaceService) AppUtil.getBean("provinceInterfaceService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("开始执行消防设计审核向住建上报数据任务");
        try {
            provinceInterfaceService.sendToProvince();
        } catch (Exception e) {
            // TODO: handle exception
            log.error("", e);
        }
        log.info("结束执行消防设计审核向住建上报数据任务");
    }

}
