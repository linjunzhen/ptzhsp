/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;

/**
 * 
 * 描述：多证合一
 * 
 * @author Rider Chen
 * @created 2018年8月7日 上午9:13:21
 */
@Controller
@RequestMapping("/multipleController")
public class MultipleController extends BaseController {
    /**
     * 跳转到房地产经纪机构及其分支机构备案界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshFdcjjjgDiv")
    public ModelAndView refreshFdcjjjgDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/common/fdcjjjgDiv");
    }

    /**
     * 跳转到广告发布单位
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGgfbdwDiv")
    public ModelAndView refreshGgfbdwDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/common/ggfbdwDiv");
    }

    /**
     * 跳转到广告发布单位
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGczjzxqyslDiv")
    public ModelAndView refreshGczjzxqyslDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/common/gczjzxqyslDiv");
    }
}
