/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.developer.service.CodeProjectService;
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
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/codeProjectController")
public class CodeProjectController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CodeProjectController.class);
    /**
     * 引入codeProjectService
     */
    @Resource
    private CodeProjectService codeProjectService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     * 描述 tree数据源展现
     * @author Flex Hu
     * @throws IOException 
     * @created 2014年9月16日 下午3:27:35
     */
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String,Object> rootMap  = new HashMap<String,Object>();
        rootMap.put("id", "-1");
        rootMap.put("name", "代码建模项目包");
        rootMap.put("open", true);
        //rootMap.put("icon","plug-in/easyui-1.4/themes/icons/monitor.png");
        List<Map<String,Object>> children = codeProjectService.findAllProject();
        for(Map<String,Object> child:children){
            child.put("id",(String)child.get("PROJECT_ID"));
            child.put("name",(String)child.get("PROJECT_NAME"));
            //child.put("icon","plug-in/easyui-1.4/themes/icons/folder_table.png");
        }
        rootMap.put("children", children);
        String json = JSON.toJSONString(rootMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    /**
     * 列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("developer/codeproject/list");
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
        codeProjectService.removeByProjectId(selectColNames);
        j.setMsg("删除成功");
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
        filter.addSorted("CREATE_TIME","desc");
        List<Map<String, Object>> list = codeProjectService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
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
            Map<String,Object> codeProject = codeProjectService.getByJdbc("T_MSJW_DEVELOPER_PROJECT",
                    new String[]{"PROJECT_ID"},new Object[]{entityId});
            request.setAttribute("codeProject", codeProject);
        }
        return new ModelAndView("developer/codeproject/info");
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
        codeProjectService.saveOrUpdateCodeProject(request);
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
    @RequestMapping(params = "tableInfo")
    public void tableInfo(HttpServletRequest request,
            HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        String projectId = request.getParameter("PROJECT_ID");
        if(StringUtils.isNotEmpty(projectId)){
            list = this.codeProjectService.findTableInfos(projectId);
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}
