/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchart.service.BusSpecialService;
import net.evecom.platform.flowchange.service.BusSpecialChangeService;
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
import com.alibaba.fastjson.TypeReference;

/**
 * 描述 业务专项
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
@Controller
@RequestMapping("/busSpecialChangeController")
public class BusSpecialChangeController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusSpecialChangeController.class);
    /**
     * sysLogService
     */
    @Resource
    private BusSpecialChangeService busSpecialChangeService;

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
    @RequestMapping(params = "busSpecialChangeView")
    public ModelAndView busSpecialView(HttpServletRequest request) {
        return new ModelAndView("flowchange/busSpecialChange/busSpecialChangeView");
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
        return new ModelAndView("flowchange/busSpecialChange/BusSpecialChangeGoInfo");
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
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.UPDATE_TIME", "desc");
        filter.addSorted("T.BUS_CODE", "desc");
//        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
//            String userBusCodes = AppUtil.getCurrentUserBusCodeString(); 
//            filter.addFilter("Q_A.UNIT_CODE_IN", userBusCodes);
//        }
        List<Map<String, Object>> list = busSpecialChangeService.findSpecialBySqlFilter(filter);
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
        filter.addSorted("T.TREE_SN", "asc");
        List<Map<String, Object>> list = busSpecialChangeService.findTacheBySqlFilter(filter);
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
            Map<String, Object> busSpecial = busSpecialChangeService.getByJdbc("T_LCJC_BUS_SPECIAL_CHANGE",
                    new String[] { "CHANGE_ID" }, new Object[] { entityId });
            request.setAttribute("busSpecial", busSpecial);
        }
        return new ModelAndView("flowchange/busSpecialChange/BusSpecialChangeInfo");
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
        String entityId = request.getParameter("CHANGE_ID");
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            treeData.put("CREATE_USER", sysUser.getDepName());
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            treeData.put("UPDATE_USER", sysUser.getDepName());
            treeData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = busSpecialChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_SPECIAL_CHANGE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 专项记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 专项记录", SysLogService.OPERATE_TYPE_ADD);
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
            List busSys = busSpecialChangeService.findTacheByBuscode(busCode);
            if (busSys.size() > 0) {
                j.setSuccess(false);
                j.setMsg("抱歉,该业务专项存在业务环节,请先转移业务环节再进行删除操作!");
                return j;
            }
        }
        busSpecialChangeService.remove("T_LCJC_BUS_SPECIAL_CHANGE", "BUS_ID", idStr);
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
            Map<String, Object> busTache = busSpecialChangeService.getByJdbc("T_LCJC_BUS_TACHE_CHANGE",
                    new String[] { "TACHE_ID" }, new Object[] { entityId });
            request.setAttribute("busTache", busTache);
        } else {
            String busCode = request.getParameter("busCode");
            Map<String, Object> busTache = new HashMap<String, Object>();
            busTache.put("BUS_CODE", busCode);
            request.setAttribute("busTache", busTache);
        }
        return new ModelAndView("flowchange/busSpecialChange/BusTacheChangeInfo");
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
        String applyId = request.getParameter("applyId");
        Map<String, Object> busSpecial = busSpecialChangeService.getByJdbc("T_LCJC_BUS_SPECIAL_CHANGE", new String[] {
            "BUS_CODE", "APPLY_ID" }, new Object[] { busCode, applyId });
        request.setAttribute("busCode", busSpecial.get("BUS_CODE"));
        request.setAttribute("busName", busSpecial.get("BUS_NAME"));
        request.setAttribute("applyId", busSpecial.get("APPLY_ID"));
        return new ModelAndView("flowchange/busSpecialChange/BusTacheChangeEdit");
    }

    /**
     * 批量保存业务环节
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveTacheChange")
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
        if (!"[]".equals(insertedJson)) {
            List<Map<String, Object>> insertedList = JSON.parseObject(insertedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            int tacheCode = busSpecialChangeService.getMaxTacheCode(busCode);
            String applyId = String.valueOf(formMap.get("APPLY_ID"));
            Map<String, Object> bus = busSpecialChangeService.getByJdbc("T_LCJC_BUS_SPECIAL_CHANGE", new String[] {
                "APPLY_ID", "BUS_CODE" }, new Object[] { applyId, busCode });
            for (Map<String, Object> insertMap : insertedList) {
                insertMap.put("APPLY_ID", applyId);
                insertMap.put("BUS_CODE", busCode);
                insertMap.put("CREATE_USER", sysUser.getDepName());
                insertMap.put("CREATE_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                insertMap.put("UPDATE_USER", sysUser.getFullname());
                insertMap.put("BUS_CODE", busCode);
                insertMap.put("UNIT_CODE", bus.get("UNIT_CODE"));
                insertMap.put("TACHE_ID", UUID.randomUUID().toString());
                insertMap.put("TACHE_CODE", busCode + "." + tacheCode);
                String recordId = busSpecialChangeService.saveOrUpdate(insertMap, "T_LCJC_BUS_TACHE_CHANGE", null);
                tacheCode++;
                sysLogService.saveLog("新增了ID为[" + recordId + "]的 专项环节变更记录", SysLogService.OPERATE_TYPE_ADD);
            }
        }
        // 修改
        if (!"[]".equals(updatedJson)) {
            List<Map<String, Object>> updatedList = JSON.parseObject(updatedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> updateMap : updatedList) {
                updateMap.put("UPDATE_DATE", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                updateMap.put("UPDATE_USER", sysUser.getFullname());
                String serialId = String.valueOf(updateMap.get("CHANGE_ID"));
                String recordId = busSpecialChangeService.saveOrUpdate(updateMap, "T_LCJC_BUS_TACHE_CHANGE", serialId);
                sysLogService.saveLog("更新了ID为[" + recordId + "]的专项环节变更记录", SysLogService.OPERATE_TYPE_EDIT);
            }
        }
        // 删除
        if (!"[]".equals(deletedJson)) {
            List<Map<String, Object>> deletedList = JSON.parseObject(deletedJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            for (Map<String, Object> deleteMap : deletedList) {
                String serialId = String.valueOf(deleteMap.get("CHANGE_ID"));
                busSpecialChangeService.remove("T_LCJC_BUS_TACHE_CHANGE", "CHANGE_ID", new String[] { serialId });
                busSpecialChangeService.removeByLike("T_LCJC_BUS_RULECONFIG_CHANGE", "PROCESS_CODE", new Object[] {
                        deleteMap.get("TACHE_CODE") + "%", deleteMap.get("APPLY_ID") });
                busSpecialChangeService.removeByLike("T_LCJC_BUS_PROCESS_CHANGE", "TACHE_CODE", new Object[] {
                        deleteMap.get("TACHE_CODE"), deleteMap.get("APPLY_ID") });
                busSpecialChangeService.removeByLike("T_LCJC_BUS_COLUMN_CHANGE", "PROCESS_CODE", new Object[] {
                        deleteMap.get("TACHE_CODE") + "%", deleteMap.get("APPLY_ID") });
                sysLogService.saveLog("删除了ID为[" + serialId + "]的专项环节变更记录", SysLogService.OPERATE_TYPE_DEL);
            }
        }

        j.setMsg("保存成功");
        return j;
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
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        String busCode = String.valueOf(treeData.get("BUS_CODE"));
        Map<String, Object> busObject = busSpecialChangeService.getBusByBuscode(busCode);
        String unitCode = String.valueOf(busObject.get("UNIT_CODE"));
        if (StringUtils.isEmpty(entityId)) {
            treeData.put("UNIT_CODE", unitCode);
            treeData.put("VERSION", busObject.get("VERSION"));// 版本号
            treeData.put("APPLY_ID", busObject.get("APPLY_ID"));// 申报号
            treeData.put("CREATE_USER", sysUser.getDepName());
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else {
            treeData.put("UPDATE_USER", sysUser.getDepName());
            treeData.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = busSpecialChangeService.saveOrUpdate(treeData, "T_LCJC_BUS_TACHE_CHANGE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 环节记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 环节记录", SysLogService.OPERATE_TYPE_ADD);
        }
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
        for (String changeId : selectColNames.split(",")) {
            Map<String, Object> tache = busSpecialChangeService.getByJdbc("T_LCJC_BUS_TACHE_CHANGE",
                    new String[] { "CHANGE_ID" }, new Object[] { changeId });
            busSpecialChangeService.removeByLike("T_LCJC_BUS_RULECONFIG_CHANGE", "PROCESS_CODE",
                    new Object[] { tache.get("TACHE_CODE") + "%", tache.get("APPLY_ID") });
            busSpecialChangeService.removeByLike("T_LCJC_BUS_PROCESS_CHANGE", "TACHE_CODE",
                    new Object[] { tache.get("TACHE_CODE"), tache.get("APPLY_ID") });
            busSpecialChangeService.removeByLike("T_LCJC_BUS_COLUMN_CHANGE", "PROCESS_CODE",
                    new Object[] { tache.get("TACHE_CODE") + "%", tache.get("APPLY_ID") });
            busSpecialChangeService.removeByLike("T_LCJC_BUS_TACHE_CHANGE", "TACHE_CODE",
                    new Object[] { tache.get("TACHE_CODE"), tache.get("APPLY_ID") });
        }
        // busSpecialChangeService.remove("T_LCJC_BUS_TACHE_CHANGE",
        // "CHANGE_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
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
        SysUser curLoginUser = AppUtil.getLoginUser();
//        Set<String> curUserCitySet = curLoginUser.getCitySet();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String unitcode = request.getParameter("unitcode");
        if (StringUtils.isNotEmpty(unitcode) && !unitcode.equals("undefined")) {
            // 级联查询单位所属的专项
            list = busSpecialChangeService.findSysByUnitCode(unitcode.trim());
        } else {
            if (curLoginUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
                // 超管权限查到所有的数据
                list = busSpecialChangeService.findAllByTableName("T_LCJC_BUS_SPECIAL_CHANGE");
            } else {
                list = busSpecialChangeService.findSysByUnitCode(curLoginUser.getDepCode());
            }
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
        SysUser sysUser = AppUtil.getLoginUser();
        String sysUserResKey = sysUser.getResKeys();
        String unitCode = null;
        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
            unitCode = sysUser.getDepCode();
        }
        List<Map<String, Object>> list = busSpecialChangeService.findByUnitCode(unitCode);
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("BUS_ID", "");
            map.put("BUS_CODE", "");
            map.put("BUS_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    @RequestMapping(params = "loadNotChanged")
    public void loadNotChanged(HttpServletRequest request, HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        /*SysUser sysUser = AppUtil.getLoginUser();
        String sysUserResKey = sysUser.getResKeys();
        String unitCode = null;
        if (!sysUserResKey.equals(SysUser.ADMIN_RESKEY)) {
            unitCode = sysUser.getDepCode();
        }*/
        String name = request.getParameter("q") == null ? "" : request.getParameter("q");
        List<Map<String, Object>> list = busSpecialChangeService.findNotChanged(name);
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("BUS_ID", "");
            map.put("BUS_CODE", "");
            map.put("BUS_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 变更申请操作
     * 
     * @author John Zhang
     * @created 2015-9-2 上午11:25:20
     * @param request
     * @return
     */
    @RequestMapping(params = "saveToChange")
    @ResponseBody
    public AjaxJson saveToChange(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        // 变更申请操作
        Map<String, Object> specialInfo = busSpecialChangeService.getByJdbc("T_LCJC_BUS_SPECIAL",
                new String[] { "BUS_CODE" }, new Object[] { treeData.get("BUS_CODE") });
        if (specialInfo == null) {
            j.setMsg("不存在该业务专项，变更申请失败");
            j.setSuccess(false);
            return j;
        }
        try {
            String busCode = specialInfo.get("BUS_CODE").toString();
            List<Map<String, Object>> list = busSpecialChangeService.findByBusCodeAndUserId(busCode);
            if (list.size() > 0) {
                j.setMsg("该业务专项正在变更中，不能重复发起变更");
                j.setSuccess(false);
                return j;
            }
            specialInfo.put("APPLY_NAME", treeData.get("APPLY_NAME"));
            busSpecialChangeService
                    .goChange(specialInfo, StringUtil.toString(treeData.get("BUSSYS_CHANGECODE"), false));
        } catch (Exception e) {
            log.error(e.getMessage());
            j.setMsg("变更申请异常，请联系管理员");
            j.setSuccess(false);
            return j;
        }

        j.setMsg("变更申请成功");
        j.setSuccess(true);
        return j;
    }
}
