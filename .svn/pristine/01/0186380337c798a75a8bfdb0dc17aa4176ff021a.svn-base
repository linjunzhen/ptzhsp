/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月25日 上午8:58:43
 */
@Controller
@RequestMapping("/flowDataQueryController")
public class FlowDataQueryController extends BaseController {

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
     * 
     * 获取我的申请业务数据接口编写的例子调用
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "myApply")
    public void myApply(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //获取业务表名称
        String busTableName = request.getParameter("BUS_TABLENAME");
        Set<String> fields = new HashSet<String>();
        fields.add("INFO_ID");
        fields.add("PERSON_NAME");
        fields.add("LEAVE_DATE");
        filter.addFilter("Q_T.CREATOR_ACCOUNT_EQ",AppUtil.getLoginUser().getUsername());
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = executionService.findMyApply(filter, busTableName, fields);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 获取经我审批的业务数据接口编写的例子调用
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "handledByMe")
    public void handledByMe(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //获取业务表名称
        String busTableName = request.getParameter("BUS_TABLENAME");
        Set<String> fields = new HashSet<String>();
        fields.add("INFO_ID");
        fields.add("PERSON_NAME");
        fields.add("LEAVE_DATE");
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = executionService.findHandledByMe(filter, 
                busTableName, fields,AppUtil.getLoginUser().getUsername());
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 获取待我审批的业务数据接口编写的例子调用
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "needMeHandle")
    public void needMeHandle(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //获取业务表名称
        String busTableName = request.getParameter("BUS_TABLENAME");
        Set<String> fields = new HashSet<String>();
        fields.add("INFO_ID");
        fields.add("PERSON_NAME");
        fields.add("LEAVE_DATE");
        filter.addSorted("E.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findTaskBusDatas(filter,
                busTableName, fields, AppUtil.getLoginUser().getUsername());
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}
