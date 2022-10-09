/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CommonOpinionService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 描述 常用意见Controller
 * 
 * @author Derek Zhang
 * @created 2015年9月22日 上午11:21:39
 */
@Controller
@RequestMapping("/commonOpinionController")
public class CommonOpinionController extends BaseController {
    /**
     * log4J声明
     */
    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(CommonOpinionController.class);
    /**
     * 引入Service
     */
    @Resource
    private CommonOpinionService commonOpinionService;

    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        //String entityId = request.getParameter("entityId");
        // if (StringUtils.isNotEmpty(entityId) &&
        // !entityId.equals("undefined")) {
        // @SuppressWarnings("unchecked")
        // Map<String, Object> serviceItem =
        // serialNumberService.getByJdbc("T_WSBS_SERIALNUMBER",
        // new String[] { "SERIAL_ID" }, new Object[] { entityId });
        // request.setAttribute("serialNumber", serviceItem);
        // }
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        List<Map<String, Object>> list = dictionaryService.findByTypeCode("SerialParameter");
        request.setAttribute("serialParam", list);
        return new ModelAndView("wsbs/serialnumber/serialNumberInfo");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveCommonOpinion")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String entityId = request.getParameter("OPINION_ID");
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("USER_ID", sysUser.getUserId());
        if (variables.get("OPINION_CONTENT") != null) {
            variables.put("OPINION_CONTENT", ((String) variables.get("OPINION_CONTENT")).trim());
        }
        if (commonOpinionService.isExist(variables)) {
            j.setMsg("本条常用意见已存在！");
            j.setSuccess(false);
        } else {
            String opinionId = this.commonOpinionService.saveOrUpdate(variables, "T_WSBS_OPINION", entityId);
            if (StringUtils.isNotEmpty(opinionId)) {
                sysLogService.saveLog("修改了ID为[" + entityId + "]的常用审核审批意见记录", SysLogService.OPERATE_TYPE_EDIT);
            } else {
                sysLogService.saveLog("新增了ID为[" + opinionId + "]的常用审核审批意见记录", SysLogService.OPERATE_TYPE_ADD);
            }
            j.setJsonString(opinionId);
            j.setMsg("保存成功");
        }
        return j;
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "removeCommonOpinion")
    @ResponseBody
    public AjaxJson deleteCommonOpinion(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("USER_ID", sysUser.getUserId());
        if (variables.get("OPINION_CONTENT") != null) {
            variables.put("OPINION_CONTENT", ((String) variables.get("OPINION_CONTENT")).trim());
        }
        if (!commonOpinionService.isExist(variables)) {
            j.setMsg("本条常用意见已删除！");
            j.setSuccess(false);
        } else {
            commonOpinionService.remove("T_WSBS_OPINION", "OPINION_CONTENT",
                    ((String) variables.get("OPINION_CONTENT")).split(","));
            sysLogService
                    .saveLog("删除了常用意见[" + variables.get("OPINION_CONTENT") + "]记录", SysLogService.OPERATE_TYPE_DEL);
            j.setMsg("删除成功！");
        }
        return j;
    }

    /**
     * 
     * 
     * 加载编号选择器信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "load")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        //SqlFilter filter = new SqlFilter(request);
        String defaultEmpty = request.getParameter("defaultEmpty");
//        List<Map<String, Object>> list ;// serialNumberService.findSerialNumbers(filter);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        for (Map<String, Object> map : list) {
//            map.put("PINYIN", StringUtil.getPingYin((String) map.get("SERIAL_NAME")));
//        }
        if (StringUtils.isNotEmpty(defaultEmpty)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("SERIAL_ID", "");
            map.put("SERIAL_NAME", "请选择编号配置");
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 序号选择器
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String serialNumberIds = request.getParameter("serialNumberIds");
        String serialNumberNames = request.getParameter("serialNumberNames");
        try {
            serialNumberNames = new String(serialNumberNames.getBytes("iso-8859-1"), "utf-8");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("serialNumberIds", serialNumberIds);
        request.setAttribute("serialNumberNames", serialNumberNames);
        return new ModelAndView("wsbs/serialnumber/serialNumberSelector");
    }

}
