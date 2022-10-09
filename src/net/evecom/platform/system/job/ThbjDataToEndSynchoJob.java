/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述
 * @author Faker Li
 * @created 2016年3月23日 下午4:21:34
 */
public class ThbjDataToEndSynchoJob implements Job{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ThbjDataToEndSynchoJob.class);
    /**
     * 
     */
    private ExecutionService executionService;
    /**
     * 
     */
    private BjxxService bjxxService;
    /**
     * 
     */
    private WorkdayService workdayService;
    /**
     * 方法_构造方法
     * 
     */
    public ThbjDataToEndSynchoJob() {
        super();
        if (executionService == null) {
            executionService = (ExecutionService) AppUtil.getBean("executionService");
        }
        if (bjxxService == null) {
            bjxxService = (BjxxService) AppUtil.getBean("bjxxService");
        }
        if (workdayService == null) {
            workdayService = (WorkdayService) AppUtil.getBean("workdayService");
        }
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月23日 下午4:21:53
     * @param arg0
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Map<String, Object>> exeList = executionService.findBjList();
        if(exeList!=null){
            for (int i = 0; i < exeList.size(); i++) {
                String exeId = (String)exeList.get(i).get("EXE_ID");
                Map<String, Object> bjxx = bjxxService
                        .getByJdbc("T_WSBS_BJXX", new String[]{"EXE_ID"}, new Object[]{exeId});
                if(bjxx!=null){
                    String beginTime = bjxx.get("CREATE_TIME").toString();
                    beginTime = DateTimeUtil.formatDateStr(beginTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
                    String endTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
                    String qqsj = workdayService.getDeadLineDateNatural(beginTime, 90);
                    int subDate = DateTimeUtil.getDaysBetween(qqsj,endTime);
                    if(subDate>0){
                        executionService.endByExeId(exeId.split(","),"");
                    }
                }
            }
        }
    }

}
