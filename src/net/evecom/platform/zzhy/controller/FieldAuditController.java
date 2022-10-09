/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

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
import net.evecom.platform.zzhy.service.FieldAuditService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  商事字段审核Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/fieldAuditController")
public class FieldAuditController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FieldAuditController.class);
    /**
     * 引入Service
     */
    @Resource
    private FieldAuditService fieldAuditService;
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
        fieldAuditService.remove("T_COMMERCIAL_FIELDAUDIT","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 商事字段审核记录",SysLogService.OPERATE_TYPE_DEL);
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  fieldAudit = fieldAuditService.getByJdbc("T_COMMERCIAL_FIELDAUDIT",
                    new String[]{"RECORD_ID"},new Object[]{entityId});
            request.setAttribute("fieldAudit", fieldAudit);
        }
        return new ModelAndView("zzhy/fieldAudit/info");
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
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = fieldAuditService.saveOrUpdate(variables, "T_COMMERCIAL_FIELDAUDIT", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 商事字段审核记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 商事字段审核记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 修改操作
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("RECORD_ID");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  variables = fieldAuditService.getByJdbc("T_COMMERCIAL_FIELDAUDIT",
                    new String[]{"RECORD_ID"},new Object[]{entityId});
            variables.put("CONFIRM_STATUS", 1);
            String recordId = fieldAuditService.saveOrUpdate(variables, "T_COMMERCIAL_FIELDAUDIT", entityId);
        }
        result.put("msg", "操作成功");
        result.put("success", true);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        List<Map<String, Object>>  list = fieldAuditService.findBySqlFilter(filter, variables);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
}

