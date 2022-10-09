/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 描述上下文工具类
 * 
 * @author Panda Chen
 * @created 2016-11-2 下午04:03:20
 */
public class ContextHolderUtil {

    /**
     * 
     * 描述SpringMvc下获取request
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:03:27
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;

    }

    /**
     * 
     * 描述SpringMvc下获取session
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:03:33
     * @return
     */
    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;

    }

}
