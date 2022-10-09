/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.controller;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowDataUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchange.service.FlowChangeService;
import net.evecom.platform.flowchange.service.ProcessChangeService;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.flowchart.service.MonitorNodeService;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 监察点及要素变更controller
 * 
 * @author Sundy Sun
 * @version v2.0
 * 
 */
@Controller
@RequestMapping("/processChange")
public class ProcessChangeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProcessChangeController.class);
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
    private ProcessChangeService processChangeService;
    /**
     * 监察点service
     */
    @Resource
    private MonitorNodeService monitorNodeService;

    /**
     * 
     * 跳转到监察点变更列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "processChangeList")
    public ModelAndView processChangeList(HttpServletRequest request) {
        return new ModelAndView("flowchange/monitorNode/processList");
    }

    /**
     * 获取监察点列表数据URL
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
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
        filter.addSorted("U.UPDATE_TIME", "desc");
        //filter.addSorted("U.TACHE_CODE", "asc");
        filter.addSorted("U.BUS_NAME", "desc");
        List<Map<String, Object>> list = processChangeService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到发起变更选择页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "changeSelect")
    public ModelAndView changeSelect(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("flowchange/monitorNode/changeSelect");
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

        filter.addFilter("Q_U.STATUS_EQ", "3");
        filter.addFilter("Q_U.CHANGE_FLAG_EQ", "-1");
        List<Map<String, Object>> list = flowChartService.findBySqlFilter(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 方法:1.监察点及要素发起变更
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "startChange")
    @ResponseBody
    public AjaxJson startNodeChange(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        String entityId = request.getParameter("BUS_ID");
        String busCode = request.getParameter("BUS_CODE");
        Map<String, Object> reqData = BeanUtil.getMapFromRequest(request);

        if (StringUtils.isNotEmpty(entityId)) {
            // 1.copy监察点数据；2.copy监察要素数据；3.更改正式环境的业务专项数据表change_flag;
            SqlFilter filter = new SqlFilter(request);
            filter.addFilter("Q_U.BUS_CODE_EQ", busCode);
            // filter.addFilter("Q_U.BUS_ID_EQ",entityId);
            filter.addSorted("U.APPLY_ID", "desc");
            List<Map<String, Object>> list = flowChangeService.findNewestBySqlFilter(filter);
            String applyId = (String) list.get(0).get("APPLY_ID");
            if (StringUtils.isNotEmpty(applyId) || list.size() == 1) {
                processChangeService.copyMonitorNode(busCode, sysUser.getUserId(), applyId);
            } else if (list.size() > 1) {
                applyId = (String) list.get(1).get("APPLY_ID");
                processChangeService.copyMonitorNode(busCode, sysUser.getUserId(), applyId);
            }
            reqData.put("CHANGE_FLAG", "2");
            flowChartService.saveOrUpdate(reqData, "t_lcjc_bus_special", entityId);
            sysLogService.saveLog("发起变更ID为[" + entityId + "]的 监察点及要素变更记录", SysLogService.OPERATE_TYPE_EDIT);

        }

        j.setMsg("变更成功");
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转到监察节点配置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "factorMng")
    public ModelAndView factorMng(HttpServletRequest request, HttpServletResponse response) {
        String busCode = request.getParameter("busCode");
        String applyId = request.getParameter("applyId");
        if (StringUtils.isNotEmpty(busCode) && !busCode.equals("undefined") && !busCode.equals("0")) {
            List<Map<String, Object>> list = flowChangeService.getBusByBusCode(busCode, applyId);
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
            List<TacheFlow> flist = flowChangeService.getFlowByBusiCode(bus.getBusCode(), applyId);
            if (flist != null) {
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
            }
        }
        return new ModelAndView("flowchange/monitorNode/changeFactorMng");
    }

    /**
     * 加载流程数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "getflow")
    public void getflow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        String tacheCode = request.getParameter("tacheCode");
        String applyId = request.getParameter("applyId");
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        // List<Map<String, Object>> topList = new ArrayList<Map<String,
        // Object>>();
        if (StringUtils.isNotEmpty(tacheCode) && !tacheCode.equals("undefined") && !tacheCode.equals("0")) {
            TacheFlow flow = flowChangeService.getFlowByTacheCode(tacheCode, applyId);
            if (flow != null) {
                List<Map<String, Object>> nlist=processChangeService
                    .findMonitorNodeByTCode(flow.getTacheCode(),applyId);
                String nnewStr=FlowDataUtil.setFlowNodeColor(flow.getFlowInfo(), nlist);
                flowinfo.put("flowInfo",nnewStr);
                //flowinfo.put("flowInfo", flow.getFlowInfo());
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
     * 跳转到监察节点配置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "factorView")
    public ModelAndView factorView(HttpServletRequest request, HttpServletResponse response) {
        String busCode = request.getParameter("busCode");
        String applyId = request.getParameter("applyId");
        if (StringUtils.isNotEmpty(busCode) && !busCode.equals("undefined") && !busCode.equals("0")) {
            List<Map<String, Object>> list = flowChangeService.getBusByBusCode(busCode, applyId);
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
            List<TacheFlow> flist = flowChangeService.getFlowByBusiCode(bus.getBusCode(), applyId);
            if (flist != null) {
                request.setAttribute("tacheInfoList", flist);
                request.setAttribute("firstTache", flist.get(0).getTacheCode());
            }
        }
        return new ModelAndView("flowchange/monitorNode/changeFactorView");
    }

    /**
     * 跳转到要素信息详细页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "factorInfo")
    public ModelAndView factorInfo(HttpServletRequest request) {
//        String entityId = request.getParameter("entityId");
        String applyId = request.getParameter("applyId");
        String processCode = request.getParameter("processCode");
//        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
//            Map<String, Object> factor = processChangeService.getByJdbc("t_lcjc_bus_ruleconfig_change",
//                    new String[] { "CHANGE_ID" }, new Object[] { entityId });
//            request.setAttribute("factorInfo", factor);
//        } else {// 新增
//            String tacheCode = request.getParameter("tacheCode");
//            String processName = request.getParameter("processName");
//            Map<String, Object> factor = new HashMap<String, Object>();
//            String message = "";
//            try {
//                message = new String(processName.getBytes("ISO-8859-1"), "UTF-8");
//                factor.put("PROCESS_NAME", message);
//            } catch (UnsupportedEncodingException e) {
//                log.error(e.getMessage());
//            }
//            factor.put("PROCESS_CODE", processCode);
//            factor.put("TACHE_CODE", tacheCode);
//            factor.put("APPLY_ID", applyId);
//            request.setAttribute("factorInfo", factor);
//        }
        request.setAttribute("processCode", processCode);
        request.setAttribute("applyId", applyId);
        return new ModelAndView("flowchange/monitorNode/factorInfo");
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
        String applyId = request.getParameter("applyId");
        String json = "";
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            List<Map<String, Object>> factors = processChangeService.findByProcessCode(processCode, applyId);
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

    /***
     * 描述：(业务梳理)可编辑表格数据的加载
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "factInfodatagrid")
    public void factInfodatagrid(HttpServletRequest request, HttpServletResponse response) {
        String processCode = request.getParameter("processCode");
        String applyId = request.getParameter("applyId");
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            List<Map<String, Object>> factors = processChangeService.findByProcessCode(processCode, applyId);
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
        request.setAttribute("applyId", applyId);
        this.setListToJsonString(newlist.size(), newlist, null, JsonUtil.EXCLUDE, response);
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
        //String applyId = String.valueOf(formMap.get("APPLY_ID"));
        processChangeService.saveOrUpdateBatch(variables);
        j.setSuccess(true);
        j.setMsg(processCode);
        return j;
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
        String applyId = request.getParameter("applyId");
        String json = "";
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            BusProcessInfo pro = processChangeService.getProcessByCode(processCode, applyId);
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
        //String changeId = request.getParameter("changeId");
        String state = request.getParameter("status");
        String applyId = request.getParameter("applyId");
        SysUser sysUser = AppUtil.getLoginUser();
        String json = "";
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") && !processCode.equals("0")) {
            BusProcessInfo pro = processChangeService.getProcessByCode(processCode, applyId);
            json = JSON.toJSONString(pro);
            Map<String, Object> reqData = new HashMap<String, Object>();
            reqData.put("PROCESS_CODE", processCode);
            reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("UPDATE_USER", sysUser.getUserId());
            reqData.put("IS_MONITORNODE", state);
            reqData.put("APPLY_ID", applyId);
            reqData.put("PROCESS_ID", pro.getProcessId());
            reqData.put("CHANGE_ID", pro.getChangeId());
            processChangeService
                    .saveOrUpdate(reqData, "t_lcjc_bus_process_change", pro.getChangeId());
            processChangeService.updateByProcessCode(processCode, sysUser.getUserId(),state,applyId);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 变更要素提交确认
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
        String applyId = request.getParameter("applyId");
        SysUser sysUser = AppUtil.getLoginUser();
        processChangeService.changeFactorSubmit(busCode, sysUser.getUserId(), applyId);

        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 跳转到流程图变更审批表单（新旧流程图对比页面）
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "viewProcessDiff")
    public ModelAndView viewProcessDiff(HttpServletRequest request, HttpServletResponse response) {
        String applyId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(applyId) && !applyId.equals("undefined") && !applyId.equals("0")) {
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
            // 获取旧的流程图信息
            SqlFilter filter = new SqlFilter(request);
            filter.addFilter("Q_U.BUS_CODE_EQ", bus.getBusCode());
            filter.addSorted("U.TACHE_CODE", "asc");
            List<Map<String, Object>> oldlist = monitorNodeService.findBySqlFilter(filter);
            if (oldlist != null) {
                request.setAttribute("oldProcessList", oldlist);
            }

            // 获取新的流程图信息
            filter.addFilter("Q_U.APPLY_ID_EQ", applyId);
            filter.addSorted("U.TACHE_CODE", "asc");
            List<Map<String, Object>> newlist = processChangeService.findBySqlFilter(filter);
            if (newlist != null) {
                request.setAttribute("newProcessList", newlist);
            }

            request.setAttribute("stateFlg", bus.getStatus());
            request.setAttribute("busInfo", bus);
        }
        return new ModelAndView("flowchange/monitorNode/viewProcessDiff");
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
        String entityId = request.getParameter("CHANGE_ID");
        Map<String, Object> reqData = BeanUtil.getMapFromRequest(request);
        String factorNote = request.getParameter("RULE_DESC");
        String processCode = request.getParameter("PROCESS_CODE");
        String applyId = request.getParameter("APPLY_ID");
        String[] substr = processCode.split("\\.");
        BusSpecialInfo bus = flowChartService.getBusByBusCode(substr[0]);

        reqData.put("RULE_DESC", factorNote.trim());
        reqData.put("APPLY_ID", applyId);
        // String unitType = String.valueOf(treeData.get("UNIT_TYPE"));
        SysUser sysUser = AppUtil.getLoginUser();
        if (StringUtils.isEmpty(entityId)) {
            reqData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            reqData.put("CREATE_USER", sysUser.getUserId());
            reqData.put("ELEMENT_STATUS", "-1");
            reqData.put("APPLY_ID", applyId);
            String nextRuleId = UUIDGenerator.getUUID();
            reqData.put("RULE_ID", nextRuleId);
            reqData.put("unit_code", bus.getUnitCode());
        }
        reqData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        reqData.put("UPDATE_USER", sysUser.getUserId());
        if (StringUtils.isNotEmpty(entityId)) {
            processChangeService.saveOrUpdate(reqData, "T_LCJC_BUS_RULECONFIG_change", entityId);
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 要素记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            String recordId = processChangeService.saveOrUpdate(reqData, "T_LCJC_BUS_RULECONFIG_change", null);
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 要素记录", SysLogService.OPERATE_TYPE_ADD);
        }

        j.setMsg(processCode);
        j.setSuccess(true);
        return j;
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
        String entityId = request.getParameter("CHANGE_ID");
        processChangeService.remove("T_LCJC_BUS_RULECONFIG_change", "CHANGE_ID", new Object[] { entityId });
        j.setMsg("删除成功");
        j.setSuccess(true);
        return j;
    }

    /**
     * 监察点变更审核通过(更新过程点和监察点状态）
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "passAudit")
    public void passAudit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";
        String busCode = request.getParameter("busCode");
        SysUser sysUser = AppUtil.getLoginUser();
        flowChangeService.monitorNodePassAudit("3", busCode, sysUser.getUserId());

        json = "success";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
}
