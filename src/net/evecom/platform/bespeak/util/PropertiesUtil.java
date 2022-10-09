/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 描述 读写配置文件工具类
 * 
 * @author Panda Chen
 * @created 2015-3-26 下午03:27:46
 */
public class PropertiesUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(PropertiesUtil.class);
    /**
     * 配置文件名称
     */
    private String properiesName = "";

    /**
     * 
     * 构造函数：读写配置文件工具类
     * 
     */
    public PropertiesUtil() {

    }

    /**
     * 
     * 构造函数：读写配置文件工具类
     * 
     * @param fileName
     */
    public PropertiesUtil(String fileName) {
        this.properiesName = fileName;
    }

    /**
     * 
     * 描述 读配置文件
     * 
     * @author Panda Chen
     * @created 2015-3-26 下午03:28:00
     * @param key
     * @return
     */
    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return value;
    }

    /**
     * 
     * 描述 获取配置文件内容
     * 
     * @author Panda Chen
     * @created 2015-3-26 下午03:28:10
     * @return
     */
    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(properiesName);
            p.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
        return p;
    }

    /**
     * 
     * 描述 写配置文件
     * 
     * @author Panda Chen
     * @created 2015-3-26 下午03:28:17
     * @param key
     * @param value
     */
    public void writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(properiesName);
            p.load(is);
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());

            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } finally {
            if (null != is)
                try {
                    is.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    log.error(e1.getMessage());
                }
            if (null != os)
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
        }

    }
}
