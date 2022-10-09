/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.message.controller;

import java.io.IOException;
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
import net.evecom.platform.message.service.MessageService;
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
 * 描述  消息提醒Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/messageController")
public class MessageController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MessageController.class);
    /**
     * 引入Service
     */
    @Resource
    private MessageService messageService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("message/list");
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
        messageService.remove("T_APP_MESSAGE","MESSAGE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 消息提醒记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
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
        filter.addSorted("T.MESSAGE_STATUS", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = messageService.findBySqlFilter(filter, "");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  message = messageService.getByJdbc("T_APP_MESSAGE",
                    new String[]{"MESSAGE_ID"},new Object[]{entityId});
            request.setAttribute("message", message);
        }
        return new ModelAndView("message/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("MESSAGE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = messageService.saveOrUpdate(variables, "T_APP_MESSAGE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 消息提醒记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 消息提醒记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * easyui AJAX请求数据 手机APP消息提醒列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/messagePagelist")
    public void messagePagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String esn = request.getParameter("esn");
        Map<String, Object> mapList = messageService.findMessageAppBySql(page, rows, esn);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
   
    /**
     * 
     * 描述 前台添加网上评议
     * 
     * @author Rider Chen
     * @created 2015-11-20 下午04:32:26
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveMessage")
    public void saveMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();

        String esn = request.getParameter("esn");
        String status = request.getParameter("status"); 
        String entityIds = request.getParameter("MESSAGE_IDS");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("ESN", esn);
        variables.put("STATUS", status);
        if(StringUtils.isNotEmpty(entityIds)){
            String []ids = entityIds.split(",");
            for (String id : ids) {
                variables.put("MESSAGE_ID", id);
                messageService.saveOrUpdate(variables, "T_APP_MESSAGE_ESN", null);
            }
            result.put("msg", "操作成功");
            result.put("success", true);
        }else{

            result.put("msg", "操作失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求数据 手机详情
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getMessage")
    public void getMessage(HttpServletRequest request, HttpServletResponse response) {
        String mid = request.getParameter("mid");
        Map<String, Object> content = messageService.getByJdbc("T_APP_MESSAGE",
                new String[] { "MESSAGE_ID" }, new Object[] { mid });
        String esn = request.getParameter("esn");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("ESN", esn);
        variables.put("STATUS", 1);
        variables.put("MESSAGE_ID", mid);
        Map<String, Object> esnMap = messageService.getByJdbc("T_APP_MESSAGE_ESN",
                new String[] { "ESN","MESSAGE_ID","STATUS" }, new Object[] { esn, mid,1});
        if(null==esnMap){
            messageService.saveOrUpdate(variables, "T_APP_MESSAGE_ESN", null);
        }
        String json = JSON.toJSONString(content);
        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求数据 
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/getCount")
    public void getCount(HttpServletRequest request, HttpServletResponse response) {
        String esn = request.getParameter("esn");
        Map<String, Object> count = messageService.getCount(esn);
        String json = JSON.toJSONString(count);
        this.setJsonString(json, response);
    }
}

