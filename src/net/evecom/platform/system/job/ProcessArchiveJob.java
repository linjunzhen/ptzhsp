/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.ProcessArchiveService;
import net.evecom.platform.zhsp.service.ServiceHandleService;

/**
 * 描述 数据归档定时任务
 * 
 * @author Reuben Bao
 * @created 2019年3月28日 上午10:19:46
 */
public class ProcessArchiveJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProcessArchiveJob.class);
    /**
     * 引入 processArchiveService
     */
    private ProcessArchiveService processArchiveService;
    /**
     * 引入 serviceHandleService
     */
    private ServiceHandleService serviceHandleService;

    /**
     * 方法_构造方法
     * 
     */
    public ProcessArchiveJob() {
        super();
        if (processArchiveService == null) {
            processArchiveService = (ProcessArchiveService) AppUtil.getBean("processArchiveService");
        }
        if (serviceHandleService == null) {
            serviceHandleService = (ServiceHandleService) AppUtil.getBean("serviceHandleService");
        }
    }

    /**
     * 描述 查询字典表配置的天数前的流程数据进行归档操作
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午10:23:43
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) {
        log.info("-------执行流程归档定时器开始--------");
        try {
            int needCount = serviceHandleService.getExecutionCountByDicCode();
            int circleNum = needCount/500;
            if(needCount%500>0){
                circleNum++;
            }
            for(int circle=0;circle<circleNum;circle++){
                // 查询字典表配置的天数前的流程数据，每次执行0-900条数据
                List<Map<String, Object>> exectionList = serviceHandleService.getExecutionListByDicCode();
                StringBuffer exeIds = new StringBuffer();
                if (exectionList != null && exectionList.size() > 0) {
                    for (int i = 0; i < exectionList.size(); i++) {
                        Map<String, Object> exectionMap = exectionList.get(i);
                        exeIds.append("'").append(StringUtil.getValue(exectionMap, "EXE_ID")).append("',");
                        // 每50条执行一次数据库操作
                        if (i % 50 == 0 || (exectionList.size() - i < 50 && i == exectionList.size() - 1)) {
                            String exeIdsStr = exeIds.toString().substring(0, exeIds.toString().length() - 1);
                            processArchiveService.processArchiveByExeId(exeIdsStr);
                            exeIds.setLength(0);
                        }
                    }
                    // 删除已归档流程
                    processArchiveService.deleteFiledFlows();
                }
            }
        } catch (Exception e) {
            log.error("执行流程归档定时器报错：processArchiveService.processArchive()", e);
        }
        log.info("-------执行流程归档定时器结束--------");
    }
}
