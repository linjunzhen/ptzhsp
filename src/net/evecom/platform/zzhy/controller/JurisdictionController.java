/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

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
import net.evecom.platform.zzhy.service.JurisdictionService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  辖区分局管理Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/jurisdictionController")
public class JurisdictionController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(JurisdictionController.class);
    /**
     * 引入Service
     */
    @Resource
    private JurisdictionService jurisdictionService;
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
        jurisdictionService.remove("T_COMMERCIAL_JURISDICTION","XQ_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 辖区分局管理记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  jurisdiction = jurisdictionService.getByJdbc("T_COMMERCIAL_JURISDICTION",
                    new String[]{"XQ_ID"},new Object[]{entityId});
            request.setAttribute("jurisdiction", jurisdiction);
        }
        return new ModelAndView("zzhy/jurisdiction/info");
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
        String entityId = request.getParameter("XQ_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = jurisdictionService.saveOrUpdate(variables, "T_COMMERCIAL_JURISDICTION", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 辖区分局管理记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 辖区分局管理记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("zzhy/jurisdiction/list");
    }
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.XQ_ID", "DESC");
        List<Map<String, Object>> list = jurisdictionService.findBySqlFilter(filter, null);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     *
     * 描述  匹配辖区工商局
     * @created 2016-11-17 上午11:31:23
     * @param request
     * @param response
     */
    @RequestMapping("getLocalArea")
    @ResponseBody
    public AjaxJson getLocalArea(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String registerAddr = request.getParameter("registerAddr");
        String localArea=jurisdictionService.getLocalArea(registerAddr);
        j.setSuccess(true);
        j.setMsg(localArea);
        return j;
    }
}

