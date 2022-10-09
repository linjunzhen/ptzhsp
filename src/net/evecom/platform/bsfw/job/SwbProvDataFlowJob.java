/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.service.SwbProvDataService;
import net.evecom.platform.system.job.HelloJob;
import net.evecom.platform.wsbs.service.SwbDataParseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 省网办收办分离式启动流程定时器
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月27日 下午3:59:35
 */
public class SwbProvDataFlowJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SwbProvDataFlowJob.class);
    /**
     * swbProvDataService
     */
    private SwbProvDataService swbProvDataService;
    
    /**
     * 方法_构造方法
     * 
     */
    public SwbProvDataFlowJob() {
        super();
        if (swbProvDataService == null) {
            swbProvDataService = (SwbProvDataService) AppUtil.getBean("swbProvDataService");
        }
    }
    /**
     * 
     * 描述
     * @author Water Guo
     * @created 2018年1月27日 下午3:59:40
     * @param arg0
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Map<String,Object>> swbDatas = swbProvDataService.findNeedToStart();
        if(swbDatas!=null&&swbDatas.size()>0){
            log.info("发现省网办收办分离式数据量为:"+swbDatas.size());
        }
        for(Map<String,Object> swbData:swbDatas){
            try {
                swbProvDataService.startSwbFlow(swbData);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
