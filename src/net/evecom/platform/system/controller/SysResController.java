/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysResService;
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
 * 描述  系统资源Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/sysResController")
public class SysResController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SysResController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysResService sysResService;
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
        sysResService.remove("T_MSJW_SYSTEM_RES","RES_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 系统资源记录",SysLogService.OPERATE_TYPE_DEL);
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
        Map<String,Object>  sysres = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")&&!entityId.equals("0")){
            sysres = sysResService.getByJdbc("T_MSJW_SYSTEM_RES",
                    new String[]{"RES_ID"},new Object[]{entityId});
            
        }
        sysres.put("PARENT_ID", parentId);
        sysres.put("PARENT_NAME", parentName);
        request.setAttribute("sysres", sysres);
        return new ModelAndView("system/sysRes/SysResInfo");
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
        String entityId = request.getParameter("RES_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = sysResService.saveOrUpdateRes(parentId, treeData);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 系统资源记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 系统资源记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "SysResView")
    public ModelAndView sysResView(HttpServletRequest request) {
        return new ModelAndView("system/sysRes/SysResView");
    }
    /**
     * 
     * 描述 递归设置孩子对象
     * @author Flex Hu
     * @created 2014年12月2日 下午5:27:51
     * @param parent
     * @param currentUser
     */
    private void setChild(Map<String,Object> parent,SysUser currentUser){
        List<Map<String,Object>> childMenuList = sysResService.findSubMenusByParentId(parent.get("RES_ID").toString());
        if(childMenuList!=null&&childMenuList.size()>0){
            for(Map<String,Object> child:childMenuList){
                child.put("id",(String)child.get("RES_ID"));
                child.put("name",(String)child.get("RES_NAME"));
                //child.put("icon","plug-in/easyui-1.4/themes/icons/folder_table.png");
                child.put("resKey", child.get("RES_KEY").toString());
                child.put("resUrl", child.get("RES_URL").toString());
                boolean isGranted = sysResService.isGranted(child.get("RES_KEY").toString(), currentUser);
                if(!isGranted){
                    child.put("isHidden",true);
                }
                this.setChild(parent, currentUser);
            }
            parent.put("children", childMenuList);
        }
    }
    
    /**
     * 
     * 描述 tree数据源展现
     * @author Flex Hu
     * @throws IOException 
     * @created 2014年9月16日 下午3:27:35
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //获取根菜单数据
        String resKey = request.getParameter("resKey");
        Map<String,Object> rootMenu = sysResService.getByJdbc("T_MSJW_SYSTEM_RES",new String[]{"RES_KEY"},
                new Object[]{resKey});
        rootMenu.put("id", "-1");
        rootMenu.put("name", rootMenu.get("RES_NAME").toString());
        rootMenu.put("open", true);
        rootMenu.put("resKey", rootMenu.get("RES_KEY").toString());
        //rootMenu.put("icon","plug-in/easyui-1.4/themes/icons/monitor.png");
        List<Map<String,Object>> subChild = sysResService.findSubMenusByParentId(rootMenu.get("RES_ID").toString());
        SysUser currentUser = AppUtil.getLoginUser();
        for(Map<String,Object> child:subChild){
            child.put("id",(String)child.get("RES_ID"));
            child.put("name",(String)child.get("RES_NAME"));
            //child.put("icon","plug-in/easyui-1.4/themes/icons/folder_table.png");
            child.put("resKey", child.get("RES_KEY").toString());
            child.put("resUrl", child.get("RES_URL").toString());
            boolean isGranted = sysResService.isGranted(child.get("RES_KEY").toString(), currentUser);
            if(!isGranted){
                child.put("isHidden",true);
            }
            this.setChild(child, currentUser);
        }
        rootMenu.put("children", subChild);
        String json = JSON.toJSONString(rootMenu);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "grantRole")
    @ResponseBody
    public AjaxJson grantRole(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String roleId = request.getParameter("roleId");
        String[] resIds = request.getParameterValues("checkResIds[]");
        this.sysResService.saveSysResAndRole(roleId, resIds);
        j.setMsg("授权成功");
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
    @RequestMapping(params = "querys")
    public void querys(HttpServletRequest request,
            HttpServletResponse response) {
        String resId= request.getParameter("resId");
        List<Map> list = new ArrayList<Map>();
        if(StringUtils.isNotEmpty(resId)){
            Map<String,Object> pro = sysResService.getByJdbc("T_MSJW_SYSTEM_RES",
                    new String[]{"RES_ID"},new Object[]{resId});
            String tableInfo = (String) pro.get("RES_OPERURLJSON");
            list = JSON.parseArray(tableInfo, Map.class);
        }
        if(list==null){
            list = new ArrayList<Map>();
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

