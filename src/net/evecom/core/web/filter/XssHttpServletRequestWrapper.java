/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015年7月23日 下午5:19:47
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年7月24日 上午9:41:56
     * @param servletRequest
     */
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年7月24日 上午9:41:59
     * @param parameter
     * @return
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年7月24日 上午9:42:06
     * @param parameter
     * @return
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年7月24日 上午9:42:09
     * @param name
     * @return
     * @see javax.servlet.http.HttpServletRequestWrapper#getHeader(java.lang.String)
     */
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    /***
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年7月24日 上午9:42:13
     * @param value
     * @return
     */
    private String cleanXSS(String value) {
        // You'll need to remove the spaces from the html entities below
        /**
         * 影响业务，注释掉
         * @author Rider Chen
         * @created 2019年3月5日 上午11:12:16
         */
        //value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        //value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        //value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
}
