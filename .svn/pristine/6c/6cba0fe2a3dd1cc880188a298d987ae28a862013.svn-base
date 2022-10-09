/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.statis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.statis.service.StatisticalReportService;
import net.evecom.platform.system.service.WorkdayService;
/**
 * 
 * 描述 统计报表controller
 * @author Faker Li
 * @created 2016年3月9日 上午10:50:50
 */
@Controller
@RequestMapping("/statisticalReportController")
public class StatisticalReportController extends BaseController{
    /**
     * 引入Service
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;

    /**
     * 引入Service
     */
    @Resource
    private StatisticalReportService statisticalReportService;
    
    /**
     * 引入Service
     */
    @Resource
    private StatisticsService statisticsService;
    /**
     * 跳转到收件按天统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sjattjStatis")
    public ModelAndView sjattjStatis(HttpServletRequest request) {
        String eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        request.setAttribute("eTime", eTime);
        request.setAttribute("sTime", sTime);
        return new ModelAndView("statisreport/sjattjStatis");
    }
    /**
     * 
     * 描述 获取收件按天统计数
     * @author Faker Li
     * @created 2016年3月9日 下午4:38:50
     * @param request
     * @param response
     */
    @RequestMapping(params = "sjattjData")
    public void sjattjData(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> maps = new HashMap<String,Object>();
        String sTime = request.getParameter("Q_T.CREATE_TIME_>=");
        String eTime = request.getParameter("Q_T.CREATE_TIME_<=");
        String echartType = request.getParameter("echartType");
        int sumtjs = 0;
        if(StringUtils.isEmpty(sTime)||StringUtils.isEmpty(eTime)){
            eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        }
        List<Map<String, Object>> list = workdayService.findWorkDayByseTime(sTime,eTime);
        if(list!=null){
            List<String> tjsj = new ArrayList<String>();
            int[] tjsBar = new int[list.size()];
            List<Object> tjsPie = new ArrayList<Object>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> e = list.get(i);
                int cksjs = executionService.getSjsByTime((String)e.get("W_DATE"),"2");
                tjsj.add((String)e.get("W_DATE"));
                sumtjs += cksjs;
                if(echartType.equals("1")){
                    tjsBar[i] = cksjs;
                    maps.put("tjs", tjsBar);
                }else if(echartType.equals("2")){
                    Map<String, Object> ePie = new HashMap<String, Object>();
                    ePie.put("value", cksjs);
                    ePie.put("name", (String)e.get("W_DATE"));
                    tjsPie.add(ePie);
                    maps.put("tjs", tjsPie);
                }
            }
            maps.put("sumtjs", sumtjs);
            maps.put("tjsj", tjsj);
        }
        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到办件总数统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bjzstjStatis")
    public ModelAndView bjzstjStatis(HttpServletRequest request) {
        return new ModelAndView("statisreport/bjzstjStatis");
    }
    
