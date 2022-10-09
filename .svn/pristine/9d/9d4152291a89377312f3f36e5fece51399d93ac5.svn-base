/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.bsfw.service.DataAbutmentService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述  数据对接定时任务
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午5:10:02
 */
public class DataSynchoJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(DataSynchoJob.class);
    
    /**
     * applyInfoService
     */
    private DataAbutmentService dataAbutmentService;
    /**
     * dataAbutLogService
     */
    private DataAbutLogService dataAbutLogService;

    /**
     * 方法_构造方法
     * 
     */
    public DataSynchoJob() {
        super();
        if (dataAbutmentService == null) {
            dataAbutmentService = (DataAbutmentService) AppUtil.getBean("dataAbutmentService");
        }
        if(dataAbutLogService==null){
            dataAbutLogService = (DataAbutLogService) AppUtil.getBean("dataAbutLogService");
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年10月12日 下午5:13:54
     * @param context
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取内网配置的数据对接配置列表信息
        List<Map<String,Object>> list = dataAbutmentService.
                findByInvokeType(DataAbutmentService.INVOKE_TYPE_JOB,
                        DataAbutmentService.NETWORK_TYPE_INNER);
        for(Map<String,Object> dataAbutment:list){
            //获取对接实现接口
            String interfaceCode = (String) dataAbutment.get("AABUT_INTERFACE");
            if(StringUtils.isNotEmpty(interfaceCode)){
                String[] beanMethods = interfaceCode.split("[.]");
                String beanId = beanMethods[0];
                String method = beanMethods[1];
                Object serviceBean = AppUtil.getBean(beanId);
                if (serviceBean != null) {
                    Method invokeMethod;
                    try {
                        invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                                new Class[] {Map.class});
                        invokeMethod.invoke(serviceBean,new Object[] {dataAbutment});
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

}
