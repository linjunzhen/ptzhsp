/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tyjk.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.tyjk.service.FlowWebService;
/**
 * 
 * 描述：定时删除不动产附件信息表中的数据
 * @author Rider Chen
 * @created 2019年6月14日 下午5:16:07
 */
public class DelFileTransJob implements Job{

    /**
     * log
     */
    private static Log log = LogFactory.getLog(DelFileTransJob.class);

    /**
     * 引入flowWebService
     */
    private FlowWebService flowWebService;
    /**
     * 方法_构造方法
     * 
     */
    public DelFileTransJob() {
        super();
        if (flowWebService == null) {
            flowWebService = (FlowWebService) AppUtil.getBean("flowWebService");
        }
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("附件接口文件表删除一个月前的数据定时任务开始");
        flowWebService.delFileTrans();
        log.info("附件接口文件表删除一个月前的数据定时任务结束");
    }

}
