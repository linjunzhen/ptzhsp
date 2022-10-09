/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statistic.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.statistic.service.StatisticsNewService;
import net.evecom.platform.statistic.service.impl.StatisticsNewServiceImpl;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  新报表Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/statisticsNewController")
public class StatisticsNewController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(StatisticsNewController.class);
    /**
     * 引入Service
     */
    @Resource
    private StatisticsNewService statisticsNewService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "slbjpyStatis")
    public ModelAndView slbjpyStatis(HttpServletRequest request) {
        return new ModelAndView("statistics/slbjpyStatis");
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
        List<Map<String, Object>> list = statisticsNewService
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
        List<Map<String, Object>> dataList = statisticsNewService
                .getSljPjStatist(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statistics/slbjpyStatis.xls";
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
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "ckbjltjStatis")
    public ModelAndView ckbjltjStatis(HttpServletRequest request) {
        return new ModelAndView("statistics/ckbjltjStatis");
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
        List<Map<String, Object>> list = statisticsNewService
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
        List<Map<String, Object>> dataList = statisticsNewService
                .getCkbjltjStatist(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statistics/ckbjltjStatis.xls";
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
     * 描述：一窗通办办件明细表页面
     * @author Madison You
     * @created 2019-07-02 上午11:28:01
     */
    @RequestMapping(params = "bjmxbStatis")
    public ModelAndView bjmxView(){
        return new ModelAndView("statistics/bjmxbStatis");
    }

    /**
     * 描述：一窗通办办件明细表获取数据
     * @author Madison You
     * @created 2019-07-02 上午11:28:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "bjmxbData")
    public void bjmxbData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsNewService.findbjmxbDataBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述：一窗通办办件明细表导出
     * @author Madison You
     * @created 2019-07-02 上午11:28:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportBjmxbExcel")
    public void exportBjmxbExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsNewService
                .findbjmxbDataBySqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statistics/bjmxbstatis.xls";
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
        excludeFields.add("FZXSSZ");
        excludeFields.add("SLSHXS");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心部门一窗通办办件明细表.xls",
                excludeFields, response, startRow, startCol, datas,"",false,new int[]{0});
    }


    /**
     * 描述：一窗通办等待时长统计表页面
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    @RequestMapping(params = "ddsctjStatis")
    public ModelAndView ddsctjView(){
        return new ModelAndView("statistics/ddsctjStatis");
    }


    /**
     * 描述：一窗通办等待时长统计表数据
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    @RequestMapping(params = "ddsctjData")
    public void ddsctjData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsNewService.findddsctjDataBySqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述：一窗通办等待时长统计表导出表格
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    @RequestMapping(params = "exportDdsctjExcel")
    public void exportDdsctjExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsNewService
                .findddsctjDataBySqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statistics/ddsctjstatis.xls";
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
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心部门一窗通办等待时长统计表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1});
    }


    /**
     * 描述：一窗通办办件统计表页面
     * @author Madison You
     * @created 2019-07-03 上午11:28:01
     */
    @RequestMapping(params = "ycbjtjStatis")
    public ModelAndView ycbjtjView(){
        return new ModelAndView("statistics/ycbjtjStatis");
    }

    /**
     * 描述：一窗通办办件统计表数据
     * @author Madison You
     * @created 2019-07-04 上午11:28:01
     */
    @RequestMapping(params = "ycbjtjData")
    public void yctbbjtjData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = null;
        if (filter.getRequest().getParameter("Q_A.CREATE_TIME_>=") != null) {
            list = statisticsNewService.findycbjtjDataBySqlFilter(filter);
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述：一窗通办办件统计表导出
     * @author Madison You
     * @created 2019-07-04 上午11:28:01
     */
    @RequestMapping(params = "exportYcbjtjExcel")
    public void exportYcbjtjExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsNewService
                .findycbjtjDataBySqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statistics/ycbjtjstatis.xls";
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
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("BJSCHJFZ");
        excludeFields.add("BJSCJBFZ");
        excludeFields.add("BJSCPTFZ");
        excludeFields.add("BJSCCNFZ");
        excludeFields.add("SLSCHJFZ");
        excludeFields.add("SLSCJBFZ");
        excludeFields.add("SLSCPTFZ");
        excludeFields.add("SLSCCNFZ");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心部门一窗通办办件统计表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1});
    }

    /**
     * 描述：一窗通办事项系数设置表页面
     * @author Madison You
     * @created 2019-07-04 上午11:28:01
     */
    @RequestMapping(params = "sxxsszStatis")
    public ModelAndView sxxsszView(){
        return new ModelAndView("statistics/sxxsszStatis");
    }

    /**
     * 描述：一窗通办事项系数设置表数据
     * @author Madison You
     * @created 2019-07-05 上午10:56:01
     */
    @RequestMapping(params = "sxxsszData")
    public void sxxsszData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsNewService.findsxxsszDataBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述：一窗通办事项系数设置表导出表格
     * @author Madison You
     * @created 2019-07-05 上午10:56:01
     */
    @RequestMapping(params = "exportSxxsszExcel")
    public void exportSxxsszExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsNewService
                .findsxxsszDataBySqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/statistics/sxxsszstatis.xls";
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
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("ITEM_CODE");
        excludeFields.add("BJSL");
        excludeFields.add("FZXSSZ");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心部门一窗通办事项系数设置表.xls",
                excludeFields, response, startRow, startCol, datas,"",false,new int[]{0});
    }

    /**
     * 描述: 修改分值系数设置的系数页面
     *
     * @author Madison You
     * @created 2019/8/29 14:27:00
     * @param
     * @return
     */
    @RequestMapping(params = "sxxsszReviseView")
    public ModelAndView sxxsszReviseView(HttpServletRequest request){
        String itemCode = request.getParameter("itemCode");
        Map<String,Object> map = statisticsNewService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[]{"ITEM_CODE"}, new Object[]{itemCode});
        request.setAttribute("subject",map);
        return new ModelAndView("statistics/sxxsszReviseView");
    }

    /**
     * 描述: 修改分值系数设置的系数
     *
     * @author Madison You
     * @created 2019/8/29 14:45:00
     * @param
     * @return
     */
    @RequestMapping(params = "sxxsszReviseUpdateAndSave")
    @ResponseBody
    public AjaxJson sxxsszReviseUpdateAndSave(HttpServletRequest request , HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String itemID = request.getParameter("ITEM_ID");
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        statisticsNewService.saveOrUpdate(flowForm,"T_WSBS_SERVICEITEM",itemID);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述:环土局专属行业列表
     *
     * @author Madison You
     * @created 2019/8/22 10:27:00
     * @param
     * @return
     */
    @RequestMapping(params = "HTIndustryList")
    public ModelAndView htIndustryList(HttpServletRequest request){
        return new ModelAndView("statistics/htindustryList");
    }

    /**
     * 描述:环土局专属行业列表
     *
     * @author Madison You
     * @created 2019/8/22 11:42:00
     * @param
     * @return
     */
    @RequestMapping(params = "HTIndustryListData")
    public void htIndustryListData(HttpServletRequest request , HttpServletResponse response){
        List<Map<String, Object>> list = statisticsNewService.htIndustryList(request);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:修改环土局专属行业页面
     *
     * @author Madison You
     * @created 2019/8/22 11:42:00
     * @param
     * @return
     */
    @RequestMapping(params = "reviseHTIndustry")
    public ModelAndView reviseHTIndustry(HttpServletRequest request , HttpServletResponse response){
        String industryID = request.getParameter("INDUSTRY_ID");
        Map<String, Object> map = null;
        if (industryID != null && industryID.length() > 0) {
            map = statisticsNewService.getByJdbc("T_MSJW_SYSTEM_HTINDUSTRY", new String[]{"INDUSTRY_ID"},
                    new Object[]{industryID});
        }
        request.setAttribute("industry", map);
        return new ModelAndView("statistics/reviseHTIndustry");
    }

    /**
     * 描述:修改或添加环土局行业
     *
     * @author Madison You
     * @created 2019/8/22 15:09:00
     * @param
     * @return
     */
    @RequestMapping(params = "reviseHTIndustryUpdateAndSave")
    @ResponseBody
    public AjaxJson reviseHTIndustryUpdateAndSave(HttpServletRequest request , HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String industryID = request.getParameter("INDUSTRY_ID");
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        statisticsNewService.saveOrUpdate(flowForm,"T_MSJW_SYSTEM_HTINDUSTRY",industryID);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述:删除环土局行业
     *
     * @author Madison You
     * @created 2019/8/22 15:10:00
     * @param
     * @return
     */
    @RequestMapping(params = "deleteHTIndustry")
    @ResponseBody
    public AjaxJson deleteHTIndustry(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        if (selectColNames != null && selectColNames.length() > 0) {
            statisticsNewService.remove("T_MSJW_SYSTEM_HTINDUSTRY", new String[]{"INDUSTRY_ID"},
                    new Object[]{selectColNames});
            j.setMsg("删除成功");
        } else {
            j.setMsg("删除失败");
        }
        return j;
    }

    /**
     * 描述:人员分组页面
     *
     * @author Madison You
     * @created 2019/9/2 16:28:00
     * @param
     * @return
     */
    @RequestMapping(params = "ryfzStatis")
    public ModelAndView ryfzStatis(){
        return new ModelAndView("statistics/ryfzStatis");
    }

    /**
     * 描述:绩效考核表格人员分组
     *
     * @author Madison You
     * @created 2019/9/2 16:28:00
     * @param
     * @return
     */
    @RequestMapping(params = "ryfzData")
    public void ryfzData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsNewService.ryfzData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:绩效考核表格人员分组修改
     *
     * @author Madison You
     * @created 2019/9/20 11:10:00
     * @param
     * @return
     */
    @RequestMapping(params = "ryfzReviseView")
    public ModelAndView ryfzReviseView(HttpServletRequest request) {
        String userId = request.getParameter("USER_ID");
        Map<String,Object> userInfo = statisticsNewService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                new String[]{"USER_ID"}, new Object[]{userId});
        request.setAttribute("subject", userInfo);
        return new ModelAndView("statistics/ryfzReviseView");
    }

    /**
     * 描述:绩效考核表格人员分组保存
     *
     * @author Madison You
     * @created 2019/9/20 11:17:00
     * @param
     * @return
     */
    @RequestMapping(params = "ryfzReviseUpdateAndSave")
    @ResponseBody
    public AjaxJson ryfzReviseUpdateAndSave(HttpServletRequest request , HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String userId = request.getParameter("USER_ID");
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        statisticsNewService.saveOrUpdate(flowForm,"T_MSJW_SYSTEM_SYSUSER",userId);
        j.setMsg("保存成功");
        return j;
    }


    /**
     * 描述:工程建设项目查询页面
     *
     * @author Madison You
     * @created 2019/9/10 10:16:00
     * @param
     * @return
     */
    @RequestMapping(params = "gcjsxmcxStatis")
    public ModelAndView gcjsxmcxStatis(){
        return new ModelAndView("statistics/gcjsxmcxStatis");
    }

    /**
     * 描述:工程建设项目查询数据
     *
     * @author Madison You
     * @created 2019/9/10 10:16:00
     * @param
     * @return
     */
    @RequestMapping(params = "gcjsxmcxData")
    public void gcjsxmcxData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsNewService.gcjsxmcxData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述: 工程建设项目查询修改页面
     *
     * @author Madison You
     * @created 2019/9/16 11:47:00
     * @param
     * @return
     */
    @RequestMapping(params = "gcjsxmcxReviseView")
    public ModelAndView gcjsxmcxReviseView(HttpServletRequest request){
        String projectCode = request.getParameter("PROJECT_CODE");
        Map<String,Object> map = statisticsNewService.getByJdbc("SPGL_XMJBXXB", new String[]{"PROJECT_CODE"},
                new Object[]{projectCode});
        request.setAttribute("subject", map);
        return new ModelAndView("statistics/gcjsxmcxReviseView");
    }

    /**
     * 描述: 工程建设项目查询修改保存
     *
     * @author Madison You
     * @created 2019/9/16 11:47:00
     * @param
     * @return
     */
    @RequestMapping(params = "gcjsxmcxReviseUpdateAndSave")
    @ResponseBody
    public AjaxJson gcjsxmcxReviseUpdateAndSave(HttpServletRequest request , HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("ID");
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        statisticsNewService.saveOrUpdate(flowForm,"SPGL_XMJBXXB",id);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述: 工程建设项目材料下载
     *
     * @author Madison You
     * @created 2019/9/16 14:19:00
     * @param
     * @return
     */
    @RequestMapping(params = "downLoadGcjsxmcxView")
    public ModelAndView downLoadGcjsxmcxView(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        String projectCode = request.getParameter("PROJECT_CODE");
        List<Map<String, Object>> materList = statisticsNewService.fetchGcjsxmcxMater(filter);
        request.setAttribute("PROJECT_CODE",projectCode);
        request.setAttribute("subject", materList);
        return new ModelAndView("statistics/downLoadGcjsxmcxView");
    }

    /**
     * 描述:工程建设项目当前已办理事项和办件
     *
     * @author Madison You
     * @created 2019/9/19 8:50:00
     * @param
     * @return
     */
    @RequestMapping(params = "gcjsxmcxDealItem")
    public ModelAndView gcjsxmcxDealItem(HttpServletRequest request){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.gcjsxmcxDealItemList(filter);
        request.setAttribute("subject",list);
        request.setAttribute("PROJECT_CODE",request.getParameter("PROJECT_CODE"));
        return new ModelAndView("statistics/gcjsxmcxDealItem");
    }



    /**
     * 描述: 商事办件状态和结果附件查询
     *
     * @author Madison You
     * @created 2019/9/18 9:44:00
     * @param
     * @return
     */
    @RequestMapping("/findCommercialStatus")
    @ResponseBody
    public Map<String,Object> findCommercialStatus(HttpServletRequest request , HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<>();
        String SBH = request.getParameter("XW_SSSBH");
        Map<String,Object> busRecord = statisticsNewService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"}, new Object[]{SBH});
        if (busRecord != null) {
            String RUN_STATUS = busRecord.get("RUN_STATUS").toString();
            if (RUN_STATUS.equals("2")) {
                map.put("status", "success");
                map.put("RESULT_FILE_ID", busRecord.get("RESULT_FILE_ID"));
            } else {
                map.put("status", "fail");
            }
        } else {
            map.put("status", "none");
        }
        return map;
    }

    /**
     * 描述:绩效考核得分页面
     *
     * @author Madison You
     * @created 2019/9/26 15:19:00
     * @param
     * @return
     */
    @RequestMapping(params = "jxkhdfStatis")
    public ModelAndView jxkhdfStatis(){
        return new ModelAndView("statistics/jxkhdfStatis");
    }

    /**
     * 描述:绩效考核得分数据
     *
     * @author Madison You
     * @created 2019/9/26 15:23:00
     * @param
     * @return
     */
    @RequestMapping(params = "jxkhdfCal")
    public void jxkhdfCal(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsNewService.jxkhdfCal(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:绩效考核加分减分页面
     *
     * @author Madison You
     * @created 2019/10/14 17:10:00
     * @param
     * @return
     */
    @RequestMapping(params = "jxjfkfStatis")
    public ModelAndView jxjfkfStatis(){
        return new ModelAndView("statistics/jxjfkfStatis");
    }

    /**
     * 描述:绩效考核加分减分数据
     *
     * @author Madison You
     * @created 2019/10/14 17:13:00
     * @param
     * @return
     */
    @RequestMapping(params = "jxjfkfData")
    public void jxjfkfData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.jxjfkfData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:绩效考核加分减分新增页面
     *
     * @author Madison You
     * @created 2019/10/15 8:41:00
     * @param
     * @return
     */
    @RequestMapping(params = "addJxjfkfStatis")
    public ModelAndView addJxjfkfStatis(HttpServletRequest request , HttpServletResponse response){
        String jfkfId = request.getParameter("JFKF_ID");
        Map<String, Object> jxjfkfInfo = new HashMap<String, Object>();
        if (jfkfId != null) {
            jxjfkfInfo = statisticsNewService.getByJdbc("JBPM6_JXJFKFSTATIST",
                    new String[]{"JFKF_ID"}, new Object[]{jfkfId});
        }
        request.setAttribute("subject",jxjfkfInfo);
        return new ModelAndView("statistics/addJxjfkfStatis");
    }

    /**
     * 描述:绩效考核加分减分保存
     *
     * @author Madison You
     * @created 2019/10/15 12:32:00
     * @param
     * @return
     */
    @RequestMapping(params = "jxjfkfUpdateAndSave")
    @ResponseBody
    public AjaxJson jxjfkfUpdateAndSave(HttpServletRequest request , HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String userId = AppUtil.getLoginUser().getUserId();
        String JFKF_ID = request.getParameter("JFKF_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("OPERATE_ID", userId);
        statisticsNewService.saveOrUpdate(variables, "JBPM6_JXJFKFSTATIST", JFKF_ID);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述:绩效考核加分减分删除
     *
     * @author Madison You
     * @created 2019/10/15 14:15:00
     * @param
     * @return
     */
    @RequestMapping(params = "deleteJxjfkfStatis")
    @ResponseBody
    public AjaxJson deleteJxjfkfStatis(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        if (selectColNames != null && selectColNames.length() > 0) {
            String[] selectColNameArr = selectColNames.split(",");
            for (String selectColName : selectColNameArr) {
                statisticsNewService.remove("JBPM6_JXJFKFSTATIST", new String[]{"JFKF_ID"},
                        new Object[]{selectColName});
            }
            j.setMsg("删除成功");
        } else {
            j.setMsg("删除失败");
        }
        return j;
    }

    /**
     * 描述:统计所有当前环节还在网上预审的件
     *
     * @author Madison You
     * @created 2019/10/16 10:10:00
     * @param
     * @return
     */
    @RequestMapping(params = "wsysStatis")
    public ModelAndView wsysStatis(){
        return new ModelAndView("statistics/wsysStatis");
    }

    /**
     * 描述:统计所有当前环节还在网上预审的件
     *
     * @author Madison You
     * @created 2019/10/16 10:13:00
     * @param
     * @return
     */
    @RequestMapping(params = "wsysData")
    public void wsysData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.wsysData(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:导出网上预审办件表格
     *
     * @author Madison You
     * @created 2019/10/17 9:46:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportWsysExcel")
    public void exportWsysExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsNewService.wsysData(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statistics/wsysStatis.xls";
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
        ExcelUtil.exportXls(tplPath, dataList, "网上预审办件.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }

    /**
     * 描述:预约取号统计页面
     *
     * @author Madison You
     * @created 2020/2/1 11:27:00
     * @param
     * @return
     */
    @RequestMapping(params = "callAppointStatis")
    public ModelAndView callAppointStatis(HttpServletRequest request , HttpServletResponse response){
        request.setAttribute("currentDate", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        return new ModelAndView("statistics/callAppointStatis");
    }

    /**
     * 描述:预约取号统计数据
     *
     * @author Madison You
     * @created 2020/2/1 11:36:00
     * @param
     * @return
     */
    @RequestMapping(params = "callAppointData")
    public void callAppointData(HttpServletRequest request , HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.callAppointData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:不动产业务受理情况表
     *
     * @author Madison You
     * @created 2020/4/7 9:47:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcCallStatis")
    public ModelAndView bdcCallStatis(HttpServletRequest request , HttpServletResponse response){
        return new ModelAndView("statistics/bdcCallStatis");
    }

    /**
     * 描述:不动产业务受理情况数据
     *
     * @author Madison You
     * @created 2020/4/7 9:49:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcCallData")
    public void bdcCallData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.bdcCallData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:不动产业务受理同步页面
     *
     * @author Madison You
     * @created 2020/4/7 10:07:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcCallDataRefresh")
    public ModelAndView bdcCallDataRefresh(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("statistics/bdcCallDataRefresh");
    }

    /**
     * 描述:不动产业务受理数据同步
     *
     * @author Madison You
     * @created 2020/4/7 16:21:00
     * @param
     * @return
     */
    @RequestMapping(params = "refreshBdcCallBjxl")
    @ResponseBody
    public AjaxJson refreshBdcCallBjxl(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String beginDate = request.getParameter("Q_T.STATIST_DATE_>=");
        String endDate = request.getParameter("Q_T.STATIST_DATE_<=");
        List<String> days = this.getEveryDays(beginDate, endDate);
        try{
            for (String day : days) {
                statisticsNewService.bdcCallDataCal(day);
            }
            j.setMsg("同步成功");
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("同步失败");
            log.error(e.getMessage());
        }
        return j;
    }

    /**
     * 描述:导出不动产业务受理数据表格
     *
     * @author Madison You
     * @created 2020/4/8 9:42:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportBdcCallExcel")
    public void exportBdcCallExcel(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = statisticsNewService.bdcCallData(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statistics/bdcCallStatis.xls";
        int startRow = 4;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("CURRENTROW");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, dataList, "不动产登记交易处业务办理情况表.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }

    /**
     * 描述:按部门查询申请人信息
     *
     * @author Madison You
     * @created 2021/1/18 15:39:00
     * @param
     * @return
     */
    @RequestMapping(params = "departSqrStatis")
    public ModelAndView departSqrStatis(HttpServletRequest request) {
        return new ModelAndView("statistics/departSqrStatis");
    }

    /**
     * 描述:部门申请人数据
     *
     * @author Madison You
     * @created 2021/1/18 15:45:00
     * @param
     * @return
     */
    @RequestMapping(params = "departSqrData")
    public void departSqrData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.getDepartSqrData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:导出部门申请人数据
     *
     * @author Madison You
     * @created 2021/1/18 16:26:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportDepartSqrExcel")
    public void exportDepartSqrExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("isExport", "1");
        List<Map<String,Object>> dataList = statisticsNewService.getDepartSqrData(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statistics/departSqrStatis.xls";
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
        excludeFields.add("RN");
        excludeFields.add("CURRENTROW");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "部门申请人统计.xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }

    /**
     * 描述:每日受理统计（线上）
     *
     * @author Madison You
     * @created 2021/3/23 9:25:00
     * @param
     * @return
     */
    @RequestMapping(params = "mrsltjXsStatisView")
    public ModelAndView mrsltjXsStatisView(HttpServletRequest request) {
        request.setAttribute("date", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        return new ModelAndView("statistics/mrsltjXsStatisView");
    }

    /**
     * 描述:每日受理统计（线上）
     *
     * @author Madison You
     * @created 2021/3/23 9:28:00
     * @param
     * @return
     */
    @RequestMapping(params = "mrsltjXsData")
    public void mrsltjXsData(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.mrsltjXsData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/25 14:17:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportMrsltjXsExcel")
    public void exportMrsltjXsExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("isExport", "1");
        List<Map<String,Object>> dataList = statisticsNewService.mrsltjXsData(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statistics/mrsltjXsExcel.xls";
        int startRow = 4;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("RN");
        excludeFields.add("CURRENTROW");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, dataList, "每日受理统计(线上).xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }



    /**
     * 描述:每日受理统计线下
     *
     * @author Madison You
     * @created 2021/3/23 9:26:00
     * @param
     * @return
     */
    @RequestMapping(params = "mrsltjXxStatisView")
    public ModelAndView mrsltjXxStatisView(HttpServletRequest request) {
        request.setAttribute("date", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        return new ModelAndView("statistics/mrsltjXxStatisView");
    }

    /**
     * 描述:每日受理统计线下
     *
     * @author Madison You
     * @created 2021/3/23 9:28:00
     * @param
     * @return
     */
    @RequestMapping(params = "mrsltjXxData")
    public void mrsltjXxData(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list = statisticsNewService.mrsltjXxData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE,
                response);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/25 14:26:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportMrsltjXxExcel")
    public void exportMrsltjXxExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("isExport", "1");
        List<Map<String,Object>> dataList = statisticsNewService.mrsltjXxData(filter);
        String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/statistics/mrsltjXxExcel.xls";
        int startRow = 4;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("RN");
        excludeFields.add("CURRENTROW");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, dataList, "每日受理统计(线下).xls", excludeFields,
                response, startRow, startCol, datas,"",false,new int[]{0});
    }



    /**
     * 描述:获取两时间中日期的合集
     *
     * @author Madison You
     * @created 2020/4/7 16:30:00
     * @param
     * @return
     */
    private List<String> getEveryDays(String startTime, String endTime) {
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            log.info("获取两时间中日期的合集出错");
        }
        return days;
    }


}

