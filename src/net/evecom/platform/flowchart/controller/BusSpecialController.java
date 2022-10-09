/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.evecom.platform.flowchart.service.BusRuleService;
import net.evecom.platform.flowchart.service.BusSpecialService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 描述 业务专项
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
@Controller
@RequestMapping("/busSpecialController")
public class BusSpecialController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusSpecialController.class);
    /**
     * sysLogService
     */
    @Resource
    private BusSpecialService busSpecialService;
    /**
     * busRuleService
     */
    @Resource
    private BusRuleService busRuleService;
    /**
     * busColumnBasicService
     */

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
    @RequestMapping(params = "BusSpecialView")
    public ModelAndView busSpecialView(HttpServletRequest request) {
        return new ModelAndView("flowchart/busSpecial/BusSpecialView");
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
        //SysUser sysUser = AppUtil.getLoginUser();
        //String sysUserResKey = sysUser.getResKeys();
        //String unitPath = "";
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.UPDATE_TIME", "desc");
        filter.addSorted("T.BUS_CODE", "desc");
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            unitPath = sysUser.getUnitPath();
//            filter.addFilter("Q_A.PATH_RLIKE", unitPath);
//        }
        List<Map<String, Object>> list = busSpecialService.findSpecialBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "tacheDatagrid")
    public void tacheDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.TREE_SN", "ASC");
        List<Map<String, Object>> list = busSpecialService.findTacheBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "processDatagrid")
    public void processDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.PROCESS_CODE", "ASC");
        List<Map<String, Object>> list = busSpecialService.findProcessBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "basicDatagrid")
    public void basicDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String busCode = request.getParameter("busCode");
        List<Map<String, Object>> list = busSpecialService.findBasicColumn(busCode, filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null,
                JsonUtil.EXCLUDE, response);
        
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "esuperDatagrid")
    public void esuperDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = busSpecialService.findEsuperColumn(request.getParameter("busCode")
                , filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "specialInfo")
    public ModelAndView specialInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> busSpecial = busSpecialService.getByJdbc("T_LCJC_BUS_SPECIAL",
                    new String[] { "BUS_CODE" }, new Object[] { entityId });
            request.setAttribute("busSpecial", busSpecial);
        }
        return new ModelAndView("flowchart/busSpecial/BusSpecialEdit");
    }

    /**
     * 
     * 跳转到添加专项页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "specialAdd")
    public ModelAndView specialAdd(HttpServletRequest request) {
        return new ModelAndView("flowchart/busSpecial/BusSpecialInfo");
    }

    /**
     * 增加或者修改专项信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveEditSpecial")
    @ResponseBody
    public AjaxJson saveEditSpecial(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("BUS_ID");
        //SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
//            treeData.put("CREATE_USER", sysUser.getUnitName());
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            treeData.put("STATUS", "1");
        } else {
//            treeData.put("UPDATE_USER", sysUser.getUnitName());
            treeData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            treeData.put("STATUS", "1");
        }
        String recordId = busSpecialService.saveOrUpdate(treeData, "T_LCJC_BUS_SPECIAL", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 专项记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 专项记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 批量增加专项信息
     * 
     * @author John Zhang
     * @created 2015-8-31 上午09:35:48
     * @param request
     * @return
     */
    @RequestMapping(params = "saveAddSpecial")
    @ResponseBody
    public AjaxJson saveAddSpecial(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String, Object> formMap = new HashMap<String, Object>();
        for (String str : formDatas.split("&")) {
            String[] strs = str.split("=");
            if (strs.length > 1) {
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String bussysUnitcode = String.valueOf(formMap.get("UNIT_CODE"));

        // 可编剧表格的信息
        String jsonDatas = String.valueOf(variables.get("jsonDatas"));
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);
        String insertedJson = String.valueOf(jsonMap.get("inserted"));
        String updatedJson = String.valueOf(jsonMap.get("updated"));
        String deletedJson = String.valueOf(jsonMap.get("deleted"));

        if (!"[]".equals(insertedJson)) {
            List<Map<String, Object>> insertedList = JSON.parseObject(insertedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            int maxCode = busSpecialService.getMaxBusCode(bussysUnitcode);// 当前业务装箱编码最大值+1
            DecimalFormat df = new DecimalFormat("000");
            for (Map<String, Object> insertMap : insertedList) {
                insertMap.put("CREATE_USER", sysUser.getUsername());
                insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_USER", sysUser.getUsername());
                insertMap.put("BUS_CODE", bussysUnitcode + df.format(maxCode));
                insertMap.put("UNIT_CODE", bussysUnitcode);
                String recordId = busSpecialService.saveOrUpdate(insertMap, "T_LCJC_BUS_SPECIAL", null);
                sysLogService.saveLog("新增了ID为[" + recordId + "]的 专项记录", SysLogService.OPERATE_TYPE_ADD);
                maxCode++;
            }
        }
        // 修改
        if (!"[]".equals(updatedJson)) {
            List<Map<String, Object>> updatedList = JSON.parseObject(updatedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> updateMap : updatedList) {
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", sysUser.getUsername());
                String serialId = String.valueOf(updateMap.get("BUS_ID"));
                String recordId = busSpecialService.saveOrUpdate(updateMap, "T_LCJC_BUS_SPECIAL", serialId);
                sysLogService.saveLog("更新了ID为[" + recordId + "]的专项记录", SysLogService.OPERATE_TYPE_EDIT);
            }
        }
        // 删除
        if (!"[]".equals(deletedJson)) {
            List<Map<String, Object>> deletedList = JSON.parseObject(deletedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("BUS_ID"));
                busSpecialService.remove("T_LCJC_BUS_SPECIAL", "BUS_ID", new String[] { serialId });
                sysLogService.saveLog("删除了ID为[" + serialId + "]的专项记录", SysLogService.OPERATE_TYPE_DEL);
            }
        }

        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 删除专项信息
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "specialMuitDel")
    @ResponseBody
    public AjaxJson specialMuitDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String[] idStr = selectColNames.split(",");
        for (String busCode : idStr) {
            /*
             * List busSys = busSpecialService.findTacheByBuscode(busCode); if
             * (busSys.size() > 0) { j.setSuccess(false);
             * j.setMsg("抱歉,该业务专项存在业务环节,请先转移业务环节再进行删除操作!"); return j; }
             */
            busSpecialService.remove("T_LCJC_BUS_SPECIAL", "BUS_CODE", new Object[] { busCode });
            busSpecialService.remove("T_LCJC_BUS_TACHE", "BUS_CODE", new Object[] { busCode });
            busSpecialService.remove("T_LCJC_BUS_AUDIT", "BUS_CODE", new Object[] { busCode });
            busSpecialService.removeByLike("T_LCJC_BUS_RULECONFIG", "PROCESS_CODE", new Object[] { busCode + "%" });
            busSpecialService.removeByLike("T_LCJC_BUS_PROCESS", "TACHE_CODE", new Object[] { busCode + "%" });
            busSpecialService.removeByLike("T_LCJC_BUS_COLUMN", "PROCESS_CODE", new Object[] { busCode + "%" });
        }
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tacheInfo")
    public ModelAndView tacheInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> busTache = busSpecialService.getByJdbc("T_LCJC_BUS_TACHE", new String[] { "TACHE_ID" },
                    new Object[] { entityId });
            request.setAttribute("busTache", busTache);
        } else {
            String busCode = request.getParameter("busCode");
            Map<String, Object> busTache = new HashMap<String, Object>();
            busTache.put("BUS_CODE", busCode);
            request.setAttribute("busTache", busTache);
        }
        return new ModelAndView("flowchart/busSpecial/BusTacheInfo");
    }

    /**
     * 跳转到业务环节编辑页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "toTacheEdit")
    public ModelAndView toTacheEdit(HttpServletRequest request) {
        String busCode = request.getParameter("busCode");
        Map<String, Object> busSpecial = busSpecialService.getBusByBuscode(busCode);
        request.setAttribute("busCode", busSpecial.get("BUS_CODE"));
        request.setAttribute("busName", busSpecial.get("BUS_NAME"));
        return new ModelAndView("flowchart/busSpecial/BusTacheEdit");
    }

    /**
     * 增加或者修改环节信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveEditTache")
    @ResponseBody
    public AjaxJson saveEditTache(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("TACHE_ID");
        //SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        String busCode = String.valueOf(treeData.get("BUS_CODE"));
        Map<String, Object> busObject = busSpecialService.getBusByBuscode(busCode);
        String unitCode = String.valueOf(busObject.get("UNIT_CODE"));
        if (StringUtils.isEmpty(entityId)) {
            treeData.put("UNIT_CODE", unitCode);
            //treeData.put("CREATE_USER", sysUser.getd);
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            //treeData.put("UPDATE_USER", sysUser.getUnitName());
            treeData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = busSpecialService.saveOrUpdate(treeData, "T_LCJC_BUS_TACHE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 环节记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 环节记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 批量保存业务环节
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveTache")
    @ResponseBody
    public AjaxJson saveTache(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String formDatas = String.valueOf(variables.get("formDatas"));
        Map<String, Object> formMap = new HashMap<String, Object>();
        for (String str : formDatas.split("&")) {
            String[] strs = str.split("=");
            if (strs.length > 1) {
                formMap.put(strs[0].trim(), strs[1].trim());
            }
        }
        String busCode = String.valueOf(formMap.get("BUS_CODE"));

        // 可编剧表格的信息
        String jsonDatas = String.valueOf(variables.get("jsonDatas"));
        Map<String, Object> jsonMap = (Map<String, Object>) JSON.parse(jsonDatas);
        String insertedJson = String.valueOf(jsonMap.get("inserted"));
        String updatedJson = String.valueOf(jsonMap.get("updated"));
        String deletedJson = String.valueOf(jsonMap.get("deleted"));
        Map<String, Object> busSpecial = busSpecialService.getBusByBuscode(busCode);
        if (!"[]".equals(insertedJson)) {
            List<Map<String, Object>> insertedList = JSON.parseObject(insertedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            int tacheCode = busSpecialService.getMaxTacheCode(busCode);
            for (Map<String, Object> insertMap : insertedList) {
                insertMap.put("CREATE_USER", sysUser.getDepCode());
                insertMap.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("BUS_CODE", busCode);
                insertMap.put("UNIT_CODE", busSpecial.get("UNIT_CODE"));
                insertMap.put("TACHE_CODE", busCode + "." + tacheCode);
                String recordId = busSpecialService.saveOrUpdate(insertMap, "T_LCJC_BUS_TACHE", null);
                tacheCode++;
                busSpecial.put("STATUS", "1");
                sysLogService.saveLog("新增了ID为[" + recordId + "]的 专项环节记录", SysLogService.OPERATE_TYPE_ADD);
            }
        }
        // 修改
        if (!"[]".equals(updatedJson)) {
            List<Map<String, Object>> updatedList = JSON.parseObject(updatedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> updateMap : updatedList) {
                updateMap.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", sysUser.getFullname());
                String serialId = String.valueOf(updateMap.get("TACHE_ID"));
                String recordId = busSpecialService.saveOrUpdate(updateMap, "T_LCJC_BUS_TACHE", serialId);
                busSpecial.put("STATUS", "1");
                sysLogService.saveLog("更新了ID为[" + recordId + "]的专项环节记录", SysLogService.OPERATE_TYPE_EDIT);
            }
        }
        // 删除
        if (!"[]".equals(deletedJson)) {
            List<Map<String, Object>> deletedList = JSON.parseObject(deletedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("TACHE_ID"));
                busSpecialService.remove("T_LCJC_BUS_TACHE", "TACHE_ID", new String[] { serialId });
                busSpecialService.removeByLike("T_LCJC_BUS_RULECONFIG", "PROCESS_CODE",
                        new Object[] { deleteMap.get("TACHE_CODE") + "%" });
                busSpecialService.remove("T_LCJC_BUS_PROCESS", "TACHE_CODE",
                        new Object[] { deleteMap.get("TACHE_CODE") });
                busSpecialService.removeByLike("T_LCJC_BUS_COLUMN", "PROCESS_CODE",
                        new Object[] { deleteMap.get("TACHE_CODE") + "%" });
                sysLogService.saveLog("删除了ID为[" + serialId + "]的专项环节记录", SysLogService.OPERATE_TYPE_DEL);
            }
        }
        busSpecialService.saveOrUpdate(busSpecial, "T_LCJC_BUS_SPECIAL", busSpecial.get("BUS_ID").toString());
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 删除环节信息
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "tacheMuitDel")
    @ResponseBody
    public AjaxJson tacheMuitDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        busSpecialService.remove("T_LCJC_BUS_TACHE", "TACHE_CODE", selectColNames.split(","));
        for (String tacheCode : selectColNames.split(",")) {
            busSpecialService.removeByLike("T_LCJC_BUS_RULECONFIG", "PROCESS_CODE", new Object[] { tacheCode + "%" });
            busSpecialService.remove("T_LCJC_BUS_PROCESS", "TACHE_CODE", new Object[] { tacheCode });
            busSpecialService.removeByLike("T_LCJC_BUS_COLUMN", "PROCESS_CODE", new Object[] { tacheCode + "%" });
        }

        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 导出专项
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "expSpecial")
    public ModelAndView expSpecial(HttpServletRequest request) {
        String busCode = request.getParameter("busCode");
        Map<String, Object> busSpecial = busSpecialService.getBusByBuscode(busCode);
        List<Map<String, Object>> busTacheList = busSpecialService.findTacheByBuscode(busCode);
        int n_size = 1;
        if (busTacheList.size() > 0) {
            n_size = busTacheList.size();
        }
        int tacheSize = n_size*30+30;
        request.setAttribute("busSpecial", busSpecial);
        request.setAttribute("busTacheList", busTacheList);
        request.setAttribute("tacheSize", tacheSize);
        int baseSize = busSpecialService.getByLike("T_LCJC_BUS_COLUMN", "PROCESS_CODE", 
                new Object[] { busCode }).size();
        request.setAttribute("basicSize",  baseSize > 10?350:(baseSize+1)*30);
        int processSize = busSpecialService.getByLike("T_LCJC_BUS_PROCESS", "PROCESS_CODE", 
                new Object[] { busCode + ".%" }).size();
        
        request.setAttribute("processSize", processSize > 10?350:(processSize+1)*30);
        int esuperSize = busSpecialService.getByLike("T_LCJC_BUS_COLUMN", "PROCESS_CODE",
                new Object[] { busCode + ".%" }).size();
        request.setAttribute("esuperSize", esuperSize > 10?350:(esuperSize+1)*30);
        int ruleConfigSize = busSpecialService.getByLike("T_LCJC_BUS_RULECONFIG", 
                "PROCESS_CODE", new Object[] { busCode + ".%" }).size();
        request.setAttribute("ruleConfigSize", ruleConfigSize > 10?350:(ruleConfigSize+1)*30);
        return new ModelAndView("flowchart/busSpecial/BusExpSpecial");
    }

    /**
     * 
     * 生成专项副本
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "copySpecial")
    @ResponseBody
    public AjaxJson copySpecial(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser sysUser = AppUtil.getLoginUser();
        String busCode = request.getParameter("Q_T.BUS_CODE_EQ");
        if (StringUtils.isNotEmpty(busCode)) {
            Map<String, Object> busSpecial = busSpecialService.getBusByBuscode(busCode);// 旧专项
            busSpecial.put("BUS_ID", null);
            String unitCode = busSpecial.get("UNIT_CODE").toString();
            DecimalFormat df = new DecimalFormat("000");
            busSpecial.put("BUS_CODE", unitCode + df.format(busSpecialService.getMaxBusCode(unitCode)));
            busSpecial.put("BUS_NAME", busSpecial.get("BUS_NAME") + "(副本)");
            busSpecial.put("STATUS", "1");
            busSpecial.put("VERSION", "1");
            busSpecial.put("CREATE_USER", sysUser.getUsername());
            busSpecial.put("UPDATE_USER", sysUser.getUsername());
            busSpecial.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            busSpecial.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            busSpecial.put("APPLY_ID", null);
            String recordId = busSpecialService.saveOrUpdate(busSpecial, "T_LCJC_BUS_SPECIAL", null);// 生成新专项
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 专项记录", SysLogService.OPERATE_TYPE_ADD);
            SqlFilter filter = new SqlFilter(request);
            filter.addSorted("T.TREE_SN", "asc");
            List<Map<String, Object>> tacheList = busSpecialService.findTacheBySqlFilter(filter);// 旧环节
            String newBusCode = busSpecial.get("BUS_CODE").toString();
            for (Map<String, Object> tache : tacheList) {
                tache.put("TACHE_ID", null);
                tache.put("STATUS", "0");
                tache.put("BUS_CODE", newBusCode);
                String oldTacheCode = tache.get("TACHE_CODE").toString();
                String newTacheCode = oldTacheCode.replace(busCode, newBusCode);
                tache.put("TACHE_CODE", newTacheCode);
                tache.put("CREATE_USER", sysUser.getUsername());
                tache.put("UPDATE_USER", sysUser.getUsername());
                tache.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                tache.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                tache.put("APPLY_ID", null);
                if (tache.get("FLOW_INFO") != null) {
                    tache.put("FLOW_INFO", tache.get("FLOW_INFO").toString().replaceAll(busCode, newBusCode));
                }
                String tacheId = busSpecialService.saveOrUpdate(tache, "T_LCJC_BUS_TACHE", null);// 新环节生成
                sysLogService.saveLog("新增了ID为[" + tacheId + "]的 专项环节记录", SysLogService.OPERATE_TYPE_ADD);
            }
            String busCodeLike = busCode + "%";
            List<Map<String, Object>> processes = busSpecialService.getByLike("T_LCJC_BUS_PROCESS", "PROCESS_CODE",
                    new Object[] { busCodeLike });

            for (Map<String, Object> pro : processes) {
                pro.put("PROCESS_ID", null);
                pro.put("APPLY_ID", null);
                pro.put("TACHE_CODE", pro.get("TACHE_CODE").toString().replace(busCode, newBusCode));
                pro.put("PROCESS_CODE", pro.get("PROCESS_CODE").toString().replace(busCode, newBusCode));
                pro.put("CREATE_USER", sysUser.getUsername());
                pro.put("UPDATE_USER", sysUser.getUsername());
                pro.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                pro.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                pro.put("STATUS", "0");
                busSpecialService.saveOrUpdate(pro, "T_LCJC_BUS_PROCESS", null);
            }

            List<Map<String, Object>> ruleConfigs = busSpecialService.getByLike("T_LCJC_BUS_RULECONFIG",
                    "PROCESS_CODE", new Object[] { busCodeLike });

            for (Map<String, Object> ruleConfig : ruleConfigs) {
                ruleConfig.put("RULE_ID", null);
                ruleConfig.put("APPLY_ID", null);
                ruleConfig.put("PROCESS_CODE", ruleConfig.get("PROCESS_CODE").toString().replace(busCode, newBusCode));
                ruleConfig.put("CREATE_USER", sysUser.getUsername());
                ruleConfig.put("UPDATE_USER", sysUser.getUsername());
                ruleConfig.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                ruleConfig.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                ruleConfig.put("STATUS", "0");
                busSpecialService.saveOrUpdate(ruleConfig, "T_LCJC_BUS_RULECONFIG", null);
            }

            List<Map<String, Object>> basicColumns = busSpecialService.getByLike("T_LCJC_BUS_COLUMN", "PROCESS_CODE",
                    new Object[] { busCodeLike });
            for (Map<String, Object> columns : basicColumns) {
                columns.put("SERIAL_ID", null);
                columns.put("APPLY_ID", null);
                columns.put("PROCESS_CODE", columns.get("PROCESS_CODE").toString().replace(busCode, newBusCode));
                columns.put("CREATE_USER", sysUser.getUsername());
                columns.put("UPDATE_USER", sysUser.getUsername());
                columns.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                columns.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                columns.put("STATUS", "0");
                busSpecialService.saveOrUpdate(columns, "T_LCJC_BUS_COLUMN", null);
            }

            j.setMsg("生成副本成功");
            j.setSuccess(true);
        } else {
            j.setMsg("请选择一个业务专项");
            j.setSuccess(false);
        }
        return j;
    }

    

    /**
     * 
     * 加载数据字典URL
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        //SysUser sysUser = AppUtil.getLoginUser();
        //String sysUserResKey = sysUser.getResKeys();
        String unitCode = null;
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            unitCode = sysUser.getUnitCode();
//        }
        List<Map<String, Object>> list = busSpecialService.findByUnitCode(unitCode);
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("BUS_ID", "");
            map.put("BUS_CODE", "");
            map.put("BUS_NAME", "请选择业务专项");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 专项导出Excel
     * 
     * @author John Zhang
     * @created 2015-11-11 下午02:25:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "expExcel")
    public void expExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String busCode = request.getParameter("Q_T.BUS_CODE_EQ");
        SqlFilter filter = new SqlFilter(request);

        Map<String, Object> busSpecial = busSpecialService.findSpecialBySqlFilter(filter).get(0);// 业务专项信息
        filter.addSorted("T.TREE_SN", "ASC");

        List<Map<String, Object>> basicColums = busSpecialService.findBasicColumn(busCode, null);// 业务基本信息
        for (Map<String, Object> map : basicColums) {// 处理字段类型
            if ("0".equals(map.get("FIELD_TYPE").toString())) {
                map.put("FIELD_TYPE", "默认");
            } else {
                map.put("FIELD_TYPE", "时间");
            }
        }
        List<Map<String, Object>> tacheList = busSpecialService.findTacheBySqlFilter(filter);
        Map<String, Object> queryParams = filter.getQueryParams();
        Map<String, String> orderParams = filter.getOrderParams();
        queryParams.remove("Q_T.BUS_CODE_EQ");
        queryParams.put("Q_B.BUS_CODE_EQ", busCode);
        filter.addSorted("A.TREE_SN", "ASC");
        List<Map<String, Object>> proceesses = busSpecialService.findProcessBySqlFilter(filter);// 环节过程信息
        for (Map<String, Object> map : proceesses) {// 处理字段类型
            if ("0".equals(map.get("IS_MONITORNODE").toString())) {
                map.put("IS_MONITORNODE", "否");
            } else {
                map.put("IS_MONITORNODE", "是");
            }
        }

        List<Map<String, Object>> esuperColumns = busSpecialService.findEsuperColumn(busCode, null);// 监察字段
        for (Map<String, Object> map : esuperColumns) {// 处理字段类型
            if ("0".equals(map.get("FIELD_TYPE").toString())) {
                map.put("FIELD_TYPE", "默认");
            } else {
                map.put("FIELD_TYPE", "时间");
            }
        }

        orderParams.clear();
        orderParams.put("B.TREE_SN", "asc");
        orderParams.put("A.TREE_SN", "asc");
        List<Map<String, Object>> ruleConfigs = busRuleService.findBySqlFilter(filter);
        for (Map<String, Object> map : ruleConfigs) {// 处理字段类型
            if ("0".equals(map.get("IS_ARTIFICIAL").toString())) {
                map.put("IS_ARTIFICIAL", "自动监察");
            } else {
                map.put("IS_ARTIFICIAL", "人工监察");
            }
            String analysisType = map.get("ANALYSIS_TYPE").toString();
            if ("1".equals(analysisType)) {
                map.put("ANALYSIS_TYPE", "时限监察");
            } else if ("2".equals(analysisType)) {
                map.put("ANALYSIS_TYPE", "内容监察");
            } else if ("3".equals(analysisType)) {
                map.put("ANALYSIS_TYPE", "流程监察");
            } else {
                map.put("ANALYSIS_TYPE", "收费监察");
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        createSheet(workbook, "业务基本信息", new String[] { "系统名称", "字段名称", "字段编码",
                "字段类型", "排序" }, new String[] { "SYSTEM_NAME", "COLUMN_NAME",
                "COLUMN_CODE", "FIELD_TYPE", "BUSSYS_SN" }, basicColums);

        createSheet(workbook, "业务环节信息", new String[] { "环节名称", "环节编码", "排序" },
                new String[] { "TACHE_NAME", "TACHE_CODE", "TREE_SN" },
                tacheList);

        createSheet(workbook, "环节过程信息", new String[] { "环节名称", "过程名称", "过程编码",
                "是否监察点" }, new String[] { "TACHE_NAME", "PROCESS_NAME",
                "PROCESS_CODE", "IS_MONITORNODE" }, proceesses);

        createSheet(workbook, "监察字段信息", new String[] { "环节名称", "过程名称", "过程编码",
                "字段名称", "字段编码", "字段类型", "排序" }, new String[] { "TACHE_NAME",
                "PROCESS_NAME", "PROCESS_CODE", "COLUMN_NAME", "COLUMN_CODE",
                "FIELD_TYPE", "BUSSYS_SN" }, esuperColumns);

        createSheet(workbook, "监察规则信息", new String[] { "环节名称", "监察点名称", "监察要素",
                "监察类型", "监察方式", "预警状态", "规则表达式", "表达式描述", "规则描述" },
                new String[] { "TACHE_NAME", "PROCESS_NAME", "SUPER_ELEMENTS",
                        "ANALYSIS_TYPE", "IS_ARTIFICIAL", "WARN_STATUS",
                        "JUDGE_CONDITIONS", "JUDGE_DESC", "RULE_DESC" },
                ruleConfigs);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader(
                "Content-disposition",
                "attachment;filename="
                        + URLEncoder.encode(busSpecial.get("BUS_NAME") + "（" + busSpecial.get("UNIT_NAME") + "）"
                                + busSpecial.get("BUS_CODE") + ".xls", "UTF-8"));
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    public void createSheet(HSSFWorkbook workbook, String sheetName, String[] titles, String[] values,
            List<Map<String, Object>> list) {
        HSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        HSSFCellStyle headerStyle1 = createHeaderStyle(workbook, 18);
        HSSFCellStyle headerStyle2 = createHeaderStyle(workbook, 14);
        HSSFCellStyle contentStyle = createContentStyle(workbook);
        int iRows = 0;
        // 创建标题行
        HSSFRow row = sheet.createRow(iRows);
        row.setHeightInPoints(Short.valueOf("25"));
        HSSFCell cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);

        for (int i = 0; i < titles.length; i++) {
            cell.setCellValue(sheetName);
            cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(headerStyle1);
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length - 1));

        iRows++;
        // 创建表头
        row = sheet.createRow(iRows);

        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i, HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headerStyle2);
            sheet.setColumnWidth(i, titles[i].getBytes().length * 512);
        }
        iRows++;
        // 数据封装
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            row = sheet.createRow(iRows);
            for (int j = 0; j < values.length; j++) {
                cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(map.get(values[j]) == null ? "" : String.valueOf(map.get(values[j])));
                cell.setCellStyle(contentStyle);
                sheet.setColumnWidth(j,
                        (values[j].getBytes().length > titles[j].getBytes().length) ? values[j].getBytes().length * 512
                                : titles[j].getBytes().length * 512);
            }
            iRows++;
        }

    }

    private HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook, int fontSize) {
        // 设置字体
        HSSFFont fontT = workbook.createFont();
        fontT.setFontHeightInPoints((short) fontSize); // 字体高度
        fontT.setColor(HSSFColor.BLACK.index); // 字体颜色
        fontT.setFontName("宋体"); // 字体
        fontT.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // 宽度

        // 设置单元格类型
        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(fontT);
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
        cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle1.setWrapText(true);
        return cellStyle1;
    }

    private HSSFCellStyle createContentStyle(HSSFWorkbook workbook) {
        // 设置内容样式
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        // cellStyle1.setFont(fontT);
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：靠左
        cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        cellStyle2.setWrapText(true);
        return cellStyle2;
    }
    
    /***
     * 下拉获取业务专项数据源
     * 
     * @param request
     * @param response
     * @author Water Guo
     * @date 2015-08-07 11:28 AM
     */
    @RequestMapping(params = "comboxSystems")
    public void cities(HttpServletRequest request, HttpServletResponse response) {
        //SysUser curLoginUser = AppUtil.getLoginUser();
//        Set<String> curUserCitySet = curLoginUser.getCitySet();
        List<Map<String, Object>> list;
        String unitcode = request.getParameter("unitcode");
        if (StringUtils.isNotEmpty(unitcode) && !unitcode.equals("undefined")) {
            // 级联查询单位所属的专项
            list = busSpecialService.findSysByUnitCode(unitcode.trim());
        } else {
            list = busSpecialService.findAllByTableName("T_LCJC_BUS_SPECIAL");
//            if (curLoginUser.getResKeys().equals(SysUser.ADMIN_RESKEY) || curUserCitySet.size() == 0) {
//                // 超管权限查到所有的数据
//                list = busSpecialService.findAllByTableName("T_LCJC_BUS_SPECIAL");
//            } else {
//                list = busSpecialService.findSysByUnitCode(curLoginUser.getUnitCode());
//            }
        }
        if (list.isEmpty()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("BUS_ID", "");
            map.put("BUS_CODE", "");
            map.put("BUS_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
