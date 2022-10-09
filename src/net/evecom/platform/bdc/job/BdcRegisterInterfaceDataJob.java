/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.job;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bdc.service.BdcRegisterInterfaceService;

/**
 * 描述:不动产获取省外网数据定时器（内网）
 *
 * @author Scolder Lin
 * @created 2021/07/09 11:12
 */
public class BdcRegisterInterfaceDataJob implements Job{
    /**
     * 引入service
     */
    private BdcRegisterInterfaceService bdcRegisterInterfaceService;
    /**
     * 引入log
     */
    private static Log log = LogFactory.getLog(BdcRegisterInterfaceDataJob.class);
    
    /**
     * 描述:
     *
     * @author Scolder Lin
     * @created 2021/07/09 11:14:30
     * @param 
     * @return
     */
    public BdcRegisterInterfaceDataJob() {
        super();
        if (bdcRegisterInterfaceService == null) {
            bdcRegisterInterfaceService = (BdcRegisterInterfaceService) AppUtil.getBean("bdcRegisterInterfaceService");
        }
    }
    /**
     * 描述:执行方法
     *
     * @author Scolder Lin
     * @created 2021/07/09 11:16:00
     * @param context
     * @return
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("开始调用不动产推送办理数据接口数据定时器");
        try {
            bdcRegisterInterfaceService.extranetApplyReport(log);
        } catch (IOException e) {
            log.info("推送不动产办理数据失败");
        }
        log.info("结束调用不动产推送办理数据接口定时器");
        
        log.info("开始调用不动产推送办结数据接口定时器");
        try {
            bdcRegisterInterfaceService.extranetConcludeReport(log);
        } catch (IOException e) {
            log.info("推送不动产办结数据失败");
        }
        log.info("结束调用不动产推送办结数据接口数据定时器");
    }

}
