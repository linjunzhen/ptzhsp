/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.controller.ExecutionController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 部门办件状态
 * @author Sundy Sun
 * @version v1.0
 */
@Controller
@RequestMapping("/deptItemViewController")
public class DeptItemViewController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExecutionController.class);
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 跳转流程实例监控界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goFlowView")
    public ModelAndView goFlowView(HttpServletRequest request) {
        return new ModelAndView("wsbs/serviceitem/deptServiceItem");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        boolean haveHandUp = false;
        String leftDayRange = request.getParameter("LEFTDAYRANGE");
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findItemForDeptView(filter,haveHandUp,leftDayRange);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}
