/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.call.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.service.BespeakOnlineService;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.call.service.CallSetService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.system.mas.SMSClient;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.PushDataToSJJXXZXService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.thread.PushDateToSJJXXZXRunnable;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-12-5 上午8:57:09
 */
@Controller
@RequestMapping("/appointmentController")
public class AppointmentController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(AppointmentController.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * callService
     */
    @Resource
    private CallService callService;
    /**
     * callService
     */
    @Resource
    private NewCallService newCallService;
    /**
     * 
     */
    @Resource
    private CallSetService callSetService;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     */
    @Resource
    private BespeakOnlineService bespeakOnlineService;
    
    /**
     * 引入省经济中心信息推送service
     */
    @Resource
    private PushDataToSJJXXZXService pushDataToSJJXXZXService;
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-5 上午9:12:12
     * @param request
     * @return
     */
    @RequestMapping(params="appointmentView")
    public ModelAndView appointmentView(HttpServletRequest request){
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        return new ModelAndView("call/appointment/appointmentView");
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-5 上午9:14:51
     * @param request
     * @param response
     */
    @RequestMapping(params="appointmentGrid")
    public void appointmentGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
            filter.addFilter("Q_t.DATE_TIME_=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        filter.addSorted("t.DATE_TIME", "asc");
        List<Map<String,Object>> list = callService.findAppointmentBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-6 上午9:00:46
     * @param request
     * @return
     */
    @RequestMapping(params="appointmentTake")
    public ModelAndView appointmentTake(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> appointment = callService.getByJdbc(
                "T_BESPEAK_APPLY", new String[] { "ID" },
                new Object[] { entityId });
        String departName = callService
                .getByJdbc("t_msjw_system_department",
                        new String[] { "depart_id" },
                        new Object[] { appointment.get("depart_id") })
                .get("depart_name").toString();
        appointment.put("DEPART_NAME", departName);
        request.setAttribute("appointment", appointment);
        return new ModelAndView("call/appointment/appointmentTake");
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-5 上午9:12:12
     * @param request
     * @return
     */
    @RequestMapping(params="newAppointmentView")
    public ModelAndView newAppointmentView(HttpServletRequest request){
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        return new ModelAndView("callnew/appointment/appointmentView");
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-5 上午9:14:51
     * @param request
     * @param response
     */
    @RequestMapping(params="newAppointmentGrid")
    public void newAppointmentGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_t.STATUS_!=", "0");
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
            filter.addFilter("Q_t.DATE_TIME_=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        filter.addSorted("t.DATE_TIME", "asc");
        List<Map<String,Object>> list = newCallService.findAppointmentBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-6 上午9:00:46
     * @param request
     * @return
     */
    @RequestMapping(params="newAppointmentTake")
    public ModelAndView newAppointmentTake(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> appointment = callService.getByJdbc("T_CKBS_APPOINTMENT_APPLY",
                new String[] { "RECORD_ID" }, new Object[] { entityId });
        String businessName = callService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "BUSINESS_CODE" },
                new Object[] { appointment.get("BUSINESS_CODE") }).get("BUSINESS_NAME").toString();
        appointment.put("BUSINESS_NAME", businessName);
        
        request.setAttribute("appointment", appointment);
        return new ModelAndView("callnew/appointment/appointmentTake");
    }
    
    /**
     * 
     * 描述    管理后台人员辅助预约
     * @author Danto Huang
     * @created 2020年2月1日 上午9:59:24
     * @param request
     * @return
     */
    @RequestMapping(params="toWinAppoointment")
    public ModelAndView toWinAppoointment(HttpServletRequest request){
        
        return new ModelAndView("callnew/appointment/winAppointmentView");
    }
    

    /**
     * 
     * 描述    当日预约记录
     * @author Danto Huang
     * @created 2020年2月1日 上午10:02:53
     * @param request
     * @param response
     */
    @RequestMapping(params="winAppointmentGrid")
    public void winAppointmentGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_t.RECORD_FORM_=", "2");
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
            filter.addFilter("Q_t.CREATE_TIME_RLIKE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        filter.addSorted("t.CREATE_TIME", "desc");
        List<Map<String,Object>> list = newCallService.findAppointmentBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述    办事预约
     * @author Danto Huang
     * @created 2020年2月1日 上午10:25:37
     * @param request
     * @return
     */
    @RequestMapping(params="winAppointmentApply")
    public ModelAndView winAppointmentApply(HttpServletRequest request){
        
        return new ModelAndView("callnew/appointment/winAppointmentApply");
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月1日 上午10:59:20
     * @param request
     * @param response
     */
    @RequestMapping(params="loadDepart")
    public void loadDepart(HttpServletRequest request,HttpServletResponse response){
        List<Map<String,Object>> list = callSetService.findDepart();
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月1日 上午10:59:20
     * @param request
     * @param response
     */
    @RequestMapping(params="loadBusiness")
    public void loadBusiness(HttpServletRequest request,HttpServletResponse response){
        String departId = request.getParameter("departId");
        List<Map<String,Object>> list = callSetService.findByParentId(departId);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }   

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月1日 上午10:59:20
     * @param request
     * @param response
     */
    @RequestMapping(params="loadDate")
    public void loadDate(HttpServletRequest request,HttpServletResponse response){
     // 获取可提前预约的天数
        String tq = dictionaryService.getDicCode("wsyyktqyygzr", "可提前预约工作日");
        // 获取可预约日期（提前5个工作日）
        String wdsql = "select * from (select t.* from t_msjw_system_workday t " +
                "where t.w_date>to_char(sysdate, 'yyyy-MM-dd' ) and t.w_setid=2 " +
                "order by t.w_date asc ) wd where rownum<=?";
        List<Map<String,Object>> list = bespeakOnlineService.getListBySql(wdsql, new String[] { tq });
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月1日 下午1:31:22
     * @param request
     * @param response
     */
    @RequestMapping(params="loadBespeakTimeList")
    public void loadBespeakTimeList(HttpServletRequest request,HttpServletResponse response){
        String departId = request.getParameter("departId");
        String wDate = request.getParameter("wDate");
        List<Map<String, Object>> bespeaklist = new ArrayList<Map<String, Object>>();
        String kyysql = "select * from T_CKBS_APPOINTMENT_SET t where t.depart_id=? order by t.begin_time";
        List<Map<String, Object>> kyylist = bespeakOnlineService.getListBySql(kyysql, new String[] { departId });
        for (Map kyymap : kyylist) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.putAll(kyymap);
            StringBuffer yyysql = new StringBuffer();
            yyysql.append("select t.* from T_CKBS_APPOINTMENT_APPLY t ");
            yyysql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
            yyysql.append("where t.date_time = ? and t.begin_time=? and t.end_time=? and b.depart_id=? ");
            yyysql.append(" and t.status<>0 ");
            List<Map<String, Object>> yyylist = bespeakOnlineService.getListBySql(yyysql.toString(),
                    new String[] { wDate, kyymap.get("BEGIN_TIME").toString(),
                            kyymap.get("END_TIME").toString(), departId });
            map.put("bespeakNumber", yyylist.size());
            Date nowTime = new Date();
            long time = DateTimeUtil.getIntervalTime(DateTimeUtil.getStrOfDate(nowTime, "yyyy-MM-dd HH:mm"), wDate
                    + " " + kyymap.get("BEGIN_TIME").toString(), "yyyy-MM-dd HH:mm", 3);
            // 小于十分钟不可预约，大于十分钟可预约，也可取消预约0：不可预约，1：可预约，2：取消预约
            if (time < 10) {
                map.put("isOverTime", "0");
            } else {
                map.put("isOverTime", "1");
            }
            bespeaklist.add(map);
        }
        String json = JSON.toJSONString(bespeaklist);
        this.setJsonString(json, response);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/19 11:59:00
     * @param 
     * @return 
     */
    @RequestMapping("/loadBespeakTimeListForDevbase")
    @ResponseBody
    public List<Map<String, Object>> loadBespeakTimeListForDevbase
            (HttpServletRequest request, HttpServletResponse response) {
        String departId = request.getParameter("departId");
        String wDate = request.getParameter("wDate");
        List<Map<String, Object>> bespeaklist = new ArrayList<Map<String, Object>>();
        String kyysql = "select * from T_CKBS_APPOINTMENT_SET t where t.depart_id=? order by t.begin_time";
        List<Map<String, Object>> kyylist = bespeakOnlineService.getListBySql(kyysql, new String[] { departId });
        for (Map kyymap : kyylist) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.putAll(kyymap);
            StringBuffer yyysql = new StringBuffer();
            yyysql.append("select t.* from T_CKBS_APPOINTMENT_APPLY t ");
            yyysql.append("left join T_CKBS_BUSINESSDATA b on b.business_code = t.business_code ");
            yyysql.append("where t.date_time = ? and t.begin_time=? and t.end_time=? and b.depart_id=? ");
            yyysql.append(" and t.status<>0 ");
            List<Map<String, Object>> yyylist = bespeakOnlineService.getListBySql(yyysql.toString(),
                    new String[] { wDate, kyymap.get("BEGIN_TIME").toString(),
                            kyymap.get("END_TIME").toString(), departId });
            String ALLOW_BESPEAK_NUMBER = kyymap.get("ALLOW_BESPEAK_NUMBER").toString();
            int x = Integer.parseInt(ALLOW_BESPEAK_NUMBER);
            if (yyylist.size() >= x) {
                map.put("bespeakNumber", x);
            } else {
                map.put("bespeakNumber", yyylist.size());
            }
            Date nowTime = new Date();
            long time = DateTimeUtil.getIntervalTime(DateTimeUtil.getStrOfDate(nowTime, "yyyy-MM-dd HH:mm"), wDate
                    + " " + kyymap.get("BEGIN_TIME").toString(), "yyyy-MM-dd HH:mm", 3);
            // 小于十分钟不可预约，大于十分钟可预约，也可取消预约0：不可预约，1：可预约，2：取消预约
            if (time < 10) {
                map.put("isOverTime", "0");
            } else {
                map.put("isOverTime", "1");
            }
            bespeaklist.add(map);
        }
        return bespeaklist;
    }
    
    /**
     * 
     * 描述    预约
     * @author Danto Huang
     * @created 2018年9月11日 下午2:59:07
     * @param request
     * @return
     */
    @RequestMapping(params="saveAppointApply")
    @ResponseBody
    public AjaxJson saveAppointApply(HttpServletRequest request,HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String userName = request.getParameter("userName");
        String cardNo = request.getParameter("cardNo");
        String mobile = request.getParameter("mobile");
        if (StringUtils.isEmpty(userName)|| StringUtils.isEmpty(cardNo)|| StringUtils.isEmpty(mobile)) {
            j.setMsg("预约信息不完整，请补充完整后再试！");
            j.setSuccess(false);
        } else {
            try {
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                variables.put("NAME", userName);
                variables.put("CARD", cardNo);
                variables.put("PHONE", mobile);
                String TIMESET_ID = variables.get("TIMESET_ID").toString();
                Map<String, Object> set = bespeakOnlineService.getByJdbc("T_CKBS_APPOINTMENT_SET",
                        new String[] { "RECORD_ID" }, new Object[] { TIMESET_ID });
                Integer allowBespeakNumber = Integer.parseInt(set.get("ALLOW_BESPEAK_NUMBER").toString());
                String beginTime = set.get("BEGIN_TIME").toString();
                String endTime = set.get("END_TIME").toString();
                // 该时段已预约数
                String yyysql = "select t.* from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.date_time = ? and t.begin_time=? and t.end_time=? " +
                        "and t.business_code=? and t.status='1'";
                List<Map<String, Object>> yyylist = bespeakOnlineService.getListBySql(yyysql,
                        new String[] { variables.get("DATE_TIME").toString(), beginTime,
                                endTime, variables.get("BUSINESS_CODE").toString() });
                // 一个身份证，同一个部门一天只能预约一次
                String cardsql = "select * from T_CKBS_APPOINTMENT_APPLY t " +
                        "where t.card=? and t.date_time = ? and t.business_code=? and t.status='1'";
                List<Map<String, Object>> cardlist = bespeakOnlineService.getListBySql(cardsql,
                        new String[] { variables.get("CARD").toString(), variables.get("DATE_TIME").toString(),
                                variables.get("BUSINESS_CODE").toString() });
                if (cardlist.size() > 0) {
                    j.setMsg("本次预约失败<br/>您已预约了" + cardlist.get(0).get("DATE_TIME") + " "
                            + cardlist.get(0).get("BEGIN_TIME") + "-" + cardlist.get(0).get("END_TIME") + "时段");
                    j.setSuccess(false);
                } else if (allowBespeakNumber <= yyylist.size()) {
                    j.setMsg("该时段已预约满");
                    j.setSuccess(false);
                } else {
                    variables.put("BEGIN_TIME", beginTime);
                    variables.put("END_TIME", endTime);
                    variables.put("RECORD_FORM", 2);
                    variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    bespeakOnlineService.saveOrUpdate(variables, "T_CKBS_APPOINTMENT_APPLY", null);
                    
                    //短信内容
                    Map<String, Object> bus = bespeakOnlineService.getByJdbc("T_CKBS_BUSINESSDATA",
                            new String[] { "BUSINESS_CODE" }, new Object[] { variables.get("BUSINESS_CODE") });
                    String departName = (String) bespeakOnlineService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                            new String[] { "DEPART_ID" }, new Object[] { bus.get("DEPART_ID") }).get("DEPART_NAME");
                    StringBuffer msg = new StringBuffer();
                    msg.append(mobile).append("用户，您好！您已成功预约").append(departName).append(bus.get("BUSINESS_NAME"));
                    msg.append(variables.get("DATE_TIME")).append(" ").append(beginTime).append("-").append(endTime);
                    msg.append("时段办理业务，请您届时准时前来取号办理。");
                    msg.append("请提前下载好闽政通并做好实名认证，现场需出示“八闽健康码“。");
                    //TODO 短信接口调用
                    int sendRst = SMSClient.sendMsg(mobile, msg.toString());
                    Map<String,Object> msgInfo = new HashMap<String, Object>();
                    msgInfo.put("CONTENT", msg.toString());
                    msgInfo.put("RECEIVER_MOB", mobile);
                    msgInfo.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd hh:mm:ss"));
                    if(sendRst==1){
                        msgInfo.put("SEND_STATUS", 1);
                    }else{
                        msgInfo.put("SEND_STATUS", 2);
                    }
                    bespeakOnlineService.saveOrUpdate(msgInfo, "T_MSJW_SYSTEM_MSGSEND", null);
                    String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
                    if ("开".equals(switchTurn)) {
                        //推送至省经济中心放号实时记录
                        if(StringUtil.isNotEmpty(variables)){
                            //放号
                            pushDataToSJJXXZXService.pushDateToSJJXXZX(variables, "addQueueAssignInfo");
                        }
                    }
                    j.setMsg("预约成功");
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
                j.setMsg("预约失败，请稍后再试");
                j.setSuccess(false);
            }
        }
        return j;
    }
    
    @RequestMapping(params="cancelWinApply")
    @ResponseBody
    public AjaxJson cancelWinApply(HttpServletRequest request,HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("entityId");
        Map<String, Object> info = bespeakOnlineService.getByJdbc("T_CKBS_APPOINTMENT_APPLY",
                new String[] { "RECORD_ID" }, new String[] { recordId });
        if (!StringUtils.isEmpty(info) && info.get("IS_TAKE").equals("0")) {
            info.put("STATUS", 0);
            bespeakOnlineService.saveOrUpdate(info, "T_CKBS_APPOINTMENT_APPLY", recordId);
            j.setMsg("取消预约成功");
        } else {
            j.setMsg("取消预约失败<br/>失败原因：该预约号已进行取号，不得取消");
            j.setSuccess(false);
        }

        return j;
    }
    
}
