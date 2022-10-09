/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.BillRightService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 权利清单管理
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-12 上午9:57:36
 */
@Controller
@RequestMapping("/billRightController")
public class BillRightController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BillRightController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 引入billRightService
     */
    @Resource
    private BillRightService billRightService;
    /**
     * 引入departmentService
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 
     * 描述   跳转权利清单管理
     * @author Danto Huang
     * @created 2016-9-12 上午10:41:27
     * @param request
     * @return
     */
    @RequestMapping(params="goBillRightView")
    public ModelAndView goBillRightView(HttpServletRequest request){
        
        return new ModelAndView("wsbs/billRight/billRightView");
    }

    /**
     * 
     * 描述   目录清单列表
     * @author Danto Huang
     * @created 2016-9-12 上午11:14:34
     * @param request
     * @return
     */
    @RequestMapping(params="billCatalogData")
    public void billCatalogData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.TREE_SN", "ASC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.ITEM_CATALOGCODE", "asc");
        List<Map<String,Object>> list = billRightService.findBillCatalogByFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述   目录清单信息
     * @author Danto Huang
     * @created 2016-9-12 下午4:27:31
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="catalogInfo")
    public ModelAndView catalogInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> billCatalog = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            billCatalog = billRightService.getByJdbc(
                    "T_WSBS_RIGHTBILL_CATALOG", new String[] { "BILL_ID" },
                    new Object[] { entityId });
            String departId = (String) billCatalog.get("DEPART_ID");
            Map<String, Object> department = departmentService.getByJdbc(
                    "T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                    new Object[] { departId });
            billCatalog.put("DEPART_NAME",
                    (String) department.get("DEPART_NAME"));

            Map<String, Object> dic = dictionaryService.getByJdbc(
                    "T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE",
                            "DIC_CODE" }, new String[] { "ServiceItemKind",
                            billCatalog.get("ITEM_KIND").toString() });
            if (dic != null) {
                billCatalog.remove("ITEM_KIND");
                billCatalog.put("ITEM_KIND", (String) dic.get("DIC_NAME"));
            }
        }
        request.setAttribute("billCatalog", billCatalog);
        return new ModelAndView("wsbs/billRight/billCatalogInfo");
    }
    
    /**
     * 
     * 描述   目录清单保存
     * @author Danto Huang
     * @created 2016-9-13 上午8:50:50
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateCataLog")
    @ResponseBody
    public AjaxJson saveOrUpdateCataLog(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BILL_ID");
        String itemKind=request.getParameter("ITEM_KIND");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("STATUS", "1");
        }
        String recordId = billRightService.saveOrUpdate(variables, "T_WSBS_RIGHTBILL_CATALOG", entityId);

        if (StringUtils.isNotEmpty(entityId)) {
            Map<String, Object> catalogBill = billRightService.getByJdbc(
                    "T_WSBS_RIGHTBILL_CATALOG", new String[] { "BILL_ID" },
                    new Object[] { entityId });
            if(catalogBill.get("ITEM_CATALOGCODE")!=null){
                String catalogCode = catalogBill.get("ITEM_CATALOGCODE").toString();
                String catalogId= billRightService.getByJdbc(
                        "T_WSBS_SERVICEITEM_CATALOG", new String[] { "CATALOG_CODE" },
                        new Object[] { catalogCode }).get("CATALOG_ID").toString();
                Map<String, Object> catalog = new HashMap<String, Object>();
                catalog.put("CATALOG_NAME", variables.get("BILL_NAME"));
                catalog.put("DEPART_ID", variables.get("DEPART_ID"));
                catalog.put("FTA_FLAG", variables.get("FTA_FLAG"));
                catalog.put("SXXZ", itemKind);
                billRightService.saveOrUpdate(catalog, "T_WSBS_SERVICEITEM_CATALOG", catalogId);
            }
            List<Map<String,Object>> itemList = billRightService.findBillItemById(entityId);
            for (Map<String, Object> map : itemList) {
                map.put("DEPART_ID", variables.get("DEPART_ID"));
                billRightService.saveOrUpdate(map, "T_WSBS_RIGHTBILL_ITEM", (String)map.get("BILL_ID"));
                String itemCode=map.get("SERVICEITEM_CODE")==null?"":
                    map.get("SERVICEITEM_CODE").toString();
                if(StringUtils.isNotEmpty(itemCode)){
                    Map<String,Object> serviceItem=billRightService.getByJdbc("T_WSBS_SERVICEITEM",
                            new String[]{"ITEM_CODE"}, new Object[]{(String)map.get("SERVICEITEM_CODE")});
                    serviceItem.put("SXXZ", itemKind);
                    billRightService.saveOrUpdate(serviceItem, "T_WSBS_SERVICEITEM",(String)serviceItem.get("ITEM_ID"));
                }
            }
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 目录清单记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 目录清单记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述   删除目录清单
     * @author Danto Huang
     * @created 2016-9-13 上午9:14:52
     * @param request
     * @return
     */
    @RequestMapping(params="multiDelCatalog")
    @ResponseBody
    public AjaxJson multiDelCatalog(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String msg = billRightService.removeCatalog(selectColNames);
        if(msg==null){
            j.setMsg("删除成功");
        }else{
            j.setSuccess(false);
            j.setMsg(msg);
        }
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的清单记录", SysLogService.OPERATE_TYPE_DEL);
        return j;
    }
    
    /**
     * 
     * 描述   跳转事项清单管理
     * @author Danto Huang
     * @created 2016-9-13 上午9:38:13
     * @param request
     * @return
     */
    @RequestMapping(params="billItemManage")
    @SuppressWarnings("unchecked")
    public ModelAndView billItemManage(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> billCatalog = billRightService.getByJdbc(
                "T_WSBS_RIGHTBILL_CATALOG", new String[] { "BILL_ID" },
                new Object[] { entityId });
        request.setAttribute("billCatalog", billCatalog);
        return new ModelAndView("wsbs/billRight/billItemView");
    }
    
    /**
     * 
     * 描述   权利清单列表（事项）
     * @author Danto Huang
     * @created 2016-9-13 上午10:40:08
     * @param request
     * @param response
     */
    @RequestMapping(params="billItemData")
    public void billItemData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        String billCatalogId = request.getParameter("billCatalogId");
        filter.addFilter("Q_T.CATALOG_BILL_ID_=", billCatalogId);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.Serviceitem_Code", "asc");
        List<Map<String,Object>> list = billRightService.findBillItemByFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述   事项清单信息
     * @author Danto Huang
     * @created 2016-9-13 上午11:02:39
     * @param request
     * @return
     */
    @RequestMapping(params="billItemInfo")
    public ModelAndView billItemInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String departId = request.getParameter("departId");
        String catalogId = request.getParameter("catalogId");
        String isPublic = request.getParameter("isPublic");
        String fta = request.getParameter("fta");
        Map<String,Object> itemInfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId)) {
            itemInfo = billRightService.getByJdbc("T_WSBS_RIGHTBILL_ITEM",
                    new String[] { "BILL_ID" }, new Object[] { entityId });
        } else {
            itemInfo.put("DEPART_ID", departId);
            itemInfo.put("CATALOG_BILL_ID", catalogId);
            itemInfo.put("IS_PUBLIC", isPublic);
            itemInfo.put("FTA_FLAG", fta);
        }
        request.setAttribute("itemInfo", itemInfo);
        return new ModelAndView("wsbs/billRight/billItemInfo");
    }
    
    /**
     * 
     * 描述   事项清单信息保存
     * @author Danto Huang
     * @created 2016-9-13 上午11:19:27
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateItem")
    @ResponseBody
    public AjaxJson saveOrUpdateItem(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BILL_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("STATUS", "1");
        }
        String recordId = billRightService.saveOrUpdate(variables, "T_WSBS_RIGHTBILL_ITEM", entityId);
        String billCatalogId = request.getParameter("CATALOG_BILL_ID");
        billRightService.updateItemNum(billCatalogId);

        if (StringUtils.isNotEmpty(entityId)) {
            Map<String, Object> itemBill = billRightService.getByJdbc(
                    "T_WSBS_RIGHTBILL_ITEM", new String[] { "BILL_ID" },
                    new Object[] { entityId });
            if(itemBill.get("SERVICEITEM_CODE")!=null){
                String itemCode = itemBill.get("SERVICEITEM_CODE").toString();
                String itemId= billRightService.getByJdbc(
                        "T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                        new Object[] { itemCode }).get("ITEM_ID").toString();
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("ITEM_NAME", variables.get("BILL_NAME"));
                item.put("FTA_FLAG", variables.get("FTA_FLAG"));
                item.put("RZBSDTFS", variables.get("RZBSDTFS"));
                billRightService.saveOrUpdate(item, "T_WSBS_SERVICEITEM", itemId);
            }
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 事项清单记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 事项清单记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述   删除子项
     * @author Danto Huang
     * @created 2016-9-13 下午2:06:59
     * @param request
     * @return
     */
    @RequestMapping(params="multiDelItem")
    @ResponseBody
    public AjaxJson multiDelItem(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String msg = billRightService.removeItems(selectColNames);
        if(msg==null){
            j.setMsg("删除成功");
        }else{
            j.setSuccess(false);
            j.setMsg(msg);
        }
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的清单记录", SysLogService.OPERATE_TYPE_DEL);
        return j;
    }
    
    /**
     * 
     * 描述   目录清单选择器
     * @author Danto Huang
     * @created 2016-9-18 上午9:15:13
     * @param request
     * @return
     */
    @RequestMapping("/catalogSelector")
    public ModelAndView catalogSelector(HttpServletRequest request) {
        return new ModelAndView("wsbs/billRight/catalogSelector");
    }
    
    /**
     * 
     * 描述   获取未绑定事项目录的目录清单
     * @author Danto Huang
     * @created 2016-9-18 上午9:35:02
     * @param request
     */
    @RequestMapping(params="catalogForBind")
    public void catalogForBind(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.TREE_SN", "ASC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String,Object>> list = billRightService.findBillCatalogForBind(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述   事项清单选择器
     * @author Danto Huang
     * @created 2016-9-18 上午9:15:13
     * @param request
     * @return
     */
    @RequestMapping("/itemSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        return new ModelAndView("wsbs/billRight/itemSelector");
    }
    
    /**
     * 
     * 描述   获取未绑定事项清单
     * @author Danto Huang
     * @created 2016-9-18 上午9:35:02
     * @param request
     */
    @RequestMapping(params="itemForBind")
    public void itemForBind(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("D.TREE_SN", "ASC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String,Object>> list = billRightService.findBillItemForBind(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX前台权力清单请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/qzqdlist")
    public void qzqdlist(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("ITEM_NAME");
        String departId = request.getParameter("busTypeIds");
        String dicCodes = request.getParameter("dicCodes");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String[] SFZX= request.getParameterValues("SFZX");
        String isZx = "";
        if(null!=SFZX&&SFZX.length>0){
            isZx = SFZX[0];
        }
//        Map<String, Object> mapList = billRightService.findfrontQzqdList(page, rows, keyword,dicCodes, departId,isZx);
        Map<String, Object> mapList = billRightService.findfrontRzswmlList(page, rows, keyword,dicCodes, departId,isZx);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * 2018新版前台权力清单请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/rightlist")
    public void rightlist(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyWord");
        String departId = request.getParameter("busTypeIds");
        String dicCodes = request.getParameter("dicCodes");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String[] SFZX= request.getParameterValues("SFZX");
        String isZx = "";
        if(null!=SFZX&&SFZX.length>0){
            isZx = SFZX[0];
        }
        Map<String, Object> mapList = billRightService.findRightlList(page, rows, keyword,dicCodes, departId,isZx);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 草稿列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/jskzx")
    public void jskzx(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("ITEM_NAME");
        String departId = request.getParameter("busTypeIds");
        String dicCodes = request.getParameter("dicCodes");
        Map<String, Object> mapList = null;
        Map<String, Object> mapList2 = null;
//        mapList = billRightService.findfrontCount(keyword, dicCodes, departId, "1");
//        mapList2 = billRightService.findfrontCount(keyword, dicCodes, departId, "0");
        mapList = billRightService.findfrontCountNew(keyword, dicCodes, departId, "1");
        mapList2 = billRightService.findfrontCountNew(keyword, dicCodes, departId, "0");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(mapList);
        list.add(mapList2);
        JsonUtil.printJson(response, list);
    }
    /**
     * 权责清单页面跳转
     *
     * @param request
     * @return
     */
    @RequestMapping("/qzqdDetail")
    public ModelAndView bsznDetail(HttpServletRequest request) {
        String rightId = request.getParameter("rightId");
        Map<String,Object> rightItem=billRightService.getRightDetail(rightId);
        request.setAttribute("rightItem",rightItem);
        return new ModelAndView("website/qlqd/qzqdDetail");
    }
    
    /**
     * 
     * 
     * 加载权责清单事项自动补全数据
     * @param request
     * @param response
     */
    @RequestMapping("/loadQzqdBsSearch")
    public void loadAPPbsSearch(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = billRightService.findBillItemByList("","","");
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("BILL_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
