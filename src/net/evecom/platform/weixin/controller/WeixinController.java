/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.weixin.model.SignUtil;
import net.evecom.platform.weixin.service.WHomeService;
import net.evecom.platform.weixin.service.WeixinCoreService;
import net.evecom.platform.weixin.util.MessageUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述  微信公众平台核心
 * @author Sundy Sun
 * @version v2.0
 *
 */
@Controller
@RequestMapping("/weachat")
public class WeixinController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WeixinController.class);
    /**
     * 引入Service
     */
    @Resource
    private WHomeService whomeService;
    
    @RequestMapping(params = "chat", method = { RequestMethod.GET})
    @ResponseBody
    public void checkChat(Model model, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        //log.info("进入chat");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
     // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }
    @RequestMapping(params = "chat", method = { RequestMethod.POST})
    @ResponseBody
    public void liaotian(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //log.info("进入chat 聊天");
     // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        //String respMessage = WeixinCoreService.processRequest(request);
        Map<String, String> requestMap = MessageUtil.parseXml(request);
        
        String respMessage =whomeService.getWechatPost(request,requestMap);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
      
    }
    
    
}
