/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysEhcacheService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 缓存管理Controller
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/sysEhcacheController")
public class SysEhcacheController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SysEhcacheController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysEhcacheService sysEhcacheService;
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
        sysEhcacheService.remove("T_MSJW_SYSTEM_EHCACHE", "EHCACHE_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 缓存管理记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "SysEhcacheView")
    public ModelAndView sysUserView(HttpServletRequest request) {
        return new ModelAndView("system/ehcache/SysEhcacheView");
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
        Map<String, Object> sysEhcache = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            sysEhcache = sysEhcacheService.getByJdbc("T_MSJW_SYSTEM_EHCACHE", new String[] { "EHCACHE_ID" },
                    new Object[] { entityId });
            if (StringUtils.isNotEmpty((String) sysEhcache.get("EHCACHE_DEL_KEY"))) {
                List<Map<String, Object>> list = sysEhcacheService.findByDelEhcacheKeys((String) sysEhcache
                        .get("EHCACHE_DEL_KEY"));
                StringBuffer edelname=new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    if (i > 0) {
                        edelname.append(",");
                    }
                    edelname.append(list.get(i).get("EHCACHE_NAME"));
                }
                sysEhcache.put("EHCACHE_DEL_NAME", edelname.toString());
            }
        } else {
            sysEhcache.put("EHCACHE_STATUE", "1");
        }
        request.setAttribute("sysEhcache", sysEhcache);
        return new ModelAndView("system/ehcache/info");
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
        String entityId = request.getParameter("EHCACHE_ID");
        String statue = request.getParameter("EHCACHE_STATUE");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(statue) && statue.equals("1")) {
            variables.put("EHCACHE_KEY", StringUtil.getMd5Encode((String) variables.get("EHCACHE_CLASS_NAME")));
        } else {
            variables.put("EHCACHE_KEY", "");
        }
        String recordId = sysEhcacheService.saveOrUpdate(variables, "T_MSJW_SYSTEM_EHCACHE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 缓存管理记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 缓存管理记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
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
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = sysEhcacheService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String delEhcacheKeys = request.getParameter("delEhcacheKeys");
        String delEhcacheNames = request.getParameter("delEhcacheNames");
        if (StringUtils.isNotEmpty(delEhcacheKeys) && !delEhcacheKeys.equals("undefined")) {
            request.setAttribute("delEhcacheKeys", delEhcacheKeys);
            request.setAttribute("delEhcacheNames", delEhcacheNames);
        }
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("system/ehcache/ehcacheSelector");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request, HttpServletResponse response) {
        String delEhcacheKeys = request.getParameter("delEhcacheKeys");
        List<Map<String, Object>> list = null;
        if (StringUtils.isNotEmpty(delEhcacheKeys)) {
            list = sysEhcacheService.findByDelEhcacheKeys(delEhcacheKeys);
        }
        if (list != null) {
            this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
        }
    }
}
