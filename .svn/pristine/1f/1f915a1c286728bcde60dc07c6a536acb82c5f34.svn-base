/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.util.ArrayList;
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
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
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
 * 描述  流程映射类Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowMappedController")
public class FlowMappedController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowMappedController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
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
        flowMappedService.remove("T_WSBS_FLOWYS","YS_ID",selectColNames.split(","));
        flowMappedService.remove("T_WSBS_FLOWYSNODES", new String[]{"YS_ID"},selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 流程映射类记录",SysLogService.OPERATE_TYPE_DEL);
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
        Map<String,Object>  flowMapped  = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            flowMapped = flowMappedService.getByJdbc("T_WSBS_FLOWYS",
                    new String[]{"YS_ID"},new Object[]{entityId});
        }else{
            String defId = request.getParameter("defId");
            flowMapped.put("DEF_ID",defId);
        }
        String version = request.getParameter("version");
        flowMapped.put("version",version);
        request.setAttribute("flowMapped", flowMapped);
        return new ModelAndView("hflow/flowmapped/info");
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
        String entityId = request.getParameter("YS_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        //String recordId = flowMappedService.saveOrUpdate(variables, "T_WSBS_FLOWYS", entityId);
        String recordId = flowMappedService.saveOrUpdateYs(variables, "T_WSBS_FLOWYS");
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 流程映射类记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 流程映射类记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 跳转到流程映射配置界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowMappedConfig")
    public ModelAndView flowMappedConfig(HttpServletRequest request) {
        String defId = request.getParameter("defId");
        String version = request.getParameter("version");
        request.setAttribute("defId", defId);
        request.setAttribute("version", version);
        return new ModelAndView("hflow/flowmapped/flowMappedConfig");
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
        filter.addSorted("T.YS_CN","ASC");
        List<Map<String, Object>> list = flowMappedService.findBySqlFilter(filter);
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
    @RequestMapping("/getByDefidAndNodeName")
    public void getByDefidAndNodeName(HttpServletRequest request,
            HttpServletResponse response){
        String defId = request.getParameter("defId");
        List<Map<String, Object>> list = flowMappedService.getByDefidAndNodeName(defId);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 跳转到流程映射配置界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String defId = request.getParameter("defId");
        String version = request.getParameter("version");
        request.setAttribute("defId", defId);
        request.setAttribute("version", version);
        return new ModelAndView("hflow/flowmapped/selector");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "nodeDatagrid")
    public void nodeDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        //SqlFilter filter = new SqlFilter(request);
        String defId = request.getParameter("defId");
        String flowVersion = request.getParameter("version");
      //获取开始节点的名称
        String startNodeName = flowNodeService.getNodeName(defId, 
                Integer.parseInt(flowVersion), Jbpm6Constants.START_NODE);
        //String endNodeName = flowNodeService.getNodeName(defId, 
               // Integer.parseInt(flowVersion), Jbpm6Constants.END_NODE);
        List<String> nodeList = flowNodeService.findTaskNodeNames(defId, Integer.parseInt(flowVersion));
        nodeList.add(startNodeName);
        nodeList.add("已办结");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < nodeList.size(); i++) {
            Map<String, Object> e = new HashMap<String, Object>();
            e.put("NODENAME", nodeList.get(i));
            list.add(e);
        }
        this.setListToJsonString(nodeList.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

