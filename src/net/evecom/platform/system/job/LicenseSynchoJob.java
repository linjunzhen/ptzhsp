/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.LicenseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 电子证照生成盖单和证照文件获取定时任务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年12月18日 下午2:53:43
 */
public class LicenseSynchoJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(LicenseSynchoJob.class);

    /**
     * LicenseService
     */
    private LicenseService licenseService;

    /**
     * 方法_构造方法
     * 
     */
    public LicenseSynchoJob() {
        super();
        if (licenseService == null) {
            licenseService = (LicenseService) AppUtil.getBean("licenseService");
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Derek Zhang
     * @created 2015年12月18日 下午2:53:43
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        licenseService.sendLicenseDataToZZZX(log);
    }

}
