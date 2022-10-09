/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.service.CommercialService;

/**
 * 描述 五星流程、面签 自动跳转
 * 
 * @author Reuben Bao
 * @created 2019年3月28日 上午10:19:46
 */
public class SCYJDAutoJumpJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SCYJDAutoJumpJob.class);
    /**
     * 引入service
     */
    @Resource
    private CommercialService commercialService;

    /**
     * 方法_构造方法
     * 
     */
    public SCYJDAutoJumpJob() {
        if (commercialService == null) {
            commercialService = (CommercialService) AppUtil.getBean("commercialService");
        }
    }

    /**
     * 描述 五星流程、面签 自动跳转（）
     * 
     * 
     * @author Reuben Bao
     * @created 2019年8月6日 上午11:32:20
     * @param context
     */
    @Override
    public void execute(JobExecutionContext context) {
        try {
            //因城信所1分钟在审批取一次数据。审查与决定需停留时间大于2分钟
            log.info("++++++开始执行审查与决定自动跳转任务++++++");
            List<Map<String, Object>> listscyjd = commercialService.findSCYJDNeedAutoJump();
            if (listscyjd != null && listscyjd.size() > 0) {
                log.info("发现需要自动跳转环节流程数据量为:" + listscyjd.size());
                for (Map<String, Object> data : listscyjd) {
                    try {
                        commercialService.jumpTaskForYjhz(data);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行审查与决定自动跳转任务++++++");

        } catch (Exception e) {
            log.error("执行五星流程自动跳转任务,面签自动跳转,抵押转本智能审批定时器报错：ComWXAutoJumpJob.execute()", e);
        }
    }
}
