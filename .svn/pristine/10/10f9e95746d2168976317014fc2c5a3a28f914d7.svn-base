/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

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
import net.evecom.platform.bsfw.service.CyjbService;
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
 * 描述  产业奖补Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/cyjbController")
public class CyjbController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CyjbController.class);
    /**
     * 引入Service
     */
    @Resource
    private CyjbService cyjbService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
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
        cyjbService.remove("T_BSFW_PTJ_CYJB","CYJB_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 产业奖补记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  cyjb = cyjbService.getByJdbc("T_BSFW_PTJ_CYJB",
                    new String[]{"CYJB_ID"},new Object[]{entityId});
            request.setAttribute("cyjb", cyjb);
        }
        return new ModelAndView("bsfw/cyjb/info");
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
        String entityId = request.getParameter("CYJB_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = cyjbService.saveOrUpdate(variables, "T_BSFW_PTJ_CYJB", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 产业奖补记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 产业奖补记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveCyjb")
    @ResponseBody
    public AjaxJson saveCyjb(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String busRecordId = request.getParameter("YW_ID");
        String CyjbJson = request.getParameter("CyjbJson");
        String CYJB_SPJE = request.getParameter("CYJB_SPJE");
        String CYJB_SPDEPT_NAME = request.getParameter("CYJB_SPDEPT_NAME");
        String CYJB_CZSJ = request.getParameter("CYJB_CZSJ");
        String CYJB_SPDEPT_ID = request.getParameter("CYJB_SPDEPT_ID");
        Map<String, Object> variables = sysLogService.getByJdbc("T_BSFW_PTJINFO", new String[] { "YW_ID" },
                new String[] { busRecordId });
        if(null!=variables&&variables.size()>0){
            variables.put("CYJB_SPJE", CYJB_SPJE);
            variables.put("CYJB_SPDEPT_NAME", CYJB_SPDEPT_NAME);
            variables.put("CYJB_CZSJ", CYJB_CZSJ);
            variables.put("CYJB_SPDEPT_ID", CYJB_SPDEPT_ID);            
            sysLogService.saveOrUpdate(variables, "T_BSFW_PTJINFO", busRecordId);
        }
        if (StringUtils.isNotEmpty(CyjbJson)) {
            // 保存信息前，先删除
            sysLogService.remove("T_BSFW_PTJ_CYJB", new String[] { "YW_ID" }, new Object[] { busRecordId });
            List<Map> cyjbList = JSON.parseArray(CyjbJson, Map.class);
            for (int i = 0; i < cyjbList.size(); i++) {
                Map<String, Object> productCpxxMap = cyjbList.get(i);
                productCpxxMap.put("YW_ID", busRecordId);
                sysLogService.saveOrUpdate(productCpxxMap, "T_BSFW_PTJ_CYJB", "");
            }
        }
        if (StringUtils.isNotEmpty(busRecordId)) {
            sysLogService.saveLog("修改了ID为[" + busRecordId + "]的 产业奖补记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + busRecordId + "]的 产业奖补记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 产业奖补
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshCyjbDiv")
    public ModelAndView refreshTechnicalPersonnelDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/refreshCyjbDiv");
    }
}

