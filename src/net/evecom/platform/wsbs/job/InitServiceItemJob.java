/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * 描述  初始化事项列表基础数据定时器
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class InitServiceItemJob implements StatefulJob {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(InitServiceItemJob.class);

    /**
     * 事项Service
     */
    private ServiceItemService serviceItemService;

    /**
     * 方法_构造方法
     *
     */
    public InitServiceItemJob() {
        super();
        if (serviceItemService == null) {
            serviceItemService = (ServiceItemService) AppUtil.getBean("serviceItemService");
        }
    }

    /**
     *
     * 描述
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see Job#execute(JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("----------事项列表基础数据定时器调用开始-----------");
        serviceItemService.initServiceItem("100");
        log.info("----------事项列表基础数据定时器调用结束-----------");
    }
}
