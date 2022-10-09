/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.service.SysSendMsgService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * 描述 删除保存90天以上的草稿
 * @author Rider Chen
 * @created 2017年1月17日 下午1:44:35
 */
public class DelTempSaveFlow implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(DelTempSaveFlow.class);
    /**
     * 引入 ExecutionService
     */
    private ExecutionService executionService;
    /**
     * 
     * 描述 构造方法
     * @author Rider Chen
     * @created 2017年1月17日 下午1:46:04
     */
    public DelTempSaveFlow(){
        super();
        if (executionService == null) {
            executionService = (ExecutionService) AppUtil.getBean("executionService");
        }
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list;
        list = executionService.getDraftApply(90);
        if(null != list && list.size() > 0){
            StringBuffer exeIds=new StringBuffer();
            for (Map<String, Object> map : list) {
                exeIds.append(",").append(map.get("EXE_ID"));
            }
            if(StringUtils.isNotEmpty(exeIds.toString())){
                exeIds = new StringBuffer(exeIds.substring(1, exeIds.length()));
            }
            executionService.remove("JBPM6_EXECUTION", "EXE_ID", exeIds.toString().split(","));
            log.info("删除保存时间大于90天的流程草稿数据，流水号为：【"+exeIds+"】");
        }
        //获取退回补件90天仍未补件的办件信息
//        List<Map<String, Object>> taskList = executionService.getDraftTask(90);
//        if(null != taskList && taskList.size() > 0){
//            StringBuffer exeIds=new StringBuffer();
//            for (Map<String, Object> map : taskList) {
//                exeIds.append(",").append(map.get("EXE_ID"));
//            }
//            if(StringUtils.isNotEmpty(exeIds.toString())){
//                exeIds = new StringBuffer(exeIds.substring(1, exeIds.length()));
//            }
//            executionService.endByExeId(exeIds.toString().split(","),"退回补件90天仍未进行补件，系统自动予以退件处理！");
//            log.info("退回补件90天仍未补件的办件予以退件，流水号为：【"+exeIds+"】");
//        }
    }

}
