/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.BdcDataParseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 不动产数据同步定时器
 * @author Kester Chen
 * @created 2018-3-9 下午4:45:51
 */
public class BdcProvDataSituationJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcProvDataSituationJob.class);
    /**
     * bdcDataParseService
     */
    private BdcDataParseService bdcDataParseService;
    
    /**
     * 方法_构造方法
     * 
     */
    public BdcProvDataSituationJob() {
        super();
        if (bdcDataParseService == null) {
            bdcDataParseService = (BdcDataParseService) AppUtil.getBean("bdcDataParseService");
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
        bdcDataParseService.bdcProvDataSituation(log);
    }

}
