/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.weixin.model.WxAccessTokenInfo;
import net.evecom.platform.weixin.service.WxUserService;
import net.evecom.platform.weixin.util.AccessToken;
import net.evecom.platform.weixin.util.JSSDKHelper;
import net.evecom.platform.weixin.util.WeixinUtil;
/**
 * 描述 微信公众号app接口
 * @author Laura Song
 */
@Controller
@RequestMapping("wxAppInterfaceController")
public class WxAppInterfaceController  extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WxAppInterfaceController.class);
    
    /**
     * service
     */
    @Resource
    private WxUserService wxUserService;
    
    /**
     * 描述 首次获取网页授权accesstoken 根据code
     * @author Laura Song
     * @param request
     * @param response
     */
    @RequestMapping("/getToken")
    @ResponseBody
    public void getToken(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取code
        String code  = request.getParameter("code");
        if(code!=null){
         // 设置url 参数等
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String appId=properties.getProperty("APP_ID");
            String appSecret=properties.getProperty("APP_SECRET");
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
            String param = "appid="+appId+"&secret="+appSecret+"&code="+code;
            param += "&grant_type=authorization_code";
            
            String jsonStr = HttpRequestUtil.sendGet(url, param);
            Map<String, Object> map = JsonUtil.parseJSON2Map(jsonStr);
            if(map != null && !map.containsKey("errcode")){
                result.put("success", true);
                result.put("info", map);
            }else{
                result.put("success", false);
                result.put("msg", map.get("errmsg"));
            }
        }else{
            result.put("success", false);
            result.put("msg", "参数缺失");
        }
        
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述 刷新网页授权accesstoken 根据refresh code
     * @author Laura Song
     * @param request
     * @param response
     */
    @RequestMapping("/refreshToken")
    @ResponseBody
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取refresh_token
        String refresh_token  = request.getParameter("refresh_token");
        if(refresh_token!=null){
            // 设置url 参数等
            Properties properties = FileUtil.readProperties("conf/config.properties");
            String appId=properties.getProperty("APP_ID");
            String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
            String param = "appid="+appId;
            param += "&grant_type=refresh_token&refresh_token="+refresh_token;
            
            String jsonStr = HttpRequestUtil.sendGet(url, param);
            Map<String, Object> map = JsonUtil.parseJSON2Map(jsonStr);
            if(map != null && !map.containsKey("errcode")){
                result.put("success", true);
                result.put("info", map);
            }else{
                result.put("success", false);
                result.put("msg", map.get("errmsg"));
            }
        }else{
            result.put("success", false);
            result.put("msg", "参数缺失");
        }
        
        
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 根据openid 获取用户信息
     * @author Laura Song
     * @param request
     * @param response
     */
    @RequestMapping("/getUserByOpenId")
    @ResponseBody
    public void getUserByOpenId(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取openid
        String openid  = request.getParameter("openid");
        if(openid==null){
            result.put("success", false);
            result.put("msg", "参数缺失");
        }else{
            Map<String, Object> user = wxUserService.findUserByOpenId(openid);
            result = user;
        }
        
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 根据网页授权token 获取jssdk配置信息 初始化js-sdk
     * @author Laura Song
     * @param request
     * @param response
     */
    @RequestMapping("/getJssdkInfo")
    @ResponseBody
    public void getWxSignInfo(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取参数
        //String access_token  = request.getParameter("access_token");
        /*Properties properties = FileUtil.readProperties("conf/config.properties");
        String appId=properties.getProperty("APP_ID");
        String appsecret=properties.getProperty("APP_SECRET");
        AccessToken token  = WeixinUtil.getAccessToken(appId, appsecret);
        String access_token = token.getToken();*/
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String appId=properties.getProperty("APP_ID");
        String access_token = WxAccessTokenInfo.accessToken.getTokenName();
        String url  = request.getParameter("url");
        if(access_token==null || url==null){
            result.put("success", false);
            result.put("msg", "参数缺失");
        }else{
            // 获取jsapi_ticket
            SortedMap<Object, Object> params = new TreeMap<Object, Object>();
            String jsapi_ticket = JSSDKHelper.getJsapiTicket(access_token);
            log.info("jsapi_ticket:"+jsapi_ticket);
            String noncestr = JSSDKHelper.getNoncestr();
            String timestamp = JSSDKHelper.getTimestamp();
            params.put("noncestr", noncestr);
            params.put("jsapi_ticket", jsapi_ticket);
            params.put("timestamp", timestamp);
            params.put("url", url.toString());
            String sign = JSSDKHelper.createSignBySha1(params);
            params.put("signature", sign);
            params.put("appId", appId);
            result.put("success", true);
            result.put("info", params);
            
        }
        
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
}

