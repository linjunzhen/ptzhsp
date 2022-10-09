/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.FlowTypeService;
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
 * 描述  流程类别Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/flowTypeController")
public class FlowTypeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowTypeController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowTypeService flowTypeService;
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
        flowTypeService.remove("JBPM6_FLOWTYPE","TYPE_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 流程类别记录",SysLogService.OPERATE_TYPE_DEL);
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
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  flowType = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            flowType = flowTypeService.getByJdbc("JBPM6_FLOWTYPE",
                    new String[]{"TYPE_ID"},new Object[]{entityId});
            
        }
        flowType.put("PARENT_ID", parentId);
        flowType.put("PARENT_NAME", parentName);
        request.setAttribute("flowType", flowType);
        return new ModelAndView("hflow/flowdef/flowTypeInfo");
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
        String entityId = request.getParameter("TYPE_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        flowTypeService.saveOrUpdateTreeData(parentId, treeData,"JBPM6_FLOWTYPE",null);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述 加载数据
     * @author Flex Hu
     * @created 2015年8月18日 下午4:14:55
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request,
            HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String,Object>> list = flowTypeService.findTypes("0");
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("TYPE_ID","");
            map.put("TYPE_NAME","请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}

