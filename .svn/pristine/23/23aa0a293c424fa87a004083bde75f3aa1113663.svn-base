/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import net.evecom.platform.flowchart.service.BusColumnBasicService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述：基本信息字段配置
 * 
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-5
 */
@Controller
@RequestMapping("/busColumnBasicController")
public class BusColumnBasicController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusColumnBasicController.class);

    /**
     * @Resource busColumnBasicService
     */
    @Resource
    private BusColumnBasicService busColumnBasicService;

    /**
     * @Resource sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("flowchart/buscolumn/busColumnView");
    }
    
    
    /**
     * 跳转到信息变更页面
     * @param request
     * @return
     */
    @RequestMapping(params = "changeView")
    public ModelAndView changeView(HttpServletRequest request) {
        return new ModelAndView("flowchart/buscolumn/changeView");
    }

    /**
     * 跳转到表单
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> basicColumn = new HashMap<String, Object>();
            String[] strs = entityId.split("[.]");
            if(strs.length > 2){
                basicColumn.put("BUSSYS_UNITCODE", strs[0]);
                basicColumn.put("BUSSYS_PLATCODE", strs[1]);
                basicColumn.put("PROCESS_CODE", strs[2]);
                Map<String,Object> unit = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSUNIT", new String[]{"UNIT_CODE"}, new String[]{strs[0]});
                if(unit != null){
                    basicColumn.put("BUSSYS_UNITNAME", String.valueOf(unit.get("UNIT_NAME")));
                }
                Map<String,Object> sys = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSSYS", new String[]{"SYSTEM_CODE"}, new String[]{strs[1]});
                if(sys != null){
                    basicColumn.put("BUSSYS_PLATNAME", String.valueOf(sys.get("SYSTEM_NAME")));
                }
                Map<String,Object> special = this.busColumnBasicService.
                        getByJdbc("T_LCJC_BUS_SPECIAL", new String[]{"BUS_CODE"}, new String[]{strs[2]});
                if(special != null){
                    basicColumn.put("PROCESS_NAME", String.valueOf(special.get("BUS_NAME")));
                }
            }
            //编辑状态
            basicColumn.put("editType", "edit");
            request.setAttribute("basicColumn", basicColumn);
        } else {
            SysUser curLoginUser = AppUtil.getLoginUser();
            Map<String, Object> basicColumn = new HashMap<String, Object>();
            basicColumn.put("BUSSYS_UNITCODE", curLoginUser.getDepCode());
            basicColumn.put("IS_SHOW", "1");
            basicColumn.put("FIELD_TYPE", "0");
            basicColumn.put("BUSSYS_TYPE", "1");
            request.setAttribute("basicColumn", basicColumn);
        }
        return new ModelAndView("flowchart/buscolumn/busColumnInfo");
    }
    
    /**
     * 跳转发起变更页面
     * @param request
     * @return
     */
    @RequestMapping(params = "changeInfo")
    public ModelAndView changeInfo(HttpServletRequest request) {
        String applyId = request.getParameter("applyId");
        request.setAttribute("applyId", applyId);
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> basicColumn = new HashMap<String, Object>();
            String[] strs = entityId.split("[.]");
            if(strs.length > 2){
                basicColumn.put("BUSSYS_UNITCODE", strs[0]);
                basicColumn.put("BUSSYS_PLATCODE", strs[1]);
                basicColumn.put("PROCESS_CODE", strs[2]);
                Map<String,Object> unit = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSUNIT", new String[]{"UNIT_CODE"}, new String[]{strs[0]});
                if(unit != null){
                    basicColumn.put("BUSSYS_UNITNAME", String.valueOf(unit.get("UNIT_NAME")));
                }
                Map<String,Object> sys = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSSYS", new String[]{"SYSTEM_CODE"}, new String[]{strs[1]});
                if(sys != null){
                    basicColumn.put("BUSSYS_PLATNAME", String.valueOf(sys.get("SYSTEM_NAME")));
                }
                Map<String,Object> special = this.busColumnBasicService.
                        getByJdbc("T_LCJC_BUS_SPECIAL", new String[]{"BUS_CODE"}, new String[]{strs[2]});
                if(special != null){
                    basicColumn.put("PROCESS_NAME", String.valueOf(special.get("BUS_NAME")));
                }
            }
            //编辑状态
            request.setAttribute("basicColumn", basicColumn);
        } 
        return new ModelAndView("flowchart/buscolumn/busColumnChangeInfo");
    }
    
    /**
     * 描述 (业务变更) 查看变更表单
     * @author Water Guo
     * @created 2015-10-16 上午10:59:19
     * @param request
     * @return
     */
    @RequestMapping(params = "seechangeInfo")
    public ModelAndView seechangeInfo(HttpServletRequest request) {
        String applyId = request.getParameter("applyId");
        request.setAttribute("applyId", applyId);
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> basicColumn = new HashMap<String, Object>();
            String[] strs = entityId.split("[.]");
            if(strs.length > 2){
                basicColumn.put("BUSSYS_UNITCODE", strs[0]);
                basicColumn.put("BUSSYS_PLATCODE", strs[1]);
                basicColumn.put("PROCESS_CODE", strs[2]);
                Map<String,Object> unit = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSUNIT", new String[]{"UNIT_CODE"}, new String[]{strs[0]});
                if(unit != null){
                    basicColumn.put("BUSSYS_UNITNAME", String.valueOf(unit.get("UNIT_NAME")));
                }
                Map<String,Object> sys = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSSYS", new String[]{"SYSTEM_CODE"}, new String[]{strs[1]});
                if(sys != null){
                    basicColumn.put("BUSSYS_PLATNAME", String.valueOf(sys.get("SYSTEM_NAME")));
                }
                Map<String,Object> special = this.busColumnBasicService.
                        getByJdbc("T_LCJC_BUS_SPECIAL", new String[]{"BUS_CODE"}, new String[]{strs[2]});
                if(special != null){
                    basicColumn.put("PROCESS_NAME", String.valueOf(special.get("BUS_NAME")));
                }
            }
            //编辑状态
            request.setAttribute("basicColumn", basicColumn);
        } 
        return new ModelAndView("flowchart/buscolumn/busColumnSeeChangeInfo");
    }
    
    /**
     * 描述业务梳理查看详细表单
     * @author Water Guo
     * @created 2015-11-19 下午12:53:33
     * @param request
     * @return
     */
    @RequestMapping(params = "seeInfo")
    public ModelAndView seeInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> basicColumn = new HashMap<String, Object>();
            String[] strs = entityId.split("[.]");
            if(strs.length > 2){
                basicColumn.put("BUSSYS_UNITCODE", strs[0]);
                basicColumn.put("BUSSYS_PLATCODE", strs[1]);
                basicColumn.put("PROCESS_CODE", strs[2]);
                Map<String,Object> unit = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSUNIT", new String[]{"UNIT_CODE"}, new String[]{strs[0]});
                if(unit != null){
                    basicColumn.put("BUSSYS_UNITNAME", String.valueOf(unit.get("UNIT_NAME")));
                }
                Map<String,Object> sys = this.busColumnBasicService.
                        getByJdbc("T_LCJC_SYSTEM_BUSSYS", new String[]{"SYSTEM_CODE"}, new String[]{strs[1]});
                if(sys != null){
                    basicColumn.put("BUSSYS_PLATNAME", String.valueOf(sys.get("SYSTEM_NAME")));
                }
                Map<String,Object> special = this.busColumnBasicService.
                        getByJdbc("T_LCJC_BUS_SPECIAL", new String[]{"BUS_CODE"}, new String[]{strs[2]});
                if(special != null){
                    basicColumn.put("PROCESS_NAME", String.valueOf(special.get("BUS_NAME")));
                }
            }
            //编辑状态
            request.setAttribute("basicColumn", basicColumn);
        } 
        return new ModelAndView("flowchart/buscolumn/busColumnSeeInfo");
    }
    
    

    /**
     * (业务梳理)修改或者修改操作
     * @author Water Guo
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        busColumnBasicService.saveOrUpdate(variables);
        j.setMsg("保存成功");
        return j;
    }
    
    /***
     * 描述 变更的保存
     * @author Water Guo
     * @created 2015-9-22 上午11:04:31
     * @param request
     * @return
     */
    @RequestMapping(params = "changeSaveOrUpdate")
    @ResponseBody
    public AjaxJson changeSaveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        busColumnBasicService.changeSaveOrUpdate(variables);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述：(业务梳理)基本信息字段管理列表
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //filter.addSorted("TT.STATUS", "ASC");
        filter.addSorted("S.UPDATE_TIME", "DESC");
        filter.addSorted("S.CREATE_TIME", "DESC");
        filter.addSorted("S.BUS_CODE", "ASC");
        filter.addSorted("TT.ID", "ASC");
        List<Map<String, Object>> list = busColumnBasicService.datagrid(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 （变更）获取基本信息字段变更的列表
     * @author Water Guo
     * @created 2015-9-18 下午4:49:34
     * @param request
     * @param response
     */
    @RequestMapping(params = "changeDatagrid")
    public void changeDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T1.UPDATE_TIME", "DESC");
        filter.addSorted("T1.CREATE_TIME", "DESC");
        filter.addSorted("T1.BUS_CODE", "ASC");
        List<Map<String, Object>> list = busColumnBasicService.columnAlterDatagrid(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /***
     * 描述：(业务梳理)可编辑表格数据的加载
     * @author Water Guo
     * @created 2015-8-18 上午11:00:07
     * @param request
     * @param response
     */
    @RequestMapping(params = "columndatagrid")
    public void columndatagrid(HttpServletRequest request, HttpServletResponse response) {
        String processCode = request.getParameter("processCode");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined")) {
            //根据专项编码查询字段
            list = busColumnBasicService.listByProcessCode(processCode);
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述(字段变革)可编辑表格数据的加载
     * @author Water Guo
     * @created 2015-9-22 上午10:36:49
     * @param request
     * @param response
     */
    @RequestMapping(params = "columnchangedatagrid")
    public void columnchangedatagrid(HttpServletRequest request, HttpServletResponse response) {
        String processCode = request.getParameter("processCode");
        String platCode = request.getParameter("platCode");//系统编码
        String applyId = request.getParameter("applyId");;
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined")
                && StringUtils.isNotEmpty(platCode) && !platCode.equals("undefined")
                && StringUtils.isNotEmpty(applyId) && !applyId.equals("undefined")) {
            //根据专项编码和系统平台编码查询字段
            list = busColumnBasicService.listChangeColumsByBusAndPlatCode(processCode,platCode,applyId);
        }else{
            log.error("专项编码和系统平台编码|applyId为空，参数未传递！！");
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 批量删除
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        //自定义删除功能
        this.busColumnBasicService.multDel(selectColNames);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 描述 根据操作申报号和过程编号（基本信息的专项编号加载字段数据）
     * @author Water Guo
     * @created 2015-9-9 下午2:32:50
     * @param request
     * @param response
     */
    @RequestMapping(params = "materialsdatagrid")
    public void materialsdatagrid(HttpServletRequest request, HttpServletResponse response) {
        String appliyId = request.getParameter("appliyId");
        String processCode = request.getParameter("processCode");    
        String type = request.getParameter("columnType");//默认基本信息字段
        if(StringUtils.isEmpty(type) || type.equals("undefined")){
            type="1";//默认为1，表示基本信息，2表示监察字段
        }
        //当type=1时，platCode不能为空
        String platCode = request.getParameter("platCode");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined")
                && StringUtils.isNotEmpty(appliyId) && !appliyId.equals("undefined")) {
            //根据专项编码查询字段
            if("1".equals(type)&&StringUtils.isNotEmpty(platCode)){
                list = busColumnBasicService.listMaterialsColumn(appliyId,processCode,type,platCode);
            }else{
                list = busColumnBasicService.listMaterialsColumn(appliyId,processCode,type,null);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 (业务变更时，从变更表中加载基本信息字段)
     * @author Water Guo
     * @created 2015-10-10 下午10:45:36
     * @param request
     * @param response
     */
    @RequestMapping(params = "materialsChangedatagrid")
    public void materialsChangedatagrid(HttpServletRequest request, HttpServletResponse response) {
        String appliyId = request.getParameter("appliyId");
        String processCode = request.getParameter("processCode");    
        String type = request.getParameter("columnType");//默认基本信息字段
        if(StringUtils.isEmpty(type) || type.equals("undefined")){
            type="1";//默认为1，表示基本信息，2表示监察字段
        }
        //当type=1时，platCode不能为空
        String platCode = request.getParameter("platCode");
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(processCode) && !processCode.equals("undefined")
                && StringUtils.isNotEmpty(appliyId) && !appliyId.equals("undefined")) {
            //根据专项编码查询字段
            if("1".equals(type)&&StringUtils.isNotEmpty(platCode)){
                list = busColumnBasicService.listMaterialsChangeColumn(appliyId,processCode,type,platCode);
            }else{
                list = busColumnBasicService.listMaterialsChangeColumn(appliyId,processCode,type,null);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /***
     * 描述基本信息确认提交业务梳理
     * @author Water Guo
     * @created 2015-9-16 下午3:45:59
     * @param request
     * @return
     */
    @RequestMapping(params = "submitColumn")
    @ResponseBody
    public AjaxJson submitColumn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        //确认提交基本信息业务梳理材料
        busColumnBasicService.submitColumn(id);
        j.setMsg("确认成功");
        return j;
    }
    
    /**
     * 描述 检查当前的专项的基本信息是否已存在
     * @author Water Guo
     * @created 2015-10-16 上午9:44:06
     * @param request
     * @return
     */
    @RequestMapping(params = "check")
    @ResponseBody
    public AjaxJson check(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String busscode = request.getParameter("busscode");
        String platcode = request.getParameter("platcode");
        if(StringUtils.isNotEmpty(busscode) && !busscode.equals("undefined")
                && StringUtils.isNotEmpty(platcode) && !platcode.equals("undefined")){
            Boolean flag = busColumnBasicService.check(busscode,platcode);
            if(flag){
                j.setSuccess(true);
                j.setMsg("该专项已存在基本信息设置！");
            }else{
                j.setSuccess(false);
            }
        }else{
            j.setSuccess(true);
            j.setMsg("参数不完整！！");
        }
        return j;
    }
    
}
