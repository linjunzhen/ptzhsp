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
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysResService;
import net.evecom.platform.system.service.SysRoleService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 系统角色Controller
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/sysRoleController")
public class SysRoleController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SysRoleController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysRoleService sysRoleService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * sysResService
     */
    @Resource
    private SysResService sysResService;
    /**
     * dicTypeService
     */
    @Resource
    private DicTypeService dicTypeService;
    
    /**
     * 
     * 描述 显示角色树
     * @author Flex Hu
     * @created 2015年9月23日 下午5:29:45
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "roleTree")
    @ResponseBody
    public void roleTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rootMap = new HashMap<String, Object>();
        rootMap.put("id", "0");
        rootMap.put("name", "角色列表");
        rootMap.put("open", true);
        //rootMap.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> children = sysRoleService.findBySqlFilter(filter);
        for (Map<String, Object> child : children) {
            child.put("id", (String) child.get("ROLE_ID"));
            child.put("name", (String) child.get("ROLE_NAME"));
            //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
        }
        rootMap.put("children", children);
        String json = JSON.toJSONString(rootMap);
        this.setJsonString(json, response);
    }
    
    /**
     * 显示角色分组树
     * 
     * @author Mason Wang
     */
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> rootMap = new HashMap<String, Object>();
        rootMap.put("id", "-1");
        rootMap.put("name", "角色分组");
        rootMap.put("open", true);
        //rootMap.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        List<Map<String, Object>> children = sysRoleService.findAllRolesGroup();
        for (Map<String, Object> child : children) {
            child.put("id", (String) child.get("DIC_CODE"));
            child.put("name", (String) child.get("DIC_NAME"));
            //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
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
     * 
     * 删除角色信息数据
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        sysRoleService.removeRoleCascade(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 系统角色记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     *
     * 显示角色信息列表
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> sysRole = sysRoleService.getByJdbc("T_MSJW_SYSTEM_SYSROLE", new String[] { "ROLE_ID" },
                    new Object[] { entityId });
            request.setAttribute("sysrole", sysRole);
        }
        return new ModelAndView("system/sysrole/SysRoleInfo");
    }

    /**
     *
     * 增加或者修改角色信息 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ROLE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        boolean isok = false;
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            isok = true;
        }
        String recordId = sysRoleService.saveOrUpdate(variables, "T_MSJW_SYSTEM_SYSROLE", entityId);
        if(isok){
            SysUser sysUser = AppUtil.getLoginUser();
            sysRoleService.saveRoleData(recordId, new String[] { sysUser.getDepId() },
                    new String[] { sysUser.getDepCode() });
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "SysRoleView")
    public ModelAndView sysRoleView(HttpServletRequest request) {
        return new ModelAndView("system/sysrole/SysRoleView");
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
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = sysRoleService.findBySqlFilter(filter);
       
        for (Map<String, Object> role : list) {
            StringBuffer userIds = new StringBuffer("");
            StringBuffer userNames = new StringBuffer("");
            String roleId = (String) role.get("ROLE_ID");
            List<Map<String, Object>> users = sysUserService.findUsersByRoleId(roleId);
            for (int i = 0; i < users.size(); i++) {
                Map<String, Object> user = users.get(i);
                if (i > 0) {
                    userIds.append(",");
                    userNames.append(",");
                }
                userIds.append(user.get("USER_ID"));
                userNames.append(user.get("FULLNAME"));
            }
            role.put("USER_IDS", userIds.toString());
            role.put("USER_NAMES", userNames.toString());
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 
     * 加载角色选择器信息
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String, Object>> list = sysRoleService.findRoles(filter);
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getPingYin((String) map.get("ROLE_NAME")));
        }
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ROLE_ID", "");
            map.put("ROLE_NAME", "请选择角色");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String roleIds = request.getParameter("roleIds");
        String roleNames = request.getParameter("roleNames");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("roleIds", roleIds);
        request.setAttribute("roleNames", roleNames);
        return new ModelAndView("system/sysrole/SysRoleSelector");
    }

    /**
     * 
     * 将角色分配给用户
     * @param request
     * @return
     */
    @RequestMapping(params = "grantUsers")
    @ResponseBody
    public AjaxJson grantUsers(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String roleId = request.getParameter("roleId");
        String userIds = request.getParameter("userIds");
        sysRoleService.saveRoleUser(userIds.split(","), roleId);
        j.setMsg("分配用户成功");
        return j;
    }

    /**
     * 
     * 跳转到角色授权界面
     * @param request
     * @return
     */
    @RequestMapping(params = "grant")
    public ModelAndView grant(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        String needCheckIds = sysResService.getGrantResIds(roleId);
        Map<String,Object> depInfo = sysRoleService.getDepInfo(roleId);
        request.setAttribute("needCheckResIds", needCheckIds);
        request.setAttribute("roleId", roleId);
        request.setAttribute("needCheckDataIds", depInfo.get("DEP_IDS"));
        
        Map<String,Object> moduleInfo = sysRoleService.getRoleIdToModuleID(roleId);
        if(null!=moduleInfo){
            request.setAttribute("needCheckModuleIds", moduleInfo.get("MODULE_IDS"));
        }
        
        return new ModelAndView("system/sysrole/SysRoleGrant");
    }
    
    /**
     * 进行角色授权操作
     * @param request
     * @return
     */
    @RequestMapping(params = "grantRole")
    @ResponseBody
    public AjaxJson grantRole(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String roleId = request.getParameter("roleId");
        String[] resIds = request.getParameterValues("checkResIds[]");
        String[] checkModuleIds = request.getParameterValues("checkModuleIds[]");
        //保存角色授权操作日志
        sysRoleService.saveOrUpdateRoleRightsLog(roleId, resIds, request);
        sysRoleService.saveOrUpdateRoleRights(roleId, resIds, request);
        sysRoleService.saveRoleMoudle(roleId, checkModuleIds);
        j.setMsg("授权成功");
        return j;
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2015-7-31 上午9:36:24
     * @param request
     * @param response
     */
    @RequestMapping(params="loadBus")
    public void loadBus(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list;
        list = sysRoleService.findBusType();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("CATEGORYID","");
        map.put("TYPENAME", "请选择");
        list.add(0, map);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
