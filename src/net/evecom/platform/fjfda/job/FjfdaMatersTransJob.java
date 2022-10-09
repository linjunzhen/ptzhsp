/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.job;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.fjfda.service.FoodManagementService;
import net.evecom.platform.fjfda.util.TokenUtil;

/**
 * 描述 食药监材料附件上传定时任务
 * 
 * @author Keravon Feng
 * @created 2019年2月22日 下午4:06:36
 */
public class FjfdaMatersTransJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(FjfdaMatersTransJob.class);

    /**
     * foodManagementService
     */
    @Resource
    private FoodManagementService foodManagementService;

    /**
     * 附件存放的根路径
     */
    private String path;
    /**
     * 接口IP地址
     */
    private String url;
    /**
     * 存储上传完成的业务表ID
     */
    private Set<String> busRecordIdSets = null;

    /**
     * 描述 构造方法
     * 
     * @author Keravon Feng
     * @created 2019年2月22日 下午4:12:21
     */
    public FjfdaMatersTransJob() {
        super();
        if (foodManagementService == null) {
            foodManagementService = (FoodManagementService) AppUtil.getBean("foodManagementService");
        }
        Properties properties = FileUtil.readProperties("project.properties");
        path = properties.getProperty("AttachFilePath");
        url = properties.getProperty("fjfdaURL");
        busRecordIdSets = new HashSet<String>();
    }

    /**
     * 描述 执行任务
     * 
     * @author Keravon Feng
     * @created 2019年2月22日 下午5:03:45
     * @param arg0
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // 获取待上传的食药附件
        List<Map<String, Object>> list = foodManagementService.listTransMaters(50);
        log.info("=====开始批量上传食药业务材料附件=========");
        if (list.size() > 0) {
            for (Map<String, Object> map : list) {
                if (map.get("FILEPATH") != null) {
                    String filepath = path + (String) map.get("FILEPATH");
                    Map<String, Object> res = TokenUtil.uploadFile(filepath);
                    String ywId = String.valueOf(map.get("YW_ID"));
                    if (res != null && "true".equals(String.valueOf(res.get("success")))) {
                        busRecordIdSets.add(String.valueOf(map.get("BUS_RECORDID")));
                        map.put("TRANS_STATE", "1");
                        foodManagementService.saveOrUpdate(map, "T_FJFDA_TRANS_MATERS", ywId);
                    } else {
                        map.put("TRANS_STATE", "-1");
                        foodManagementService.saveOrUpdate(map, "T_FJFDA_TRANS_MATERS", ywId);
                    }
                }
            }
            // 更新传输任务主表的附件上传状态
            for (String busrecordId : busRecordIdSets) {
                Map<String, Object> trans = foodManagementService.getByJdbc("T_FJFDA_TRANS",
                        new String[] { "BUS_RECORDID" }, new Object[] { busrecordId });
                if (trans != null) {
                    trans.put("IS_MATERS_UPLOAD", "1");
                    foodManagementService.saveOrUpdate(trans, "T_FJFDA_TRANS", String.valueOf(trans.get("YW_ID")));
                }
            }
        }
        log.info("=====结束批量上传食药业务材料附件=========");
    }
}
