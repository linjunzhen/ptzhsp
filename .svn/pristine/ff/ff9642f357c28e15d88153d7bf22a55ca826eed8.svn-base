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
import net.evecom.platform.hflow.service.ButtonRightService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  按钮权限Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/buttonRightController")
public class ButtonRightController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ButtonRightController.class);
    /**
     * 引入Service
     */
    @Resource
    private ButtonRightService buttonRightService;
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
        buttonRightService.remove("JBPM6_BUTTONRIGHT","RIGHT_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 按钮权限记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  buttonRight = buttonRightService.getByJdbc("JBPM6_BUTTONRIGHT",
                    new String[]{"RIGHT_ID"},new Object[]{entityId});
            request.setAttribute("buttonRight", buttonRight);
        }
        return new ModelAndView("hflow/buttonRight/info");
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
        String defId = request.getParameter("DEFID");
        String nodeName = request.getParameter("NODENAME");
        int flowVersion = Integer.parseInt(request.getParameter("FLOWVERSION"));
        String buttonRightJson = request.getParameter("BUTTONRIGHT_JSON");
        buttonRightService.saveButtonRight(defId, nodeName, buttonRightJson,flowVersion);
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
        String nodeName = request.getParameter("nodeName");
        String defId = request.getParameter("defId");
        int flowVersion = Integer.parseInt(request.getParameter("flowVersion"));
        buttonRightService.saveNewButtonRight(defId, nodeName, flowVersion);
        List<Map<String, Object>> list = buttonRightService.findList(nodeName, defId,false,flowVersion);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到权限配置界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "buttonrightconf")
    public ModelAndView buttonrightconf(HttpServletRequest request) {
        Map<String, Object> nodeData = BeanUtil.getMapFromRequest(request);
        request.setAttribute("nodeData", nodeData);
        return new ModelAndView("hflow/buttonright/buttonrightconf");
    }
}

