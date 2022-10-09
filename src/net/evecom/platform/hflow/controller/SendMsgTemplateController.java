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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.MsgTemplateService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 短信发送模板
 * @author Sundy Sun
 * @version v1.0
 */
@Controller
@RequestMapping("/msgTemplateController")
public class SendMsgTemplateController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SendMsgTemplateController.class);
    /**
     * service
     */
    @Resource
    private MsgTemplateService msgTemplateService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 
     * 描述   视图列表
     * @return
     */
    @RequestMapping(params="view")
    public ModelAndView view(HttpServletRequest request){
        return new ModelAndView("hflow/msgtemplate/templatelist");
    }
    /**
     * 
     * 描述   数据列表
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = msgTemplateService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   
     * @param request
     * @return
     */
    @RequestMapping(params="info")
    public ModelAndView info(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> templateInfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            templateInfo=msgTemplateService.getByJdbc("JBPM6_SENDMSG_TEMPLATE", new String[] { "TEMPLATE_ID"},
                    new Object[] {entityId});
        }
        request.setAttribute("templateInfo", templateInfo);
        return new ModelAndView("hflow/msgtemplate/templateInfo");
    }
    
    /**
     * 
     * 描述   部门服务事项更新
     * @author Danto Huang
     * @created 2016-9-20 上午10:36:56
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("TEMPLATE_ID");
        String status = "";
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            SysUser sysUser = AppUtil.getLoginUser();
            variables.put("CREATE_USERID", sysUser.getUserId());
            variables.put("CREATE_USERNAME", sysUser.getUsername());
        }
        String recordId = msgTemplateService.saveOrUpdate(variables, "JBPM6_SENDMSG_TEMPLATE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 短信模板记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 短信模板记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }    
    /**
     * 
     * 描述   删除服务事项
     * @author Danto Huang
     * @created 2016-9-20 下午3:37:42
     * @param request
     * @return
     */
    @RequestMapping(params="multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        msgTemplateService.removeCascade(selectColNames);
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的短信模板记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
}
