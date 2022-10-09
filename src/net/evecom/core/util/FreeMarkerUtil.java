/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午10:02:04
 */
public class FreeMarkerUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FreeMarkerUtil.class);
    /**
     * 根据文件的名称获取到模板
     * 
     * @author Flex Hu
     * @param name
     * @return
     */
    public static Template getTemplate(String name) {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(FreeMarkerUtil.class, "/codegen");
        try {
            return cfg.getTemplate(name);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * 描述 获取模版填充后的字符串
     * @author Flex Hu
     * @created 2014年9月21日 上午8:15:48
     * @param tplString
     * @param root
     * @return
     */
    public static String getResultString(String tplString,Map<String,Object> root){
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new StringTemplateLoader(tplString));
        cfg.setDefaultEncoding("UTF-8");
        Template template;
        try {
            template = cfg.getTemplate("");
            StringWriter writer = new StringWriter();
            template.process(root, writer);
            return writer.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (TemplateException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * 描述 获取模版填充后的字符串
     * @author Flex Hu
     * @created 2014年9月21日 上午8:15:48
     * @param tplString
     * @param root
     * @param isLog
     * @return
     */
    public static String getResultString(String tplString,Map<String,Object> root,Boolean isLog){
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new StringTemplateLoader(tplString));
        cfg.setDefaultEncoding("UTF-8");
        Template template;
        try {
            template = cfg.getTemplate("");
            StringWriter writer = new StringWriter();
            template.process(root, writer);
            return writer.toString();
        } catch (IOException e) {
          if(isLog){
              log.error("数据："+root);
              log.error("模板："+tplString);
          }
          log.error(e.getMessage());
        } catch (TemplateException e) {
            if(isLog){
              log.error("数据："+root);
                log.error("模板："+tplString);
            }
             log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 
     * 描述 将字符串形式的模版写入到磁盘
     * @author Flex Hu
     * @created 2014年9月16日 上午11:21:37
     * @param tplString
     * @param filePath
     * @param root
     */
    public static void writeStringTplToDisk(String tplString,String filePath,Map<String, Object> root){
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new StringTemplateLoader(tplString));
        cfg.setDefaultEncoding("UTF-8");
        Template template;
        OutputStreamWriter out = null;
        try {
            template = cfg.getTemplate("");
            File codeFile = new File(filePath);
            if (codeFile.exists()) {
                codeFile.delete();
            }
            String folderPath = filePath
                    .substring(0, filePath.lastIndexOf("/"));
            File file = new File(folderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new OutputStreamWriter(new FileOutputStream(filePath, true),
                    "UTF-8");
            template.process(root, out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
    }
    
    
    /**
     * 写入模板结果到磁盘
     * 
     * @author Flex Hu
     * @param root
     * @param tplString
     * @param filePath
     */
    public static void writeFileToDisk(Map<String, Object> root,
            String tplName, String filePath) {
        Properties properties = FileUtil.readProperties("project.properties");
        //获取项目代码地址
        String projectPath = properties.getProperty("projectPath");
        String tplPath = projectPath+"src/codegen/"+tplName;
        String tplString = FileUtil.getContentOfFile(tplPath);
        FreeMarkerUtil.writeStringTplToDisk(tplString, filePath, root);
    }
    /**
     * 
     * 描述 获取模版文件的内容
     * @author Flex Hu
     * @created 2014年9月21日 上午11:47:41
     * @param ftlName
     * @return
     */
    public static String getFtlContent(String ftlName){
        Properties properties = FileUtil.readProperties("project.properties");
        //获取项目代码地址
        String projectPath = properties.getProperty("projectPath");
        String tplPath = projectPath+"src/codegen/"+ftlName;
        String tplString = FileUtil.getContentOfFile(tplPath);
        return tplString;
    }

    /**
     * 生成文件到磁盘
     * 
     * @author Flex Hu
     * @param ftlFileName
     *            :ftl文件名称
     * @param root
     *            :数据根
     * @param filePath
     *            :输出文件的路径
     */
    public static void writeFileToDisk(String ftlFileName,
            Map<String, Object> root, String filePath) {
        OutputStreamWriter out = null;
        // FileWriter out = null;
        try {
            File codeFile = new File(filePath);
            if (codeFile.exists()) {
                codeFile.delete();
            }
            String folderPath = filePath
                    .substring(0, filePath.lastIndexOf("/"));
            File file = new File(folderPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new OutputStreamWriter(new FileOutputStream(filePath, true),
                    "UTF-8");
            // out= new FileWriter(new File(filePath));
            Template temp = FreeMarkerUtil.getTemplate(ftlFileName);
            temp.process(root, out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
}
