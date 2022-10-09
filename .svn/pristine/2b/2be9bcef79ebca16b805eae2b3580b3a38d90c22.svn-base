/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.job;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.bdc.service.BdcYgspfService;

/**
 * 
 * 描述：定时推送数据至FTP
 * @author Rider Chen
 * @created 2020年8月16日 下午3:18:54
 */
public class BdcSetWwDataJob implements Job {
    /**
     * 
     */
    private static Log log = LogFactory.getLog(BdcSetWwDataJob.class);
    /**
     * bdcYgspfService
     */
    @Resource
    public BdcYgspfService bdcYgspfService;
    /**
     * 
     * 描述： 构造方法
     * @author Rider Chen
     * @created 2020年8月16日 下午3:20:09
     */
    public BdcSetWwDataJob(){

        super();
        if (bdcYgspfService == null) {
            bdcYgspfService = (BdcYgspfService) AppUtil.getBean("bdcYgspfService");
        }
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("开始调用不动产数据推送至FTP定时器");
        Map<String, Object> result = bdcYgspfService.pushFileToFtp(null);
        log.info("返回结果数据：" + JSON.toJSONString(result));
        log.info("结束调用不动产数据推送至FTP定时器");
    }

}
