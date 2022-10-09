/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.call.service.CallSetService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.system.dao.SysSendMsgDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.PushDataToSJJXXZXService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.thread.PushDateToSJJXXZXRunnable;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年6月26日 上午11:11:01
 */
@Controller
@RequestMapping("callSetController")
public class CallSetController extends BaseController {

    /**
     * 引入service
     */
    @Resource
    private CallSetService callSetService;
    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 引入dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 引入省经济中心信息推送service
     */
    @Resource
    private PushDataToSJJXXZXService pushDataToSJJXXZXService;
    
    /**
     * dao
     */
    @Resource
    private SysSendMsgDao dao;
    
    /**
     * 
     * 描述    业务管理
     * @author Danto Huang
     * @created 2018年6月26日 上午11:14:36
     * @param request
     * @return
     */
    @RequestMapping(params="busManageView")
    public ModelAndView busManageView(HttpServletRequest request){        
        return new ModelAndView("callnew/setting/busmanage/busManageView");
    }
    
    /**
     * 
     * 描述    业务管理数据
     * @author Danto Huang
     * @created 2018年6月27日 上午9:16:06
     * @param request
     * @param response
     */
    @RequestMapping(params="busManageData")
    public void busManageData(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.BELONG_ROOM", "asc");
        filter.addSorted("d.TREE_SN", "asc");
        //filter.addSorted("d.DEPART_ID", "asc");
        filter.addSorted("t.BUSINESS_CODE", "asc");
        List<Map<String, Object>> list = callSetService.getBusManageData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    业务管理信息页面
     * @author Danto Huang
     * @created 2018年6月27日 上午9:47:51
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="busManageinfo")
    public ModelAndView busManageinfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String,Object> busManageinfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            busManageinfo = callSetService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "DATA_ID" },
                    new Object[] { entityId });
            String departName = (String) callSetService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { busManageinfo.get("DEPART_ID") }).get("DEPART_NAME");
            busManageinfo.put("DEPART_NAME", departName);
        }
        request.setAttribute("busManageinfo", busManageinfo);
        return new ModelAndView("callnew/setting/busmanage/busManageinfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateBus")
    @ResponseBody
    public AjaxJson saveOrUpdateBus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DATA_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = callSetService.saveOrUpdate(variables, "T_CKBS_BUSINESSDATA", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的排队管理业务信息记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的排队管理业务信息记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    删除业务管理
     * @author Danto Huang
     * @created 2018年6月27日 上午11:29:39
     * @param request
     * @return
     */
    @RequestMapping(params = "delBus")
    @ResponseBody
    public AjaxJson delBus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] ids = selectColNames.split(",");
        boolean flag = true;
        for(int i=0;i<ids.length;i++){
            Map<String, Object> bus = callSetService.getByJdbc("T_CKBS_BUSINESSDATA", new String[] { "DATA_ID" },
                    new Object[] { ids[i] });
            if(callSetService.isUsingBusData(bus.get("BUSINESS_CODE").toString())){
                flag = false;
                break;
            }
        }
        if(flag){
            callSetService.remove("T_CKBS_BUSINESSDATA","DATA_ID",selectColNames.split(","));
            sysLogService.saveLog("删除了ID为["+selectColNames+"]的排队管理业务信息记录",SysLogService.OPERATE_TYPE_DEL);
            j.setMsg("删除成功");
            j.setSuccess(true);
        }else{
            j.setMsg("已经被使用的业务数据不允许删除！");
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 
     * 描述    启用/停用
     * @author Danto Huang
     * @created 2018年6月27日 上午11:48:39
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="busUse")
    @ResponseBody
    public AjaxJson busUse(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("SERVICE_STATUS", statu);
        for(int i=0;i<entityId.length;i++){
            callSetService.saveOrUpdate(variable, "T_CKBS_BUSINESSDATA", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    取号部门图标管理
     * @author Danto Huang
     * @created 2018年6月28日 下午3:44:33
     * @param request
     * @return
     */
    @RequestMapping(params="departIconManage")
    public ModelAndView departIconManage(HttpServletRequest request){
        return new ModelAndView("callnew/setting/busmanage/departIconView");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月28日 下午3:49:27
     * @param request
     * @param response
     */
    @RequestMapping(params="departIconDatagrid")
    public void departIconDatagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("d.tree_sn", "asc");
        List<Map<String, Object>> list = callSetService.findDepartIconList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述    取号部门图标管理
     * @author Danto Huang
     * @created 2018年6月28日 下午3:44:33
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="departIconInfo")
    public ModelAndView departIconInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> iconInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            iconInfo = callSetService.getByJdbc("T_CKBS_BUSINESSDATA_ICON",
                    new String[] { "DEPART_ID" }, new Object[] { entityId });
            Map<String,Object> depart = callSetService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { entityId });
            iconInfo.put("DEPART_NAME", depart.get("DEPART_NAME"));
        }
        request.setAttribute("iconInfo", iconInfo);
        return new ModelAndView("callnew/setting/busmanage/departIconInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年7月3日 上午10:22:27
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="saveOrUpdateDepIcon")
    @ResponseBody
    public AjaxJson saveOrUpdateDepIcon(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        callSetService.saveOrUpdate(variables, "T_CKBS_BUSINESSDATA_ICON", entityId);
        sysLogService.saveLog("修改了ID为["+entityId+"]的排队管理单位/部门配置记录",SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月27日 下午5:21:34
     * @param request
     * @param response
     */
    @RequestMapping(params="loadBusiness")
    public void loadBusiness(HttpServletRequest request,HttpServletResponse response){
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String,Object>> list = callSetService.findBusinessForSelect();
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("BUSINESS_CODE","");
            map.put("BUSINESS_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述    业务选择器
     * @author Danto Huang
     * @created 2018年7月12日 下午3:36:50
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String businessCodes = request.getParameter("businessCodes");
        String businessNames = request.getParameter("businessNames");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        if(StringUtils.isNotEmpty(businessCodes)&&!businessCodes.equals("undefined")){
            request.setAttribute("businessCodes", businessCodes);
            request.setAttribute("businessNames", businessNames);
        }
        return new ModelAndView("callnew/setting/busmanage/Selector");
    }

    /**
     * 
     * 描述    已选业务
     * @author Danto Huang
     * @created 2018年7月12日 下午4:03:46
     * @param request
     * @param response
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String businessCodes = request.getParameter("businessCodes");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(businessCodes)){
            list = callSetService.findBybusinessCode(businessCodes);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
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
        return new ModelAndView("callnew/setting/winscreen/winScreenView");
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
        filter.addSorted("to_number(t.win_no)", "asc");
        List<Map<String, Object>> list = callSetService.findWinScreenBySqlFilter(filter);
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
    @SuppressWarnings("unchecked")
    @RequestMapping(params="winScreenInfo")
    public ModelAndView winScreenInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> winScreenInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty("entityId")){
            winScreenInfo = callSetService.getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "RECORD_ID"},
                    new Object[] { entityId });
        }
        request.setAttribute("winScreenInfo", winScreenInfo);
        return new ModelAndView("callnew/setting/winscreen/winScreenInfo");
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
        String winNo = request.getParameter("WIN_NO");
        Map<String, Object> winInfo = callSetService.getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "WIN_NO" },
                new Object[] { winNo });
        if(winInfo!=null&&(StringUtils.isEmpty(entityId)||!winInfo.get("RECORD_ID").equals(entityId))){
            j.setSuccess(false);
            j.setMsg("窗口编号："+winNo+"的配置信息已存在，请勿重复配置！");
        }else{
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            if(StringUtils.isEmpty(entityId)){
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            String recordId = callSetService.saveOrUpdate(variables, "T_CKBS_WIN_SCREEN", entityId);
            if(StringUtils.isNotEmpty(entityId)){
                variables.put("acType", "2");
                sysLogService.saveLog("修改了ID为["+entityId+"]的窗口屏绑定关系记录",SysLogService.OPERATE_TYPE_EDIT);
            }else{
                variables.put("acType", "1");
                sysLogService.saveLog("新增了ID为["+recordId+"]的窗口屏绑定关系记录",SysLogService.OPERATE_TYPE_ADD);
            }
            //推送至省经济中心窗口状态实时记录
            String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
            if ("开".equals(switchTurn)) {
                if(StringUtil.isNotEmpty(variables)){
                    //窗口资源报送
                    pushDataToSJJXXZXService.pushDateToSJJXXZX(variables,"addCounterSourceInfo");
                };
            }
            j.setMsg("保存成功");
        }
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
        String[] split = selectColNames.split(",");
        if(split.length > 1){
            j.setMsg("只能选择一条记录进行删除");
            return j;
        }
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("RECORD_ID", selectColNames);
        //推送至省经济中心取号实时记录
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
        if ("开".equals(switchTurn)) {
            //推送至省经济中心串口状态实时记录
            if(StringUtil.isNotEmpty(variables)){
                variables.put("acType", "3");
                //窗口资源报送
                pushDataToSJJXXZXService.pushDateToSJJXXZX(variables,"addCounterSourceInfo");
            };
        }
        callSetService.remove("T_CKBS_WIN_SCREEN","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 窗口单位记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    启用/禁用
     * @author Danto Huang
     * @created 2018年8月20日 下午5:37:30
     * @param request
     * @return
     */
    @RequestMapping(params="winScreenUse")
    @ResponseBody
    public AjaxJson winScreenUse(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("USE_STATUS", statu);
        for(int i=0;i<entityId.length;i++){
            callSetService.saveOrUpdate(variable, "T_CKBS_WIN_SCREEN", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月27日 下午5:39:01
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getScreenInfo")
    @ResponseBody
    public AjaxJson getScreenInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String winNo = request.getParameter("winNo");
        Map<String, Object> map = callSetService.getByJdbc("T_CKBS_WIN_SCREEN", new String[] { "WIN_NO" },
                new Object[] { winNo });
        j.setMsg(map.get("SCREEN_NO")+","+map.get("BELONG_ROOM"));
        return j;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月27日 下午5:21:34
     * @param request
     * @param response
     */
    @RequestMapping(params="loadWinNo")
    public void loadWinNo(HttpServletRequest request,HttpServletResponse response){
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String,Object>> list = callSetService.findWinNoForSelect();
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("VALUE","");
            map.put("TEXT", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月27日 下午5:21:34
     * @param request
     * @param response
     */
    @RequestMapping(params="loadScreenNo")
    public void loadScreenNo(HttpServletRequest request,HttpServletResponse response){
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String,Object>> list = callSetService.findScreenNoForSelect();
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("VALUE","");
            map.put("TEXT", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    窗口人员管理
     * @author Danto Huang
     * @created 2018年6月27日 下午4:18:19
     * @param request
     * @return
     */
    @RequestMapping(params="winUserView")
    public ModelAndView winUserView(HttpServletRequest request){
        return new ModelAndView("callnew/setting/winuser/winUserView");
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月27日 下午4:19:12
     * @param request
     */
    @RequestMapping(params="winUserDatagrid")
    public void winUserDatagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("to_number(t.win_no)", "asc");
        List<Map<String, Object>> list = callSetService.findWinUserBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }    
    
    /**
     * 
     * 描述    窗口人员管理
     * @author Danto Huang
     * @created 2018年6月27日 下午4:18:19
     * @param request
     * @return
     */
    @RequestMapping(params="winUserLimiteView")
    public ModelAndView winUserLimiteView(HttpServletRequest request){
        return new ModelAndView("callnew/setting/winuser/winUserLimiteView");
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年6月27日 下午4:19:12
     * @param request
     */
    @RequestMapping(params="waitNum")
    public void waitNum(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> listAB = newCallService.findABWaitingList();
        for (Map<String, Object> map : listAB) {
            map.put("BUSINESS_CODE", "A、B");
            list.add(map);
        }
        List<Map<String,Object>> listOther = newCallService.findOtherWaitingList();
        for (Map<String, Object> map : listOther) {
            map.put("BUSINESS_CODE", "其他");
            list.add(map);
        }
        filter.getPagingBean().setTotalItems(list.size());
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
    @SuppressWarnings("unchecked")
    @RequestMapping(params="winUserInfo")
    public ModelAndView winUserInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> winUserInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            winUserInfo = callSetService.getByJdbc("T_CKBS_WIN_USER", new String[] { "RECORD_ID"},
                    new Object[] { entityId });
        }
        request.setAttribute("winUserInfo", winUserInfo);
        return new ModelAndView("callnew/setting/winuser/winUserInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateWinUser")
    @ResponseBody
    public AjaxJson saveOrUpdateWinUser(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = callSetService.saveOrUpdate(variables, "T_CKBS_WIN_USER", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的窗口人员信息记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的窗口人员信息记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述    删除窗口人员信息
     * @author Danto Huang
     * @created 2018年6月28日 上午8:58:42
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDelWinUser")
    @ResponseBody
    public AjaxJson multiDelWinUser(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callSetService.remove("T_CKBS_WIN_USER","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 窗口人员信息记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    窗口人员是否可多次受理
     * @author Danto Huang
     * @created 2018年6月28日 上午8:49:16
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="isContinue")
    @ResponseBody
    public AjaxJson isContinue(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_CONTINUE", statu);
        for(int i=0;i<entityId.length;i++){
            callSetService.saveOrUpdate(variable, "T_CKBS_WIN_USER", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    窗口人员禁用/启用
     * @author Danto Huang
     * @created 2018年6月28日 上午8:53:27
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="isUse")
    @ResponseBody
    public AjaxJson isUse(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityIds = request.getParameter("entityIds");
        String statu = request.getParameter("statu");
        String[] entityId = entityIds.split(",");
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("IS_USE", statu);
        for(int i=0;i<entityId.length;i++){
            callSetService.saveOrUpdate(variable, "T_CKBS_WIN_USER", entityId[i]);
        }
        j.setMsg("操作成功");
        return j;
    }
    /**
     * 
     * 描述 开启或限制2.4.6.8.10.12AB类业务
     * @author Kester Chen
     * @created 2019年3月20日 下午4:27:20
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="limitAB")
    @ResponseBody
    public AjaxJson limitAB(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String statu = request.getParameter("statu");
        if ("0".equals(statu)) {
            List<Map<String, Object>> winUserlist = 
                    callSetService.findWinUserByWinNo("'02','04','06','08','10','12'");
            for (Map<String, Object> map : winUserlist) {
                String winBusinessCodes = map.get("WIN_BUSINESS_CODES")==null?
                        "":map.get("WIN_BUSINESS_CODES").toString();
                String winBusinessNames = map.get("WIN_BUSINESS_NAMES")==null?
                        "":map.get("WIN_BUSINESS_NAMES").toString();
                if (StringUtils.isNotEmpty(winBusinessCodes)) {
                    winBusinessCodes = winBusinessCodes.replace("A,B,", "");
                    winBusinessCodes = winBusinessCodes.replace(",A", "");
                    winBusinessCodes = winBusinessCodes.replace("A", "");
                    winBusinessCodes = winBusinessCodes.replace(",B", "");
                    winBusinessCodes = winBusinessCodes.replace("B", "");
                    winBusinessNames = winBusinessNames.replace("不动产,不动产快件,", "");
                    winBusinessNames = winBusinessNames.replace(",不动产快件", "");
                    winBusinessNames = winBusinessNames.replace("不动产快件", "");
                    winBusinessNames = winBusinessNames.replace(",不动产", "");
                    winBusinessNames = winBusinessNames.replace("不动产", "");
                    map.put("WIN_BUSINESS_CODES", winBusinessCodes);
                    map.put("WIN_BUSINESS_NAMES", winBusinessNames);
                    callSetService.saveOrUpdate(map, "T_CKBS_WIN_USER", map.get("RECORD_ID").toString());
                }
            }

            List<Map<String, Object>> winUserlist2 = 
                    callSetService.findWinUserByWinNo("'01','03','05','07','09','11'");
            for (Map<String, Object> map : winUserlist2) {
                String winBusinessCodes = map.get("WIN_BUSINESS_CODES")==null?
                        "":map.get("WIN_BUSINESS_CODES").toString();
                String winBusinessNames = map.get("WIN_BUSINESS_NAMES")==null?
                        "":map.get("WIN_BUSINESS_NAMES").toString();
                if (StringUtils.isNotEmpty(winBusinessCodes)) {
                    winBusinessCodes = "A,B";
                    winBusinessNames = "不动产,不动产快件";
                    map.put("WIN_BUSINESS_CODES", winBusinessCodes);
                    map.put("WIN_BUSINESS_NAMES", winBusinessNames);
                    callSetService.saveOrUpdate(map, "T_CKBS_WIN_USER", map.get("RECORD_ID").toString());
                }
            }
            
        } else if ("1".equals(statu)) {
            List<Map<String, Object>> winUserlist = 
                    callSetService.findWinUserByWinNo("'01','02','03','04','05','06','07','08','09','10','11','12'");
            for (Map<String, Object> map : winUserlist) {
                String winBusinessCodes = map.get("WIN_BUSINESS_CODES")==null?
                        "":map.get("WIN_BUSINESS_CODES").toString();
                String winBusinessNames = map.get("WIN_BUSINESS_NAMES")==null?
                        "":map.get("WIN_BUSINESS_NAMES").toString();
                if (StringUtils.isNotEmpty(winBusinessCodes)) {
                    winBusinessCodes = "A,B,C,D,E,F,Y,G,H";
                    winBusinessNames = "不动产,不动产快件,公积金,公积金快件,医保,医保快件,婚姻登记,社保,社保快件";
                    map.put("WIN_BUSINESS_CODES", winBusinessCodes);
                    map.put("WIN_BUSINESS_NAMES", winBusinessNames);
                    callSetService.saveOrUpdate(map, "T_CKBS_WIN_USER", map.get("RECORD_ID").toString());
                }
            }
        }
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年3月13日 上午11:16:41
     * @param request
     * @return
     */
    @RequestMapping(params="newWinGroup")
    public ModelAndView newWinGroup(HttpServletRequest request){
        return new ModelAndView("callnew/setting/winuser/newWinGroup");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年3月13日 下午2:13:09
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateNewWinGroup")
    @ResponseBody
    public void saveOrUpdateNewWinGroup(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> varibales = BeanUtil.getMapFromRequest(request);
        String recordId = callSetService.saveOrUpdate(varibales, "T_CKBS_WIN_GROUP", null);
        resultMap.put("success", true);
        resultMap.put("msg", "保存成功！");
        resultMap.put("recordId", recordId);
        resultMap.put("winGroupName", varibales.get("GROUP_NAME"));
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    窗口分组选择信息
     * @author Danto Huang
     * @created 2019年3月13日 上午10:36:55
     * @param request
     * @param response
     */
    @RequestMapping(params="findGroupForWin")
    public void findGroupForWin(HttpServletRequest request,HttpServletResponse response){
        String defaultEmpty = request.getParameter("defaultEmpty");
        List<Map<String,Object>> list = callSetService.findWinGroupForSelect();        
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("VALUE","");
            map.put("TEXT", "请选择");
            if(list==null){
                list = new ArrayList<Map<String,Object>>();
            }
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述    窗口用户信息json
     * @author Danto Huang
     * @created 2018年7月9日 下午3:22:14
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="userInfoJson")
    @ResponseBody
    public AjaxJson userInfoJson(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String winNo = request.getParameter("winNo");
        Map<String, Object> variables = callSetService.getByJdbc("T_CKBS_WIN_USER", new String[] { "WIN_NO" },
                new Object[] { winNo });
        if(variables==null){
            j.setMsg("你选择的窗口无办理人员");
            j.setSuccess(false);
        }else{
            j.setSuccess(true);
            j.setMsg("操作成功");
            j.setJsonString(JSON.toJSONString(variables));
        }
        return j;
    }
    
    /**
     * 
     * 描述    预约取号配置
     * @author Danto Huang
     * @created 2018年7月18日 上午9:33:31
     * @param request
     * @return
     */
    @RequestMapping(params="appointmentSetView")
    public ModelAndView appointmentSetView(HttpServletRequest request){
        return new ModelAndView("callnew/setting/appointment/appointmentSetView");
    }
    
    /**
     * 
     * 描述    预约时段数据列表
     * @author Danto Huang
     * @created 2018年7月18日 上午9:41:53
     * @param request
     * @param response
     */
    @RequestMapping(params="appointmentSetDatagrid")
    public void appointmentSetDatagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.BEGIN_TIME", "asc");
        List<Map<String, Object>> list = callSetService.findAppointSetBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    } 
    
    /**
     * 
     * 描述    网上预约设置部门/业务树
     * @author Danto Huang
     * @created 2018年7月19日 上午9:37:07
     * @param request
     * @param response
     */
    @RequestMapping(params = "tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> root = getParent();
        String json = JSON.toJSONString(root);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年7月19日 上午10:17:02
     * @return
     */
    private Map<String, Object> getParent() {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "预约部门/业务");
        root.put("open", true);
        List<Map<String, Object>> toplist = callSetService.findDepart();
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("DEPART_ID"));
            top.put("name", top.get("DEPART_NAME"));
            top.put("parentId", 0);
            this.getChildren(top, (String) top.get("DEPART_ID"));
        }
        root.put("children", toplist);
        return root;
    }

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年7月19日 上午10:16:56
     * @param parentType
     * @param parentId
     */
    private void getChildren(Map<String, Object> parentType, String parentId) {
        List<Map<String, Object>> children = callSetService.findByParentId(parentId);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("DATA_ID"));
                child.put("name", child.get("BUSINESS_NAME"));
                child.put("parentId", parentId);
            }
        }
    }

    /**
     * 
     * 描述    跳转预约时段配置
     * @author Danto Huang
     * @created 2018年7月20日 上午10:05:30
     * @param request
     * @return
     */
    @RequestMapping(params = "appointmentSetInfo")
    public ModelAndView appointmentSetInfo(HttpServletRequest request) {
        String departId = request.getParameter("departId");
        String entityId = request.getParameter("entityId");
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("DEPART_ID", departId);
        if (StringUtils.isNotBlank(entityId)) {
            info = callSetService.getByJdbc("T_CKBS_APPOINTMENT_SET", new String[] { "RECORD_ID" },
                    new Object[] { entityId });
        }
        request.setAttribute("info", info);
        return new ModelAndView("callnew/setting/appointment/appointmentSetInfo");
    }
    /**
     * 
     * 描述    保存预约时段设置
     * @author Danto Huang
     * @created 2018年7月20日 上午10:27:27
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateSet")
    @ResponseBody
    public AjaxJson saveOrUpdateSet(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        callSetService.saveOrUpdate(variables, "T_CKBS_APPOINTMENT_SET", entityId);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年7月24日 下午3:27:54
     * @param request
     * @return
     */
    @RequestMapping(params = "doDelForTime")
    @ResponseBody
    public AjaxJson doDelForTime(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        callSetService.remove("T_CKBS_APPOINTMENT_SET", "RECORD_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 描述:解除窗口占用
     *
     * @author Madison You
     * @created 2020/3/17 9:19:00
     * @param
     * @return
     */
    @RequestMapping(params = "removeWinOccupation")
    @ResponseBody
    public AjaxJson removeWinOccupation(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        HashMap<String, Object> variables = new HashMap<>();
        String recordIds = request.getParameter("recordIds");
        String[] recordIdArr = recordIds.split(",");
        variables.put("CUR_USERID", "");
        String switchTurn = dictionaryService.getDicNames("sjjxxzxConfig", "switchTurn");
        for (String recordId : recordIdArr) {
            //推送至省经济中心串口状态实时记录
            Map<String,Object> variableSend = new HashMap<String, Object>();
            variableSend.put("RECORD_ID", recordId);
            variableSend.put("COUNTERSTATUS", "3");
            if ("开".equals(switchTurn)) {
                //窗口资源报送
                pushDataToSJJXXZXService.pushDateToSJJXXZX(variableSend, "addCounterNowInfo");
            }
            callSetService.saveOrUpdate(variables, "T_CKBS_WIN_USER", recordId);
        }
        j.setMsg("解除占用成功");
        return j;
    }
    
}
