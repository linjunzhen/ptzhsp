/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.fjfda.service.FjfdaTransService;

/**
 * 描述 调用省平台更新业务流程状态
 * @author Keravon Feng
 * @created 2019年3月5日 上午9:38:15
 */
public class FjfdaUpdateStatusJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FjfdaUpdateStatusJob.class);
    /**
     * fjfdaTransService
     */
    @Resource
    private FjfdaTransService fjfdaTransService;
    
    /**
     * 描述 FjfdaUpdateStatusJob
     * @author Keravon Feng
     * @created 2019年3月5日 上午9:45:51
     */
    public FjfdaUpdateStatusJob(){
        super();
        if (fjfdaTransService == null) {
            fjfdaTransService = (FjfdaTransService) AppUtil.getBean("fjfdaTransService");
        }
    }
    
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("++++++++开始批量更新食药业务数据的流程状态++++++++");
        List<Map<String,Object>> list = fjfdaTransService.listUpdateStatusData(50);
        for(Map<String,Object> map : list){
            fjfdaTransService.updateFlowRunStatus(map);
        }
        log.info("++++++++结束批量更新食药业务数据的流程状态++++++++");
    }

}
