/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 描述 禁用
 * @author Rider Chen
 * @created 2017年5月16日 下午3:26:01
 */
public class DisableUserJob implements Job {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(DisableUserJob.class);

    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 
     * 描述 构造方法
     * @author Rider Chen
     * @created 2017年5月16日 下午3:32:30
     */
    public DisableUserJob(){
        super();
        if (sysUserService == null) {
            sysUserService = (SysUserService) AppUtil.getBean("sysUserService");
        }
    }
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = sysUserService.getDraftUser(5);
        if(null != list && list.size() > 0){
            list = encryptionService.listDecrypt(list, "T_MSJW_SYSTEM_SYSUSER");
            for (Map<String, Object> map : list) {
                String userId = map.get("USER_ID") == null?"":map.get("USER_ID").toString();
                map.put("STATUS", -1);
                map.put("IS_DISABLE", 0);
                /*map.put("MOBILE", "");
                map.put("USERCARD", "");*/
                sysUserService.saveOrUpdate(map,"T_MSJW_SYSTEM_SYSUSER",userId);
            }
            log.info("禁用激活5天并需要自动禁用的用户数据");
        }

    }

}
