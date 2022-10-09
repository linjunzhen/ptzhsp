/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import java.util.Date;
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
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.zzhy.service.DomesticControllerService;
import net.evecom.platform.zzhy.service.ForeignControllerService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  外资企业登记信息Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/foreignControllerController")
public class ForeignControllerController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ForeignControllerController.class);
    /**
     * 引入Service
     */
    @Resource
    private ForeignControllerService foreignControllerService;
    /**
     * 引入Service
     */
    @Resource
    private DomesticControllerService domesticControllerService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        foreignControllerService.remove("T_COMMERCIAL_COMPANY","COMPANY_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 外资企业登记信息记录",SysLogService.OPERATE_TYPE_DEL);
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  foreignController = foreignControllerService.getByJdbc("T_COMMERCIAL_COMPANY",
                    new String[]{"COMPANY_ID"},new Object[]{entityId});
            request.setAttribute("foreignController", foreignController);
        }
        return new ModelAndView("zzhy/foreignController/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("COMPANY_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = foreignControllerService.saveOrUpdate(variables, "T_COMMERCIAL_COMPANY", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 外资企业登记信息记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 外资企业登记信息记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDiv")
    public ModelAndView refreshGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/foreign/gdxxDiv");
    } 
    /**
     * 跳转到董事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshDsxxDiv")
    public ModelAndView refreshDsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String directorJob = request.getParameter("directorJob");
        request.setAttribute("directorJob", directorJob);
        String directorGenerationMode = request.getParameter("directorGenerationMode");
        request.setAttribute("directorGenerationMode", directorGenerationMode);
        return new ModelAndView("website/applyforms/foreign/dsxxDiv");
    }
    /**
     * 跳转到监事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJsxxDiv")
    public ModelAndView refreshJsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/foreign/jsxxDiv");
    }
    /**
     * 跳转到经理信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJlxxDiv")
    public ModelAndView refreshJlxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/foreign/jlxxDiv");
    }
    
    /**
     * 跳转到外方投资者信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJwtzzxxDiv")
    public ModelAndView refreshJwtzzxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String companySettype = request.getParameter("companySettype");
        request.setAttribute("companySettype", companySettype);
        String num = request.getParameter("num");
        request.setAttribute("num", num);
        return new ModelAndView("website/applyforms/foreign/jwtzzxxDiv");
    }
    
    /**
     * 跳转到外方投资者信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshZftzzxxDiv")
    public ModelAndView refreshZftzzxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String companySettype = request.getParameter("companySettype");
        request.setAttribute("companySettype", companySettype);
        String num = request.getParameter("num");
        request.setAttribute("num", num);
        return new ModelAndView("website/applyforms/foreign/zftzzxxDiv");
    }
    
    /**
     * 跳转到外方投资者信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshAttorneyDiv")
    public ModelAndView refreshAttorneyDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/foreign/attorneyDiv");
    }
    
    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshQysjkzrxxDiv")
    public ModelAndView refreshQysjkzrxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/foreign/qysjkzrDiv");
    } 
}

