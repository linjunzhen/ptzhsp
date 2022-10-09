/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import net.evecom.platform.flowchange.service.BusEsuperChangeService;
import net.evecom.platform.flowchange.service.BusRuleChangeService;
import net.evecom.platform.flowchange.service.BusSpecialChangeService;
import net.evecom.platform.flowchart.model.BusSpecialInfo;
import net.evecom.platform.flowchart.model.TacheFlow;
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

/**
 * 描述 变更监察规则
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
@Controller
@RequestMapping("/busRuleChangeController")
public class BusRuleChangeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusRuleChangeController.class);
    /**
     * busRuleChangeService
     */
    @Resource
    private BusRuleChangeService busRuleChangeService;
    /**
     * busEsuperChangeService
     */
    @Resource
    private BusEsuperChangeService busEsuperChangeService;
    /**
     * busSpecialChangeService
     */
    @Resource
    private BusSpecialChangeService busSpecialChangeService;
    /**
     * 流程service
     */
    @Resource
    private TacheFlowService flowChartService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "BusRuleChangeView")
    public ModelAndView busRuleChangeView(HttpServletRequest request) {
        return new ModelAndView("flowchange/busRuleChange/BusRuleChangeView");
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
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        /*SysUser sysUser = AppUtil.getLoginUser();*/
//        String userBusCodes = AppUtil.getCurrentUserBusCodeString();
//        String sysUserResKey = sysUser.getResKeys();
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
////            String unitPath = sysUser.getUnitPath();
////            filter.addFilter("Q_E.PATH_RLIKE", unitPath);
//            filter.addFilter("Q_T.BUSSYS_UNITCODE_IN", userBusCodes);
//        }
        filter.addSorted("T.UPDATE_TIME", "desc");
//        filter.addFilter("Q_T.IS_SHOW_EQ", "1");
        // filter.addSorted("A.TREE_SN","asc");
        List<Map<String, Object>> list = busRuleChangeService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goChange")
    public ModelAndView goChange(HttpServletRequest request) {
        return new ModelAndView("flowchange/busRuleChange/BusRuleChangeGoInfo");
    }

    /**
     * 变更申请操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveToChange")
    @ResponseBody
    public AjaxJson saveToChange(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        // 变更申请操作
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述 业务系统修改页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            // Map<String,Object> busRule =
            // busRuleChangeService.getByJdbc("T_LCJC_BUS_RULECONFIG",
            // new String[]{"RULE_ID"},new Object[]{entityId});
            Map<String, Object> busRule = busRuleChangeService.findMapByRuleId(entityId);
            request.setAttribute("busRule", busRule);
            String ruleType = String.valueOf(busRule.get("ANALYSIS_TYPE"));
            if ("1".equals(ruleType)) {// 时限监察
                return new ModelAndView("flowchange/busRuleChange/BusRuleChangeTimeInfo");
            }
            return new ModelAndView("flowchange/busRuleChange/BusRuleChangeEditInfo");
        } else {// 新增
            return new ModelAndView("flowchange/busRuleChange/BusRuleChangeAddInfo");
        }
    }

    /**
     * 
     * 描述 业务系统新增页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "winInfo")
    public ModelAndView winInfo(HttpServletRequest request) {
        String processCode = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined")) {
            // Map<String,Object> busRule =
            // busRuleChangeService.getByJdbc("T_LCJC_BUS_RULECONFIG",
            // new String[]{"RULE_ID"},new Object[]{entityId});
            Map<String, Object> busRule = busEsuperChangeService.findMapByProcessCode(processCode);
            // busRuleChangeService.findMapByProcessCode(processCode);
            request.setAttribute("busRule", busRule);
        } else {
            Map<String, Object> busRule = new HashMap<String, Object>();
            request.setAttribute("busRule", busRule);
        }
        return new ModelAndView("flowchange/busRuleChange/BusRuleChangeWinInfo");
    }

    /**
     * 
     * 描述 业务系统修改页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "winEditInfo")
    public ModelAndView winEditInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            // Map<String,Object> busRule =
            // busRuleChangeService.getByJdbc("T_LCJC_BUS_RULECONFIG",
            // new String[]{"RULE_ID"},new Object[]{entityId});
            Map<String, Object> busRule = busRuleChangeService.findMapByRuleId(entityId);
            request.setAttribute("busRule", busRule);
        } else {// 新增
            Map<String, Object> busRule = new HashMap<String, Object>();
            request.setAttribute("busRule", busRule);
        }
        return new ModelAndView("flowchange/busRuleChange/BusRuleChangeWinInfo");
    }

    /**
     * 增加或者修改规则信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveEditRule")
    @ResponseBody
    public AjaxJson saveEditRule(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CHANGE_ID");
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        String ruleElement = String.valueOf(treeData.get("SUPER_ELEMENTS"));
        String ruleType = String.valueOf(treeData.get("ANALYSIS_TYPE"));
        String startProcess = String.valueOf(treeData.get("RuleTime_ProcessB"));// 开始过程编号
        String applyId = String.valueOf(treeData.get("APPLY_ID"));// 申报号
        String recordId = "";
        if (StringUtils.isEmpty(entityId)) {// 新增
            treeData.put("BUSSYS_UNITCODE", sysUser.getDepCode());
            treeData.put("CREATE_USER", sysUser.getDepName());
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            treeData.put("UPDATE_USER", sysUser.getDepName());
            treeData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            treeData.put("IS_SHOW", "1");
            if ("1".equals(ruleType)) {// 时限监察
                if (StringUtils.isNotEmpty(startProcess)) {
                    recordId = busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", entityId);
                    treeData.put("PROCESS_CODE", startProcess);
                    treeData.put("IS_SHOW", "0");
                    busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", entityId);// 开始过程编码
                } else {// 未进行规则修改
                    recordId = busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", entityId);
                }
            } else {// 其他类型监察
                recordId = busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG", entityId);
            }
            sysLogService.saveLog("菜单【监察规则变更】新增监察要素【" + ruleElement + "】的规则记录",
                    SysLogService.OPERATE_TYPE_ADD);
        } else {// 更新
            treeData.put("UPDATE_USER", sysUser.getDepName());
            treeData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            if ("1".equals(ruleType)) {// 时限监察
                if (StringUtils.isNotEmpty(startProcess)) {
                    // 删除原有的规则
                    busRuleChangeService.delStartProcessByRuleId(entityId, applyId);
                    // 更新结束过程编码
                    recordId = busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", entityId);
                    // 新增开始过程编码
                    treeData.put("PROCESS_CODE", startProcess);
                    treeData.put("IS_SHOW", "0");
                    busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", null);// 开始过程编码
                } else {// 未进行规则修改
                    recordId = busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", entityId);
                }
            } else {// 其他类型监察
                recordId = busRuleChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_RULECONFIG_CHANGE", entityId);
            }
            sysLogService.saveLog("菜单【监察规则变更】更新监察要素【" + ruleElement + "】的规则记录",
                    SysLogService.OPERATE_TYPE_EDIT);
        }
        Map<String, Object> changeRuleMap = busRuleChangeService.findMapByRuleId(entityId);
        String busCode = String.valueOf(changeRuleMap.get("BUS_CODE"));// 业务专项
        String applyIds = String.valueOf(changeRuleMap.get("APPLY_ID"));// 申报号
        if(busRuleChangeService.checkColumnByBusCode(busCode, applyId)){
            //更新提交审核表
            busSpecialChangeService.confirmChange(applyIds, busCode, "JCGZPZ");
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 跳转到监察规则配置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "toAddRule")
    public ModelAndView toAddRule(HttpServletRequest request, HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        //Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined") && !entityId.equals("0")) {
            BusSpecialInfo bus = flowChartService.getBusByBusCode(entityId);
            request.setAttribute("busInfo", bus);
            String defaultTache = entityId + ".1";
            TacheFlow flow = flowChartService.getFlowByTacheCode(defaultTache);
            request.setAttribute("flowInfo", flow);
            List<TacheFlow> flist = flowChartService.getFlowByBusiCode(bus.getBusCode());
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    Map<String, Object> flowinfo = new HashMap<String, Object>();
                    TacheFlow bean = flist.get(i);
                    flowinfo.put("flowInfo", bean.getFlowInfo());
                    flowinfo.put("tacheId", bean.getTacheId());
                    flowinfo.put("tacheCode", bean.getTacheCode());
                    flowinfo.put("busCode", bean.getBusCode());
                    // map.put("" + bean.getTacheCode(), flowinfo);
                }
                request.setAttribute("tacheInfoList", flist);
            }
        }
        return new ModelAndView("flowchange/busRuleChange/BusRuleChangeAddWin");
    }

    /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "seeInfo")
    public ModelAndView seeInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            // Map<String,Object> busRule =
            // busRuleChangeService.getByJdbc("T_LCJC_BUS_RULECONFIG",
            // new String[]{"RULE_ID"},new Object[]{entityId});
            Map<String, Object> busRule = busRuleChangeService.findMapByRuleId(entityId);
            request.setAttribute("busRule", busRule);
        }
        return new ModelAndView("flowchange/busRuleChange/BusRuleChangeSeeInfo");
    }

    /**
     * 
     * 删除信息
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "muitDel")
    @ResponseBody
    public AjaxJson muitDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] ruleids = selectColNames.split(",");
        for (String ruleId : ruleids) {
            Map<String, Object> busRule = busRuleChangeService.getByJdbc("T_LCJC_BUS_RULECONFIG_CHANGE",
                    new String[] { "CHANGE_ID" }, new Object[] { ruleId });
            String status = String.valueOf(busRule.get("STATUS"));
            int n_status = 0;
            if (StringUtils.isNotBlank(status)) {
                n_status = Integer.valueOf(status);
            }
            if (n_status == 2 || n_status == 3) {
                j.setSuccess(false);
                j.setMsg("抱歉,您选择的规则要素中要素【" + busRule.get("SUPER_ELEMENTS") + "】不能进行删除操作!");
                return j;
            }
        }
        busRuleChangeService.remove("T_LCJC_BUS_RULECONFIG_CHANGE", "CHANGE_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 
     * 加载 业务环节列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "loadTache")
    public void loadTache(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String busCode = request.getParameter("busCode");
        String applyId = request.getParameter("applyId");
        List<Map<String, Object>> list = busRuleChangeService.findTacheByBus(busCode,applyId);
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("TACHE_ID", "");
            map.put("TACHE_CODE", "");
            map.put("TACHE_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 加载 过程编码列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "loadProcess")
    public void loadProcess(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String tacheCode = request.getParameter("tacheCode");
        String applyId = request.getParameter("applyId");
        List<Map<String, Object>> list = busRuleChangeService.findProcessByTache(tacheCode,applyId);
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("PROCESS_ID", "");
            map.put("PROCESS_CODE", "");
            map.put("PROCESS_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
