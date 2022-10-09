/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.service.CommercialService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-11-17 上午9:56:05
 */
public class CommercialAutoJumpJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(CommercialAutoJumpJob.class);
    /**
     * 引入service
     */
    @Resource
    private CommercialService commercialService;

    
    /**
     * 方法_构造方法
     * 
     */
    public CommercialAutoJumpJob() {
        super();
        if (commercialService == null) {
            commercialService = (CommercialService) AppUtil.getBean("commercialService");
        }
        log.info("++++++开始执行商事登记流程自动跳转任务++++++");
        List<Map<String,Object>> list = commercialService.findNeedAutoJump();
        if(list!=null&&list.size()>0){
            log.info("发现需要自动跳转环节的商事登记流程数据量为:"+list.size());
            for(Map<String,Object> data:list){
                try {
                    commercialService.jumpTaskForYjhz(data);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        List<Map<String,Object>> bslist = commercialService.findBsNeedAutoJump();
        if(bslist!=null&&bslist.size()>0){
            log.info("发现需要自动跳转环节的商事登记并审汇总数据量为:"+bslist.size());
            for(Map<String,Object> bsdata:bslist){
                try {
                    commercialService.jumpTaskForYjhz(bsdata);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.info("++++++结束执行商事登记流程自动跳转任务++++++");
        
        log.info("++++++开始执行面签自动跳转任务++++++");
        List<Map<String,Object>> listMQ = commercialService.findMQNeedAutoJump();
        if(listMQ!=null&&listMQ.size()>0){
            log.info("发现需要自动跳转环节的商事登记流程数据量为:"+listMQ.size());
            for(Map<String,Object> data:listMQ){
                try {
                    commercialService.jumpTaskForYjhz(data);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.info("++++++结束执行面签自动跳转任务++++++");
    }
    
    /**
     * 
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        /*log.info("++++++开始执行商事登记流程自动跳转任务++++++");
        List<Map<String,Object>> list = commercialService.findNeedAutoJump();
        if(list!=null&&list.size()>0){
            log.info("发现需要自动跳转环节的商事登记流程数据量为:"+list.size());
            for(Map<String,Object> data:list){
                try {
                    commercialService.jumpTaskForYjhz(data);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        List<Map<String,Object>> bslist = commercialService.findBsNeedAutoJump();
        if(bslist!=null&&bslist.size()>0){
            log.info("发现需要自动跳转环节的商事登记并审汇总数据量为:"+bslist.size());
            for(Map<String,Object> bsdata:bslist){
                try {
                    commercialService.jumpTaskForYjhz(bsdata);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.info("++++++结束执行商事登记流程自动跳转任务++++++");*/
    }
}
