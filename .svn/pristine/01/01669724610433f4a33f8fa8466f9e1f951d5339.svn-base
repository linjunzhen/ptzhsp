/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.evecom.core.util.GenPlatReqUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.IndustryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 商事-行业管理controller
 * 
 * @author Allin Lin
 * @created 2020年11月18日 上午10:26:54
 */
@Controller
@RequestMapping("/industryController")
public class IndustryController extends BaseController {

    /**
     * 引入service
     */
    @Resource
    private IndustryService industryService;

    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 
     * 描述 页面跳转(行业管理)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "industryView")
    public ModelAndView industryView(HttpServletRequest request) {
        return new ModelAndView("commercial/industry/IndustryView");
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
        List<Map<String, Object>> list = industryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     *
     * 显示主行业信息列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> industry = industryService.getByJdbc("T_COMMERCIAL_INDUSTRY",
                    new String[] { "INDUSTRY_ID" }, new Object[] { entityId });
            request.setAttribute("industry", industry);
        }
        return new ModelAndView("commercial/industry/IndustryInfo");
    }

    /**
     *
     * 增加或者修改主行业信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateMainIndus")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("INDUSTRY_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        boolean isSample = false;// 行业名称是否重复
        List<Map<String, Object>> record = industryService.getAllByJdbc("T_COMMERCIAL_INDUSTRY",
                new String[] { "PARENT_ID", "INDUSTRY_NAME" },
                new Object[] { "0", variables.get("INDUSTRY_NAME") });
        if (StringUtils.isEmpty(entityId)) {// 增加
            variables.put("PARENT_ID", "0");// 主行业父类别ID默认为0
            if (record.size() > 0) {
                isSample = true;
            }
        } else {// 修改
            Map<String, Object> industry = industryService.getByJdbc("T_COMMERCIAL_INDUSTRY",
                    new String[] { "INDUSTRY_ID"},  new Object[] {entityId});
            if(!StringUtils.equalsIgnoreCase(StringUtil.getString(variables.get("INDUSTRY_NAME")),
                    StringUtil.getString(industry.get("INDUSTRY_NAME")))){
                if (record.size() > 0) {
                    isSample = true;
                }
            }
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (isSample) {
            j.setSuccess(false);
            j.setMsg("此行业名称已存在！");
        } else {
            industryService.saveOrUpdate(variables, "T_COMMERCIAL_INDUSTRY", entityId);
            j.setMsg("保存成功！");
        }
        return j;
    }

    /**
     * 
     * 删除主行业信息数据
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
        industryService.removeIndustryCascade(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 商事-主行业管理表记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     *
     * 跳转至子行业信息列表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "childIndustryView")
    public ModelAndView childIndustryView(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        request.setAttribute("entityId", entityId);
        return new ModelAndView("commercial/industry/ChildIndustryView");
    }
    /**
     *
     * 跳转至子行业信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectChildIndustryView")
    public ModelAndView selectChildIndustryView(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        request.setAttribute("entityId", entityId);
        return new ModelAndView("commercial/industry/selectChildIndustryView");
    }
    /**
     *
     * 跳转至主行业信息列表页面(前台）
     *
     * @param request
     * @return
     */
    @RequestMapping(params="selectMainBussinessView")
    public ModelAndView selectMainBussinessView(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        request.setAttribute("entityId", entityId);
        return new ModelAndView("commercial/industry/selectMainBussinessView");
    }
    /**
     * 描述 获取指定主行业ID下的子行业信息
     * 
     * @author Allin Lin
     * @created 2020年11月18日 下午2:42:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "childIndustryDatagrid")
    public void childIndustryDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = industryService.findChildIndustryBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述 获取指定主行业ID下的子行业信息(前台)
     *
     * @author Allin Lin
     * @created 2020年11月18日 下午2:42:33
     * @param request
     * @param response
     */
    @RequestMapping( "/childIndustryForWebsite")
    public void childIndustryForWebsite(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = industryService.findChildIndustryBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述 获取指定子行业ID下的主行业信息(前台)
     *
     * @author Allin Lin
     * @created 2020年11月18日 下午2:42:33
     * @param request
     * @param response
     */
    @RequestMapping( "/mainBussinessForWebsite")
    public void mainBussinessForWebsite(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = industryService.findBussinessBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述 显示子行业信息
     * 
     * @author Allin Lin
     * @created 2020年11月18日 下午3:07:37
     * @param request
     * @return
     */
    @RequestMapping(params = "childIndustryinfo")
    public ModelAndView childIndustryinfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String parentId = request.getParameter("parentId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> childIndustry = industryService.getByJdbc("T_COMMERCIAL_INDUSTRY",
                    new String[] { "INDUSTRY_ID" }, new Object[] { entityId });
            request.setAttribute("childIndustry", childIndustry);
        }
        request.setAttribute("parentId", parentId);
        return new ModelAndView("commercial/industry/ChildIndustryInfo");
    }

    /**
     * 
     * 描述 增加或保存子行业信息
     * 
     * @author Allin Lin
     * @created 2020年11月18日 下午3:21:37
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateChildIndus")
    @ResponseBody
    public AjaxJson saveOrUpdateChildIndus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("INDUSTRY_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        List<Map<String, Object>> record = industryService.getAllByJdbc("T_COMMERCIAL_INDUSTRY",
                new String[] { "PARENT_ID", "INDUSTRY_NAME" },
                new Object[] { variables.get("PARENT_ID"), variables.get("INDUSTRY_NAME")});
        boolean isSample = false;// 同一主行业下子行业名称是否重复
        if (StringUtils.isEmpty(entityId)) {// 增加
            if (record.size() > 0) {
                isSample = true;
            }
        } else {// 修改
            Map<String, Object> industry = industryService.getByJdbc("T_COMMERCIAL_INDUSTRY",
                    new String[] { "INDUSTRY_ID"},  new Object[] {entityId});
            if(!StringUtils.equalsIgnoreCase(StringUtil.getString(variables.get("INDUSTRY_NAME")),
                    StringUtil.getString(industry.get("INDUSTRY_NAME")))){
                if (record.size() > 0) {
                    isSample = true;
                }
            }
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (isSample) {
            j.setSuccess(false);
            j.setMsg("此行业名称已存在！");
        } else {
            industryService.saveOrUpdate(variables, "T_COMMERCIAL_INDUSTRY", entityId);
            j.setMsg("保存成功！");
        }
        return j;
    }

    /**
     * 
     * 描述 删除子行业信息
     * 
     * @author Allin Lin
     * @created 2020年11月18日 下午3:33:07
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDelChildIndustry")
    @ResponseBody
    public AjaxJson multiDelChildIndustry(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        industryService.remove("T_COMMERCIAL_INDUSTRY", "INDUSTRY_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 商事-子行业管理表记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 描述 跳转到选择器信息界面
     * 
     * @author Allin Lin
     * @created 2020年11月18日 下午11:11:56
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        String comtypeId = request.getParameter("comtypeId");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", comtypeId);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("commercial/industry/ComtypeSelector");
    }

    /**
     * 企业类型树形数据源加载
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取是否需要显示根节点
        String isShowRoot = request.getParameter("isShowRoot");
        String rootName = request.getParameter("rootName");
        // 获取需要回显的IDS
        String checkIds = request.getParameter("needCheckIds");
        Set<String> needCheckIds = new HashSet<String>();
        if (StringUtils.isNotEmpty(checkIds)) {
            String[] checkIdArray = checkIds.split(",");
            for (String checkId : checkIdArray) {
                needCheckIds.add(checkId);
            }
        }
        String json = "";
        Map<String, Object> rootMap = null;
        if (isShowRoot.equals("true")) {
            rootMap = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(rootName)) {
                rootMap.put("name", rootName);
            }
            rootMap.put("TREE_LEVEL", 1);
            rootMap.put("id", "0");
            rootMap.put("PARENT_ID", "-1");
            rootMap.put("open", true);
            // rootMap.put("icon",
            // "plug-in/easyui-1.4/themes/icons/monitor.png");
            if (needCheckIds.size() > 0) {
                rootMap.put("checked", true);
            }
        }
        List<Map> compTypeList = GenPlatReqUtil.findDatas("pt_comptype_list", null);// 从支撑资源平台获取企业类型信息列表
        for (Map<String, Object> map : compTypeList) {
            // 获取ID值
            String id = StringUtil.getString(map.get("COMTYPE_ID"));
            // 获取NAME值
            String name = StringUtil.getString(map.get("COMTYPE_NAME"));
            map.put("id", id);
            map.put("name", name);
            // map.put("icon","plug-in/easyui-1.4/themes/icons/folder_table.png");
            if (needCheckIds.contains(id)) {
                map.put("checked", true);
            }
            this.getChildren(map,needCheckIds);
        }
        if (isShowRoot.equals("true")) {
            rootMap.put("children", compTypeList);
            json = JSON.toJSONString(rootMap);
        } else if (isShowRoot.equals("false")) {
            json = JSON.toJSONString(compTypeList);
        }
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    获取子节点信息
     * @author Allin Lin
     * @created 2020年11月19日 上午10:10:58
     * @param childrenMap
     */
    public void getChildren(Map<String, Object> childrenMap,Set<String> needCheckIds) {
        if (StringUtils.isNotEmpty(StringUtil.getString(childrenMap.get("children")))) {// 存在子节点
            List<Map<String, Object>> children = (List<Map<String, Object>>) childrenMap.get("children");
            for (Map<String, Object> child : children) {
                String id = StringUtil.getString(child.get("COMTYPE_ID"));
                String name = StringUtil.getString(child.get("COMTYPE_NAME"));
                child.put("id", id);
                child.put("name", name);
                // child.put("icon","plug-in/easyui-1.4/themes/icons/folder_table.png");
                if (needCheckIds.contains(id)) {
                    child.put("checked", true);
                }
                this.getChildren(child,needCheckIds);
            }
        }
    }
}
