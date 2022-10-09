/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.platform.hflow.service.ExeDataService;
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
public class NewComWXAutoJumpJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(NewComWXAutoJumpJob.class);
    /**
     * 引入service
     */
    @Resource
    private CommercialService commercialService;

    /**
     * 方法_构造方法
     * 
     */
    public NewComWXAutoJumpJob() {
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
            log.info("++++++开始执行受理自动跳转任务++++++");
            List<Map<String, Object>> listwx = commercialService.findWXNeedAutoJump();
            if (listwx != null && listwx.size() > 0) {
                log.info("发现需要自动跳转环节流程数据量为:" + listwx.size());
                for (Map<String, Object> data : listwx) {
                    try {
                        String isSign = data.get("IS_SIGN")==null?
                                "":data.get("IS_SIGN").toString();
                        if (StringUtils.isNotEmpty(isSign)) {
                            if ("2".equals(isSign)) {
                                commercialService.jumpTaskForYjhz(data);
                            }else if ("5".equals(isSign)||"7".equals(isSign)||"8".equals(isSign)) {
                                commercialService.jumpTaskForZdtj(data);
                            }
                        }else{
                            commercialService.jumpTaskForYjhz(data);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行受理自动跳转任务++++++");

            log.info("++++++开始执行面签自动跳转任务++++++");
            List<Map<String, Object>> listMQ = commercialService.findMQNeedAutoJump();
            if (listMQ != null && listMQ.size() > 0) {
                log.info("发现需要自动跳转环节流程数据量为:" + listMQ.size());
                for (Map<String, Object> data : listMQ) {
                    try {
                        commercialService.jumpTaskForYjhz(data);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行面签自动跳转任务++++++");


            log.info("++++++开始执行商事秒批面签自动跳转任务++++++");
            List<Map<String, Object>> mpIdIdentifyList = commercialService.findMpIdIdentifyNeedAutoJump(ExeDataService.ID_IDENTIFY_TASKNAME_MP,
                    ExeDataService.ID_IDENTIFY_TASKNAME_MP);
            if (mpIdIdentifyList != null && mpIdIdentifyList.size() > 0) {
                log.info("发现需要自动跳转环节的商事登记流程数据量为:" + mpIdIdentifyList.size());
                for (Map<String, Object> data : mpIdIdentifyList) {
                    try {
                        commercialService.jumpTaskForYjhz(data);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行商事秒批面签自动跳转任务++++++");


            log.info("++++++开始执行办结自动跳转任务++++++");
            List<Map<String, Object>> listbj = commercialService.findBJNeedAutoJump();
            if (listbj != null && listbj.size() > 0) {
                log.info("发现需要自动跳转环节流程数据量为:" + listbj.size());
                for (Map<String, Object> data : listbj) {
                    try {
                        commercialService.jumpTaskForYjhz(data);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("++++++结束执行办结自动跳转任务++++++");

        } catch (Exception e) {
            log.error("执行五星流程自动跳转任务,面签自动跳转,抵押转本智能审批定时器报错：ComWXAutoJumpJob.execute()", e);
        }
    }
}
