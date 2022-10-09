/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.util.Date;
import java.util.HashMap;
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
import net.evecom.platform.hflow.service.AuditConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  审批控件Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/auditConfigController")
public class AuditConfigController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(AuditConfigController.class);
    /**
     * 引入Service
     */
    @Resource
    private AuditConfigService auditConfigService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        auditConfigService.remove("JBPM6_AUDITCONFIG","CONFIG_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 审批控件记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  auditConfig = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            auditConfig = auditConfigService.getByJdbc("JBPM6_AUDITCONFIG",
                    new String[]{"CONFIG_ID"},new Object[]{entityId});
        }else{
            auditConfig = new HashMap<String,Object>();
            auditConfig.put("AUDITER_TYPE", "1");
        }
        request.setAttribute("auditConfig", auditConfig);
        return new ModelAndView("hflow/auditconfig/auditconfiginfo");
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
        String entityId = request.getParameter("CONFIG_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String auditType = request.getParameter("AUDITER_TYPE");
        if(auditType.equals(Jbpm6Constants.AUDITTYPE_INTERFACE)||auditType.equals(Jbpm6Constants.AUDITTYPE_TASKDECIDE)){
            variables.put("SELECTOR_URL","");
            variables.put("SELECTOR_HEIGHT",null);
            variables.put("SELECTOR_WIDTH",null);
        }else{
            variables.put("CONFIG_CODE", "");
        }
        auditConfigService.saveOrUpdate(variables, "JBPM6_AUDITCONFIG", entityId);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hflow/auditconfig/auditconfigview");
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
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = auditConfigService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

