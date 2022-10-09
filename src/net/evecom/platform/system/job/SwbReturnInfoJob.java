


/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.bsfw.service.DataAbutmentService;
import net.evecom.platform.wsbs.service.SwbDataParseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 省网办数据解析接定时任务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年1月21日 下午3:53:54
 */
public class SwbReturnInfoJob implements Job {
    /**SwbReturnInfoJob
     * log
     */
    private static Log log = LogFactory.getLog(SwbDataParsJob.class);

    /**
     * swbDataParseService
     */
    private SwbDataParseService swbDataParseService;

    /**
     * 方法_构造方法
     * 
     */
    public SwbReturnInfoJob() {
        super();
        if (swbDataParseService == null) {
            swbDataParseService = (SwbDataParseService) AppUtil.getBean("swbDataParseService");
        }
    }

    /**
     * 
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
        
         log.info("TTTTTTTTTTTTTT------获取省网反馈信息-----------");  
         
        swbDataParseService.parseReturnInfos(log);
    }

}
