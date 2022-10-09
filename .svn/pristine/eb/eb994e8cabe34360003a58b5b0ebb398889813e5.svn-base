/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.jkzx.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.jkzx.service.JkzxService;
import net.evecom.platform.statis.service.StatisticsService;
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
 * 描述 监控中心Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2017年3月15日 09:26:34
 */
@Controller
@RequestMapping("/jkzxController")
public class JkzxController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(JkzxController.class);
    /**
     * 引入Service
     */
    @Resource
    private JkzxService jkzxService;
    /**
     * 
     */
    @Resource
    private StatisticsService statisticsService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    public static void main(String[] args) {
        //System.out.println(DateTimeUtil.getCurrentYear());
    }

    /**
     * 
     * 描述 今年各部门办件量统计
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryBmbjltj")
    public void queryBmbjltj(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        sqlFilter.addFilter("Q_T.CREATE_TIME_>=", DateTimeUtil.getCurrentYear() + "-01-01 00:00:00");
        sqlFilter.addFilter("Q_T.CREATE_TIME_<=", DateTimeUtil.getCurrentYear() + "-12-31 23:59:59");
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlBmbjltj(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 各部门事项入驻情况
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryBmsxrztjb")
    public void queryBmsxrztjb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlDepartItem(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 实时办件信息监测
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/querySsbjxxjc")
    public void querySsbjxxjc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlSsbjxx(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 窗口叫号信息
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryCkjhxx")
    public void queryCkjhxx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();

        sqlFilter.addFilter("Q_T.CREATE_TIME_>=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")+ " 00:00:00");
        sqlFilter.addFilter("Q_T.CREATE_TIME_<=", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + " 23:59:59");
        
        List<Map<String, Object>> list = jkzxService.findBySqlCkjhxx(sqlFilter, variables);
        if (null != list && list.size()>0) {
            for (Map<String, Object> map : list) {
                String TAKE_TIME = (String)map.get("TAKE_TIME");
                String FIRST_CALL_TIME = (String)map.get("FIRST_CALL_TIME");
                if(StringUtils.isNotEmpty(TAKE_TIME) && StringUtils.isNotEmpty(FIRST_CALL_TIME)){
                    map.put("WAIT_TIME", DateTimeUtil.getIntervalMinute(TAKE_TIME, FIRST_CALL_TIME));
                }
            }
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 今年窗口叫号量统计
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryCkjhl")
    public void queryCkjhl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        sqlFilter.addFilter("Q_T.CREATE_TIME_>=", DateTimeUtil.getCurrentYear() + "-01-01 00:00:00");
        sqlFilter.addFilter("Q_T.CREATE_TIME_<=", DateTimeUtil.getCurrentYear() + "-12-31 23:59:59");
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlCkjhl(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 各部门评价器满意度统计
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryBmpjmydtj")
    public void queryBmpjmydtj(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlBmpjmydtj(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 窗口评价器满意度统计
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午11:24:31
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryCkpjmydtj")
    public void queryCkpjmydtj(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlCkpjmydtj(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 审批事项统计
     * @author Rider Chen
     * @created 2017年4月6日 上午8:39:06
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/querySpsxtj")
    public void querySpsxtj(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findByEffiItemSqlFilter(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 逾期办结件明细表
     * @author Rider Chen
     * @created 2017年4月6日 上午8:39:06
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryYqbjmx")
    public void queryYqbjmx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.getYqbjmxStatist(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 办件类型统计
     * @author Rider Chen
     * @created 2017年4月6日 上午8:39:06
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryBjlx")
    public void queryBjlx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlBjlx(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 获取诉求信息
     * @author Rider Chen
     * @created 2017年5月8日 上午10:41:37
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryLetter")
    public void queryLetter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SqlFilter sqlFilter = new SqlFilter(request);
        
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        List<Map<String, Object>> list = jkzxService.findBySqlLetter(sqlFilter, variables);
        if (null != list && list.size()>0) {
            result.put("msg", "获取数据成功");
            result.put("success", true);
            result.put("list", list);
        } else {
            result.put("msg", "获取数据失败");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
}
