/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.system.job;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.service.CreditService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 
 * 描述 定时同步证照目录
 * @author Water Guo
 * @created 2018-11-15 上午10:44:45
 */
public class EvidenceMetaJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(EvidenceMetaJob.class);

    /**
     * 引入dataPWDChanceService
     */
    private CreditService creditService;

    /**
     * 方法_构造方法
     *
     */
    public EvidenceMetaJob() {
        super();
        if (creditService == null) {
            creditService = (CreditService) AppUtil.getBean("creditService");
        }
    }
    
    /**
     * 提示同步证照目录job
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("提示同步证照目录:"+currentDate);
        creditService.saveOrgListByInterface();
        log.info("同步证照目录，提示完毕:"+currentDate);
    }
}
