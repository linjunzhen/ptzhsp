/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  流程事件Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowEventController")
public class FlowEventController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowEventController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 跳转到权限配置界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "eventconf")
    public ModelAndView eventconf(HttpServletRequest request) {
        Map<String, Object> nodeData = BeanUtil.getMapFromRequest(request);
        request.setAttribute("nodeData", nodeData);
        return new ModelAndView("hflow/flowevent/eventconf");
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
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.EVENT_TYPE","asc");
        List<Map<String, Object>> list = flowEventService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String eventJson = request.getParameter("FLOWEVENT_JSON");
        String defId = request.getParameter("EVENT_DEFID");
        String nodeName = request.getParameter("EVENT_NODENAME");
        int flowVersion = Integer.parseInt(request.getParameter("EVENT_FLOWVERSION"));
        flowEventService.saveEvent(defId, nodeName, eventJson,flowVersion);
        j.setMsg("保存成功");
        return j;
    }
}

