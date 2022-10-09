/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.platform.system.service.CreditDataInputService;
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
public class CreditDataInputSynchoJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CreditDataInputSynchoJob.class);
    /**
     * 引入 CreditDataInputService
     */
    private CreditDataInputService creditDataInputService;

    /**
     * 审批互联网服务器地址
     */
    private final String URL = "http://192.168.129.178/creditDataInputController/creditDataInputSend.do";

    /**
     * 方法_构造方法
     * 
     */
    public CreditDataInputSynchoJob() {
        super();
        if (creditDataInputService == null) {
            creditDataInputService = (CreditDataInputService) AppUtil.getBean("creditDataInputService");
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
        /*将定时任务转发到审批互联网服务器上运行*/
        log.info("开始推送平潭市场主体信用汇总数据......");
        HttpRequestUtil.sendPost(URL, null);
        log.info("结束推送平潭市场主体信用汇总数据......");
    }
}
