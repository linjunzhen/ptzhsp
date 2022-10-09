/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.weixin.service.UserBindService;
import net.evecom.platform.weixin.util.WeixinUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述  微信平台用户注册
 * @author Sundy Sun
 * @version v1.0
 *
 */
@Controller
@RequestMapping("userBindController")
public class UserBindController extends BaseController{

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
     * 引入Service
     */
    @Resource
    private UserBindService userBindService;
    /**
     * 引入Service
     */
    @Resource
    private EncryptionService encryptionService;
    
    /**
     * 
     * 跳转到用户绑定
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping(params = "toBind")
    public ModelAndView toBind(HttpServletRequest request) {
        boolean isbind=false;//默认未绑定
        String code = request.getParameter("code");//我们要的code
        log.info(code);
        //String scope=request.getParameter("scope");
//        String openId=(String)AppUtil.getOpenID();
        String openId=(String) AppUtil.getOpenID();
        if(!StringUtils.isNotEmpty(openId)){
            openId=request.getParameter("openID");
            if(StringUtils.isNotEmpty(openId)){
                HttpSession session = AppUtil.getSession();
                AppUtil.addOpenIdToSession(session, openId);
            }else{
                openId=WeixinUtil.getOpenByCode(code);
                HttpSession session = AppUtil.getSession();
                AppUtil.addOpenIdToSession(session, openId);
            }
        }else{
            HttpSession session = AppUtil.getSession();
            AppUtil.addOpenIdToSession(session, openId);
        }
        String where=" where T.OPEN_ID='"+openId+"'";
        List<Map<String, Object>> blist=userBindService.findBindBySql(where);
        if (blist.size()>0) {
            isbind=true;
        } 
        if(isbind){
            Map<String, Object> userInfo=blist.get(0);
            request.setAttribute("userName", userInfo.get("USER_NAME"));
            return new ModelAndView("weixin/bind/binded");
        }else{
            request.setAttribute("hintMsg","您尚未绑定账号");
            return new ModelAndView("weixin/bind/toBind");
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
        String newPwd = StringUtil.getMd5Encode((String)request.getParameter("userPwd"));
        //AjaxJson j = new AjaxJson();
        Map<String,Object> result = new HashMap<String,Object>();
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> user = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] {"YHZH","DLMM"},
                new Object[] {yhzh,newPwd});
        if(user!=null){
            result.put("success", true);
        }else{
            result.put("success", false);
            result.put("msg","用户名或密码错误，请重新输入!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);  
    }
    /**
     * 
     * 跳转到保存成功页面
     * @param request
     * @return
     */
    @RequestMapping(params = "bindSave")
    public ModelAndView bindSave(HttpServletRequest request,HttpServletResponse response) {
        String yhzh = request.getParameter("user_name");
        //AjaxJson j = new AjaxJson();
        Map<String,Object> result = new HashMap<String,Object>();
        String openId=(String) AppUtil.getOpenID();
        //String entityId = request.getParameter("BIND_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        //if(StringUtils.isNotEmpty(openId)){ 
        String newPwd = StringUtil.getMd5Encode((String)request.getParameter("user_pwd"));
        //Map<String, Object> user = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] {"YHZH","DLMM"},
                //new Object[] {yhzh,newPwd});
        String where=" where T.YHZH='"+yhzh+"' and  (T.DLMM='"+newPwd+"' or T.DLMM=T.SJHM) ";
        List<Map<String, Object>> ulist=userBindService.findUserBySql(where);
        if(ulist.size()>0){
            Map<String,Object> map=ulist.get(0);
            //获取用户状态
            String state=(String) map.get("YHZT");
            if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                result.put("success", false);
                result.put("msg","抱歉该用户被禁用!");
                return new ModelAndView("weixin/bind/binderror");
            }else{
                variables.put("OPEN_ID", openId);
                String password = StringUtil.getMd5Encode((String)request.getParameter("user_pwd"));
                variables.put("user_pwd", password);
                variables.put("BIND_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                userBindService.saveOrUpdate(variables, "T_WX_USERBIND", null);
                result.put("success", true);
                result.put("openID",openId);
                request.setAttribute("userName",yhzh);
                return new ModelAndView("weixin/bind/binded");
            }
        }else{
            result.put("success", false);
            result.put("msg","账号密码不匹配!");
            return new ModelAndView("weixin/bind/binderror");
        }
        //}
      
        //return toBind(request);
//        String json = JSON.toJSONString(result);
//        this.setJsonString(json, response);  
    }

