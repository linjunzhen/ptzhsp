/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.elicensejsoninfo.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.elicensejsoninfo.service.ElectronicLicenseJsonInfoService;

/**
 * 描述 电子证照业务相关dao实现类
 * 
 * @author Roy Li
 *
 */
public class SaveElicenseJsonInfoJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(SaveElicenseJsonInfoJob.class);

    /**
     * ElectronicLicenseJsonInfoService
     */
    private ElectronicLicenseJsonInfoService electronicLicenseJsonInfoService;

    /**
     * 方法_构造方法
     * 
     */
    public SaveElicenseJsonInfoJob() {
        super();
        if (electronicLicenseJsonInfoService == null) {
            electronicLicenseJsonInfoService = (ElectronicLicenseJsonInfoService) AppUtil.getBean("electronicLicenseJsonInfoService");
        }
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("开始执行电子证照-getCertificateDataSubmitExample定时任务");
        try {
            electronicLicenseJsonInfoService.getCzwsJsonInfo(); // 电子证照-城镇污水排入排水管网许可证-证照照面范例
            electronicLicenseJsonInfoService.getLmcfJsonInfo(); // 电子证照-林木采伐许可证-证照照面范例
            electronicLicenseJsonInfoService.getSpfysJsonInfo(); // 电子证照-商品房预售许可证-证照照面范例
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("结束执行电子证照-getCertificateDataSubmitExample定时任务");
    }

}
