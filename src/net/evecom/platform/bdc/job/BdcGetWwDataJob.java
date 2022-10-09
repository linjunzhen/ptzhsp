/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.job;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bdc.service.BdcGetWwDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * 描述:不动产获取省外网数据定时器（内网）
 *
 * @author Madison You
 * @created 2020/04/15 11:19
 */
public class BdcGetWwDataJob implements Job {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 11:28:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcGetWwDataJob.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 11:27:00
     * @param
     * @return
     */
    @Resource
    private BdcGetWwDataService bdcGetWwDataService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 11:28:00
     * @param
     * @return
     */
    public BdcGetWwDataJob() {
        super();
        if (bdcGetWwDataService == null) {
            bdcGetWwDataService = (BdcGetWwDataService) AppUtil.getBean("bdcGetWwDataService");
        }
    }


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 11:21:00
     * @param
     * @return
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始调用不动产获取省外网数据定时器");
        bdcGetWwDataService.bdcGetWwData(log);
        log.info("结束调用不动产获取省外网数据定时器");
    }
}
