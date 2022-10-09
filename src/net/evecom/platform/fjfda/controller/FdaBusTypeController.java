/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.fjfda.service.FdaBusTypeService;

/**
 * 描述 针对fdabustype
 * @author Keravon Feng
 * @created 2019年2月27日 下午2:28:13
 */
@Controller
@RequestMapping("/fdaBusTypeController")
public class FdaBusTypeController extends BaseController {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FdaBusTypeController.class);
    
    /**
     * fdaBusTypeService
     */
    @Resource
    private FdaBusTypeService fdaBusTypeService;
    
    /**
     * 描述
     * @author Keravon Feng
     * @created 2019年2月27日 下午2:32:34
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        String typeIds = request.getParameter("typeIds");
        String parentId = request.getParameter("parentId");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", typeIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("parentId", parentId);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("bsdt/applyform/fjfda/foodProduction/busTypeSelector");
    }
    
}
