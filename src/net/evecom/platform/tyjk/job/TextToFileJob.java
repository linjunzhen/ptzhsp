/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tyjk.job;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.tyjk.service.FlowWebService;

/**
 * 
 * 描述：附件接口文件流保存到磁盘
 * @author Rider Chen
 * @created 2018年11月16日 上午10:20:06
 */
public class TextToFileJob implements Job {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(TextToFileJob.class);

    /**
     * 引入flowWebService
     */
    private FlowWebService flowWebService;
    /**
     * 方法_构造方法
     * 
     */
    public TextToFileJob() {
        super();
        if (flowWebService == null) {
            flowWebService = (FlowWebService) AppUtil.getBean("flowWebService");
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub
        log.info("附件接口文件流保存到磁盘定时任务开始");
        // 获取文件存储路径
        Properties properties = FileUtil.readProperties("project.properties");
        String fileWebTypeTwo = properties.getProperty("fileWebTypeTwo");
        String attachFileFolderPath = properties.getProperty("AttachFilePath");
        List<Map<String, Object>> list  = flowWebService.getFileTrans(fileWebTypeTwo);
        log.info("附件接口文件流保存到磁盘定时任务执行数量：" + list.size());
        for (Map<String, Object> map : list) {
            String filePath = attachFileFolderPath + map.get("FILE_PATH");  
            // 建立相对路径目录
            File relativeFolder = new File(filePath.substring(0,filePath.lastIndexOf("/")+1));
            if (!relativeFolder.exists()) {
                relativeFolder.mkdirs();
            }
            String fileTxt = String.valueOf(map.get("FILE_TXT"));
            try {
                log.info("附件路径："+filePath);
                FileUtil.textToFile(filePath, fileTxt);
                map.put("PARSE_STATUS", 1);
            } catch (Exception e) {
                // TODO: handle exception
                map.put("PARSE_STATUS", -1);
            }
            flowWebService.saveOrUpdate(map, "T_WSBS_FILETRANS", map.get("TRANSID").toString());
        }
        log.info("附件接口文件流保存到磁盘定时任务结束");
    }

}
