/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.platform.weixin.model.SignUtil;
/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class CoreServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1211228837243643826L;
    
    /**
     * 请求校验（确认请求来自微信服务器）
     */
    public void doGet(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
     // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        PrintWriter out=response.getWriter();
     // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }
    /**
    * 处理微信服务器发来的消息
    */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO 消息的接收、处理、响应
    }

}
