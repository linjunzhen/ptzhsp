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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述  开普云对接定时任务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class CompanyPushDataJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CompanyPushDataJob.class);

    /**
     * statistCommercialService
     */
    private CreatTaskInterService creatTaskInterService;

    /**
     * 方法_构造方法
     *
     */
    public CompanyPushDataJob() {
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
        log.info("----------开普云业务数据接口调用开始-----------");
        try{
            creatTaskInterService.pushDataOfSubmit();
            creatTaskInterService.pushDataOfSubmitToGt();
        }catch (Exception e){
            log.error("开普云业务数据接口调用报错",e);
        }
        log.info("----------开普云接口业务数据调用结束-----------");
    }
}
