/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.controller;

import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.call.service.CallService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * 描述
 * @author Danto Huang
 * @created 2016-1-13 上午11:31:28
 */
@Controller
@RequestMapping("/callDataController")
public class CallDataController extends BaseController {
    /**
     * callService
     */
    @Resource
    private CallService callService;
    /**
     *
     * 描述排队信息展示页面
     * @created 2019年03月17日 下午4:04:15
     * @param request
     * @return
     */
    @RequestMapping(params="callingLineInfo")
    public ModelAndView callingLineInfo(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        if(StringUtils.isEmpty(roomNo)){//一窗通办大厅
            List<Map<String, Object>> callingLineInfos = callService.callingLineInfo(filter);
            request.setAttribute("callingLineInfos", callingLineInfos);
        }else if("H".equals(roomNo)){//海坛分中心
            List<Map<String, Object>> callingLineInfos = callService.htCallingLineInfo(filter);
            request.setAttribute("callingLineInfos", callingLineInfos);
        }else if("X".equals(roomNo)){//君山分中心
            List<Map<String, Object>> callingLineInfos = callService.jsCallingLineInfo(filter,roomNo);
            request.setAttribute("callingLineInfos", callingLineInfos);
        }else{
            List<Map<String, Object>> callingLineInfos = callService.callingLineInfo(filter);
            request.setAttribute("callingLineInfos", callingLineInfos);
        }
        return new ModelAndView("callnew/takeNo/callingInfo");
    }
    /**
    *
    * 描述排队信息展示页面
    * @created 2019年03月17日 下午4:04:15
    * @param request
    * @return
    */
   @RequestMapping(params="yqyzCallingLineInfo")
   public ModelAndView yqyzCallingLineInfo(HttpServletRequest request){
       SqlFilter filter = new SqlFilter(request);
       List<Map<String, Object>> callingLineInfos = callService.yqyzCallingLineInfo(filter);
       request.setAttribute("callingLineInfos", callingLineInfos);
       return new ModelAndView("callnew/takeNo/yqyzCallingInfo");
   }

   /**
    * 描述:
    *
    * @author Madison You
    * @created 2021/3/30 10:13:00
    * @param
    * @return
    */
    @RequestMapping(params="xmtzCallingLineInfo")
    public ModelAndView xmtzCallingLineInfo(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> callingLineInfos = callService.xmtzCallingLineInfo(filter);
        request.setAttribute("callingLineInfos", callingLineInfos);
        return new ModelAndView("callnew/takeNo/xmtzCallingInfo");
    }
    
    /**
     * 
     * 描述    根据业务编码展示排队信息页面
     * @author Danto Huang
     * @created 2019年4月30日 上午10:07:16
     * @param request
     * @return
     */
    @RequestMapping(params="callingLineInfoForMarriage")
    public ModelAndView callingLineInfoForMarriage(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> callingLineInfos = callService.callingLineInfoForMarriage(filter);
        request.setAttribute("callingLineInfos", callingLineInfos);
        return new ModelAndView("callnew/takeNo/callingInfoForMarriage");
    }
    
    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="lineInfoJsonForMarriage")
    public void lineInfoJsonForMarriage(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> callingLineInfos = callService.callingLineInfoForMarriage(filter);
        String json = JSON.toJSONString(callingLineInfos);
        this.setJsonString(json, response);
    }
}