/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bdc.service.BdcQslyshbsyService;
import net.evecom.platform.bsfw.service.BdcQlcApplyService;

/**
 * 描述  不动产智能审批-待受理环节自动跳转
 * @author Allin Lin
 * @created 2020年9月9日 下午2:34:19
 */
public class BdcDslAutoJumpJob implements Job{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcDslAutoJumpJob.class);
    
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
    public BdcDslAutoJumpJob() {
        if (bdcQlcApplyService == null) {
            bdcQlcApplyService = (BdcQlcApplyService) AppUtil.getBean("bdcQlcApplyService");
        }
        if (bdcQslyshbsyService == null) {
            bdcQslyshbsyService = (BdcQslyshbsyService) AppUtil.getBean("bdcQslyshbsyService");
        }
    }
    
    
    @Override
    public void execute(JobExecutionContext context){
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
        log.info("++++++开始执行不动产平潭通智能审批流程网上预审环节自动跳转任务++++++");
        List<Map<String, Object>> pttList = bdcQlcApplyService.findPttNeedAutoJump();
        if (pttList != null && pttList.size() > 0) {
            log.info("发现平潭通智能审批网上预审需要自动跳转环节的流程数据量为:" + pttList.size());
        }
        for (Map<String, Object> data : pttList) {
            try {
                bdcQlcApplyService.jumpTaskToBdcDsl(data);
            } catch (Exception e) {
                log.error("平潭通智能审批网上预审自动跳转任务出错：" + e.getMessage(), e);
            }
        }
    }
}
