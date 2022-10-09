/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.flowchart.service.BusProcessService;
import net.evecom.platform.flowchart.service.MonitorNodeService;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Controller
@RequestMapping("monitorNodeController")
public class MonitorNodeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FlowChartController.class);
    /**
     * 监察点service
     */
    @Resource
    private MonitorNodeService monitorNodeService;
    /**
     * 流程service
     */
    @Resource
    private TacheFlowService flowChartService;
    /**
     * 节点service
     */
    @Resource
    private BusProcessService busProcessService;
    /**
     * 正常状态
     */
    public static final int NORMAL_STATUS = 1;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 
     * 跳转到监察点列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "monitorNodeList")
    public ModelAndView monitorNodeList(HttpServletRequest request) {
        return new ModelAndView("flowchart/monitorNode/monitorNodeList");
    }

    /**
     * 获取业务监察点列表数据URL
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
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

        filter.addFilter("Q_U.TACHE_STATUS_NEQ", "0");
        filter.addSorted("U.UPDATE_TIME", "desc");
        //filter.addSorted("U.TACHE_CODE", "asc");
        filter.addSorted("U.BUS_NAME", "desc");
        List<Map<String, Object>> list = monitorNodeService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到监察节点配置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "factorMng")
    public ModelAndView factorMng(HttpServletRequest request, HttpServletResponse response) {
        //String tacheCode = request.getParameter("tacheCode");
        String busCode = request.getParameter("busCode");
        if (StringUtils.isNotEmpty(busCode) && !busCode.equals("undefined")) {
            
            BusSpecialInfo bus = flowChartService.getBusByBusCode(busCode);
            request.setAttribute("busInfo", bus);
            List<TacheFlow> flist = flowChartService.getFlowByBusiCode(bus.getBusCode());
            if (flist != null) {
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
            }
        }
        return new ModelAndView("flowchart/monitorNode/factorMng");
    }

    /**
     * 跳转到监察节点配置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "factorView")
    public ModelAndView factorView(HttpServletRequest request, HttpServletResponse response) {
        String busCode = request.getParameter("busCode");
        if (StringUtils.isNotEmpty(busCode) && !busCode.equals("undefined") && !busCode.equals("0")) {
           
            BusSpecialInfo bus = flowChartService.getBusByBusCode(busCode);
            request.setAttribute("busInfo", bus);
            List<TacheFlow> flist = flowChartService.getFlowByBusiCode(bus.getBusCode());
            if (flist != null) {
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
            }
        }
        return new ModelAndView("flowchart/monitorNode/factorView");
    }

    /**
     * 跳转到要素信息详细页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "factorInfo")
    public ModelAndView factorInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String processCode = request.getParameter("processCode");
        String tacheCode = request.getParameter("tacheCode");
        String processName = request.getParameter("processName");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> factor = monitorNodeService.getByJdbc("t_lcjc_bus_ruleconfig",
                    new String[] { "RULE_ID" }, new Object[] { entityId });
            request.setAttribute("factorInfo", factor);
        } else {// 新增
            Map<String, Object> factor = new HashMap<String, Object>();
            String message = "";
            try {
                message = new String(processName.getBytes("ISO-8859-1"), "UTF-8");
                factor.put("PROCESS_NAME", message);
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
            factor.put("PROCESS_CODE", processCode);
            factor.put("TACHE_CODE", tacheCode);
            request.setAttribute("factorInfo", factor);
        }
        request.setAttribute("processCode", processCode);
        return new ModelAndView("flowchart/monitorNode/factorInfo");
    }

    /**
     * 方法：获取过程点对应要素
     * 
     * @param request
     *            传入参数
     * @throws IOException
     */
    @RequestMapping(params = "getFactorInfoList")
    @ResponseBody
    public void getFactorInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String processCode = request.getParameter("processCode");
        String json = "";
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            List<Map<String, Object>> factors = monitorNodeService.findByProcessCode(processCode);
            List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < factors.size(); i++) {
                Map<String, Object> map = factors.get(i);
                String dicname = (String) map.get("DIC_NAME");
                if (!StringUtils.isNotEmpty(dicname)) {
                    map.put("DIC_NAME", " ");
                }
                newlist.add(map);
            }
            json = JSON.toJSONString(newlist);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 方法：获取过程点状态
     * 
     * @param request
     *            传入参数
     * @throws IOException
     */
    @RequestMapping(params = "getNodeState")
    @ResponseBody
    public void getNodeState(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String processCode = request.getParameter("processCode");
        String json = "";
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            BusProcessInfo pro = busProcessService.getProcessByCode(processCode);
            json = JSON.toJSONString(pro);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 方法：update过程点状态
     * 
     * @param request
     *            传入参数
     * @throws IOException
     */
    @RequestMapping(params = "updateNodeState")
    @ResponseBody
    public void updateNodeState(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String processCode = request.getParameter("processCode");
        String state = request.getParameter("status");
        SysUser sysUser = AppUtil.getLoginUser();
        String json = "";
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            BusProcessInfo pro = busProcessService.getProcessByCode(processCode);
            json = JSON.toJSONString(pro);
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("PROCESS_CODE", processCode);
            reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("IS_MONITORNODE", state);
            reqData.put("UPDATE_USER", sysUser.getUserId());
            busProcessService.saveOrUpdate(reqData, "t_lcjc_bus_process", pro.getProcessId());
            busProcessService.updateByProcessCode(processCode, sysUser.getUserId(),state);
        }
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
    public void submitAudit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        //String tacheCode = request.getParameter("tacheCode");
        String busCode = request.getParameter("busCode");
        //String flag = request.getParameter("flag");
        SysUser sysUser = AppUtil.getLoginUser();
        //if ("first".equals(flag)) {// 设置状态为0等到集中提交；
        monitorNodeService.factorSubmit(busCode, sysUser.getUserId());
        // }
        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 提交审核要素信息
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "backDraw")
    public void backDraw(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        String tacheCode = request.getParameter("tacheCode");
        String busCode = request.getParameter("busCode");
        SysUser sysUser = AppUtil.getLoginUser();
        // 撤销监察点和要素状态
        busProcessService.cancelNodeAduit(tacheCode, busCode, sysUser.getUserId());

        // 更新流程图审核表
        String configName = "权力运行流程图";
        flowChartService.updateAuditInfo("0", sysUser.getUserId(), busCode, configName);

        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 增加或者修改信息(保存规则表)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RULE_ID");
        Map<String, Object> reqData = BeanUtil.getMapFromRequest(request);
        String factorNote = request.getParameter("RULE_DESC");
        //String type = request.getParameter("ANALYSIS_TYPE");
        String processCode = request.getParameter("PROCESS_CODE");
        String[] substr = processCode.split("\\.");
        BusSpecialInfo bus = flowChartService.getBusByBusCode(substr[0]);
        reqData.put("RULE_DESC", factorNote.trim());
        // String unitType = String.valueOf(treeData.get("UNIT_TYPE"));
        SysUser sysUser = AppUtil.getLoginUser();
        if (StringUtils.isEmpty(entityId)) {
            reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("CREATE_USER", sysUser.getUserId());
            reqData.put("unit_code", bus.getUnitCode());
            reqData.put("ELEMENT_STATUS", "-1");
        }
        reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        reqData.put("UPDATE_USER", sysUser.getUserId());
        String recordId = monitorNodeService.saveOrUpdate(reqData, "T_LCJC_BUS_RULECONFIG", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 要素记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 要素记录", SysLogService.OPERATE_TYPE_ADD);
        }

        j.setMsg(processCode);
        j.setSuccess(true);
        return j;
    }

    /**
     * (业务梳理)修改或者修改操作
     * 
     * @author Water Guo
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateBatch")
    @ResponseBody
    public AjaxJson saveOrUpdateBatch(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String, Object> formMap = new HashMap<String, Object>();
        for (String str : formDatas.split("&")) {
            String[] strs = str.split("=");
            if (strs.length > 1) {
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String processCode = String.valueOf(formMap.get("PROCESS_CODE"));
        monitorNodeService.saveOrUpdateBatch(variables);
        j.setSuccess(true);
        j.setMsg(processCode);
        return j;
    }

    /***
     * 描述：(业务梳理)可编辑表格数据的加载
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "factInfodatagrid")
    public void factInfodatagrid(HttpServletRequest request, HttpServletResponse response) {
        String processCode = request.getParameter("processCode");
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            List<Map<String, Object>> factors = monitorNodeService.findByProcessCode(processCode);
            for (int i = 0; i < factors.size(); i++) {
                Map<String, Object> map = factors.get(i);
                String dicname = (String) map.get("DIC_NAME");
                if (!StringUtils.isNotEmpty(dicname)) {
                    map.put("DIC_NAME", " ");
                }
                newlist.add(map);
            }
        }
        request.setAttribute("processCode", processCode);
        this.setListToJsonString(newlist.size(), newlist, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "delFactorInfo")
    @ResponseBody
    public AjaxJson delFactorInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RULE_ID");
        monitorNodeService.remove("T_LCJC_BUS_RULECONFIG", "RULE_ID", new Object[] { entityId });
        j.setMsg("删除成功");
        j.setSuccess(true);
        return j;
    }
    
    /**
     * 加载流程数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "getNodeflow")
    public void getNodeflow(HttpServletRequest request, 
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
                    String nnewStr=FlowDataUtil.setFlowNodeColor(neStr, nlist);
                    flowinfo.put("flowInfo", nnewStr);
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

}
