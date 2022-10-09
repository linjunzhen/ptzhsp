/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.zzhy.service.ResetPushStatusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述  开普云对接重置状态定时任务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class ResetPushStatusJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ResetPushStatusJob.class);

    /**
     * statistCommercialService
     */
    private ResetPushStatusService resetPushStatusService;

    /**
     * 方法_构造方法
     *
     */
    public ResetPushStatusJob() {
        super();
        if (resetPushStatusService == null) {
            resetPushStatusService = (ResetPushStatusService) AppUtil.getBean("resetPushStatusService");
        }
    }

    /**
     *
     * 描述
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see Job#execute(JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("----------开普云对接重置状态调用开始-----------");
        resetPushStatusService.resetPushStatus();
        log.info("----------开普云对接重置状态调用结束-----------");
    }
}
