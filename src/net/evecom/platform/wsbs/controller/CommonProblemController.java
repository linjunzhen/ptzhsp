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
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CommonProblemService;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  常见问题Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/commonProblemController")
public class CommonProblemController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CommonProblemController.class);
    /**
     * 引入Service
     */
    @Resource
    private CommonProblemService commonProblemService;
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
        commonProblemService.remove("T_WSBS_COMMONPROBLEM","PROBLEM_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 常见问题记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  commonProblem = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            commonProblem = commonProblemService.getByJdbc("T_WSBS_COMMONPROBLEM",
                    new String[]{"PROBLEM_ID"},new Object[]{entityId});
            String answerContent = (String) commonProblem.get("ANSWER_CONTENT");
            commonProblem.put("ANSWER_CONTENT",StringEscapeUtils.escapeHtml(answerContent));
        }else{
            //获取项目ID
            String itemId = request.getParameter("itemId");
            commonProblem = new HashMap<String,Object>();
            commonProblem.put("ITEM_ID",itemId);
        }
        request.setAttribute("commonProblem", commonProblem);
        return new ModelAndView("wsbs/commonproblem/problemInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("PROBLEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("LASTER_UPDATETIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String recordId = commonProblemService.saveOrUpdate(variables, "T_WSBS_COMMONPROBLEM", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 常见问题记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 常见问题记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
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
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.LASTER_UPDATETIME","desc");
        List<Map<String, Object>> list = commonProblemService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/commonproblem/problemView");
    }
    /**
     * easyui AJAX请求数据 常见问题列表分页
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String itemId = request.getParameter("itemId");
        Map<String, Object> mapList = commonProblemService.findfrontList(page, rows,itemId);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 所有常见问题分页
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/cjwtPagelist")
    public void cjwtPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String problemTitle = request.getParameter("PROBLEM_TITLE");
        String busTypeIds = request.getParameter("busTypeIds");
        Map<String, Object> mapList = commonProblemService.findCjwtList(page, rows,problemTitle,busTypeIds);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到常见问题详细页
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/cjwtDetail")
    public ModelAndView cjwtDetail(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  commonProblem = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            commonProblem = commonProblemService.getByJdbc("T_WSBS_COMMONPROBLEM",
                    new String[]{"PROBLEM_ID"},new Object[]{entityId});
            //String answerContent = (String) commonProblem.get("ANSWER_CONTENT");
            //commonProblem.put("ANSWER_CONTENT",StringEscapeUtils.escapeHtml(answerContent));
        }
        request.setAttribute("commonProblem", commonProblem);
        return new ModelAndView("website/bsdt/cjwtDetail");
    }
    /**
     * 
     * 描述    历史记录
     * @author Danto Huang
     * @created 2018年11月13日 上午10:14:16
     * @param request
     * @param response
     */
    @RequestMapping(params = "hisDatagrid")
    public void hisDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.LASTER_UPDATETIME","desc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_T.ITEM_ID_=",itemId);
        String version = request.getParameter("version");
        filter.addFilter("Q_T.C_VERSION_=",version);
        filter.addFilter("Q_T.EFFECT_STATUS_=","1");
        List<Map<String, Object>> list = commonProblemService.findHisBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
}

