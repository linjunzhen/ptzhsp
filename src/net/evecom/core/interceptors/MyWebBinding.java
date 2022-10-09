/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.interceptors;

import java.util.Date;

import net.evecom.core.util.DateConvertEditor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * MyWebBinding类
 * 
 * @author Flex Hu
 * @version 1.0
 */
public class MyWebBinding implements WebBindingInitializer {

    /**
     * 方法initBinder
     * 
     * @param binder
     *            传入参数
     * @param request
     *            传入参数
     */
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // 1. 使用spring自带的CustomDateEditor
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // binder.registerCustomEditor(Date.class, new
        // CustomDateEditor(dateFormat, true));
        // 2. 自定义的PropertyEditorSupport
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }

}
