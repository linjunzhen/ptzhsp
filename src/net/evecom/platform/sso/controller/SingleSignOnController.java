/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sso.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WebUtil;
import net.evecom.core.web.interceptors.AuthInterceptor;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.sso.service.SingleSignOnService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.IpFilterConfigService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 单点登录Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/singleSignOnController")
public class SingleSignOnController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SingleSignOnController.class);
    /**
     * 引入Service
     */
    @Resource
    private SingleSignOnService singleSignOnService;
    /**
     * 引入service
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * ipFilterConfigService
     */
    @Resource
    private IpFilterConfigService ipFilterConfigService;

    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("sso/singleSignOn/list");
    }

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
        singleSignOnService.remove("T_SYSTEM_SINGLESIGNON", "SSO_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 单点登录记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> singleSignOn = singleSignOnService.getByJdbc("T_SYSTEM_SINGLESIGNON",
                    new String[] { "SSO_ID" }, new Object[] { entityId });
            request.setAttribute("singleSignOn", singleSignOn);
        }
        return new ModelAndView("sso/singleSignOn/info");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.SSO_ID", "DESC");
        List<Map<String, Object>> list = singleSignOnService.findBySqlFilter(filter, null);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
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
        String entityId = request.getParameter("SSO_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = singleSignOnService.saveOrUpdate(variables, "T_SYSTEM_SINGLESIGNON", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 单点登录记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 单点登录记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 描述 获取单点登录token
     * 
     * @author Rider Chen
     * @created 2021年6月10日 上午10:47:45
     * @param request
     * @param response
     */
    @RequestMapping("/getToken")
    public void getToken(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> info = null;
        String idAddress = BrowserUtils.getIpAddr(request);
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        if (StringUtils.isEmpty(userName)) {
            result.put("success", false);
            result.put("msg", "输入参数不合法,参数userName为空");
        } else if (StringUtils.isEmpty(pwd)) {
            result.put("success", false);
            result.put("msg", "输入参数不合法,参数pwd为空");
        } else {
            info = singleSignOnService.getByJdbc("T_SYSTEM_SINGLESIGNON", new String[] { "SSO_USERNAME", "SSO_PWD" },
                    new Object[] { userName, pwd });
            if (null != info && info.size() > 0) {
                String tokenTime = StringUtil.getString(info.get("SSO_TOKEN_TIME"));
                if (StringUtils.isNotEmpty(tokenTime)) {
                    String ssotoken = StringUtil.getString(info.get("SSO_TOKEN"));
                    long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                            DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    if (timeMinute < 120) {
                        result.put("success", true);
                        result.put("msg", "获取token成功");
                        result.put("token", ssotoken);
                    } else {
                        String token = StringUtil
                                .encryptMd5(userName + pwd + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss"));
                        info.put("SSO_TOKEN", token);
                        info.put("SSO_TOKEN_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        info.put("SSO_TOKEN_IP", idAddress);
                        singleSignOnService.saveOrUpdate(info, "T_SYSTEM_SINGLESIGNON", info.get("SSO_ID").toString());
                        result.put("success", true);
                        result.put("msg", "获取token成功");
                        result.put("token", token);
                    }
                } else {
                    String token = StringUtil
                            .encryptMd5(userName + pwd + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss"));
                    info.put("SSO_TOKEN", token);
                    info.put("SSO_TOKEN_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    info.put("SSO_TOKEN_IP", idAddress);
                    singleSignOnService.saveOrUpdate(info, "T_SYSTEM_SINGLESIGNON", info.get("SSO_ID").toString());
                    result.put("success", true);
                    result.put("msg", "获取token成功");
                    result.put("token", token);
                }
            } else {
                result.put("success", false);
                result.put("msg", "查询不到用户信息");
            }
            singleSignOnService.saveLog(info, variables, result, "获取TOKEN", 1);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 获取已登录用户信息
     * 
     * @author Rider Chen
     * @created 2021年6月10日 下午3:53:23
     * @param request
     * @param response
     */
    @RequestMapping("/getLoginUser")
    public ModelAndView getLoginUser(HttpServletRequest request) {
        String returnurl = request.getParameter("returnurl");
        String token = request.getParameter("token");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(returnurl)) {
            Map<String, Object> ssoInfo = sysUserService.getByJdbc("T_SYSTEM_SINGLESIGNON",
                    new String[] { "SSO_TOKEN" }, new Object[] { token });
            if (null != ssoInfo && ssoInfo.size() > 0) {
                String tokenTime = StringUtil.getString(ssoInfo.get("SSO_TOKEN_TIME"));
                long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                        DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if (timeMinute <= 120) {// token获取时间大于120分钟时拒绝访问
                    Map<String, Object> ssoUser = new HashMap<String, Object>();
                    String trustticket = UUIDGenerator.getUUID();
                    SysUser currentUser = AppUtil.getLoginUser();
                    if (null != currentUser) {
                        ssoUser.put("trustticket", trustticket);
                        ssoUser.put("user_id", currentUser.getUserId());
                        if (returnurl.indexOf("?") == -1) {
                            returnurl += "?success=true&code=000&trustticket=" + trustticket;
                        } else {
                            returnurl += "&success=true&code=000&trustticket=" + trustticket;
                        }
                        singleSignOnService.saveOrUpdate(ssoUser, "T_SYSTEM_SINGLESIGNON_USER", null);// 保存登录用户信息
                        result.put("returnurl", returnurl);
                        singleSignOnService.saveLog(ssoInfo, variables, result, "获取已登录用户信息", 4);
                        return new ModelAndView("redirect:" + returnurl);
                    }
                }
            }
            if (returnurl.indexOf("?") == -1) {
                returnurl += "?success=false&code=001";
            } else {
                returnurl += "&success=false&code=001";
            }
            return new ModelAndView("redirect:" + returnurl);
        }
        return new ModelAndView("redirect:/403filter.jsp");
    }

    /**
     * 
     * 描述 根据信任票据获取用户信息
     * 
     * @author Rider Chen
     * @created 2021年6月10日 下午3:24:53
     * @param request
     * @param response
     */
    @RequestMapping("/getUserToTrustTicket")
    public void getUserToTrustTicket(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> info = null;
        String trustticket = request.getParameter("trustticket");
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(trustticket)) {
            result.put("success", false);
            result.put("msg", "输入参数不合法,参数trustticket为空");
        } else if (StringUtils.isEmpty(token)) {
            result.put("success", false);
            result.put("msg", "输入参数不合法,参数token为空");
        } else {
            info = singleSignOnService.getByJdbc("T_SYSTEM_SINGLESIGNON", new String[] { "SSO_TOKEN" },
                    new Object[] { token });
            if (null != info && info.size() > 0) {
                String tokenTime = StringUtil.getString(info.get("SSO_TOKEN_TIME"));
                long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                        DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if (timeMinute > 120) {// token获取时间大于120分钟时拒绝访问
                    result.put("success", false);
                    result.put("msg", "TOKEN信息过期无效");
                } else {
                    Map<String, Object> ssoUser = singleSignOnService.getByJdbc("T_SYSTEM_SINGLESIGNON_USER",
                            new String[] { "TRUSTTICKET" }, new Object[] { trustticket });
                    if (null != ssoUser && ssoUser.size() > 0) {
                        String ctime = StringUtil.getString(ssoUser.get("CREATE_TIME"));
                        long usesTimeMinute = DateTimeUtil.getIntervalMinute(ctime,
                                DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        if (usesTimeMinute > 30) {// 信任票据生成大于30分钟时，票据无效
                            result.put("success", false);
                            result.put("msg", "信任票据过期无效");
                        } else {
                            String userId = StringUtil.getString(ssoUser.get("USER_ID"));
                            Map<String, Object> userInfo = singleSignOnService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                                    new String[] { "USER_ID" }, new Object[] { userId });

                            if (null != userInfo && userInfo.size() > 0) {
                                SysUser sysUser = sysUserService
                                        .getAllInfoByUsername(userInfo.get("USERNAME").toString());
                                sysUser.setLoginUserInfoJson("");
                                sysUser.setPassword("");
                                String userJson = JSON.toJSONString(sysUser);
                                result.put("success", true);
                                result.put("msg", "查询成功");
                                result.put("data", new Sm4Utils().encryptDataCBC(userJson));
                            } else {
                                result.put("success", false);
                                result.put("msg", "查询不到登录用户信息");
                            }
                        }
                    } else {
                        result.put("success", false);
                        result.put("msg", "查询不到信任票据信息");
                    }
                }

            } else {
                result.put("success", false);
                result.put("msg", "查询不到TOKEN信息");
            }
            singleSignOnService.saveLog(info, variables, result, "获取用户信息", 3);
        }

        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 退出系统页面跳转
     * 
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String returnurl = request.getParameter("returnurl");
        String token = request.getParameter("token");
        Map<String, Object> info = null;
        if (StringUtils.isNotEmpty(returnurl) && StringUtils.isNotEmpty(token)) {
            info = singleSignOnService.getByJdbc("T_SYSTEM_SINGLESIGNON", new String[] { "SSO_TOKEN" },
                    new Object[] { token });
            if (null != info && info.size() > 0) {
                String tokenTime = StringUtil.getString(info.get("SSO_TOKEN_TIME"));
                long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                        DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if (timeMinute <= 120) {
                    HttpSession session = AppUtil.getSession();
                    if (AuthInterceptor.sessionMap != null) {
                        if (AppUtil.getLoginUser() != null) {
                            AuthInterceptor.sessionMap.remove(AppUtil.getLoginUser().getUsername());
                        }
                    }
                    session.setAttribute("curLoginUser", null);
                    /* 清空jsessionid */
                    session.invalidate();
                    Cookie cookie = request.getCookies()[0];
                    cookie.setMaxAge(0);
                    if (returnurl.indexOf("?") == -1) {
                        returnurl += "?success=true&code=000";
                    } else {
                        returnurl += "&success=true&code=000";
                    }
                    result.put("returnurl", returnurl);
                    singleSignOnService.saveLog(info, variables, result, "注销登录用户", 5);
                    return new ModelAndView("redirect:" + returnurl);
                }
            }
            if (returnurl.indexOf("?") == -1) {
                returnurl += "?success=false&code=001";
            } else {
                returnurl += "&success=false&code=001";
            }
            result.put("returnurl", returnurl);
            singleSignOnService.saveLog(info, variables, result, "注销登录用户", 5);
            return new ModelAndView("redirect:" + returnurl);
        }
        return new ModelAndView("redirect:/login.jsp");
    }

}
