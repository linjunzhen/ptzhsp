/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 描述
 * @author Faker Li
 * @created 2016年5月13日 上午9:29:18
 */
public class HtmlToWordUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(HtmlToWordUtil.class);
    /**
     * 
     * 描述 flt生成word
     * @author Faker Li
     * @created 2016年5月13日 上午9:51:43
     * @param dataMap
     * @param ftlPath
     * @return
     */
    public static String createWord(Map<String, Object> dataMap,String ftlPath){
        String temPath = "";
        try {
            Properties properties = FileUtil.readProperties("project.properties");
            String loadPath = properties.getProperty("AttachFilePath")
                    +ftlPath.substring(0,ftlPath.lastIndexOf("/") + 1);
            String filename = ftlPath.substring(ftlPath.lastIndexOf("/") + 1,
                    ftlPath.length());
            //ftl完整路径
            String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
            String uploadPath =  "documenttemplate/";
         // 定义上传文件的保存的相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
         // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            String uuId = UUIDGenerator.getUUID();
            temPath = "attachFiles/" + uploadPath+currentDate+"/"+ uuId+".doc";
            //生成文件完整路径
            String fullDocPath = uploadFullPath + uuId+".doc";
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(new File(loadPath));
            Template template = configuration.getTemplate(filename);
            Writer w = new OutputStreamWriter(new FileOutputStream(fullDocPath), "utf-8");  
            template.process(dataMap, w);  
            w.flush();
            w.close();  
        }catch(Exception e){
            log.error("",e);
            return null;
        }
        return temPath;
    }
}
