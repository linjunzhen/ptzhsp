/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.bsfw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-11-23 上午10:13:47
 */
@Controller
@RequestMapping("/commercialController")
public class CommercialController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CommercialController.class);
    /**
     * commercialService
     */
    @Resource
    private CommercialService commercialService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:15:58
     * @param request
     * @return
     */
    @RequestMapping(params="scopeSelector")
    public ModelAndView scopeSelector(HttpServletRequest request){
        String selected = request.getParameter("selected");
        request.setAttribute("selected", selected);
        return new ModelAndView("bsdt/applyform/scopeSelector");
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:15:58
     * @param request
     * @return
     */
    @RequestMapping(params="industrySelector")
    public ModelAndView industrySelector(HttpServletRequest request){
        String selected = request.getParameter("selected");
        String industryCode = request.getParameter("industryCode");
        request.setAttribute("selected", selected);
        request.setAttribute("industryCode", industryCode);
        return new ModelAndView("bsdt/applyform/industrySelector");
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:34:45
     * @param request
     * @param response
     */
    @RequestMapping("/loadIndustry")
    public void loadIndustry(HttpServletRequest request,HttpServletResponse response){
        String parentCode = request.getParameter("parentCode");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(parentCode)){
            list = commercialService.getIndustryByParentCode(parentCode);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("INDU_ID","");
        map.put("INDU_CODE","");
        map.put("REMARK","");
        map.put("INDU_NAME", "全部");
        list.add(0, map);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 下午2:57:22
     * @param request
     * @param response
     */
    @RequestMapping("/loadTZIndustry")
    public void loadTZIndustry(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String,Object>> list;
        filter.addFilter("Q_t.is_tzindustry_=", "1");
        list = commercialService.findTZIndustryBySqlFilter(filter);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("INDU_ID","");
        map.put("INDU_CODE","");
        map.put("REMARK","");
        map.put("INDU_NAME", "全部");
        list.add(0, map);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-1-6 上午9:13:53
     * @param request
     * @return
     */
    @RequestMapping(params="goFieldAudit")
    public ModelAndView goFieldAudit(HttpServletRequest request){
        String fieldName = request.getParameter("fieldName");
        String curNode = request.getParameter("curNode");
        String exeId = request.getParameter("exeId");
        String fieldId = request.getParameter("fieldId");
        String fieldtext = request.getParameter("fieldtext");
        String belongId = request.getParameter("belongId");
        request.setAttribute("belongId", belongId);
        request.setAttribute("fieldtext", fieldtext);
        request.setAttribute("fieldId", fieldId);
        request.setAttribute("fieldName", fieldName);
        request.setAttribute("curNode", curNode);
        request.setAttribute("exeId", exeId);
        return new ModelAndView("bsdt/applyform/fieldaduit/fieldAuditOpinion");
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-1-6 上午9:34:43
     * @param request
     * @return
     */
    @RequestMapping(params="saveFieldOpinion")
    @ResponseBody
    public AjaxJson saveFieldOpinion(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        this.commercialService.saveOrUpdate(variables, "T_COMMERCIAL_FIELDAUDIT", null);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-1-6 上午9:34:43
     * @param request
     * @return
     */
    @RequestMapping(params="deleteFieldOpinion")
    @ResponseBody
    public AjaxJson deleteFieldOpinion(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String fieldName = request.getParameter("fieldName");
        String curNode = request.getParameter("curNode");
        String exeId = request.getParameter("exeId");
        commercialService.updateField(exeId, fieldName, curNode);
        j.setMsg("删除意见成功");
        return j;
    }
    
    /**
     * 
     * 描述   表单字段审核记录
     * @author Danto Huang
     * @created 2017-1-9 下午2:09:31
     * @param request
     * @return
     */
    @RequestMapping(params="filedProblemView")
    public ModelAndView filedProblemView(HttpServletRequest request){
        String exeId= request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        return new ModelAndView("bsdt/applyform/fieldaduit/filedProblemView");
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-1-9 下午2:22:15
     * @param request
     * @param response
     */
    @RequestMapping(params="opinionDatagrid")
    public void opinionDatagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "asc");
        filter.addFilter("Q_t.CONFIRM_STATUS_=", "0");
        List<Map<String, Object>> list = commercialService.findFieldOpinion(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    /**
     * 
     * 描述   获取自贸区地址划分
     * @author Danto Huang
     * @created 2017-2-16 上午9:31:09
     * @param request
     * @param response
     */
    @RequestMapping(params = "getFatAddr")
    @ResponseBody
    public void getFatAddr(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> addrList =  dictionaryService.findByTypeCode("zhqhfdz");
        StringBuffer addrs=new StringBuffer();
        for (Map<String, Object> map : addrList) {
            if (addrs.length() > 0) {
                addrs.append(",");
            }
            addrs.append(map.get("DIC_CODE"));
        }
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("OPER_SUCCESS", true);
        variables.put("zhqhfdz", addrs);
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
}
