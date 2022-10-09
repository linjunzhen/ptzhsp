/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.platform.base.controller.BaseController;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午9:47:56
 */
@Controller
@RequestMapping("/mainController")
public class MainController extends BaseController {

    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("main/index");
    }
}
