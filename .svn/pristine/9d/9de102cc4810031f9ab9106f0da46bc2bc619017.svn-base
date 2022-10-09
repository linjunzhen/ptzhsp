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
import net.evecom.platform.zzhy.service.SsnzqybaService;
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
 * 描述 商事内资企业备案Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/ssnzqybaController")
public class SsnzqybaController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SsnzqybaController.class);
    /**
     * 引入Service
     */
    @Resource
    private SsnzqybaService ssnzqybaService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        ssnzqybaService.remove("T_COMMERCIAL_SSNZQYBA", "COMPANY_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 商事内资企业备案记录", SysLogService.OPERATE_TYPE_DEL);
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
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> ssnzqyba = ssnzqybaService.getByJdbc("T_COMMERCIAL_SSNZQYBA",
                    new String[] { "COMPANY_ID" }, new Object[] { entityId });
            request.setAttribute("ssnzqyba", ssnzqyba);
        }
        return new ModelAndView("zzhy/ssnzqyba/info");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("COMPANY_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = ssnzqybaService.saveOrUpdate(variables, "T_COMMERCIAL_SSNZQYBA", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 商事内资企业备案记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 商事内资企业备案记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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
        String ssdjzw = request.getParameter("ssdjzw");
        String isClose = request.getParameter("isClose");
        request.setAttribute("ssdjzw", ssdjzw);
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/dsxxDiv");
    }

    /**
     * 跳转到旧董事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshOldDsxxDiv")
    public ModelAndView refreshOldDsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String ssdjzw = request.getParameter("ssdjzw");
        String isClose = request.getParameter("isClose");
        request.setAttribute("ssdjzw", ssdjzw);
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/olddsxxDiv");
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
        // 是否显示删除键
        String isClose = request.getParameter("isClose");
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/jsxxDiv");
    }

    /**
     * 跳转到旧监事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshOldJsxxDiv")
    public ModelAndView refreshOldJsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        // 是否显示删除键
        String isClose = request.getParameter("isClose");
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/oldjsxxDiv");
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
        // 是否显示删除键
        String isClose = request.getParameter("isClose");
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/jlxxDiv");
    }

    /**
     * 跳转到旧经理信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshOldJlxxDiv")
    public ModelAndView refreshOldJlxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        // 是否显示删除键
        String isClose = request.getParameter("isClose");
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/oldjlxxDiv");
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
        String isClose = request.getParameter("isClose");
        request.setAttribute("currentTime", currentTime);
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/gdxxDiv");
    }

    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/initGdxxDiv")
    public ModelAndView initGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        String isClose = request.getParameter("isClose");
        String holder = request.getParameter("holder");
        List<Map<String, Object>> holderList = null;
        if (StringUtils.isNotEmpty(holder)) {
            holderList = (List<Map<String, Object>>) JSON.parse(holder);
        }
        request.setAttribute("holderList", holderList);
        request.setAttribute("currentTime", currentTime);
        request.setAttribute("isClose", isClose);
        return new ModelAndView("website/applyforms/ssqcwb/ssnzqyba/initGdxxDiv");
    }
}
