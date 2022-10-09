/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.controller;

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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.cms.service.ModuleService;
import net.evecom.platform.system.model.SysUser;
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
 * 描述 栏目Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/moduleController")
public class ModuleController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ModuleController.class);
    /**
     * 引入Service
     */
    @Resource
    private ModuleService moduleService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 
     * 列表页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("cms/module/list");
    }

    /**
     * 
     * 列表页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "publishList")
    public ModelAndView publishList(HttpServletRequest request) {
        return new ModelAndView("cms/module/publishList");
    }

    /**
     * 
     * 列表页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "contentInfo")
    public ModelAndView contentInfo(HttpServletRequest request) {
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        Set<String> roleCodes = sysUser.getRoleCodes();
        if ("__ALL".equals(resKey)||roleCodes.contains("CSLDSH")||roleCodes.contains("ZXZRSH")) {
            request.setAttribute("isShow", "true");
        }
        return new ModelAndView("cms/module/contentInfo");
    }

    /**
     * 
     * 列表页面跳转
     * 
     * @author Rider Chen
     * @created 2015-11-16 下午03:21:21
     * @param request
     * @return
     */
    @RequestMapping(params = "auditList")
    public ModelAndView auditList(HttpServletRequest request) {
        return new ModelAndView("cms/module/auditList");
    }
    
    /**
     * 
     * 列表页面跳转
     * 
     * @author Sundy Sun
     * @param request
     * @return
     */
    @RequestMapping(params = "auditedList")
    public ModelAndView auditedList(HttpServletRequest request) {
        SysUser sysUser = AppUtil.getLoginUser();
        request.setAttribute("sysUser", sysUser);
        return new ModelAndView("cms/module/auditedList");
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        if (null != selectColNames) {
            String[] ids = selectColNames.split(",");
            for (String id : ids) {
                List<Map<String, Object>> contentlist = moduleService.findByContentId(id);
                if (null != contentlist && contentlist.size() > 0) {
                    j.setSuccess(false);
                    j.setMsg("拥有文章,请先删除文章数据");
                    return j;
                }
            }
        }
        moduleService.removeModule("T_CMS_ARTICLE_MODULE", "MODULE_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 栏目记录", SysLogService.OPERATE_TYPE_DEL);
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
        Map<String, Object> module = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            module = moduleService.getByJdbcModule("T_CMS_ARTICLE_MODULE", new String[] { "MODULE_ID" },
                    new Object[] { entityId });
        }
        module.put("PARENT_NAME", request.getParameter("PARENT_NAME"));
        module.put("PARENT_ID", request.getParameter("PARENT_ID"));
        request.setAttribute("module", module);

        return new ModelAndView("cms/module/moduleInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("MODULE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("CREATE_USERID", sysUser.getUserId());
        variables.put("CREATE_USER", sysUser.getFullname());
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = moduleService.saveOrUpdateTreeDataModule(request.getParameter("PARENT_ID"), variables,
                "T_CMS_ARTICLE_MODULE", "S_CMS_ARTICLE_MODULE");

        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 栏目记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 栏目记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * easyui AJAX请求数据 信息采编
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ISTOP", "desc");
        filter.addSorted("T.RELEASE_TIME", "desc");
        filter.addFilter("Q_T.AUDIT_STATUS_=", "0");
        List<Map<String, Object>> list = moduleService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, new String[] { "CONTENT_TEXT" },
                JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据 信息采编
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "publishDatagrid")
    public void publishDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ISTOP", "desc");
        filter.addSorted("T.RELEASE_TIME", "desc");
        filter.addFilter("Q_T.AUDIT_STATUS_>=", "3");
        List<Map<String, Object>> list = moduleService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, new String[] { "CONTENT_TEXT" },
                JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 标准化建设列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bzhjsDatagrid")
    public void bzhjsDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ISTOP", "desc");
        filter.addSorted("T.RELEASE_TIME", "desc");
        List<Map<String, Object>> list = moduleService.findBzhjsDatagrid(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, new String[] { "CONTENT_TEXT" },
                JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "auditDatagrid")
    public void auditDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ISTOP", "desc");
        filter.addSorted("T.RELEASE_TIME", "desc");
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        Set<String> roleCodes = sysUser.getRoleCodes();
        if ("__ALL".equals(resKey)) {// 判断是否超级管理员
            filter.addFilter("Q_T.AUDIT_STATUS_>=", "1");
            filter.addFilter("Q_T.AUDIT_STATUS_<=", "2");
        } else if (roleCodes.contains("CSLDSH")) {// 判断是否处室领导审核
            filter.addFilter("Q_T.AUDIT_STATUS_=", "1");
        } else if (roleCodes.contains("ZXZRSH")) {// 判断是否中心主任审核
            filter.addFilter("Q_T.AUDIT_STATUS_=", "2");
        } else if (roleCodes.contains("XXFB")) {// 判断是否信息发布
            filter.addFilter("Q_T.AUDIT_STATUS_=", "3");
        } else if (roleCodes.contains("XXCB")) {// 判断是否信息采编
            filter.addFilter("Q_T.AUDIT_STATUS_IN", "1,2");
        }else{
            filter.addFilter("Q_T.AUDIT_STATUS_>=", "1");
            filter.addFilter("Q_T.AUDIT_STATUS_<=", "2");
        }
        List<Map<String, Object>> list = moduleService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, new String[] { "CONTENT_TEXT" },
                JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "auditedDatagrid")
    public void auditedDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ISTOP", "desc");
        filter.addSorted("T.RELEASE_TIME", "desc");
        filter.addFilter("Q_T.AUDIT_STATUS_>=", "1");
        SysUser sysUser = AppUtil.getLoginUser();
        String userId=null;
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        Set<String> roleCodes = sysUser.getRoleCodes();
        boolean isGly = roleCodes.contains("88888_GLY");
        /*Set<String> roleCodes = sysUser.getRoleCodes();*/
        if (!"__ALL".equals(resKey)&&!isGly) {// 判断是否超级管理员
            //filter.addFilter("A.AUDIT_USERID_EQ", sysUser.getUserId());
            userId=sysUser.getUserId();
        }
        List<Map<String, Object>> list = moduleService.findViewBySqlFilter(filter,userId);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, new String[] { "CONTENT_TEXT" },
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        String moduleIds = request.getParameter("moduleIds");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", moduleIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        return new ModelAndView("cms/module/moduleSelector");
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-8 下午05:14:33
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId) {
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        // 获取topType
        List<Map<String, Object>> children;
        if ("__ALL".equals(resKey)) {// 判断是否超级管理员
            children = moduleService.findByParentId(parentId);
        }else{
            children = moduleService.findRoleByParentId(parentId);
        }
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("MODULE_ID"));
                child.put("name", child.get("MODULE_NAME"));
                //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, child.get("MODULE_ID").toString());
            }
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-8 下午05:14:37
     * @param request
     * @param response
     * @see net.evecom.platform.base.controller.BaseController#tree
     *      (javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping(params = "tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "栏目结构树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", -1);
        root.put("TREE_LEVEL", 1);
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        // 获取topType
        List<Map<String, Object>> toplist;
        if ("__ALL".equals(resKey)) {// 判断是否超级管理员
            toplist= moduleService.findByParentId("0");
        }else{
            toplist = moduleService.findRoleByParentId("0");
        }
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("MODULE_ID"));
            top.put("name", top.get("MODULE_NAME"));
            //top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildren(top, top.get("MODULE_ID").toString());
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }

    /**
     * 跳转到标准化建设数据界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goBzhjsMyPortal")
    public ModelAndView goBzhjsMyPortal(HttpServletRequest request) {
        return new ModelAndView("cms/myPortal/bzhjsMyPortalList");
    }
    
    /**
     * 
     * 描述  标准化建设树
     * 
     * @author Scolder Lin
     * @created 2021-06-08 下午02:44:37
     * @param request
     * @param response
     */
    @RequestMapping(params = "bzhjsTree")
    public void bzhjsTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "663");
        root.put("name", "标准化建设");
        root.put("open", true);
        root.put("PARENT_ID", -1);
        root.put("TREE_LEVEL", 1);
        // 获取topType
        List<Map<String, Object>> toplist = moduleService.findByParentId("663");
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("MODULE_ID"));
            top.put("name", top.get("MODULE_NAME"));
            this.getChildren(top, top.get("MODULE_ID").toString());
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
}
