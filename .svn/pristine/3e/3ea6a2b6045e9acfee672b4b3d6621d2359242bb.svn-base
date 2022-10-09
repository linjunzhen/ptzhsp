/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.job;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bdc.service.BdcGetWwDataService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年8月17日 上午9:31:43
 */
public class BdcWwDataFlowJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcWwDataFlowJob.class);

    /**
     * 
     */
    @Resource
    private BdcGetWwDataService bdcGetWwDataService;
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年8月17日 上午9:34:46
     */
    public BdcWwDataFlowJob() {
        super();
        if (bdcGetWwDataService == null) {
            bdcGetWwDataService = (BdcGetWwDataService) AppUtil.getBean("bdcGetWwDataService");
        }
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2020年8月17日 上午9:35:06
     * @param jobExecutionContext
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始不动产外网申请流程发起");
        List<Map<String,Object>> list = bdcGetWwDataService.findNeedStart();
        if(list!=null&&list.size()>0){
            log.info("发现不动产全流程外网数据量为:"+list.size());
        }
        for(Map<String,Object> wwData:list){
            try {
                bdcGetWwDataService.startWwDataFlow(wwData);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
        log.info("结束不动产外网申请流程发起");
    }
}
