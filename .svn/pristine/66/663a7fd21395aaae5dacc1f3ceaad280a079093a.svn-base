/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FlowDataUtil;
import net.evecom.core.util.JsonValidator;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.flowchart.controller.FlowChartController;
import net.evecom.platform.flowchart.model.TacheFlow;
import net.evecom.platform.weixin.util.WeixinUtil;

/**
 * 描述  微信平台用户注册
 * @author Sundy Sun
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/wregisterController")
public class RegisterController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(RegisterController.class);
    /**
     * 引入Service
     */
    @Resource
    private UserInfoService userInfoService;
    
    /**
     * 
     * 跳转到用户注册
     * @param request
     * @return
     */
    @RequestMapping(params = "register")
    public ModelAndView register(HttpServletRequest request) {
        return new ModelAndView("weixin/register");
    }
    /**
     * 
     * 跳转到用户注册-账号信息界面
     * @param request
     * @return
     */
    @RequestMapping(params = "registerSecond")
    public ModelAndView registerSecond(HttpServletRequest request) {
        String userType = request.getParameter("userType");
        request.setAttribute("userType", userType);
        return new ModelAndView("weixin/registerSecond");
    }
    
    /**
     * 生成
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "createCode")
    public void createCode(HttpServletRequest request, 
            HttpServletResponse response)throws IOException {
        String json = "";
        String tel = request.getParameter("telephone");
        Map<String, Object> flowinfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(tel) && !tel.equals("tel")) {
            String code=WeixinUtil.getRandomString();
            flowinfo.put("sendCode", code);
        }
        json = JSON.toJSONString(flowinfo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
    /**
     * 
     * 跳转到用户注册-资料信息界面
     * @param request
     * @return
     */
    @RequestMapping(params = "registerThird")
    public ModelAndView registerThird(HttpServletRequest request) {
        String userType = request.getParameter("userType");
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        //String telephone = request.getParameter("telephone");
        //String validCode = request.getParameter("validCode");
        request.setAttribute("userType", userType);
        request.setAttribute("userName", userName);
        request.setAttribute("userPwd", userPwd);
        //request.setAttribute("telephone", telephone);
        if("1".equals(userType)){
            return new ModelAndView("weixin/registerThird");
        }else{
            return new ModelAndView("weixin/registerQY");
        }
    }
    
    /**
     * 
     * 验证用户名是否存在
     * @param request
     * @return
     */
    @RequestMapping(params = "checkUser")
    public void checkUser(HttpServletRequest request,HttpServletResponse response) {
        String yhzh = request.getParameter("userName");
        //AjaxJson j = new AjaxJson();
        Map<String,Object> result = new HashMap<String,Object>();
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> user = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] {"YHZH"},
                new Object[] {yhzh});
        if(user!=null){
            result.put("success", false);
            result.put("msg","用户名已经存在，请重新输入!");
        }else{
            result.put("success", true);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);  
    }
    /**
     * 
     * 跳转到用户注册-企业注册2资料信息界面
     * @param request
     * @return
     */
    @RequestMapping(params = "registerQy")
    public ModelAndView registerQy(HttpServletRequest request) {
        String userType = request.getParameter("userType");
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        String YHMC = request.getParameter("YHMC");
        String JGLX = request.getParameter("JGLX");
        String ZZJGDM = request.getParameter("ZZJGDM");
        String YHXB = request.getParameter("YHXB");
        String ZJHM = request.getParameter("ZJHM");
        String SJHM = request.getParameter("SJHM");
        String DHHM = request.getParameter("DHHM");
        String YZBM = request.getParameter("YZBM");
        String DWDZ = request.getParameter("DWDZ");
        String YHZH=request.getParameter("YHZH");
        
        String FRDB = request.getParameter("FRDB");
        
        request.setAttribute("userType", userType);
        request.setAttribute("userName", userName);
        request.setAttribute("userPwd", userPwd);
        request.setAttribute("YHZH", YHZH);
        request.setAttribute("FRDB", FRDB);
        request.setAttribute("YHMC", YHMC);
        request.setAttribute("JGLX", JGLX);
        request.setAttribute("ZZJGDM", ZZJGDM);
        request.setAttribute("YHXB", YHXB);
        request.setAttribute("ZJHM", ZJHM);
        request.setAttribute("SJHM", SJHM);
        request.setAttribute("DHHM", DHHM);
        request.setAttribute("YZBM", YZBM);
        request.setAttribute("DWDZ", DWDZ);
        //request.setAttribute("telephone", telephone);
        return new ModelAndView("weixin/registerQY2");
    }
    /**
     * 
     * 跳转到用户注册-账号信息界面
     * @param request
     * @return
     */
    @RequestMapping(params = "registerSave")
    public ModelAndView registerSave(HttpServletRequest request) {
//        String validateCode = request.getParameter("validateCode");
//        String kaRandCode = (String) request.getSession().getAttribute(
//                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //String yhzh = request.getParameter("YHZH");
        //AjaxJson j = new AjaxJson();
//        Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
//                new Object[] { yhzh });
//        if (userInfo != null) {
//            j.setSuccess(false);
//            j.setMsg("账号已存在");
//            request.setAttribute("msg", "账号已存在");
//        } else {

        String entityId = request.getParameter("USER_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        //if (StringUtils.isEmpty(entityId)) {
        String ps=request.getParameter("DLMM");
        log.info("prehandle passward:"+ps);
        String password = StringUtil.getMd5Encode((String)request.getParameter("DLMM"));
        log.info("md5 passward:"+password);
        variables.put("DLMM", password);
        variables.put("ZCSH", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        variables.put("YHZT", "1");
        //}
        if(request.getParameter("USER_TYPE").equals("1")){
            variables.put("ZZJGDM", "");
            variables.put("FRDB", "");
            variables.put("DWDZ", "");
        }else{
            //variables.put("YHXB", "");
            variables.put("DZYX", "");
            variables.put("ZY", "");
            //variables.put("YZBM", "");
            variables.put("ZTZZ", "");
        }
        String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO", entityId);
        Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO",
                new String[]{"USER_ID"},new Object[]{recordId});
        HttpSession session = AppUtil.getSession();
        AppUtil.addMemberToSession(session, userInfo);
        request.setAttribute("msg", "恭喜，注册成功");
//        }
        return new ModelAndView("weixin/registerSuccess");
    }
}
