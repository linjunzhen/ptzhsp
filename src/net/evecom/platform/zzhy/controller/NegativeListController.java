/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import java.util.Date;
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
import net.evecom.platform.zzhy.service.NegativeListService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  负面清单Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2016年12月13日 上午9:15:15
 */
@Controller
@RequestMapping("/negativeListController")
public class NegativeListController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(NegativeListController.class);
    /**
     * 引入Service
     */
    @Resource
    private NegativeListService negativeListService;
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
        negativeListService.remove("T_COMMERCIAL_NEGATIVELIST","NEGATIVELIST_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 负面清单记录",SysLogService.OPERATE_TYPE_DEL);
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  negativeList = negativeListService.getByJdbc("T_COMMERCIAL_NEGATIVELIST",
                    new String[]{"NEGATIVELIST_ID"},new Object[]{entityId});
            request.setAttribute("negativeList", negativeList);
        }
        return new ModelAndView("zzhy/negativeList/info");
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
        String entityId = request.getParameter("NEGATIVELIST_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = negativeListService.saveOrUpdate(variables, "T_COMMERCIAL_NEGATIVELIST", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 负面清单记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 负面清单记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
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
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String negativeListCodes = request.getParameter("negativeListCodes");
        String negativeListNames = request.getParameter("negativeListNames");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if(StringUtils.isNotEmpty(negativeListCodes)&&!negativeListCodes.equals("undefined")){
            request.setAttribute("negativeListCodes", negativeListCodes);
            request.setAttribute("negativeListNames", negativeListNames);
        }
        return new ModelAndView("zzhy/negativeList/negativeListSelector");
    }
    
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SqlFilter filter = new SqlFilter(request);        
        filter.addSorted("T.NEGATIVELIST_CODE", "ASC");
        List<Map<String, Object>>  list = negativeListService.findBySqlFilter(filter, variables);
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
    @RequestMapping("/selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String negativeListCodes = request.getParameter("negativeListCodes");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(negativeListCodes)){
            list = negativeListService.findByNegetiveListCOde(negativeListCodes);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
}

