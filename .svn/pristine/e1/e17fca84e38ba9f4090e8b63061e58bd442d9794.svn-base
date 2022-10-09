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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.BsscService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  办事收藏Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/bsscController")
public class BsscController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BsscController.class);
    /**
     * 引入Service
     */
    @Resource
    private BsscService bsscService;
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
        bsscService.remove("T_WSBS_BSSC","BSSC_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 办事收藏记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  bssc = bsscService.getByJdbc("T_WSBS_BSSC",
                    new String[]{"BSSC_ID"},new Object[]{entityId});
            request.setAttribute("bssc", bssc);
        }
        return new ModelAndView("wsbs/bssc/info");
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
        String entityId = request.getParameter("BSSC_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = bsscService.saveOrUpdate(variables, "T_WSBS_BSSC", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 办事收藏记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 办事收藏记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping("/scsx")
    @ResponseBody
    public AjaxJson scsx(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BSSC_ID");
        String itemCode = request.getParameter("itemCode");
        if(AppUtil.getLoginMember()!=null){
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            Map<String,Object>  bssc = bsscService.getByJdbc("T_WSBS_BSSC",
                    new String[]{"ITEM_CODE","YHZH"},new Object[]{itemCode,AppUtil.getLoginMember().get("YHZH")});
            if(StringUtils.isEmpty(entityId)&&bssc==null){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                variables.put("ITEM_CODE", itemCode);
                variables.put("YHZH", AppUtil.getLoginMember().get("YHZH"));
                bsscService.saveOrUpdate(variables, "T_WSBS_BSSC", entityId);
                j.setMsg("收藏成功");
            }else{
                j.setMsg("已收藏过");
            }
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * easyui AJAX请求数据 用户中心我的收藏列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params ="pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = bsscService.findfrontList(page, rows,
                (String)AppUtil.getLoginMember().get("YHZH"));
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * 前台删除收藏
     * @param request
     * @return
     */
    @RequestMapping("/scsc")
    @ResponseBody
    public AjaxJson scsc(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BSSC_ID");
        if(AppUtil.getLoginMember()!=null){
            bsscService.remove("T_WSBS_BSSC","BSSC_ID",entityId.split(","));
            j.setMsg("删除成功");
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 
     * 描述 app获取收藏界面
     * @author Faker Li
     * @created 2016年1月28日 上午11:02:08
     * @param request
     * @param response
     */
    @RequestMapping("/appWdsclist")
    public void appWdbjlist(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String yhzh = request.getParameter("YHZH");
        Map<String, Object> mapList = bsscService.findfrontList(page, rows,
                yhzh);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List)mapList.get("list"),
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * APP前台删除收藏
     * @param request
     * @return
     */
    @RequestMapping("/appscsc")
    @ResponseBody
    public AjaxJson appscsc(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BSSC_ID");
        if(StringUtils.isNotEmpty(entityId)){
            bsscService.remove("T_WSBS_BSSC","BSSC_ID",entityId.split(","));
            j.setMsg("删除成功");
        }else{
            j.setSuccess(false);
            j.setMsg("删除失败");
        }
        return j;
    }
    /**
     * 
     * 描述 app收藏事项
     * @author Faker Li
     * @created 2016年2月18日 下午3:42:23
     * @param request
     * @return
     */
    @RequestMapping("/appscsx")
    @ResponseBody
    public AjaxJson appscsx(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BSSC_ID");
        String itemCode = request.getParameter("itemCode");
        String yhzh = request.getParameter("YHZH");
        Map<String, Object> variables = new HashMap<String, Object>();
        Map<String,Object>  bssc = bsscService.getByJdbc("T_WSBS_BSSC",
                new String[]{"ITEM_CODE","YHZH"},new Object[]{itemCode,yhzh});
        if(StringUtils.isEmpty(entityId)&&bssc==null){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variables.put("ITEM_CODE", itemCode);
            variables.put("YHZH", yhzh);
            bsscService.saveOrUpdate(variables, "T_WSBS_BSSC", entityId);
            j.setMsg("收藏成功");
        }else{
            j.setMsg("已收藏过");
        }
        return j;
    }
}

