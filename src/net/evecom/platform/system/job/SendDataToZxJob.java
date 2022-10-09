/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.ZxInterfaceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 发送数据到总线定时任务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年1月21日 下午3:53:54
 */
public class SendDataToZxJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SendDataToZxJob.class);

    /**
     * zxInterfaceService
     */
    private ZxInterfaceService zxInterfaceService;

    /**
     * 方法_构造方法
     * 
     */
    public SendDataToZxJob() {
        super();
        if (zxInterfaceService == null) {
            zxInterfaceService = (ZxInterfaceService) AppUtil.getBean("zxInterfaceService");
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        zxInterfaceService.sendDataToZx();
    }

}
