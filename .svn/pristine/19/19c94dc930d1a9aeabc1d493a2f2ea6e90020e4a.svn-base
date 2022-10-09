/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.FdaStaticService;

/**
 * 描述
 * @author Danto Huang
 * @created 2019年2月25日 下午3:48:16
 */
@Controller
@RequestMapping("/fdaStaticController")
public class FdaStaticController extends BaseController {
    /**
     * 引入service
     */
    @Resource
    private FdaStaticService fdaStaticService;

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "fdaStatic")
    public ModelAndView fdaStatic(HttpServletRequest request) {
        return new ModelAndView("bsfw/fda/fdaStatic");
    }
    /**
     * 跳转到个人绩效报表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goIndPerStatis")
    public ModelAndView goIndPerStatis(HttpServletRequest request) {
        return new ModelAndView("bsfw/fda/indPerStatis");
    }
    
    /**
     * 
     * 描述   异常办件统计
     * @param request
     * @return
     */
    @RequestMapping(params = "abnormalStatis")
    public ModelAndView abnormalStatis(HttpServletRequest request){
        return new ModelAndView("bsfw/fda/abnormalStatis");
    }

    /**
     * 
     * 描述    逾期办件统计
     * @author Danto Huang
     * @created 2019年2月25日 下午4:03:25
     * @param request
     * @param response
     */
    @RequestMapping(params = "adnormalData")
    public void adnormalData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> list = fdaStaticService.getAdnormalStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,response);
    }
    
    /**
     * 
     * 描述    窗口人员办件量统计
     * @author Danto Huang
     * @created 2019年2月25日 下午4:27:40
     * @param request
     * @param response
     */
    @RequestMapping(params = "indPerData")
    public void glcData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = fdaStaticService.getIndPerDataStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:25:46
     * @param request
     * @return
     */
    @RequestMapping(params = "countsDetail")
    public ModelAndView countsDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("bsfw/fda/countsDetail");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:28:29
     * @param request
     * @param response
     */
    @RequestMapping(params = "countsDetailData")
    public void countsDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = fdaStaticService.countsDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:32:28
     * @param request
     * @return
     */
    @RequestMapping(params = "banJSDetail")
    public ModelAndView banJSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("bsfw/fda/banJSDetail");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:33:35
     * @param request
     * @param response
     */
    @RequestMapping(params = "banJSDetailData")
    public void banJSDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = fdaStaticService.banJSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:36:26
     * @param request
     * @return
     */
    @RequestMapping(params = "zBSDetail")
    public ModelAndView zBSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("bsfw/fda/zBSDetail");
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:37:11
     * @param request
     * @param response
     */
    @RequestMapping(params = "zBSDetailData")
    public void zBSDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = fdaStaticService.zBSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:37:44
     * @param request
     * @return
     */
    @RequestMapping(params = "tJSDetail")
    public ModelAndView tJSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("bsfw/fda/tJSDetail");
    }

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:37:41
     * @param request
     * @param response
     */
    @RequestMapping(params = "tJSDetailData")
    public void tJSDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = fdaStaticService.tJSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}
