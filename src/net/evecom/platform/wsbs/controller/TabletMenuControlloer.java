/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.hd.controller.BusConsultController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.wsbs.service.TabletMenuService;

/**
 * 描述 权力清单信息维护
 * @author Sundy Sun
 * @version v1.0
 */
@Controller
@RequestMapping("/tabletMenuController")
public class TabletMenuControlloer extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusConsultController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private TabletMenuService tabletMenuService;
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
     * 引入dataSourceService
     */
    @Resource
    private DataSourceService dataSourceService;
    /**
     * 视图页面
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/tabletmenu/deptlist");
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
        List<Map<String, Object>> list = tabletMenuService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到办事部门信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "deptInfo")
    public ModelAndView deptInfo(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String tabletId = request.getParameter("tabletId");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        if (StringUtils.isNotEmpty(tabletId) && !tabletId.equals("undefined")) {
            request.setAttribute("tabletId", tabletId);
        }
        return new ModelAndView("wsbs/tabletmenu/workDeptSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "deptdatagrid")
    public void deptdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("U.TREE_SN", "desc");
        List<Map<String, Object>> list = tabletMenuService.findBSDeptBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "deptselected")
    public void deptselected(HttpServletRequest request,
            HttpServletResponse response) {
        //String tabletId = request.getParameter("tabletId");
        //String userAccounts = request.getParameter("userAccounts");
        List<Map<String,Object>> list = tabletMenuService.findWorkOfficeById();
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * 
     * 保存办事部门
     * @param request
     * @return
     */
    @RequestMapping(params = "saveWorkDept")
    @ResponseBody
    public AjaxJson saveWorkDept(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String treeSns = request.getParameter("treeSns");
        String departIds = request.getParameter("departIds");
        String departNames = request.getParameter("departNames");
        String departCodes = request.getParameter("departCodes");
        tabletMenuService.saveWorkDept(departIds.split(","),departNames.split(",")
                ,departCodes.split(","), treeSns.split(","));
        j.setMsg("保存成功");
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
        //String departName = request.getParameter("DEPART_NAME");
        Map<String, Object> tablet = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined") && !entityId.equals("0")) {
            tablet = tabletMenuService.getByJdbc("T_WSBS_TABLE_MENU", new String[] { "TABLET_ID" },
                    new Object[] { entityId });
            departId = (String) tablet.get("DEPART_ID");
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { departId });
            tablet.put("DEPART_ID", departId);
            tablet.put("DEPART_NAME", (String) department.get("DEPART_NAME"));
            
            Map<String, Object> dictionart = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] {
                "TYPE_CODE", "DIC_CODE" }, new String[] { "ServiceItemKind", tablet.get("ITEM_KIND").toString() });
            if (dictionart != null) {
                tablet.put("ITEM_KIND", (String) dictionart.get("DIC_NAME"));
            }
        }
        request.setAttribute("tablet", tablet);
        return new ModelAndView("wsbs/tabletmenu/tabletInfo");
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
        String entityId = request.getParameter("TABLET_ID");
        String departId = request.getParameter("DEPART_ID");
        //String sxxz = request.getParameter("ITEM_KIND");
        String catalogCode = request.getParameter("ITEM_CATALOGCODE");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("STATUS", "1");
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { departId });
//            String departsxxzcode = department.get("DEPART_CODE") + sxxz;
//            variables.put("DEPART_SXXZ_CODE", departsxxzcode);
//            String numcode = catalogService.getMaxNumCode(departsxxzcode);
//            variables.put("NUM_CODE", numcode);
//            variables.put("CATALOG_CODE", departsxxzcode + numcode);
        }
        String recordId = tabletMenuService.saveOrUpdate(variables, "T_WSBS_TABLE_MENU", entityId);

        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 清单记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 清单记录", SysLogService.OPERATE_TYPE_ADD);
        }
       /******保存清单对应服务事项******/
        String itemIds = request.getParameter("ITEM_IDS");
        List<Map<String,Object>> list;
        list=tabletMenuService.queryItemsByTablet(itemIds);
        String[] ids=new String[list.size()];
        String[] names=new String[list.size()];
        String[] codes=new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> map=list.get(i);
            String str=(String) map.get("ITEM_ID");
            ids[i]=str;
            names[i]=(String) map.get("ITEM_NAME");
            codes[i]=(String) map.get("ITEM_CODE");
        }
        tabletMenuService.saveMenuItem(ids,names,codes, recordId,catalogCode);
        
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
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        //tabletMenuService.removeCascade(selectColNames.split(","));
        tabletMenuService.removeCascade(selectColNames.split(","));// .remove("T_WSBS_TABLE_MENU",
                                                                   // "tablet_id",
                                                                   // selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的清单记录", SysLogService.OPERATE_TYPE_DEL);
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
    @RequestMapping(params = "kxdatagrid")
    public void kxdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        String tabletId=request.getParameter("tabletId");
        String itemKind = request.getParameter("itemKind");
        String departId = request.getParameter("departId");
        String catalogCode = request.getParameter("Q_T.CATALOG_CODE_EQ");
        Map<String, Object> tablet;
        if (StringUtils.isNotEmpty(tabletId) && !tabletId.equals("undefined") && !tabletId.equals("0")) {
            tablet = tabletMenuService.getByJdbc("T_WSBS_TABLE_MENU", new String[] { "TABLET_ID" },
                    new Object[] {tabletId});
            filter.addFilter("Q_T.SXXZ_EQ", (String) tablet.get("ITEM_KIND"));
        }else if(StringUtils.isNotEmpty(itemKind) && !itemKind.equals("undefined") && !itemKind.equals("0")) {
            filter.addFilter("Q_T.SXXZ_EQ", itemKind);
        }
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 超管进行数据级别权限控制
        if (curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            if(!StringUtil.isBlank(departId)){
                Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[] { "DEPART_ID" }, new Object[] { departId });
                filter.addFilter("Q_T.SSBMBM_EQ", (String) department.get("DEPART_CODE"));
            }
        }
        if (StringUtils.isNotEmpty(catalogCode) && !catalogCode.equals("undefined")) {
            filter.addFilter("Q_T.FWSXZT_EQ","1");
            List<Map<String, Object>> list = serviceItemService.findBySqlFilter(filter);
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
        }else{
            filter.addFilter("Q_T.FWSXZT_EQ","1");
            //List<Map<String, Object>> list = serviceItemService.findBySqlFilter(filter);
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), null, null, JsonUtil.EXCLUDE, response);
        }
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sxselector")
    public ModelAndView sxselector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String tabletId = request.getParameter("tabletId");
        String itemKind = request.getParameter("itemKind");
        String departId = request.getParameter("departId");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        if (StringUtils.isNotEmpty(tabletId) && !tabletId.equals("undefined")) {
            request.setAttribute("tabletId", tabletId);
        }
        request.setAttribute("itemKind", itemKind);
        request.setAttribute("departId", departId);
        return new ModelAndView("wsbs/tabletmenu/serviceItemSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String tabletId = request.getParameter("tabletId");
        //String userAccounts = request.getParameter("userAccounts");
        String catalogCode = request.getParameter("Q_T.ITEM_CATALOGCODE_EQ");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(tabletId)){
            list = tabletMenuService.findMenuById(tabletId,catalogCode);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * 
     * 选择服务事项
     * @param request
     * @return
     */
    @RequestMapping(params = "saveMenuItems")
    @ResponseBody
    public AjaxJson saveMenuItems(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String tabletId = request.getParameter("tabletId");
        String itemIds = request.getParameter("itemIds");
        String itemNames = request.getParameter("itemNames");
        String itemCodes = request.getParameter("itemCodes");
        String catalogCode = request.getParameter("ITEM_CATALOGCODE");
        tabletMenuService.saveMenuItem(itemIds.split(","),
                itemNames.split(","), itemCodes.split(","), tabletId,
                catalogCode);
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 通用树形数据源
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取表名称
        String tableName = request.getParameter("tableName").toUpperCase();
        // 获取代表ID、NAME的名称
        String[] idAndNameCol = request.getParameter("idAndNameCol").split(",");
        // 获取需要开始读取的根节点ID
        String rootParentId = request.getParameter("rootParentId");
        // 判断是否是字典类别树
        String dicTypeCode = request.getParameter("dicTypeCode");
        if (StringUtils.isNotEmpty(dicTypeCode)) {
            Map<String, Object> dicType = dataSourceService.
                    getByJdbc("T_MSJW_SYSTEM_DICTYPE", new String[] { "TYPE_CODE" },
                    new Object[] { dicTypeCode });
            String typeId = (String) dicType.get("TYPE_ID");
            rootParentId = typeId;
        }
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

            if (rootParentId.equals("0")) {
                rootMap = new HashMap<String, Object>();
                if (StringUtils.isNotEmpty(rootName)) {
                    rootMap.put("name", rootName);
                } else {
                    rootMap.put("name", "所有类别");
                }
                rootMap.put("TREE_LEVEL", 1);
            } else {
                rootMap = this.dataSourceService.getByJdbc(tableName, new String[] { idAndNameCol[0] },
                        new Object[] { rootParentId });
                rootMap.put("name", rootMap.get(idAndNameCol[1]));
            }
            rootMap.put("id", rootParentId);
            rootMap.put("PARENT_ID", "-1");
            rootMap.put("open", true);
            //rootMap.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
            if (needCheckIds.size() > 0) {
                rootMap.put("checked", true);
            }
        }
        String cols = request.getParameter("idAndNameCol") + "," + request.getParameter("targetCols");
        List<Map<String, Object>> allList = tabletMenuService.findTree(request, tableName, rootParentId, cols);
        List<Map<String, Object>> topList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : allList) {
            // 获取ID值
            String id = map.get(idAndNameCol[0]).toString();
            // 获取NAME值
            String name = (String) map.get(idAndNameCol[1]);
            // 获取父亲ID
            String parentId = map.get("PARENT_ID").toString();
            if (parentId.equals(rootParentId)) {
                map.put("id", id);
                map.put("name", name);
                //map.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                if (needCheckIds.contains(id)) {
                    map.put("checked", true);
                }
                this.getChildren(allList, id, map, idAndNameCol, needCheckIds);
                topList.add(map);
            }
        }
        if (isShowRoot.equals("true")) {
            rootMap.put("children", topList);
            json = JSON.toJSONString(rootMap);
        } else if (isShowRoot.equals("false")) {
            json = JSON.toJSONString(topList);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    
    /**
     * easyui AJAX前台权力清单请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/qlqdnewlist")
    public void qlqdnewlist(HttpServletRequest request, HttpServletResponse response) {
        String sxxz = request.getParameter("SXXZ");
        String busTypeIds = request.getParameter("busTypeIds");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = tabletMenuService.findfrontQlqdList(page, rows,sxxz,busTypeIds);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    @RequestMapping(params = "catalogtree")
    public void catalogtree(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "服务目录树");
        root.put("open", true);
        //root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        filter.setPagingBean(null);
        String kind=request.getParameter("itemKind");
        if(StringUtils.isNotEmpty(kind)){
            filter.addFilter("Q_SC.SXXZ_EQ", kind);
        }
        // 获取topType
        List<Map<String, Object>> toplist = tabletMenuService.findCatalogBySqlFilter(filter);
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
