/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowDataUtil;
import net.evecom.core.util.FlowNodeBean;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.JsonValidator;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchange.model.FlowChangeView;
import net.evecom.platform.flowchange.service.FlowChangeService;
import net.evecom.platform.flowchange.service.ProcessChangeService;
import net.evecom.platform.flowchart.controller.FlowChartController;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.flowchart.service.BusProcessService;
import net.evecom.platform.flowchart.service.MonitorNodeService;
import net.evecom.platform.flowchart.service.TacheFlowService;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述 流程图变更绘制
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Controller
@RequestMapping("/flowChange")
public class FlowChangeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowChangeController.class);
    /**
     * 流程图变更service
     */
    @Resource
    private FlowChangeService flowChangeService;
    /**
     * 流程图绘制service
     */
    @Resource
    private TacheFlowService flowChartService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 监察点service
     */
    @Resource
    private MonitorNodeService monitorNodeService;
    /**
     * 节点service
     */
    @Resource
    private ProcessChangeService processChangeService;
    
    /**
     * 
     * 跳转到权力运行列表界面
     * @param request
     * @return
     */
    @RequestMapping(params = "chartList")
    public ModelAndView chartList(HttpServletRequest request) {
        return new ModelAndView("flowchange/chartList");
    }
    
    /**
     * 获取业务专项列表数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SysUser sysUser = AppUtil.getLoginUser();
        String sysUserResKey = sysUser.getResKeys();
        SqlFilter filter = new SqlFilter(request);
        String unitCode = null;
        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
            unitCode = sysUser.getDepCode();
            filter.addFilter("Q_U.UNIT_CODE_EQ", unitCode);
        }
//        String userBusCodes = AppUtil.getCurrentUserBusCodeString();
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            filter.addFilter("Q_U.UNIT_CODE_IN", userBusCodes);
//        }
        //filter.addFilter("Q_U.CHANGE_STATE_IN","0,1,2,3,4,5,6,7");
        //filter.addFilter("Q_U.CHANGE_FLAG_EQ","1");
        filter.addFilter("Q_U.STATUS_NEQ", "0");//专项确认以后的；
        filter.addSorted("U.UPDATE_TIME","desc");
        List<Map<String, Object>> list = flowChangeService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到发起变更选择页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "busSpeList")
    public ModelAndView busSpeList(HttpServletRequest request,
            HttpServletResponse response) {
        //String entityId = request.getParameter("entityId");
        SysUser sysUser = AppUtil.getLoginUser();
        String sysUserResKey = sysUser.getResKeys();
        SqlFilter filter = new SqlFilter(request);
        String unitCode = null;
        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
            unitCode = sysUser.getDepCode();
            filter.addFilter("Q_U.UNIT_CODE_EQ", unitCode);
        }
        
        filter.addFilter("Q_U.STATUS_EQ","2");
        List<Map<String, Object>> list = flowChartService.findBySqlFilter(filter);
        request.setAttribute("speList", list);
        return new ModelAndView("flowchange/busSpeSelect");
    }
    
    /***
     * 下拉获取业务专项数据源
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "comboxSpecial")
    public void comboxSpecial(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = AppUtil.getLoginUser();
        String sysUserResKey = sysUser.getResKeys();
        SqlFilter filter = new SqlFilter(request);
        String unitCode = null;
        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
            unitCode = sysUser.getDepCode();
            filter.addFilter("Q_U.UNIT_CODE_EQ", unitCode);
        }
        
        filter.addFilter("Q_U.STATUS_EQ","3");
        filter.addFilter("Q_U.CHANGE_FLAG_EQ","-1");
        //filter.addFilter("Q_U.CHANGE_STATE_EQ","-1");
        List<Map<String, Object>> list = flowChartService.findBySqlFilter(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 方法:1.流程图发起变更
     *
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "startChange")
    @ResponseBody
    public AjaxJson startChange(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        String entityId = request.getParameter("BUS_ID");
        String busCode = request.getParameter("BUS_CODE");
        Map<String, Object> reqData = BeanUtil.getMapFromRequest(request);
        //String unitType = String.valueOf(treeData.get("UNIT_TYPE"));
        if(StringUtils.isNotEmpty(entityId)){
            reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("UPDATE_USER", sysUser.getUserId());
            reqData.put("CHANGE_FLAG","1");
            flowChartService.saveOrUpdate(reqData,"t_lcjc_bus_special",entityId);
            String date = DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmm");
            //申报号为业务专项+当前时间 
            flowChangeService.copyBusItem(busCode,date);
            flowChangeService.copyTaches(busCode,date);
            sysLogService.saveLog("发起变更ID为["+entityId+"]的 业务专项记录",SysLogService.OPERATE_TYPE_EDIT);
        }
        
        j.setMsg("变更成功");
        j.setSuccess(true);
        return j;
    }
    
    /**
     * 跳转到流程图变更审批表单（新旧流程图对比页面）
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "viewNewOldFlow")
    public ModelAndView viewNewOldFlow(HttpServletRequest request,
            HttpServletResponse response) {
        //String busCode = request.getParameter("entityId");
        String applyId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(applyId) && !applyId.equals("undefined")
                && !applyId.equals("0")) {
            List<Map<String, Object>> list = flowChangeService.getBusByApplyId(applyId);
            BusSpecialInfo bus = null;
            if (list.size() > 0) {
                Map<String, Object> map = list.get(0);
                bus = new BusSpecialInfo();
                bus.setBusCode((String) map.get("bus_code"));
                bus.setBusName((String) map.get("bus_name"));
                bus.setApplyId((String) map.get("apply_id"));
                bus.setStatus(Integer.valueOf(map.get("status").toString()));
            }
            //获取旧的流程图信息
            List<TacheFlow> flist =flowChartService.getFlowByBusiCode(bus.getBusCode());
            if (flist != null) {
                request.setAttribute("oldTacheList", flist);
            }
            //获取新的流程图信息
            List<TacheFlow> newlist =flowChangeService.getFlowByBusiCode(bus.getBusCode(), applyId);
            if (newlist != null) {
                request.setAttribute("newTacheList", newlist);
            }
            request.setAttribute("stateFlg", bus.getStatus());
            request.setAttribute("busInfo", bus);
        }
        return new ModelAndView("flowchange/flowNewOldView");
    }
    
    
    /**
     * 跳转到流程图变更页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowchange")
    public ModelAndView flowChange(HttpServletRequest request,
            HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        String applyId = request.getParameter("applyId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            List<TacheFlow> flist =flowChangeService.getChangeFlowByBusId(entityId,applyId);
            if (flist != null) {
                // request.setAttribute("flowdata", flist.get(0).getFlowInfo());
                // request.setAttribute("result", map);
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
                List<Map<String, Object>> list = flowChangeService.getBusByBusCode(flist.get(0).getBusCode(),applyId);
                BusSpecialInfo bus = null;
                if (list.size() > 0) {
                    Map<String, Object> map = list.get(0);
                    bus = new BusSpecialInfo();
                    bus.setBusCode((String) map.get("bus_code"));
                    bus.setBusName((String) map.get("bus_name"));
                    bus.setApplyId((String) map.get("apply_id"));
                    bus.setStatus(Integer.valueOf(map.get("status").toString()));
                }
                request.setAttribute("busInfo", bus);
            }
        }
        return new ModelAndView("flowchange/changeflow");
    }
    
    /**
     * 跳转到多环节流程查看页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowview")
    public ModelAndView flowView(HttpServletRequest request,
            HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        String applyId = request.getParameter("applyId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            List<TacheFlow> flist =flowChangeService.getChangeFlowByBusId(entityId,applyId);
            if (flist != null) {
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
                List<Map<String, Object>> list = flowChangeService.getBusByBusCode(flist.get(0).getBusCode(),applyId);
                BusSpecialInfo bus = null;
                if (list.size() > 0) {
                    Map<String, Object> map = list.get(0);
                    bus = new BusSpecialInfo();
                    bus.setBusCode((String) map.get("bus_code"));
                    bus.setBusName((String) map.get("bus_name"));
                    bus.setApplyId((String) map.get("apply_id"));
                    bus.setStatus(Integer.valueOf(map.get("status").toString()));
                }
                request.setAttribute("busInfo", bus);
                request.setAttribute("stateFlg", bus.getStatus());
            }
        }
        return new ModelAndView("flowchange/flowview");
    }

    
    /**
     * 加载流程数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "getflow")
    public void getflow(HttpServletRequest request, 
            HttpServletResponse response)throws IOException {
        String json = "";
        String tacheCode = request.getParameter("tacheCode");
        String applyId = request.getParameter("applyId");
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        // List<Map<String, Object>> topList = new ArrayList<Map<String,
        // Object>>();
        if (StringUtils.isNotEmpty(tacheCode) && !tacheCode.equals("undefined")
                && !tacheCode.equals("0")) {
            TacheFlow flow = flowChangeService.getFlowByTacheCode(tacheCode,applyId);
            if (flow != null) {
                flowinfo.put("flowInfo", flow.getFlowInfo());
                JsonValidator jsonV=new JsonValidator();
                if(StringUtils.isNotEmpty(flow.getFlowInfo())&&jsonV.validate(flow.getFlowInfo())){
                    List<Map<String, Object>> nlist=monitorNodeService.findMonitorNodeByTCode(flow.getTacheCode());
                    if(nlist.size()>0){
                        String neStr=FlowDataUtil.setEditState(flow.getFlowInfo(), nlist);
                        flowinfo.put("flowInfo", neStr);
                    }
                }
                flowinfo.put("flowSvg", flow.getFlowSvg());
                flowinfo.put("tacheId", flow.getTacheId());
                flowinfo.put("tacheCode", flow.getTacheCode());
                flowinfo.put("busCode", flow.getBusCode());
                flowinfo.put("flowHeight", flow.getFlowHeight());
            }
        }
        json = JSON.toJSONString(flowinfo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    
    /**
     * 加载流程数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "getflowForView")
    public void getflowForView(HttpServletRequest request, 
            HttpServletResponse response)throws IOException {
        String json = "";
        String tacheCode = request.getParameter("tacheCode");
        String applyId = request.getParameter("applyId");
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        // List<Map<String, Object>> topList = new ArrayList<Map<String,
        // Object>>();
        if (StringUtils.isNotEmpty(tacheCode) && !tacheCode.equals("undefined")
                && !tacheCode.equals("0")) {
            TacheFlow flow = flowChangeService.getFlowByTacheCode(tacheCode,applyId);
            if (flow != null) {
                flowinfo.put("flowInfo", flow.getFlowInfo());
                flowinfo.put("tacheId", flow.getTacheId());
                flowinfo.put("tacheCode", flow.getTacheCode());
                flowinfo.put("busCode", flow.getBusCode());
                flowinfo.put("flowHeight", flow.getFlowHeight());
            }
        }
        json = JSON.toJSONString(flowinfo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 保存 并加载流程数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "saveflow")
    public void saveflow(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String json = "";
        String nextcourseId = request.getParameter("nextTacheCode");
        //保存上一环节流程图
        saveFlowInfo(request);
        //获取下一环节流程图
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(nextcourseId)
                && !nextcourseId.equals("undefined")
                && !nextcourseId.equals("0")) {
            String applyId = request.getParameter("applyId");
            TacheFlow flow = flowChangeService.getFlowByTacheCode(nextcourseId,applyId);
            if (flow != null) {
                flowinfo.put("flowInfo", flow.getFlowInfo());
                JsonValidator jsonV=new JsonValidator();
                if(StringUtils.isNotEmpty(flow.getFlowInfo())&&jsonV.validate(flow.getFlowInfo())){
                    List<Map<String, Object>> nlist=monitorNodeService.findMonitorNodeByTCode(flow.getTacheCode());
                    String neStr=FlowDataUtil.setEditState(flow.getFlowInfo(), nlist);
                    flowinfo.put("flowInfo", neStr);
                }
                flowinfo.put("tacheId", flow.getTacheId());
                flowinfo.put("tacheCode", flow.getTacheCode());
                flowinfo.put("busCode", flow.getBusCode());
                flowinfo.put("flowHeight", flow.getFlowHeight());
                flowinfo.put("applyId", flow.getApplyId());
            }
        }
        json = JSON.toJSONString(flowinfo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    
    /**
     * 暂存
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "tempSave")
    public void tempSaveflow(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String json = "";
        saveFlowInfo(request);
        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    
    /**
     * 保存流程图信息
     * @param request
     * @return
     */
    public String saveFlowInfo(HttpServletRequest request){
        String tacheCode = request.getParameter("tacheCode");
        String flowInfo = request.getParameter("flowInfo");
        String flowSvg=request.getParameter("flowSvg");
        String height = request.getParameter("flowHeight");
        String applyId = request.getParameter("applyId");
        String tacheId = request.getParameter("tacheId");
        SysUser sysUser = AppUtil.getLoginUser();
        String newflow="";
        String isAutoCreate=request.getParameter("isAutoCreate");//是否自动编码
        if("1".equals(isAutoCreate)){//自动编码
            newflow=FlowDataUtil.setNodeIdForChange(flowInfo, tacheCode);//setNodeIdKey(flowInfo, tacheCode);
        }else{//手动编码
            newflow=flowInfo;
        }
        flowChangeService.updateFlow(tacheId, newflow, height,applyId,sysUser.getUserId(),flowSvg);
        return "success";
    }
    

    
    /**
     * 2.确认
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "submitAudit")
    public void submitAudit(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String json = "";
        String tacheCode = request.getParameter("tacheCode");
        String busCode = request.getParameter("busCode");
        String applyId = request.getParameter("applyId");
        SysUser sysUser = AppUtil.getLoginUser();
        flowChangeService.submitAudit(tacheCode, busCode,sysUser.getUserId(),applyId);
        //删除旧的过程信息
//        busProcessService.deleteProcessByBus(busCode);
        //生成节点对应数据
        List<TacheFlow> flist =flowChangeService.getFlowByBusiCode(busCode, applyId);
        for (int i = 0; i < flist.size(); i++) {
            TacheFlow flow=flist.get(i);
            String jsonStr=flow.getFlowInfo();
            JsonValidator jsonV=new JsonValidator();
            if(StringUtils.isNotEmpty(flow.getFlowInfo())&&jsonV.validate(flow.getFlowInfo())){
                JSONObject jsonObj = JSON.parseObject(jsonStr);
                JSONArray data = jsonObj.getJSONArray("nodeDataArray");
                List<FlowNodeBean> list = new ArrayList<FlowNodeBean>();
                for (int j = 0; j< data.size(); j++) {
                    String nodestr = data.getString(j);
                    FlowNodeBean bean = JSON.parseObject(nodestr, FlowNodeBean.class);
                    if("start".equals(bean.getCategory())||"end".equals(bean.getCategory())
                            ||"Comment".equals(bean.getCategory())){
                    }else{
                        list.add(bean);
                    }
                    //list.add(bean);
                }
                processChangeService.saveBatch(list, sysUser.getDepCode()
                        ,sysUser.getUserId(),flow.getTacheCode(),applyId);
            }
        }
        
        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    /**
     * 方法：3.流程图修改后审核通过
     * (更新专项状态；更新环节状态;生成过程数据;更新流程图到正式表；更新过程数据到正式表）
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "flowPassAudit")
    public void flowPassAudit(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String json = "";
        //申报号
        String applyId = request.getParameter("applyId");
        SysUser sysUser = AppUtil.getLoginUser();
        //更新流程图状态
        flowChangeService.endAudit("3", applyId,sysUser.getUserId());
        
        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    /**
     * 流程图变更申请通过(更新业务专项状态、更新环节状态）
     * 
     * @param request
     * @param response
     * @throws IOException
     */
//    @RequestMapping(params = "applyPassAudit")
//    public void applyPassAudit(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        String json = "";
//        //申报号
//        String applyId = request.getParameter("applyId");
//        //String busCode = request.getParameter("busCode");
//        SysUser sysUser = AppUtil.getLoginUser();
//        //更新流程图状态;更新环节状态
//        flowChangeService.applyAuditPass(applyId, sysUser.getUserId());
//        json = "success";
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.print(json);
//        out.flush();
//        out.close();
//    }
    
    
}
