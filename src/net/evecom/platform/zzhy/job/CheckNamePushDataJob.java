/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.zzhy.service.CreatTaskInterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述  开普云对接定时任务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class CheckNamePushDataJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CheckNamePushDataJob.class);

    /**
     * statistCommercialService
     */
    private CreatTaskInterService creatTaskInterService;

    /**
     * 方法_构造方法
     *
     */
    public CheckNamePushDataJob() {
        super();
        if (creatTaskInterService == null) {
            creatTaskInterService = (CreatTaskInterService) AppUtil.getBean("creatTaskInterService");
        }
    }

    /**
     *
     * 描述
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see Job#execute(JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("----------开普云名称查重接口调用开始-----------");
        try{
            creatTaskInterService.pushDataOfQueryCompanyName();
            creatTaskInterService.pushDataOfQueryCompanyNameToGt();
        }catch (Exception e){
            log.error("开普云名称查重接口调用报错",e);
        }
        log.info("----------开普云名称查重接口调用结束-----------");
    }
}
