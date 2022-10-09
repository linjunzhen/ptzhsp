/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  工作日Controller
 * @author Roy Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/workdayController")
public class WorkdayController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WorkdayController.class);
    /**
     * 引入Service
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public void multiDel(HttpServletRequest request, HttpServletResponse response) {
        String selectColNames = request.getParameter("calendarId");
        workdayService.remove("T_SYSTEM_WORKDAY","W_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 工作日记录",SysLogService.OPERATE_TYPE_DEL);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        pw.write("{\"IsSuccess\":true,\"Msg\":\"\u64cd\u4f5c\u6210\u529f!\"}");
        pw.flush();
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
            Map<String,Object>  workday = workdayService.getByJdbc("T_MSJW_SYSTEM_WORKDAY",
                    new String[]{"W_ID"},new Object[]{entityId});
            request.setAttribute("workday", workday);
        }
        return new ModelAndView("system/workday/info");
    }
    
    /**
     * 
     * 描述 获取信息
     * 
     * @author Roy Li
     * @created 2014-11-7 上午9:00:11
     * @param request
     * @param response
     * @throws ParseException 
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) throws ParseException {      
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = workdayService.findBySqlFilter(filter);
        StringBuffer jsonTemp=new StringBuffer();
        jsonTemp.append("{\"start\":\"\\/Date(");
        jsonTemp.append(new SimpleDateFormat("yyyy-MM-dd").parse
                (request.getParameter("Q_W_DATE_>=")).getTime());
        jsonTemp.append(")\\/\",\"end\":\"\\/Date(");
        jsonTemp.append(new SimpleDateFormat("yyyy-MM-dd").parse
                (request.getParameter("Q_W_DATE_<=")).getTime());
        jsonTemp.append(")\\/\",\"error\":null,\"issort\":true,\"events\":[");
        for (Map<String, Object> record : list) {
            jsonTemp.append("[\"").append(record.get("W_ID")).append("\",\"");
            jsonTemp.append(record.get("W_TITLE")).append("\",\"\\/Date(");
            jsonTemp.append(new SimpleDateFormat("yyyy-MM-dd").parse(record.get("W_DATE").toString()).getTime());
            jsonTemp.append(")\\/\",\"\\/Date(");
            jsonTemp.append(new SimpleDateFormat("yyyy-MM-dd").parse(record.get("W_DATE").toString()).getTime());
            jsonTemp.append(")\\/\", 1,0,0,0,1,\"\",\"\"],");
        }
        if (list.size() > 0) {
            jsonTemp = new StringBuffer(jsonTemp.substring(0, jsonTemp.length() - 1));
        }
        jsonTemp.append("]}");
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        pw.write(jsonTemp.toString());
        pw.flush();
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     * @throws ParseException 
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        String entityId = request.getParameter("W_ID");
        String sd = request.getParameter("CalendarStartTime").substring(0, 10);
        String ed = request.getParameter("CalendarEndTime").substring(0, 10);
        
        SqlFilter filter = new SqlFilter(request);
        filter.getQueryParams().put("Q_W_DATE_>=", sd);
        filter.getQueryParams().put("Q_W_DATE_<=", ed);
        List<Map<String, Object>> list = workdayService.findBySqlFilter(filter);
        //log.info("--------------------"+list.size());
        if(list.size()>0){
            pw.write("{\"IsSuccess\":false,\"Msg\":\"同一日期不能重复设置，请先删除后再试!\"}");
            pw.flush();
            return;
        }
        int days = DateTimeUtil.getDaysBetween(sd,ed);        
        StringBuffer recordId=new StringBuffer();
        for(int i=0;i<=days;i++){
            Date dnext = DateTimeUtil.getNextDay(DateTimeUtil.strToDate(sd), i);            
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("W_TITLE", "1".equals(request.getParameter("CalendarTitle"))?"休息日":"工作日");
            variables.put("W_SETID", request.getParameter("CalendarTitle"));
            variables.put("W_DATE", DateTimeUtil.dateToStr(dnext));
            recordId.append(workdayService.saveOrUpdate(variables, "T_SYSTEM_WORKDAY", 
                 entityId,"SEQ_SYSTEM_WORKDAY")).append(",");
        }
        
        pw.write("{\"IsSuccess\":true,\"Msg\":\"\u64cd\u4f5c\u6210\u529f!\",\"Data\":\""+
        recordId.substring(0, recordId.length()-1)+"\"}");
        pw.flush();
    }
    
    /**
     * 跳转到工作日设置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "WorkdayView")
    public ModelAndView workdayView(HttpServletRequest request) {
        return new ModelAndView("system/workday/WorkdayView");
    }
    /**
     * 
     * 描述 获取日期
     * @author Faker Li
     * @created 2015年11月10日 上午10:57:51
     * @param request
     * @param response
     * @throws ParseException
     */
    @RequestMapping(params = "findData")
    public void findData(HttpServletRequest request, HttpServletResponse response) throws ParseException {      
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = workdayService.findDataBySqlFilter(filter);
        for (Map<String, Object> map : list) {
            if(map.get("W_SETID").toString().equals("1")){
                map.put("title", "休息日");
                map.put("color", "#FF0000");
            }else if (map.get("W_SETID").toString().equals("2")){
                map.put("title", "工作日");
            }
            map.put("start",(String) map.get("W_DATE"));
        }
        JsonUtil.printJson(response, list);
    }
    /**
     * 修改日历
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "updateWorkday")
    @ResponseBody
    public AjaxJson updateWorkday(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("W_ID");
        if (StringUtils.isNotEmpty(entityId)) {
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            String recordId = workdayService.saveOrUpdate(variables, "T_MSJW_SYSTEM_WORKDAY", entityId);
        }
        j.setMsg("修改成功");
        return j;
    }
}

