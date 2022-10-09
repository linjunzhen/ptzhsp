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
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.flowchart.service.BusSysService;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 业务系统
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 3:08:05 PM
 */
@Controller
@RequestMapping("/busSysController")
public class BusSysController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BusSysController.class);
    /**
     * busSysService
     */
    @Resource
    private BusSysService busSysService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 
     * 删除部门信息
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
        String[] sysIds = selectColNames.split(",");
        StringBuffer sysNames = new StringBuffer("");
        for(String sysId : sysIds){
            if(StringUtils.isNotEmpty(sysNames.toString())){
                sysNames.append(",");
            }
            Map<String, Object> busSys = busSysService.getByJdbc("T_LCJC_SYSTEM_BUSSYS", new String[] { 
                "SYSTEM_ID" },new Object[] { sysId });
            sysNames.append(busSys.get("UNIT_NAME"));
        }
        busSysService.remove("T_LCJC_SYSTEM_BUSSYS", "SYSTEM_ID", sysIds);
        sysLogService.saveLog("删除了业务系统["+selectColNames+"]的记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> busSys = busSysService.getByJdbc("T_LCJC_SYSTEM_BUSSYS", new String[] { "SYSTEM_ID" },
                    new Object[] { entityId });
            request.setAttribute("busSys", busSys);
        } else {
            String unitId = request.getParameter("parentId");
            Map<String, Object> busSys = new HashMap<String, Object>();
            busSys.put("UNIT_ID", unitId);
            request.setAttribute("busSys", busSys);
        }
        return new ModelAndView("flowchart/bussys/BussysInfo");
    }

    /**
     * 增加或者修改部门信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SYSTEM_ID");
        Map<String, Object> treeData = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            treeData.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        busSysService.saveOrUpdate(treeData, "T_LCJC_SYSTEM_BUSSYS", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了业务系统[" + treeData.get("UNIT_NAME")+ "]的记录", 
                SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了业务系统[" + treeData.get("UNIT_NAME") + "]的记录", 
                SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述 业务系统页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "BussysView")
    public ModelAndView bussysView(HttpServletRequest request) {
        return new ModelAndView("flowchart/bussys/BussysView");
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
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = busSysService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 描述 通过业务单位获取业务系统下拉
     * @author Toddle Chen
     * @created Sep 22, 2015 6:00:36 PM
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request,
            HttpServletResponse response) {
        String defaultEmpty = request.getParameter("defaultEmpty");
        String unitCode = request.getParameter("unitCode");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(unitCode)){
            list = busSysService.findSysByUnitCode(unitCode);
        }
        if(StringUtils.isNotEmpty(defaultEmpty)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("SYSTEM_ID", "");
            map.put("SYSTEM_CODE", "");
            map.put("SYSTEM_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /***
     * 下拉获取业务系统数据源
     * 
     * @param request
     * @param response
     * @author Water Guo
     * @date 2015-08-07 11:28 AM
     */
    @RequestMapping(params = "comboxSystems")
    public void comboxSystems(HttpServletRequest request, HttpServletResponse response) {
        SysUser curLoginUser = AppUtil.getLoginUser();
//        Set<String> curUserCitySet = curLoginUser.getCitySet();
        List<Map<String, Object>> list;
        String uintId = request.getParameter("uintId");
        if(StringUtils.isNotEmpty(uintId) && !uintId.equals("undefined")){
            list = busSysService.findSysByUnitId(uintId);
        }else{
            list = busSysService.findSysByUnitId(curLoginUser.getDepId());
//            if (curLoginUser.getResKeys().equals(SysUser.ADMIN_RESKEY) 
//                    || curUserCitySet.size() == 0) {
//                // 超管权限查到所有的业务系统数据
//                list = busSysService.findAllSystems();
//            } else {
//                list = busSysService.findSysByUnitId(curLoginUser.getDepId());
//            }
        }
        if(list.isEmpty()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("SYSTEM_ID", "");
            map.put("SYSTEM_CODE", "");
            map.put("SYSTEM_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /***
     * 下拉获取业务系统数据源
     * 
     * @param request
     * @param response
     * @author Water Guo
     * @date 2015-08-07 11:28 AM
     */
    @RequestMapping(params = "comboxSystemsUnitCode")
    public void comboxSystemsUnitCode(HttpServletRequest request, HttpServletResponse response) {
        SysUser curLoginUser = AppUtil.getLoginUser();
//        Set<String> curUserCitySet = curLoginUser.getCitySet();
        List<Map<String, Object>> list;
        String unitcode = request.getParameter("unitcode");
        Map<String,Object> uinit  = busSysService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", 
                new String[]{"DEPART_CODE","STATUS"}, 
                new String[]{unitcode,"1"});
        String uintId = String.valueOf(uinit.get("DEPART_ID"));
        if(StringUtils.isNotEmpty(uintId) && !"null".equals(uintId)){
            list = busSysService.findSysByUnitId(uintId);
        }else{
            list = busSysService.findSysByUnitId(curLoginUser.getDepId());
//            if (curLoginUser.getResKeys().equals(SysUser.ADMIN_RESKEY) 
//                    || curUserCitySet.size() == 0) {
//                // 超管权限查到所有的业务系统数据
//                list = busSysService.findAllSystems();
//            } else {
//                list = busSysService.findSysByUnitId(curLoginUser.getUnitID());
//            }
        }
        if(list.isEmpty()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("SYSTEM_ID", "");
            map.put("SYSTEM_CODE", "");
            map.put("SYSTEM_NAME", "请选择");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

}
