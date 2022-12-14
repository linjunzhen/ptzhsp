/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月14日 上午8:49:27
 */
public class BrowserUtils {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BrowserUtils.class);
    /**
     * 方法isIE
     * 
     * @param request
     *            传入参数
     * @return 返回值boolean
     */
    public static boolean isIE(HttpServletRequest request) {
        return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request
                .getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true
                : false;
    }

    /**
     * 获取IE版本
     * 
     * @param request
     * @return
     */
    public static Double getIEversion(HttpServletRequest request) {
        Double version = 0.0;
        if (getBrowserType(request, IE11)) {
            version = 11.0;
        } else if (getBrowserType(request, IE10)) {
            version = 10.0;
        } else if (getBrowserType(request, IE9)) {
            version = 9.0;
        } else if (getBrowserType(request, IE8)) {
            version = 8.0;
        } else if (getBrowserType(request, IE7)) {
            version = 7.0;
        } else if (getBrowserType(request, IE6)) {
            version = 6.0;
        }
        return version;
    }

    /**
     * 获取浏览器类型
     * 
     * @param request
     * @return
     */
    public static BrowserType getBrowserType(HttpServletRequest request) {
        BrowserType browserType = null;
        if (getBrowserType(request, IE11)) {
            browserType = BrowserType.IE11;
        }
        if (getBrowserType(request, IE10)) {
            browserType = BrowserType.IE10;
        }
        if (getBrowserType(request, IE9)) {
            browserType = BrowserType.IE9;
        }
        if (getBrowserType(request, IE8)) {
            browserType = BrowserType.IE8;
        }
        if (getBrowserType(request, IE7)) {
            browserType = BrowserType.IE7;
        }
        if (getBrowserType(request, IE6)) {
            browserType = BrowserType.IE6;
        }
        if (getBrowserType(request, FIREFOX)) {
            browserType = BrowserType.Firefox;
        }
        if (getBrowserType(request, SAFARI)) {
            browserType = BrowserType.Safari;
        }
        if (getBrowserType(request, CHROME)) {
            browserType = BrowserType.Chrome;
        }
        if (getBrowserType(request, OPERA)) {
            browserType = BrowserType.Opera;
        }
        if (getBrowserType(request, "Camino")) {
            browserType = BrowserType.Camino;
        }
        return browserType;
    }

    /**
     * 方法getBrowserType
     * 
     * @param request
     *            传入参数
     * @param brosertype
     *            传入参数
     * @return 返回值boolean
     */
    private static boolean getBrowserType(HttpServletRequest request,
            String brosertype) {
        return request.getHeader("USER-AGENT").toLowerCase()
                .indexOf(brosertype) > 0 ? true : false;
    }

    /**
     * 属性"rv:11.0"
     */
    private final static String IE11 = "rv:11.0";
    /**
     * 属性10.0"
     */
    private final static String IE10 = "MSIE 10.0";
    /**
     * 属性9.0"
     */
    private final static String IE9 = "MSIE 9.0";
    /**
     * 属性8.0"
     */
    private final static String IE8 = "MSIE 8.0";
    /**
     * 属性7.0"
     */
    private final static String IE7 = "MSIE 7.0";
    /**
     * 属性6.0"
     */
    private final static String IE6 = "MSIE 6.0";
    /**
     * 属性"Maxthon"
     */
    private final static String MAXTHON = "Maxthon";
    /**
     * 属性"QQBrowser"
     */
    private final static String QQ = "QQBrowser";
    /**
     * 属性"GreenBrowser"
     */
    private final static String GREEN = "GreenBrowser";
    /**
     * 属性"360SE"
     */
    private final static String SE360 = "360SE";
    /**
     * 属性"Firefox"
     */
    private final static String FIREFOX = "Firefox";
    /**
     * 属性"Opera"
     */
    private final static String OPERA = "Opera";
    /**
     * 属性"Chrome"
     */
    private final static String CHROME = "Chrome";
    /**
     * 属性"Safari"
     */
    private final static String SAFARI = "Safari";
    /**
     * 属性"其它"
     */
    private final static String OTHER = "其它";

    /**
     * 方法checkBrowse
     * 
     * @param request
     *            传入参数
     * @return 返回值String
     */
    public static String checkBrowse(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT");
        if (regex(OPERA, userAgent))
            return OPERA;
        if (regex(CHROME, userAgent))
            return CHROME;
        if (regex(FIREFOX, userAgent))
            return FIREFOX;
        if (regex(SAFARI, userAgent))
            return SAFARI;
        if (regex(SE360, userAgent))
            return SE360;
        if (regex(GREEN, userAgent))
            return GREEN;
        if (regex(QQ, userAgent))
            return QQ;
        if (regex(MAXTHON, userAgent))
            return MAXTHON;
        if (regex(IE11, userAgent))
            return IE11;
        if (regex(IE10, userAgent))
            return IE10;
        if (regex(IE9, userAgent))
            return IE9;
        if (regex(IE8, userAgent))
            return IE8;
        if (regex(IE7, userAgent))
            return IE7;
        if (regex(IE6, userAgent))
            return IE6;
        return OTHER;
    }

    /**
     * 方法regex
     * 
     * @param regex
     *            传入参数
     * @param str
     *            传入参数
     * @return 返回值boolean
     */
    public static boolean regex(String regex, String str) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        return m.find();
    }
    
    /**
     * 获取登录用户IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "本地";
        }
        return ip;
    }
    /**
     * 
     * 描述 获取本机的IP地址
     * @author Flex Hu
     * @created 2015年7月28日 下午5:23:22
     * @return
     */
    public static String getLocalIpAddress(){
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            String ip=addr.getHostAddress();//获得本机IP
            return ip;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 
     * 描述 获取本机名称
     * @author Flex Hu
     * @created 2015年7月28日 下午5:24:44
     * @return
     */
    public static String getHostName(){
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            String hostName=addr.getHostName();
            return hostName;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return null;
    }
}
