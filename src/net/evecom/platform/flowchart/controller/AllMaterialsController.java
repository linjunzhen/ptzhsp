/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import net.evecom.core.util.FlowDataUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.JsonValidator;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchart.service.BusColumnBasicService;
import net.evecom.platform.flowchart.service.BusDeployService;
import net.evecom.platform.flowchart.service.EstimateService;
import net.evecom.platform.flowchart.service.AllMaterialsService;
import net.evecom.platform.flowchange.service.BusAuditChangeService;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述业务梳理（变更）提交审核统一处理模块
 * 
 * @author Water Guo
 * @created 2015-8-26 下午5:28:33
 */
@Controller
@RequestMapping("/allMaterialsController")
public class AllMaterialsController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(AllMaterialsController.class);

    /**
     * @Resource sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 流程
     */
    @Resource
    private TacheFlowService flowChartService;
    /**
     * 业务材料审核中间数据封装
     */
    @Resource
    private AllMaterialsService allMaterialsService;
    /**
     * 基本信息字段
     */
    @Resource
    private BusColumnBasicService busColumnBasicService;
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 部署图信息
     */
    @Resource
    private BusDeployService busDeployService;

    /**
     * 业务估算数据
     */
    @Resource
    private EstimateService estimateService;
    /**
     * 变更审核记录表业务
     */
    @Resource
    private BusAuditChangeService busAuditChangeService;

    /**
     * (业务梳理)描述根据操作申报号和过程编号从【历史表】中获取审核详情页面监察点监察要素与监察规则
     * @author Water Guo
     * @created 2015-9-10 上午9:32:25
     * @param request
     * @param response
     */
    @RequestMapping(params = "RuleDatagrid")
    public void ruleDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String appliyId = request.getParameter("appliyId");
        String processCode = request.getParameter("processCode");
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") 
                && StringUtils.isNotEmpty(appliyId)
                && !appliyId.equals("undefined")) {
            // 根据操作申报号和过程编号查询该过程节点的监察点相关信息（要素和过程）
            list = allMaterialsService.listMaterialsRuleDatagrid(filter, appliyId, processCode);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 (业务变更)描述根据操作申报号和过程编号从【变更表】中获取审核详情页面监察点监察要素与监察规则
     * @author Water Guo
     * @created 2015-10-11 下午12:54:42
     * @param request
     * @param response
     */
    @RequestMapping(params = "RuleChangeDatagrid")
    public void ruleChangeDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String appliyId = request.getParameter("appliyId");
        String processCode = request.getParameter("processCode");
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined") 
                && StringUtils.isNotEmpty(appliyId)
                && !appliyId.equals("undefined")) {
            // （数据源变更表）根据操作申报号和过程编号查询该过程节点的监察点相关信息（要素和过程）
            list = allMaterialsService.listMaterialsChangeRuleDatagrid(filter, appliyId, processCode);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到审核详情表单页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/flowAllInfo")
    public ModelAndView flowAllInfo(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        if (StringUtils.isNotEmpty(taskId) && !taskId.equals("null")) {
            Map<String,Object> map = allMaterialsService.getByJdbc("JBPM6_TASK", 
                    new String[]{"TASK_ID"}, new String[]{taskId});
            String option = String.valueOf(map.get("HANDLE_OPINION"));
            if(StringUtils.isNotEmpty(option) && !"null".equals(option)){
                request.setAttribute("option", option);
            }            
            request.setAttribute("taskId", taskId);
        }else{
            log.error("系统错误：无法从流程引擎中获取taskId或taskId参数未传递！！");
            request.setAttribute("taskId", "null");
        }
        String exeId = request.getParameter("exeId");
        String applyId = null;
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("undefined")) {
            Map<String, Object> flowInfo = allMaterialsService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new String[] { exeId });
            if (flowInfo != null) {
                applyId = String.valueOf(flowInfo.get("BUS_RECORDID"));
            }
        } else {
            log.error("系统错误：无法从流程引擎中获取exeId或exeId参数未传递！！");
        }
        if (StringUtils.isNotEmpty(applyId) && !applyId.equals("null")) {
            request.setAttribute("applyId", applyId);
            Map<String, Object> apply = allMaterialsService.getByJdbc("T_LCJC_APPLYINFO", new String[] { "APPLY_ID" },
                    new String[] { applyId });

            // 专项编码
            String buscode = String.valueOf(apply.get("BUS_CODE"));
            //getFlowInfobyApplyIdFromHistory(request, applyId, buscode);
            getFlowInfobyApplyIdFromHistoryNew(request,applyId,buscode);

            // 描述 根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
            List<Map<String, Object>> syses = busColumnBasicService.getListSysByBusCode(buscode, applyId);
            request.setAttribute("sysList", syses);

            // TYPE_STAGE = 1,2
            // APPLY_TYPE = 1
            String applyType = String.valueOf(apply.get("APPLY_TYPE"));
            if (StringUtils.isEmpty(applyType) || applyType.equals("null")) {
                log.error(" applyType IS NULL");
                applyType = "0";
            }
            String applyStage = String.valueOf(apply.get("TYPE_STAGE"));
            if (StringUtils.isEmpty(applyStage) || applyStage.equals("null")) {
                log.error(" applyStage IS NULL");
                applyStage = "0";
            }
            request.setAttribute("applyType", applyType);
            request.setAttribute("applyStage", applyStage);
            // 部署图(通过部门和操作申报号获得部署图相关的信息)
            // List<Map<String, Object>> deploys =
            // busDeployService.getListHistoryByApply(bus.getUnitCode(),
            // applyId);
            // request.setAttribute("sysDeploys", deploys);
            // 估算信息（通过部门和操作申报号获得业务估算信息相关的信息）
            // List<Map<String, Object>> estimates =
            // estimateService.getListHistoryByApply(bus.getUnitCode(),
            // applyId);
            // request.setAttribute("sysEstimates", estimates);
        } else {
            log.error("系统发生不可预期的错误，无法获取操作申报号:applyId is null !!");
        }
        return new ModelAndView("flowchart/allmaterials/FlowAllMaterialsForm");
    }

    /**
     * 描述 变更审核详细页面
     * 
     * @author Water Guo
     * @created 2015-9-24 上午10:23:52
     * @param request
     * @return
     */
    @RequestMapping("/flowAllChangeInfo")
    public ModelAndView flowAllChangeInfo(HttpServletRequest request) {
        String taskId = request.getParameter("taskId");
        if (StringUtils.isNotEmpty(taskId) && !taskId.equals("null")) {
            Map<String,Object> map = allMaterialsService.getByJdbc("JBPM6_TASK", 
                    new String[]{"TASK_ID"}, new String[]{taskId});
            String option = String.valueOf(map.get("HANDLE_OPINION"));
            if(StringUtils.isNotEmpty(option) && !"null".equals(option)){
                request.setAttribute("option", option);
            }            
            request.setAttribute("taskId", taskId);
        }else{
            log.error("系统错误：无法从流程引擎中获取taskId或taskId参数未传递！！");
            request.setAttribute("taskId", "null");
        }
        String exeId = request.getParameter("exeId");
        String applyId = null;
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("undefined")) {
            Map<String, Object> flowInfo = allMaterialsService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new String[] { exeId });
            if (flowInfo != null) {
                applyId = String.valueOf(flowInfo.get("BUS_RECORDID"));
            }
        } else {
            log.error("系统错误：无法从流程引擎中获取exeId或exeId参数未传递！！");
        }
        if (StringUtils.isNotEmpty(applyId) && !applyId.equals("null")) {
            request.setAttribute("applyId", applyId);
            Map<String, Object> apply = allMaterialsService.getByJdbc("T_LCJC_APPLYINFO", new String[] { "APPLY_ID" },
                    new String[] { applyId });
            String lastApplyId = String.valueOf(apply.get("LAST_APPLY_ID"));
            request.setAttribute("lastApplyId", lastApplyId);
            // 专项编码
            String buscode = String.valueOf(apply.get("BUS_CODE"));
            request.setAttribute("buscode", buscode);
            //查询变更提交的记录表
            List<Map<String,Object>> list = busAuditChangeService.listByApplyId(applyId);
            if(list!=null && list.size()==1){
                //单一材料的变更
                singleChange(request, applyId, lastApplyId, buscode,list);
            }else if(list!=null && list.size()>1){
                //多种材料的变更
                mulitChange(request, applyId, lastApplyId, buscode,list);
            }else{
                log.error("变更提交的记录检查表为空！！！");
            }
        } else {
            log.error("系统发生不可预期的错误，无法获取操作申报号:applyId is null !!");
        }
        return new ModelAndView("flowchart/allmaterials/FlowAllChangeMaterialsForm");
    }
    
    /**
     * 描述 变更详情页面中加载流程图的方法
     * @author Water Guo
     * @created 2015-10-20 下午10:22:24
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "getChangeflow")
    public void getflow(HttpServletRequest request, 
            HttpServletResponse response)throws IOException {
        String tacheCode = request.getParameter("tacheCode");
        String isFlowChange = request.getParameter("isFlowChange");
        String lastApplyId = request.getParameter("lastApplyId");
        String applyId = request.getParameter("applyId");
        
        String json = "";
        TacheFlow flow = null;
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(tacheCode) && !tacheCode.equals("undefined")
                && !tacheCode.equals("0") && StringUtils.isNotEmpty(isFlowChange) 
                && !"undefined".equals(isFlowChange)){
            if("true".equals(isFlowChange)){
                //从变更表中获得当前申报号当前环节的流程图信息
                flow = allMaterialsService.getFlowByTacheCodeFromChange(tacheCode,applyId);
                //当流程图做过变更需要从上一个操作申报号中找到对应的流程图对应环节的流程图的SVG字符串
                TacheFlow lastFlow = allMaterialsService.getFlowByTacheCodeFromHistory(tacheCode, lastApplyId);
                if(lastFlow != null){
                    String lastFlowSvg = lastFlow.getFlowSvg();
                    flowinfo.put("lastFlowSvg", lastFlowSvg);
                }else{
                    flowinfo.put("lastFlowSvg", "null");
                }
            }else{
                //从历史表中获得当前申报号当前环节的流程图信息
                flow = allMaterialsService.getFlowByTacheCodeFromHistory(tacheCode,lastApplyId);
            }            
            if (flow != null) {
                flowinfo.put("flowInfo", flow.getFlowInfo());
                JsonValidator jsonV=new JsonValidator();
                if(StringUtils.isNotEmpty(flow.getFlowInfo())&&jsonV.validate(flow.getFlowInfo())){
                    List<Map<String, Object>> nlist = null;
                    if("true".equals(isFlowChange)){
                        // 根据环节编码查询过程 (变更表)
                        nlist =allMaterialsService.findMonitorNodeByTCodeFromChange(flow.getTacheCode(),applyId);
                    }else{
                        // 根据环节编码查询过程 (历史表) 
                        nlist =allMaterialsService.findMonitorNodeByTCodeFromHistory(flow.getTacheCode(),lastApplyId);
                    }
                    String neStr=FlowDataUtil.setEditState(flow.getFlowInfo(), nlist);
                    flowinfo.put("flowInfo", neStr);
                    
                }
                flowinfo.put("flowSvg", flow.getFlowSvg());
                flowinfo.put("tacheId", flow.getTacheId());
                flowinfo.put("tacheCode", flow.getTacheCode());
                flowinfo.put("busCode", flow.getBusCode());
                flowinfo.put("flowHeight", flow.getFlowHeight());
            }
        }else{
            log.error("操作变更标识isFlowChange等参数为空，无法判别此次权力流程图是否变更！！！");
        }
        json = JSON.toJSONString(flowinfo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    // =======以下开始为私有方法=========

    /**
     * 描述 判断该次变更是否含有某项材料
     * 
     * @author Water Guo
     * @created 2015-9-24 下午4:18:47
     * @param list
     * @param isFlowChange
     * @return
     */
    private boolean checkAuditType(List<Map<String, Object>> list, String auditType) {
        boolean flag = false;
        if (!list.isEmpty()) {
            for (Map<String, Object> obj : list) {
                String confcode = String.valueOf(obj.get("CONFIG_CODE"));
                if (auditType.equals(confcode)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 描述 (业务变更)多材料同时变更时变更审核页面数据的组装
     * 
     * @author Water Guo
     * @created 2015-9-24 下午3:56:40
     * @param request
     * @param applyId
     * @param lastApplyId
     * @param buscode
     * @param list
     */
    private void mulitChange(HttpServletRequest request, String applyId, String lastApplyId, String buscode,
            List<Map<String, Object>> list) {
        String basicFlag = null, flowFlag = null, columnFlag = null, elementFlag = null, rulerFlag = null;
        // 当pageType=1表示此次变更只对一种材料的变更，pageType=2代表着多种材料的变更
        String pageType = "2";
        request.setAttribute("pageType", pageType);
        // 1.判断该变更是否涉及流程图的变更
        boolean isFlowChange = checkAuditType(list, "YWLCT");
        request.setAttribute("isFlowChange", isFlowChange);
        // 2.如果未包含流程图变更的，获取该操作申报号的上一个操作申报号的流程图，否则,获取当前操作申报号的流程图
        if (isFlowChange == true) {
            // 业务流程图
            flowFlag = "1";
            // 从变更表中获取环节信息，并获得流程图相关信息
            getFlowInfobyApplyIdFromChange(request,applyId,buscode);
        } else {
            // 业务流程图
            flowFlag = "1";
            // 从历史表中获取环节信息，并获得流程图相关信息
            //getFlowInfobyApplyIdFromHistory(request, lastApplyId, buscode);
            getFlowInfobyApplyIdFromHistoryNew(request,lastApplyId, buscode);
        }
        request.setAttribute("flowFlag", flowFlag);
        // 3.判断是否包含对基本信息的变更
        boolean isBasic = checkAuditType(list, "JCJBXX");
        if (isBasic == true) {
            // 监察基本信息
            basicFlag = "1";
            // （变更业务）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
            List<Map<String, Object>> syses = busColumnBasicService.getListSysByBusCodeChange(buscode, applyId);
            request.setAttribute("sysList", syses);
            // (获取上一个版本操作申报号相关的该专项的基本信息涉及的相关业务系统 
            List<Map<String, Object>> lastSyses = busColumnBasicService.getListSysByBusCode(buscode, lastApplyId);
            request.setAttribute("sysLastList", lastSyses);
        }
        request.setAttribute("basicFlag", basicFlag);
        // 4.判断是否包含对监察字段的变更
        boolean isSuperColumn = checkAuditType(list, "JCSJZD");
        if (isSuperColumn == true) {
            columnFlag = "1";
        }
        request.setAttribute("columnFlag", columnFlag);
        // 5.判断是否包含对监察要素的变更
        boolean isElement = checkAuditType(list, "JCJDYYS");
        if (isElement == true) {
            elementFlag = "1";
        }
        request.setAttribute("elementFlag", elementFlag);
        // 6.判断是否包含对监察规则的变更
        boolean isRuler = checkAuditType(list, "JCGZPZ");
        if (isRuler == true) {
            rulerFlag = "1";
        }
        request.setAttribute("rulerFlag", rulerFlag);
    }

    /**
     * 描述 (业务变更)单一材料的变更表单数据的组装
     * 
     * @author Water Guo
     * @created 2015-9-24 下午3:40:55
     * @param request
     * @param applyId
     * @param lastApplyId
     * @param buscode
     * @param list
     */
    private void singleChange(HttpServletRequest request, String applyId, String lastApplyId, String buscode,
            List<Map<String, Object>> list) {
        String basicFlag = null, flowFlag = null, columnFlag = null, elementFlag = null, rulerFlag = null;
        // 当pageType=1表示此次变更只对一种材料的变更，pageType=2代表着多种材料的变更
        String pageType = "1";
        request.setAttribute("pageType", pageType);
        Map<String, Object> auditmap = list.get(0);
        String confcode = String.valueOf(auditmap.get("CONFIG_CODE"));
        if ("JCJBXX".equals(confcode)) {
            // 监察基本信息
            basicFlag = "1";
            request.setAttribute("basicFlag", basicFlag);
            // （变更业务）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
            List<Map<String, Object>> syses = busColumnBasicService.getListSysByBusCodeChange(buscode, applyId);
            request.setAttribute("sysList", syses);
            // (获取上一个版本操作申报号相关的该专项的基本信息涉及的相关业务系统 
            List<Map<String, Object>> lastSyses = busColumnBasicService.getListSysByBusCode(buscode, lastApplyId);
            request.setAttribute("sysLastList", lastSyses);
        } else if ("YWLCT".equals(confcode)) {
            // 业务流程图
            flowFlag = "1";
            request.setAttribute("flowFlag", flowFlag);
            // 从变更表中获取当前环节信息，并获得流程图相关信息
            List<TacheFlow> flist = getChangeFlowCompentsDataByApplyId(request, buscode, applyId);
            request.setAttribute("tacheInfoList", flist);
            // 从变更表中和操作申报号找到该对接专项的基本信息
            BusSpecialInfo bus = allMaterialsService.getChangeBusByBusCode(buscode, applyId);
            request.setAttribute("busInfo", bus);
            // 其他
            request.setAttribute("stateFlg", bus.getStatus());
            // 并把该流程图的上一个历史版本的第一个环节的流程图片获取出来   、
            // 表示流程图有做过变更,用来区分加载流程图的来源（变更表，还是历史表）
            request.setAttribute("isFlowChange", "true");
        } else if ("JCSJZD".equals(confcode)) {
            // 监察字段变更
            columnFlag = "1";
            request.setAttribute("columnFlag", columnFlag);
        } else if ("JCJDYYS".equals(confcode)) {
            // 监察节点与要素
            elementFlag = "1";
            request.setAttribute("elementFlag", elementFlag);
        } else if ("JCGZPZ".equals(confcode)) {
            // 监察规则变更
            rulerFlag = "1";
            request.setAttribute("rulerFlag", rulerFlag);
        }
        // 由于监察字段|要素|规则依赖于流程图，当单一变更时需要获取该次变更上一个申报号所对应的流程图
        if ("1".equals(columnFlag) || "1".equals(elementFlag) || "1".equals(rulerFlag)) {
            //getFlowInfobyApplyIdFromHistory(request, lastApplyId, buscode);
            getFlowInfobyApplyIdFromHistoryNew(request, lastApplyId, buscode);
            //说明包含流程图的部分
            request.setAttribute("flowFlag", "2");
            // 表示流程图有做过变更,用来区分加载流程图的来源（变更表，还是历史表）
            request.setAttribute("isFlowChange", "false");
        }
    }

    /**
     * 描述 从历史记录表中获取流程图等
     * 
     * @param request
     * @param applyId
     * @param buscode
     */
    private void getFlowInfobyApplyIdFromHistoryNew(HttpServletRequest request, String applyId, String buscode) {
        List<TacheFlow> flist = getFlowCompentsDataByApplyId(request, buscode, applyId);
        request.setAttribute("tacheInfoList", flist);
        // 初始获得该专项第一个环节的第一个监察节点的过程编码
        if (!flist.isEmpty()) {
            TacheFlow firstflow = flist.get(0);
            // 获得该专项第一个环节的第一个监察节点的过程编码
            Map<String, Object> firstprocces = allMaterialsService
                    .queryFirstProccess(firstflow.getTacheCode(), applyId);
            if (firstprocces != null) {
                request.setAttribute("defaultprocescode", String.valueOf(firstprocces.get("PROCESS_CODE")));
            }
        } else {
            request.setAttribute("defaultprocescode", buscode + ".1.1");
        }
        // 从历史纪录表中和操作申报号找到该对接专项的基本信息
        BusSpecialInfo bus = allMaterialsService.getBusByBusCode(buscode, applyId);
        request.setAttribute("busInfo", bus);
        // 其他
        request.setAttribute("stateFlg", bus.getStatus());
    }
    /**
     * 描述 从历史记录表中获取流程图等相关信息（变更）
     * 
     * @author Water Guo
     * @created 2015-9-24 下午4:10:36
     * @param request
     * @param applyId
     * @param buscode
     */
    private void getFlowInfobyApplyIdFromHistory(HttpServletRequest request, String applyId, String buscode) {
        List<TacheFlow> flist = getFlowCompentsDataByApplyId(request, buscode, applyId);
        request.setAttribute("tacheInfoList", flist);
        // 初始获得该专项第一个环节的第一个监察节点的过程编码
        if (!flist.isEmpty()) {
            TacheFlow firstflow = flist.get(0);
            // 获得该专项第一个环节的第一个监察节点的过程编码
            Map<String, Object> firstprocces = allMaterialsService.getFirstProccess(firstflow.getTacheCode(), applyId);
            if (firstprocces != null) {
                request.setAttribute("defaultprocescode", String.valueOf(firstprocces.get("PROCESS_CODE")));
            }
        } else {
            request.setAttribute("defaultprocescode", buscode + ".1.1");
        }
        // 从历史纪录表中和操作申报号找到该对接专项的基本信息
        BusSpecialInfo bus = allMaterialsService.getBusByBusCode(buscode, applyId);
        request.setAttribute("busInfo", bus);
        // 其他
        request.setAttribute("stateFlg", bus.getStatus());
    }
    
    /**
     * 描述 从变更表中获取流程图相关的信息(变更表中的环节信息)
     * @author Water Guo
     * @created 2015-10-11 下午4:30:49
     * @param request
     * @param applyId
     * @param buscode
     */
    private void getFlowInfobyApplyIdFromChange(HttpServletRequest request, String applyId, String buscode) {
        List<TacheFlow> flist = getChangeFlowCompentsDataByApplyId(request, buscode, applyId);
        request.setAttribute("tacheInfoList", flist);
        // 初始获得该专项第一个环节的第一个监察节点的过程编码
        if (!flist.isEmpty()) {
            TacheFlow firstflow = flist.get(0);
            // 获得该专项第一个环节的第一个监察节点的过程编码
            Map<String, Object> firstprocces = allMaterialsService
                .queryFirstChangeProccess(firstflow.getTacheCode(), applyId);
                //.getFirstChangeProccess(firstflow.getTacheCode(),applyId);
            if (firstprocces != null) {
                request.setAttribute("defaultprocescode", String.valueOf(firstprocces.get("PROCESS_CODE")));
            }
        } else {
            request.setAttribute("defaultprocescode", buscode + ".1.1");
        }
        // 从历史纪录表中和操作申报号找到该对接专项的基本信息
        BusSpecialInfo bus = allMaterialsService.getChangeBusByBusCode(buscode, applyId);
        request.setAttribute("busInfo", bus);
        // 其他
        request.setAttribute("stateFlg", bus.getStatus());
    }

    /**
     * 描述 (业务梳理)通过操作的申报号与专项编码获得某专项的流程图信息(获取流程图历史纪录)
     * 
     * @author Water Guo
     * @created 2015-8-31 下午5:06:19
     * @param request
     * @param buscode
     * @param applyId
     * @return
     */
    private List<TacheFlow> getFlowCompentsDataByApplyId(HttpServletRequest request, String buscode, String applyId) {
        // 根据申请编号和所属业务专项流程图和环节相关信息
        List<TacheFlow> flist = allMaterialsService.getFlowByApplyId(applyId, buscode);
        // 存储流程图相关信息
        Map<String, Object> map = new HashMap<String, Object>();
        for (TacheFlow flow : flist) {
            Map<String, Object> flowinfo = new HashMap<String, Object>();
            flowinfo.put("flowInfo", flow.getFlowInfo());
            flowinfo.put("tacheId", flow.getTacheId());
            flowinfo.put("tacheCode", flow.getTacheCode());
            flowinfo.put("busiCode", flow.getBusCode());
            map.put(flow.getTacheCode(), flowinfo);
        }
        // 流程图想信息
        request.setAttribute("flowdata", flist.get(0).getFlowInfo());
        request.setAttribute("result", map);
        return flist;
    }
    
    /**
     * 描述 (业务变更)通过操作的申报号与专项编码获得某专项的流程图信息(从变更表中获取当前的流程图信息)
     * 
     * @author Water Guo
     * @created 2015-8-31 下午5:06:19
     * @param request
     * @param buscode
     * @param applyId
     * @return
     */
    private List<TacheFlow> getChangeFlowCompentsDataByApplyId(HttpServletRequest request, String buscode, 
        String applyId) {
        // 根据申请编号和所属业务专项流程图和环节相关信息
        List<TacheFlow> flist = allMaterialsService.getChangeFlowByApplyId(applyId, buscode);
        // 存储流程图相关信息
        Map<String, Object> map = new HashMap<String, Object>();
        for (TacheFlow flow : flist) {
            Map<String, Object> flowinfo = new HashMap<String, Object>();
            flowinfo.put("flowInfo", flow.getFlowInfo());
            flowinfo.put("tacheId", flow.getTacheId());
            flowinfo.put("tacheCode", flow.getTacheCode());
            flowinfo.put("busiCode", flow.getBusCode());
            map.put(flow.getTacheCode(), flowinfo);
        }
        // 流程图想信息
        request.setAttribute("flowdata", flist.get(0).getFlowInfo());
        request.setAttribute("result", map);
        return flist;
    }
    
    /**
     * @描述 批注信息暂存
     * @author Water Guo
     * @created 2015-11-26 上午10:18:39
     * @param request
     * @return
     */
    @RequestMapping(params = "tempSave")
    @ResponseBody
    public AjaxJson tempSave(HttpServletRequest request) {
        //当前用户信息
        SysUser user = AppUtil.getLoginUser();
        AjaxJson j = new AjaxJson();
        String task_id = request.getParameter("task_id");
        String option = request.getParameter("option");
        Map<String,Object> map = allMaterialsService.getByJdbc("JBPM6_TASK", 
              new String[]{"TASK_ID"}, new String[]{task_id});
        if(map != null){
            map.put("HANDLE_OPINION", option);
            String recordId = flowTaskService.saveOrUpdate(map, "JBPM6_TASK", task_id);
            sysLogService.saveLog(user.getFullname()+"审核详情页面的批注意见，暂存成功！taskId:【"+recordId+"】", 
                    SysLogService.OPERATE_TYPE_EDIT);
        }
        j.setMsg("确认成功");
        return j;
    }
}
