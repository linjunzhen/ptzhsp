/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.LogConfigService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年9月5日 下午4:54:54
 */
@Controller
@RequestMapping("/logConfigController")
public class LogConfigController extends BaseController {

    /**
     * 引入logConfigService
     */
    @Resource
    private LogConfigService logConfigService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午5:05:27
     * @param request
     * @return
     */
    @RequestMapping(params="logConfigView")
    public ModelAndView logConfigView(HttpServletRequest request){
        return new ModelAndView("system/logConfig/logConfigView");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午5:29:08
     * @param request
     * @param response
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String,Object>  configInfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            configInfo = logConfigService.getByJdbc("T_SYSTEM_LOGCONFIG_TABLES",
                    new String[]{"CONFIG_ID"},new Object[]{entityId});
            
        }
        configInfo.put("PARENT_ID", parentId);
        configInfo.put("PARENT_NAME", parentName);
        request.setAttribute("configInfo", configInfo);
        return new ModelAndView("system/logConfig/logConfigInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午5:42:14
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateTree")
    @ResponseBody
    public AjaxJson saveOrUpdateTree(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONFIG_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = logConfigService.saveOrUpdateTreeData(parentId, treeData,"T_SYSTEM_LOGCONFIG_TABLES",null);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 日志维护表记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 日志维护表记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2018年9月5日 下午5:24:41
     * @param request
     * @param response
     * 
     */
    @RequestMapping("/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("id", "0");
        root.put("name","日志维护表");
        root.put("open", true);
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        //获取topType
        List<Map<String,Object>> toplist = logConfigService.findConfigTableList("0");
        for(Map<String,Object> top:toplist){
            top.put("id", top.get("CONFIG_ID"));
            top.put("name", top.get("CONFIG_NAME"));
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午5:58:18
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.CREATE_TIME","asc");
        List<Map<String, Object>> list = logConfigService.findConfigListBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月6日 上午9:26:36
     * @param request
     * @return
     */
    @RequestMapping(params="columnInfo")
    public ModelAndView columnInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String configId = request.getParameter("CONFIG_ID");
        String configName = request.getParameter("CONFIG_NAME");
        Map<String,Object> columnInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            columnInfo = logConfigService.getByJdbc("T_SYSTEM_LOGCONFIG_COLUMN", new String[] { "COLUMN_ID" },
                    new Object[] { entityId });
        }else{
            columnInfo.put("CONFIG_ID", configId);
            columnInfo.put("CONFIG_NAME", configName);
        }
        request.setAttribute("columnInfo", columnInfo);
        return new ModelAndView("system/logConfig/columnInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月6日 上午9:43:10
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateColumn")
    @ResponseBody
    public AjaxJson saveOrUpdateColumn(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("COLUMN_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = logConfigService.saveOrUpdate(variables, "T_SYSTEM_LOGCONFIG_COLUMN", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 日志维护表字段记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 日志维护表字段记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
}
