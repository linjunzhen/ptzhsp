/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchart.service.BusContactService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述：业务联系人
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-5
 */
@Controller
@RequestMapping("/busContactController")
public class BusContactController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusContactController.class);
    /**
     * busContactService
     */
    @Resource
    private BusContactService busContactService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 跳转到列表
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("flowchart/contact/contactView");
    }
    
    /**
     * 跳转到表单
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> busContact = busContactService.getByJdbc("T_LCJC_BUS_CONTRACT", new String[] { "ID" },
                    new String[] { entityId });
            request.setAttribute("busContact", busContact);
        }else{
            Map<String, Object> busContact = new HashMap<String, Object>();
            busContact.put("CONTACT_SEX", "0");
            busContact.put("CONTACT_TYPE", "0");
            request.setAttribute("busContact", busContact);
        }
        return new ModelAndView("flowchart/contact/contactInfo");
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
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = busContactService.saveOrUpdate(variables, "T_LCJC_BUS_CONTRACT", entityId);
        if (StringUtils.isEmpty(entityId)) {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的联系人记录", SysLogService.OPERATE_TYPE_ADD);
        } else {
            sysLogService.saveLog("更新了ID为[" + recordId + "]的联系人记录", SysLogService.OPERATE_TYPE_EDIT);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 描述：表格数据组装
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("L.CREATE_TIME", "desc");
        List<Map<String, Object>> list = busContactService.datagrid(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 批量删除业务估算数据
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        this.busContactService.remove("T_LCJC_BUS_CONTRACT", "ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的联系人信息记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
}
