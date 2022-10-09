/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.controller;

import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.model.SysUser;

import net.evecom.platform.zzhy.service.StatistCommercialService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2016年3月8日 上午9:05:44
 */
@Controller
@RequestMapping("/statisticsController")
public class StatisticsController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(StatisticsController.class);
    /**
     * 
     */
    @Resource
    private StatisticsService statisticsService;
    /**
     * 
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 
     */
    @Resource
    private ExecutionService executionService;
    /**
     * statistCommercialService
     */
    @Autowired
    private StatistCommercialService statistCommercialService;

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bjtjbStatis")
    public ModelAndView bjtjbStatis(HttpServletRequest request) {
        return new ModelAndView("statis/bjtjbStatis");
    }
    /**
     * 
     * 描述：
     * @author Water Guo
     * @created 2017-10-19 上午10:49:12
     * @param request
     * @return
     */
    @RequestMapping(params = "xzbjtjbStatis")
    public ModelAndView xzbjtjbStatis(HttpServletRequest request) {
        return new ModelAndView("statis/xzbjtjbStatis");
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sxslylStatis")
    public ModelAndView sxslylStatis(HttpServletRequest request) {
        return new ModelAndView("statis/sxslylStatis");
    }
    /**
     * 获取类事项数量
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "sxslylData")
    public void sxlxylData(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .findsxslylDataBySqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 导出事项数量
     * @author Madison You
     * @created 2019年7月26日 上午10:40:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSxslylExcel")
    public void exportSxslylExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService.findsxslylDataBySqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/sxslylstatis.xls";
        int startRow = 4;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, list, "平潭综合实验区行政服务中心部门事项数量统计表.xls",
        excludeFields, response, startRow, startCol, datas,"",true,new int[]{1});
    }

    /**
     * 
     * 描述  跳转到即办结率统计表
     * @author Flex Hu
     * @created 2016年3月9日 上午10:53:18
     * @param request
     * @return
     */
    @RequestMapping(params = "jbjlStatis")
    public ModelAndView jbjlStatis(HttpServletRequest request) {
        return new ModelAndView("statis/jbjlStatis");
    }
    
    /**
     * 跳转到时限类型统计界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sxlxStatis")
    public ModelAndView sxlxStatis(HttpServletRequest request) {
        return new ModelAndView("statis/sxlxStatis");
    }
    
    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String beginDate = request.getParameter("Q_T.STATIST_DATE_>=");
        String endDate = request.getParameter("Q_T.STATIST_DATE_<=");
        try {
            statisticsService.saveOrUpdateStatis(beginDate, endDate);
            j.setMsg("同步成功");
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("同步失败");
            log.error(e.getMessage());
        }
        return j;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "dataRefresh")
    public ModelAndView dataRefresh(HttpServletRequest request) {
        return new ModelAndView("statis/dataRefresh");
    }

    /**
     * 获取类别数据URL
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bjtjbData")
    public void bjtjbData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_>=")!=null){
            String start = filter.getQueryParams().get("Q_T.STATIST_DATE_>=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_>=");
            filter.addFilter("Q_T.STATIST_DATE_>=", start);
        }
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_<=")!=null){
            String end = filter.getQueryParams().get("Q_T.STATIST_DATE_<=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_<=");
            filter.addFilter("Q_T.STATIST_DATE_<=", end);
        }
        List<Map<String, Object>> list = statisticsService
                .findBySqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }



    /**
     * 
     * 描述：乡镇办证统计
     * @author Water Guo
     * @created 2017-10-19 上午10:09:41
     * @param request
     * @param response
     */
    @RequestMapping(params = "xzbjtjbData")
    public void xzbjtjbData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_>=")!=null){
            String start = filter.getQueryParams().get("Q_T.STATIST_DATE_>=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_>=");
            filter.addFilter("Q_T.STATIST_DATE_>=", start);
        }
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_<=")!=null){
            String end = filter.getQueryParams().get("Q_T.STATIST_DATE_<=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_<=");
            filter.addFilter("Q_T.STATIST_DATE_<=", end);
        }
        List<Map<String, Object>> list = statisticsService
                .findXZBySqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }
    /**
     * 
     * 描述 获取即办结率统计数据
     * @author Flex Hu
     * @created 2016年3月9日 上午11:23:11
     * @param request
     * @param response
     */
    @RequestMapping(params = "jbjlData")
    public void jbjlData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_>=")!=null){
            String start = filter.getQueryParams().get("Q_T.STATIST_DATE_>=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_>=");
            filter.addFilter("Q_T.STATIST_DATE_>=", start);
        }
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_<=")!=null){
            String end = filter.getQueryParams().get("Q_T.STATIST_DATE_<=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_<=");
            filter.addFilter("Q_T.STATIST_DATE_<=", end);
        }
        List<Map<String, Object>> list = statisticsService.findJbjlList(filter);
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述 导出即办结率统计表
     * @author Rider Chen
     * @created 2016-3-8 上午09:27:30
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportJbjl")
    public void exportJbjl(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        StringBuffer fileName  = new StringBuffer("即办结率统计表");
        getFileNameAndFilterTime(request, filter, fileName);
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_>=")!=null){
            String start = filter.getQueryParams().get("Q_T.STATIST_DATE_>=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_>=");
            filter.addFilter("Q_T.STATIST_DATE_>=", start);
        }
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_<=")!=null){
            String end = filter.getQueryParams().get("Q_T.STATIST_DATE_<=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_<=");
            filter.addFilter("Q_T.STATIST_DATE_<=", end);
        }
        List<Map<String, Object>> dataList = statisticsService.findJbjlList(filter);
        String tplPath = AppUtil.getAppAbsolutePath()+"/webpage/statis/jbjlstatisc.xls";
        int startRow = 6;
        int startCol = 1;
        Set<String>  excludeFields= new HashSet<String>();

        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);

        ExcelUtil
                .exportXls(tplPath, dataList, fileName.toString(),
                        excludeFields, response, startRow, startCol, datas, "",
                        true, new int[]{1});
        //ExcelUtil.exportXls(tplPath, dataList, fileName.toString(), excludeFields, response, startRow, startCol);
    }
    
    /**
     * 
     * 描述 导出EXCEL
     * @author Rider Chen
     * @created 2016-3-8 上午09:27:30
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        StringBuffer fileName  = new StringBuffer("办件统计表");
        getFileNameAndFilterTime(request, filter, fileName);
        List<Map<String, Object>> dataList;
        String tplPath;
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_>=")!=null){
            String start = filter.getQueryParams().get("Q_T.STATIST_DATE_>=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_>=");
            filter.addFilter("Q_T.STATIST_DATE_>=", start);
        }
        if(filter.getQueryParams().get("Q_T.STATIST_DATE_<=")!=null){
            String end = filter.getQueryParams().get("Q_T.STATIST_DATE_<=").toString().substring(0, 10);
            filter.removeFilter("Q_T.STATIST_DATE_<=");
            filter.addFilter("Q_T.STATIST_DATE_<=", end);
        }
        String dataType=request.getParameter("dataType");
        if(StringUtils.isEmpty(dataType)){
            dataList = statisticsService.findBySqlFilter(filter);
            tplPath = AppUtil.getAppAbsolutePath()+"/webpage/statis/bjtjbstatisc.xls";
        }else{
            //乡镇统计
            dataList = statisticsService.findXZBySqlFilter(filter);
            tplPath = AppUtil.getAppAbsolutePath()+"/webpage/statis/xzbjtjbstatisc.xls";
        }
        int startRow = 7;
        int startCol = 1;
        Set<String>  excludeFields= new HashSet<String>();
        excludeFields.add("DEP_CODE");
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        
        ExcelUtil
                .exportXls(tplPath, dataList, fileName.toString(),
                        excludeFields, response, startRow, startCol, datas, "",
                        true, new int[]{1});
    }

    /**
     * 描述
     * @author Flex Hu
     * @created 2016年3月9日 下午3:14:48
     * @param request
     * @param filter
     * @param fileName
     */
    public void getFileNameAndFilterTime(HttpServletRequest request, SqlFilter filter, StringBuffer fileName) {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if(StringUtils.isNotEmpty(beginDate)){
            filter.addFilter("Q_T.STATIST_DATE_>=", beginDate+" 00:00:00");
            fileName.append(beginDate);
        }
        if(StringUtils.isNotEmpty(beginDate)||StringUtils.isNotEmpty(endDate)){
            fileName.append("至");
        }
        if(StringUtils.isNotEmpty(endDate)){
            filter.addFilter("Q_T.STATIST_DATE_<=", endDate+" 23:59:59");
            fileName.append(endDate);
        }
        fileName.append(".xls");
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "xzfwzxrybjpyStatis")
    public ModelAndView xzfwzxrybjpyStatis(HttpServletRequest request) {
        return new ModelAndView("statis/xzfwzxrybjpyStatis");
    }

    /**
     * 
     * 描述 办件评议统计列表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "xzfwzxrybjpyData")
    public void xzfwzxrybjpyData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .findByUserItemSqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 
     * 描述 导出办件评议统计报表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:03:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportBjpytjExcel")
    public void exportBjpytjExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .findByUserItemSqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/xzfwzxrybjpyStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心人员办件评议统计分析表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1,2});
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "spsxtjbStatis")
    public ModelAndView spsxtjbStatis(HttpServletRequest request) {
        return new ModelAndView("statis/spsxtjbStatis");
    }

    /**
     * 
     * 描述 审批事项统计表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "spsxtjbData")
    public void spsxtjbData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .findByEffiItemSqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 
     * 描述 导出审批事项统计表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:03:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSpsxtjbExcel")
    public void exportSpsxtjbExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .findByEffiItemSqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/spsxtjbStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String DEPT_NAME = request.getParameter("DEPTNAME");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        if(StringUtils.isNotEmpty(DEPT_NAME)){
            DEPT_NAME = "("+DEPT_NAME+")";
        }else{
            DEPT_NAME = "";
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        ExcelReplaceDataVO vo2 = new ExcelReplaceDataVO();
        vo2.setRow(0);
        vo2.setColumn(0);
        vo2.setKey("${dept_name}");
        vo2.setValue(DEPT_NAME);
        datas.add(vo1);
        datas.add(vo2);
        ExcelUtil.exportXls(tplPath, dataList, "审批事项统计表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sjdzxlStatis")
    public ModelAndView sjdzxlStatis(HttpServletRequest request) {
        return new ModelAndView("statis/sjdzxlStatis");
    }

    /**
     * 
     * 描述 时间段总效率报表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "sjdzxlData")
    public void sjdzxlData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .findByCountEffiItemSqlFilter(filter);
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("Q_T.END_TIME_>=");
        String endDate = request.getParameter("Q_T.END_TIME_<=");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        for (Map<String, Object> map : list) {
            if(StringUtils.isNotEmpty(time.toString())){
                map.put("TIMES", time);
            }else{
                map.put("TIMES", "所有");
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 
     * 描述 导出时间段总效率报表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:03:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSjdzxlExcel")
    public void exportSjdzxlExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .findByCountEffiItemSqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/sjdzxlStatis.xls";
        int startRow = 3;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        for (Map<String, Object> map : dataList) {
            if(StringUtils.isNotEmpty(time.toString())){
                map.put("TIMES", time);
            }else{
                map.put("TIMES", "所有");
            }
        }
        Set<String> excludeFields = new HashSet<String>();
        ExcelUtil.exportXls(tplPath, dataList, "时间段总效率报表.xls", excludeFields,
                response, startRow, startCol);
    }
    /**
     * 跳转到图表统计界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tjtbStatis")
    public ModelAndView tjtbStatis(HttpServletRequest request) {
        return new ModelAndView("statis/tjtbStatis");
    }
    
    /**
     * 跳转到办结效率统计图标
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bjxlStatis")
    public ModelAndView bjxlStatis(HttpServletRequest request) {
        String eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        request.setAttribute("eTime", eTime);
        request.setAttribute("sTime", sTime);
        return new ModelAndView("statis/bjxlStatis");
    }
    
    /**
     * 
     * 描述 获取办结效率统计数据
     * @author Flex Hu
     * @created 2016年3月10日 下午3:49:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "bjxlData")
    public void bjxlData(HttpServletRequest request,HttpServletResponse response) {
        String echartType = request.getParameter("echartType");
        Map<String,Object> result = new HashMap<String,Object>();
        String beginDate = request.getParameter("Q_T.CREATE_TIME_>=");
        String endDate = request.getParameter("Q_T.CREATE_TIME_<=");
        Map<String,Object> bjxlInfo = this.statisticsService.getBjxlStatist(beginDate, endDate);
        result.put("echartType", echartType);
        result.put("PJ_XL", bjxlInfo.get("PJ_XL"));
        //定义Y周的效率列表
        List<Double> DAY_XLLIST = new ArrayList<Double>();
        //定义饼图的数据格式
        List<Map<String,Object>> nameAndValueList = new ArrayList<Map<String,Object>>();
        //定义X轴时间的值列表
        List<String> TIME_LIST = new ArrayList<String>();
        List<Map<String,Object>> BJ_XLLIST = (List<Map<String, Object>>) bjxlInfo.get("BJ_XLLIST");
        for(Map<String,Object> bjxl:BJ_XLLIST){
            Map<String,Object> nameAndValue = new HashMap<String,Object>();
            DAY_XLLIST.add(Double.parseDouble(bjxl.get("BJ_XL").toString()));
            TIME_LIST.add(bjxl.get("WORK_DAY").toString());
            nameAndValue.put("name", bjxl.get("WORK_DAY").toString());
            nameAndValue.put("value", Double.parseDouble(bjxl.get("BJ_XL").toString()));
            nameAndValueList.add(nameAndValue);
        }
        result.put("DAY_XLLIST", DAY_XLLIST);
        result.put("TIME_LIST", TIME_LIST);
        result.put("nameAndValueList", nameAndValueList);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 获取时限类型统计数据
     * @author Flex Hu
     * @created 2016年3月10日 下午3:49:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "sxlxData")
    public void sxlxData(HttpServletRequest request,HttpServletResponse response) {
        String echartType = request.getParameter("echartType");
        Map<String,Object> result = new HashMap<String,Object>();
        SqlFilter filter = new SqlFilter(request);
        Map<String,Object> sxlxInfo = this.statisticsService.getSxlxStatist(filter);
        result.put("echartType", echartType);
        result.put("TOTAL_COUNT", sxlxInfo.get("TOTAL_COUNT"));
        //定义三种类型的数量值
        List<Integer> SXLX_COUNT = new ArrayList<Integer>();
        //定义饼图的数据格式
        List<Map<String,Object>> nameAndValueList = new ArrayList<Map<String,Object>>();
        //定义三种类型的名称值
        List<String> LX_NAME = new ArrayList<String>();
        List<Map<String,Object>> sxlxList = (List<Map<String, Object>>) sxlxInfo.get("sxlxList");
        for(Map<String,Object> sxlx:sxlxList){
            Map<String,Object> nameAndValue = new HashMap<String,Object>();
            SXLX_COUNT.add(Integer.parseInt(sxlx.get("SXLX_COUNT").toString()));
            if(sxlx.get("LX_NAME")!=null){
                LX_NAME.add(sxlx.get("LX_NAME").toString());
                nameAndValue.put("name", sxlx.get("LX_NAME").toString());
                nameAndValue.put("value", Integer.parseInt(sxlx.get("SXLX_COUNT").toString()));
                nameAndValueList.add(nameAndValue);
            }
        }
        result.put("SXLX_COUNT", SXLX_COUNT);
        result.put("LX_NAME", LX_NAME);
        result.put("nameAndValueList", nameAndValueList);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "ckbjltjStatis")
    public ModelAndView ckbjltjStatis(HttpServletRequest request) {
        return new ModelAndView("statis/ckbjltjStatis");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "ckbjltjData")
    public void ckbjltjData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .getCkbjltjStatist(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 
     * 描述 导出窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:03:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportCkbjltjExcel")
    public void exportCkbjltjExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .getCkbjltjStatist(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/ckbjltjStatis.xls";
        int startRow = 6;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }

        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        Set<String> excludeFields = new HashSet<String>();
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心窗口办件量统计分析表.xls",
                excludeFields, response, startRow, startCol, datas, "", true,
                new int[] { 1, 2 });
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bjzttjStatis")
    public ModelAndView bjzttjStatis(HttpServletRequest request) {
        return new ModelAndView("statis/bjzttjStatis");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "bjzttjData")
    public void bjzttjData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .getBjzttjStatist(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 
     * 描述 导出窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:03:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportBjzttjExcel")
    public void exportBjzttjExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .getBjzttjStatist(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/bjzttjStatis.xls";
        int startRow = 6;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }

        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        Set<String> excludeFields = new HashSet<String>();        
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心办件状态统计分析表.xls ", excludeFields,
                response, startRow, startCol,datas,"",true,new int[]{1});
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "spsxmxStatis")
    public ModelAndView spsxhzStatis(HttpServletRequest request) {
        return new ModelAndView("statis/spsxmxStatis");
    }

    /**
     * 
     * 描述 审批事项明细表
     * 
     * @author Danto Huang
     * @created 2016-4-13 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "spsxmxData")
    public void spsxmxData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.STATUS", "desc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = statisticsService.getSpsxmxStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,response);
    }
    /**
     * 
     * 描述 导出审批事项明细表
     * 
     * @author Danto Huang
     * @created 2016-4-13 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSpsxmxExcel")
    public void exportSpsxmxExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.STATUS", "desc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> dataList = statisticsService.getSpsxmxStatistForExp(filter);
        if(dataList!=null&&dataList.size()>10000){
            dataList=new ArrayList<>();
        }
        //SysUser sysUser = AppUtil.getLoginUser();
        String sysUserName = AppUtil.getLoginUser() == null ? 
                "" : AppUtil.getLoginUser().getUsername();
        log.info("开始导出审批事项明细表数据条数:"+dataList.size()+"导出人员:"+sysUserName);
        
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/spsxmxStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String DEPT_NAME = request.getParameter("DEPTNAME");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        if(StringUtils.isNotEmpty(DEPT_NAME)){
            DEPT_NAME = "("+DEPT_NAME+")";
        }else{
            DEPT_NAME = "";
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        ExcelReplaceDataVO vo2 = new ExcelReplaceDataVO();
        vo2.setRow(0);
        vo2.setColumn(0);
        vo2.setKey("${dept_name}");
        vo2.setValue(DEPT_NAME);
        datas.add(vo1);
        datas.add(vo2);
        ExcelUtil.exportXls(tplPath, dataList, "审批事项明细表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    } 
    /**
     * 
     * 描述：特殊环节挂起统计导出
     * @author Water Guo
     * @created 2018-1-9 下午3:13:48
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSpeExcel")
    public void exportSpeExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("H.BEGIN_TIME","DESC");
        filter.addSorted("E.EXE_ID", "DESC");
        filter.addSorted("H.TASK_ID","DESC");
        List<Map<String, Object>> dataList = flowTaskService.findHangSpe(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/speStatis.xls";
        int startRow = 2;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("TASK_ID");
        excludeFields.add("LINK_ID");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, dataList, "特殊环节监察表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    } 
    /**
     * 
     * 描述：导出全部事项
     * @author Water Guo
     * @created 2017-3-8 下午4:49:07
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportAllItemToExcel")
    public void exportAllItemToExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.DEPART_NAME", "desc");
//        filter.addSorted("T.CATALOG_NAME", "asc");
        List<Map<String, Object>> dataList = statisticsService.getAllItemForExp(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/allServiceItem.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        ExcelReplaceDataVO vo2 = new ExcelReplaceDataVO();
        vo2.setRow(0);
        vo2.setColumn(0);
        vo2.setKey("${dept_name}");
        vo2.setValue("");
        datas.add(vo1);
        datas.add(vo2);
        ExcelUtil.exportXls(tplPath, dataList, "事项汇总表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
       }
    /**
     *
     * 描述：企业信息表
     * @author Water Guo
     * @created 2017-3-8 下午4:49:07
     * @param request
     * @param response
     */
    @RequestMapping("/exportCompanyInfosToExcel")
    public void exportCompanyInfosToExcel(HttpServletRequest request,
                                     HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        filter.getPagingBean().setPageSize(300000);
//        filter.addSorted("T.CATALOG_NAME", "asc");
        List<Map<String, Object>> dataList = statistCommercialService.findInfoBySqlFilterForExp(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/company.xls";
        int startRow = 3;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelUtil.exportXls(tplPath, dataList, "企业信息表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "clsxmxStatis")
    public ModelAndView clsxmxStatis(HttpServletRequest request) {
        return new ModelAndView("statis/clsxmxStatis");
    }

    /**
     * 
     * 描述 审批事项明细表
     * 
     * @author Danto Huang
     * @created 2016-4-13 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "clsxmxData")
    public void clsxmxData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.oper_time", "desc");
        List<Map<String, Object>> list = statisticsService.getClsxmxStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,response);
    }
    /**
     * 
     * 描述   逾期办结件查询
     * @author Danto Huang
     * @created 2016-8-15 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "yqbjmxStatis")
    public ModelAndView yqbjmxStatis(HttpServletRequest request){
        return new ModelAndView("statis/yqbjmxStatis");
    }

    /**
     * 
     * 描述 逾期办结件明细表
     * 
     * @author Danto Huang
     * @created 2016-8-15 上午10:47:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "yqbjmxData")
    public void yqbjmxData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> list = statisticsService.getYqbjmxStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,response);
    }
    
    /**
     * 
     * 描述 导出审批事项明细表
     * 
     * @author Danto Huang
     * @created 2016-4-13 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportYqbjmxExcel")
    public void exportYqbjmxExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> dataList = statisticsService.getYqbjmxStatistForExp(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/yqbjmxStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String DEPT_NAME = request.getParameter("DEPTNAME");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        if(StringUtils.isNotEmpty(DEPT_NAME)){
            DEPT_NAME = "("+DEPT_NAME+")";
        }else{
            DEPT_NAME = "";
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        ExcelReplaceDataVO vo2 = new ExcelReplaceDataVO();
        vo2.setRow(0);
        vo2.setColumn(0);
        vo2.setKey("${dept_name}");
        vo2.setValue(DEPT_NAME);
        datas.add(vo1);
        datas.add(vo2);
        ExcelUtil.exportXls(tplPath, dataList, "逾期办结事项明细表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    } 
    
    
    /**
     * 获取累计办件数据
     * @param request
     * @return
     */
    @RequestMapping("/getLjbjsj")
    @ResponseBody
    public void getLjbjsj(HttpServletRequest request, HttpServletResponse response) {
         Map<String, Object> variables = statisticsService.getLjbjsj(); 
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);        
    }

    
    /**
     * 获取累计办件数据
     * @param request
     * @return
     */
    @RequestMapping("/getYearLjbjsj")
    @ResponseBody
    public void getYearLjbjsj(HttpServletRequest request, HttpServletResponse response) {
         Map<String, Object> variables = statisticsService.getYearLjbjsj(); 
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);        
    }

    
    /**
     * 获取累计办件数据
     * @param request
     * @return
     */
    @RequestMapping("/getMonthLjbjsj")
    @ResponseBody
    public void getMonthLjbjsj(HttpServletRequest request, HttpServletResponse response) {
         Map<String, Object> variables = statisticsService.getMonthLjbjsj(); 
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);        
    }

    
    /**
     * 获取累计办件数据
     * @param request
     * @return
     */
    @RequestMapping("/getDayLjbjsj")
    @ResponseBody
    public void getDayLjbjsj(HttpServletRequest request, HttpServletResponse response) {
         Map<String, Object> variables = statisticsService.getDayLjbjsj(); 
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);        
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-19 上午11:52:42
     * @param request
     * @return
     */
    @RequestMapping(params="refreshTzxmBjxl")
    @ResponseBody
    public AjaxJson refreshTzxmBjxl(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String beginDate = request.getParameter("Q_T.STATIST_DATE_>=");
        String endDate = request.getParameter("Q_T.STATIST_DATE_<=");
        try {
            executionService.tzxmBjxl(beginDate, endDate);
            j.setMsg("同步成功");
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("同步失败");
            log.error(e.getMessage());
        }
        return j;
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-19 下午3:05:56
     * @param request
     * @return
     */
    @RequestMapping(params="tzxmbjmx")
    public ModelAndView tzxmbjmx(HttpServletRequest request){        
        return new ModelAndView("statis/tzxmbjmx");
    }
    
    /**
     * 
     * 描述   投资项目逾期办结明细
     * @author Danto Huang
     * @created 2016-10-19 下午3:21:30
     * @param request
     * @param response
     */
    @RequestMapping(params="tzxmbjmxData")
    public void tzxmbjmxData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.create_time", "desc");
        List<Map<String, Object>> list = statisticsService.getTzxmyqbjmxStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,response);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-19 下午3:38:00
     * @param request
     * @return
     */
    @RequestMapping(params="tzxmDataRefresh")
    public ModelAndView tzxmDataRefresh(HttpServletRequest request){
        return new ModelAndView("statis/tzxmDataRefresh");
    }
    
    /**
     * 
     * 描述 导出审批事项明细表
     * 
     * @author Danto Huang
     * @created 2016-4-13 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportTzxmYqbjmxExcel")
    public void exportTzxmYqbjmxExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.create_time", "desc");
        List<Map<String, Object>> dataList = statisticsService.getTzxmyqbjmxStatistForExp(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/tzxmyqbjmxStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "投资项目逾期办结事项明细表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }
    
    /**
     * 跳转到产业奖补统计信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "cyjbStatis")
    public ModelAndView cyjbStatis(HttpServletRequest request) {
        return new ModelAndView("statis/cyjbStatis");
    }

    /**
     * 
     * 描述 产业奖补统计列表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "cyjbData")
    public void cyjbData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.getPagingBean().setPageSize(50);
        List<Map<String, Object>> list = statisticsService
                .findByCyjbItemSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }
    /**
     * 
     * 描述 导出办件评议统计报表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:03:18
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportCjybExcel")
    public void exportCjybExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .findByCyjbExcelSqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/cjybStatis.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "产业奖补统计分析表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1,2,3,4,5,6});
    }

    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "slbjpyStatis")
    public ModelAndView slbjpyStatis(HttpServletRequest request) {
        return new ModelAndView("statis/slbjpyStatis");
    }

    /**
     * 
     * 描述   办件评议统计列表
     * @author Danto Huang
     * @created 2016-10-31 下午4:26:22
     * @param request
     * @param response
     */
    @RequestMapping(params = "slbjpyData")
    public void slbjpyData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .getSljPjStatist(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 
     * 描述    导出办件评议统计报表
     * @author Danto Huang
     * @created 2016-10-31 下午4:26:12
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSlBjpytjExcel")
    public void exportSlBjpytjExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsService
                .getSljPjStatist(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statis/slbjpyStatis.xls";
        int startRow = 5;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心人员办件评议统计分析表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1,1});
    }
    
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-9-18 下午2:08:38
     * @param request
     * @return
     */
    @RequestMapping(params="exportSwbView")
    public ModelAndView exportSwbView(HttpServletRequest request){
        return new ModelAndView("statis/exportSwbView");
    }
    

    /**
     * 
     * 描述 导出省网办事项统计报表
     * @author Rider Chen
     * @created 2016年11月10日 上午10:05:58
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportSwbExcel")
    public void exportSwbExcel(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<List<Map<String, Object>>> dataList = new ArrayList<List<Map<String,Object>>>();
        //事项信息
        List<Map<String, Object>> itemList = statisticsService.findBySwbItemSqlFilter(filter);
        //材料信息
        List<Map<String, Object>> materList = statisticsService.findBySwbMaterSqlFilter(filter);
        //日志信息
        List<Map<String, Object>> logList = statisticsService.findBySwbLogSqlFilter(filter);
        dataList.add(itemList);
        dataList.add(materList);
        dataList.add(logList);
        String tplPath = AppUtil.getAppAbsolutePath()  + "/webpage/statis/swbStatis.xls";
        int startRow = 3;
        int startCol = 1;
        //排除字段
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("UPDATE_TIME");
        excludeFields.add("TREE_SN");
        excludeFields.add("CATALOG_NAME");
        excludeFields.add("ITEM_NAME");
        ExcelUtil.exportSheet(tplPath, dataList, "平潭综合实验区行政省网办事项统计分析表.xls",
                excludeFields, response, startRow, startCol, null,"",true,new int[]{1,1});
    }
    /**
     * 
     * 描述 导出Excel文件
     * @author Rider Chen
     * @created 2021年3月26日 下午2:19:41
     * @param request
     * @param response
     */
    @RequestMapping("/exportExcelToSql")
    public void exportExcelToSql(HttpServletRequest request , HttpServletResponse response) {
        List<Map<String, Object>> list  =  statisticsService.getSqlStatist(null);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statis/sqlstatis.xls";
        int startRow = 2;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, list, "统计数据.xls",
        excludeFields, response, startRow, startCol, datas,"",false,new int[]{0});
    }
}
