/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
@Controller
@RequestMapping("/deptStaticsController")
public class EfficientStaticsController extends BaseController{
    
    /**
     * 
     */
    @Resource
    private StatisticsService statisticsService;
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "deptVertical")
    public ModelAndView deptVertical(HttpServletRequest request) {
        SysUser sysUser = AppUtil.getLoginUser();
        request.setAttribute("sysUser", sysUser);
        return new ModelAndView("statis/efficiency/deptVertical");
    }
    /**
     * 获取类别数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "deptVerticalData")
    public void deptVerticalData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(new PagingBean());
        //SqlFilter filter = new SqlFilter(request);
        //SysUser sysUser = AppUtil.getLoginUser();
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String deptId=request.getParameter("DEPART_ID");
        //String deptCode="";
        // if(StringUtils.isNotEmpty(deptId)){
        // Map<String,Object>
        // map=statisticsService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",new
        // String[]{ "DEPART_ID" },
        // new Object[] {deptId});
        // deptCode=(String) map.get("DEPART_CODE");
        // }else{
        // deptCode=sysUser.getDepCode();
        // }
        String begin=request.getParameter("BEGIN_DATE");
        String end=request.getParameter("END_DATE");
        List<Map<String, Object>> list = statisticsService.findDeptEffByFilter(filter,begin,end,deptId);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述 导出EXCEL
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(new PagingBean());
        String beginDate = request.getParameter("Q_T.STATIST_DATE_GE");
        String endDate = request.getParameter("Q_T.STATIST_DATE_LE");
        String deptId=request.getParameter("departId");
        String deptCode="";
        if(StringUtils.isNotEmpty(deptId)){
            Map<String,Object> map=statisticsService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", new String[] { "DEPART_ID" },
                    new Object[] {deptId});
            deptCode=(String) map.get("DEPART_CODE");
        }
        List<Map<String, Object>> dataList = statisticsService.findDeptEffByFilter(filter,beginDate,endDate,deptCode);
        String tplPath = AppUtil.getAppAbsolutePath()+"/webpage/statis/efficiency/deptverstatisc.xls";
        int startRow = 3;
        int startCol = 1;
        StringBuffer fileName  = new StringBuffer("部门纵向效率表");
        if(StringUtils.isNotEmpty(beginDate)){
            fileName.append(beginDate);
        }
        if(StringUtils.isNotEmpty(beginDate)||StringUtils.isNotEmpty(endDate)){
            fileName.append("至");
        }
        if(StringUtils.isNotEmpty(endDate)){
            fileName.append(endDate);
        }
        fileName.append(".xls");
        Set<String>  excludeFields= new HashSet<String>();
        excludeFields.add("DEP_CODE");
        ExcelUtil.exportXls(tplPath, dataList, fileName.toString(), excludeFields, response, startRow, startCol);
    }
    
    /**
     * 跳转到横向效率统计信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "deptHorizon")
    public ModelAndView deptHorizon(HttpServletRequest request) {
        return new ModelAndView("statis/efficiency/deptHorizon");
    }
    /**
     * 获取横向效率统计数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "deptHorizonData")
    public void deptHorizonData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(new PagingBean());
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String begin=request.getParameter("BEGIN_DATE");
        String end=request.getParameter("END_DATE");
        List<Map<String, Object>> list = statisticsService.findHorizonEffByFilter(filter,begin,end);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到横向效率统计信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "stageTotal")
    public ModelAndView stageTotal(HttpServletRequest request) {
        return new ModelAndView("statis/efficiency/stageTotal");
    }
    /**
     * 获取横向效率统计数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "stageTotalData")
    public void stageTotalData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String begin=request.getParameter("BEGIN_DATE");
        String end=request.getParameter("END_DATE");
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        List<Map<String, Object>> list = statisticsService.findStageTotalByFilter(filter, begin, end);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述 导出EXCEL
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportStageExcel")
    public void exportStageExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(new PagingBean());
        String beginDate = request.getParameter("Q_T.STATIST_DATE_GE");
        String endDate = request.getParameter("Q_T.STATIST_DATE_LE");
        
        List<Map<String, Object>> dataList = statisticsService.findStageTotalByFilter(filter,beginDate,endDate);
        String tplPath = AppUtil.getAppAbsolutePath()+"/webpage/statis/efficiency/stagetotalStatisc.xls";
        int startRow = 3;
        int startCol = 1;
        StringBuffer fileName  = new StringBuffer("阶段性总效率报表");
        if(StringUtils.isNotEmpty(beginDate)){
            fileName.append(beginDate);
        }
        if(StringUtils.isNotEmpty(beginDate)||StringUtils.isNotEmpty(endDate)){
            fileName.append("至");
        }
        if(StringUtils.isNotEmpty(endDate)){
            fileName.append(endDate);
        }
        fileName.append(".xls");
        Set<String>  excludeFields= new HashSet<String>();
        //excludeFields.add("DEP_CODE");
        ExcelUtil.exportXls(tplPath, dataList, fileName.toString(), excludeFields, response, startRow, startCol);
    }
}
