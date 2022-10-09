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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExePassInfoService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  流程传阅Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/exePassInfoController")
public class ExePassInfoController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExePassInfoController.class);
    /**
     * 引入Service
     */
    @Resource
    private ExePassInfoService exePassInfoService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
   
    
    /**
     * 跳转到可传阅流程
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "exepassview")
    public ModelAndView exepassview(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/exepassview");
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
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_P.USER_ID_EQ",sysUser.getUserId());
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = exePassInfoService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
   
}

