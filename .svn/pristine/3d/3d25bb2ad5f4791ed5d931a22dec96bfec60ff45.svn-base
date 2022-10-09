/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

import java.io.IOException;
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
import net.evecom.platform.hd.service.ComplainService;
import net.evecom.platform.system.model.SysUser;
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
 * 描述  投诉监督Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/complainController")
public class ComplainController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ComplainController.class);
    /**
     * 引入Service
     */
    @Resource
    private ComplainService complainService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 
     * view
     * @author Rider Chen
     * @created 2015-11-23 下午03:49:17
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("hd/complain/list");
    }
    /**
     * 
     * 描述 前台公众用户登录
     * @author Rider Chen
     * @created 2015-12-14 上午09:07:41
     * @param request
     * @return
     */
    @RequestMapping(params = "tsjd")
    public ModelAndView tsjd(HttpServletRequest request) {
        return new ModelAndView("website/hd/tsjd");
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
        filter.addSorted("U.CREATE_TIME", "desc");
        List<Map<String, Object>>  list = complainService.findBySqlFilter(filter, 
                "where (U.STATUS IS NULL OR U.STATUS=1) ");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     *
     * 堵点难点问题征集view
     * @author Rider Chen
     * @created 2015-11-23 下午03:49:17
     * @param request
     * @return
     */
    @RequestMapping(params ="questionView")
    public ModelAndView questionView(HttpServletRequest request) {
        return new ModelAndView("hd/complain/questionList");
    }
    /**
     * 堵点难点问题征集 AJAX请求列表数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "questionDatagrid")
    public void questionDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("U.CREATE_TIME", "desc");
        List<Map<String, Object>>  list = complainService.findQuestionBySqlFilter(filter,
                "where 1=1  ");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
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
        Map<String, Object> variables=new HashMap<String, Object>();
        String[] colnames=selectColNames.split(",");
        for (int i = 0; i < colnames.length; i++) {
            variables.put("COMPLAIN_ID", colnames[i]);
            variables.put("STATUS", "-1");
            complainService.saveOrUpdate(variables, "T_HD_COMPLAIN", colnames[i]);
        }
        //complainService.remove("T_HD_COMPLAIN","COMPLAIN_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 投诉监督记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  complain = complainService.getByJdbc("T_HD_COMPLAIN",
                    new String[]{"COMPLAIN_ID"},new Object[]{entityId});
            request.setAttribute("complain", complain);
        }
        return new ModelAndView("hd/complain/info");
    }
    /**
     * 跳转到信息页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "questionInfo")
    public ModelAndView questionInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  question = complainService.getByJdbc("T_HD_QUESTION",
                    new String[]{"QUESTION_ID"},new Object[]{entityId});
            request.setAttribute("question", question);
        }
        return new ModelAndView("hd/complain/questionInfo");
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/yhzxInfo")
    public ModelAndView yhzxInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  complain = complainService.getByJdbc("T_HD_COMPLAIN",
                    new String[]{"COMPLAIN_ID"},new Object[]{entityId});
            request.setAttribute("complain", complain);
        }
        return new ModelAndView("website/yhzx/tsxx");
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
        String entityId = request.getParameter("COMPLAIN_ID");
        String content = request.getParameter("REPLY_CONTENT");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        //回复
        if(StringUtils.isNotEmpty(content)){
            SysUser sysUser = AppUtil.getLoginUser();
            variables.put("REPLY_STATUS", "1");
            variables.put("REPLY_USERID", sysUser.getUserId());
            variables.put("REPLY_USERNAME", sysUser.getFullname());
            variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = complainService.saveOrUpdate(variables, "T_HD_COMPLAIN", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 投诉监督记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 投诉监督记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "replyQuestion")
    @ResponseBody
    public AjaxJson replyQuestion(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("QUESTION_ID");
        String content = request.getParameter("REPLY_CONTENT");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        //回复
        if(StringUtils.isNotEmpty(content)){
            SysUser sysUser = AppUtil.getLoginUser();
            variables.put("REPLY_FLAG", "1");
            variables.put("REPLY_USERID", sysUser.getUserId());
            variables.put("REPLY_USER", sysUser.getFullname());
            variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述 前台添加投诉监督
     * 
     * @author Rider Chen
     * @created 2015-11-20 下午04:32:26
     * @param request
     * @param response
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveComplain")
    public void saveComplain(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> result = new HashMap<String, Object>();
        String entityId = request.getParameter("COMPLAIN_ID");
        String userId = request.getParameter("CREATE_USERID");
        if (StringUtils.isNotEmpty(userId)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if(StringUtils.isEmpty(entityId)){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            complainService.saveOrUpdate(variables, "T_HD_COMPLAIN", entityId);
            result.put("msg", "保存成功");
            result.put("success", true);
        }else{
            result.put("msg", "保存失败,未登录");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求数据 用户中心我的投诉列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = complainService.findfrontList(page, rows);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据 手机APP用户中心我的投诉列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/complainPagelist")
    public void complainPagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String userName = request.getParameter("userName");
        Map<String, Object> mapList = complainService.findfrontAppList(page, rows, userName);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }
}

