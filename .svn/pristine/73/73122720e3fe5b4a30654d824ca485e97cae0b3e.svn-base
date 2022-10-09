/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
public class CookieFilter implements Filter{

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse resp = (HttpServletResponse) response;  
  
        Cookie[] cookies = req.getCookies();  
  
        if (cookies != null) {  
                Cookie cookie = cookies[0];  
                if (cookie != null) {  
                    /*cookie.setMaxAge(3600); 
                    cookie.setSecure(true); 
                    resp.addCookie(cookie);*/  
                      
                    //Servlet 2.5不支持在Cookie上直接设置HttpOnly属性  
                    String value = cookie.getValue();  
                    StringBuilder builder = new StringBuilder();  
                    builder.append("JSESSIONID=" + value + "; ");  
                    builder.append("Secure; ");  
                    builder.append("HttpOnly; ");  
                    Calendar cal = Calendar.getInstance();  
                    cal.add(Calendar.HOUR, 1);  
                    Date date = cal.getTime();  
                    Locale locale = Locale.CHINA;  
                    SimpleDateFormat sdf =   
                            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",locale);  
                    builder.append("Expires=" + sdf.format(date));  
                    resp.setHeader("Set-Cookie", builder.toString());  
                }  
        }  
        chain.doFilter(req, resp); 
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }
    
}