    /**
     * 
     * 描述办件总数统计
     * @author Rider Chen
     * @created 2016-3-10 下午05:00:24
     * @param request
     * @param response
     */
    @RequestMapping(params = "bjzstjData")
    public void getBjzstj(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String echartType = request.getParameter("echartType");
        Map<String, Object> maps = new HashMap<String,Object>();
        List<Map<String, Object>> list  = statisticsService.getBjzstjStatist(filter);
        for (Map<String, Object> map : list) {
            maps.put("sumtjs", map.get("COUNTS"));//办件总数
            List<String> name = new ArrayList<String>();//标题
            name.add("草稿数");
            name.add("在办数");
            name.add("办结数");
            name.add("退件数");
            if(echartType.equals("1")){//柱状图
                List<Integer> num = new ArrayList<Integer>();//统计数
                num.add(Integer.parseInt(map.get("cgs").toString()));               
                num.add(Integer.parseInt(map.get("zbs").toString()));
                num.add(Integer.parseInt(map.get("bjs").toString()));
                num.add(Integer.parseInt(map.get("tjs").toString()));
                maps.put("tjs", num);
            }else if(echartType.equals("2")){//饼状图

                List<Object> tjsPie = new ArrayList<Object>();
                Map<String, Object> ePie0 = new HashMap<String, Object>();
                ePie0.put("value", map.get("cgs"));
                ePie0.put("name", "草稿数");
                tjsPie.add(ePie0);
                Map<String, Object> ePie1 = new HashMap<String, Object>();
                ePie1.put("value", map.get("zbs"));
                ePie1.put("name", "在办数");
                tjsPie.add(ePie1);
                Map<String, Object> ePie2 = new HashMap<String, Object>();
                ePie2.put("value", map.get("bjs"));
                ePie2.put("name", "办结数");
                tjsPie.add(ePie2);
                Map<String, Object> ePie3 = new HashMap<String, Object>();
                ePie3.put("value", map.get("tjs"));
                ePie3.put("name", "退件数");
                tjsPie.add(ePie3);
                maps.put("tjs", tjsPie);
            }
            maps.put("name", name);
        }

        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到承诺件办结统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "cnjbjtjStatis")
    public ModelAndView cnjbjtjStatis(HttpServletRequest request) {
        return new ModelAndView("statisreport/cnjbjtjStatis");
    }
    /**
     * 
     * 描述承诺件办结统计
     * @author Rider Chen
     * @created 2016-3-10 下午05:00:32
     * @param request
     * @param response
     */
    @RequestMapping(params = "cnjbjtjData")
    public void getCnjbjtj(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.SXLX_=", "2");
        List<Map<String, Object>> list = statisticsService
                .findByCountEffiItemSqlFilter(filter);
        String echartType = request.getParameter("echartType");
        Map<String, Object> maps = new HashMap<String,Object>();
        for (Map<String, Object> map : list) {
            maps.put("sumtjs", map.get("COUNTS"));//办件总数
            List<String> name = new ArrayList<String>();//标题
            name.add("提前办结");
            name.add("按时办结");
            name.add("超期办结");
            name.add("即时办结");
            if(echartType.equals("1")){//柱状图
                List<Integer> num = new ArrayList<Integer>();//统计数
                num.add(Integer.parseInt(map.get("EFFI_DESC_1").toString()));               
                num.add(Integer.parseInt(map.get("EFFI_DESC_2").toString()));
                num.add(Integer.parseInt(map.get("EFFI_DESC_3").toString()));
                num.add(Integer.parseInt(map.get("EFFI_DESC_4").toString()));
                maps.put("tjs", num);
            }else if(echartType.equals("2")){//饼状图

                List<Object> tjsPie = new ArrayList<Object>();
                Map<String, Object> ePie0 = new HashMap<String, Object>();
                ePie0.put("value", map.get("EFFI_DESC_1"));
                ePie0.put("name", "提前办结");
                tjsPie.add(ePie0);
                Map<String, Object> ePie1 = new HashMap<String, Object>();
                ePie1.put("value", map.get("EFFI_DESC_2"));
                ePie1.put("name", "按时办结");
                tjsPie.add(ePie1);
                Map<String, Object> ePie2 = new HashMap<String, Object>();
                ePie2.put("value", map.get("EFFI_DESC_3"));
                ePie2.put("name", "超期办结");
                tjsPie.add(ePie2);
                Map<String, Object> ePie3 = new HashMap<String, Object>();
                ePie3.put("value", map.get("EFFI_DESC_4"));
                ePie3.put("name", "即时办结");
                tjsPie.add(ePie3);
                maps.put("tjs", tjsPie);
            }
            maps.put("name", name);
        }

        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    /**
     * 跳转到评议结果统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "pyjgtjStatis")
    public ModelAndView pyjgtjStatis(HttpServletRequest request) {
        return new ModelAndView("statisreport/pyjgtjStatis");
    }
    /**
     * 
     * 描述评议结果统计
     * @author Rider Chen
     * @created 2016-3-10 下午05:00:32
     * @param request
     * @param response
     */
    @RequestMapping(params = "pyjgtjData")
    public void getPyjgtj(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .getPyjgtjStatist(filter);
        String echartType = request.getParameter("echartType");
        Map<String, Object> maps = new HashMap<String,Object>();
        for (Map<String, Object> map : list) {
            maps.put("sumtjs", map.get("SUMS"));//办件总数
            List<String> name = new ArrayList<String>();//标题
            name.add("非常满意");
            name.add("满意");
            name.add("一般");
            name.add("不满意");
            if(echartType.equals("1")){//柱状图
                List<Integer> num = new ArrayList<Integer>();//统计数
                num.add(Integer.parseInt(map.get("EVALUATE_3").toString()));               
                num.add(Integer.parseInt(map.get("EVALUATE_2").toString()));
                num.add(Integer.parseInt(map.get("EVALUATE_1").toString()));
                num.add(Integer.parseInt(map.get("EVALUATE_0").toString()));
                maps.put("tjs", num);
            }else if(echartType.equals("2")){//饼状图

                List<Object> tjsPie = new ArrayList<Object>();
                Map<String, Object> ePie0 = new HashMap<String, Object>();
                ePie0.put("value", map.get("EVALUATE_3"));
                ePie0.put("name", "非常满意");
                tjsPie.add(ePie0);
                Map<String, Object> ePie1 = new HashMap<String, Object>();
                ePie1.put("value", map.get("EVALUATE_2"));
                ePie1.put("name", "满意");
                tjsPie.add(ePie1);
                Map<String, Object> ePie2 = new HashMap<String, Object>();
                ePie2.put("value", map.get("EVALUATE_1"));
                ePie2.put("name", "一般");
                tjsPie.add(ePie2);
                Map<String, Object> ePie3 = new HashMap<String, Object>();
                ePie3.put("value", map.get("EVALUATE_0"));
                ePie3.put("name", "不满意");
                tjsPie.add(ePie3);
                maps.put("tjs", tjsPie);
            }
            maps.put("name", name);
        }

        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    /**
     * 跳转到收件类型统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sjlxtjStatis")
    public ModelAndView sjlxtjStatis(HttpServletRequest request) {
        return new ModelAndView("statisreport/sjlxtjStatis");
    }
    /**
     * 
     * 描述收件类型统计
     * @author Rider Chen
     * @created 2016-3-10 下午05:00:32
     * @param request
     * @param response
     */
    @RequestMapping(params = "sjlxtjData")
    public void getSjlxtj(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticsService
                .getSjlxtjStatist(filter);
        String echartType = request.getParameter("echartType");
        Map<String, Object> maps = new HashMap<String,Object>();
        for (Map<String, Object> map : list) {
            maps.put("sumtjs", map.get("counts"));//办件总数
            List<String> name = new ArrayList<String>();//标题
            name.add("网上申请");
            name.add("窗口收件");
            name.add("省网办发送");
            if(echartType.equals("1")){//柱状图
                List<Integer> num = new ArrayList<Integer>();//统计数
                num.add(Integer.parseInt(map.get("wssq").toString()));               
                num.add(Integer.parseInt(map.get("cksj").toString()));
                num.add(Integer.parseInt(map.get("swbsf").toString()));
                maps.put("tjs", num);
            }else if(echartType.equals("2")){//饼状图

                List<Object> tjsPie = new ArrayList<Object>();
                Map<String, Object> ePie0 = new HashMap<String, Object>();
                ePie0.put("value", map.get("wssq"));
                ePie0.put("name", "网上申请");
                tjsPie.add(ePie0);
                Map<String, Object> ePie1 = new HashMap<String, Object>();
                ePie1.put("value", map.get("cksj"));
                ePie1.put("name", "窗口收件");
                tjsPie.add(ePie1);
                Map<String, Object> ePie2 = new HashMap<String, Object>();
                ePie2.put("value", map.get("swbsf"));
                ePie2.put("name", "省网办发送");
                tjsPie.add(ePie2);
                maps.put("tjs", tjsPie);
            }
            maps.put("name", name);
        }

        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    
    /**
     * 跳转到收件按小时统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sjaxstjStatis")
    public ModelAndView sjaxstjStatis(HttpServletRequest request) {
        String eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        request.setAttribute("eTime", eTime);
        request.setAttribute("sTime", sTime);
        return new ModelAndView("statisreport/sjaxstjStatis");
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年3月10日 下午3:39:41
     * @param request
     * @param response
     */
    @RequestMapping(params = "sjaxstjData")
    public void sjaxstjData(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> maps = new HashMap<String,Object>();
        String sTime = request.getParameter("Q_T.CREATE_TIME_>=");
        String eTime = request.getParameter("Q_T.CREATE_TIME_<=");
        String echartType = request.getParameter("echartType");
        int sumtjs = 0;
        if(StringUtils.isEmpty(sTime)||StringUtils.isEmpty(eTime)){
            eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        }
        sTime = sTime + " 00:00:00";
        eTime = eTime + " 23:59:59";
        int eCount = executionService.getCountByHour(sTime,eTime,"08","2");
        int nCount = executionService.getCountByHour(sTime,eTime,"09","2");
        int tCount = executionService.getCountByHour(sTime,eTime,"10","2");
        int elCount = executionService.getCountByHour(sTime,eTime,"11","2");
        int twCount = executionService.getCountByHour(sTime,eTime,"12","2");
        int foCount = executionService.getCountByHour(sTime,eTime,"14","2");
        int fiCount = executionService.getCountByHour(sTime,eTime,"15","2");
        int siCount = executionService.getCountByHour(sTime,eTime,"16","2");
        int seCount = executionService.getCountByHour(sTime,eTime,"17","2");
        int[] tjsBar = {eCount,nCount,tCount,elCount,twCount,foCount,fiCount,siCount,seCount};
        List<Object> tjsPie = new ArrayList<Object>();
        sumtjs = eCount+nCount+tCount+elCount+twCount+foCount+fiCount+siCount+seCount;
        maps.put("sumtjs", sumtjs);
        List<String> tjsj = new ArrayList<String>();
        tjsj.add("08点");
        tjsj.add("09点");
        tjsj.add("10点");
        tjsj.add("11点");
        tjsj.add("12点");
        tjsj.add("14点");
        tjsj.add("15点");
        tjsj.add("16点");
        tjsj.add("17点");
        maps.put("tjsj", tjsj);
        if(echartType.equals("1")){
            maps.put("tjs", tjsBar);
        }else if(echartType.equals("2")){
            for (int i = 0; i < 9; i++) {
                Map<String, Object> ePie = new HashMap<String, Object>();
                ePie.put("value", tjsBar[i]);
                ePie.put("name",tjsj.get(i));
                tjsPie.add(ePie);
            }
            maps.put("tjs", tjsPie);
        }
        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    /**
     * 跳转当场办结效率统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "dcbjxltjStatis")
    public ModelAndView dcbjxltjStatis(HttpServletRequest request) {
        String eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        request.setAttribute("eTime", eTime);
        request.setAttribute("sTime", sTime);
        return new ModelAndView("statisreport/dcbjxltjStatis");
    }
    /**
     * 
     * 描述  当场办结效率统计
     * @author Faker Li
     * @created 2016年3月11日 上午9:49:37
     * @param request
     * @param response
     */
    @RequestMapping(params = "dcbjxltjjData")
    public void dcbjxltjjData(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> maps = new HashMap<String,Object>();
        String sTime = request.getParameter("Q_T.CREATE_TIME_>=");
        String eTime = request.getParameter("Q_T.CREATE_TIME_<=");
        String echartType = request.getParameter("echartType");
        double svgtjs = 0.0;
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
        if(StringUtils.isEmpty(sTime)||StringUtils.isEmpty(eTime)){
            eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
            sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        }
        List<Map<String, Object>> list = workdayService.findWorkDayByseTime(sTime,eTime);
        if(list!=null){
            List<String> tjsj = new ArrayList<String>();
            double[] tjsBar = new double[list.size()];
            List<Object> tjsPie = new ArrayList<Object>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> e = list.get(i);
                int dtsjs = executionService.getSjsByTime((String)e.get("W_DATE"),"2");
                int dcbjs = statisticalReportService.getBjsByTime((String)e.get("W_DATE"),"2");
                tjsj.add((String)e.get("W_DATE"));
                
                double djl = 0.0;
                if(dtsjs!=0){
                    djl = Double.parseDouble(df.format((dcbjs * 100.0) / dtsjs));
                }
                svgtjs += djl;
                if(echartType.equals("1")){
                    tjsBar[i] = djl;
                    maps.put("tjs", tjsBar);
                }else if(echartType.equals("2")){
                    Map<String, Object> ePie = new HashMap<String, Object>();
                    ePie.put("value", djl);
                    ePie.put("name", (String)e.get("W_DATE"));
                    tjsPie.add(ePie);
                    maps.put("tjs", tjsPie);
                }
            }
            maps.put("sumtjs", df.format(svgtjs/list.size()));
            maps.put("tjsj", tjsj);
        }
        maps.put("echartType", echartType);
        String json = JSON.toJSONString(maps);
        this.setJsonString(json, response);
    }
    /**
     * 跳转办件提速统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bjtstj")
    public ModelAndView bjtstjStatis(HttpServletRequest request) {
        String eTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String sTime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -6),"yyyy-MM-dd");
        request.setAttribute("eTime", eTime);
        request.setAttribute("sTime", sTime);
        return new ModelAndView("statisreport/bjtstjStatis");
    }
    /**
     * 
     * 描述 获取提速列表
     * @author Faker Li
     * @created 2016年3月11日 下午4:19:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "bjtstjData")
    public void bjtstjData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = statisticalReportService.findBySqlFilter(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
}
