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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.FlowFormService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  流程表单Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowFormController")
public class FlowFormController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowFormController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowFormService flowFormService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    
    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hflow/flowform/flowFormView");
    }
    
    /**
     * 预览界面
     * 
     * @return
     */
    @RequestMapping(params = "preview")
    public ModelAndView preview(HttpServletRequest request) {
        String formId = request.getParameter("formId");
        Map<String,Object> flowForm = flowFormService.getByJdbc("JBPM6_FLOWFORM",
                new String[]{"FORM_ID"},new Object[]{formId});
        String url = flowFormService.getUrlByFlowForm(flowForm,null);
        return new ModelAndView("redirect:"+url);
    }
    
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
        flowFormService.removeCascade(selectColNames.split(","));
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
        String formId = request.getParameter("FORM_ID");
        Map<String,Object> flowForm = null;
        if(StringUtils.isNotEmpty(formId)&&!formId.equals("undefined")){
            flowForm = flowFormService.getByJdbc("JBPM6_FLOWFORM",
                    new String[]{"FORM_ID"},new Object[]{formId});
            //获取类别
            Map<String,Object> flowType = flowFormService.getByJdbc("JBPM6_FLOWTYPE",new String[]{"TYPE_ID"},
                    new Object[]{flowForm.get("TYPE_ID")});
            flowForm.put("TYPE_NAME", flowType.get("TYPE_NAME"));
        }else{
            String typeId = request.getParameter("TYPE_ID");
            String typeName = request.getParameter("TYPE_NAME");
            flowForm = new HashMap<String,Object>();
            flowForm.put("TYPE_ID",typeId);
            flowForm.put("TYPE_NAME",typeName);
        }
        request.setAttribute("flowForm", flowForm);
        return new ModelAndView("hflow/flowform/flowFormInfo");
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
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        try {
            flowFormService.saveOrUpdateCascadeField(flowForm);
            j.setMsg("保存成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            j.setSuccess(false);
            j.setMsg("保存失败");
        }
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
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowFormService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "paramjson")
    public void paramjson(HttpServletRequest request,
            HttpServletResponse response) {
        String formId = request.getParameter("formId");
        if(StringUtils.isNotEmpty(formId)){
            Map<String,Object> flowForm = flowFormService.getByJdbc("JBPM6_FLOWFORM",
                    new String[]{"FORM_ID"},new Object[]{formId});
            String paramJson = (String) flowForm.get("PARAM_JSON");
            if(StringUtils.isNotEmpty(paramJson)){
                List list = JSON.parseArray(paramJson, Map.class);
                this.setListToJsonString(list.size(), list,
                        null, JsonUtil.EXCLUDE, response);
            }
        }
    }
    
    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String formIds = request.getParameter("formIds");
        String formNames = request.getParameter("formNames");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("formIds", formIds);
        request.setAttribute("formNames", formNames);
        return new ModelAndView("hflow/flowform/flowFormSelector");
    }
    
    /**
     * 
     * 
     * 加载选择器信息
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","DESC");
        List<Map<String, Object>> list = flowFormService.findBySqlFilter(filter);
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("FORM_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}

