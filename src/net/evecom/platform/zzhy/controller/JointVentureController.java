/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
/**
 * 
 * 描述 合伙企业controller
 * @author Rider Chen
 * @created 2018年6月6日 下午5:33:26
 */
@Controller
@RequestMapping("/jointVentureController")
public class JointVentureController extends BaseController {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(JointVentureController.class);
    
    
    /**
     * 跳转到股东信息界面
     *
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDiv")
    public ModelAndView refreshGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/nzJointVenture/gdxxDiv");
    }
    /**
     * 跳转到股东信息界面
     *
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDivMp")
    public ModelAndView refreshGdxxDivMp(HttpServletRequest request) {
        String companyTypeCode=request.getParameter("companyTypeCode");
        request.setAttribute("companyTypeCode",companyTypeCode);
        //是否显示删除键
        String deleKey=request.getParameter("deleKey");
        request.setAttribute("deleKey",deleKey);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/nzJointVenture/gdxxDiv_MP");
    }
    /**
     * 跳转到执行事务合伙人信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshZxswhhrDiv")
    public ModelAndView refreshZxswhhrDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/nzJointVenture/zxswhhrDiv");
    }
    
    
    /**
     * 跳转到外方投资者信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJwtzzxxDiv")
    public ModelAndView refreshJwtzzxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String companySettype = request.getParameter("companySettype");
        request.setAttribute("companySettype", companySettype);
        String num = request.getParameter("num");
        request.setAttribute("num", num);
        return new ModelAndView("website/applyforms/wzJointVenture/jwtzzxxDiv");
    }
    
    /**
     * 跳转到外方投资者信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshZftzzxxDiv")
    public ModelAndView refreshZftzzxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String companySettype = request.getParameter("companySettype");
        request.setAttribute("companySettype", companySettype);
        String num = request.getParameter("num");
        request.setAttribute("num", num);
        return new ModelAndView("website/applyforms/wzJointVenture/zftzzxxDiv");
    }
    
    /**
     * 跳转到外商投资企业法律文件送达授权委托书信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshAttorneyDiv")
    public ModelAndView refreshAttorneyDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/wzJointVenture/attorneyDiv");
    }
}
