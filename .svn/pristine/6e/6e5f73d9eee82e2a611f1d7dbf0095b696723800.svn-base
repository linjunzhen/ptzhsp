/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.FormValidateService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  验证配置Controller
 * @author Danto Huang
 * @version 1.0
 * @created 2015-3-23 上午10:26:12
 */
@Controller
@RequestMapping("/formValidateController")
public class FormValidateController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FormValidateController.class);
    /**
     * formValidateService
     */
    @Resource
    private FormValidateService formValidateService;
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
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("system/validate/list");
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
        formValidateService.remove("T_MSJW_SYSTEM_VALIDATE_RULE","RULE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 字典信息记录",SysLogService.OPERATE_TYPE_DEL);
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
        String fromId = request.getParameter("FORM_ID");
        String formName = request.getParameter("FORM_NAME");
        Map<String,Object>  validateRule  = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            validateRule = formValidateService.getByJdbc("T_MSJW_SYSTEM_VALIDATE_RULE",
                    new String[]{"RULE_ID"},new Object[]{entityId});
            fromId = (String) validateRule.get("FORM_ID");
            Map<String,Object> validateForm = formValidateService.
                    getByJdbc("T_MSJW_SYSTEM_VALIDATE_FORM",new String[]{"FORM_ID"},new Object[]{fromId});
            validateRule.put("FORM_ID", fromId);
            validateRule.put("FORM_NAME",(String)validateForm.get("FORM_NAME"));
        }else{
            validateRule.put("FORM_ID",fromId);
            validateRule.put("FORM_NAME",formName);
        }
        request.setAttribute("validateRule", validateRule);
        return new ModelAndView("system/validate/validateRuleInfo");
    }

    /**
     * 跳转到表单树信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "infoTree")
    public ModelAndView infoTree(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  validateForm = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            validateForm = formValidateService.getByJdbc("T_MSJW_SYSTEM_VALIDATE_FORM",
                    new String[]{"FORM_ID"},new Object[]{entityId});
            
        }
        validateForm.put("PARENT_ID", parentId);
        validateForm.put("PARENT_NAME", parentName);
        request.setAttribute("validateForm", validateForm);
        return new ModelAndView("system/validate/validateFormInfo");
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
        String entityId = request.getParameter("RULE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(variables.get("NOTNULL")==null){
            variables.put("NOTNULL",0);
        }
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String formId = (String) variables.get("FORM_ID");
            int maxSn = formValidateService.getMaxSn(formId);
            variables.put("RULE_SN", maxSn+1);
        }
        String recordId = formValidateService.saveOrUpdate(variables, "T_MSJW_SYSTEM_VALIDATE_RULE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的详细验证规则记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的详细验证规则记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 表单树修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateTree")
    @ResponseBody
    public AjaxJson saveOrUpdateTree(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("FORM_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = formValidateService.saveOrUpdateTreeData(parentId, treeData, "T_MSJW_SYSTEM_VALIDATE_FORM",
                null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的验证配置记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的验证配置记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 配置删除方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelTree")
    @ResponseBody
    public AjaxJson multiDelTree(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        formValidateService.removeByFormId(selectColNames);
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的验证配置记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
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
        filter.addSorted("D.RULE_SN","desc");
        filter.addSorted("D.CREATE_TIME","desc");
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = formValidateService.findBySqlFilter(filter);
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
    @RequestMapping(params = "ruleInfo")
    public void ruleInfo(HttpServletRequest request,
            HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        String ruleId = request.getParameter("RULE_ID");
        if(StringUtils.isNotEmpty(ruleId)){
            list = this.formValidateService.findRuleInfos(ruleId);
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    
    /**
     * 方法updateSn
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] ruleIds = request.getParameterValues("ruleIds[]");
        this.formValidateService.updateSn(ruleIds);
        j.setMsg("排序成功");
        return j;
    }
}
