/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.BusScopeService;
import net.evecom.platform.commercial.service.IndustryService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述  商事-经营范围管理Controller
 * @author Allin Lin
 * @created 2020年11月19日 下午2:55:33
 */
@Controller
@RequestMapping("/busScopeController")
public class BusScopeController extends BaseController{
    
    /**
     * 引入service
     */
    @Resource
    private BusScopeService busScopeService;
    
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
     * 描述 页面跳转(经营范围管理)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "busScopeView")
    public ModelAndView busScopeView(HttpServletRequest request) {
        return new ModelAndView("commercial/busscope/busScopeView");
    }
    
    /**
     * easyui AJAX请求经营范围列表数据
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
        List<Map<String, Object>> list = busScopeService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
    *
    * 显示经营范围信息界面
    * 
    * @param request
    * @return
    */
   @RequestMapping(params = "info")
   public ModelAndView info(HttpServletRequest request) {
       String entityId = request.getParameter("entityId");
       if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
           Map<String, Object> busscope = busScopeService.getByJdbc("T_WSBS_BUSSCOPE",
                   new String[] { "ID" }, new Object[] { entityId });
           request.setAttribute("busscope", busscope);
       }
       return new ModelAndView("commercial/busscope/busScopeInfo");
   }
    
   /**
    * 跳转到子行业选择器页面
    * 
    * @param request
    * @return
    */
   @RequestMapping(params = "selector")
   public ModelAndView selector(HttpServletRequest request) {
       String allowCount= request.getParameter("allowCount");
       String childIndustryIds = request.getParameter("childIndustryIds");
       request.setAttribute("allowCount", Integer.parseInt(allowCount));
       if(StringUtils.isNotEmpty(childIndustryIds)&&!childIndustryIds.equals("undefined")){
           request.setAttribute("childIndustryIds", childIndustryIds);
       }
       return new ModelAndView("commercial/busscope/childIndustrySelector");
   }
   
   /**
    * 描述     显示主行业树
    * @author Allin Lin
    * @created 2020年11月20日 下午2:59:43
    * @param request
    * @param response
    */
   @RequestMapping(params = "industryTree")
   @ResponseBody
   public void industryTree(HttpServletRequest request, HttpServletResponse response) {
       Map<String, Object> rootMap = new HashMap<String, Object>();
       rootMap.put("id", "0");
       rootMap.put("name", "主行业列表");
       rootMap.put("open", true);
       //rootMap.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
       SqlFilter filter = new SqlFilter(request);
       filter.addSorted("T.CREATE_TIME", "desc");
       filter.setPagingBean(new PagingBean(0,99999));
       List<Map<String, Object>> children = industryService.findBySqlFilter(filter);
       for (Map<String, Object> child : children) {
           child.put("id", (String) child.get("INDUSTRY_ID"));
           child.put("name", (String) child.get("INDUSTRY_NAME"));
           //child.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
       }
       rootMap.put("children", children);
       String json = JSON.toJSONString(rootMap);
       this.setJsonString(json, response);
   }
   
   
   /**
    * 描述 获取所有子行业信息
    * 
    * @author Allin Lin
    * @created 2020年11月18日 下午2:42:33
    * @param request
    * @param response
    */
   @RequestMapping(params = "childIndusDatagrid")
   public void childIndusDatagrid(HttpServletRequest request, HttpServletResponse response) {
       SqlFilter filter = new SqlFilter(request);
       filter.addSorted("T.CREATE_TIME", "desc");
       String parentId = filter.getRequest().getParameter("PARENT_ID");//父行业ID
       if(StringUtils.isNotEmpty(parentId) && !"0".equals(parentId)){
           filter.addFilter("Q_T.PARENT_ID_=",parentId);
       }else{
           filter.addFilter("Q_T.PARENT_ID_!=","0");
       }
       List<Map<String, Object>> list = industryService.findChildIndustryBySqlFilter(filter);
       this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
   }
   
   
   /**
    * 描述    获取已选择子行业列表信息
    * @author Allin Lin
    * @created 2020年11月21日 下午3:22:11
    * @param request
    * @param response
    */
   @RequestMapping(params = "selectedIndustry")
   public void selected(HttpServletRequest request,
           HttpServletResponse response) {
       String childIndustryIds = request.getParameter("childIndustryIds");
       List<Map<String,Object>> list = null;
       if(StringUtils.isNotEmpty(childIndustryIds)){
           list = busScopeService.findBychildIndustryIds(childIndustryIds);
       }
       if(list!=null){
           this.setListToJsonString(list.size(), list,
                   null, JsonUtil.EXCLUDE, response);
       }
   }
   
   /**
    * 增加或者修改经营范围信息
    * @param request
    * @return
    */
   @RequestMapping(params = "saveOrUpdate")
   @ResponseBody
   public AjaxJson saveOrUpdate(HttpServletRequest request) {
       AjaxJson j = new AjaxJson();
       String entityId = request.getParameter("ID");
       Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
       String recordId = busScopeService.saveOrUpdate(variables, "T_WSBS_BUSSCOPE", entityId);
       if(StringUtils.isNotEmpty(entityId)){
           sysLogService.saveLog("修改了ID为["+entityId+"]的商事-经营范围记录",SysLogService.OPERATE_TYPE_EDIT);
       }else{
           sysLogService.saveLog("新增了ID为["+recordId+"]的商事-经营范围记录",SysLogService.OPERATE_TYPE_ADD);
       }
       j.setMsg("保存成功");
       return j;
   }

    /**
     * 跳转到导入经营范围页面
     * @param request impIndustryView
     * @return
     */
    @RequestMapping(params="impIndustryView")
    public ModelAndView uploadInfo(HttpServletRequest request){
        return new ModelAndView("commercial/busscope/impIndustryView");
    }
   /**
    * 描述    删除经营范围信息
    * @author Allin Lin
    * @created 2020年11月21日 下午4:09:08
    * @param request
    * @return
    */
   @RequestMapping(params = "multiDel")
   @ResponseBody
   public AjaxJson multiDel(HttpServletRequest request) {
       AjaxJson j = new AjaxJson();
       String selectColNames = request.getParameter("selectColNames");
       busScopeService.remove("T_WSBS_BUSSCOPE", "ID", selectColNames.split(","));
       sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 商事-经营范围管理表记录", SysLogService.OPERATE_TYPE_DEL);
       j.setMsg("删除成功");
       return j;
   }

    /**
     * 获取行业类别2
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findIndustryCategory")
    @ResponseBody
    public List<Map<String, Object>> findIndustryCategory(HttpServletRequest request, HttpServletResponse response) {
        String parentId=request.getParameter("parentId");
        if(StringUtils.isEmpty(parentId)){
            parentId="0";
        }
        List<Map<String, Object>> tdytList = busScopeService.findIndustryCategory(parentId);
        return tdytList;
    }
    /**
     * 获取行业类别2
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/importXlsData")
    @ResponseBody
    public AjaxJsonCode importXlsData(HttpServletRequest request, HttpServletResponse response) {
        String fileId=request.getParameter("APPLY_PATH");
        AjaxJsonCode ajaxJsonCode= busScopeService.importXlsDataByFileId(fileId);
        return ajaxJsonCode;
    }
}
