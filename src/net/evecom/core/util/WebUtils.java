/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
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
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;



import net.evecom.platform.hflow.service.impl.ExecutionServiceImpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;

/**
 * 描述 
 * @author Sundy Sun
 * @version v1.0
 */
public class WebUtils {
    /**
     * logger
     */
    private static Log log = LogFactory.getLog(WebUtils.class);


    /**
     * 获取异常的祥细信息，用于记录到日志中。 RuntimeException只会在控制台打印，不会记录到日志文件中，所以需要此方法
     * 
     * @param e
     * @return
     */
    public static String getExceptionCauseStr(final Exception e) {
        java.io.StringWriter sw = new java.io.StringWriter();
        log.error(new java.io.PrintWriter(sw, true));
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
     * 
     * public static boolean pathMatchesUrl(String patternUrl, String
     * requestUrl) { if ("/**".equals(patternUrl) || "**".equals(patternUrl)) {
     * return true; } PathMatcher pathMatcher = new AntPathMatcher(); return
     * pathMatcher.match(patternUrl, requestUrl); }
     */

    /**
     * 描述：获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @author Robin Zhang
     * @created 2015-9-17 上午11:22:39
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
     * 从指定集合中递归获取指定节点下的所有子节点 。用于生成json树。生成的树状结构在rootNode中。
     * 
     * @param rootNode
     *            指定节点
     * @param allList
     *            指定集合
     */

    /*
     * public static void recurrenTree(JsonTree rootNode, List<JsonTree>
     * allList){ allList.remove(rootNode);//删除掉本身。 List<JsonTree> tempList = new
     * ArrayList<JsonTree>();//除去已被用过栏目的集合 tempList.addAll(allList);
     * List<JsonTree> children = new ArrayList<JsonTree>();//本级子栏目 for (JsonTree
     * node : allList) { if
     * (rootNode.getEntityId().equals(node.getEntityParentId())){
     * children.add(node); //从allChannelList中删除被加入的栏目。避免不必要的循环
     * tempList.remove(node); } } rootNode.setChildren(children); if
     * (children.size() == 0) { return; } else { for (JsonTree temp : children)
     * { recurrenTree(temp, tempList); } } }
     */

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
     * 压力测试
     */
    public static void yltest() {
        // final File file1 = new File("E:\\New.html");
        // final int b = 0;
        for (int i = 0; i < 150; i++) {
            // 2.创建实现Runnable接口的对象
            final int a = i;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    // for (int j = 0; j < 10; j++) {
                    // log.info("Thread=" + a + ";j=" + j);
                    try {// ct_2927_35938 ct_6218_36592
                        final URL url = new URL("http://127.0.0.1:88/fzqsngWeb/getR/ct_6218_36592");
                        // final URL url = new
                        // URL("http://192.168.5.66:8081/fzqsng/siteController.do?queryUser");
                        URLConnection con = url.openConnection();
                        con.setDoOutput(true);
                        con.setRequestProperty("User-Agent", a + "");
                        // InputStream in = (InputStream) con.getContent();
                        con.getContent().toString();
                        // File file = new File("E:\\test\\"+a+j+".html");
                        // FileUtils.copyInputStreamToFile(in, file);
                        // in.close();
                    } catch (Exception e) {
                         log.error(e.getMessage());
                    }
                    // }
                }
            }); // 3.创建一个Thread类的对象
            t.start(); // 4.启动线程
        }

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
            img = "<img class=\"newimg\" src=\"" + JFinal.me().getContextPath() + "/common/images/news.gif\" ></img>";
        }
        return img;
    }

    /**
     * 过滤请求参数
     * 
     * @param req
     * @throws IOException
     * @throws ServletException
     */
    public static boolean checkBadChar(HttpServletRequest req) {
        // 获得所有请求参数名
        Enumeration params = req.getParameterNames();
        String names = "";
        StringBuffer values=new StringBuffer();
        String query = req.getQueryString();
        while (params.hasMoreElements()) {
            // 得到参数名
            String name = params.nextElement().toString();
            names = names + name;
            // 得到参数对应值
            String[] value = req.getParameterValues(name);
            for (int i = 0; i < value.length; i++) {
                values.append(value[i]);
            }
        }

        if (query != null) {
            if (sqlValidate(req, query)) {
                return true;
            }
        }
        if (names != "") {
            if (sqlValidate(req, names)) {
                return true;
            }
        }
        if (values.toString() != "") {
            if (sqlValidate(req, values.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 有非法字符就返回true
     * 
     * @param servletRequest
     * @param str
     * @return
     */
    public static boolean sqlValidate(HttpServletRequest servletRequest, String str) {
        if (StrKit.isBlank(str)) {
            return false;
        }
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String badStr = properties.getProperty("paramFilterChar");
        str = str.toLowerCase();// 统一转为小写

        String[] badStrs = badStr.split(",");
        String lockIp = getIpAddr(servletRequest);
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) != -1) {
                // 记入黑名单。禁访问。
                StringBuffer cause = new StringBuffer("请求的url:").append(servletRequest.getRequestURL().toString())
                        .append("参数：").append("{"+str+"}").append("中含有非法字符：").append("{"+badStrs[i]+"}");
                //J2CacheKit.put("IPLockCache", lockIp, cause);
                log.error("从ip地址：" + lockIp + cause);
                return true;
            }
        }
        return false;
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

    public static void main(String[] args) {
        log.info("safb".hashCode());
        /*
         * String unsafe =
         * "filename=1\" onclick=\"prompt(5627), append: \" onclick=\"prompt(5627), 133"
         * ; boolean safe = Jsoup.isValid(unsafe, Whitelist.none());
         * log.info(safe);
         */
        // creatHtml("http://www.fjhi.gov.cn", "d:/index222.html");
        // yltest();
        // cachetest();
        // String a =
        // "2013年10月25日上午，市局召,.,开竞,,,,ss争上岗科级领dw导任前集c,,,,..体谈话会。市局党委委员、政治部主任叶宝华，
                //人事训练处处长徐恒祥，市局监察室副主任陈成东及有关单位领导出席了会议。";
        // String a =
        // "<h3><span style=\"float:left;\">专题专栏</span>份全</h3>
        // <a
        // href=\"<%=path%>/site/fz/xx/ztindex.jsp\" target=\"_top\" style=\"color:#346AB2;\">更多&gt;&gt;&nbsp;</a>";
        // String b = "争上岗科级上午";
        // log.info((int)b.charAt(0));
        // log.info(Jsoup.clean(a, Whitelist.none()));
        // log.info(subString4JSPveiw(b,5,true));
        /*
         * String s1 = s.replaceFirst(",", ""); for (int i = 0; i < args.length;
         * i++) { String string = args[i]; log.info(string); }
         */

        // yltest();
        // cachetest();

        //
        // try {
        // log.info(PasswordEncoder.encodePassword("123456", "cce"));
        // } catch (Exception e) {
        // log.error(e.getMessage());
        // }
        // try {
        // log.info(PasswordEncoder.isPasswordValid("ceb4f32325eda6142bd65215f4c0f371",
        // "admin", "admin1"));
        // } catch (Exception e) {
        // log.error(e.getMessage());
        // }
        // log.info(pathMatchesUrl("/sys/sysUser/*save*","/sys/sysUser/saveOrupdateBefore"));;

        // try {
        // log.info(new
        // String("360鎴浘20120411084435043.jpg".getBytes(), "UTF-8"));
        // } catch (UnsupportedEncodingException e) {
        // log.error(e.getMessage());
        // }
        //
        // for (int i = 0; i < 4; i++) {
        // log.info(a.get(i)+","+b.get(i)+","+c.get(i));
        // }
    }

}