/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.job;

import java.util.List;
import java.util.Map;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bsfw.service.ZftzService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 政府投资自动跳转环节定时器
 * @author Flex Hu
 * @version 1.0
 * @created 2016年2月17日 下午5:17:10
 */
public class ZftzAutoJumpJob implements Job {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ZftzAutoJumpJob.class);
    /**
     * swbProvDataService
     */
    private ZftzService zftzService;
    
    /**
     * 方法_构造方法
     * 
     */
    public ZftzAutoJumpJob() {
        super();
        if (zftzService == null) {
            zftzService = (ZftzService) AppUtil.getBean("zftzService");
        }
    }

    /**
     * 描述
     * @author Flex Hu
     * @created 2016年2月17日 下午5:17:10
     * @param arg0
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Map<String,Object>> list = zftzService.findNeedAutoJump();
        if(list!=null&&list.size()>0){
            log.info("发现需要自动跳转环节的流程数据量为:"+list.size());
        }
        for(Map<String,Object> data:list){
            try {
                zftzService.jumpTaskForWzgs(data);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
