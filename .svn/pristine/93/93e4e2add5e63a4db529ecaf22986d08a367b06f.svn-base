/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import net.evecom.core.util.Constant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.Des;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WebUtil;
import net.evecom.core.web.interceptors.AuthInterceptor;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.WinSignService;
import net.evecom.platform.sso.service.SingleSignOnService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.IpFilterConfigService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysResService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 描述
 * 
 * @author Flex Hu
 * @created 2014年9月5日 上午11:16:56
 */
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(LoginController.class);
    /**
     * 引入service
     */
    @Resource
    private SysUserService sysUserService;
    /**
     * 引入logService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private SysResService sysResService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private WinSignService winSignService;
    /**
     * 
     */
    @Resource
    private IpFilterConfigService ipFilterConfigService;
    /**
     * 引入Service
     */
    @Resource
    private SingleSignOnService singleSignOnService;
    
    /**
     * 类型分组列表页面跳转
     * 
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "login")
    public ModelAndView login(HttpServletRequest request) throws UnsupportedEncodingException {
        /*String returnurl = request.getParameter("returnurl");
        String token = request.getParameter("token");
        if(StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(returnurl)){
            Map<String, Object> ssoInfo = sysUserService.getByJdbc("T_SYSTEM_SINGLESIGNON", new String[] { "SSO_TOKEN"},
                    new Object[] {token});
            if(null!=ssoInfo && ssoInfo.size()>0){
                String tokenTime = StringUtil.getString(ssoInfo.get("SSO_TOKEN_TIME"));
                long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                        DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if(timeMinute>120){//token获取时间大于120分钟时拒绝访问
                    if (returnurl.indexOf("?") == -1) {
                        returnurl += "?success=false&code=001";
                    } else {
                        returnurl += "&success=false&code=001";
                    }
                    return new ModelAndView("redirect:"+returnurl);
                }else{
                    SysUser currentUser = AppUtil.getLoginUser();
                    if(null!=currentUser){
                        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                        Map<String, Object> result = new HashMap<String, Object>();
                        Map<String, Object> ssoUser = new HashMap<String, Object>();
                        String trustticket =  UUIDGenerator.getUUID();
                        ssoUser.put("trustticket", trustticket);
                        ssoUser.put("user_id", currentUser.getUserId());
                        if(returnurl.indexOf("?")==-1){
                            returnurl+="?success=true&code=000&trustticket="+trustticket;
                        }else{
                            returnurl+="&success=true&code=000&trustticket="+trustticket;
                        }
                        singleSignOnService.saveOrUpdate(ssoUser, "T_SYSTEM_SINGLESIGNON_USER", null);//保存登录用户信息
                        result.put("returnurl", returnurl);
                        singleSignOnService.saveLog(ssoInfo, variables, result, "单点登录",2);
                        return new ModelAndView("redirect:" + returnurl);
                    } else{
                        request.setAttribute("returnurl", returnurl);
                        request.setAttribute("token", token);
                        return new ModelAndView("system/login/login");
                    }
                }
            } else{
                if (returnurl.indexOf("?") == -1) {
                    returnurl += "?success=false&code=001";
                } else {
                    returnurl += "&success=false&code=001";
                }
                return new ModelAndView("redirect:"+returnurl);
            }
        } else{*/
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("http_client_ip");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            // 如果是多级代理，那么取第一个ip为客户ip
            if (ip != null && ip.indexOf(",") != -1) {
                ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
            }
            log.info("当前用户访问ip：" + ip);
            if (!checkIp(request)) {
                return new ModelAndView("redirect:/404.html");
            }else{
                Properties properties = FileUtil.readProperties("conf/config.properties");
                String sso_login = properties.getProperty("sso_login");
                if("1".equals(sso_login)){
                    String ssoLoginUrl = properties.getProperty("sso_login_url");
                    String ssoClientId = properties.getProperty("sso_client_id");
                    String returnUrl = properties.getProperty("return_url");
                    String loginType = properties.getProperty("sso_login_type");
                    return new ModelAndView("redirect:" + ssoLoginUrl + "?client_id=" + ssoClientId + "&redirect_uri="
                            + URLDecoder.decode(returnUrl, "UTF-8")+"&login_type="+loginType);
                }else{
                    return new ModelAndView("system/login/login");
                }
            }
        //}
    }
    
    /**
     * 
     * 描述    统一身份证认证登录回调本地登录
     * @author Danto Huang
     * @created 2021年9月1日 下午3:49:58
     * @param request
     * @return
     */
    @RequestMapping(params="unifiedlogin")
    public ModelAndView unifiedlogin(HttpServletRequest request, HttpServletResponse response){        
        String token = request.getParameter("token");
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String sso_base_url = properties.getProperty("sso_base_url");
        String appId = properties.getProperty("sso_appid");
        String sso_secret_key = properties.getProperty("sso_secret_key");
        JSONObject json = new JSONObject();
        json.put("token", token);
        Sm4Utils sm4Utils = new Sm4Utils(sso_secret_key, "", true);
        String body = HttpUtil.createPost(sso_base_url + "/oauth/getUserInfoByToken").header("appid", appId)
                .form("body", sm4Utils.encryptDataECB(json.toString())).execute().body();
        JSONObject backObj = JSONUtil.parseObj(body);
        StringBuffer msg = new StringBuffer("");
        if(backObj.getBool("success")){
            JSONObject backUserInfo = backObj.getJSONObject("data");
            String mobile = backUserInfo.getStr("sysuser_mobile")==null?"":backUserInfo.getStr("sysuser_mobile");
            String idcard = backUserInfo.getStr("sysuser_idcard")==null?"":backUserInfo.getStr("sysuser_idcard");
            String account = backUserInfo.getStr("sysuser_account");
            if (StringUtils.isNotEmpty(account)&&!account.equals("admin") && !checkIp(request)) {
                msg.append("您当前登录计算机IP受限，请使用服务中心计算机登录!");
                return checkUserOutForLoginback(request,response,msg.toString(),account);
            }
            if("admin".equals(account)){
                AppUtil.getSession().invalidate();//设置用户信息到session前先销毁上一个session 
                HttpSession session = AppUtil.getSession();
                SysUser sysUser = sysUserService.getAllInfoByUsername("admin");
                AppUtil.addUserToSession(session, sysUser);
                sysLogService.saveLog("用户【" + sysUser.getFullname() + "】登录了系统",
                        SysLogService.OPERATE_TYPE_LOGIN);
                /*List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                for (Map<String, Object> topChildMenu : topChildMenuList) {
                    boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), sysUser);
                    if (!isGrant) {
                        topChildMenu.put("ishide", true);
                    } else {
                        List<Map<String, Object>> secondMenuList = this.findSecondMenuList(
                                topChildMenu.get("RES_ID").toString(), sysUser);
                        topChildMenu.put("secondMenuList", secondMenuList);
                    }
                }
                request.setAttribute("topChildMenuList", topChildMenuList);*/
                
                request.getSession().setAttribute("newAcceptVal", sysUser.getIsAcceptMsg());
                request.getSession().setAttribute("newMobileVal", sysUser.getMobile());
                sysUserService.reSetWrongNum("admin");
                //return new ModelAndView("main/index");
                return new ModelAndView("redirect:loginController.do?checkUser");
            }else{
                Map<String, Object> existUser = sysUserService.isExistsUserByMobileOrCard(mobile, idcard);
                if ((boolean) existUser.get("success")) {
                    Map<String, Object> userInfo = (Map<String, Object>) existUser.get("user");
                    int status = Integer.valueOf(userInfo.get("STATUS").toString());
                    String username = userInfo.get("USERNAME").toString();
                    if (status == SysUser.STATUS_ACTIVE) {
                        AppUtil.getSession().invalidate();// 设置用户信息到session前先销毁上一个session
                        HttpSession session = AppUtil.getSession();
                        SysUser sysUser = sysUserService.getAllInfoByUsername(username);
                        AppUtil.addUserToSession(session, sysUser);
                        sysLogService.saveLog("用户【" + sysUser.getFullname() + "】登录了系统",
                                SysLogService.OPERATE_TYPE_LOGIN);
                        /*List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                        for (Map<String, Object> topChildMenu : topChildMenuList) {
                            boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), sysUser);
                            if (!isGrant) {
                                topChildMenu.put("ishide", true);
                            } else {
                                List<Map<String, Object>> secondMenuList = this
                                        .findSecondMenuList(topChildMenu.get("RES_ID").toString(), sysUser);
                                topChildMenu.put("secondMenuList", secondMenuList);
                            }
                        }
                        request.setAttribute("topChildMenuList", topChildMenuList);*/                        
                        request.getSession().setAttribute("newAcceptVal", sysUser.getIsAcceptMsg());
                        request.getSession().setAttribute("newMobileVal", sysUser.getMobile());
                        sysUserService.reSetWrongNum(username);
                        //return new ModelAndView("main/index");
                        return new ModelAndView("redirect:loginController.do?checkUser");
                    } else if (status == SysUser.STATUS_FREEZEN) {
                        msg.append("抱歉!审批系统该用户被禁用!");
                    } else if (status == SysUser.STATUS_DISC) {
                        msg.append("抱歉!审批系统该用户被停用!");
                    } else {
                        SysUser currentUser = AppUtil.getLoginUser();
                        if (currentUser != null) {
                            /*List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                            for (Map<String, Object> topChildMenu : topChildMenuList) {
                                boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(),
                                        currentUser);
                                if (!isGrant) {
                                    topChildMenu.put("ishide", true);
                                } else {
                                    List<Map<String, Object>> secondMenuList = sysResService
                                            .findSubMenusByParentId(topChildMenu.get("RES_ID").toString());
                                    for (Map<String, Object> secondMenu : secondMenuList) {
                                        boolean isGrantSecond = sysResService
                                                .isGranted(secondMenu.get("RES_KEY").toString(), currentUser);
                                        if (!isGrantSecond) {
                                            secondMenu.put("ishide", true);
                                        }
                                    }
                                    topChildMenu.put("secondMenuList", secondMenuList);
                                }
                            }*/
                            request.getSession().setAttribute("newAcceptVal", currentUser.getIsAcceptMsg());
                            request.getSession().setAttribute("newMobileVal", currentUser.getMobile());
                            /*request.setAttribute("topChildMenuList", topChildMenuList);*/
                            
                            sysUserService.reSetWrongNum(username);
                            //return new ModelAndView("main/index");
                            return new ModelAndView("redirect:loginController.do?checkUser");
                        } else {
                            msg.append("审批系统无匹配登录用户，请联系管理员！");
                        }
                    }
                }else{
                    if("1".equals(existUser.get("errcode"))){
                        Map<String,Object> newUser = new HashMap<String, Object>();
                        newUser.put("USERNAME", backUserInfo.getStr("sysuser_account"));
                        newUser.put("FULLNAME", backUserInfo.getStr("sysuser_name"));
                        newUser.put("MOBILE", backUserInfo.getStr("sysuser_mobile"));
                        newUser.put("EMAIL", backUserInfo.getStr("sysuser_mail"));
                        newUser.put("USERCARD", backUserInfo.getStr("sysuser_idcard"));                        
                        newUser.put("DEPART_ID", "7D120C9034154F0E0000280000000037");
                        int passNum = StringUtil.getRandomIntNumber(9000, 1000);
                        String password = StringUtil.encryptSha256(SysUser.DEFAULT_PASSWORD+passNum);
                        newUser.put("PASSWORD", password);
                        newUser.put("STATUS", SysUser.STATUS_ACTIVE);
                        newUser.put("IS_MODIFYPASS", "1");
                        newUser.put("IS_UNIQUE_BIND", "1");
                        newUser.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        sysUserService.saveOrUpdate(newUser, "T_MSJW_SYSTEM_SYSUSER", null);
                        
                        HttpSession session = AppUtil.getSession();
                        SysUser sysUser = sysUserService.getAllInfoByUsername(backUserInfo.getStr("sysuser_account"));
                        AppUtil.addUserToSession(session, sysUser);
                        sysLogService.saveLog("用户【" + sysUser.getFullname() + "】登录了系统",
                                SysLogService.OPERATE_TYPE_LOGIN);
                        /*List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                        for (Map<String, Object> topChildMenu : topChildMenuList) {
                            boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), sysUser);
                            if (!isGrant) {
                                topChildMenu.put("ishide", true);
                            } else {
                                List<Map<String, Object>> secondMenuList = this
                                        .findSecondMenuList(topChildMenu.get("RES_ID").toString(), sysUser);
                                topChildMenu.put("secondMenuList", secondMenuList);
                            }
                        }
                        request.setAttribute("topChildMenuList", topChildMenuList);*/                        
                        request.getSession().setAttribute("newAcceptVal", sysUser.getIsAcceptMsg());
                        request.getSession().setAttribute("newMobileVal", sysUser.getMobile());
                        sysUserService.reSetWrongNum(backUserInfo.getStr("sysuser_account"));
                        //return new ModelAndView("main/index");
                        return new ModelAndView("redirect:loginController.do?checkUser");
                    }else{
                        msg.append(existUser.get("msg"));
                    }
                }
                return checkUserOutForLoginback(request,response,msg.toString(),account);
            }
        }

        return checkUserOutForLoginback(request,response,backObj.getStr("msg"),"");
        
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年9月3日 上午11:25:15
     * @param request
     * @param response
     * @param msg
     * @param username
     * @return
     */
    public ModelAndView checkUserOutForLoginback(HttpServletRequest request,HttpServletResponse response
            ,String msg,String username){
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String ssoLoginUrl = properties.getProperty("sso_loginout_url");
            String ssoClientId = properties.getProperty("sso_client_id");
            String returnUrl = properties.getProperty("return_out_url");
            String redirectUrl = ssoLoginUrl + "?client_id=" + ssoClientId + "&redirect_uri="
                    + URLDecoder.decode(returnUrl, "UTF-8");
            
            out = response.getWriter();
            StringBuffer result = new StringBuffer("<script>alert('");
            result.append(msg.toString()).append("');");
            result.append("window.top.location.href='");
            result.append(redirectUrl);

            
            result.append("';").append("</script>");
            out.println(result);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 类型分组列表页面跳转
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "facelogin")
    public ModelAndView facelogin(HttpServletRequest request) {
        String returnurl = request.getParameter("returnurl");
        String token = request.getParameter("token");
        if(StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(returnurl)){
            Map<String, Object> ssoInfo = sysUserService.getByJdbc("T_SYSTEM_SINGLESIGNON", new String[] { "SSO_TOKEN"},
                    new Object[] {token});
            if(null!=ssoInfo && ssoInfo.size()>0){
                String tokenTime = StringUtil.getString(ssoInfo.get("SSO_TOKEN_TIME"));
                long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime, DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if(timeMinute>120){//token获取时间大于120分钟时拒绝访问
                    return new ModelAndView("redirect:/403filter.html");
                }else{
                    request.setAttribute("returnurl", returnurl);
                    request.setAttribute("token", token);
                    return new ModelAndView("system/login/facelogin");
                }
            } else{
                return new ModelAndView("redirect:/403filter.jsp");
            }
        } else{
            if (!checkIp(request)) {
                return new ModelAndView("redirect:/404.html");
            }else{
                return new ModelAndView("system/login/facelogin");
            }
        }
    }

    /**
     * 类型分组列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "autologin")
    public ModelAndView autologin(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String isEncryptedByIDS = request.getParameter("isEncryptedByIDS");
        StringBuffer msg = new StringBuffer("");
        if ("true".equals(isEncryptedByIDS)) {
            userName = StringUtil.getBase64Decode(userName, "utf-8");
            password = StringUtil.getBase64Decode(password, "utf-8");
            int status = sysUserService.isExistsUser(userName, StringUtil.encryptSha256(password));
            if (status == SysUser.STATUS_ACTIVE) {
                HttpSession session = AppUtil.getSession();
                SysUser sysUser = sysUserService.getAllInfoByUsername(userName);
                AppUtil.addUserToSession(session, sysUser);
                sysLogService.saveLog("用户【" + sysUser.getFullname() + "】登录了系统", SysLogService.OPERATE_TYPE_LOGIN);
                List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                for (Map<String, Object> topChildMenu : topChildMenuList) {
                    boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), sysUser);
                    if (!isGrant) {
                        topChildMenu.put("ishide", true);
                    } else {
                        List<Map<String, Object>> secondMenuList = this.findSecondMenuList(
                                topChildMenu.get("RES_ID").toString(), sysUser);
                        topChildMenu.put("secondMenuList", secondMenuList);
                    }
                }
                request.setAttribute("topChildMenuList", topChildMenuList);
                return new ModelAndView("main/index");
            } else {
                SysUser currentUser = AppUtil.getLoginUser();
                if (currentUser != null) {
                    List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                    for (Map<String, Object> topChildMenu : topChildMenuList) {
                        boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(),
                                currentUser);
                        if (!isGrant) {
                            topChildMenu.put("ishide", true);
                        } else {
                            List<Map<String, Object>> secondMenuList = sysResService
                                    .findSubMenusByParentId(topChildMenu.get("RES_ID").toString());
                            for (Map<String, Object> secondMenu : secondMenuList) {
                                boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY")
                                        .toString(), currentUser);
                                if (!isGrantSecond) {
                                    secondMenu.put("ishide", true);
                                }
                            }
                            topChildMenu.put("secondMenuList", secondMenuList);
                        }

                    }
                    request.setAttribute("topChildMenuList", topChildMenuList);
                    return new ModelAndView("main/index");
                } else {
                    msg.append("用户帐号或者密码错误!");
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                StringBuffer result = new StringBuffer("<script>alert('");
                result.append(msg.toString()).append("');");
                result.append("window.top.location.href='");
                result.append(AppUtil.getWebRootPath(request)).append("/loginController.do?login")
                        .append("&username=").append(userName).append("';").append("</script>");
                out.println(result);
                out.flush();
                out.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            return null;
        } else {
            return new ModelAndView("system/login/login");
        }
    }

    /**
     * 退出系统页面跳转
     * 
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(params = "logout")
    public ModelAndView logout(HttpServletRequest request) throws UnsupportedEncodingException {
        HttpSession session = AppUtil.getSession();
        if (AuthInterceptor.sessionMap != null) {
            if (AppUtil.getLoginUser() != null) {
                AuthInterceptor.sessionMap.remove(AppUtil.getLoginUser().getUsername());
            }
        }
        session.setAttribute("curLoginUser", null);
        /*清空jsessionid*/
        session.invalidate();
        Cookie cookie = request.getCookies()[0];
        cookie.setMaxAge(0);
        if (!checkIp(request)) {
            return new ModelAndView("redirect:/404.html");
        }else{
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String sso_login = properties.getProperty("sso_login");
            if("1".equals(sso_login)){
                String ssoLoginUrl = properties.getProperty("sso_loginout_url");
                String ssoClientId = properties.getProperty("sso_client_id");
                String returnUrl = properties.getProperty("return_out_url");
                return new ModelAndView("redirect:" + ssoLoginUrl + "?client_id=" + ssoClientId + "&redirect_uri="
                        + URLDecoder.decode(returnUrl, "UTF-8"));
            }else{
                return new ModelAndView("system/login/login");
            }
        }
    }

    /**
     * 进入到后台首页
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "index")
    public ModelAndView index(HttpServletRequest request) {
        String resKey = request.getParameter("resKey");
        SysUser currentUser = AppUtil.getLoginUser();
        Map<String, Object> rootMenu = sysResService.getByJdbc("T_MSJW_SYSTEM_RES", new String[] { "RES_KEY" },
                new Object[] { resKey });
        List<Map<String, Object>> topChildMenuList = sysResService.findSubMenusByParentId(rootMenu.get("RES_ID")
                .toString());
        for (Map<String, Object> topChildMenu : topChildMenuList) {
            boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), currentUser);
            if (!isGrant) {
                topChildMenu.put("ishide", true);
            } else {
                List<Map<String, Object>> secondMenuList = sysResService.findSubMenusByParentId(topChildMenu.get(
                        "RES_ID").toString());
                for (Map<String, Object> secondMenu : secondMenuList) {
                    boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY").toString(),
                            currentUser);
                    if (!isGrantSecond) {
                        secondMenu.put("ishide", true);
                    }
                }
                topChildMenu.put("secondMenuList", secondMenuList);
            }

        }
        request.setAttribute("systemName", rootMenu.get("RES_NAME").toString());
        request.setAttribute("resKey", resKey);
        request.setAttribute("topChildMenuList", topChildMenuList);
        return new ModelAndView("main/index");
    }

    /**
     * 类型分组列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "changeSystem")
    public ModelAndView changeSystem(HttpServletRequest request) {
        String resKey = request.getParameter("resKey");
        request.setAttribute("resKey", resKey);
        return new ModelAndView("main/changesubSystem");
    }

    public List<Map<String, Object>> findSecondMenuList(String resId, SysUser curLoginUser) {
        List<Map<String, Object>> secondMenuList = sysResService.findSubMenusByParentId(resId);
        for (Map<String, Object> secondMenu : secondMenuList) {
            boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY").toString(), curLoginUser);
            if (!isGrantSecond) {
                secondMenu.put("ishide", true);
            }
        }
        return secondMenuList;
    }

    /**
     * 类型分组列表页面跳转
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "checkUser")
    public ModelAndView checkUser(HttpServletRequest request, HttpServletResponse response) { 
        String scanToken = request.getParameter("scanToken");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String returnurl = request.getParameter("returnurl");
        String userWinIp = request.getParameter("userip");
        StringBuffer msg = new StringBuffer("");
        if(StringUtils.isNotEmpty(scanToken)){
            String uname = request.getParameter("uname");
            Map<String, Object> info = sysUserService.getByJdbc("T_SYSTEM_SCANCODE_LOGIN",
                    new String[] { "TOKEN" }, new Object[] { scanToken });
            if (null != info) {
                String code = StringUtil.getString(info.get("CODE"));
                String tokenTime = StringUtil.getString(info.get("CREATE_TIME"));
                if (StringUtils.isNotEmpty(tokenTime)) {
                    long timeMinute = DateTimeUtil.getIntervalMinute(tokenTime,
                            DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    if (timeMinute >=2) {//超过两分钟超时
                        msg.append("登录超时，请重新扫码登录!");
                    } else{
                        if (StringUtils.isNotEmpty(code)) {
                            List<Map<String, Object>>  list = sysUserService.findByMoblie(code);
                            if(null!=list && list.size()>0){
                                for (Map<String, Object> map : list) {
                                    String USERNAME = StringUtil.getString(map.get("USERNAME"));
                                    if (StringUtils.isNotEmpty(USERNAME) && StringUtils.isNotEmpty(uname)
                                            && USERNAME.equals(uname)) {
                                        return sysUserLogin(request, uname, returnurl, userWinIp);
                                    }
                                }
                                msg.append("登录用户与扫码账号不匹配!");
                            } else{
                                msg.append("未查询到匹配用户!");
                            }
                        }  else {
                            msg.append("未扫码登录!");
                        }
                    }
                }
            } else {
                msg.append("登录令牌错误!");
            }
        } else{
            if (StringUtils.isNotEmpty(password) && password.length()>200) {
                msg.append("抱歉输入密码过长!");
                return checkUserOut(request,response,msg.toString(),username);
            }
            if(StringUtils.isNotEmpty(password)){
                password=Des.strDec(request.getParameter("password"), "1", "2", "3");
            }
            //String clientip = WebUtil.getIpAddr(request);
            SysUser curLoginUser = AppUtil.getLoginUser();
            if (curLoginUser != null) {
                curUserNoNull(request,curLoginUser,userWinIp);
                sysUserService.reSetWrongNum(username);
                return returnUrl(returnurl,request);
            }else if(StringUtils.isNotEmpty(password) && password.length()>32){
                msg.append("抱歉输入密码过长!");
            } else if (StringUtils.isNotEmpty(username)&&!username.equals("admin") && !checkIp(request)) {
                msg.append("您当前登录计算机IP受限，请使用服务中心计算机登录!");
            } else {
                String validateCode = request.getParameter("validateCode");
                String kaRandCode = String.valueOf(request.getSession().getAttribute(
                        Constant.REGCODE_SESSION));
                if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(kaRandCode)) {
                    kaRandCode = kaRandCode.toLowerCase();
                    validateCode = validateCode.toLowerCase();
                }
                if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(kaRandCode)
                        && validateCode.equals(kaRandCode)) {
                    //登录成功后清空验证码 
                    request.getSession().removeAttribute(Constant.REGCODE_SESSION);
                    int status = sysUserService.isExistsUser(username, StringUtil.encryptSha256(password));
                    if (status == SysUser.STATUS_ACTIVE) {//激活状态，进行用户登录处理
                        return sysUserLogin(request, username, returnurl, userWinIp);
                    } else if (status == SysUser.STATUS_FREEZEN) {
                        msg.append("抱歉!该用户被禁用!");
                    } else if (status == SysUser.STATUS_DISC) {
                        msg.append("抱歉!该用户被停用!");
                    } else {
                        SysUser currentUser = AppUtil.getLoginUser();
                        if (currentUser != null) {
                            List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                            for (Map<String, Object> topChildMenu : topChildMenuList) {
                                boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(),
                                        currentUser);
                                if (!isGrant) {
                                    topChildMenu.put("ishide", true);
                                } else {
                                    List<Map<String, Object>> secondMenuList = sysResService
                                            .findSubMenusByParentId(topChildMenu.get("RES_ID").toString());
                                    for (Map<String, Object> secondMenu : secondMenuList) {
                                        boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY")
                                                .toString(), currentUser);
                                        if (!isGrantSecond) {
                                            secondMenu.put("ishide", true);
                                        }
                                    }
                                    topChildMenu.put("secondMenuList", secondMenuList);
                                }
                            }
                            request.getSession().setAttribute("newAcceptVal", currentUser.getIsAcceptMsg());
                            request.getSession().setAttribute("newMobileVal", currentUser.getMobile());
                            request.setAttribute("topChildMenuList", topChildMenuList);
                            // return new ModelAndView("portal/portal");
                            // 判断签到是否开启
                            if (StringUtils.isNotBlank(userWinIp)&&isSignOppen()) {
                                request.getSession().setAttribute("userWinIp", userWinIp);
                                Map<String, Object> winSignMap = this.winSign(request);
                                if (winSignMap != null&&!winSignMap.isEmpty()) {
                                    request.getSession().setAttribute("winNo", winSignMap.get("WIN_NO"));
                                    request.getSession().setAttribute("winAddr", winSignMap.get("WIN_IP"));
                                    request.getSession().setAttribute("loginTime", winSignMap.get("LOGIN_TIME"));
                                }}
                            sysUserService.reSetWrongNum(username);
                            return returnUrl(returnurl,request);
                        } else {
                            msg.append("用户帐号或者密码错误!连续输错五次账号将被禁用!");
                            if (StringUtils.isNotEmpty(username)&&!username.equals("admin")) {
                                sysUserService.handWrongNum(username);
                            }
                            
                        }}
                } else {
                    msg.append("验证码填写错误!");
                }
            }
        }
        return checkUserOut(request,response,msg.toString(),username);
    }

    private ModelAndView sysUserLogin(HttpServletRequest request, String username, String returnurl, String userWinIp) {
        AppUtil.getSession().invalidate();//设置用户信息到session前先销毁上一个session 
        HttpSession session = AppUtil.getSession();
        SysUser sysUser = sysUserService.getAllInfoByUsername(username);
        AppUtil.addUserToSession(session, sysUser);
        // 设置登录用户拥有的业务编码数据权限
        // Set<String> busCodes = departmentService.getBusCodeSetByUserId(sysUser.getUserId());
        // session.setAttribute("userBusCodes", busCodes);
        sysLogService.saveLog("用户【" + sysUser.getFullname() + "】登录了系统",
                SysLogService.OPERATE_TYPE_LOGIN);
        List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
        for (Map<String, Object> topChildMenu : topChildMenuList) {
            boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), sysUser);
            if (!isGrant) {
                topChildMenu.put("ishide", true);
            } else {
                List<Map<String, Object>> secondMenuList = this.findSecondMenuList(
                        topChildMenu.get("RES_ID").toString(), sysUser);
                topChildMenu.put("secondMenuList", secondMenuList);
            }
        }
        request.setAttribute("topChildMenuList", topChildMenuList);
        // return new ModelAndView("portal/portal");
        // 判断签到是否开启
        if (StringUtils.isNotBlank(userWinIp)&&isSignOppen()) {
            session.setAttribute("userWinIp", userWinIp);
            this.winSign(request);
            Map<String, Object> winSignMap = this.winSign(request);
            if (winSignMap != null&&!winSignMap.isEmpty()) {
                request.getSession().setAttribute("winNo", winSignMap.get("WIN_NO"));
                request.getSession().setAttribute("winAddr", winSignMap.get("WIN_IP"));
                request.getSession().setAttribute("loginTime", winSignMap.get("LOGIN_TIME"));
            }
        }
        request.getSession().setAttribute("newAcceptVal", sysUser.getIsAcceptMsg());
        request.getSession().setAttribute("newMobileVal", sysUser.getMobile());
        sysUserService.reSetWrongNum(username);
        return returnUrl(returnurl,request);
    }

    @SuppressWarnings("unchecked")
    private ModelAndView returnUrl(String returnurl,HttpServletRequest request) {
        if(StringUtils.isNotEmpty(returnurl)){//单点登录
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            String token  = StringUtil.getString(variables.get("token"));
            Map<String, Object> ssoInfo = sysUserService.getByJdbc("T_SYSTEM_SINGLESIGNON", new String[] { "SSO_TOKEN"},
                    new Object[] {token});
            if(null!=ssoInfo && ssoInfo.size()>0){
                Map<String, Object> result = new HashMap<String, Object>();
                Map<String, Object> ssoUser = new HashMap<String, Object>();
                String trustticket =  UUIDGenerator.getUUID();
                SysUser currentUser = AppUtil.getLoginUser();
                ssoUser.put("trustticket", trustticket);
                ssoUser.put("user_id", currentUser.getUserId());
                if(returnurl.indexOf("?")==-1){
                    returnurl+="?success=true&code=000&trustticket="+trustticket;
                }else{
                    returnurl+="&success=true&code=000&trustticket="+trustticket;
                }
                singleSignOnService.saveOrUpdate(ssoUser, "T_SYSTEM_SINGLESIGNON_USER", null);//保存登录用户信息
                result.put("returnurl", returnurl);
                singleSignOnService.saveLog(ssoInfo, variables, result, "单点登录",2);
                return new ModelAndView("redirect:" + returnurl);
            } else{
                if(returnurl.indexOf("?")==-1){
                    returnurl+="?success=false&code=001";
                }else{
                    returnurl+="&success=false&code=001";
                }
                return new ModelAndView("redirect:" + returnurl);
            }
        } else{
            return new ModelAndView("main/index");
        }
    }

    /**
     * miaoshu
     */
    public ModelAndView checkUserOut(HttpServletRequest request,HttpServletResponse response
            ,String msg,String username){
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            StringBuffer result = new StringBuffer("<script>alert('");
            result.append(msg.toString()).append("');");
            result.append("window.top.location.href='");
            result.append(AppUtil.getWebRootPath(request)).append("/loginController.do?login").append("&username=")
                    .append(null==username?"":username);

            String returnurl = request.getParameter("returnurl");
            String token = request.getParameter("token");
            if(StringUtils.isNotEmpty(returnurl)){
                result.append("&returnurl=").append(returnurl);
            }
            if(StringUtils.isNotEmpty(token)){
                result.append("&token=").append(token);
            }
            result.append("';").append("</script>");
            out.println(result);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    public void curUserNoNull(HttpServletRequest request,SysUser curLoginUser,String userWinIp){
        List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
        for (Map<String, Object> topChildMenu : topChildMenuList) {
            boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), curLoginUser);
            if (!isGrant) {
                topChildMenu.put("ishide", true);
            } else {
                List<Map<String, Object>> secondMenuList = this.findSecondMenuList(topChildMenu.get("RES_ID")
                        .toString(), curLoginUser);
                topChildMenu.put("secondMenuList", secondMenuList);
            }
        }
        request.setAttribute("topChildMenuList", topChildMenuList);
        // 判断签到是否开启
        if (StringUtils.isNotBlank(userWinIp)&&isSignOppen()) {
            request.getSession().setAttribute("userWinIp", userWinIp);
            Map<String, Object> winSignMap = this.winSign(request);
            if (winSignMap != null&&!winSignMap.isEmpty()) {
                request.getSession().setAttribute("winNo", winSignMap.get("WIN_NO"));
                request.getSession().setAttribute("winAddr", winSignMap.get("WIN_IP"));
                request.getSession().setAttribute("loginTime", winSignMap.get("LOGIN_TIME"));
            }
        }
        SysUser sysUser = sysUserService.getAllInfoByUsername(curLoginUser.getUsername());
        request.getSession().setAttribute("newAcceptVal", sysUser.getIsAcceptMsg());
        request.getSession().setAttribute("newMobileVal", sysUser.getMobile());
    }

    /**
     * 描述 判断是否是签到的IP
     * 
     * @author Derek Zhang
     * @created 2016年7月9日 上午11:29:12
     * @param userWinIp
     * @return
     */
    private boolean isSignOppen() {
        String zxqdkg = this.dictionaryService.findByDicCodeAndTypeCode("zxqdkg", "zxqd");
        return zxqdkg == null ? false : zxqdkg.equals("on");
    }

    /**
     * 
     * 描述 从门户页进入到后台首页
     * 
     * @author Derek Zhang
     * @created 2015年10月19日 下午5:09:38
     * @param request
     * @return
     */
    @RequestMapping(params = "portalToIndex")
    public ModelAndView portalToIndex(HttpServletRequest request) {
        String reskey = request.getParameter("reskey");
        Map<String, Object> reskeyItem = null;
        if (StringUtils.isNotEmpty(reskey) && !reskey.equals("undefined")) {
            reskeyItem = sysResService.getByJdbc("T_MSJW_SYSTEM_RES", new String[] { "RES_KEY" },
                    new Object[] { reskey });
        }
        if (reskeyItem == null) {
            reskeyItem = new HashMap<String, Object>();
            reskeyItem.put("RES_ID", "-1");
            reskeyItem.put("RES_NAME", "");
        }
        SysUser currentUser = AppUtil.getLoginUser();
        if (currentUser != null) {
            List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus(reskeyItem.get("RES_ID")
                    .toString());
            for (Map<String, Object> topChildMenu : topChildMenuList) {
                boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), currentUser);
                if (!isGrant) {
                    topChildMenu.put("ishide", true);
                } else {
                    List<Map<String, Object>> secondMenuList = sysResService.findSubMenusByParentId(topChildMenu
                            .get("RES_ID").toString());
                    for (Map<String, Object> secondMenu : secondMenuList) {
                        boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY").toString(),
                                currentUser);
                        if (!isGrantSecond) {
                            secondMenu.put("ishide", true);
                        }
                    }
                    topChildMenu.put("secondMenuList", secondMenuList);
                }

            }
            request.setAttribute("topChildMenuList", topChildMenuList);
            request.setAttribute("RES_NAME", reskeyItem.get("RES_NAME").toString());
            request.setAttribute("reskey", reskey);
            return new ModelAndView("main/index");
        } else {
            HttpSession session = AppUtil.getSession();
            session.setAttribute("curLoginUser", null);
            return new ModelAndView("system/login/login");
        }
    }

    /**
     * 
     * 描述 根据key获取左边列表
     * 
     * @author Faker Li
     * @created 2015年12月2日 上午9:52:21
     * @param request
     * @param response
     */
    @RequestMapping(params = "findLeftList")
    public void jskzx(HttpServletRequest request, HttpServletResponse response) {
        String reskey = request.getParameter("reskey");
        Map<String, Object> reskeyItem = null;
        if (StringUtils.isNotEmpty(reskey) && !reskey.equals("undefined")) {
            reskeyItem = sysResService.getByJdbc("T_MSJW_SYSTEM_RES", new String[] { "RES_KEY" },
                    new Object[] { reskey });
        }
        if (reskeyItem == null) {
            reskeyItem = new HashMap<String, Object>();
            reskeyItem.put("RES_ID", "-1");
            reskeyItem.put("RES_NAME", "");
        }
        SysUser currentUser = AppUtil.getLoginUser();
        List<Map<String, Object>> topChildMenuList = new ArrayList<Map<String, Object>>();
        if (currentUser != null) {
            topChildMenuList = sysResService.findSubMenus(reskeyItem.get("RES_ID").toString());
            for (Map<String, Object> topChildMenu : topChildMenuList) {
                boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), currentUser);
                if (!isGrant) {
                    topChildMenu.put("ishide", true);
                } else {
                    List<Map<String, Object>> secondMenuList = sysResService.findSubMenusByParentId(topChildMenu
                            .get("RES_ID").toString());
                    for (Map<String, Object> secondMenu : secondMenuList) {
                        boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY").toString(),
                                currentUser);
                        if (!isGrantSecond) {
                            secondMenu.put("ishide", true);
                        }
                    }
                    if(secondMenuList!=null&&secondMenuList.size()>0){
                        topChildMenu.put("hasChild", true);
                    }
                    topChildMenu.put("secondMenuList", secondMenuList);
                }

            }
        }
        JsonUtil.printJson(response, topChildMenuList);
    }

    private boolean checkIp(HttpServletRequest request) {
        boolean checkResult = false;
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String filterSwitch = properties.getProperty("IP_FILTER_SWITCH");
        if (filterSwitch.equals("1")) {
            String ip = WebUtil.getIpAddr(request);
            if (ip.equals("127.0.0.1")) {
                checkResult = true;
            } else {
                String checkIp = ip.substring(0, ip.lastIndexOf("."));
                /*List<Map<String, Object>> list = dictionaryService.findByTypeCode("IPfilter");
                for (Map<String, Object> ipFilter : list) {
                    if (checkIp.equals(ipFilter.get("DIC_CODE"))) {
                        checkResult = true;
                        break;
                    } else {
                        log.info("ip：" + ip + "已被限制访问");
                    }
                }*/
                String checkIpBit = ip.substring(ip.lastIndexOf(".")+1);
                List<Map<String, Object>> list = ipFilterConfigService.findEnableIpFilter();
                for (Map<String, Object> ipFilter : list) {
                    if (checkIp.equals(ipFilter.get("IP_SEGMENT"))) {
                        int start = Integer.valueOf(ipFilter.get("IP_BIT_START").toString());
                        int end = Integer.valueOf(ipFilter.get("IP_BIT_END").toString());
                        if(Integer.valueOf(checkIpBit)>=start&&Integer.valueOf(checkIpBit)<=end){
                            checkResult = true;
                        }else{
                            log.info("ip：" + ip + "已被限制访问");
                        }
                        break;
                    }
                }
            }
        } else {
            checkResult = true;
        }
        return checkResult;
    }

    /**
     * 描述 窗口人员 签到
     * 
     * @author Derek Zhang
     * @created 2016年7月7日 上午9:35:56
     * 1:首次签到业务 2:日常签到业务
     * @return
     */
    private Map<String, Object> winSign(HttpServletRequest request) {
        SysUser currentUser = AppUtil.getLoginUser();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        String userWinIp = (request.getSession().getAttribute("userWinIp") == null ? "" : (String) request
                .getSession().getAttribute("userWinIp"));
        if (request.getSession().getAttribute("userWinIp") != null && currentUser != null && currentUser != null) {
            // 判断是否需要签到
            boolean needSign = this.winSignService.isNeedSign(userWinIp, WebUtil.getIpAddr(request));
            if (needSign) {
                request.getSession().setAttribute("needSign", "true");
                String signRateStr = this.dictionaryService.findByDicCodeAndTypeCode("signRate", "zxqd");
                if (StringUtils.isBlank(signRateStr))
                    signRateStr = "2";
                Integer signRate = Integer.parseInt(signRateStr) * 60 * 1000;
                request.getSession().setAttribute("signRate", signRate);
                // 判断是否首次签到
                List<Map<String, Object>> resultList = this.winSignService.isFirstSignByCurDate(userWinIp,
                        WebUtil.getIpAddr(request));
                // 签到信息保存到签表
                if (resultList == null || resultList.isEmpty()) {
                    Map<String, Object> colValues = this.winSignService.doSign(currentUser, request.getSession()
                            .getAttribute("userWinIp") + "", WebUtil.getIpAddr(request));
                    returnMap.put("winNo", colValues.get("WIN_NO"));
                    returnMap.put("winAddr", request.getSession().getAttribute("userWinIp") + "");
                    returnMap.put("loginTime",
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(colValues.get("LOGIN_TIME")));

                    request.getSession().setAttribute("firsttimes", signRate);
                } else {
                    returnMap = resultList.get(0);
                    String signTime = (String) returnMap.get("LOGIN_TIME");
                    try {
                        if ((new Date().getTime())
                                - ((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                                        .parse(signTime)).getTime() > signRate) {
                            Map<String, Object> colValues = this.winSignService.doSign(currentUser, request
                                    .getSession().getAttribute("userWinIp") + "", WebUtil.getIpAddr(request));
                            returnMap.put("winNo", colValues.get("WIN_NO"));
                            returnMap.put("winAddr", request.getSession().getAttribute("userWinIp") + "");
                            returnMap
                                    .put("loginTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(colValues
                                            .get("LOGIN_TIME")));
                        } else {
                            long firsttimes = signRate
                                    - ((new Date().getTime()) - ((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                                            .parse(signTime)).getTime());
                            request.getSession().setAttribute("firsttimes", firsttimes);
                        }
                    } catch (ParseException e) {
                    }
                }
            }else{
                request.getSession().setAttribute("needSign", "false");
            }
        }
        return returnMap;
    }
    
    /**
     * 类型分组列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "checkFacelogin")
    public ModelAndView checkFacelogin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String userWinIp = request.getParameter("userip");
        StringBuffer msg = new StringBuffer("");
        SysUser curLoginUser = AppUtil.getLoginUser();
        if (curLoginUser != null) {
            curUserNoNull(request,curLoginUser,userWinIp);
            sysUserService.reSetWrongNum(username);
            return new ModelAndView("main/index");
        }else if (StringUtils.isNotEmpty(username)&&!username.equals("admin") && !checkIp(request)) {
            msg.append("您当前登录计算机IP受限，请使用服务中心计算机登录!");
        } else {
            
                int status = sysUserService.isExistsUser(username,null);
                if (status == SysUser.STATUS_ACTIVE) {
                    HttpSession session = AppUtil.getSession();
                    SysUser sysUser = sysUserService.getAllInfoByUsername(username);
                    AppUtil.addUserToSession(session, sysUser);
                    sysLogService.saveLog("用户【" + sysUser.getFullname() + "】登录了系统",
                            SysLogService.OPERATE_TYPE_LOGIN);
                    List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                    for (Map<String, Object> topChildMenu : topChildMenuList) {
                        boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(), sysUser);
                        if (!isGrant) {
                            topChildMenu.put("ishide", true);
                        } else {
                            List<Map<String, Object>> secondMenuList = this.findSecondMenuList(
                                    topChildMenu.get("RES_ID").toString(), sysUser);
                            topChildMenu.put("secondMenuList", secondMenuList);
                        }
                    }
                    request.setAttribute("topChildMenuList", topChildMenuList);
                    // return new ModelAndView("portal/portal");
                    // 判断签到是否开启
                    if (StringUtils.isNotBlank(userWinIp)&&isSignOppen()) {
                        session.setAttribute("userWinIp", userWinIp);
                        this.winSign(request);
                        Map<String, Object> winSignMap = this.winSign(request);
                        if (winSignMap != null&&!winSignMap.isEmpty()) {
                            request.getSession().setAttribute("winNo", winSignMap.get("WIN_NO"));
                            request.getSession().setAttribute("winAddr", winSignMap.get("WIN_IP"));
                            request.getSession().setAttribute("loginTime", winSignMap.get("LOGIN_TIME"));
                        }
                    }
                    request.getSession().setAttribute("newAcceptVal", sysUser.getIsAcceptMsg());
                    request.getSession().setAttribute("newMobileVal", sysUser.getMobile());
                    sysUserService.reSetWrongNum(username);
                    return new ModelAndView("main/index");
                } else if (status == SysUser.STATUS_FREEZEN) {
                    msg.append("抱歉!该用户被禁用!");
                } else if (status == SysUser.STATUS_DISC) {
                    msg.append("抱歉!该用户被停用!");
                } else {
                    SysUser currentUser = AppUtil.getLoginUser();
                    if (currentUser != null) {
                        List<Map<String, Object>> topChildMenuList = sysResService.findSubMenus("0");
                        for (Map<String, Object> topChildMenu : topChildMenuList) {
                            boolean isGrant = sysResService.isGranted(topChildMenu.get("RES_KEY").toString(),
                                    currentUser);
                            if (!isGrant) {
                                topChildMenu.put("ishide", true);
                            } else {
                                List<Map<String, Object>> secondMenuList = sysResService
                                        .findSubMenusByParentId(topChildMenu.get("RES_ID").toString());
                                for (Map<String, Object> secondMenu : secondMenuList) {
                                    boolean isGrantSecond = sysResService.isGranted(secondMenu.get("RES_KEY")
                                            .toString(), currentUser);
                                    if (!isGrantSecond) {
                                        secondMenu.put("ishide", true);
                                    }
                                }
                                topChildMenu.put("secondMenuList", secondMenuList);
                            }
                        }
                        request.getSession().setAttribute("newAcceptVal", currentUser.getIsAcceptMsg());
                        request.getSession().setAttribute("newMobileVal", currentUser.getMobile());
                        request.setAttribute("topChildMenuList", topChildMenuList);
                        // return new ModelAndView("portal/portal");
                        // 判断签到是否开启
                        if (StringUtils.isNotBlank(userWinIp)&&isSignOppen()) {
                            request.getSession().setAttribute("userWinIp", userWinIp);
                            Map<String, Object> winSignMap = this.winSign(request);
                            if (winSignMap != null&&!winSignMap.isEmpty()) {
                                request.getSession().setAttribute("winNo", winSignMap.get("WIN_NO"));
                                request.getSession().setAttribute("winAddr", winSignMap.get("WIN_IP"));
                                request.getSession().setAttribute("loginTime", winSignMap.get("LOGIN_TIME"));
                            }}
                        sysUserService.reSetWrongNum(username);
                        return new ModelAndView("main/index");
                    } else {
                        msg.append("用户帐号或者密码错误!连续输错五次账号将被禁用!");
                        if (StringUtils.isNotEmpty(username)&&!username.equals("admin")) {
                            sysUserService.handWrongNum(username);
                        }
                        
                    }}
        }
        return checkUserOut(request,response,msg.toString(),username);
    }
}
