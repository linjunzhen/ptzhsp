/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

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
import net.evecom.platform.wsbs.service.ServiceItemFormService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  服务事项绑定表单Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/serviceItemFormController")
public class ServiceItemFormController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ServiceItemFormController.class);
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemFormService serviceItemFormService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
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
        serviceItemFormService.remove("T_WSBS_SERVICEITEM_FORM","ITEM_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 服务事项绑定表单记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  serviceItemForm = serviceItemFormService.getByJdbc("T_WSBS_SERVICEITEM_FORM",
                    new String[]{"ITEM_ID"},new Object[]{entityId});
            request.setAttribute("serviceItemForm", serviceItemForm);
        }
        return new ModelAndView("wsbs/serviceItemForm/info");
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
        String entityId = request.getParameter("ITEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = serviceItemFormService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_FORM", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 服务事项绑定表单记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 服务事项绑定表单记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String bindFormIds = request.getParameter("bindFormIds");
        String bindFormNames = request.getParameter("bindFormNames");
        if (StringUtils.isNotEmpty(bindFormIds) && !bindFormIds.equals("undefined")) {
            request.setAttribute("bindFormIds", bindFormIds);
            request.setAttribute("bindFormNames", bindFormNames);
        }
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("wsbs/serviceitem/serviceItemFormSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String bindFormIds = request.getParameter("bindFormIds");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(bindFormIds)){
            list = dictionaryService.findByDicIds(bindFormIds);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
}

