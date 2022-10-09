/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.platform.evaluate.service.EvaluateService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.SysSendMsgService;

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
public class MsgSendSynchoJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(MsgSendSynchoJob.class);
    /**
     * 引入 zgptInterfaceService
     */
    private SysSendMsgService sysSendMsgService;
    /**
     * 引入 zgptInterfaceService
     */
    private ExeDataService exeDataService;

    /**
     * LicenseService
     */
    private EvaluateService evaluateService;

    /**
     * 方法_构造方法
     * 
     */
    public MsgSendSynchoJob() {
        super();
        if (sysSendMsgService == null) {
            sysSendMsgService = (SysSendMsgService) AppUtil.getBean("sysSendMsgService");
        }
        if (exeDataService == null) {
            exeDataService = (ExeDataService) AppUtil.getBean("exeDataService");
        }
        if (evaluateService == null) {
            evaluateService = (EvaluateService) AppUtil.getBean("evaluateService");
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
        log.info("开始调用短信发送定时任务......Start");
        sysSendMsgService.sendMsgList();
        log.info("结束调用短信发送定时任务......End");
        
        log.info("开始调用中国移动短信发送任务......Start");
        HttpRequestUtil.sendGet("http://192.168.129.178/exeDataController/sendMsgByTime.do","");
        log.info("结束调用中国移动短信发送任务......End");

        log.info("开始执行审批系统办结办件短信评价定时器任务");
        evaluateService.sendEvaluateMsg();
        log.info("结束执行审批系统办结办件短信评价定时器任务");
    }
}
