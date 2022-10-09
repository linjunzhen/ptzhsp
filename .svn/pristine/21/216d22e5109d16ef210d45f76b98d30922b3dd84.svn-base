/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.Des;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SendMsgUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.dao.UserInfoDao;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.bsfw.util.MztSample;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;

/**
 * 描述 前台用户管理Controller
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/userInfoController")
public class UserInfoController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(UserInfoController.class);
    /**
     * 引入Service
     */
    @Resource
    private UserInfoService userInfoService;
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
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 所引入的dao
     */
    @Resource
    private UserInfoDao dao;

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
        userInfoService.remove("T_BSFW_USERINFO", "USER_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 前台用户管理记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述 根据用户id查询是否存在该用户查询类别值
     * @author Water Guo
     * @created 2015-12-31 下午02:40:04
     */
    @RequestMapping("/useridExistence")
    public void useridExistence(HttpServletRequest request, HttpServletResponse response){
        String uploadUserId =  request.getParameter("uploadUserId");
        List<Map<String, Object>> userInfo = sysLogService.useridExistence(uploadUserId);
        String json = JSON.toJSONString(userInfo);
        this.setJsonString(json, response);
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
            Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "USER_ID" },
                    new Object[] { entityId });
            SysUser sysUser = AppUtil.getLoginUser();
            // 获取菜单KEY
            String resKey = sysUser.getResKeys();
            Set<String> roleCodes = sysUser.getRoleCodes();
            // 判断是否行政服务中心管理员
            boolean gly = roleCodes.contains("88888_GLY");
            
            if ("__ALL".equals(resKey)||gly) {// 判断是否超级管理员/行政服务中心管理员
                if (userInfo.get("SJHM")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("SJHM").toString())) {
                    String sjhm = userInfo.get("SJHM").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(3, 7, "****");
                    userInfo.put("SJHM", sb.toString().toLowerCase());
                }
                if (userInfo.get("ZJHM")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("ZJHM").toString())) {
                    String sjhm = userInfo.get("ZJHM").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(6, 14, "********");
                    userInfo.put("ZJHM", sb.toString().toLowerCase());
                }
                userInfo.remove("DLMM");