    /**
     * 描述:绑定用户
     *
     * @author Madison You
     * @created 2020/5/26 14:29:00
     * @param
     * @return
     */
    @RequestMapping("/newBindSave")
    @ResponseBody
    public Map<String,Object> newBindSave(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("success", true);
        if (variables != null) {
            /*判断集合中是否有参数为空*/
            boolean isParamsEmpty = userBindService.isParamsEmpty(variables);
            if (!isParamsEmpty) {
                String USER_TYPE = StringUtil.getValue(variables, "USER_TYPE");
                Map<String, Object> userInfo = null;
                if (USER_TYPE.equals("1")) {
                    /*个人用户*/
                    List<Map<String, Object>> gryhUserInfoList = userBindService.findGryhUserInfo(variables);
                    if (gryhUserInfoList != null && gryhUserInfoList.size() > 0) {
                        userInfo = gryhUserInfoList.get(0);
                    }
                } else if (USER_TYPE.equals("2")) {
                    /*法人用户*/
                    List<Map<String, Object>> fryhUserInfoList = userBindService.findFryhUserInfo(variables);
                    if (fryhUserInfoList != null && fryhUserInfoList.size() > 0) {
                        userInfo = fryhUserInfoList.get(0);
                    }
                }
                if (userInfo != null) {
                    //获取用户状态
                    String state = (String) userInfo.get("YHZT");
                    if (state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)) {
                        returnMap.put("success", false);
                        returnMap.put("msg", "抱歉，该用户已被禁用");
                    } else {
                        Map<String, Object> bindUserInfo = new HashMap<>();
                        String openId = AppUtil.getOpenID();
                        bindUserInfo.put("OPEN_ID", openId);
                        bindUserInfo.put("USER_PWD", userInfo.get("DLMM"));
                        bindUserInfo.put("USER_NAME", userInfo.get("YHZH"));
                        bindUserInfo.put("BIND_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        userBindService.saveOrUpdate(bindUserInfo, "T_WX_USERBIND", null);
                        returnMap.put("userName", userInfo.get("YHZH"));
                        returnMap.put("msg", "绑定成功");
                    }
                } else {
                    returnMap.put("success", false);
                    returnMap.put("msg", "暂无此用户,请到平潭综合实验区行政服务中心门户网注册登录后再到此页面绑定");
                }
            } else {
                returnMap.put("success", false);
                returnMap.put("msg", "系统错误，请联系管理员");
            }
        } else {
            returnMap.put("success", false);
            returnMap.put("msg", "系统错误，请联系管理员");
        }
        return returnMap;
    }

    /**
     * 描述:已绑定页面
     *
     * @author Madison You
     * @created 2020/5/26 16:47:00
     * @param
     * @return
     */
    @RequestMapping(params = "toBinded")
    public ModelAndView toBinded(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("userName",request.getParameter("userName"));
        return new ModelAndView("weixin/bind/binded");
    }

    /**
     * 保存变更信息
     * 方法：@param request
     * 方法：@param response
     */
    @RequestMapping(params="saveChangeInfo")
    public void saveChangeInfo(HttpServletRequest request,HttpServletResponse response){
        String yhzh = request.getParameter("user_name");
        String oldPwd=request.getParameter("oldPwd");
        //String userpwd=request.getParameter("user_pwd");
        String openId=(String) AppUtil.getOpenID();
        Map<String,Object> result = new HashMap<String,Object>();
        //AjaxJson j = new AjaxJson();
        Map<String, Object> userInfo = userBindService.getByJdbc("T_WX_USERBIND", new String[] {"OPEN_ID"},
                new Object[] {openId});
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (userInfo != null) {
            String pwd=(String) userInfo.get("user_pwd");
            String old=StringUtil.getMd5Encode(oldPwd);
            if(old.equals(pwd)){//判断原密码是否正确
                String newPwd = StringUtil.getMd5Encode((String)request.getParameter("user_pwd"));
                Map<String, Object> user = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] {"YHZH","DLMM"},
                        new Object[] {yhzh,newPwd});
                if(user!=null){//判断新的绑定账号是否存在
                  //获取用户状态
                    String state=(String) user.get("YHZT");
                    if(state.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)){
                        result.put("success", false);
                        result.put("msg","抱歉新用户被禁用!");
                    }else{
                        variables.put("USER_PWD", newPwd);
                        variables.put("BIND_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        String bindId= (String) userInfo.get("BIND_ID");
                        userBindService.saveOrUpdate(variables, "T_WX_USERBIND",bindId);
                        result.put("success", true);
                        result.put("openID",openId);
                    }
                }else{
                    result.put("success", false);
                    result.put("msg","新的账号密码不匹配!");
                }
            }else{
                result.put("success", false);
                result.put("msg","原账号密码不正确!");
            }
        }else{
            result.put("success", false);
            result.put("msg", "原账号未绑定!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 跳转到变更用户绑定
     * @param request
     * @return
     */
    @RequestMapping(params = "toChangeBind")
    public ModelAndView toChangeBind(HttpServletRequest request) {
        return new ModelAndView("weixin/bind/changeUserBind");
    }
    
    /**
     * 
     * 跳转到绑定页面
     * @param request
     * @return
     */
    @RequestMapping(params = "cancelBD")
    public ModelAndView cancelBD(HttpServletRequest request) {
        //1.删除绑定信息，2.去除会话中信息，3，跳转到绑定页面；
        String openId=AppUtil.getOpenID();
        //HttpSession session = AppUtil.getSession();
        //session.removeAttribute("openID");
        //log.info("cancel bind remove open");
        userBindService.remove("T_WX_USERBIND", "OPEN_ID", new Object[] {openId});
        //log.info("cancel bind remove userbind");
        return new ModelAndView("weixin/bind/toBind");
    }
}
