/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
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
import net.evecom.platform.zzhy.service.StatistCommercialService;
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
 * 描述  商事登记统计分析Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/statistCommercialController")
public class StatistCommercialController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(StatistCommercialController.class);
    /**
     * 引入Service
     */
    @Resource
    private StatistCommercialService statistCommercialService;
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
        statistCommercialService.remove("T_COMMERCIAL_STATIST","STATIST_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 商事登记统计分析记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  statistCommercial = statistCommercialService.getByJdbc("T_COMMERCIAL_STATIST",
                    new String[]{"STATIST_ID"},new Object[]{entityId});
            request.setAttribute("statistCommercial", statistCommercial);
        }
        return new ModelAndView("zzhy/statistCommercial/info");
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
        String entityId = request.getParameter("STATIST_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = statistCommercialService.saveOrUpdate(variables, "T_COMMERCIAL_STATIST", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 商事登记统计分析记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 商事登记统计分析记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "ldcStatis")
    public ModelAndView bjtjbStatis(HttpServletRequest request) {
        return new ModelAndView("zzhy/statistCommercial/ldcStatis");
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
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.STATIST_TYPE", "asc");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        List<Map<String, Object>>  list = statistCommercialService.findBySqlFilter(filter, variables);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "getTotalInvestment")
    public void getTotalInvestment(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TOTAL_INVESTMENT", "asc");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        List<Map<String, Object>>  list = statistCommercialService.getTotalInvestmentBySqlFilter(filter, variables);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "glcStatis")
    public ModelAndView glcStatis(HttpServletRequest request) {
        return new ModelAndView("zzhy/statistCommercial/glcStatis");
    }
    /**
     * 跳转到个人绩效报表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goIndPerStatis")
    public ModelAndView goIndPerStatis(HttpServletRequest request) {
        return new ModelAndView("zzhy/statistCommercial/indPerStatis");
    }/**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "indPerData")
    public void glcData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statistCommercialService
                .getIndPerDataStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "countsDetail")
    public ModelAndView countsDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("zzhy/statistCommercial/countsDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "countsDetailData")
    public void countsDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = statistCommercialService.countsDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "banJSDetail")
    public ModelAndView banJSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("zzhy/statistCommercial/banJSDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "banJSDetailData")
    public void banJSDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = statistCommercialService.banJSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "zBSDetail")
    public ModelAndView zBSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("zzhy/statistCommercial/zBSDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "zBSDetailData")
    public void zBSDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = statistCommercialService.zBSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tJSDetail")
    public ModelAndView tJSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("zzhy/statistCommercial/tJSDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "tJSDetailData")
    public void tJSDetailData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = statistCommercialService.tJSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述   异常办件统计
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "abnormalStatis")
    public ModelAndView abnormalStatis(HttpServletRequest request){
        return new ModelAndView("zzhy/statistCommercial/abnormalStatis");
    }
    
    /**
     * 
     * 描述   异常办件统计
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "adnormalData")
    public void adnormalData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> list = statistCommercialService.getAdnormalStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,response);
    }
    /**
     * 
     * 描述   转跳企业办件情况查询
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "manSituationStatis")
    public ModelAndView manSituationStatis(HttpServletRequest request){
        return new ModelAndView("zzhy/statistCommercial/manSituationStatis");
    }
    /**
     *
     * 描述   转跳企业办件信息查询
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "goInfoStatist")
    public ModelAndView goInfoStatist(HttpServletRequest request){
        return new ModelAndView("zzhy/statistCommercial/goInfoStatist");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "manSituationStatisData")
    public void manSituationStatisData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = statistCommercialService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "goInfoStatisData")
    public void goInfoStatisData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = statistCommercialService.findInfoBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   转跳注册官办件量统计
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "userStatis")
    public ModelAndView userStatis(HttpServletRequest request){
        return new ModelAndView("zzhy/statistCommercial/userStatis");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "userStatisData")
    public void userStatisData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statistCommercialService.getUserTaskBySqlFilter(filter);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

