/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import net.evecom.platform.system.service.ScheduleService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  定时器Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/scheduleController")
public class ScheduleController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ScheduleController.class);
    /**
     * 引入Service
     */
    @Resource
    private ScheduleService scheduleService;
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
        try {
            Scheduler scheduler = (Scheduler) AppUtil
                    .getBean("schedulerFactory");
            scheduler.pauseAll();
            for(String jobId:selectColNames.split(",")){
                Map<String,Object> delSche = scheduleService.getByJdbc("T_MSJW_SYSTEM_SCHEDULE",
                        new String[]{"JOB_ID"},new Object[]{jobId});
                scheduler.deleteJob((String)delSche.get("JOB_NAME"),
                        Scheduler.DEFAULT_GROUP);
            }
            scheduler.resumeAll();
            scheduleService.remove("T_MSJW_SYSTEM_SCHEDULE","JOB_ID",selectColNames.split(","));
            
        }catch (SchedulerException e) {
            log.error(e.getMessage());
        }
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
            Map<String,Object>  schedule = scheduleService.getByJdbc("T_MSJW_SYSTEM_SCHEDULE",
                    new String[]{"JOB_ID"},new Object[]{entityId});
            request.setAttribute("schedule", schedule);
        }
        return new ModelAndView("system/schedule/ScheduleInfo");
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
        Scheduler scheduler = (Scheduler) AppUtil.getBean("schedulerFactory");
        String entityId = request.getParameter("JOB_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            scheduler.pauseAll();
            String jobname = (String) variables.get("JOB_NAME");
            String classname = (String) variables.get("CLASS_NAME");
            String jobCron = (String) variables.get("JOB_CRON");
            if (StringUtils.isNotEmpty(entityId)) {// 更新
                scheduler.deleteJob(jobname,
                        Scheduler.DEFAULT_GROUP);
            } else {// 新增
                variables.put("JOB_STATUS", ScheduleService.ACTIVE_STATUS);
            }
            int jobStatus = Integer.parseInt(variables.get("JOB_STATUS").toString());
            if (jobStatus==ScheduleService.ACTIVE_STATUS) {// 激活状态
                String uuid = UUID.randomUUID().toString();
                String trigname = uuid.replace("-", "").substring(0, 8);
                JobDetail job = new JobDetail(jobname,
                        Scheduler.DEFAULT_GROUP, Class.forName(classname));
                CronTrigger triggers = new CronTrigger(trigname,
                        Scheduler.DEFAULT_GROUP, jobCron);
                scheduler.scheduleJob(job, triggers);
            }
            if(StringUtils.isEmpty(entityId)){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            scheduleService.saveOrUpdate(variables, "T_MSJW_SYSTEM_SCHEDULE", entityId);
            
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        } catch (ParseException e) {
            log.error(e.getMessage());
        } finally {
            try {
                scheduler.resumeAll();
            } catch (SchedulerException e) {
                log.error(e.getMessage());
            }
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 加载数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "hours")
    public void hours(HttpServletRequest request,
            HttpServletResponse response) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for(int i = 0;i<24;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("DIC_CODE", String.valueOf(i));
            map.put("DIC_NAME", String.valueOf(i));
            list.add(map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 加载数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "minutes")
    public void minutes(HttpServletRequest request,
            HttpServletResponse response) {
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for(int i = 0;i<60;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("DIC_CODE", String.valueOf(i));
            map.put("DIC_NAME", String.valueOf(i));
            list.add(map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
   
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "ScheduleList")
    public ModelAndView scheduleList(HttpServletRequest request) {
        return new ModelAndView("system/schedule/ScheduleList");
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
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = scheduleService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 方法startJob
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "startJob")
    @ResponseBody
    public AjaxJson startJob(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        try {
            Scheduler scheduler = (Scheduler) AppUtil
                    .getBean("schedulerFactory");
            scheduler.pauseAll();
            for(String jobId:selectColNames.split(",")){
                Map<String,Object> jobShe = scheduleService.getByJdbc("T_MSJW_SYSTEM_SCHEDULE",
                        new String[]{"JOB_ID"},new Object[]{jobId});
                int jobStatus = Integer.parseInt(jobShe.get("JOB_STATUS").toString());
                String jobname = (String) jobShe.get("JOB_NAME");
                String classname = (String) jobShe.get("CLASS_NAME");
                String jobCron = (String) jobShe.get("JOB_CRON");
                if (jobStatus == ScheduleService.FREEZEN_STATUS) {// 原先禁用的任务
                    JobDetail job = new JobDetail(jobname,
                            Scheduler.DEFAULT_GROUP, Class.forName(classname));
                    String uuid = UUID.randomUUID().toString();
                    String trigname = uuid.replace("-", "").substring(0, 8);
                    CronTrigger triggers = new CronTrigger(trigname,
                            Scheduler.DEFAULT_GROUP,jobCron);
                    scheduler.scheduleJob(job, triggers);
                }
            }
            scheduler.resumeAll();
            scheduleService.updateStatus(selectColNames, ScheduleService.ACTIVE_STATUS);
        }catch (SchedulerException e) {
            log.error(e.getMessage());
        } catch (ParseException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        j.setMsg("启动成功");
        return j;
    }
    
    /**
     * 方法stopJob
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "stopJob")
    @ResponseBody
    public AjaxJson stopJob(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        try {
            Scheduler scheduler = (Scheduler) AppUtil
                    .getBean("schedulerFactory");
            scheduler.pauseAll();
            for(String jobId:selectColNames.split(",")){
                Map<String,Object> delSche = scheduleService.getByJdbc("T_MSJW_SYSTEM_SCHEDULE",
                        new String[]{"JOB_ID"},new Object[]{jobId});
                scheduler.deleteJob((String)delSche.get("JOB_NAME"),
                        Scheduler.DEFAULT_GROUP);
            }
            scheduler.resumeAll();
            scheduleService.updateStatus(selectColNames, ScheduleService.FREEZEN_STATUS);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
        j.setMsg("禁用成功");
        return j;
    }
}

