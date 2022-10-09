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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.LawReguService;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  法律法规Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/lawReguController")
public class LawReguController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(LawReguController.class);
    /**
     * 引入Service
     */
    @Resource
    private LawReguService lawReguService;
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
        lawReguService.deleteCascade(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 法律法规记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  lawRegu = lawReguService.getByJdbc("T_WSBS_LAWREGU",
                    new String[]{"LAW_ID"},new Object[]{entityId});
            //获取法规的内容
            String lawContent = (String) lawRegu.get("LAW_CONTENT");
            lawRegu.put("LAW_CONTENT",StringEscapeUtils.escapeHtml(lawContent));
            request.setAttribute("lawRegu", lawRegu);
        }
        return new ModelAndView("wsbs/lawregu/lawreguinfo");
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
        String entityId = request.getParameter("LAW_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String recordId = lawReguService.saveOrUpdate(variables, "T_WSBS_LAWREGU", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 法律法规记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 法律法规记录",SysLogService.OPERATE_TYPE_ADD);
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
        return new ModelAndView("wsbs/lawregu/lawreguview");
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
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.UPDATE_TIME","desc");
        List<Map<String, Object>> list = lawReguService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 
     * 加载法律法规数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.UPDATE_TIME","desc");
        List<Map<String, Object>> list = lawReguService.findBySqlFilter(filter);
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("LAW_TITLE")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 
     * 根据办事指南id获取法律法规配置信息
     * @param request
     * @param response
     */
    @RequestMapping(params = "lawIdAndTitle")
    public void lawIdAndTitle(HttpServletRequest request, HttpServletResponse response) {
        String guideId = request.getParameter("guideId");
        List<Map<String,Object>> list = this.lawReguService.findByGuideId(guideId);
        Map<String,String> obj = new HashMap<String,String>();
        StringBuffer lawIds = new StringBuffer("");
        StringBuffer lawTitles = new StringBuffer("");
        for(Map<String,Object> map:list){
            lawIds.append(map.get("LAW_ID")).append(",");
            lawTitles.append(map.get("LAW_TITLE")).append(",");
        }
        if(lawIds.length()>=1){
            lawIds.deleteCharAt(lawIds.length()-1);
            lawTitles.deleteCharAt(lawTitles.length()-1);
        }
        obj.put("lawIds",lawIds.toString());
        obj.put("lawTitles",lawTitles.toString());
        String json = JSON.toJSONString(obj);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String lawIds = request.getParameter("lawIds");
        String lawTitles = request.getParameter("lawTitles");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("lawIds", lawIds);
        request.setAttribute("lawTitles", lawTitles);
        return new ModelAndView("wsbs/lawregu/lawreguSelector");
    }
    
    /**
     * 分配法律法规给办事项目
     * @param request
     * @return
     */
    @RequestMapping(params = "grantLaws")
    @ResponseBody
    public AjaxJson grantLaws(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String guideId = request.getParameter("guideId");
        String lawIds = request.getParameter("lawIds");
        lawReguService.saveMiddleConfig(guideId, lawIds);
        j.setMsg("操作成功");
        return j;
    }
}

