/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.system.service.LinkWarnMsgService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 短信息发送定时任务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年12月2日 上午10:58:05
 */
public class LinkWarnMsgJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(LinkWarnMsgJob.class);
    /**
     * 引入 zgptInterfaceService
     */
    private LinkWarnMsgService linkWarnMsgService;

    /**
     * 方法_构造方法
     * 
     */
    public LinkWarnMsgJob() {
        super();
        if (linkWarnMsgService == null) {
            linkWarnMsgService = (LinkWarnMsgService) AppUtil.getBean("linkWarnMsgService");
        }
    }

    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("开始调用挂起短信预警定时任务......");
        linkWarnMsgService.proWarnMsg(log);
        log.info("结束调用挂起短信预警定时任务......");

    }
}
