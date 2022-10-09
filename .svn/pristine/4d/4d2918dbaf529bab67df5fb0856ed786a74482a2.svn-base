/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

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
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.SerialNumberService;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
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
 * 描述 事项目录Controller
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/catalogController")
public class CatalogController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CatalogController.class);
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

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
        catalogService.removeCascade(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 事项目录记录", SysLogService.OPERATE_TYPE_DEL);
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
        String departId = request.getParameter("DEPART_ID");
        String departName = request.getParameter("DEPART_NAME");
        Map<String, Object> catalog = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined") && !entityId.equals("0")) {
            catalog = catalogService.getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_ID" },
                    new Object[] { entityId });
            departId = (String) catalog.get("DEPART_ID");
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { departId });
            catalog.put("DEPART_ID", departId);
            catalog.put("DEPART_NAME", (String) department.get("DEPART_NAME"));
            String cdepartId = (String) catalog.get("CHILD_DEPART_ID");
            if(StringUtils.isNotEmpty(cdepartId)){
                Map<String, Object> cdepartment = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[] { "DEPART_ID" }, new Object[] { cdepartId });
                catalog.put("CHILD_DEPART_ID", cdepartId);
                catalog.put("CHILD_DEPART_NAME", (String) cdepartment.get("DEPART_NAME"));
            }else{
                catalog.put("CHILD_DEPART_ID", "");
                catalog.put("CHILD_DEPART_NAME", "");
            }
            Map<String, Object> dictionart = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] {
                "TYPE_CODE", "DIC_CODE" }, new String[] { "ServiceItemXz", catalog.get("SXXZ").toString() });
            if (dictionart != null) {
                catalog.put("SXXZ", (String) dictionart.get("DIC_NAME"));
            }
        } else {
            catalog.put("DEPART_ID", departId);
            catalog.put("DEPART_NAME", departName);
        }
        request.setAttribute("catalog", catalog);
        return new ModelAndView("wsbs/catalog/info");
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
        String entityId = request.getParameter("CATALOG_ID");
        String departId = request.getParameter("DEPART_ID");
        String sxxz = request.getParameter("SXXZ");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            int maxSn = catalogService.getMaxSn();
            variables.put("C_SN", maxSn + 1);
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { departId });
            String departsxxzcode = department.get("DEPART_CODE") + sxxz;
            variables.put("DEPART_SXXZ_CODE", departsxxzcode);
            String numcode = catalogService.getMaxNumCode(departsxxzcode);
            variables.put("NUM_CODE", numcode);
            variables.put("CATALOG_CODE", departsxxzcode + numcode);
        }
        String recordId = catalogService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_CATALOG", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 事项目录记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 事项目录记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/catalog/CatalogView");
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
        filter.addSorted("SC.CATALOG_NAME", "asc");
        //filter.addSorted("SC.C_SN", "desc");
        //filter.addSorted("SC.CREATE_TIME", "desc");
        List<Map<String, Object>> list = catalogService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 方法updateSn
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "updateSn")
    @ResponseBody
    public AjaxJson updateSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] catalogIds = request.getParameterValues("catalogIds[]");
        this.catalogService.updateSn(catalogIds);
        j.setMsg("排序成功");
        return j;
    }

    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String catalogCode = request.getParameter("catalogCode");
        String catalogName = request.getParameter("catalogName");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        if (StringUtils.isNotEmpty(catalogCode) && !catalogCode.equals("undefined")) {
            request.setAttribute("catalogCode", catalogCode);
            request.setAttribute("catalogName", catalogName);
        }
        return new ModelAndView("wsbs/catalog/CatalogSelector");
    }

    @RequestMapping(params = "tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "服务目录树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        filter.setPagingBean(null);
        // 获取topType
        List<Map<String, Object>> toplist = catalogService.findBySqlFilter(filter);
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("CATALOG_CODE"));
            top.put("name", top.get("CATALOG_NAME"));
            //top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
}
