/*
 *  Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 *  EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.web.interceptors.AuthInterceptor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 描述：
 * 
 * @author Robin Zhang
 * @created 2015-3-3 上午11:58:39
 */
public class WebUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WebUtil.class);
    /**
     * 获取异常的祥细信息，用于记录到日志中。 RuntimeException只会在控制台打印，不会记录到日志文件中，所以需要此方法
     * 
     * @param e
     * @return
     */
    public static String getExceptionCauseStr(final Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        log.info(new java.io.PrintWriter(sw, true));
        return sw.toString();
    }

    /*
     * 通配符：? 匹配任意一个字符 通配符：* 匹配任意多个字符，但不能跨越目录 通配符：** 可以匹配任意多个字符，可以跨越目录
     * 
     * @param patternUrl
     * 
     * @param url 请求的URL
     * 
     * @return
     */
    public static boolean pathMatchesUrl(String patternUrl, String requestUrl) {
        if ("/**".equals(patternUrl) || "**".equals(patternUrl)) {
            return true;
        }
        PathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match(patternUrl, requestUrl);
    }

    /**
     * by:zzb 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }


    /**
     * 转换文件大小
     * 
     * @param fileLength
     * @return
     */
    public static String getFormatFileSize(long fileLength) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLength < 1024) {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576) {
            fileSizeString = df.format((double) fileLength / 1024) + "K";
        } else if (fileLength < 1073741824) {
            fileSizeString = df.format((double) fileLength / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLength / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * 截取字符串
     * 
     * @param str
     * @param subLenght
     * @param ellipsis
     *            true/false
     * @return 返回合适在页面显示的长度。
     */
    public static String subString4JSPveiw(String str, int subLength, boolean ellipsis) {
        return subString4JSPveiw(str, subLength, ellipsis, null, 0);
    }

    public static String subString4JSPveiw(String str, int subLength, Boolean ellipsis, String objdate, int timeout) {
        int bytesLength = subLength;
        if (str == null) {
            return "";
        }
        if (str.indexOf("&#8226;") != -1) {
            str = str.replace("&#8226;", "•");
        }

        // 2015年7月2日 10:52:46增加 清除HTML标签
        Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll("");

        double d = 0; // 总长度。 length
        int n = 0; // char length
        double othersize = 0;
        if (ellipsis) {
            othersize = 1.5;
        }
        String newImg = showNew(objdate, timeout);
        if (newImg.length() > 0) {
            othersize += 2;
        }

        for (int i = 0; i < str.length(); i++) {
            d = ((int) str.charAt(i)) > 256 ? d + 1 : d + 0.5;
            if (d + (othersize >= 2 ? 2 : 0) > bytesLength) {
                n = (int) (n == 0 ? i - (othersize >= 2 ? 2 : othersize) : n);
            }
        }

        if (n > 0) {
            str = str.substring(0, n);
            if (ellipsis) {
                str += "...";
            }
        }
        return str += newImg;
    }

    /**
     * 
     * @param input
     *            发布时间
     * @param shownewtime
     *            显示天数
     * @return
     */
    public static String showNew(String input, int shownewtime) {
        if (input == null || "".equals(input) || shownewtime <= 0) {
            return "";
        }
        Date inputDate;
        try {
            if (input.length() > 10) {
                inputDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input);
            } else {
                inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(input);
            }
        } catch (ParseException e) {
            // log.error(e.getMessage());
            return "";
        }
        boolean overdue = (new Date().getTime() - inputDate.getTime()) / 86400000 < shownewtime;
        // 是否显示new图标。
        String img = "";
        if (shownewtime == -1 || overdue) {
            img = "<img class=\"newimg\" src=\"webpage/website/common/images/news.gif\" ></img>";
        }
        return img;
    }

    
    /**
     * 描述：验证请求是否来源于站内。防止跨域提交表单
     * 
     * @author Robin Zhang
     * @created 2015-6-15 上午11:10:09
     * @param request
     * @return true 站内提交，验证通过。false 站外提交，验证失败
     */
    public static boolean validateReferer(HttpServletRequest request) {
        String referer = "";
        boolean referer_sign = true; // true 站内提交，验证通过 //false 站外提交，验证失败
        Enumeration headerValues = request.getHeaders("Referer");
        while (headerValues.hasMoreElements()) {
            referer = (String) headerValues.nextElement();
        }
        // 判断是否存在请求页面
        if (referer == null || referer.length() < 1)
            referer_sign = false;
        else {
            // 判断请求页面和getRequestURI是否相同
            String servername_str = request.getServerName();
            if (StringUtils.isNotEmpty(servername_str)) {
                int index = 0;
                if (StringUtils.indexOf(referer, "https://") == 0) {
                    index = 8;
                } else if (StringUtils.indexOf(referer, "http://") == 0) {
                    index = 7;
                }
                if (referer.length() - index < servername_str.length()) // 长度不够
                    referer_sign = false;
                else { // 比较字符串（主机名称）是否相同
                    String referer_str = referer.substring(index, index + servername_str.length());
                    if (!servername_str.equalsIgnoreCase(referer_str))
                        referer_sign = false;
                }
            } else {
                referer_sign = false;
            }
        }
        return referer_sign;
    }
}