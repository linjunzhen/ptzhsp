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
import net.evecom.platform.hflow.service.FormFieldService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  表单字段Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/formFieldController")
public class FormFieldController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FormFieldController.class);
    /**
     * 引入Service
     */
    @Resource
    private FormFieldService formFieldService;
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
        formFieldService.remove("JBPM6_FORMFIELD","FIELD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 表单字段记录",SysLogService.OPERATE_TYPE_DEL);
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
        String formId = request.getParameter("formId");
        request.setAttribute("formId", formId);
        return new ModelAndView("hflow/formfield/formFieldView");
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
        String entityId = request.getParameter("FIELD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = formFieldService.saveOrUpdate(variables, "JBPM6_FORMFIELD", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 表单字段记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 表单字段记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = formFieldService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

