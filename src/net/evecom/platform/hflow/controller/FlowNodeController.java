/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  流程节点Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowNodeController")
public class FlowNodeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowNodeController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * nodeConfigService
     */
    @Resource
    private NodeConfigService nodeConfigService;
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
        flowNodeService.remove("JBPM6_FLOWNODE","NODE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 流程节点记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  flowNode = flowNodeService.getByJdbc("JBPM6_FLOWNODE",
                    new String[]{"NODE_ID"},new Object[]{entityId});
            request.setAttribute("flowNode", flowNode);
        }
        return new ModelAndView("hflow/flowNode/info");
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
        String entityId = request.getParameter("NODE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = flowNodeService.saveOrUpdate(variables, "JBPM6_FLOWNODE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 流程节点记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 流程节点记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(params = "goConfig")
    public ModelAndView goConfig(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> nodeData = BeanUtil.getMapFromRequest(request);
        Map<String,Object> nodeConfig =nodeConfigService.getByJdbc("JBPM6_NODECONFIG",
            new String[]{"NODE_NAME","DEF_ID","DEF_VERSION"},new Object[]{nodeData.get("nodeName"),
                nodeData.get("defId"),nodeData.get("flowVersion")});
        if(nodeConfig!=null){
            nodeData.put("NODE_CONFID",nodeConfig.get("NODE_CONFID"));
            nodeData.put("CONFIG_ID", nodeConfig.get("CONFIG_ID"));
            nodeData.put("IS_ADDAGREECONTROL", nodeConfig.get("IS_ADDAGREECONTROL"));
            nodeData.putAll(nodeConfig);
        }else{
            nodeData.put("IS_ADDAGREECONTROL","-1");
            nodeData.put("IS_TASKORDER","-1");
            nodeData.put("IS_ASSIGNDEADTIME","-1");
            nodeData.put("IS_ADDPASS","-1");
            nodeData.put("IS_WWYS","0");
            nodeData.put("WORKDAY_LIMIT", 0);
            nodeData.put("IS_PROVINCE_START","-1");
            nodeData.put("IS_ADDPERUAL","-1");
        }
        request.setAttribute("nodeData", nodeData);
        String encodeNodeName = URLEncoder.encode((String)nodeData.get("nodeName"),"UTF-8");
        
        StringBuffer fieldRightUrl = new StringBuffer("fieldRightController.do?rightconf&nodeName=");
        fieldRightUrl.append(encodeNodeName).append("&defId=").append(nodeData.get("defId"));
        fieldRightUrl.append("&flowVersion=").append(nodeData.get("flowVersion"));
        
        StringBuffer flowEventUrl = new StringBuffer("flowEventController.do?eventconf&nodeName=");
        flowEventUrl.append(encodeNodeName).append("&defId=").append(nodeData.get("defId"));
        flowEventUrl.append("&flowVersion=").append(nodeData.get("flowVersion"));
        
        StringBuffer buttonRightUrl = new StringBuffer("buttonRightController.do?buttonrightconf&nodeName=");
        buttonRightUrl.append(encodeNodeName).append("&defId=").append(nodeData.get("defId"));
        buttonRightUrl.append("&flowVersion=").append(nodeData.get("flowVersion"));
        
        StringBuffer nodeAudierUrl = new StringBuffer("nodeAuditerController.do?nodeauditconf&nodeName=");
        nodeAudierUrl.append(encodeNodeName).append("&defId=").append(nodeData.get("defId"));
        nodeAudierUrl.append("&flowVersion=").append(nodeData.get("flowVersion"));
        //定义审批材料配置URL
        StringBuffer materConfigUrl = new StringBuffer("materConfigController.do?materconf&nodeName=");
        materConfigUrl.append(encodeNodeName).append("&defId=").append(nodeData.get("defId"));
        request.setAttribute("fieldRightUrl", fieldRightUrl.toString());
        request.setAttribute("flowEventUrl", flowEventUrl.toString());
        request.setAttribute("buttonRightUrl", buttonRightUrl.toString());
        request.setAttribute("nodeAudierUrl", nodeAudierUrl.toString());
        request.setAttribute("materConfigUrl", materConfigUrl.toString());
        return new ModelAndView("hflow/nodeconfig/flowNodeConfigTab");
    }
}

