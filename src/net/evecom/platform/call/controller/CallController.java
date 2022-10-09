/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.weixin.model.TemplateData;
import net.evecom.platform.weixin.model.WechatTemplate;
import net.evecom.platform.weixin.model.WxAccessToken;
import net.evecom.platform.weixin.util.WeixinUtil;

/**
 * 描述
 * @author Danto Huang
 * @created 2016-1-13 上午11:31:28
 */
@Controller
@RequestMapping("/callController")
public class CallController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CallController.class);
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * callService
     */
    @Resource
    private CallService callService;
    /**
     * departmentService
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    
    /**
     * 
     * 描述 叫号窗口管理
     * @author Danto Huang
     * @created 2016-1-13 上午11:42:51
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="winView")
    public ModelAndView winView(HttpServletRequest request,HttpServletResponse response){
        return new ModelAndView("call/win/winView");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "winDatagrid")
    public void winDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.WIN_NO", "desc");
        List<Map<String, Object>> list = callService.findBySqlFilter(filter);
        for(Map<String,Object> winInfo : list){
            String winDepartNos = winInfo.get("WINDEPART_NO")==null?
                    "":winInfo.get("WINDEPART_NO").toString();
            if (!winDepartNos.isEmpty()) {
                String winDepartName = dictionaryService.getDicNames("winDepartNo", winDepartNos);
                winInfo.put("WINDEPART_NAME", winDepartName);
            }
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     *
     * 删除叫号窗口信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelWin")
    @ResponseBody
    public AjaxJson multiDelWin(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callService.remove("T_CKBS_SERVICEWIN","WIN_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 叫号窗口记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "winInfo")
    public ModelAndView winInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object> win = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            win = callService.getByJdbc("T_CKBS_SERVICEWIN",new String[]{"WIN_ID"},new Object[]{entityId});
            win.put("USERNAMES", "("+win.get("FULLNAME")+")("+win.get("USERNAME")+")");
        }
        request.setAttribute("win", win);
        return new ModelAndView("call/win/winInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateWin")
    @ResponseBody
    public AjaxJson saveOrUpdateWin(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("WIN_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = callService.saveOrUpdate(variables, "T_CKBS_SERVICEWIN", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的叫号窗口记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的叫号窗口记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }    

    /**
     * 
     * 描述 VIP管理
     * @author Danto Huang
     * @created 2016-1-13 上午11:42:51
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="vipView")
    public ModelAndView vipView(HttpServletRequest request,HttpServletResponse response){
        return new ModelAndView("call/vip/vipView");
    }

    /**
     * easyui AJAX请求数据(VIP管理)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "vipDatagrid")
    public void vipDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = callService.findVIPBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     *
     * 删除VIP信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelVip")
    @ResponseBody
    public AjaxJson multiDelVip(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callService.remove("T_CKBS_VIP","VIP_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 VIP记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    

    
    /**
     * 跳转到VIP信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "vipInfo")
    public ModelAndView vipInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object> vip = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)){
            vip = callService.getByJdbc("T_CKBS_VIP",new String[]{"VIP_ID"},new Object[]{entityId});
        }
        request.setAttribute("vip", vip);
        return new ModelAndView("call/vip/vipInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateVip")
    @ResponseBody
    public AjaxJson saveOrUpdateVip(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("VIP_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = callService.saveOrUpdate(variables, "T_CKBS_VIP", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的VIP记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的VIP记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述 窗口办件
     * @author Danto Huang
     * @created 2016-1-14 上午11:12:53
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="queuingView")
    public ModelAndView queuingView(HttpServletRequest request){
        return new ModelAndView("call/queuing/queuingtab");
    }
    
    /**
     * 
     * 描述 窗口办件(待办页)
     * @author Danto Huang
     * @created 2016-1-14 上午11:12:53
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="goQueuingUndo")
    public ModelAndView goQueuingUndo(HttpServletRequest request){
        String username = AppUtil.getLoginUser().getUsername();
        String winNo = "";
        Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
        if(winInfo!=null){
            winNo = winInfo.get("WIN_NO").toString();
        }
        request.setAttribute("winNo", winNo);
        return new ModelAndView("call/queuing/queuingUndo");
    }
    
    /**
     * 
     * 描述 窗口办件(已受理)
     * @author Danto Huang
     * @created 2016-1-14 上午11:12:53
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="goQueuingDone")
    public ModelAndView goQueuingDone(HttpServletRequest request){
        String username = AppUtil.getLoginUser().getUsername();
        Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
        if(winInfo!=null){
            request.setAttribute("isContinue", winInfo.get("IS_CONTINUE").toString());
        }
        return new ModelAndView("call/queuing/queuingDone");
    }

    /**
     * easyui AJAX请求数据(窗口等待叫号)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "queuingUndoGrid")
    public void queuingUndoGrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        String winNo = "";
        Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
        if(winInfo!=null){
            winNo = winInfo.get("WINDEPART_NO").toString();
        }
        if(!username.equals("admin")){
            filter.addFilter("Q_T.WIN_NO_IN", winNo);
        }
        filter.addFilter("Q_T.CALL_STATUS_IN", "0");
        filter.addSorted("T.IS_VIP", "desc");
        filter.addSorted("t.IS_TOP", "desc");
        filter.addSorted("t.TOP_TIME", "desc");
        filter.addSorted("T.IS_FORWARD", "desc");
        filter.addSorted("appointcall", "desc");
        filter.addSorted("T.IS_APPOINTMENT", "desc");
        filter.addSorted("T.CREATE_TIME", "asc");
        filter.addSorted("T.LINE_NO", "asc");
        List<Map<String, Object>> list = callService.findQueuingBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * easyui AJAX请求数据(窗口叫号中)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "callingGrid")
    public void callingGrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        String winNo = "";
        Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
        if(winInfo!=null){
            winNo = winInfo.get("WIN_NO").toString();
        }
        if(!username.equals("admin")){
            filter.addFilter("Q_T.CUR_WIN_=", winNo);
//            filter.addFilter("Q_T.OPERATOR_ID_=", AppUtil.getLoginUser().getUserId());
        }
        filter.addFilter("Q_T.CALL_STATUS_=", "6");
        filter.addSorted("T.IS_VIP", "desc");
        filter.addSorted("t.IS_TOP", "desc");
        filter.addSorted("t.TOP_TIME", "desc");
        filter.addSorted("T.IS_FORWARD", "desc");
        filter.addSorted("T.LINE_NO", "asc");
        List<Map<String, Object>> list = callService.findQueuingBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }

    /**
     * easyui AJAX请求数据(窗口叫号已受理)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "waitingGrid")
    public void waitingGrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String departId = request.getParameter("departId");
        if(departId!=null){
            filter.addFilter("Q_T.DEPART_ID_=", departId);
        }
        filter.addFilter("Q_T.CALL_STATUS_IN", "0");
        List<Map<String, Object>> list = callService.findQueuingBySqlFilter(filter);
//        System.out.println(""+filter.getPagingBean().getTotalItems());
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
//        String json = JSON.toJSONString(list);
//        this.setJsonString(json, response);
    }
    /**
     * easyui AJAX请求数据(窗口叫号等待中)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "queuingDoneGrid")
    public void queuingDoneGrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        String winNo = "";
        Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
        if(winInfo!=null){
        //    winNo = winInfo.get("WIN_NO").toString();
        //    filter.addFilter("Q_T.CUR_WIN_=", winNo);
            filter.addFilter("Q_T.OPERATOR_ID_=", AppUtil.getLoginUser().getUserId());
        }else if(!AppUtil.getLoginUser().getUsername().equals("admin")){
            filter.addFilter("Q_T.CUR_WIN_=", "0");
        }
        filter.addFilter("Q_T.CALL_STATUS_!=", "0");
        filter.addSorted("DECODE(T.OPER_TIME,NULL,0,1)", "DESC");
        filter.addSorted("T.OPER_TIME", "DESC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String, Object>> list = callService.findQueuingBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    /**
     * 
     * 描述 过号
     * @author Danto Huang
     * @created 2016-1-15 下午3:45:06
     * @param request
     * @return
     */
    @RequestMapping(params="queuingPass")
    @ResponseBody
    public AjaxJson queuingPass(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CALL_STATUS", "2");
        variables.put("OPERATOR", AppUtil.getLoginUser().getFullname());
        variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
        variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    /**
     * 
     * 描述 过号
     * @author Danto Huang
     * @created 2016-1-15 下午3:45:06
     * @param request
     * @return
     */
    @RequestMapping(params="bdcAccept")
    @ResponseBody
    public AjaxJson bdcAccept(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CALL_STATUS", "1");
        variables.put("OPERATOR", AppUtil.getLoginUser().getFullname());
        variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
        variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述 窗口叫号处理
     * @author Danto Huang
     * @created 2016-1-15 下午11:15:42
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingDeal")
    public ModelAndView goQueuingDeal(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = callService.getByJdbc("T_CKBS_NUMRECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("call/queuing/queuingDeal");
    }
    /**
     * 
     * 描述 窗口叫号处理
     * @author Danto Huang
     * @created 2016-1-15 下午11:15:42
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingDealzx")
    public ModelAndView goQueuingDealzx(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = callService.getByJdbc("T_CKBS_NUMRECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("call/queuing/queuingDealZX");
    }
    /**
     * 
     * 描述 窗口叫号处理
     * @author Danto Huang
     * @created 2016-1-15 下午11:15:42
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingDeallz")
    public ModelAndView goQueuingDeallz(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = callService.getByJdbc("T_CKBS_NUMRECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("call/queuing/queuingDealLZ");
    }
    /**
     * 
     * 描述 过号
     * @author Danto Huang
     * @created 2016-1-15 下午3:45:06
     * @param request
     * @return
     */
    @RequestMapping(params="queuingDeal")
    @ResponseBody
    public AjaxJson queuingDeal(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("OPERATOR", AppUtil.getLoginUser().getFullname());
        variables.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
        variables.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述 办件转发
     * @author Danto Huang
     * @created 2016-2-26 下午4:22:00
     * @param request
     * @return
     */
    @RequestMapping(params="queuingForward")
    @ResponseBody
    public AjaxJson queuingForward(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("IS_FORWARD", "1");
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述 窗口叫号处理
     * @author Danto Huang
     * @created 2016-1-15 下午11:15:42
     * @param request
     * @return
     */
    @RequestMapping(params="goQueuingForward")
    public ModelAndView goQueuingForward(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        String winNo = request.getParameter("winNo");
        request.setAttribute("winNo", winNo);
        request.setAttribute("recordId", recordId);
        return new ModelAndView("call/queuing/queuingForward");
    }
    /**
     * 
     * 描述 跳转取号页面
     * @author Danto Huang
     * @created 2016-1-16 下午2:04:37
     * @param request
     * @return
     */
    @RequestMapping(params="goTakeNo")
    public ModelAndView goTakeNo(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/index");
    }
    /**
     * 
     * 描述 跳转预约取号页面
     * @author Danto Huang
     * @created 2016-1-16 下午2:04:37
     * @param request
     * @return
     */
    @RequestMapping(params="goAppointTakeNo")
    public ModelAndView goAppoinTakeNo(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/indexAppoint");
    }
    /**
     * 
     * 描述 跳转一窗通办取号页面
     * @author Danto Huang
     * @created 2016-1-16 下午2:04:37
     * @param request
     * @return
     */
    @RequestMapping(params="goYctbTakeNo")
    public ModelAndView goYctbTakeNo(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        request.setAttribute("roomNo", roomNo);
        String departId = request.getParameter("departId");
        if(StringUtils.isEmpty(departId)){
            departId = "2c90b38a67a6266d0167ab958b94619b";
        }
        request.setAttribute("departId", departId);
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        if(StringUtils.isEmpty(ifMaterPrint)){
            ifMaterPrint = "0";
        }
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        String macType = request.getParameter("macType");
        if(StringUtils.isEmpty(macType)){
            macType = "0";
        }
        request.setAttribute("macType", macType);
        return new ModelAndView("call/takeNo/indexYctb");
    }
    /**
     * 
     * 描述 跳转一窗通办取号页面
     * @author Danto Huang
     * @created 2016-1-16 下午2:04:37
     * @param request
     * @return
     */
    @RequestMapping(params="goYctbTakeNoMacW")
    public ModelAndView goYctbTakeNoMacW(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        request.setAttribute("roomNo", roomNo);
        String departId = request.getParameter("departId");
        if(StringUtils.isEmpty(departId)){
            departId = "2c90b38a67a6266d0167ab958b94619b";
        }
        request.setAttribute("departId", departId);
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        if(StringUtils.isEmpty(ifMaterPrint)){
            ifMaterPrint = "0";
        }
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        return new ModelAndView("call/takeNo/indexYctbMacW");
    }


    /**
     * 描述:跳转一企一证取号页面
     *
     * @author Madison You
     * @created 2019/12/26 14:14:00
     * @param
     * @return
     */
    @RequestMapping(params = "goYqyzTakeNoMacW")
    public ModelAndView goYqyzTakeNoMacW(HttpServletRequest request) {
        String roomNo = dictionaryService.getDicCode("YQYZJBPZ", "roomNo");
        String departId = dictionaryService.getDicCode("YQYZJBPZ", "departId");
        String ifMaterPrint = dictionaryService.getDicCode("YQYZJBPZ", "ifMaterPrint");
        request.getSession().setAttribute("callType", "YQYZ");
        request.getSession().setAttribute("departId", departId);
        request.getSession().setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("departId", departId);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        return new ModelAndView("call/takeNo/indexYqyzMacW");
    }

    /**
     * 
     * 跳转
     * @param request
     * @return
     */
    @RequestMapping(params="goQueQuery")
    public ModelAndView goQueQuery(HttpServletRequest request) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.ROOM_NO_IN","A,B");
        List<Map<String, Object>> itemA = newCallService.findLineUpNumBySqlFilter(filter);
        request.setAttribute("itemA", itemA);

        filter.removeFilter("Q_T.ROOM_NO_IN");
        filter.addFilter("Q_T.ROOM_NO_=","C");
        List<Map<String, Object>> itemC = newCallService.findLineUpNumBySqlFilter(filter);
        request.setAttribute("itemC", itemC);

        filter.addFilter("Q_T.ROOM_NO_=","D");
        List<Map<String, Object>> itemD = newCallService.findLineUpNumBySqlFilter(filter);
        request.setAttribute("itemD", itemD);

        filter.addFilter("Q_T.ROOM_NO_=","E");
        List<Map<String, Object>> itemE = newCallService.findLineUpNumBySqlFilter(filter);
        request.setAttribute("itemE", itemE);

        filter.addFilter("Q_T.ROOM_NO_=","F");
        List<Map<String, Object>> itemF = newCallService.findLineUpNumBySqlFilter(filter);
        request.setAttribute("itemF", itemF);

        filter.addFilter("Q_T.ROOM_NO_=","Y");
        List<Map<String, Object>> itemY = newCallService.findLineUpNumBySqlFilter(filter);
        request.setAttribute("itemY", itemY);
        
        return new ModelAndView("weixin/quequery");
    }
    /**
     *
     * 跳转
     * @param request
     * @return
     */
    @RequestMapping(params="goBusWaitNum")
    public ModelAndView goBusWaitNum(HttpServletRequest request) {
        return new ModelAndView("weixin/waitNumQuery");
    }
    ///**
    // *
    // * 跳转
    // * @param request
    // * @return
    // */
    //@RequestMapping(params="busWaitNumDatagrid")
    //public void busWaitNumDatagrid(HttpServletRequest request, HttpServletResponse response) {
    //    String cardId = request.getParameter("cardId");
    //    List<Map<String, Object>> list = newCallService.getBusWaitNumByCardId(cardId);
    //    Map<String, Object> result = new HashMap<String, Object>();
    //    result.put("success", true);
    //    result.put("itemList", list);
    //    String json = JSON.toJSONString(list);
    //    this.setJsonString(json, response);
    //}
    /**
     *
     * 根据排队号获取业务排队前面排队位置
     * @param request
     * @return
     */
    @RequestMapping(params="busWaitNumDatagrid")
    public void busWaitNumDatagrid(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> busCordInfos = new ArrayList<Map<String, Object>>();
        String lineNo = request.getParameter("lineNo");
        Map<String,Object>  busCordInfo= newCallService.getBusWaitNumByLineNo(lineNo);
        if(busCordInfo.size()!=0){
            busCordInfos.add(busCordInfo);
        }
        String json = JSON.toJSONString(busCordInfos);
        this.setJsonString(json, response);
    }
    /**
     *
     * 更新openId
     * @param request
     * @return
     */
    @RequestMapping(params="updateOpenId")
    public void updateOpenId(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        newCallService.updateOpenId(variable);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 跳转取件展示页面
     * @author Kester Chen
     * @created 2018年12月27日 下午4:04:15
     * @param request
     * @return
     */
    @RequestMapping(params="pickUpParts")
    public ModelAndView pickUpParts(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.ACCEPTWAY_=", "1");
        filter.addFilter("Q_T.SFCJ_=", "1");
        filter.addFilter("Q_T.RUN_STATUS_IN", "1,2,3");
        filter.addFilter("Q_T.CREATE_TIME_like", 
                "%"+DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+"%");
        filter.addSorted("T.CJZT","asc");
        filter.addSorted("T.RUN_STATUS","desc");
        filter.addSorted("T.CREATE_TIME","asc");
        List<Map<String, Object>> list = executionService.findBySqlFilterAll(filter);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            String runStatus = map.get("RUN_STATUS")==null?
                    "":map.get("RUN_STATUS").toString();
            if (!"4".equals(runStatus)) {
                String nameString = map.get("SQRMC")==null?
                        "":map.get("SQRMC").toString();
                if (StringUtils.isNotEmpty(nameString)) {
                    StringBuilder sb = new StringBuilder(nameString);
                    nameString = sb.replace(1, 2, "*").toString();
                    map.put("SQRMC", nameString);
                }
                String createTime = map.get("CREATE_TIME")==null?
                        "":map.get("CREATE_TIME").toString();
                if (StringUtils.isNotEmpty(createTime)) {
                    StringBuilder sb = new StringBuilder(createTime);
                    createTime = sb.replace(0, 11, " ").toString();
                    map.put("CREATE_TIME", createTime);
                }
                returnList.add(map);
            }
        }
        request.setAttribute("appointList", returnList);

        return new ModelAndView("callnew/takeNo/pickUpParts"); 
    }
    /**
     *
     * 描述排队信息展示页面
     * @created 2019年03月17日 下午4:04:15
     * @param request
     * @return
     */
    @RequestMapping(params="callingLineInfo")
    public ModelAndView callingLineInfo(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> callingLineInfos = callService.callingLineInfo(filter);
        request.setAttribute("callingLineInfos", callingLineInfos);
        return new ModelAndView("callnew/takeNo/callingLineInfo");
    }

    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="lineInfoJson")
    public void lineInfoJson(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        String json = "";
        if(StringUtils.isEmpty(roomNo)){
            List<Map<String, Object>> callingLineInfos = callService.callingLineInfo(filter);
            json = JSON.toJSONString(callingLineInfos);
        }else if("H".equals(roomNo)){
            List<Map<String, Object>> callingLineInfos = callService.htCallingLineInfo(filter);
            json = JSON.toJSONString(callingLineInfos);
        }else if("X".equals(roomNo)){
            List<Map<String, Object>> callingLineInfos = callService.jsCallingLineInfo(filter,roomNo);
            json = JSON.toJSONString(callingLineInfos);
        }else{
            List<Map<String, Object>> callingLineInfos = callService.callingLineInfo(filter);
            json = JSON.toJSONString(callingLineInfos);
        }
//        List<Map<String, Object>> callingLineInfos = callService.callingLineInfo(filter);
//        String json = JSON.toJSONString(callingLineInfos);
        this.setJsonString(json, response);
    }

    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="yqyzlineInfoJson")
    public void yqyzlineInfoJson(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> callingLineInfos = callService.yqyzCallingLineInfo(filter);
        String json = JSON.toJSONString(callingLineInfos);
        this.setJsonString(json, response);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/30 9:56:00
     * @param
     * @return
     */
    @RequestMapping(params="xmtzlineInfoJson")
    public void xmtzlineInfoJson(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> callingLineInfos = callService.xmtzCallingLineInfo(filter);
        String json = JSON.toJSONString(callingLineInfos);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 测试页面
     * @author Water Guo
     * @created 2017-5-15 上午9:57:50
     * @param request
     * @return
     */
    @RequestMapping(params="goAppointTakeNoTest")
    public ModelAndView goAppointTakeNoTest(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/indexAppointTest");
    }
    /**
     * 
     * 描述 测试页面
     * @author Water Guo
     * @created 2017-5-15 上午10:00:28
     * @param request
     * @return
     */
    @RequestMapping(params="toTypeChooseTest")
    public ModelAndView toTypeChooseTest(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        String lc = dictionaryService.get("roomNo", roomNo).get("DIC_DESC").toString();
        List<Map<String,Object>> departList = callService.findDepartWait(roomNo);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("lc", lc);
        request.setAttribute("departList", departList);
        return new ModelAndView("call/takeNo/chooseTypeTest"); 
    }
    /**
     *     
     * 描述   
     * @author Danto Huang
     * @created 2016-6-17 上午9:50:38
     * @param request
     * @return
     */
    @RequestMapping(params="toTypeChoose")
    public ModelAndView toTypeChoose(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        String lc = dictionaryService.get("roomNo", roomNo).get("DIC_DESC").toString();
        List<Map<String,Object>> departList = callService.findDepartWait(roomNo);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("lc", lc);
        request.setAttribute("departList", departList);
        return new ModelAndView("call/takeNo/chooseType"); 
    }
    /**
     *     
     * 描述   
     * @author Danto Huang
     * @created 2016-6-17 上午9:50:38
     * @param request
     * @return
     */
    @RequestMapping(params="toYctbTypeChoose")
    public ModelAndView toYctbTypeChoose(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String macType = request.getParameter("macType");
        String departId = request.getParameter("departId");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        String lc = dictionaryService.get("roomNo", roomNo).get("DIC_DESC").toString();
        List<Map<String,Object>> departList = callService.findDepartWait(roomNo);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("macType", macType);
        request.setAttribute("departId", departId);
        request.setAttribute("lc", lc);
        request.setAttribute("departList", departList);
        return new ModelAndView("call/takeNo/chooseYctbType"); 
    }
    /**
     *     
     * 描述   
     * @author Danto Huang
     * @created 2016-6-17 上午9:50:38
     * @param request
     * @return
     */
    @RequestMapping(params="toYctbTypeChooseMacW")
    public ModelAndView toYctbTypeChooseMacW(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String departId = request.getParameter("departId");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        String lc = dictionaryService.get("roomNo", roomNo).get("DIC_DESC").toString();
        List<Map<String,Object>> departList = callService.findDepartWait(roomNo);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("departId", departId);
        request.setAttribute("lc", lc);
        request.setAttribute("departList", departList);
        return new ModelAndView("call/takeNo/chooseYctbTypeMacW"); 
    }

    /**
     * 描述:一企一证目录页面
     *
     * @author Madison You
     * @created 2019/12/26 15:19:00
     * @param
     * @return
     */
    @RequestMapping(params = "toYqyzTypeChooseMacW")
    public ModelAndView toYqyzTypeChooseMacW(HttpServletRequest request) {
        String roomNo = request.getParameter("roomNo");
        String ifMaterPrint = request.getParameter("ifMaterPrint");
        String departId = request.getParameter("departId");
        Map<String, Object> roomNoMap = dictionaryService.get("roomNo", roomNo);
        String lc = "";
        if (roomNoMap != null) {
            lc = (String) roomNoMap.get("DIC_DESC");
        }
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("ifMaterPrint", ifMaterPrint);
        request.setAttribute("departId", departId);
        request.setAttribute("lc", lc);
        return new ModelAndView("call/takeNo/chooseYqyzTypeMacW");
    }

    /**
     * 
     * 描述 身份证读取页
     * @author Danto Huang
     * @created 2016-1-16 下午10:17:14
     * @param request
     * @return
     */
    @RequestMapping(params="toReadCardAppoint")
    public ModelAndView toReadCardAppoint(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        if("H".equals(roomNo)||"I".equals(roomNo)){
            return new ModelAndView("call/takeNo/readCardAppointMacW");
        }else{
            return new ModelAndView("call/takeNo/readCardAppoint");
        }
    }
    /**
     * 
     * 描述 身份证读取页
     * @author Danto Huang
     * @created 2016-1-16 下午10:17:14
     * @param request
     * @return
     */
    @RequestMapping(params="toYctbReadCardAppoint")
    public ModelAndView toYctbReadCardAppoint(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/readCardAppointYctb");
    }
    /**
     * 
     * 描述 身份证读取页
     * @author Danto Huang
     * @created 2016-1-16 下午10:17:14
     * @param request
     * @return
     */
    @RequestMapping(params="toReadCardAppointMacW")
    public ModelAndView toReadCardAppointMacW(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/readCardAppointMacW");
    }
    /**
     * 
     * 描述 身份证读取页
     * @author Water Guo
     * @created 2017-5-11 上午10:40:40
     * @param request
     * @return
     */
    @RequestMapping(params="toReadCardAgain")
    public ModelAndView toReadCardAgain(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        if("H".equals(roomNo)||"I".equals(roomNo)){
            return new ModelAndView("call/takeNo/readCardAgainMacW");
        }else{
            return new ModelAndView("call/takeNo/readCardAgain");
        }
    }
    /**
     * 
     * 描述 身份证读取页
     * @author Water Guo
     * @created 2017-5-11 上午10:40:40
     * @param request
     * @return
     */
    @RequestMapping(params="toYctbReadCardAgain")
    public ModelAndView toYctbReadCardAgain(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/readCardAgainYctb");
    }
    /**
     * 
     * 描述 身份证读取页
     * @author Water Guo
     * @created 2017-5-11 上午10:40:40
     * @param request
     * @return
     */
    @RequestMapping(params="toReadCardAgainMacW")
    public ModelAndView toReadCardAgainMacW(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        request.setAttribute("roomNo", roomNo);
        return new ModelAndView("call/takeNo/readCardAgainMacW");
    }
    /**
     *     
     * 描述   
     * @author Danto Huang
     * @created 2016-6-17 上午9:50:38
     * @param request
     * @return
     */
    @RequestMapping(params="toAppointChoose")
    public ModelAndView toAppointDeptChoose(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
          filter.addFilter("Q_t.DATE_TIME_=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.CARD_=", cardNo);
        }
        filter.addSorted("t.DATE_TIME", "asc");
        List<Map<String,Object>> appointList = callService.findAppointmentDataBySqlFilter(filter);
        request.setAttribute("appointList", appointList);
        
        return new ModelAndView("call/takeNo/appointChooseView"); 
//        return new ModelAndView("call/takeNo/chooseDeptAppoint"); 
    }
    /**
     *     
     * 描述   
     * @author Danto Huang
     * @created 2016-6-17 上午9:50:38
     * @param request
     * @return
     */
    @RequestMapping(params="toAgainChoose")
    public ModelAndView toAgainChoose(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String roomNo = request.getParameter("roomNo");
        String cardNo = request.getParameter("cardNo");
        request.setAttribute("cardNo", cardNo);
        request.setAttribute("roomNo", roomNo);
        
        if(filter.getQueryParams().get("Q_t.CREATE_TIME_=")==null){
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
//            String time = "2017-05-08";
            time = "%"+time+"%";
          filter.addFilter("Q_t.CREATE_TIME_Like", time);
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.line_cardno_=", cardNo);
        }
        filter.addSorted("t.CREATE_TIME", "asc");
        List<Map<String,Object>> appointList = callService.findAgainDataBySqlFilter(filter);
        request.setAttribute("appointList", appointList);

//        return new ModelAndView("call/takeNo/appointChooseView"); 
        return new ModelAndView("call/takeNo/againChooseView"); 
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
        String cardNo = request.getParameter("cardNo");
        if(filter.getQueryParams().get("Q_t.DATE_TIME_=")==null){
          filter.addFilter("Q_t.DATE_TIME_=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        }
        if(cardNo!=null){
            filter.addFilter("Q_t.CARD_=", cardNo);
        }
        filter.addSorted("t.DATE_TIME", "asc");
        List<Map<String,Object>> list = callService.findAppointmentDataBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    /**
     *     
     * 描述   
     * @author Danto Huang
     * @created 2016-6-17 上午9:50:38
     * @param request
     * @return
     */
    @RequestMapping(params="toDeptChoose")
    public ModelAndView toDeptChoose(HttpServletRequest request){
        String roomNo = request.getParameter("roomNo");
        String isAppoint = request.getParameter("isAppoint");
        if(StringUtils.isEmpty(roomNo)){
            roomNo = "A";
        }
        if(StringUtils.isEmpty(isAppoint)){
            isAppoint = "0";
        }
        String lc = dictionaryService.get("roomNo", roomNo).get("DIC_DESC").toString();
        List<Map<String,Object>> departList = callService.findDepartWait(roomNo);
        request.setAttribute("roomNo", roomNo);
        request.setAttribute("isAppoint", isAppoint);
        request.setAttribute("lc", lc);
        request.setAttribute("departList", departList);
        return new ModelAndView("call/takeNo/chooseDept"); 
    }
    
    /**
     * 
     * 描述 进入办事部门明细
     * @author Danto Huang
     * @created 2016-1-16 下午10:17:14
     * @param request
     * @return
     */
    @RequestMapping(params="toChildDepart")
    public ModelAndView toChildDepart(HttpServletRequest request){
        //AjaxJson j = new AjaxJson();
        String departId = request.getParameter("departId");
        String roomNo = request.getParameter("roomNo");
        String departName = request.getParameter("departName");
        List<Map<String,Object>> childList = callService.findByParentId(departId);
        //j.setJsonString(JSON.toJSONString(childList));
        //return j;
        request.setAttribute("roomNo", roomNo);
        if(childList!=null&&childList.size()>0){
            request.setAttribute("childList", childList);
            return new ModelAndView("call/takeNo/chooseChildDept"); 
        }else{
            request.setAttribute("departId", departId);
            request.setAttribute("departName", departName);
            return new ModelAndView("call/takeNo/readCard");
        }
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
        return new ModelAndView("call/takeNo/appointmentTake");
    }

    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-6 上午9:00:46
     * @param request
     * @return
     */
    @RequestMapping(params="appointmentTakeNo")
    @ResponseBody
    public AjaxJson appointmentTakeNo(HttpServletRequest request){
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


        AjaxJson j = new AjaxJson();
        Map<String,Object> variables = new HashMap<String, Object>();
        String departId = appointment.get("DEPART_ID")==null?
                "":appointment.get("DEPART_ID").toString();
        String lineName = appointment.get("NAME")==null?
                "":appointment.get("NAME").toString();
        String lineCardNo = appointment.get("CARD")==null?
                "":appointment.get("CARD").toString();
        String zjlx = "SF";
        String isVip = "";
        String isAppoint = "1";
        String appointmentId = appointment.get("ID")==null?
                "":appointment.get("ID").toString();
        
        //取号部门所属业务大类
        String winNo = callService.getWinDepartNo(departId);
        if(winNo==null||!callService.isWinDepart(winNo)){
            j.setMsg("您选择的部门无对应办事窗口，请咨询前台！");
            j.setSuccess(false);
        }else if(callService.isWaiting(winNo, lineCardNo)){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else{
            variables.put("WIN_NO", winNo);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            String roomNo = callService.getRoomNoByDeptId(departId).get("BELONG_NO").toString();
            variables.put("ROOM_NO", roomNo);
            Properties properties = FileUtil.readProperties("project.properties");
            String mappingDep = properties.getProperty(departId);
            if(StringUtils.isEmpty(mappingDep)){
                variables.put("DEPART_ID", departId);
            }else{
                variables.put("DEPART_ID", mappingDep);
            }
            String lineNo = "";
            if(isVip!=null&&isVip!=""&&isVip.equals("1")){
                variables.put("IS_VIP", "1");
                lineNo = "V";
            }else if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                variables.put("IS_APPOINTMENT", "1");
                variables.put("IS_VIP", "0");
                lineNo = "Y";
            }else{
                variables.put("IS_VIP", "0");
                lineNo = roomNo;
            }
            int maxSn = callService.getMaxTakeSn(winNo);
            maxSn = maxSn+1;
            String lineSn = "";
            if(maxSn<10){
                lineSn = "00"+String.valueOf(maxSn);
            }else if(maxSn<100){
                lineSn = "0"+String.valueOf(maxSn);
            }else{
                lineSn = String.valueOf(maxSn);
            }
            variables.put("TAKE_SN", maxSn);
            lineNo = lineNo+winNo+lineSn;
            variables.put("LINE_NO", lineNo);
            variables.put("CALL_STATUS", "0");
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            variables.put("CREATE_TIME", createTime);
            String recordId = callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", null);
            String waitNum = callService.getWaitCountByWinNO(winNo);
            String careWinNo = callService.getCareWin(winNo);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", lineNo);
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            line.put("createTime", createTime);
            
            Sm4Utils sm4 = new Sm4Utils();
            //获取办事（取号）用户信息表中的手机号
            String cardNo = sm4.encryptDataCBC(lineCardNo);
            Map<String, Object> lineUser = newCallService.getByJdbc("T_BSFW_LINEUSERS", 
                    new String[]{"LINE_CARDNO"}, new Object[]{cardNo});
            if(lineUser==null){
                //将用户信息更新
                Map<String, Object> user = new HashMap<String, Object>();
                user.put("LINE_NAME", lineName);
                user.put("LINE_CARDNO", lineCardNo);
                newCallService.saveOrUpdate(user, "T_BSFW_LINEUSERS", null);
                line.put("userMobile", "");
            }else if(StringUtils.isEmpty(StringUtil.getString(lineUser.get("LINE_MOBILE")))){
                line.put("userMobile", "");
            }else{//存在用户信息
                String mobile = StringUtil.getString(lineUser.get("LINE_MOBILE"));//手机号中间4位数做隐藏
                String replaceMobile =mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
                line.put("userMobile",replaceMobile);
            }
            
            if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                Map<String,Object> appointmentn = new HashMap<String, Object>();
                appointmentn.put("NUM_ID", recordId);
                appointmentn.put("IS_TAKE", "1");
                appointmentn.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                callService.saveOrUpdate(appointmentn, "T_BESPEAK_APPLY", appointmentId);
            }
            j.setJsonString(JSON.toJSONString(line));
        }
        return j;
    
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-12-6 上午9:00:46
     * @param request
     * @return
     */
    @RequestMapping(params="againTakeNo")
    @ResponseBody
    public AjaxJson againTakeNo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> appointment = callService.getByJdbc(
                "T_CKBS_NUMRECORD", new String[] { "RECORD_ID" },
                new Object[] { entityId });
        String departName = callService
                .getByJdbc("t_msjw_system_department",
                        new String[] { "depart_id" },
                        new Object[] { appointment.get("depart_id") })
                .get("depart_name").toString();
        appointment.put("DEPART_NAME", departName);
        request.setAttribute("appointment", appointment);
        
        AjaxJson j = new AjaxJson();
        String departId = appointment.get("DEPART_ID")==null?
                "":appointment.get("DEPART_ID").toString();
        String isVip = "";
        String isAppoint = "0";
        
        //取号部门所属业务大类
        String winNo = callService.getWinDepartNo(departId);
        if(winNo==null||!callService.isWinDepart(winNo)){
            j.setMsg("您选择的部门无对应办事窗口，请咨询前台！");
            j.setSuccess(false);
//        }else if(callService.isWaiting(winNo, lineCardNo)){
//            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
//            j.setSuccess(false);
        }else{
            String roomNo = callService.getRoomNoByDeptId(departId).get("BELONG_NO").toString();
            Properties properties = FileUtil.readProperties("project.properties");
            String mappingDep = properties.getProperty(departId);
            if(StringUtils.isEmpty(mappingDep)){
            }else{
            }
            String lineNo = "";
//            if(isVip!=null&&isVip!=""&&isVip.equals("1")){
//                lineNo = "V";
//            }else if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
//                lineNo = "Y";
//            }else{
//                lineNo = roomNo;
//            }
//            int maxSn = callService.getMaxTakeSn(winNo);
//            maxSn = maxSn+1;
//            String lineSn = "";
//            if(maxSn<10){
//                lineSn = "00"+String.valueOf(maxSn);
//            }else if(maxSn<100){
//                lineSn = "0"+String.valueOf(maxSn);
//            }else{
//                lineSn = String.valueOf(maxSn);
//            }
//            lineNo = lineNo+winNo+lineSn;
            lineNo = appointment.get("LINE_NO")==null?
                    "":appointment.get("LINE_NO").toString();
            String waitNum = callService.getWaitCountByWinNO(winNo);
            String careWinNo = callService.getCareWin(winNo);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", lineNo);
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            j.setJsonString(JSON.toJSONString(line));
        }
        return j;
    
    }
    /**
     * 
     * 描述 生成取号结果
     * @author Danto Huang
     * @created 2016-1-16 下午10:17:14
     * @param request
     * @return
     */
    @RequestMapping(params="takeNo")
    @ResponseBody
    public AjaxJson takeNo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Map<String,Object> variables = new HashMap<String, Object>();
        String departId = request.getParameter("departId");
        String lineName = request.getParameter("lineName");
        String roomNo = request.getParameter("roomNo");
        String lineCardNo = request.getParameter("lineCardNo");
        String zjlx = request.getParameter("LINE_ZJLX");
        String isVip = request.getParameter("isVip");
        String isAppoint = request.getParameter("isAppoint");
        String appointmentId = request.getParameter("appointmentId");
        
        //取号部门所属业务大类
        String winNo = callService.getWinDepartNo(departId);
        if(winNo==null||!callService.isWinDepart(winNo)){
            j.setMsg("您选择的部门无对应办事窗口，请咨询前台！");
            j.setSuccess(false);
        }else if(callService.isWaiting(winNo, lineCardNo)){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else{
            variables.put("WIN_NO", winNo);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            if (StringUtils.isEmpty(roomNo)) {
                roomNo = callService.getRoomNoByDeptId(departId).get("BELONG_NO").toString();
            }
            variables.put("ROOM_NO", roomNo);
            Properties properties = FileUtil.readProperties("project.properties");
            String mappingDep = properties.getProperty(departId);
            if(StringUtils.isEmpty(mappingDep)){
                variables.put("DEPART_ID", departId);
            }else{
                variables.put("DEPART_ID", mappingDep);
            }
            String lineNo = "";
            if(isVip!=null&&isVip!=""&&isVip.equals("1")){
                variables.put("IS_VIP", "1");
                lineNo = "V";
            }else if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                variables.put("IS_APPOINTMENT", "1");
                variables.put("IS_VIP", "0");
                lineNo = "Y";
            }else{
                variables.put("IS_VIP", "0");
                lineNo = roomNo;
            }
            int maxSn = callService.getMaxTakeSn(winNo);
            maxSn = maxSn+1;
            String lineSn = "";
            if(maxSn<10){
                lineSn = "00"+String.valueOf(maxSn);
            }else if(maxSn<100){
                lineSn = "0"+String.valueOf(maxSn);
            }else{
                lineSn = String.valueOf(maxSn);
            }
            variables.put("TAKE_SN", maxSn);
            lineNo = lineNo+winNo+lineSn;
            variables.put("LINE_NO", lineNo);
            variables.put("CALL_STATUS", "0");
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            variables.put("CREATE_TIME", createTime);
            String datelineNo = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+lineNo;
            variables.put("DATE_LINE_NO", datelineNo);
//            boolean a = callService.isExist(lineNo);
            String recordId = callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", null);
            String waitNum = callService.getWaitCountByWinNO(winNo);
            String careWinNo = callService.getCareWin(winNo);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", lineNo);
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            line.put("createTime", createTime);
            if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                Map<String,Object> appointment = new HashMap<String, Object>();
                appointment.put("NUM_ID", recordId);
                appointment.put("IS_TAKE", "1");
                appointment.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                callService.saveOrUpdate(appointment, "T_BESPEAK_APPLY", appointmentId);
            }
            j.setJsonString(JSON.toJSONString(line));
        
//            if (callService.isExist(lineNo)) {
//                j.setMsg("取号失败请重新取号！");
//                j.setSuccess(false);
//            }else {}
        }
        return j;
    }
    /**
     * base64图片上传+取号
     * @param request
     * @return
     */
    @RequestMapping(params = "videoInputTakeNo")
    @ResponseBody
    public AjaxJson videoInputTakeNo(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        Map<String,Object> variables = new HashMap<String, Object>();
        String departId = request.getParameter("departId");
        String lineName = request.getParameter("lineName");
        String lineCardNo = request.getParameter("lineCardNo");
        String zjlx = request.getParameter("LINE_ZJLX");
        String isVip = request.getParameter("isVip");
        String isAppoint = request.getParameter("isAppoint");
        String appointmentId = request.getParameter("appointmentId");
        
        String imgStrAll = request.getParameter("base64Code"); 
        String[] jsonArr=imgStrAll.split(";"); 
        //String[] jsonArr = request.getParameterValues("base64Code"); 
        String base64Code = ""; 
        String fileIds = "";
        for (int i = 0; i < jsonArr.length; i++) {
            base64Code = jsonArr[i];
            if(StringUtils.isEmpty(base64Code)){
                continue;
            }
            //获取文件类型
//            String fileT = base64Code.substring(0,3);
//            base64Code = base64Code.substring(3,base64Code.length());
//            base64Code= base64Code.replace(fileT, "");
            // 定义上传目录的根路径
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath") ;
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = "attachFiles/image/"+currentDate;
            String uuId = UUIDGenerator.getUUID();
            // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(attachFileFolderPath+uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            uploadFullPath +="/"+uuId+".jpg";
//            uploadFullPath +="/"+uuId+"."+fileT;
            //把base64文件保存到本地
            FileUtil.decodeBase64File(base64Code, attachFileFolderPath+uploadFullPath);

            String fileName = uuId+".jpg";
//            String fileName = uuId+"."+fileT;
            String filePath = uploadFullPath;
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String fileType = FileUtil.getFileExtension(fileName);
            String uploadUserId = request.getParameter("uploadUserId"); 
            String uploadUserName = request.getParameter("uploadUserName"); 
            // 获取业务表名称
            String busTableName = request.getParameter("busTableName");
            // 获取业务表记录ID
            String busTableRecordId = request.getParameter("busRecordId");
            String attachKey = request.getParameter("attachKey");
            String FLOW_EXEID= request.getParameter("FLOW_EXEID");
            String FLOW_TASKID = request.getParameter("FLOW_TASKID");
            String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");
            String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");
            String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");
            String SFHZD = "-1";

            Map<String, Object> fileAttach = new HashMap<String, Object>();
            fileAttach.put("FILE_NAME", fileName);
            fileAttach.put("FILE_PATH", filePath);
            fileAttach.put("CREATE_TIME", createTime);
            fileAttach.put("FILE_TYPE", fileType);
            fileAttach.put("UPLOADER_ID", uploadUserId);
            fileAttach.put("UPLOADER_NAME", uploadUserName);
            fileAttach.put("BUS_TABLENAME", busTableName);
            fileAttach.put("BUS_TABLERECORDID", busTableRecordId);
            fileAttach.put("ATTACH_KEY", attachKey);
            fileAttach.put("FLOW_EXEID", FLOW_EXEID);
            fileAttach.put("FLOW_TASKID", FLOW_TASKID);
            fileAttach.put("FLOW_TASKNAME", FLOW_TASKNAME);
            fileAttach.put("UPLOADER_DEPID", UPLOADER_DEPID);
            fileAttach.put("UPLOADER_DEPNAME", UPLOADER_DEPNAME);
            fileAttach.put("SFHZD", SFHZD);
            //fileAttach.put("FILE_CONTENT", FileUtil.convertFileToBytes(uploadFile));
//            if(imgFileTypes.contains(fileType)){
                fileAttach.put("IS_IMG","1");
//            }else{
//                fileAttach.put("IS_IMG","-1");
//            }
//            FileAttachService fileAttachService = (FileAttachService) AppUtil
//                    .getBean("fileAttachService");
            String fileId = fileAttachService
                    .saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
            fileIds=fileIds+fileId+";";
//            Map<String, Object> result = new HashMap<String, Object>();
//            result.put("result", true);
//            result.put("fileId", fileId);
//            result.put("filePath", filePath);
//            result.put("fileName", fileName);
//            result.put("fileType", fileType);
//            result.put("isImg", fileAttach.get("IS_IMG"));
//            if (StringUtils.isNotEmpty(attachKey)) {
//                result.put("attachKey", attachKey);
//            }
//            resultlList.add(result);
        }
//        String json = JSONArray.toJSONString(resultlList);
//        this.setJsonString(json, response);
        
        //取号

        //取号部门所属业务大类
        String winNo = callService.getWinDepartNo(departId);
        if(winNo==null||!callService.isWinDepart(winNo)){
            j.setMsg("您选择的部门无对应办事窗口，请咨询前台！");
            j.setSuccess(false);
        }else if(callService.isWaiting(winNo, lineCardNo)){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else{
            variables.put("WIN_NO", winNo);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            String roomNo = callService.getRoomNoByDeptId(departId).get("BELONG_NO").toString();
            variables.put("ROOM_NO", roomNo);
            Properties properties = FileUtil.readProperties("project.properties");
            String mappingDep = properties.getProperty(departId);
            if(StringUtils.isEmpty(mappingDep)){
                variables.put("DEPART_ID", departId);
            }else{
                variables.put("DEPART_ID", mappingDep);
            }
            String lineNo = "";
            if(isVip!=null&&isVip!=""&&isVip.equals("1")){
                variables.put("IS_VIP", "1");
                lineNo = "V";
            }else if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                variables.put("IS_APPOINTMENT", "1");
                variables.put("IS_VIP", "0");
                lineNo = "Y";
            }else{
                variables.put("IS_VIP", "0");
                lineNo = roomNo;
            }
            int maxSn = callService.getMaxTakeSn(winNo);
            maxSn = maxSn+1;
            String lineSn = "";
            if(maxSn<10){
                lineSn = "00"+String.valueOf(maxSn);
            }else if(maxSn<100){
                lineSn = "0"+String.valueOf(maxSn);
            }else{
                lineSn = String.valueOf(maxSn);
            }
            variables.put("TAKE_SN", maxSn);
            lineNo = lineNo+winNo+lineSn;
            variables.put("LINE_NO", lineNo);
            variables.put("CALL_STATUS", "0");
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String datelineNo = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+lineNo;
            variables.put("DATE_LINE_NO", datelineNo);
            variables.put("FILEIDS", fileIds);
            variables.put("TAKENOWAY", "2");
//            boolean a = callService.isExist(lineNo);
            SysUser curUser = AppUtil.getLoginUser();
            String takeNoUser = curUser.getFullname();
            variables.put("TAKE_NO_USER",takeNoUser );
            String recordId = callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", null);
            String waitNum = callService.getWaitCountByWinNO(winNo);
            String careWinNo = callService.getCareWin(winNo);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", lineNo);
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                Map<String,Object> appointment = new HashMap<String, Object>();
                appointment.put("NUM_ID", recordId);
                appointment.put("IS_TAKE", "1");
                appointment.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                callService.saveOrUpdate(appointment, "T_BESPEAK_APPLY", appointmentId);
            }
            j.setJsonString(JSON.toJSONString(line));
        
//            if (callService.isExist(lineNo)) {
//                j.setMsg("取号失败请重新取号！");
//                j.setSuccess(false);
//            }else {}
        }
        return j;
        
    }
    /**
     * 继续取号
     * @param request
     * @return
     */
    @RequestMapping(params = "videoInputTakeNoContinue")
    @ResponseBody
    public AjaxJson videoInputTakeNoContinue(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        Map<String,Object> variables = new HashMap<String, Object>();
        String departId = request.getParameter("departId");
        String lineName = request.getParameter("lineName");
        String lineCardNo = request.getParameter("lineCardNo");
        String zjlx = request.getParameter("LINE_ZJLX");
        String isVip = request.getParameter("isVip");
        String isAppoint = request.getParameter("isAppoint");
        String appointmentId = request.getParameter("appointmentId");
        String uploadUserId = request.getParameter("uploadUserId"); 
        String uploadUserName = request.getParameter("uploadUserName"); 
        String curWin = request.getParameter("winNo"); 
        String takeNoWay = request.getParameter("takeNoWay"); 
        
        String imgStrAll = request.getParameter("base64Code"); 
        String[] jsonArr=imgStrAll.split(";"); 
        //String[] jsonArr = request.getParameterValues("base64Code"); 
        String base64Code = ""; 
        String fileIds = "";
        for (int i = 0; i < jsonArr.length; i++) {
            base64Code = jsonArr[i];
            if(StringUtils.isEmpty(base64Code)){
                continue;
            }
            //获取文件类型
//            String fileT = base64Code.substring(0,3);
//            base64Code = base64Code.substring(3,base64Code.length());
//            base64Code= base64Code.replace(fileT, "");
            // 定义上传目录的根路径
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath") ;
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = "attachFiles/image/"+currentDate;
            String uuId = UUIDGenerator.getUUID();
            // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(attachFileFolderPath+uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            uploadFullPath +="/"+uuId+".jpg";
//            uploadFullPath +="/"+uuId+"."+fileT;
            //把base64文件保存到本地
            FileUtil.decodeBase64File(base64Code, attachFileFolderPath+uploadFullPath);

            String fileName = uuId+".jpg";
//            String fileName = uuId+"."+fileT;
            String filePath = uploadFullPath;
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String fileType = FileUtil.getFileExtension(fileName);
            // 获取业务表名称
            String busTableName = request.getParameter("busTableName");
            // 获取业务表记录ID
            String busTableRecordId = request.getParameter("busRecordId");
            String attachKey = request.getParameter("attachKey");
            String FLOW_EXEID= request.getParameter("FLOW_EXEID");
            String FLOW_TASKID = request.getParameter("FLOW_TASKID");
            String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");
            String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");
            String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");
            String SFHZD = "-1";

            Map<String, Object> fileAttach = new HashMap<String, Object>();
            fileAttach.put("FILE_NAME", fileName);
            fileAttach.put("FILE_PATH", filePath);
            fileAttach.put("CREATE_TIME", createTime);
            fileAttach.put("FILE_TYPE", fileType);
            fileAttach.put("UPLOADER_ID", uploadUserId);
            fileAttach.put("UPLOADER_NAME", uploadUserName);
            fileAttach.put("BUS_TABLENAME", busTableName);
            fileAttach.put("BUS_TABLERECORDID", busTableRecordId);
            fileAttach.put("ATTACH_KEY", attachKey);
            fileAttach.put("FLOW_EXEID", FLOW_EXEID);
            fileAttach.put("FLOW_TASKID", FLOW_TASKID);
            fileAttach.put("FLOW_TASKNAME", FLOW_TASKNAME);
            fileAttach.put("UPLOADER_DEPID", UPLOADER_DEPID);
            fileAttach.put("UPLOADER_DEPNAME", UPLOADER_DEPNAME);
            fileAttach.put("SFHZD", SFHZD);
            //fileAttach.put("FILE_CONTENT", FileUtil.convertFileToBytes(uploadFile));
//            if(imgFileTypes.contains(fileType)){
                fileAttach.put("IS_IMG","1");
//            }else{
//                fileAttach.put("IS_IMG","-1");
//            }
//            FileAttachService fileAttachService = (FileAttachService) AppUtil
//                    .getBean("fileAttachService");
            String fileId = fileAttachService
                    .saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
            fileIds=fileIds+fileId+";";
//            Map<String, Object> result = new HashMap<String, Object>();
//            result.put("result", true);
//            result.put("fileId", fileId);
//            result.put("filePath", filePath);
//            result.put("fileName", fileName);
//            result.put("fileType", fileType);
//            result.put("isImg", fileAttach.get("IS_IMG"));
//            if (StringUtils.isNotEmpty(attachKey)) {
//                result.put("attachKey", attachKey);
//            }
//            resultlList.add(result);
        }
//        String json = JSONArray.toJSONString(resultlList);
//        this.setJsonString(json, response);
        
        //取号

        SqlFilter filter = new SqlFilter(request);
        String username = AppUtil.getLoginUser().getUsername();
        Map<String, Object> winInfo = callService.getWinInfoByUsername(username);
        if(winInfo!=null){
            filter.addFilter("Q_T.OPERATOR_ID_=", AppUtil.getLoginUser().getUserId());
        }else if(!AppUtil.getLoginUser().getUsername().equals("admin")){
            filter.addFilter("Q_T.CUR_WIN_=", "0");
        }
        filter.addFilter("Q_T.CALL_STATUS_!=", "0");
        filter.addSorted("DECODE(T.OPER_TIME,NULL,0,1)", "DESC");
        filter.addSorted("T.OPER_TIME", "DESC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String, Object>> list = callService.findQueuingBySqlFilter(filter);
        String lastIdNum = list.get(0).get("LINE_CARDNO").toString();
        //取号部门所属业务大类
        String winNo = callService.getWinDepartNo(departId);
        if(winNo==null||!callService.isWinDepart(winNo)){
            j.setMsg("您选择的部门无对应办事窗口，请咨询前台！");
            j.setSuccess(false);
        }else if(callService.isWaiting(winNo, lineCardNo)){
            j.setMsg("您已在当前窗口取号并在等待中，请勿重复取号！");
            j.setSuccess(false);
        }else if(!lineCardNo.equals(lastIdNum)){
            j.setMsg("非上一取号人不可继续取号！");
            j.setSuccess(false);
        }else{
            variables.put("WIN_NO", winNo);
            variables.put("LINE_ZJLX", zjlx);
            variables.put("LINE_NAME", lineName);
            variables.put("LINE_CARDNO", lineCardNo);
            String roomNo = callService.getRoomNoByDeptId(departId).get("BELONG_NO").toString();
            variables.put("ROOM_NO", roomNo);
            Properties properties = FileUtil.readProperties("project.properties");
            String mappingDep = properties.getProperty(departId);
            if(StringUtils.isEmpty(mappingDep)){
                variables.put("DEPART_ID", departId);
            }else{
                variables.put("DEPART_ID", mappingDep);
            }
            String lineNo = "";
            if(isVip!=null&&isVip!=""&&isVip.equals("1")){
                variables.put("IS_VIP", "1");
                lineNo = "V";
            }else if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                variables.put("IS_APPOINTMENT", "1");
                variables.put("IS_VIP", "0");
                lineNo = "Y";
            }else{
                variables.put("IS_VIP", "0");
                lineNo = roomNo;
            }
            int maxSn = callService.getMaxTakeSn(winNo);
            maxSn = maxSn+1;
            String lineSn = "";
            if(maxSn<10){
                lineSn = "00"+String.valueOf(maxSn);
            }else if(maxSn<100){
                lineSn = "0"+String.valueOf(maxSn);
            }else{
                lineSn = String.valueOf(maxSn);
            }
            variables.put("TAKE_SN", maxSn);
            lineNo = lineNo+winNo+lineSn;
            variables.put("LINE_NO", lineNo);
            variables.put("CALL_STATUS", "0");
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String datelineNo = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+lineNo;
            variables.put("DATE_LINE_NO", datelineNo);
            variables.put("FILEIDS", fileIds);
//            variables.put("TAKENOWAY", "3");
            variables.put("TAKENOWAY", takeNoWay);
            variables.put("CALL_STATUS", "6");
//            variables.put("OPERATOR", uploadUserName);
//            variables.put("OPERATOR_ID", uploadUserId);
            variables.put("CUR_WIN", curWin);
//            boolean a = callService.isExist(lineNo);
            SysUser curUser = AppUtil.getLoginUser();
            String takeNoUser = curUser.getFullname();
            variables.put("TAKE_NO_USER",takeNoUser );
            String recordId = callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", null);
            String waitNum = callService.getWaitCountByWinNO(winNo);
            String careWinNo = callService.getCareWin(winNo);
            Map<String,Object> line = new HashMap<String, Object>();
            line.put("lineNo", lineNo);
            line.put("waitNum", waitNum);
            line.put("winNo", careWinNo);
            line.put("roomNo", roomNo);
            if(isAppoint!=null&&isAppoint!=""&&isAppoint.equals("1")){
                Map<String,Object> appointment = new HashMap<String, Object>();
                appointment.put("NUM_ID", recordId);
                appointment.put("IS_TAKE", "1");
                appointment.put("TAKE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                callService.saveOrUpdate(appointment, "T_BESPEAK_APPLY", appointmentId);
            }
            j.setJsonString(JSON.toJSONString(line));
        
//            if (callService.isExist(lineNo)) {
//                j.setMsg("取号失败请重新取号！");
//                j.setSuccess(false);
//            }else {}
        }
        return j;
        
    }
    /**
     * 
     * 描述 转跳取号信息页
     * @author Kester Chen
     * @created 2018-4-3 下午3:11:03
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="noInfo")
    public ModelAndView noInfo(HttpServletRequest request){
        String selectTaskIds = request.getParameter("selectTaskIds");
        
        Map<String,Object> flowResult = null;
        flowResult = callService.getByJdbc("T_CKBS_NUMRECORD",
                new String[]{"RECORD_ID"},new Object[]{selectTaskIds});
        StringBuffer files=new StringBuffer();
        String ids = flowResult.get("FILEIDS")==null?"":flowResult.get("FILEIDS").toString();
        List<Map<String,Object>> fileList=fileAttachService.findListForResult(ids);
        for(Map<String, Object> filemap : fileList){
            files.append("<p style=\"margin-left: 5px; margin-right: 5px;line-height: 20px;\">");
            files.append("<a style=\"color: blue;\" href=\"javascript:AppUtil.downLoadFile(\'");
            files.append(filemap.get("FILE_ID")).append("\');\">");
            files.append(filemap.get("FILE_NAME")).append("</a>");

            files.append("<a href=\"javascript:void(0);\"  onclick=\"previewFile('");
            files.append(filemap.get("FILE_PATH"));
            files.append("');\" ><font color=\"red\">   &nbsp;&nbsp;&nbsp;预览</font></a>");
            files.append("</p>");
//            log.info(files);
        }
        flowResult.put("FILES", files.toString());
    
        String json = JSON.toJSONString(flowResult);
        request.setAttribute("resultJson", json);
        request.setAttribute("flowResult", flowResult);
        
        return new ModelAndView("call/queuing/noInfo");
    }
    
    /**
     * 
     * 描述 叫号
     * @author Danto Huang
     * @created 2016-1-20 下午3:38:12
     * @param request
     * @return
     */
    @RequestMapping(params="callQueuing")
    @ResponseBody
    public AjaxJson callQueuing(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String type = request.getParameter("type");
        String winNo = request.getParameter("winNo");
        Map<String,Object> variables = new HashMap<String, Object>();
        String voice = "";
        Map<String,Object> winScreen = callService
                .getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "WIN_NO" },
                        new Object[] { winNo });
        String ledinfo = winScreen.get("DEPARTINFO").toString();
        String wordNum = winScreen.get("WORD_NUM").toString();
        if(type.equals("callNum")){
            Map<String,Object> call = new HashMap<String, Object>();
            call.put("CALL_STATUS", "6");
            call.put("CUR_WIN", winNo);
            call.put("OPERATOR", AppUtil.getLoginUser().getFullname());
            call.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
            String recordId = request.getParameter("recordId");
            String lineNo = request.getParameter("lineNo");
            if("115".equals(winNo)){//更改115号窗口叫号播报信息
                voice = "请"+lineNo+"号，到台胞接待室";
            }else{
                voice = "请"+lineNo+"号，到"+winNo+"号窗口";                
            }
            if(wordNum.equals("8")){
                ledinfo = ledinfo+"#   请"+lineNo+"号";
            }else if(wordNum.equals("12")){
                ledinfo = ledinfo+"#       请"+lineNo+"号";
            }
            variables.put("RECORD_ID", recordId);
            variables.put("LINE_NO", lineNo);
            variables.put("WIN_NO", winNo);
            variables.put("VOICE", voice);
            variables.put("LEDINFO", ledinfo);
            callService.saveOrUpdate(call, "T_CKBS_NUMRECORD", recordId);
        }else if(type.equals("CallingWait")){
            if(wordNum.equals("8")){
                ledinfo = ledinfo+"#     请稍候";
            }else if(wordNum.equals("12")){
                ledinfo = ledinfo+"#         请稍候";
            }
            voice = "请稍候";
            variables.put("WIN_NO", winNo);
            variables.put("LEDINFO", ledinfo); 
            variables.put("VOICE", voice);     
        }else if(type.equals("CallingService")){
            if(wordNum.equals("8")){
                ledinfo = ledinfo+"#     欢迎您";
            }else if(wordNum.equals("12")){
                ledinfo = ledinfo+"#         欢迎您";
            }
            voice = "欢迎您";
            variables.put("WIN_NO", winNo);
            variables.put("LEDINFO", ledinfo);  
            variables.put("VOICE", voice);         
        }
        String roomNo = callService.getRoomNoByWinNo(winNo);
        variables.put("ROOM_NO", roomNo);
        variables.put("TYPE", type);  
        variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("OPERATOR", AppUtil.getLoginUser().getFullname());
        callService.saveOrUpdate(variables, "T_CKBS_CALLWAIT", null);

        j.setMsg(voice);
        return j;
    }
    

    /**
     * 
     * 描述 跳转评价页面
     * @author Danto Huang
     * @created 2016-1-16 下午2:04:37
     * @param request
     * @return
     */
    @RequestMapping(params="evaluateInfo")
    public ModelAndView evaluateInfo(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        request.setAttribute("recordId", recordId);
        return new ModelAndView("call/queuing/evaluate");
    }
    
    /**
     * 
     * 描述 评价结果
     * @author Danto Huang
     * @created 2016-1-20 下午3:38:12
     * @param request
     * @return
     */
    @RequestMapping(params="evaluate")
    @ResponseBody
    public AjaxJson evaluate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        String eval = request.getParameter("eval");
        Map<String, Object> variables = new HashMap<String, Object>();
        String evaluateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        variables.put("EVALUATE", eval);
        variables.put("EVALUATETIME", evaluateTime);
        //variables.put("CALL_STATUS", "6");
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("完成评价");
        return j;
    }
    
    /**
     * 
     * 描述 跳转管理员取号
     * @author Danto Huang
     * @created 2016-1-16 下午2:04:37
     * @param request
     * @return
     */
    @RequestMapping(params="takeNoAssist")
    public ModelAndView takeNoAssist(HttpServletRequest request){
        return new ModelAndView("call/takeNo/assistView");
    }

    /**
     * easyui AJAX请求数据(管理员取号)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "assistDatagrid")
    public void assistDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //String username = AppUtil.getLoginUser().getUsername();
        filter.addSorted("t.IS_VIP", "desc");
        filter.addSorted("t.IS_TOP", "desc");
        filter.addSorted("t.TOP_TIME", "desc");
        filter.addSorted("t.CREATE_TIME", "asc");
        if(!filter.getQueryParams().containsKey("Q_T.CALL_STATUS_=")){
            filter.addFilter("Q_T.CALL_STATUS_=", "0");
        }
        List<Map<String, Object>> list = callService.findNolistBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-2-29 下午4:42:48
     * @param request
     * @return
     */
    @RequestMapping(params="assistInfo")
    public ModelAndView assistInfo(HttpServletRequest request){
        return new ModelAndView("call/takeNo/assistInfo");
    }
    /**
     * 
     * 描述
     * @author Water Guo
     * @created 2016-11-08 下午4:42:48
     * @param request
     * @return
     */
    @RequestMapping(params="toTopReason")
    public ModelAndView toTopReason(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        request.setAttribute("entityId", entityId);
        return new ModelAndView("call/takeNo/toTopReason");
    }

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-2-29 下午5:23:14
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="toTop")
    @ResponseBody
    public AjaxJson toTop(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("entityId");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("IS_TOP", "1");
        variables.put("TOP_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("置顶成功");
        return j;
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-2-29 下午5:23:14
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="cancelTop")
    @ResponseBody
    public AjaxJson cancelTop(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("entityId");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("IS_TOP", "0");
        variables.put("TOP_TIME", "");
        variables.put("TOTOPREASON", "");
        callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
        j.setMsg("置顶成功");
        return j;
    }
    
    /**
     * 
     * 描述  窗口单位管理
     * @author Danto Huang
     * @created 2016-3-16 下午2:44:39
     * @param request
     * @return
     */
    @RequestMapping(params="winDpeartView")
    public ModelAndView winDpeartView(HttpServletRequest request){
        return new ModelAndView("call/depart/departView");
    }
    
    /**
     * easyui AJAX请求数据(窗口单位)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "departDatagrid")
    public void departDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.BELONG_NO", "desc");
        filter.addSorted("t.IS_TAKE", "desc");
        filter.addSorted("to_number(t.TREE_SN)", "asc");
        List<Map<String, Object>> list = callService.findWinDepartBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-3-16 下午3:19:33
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="saveWinDepart")
    @ResponseBody
    public AjaxJson saveWinDepart(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String departIds = request.getParameter("departIds");
        String departNames = request.getParameter("departNames");
        String[] departId = departIds.split(",");
        String[] departName = departNames.split(",");
        for(int i=0;i<departId.length;i++){
            Map<String, Object> depart = callService.getByJdbc("T_CKBS_DEPART", new String[] { "DEPART_ID" },
                    new Object[] { departId[i] });
            if(depart!=null){
                continue;
            }
            Map<String,Object> variable = new HashMap<String, Object>();
            variable.put("DEPART_ID", departId[i]);
            variable.put(
                    "TREE_SN",
                    callService
                            .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                                    new Object[] { departId[i] }).get("TREE_SN").toString());
            
            variable.put("DEPART_NAME", departName[i]);
            variable.put("IS_TAKE", "1");
            callService.saveOrUpdate(variable, "T_CKBS_DEPART", null);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述 修改取号状态
     * @author Danto Huang
     * @created 2016-3-16 下午4:11:31
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="isTake")
    @ResponseBody
    public AjaxJson isTake(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_TAKE", statu);
        for(int i=0;i<entityId.length;i++){
            callService.saveOrUpdate(variable, "T_CKBS_DEPART", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     *
     * 删除叫号窗口信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelWinDepart")
    @ResponseBody
    public AjaxJson multiDelWinDepart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callService.remove("T_CKBS_DEPART","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 窗口单位记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-3-16 下午4:21:22
     * @param request
     * @return
     */
    @RequestMapping(params="winDepartInfo")
    public ModelAndView winDepartInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> deptInfo = callService.getByJdbc("T_CKBS_DEPART",
                new String[] { "RECORD_ID" }, new Object[] { entityId });
        request.setAttribute("deptInfo", deptInfo);
        return new ModelAndView("call/depart/departInfo");
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-3-16 下午4:39:35
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="departBelong")
    @ResponseBody
    public AjaxJson departBelong(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        String belong = request.getParameter("BELONG_NO");
        String icon = request.getParameter("ICON_NO");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("BELONG_NO", belong);
        variable.put("ICON_NO", icon);
        callService.saveOrUpdate(variable, "T_CKBS_DEPART", entityId);
        j.setMsg("操作成功");
        return j;
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-7-5 上午10:47:46
     * @param request
     * @return
     */
    @RequestMapping(params="childDepartManage")
    public ModelAndView childDepartManage(HttpServletRequest request){
        String departId = request.getParameter("departId");
        request.setAttribute("departId", departId);
        return new ModelAndView("call/depart/childDepartView");
    }
    
    /**
     * easyui AJAX请求数据(窗口单位)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "childDepartDatagrid")
    public void childDepartDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String parentId = request.getParameter("parentId");
        filter.addFilter("Q_t.PARENT_ID_=", parentId);
        filter.addSorted("t.IS_TAKE", "desc");
        filter.addSorted("to_number(t.TREE_SN)", "asc");
        List<Map<String, Object>> list = callService.findChildDepartBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-7-5 下午3:05:12
     * @param request
     * @return
     */
    @RequestMapping(params="saveChildDepart")
    @ResponseBody
    public AjaxJson saveChildDepart(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String departIds = request.getParameter("departIds");
        String departNames = request.getParameter("departNames");
        String parentId = request.getParameter("parentId");
        String[] departId = departIds.split(",");
        String[] departName = departNames.split(",");
        for(int i=0;i<departId.length;i++){
            Map<String, Object> depart = callService.getByJdbc("T_CKBS_DEPART_CHILD", new String[] { "DEPART_ID" },
                    new Object[] { departId[i] });
            if(depart!=null){
                continue;
            }
            Map<String,Object> variable = new HashMap<String, Object>();
            variable.put("DEPART_ID", departId[i]);
            variable.put(
                    "TREE_SN",
                    callService
                            .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                                    new Object[] { departId[i] }).get("TREE_SN").toString());
            
            variable.put("DEPART_NAME", departName[i]);
            variable.put("IS_TAKE", "1");
            variable.put("PARENT_ID", parentId);
            callService.saveOrUpdate(variable, "T_CKBS_DEPART_CHILD", null);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     *
     * 删除叫号窗口信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelChildDepart")
    @ResponseBody
    public AjaxJson multiDelChildDepart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callService.remove("T_CKBS_DEPART_CHILD","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 窗口子单位记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 
     * 描述 修改取号状态
     * @author Danto Huang
     * @created 2016-3-16 下午4:11:31
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="isChildTake")
    @ResponseBody
    public AjaxJson isChildTake(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_TAKE", statu);
        for(int i=0;i<entityId.length;i++){
            callService.saveOrUpdate(variable, "T_CKBS_DEPART_CHILD", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    /**
     * 
     * 描述跳转更换窗口
     * @author Danto Huang
     * @created 2016-3-24 下午4:05:43
     * @param request
     * @return
     */
    @RequestMapping(params="goChangeWin")
    public ModelAndView goChangeWin(HttpServletRequest request){
        String winNo = request.getParameter("winNo");
        request.setAttribute("winNo", winNo);
        return new ModelAndView("call/queuing/changeWin");
    }
    
    /**
     * 
     * 描述 更换窗口
     * @author Danto Huang
     * @created 2016-2-26 下午4:22:00
     * @param request
     * @return
     */
    @RequestMapping(params="changeWin")
    @ResponseBody
    public AjaxJson changeWin(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String newWin = request.getParameter("newWin");
        String oldWin = request.getParameter("oldWin");
        String username = AppUtil.getLoginUser().getUsername();
        Map<String,Object> variables = new HashMap<String, Object>();
        Map<String, Object> winInfo = callService.getByJdbc("T_CKBS_SERVICEWIN", new String[] { "WIN_NO", "USERNAME" },
                new Object[] { oldWin, username });
        variables.put("WIN_NO", newWin);
        String screeNo = callService.getScreenNoByWinNo(newWin);
        variables.put("SCREEN_NO", screeNo);
        callService.saveOrUpdate(variables, "T_CKBS_SERVICEWIN", winInfo.get("WIN_ID").toString());
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述 修改取号状态
     * @author Danto Huang
     * @created 2016-3-16 下午4:11:31
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="isContinue")
    @ResponseBody
    public AjaxJson isContinue(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_CONTINUE", statu);
        for(int i=0;i<entityId.length;i++){
            callService.saveOrUpdate(variable, "T_CKBS_SERVICEWIN", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述 修改取号状态
     * @author Danto Huang
     * @created 2016-3-16 下午4:11:31
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="isUse")
    @ResponseBody
    public AjaxJson isUse(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_USE", statu);
        for(int i=0;i<entityId.length;i++){
            callService.saveOrUpdate(variable, "T_CKBS_SERVICEWIN", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }   

    /**
     * 
     * 描述 窗口屏绑定关系管理
     * @author Danto Huang
     * @created 2016-1-13 上午11:42:51
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="winScreenView")
    public ModelAndView winScreenView(HttpServletRequest request,HttpServletResponse response){
        return new ModelAndView("call/win/winScreenView");
    }
    
    /**
     * easyui AJAX请求数据(窗口屏绑定关系)
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "winScreenDatagrid")
    public void winScreenDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.win_no", "asc");
        List<Map<String, Object>> list = callService.findWinScreenBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }    

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2016-3-16 下午4:21:22
     * @param request
     * @return
     */
    @RequestMapping(params="winScreenInfo")
    public ModelAndView winScreenInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> winScreenInfo = callService.getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "RECORD_ID"},
                new Object[] { entityId });
        request.setAttribute("winScreenInfo", winScreenInfo);
        return new ModelAndView("call/win/winScreenInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateWinScreen")
    @ResponseBody
    public AjaxJson saveOrUpdateWinScreen(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = callService.saveOrUpdate(variables, "T_CKBS_WIN_SCREEN", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的窗口屏绑定关系记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的窗口屏绑定关系记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     *
     * 删除窗口屏绑定关系信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelWinScreen")
    @ResponseBody
    public AjaxJson multiDelWinScreen(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callService.remove("T_CKBS_WIN_SCREEN","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 窗口单位记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
    *
    * 删除窗口屏绑定关系信息
    * @param request 传入参数
    * @return 返回值AjaxJson
    */
    @RequestMapping(params = "getScreenNo")
    @ResponseBody
    public AjaxJson getScreenNo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String winNo = request.getParameter("winNo");
        String screenNo = callService.getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "WIN_NO" }, new Object[] { winNo })
                .get("SCREEN_NO").toString();
        j.setMsg(screenNo);
        return j;
    }
    
    /**
    *
    * 删除窗口屏绑定关系信息
    * @param request 传入参数
    * @return 返回值AjaxJson
    */
    @RequestMapping(params = "winScreenUse")
    @ResponseBody
    public AjaxJson winScreenUse(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] ids = entityIds.split(",");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("USE_STATUS", statu);
        for(int i=0;i<ids.length;i++){
            callService.saveOrUpdate(variables, "T_CKBS_WIN_SCREEN", ids[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述   跳转叫号记录列表页面
     * @author Danto Huang
     * @created 2016-5-24 上午8:45:52
     * @param request
     * @return
     */
    @RequestMapping(params="goCallRecord")
    public ModelAndView goCallRecord(HttpServletRequest request){
        return new ModelAndView("call/queuing/callRecord");
    }
    
    /**
     * 
     * 描述   叫号记录列表
     * @author Danto Huang
     * @created 2016-5-24 上午8:48:11
     * @param request
     * @param response
     */
    @RequestMapping(params="callRecordGrid")
    public void callRecordGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.create_time", "desc");
        List<Map<String, Object>> list = callService.getCallRecord(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述   部门业务关系对应
     * @author Danto Huang
     * @created 2016-6-22 上午10:46:49
     * @param request
     * @return
     */
    @RequestMapping(params="goDepartTobus")
    public ModelAndView goDepartTobus(HttpServletRequest request){
        return new ModelAndView("call/depart/departBusView");
    }

    /**
     * 
     * 描述   叫号记录列表
     * @author Danto Huang
     * @created 2016-5-24 上午8:48:11
     * @param request
     * @param response
     */
    @RequestMapping(params="departBusDatagrid")
    public void departBusDatagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.bus_code", "desc");
        List<Map<String, Object>> list = callService.getDepartBusList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-6-22 下午12:00:41
     * @param request
     * @return
     */
    @RequestMapping(params="departBusInfo")
    public ModelAndView departBusInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> departBus = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId)) {
            departBus = callService.getByJdbc("T_CKBS_DEPART_BUS",
                    new String[] { "RECORD_ID" }, new Object[] { entityId });
        }
        request.setAttribute("departBus", departBus);
        return new ModelAndView("call/depart/departBusInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateDepartBus")
    @ResponseBody
    public AjaxJson saveOrUpdateDepartBus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId) || entityId.equals("undefined")) {
            String departId = request.getParameter("DEPART_ID");
            Map<String, Object> bus = callService.getByJdbc(
                    "T_CKBS_DEPART_BUS", new String[] { "DEPART_ID" },
                    new Object[] { departId });
            if (bus != null) {
                entityId = bus.get("RECORD_ID").toString();
            }
        }
        callService.saveOrUpdate(variables, "T_CKBS_DEPART_BUS", entityId);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     *
     * 删除叫号窗口信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelDepartBus")
    @ResponseBody
    public AjaxJson multiDelDepartBus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callService.remove("T_CKBS_DEPART_BUS","RECORD_ID",selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述 企业设立登记事项绑定
     * @author Rider Chen
     * @created 2017年3月1日 下午8:01:54
     * @param request
     * @return
     */
    @RequestMapping(params="setCompany")
    public ModelAndView setCompany(HttpServletRequest request){
        String recordId = request.getParameter("recordId");
        Map<String, Object> record = callService.getByJdbc("T_CKBS_NUMRECORD",
                new String[] { "RECORD_ID" }, new Object[] { recordId });
        request.setAttribute("record", record);
        return new ModelAndView("call/queuing/setCompany");
    }
    
    /**
     * 
     * 描述 保存企业设立登记EXE_ID
     * @author Rider Chen
     * @created 2017年3月1日 下午8:07:53
     * @param request
     * @return
     */
    @RequestMapping(params="queuingCompany")
    @ResponseBody
    public AjaxJson queuingCompany(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String EXE_ID = request.getParameter("EXE_ID");
        boolean isok = true;
        if(StringUtils.isNotEmpty(EXE_ID)){
            SqlFilter filter = new SqlFilter(request);
            SysUser sysUser = AppUtil.getLoginUser();
            filter.addFilter("Q_T.ASSIGNER_CODE_EQ", sysUser.getUsername());
            filter.addSorted("T.CREATE_TIME","desc");
            List<Map<String, Object>> list = flowTaskService.findZzhyCallNeedMeHandle(filter,true);
            for (Map<String, Object> map : list) {
                String exeId = (String)map.get("EXE_ID");
                if(EXE_ID.equals(exeId)){
                    //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                    //callService.saveOrUpdate(variables, "T_CKBS_NUMRECORD", recordId);
                    String taskId = (String)map.get("TASK_ID");
                    j.setJsonString(taskId);
                    j.setMsg("操作成功");
                    isok = false;
                } 
            }
        }
        if(isok){
            j.setSuccess(false);
            j.setMsg("输入的申报号在待办任务中不存在！");
        }
        return j;
    }
    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2018-5-10 下午3:10:35
     * @param request
     * @return
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(params="creditInquiry")
    public ModelAndView creditInquiry(HttpServletRequest request,
            HttpServletResponse response) throws IOException  {
        InputStream inputStream = null;//接收字节输入流
        InputStreamReader inputStreamReader= null;//将字节输入流转换成字符输入流
        BufferedReader bufferedReader= null;//为字符输入流加缓冲
        FileOutputStream fileOutputStream= null;//字节输出流
        OutputStreamWriter outputStreamWriter= null;//将字节输出流转换成字符输出流
        String path = BeanUtil.getClassPath(AppUtil.class);
        path = path.substring(0, path.indexOf("WEB-INF"));
        String returnPath = "webpage/call/queuing/credit.html";
//        String returnPath = "webpage/call/queuing/credit";
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//        returnPath = returnPath + date + ".html";
        path = path + returnPath;
        try {
            SysUser curUser = AppUtil.getLoginUser();
            String depName = curUser.getDepName();
            String fullname = curUser.getFullname();
            String username = curUser.getUsername();
            String IDNumber = request.getParameter("IDNumber");
            String ENumber = request.getParameter("ENumber");
            depName = URLEncoder.encode(depName, "utf8");
            fullname = URLEncoder.encode(fullname, "utf8");
            String qurl = "";
//            qurl+=dictionaryService.getDicNames("XYDJ", "BDC_DBPASSWORD");
            if (StringUtils.isNotEmpty(IDNumber)) {
                qurl = "http://192.168.144.130:8001/api/personhtml?card_id="
//                qurl += "api/personhtml?card_id="
            + IDNumber+"&department="+depName+"&account="+username+"&name="+fullname;
            }else if (StringUtils.isNotEmpty(ENumber)) {
                qurl = "http://192.168.144.130:8001/api/companyhtml?card_id="
//                qurl += "api/companyhtml?card_id="
            + ENumber+"&department="+depName+"&account="+username+"&name="+fullname;
            }
            URL wangyi = new URL(qurl);
    //        URL wangyi = new URL("http://www.163.com/");
            inputStream = wangyi.openStream();
            String bm = "utf8";
            inputStreamReader = new InputStreamReader(inputStream, bm);
            bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            File dest = new File(path);
    //        File dest = new File("D:/Tomcat/Tomcat7.0/webapps/ptzhsp/webpage/call/queuing/credit.html");
            fileOutputStream = new FileOutputStream(dest);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, bm);
            while ((s = bufferedReader.readLine()) != null) {
                outputStreamWriter.write(s);
            }
            fileOutputStream.flush();
            outputStreamWriter.flush();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            log.error("", e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("", e);
        }finally{
            if(outputStreamWriter!=null)outputStreamWriter.close();
            if(fileOutputStream!=null)fileOutputStream.close();
            if(bufferedReader!=null)bufferedReader.close();
            if(inputStreamReader!=null)inputStreamReader.close();
            if(inputStream!=null)inputStream.close();
        }
        return new ModelAndView("redirect:/"+returnPath);
    }
    /**
     * 
     * 描述
     * @author Kester Chen
     * @created 2018-5-11 上午9:01:18
     * @param request
     * @return
     */
    @RequestMapping(params="showCreditInquiry")
    public ModelAndView showCreditInquiry(HttpServletRequest request){
        return new ModelAndView("call/queuing/showCreditInquiry");
    }
    
    /**
    *
    * 排队绑定微信OPENID
    * @param request
    * @return
    */
   @RequestMapping(params="bindOpenId")
   public void bindOpenId(HttpServletRequest request, HttpServletResponse response) {
       //String openId = AppUtil.getOpenID();
       String code = request.getParameter("code");
       String openId = WeixinUtil.getOpenByCode(code);
       Map<String, Object> result = new HashMap<String, Object>();
       result.put("success", false);
       result.put("openId", openId);
       if(StringUtils.isNotEmpty(openId)){
           String recordId = request.getParameter("recordId");
           Map<String, Object> variables = new HashMap<String, Object>();
           variables.put("OPENID", openId);
           newCallService.bindOpenId(variables, recordId);
           result.put("success", true);
       }
       
       String json = JSON.toJSONString(result);
       this.setJsonString(json, response);
   }
   
       /**
       *
       * 排队叫号通知
       * @param request
       * @return
       */
      @RequestMapping(params="sendMessage")
      public void sendMessage(HttpServletRequest request, HttpServletResponse response) {
          //String openId = request.getParameter("openId");
          WxAccessToken wxAccessToken = WeixinUtil.getWxAccessToken();
          WechatTemplate wechatTemplate = new WechatTemplate();
          wechatTemplate.setTemplateId("Rbmb2_KiX8KnfY2fxpGiGrEHGHDblWLaeIh0RQgV8UE");
          //wechatTemplate.setTouser(openId);
          wechatTemplate.setTouser("o4J2rt_vi2ixxdx08xA5JlWbhBK4");
          Map<String,TemplateData> data = new HashMap<String,TemplateData>();
          TemplateData first = new TemplateData();
          first.setColor("#173177");
          first.setValue("您好,您的排号即将到您,请提前到现场等候准备。");
          data.put("first", first);
          
          TemplateData keyword1 = new TemplateData();
          keyword1.setColor("#173177");
          keyword1.setValue("A1001");
          data.put("keyword1", keyword1);
          
          TemplateData keyword2 = new TemplateData();
          keyword2.setColor("#173177");
          keyword2.setValue("户籍办证");
          data.put("keyword2", keyword2);
          
          TemplateData keyword3 = new TemplateData();
          keyword3.setColor("#173177");
          keyword3.setValue("1号窗口");
          data.put("keyword3", keyword3);
          
          TemplateData keyword4 = new TemplateData();
          keyword4.setColor("#173177");
          keyword4.setValue("2019-03-07 09:07:22");
          data.put("keyword4", keyword4);
          
          TemplateData keyword5 = new TemplateData();
          keyword5.setColor("#173177");
          keyword5.setValue("2019-03-07 08:07:22");
          data.put("keyword5", keyword5);
          
          TemplateData remark = new TemplateData();
          remark.setColor("#173177");
          remark.setValue("请尽快前往现场等候办理，以免过号，谢谢合作!");
          data.put("remark", remark);
          
          wechatTemplate.setData(data);
          String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
          String token = wxAccessToken.getTokenName();
          url = url.replace("ACCESS_TOKEN", token);
          String wechatJson=JSONObject.toJSONString(wechatTemplate);
          wechatJson=wechatJson.replace("templateId","template_id");
          String json = HttpRequestUtil.sendPost(url, wechatJson);
          this.setJsonString(json, response);
      }
      
    /**
     *
     * 通知有绑定OPENID的微信号
     * @param request
     * @return
     */
    @RequestMapping(params="notifyWxHasOpenId")
    public void notifyWxHasOpenId(HttpServletRequest request, HttpServletResponse response) {
        String recordId = request.getParameter("recordId");
        String json=newCallService.notifyWxHasOpenId(recordId);
        if(StringUtils.isNotEmpty(json)){
            this.setJsonString(json, response);
        }
    }
}
