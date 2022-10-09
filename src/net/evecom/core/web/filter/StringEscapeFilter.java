/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.FileUtil;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
/**
 * 
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年7月22日 下午5:35:36
 */
public class StringEscapeFilter implements Filter {
    /**
     * 
     */
    FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        //把文件服务器的地址放入到session中
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();     
        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        session.setAttribute("_file_Server", fileServer);

        //设置access日志输出参数
        Enumeration<String> names = httpRequest.getParameterNames();
        StringBuilder output = new StringBuilder();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            output.append(name).append("=");
            String values[] = httpRequest.getParameterValues(name);
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    output.append("' ");
                }
 
                output.append(values[i]);
            }
            if (names.hasMoreElements())
                output.append("&");
        }        
        Sm4Utils sm4 = new Sm4Utils();
        httpRequest.setAttribute("postdata", sm4.encryptDataCBC(output.toString()));
        
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("text/html;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        //实际设置
        chain.doFilter(new XssHttpServletRequestWrapper(
                (HttpServletRequest) request), res);
    }

}