//                if (userInfo.get("DLMM")!=null
//                        &&StringUtils.isNotEmpty(userInfo.get("DLMM").toString())) {
//                    String sjhm = userInfo.get("DLMM").toString();
//                    char [] str = sjhm.toCharArray();
//                    StringBuilder sb = new StringBuilder();
//                    sb.append(str);
//                    sb.replace(3, 7, "****");
//                    userInfo.put("DLMM", sb.toString().toLowerCase());
//                }
                if (userInfo.get("ZZJGDM")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("ZZJGDM").toString())) {
                    String sjhm = userInfo.get("ZZJGDM").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(6, 14, "********");
                    userInfo.put("ZZJGDM", sb.toString().toLowerCase());
                }
                if (userInfo.get("DHHM")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("DHHM").toString())) {
                    String sjhm = userInfo.get("DHHM").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(3, 7, "****");
                    userInfo.put("DHHM", sb.toString().toLowerCase());
                }
                if (userInfo.get("JBRSFZ")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("JBRSFZ").toString())) {
                    String sjhm = userInfo.get("JBRSFZ").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(6, 14, "********");
                    userInfo.put("JBRSFZ", sb.toString().toLowerCase());
                }
                if (userInfo.get("ORG_LAW_IDCARD")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("ORG_LAW_IDCARD").toString())) {
                    String sjhm = userInfo.get("ORG_LAW_IDCARD").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(6, 14, "********");
                    userInfo.put("ORG_LAW_IDCARD", sb.toString().toLowerCase());
                }
                if (userInfo.get("MOBILE_PHONE")!=null
                        &&StringUtils.isNotEmpty(userInfo.get("MOBILE_PHONE").toString())) {
                    String sjhm = userInfo.get("MOBILE_PHONE").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(3, 7, "****");
                    userInfo.put("MOBILE_PHONE", sb.toString().toLowerCase());
                }
            }
            request.setAttribute("userInfo", userInfo);
        }
        return new ModelAndView("system/frontUser/info");
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
        String validateCode = request.getParameter("validateCode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        String yhzh = request.getParameter("YHZH");
        AjaxJson j = new AjaxJson();
        Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                new Object[] { yhzh });
        if (userInfo != null) {
            j.setSuccess(false);
            j.setMsg("账号已存在");
        } else if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(kaRandCode)
                && validateCode.equals(kaRandCode)) {
            Map<String, Object> gruserInfo = null;
            Map<String, Object> qyuserInfo = null;
            String userType = request.getParameter("USER_TYPE");
            if(userType.equals("1")){
                String zjlx = request.getParameter("ZJLX");
                String zjhm  = request.getParameter("ZJHM");
                gruserInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "ZJLX","ZJHM","USER_TYPE" },
                        new Object[] { zjlx,zjhm,userType});
            }
            if(userType.equals("2")){
                String zzjgdm = request.getParameter("ZZJGDM");
                qyuserInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "ZZJGDM","USER_TYPE" },
                        new Object[] { zzjgdm,userType});
            }
            if(gruserInfo!=null){
                j.setSuccess(false);
                j.setMsg("此证件类型的证件号码已经注册过个人用户！");
            }else if(qyuserInfo!=null){
                j.setSuccess(false);
                j.setMsg("此单位机构代码已被注册企业用户！");
            }else if(gruserInfo==null&&qyuserInfo==null){
                String entityId = request.getParameter("USER_ID");
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                if (StringUtils.isEmpty(entityId)) {
                    //String pass=WebDESUtils.decryption(request.getParameter("DLMM"), "12345678");
                    String pass=Des.strDec(request.getParameter("DLMM"), "1", "2", "3");
                    String qrpass=Des.strDec(request.getParameter("QRDLMM"), "1", "2", "3");
                    String password = StringUtil.getMd5Encode(pass);
                    //String password = StringUtil.getMd5Encode((String)request.getParameter("DLMM"));
                    variables.put("DLMM", password);
                    variables.put("ZCSJ", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    variables.put("YHZT", "1");
                }
                String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO", entityId);
                userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                        new String[]{"USER_ID"},new Object[]{recordId});
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, userInfo);
                j.setMsg("注册成功");
            }
        } else if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(kaRandCode)
                && !validateCode.equals(kaRandCode)) {
            j.setSuccess(false);
            j.setMsg("验证码错误");
        }else{
            j.setSuccess(false);
            j.setMsg("注册失败");
        }
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
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.ZCSJ","desc");
        List<Map<String, Object>> list = userInfoService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "frontUserView")
    public ModelAndView frontUserView(HttpServletRequest request) {
        return new ModelAndView("system/frontUser/frontUserView");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "updateYHZT")
    @ResponseBody
    public AjaxJson updateYHZT(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String status = request.getParameter("YHZT");
        this.userInfoService.updateYHZT(selectColNames, Integer.parseInt(status));
        j.setMsg("更新状态成功");
        return j;
    }

    /**
     * 类型分组列表页面跳转
     * @return 
     * 
     * @return
     */
    @RequestMapping("/swbLogin")
    public ModelAndView swbLogin(HttpServletRequest request, HttpServletResponse response) {
        //获取基本路径
        StringBuffer URL = request.getRequestURL();
        String URI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String basepath = URL.toString().replace(URI, "");
        basepath = basepath+contextPath;
        
        String accessToken =  sysLogService.getAccessToken();
        String swbLogin =  sysLogService.getSwbLogin(accessToken,basepath);
        return new ModelAndView("redirect:" + swbLogin);
    }
    
    /**
     * 
     * 描述    验证用户是否登录
     * @author Danto Huang
     * @created 2020年5月19日 上午9:21:46
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkLogin")
    public ModelAndView checkLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String backurl = request.getParameter("backurl");
        String notmzt = request.getParameter("notmzt");
        if(StringUtils.isNotEmpty(notmzt)){            
            ModelAndView modelAndView = new ModelAndView("redirect:" + backurl);
            /*if("true".equals(notmzt)){
                modelAndView.addObject("loginflag","localtrue");
            }else{
                modelAndView.addObject("loginflag","localfalse");
            }*/
            return modelAndView;
        }else{
            backurl = URLEncoder.encode(backurl, "UTF-8");
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String callerCode = properties.getProperty("callerCode");
            StringBuffer checkUrl = new StringBuffer();
            checkUrl.append("https://mztapp.fujian.gov.cn:8304/dataset/UnifiedController/checkLoginStatus.do");
            checkUrl.append("?checkbackurl=").append(backurl).append("&callerCode=").append(callerCode);
        
            return new ModelAndView("redirect:" + checkUrl.toString());
        }
    }
    
    /**
     * 闽政通单点登录
     * 
     * @param request
     * @return
     */
    @RequestMapping("/mztLogin")
    public ModelAndView mztLogin(HttpServletRequest request) throws Exception {
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        String returnUrl = request.getParameter("returnUrl");
        String type = request.getParameter("type");
        if (null != userInfo) {
            if(type!=null && "1".equals(type)) {//type 1：项目建设登录跳转  其他：其他登录跳转
                return new ModelAndView("redirect:/"+returnUrl);
            }else {
                return new ModelAndView("redirect:/userInfoController.do?wdbj");
            }
        } else {
            if (returnUrl != null && !"".equals(returnUrl)) {
                returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
            }
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String mztUrl = properties.getProperty("MZT_URL");
            String callerCode = properties.getProperty("callerCode");
            String backUrl = "";
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            if (returnUrl != null && !"".equals(returnUrl)) {
                backUrl = URLEncoder.encode(
                        basePath + "userInfoController.do?mztLoginResult&returnUrl=" + returnUrl + "&type=" + type,
                        "UTF-8");
            } else {
                backUrl = URLEncoder.encode(basePath + "userInfoController.do?mztLoginResult&type=" + type, "UTF-8");
            }
            return new ModelAndView("redirect:" + mztUrl + "?callerCode=" + callerCode + "&returnurl=" + backUrl);
        }
    }
    /**
     * 闽政通单点登录回调方法
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params ="mztLoginResult")
    public ModelAndView mztLoginResult(HttpServletRequest request) {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String callerCode = properties.getProperty("callerCode");
        String trustTicket = request.getParameter("trustticket");
        String userCenter = properties.getProperty("goUserCenter_URL");
        String returnUrl = request.getParameter("returnUrl");
        String type = request.getParameter("type");
        if (returnUrl != null && !"".equals(returnUrl)) {
            userCenter = returnUrl;
        }
        if(type!=null && "tzxm".equals(type)) {
            userCenter="govIvestController/index.do";
        }
        String resultData = MztSample.getUserInfo(callerCode, trustTicket);
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/";
        Map<String, Object> result = new HashMap<String, Object>();
        if (resultData != null && !"".equals(resultData)) {
            Map<String, Object> resultMap = sysLogService.mztUserInfor(resultData);
            String yhzh = resultMap.get("YHZH").toString();
            Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { yhzh });
            if (userInfo == null || userInfo.size() == 0) {
                userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "MZT_YHZH" },
                        new Object[] { yhzh });
            }
            if (userInfo != null) {
                userInfo.put("MZT_USER_ID", resultMap.get("MZT_USER_ID"));
                userInfo.put("MZT_USER_TOKEN", resultMap.get("MZT_USER_TOKEN"));
                // 获取用户的状态
                String userStatus = (String) userInfo.get("YHZT");
                if (userStatus.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)) {
                    result.put("success", false);
                    result.put("msg", "抱歉该用户被禁用!");
                } else if (userStatus.equals(AllConstant.WEBSITEUSER_STATUS_FREEZEN)) {
                    result.put("success", false);
                    result.put("msg", "抱歉该用户被锁定!");
                } else {
                    userInfo = sysLogService.getLastLoginInfoForMember(userInfo);
                    HttpSession session = AppUtil.getSession();
                    AppUtil.addMemberToSession(session, userInfo);
                    String yhmc = (String) userInfo.get("YHMC");
                    sysLogService.saveLogForMember("公众用户[" + yhmc + "]登录了网站.", SysLogService.OPERATE_TYPE_LOGIN);
                    result.put("success", true);
                }
            } else {
                result.put("success", false);
                result.put("msg", "用户名或者密码错误!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "用户不存在!");
        }
        String newUrl = basePath + userCenter;
        return new ModelAndView("redirect:" + newUrl);
    }
    /**
     * 类型分组列表页面跳转
     * @return 
     * 
     * @return
     */
    @RequestMapping("/swbRegister")
    public ModelAndView swbRegister(HttpServletRequest request, HttpServletResponse response) {
        //获取基本路径
        StringBuffer URL = request.getRequestURL();
        String URI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String basepath = URL.toString().replace(URI, "");
        basepath = basepath+contextPath;
        
        String userType = request.getParameter("user_type");
        String accessToken =  sysLogService.getAccessToken();
        String swbRegister =  sysLogService.getSwbRegister(accessToken,userType,basepath);
        return new ModelAndView("redirect:" + swbRegister);
    }
    @RequestMapping("/getUserToken")
    public ModelAndView getUserToken(HttpServletRequest request,HttpServletResponse response){
        String userToken = request.getParameter("user_token");
        String accessToken =  sysLogService.getAccessToken();
        Map<String,Object> map = sysLogService.swbUserInfor(accessToken,userToken);
        String username = map.get("YHZH").toString();
        Map<String,Object> result = new HashMap<String,Object>();
        //获取用户类型
        /*String userType = map.get("USER_TYPE").toString();*/
        //获取用户帐号
        String yhzh = username;
        Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                new String[]{"YHZH"},new Object[]{yhzh});
        if(userInfo!=null){
            //获取用户的状态
            String userStatus = (String) userInfo.get("YHZT");
            if(userStatus.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                result.put("success", false);
                result.put("msg","抱歉该用户被禁用!");
            }else if(userStatus.equals(AllConstant.WEBSITEUSER_STATUS_FREEZEN)){
                result.put("success", false);
                result.put("msg","抱歉该用户被锁定!");
            }else{
                userInfo = sysLogService.getLastLoginInfoForMember(userInfo);
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, userInfo);
                String yhmc = (String) userInfo.get("YHMC");
                sysLogService.saveLogForMember("公众用户["+yhmc+"]登录了网站.", SysLogService.OPERATE_TYPE_LOGIN);
                result.put("success", true);
            }
        }else{
            result.put("success", false);
            result.put("msg","用户名或者密码错误!");
        }
        return new ModelAndView("website/index/index");
    }
    @RequestMapping("/login")
    public void login(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> result = new HashMap<String,Object>();
        String validateCode = request.getParameter("validateCode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if(StringUtils.isNotEmpty(validateCode)&&StringUtils.isNotEmpty(kaRandCode)
                    && validateCode.equals(kaRandCode)){
            //获取用户类型
            String userType = request.getParameter("USER_TYPE");
            //获取用户帐号
            String yhzh = request.getParameter("YHZH");
            //获取用户密码
            String dlmm = request.getParameter("DLMM");
            if(StringUtils.isNotEmpty(dlmm) && dlmm.length()>20){
                result.put("success", false);
                result.put("msg","抱歉输入密码过长!");
            }else{ 
                String password = StringUtil.getMd5Encode(dlmm);
                Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                        new String[]{"USER_TYPE","YHZH","DLMM"},new Object[]{userType,yhzh,password});
                if(userInfo!=null){
                    //获取用户的状态
                    String userStatus = (String) userInfo.get("YHZT");
                    if(userStatus.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                        result.put("success", false);
                        result.put("msg","抱歉该用户被禁用!");
                    }else if(userStatus.equals(AllConstant.WEBSITEUSER_STATUS_FREEZEN)){
                        result.put("success", false);
                        result.put("msg","抱歉该用户被锁定!");
                    }else{
                        userInfo = sysLogService.getLastLoginInfoForMember(userInfo);
                        HttpSession session = AppUtil.getSession();
                        AppUtil.addMemberToSession(session, userInfo);
                        String yhmc = (String) userInfo.get("YHMC");
                        sysLogService.saveLogForMember("公众用户["+yhmc+"]登录了网站.", SysLogService.OPERATE_TYPE_LOGIN);
                        result.put("success", true);
                    }
                }else{
                    result.put("success", false);
                    result.put("msg","用户名或者密码错误!");
                }
            }
        }else{
            result.put("success", false);
            result.put("msg", "验证码填写错误!");
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
        String type = request.getParameter("type");
        HttpSession session = AppUtil.getSession();
        session.removeAttribute("curLoginMember");
        /*清空jsessionid*/
        session.invalidate();
        Cookie cookie = request.getCookies()[0];
        cookie.setMaxAge(0);
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String callerCode = properties.getProperty("callerCode");
        String logOutUrl = properties.getProperty("MZT_logOut");
        String returnRrl = properties.getProperty("ptzhsp_logOut");
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ path + "/";
        String returnUrl = "";
        if(type!=null && "project".equals(type)) {//建设项目类型的退出，直接跳转到闽政通登录界面
            returnUrl = basePath + "userInfoController/mztLogin.do";
        }else if("project2020".equals(type)){
            returnUrl = basePath + "projectWebsiteController.do?index";
        } else {
            returnUrl = basePath + returnRrl;
        }
        return new ModelAndView("redirect:" + logOutUrl + callerCode + "&returnurl="+returnUrl);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/9/6 11:30:00
     * @param
     * @return
     */
    @RequestMapping("/newLogout")
    @ResponseBody
    public Map<String,Object> newLogout(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> returnMap = new HashMap<>();
        String origin = request.getHeader("Origin");
        try{
            if (StringUtils.isNotBlank(origin)) {
                response.setHeader("Access-Control-Allow-Origin", origin);
            } else {
                response.setHeader("Access-Control-Allow-Origin", "*");
            }
            String headers = request.getHeader("Access-Control-Request-Headers");
            if (StringUtils.isNotBlank(headers)) {
                response.setHeader("Access-Control-Allow-Headers", headers);
                response.setHeader("Access-Control-Expose-Headers", headers);
            } else {
                response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,Authorization,ybg");
                response.setHeader("Access-Control-Expose-Headers", "Content-Type,Access-Token,Authorization,ybg");
            }
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            returnMap.put("success", true);
            HttpSession session = AppUtil.getSession();
            if (session != null) {
                session.removeAttribute("curLoginMember");
                /*清空jsessionid*/
                session.invalidate();
                Cookie cookie = request.getCookies()[0];
                cookie.setMaxAge(0);
            }
        } catch (Exception e) {
            log.error("退出登录错误");
            returnMap.put("success", false);
        }
        return returnMap;
    }
    
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "wdbj")
    public ModelAndView wdbj(HttpServletRequest request) {
        return new ModelAndView("website/yhzx/wdbj");
    }
    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "wdbjonl")
    public ModelAndView wdbjonl(HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Headers", "origin,x-requested-with,content-type");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST,GET");
        response.addHeader("Access-Control-Max-Age", "30");
        return new ModelAndView("website/yhzx/wdbjonl");
    }
    /**
     * 
     * 描述 获取我的办件
     * @author Faker Li
     * @created 2015年12月1日 上午9:53:25
     * @param request
     * @param response
     */
    @RequestMapping(params ="wdbjlist")
    public void wdbjlist(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = executionService.findfrontWdbjList(page,rows,
                (String)AppUtil.getLoginMember().get("YHZH"));
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List)mapList.get("list"),
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:获取我的办件（给Devbase提供的接口）
     *
     * @author Madison You
     * @created 2020/9/8 11:12:00
     * @param
     * @return
     */
    @RequestMapping("/wdbjlistDevbase")
    @ResponseBody
    public Map<String,Object> wdbjlistDevbase(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String yhzh = request.getParameter("yhzh");
        String itemCode=request.getParameter("itemCode");
        Map<String, Object> mapList = null;
        try{
            
            mapList = executionService.findfrontWdbjList(page,rows, yhzh,itemCode);
        } catch (Exception e) {
            log.error("获取我的办件（给Devbase提供的接口），用户账号为：" + yhzh);
            log.error(e.getMessage(), e);
        }
        return mapList;
    }
    /**
     * 
     * 描述 获取我收货地址
     * @author Faker Li
     * @created 2015年12月1日 上午9:53:25
     * @param request
     * @param response
     */
    @RequestMapping(params ="wdsjdzlist")
    public void wdsjdzlist(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> mapList = userInfoService.findfrontSjdzList(page,rows,
                (String)AppUtil.getLoginMember().get("USER_ID"));
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List)mapList.get("list"),
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 方法del 删除地址
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelDraft")
    @ResponseBody
    public AjaxJson multiDelDraft(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        this.userInfoService.deleteByAddrId(selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 方法del 删除材料
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelMyFile")
    @ResponseBody
    public AjaxJson multiDelMyFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        this.userInfoService.deleteByFileId(selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 方法del 删除地址
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "makeDefault")
    @ResponseBody
    public AjaxJson makeDefault(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String addId = request.getParameter("addId");
        String userId = request.getParameter("userId");
        this.userInfoService.makeDefaultAddr(addId,userId);
        j.setMsg("修改成功");
        return j;
    }
    /**
     * 
     * 描述 地址选择器
     * @author Kester Chen
     * @created 2018-7-30 上午10:45:32
     * @param request
     * @return
     */
    @RequestMapping("/addrSelector")
    public ModelAndView addrSelector(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/addrSelector");
    }
    /**
     * 
     * 描述 获取我的收件地址
     * @author Kester Chen
     * @created 2018-7-30 上午10:48:57
     * @param request
     * @param response
     */
    @RequestMapping(params="findAddrByUserId")
    public void findAddrByUserId(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.IS_DEFAULT", "DESC");
        filter.addSorted("T.CREATE_TIME", "DESC");
        List<Map<String,Object>> list = userInfoService.findAddrByUserId(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 修改前台用户密码
     * 
     * @param request
     * @return
     */
    @RequestMapping(params ="xgmm")
    @ResponseBody
    public AjaxJson xgmm(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String oldmm = request.getParameter("OLDMM");
        String password = StringUtil.getMd5Encode(oldmm);
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        if(StringUtils.isNotEmpty(oldmm)&&userInfo!=null){
            if(password.equals((String) userInfo.get("DLMM"))){
                Map<String, Object> variables = new HashMap<String, Object>();
                String newPassword = StringUtil.getMd5Encode((String)request.getParameter("DLMM"));
                variables.put("USER_ID",(String) userInfo.get("USER_ID"));
                variables.put("DLMM",newPassword);
                String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO",
                        (String) userInfo.get("USER_ID"));
                userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                        new String[]{"USER_ID"},new Object[]{recordId});
                HttpSession session = AppUtil.getSession();
                AppUtil.addMemberToSession(session, userInfo);
                j.setMsg("修改密码成功!");
            }else{
                j.setSuccess(false);
                j.setMsg("当前使用的登录密码错误!");
            }
        }else{
            j.setSuccess(false);
            j.setMsg("修改失败！");
        }
        return j;
    }
    /**
     * 修改用户信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "updateUserInfo")
    @ResponseBody
    public AjaxJson updateUserInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        String userId = request.getParameter("USER_ID");
        if(userInfo!=null&&userId.equals((String) userInfo.get("USER_ID"))){
            variables.put("USER_ID",(String) userInfo.get("USER_ID"));
            String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO",
                    (String) userInfo.get("USER_ID"));
            userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"USER_ID"},new Object[]{recordId});
            HttpSession session = AppUtil.getSession();
            AppUtil.addMemberToSession(session, userInfo);
            j.setMsg("修改成功！");
        }else{
            j.setMsg("修改失败！");
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 保存用户收件地址信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveUserRecAdd")
    @ResponseBody
    public AjaxJson saveUserRecAdd(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String USER_ID = variables.get("USER_ID") == null ? "" : variables.get("USER_ID").toString();
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        // 登录后才可继续保存
        if (StringUtils.isNotEmpty(USER_ID) && null != userInfo && userInfo.size() > 0) {
            String loginUserId = (String) userInfo.get("USER_ID");
            if (loginUserId.equals(USER_ID)) { // 登录ID与表单用户ID一致才可继续保存
                String isDefault = variables.get("IS_DEFAULT") == null ? "" : variables.get("IS_DEFAULT").toString();
                if (StringUtils.isNotEmpty(isDefault) && "1".equals(isDefault)) {
                    // 更新默认地址
                    userInfoService.updateIsDefault(variables.get("USER_ID").toString());
                }
                userInfoService.saveOrUpdate(variables, "T_BSFW_USERRECADD", null);
                j.setMsg("保存成功！");
            } else {
                j.setSuccess(false);
                j.setMsg("用户信息不一致无法保存！");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("请登录后再进行保存！");
        }
        return j;
    }
    /**
     * 保存我的材料
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveUserFile")
    @ResponseBody
    public AjaxJson saveUserFile(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String RESULT_FILE_ID = variables.get("RESULT_FILE_ID")==null?
                "":variables.get("RESULT_FILE_ID").toString();
        String USER_ID = variables.get("USER_ID")==null?
                "":variables.get("USER_ID").toString();
        String UPLOADER_NAME = variables.get("UPLOADER_NAME")==null?
                "":variables.get("UPLOADER_NAME").toString();
        String SQRSFZ = variables.get("SQRSFZ")==null?
                "":variables.get("SQRSFZ").toString();
        String SQJG_CODE = variables.get("SQJG_CODE")==null?
                "":variables.get("SQJG_CODE").toString();
        
        StringBuffer sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH T ");
        sql.append("SET T.UPLOADER_ID=?,T.UPLOADER_NAME=?,T.bus_tablerecordid='wdcl',");
        sql.append("T.SQRSFZ=?,T.SQJG_CODE=? WHERE T.FILE_ID=?");
        String[] FILE_ID = RESULT_FILE_ID.split(",");
        for (int i = 0; i < FILE_ID.length; i++) {
            String fileId = FILE_ID[i];
            dao.executeSql(sql.toString(),new Object[]{USER_ID,UPLOADER_NAME,SQRSFZ,SQJG_CODE,fileId});
        }
        
        j.setMsg("保存成功！");
        return j;
    }
    /**
     * easyui AJAX请求数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "wdcllist")
    public void wdcllist(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("userId", sysUser.getUserId());
        List<Map<String,Object>> wdcllist=fileAttachService.wdcllist(variables);
        this.setListToJsonString(wdcllist.size(), wdcllist,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 验证账号是否存在
     * @author Faker Li
     * @created 2016年1月8日 下午3:25:01
     * @param request
     * @return
     */
    @RequestMapping("/zhsfcz")
    @ResponseBody
    public void zhsfcz(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<String,Object>();
        String validateCode = request.getParameter("validateCode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if(StringUtils.isNotEmpty(validateCode)&&StringUtils.isNotEmpty(kaRandCode)
                    && validateCode.equals(kaRandCode)){
            //获取用户帐号
            String yhzh = request.getParameter("yhzh");
            Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{yhzh});
            if(userInfo!=null){
                result.put("success", true);
                String msg = userInfo.get("SJHM").toString();
                msg = msg.substring(0,3) + "****" + msg.substring(7, msg.length());
                result.put("msg", msg);
            }else{
                result.put("success", false);
                result.put("msg", "请重新查询!");
            }
        }else{
            result.put("success", false);
            result.put("msg", "验证码填写错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);

    }
    /**
     * 
     * 描述 发送短信
     * @author Faker Li
     * @created 2016年1月8日 下午3:45:45
     * @param request
     * @param response
     */
    @RequestMapping("/fsdx")
    @ResponseBody
    public void fsdx(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<String,Object>();
        //获取用户帐号
        String yhzh = request.getParameter("yhzh");
        if(StringUtils.isNotEmpty(yhzh)){
            Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{yhzh});
            if(userInfo!=null){
                Map<String,Object> variables = new HashMap<String, Object>();
                variables.put("USER_ID", userInfo.get("USER_ID"));
                variables.put("YZMFSSJ", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                String dxyzm = StringUtil.getRandomIntNumber(10000)+"";
                dxyzm = StringUtil.getFormatNumber(4, dxyzm);
                variables.put("DXYZM",dxyzm);
                userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO",
                        (String) userInfo.get("USER_ID"));
                String dxnl = "您的手机验证码是：" + dxyzm
                        + "，验证码有效时长为15分钟，15分钟后请重新点击发送验证码进行验证。【平潭网上办事大厅】";
                log.info("发送短信号码:"+userInfo.get("SJHM")+";"+dxnl);
                Map<String, Object> resultMap 
                    = SendMsgUtil.immediatelySendMsg(dxnl,(String)userInfo.get("YHZH"),(String) userInfo.get("SJHM"));
                if((Boolean)resultMap.get("result")){
                    result.put("success", true);
                    result.put("msg", "验证码已发送，15分钟内有效");
                }else{
                    result.put("success", false);
                    result.put("msg", "发送短信失败");
                }
                
            }else{
                result.put("success", false);
                result.put("msg", "发送短信失败");
            }
        }else{
            result.put("success", false);
            result.put("msg", "发送短信失败!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);

    }
    /**
     * 
     * 描述 验证短信验证码
     * @author Faker Li
     * @created 2016年1月8日 下午4:44:26
     * @param request
     * @param response
     */
    @RequestMapping("/yzdxyzm")
    @ResponseBody
    public void yzdxyzm(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<String,Object>();
        //获取用户帐号
        String yhzh = request.getParameter("yhzh");
        String yzm = request.getParameter("yzm");
        if(StringUtils.isNotEmpty(yhzh)&&StringUtils.isNotEmpty(yzm)){
            Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{yhzh});
            if(userInfo!=null&&StringUtils.isNotEmpty((String)userInfo.get("YZMFSSJ"))){
                long sc = DateTimeUtil.getIntervalMinute((String)userInfo.get("YZMFSSJ"), 
                        DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if(sc>15){
                    result.put("success", false);
                    result.put("msg", "验证码失效，请重新获取验证码!");
                }else{
                    if(yzm.equals(userInfo.get("DXYZM").toString())){
                        result.put("success", true);
                    }else{
                        result.put("success", false);
                        result.put("msg", "验证码错误，请重新获取验证码!");
                    }
                }
            }else{
                result.put("success", false);
                result.put("msg", "验证失败!");
            }
        }else{
            result.put("success", false);
            result.put("msg", "验证失败!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);

    }
    /**
     * 
     * 描述 发送短信
     * @author Faker Li
     * @created 2016年1月8日 下午3:45:45
     * @param request
     * @param response
     */
    @RequestMapping("/czmm")
    @ResponseBody
    public void czmm(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<String,Object>();
        //获取用户帐号
        String yhzh = request.getParameter("yhzh");
        if(StringUtils.isNotEmpty(yhzh)){
            Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"YHZH"},new Object[]{yhzh});
            if(userInfo!=null){
                Map<String,Object> variables = new HashMap<String, Object>();
                variables.put("USER_ID", userInfo.get("USER_ID"));
                String oldmm = request.getParameter("DLMM");
                String password = StringUtil.getMd5Encode(oldmm);
                variables.put("DLMM",password);
                userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO",
                        (String) userInfo.get("USER_ID"));
                result.put("success", true);
                result.put("msg", "重置密码成功!");
            }else{
                result.put("success", false);
                result.put("msg", "重置密码失败!");
            }
        }else{
            result.put("success", false);
            result.put("msg", "重置密码失败!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);

    }
    /**
     * 
     * 描述 APP用户登录
     * @author Faker Li
     * @created 2016年1月21日 下午4:36:13
     * @param request
     * @param response
     */
    @RequestMapping("/appLogin")
    public void appLogin(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        //获取用户帐号
        String yhzh = request.getParameter("YHZH");
        //获取用户密码
        String dlmm = request.getParameter("DLMM");
        String password = StringUtil.getMd5Encode(dlmm);
        Map<String,Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                new String[]{"YHZH","DLMM"},new Object[]{yhzh,password});
        if(userInfo!=null){
          //获取用户的状态
            String userStatus = (String) userInfo.get("YHZT");
            if(userStatus.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                result.put("success", false);
                result.put("msg","抱歉该用户被禁用!");
            }else if(userStatus.equals(AllConstant.WEBSITEUSER_STATUS_FREEZEN)){
                result.put("success", false);
                result.put("msg","抱歉该用户被锁定!");
            }else {
                String fileId = (String)userInfo.get("FILE_ID");
                if(StringUtils.isNotEmpty(fileId)){
                    Map<String,Object>  fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"},new Object[]{fileId});
                    if(fileAttach!=null){
                        userInfo.put("FILE_PATH",fileAttach.get("FILE_PATH"));
                    }else{
                        userInfo.put("FILE_PATH", ""); 
                    }
                }else{
                    userInfo.put("FILE_PATH", "");
                }
                userInfo.put("IS_SYSTEM", "0");
                result.put("success", true);
                result.put("msg","登录成功!");
                result.put("userInfo",userInfo);
            }
        }else {
            result.put("success", false);
            result.put("msg","用户名或者密码错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 APP注册账号
     * @author Faker Li
     * @created 2016年1月22日 下午3:58:56
     * @param request
     * @return
     */
    @RequestMapping("/appRegistUser")
    @ResponseBody
    public void appRegistUser(HttpServletRequest request,HttpServletResponse response) {
        String yhzh = request.getParameter("YHZH");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                new Object[] { yhzh });
        if (userInfo != null) {
            result.put("success", false);
            result.put("msg","账号已经存在！");
        } else {
            Map<String, Object> gruserInfo = null;
            Map<String, Object> qyuserInfo = null;
            String userType = request.getParameter("USER_TYPE");
            if(userType.equals("1")){
                String zjlx = request.getParameter("ZJLX");
                String zjhm  = request.getParameter("ZJHM");
                gruserInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "ZJLX","ZJHM","USER_TYPE" },
                        new Object[] { zjlx,zjhm,userType});
            }
            if(userType.equals("2")){
                String zzjgdm = request.getParameter("ZZJGDM");
                qyuserInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "ZZJGDM","USER_TYPE" },
                        new Object[] { zzjgdm,userType});
            }
            if(gruserInfo!=null){
                result.put("success", false);
                result.put("msg","此证件类型的证件号码已经注册过个人用户！");
            }else if(qyuserInfo!=null){
                result.put("success", false);
                result.put("msg","此单位机构代码已被注册企业用户！");
            }else if(gruserInfo==null&&qyuserInfo==null){
                String entityId = request.getParameter("USER_ID");
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                if (StringUtils.isEmpty(entityId)) {
                    String password = StringUtil.getMd5Encode((String)request.getParameter("DLMM"));
                    variables.put("DLMM", password);
                    variables.put("ZCSJ", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    variables.put("YHZT", "1");
                }
                String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO", entityId);
                userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                        new String[]{"USER_ID"},new Object[]{recordId});
                String fileId = (String)userInfo.get("FILE_ID");
                if(StringUtils.isNotEmpty(fileId)){
                    Map<String,Object>  fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                        new String[]{"FILE_ID"},new Object[]{fileId});
                    if(fileAttach!=null){
                        userInfo.put("FILE_PATH",fileAttach.get("FILE_PATH"));
                    }else{
                        userInfo.put("FILE_PATH", ""); 
                    }
                }else{
                    userInfo.put("FILE_PATH", "");
                }
                userInfo.put("IS_SYSTEM", "0");
                result.put("success", true);
                result.put("msg","注册成功!");
                result.put("userInfo",userInfo);
            }
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 app修改密码
     * @author Faker Li
     * @created 2016年1月28日 上午10:09:25
     * @param request
     * @param response
     */
    @RequestMapping("/appMmxg")
    public void appMmxg(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();
        String oldmm = request.getParameter("OLDDLMM");
        String password = StringUtil.getMd5Encode(oldmm);
        String userId = request.getParameter("USER_ID");
        if(StringUtils.isNotEmpty(userId)){
            Map<String, Object> userInfo  = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"USER_ID"},new Object[]{userId});
            if(userInfo.get("DLMM").toString().equals(password)){
                Map<String, Object> variables = new HashMap<String, Object>();
                String newPassword = StringUtil.getMd5Encode((String)request.getParameter("DLMM"));
                variables.put("USER_ID",(String) userInfo.get("USER_ID"));
                variables.put("DLMM",newPassword);
                String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO",
                        (String) userInfo.get("USER_ID"));
                userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                        new String[]{"USER_ID"},new Object[]{recordId});
                userInfo.put("IS_SYSTEM", "0");
                result.put("success", true);
                result.put("msg","修改密码成功!");
                result.put("userInfo",userInfo);
            }else{
                result.put("success", false);
                result.put("msg","旧密码错误!");
            }
        }else{
            result.put("success", false);
            result.put("msg","修改密码失败!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 APP修改绑定手机
     * @author Faker Li
     * @created 2016年1月28日 上午10:46:57
     * @param request
     * @param response
     */
    @RequestMapping("/appBdsj")
    public void appBdsj(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();
        String sjhm = request.getParameter("SJHM");
        String userId = request.getParameter("USER_ID");
        if(StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(sjhm)){
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("USER_ID",userId);
            variables.put("SJHM",sjhm);
            String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO",
                    userId);
            Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[]{"USER_ID"},new Object[]{recordId});
            userInfo.put("IS_SYSTEM", "0");
            result.put("success", true);
            result.put("msg","绑定新手机号码成功!");
            result.put("userInfo",userInfo);
        }else{
            result.put("success", false);
            result.put("msg","绑定新手机号码失败!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 app获取我的办件列表
     * @author Faker Li
     * @created 2016年1月28日 上午11:02:08
     * @param request
     * @param response
     */
    @RequestMapping("/appWdbjlist")
    public void appWdbjlist(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String yhzh = request.getParameter("YHZH");
        Map<String, Object> mapList = executionService.findfrontWdbjList(page,rows,yhzh);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List)mapList.get("list"),
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 APP用户登录
     * @author Faker Li
     * @created 2016年1月21日 下午4:36:13
     * @param request
     * @param response
     */
    @RequestMapping("/appSysUserLogin")
    public void appSysUserLogin(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        //获取用户帐号
        String yhzh = request.getParameter("YHZH");
        //获取用户密码
        String dlmm = request.getParameter("DLMM");
        String password = StringUtil.encryptSha256(dlmm);
        int status = sysUserService.isExistsUser(yhzh, password);
        if(status == SysUser.STATUS_ACTIVE){
            Map<String,Object> sysUser = sysUserService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                    new String[]{"USERNAME","PASSWORD"},new Object[]{yhzh,password});
            Map<String,Object> userInfo = new HashMap<String, Object>();
            userInfo.put("YHZH",(String)sysUser.get("USERNAME"));
            userInfo.put("YHMC",(String)sysUser.get("FULLNAME"));
            userInfo.put("USER_TYPE","3");
            userInfo.put("SJHM",(String)sysUser.get("MOBILE"));
            userInfo.put("IS_SYSTEM", "1");
            result.put("success", true);
            result.put("msg","登录成功!");
            result.put("userInfo",userInfo);
            String username = (String)sysUser.get("USERNAME");
            sysLogService.saveLogForApp("用户["+username+"]登录了系统（移动端）.", SysLogService.OPERATE_TYPE_LOGIN,sysUser);
        }else if(status == SysUser.STATUS_FREEZEN){
            result.put("success", false);
            result.put("msg","该用户被禁用!");
        }else{
            
            result.put("success", false);
            result.put("msg","用户名或者密码错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述 app获取我的代办列表
     * @author Faker Li
     * @created 2016年1月28日 上午11:02:08
     * @param request
     * @param response
     */
    @RequestMapping("/appWddblist")
    public void appWddblist(HttpServletRequest request,HttpServletResponse response){
        String userAccount = (String) request.getParameter("YHZH");
        List<Map<String,Object>> userTaskList = flowTaskService.findWebSiteUserTask(userAccount);
        String json = JSON.toJSONString(userTaskList);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "sqcb")
    public ModelAndView sqcb(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        Map<String, Object> revoke = userInfoService.getByJdbc("JBPM6_REVOKE",
                new String[]{"EXE_ID","REVOKE_STATUS"},new Object[]{exeId,0});
        request.setAttribute("EXE_ID", exeId);
        if(null!=revoke && revoke.size()>0){
            request.setAttribute("revoke", revoke);            
        }
        return new ModelAndView("website/yhzx/sqcb");
    }
    /**
     * 用户申请撤销办事
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params ="revoke")
    @ResponseBody
    public AjaxJson revoke(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String exeId = request.getParameter("EXE_ID");
        String REVOKE_ID = request.getParameter("REVOKE_ID");
        String REVOKE_STATUS = request.getParameter("REVOKE_STATUS");
        String AUDIT_CONTENT = request.getParameter("AUDIT_CONTENT");
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isNotEmpty(exeId)) {
            if(StringUtils.isEmpty(REVOKE_ID)){
                variables.put("USER_ID", userInfo.get("USER_ID"));                
            }else{
                SysUser sysUser = AppUtil.getLoginUser();    
                variables.put("AUDIT_USER_ID", sysUser.getUserId());            
                variables.put("AUDIT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            Map<String, Object> revoke = userInfoService.getByJdbc("JBPM6_REVOKE", new String[] { "EXE_ID",
                    "REVOKE_STATUS" }, new Object[] { exeId, 0 });
            if (StringUtils.isEmpty(REVOKE_ID) && null != revoke && revoke.size() > 0) {
                j.setSuccess(false);
                j.setMsg("不要重复申请撤办！");
            } else {
                userInfoService.saveOrUpdate(variables, "JBPM6_REVOKE", REVOKE_ID);
                if (StringUtils.isNotEmpty(REVOKE_ID) && StringUtils.isNotEmpty("REVOKE_STATUS")
                        && "1".equals(REVOKE_STATUS)) {
                     //追回流程
                    //executionService.getBackMyApply(exeId);
                    executionService.endByExeId(exeId,5, AUDIT_CONTENT);
                    j.setMsg("提交成功!");
                }else{
                    j.setMsg("提交成功!");
                }
            }
        } else {
            j.setSuccess(false);
            j.setMsg("提交失败！");
        }
        return j;
    }
    
    /**
     * 
     * 描述 是否有撤办
     * @author Rider Chen
     * @created 2017年5月9日 上午9:11:49
     * @param request
     * @return
     */
    @RequestMapping("/isRevoke")
    public void isRevoke(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> result = new HashMap<String,Object>();
        String exeId = request.getParameter("exeId");
        Map<String, Object> revoke = userInfoService.getByJdbc("JBPM6_REVOKE",
                new String[]{"EXE_ID","REVOKE_STATUS"},new Object[]{exeId,0});
        if(null!=revoke && revoke.size()>0){ 
            result.put("success", true);
            result.put("msg","有撤办!"); 
        }else{

            result.put("success", false);
            result.put("msg","无撤办!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    
}
