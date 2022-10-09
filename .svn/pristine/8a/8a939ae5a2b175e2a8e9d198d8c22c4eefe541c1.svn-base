/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.util.Map;

import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.ZgptInterfaceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 中国平潭网公示信息发布定时任务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年12月2日 上午10:58:05
 */
public class ZgptPublishDataSynchoJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ZgptPublishDataSynchoJob.class);
    /**
     * 引入 zgptInterfaceService
     */
    private ZgptInterfaceService zgptInterfaceService;

    /**
     * 方法_构造方法
     * 
     */
    public ZgptPublishDataSynchoJob() {
        super();
        if (zgptInterfaceService == null) {
            zgptInterfaceService = (ZgptInterfaceService) AppUtil.getBean("zgptInterfaceService");
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
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("开始调用公示发布定时任务，向中国平潭网发布公示信息....");
        // 获取内网配置的数据对接配置列表信息
        Map<String, Object> dataAbutment = this.zgptInterfaceService.getByJdbc(
                "t_bsfw_dataabutment", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZGPT });
        if (dataAbutment != null) {
            zgptInterfaceService.publishGSXX(dataAbutment);
            log.info("结束调用公示发布定时任务，向中国平潭网发布公示信息....");
        }
    }
}
