/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/**
 * 描述 web应用工具类
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:33:45
 */
public class AppUtil implements ApplicationContextAware {
    /**
     * application
     */
    private static ApplicationContext applicationContext;
    /**
     * servletCOntext
     */
    private static ServletContext servletContext;
    /**
     * interfaceCodesMap
     */
    private static Map<String,String> interfaceCodesMap = new HashMap<String,String>();
    /**
     * 是否允许重复登录变量
     */
    private static String isAllowLoginRepeat;
    /**
     * 所有匿名的URL集合
     */
    private static Set<String> allAnonUrlSet = new HashSet<String>();
    /**
     * 所有会话的URL集合
     */
    private static Set<String> allSessionUrlSet = new HashSet<String>();
    /**
     * @author Flex Hu
     * @created 2016年1月13日 上午9:33:38
     * @return type
     */
    public static String getIsAllowLoginRepeat() {
        return isAllowLoginRepeat;
    }
    /**
     * @author Flex Hu
     * @created 2016年1月13日 上午9:33:38
     * @param isAllowLoginRepeat
     */
    public static void setIsAllowLoginRepeat(String isAllowLoginRepeat) {
        AppUtil.isAllowLoginRepeat = isAllowLoginRepeat;
    }
    /**
     * 
     * 描述 获取session中的用户
     * @author Flex Hu
     * @created 2014年9月11日 下午4:44:31
     * @return
     */
    public static SysUser getLoginUser(){
        HttpSession session = AppUtil.getSession();
        SysUser curLoginUser = (SysUser) session.getAttribute("curLoginUser");
        return curLoginUser;
    }
    /**
     * 
     * 描述 返回当前登录用户JSON信息
     * @author Flex Hu
     * @created 2014年9月20日 下午5:30:04
     * @return
     */
    public static String getLoginUserJson(){
        SysUser curLoginUser = AppUtil.getLoginUser();
        return JSON.toJSONString(curLoginUser);
    }    
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:01
     * @param _servletContext
     */
    public static void init(ServletContext _servletContext) {
        servletContext = _servletContext;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:08
     * @param contex
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        applicationContext = contex;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:14
     * @return
     */
    public static ApplicationContext getContext() {
        return applicationContext;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:18
     * @return
     * @throws Exception
     */
    public static ServletContext getServletContext() throws Exception {
        return servletContext;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:21
     * @param cls
     * @return
     */
    public static Object getBean(Class cls) {
        return applicationContext.getBean(cls);
    }
    /**
     * 根据beanId实例化bean
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:25
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }
    /**
     * 获取web应用决定物理路径
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:41
     * @return
     */
    public static String getAppAbsolutePath() {
        return servletContext.getRealPath("/");
    }
    /**
     * 获取web应用真实路径
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:14:56
     * @param path
     * @return
     */
    public static String getRealPath(String path) {
        return servletContext.getRealPath(path);
    }
    /**
     * 获取request对象
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:15:26
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        return request;
    }
    /**
     * 获取session对象
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:15:42
     * @return
     */
    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }
    /**
     * 
     * 描述 将用户加入session中
     * @author Flex Hu
     * @created 2014年9月11日 下午4:40:35
     * @param session
     * @param curLoginUser
     */
    public static void addUserToSession(HttpSession session,SysUser curLoginUser){
        session.setAttribute("curLoginUser", curLoginUser);
    }
    /**
     * 
     * 描述 将会员加入session中
     * @author Flex Hu
     * @created 2015年3月7日 下午7:53:27
     * @param session
     * @param member
     */
    public static void addMemberToSession(HttpSession session,Map<String,Object> member){
        session.setAttribute("curLoginMember", member);
    }
    
    /**
     * 
     * 描述 获取登录的会员
     * @author Flex Hu
     * @created 2015年3月7日 下午7:54:39
     * @return
     */
    public static Map<String,Object> getLoginMember(){
        HttpSession session = AppUtil.getSession();
        Map<String,Object> member = (Map<String,Object>) session.getAttribute("curLoginMember");
        return member;
    }
    /**
     * 
     * 描述：将取号事项信息加入session中
     * @author Rider Chen
     * @created 2019年3月15日 上午9:02:37
     * @param session
     * @param member
     */
    public static void addItemCartToSession(HttpSession session,Map<String,Object> itemCart){
        session.setAttribute("itemCart", itemCart);
    }
    /**
     * 
     * 描述：获取取号事项信息
     * @author Rider Chen
     * @created 2019年3月15日 上午9:01:35
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getItemCart(){
        HttpSession session = AppUtil.getSession();
        Map<String,Object> itemCart = (Map<String, Object>) session.getAttribute("itemCart");
        return itemCart;
    }
    /**
     * 
     * 描述 将openId加入session中
     * @param session
     * @param member
     */
    public static void addOpenIdToSession(HttpSession session,String openId){
        session.setAttribute("openID", openId);
    }
    
    /**
     * 
     * 描述 获取登录的会员
     * @author Sundy Sun
     * @return
     */
    public static String getOpenID(){
        HttpSession session = AppUtil.getSession();
        String openid= (String) session.getAttribute("openID");
        return openid;
    }
    /**
     * 获得请求路径
     * 
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String requestPath = request.getRequestURI() + "?"
                + request.getQueryString();
        if (requestPath.indexOf("&") > -1) {// 去掉其他参数
            requestPath = requestPath.substring(0, requestPath.indexOf("&"));
        }
        requestPath = requestPath
                .substring(request.getContextPath().length() + 1);// 去掉项目路径
        return requestPath;
    }
    
    /**
     * 
     * 描述 获取请求的URL
     * @author Flex Hu
     * @created 2015年12月3日 下午7:35:45
     * @param request
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request){
        String requestPath = request.getRequestURI();
        // 去掉项目路径
        requestPath = requestPath
                .substring(request.getContextPath().length() + 1);
        if(request.getQueryString()!=null){
            requestPath+=("?"+request.getQueryString());
        }
        return requestPath;
    }
    /**
     * 
     * 描述 获取web应用网站请求根路径
     * @author Flex Hu
     * @created 2014年9月11日 下午5:25:27
     * @param request
     * @return
     */
    public static String getWebRootPath(HttpServletRequest request){
        StringBuffer requestPath = request.getRequestURL();
        return requestPath.substring(0, requestPath.lastIndexOf("/"));
    }
    /**
     * 
     * 描述 获取项目的webroot路径
     * @author Flex Hu
     * @created 2015年1月16日 下午2:45:51
     * @return
     */
    public static String getProjectPath(){
        String path = BeanUtil.getClassPath(AppUtil.class);
        return path.substring(0, path.indexOf("WEB-INF"));
    }
    
    /**
     * @author Flex Hu
     * @created 2015年5月28日 上午9:20:37
     * @return type
     */
    public static Map<String, String> getInterfaceCodesMap() {
        return interfaceCodesMap;
    }
    /**
     * @author Flex Hu
     * @created 2015年5月28日 上午9:20:37
     * @param interfaceCodesMap
     */
    public static void setInterfaceCodesMap(Map<String, String> interfaceCodesMap) {
        AppUtil.interfaceCodesMap = interfaceCodesMap;
    }
    /**
     * 获取当前登录用户所授权的业务单位编码集合
     * @return
     */
//    public static String getCurrentUserBusCodeString(){
//        HttpSession session = AppUtil.getSession();
//        Set<String> busCodes = (Set<String>)session.getAttribute("userBusCodes");
//        StringBuffer unitBuffer = new StringBuffer("");
//        for(String buscode : busCodes){
//            if(StringUtils.isNotBlank(unitBuffer) && unitBuffer.length()>0){
//                unitBuffer.append(",");
//            }
//            unitBuffer.append(buscode);
//        }
//        return unitBuffer.toString();
//    }
    /**
     * @author Danto Huang
     * @created 2020年6月23日 上午10:46:09
     * @return type
     */
    public static Set<String> getAllAnonUrlSet() {
        return allAnonUrlSet;
    }
    /**
     * @author Danto Huang
     * @created 2020年6月23日 上午10:46:09
     * @param allAnonUrlSet
     */
    public static void setAllAnonUrlSet(Set<String> allAnonUrlSet) {
        AppUtil.allAnonUrlSet = allAnonUrlSet;
    }
    /**
     * @author Danto Huang
     * @created 2020年6月23日 下午2:31:23
     * @return type
     */
    public static Set<String> getAllSessionUrlSet() {
        return allSessionUrlSet;
    }
    /**
     * @author Danto Huang
     * @created 2020年6月23日 下午2:31:23
     * @param allSessionUrlSet
     */
    public static void setAllSessionUrlSet(Set<String> allSessionUrlSet) {
        AppUtil.allSessionUrlSet = allSessionUrlSet;
    }

}
