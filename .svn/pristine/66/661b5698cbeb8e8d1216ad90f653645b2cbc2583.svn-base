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
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.FlowEventService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 定时同步证照目录
 * @author Water Guo
 * @created 2018-11-15 上午10:44:45
 */
public class SocialFormJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(SocialFormJob.class);

    /**
     * 引入dataPWDChanceService
     */
    private FlowEventService flowEventService;
    /**
     * 引入exeDataService
     */
    private ExeDataService exeDataService;

    /**
     * 方法_构造方法
     *
     */
    public SocialFormJob() {
        super();
        if (flowEventService == null) {
            flowEventService = (FlowEventService) AppUtil.getBean("flowEventService");
        }
        if (exeDataService == null) {
            exeDataService = (ExeDataService) AppUtil.getBean("exeDataService");
        }
    }
    
    /**
     * 提示同步证照目录job
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String currentDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        log.info("生成社会事业表:"+currentDate);
        List<Map<String,Object>>  list=exeDataService.findExeIdOfCommercial("2017-01-01");
        for (int i = 0; i < 20; i++) {
            Map<String, Object> map = list.get(i);
            String exeId = map.get("EXE_ID") == null ? "" : map.get("EXE_ID").toString();
            if (StringUtils.isNotEmpty(exeId)) {
                flowEventService.createSocialForm(exeId);
            }
        }
        log.info("生成社会事业表:"+currentDate);
    }
}
