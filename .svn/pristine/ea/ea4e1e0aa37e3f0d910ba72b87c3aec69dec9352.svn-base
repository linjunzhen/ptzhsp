/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.platform.evaluate.service.EvaluateService;
import net.evecom.platform.project.service.ProjectApplyService;
/**
 * 
 * 描述： 好差评系统评价数据补报定时器
 * @author Luffy Cai
 * @created 2021年12月15日 下午3:25:34
 */
public class EvaluateAugustSupplementJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(EvaluateAugustSupplementJob.class);

    /**
     * LicenseService
     */
    private EvaluateService evaluateService;
    /**
     * 审批互联网服务器地址
     */
    private final String URL = "http://192.168.129.178/evaluateController/getEvaluationSupplementAugust.do";

    /**
     * 方法_构造方法
     * 
     */
    public EvaluateAugustSupplementJob() {
        super();
        if (evaluateService == null) {
            evaluateService = (EvaluateService) AppUtil.getBean("evaluateService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        /*将定时任务转发到审批互联网服务器上运行*/
        log.info("开始执行好差评系统评价数据上报定时任务");
        HttpRequestUtil.sendPost(URL, null);
        log.info("结束执行好差评系统评价数据上报定时任务");
    }

}
