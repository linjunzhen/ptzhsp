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
import net.evecom.platform.bdc.service.BdcQslyshbsyService;
import net.evecom.platform.bsfw.service.BdcQlcApplyService;
import net.evecom.platform.bsfw.service.CommercialService;

/**
 * 
 * 描述  智能审批、审查与决定、五星流程、面签 自动跳转
 * @author Danto Huang
 * @created 2021年11月16日 下午4:38:39
 */
public class BsfwOtherAutoJumpJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BsfwOtherAutoJumpJob.class);
    /**
     * 引入service
     */
    @Resource
    private CommercialService commercialService;
    
    /**
     * BdcQlcApplyService
     */
    @Resource
    private BdcQlcApplyService bdcQlcApplyService;
    
    /**
     * BdcQslyshbsyService
     */
    @Resource
    private BdcQslyshbsyService bdcQslyshbsyService;

    /**
     * 方法_构造方法
     * 
     */
    public BsfwOtherAutoJumpJob() {
        if (commercialService == null) {
            commercialService = (CommercialService) AppUtil.getBean("commercialService");
        }
        if (bdcQlcApplyService == null) {
            bdcQlcApplyService = (BdcQlcApplyService) AppUtil.getBean("bdcQlcApplyService");
        }
        if (bdcQslyshbsyService == null) {
            bdcQslyshbsyService = (BdcQslyshbsyService) AppUtil.getBean("bdcQslyshbsyService");
        }
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2021年11月16日 下午4:38:59
     * @param context
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) {
        try {
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
            
            log.info("++++++开始执行不动产权属来源事宜业务（并审汇总）环节自动跳转任务++++++");
            List<Map<String, Object>> taskList = bdcQslyshbsyService.findNeedAutoJump();
            if (taskList != null && taskList.size() > 0) {
                log.info("发现不动产权属来源事宜业务（并审汇总）环节的流程数据量为:" + taskList.size());
            }
            for (Map<String, Object> task : taskList) {
                try {
                    bdcQslyshbsyService.jumpTaskToAuto(task);
                } catch (Exception e) {
                    log.error("不动产权属来源事宜业务（并审汇总）自动跳转任务出错：" + e.getMessage(), e);
                }
            }
            log.info("++++++开始执行不动产智能审批流程(预抵、首登全程网办个性化流程)待受理环节自动跳转任务++++++");
            List<Map<String, Object>> list = bdcQlcApplyService.findNeedAutoJump();
            if (list != null && list.size() > 0) {
                log.info("发现待受理需要自动跳转环节的流程数据量为:" + list.size());
            }
            for (Map<String, Object> data : list) {
                try {
                    bdcQlcApplyService.jumpTaskToBdcSl(data);
                } catch (Exception e) {
                    log.error("不动产智能审批(预抵、首登全程网办个性化流程)待受理自动跳转任务出错：" + e.getMessage(), e);
                }
            }
            log.info("++++++开始执行不动产平潭通智能审批流程((预抵、首登全程网办个性化流程)网上预审环节自动跳转任务++++++");
            List<Map<String, Object>> pttList = bdcQlcApplyService.findPttNeedAutoJump();
            if (pttList != null && pttList.size() > 0) {
                log.info("发现平潭通智能审批((预抵、首登全程网办个性化流程))网上预审需要自动跳转环节的流程数据量为:" + pttList.size());
            }
            for (Map<String, Object> data : pttList) {
                try {
                    bdcQlcApplyService.jumpTaskToBdcDsl(data);
                } catch (Exception e) {
                    log.error("平潭通智能审批((预抵、首登全程网办个性化流程))网上预审自动跳转任务出错：" + e.getMessage(), e);
                }
            }
            
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
            log.error("执行智能审批自动跳转、审查与决定自动跳转,五星流程自动跳转任务,面签自动跳转,抵押转本智能审批定时器报错：ComWXAutoJumpJob.execute()", e);
        }
    }
}
