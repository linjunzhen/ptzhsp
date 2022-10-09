/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.web.config;

import java.util.Properties;

import net.evecom.core.util.FileUtil;

/**
 * 
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-8 上午8:35:02
 */
public class WebConstants {
    /**
     * 服务地址
     */
    public static String statServerDomain;

    /**
     * @return the statServerDomain
     */
    public static String getStatServerDomain() {
        Properties properties = FileUtil.readProperties("project.properties");
        statServerDomain = properties.getProperty("vistlogService");
        return statServerDomain;
    }

}
