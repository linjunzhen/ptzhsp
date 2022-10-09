/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.web.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.AppUtil;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述权限拦截器
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午4:46:24
 */
public class AuthInterceptor implements HandlerInterceptor {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(AuthInterceptor.class);

    /**
     * 后台管理系统无须拦截的URL
     */
    private List<String> excludeUrls;
    /**
     * 网站前台需要被拦截的URL
     */
    private List<String> webSiteAuthUrls;
    /**
     * weixin不被拦截的URL
     */
    private List<String> weixinAuthUrls;

    /**
     * 登录用户Session集合
     */
    public static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:46:24
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2,
            Exception exception) throws Exception {

    }

    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 下午4:46:24
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2,
            ModelAndView modelAndView) throws Exception {

    }

    /**
     * 描述 在请求前拦截
     * @author Flex Hu
     * @created 2014年9月11日 下午4:46:24
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String requestPath = AppUtil.getRequestPath(request);// 用户访问的资源地址
        if (excludeUrls.contains(requestPath)) {
            return true;
        }else if(webSiteAuthUrls!=null&&webSiteAuthUrls.contains(requestPath)){
            Map<String,Object> loginMember = AppUtil.getLoginMember();
            if(loginMember!=null){
                return true;
            }else{
                //定义return的URL地址
                String returnUrl = URLEncoder.encode(AppUtil.getRequestUrl(request),"UTF-8");
                this.contructResponse(request, response, 
                        "/userInfoController/mztLogin.do?returnUrl="+returnUrl,true);
                return false;
            }
        }else if (weixinAuthUrls.contains(requestPath)) {
            String agent=request.getHeader("user-agent");
            String ua =agent.toLowerCase();
            if (ua.indexOf("micromessenger") > 0) {
                return true;
            }
            return false;
        }else{
            String refer = request.getHeader("Referer");
            String serverName = request.getServerName();
            String dictionaryCode = AppUtil.getIsAllowLoginRepeat();
            HttpSession session = AppUtil.getSession();
            SysUser sysUser = AppUtil.getLoginUser();
            PrintWriter out;
            StringBuilder builder = new StringBuilder();
            //后台请求地址与请求来源地址不一致则进行拦截(防止站外访问后台地址)
            if(StringUtils.isEmpty(refer)||!refer.contains(serverName)){
                response.setContentType("text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                out = response.getWriter();
                builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
                builder.append("alert(\'该地址无权限访问！\');");
                builder.append("window.top.location.href=\"");
                builder.append(AppUtil.getWebRootPath(request)).append("/404.html");
                builder.append("\";</script>");
                out.print(builder.toString());
                out.close();
                return false;
            }
            if (sysUser != null) {
                // 判断字典是否允许重复登录（1不允许重复登录，0允许重复登录）
                if (StringUtils.isNotEmpty(dictionaryCode) && "1".equals(dictionaryCode)) {
                    Boolean isLoginRepeated = (Boolean) session.getAttribute("ISLOGINREPEATED");
                    // 判断登录是否重复
                    if (null != isLoginRepeated && isLoginRepeated && !sysUser.getUsername().equals("admin")) {
                        // 设置session失效
                        response.setContentType("text/html;charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        session.invalidate();
                        out = response.getWriter();
                        String header = request.getHeader("X-Requested-With");
                        if(StringUtils.isNotEmpty(header)&&header.equalsIgnoreCase("XMLHttpRequest")){
                            response.setHeader("sessionstatus","timeout");
                            builder.append("loginrepeated");
                            out.print(builder.toString());  
                            out.close();  
                        }else{
                            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
                            builder.append("alert(\'账号在异地登录,本地账号被迫下线！\');");
                            builder.append("window.top.location.href=\"");
                            builder.append(AppUtil.getWebRootPath(request)).append("/loginController.do?login");
                            builder.append("\";</script>");
                            out.print(builder.toString());
                            out.close();
                        }
                        return false;
                    }
                    // 判断本次用户登录是否重复，重复设置ISLOGINREPEATED为true
                    if (sessionMap.containsKey(sysUser.getUsername()) && !sysUser.getUsername().equals("admin")) {
                        HttpSession repeatedSession = sessionMap.get(sysUser.getUsername());
                        if (!repeatedSession.getId().equals(session.getId())) {
                            try {
                                repeatedSession.setAttribute("ISLOGINREPEATED", true);                                
                            } catch (Exception e) {
                                // TODO: handle exception
                                log.debug("用户:"+sysUser.getUsername()+" 登录超时！");
                            }
                            // 移除登录用户MAP中的session集
                            sessionMap.remove(sysUser.getUsername());
                            //加入最新登录用户session
                            sessionMap.put(sysUser.getUsername(), session);                         
                        }
                    } else {
                        // 没有重复登录用户加入到map
                        if (null != request.getSession(false)) {
                            sessionMap.put(sysUser.getUsername(), session);
                            session.setAttribute("ISLOGINREPEATED", false);
                        }
                    }
                }
                
                Set<String> allAnonUrlSet = AppUtil.getAllAnonUrlSet();
                if(allAnonUrlSet.contains(requestPath)||StringUtils.isEmpty(requestPath)){
                    return true;
                }
                
                Set<String> grantUrlSet = sysUser.getUrlSet();
                Set<String> allSessionUrlSet = AppUtil.getAllSessionUrlSet();
                grantUrlSet.addAll(allSessionUrlSet);
                boolean isAllowAccess = false;
                if(sysUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
                    isAllowAccess = true;
                }else {
                    if(grantUrlSet!=null&&grantUrlSet.size()>0){
                        String url = AppUtil.getRequestUrl(request);
                        isAllowAccess = this.isAllowUrl(grantUrlSet, url);
                        
                    }
                }
                if(!isAllowAccess){
                    log.info("被过滤URL："+requestPath);
                    out = response.getWriter();
                    builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
                    builder.append("alert(\"当前用户不具备URL访问权限!\");");  
                    builder.append("window.top.location.href=\""); 
                    builder.append(AppUtil.getWebRootPath(request)).append("/loginController.do?login");
                    builder.append("\";</script>");  
                    out.print(builder.toString());  
                    out.close();
                    return false;
                }
                return true;
            }else{
                this.contructResponse(request, response, "/loginController.do?login",false);
                return false;
            }
        }
    }
    /**
     * 
     * @param grantUrlSet
     * @param url
     * @return
     */
    private boolean isAllowUrl(Set<String> grantUrlSet,String url) {
        boolean isAllowAccess = false;
        for(String grantUrl:grantUrlSet) {
            if(StringUtils.isNotEmpty(grantUrl)){
                isAllowAccess = wildcardEquals(grantUrl,url);
                if(isAllowAccess) {
                    isAllowAccess= true;
                    break;
                }
            }
        }
        return isAllowAccess;
    }
    /**
     * 判断白名单和请求地址是否匹配
     * @param whitePath
     * @param reqPath
     * @return
     */
    public boolean wildcardEquals(String whitePath, String reqPath) {  
        String regPath = getRegPath(whitePath);  
        return Pattern.compile(regPath).matcher(reqPath).matches();  
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 上午9:59:40
     * @param path
     * @return
     */
    public String getRegPath(String path) {  
        char[] chars = path.toCharArray();  
        int len = chars.length;  
        StringBuilder sb = new StringBuilder();  
        boolean preX = false;  
        for(int i=0;i<len;i++){  
            if (chars[i] == '*'){//遇到*字符  
                if (preX){//如果是第二次遇到*，则将**替换成.*  
                    sb.append(".*");  
                    preX = false;  
                }else if(i+1 == len){//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*  
                    sb.append("[^/]*");  
                }else{//否则单星后面还有字符，则不做任何动作，下一把再做动作  
                    preX = true;  
                    continue;  
                }  
            }else{//遇到非*字符  
                if (preX){//如果上一把是*，则先把上一把的*对应的[^/]*添进来  
                    sb.append("[^/]*");  
                    preX = false;  
                }  
                if (chars[i] == '?'){//接着判断当前字符是不是?，是的话替换成.  
                    sb.append('.');  
                }else{//不是?的话，则就是普通字符，直接添进来  
                    sb.append(chars[i]);  
                }  
            }  
        }  
        return sb.toString();  
    }
    /**
     * 
     * 描述 构建请求响应信息
     * @author Flex Hu
     * @created 2014年11月17日 下午4:40:52
     * @param request
     * @param response
     * @param redirectPath
     */
    private void contructResponse(HttpServletRequest request,HttpServletResponse response
            ,String redirectPath,boolean noalert){
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            StringBuilder builder = new StringBuilder();  
            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNotEmpty(header)&&header.equalsIgnoreCase("XMLHttpRequest")){
                response.setHeader("sessionstatus","timeout");
                if(!noalert){
                    builder.append("sessiontimeout");
                }else{
                    builder.append("websessiontimeout");
                }
                //result.put("msg", "您尚未登录或会话过期!");
                //String resultJson = JSON.toJSONString(result);
                out.print(builder.toString());  
                out.close();  
            }else{
                builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
                if(!noalert){
                    builder.append("alert(\"会话过期，请重新登录\");");  
                }
                builder.append("window.top.location.href=\"");  
                builder.append(AppUtil.getWebRootPath(request)).append(redirectPath);  
                builder.append("\";</script>");  
                out.print(builder.toString());  
                out.close();  
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午4:51:07
     * @return type
     */
    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    /**
     * @author Flex Hu
     * @created 2014年9月11日 下午4:51:07
     * @param excludeUrls
     */
    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
    
    /**
     * @author Flex Hu
     * @created 2015年11月23日 上午11:25:55
     * @return type
     */
    public List<String> getWebSiteAuthUrls() {
        return webSiteAuthUrls;
    }

    /**
     * @author Flex Hu
     * @created 2015年11月23日 上午11:25:55
     * @param webSiteAuthUrls
     */
    public void setWebSiteAuthUrls(List<String> webSiteAuthUrls) {
        this.webSiteAuthUrls = webSiteAuthUrls;
    }

    /**
     * get
     * @return
     */
    public List<String> getWeixinAuthUrls() {
        return weixinAuthUrls;
    }
    /**
     * set
     * @param weixinAuthUrls
     */
    public void setWeixinAuthUrls(List<String> weixinAuthUrls) {
        this.weixinAuthUrls = weixinAuthUrls;
    }
    
    
}
