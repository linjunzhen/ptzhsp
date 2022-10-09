/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import net.evecom.platform.hd.controller.BusConsultController;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.DepartCatalogService;

/**
 * 描述 部门服务事项目录
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-13 下午4:20:36
 */
@Controller
@RequestMapping("/departCatalogController")
public class DepartCatalogController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DepartCatalogController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private DepartCatalogService departCatalogService;
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
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    
    /**
     * 
     * 描述   跳转服务事项目录管理
     * @author Danto Huang
     * @created 2016-9-13 下午4:22:34
     * @param request
     * @return
     */
    @RequestMapping(params="catalogView")
    public ModelAndView catalogView(HttpServletRequest request){
        return new ModelAndView("wsbs/departCatalog/departCatalogView");
    }

    /**
     * 描述:跳转标准化服务事项目录管理
     *
     * @author Madison You
     * @created 2021/2/22 9:24:00
     * @param
     * @return
     */
    @RequestMapping(params = "stdCatalogView")
    public ModelAndView stdCatalogView(HttpServletRequest request) {
        return new ModelAndView("wsbs/departCatalog/departStdCatalogView");
    }
    
    /**
     * 
     * 描述   事项目录列表
     * @author Danto Huang
     * @created 2016-9-18 上午8:26:30
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.TREE_SN", "asc");
        filter.addSorted("D1.TREE_SN", "asc");
        filter.addSorted("SC.C_SN", "desc");
        List<Map<String, Object>> list = departCatalogService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:标准化事项目录列表
     *
     * @author Madison You
     * @created 2021/2/18 9:35:00
     * @param
     * @return
     */
    @RequestMapping(params = "stdDatagrid")
    public void stdDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = departCatalogService.findStdBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述   跳转目录信息
     * @author Danto Huang
     * @created 2016-9-18 上午8:28:02
     * @param request
     * @return
     */
    @RequestMapping(params="catalogInfo")
    public ModelAndView catalogInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> catalog = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined") && !entityId.equals("0")) {
            catalog = departCatalogService.getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_ID" },
                    new Object[] { entityId });
            String departId = (String) catalog.get("DEPART_ID");
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
                catalog.put("SXXZC", (String) dictionart.get("DIC_NAME"));
            }
        }
        request.setAttribute("catalog", catalog);
        return new ModelAndView("wsbs/departCatalog/departCatalogInfo");
        
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
        String billId = request.getParameter("BILL_ID");
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
        String recordId = departCatalogService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_CATALOG", entityId);
        if(StringUtils.isNotEmpty(billId)){
            Map<String,Object> billCatalog = new HashMap<String, Object>();
            billCatalog.put("ITEM_CATALOGCODE", variables.get("CATALOG_CODE"));
            departCatalogService.saveOrUpdate(billCatalog, "T_WSBS_RIGHTBILL_CATALOG", billId);
        }
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 事项目录记录", SysLogService.OPERATE_TYPE_EDIT);
            String catalogCode = departCatalogService
                    .getByJdbc("T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_ID" }, new Object[] { entityId })
                    .get("CATALOG_CODE").toString();
            departCatalogService.updateSxxzbyCatalog(catalogCode,sxxz);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 事项目录记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-12 下午6:27:52
     * @param request
     * @return
     */
    @RequestMapping(params="multiDelCatalog")
    @ResponseBody
    public AjaxJson multiDelCatalog(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        boolean rst = departCatalogService.removeCascade(selectColNames);
        if(rst){
            sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 服务事项记录", SysLogService.OPERATE_TYPE_DEL);
            j.setMsg("删除成功");
            j.setSuccess(true);
        }else{
            j.setMsg("该目录下存在服务事项，请先删除服务事项");
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016年10月20日 下午4:27:31
     * @param request
     * @param response
     */
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
        List<Map<String, Object>> toplist = departCatalogService.findBySqlFilter(filter);
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("CATALOG_CODE"));
            top.put("name", top.get("CATALOG_NAME"));
            //top.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
        }
        root.put("children", toplist);
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    目录划转
     * @author Danto Huang
     * @created 2018年8月20日 下午3:56:46
     * @param request
     * @return
     */
    @RequestMapping(params="move")
    @ResponseBody
    public AjaxJson move(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String departId = request.getParameter("departId");
        String catalogIds = request.getParameter("catalogIds");
        departCatalogService.moveCatalog(departId, catalogIds);
        j.setMsg("划转成功，请对划转的目录重新选择所属子部门");
        return j;
    }
    
    /**
     * 
     * 描述    启用/禁用
     * @author Danto Huang
     * @created 2018年8月20日 下午5:37:30
     * @param request
     * @return
     */
    @RequestMapping(params="isUse")
    @ResponseBody
    public AjaxJson isUse(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_USE", statu);
        for(int i=0;i<entityId.length;i++){
            departCatalogService.saveOrUpdate(variable, "T_WSBS_SERVICEITEM_CATALOG", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月21日 下午3:43:50
     * @param request
     * @return
     */
    @RequestMapping("/catalogSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        return new ModelAndView("wsbs/departCatalog/catalogSelector");
    }

    /**
     * 描述:标准化目录选择器
     *
     * @author Madison You
     * @created 2021/2/18 9:31:00
     * @param
     * @return
     */
    @RequestMapping("/stdCatalogSelector")
    public ModelAndView stdCatalogSelector(HttpServletRequest request) {
        return new ModelAndView("wsbs/departCatalog/stdCatalogSelector");
    }

    /**
     * 描述:查看目录事项页面
     *
     * @author Madison You
     * @created 2020/4/26 11:39:00
     * @param
     * @return
     */
    @RequestMapping(params = "checkServiceItemView")
    public ModelAndView checkServiceItemView(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        List<Map<String,Object>> itemList = departCatalogService.getServiceItemByCatalog(entityId);
        request.setAttribute("itemList", itemList);
        return new ModelAndView("wsbs/departCatalog/checkServiceItemView");
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/22 9:53:00
     * @param
     * @return
     */
    @RequestMapping(params = "checkStdServiceItemView")
    public ModelAndView checkStdServiceItemView(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        List<Map<String,Object>> itemList = departCatalogService.getStdServiceItemByCatalog(entityId);
        request.setAttribute("itemList", itemList);
        return new ModelAndView("wsbs/departCatalog/checkStdServiceItemView");
    }
}
