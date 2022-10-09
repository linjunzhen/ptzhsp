/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.ptwg.service.SearchService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-3-1 上午8:45:12
 */
@Controller
@RequestMapping("/searchController")
public class SearchController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SearchController.class);
    /**
     * searchService
     */
    @Resource
    private SearchService searchService;
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-3-1 上午8:48:46
     * @param request
     * @param response
     */
    @RequestMapping("/loadItemTypeData")
    public void loadItemTypeData(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String typeCode = request.getParameter("typeCode");
        List<Map<String,Object>> list;
        list = searchService.getItemTypeList(typeCode);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-3-1 上午8:48:46
     * @param request
     * @param response
     */
    @RequestMapping("/loadItemData")
    public void loadItemData(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        SqlFilter filter = new SqlFilter(request);
        filter.setPagingBean(pb);
        List<Map<String,Object>> list;
        list = searchService.getNameSearchList(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-3-1 上午8:48:46
     * @param request
     * @param response
     */
    @RequestMapping("/loadTypeItemData")
    public void loadTypeItemData(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        SqlFilter filter = new SqlFilter(request);
        filter.setPagingBean(pb);
        List<Map<String,Object>> list;
        if(filter.getQueryParams().get("Q_t.TYPE_ID_EQ")!=null){
            filter.addFilter("Q_t.TYPE_ID_=", filter.getQueryParams().get("Q_t.TYPE_ID_EQ").toString());
            filter.removeFilter("Q_t.TYPE_ID_EQ");
        }
        list = searchService.getTypeSearchList(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-3-1 上午8:48:46
     * @param request
     * @param response
     */
    @RequestMapping("/loadTopItemData")
    public void loadTopItemData(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        List<Map<String,Object>> list = searchService.getTopItemList(page, rows);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 个人关心事项
     * @author Danto Huang
     * @created 2017-5-17 上午10:13:31
     * @param request
     * @param response
     */
    @RequestMapping("/loadPersonalPrefer")
    public void loadPersonalPrefer(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String userId = request.getParameter("userId");

        List<Map<String,Object>> list = searchService.getPersonalPrefer(page, rows, userId);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-5-24 上午11:11:06
     * @param request
     * @param response
     */
    @RequestMapping("/loadAlsoDoneItme")
    public void loadAlsoDoneItme(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        SqlFilter filter = new SqlFilter(request);
        filter.setPagingBean(pb);
        List<Map<String,Object>> list;
        
        list = searchService.getAlsoDoneItme(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 我的足迹
     * @author Danto Huang
     * @created 2017年7月3日 下午4:08:06
     * @param request
     * @param response
     */
    @RequestMapping("/loadMyTrack")
    public void loadMyTrack(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String userCode = request.getParameter("userCode");
        String page = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
        String rows = request.getParameter("rows") == null ? "5" : request.getParameter("rows");
        List<Map<String,Object>> list;
        
        list = searchService.getMyTrack(page,rows,userCode);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);        
    }
    
    /**
     * 
     * 描述    办事指南
     * @author Danto Huang
     * @created 2018年9月7日 上午9:52:42
     * @param request
     * @param response
     */
    @RequestMapping("/loadWorkGuide")
    public void loadWorkGuide(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String itemCode = request.getParameter("item_code");
        Map<String,Object> guideInfo = searchService.findGuideByItemCode(itemCode);
        String json = JSON.toJSONString(guideInfo);
        this.setJsonString(json, response);  
    }
}
