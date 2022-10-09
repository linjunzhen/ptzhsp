/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.util.Map;

import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.service.SwbInterfaceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 投资项目数据对接任务
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class TzxmDataSaveSynchoJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(TzxmDataSaveSynchoJob.class);
    
    /**
     * applyInfoService
     */
    private SwbInterfaceService swbInterfaceService;

    /**
     * 方法_构造方法
     * 
     */
    public TzxmDataSaveSynchoJob() {
        super();
        if (swbInterfaceService == null) {
            swbInterfaceService = (SwbInterfaceService) AppUtil.getBean("swbInterfaceService");
        }
    }
    

    /**
     * 描述
 * @author Derek Zhang
     * @created 2015年12月29日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取内网配置的数据对接配置列表信息
        Map<String, Object> dataAbutment = this.swbInterfaceService.getByJdbc(
                "t_bsfw_dataabutment", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_TZXM_BLQKPLBCJK });
        if(dataAbutment!=null){
            swbInterfaceService.saveTZXMPLBLQKData(log,dataAbutment);
        }
    }

}
