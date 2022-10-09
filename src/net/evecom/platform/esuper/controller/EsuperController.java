/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.controller;

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
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.esuper.service.EsuperService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 描述 监察预警反馈功能controller
 * 
 * @author Derek Zhang
 * @created 2016年3月1日 下午4:12:47
 */
@Controller
@RequestMapping("/esuperController")
public class EsuperController extends BaseController {
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * userInfoService
     */
    @Resource
    private EsuperService esuperService;

    /**
     * 描述 进入预警反馈页面
     * 
     * @author Derek Zhang
     * @created 2016年3月2日 下午2:59:17
     * @param request
     * @return
     */
    @RequestMapping(params = "esupersView")
    public ModelAndView esupersView(HttpServletRequest request) {
        return new ModelAndView("esupers/esuper/esuperList");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.FB_STATUS", "asc");
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = esuperService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 显示预警反馈信息
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("superId");
        String businessId = request.getParameter("businessId");
        String operType = request.getParameter("operType");
        Map<String, Object> esuperDataMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(operType) && !operType.equals("undefined")) {
            if (operType.equals("detail")) {
                esuperDataMap = esuperService.getByJdbc("T_BSFW_ESUPER_INFO", new String[] { "SUPER_ID" },
                        new Object[] { entityId });
                String replyLimit = DateTimeUtil.dateToStr(
                        (DateTimeUtil.format((String) esuperDataMap.get("REPLY_LIMIT"), "yyyyMMdd")), "yyyy-MM-dd");
                esuperDataMap.put("_REPLY_LIMIT", replyLimit);
                request.setAttribute("esuperInfo", esuperDataMap);
                return new ModelAndView("esupers/esuper/esuperDetail");
            } else if (operType.equals("update")) {
                SysUser sysUser = AppUtil.getLoginUser();
                request.setAttribute("userName", sysUser.getFullname());
                if (entityId.indexOf(",") > 0) {
                    request.setAttribute("entityId", entityId);
                    request.setAttribute("businessIds", businessId);
                    return new ModelAndView("esupers/esuper/esuperBatch");
                } else {
                    esuperDataMap = esuperService.getByJdbc("T_BSFW_ESUPER_INFO", new String[] { "SUPER_ID" },
                            new Object[] { entityId });
                    request.setAttribute("esuperInfo", esuperDataMap);
                    return new ModelAndView("esupers/esuper/esuperInfo");
                }
            }
        }
        esuperDataMap = esuperService.getByJdbc("T_BSFW_ESUPER_INFO", new String[] { "SUPER_ID" },
                new Object[] { entityId });
        request.setAttribute("operType", operType);
        return new ModelAndView("esupers/esuper/esuperDetail");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SUPER_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("FB_STATUS", "1");
        variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        this.esuperService.saveOrUpdate(variables, "T_BSFW_ESUPER_INFO", entityId);
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
    @RequestMapping(params = "saveOrUpdateBatch")
    @ResponseBody
    public AjaxJson saveOrUpdateBatch(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        String userName = request.getParameter("REPLY_USER");
        String replyInfo = request.getParameter("REPLY_INFO");
        String[] superids = entityId.split(",");
        for (String superid : superids) {
            if (StringUtils.isNotEmpty(superid) && !StringUtils.isBlank(superid) && !superid.equals("undefined")) {
                Map<String, Object> variables = esuperService.getByJdbc("T_BSFW_ESUPER_INFO",
                        new String[] { "SUPER_ID" }, new Object[] { superid });
                variables.put("FB_STATUS", "1");
                variables.put("REPLY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                variables.put("REPLY_USER", userName);
                variables.put("REPLY_INFO", replyInfo);
                this.esuperService.saveOrUpdate(variables, "T_BSFW_ESUPER_INFO", superid);
            }
        }
        j.setMsg("保存成功");
        return j;
    }

}
