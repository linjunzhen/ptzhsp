/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.evaluate.service.EvaluateService;

/**
 * 描述 好差评管理系统Controller
 * 
 * @author Luffy Cai
 * @version 1.0
 * @created 2020/07/21 19:16:00
 */
@Controller
@RequestMapping("/evaluateController")
public class EvaluateController extends BaseController {


    /**
     * 引入 EvaluateService
     */
    @Resource
    private EvaluateService evaluateService;

    /**
     * 
     * @Description 好差评评价上报数据请求转发
     * @author Luffy Cai
     * @date 2020年8月5日
     * @param request
     * @param response void
     */
    @RequestMapping("/getSaveEvaluation")
    public void getSaveEvaluation(HttpServletRequest request, HttpServletResponse response) {
        evaluateService.pushEvaluationData();
        evaluateService.pushEvaluationDataByKey();
        evaluateService.pushEvaluationDataByExe();
        evaluateService.pushEvaluationDataPLJ();
        evaluateService.evaluateTheDefaultHighPraise();
    }

    /**
     * 
     * @Description 好差评评价上报数据请求转发
     * @author Luffy Cai
     * @date 2020年8月5日
     * @param request
     * @param response void
     */
    @RequestMapping("/pushBadEvaluation")
    public void pushBadEvaluation(HttpServletRequest request, HttpServletResponse response) {
        evaluateService.pushBadEvaluationData();
        evaluateService.pushBadEvaluationDataByKey();
        evaluateService.pushBadEvaluationDataByExe();
    }    
    
    
    /**
     * 
     * @Description 推送平板评价数据
     * @author Luffy Cai
     * @date 2020年8月5日
     * @param request
     * @param response void
     */
    @RequestMapping("/pushEvaluationData")
    public void pushEvaluationData(HttpServletRequest request, HttpServletResponse response) {
        evaluateService.pushEvaluationData();
    }

    /**
     * 
     * @Description 推送按键器评价数据
     * @author Luffy Cai
     * @date 2020年8月5日
     * @param request
     * @param response void
     */
    @RequestMapping("/pushEvaluationDataByKey")
    public void pushEvaluationDataByKey(HttpServletRequest request, HttpServletResponse response) {
        evaluateService.pushEvaluationDataByKey();
    }

    /**
     * 
     * @Description 获取评价统计数据
     * @author Luffy Cai
     * @date 2020年9月27日
     * @param request
     * @return Map<String,Object>
     */
    @RequestMapping("/getEvaluationStatistics")
    @ResponseBody
    public Map<String, Object> getEvaluationStatistics(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String beginTime = request.getParameter("beginTime") == null ? ""
                : request.getParameter("beginTime").toString();
        String endTime = request.getParameter("endTime") == null ? "" : request.getParameter("endTime").toString();
        if (!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {
            Map<String, Object> map = evaluateService.getEvaluationStatistics(beginTime, endTime);
            map.put("improve", "100%");
            String createTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            map.put("createTime", createTime);
            result.put("success", true);
            result.put("data", map);
            result.put("msg", "请求成功！");
        } else {
            result.put("success", false);
            result.put("msg", "缺少必要参数！");
        }
        return result;
    }

    /**
     * 
     * @Description 第三方系统推送评价数据
     * @author Luffy Cai
     * @date 2020年9月27日
     * @param request
     * @return Map<String,Object>
     */
    @RequestMapping("/pushEvaluationDataByThird")
    @ResponseBody
    public Map<String, Object> pushEvaluationDataByThird(HttpServletRequest request) {
        Map<String, Object> map = BeanUtil.getMapFromRequest(request);
        // 判断是否缺少必要参数
        Map<String, Object> checkParams = checkParamsParameter(map);
        Map<String, Object> result = new HashMap<String, Object>();
        if ((boolean) checkParams.get("success")) {
            if (StringUtils.isEmpty(map.get("handleUserName"))) {
                map.put("handleUserName", " ");
            }
            if (StringUtils.isEmpty(map.get("handleUserPageType"))) {
                map.put("handleUserPageType", " ");
            }
            if (StringUtils.isEmpty(map.get("handleUserPageCode"))) {
                map.put("handleUserPageCode", " ");
            }
            if (StringUtils.isEmpty(map.get("resultDate"))) {
                map.put("resultDate", " ");
            }
            String alternate = map.get("alternate").toString();
            if (alternate.equals("1") || alternate.equals("2")) {
                map.put("writingevalua", "办事效率不高");
            }
            result = evaluateService.pushEvaluateDataByThird(map);
        } else {
            result.put("success", false);
            result.put("msg", checkParams.get("msg"));
        }
        return result;
    }

    /**
     * 
     * @Description 判断是否缺少必要参数
     * @author Luffy Cai
     * @date 2020年9月27日
     * @param map
     * @return Map<String,Object>
     */
    public Map<String, Object> checkParamsParameter(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] list = { "EXE_ID", "itemCode", "businessCode", "businessName", "proStatus", "regionCode", "acceptDate",
                "userProp", "userName", "userPageType", "certKey", "serviceTime", "evaluateType", "projectName",
                "nodeName", "contactMobile", "pf", "type", "alternate", "assessTime" };
        result.put("success", true);
        for (int i = 0; i < list.length; i++) {
            if (StringUtils.isEmpty(map.get(list[i]))) {
                result.put("success", false);
                result.put("msg", "缺少必要参数:" + list[i]);
                break;
            }
        }
        if (result.get("success").toString().equals("true") && map.get("proStatus").toString().equals("3")) {
            if (StringUtils.isEmpty(map.get("resultDate"))) {
                result.put("success", false);
                result.put("msg", "缺少必要参数:" + "resultDate");
            }
        }
        return result;
    }

    /**
     * 
     * @Description 好差评评价补报数据请求转发
     * @author Luffy Cai
     * @date 2021年12月15日
     * @param request
     * @param response void
     */
    @RequestMapping("/getEvaluationSupplement")
    public void getEvaluationSupplement(HttpServletRequest request, HttpServletResponse response) {
        evaluateService.getEvaluationSupplement();
    }  
    
    /**
     * 
     * @Description 好差评评价补报数据请求转发
     * @author Luffy Cai
     * @date 2021年12月15日
     * @param request
     * @param response void
     */
    @RequestMapping("/getEvaluationSupplementAugust")
    public void getEvaluationSupplementAugust(HttpServletRequest request, HttpServletResponse response) {
        evaluateService.getEvaluationSupplementAugust();
    }     
}
