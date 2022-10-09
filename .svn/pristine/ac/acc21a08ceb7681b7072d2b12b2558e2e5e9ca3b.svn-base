/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchart.service.BusColumnEsuperService;
import net.evecom.platform.flowchart.service.BusSpecialService;
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
 * 描述 监察字段
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
@Controller
@RequestMapping("/busColumnEsuperController")
public class BusColumnEsuperController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusColumnEsuperController.class);
    /**
     * busColumnService
     */
    @Resource
    private BusColumnEsuperService busColumnService;
    /**
     * busSpecialService
     */
    @Resource
    private BusSpecialService busSpecialService;
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
     * @param request
     * @return
     */
    @RequestMapping(params = "BusColumnView")
    public ModelAndView busColumnView(HttpServletRequest request) {
        return new ModelAndView("flowchart/busColumnEsuper/BusColumnView");
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
    public void datagrid(HttpServletRequest request,HttpServletResponse response) {
        //SysUser sysUser = AppUtil.getLoginUser();
        SqlFilter filter = new SqlFilter(request);
//        String userBusCodes = AppUtil.getCurrentUserBusCodeString();
//        String sysUserResKey = sysUser.getResKeys();
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
////            String unitPath = sysUser.getUnitPath();
////            filter.addFilter("Q_E.PATH_RLIKE", unitPath);
//            filter.addFilter("Q_A.UNIT_CODE_IN", userBusCodes);
//        }
        filter.addSorted("C.UPDATE_TIME","desc");
//        filter.addSorted("A.PROCESS_CODE","desc");
//        filter.addFilter("Q_A.IS_MONITORNODE_EQ", "1");
        List<Map<String, Object>> list = busColumnService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 业务系统页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){//编辑
            Map<String,Object>  busProcess = busColumnService.findMapByProcessID(entityId);
            request.setAttribute("busProcess", busProcess);
            return new ModelAndView("flowchart/busColumnEsuper/BusColumnEditInfo");
        }else{//新增
            return new ModelAndView("flowchart/busColumnEsuper/BusColumnAddInfo");
        }
    }
    /**
     * 
     * 描述 业务系统页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "seeInfo")
    public ModelAndView seeInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){//编辑
            Map<String,Object>  busProcess = busColumnService.findMapByProcessID(entityId);
            request.setAttribute("busProcess", busProcess);
        }
        return new ModelAndView("flowchart/busColumnEsuper/BusColumnSeeInfo");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "columnDatagrid")
    public void columnDatagrid(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.BUSSYS_TYPE_EQ", "2");
        filter.addSorted("T.BUSSYS_SN","asc");
        List<Map<String, Object>> list = busColumnService.findColumnBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 更新监察字段信息
     * @param request
     * @return
     */
    @RequestMapping(params = "saveEditColumn")
    @ResponseBody
    public AjaxJson saveEditColumn(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String jsonDatas = request.getParameter("jsonDatas");
        String processCode = request.getParameter("processCode");
        //保存监察数据字段
        busColumnService.saveEditColumn(processCode, jsonDatas);
        //更新提交审核表
        busColumnService.editToAudit(processCode, "JCSJZD");
        
        Map<String, Object> processMap = busColumnService.findMapByProcessCode(processCode);
        sysLogService.saveLog("菜单【监察数据字段】更新监察点【"+processMap.get("PROCESS_NAME")+"】,编码【"
            +processCode+"】的 监察字段记录",SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 核对监察字段状态
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "checkColumn")
    @ResponseBody
    public AjaxJson checkColumn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String msgString = "开始新增!";
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
//        String unitCode = String.valueOf(treeData.get("BUSSYS_UNITCODE"));
//        String platCode = String.valueOf(treeData.get("BUSSYS_PLATCODE"));
        String busCode = String.valueOf(treeData.get("BUS_CODE"));
        Map<String, Object> specialMap = busSpecialService.getBusByBuscode(busCode);
        if(specialMap != null){
            String s_status = String.valueOf(specialMap.get("STATUS"));
            if("2".equals(s_status)){
                j.setSuccess(false);
                msgString = "该业务专项正在审核，无法新增!";
            }else if("3".equals(s_status)){
                j.setSuccess(false);
                msgString = "该业务专项通过审核，无法新增!";
            }else{
                j.setSuccess(true);
                msgString = "开始新增!";
            }
        }else{
            j.setSuccess(false);
            msgString = "查无此业务专项!";
        }
        j.setMsg(msgString);
        return j;
    }
    /**
     * 跳转到监察节点配置页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "toAddColumn")
    public ModelAndView toAddColumn(HttpServletRequest request,HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        //Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")
                && !entityId.equals("0")) {
            BusSpecialInfo bus = flowChartService.getBusByBusCode(entityId);
            request.setAttribute("busInfo", bus);
            String defaultTache = entityId+".1";
            TacheFlow flow = flowChartService.getFlowByTacheCode(defaultTache);
            request.setAttribute("flowInfo", flow);
            List<TacheFlow> flist =flowChartService.getFlowByBusiCode(bus.getBusCode());
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    Map<String, Object> flowinfo = new HashMap<String, Object>();
                    TacheFlow bean = flist.get(i);
                    flowinfo.put("flowInfo", bean.getFlowInfo());
                    flowinfo.put("tacheId", bean.getTacheId());
                    flowinfo.put("tacheCode", bean.getTacheCode());
                    flowinfo.put("busCode", bean.getBusCode());
                    //map.put("" + bean.getTacheCode(), flowinfo);
                }
                request.setAttribute("tacheInfoList", flist);
            }
        }
        return new ModelAndView("flowchart/busColumnEsuper/BusColumnAddWin");
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
        busColumnService.removeColumnByProcess(selectColNames);
//        busEsuperChangeService.remove("T_LCJC_BUS_COLUMN", "SERIAL_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 
     * 加载数据字典URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request,
            HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String processCode = request.getParameter("processCode"); 
        /*SysUser sysUser = AppUtil.getLoginUser();
        String sysUserResKey = sysUser.getResKeys();
        String unitCode = null;
        if(!sysUserResKey.equals(SysUser.ADMIN_RESKEY)){
            unitCode = sysUser.getUnitCode();
        }*/
        List<Map<String,Object>> list = busColumnService.findListByProcessCode(processCode);
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("SERIAL_ID","");
            map.put("COLUMN_CODE","");
            map.put("COLUMN_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
