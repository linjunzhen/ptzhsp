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
import net.evecom.platform.bsfw.service.SwbItemDataService;
import net.evecom.platform.system.job.HelloJob;
import net.evecom.platform.wsbs.service.SwbDataParseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 省网办下发事项创建定时器
 * @author Kester Chen
 * @created 2017-11-21 上午9:32:40
 */
public class SwbItemDataParseJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SwbItemDataParseJob.class);
    /**
     * SwbItemDataService
     */
    private SwbItemDataService swbItemDataService;
    
    /**
     * 方法_构造方法
     * 
     */
    public SwbItemDataParseJob() {
        super();
        if (swbItemDataService == null) {
            swbItemDataService = (SwbItemDataService) AppUtil.getBean("swbItemDataService");
        }
    }
    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2017-11-21 上午9:33:05
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Map<String,Object>> swbDatas = swbItemDataService.findNeedToCreate();
        if(swbDatas!=null&&swbDatas.size()>0){
            log.info("发现省网办未处理下发事项数据量为:"+swbDatas.size());
        }
        log.info("开始处理省网办下发事项................................");
        for(Map<String,Object> swbData:swbDatas){
            try {
                swbItemDataService.createItem(swbData);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        log.info("处理省网办下发事项结束................................");
    }

}
