/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.SwbInterOfBusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 描述  数据对接定时任务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class SendBusDataToSwbJob implements Job {

    /**
     * applyInfoService
     */
    private SwbInterOfBusService swbInterOfBusService;
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SendBusDataToSwbJob.class);
    /**
     * 方法_构造方法
     *
     */
    public SendBusDataToSwbJob() {
        super();
        if (swbInterOfBusService == null) {
            swbInterOfBusService = (SwbInterOfBusService) AppUtil.getBean("swbInterOfBusService");
        }
    }

    /**
     *
     * 描述  推送总线数据到省网办
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see Job#execute(JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("《------------------开始推送总线数据到省网办------------------》");
        swbInterOfBusService.sendToProvince();
    }

}
