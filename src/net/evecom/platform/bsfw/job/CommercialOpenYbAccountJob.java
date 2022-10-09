/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.commercial.service.CommercialSetService;

/**
 * 描述  商事登记-内资+个人独资业务开办医保账户定时器
 * @author Allin Lin
 * @created 2021年4月21日 下午4:16:54
 */
public class CommercialOpenYbAccountJob implements Job{
    /**
     * 日志写入
     */
    private static Log log = LogFactory.getLog(CommercialOpenYbAccountJob.class);
    
    /**
     * 引入 CreditDataInputService
     */
    private CommercialSetService commercialSetService;
    
    /**
     * 方法_构造方法
     * 
     */
    public CommercialOpenYbAccountJob() {
        super();
        if (commercialSetService == null) {
            commercialSetService = (CommercialSetService) AppUtil.getBean("commercialSetService");
        }
    }
    
    /**
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     */
    public void execute(JobExecutionContext context){
        log.info("++++++开始执行商事登记企业开办医保账户(推送失败重新推送)任务++++++");
        commercialSetService.pushYbAccount();
        log.info("++++++结束执行商事登记企业开办医保账户任务(推送失败重新推送)++++++");
    }

}
