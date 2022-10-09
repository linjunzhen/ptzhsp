/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.WebUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.WinSignService;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述 窗口签到处理
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年07月05日 下午3:14:27
 */
@Controller
@RequestMapping("/winSignController")
public class WinSignController extends BaseController {
    /**
     * serviceItemService
     */
    @Resource
    private WinSignService winSignService;

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "signList")
    public ModelAndView signList(HttpServletRequest request) {
        return new ModelAndView("bsfw/winsign/winsignlist");
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
        SqlFilter sqlFilter = new SqlFilter(request);
        Object oLoginTimeBegin = sqlFilter.getQueryParams().get("Q_T.LOGIN_TIME_>=");
        Object oLoginTimeEnd = sqlFilter.getQueryParams().get("Q_T.LOGIN_TIME_<=");
        String loginTimeBegin = (oLoginTimeBegin == null ? "" : "" + oLoginTimeBegin);
        String loginTimeEnd = (oLoginTimeEnd == null ? "" : "" + oLoginTimeEnd);
        SysUser curUser = AppUtil.getLoginUser();
        String s = curUser.getResKeys();
        String curUserCode = "";
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY) && s.indexOf("statisSign") == -1) {
            curUserCode = curUser.getUsername();
        }
        sqlFilter.removeFilter("Q_T.LOGIN_TIME_>=");
        sqlFilter.removeFilter("Q_T.LOGIN_TIME_<=");
        sqlFilter.addSorted("T.LOGIN_TIME", "DESC");
        List<Map<String, Object>> list = this.winSignService.findBySqlFilter(sqlFilter, loginTimeBegin,
                loginTimeEnd, curUserCode, null);
        this.setListToJsonString(sqlFilter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showWinSign")
    public ModelAndView showWinSign(HttpServletRequest request) {
        return new ModelAndView("bsfw/winsign/winsign");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "updateWinSign")
    @ResponseBody
    public AjaxJson updateWinSign(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        SysUser curUser = AppUtil.getLoginUser();
        String userWinIp = (String) request.getSession().getAttribute("userWinIp");
        Map<String, Object> returnMap = this.winSignService.doSign(curUser, userWinIp, WebUtil.getIpAddr(request));
        if (returnMap == null || returnMap.get("saveSta") == null || returnMap.get("saveSta").equals("-1")) {
            j.setSuccess(false);
            j.setMsg("签到操作失败，请联系系统管理员！");
        } else {
            request.getSession().setAttribute("loginTime",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(returnMap.get("LOGIN_TIME")));
            j.setSuccess(true);
        }
        return j;
    }

}
