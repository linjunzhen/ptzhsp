/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.SysLogService;
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
 * 描述 部门Controller
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/departmentController")
public class DepartmentController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DepartmentController.class);
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入sysUserService
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 
     * 删除部门信息
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
        List users = sysUserService.findUsersByDepId(selectColNames);
        if (users.size() > 0) {
            j.setSuccess(false);
            j.setMsg("抱歉,该部门下存在用户,请将用户转移之后再进行删除操作!");
            return j;
        }
        departmentService.removeDepart(selectColNames);
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 部门记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 显示部门详细信息
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("PARENT_ID");
        String parentName = request.getParameter("PARENT_NAME");
        Map<String, Object> department = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined") && !entityId.equals("0")) {
            department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                    new Object[] { entityId });
            // 获取区划代码
            String adviCode = (String) department.get("ADVI_CODE");
            if (StringUtils.isNotEmpty(adviCode)) {
                // 获取字典类别值
                Map<String, Object> dicType = departmentService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                        new String[] { "TYPE_CODE" }, new Object[] { adviCode });
                department.put("ADVI_NAME", dicType.get("TYPE_NAME"));
            }
        }
        department.put("PARENT_ID", parentId);
        department.put("PARENT_NAME", parentName);
        request.setAttribute("department", department);
        return new ModelAndView("system/department/DepartmentInfo");
    }

    /**
     * 增加或者修改部门信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DEPART_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            treeData.put("STATUS", DepartmentService.NORMAL_STATUS);
        }
        String recordId = departmentService.saveOrUpdateCascadeUser(parentId, treeData);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 部门记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 部门记录", SysLogService.OPERATE_TYPE_ADD);
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
    @RequestMapping(params = "DepartmentView")
    public ModelAndView departmentView(HttpServletRequest request) {
        return new ModelAndView("system/department/DepartmentView");
    }

    /**
     * 将用户加入到部门当中去
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "addUsers")
    @ResponseBody
    public AjaxJson addUsers(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String departId = request.getParameter("departId");
        String userIds = request.getParameter("userIds");
        sysUserService.updateDepId(departId, userIds, true);
        j.setMsg("加入用户成功");
        return j;
    }

    /**
     * 将用户从部门中移除
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "removeUsers")
    @ResponseBody
    public AjaxJson removeUsers(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String departId = request.getParameter("departId");
        String userIds = request.getParameter("userIds");
        sysUserService.updateDepId(departId, userIds, true);
        j.setMsg("转移用户成功");
        return j;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        String departIds = request.getParameter("departIds");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", departIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("system/department/DepartmentSelector");
    }
    
    /**
     * 跳转到一级信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/yjselector")
    public ModelAndView yjselector(HttpServletRequest request) {
        String departIds = request.getParameter("departIds");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", departIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("wsbs/tabletmenu/YJDepartmentSelector");
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
        List<Map<String, Object>> list = departmentService.findBySqlFilter(filter);
        String[] pathNameArray;
        Map department;
        String path;
        for (Map listMap : list) {
            path = listMap.get("PATH").toString();
            pathNameArray = path.split("\\.");
            StringBuffer pathName = new StringBuffer();
            for (int i = 0; i < pathNameArray.length; i++) {
                if (!pathNameArray[i].equals("0")) {
                    department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                            new Object[] { pathNameArray[i] });
                    String departName = department.get("DEPART_NAME").toString();
                    pathName.append("/" + departName);
                }
            }
            listMap.put("PATH_NAME", pathName);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2015年4月17日 下午4:05:11
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId) {
        List<Map<String, Object>> children = departmentService.findByParentId(parentId);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("DEPART_ID"));
                child.put("name", child.get("DEPART_NAME"));
                //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, (String) child.get("DEPART_ID"));
            }
        }
    }

    @RequestMapping(params = "tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = getParent();
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }

    @RequestMapping("/deptTree")
    public void deptTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = getParent();
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }

    private Map<String, Object> getParent() {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "组织机构树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        // 获取topType
        List<Map<String, Object>> toplist = departmentService.findByParentId("0");
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("DEPART_ID"));
            top.put("name", top.get("DEPART_NAME"));
            //top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildren(top, (String) top.get("DEPART_ID"));
        }
        root.put("children", toplist);
        return root;
    }

    /**
     * 
     * 加载数据字典URL
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        //SysUser sysUser = AppUtil.getLoginUser();
        //String sysUserResKey = sysUser.getResKeys();

        List<Map<String, Object>> list = departmentService.findDepartList("0");
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("UNIT_ID", "");
            map.put("UNIT_CODE", "");
            map.put("UNIT_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 开发商页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "DevelopersView")
    public ModelAndView developersView(HttpServletRequest request) {
        return new ModelAndView("flowchart/department/DevelopersView");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "developersDatagrid")
    public void developersDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.UNIT_TYPE_EQ", "3");
        List<Map<String, Object>> list = departmentService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "developersInfo")
    public ModelAndView developersInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> developers = departmentService.getByJdbc("T_LCJC_SYSTEM_DEVELOPER",
                    new String[] { "UNIT_ID" }, new Object[] { entityId });
            request.setAttribute("developers", developers);
        }
        return new ModelAndView("flowchart/department/DevelopersInfo");
    }

    /**
     * 增加或者修改部门信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveDeveloper")
    @ResponseBody
    public AjaxJson saveDeveloper(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DEPART_ID");
        String parentId = request.getParameter("PARENT_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            treeData.put("STATUS", DepartmentService.NORMAL_STATUS);
        }
        String recordId = departmentService.saveDeveloper(parentId, treeData);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 部门记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 部门记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "developersDel")
    @ResponseBody
    public AjaxJson developersDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] idStr = selectColNames.split(",");
        StringBuffer sysNames = new StringBuffer("");
        for (String sysId : idStr) {
            if (StringUtils.isNotEmpty(sysNames.toString())) {
                sysNames.append(",");
            }
            List users = sysUserService.findUsersByDepId(sysId);
            if (users.size() > 0) {
                j.setSuccess(false);
                j.setMsg("抱歉,该系统开发商下存在用户,请将用户转移之后再进行删除操作!");
                return j;
            }
            Map<String, Object> busSys = departmentService.getByJdbc("T_LCJC_SYSTEM_DEVELOPER",
                    new String[] { "UNIT_ID" }, new Object[] { sysId });
            sysNames.append(busSys.get("UNIT_NAME"));
        }
        sysLogService.saveLog("删除了系统开发商[" + sysNames + "]的 记录", SysLogService.OPERATE_TYPE_DEL);
        departmentService.remove("T_LCJC_SYSTEM_DEVELOPER", "UNIT_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 加载数据字典URL
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "loadDevelop")
    public void loadDevelop(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String, Object>> list = departmentService.findByParentId("0", 3);
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("UNIT_ID", "");
            map.put("UNIT_CODE", "");
            map.put("UNIT_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }


    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/childSelector")
    public ModelAndView childSelector(HttpServletRequest request) {
        String departIds = request.getParameter("departIds");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        String rootParentId = request.getParameter("rootParentId");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", departIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("rootParentId", rootParentId);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("system/department/DepartmentChildSelector");
    }

    @RequestMapping(params = "treeAuth")
    public void treeAuth(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = getParentAuth();
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }

    private Map<String, Object> getParentAuth() {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "组织机构树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);
        // 获取topType
        List<Map<String, Object>> toplist = departmentService.findByParentIdAuth("0");
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("DEPART_ID"));
            top.put("name", top.get("DEPART_NAME"));
            //top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildrenAuth(top, (String) top.get("DEPART_ID"));
        }
        root.put("children", toplist);
        return root;
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-9-28 上午9:01:09
     * @param parentType
     * @param parentId
     */
    public void getChildrenAuth(Map<String, Object> parentType, String parentId) {
        List<Map<String, Object>> children = departmentService.findByParentIdAuth(parentId);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("DEPART_ID"));
                child.put("name", child.get("DEPART_NAME"));
                //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildrenAuth(child, (String) child.get("DEPART_ID"));
            }
        }
    }
    
    /**
     * 一级部门树
     * 
     * @param request
     * @return
     */
    @RequestMapping("/parentSelector")
    public ModelAndView parentSelector(HttpServletRequest request) {
        String departIds = request.getParameter("departIds");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", departIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("wsbs/billRight/YJDepartmentSelector");
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-28 上午10:21:50
     * @param request
     * @param response
     */
    @RequestMapping(params = "parentTree")
    public void parentTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = getParentOnly(request);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-28 上午10:26:48
     * @return
     */
    private Map<String, Object> getParentOnly(HttpServletRequest request) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "组织机构树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", "-1");
        root.put("TREE_LEVEL", 1);

        String checkIds = request.getParameter("needCheckIds");
        Set<String> needCheckIds = new HashSet<String>();
        if (StringUtils.isNotEmpty(checkIds)) {
            String[] checkIdArray = checkIds.split(",");
            for (String checkId : checkIdArray) {
                needCheckIds.add(checkId);
            }
        }
        if (needCheckIds.size() > 0) {
            root.put("checked", true);
        }
        // 获取topType
        List<Map<String, Object>> toplist = departmentService.findByParentId("0");
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("DEPART_ID"));
            top.put("name", top.get("DEPART_NAME"));
            if (needCheckIds.contains(top.get("DEPART_ID"))) {
                top.put("checked", true);
            }
            this.getChildrenOnly(top, (String) top.get("DEPART_ID"),needCheckIds);
        }
        root.put("children", toplist);
        return root;
    }    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-28 上午10:27:43
     * @param parentType
     * @param parentId
     */
    public void getChildrenOnly(Map<String, Object> parentType, String parentId, Set<String> needCheckIds) {
        List<Map<String, Object>> children = departmentService.findByParentId(parentId);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("DEPART_ID"));
                child.put("name", child.get("DEPART_NAME"));
                if (needCheckIds.contains(child.get("DEPART_ID"))) {
                    child.put("checked", true);
                }
            }
        }
    }
}
