/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.filter;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
public class SQLParamsFilter implements Filter{
    /**
     * logger
     */
    private static Log log = LogFactory.getLog(SQLParamsFilter.class);
//    /**
//     * 引入字典处理业务服务
//     */
//    @Resource
//    private static DictionaryService dictionaryService;
    /**
     * init
     */
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * 方法doFilter
     * 
     * @param request
     *            传入参数
     * @param response
     *            传入参数
     * @param chain
     *            传入参数
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("text/html;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        String url = req.getRequestURL().toString();
        request.setAttribute("ctx", req.getContextPath());
        
        Boolean legalHost = checkHostList(req);
        if (legalHost) {
            
        }else {
            res.sendRedirect(req.getContextPath() + "/403.html");
            return;
        }
        
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String sqlurls = properties.getProperty("NoFilterUrls");
        String[] filterPaths=sqlurls.split(",");
        boolean isfilter=true;
        for (int i = 0; i < filterPaths.length; i++) {
            if (StringUtils.isNotEmpty(filterPaths[i]) && url.indexOf(filterPaths[i]) != -1) {
                isfilter = false;
                break;
            }
        }
        
        if (isfilter) {
        //if (req.getRequestURL().indexOf("/login.jsp")==-1) {
            //log.info("进入到SQLPARAMS FILTER..............");
            log.debug("SQLParamsFilter:" + req.getRequestURL());
            // 过滤请求参数。已过滤的url加个标识，判断有标识的就不要再过滤了。
            if (WebUtils.checkBadChar(req)) {
                //res.sendRedirect(req.getContextPath() + "/404.html");
                PrintWriter out;
                out = response.getWriter();
                StringBuilder builder = new StringBuilder(); 
                String header = req.getHeader("X-Requested-With");
                if(StringUtils.isNotEmpty(header)&&header.equalsIgnoreCase("XMLHttpRequest")){
                    builder.append("sessionIllegal");
                    //result.put("msg", "您尚未登录或会话过期!");
                    //String resultJson = JSON.toJSONString(result);
                    out.print(builder.toString());  
                    out.close();  
                }else{
                    builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
                    builder.append("alert(\"参数含有非法字符\");");  
                    builder.append("window.top.location.href=\"");  
                    builder.append(req.getContextPath() + "/403.jsp");  
                    builder.append("\";</script>");  
                    out.print(builder.toString());  
                    out.close();  
                }
                return;
            }
        }

        /*设置允许同源策略url*/
        String noSameOriginPaths = properties.getProperty("NoSameOriginPaths");
        String[] sameOriginPaths = noSameOriginPaths.split(",");
        boolean isSameOrigin = true;
        for (int i = 0; i < sameOriginPaths.length; i++) {
            if (StringUtils.isNotEmpty(sameOriginPaths[i]) && url.indexOf(sameOriginPaths[i]) != -1) {
                isSameOrigin = false;
            }
        }
        if (isSameOrigin) {
            res.setHeader("x-frame-options", "SAMEORIGIN");
        } else {
            String noSameOriginUrls = properties.getProperty("NoSameOriginUrls");
            res.setHeader("X-Frame-Options", "ALLOW-FROM " + noSameOriginUrls);
        }

        chain.doFilter(req, res);
    }

    public static boolean checkHostList(HttpServletRequest request) {
        String reqHost = request.getHeader("Host");
        /*StringBuffer allowHostStringBuffer = new StringBuffer("");
        allowHostStringBuffer.append("www.ptxzfwzx.gov.cn,");
        allowHostStringBuffer.append("xzfwzx.pingtan.gov.cn,");
        allowHostStringBuffer.append("59.61.182.41:80,");
        allowHostStringBuffer.append("10.23.251.226:8082,");
        allowHostStringBuffer.append("59.61.182.41,");
        allowHostStringBuffer.append("172.16.51.10,");
        allowHostStringBuffer.append("localhost:8087,");
        allowHostStringBuffer.append("localhost:8088,");
        allowHostStringBuffer.append("localhost:8080,");
        allowHostStringBuffer.append("127.0.0.1:8080,");
        allowHostStringBuffer.append("127.0.0.1:8088,");
        allowHostStringBuffer.append("localhost,");
        allowHostStringBuffer.append("172.26.181.203,");
        allowHostStringBuffer.append("127.0.0.1,");
        allowHostStringBuffer.append("24y6c14838.qicp.vip,");
        allowHostStringBuffer.append("192.168.129.178,");
        allowHostStringBuffer.append("192.168.145.130,");
        allowHostStringBuffer.append("27.151.80.26:8090,");
        allowHostStringBuffer.append("27.151.80.26,");*/

        Properties properties = FileUtil.readProperties("conf/config.properties");
        String allowHostStr = properties.getProperty("allowHostStr");//可以加上有可能用来访问的ip和非80端口，和主机内网IP。
        
        //String allowHostStr = allowHostStringBuffer.toString();
//        String allowHostStr = dictionaryService.getDicNames("ALLOWHOST", "HOST");
        String[] allowHosts = allowHostStr.split(",");
        for (String host : allowHosts) {
            if(StringUtils.isBlank(reqHost)||StringUtils.isBlank(host)||host.equals(reqHost)){
                return true;
            }
        }        
        log.error("主机名="+reqHost+"不合法");
        return false;
    }
    /**
     * 方法destroy
     * 
     */
    public void destroy() {
    }
}