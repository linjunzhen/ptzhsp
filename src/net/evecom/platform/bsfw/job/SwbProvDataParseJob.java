/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import net.evecom.core.util.AppUtil;
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
public class SwbProvDataParseJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SwbProvDataParseJob.class);
    /**
     * swbProvDataService
     */
    private SwbDataParseService swbDataParseService;
    
    /**
     * 方法_构造方法
     * 
     */
    public SwbProvDataParseJob() {
        super();
        if (swbDataParseService == null) {
            swbDataParseService = (SwbDataParseService) AppUtil.getBean("swbDataParseService");
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月27日 下午3:59:40
     * @param arg0
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        swbDataParseService.parseSwbDataFromProa(log);
    }

}
