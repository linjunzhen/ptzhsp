/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.controller;

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
import net.evecom.platform.hd.service.OsOptionService;
import net.evecom.platform.hd.service.OsTicketService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  投票记录Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/osTicketController")
public class OsTicketController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(OsTicketController.class);
    /**
     * 引入Service
     */
    @Resource
    private OsTicketService osTicketService;
    /**
     * 引入Service
     */
    @Resource
    private OsOptionService osOptionService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        // 选项ID
        String optionId = request.getParameter("optionId");
        if (StringUtils.isNotEmpty(optionId) && !optionId.equals("undefined")) {
            Map<String, Object> osOption = osOptionService.getByJdbc("T_HD_OS_OPTION",
                    new String[] { "OPTIONID" }, new Object[] { optionId });
            request.setAttribute("osOption", osOption);
        }
        return new ModelAndView("hd/os/ticketList");
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
        osTicketService.remove("T_HD_OS_TICKET","TICKETID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 投票记录记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  osTicket = osTicketService.getByJdbc("T_HD_OS_TICKET",
                    new String[]{"TICKETID"},new Object[]{entityId});
            request.setAttribute("osTicket", osTicket);
        }
        return new ModelAndView("hd/osTicket/info");
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
        String entityId = request.getParameter("TICKETID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = osTicketService.saveOrUpdate(variables, "T_HD_OS_TICKET", entityId,"S_HD_OS_TICKET");
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 投票记录记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 投票记录记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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
        // 选项ID
        String optionId = request.getParameter("optionId");
        
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.SUBMITDATE", "desc");
        if (StringUtils.isNotEmpty(optionId) && !optionId.equals("undefined")) {
            filter.addFilter("Q_T.OPTIONID_=", optionId);
        }
        List<Map<String, Object>> list = osTicketService.findBySqlFilter(filter, "");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

}

