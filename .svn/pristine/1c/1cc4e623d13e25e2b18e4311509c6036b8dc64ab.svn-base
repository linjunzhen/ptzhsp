/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.elicense.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.elicense.service.ElectronicLicenseService;

/**
 * 描述 电子证照业务相关dao实现类
 * 
 * @author Roy Li
 *
 */
public class SaveElicenseInfoJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(SaveElicenseInfoJob.class);

    /**
     * ElectronicLicenseService
     */
    private ElectronicLicenseService electronicLicenseService;

    /**
     * 方法_构造方法
     * 
     */
    public SaveElicenseInfoJob() {
        super();
        if (electronicLicenseService == null) {
            electronicLicenseService = (ElectronicLicenseService) AppUtil.getBean("electronicLicenseService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("开始执行电子证照-申报登记信息定时任务");
        try {
            electronicLicenseService.declareRegisterSubmit(); // 电子证照-申报登记信息提交
            electronicLicenseService.declareMaterialSubmit(); // 电子证照-申报登记信息材提交
            electronicLicenseService.declareResultSubmit(); // 电子证照-申报信息结果提交
            electronicLicenseService.declareResultCertificateSubmit(); // 电子证照-申报结果证照提交
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("结束执行电子证照-申报登记信息定时任务");
    }

}
