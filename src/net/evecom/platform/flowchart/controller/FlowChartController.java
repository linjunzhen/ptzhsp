/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;

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
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.FlowChartInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.flowchart.service.BusProcessService;
import net.evecom.platform.flowchart.service.MonitorNodeService;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.system.model.SysUser;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.org.objectweb.asm.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述 监察流程Controller
 * 
 * @author Sundy Sun
 * @version 2.0
 */
@Controller
@RequestMapping("/flowChartController")
public class FlowChartController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowChartController.class);
    /**
     * 流程
     */
    @Resource
    private TacheFlowService flowChartService;
    /**
     * 监察点service
     */
    @Resource
    private MonitorNodeService monitorNodeService;
    
    /**
     * 节点service
     */
    @Resource
    private BusProcessService busProcessService;
    /**
     * 
     * 跳转到权力运行列表界面
     * @param request
     * @return
     */
    @RequestMapping(params = "busSpeList")
    public ModelAndView busSpeList(HttpServletRequest request) {
        return new ModelAndView("flowchart/busSpList");
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
        //SysUser sysUser = AppUtil.getLoginUser();
        //String sysUserResKey = sysUser.getResKeys();
        SqlFilter filter = new SqlFilter(request);
//        String unitCode = null;
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            unitCode = sysUser.getUnitCode();
//            filter.addFilter("Q_U.UNIT_CODE_EQ", unitCode);
//        }
//        String userBusCodes = AppUtil.getCurrentUserBusCodeString();
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            filter.addFilter("Q_U.UNIT_CODE_IN", userBusCodes);
//        }
        
        filter.addFilter("Q_U.STATUS_NEQ", "0");//专项确认以后的；
        filter.addSorted("U.UPDATE_TIME","desc");
        List<Map<String, Object>> list = flowChartService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    
    /**
     * 跳转到多环节流程绘制页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowdraw")
    public ModelAndView flowDraw(HttpServletRequest request,
            HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            List<TacheFlow> flist =flowChartService.getFlowByBusId(entityId);
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    Map<String, Object> flowinfo = new HashMap<String, Object>();
                    TacheFlow flow = flist.get(i);
                    flowinfo.put("flowInfo", flow.getFlowInfo());
                    flowinfo.put("tacheId", flow.getTacheId());
                    flowinfo.put("tacheCode", flow.getTacheCode());
                    flowinfo.put("busiCode", flow.getBusCode());
                    map.put("" + flow.getTacheCode(), flowinfo);
                }
                // request.setAttribute("flowdata", flist.get(0).getFlowInfo());
                // request.setAttribute("result", map);
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
                BusSpecialInfo bus = flowChartService.getBusByBusCode(flist
                        .get(0).getBusCode());
                request.setAttribute("busInfo", bus);
                request.setAttribute("stateFlg", bus.getStatus());
            }
        }
        return new ModelAndView("flowchart/flowdraw");
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
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            List<TacheFlow> flist =flowChartService.getFlowByBusId(entityId);
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    Map<String, Object> flowinfo = new HashMap<String, Object>();
                    TacheFlow flow = flist.get(i);
                    flowinfo.put("flowInfo", flow.getFlowInfo());
                    flowinfo.put("tacheId", flow.getTacheId());
                    flowinfo.put("tacheCode", flow.getTacheCode());
                    flowinfo.put("busiCode", flow.getBusCode());
                    map.put("" + flow.getTacheCode(), flowinfo);
                }
                // request.setAttribute("flowdata", flist.get(0).getFlowInfo());
                // request.setAttribute("result", map);
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
                BusSpecialInfo bus = flowChartService.getBusByBusCode(flist
                        .get(0).getBusCode());
                request.setAttribute("busInfo", bus);
                request.setAttribute("stateFlg", bus.getStatus());
            }
        }
        return new ModelAndView("flowchart/flowview");
    }

    /**
     * 跳转到多环节流程
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "flowmain")
    public ModelAndView flowMain(HttpServletRequest request,
            HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = AppUtil.getSession();
        session.removeAttribute("tacheInfoList");
        session.removeAttribute("busInfo");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            List<TacheFlow> flist = flowChartService
                    .getFlowByBusiCode(entityId);
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    Map<String, Object> flowinfo = new HashMap<String, Object>();
                    TacheFlow flow = flist.get(i);
                    flowinfo.put("flowInfo", flow.getFlowInfo());
                    flowinfo.put("tacheId", flow.getTacheId());
                    flowinfo.put("tacheCode", flow.getTacheCode());
                    flowinfo.put("busiCode", flow.getBusCode());
                    map.put("" + flow.getTacheCode(), flowinfo);
                }
                // request.setAttribute("flowdata", flist.get(0).getFlowInfo());
                // request.setAttribute("result", map);
                session.setAttribute("tacheInfoList", flist);
                BusSpecialInfo bus = flowChartService.getBusByBusCode(flist
                        .get(0).getBusCode());
                session.setAttribute("busInfo", bus);
                request.setAttribute("stateFlg", bus.getStatus());
            }
        }
        return new ModelAndView("flowchart/flowmain");
    }

    /**
     * 加载流程数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "saveflow")
    public void saveflow(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String json = "";
        //String tacheCode = request.getParameter("tacheCode");
        //String tacheId = request.getParameter("tacheId");
        String nextcourseId = request.getParameter("nextTacheCode");
        //String entityId = request.getParameter("tacheId");
        saveFlowInfo(request);
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(nextcourseId)
                && !nextcourseId.equals("undefined")
                && !nextcourseId.equals("0")) {
            TacheFlow flow = flowChartService.getFlowByTacheCode(nextcourseId);
            //List<Map<String, Object>> list=monitorNodeService.findMonitorNodeByTCode(flow.getTacheCode());
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
     * 保存流程图信息
     * @param request
     * @return
     */
    public String saveFlowInfo(HttpServletRequest request){
        String tacheCode = request.getParameter("tacheCode");
        String flowInfo = request.getParameter("flowInfo");
        String flowSvg=request.getParameter("flowSvg");
        String height = request.getParameter("flowHeight");
        SysUser sysUser = AppUtil.getLoginUser();
        String newflow="";
        String isAutoCreate=request.getParameter("isAutoCreate");//是否自动编码
        if("1".equals(isAutoCreate)){//自动编码
            newflow=FlowDataUtil.setNodeIdForChange(flowInfo, tacheCode);//setNodeIdKey(flowInfo, tacheCode);
        }else{//手动编码
            newflow=flowInfo;
        }
        String entityId = request.getParameter("tacheId");
        Map<String, Object> reqData = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isNotEmpty(entityId)){
            reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm"));
            reqData.put("UPDATE_USER", sysUser.getUserId());
            reqData.put("TACHE_CODE", tacheCode);
            reqData.put("FLOW_INFO", newflow);
            reqData.put("FLOW_SVG", flowSvg);
            reqData.put("TACHE_ID", entityId);
            reqData.put("FLOW_HEIGHT", height);
            String recordId = flowChartService.saveOrUpdate(reqData,"T_LCJC_BUS_TACHE",entityId);
        }
        return "success";
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
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        // List<Map<String, Object>> topList = new ArrayList<Map<String,
        // Object>>();
        if (StringUtils.isNotEmpty(tacheCode) && !tacheCode.equals("undefined")
                && !tacheCode.equals("0")) {
            TacheFlow flow = flowChartService.getFlowByTacheCode(tacheCode);
            //FlowJsonHandle.findOutOf(flow.getFlowInfo());
            if (flow != null) {
                flowinfo.put("flowInfo", flow.getFlowInfo());
                JsonValidator jsonV=new JsonValidator();
                if(StringUtils.isNotEmpty(flow.getFlowInfo())&&jsonV.validate(flow.getFlowInfo())){
                    List<Map<String, Object>> nlist=monitorNodeService.findMonitorNodeByTCode(flow.getTacheCode());
                    String neStr=FlowDataUtil.setEditState(flow.getFlowInfo(), nlist);
                    flowinfo.put("flowInfo", neStr);
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
    @RequestMapping(params = "getTemplate")
    public void getTemplate(HttpServletRequest request, 
            HttpServletResponse response)throws IOException {
        String json = "";
        //String tacheCode = request.getParameter("tacheCode");
        Map<String, Object> flowinfo = new HashMap<String, Object>();

        List<Map<String, Object>> template=flowChartService.findTemplate("default");
        if (template.size()>0) {
            flowinfo=template.get(0);
        }else{ 
        }
        json = JSON.toJSONString(flowinfo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    
    /***
     * 下拉获取模板数据源
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "comboxTemplate")
    public void comboxTemplate(HttpServletRequest request, HttpServletResponse response) {
        //SysUser sysUser = AppUtil.getLoginUser();
        //String sysUserResKey = sysUser.getResKeys();
        SqlFilter filter = new SqlFilter(request);
        //String unitCode = null;
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            unitCode = sysUser.getUnitCode();
//            filter.addFilter("Q_U.UNIT_CODE_EQ", unitCode);
//        }
        
        //filter.addFilter("Q_U.CHANGE_STATE_EQ","-1");
        List<Map<String, Object>> list = flowChartService.findTemplateBySqlFilter(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
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
     * 确认
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
        SysUser sysUser = AppUtil.getLoginUser();
        flowChartService.submitAudit(tacheCode, busCode,sysUser.getUserId());
        //flowChartService.updateBusiState("1", busCode, null, sysUser.getUserId());
        //生成节点对应数据
        List<TacheFlow> flist =flowChartService.getFlowByBusiCode(busCode);
        for (int i = 0; i < flist.size(); i++) {
            TacheFlow flow=flist.get(i);
            String jsonStr=flow.getFlowInfo();
            JsonValidator jsonV=new JsonValidator();
            if(StringUtils.isNotEmpty(jsonStr)&&jsonV.validate(jsonStr)){
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
                }
                busProcessService.saveBatch(list,flow.getUnitCode(),sysUser.getUserId(),flow.getTacheCode());
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
     * 审核通过
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "flowPassAudit")
    public void flowPassAudit(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String json = "";
        //String tacheCode = request.getParameter("tacheCode");
        String busCode = request.getParameter("busCode");
        SysUser sysUser = AppUtil.getLoginUser();
        flowChartService.flowPassAudit("3", busCode, sysUser.getUserId());
        
        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

}
