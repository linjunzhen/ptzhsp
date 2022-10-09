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

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.NodeAuditerService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  节点审核Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/nodeAuditerController")
public class NodeAuditerController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(NodeAuditerController.class);
    /**
     * 引入Service
     */
    @Resource
    private NodeAuditerService nodeAuditerService;
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
        nodeAuditerService.remove("JBPM6_NODEAUDITERCONF","AUDITER_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 节点审核记录",SysLogService.OPERATE_TYPE_DEL);
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
        Map<String,Object>  nodeAuditer = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            nodeAuditer = nodeAuditerService.getByJdbc("JBPM6_NODEAUDITERCONF",
                    new String[]{"AUDITER_ID"},new Object[]{entityId});
        }else{
            nodeAuditer = new HashMap<String,Object>();
            String defId = request.getParameter("defId");
            String nodeName = request.getParameter("nodeName");
            int flowVersion = Integer.parseInt(request.getParameter("flowVersion"));
            nodeAuditer.put("DEF_ID", defId);
            nodeAuditer.put("DEF_VERSION", flowVersion);
            nodeAuditer.put("NODE_NAME", nodeName);
        }
        request.setAttribute("nodeAuditer", nodeAuditer);
        return new ModelAndView("hflow/nodeauditerconf/nodeauditerinfo");
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
        String entityId = request.getParameter("AUDITER_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        nodeAuditerService.saveOrUpdate(variables, "JBPM6_NODEAUDITERCONF", entityId);
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
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = nodeAuditerService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到权限配置界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "nodeauditconf")
    public ModelAndView nodeauditconf(HttpServletRequest request) {
        Map<String,Object> nodeData = BeanUtil.getMapFromRequest(request);
        request.setAttribute("nodeData", nodeData);
        return new ModelAndView("hflow/nodeauditerconf/nodeauditerview");
    }
}

