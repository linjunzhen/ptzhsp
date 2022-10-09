/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.platform.bsfw.service.BdcApplyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.service.CommercialService;

/**
 * 描述 商事登记流程自动跳转定时任务
 * 
 * @author Reuben Bao
 * @created 2019年3月28日 上午10:19:46
 */
public class ComNeedAutoJumpJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ComNeedAutoJumpJob.class);
    /**
     * 引入service
     */
    @Resource
    private CommercialService commercialService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/13 14:33:00
     * @param
     * @return
     */
    @Resource
    private BdcApplyService bdcApplyService;

    /**
     * 方法_构造方法
     * 
     */
    public ComNeedAutoJumpJob() {
        if (commercialService == null) {
            commercialService = (CommercialService) AppUtil.getBean("commercialService");
        }
        if (bdcApplyService == null) {
            bdcApplyService = (BdcApplyService) AppUtil.getBean("bdcApplyService");
        }
    }

    /**
     * 描述 商事登记流程自动跳转
     * 
     * 
     * @author Reuben Bao
     * @created 2019年8月6日 上午11:32:20
     * @param context
     */
    @Override
    public void execute(JobExecutionContext context) {
        try {
            log.info("++++++开始执行商事登记流程自动跳转任务++++++");
            List<Map<String, Object>> list = commercialService.findNeedAutoJump();
            if (list != null && list.size() > 0) {
                log.info("发现需要自动跳转环节的商事登记流程数据量为:" + list.size());
                for (Map<String, Object> data : list) {
                    try {
                        commercialService.jumpTaskForYjhz(data);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            List<Map<String, Object>> bslist = commercialService.findBsNeedAutoJump();
            if (bslist != null && bslist.size() > 0) {
                log.info("发现需要自动跳转环节的商事登记并审汇总数据量为:" + bslist.size());
                for (Map<String, Object> bsdata : bslist) {
                    try {
                        commercialService.jumpTaskForYjhz(bsdata);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行商事登记流程自动跳转任务++++++");

            log.info("++++++开始执行不动产登记资料查询自动跳转任务++++++");
            List<Map<String, Object>> listBdc = bdcApplyService.findNeedAutoJumpBdcdjzlcx();
            if (listBdc != null && listBdc.size() > 0) {
                log.info("发现需要自动跳转环节的不动产登记资料查询数据量为:" + listBdc.size());
                for (Map<String, Object> data : listBdc) {
                    try{
                        bdcApplyService.jumpTaskForBdcdjzlcx(data);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行不动产登记资料查询自动跳转任务++++++");

        } catch (Exception e) {
            log.error("执行商事登记流程自动跳转定时器报错：CommercialJob.execute()", e);
        }
    }
}
