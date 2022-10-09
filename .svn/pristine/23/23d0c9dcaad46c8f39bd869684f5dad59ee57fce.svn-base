/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.flowchart.service.TacheFlowService;
import net.evecom.platform.weixin.model.SHA1;
import net.evecom.platform.weixin.model.SignUtil;
import net.evecom.platform.weixin.model.WechatProcess;
import net.evecom.platform.weixin.service.WHomeService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.star.io.XStream;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
@Controller
@RequestMapping("/whome")
public class WHomeController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WHomeController.class);
    /**
     * service
     */
    @Resource
    private WHomeService whomeService;
    
    
    @RequestMapping(params ="chat", method = { RequestMethod.GET, RequestMethod.POST })  
    @ResponseBody  
    public void liaotian(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {  
        log.info("进入chat");  
        boolean isGet = request.getMethod().toLowerCase().equals("get");  
        if (isGet) {  
            String signature = request.getParameter("signature");  
            String timestamp = request.getParameter("timestamp");  
            String nonce = request.getParameter("nonce");  
            String echostr = request.getParameter("echostr");  
            log.info(signature);  
            log.info(timestamp);  
            log.info(nonce);  
            log.info(echostr);
            PrintWriter out=response.getWriter();
            //access(request, response);  
         // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.info(echostr);
                out.print(echostr);
            }
        } else {  
            // 进入POST聊天处理  
            log.info("enter post");  
            try {  
                // 接收消息并返回消息  
                log.info("验证成功，进行接收消息并返回消息");
                //acceptMessage(request, response);  
            } catch (Exception e) {  
                log.error(e.getMessage());  
            }  
        }  
    }  
    
//    @RequestMapping(params ="chatpost", method = { RequestMethod.GET, RequestMethod.POST })  
//    @ResponseBody  
//    public void wechat(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {  
//        request.setCharacterEncoding("UTF-8");  
//        response.setCharacterEncoding("UTF-8");  
//  
//        /** 读取接收到的xml消息 */  
//        StringBuffer sb = new StringBuffer();  
//        InputStream is = request.getInputStream();  
//        InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
//        BufferedReader br = new BufferedReader(isr);  
//        String s = "";  
//        while ((s = br.readLine()) != null) {  
//            sb.append(s);  
//        }  
//        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据  
//  
//        String result = "";  
//        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */  
//        String echostr = request.getParameter("echostr");  
//        if (echostr != null && echostr.length() > 1) {  
//            result = echostr;  
//        } else {  
//            //正常的微信处理流程  
//            result = new WechatProcess().processWechatMag(xml);  
//        }  
//  
//        try {  
//            OutputStream os = response.getOutputStream();  
//            os.write(result.getBytes("UTF-8"));  
//            os.flush();  
//            os.close();  
//        } catch (Exception e) {  
//            log.error(e.getMessage());  
//        }  
//    }  
    
    /**  
     * 验证URL真实性  
     *   
     * @param request  
     * @param response  
     * @return String  
     */  
    private String access(HttpServletRequest request, HttpServletResponse response) {  
        // 验证URL真实性  
        log.info("进入验证access");  
        String signature = request.getParameter("signature");// 微信加密签名  
        String timestamp = request.getParameter("timestamp");// 时间戳  
        String nonce = request.getParameter("nonce");// 随机数  
        String echostr = request.getParameter("echostr");// 随机字符串  
        List<String> params = new ArrayList<String>();  
        params.add("weixinCourse");  
        params.add(timestamp);  
        params.add(nonce);  
        // 1. 将token、timestamp、nonce三个参数进行字典序排序  
        Collections.sort(params, new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                return o1.compareTo(o2);  
            }  
        });  
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密  
        String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));  
        if (temp.equals(signature)) {  
            try {  
                response.getWriter().write(echostr);  
                log.info("成功返回 echostr：" + echostr);  
                return echostr;  
            } catch (IOException e) {  
                log.error(e.getMessage());  
            }  
        }  
        log.info("失败 认证");  
        return null;  
    }  
    
    private void acceptMessage(HttpServletRequest request, HttpServletResponse response) 
        throws IOException{
//        String respMessage = whomeService.getWechatPost(request);
        PrintWriter out = response.getWriter();
//        out.print(respMessage);
        out.close();
    }
//    private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException { 
//        /** 读取接收到的xml消息 */  
//        StringBuffer sb = new StringBuffer();  
//        InputStream is = request.getInputStream();  
//        InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
//        BufferedReader br = new BufferedReader(isr);  
//        String s = "";  
//        while ((s = br.readLine()) != null) {  
//            sb.append(s);  
//        }  
//        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据  
//  
//        String result = "";  
//        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */  
//        String echostr = request.getParameter("echostr");  
//        if (echostr != null && echostr.length() > 1) {  
//            result = echostr;  
//        } else {  
//            //正常的微信处理流程  
//            result = new WechatProcess().processWechatMag(xml);  
//        }  
//  
//        try {  
//            OutputStream os = response.getOutputStream();  
//            os.write(result.getBytes("UTF-8"));  
//            os.flush();  
//            os.close();  
//        } catch (Exception e) {  
//            log.error(e.getMessage());  
//        }  
//        
//        
//        // 处理接收消息  
//        ServletInputStream in = request.getInputStream();  
//        // 将POST流转换为XStream对象  
//        XStream xs = SerializeXmlUtil.createXstream();  
//        xs.processAnnotations(InputMessage.class);  
//        xs.processAnnotations(OutputMessage.class);  
//        // 将指定节点下的xml节点数据映射为对象  
//        xs.alias("xml", InputMessage.class);  
//        // 将流转换为字符串  
//        StringBuilder xmlMsg = new StringBuilder();  
//        byte[] b = new byte[4096];  
//        for (int n; (n = in.read(b)) != -1;) {  
//            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
//        }  
//        // 将xml内容转换为InputMessage对象  
//        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());  
//  
//        String servername = inputMsg.getToUserName();// 服务端  
//        String custermname = inputMsg.getFromUserName();// 客户端  
//        long createTime = inputMsg.getCreateTime();// 接收时间  
//        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
//  
//        // 取得消息类型  
//        String msgType = inputMsg.getMsgType();  
//        // 根据消息类型获取对应的消息内容  
//        if (msgType.equals(MsgType.Text.toString())) {  
//            // 文本消息  
//            log.info("开发者微信号：" + inputMsg.getToUserName());  
//            log.info("发送方帐号：" + inputMsg.getFromUserName());  
//            log.info("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));  
//            log.info("消息内容：" + inputMsg.getContent());  
//            log.info("消息Id：" + inputMsg.getMsgId());  
//  
//            StringBuffer str = new StringBuffer();  
//            str.append("<xml>");  
//            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");  
//            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");  
//            str.append("<CreateTime>" + returnTime + "</CreateTime>");  
//            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");  
//            str.append("<Content><![CDATA[你说的是：" + inputMsg.getContent() + "，吗？]]></Content>");  
//            str.append("</xml>");  
//            log.info(str.toString());  
//            response.getWriter().write(str.toString());  
//        }  
//        // 获取并返回多图片消息  
//        if (msgType.equals(MsgType.Image.toString())) {  
//            log.info("获取多媒体信息");  
//            log.info("多媒体文件id：" + inputMsg.getMediaId());  
//            log.info("图片链接：" + inputMsg.getPicUrl());  
//            log.info("消息id，64位整型：" + inputMsg.getMsgId());  
//  
//            OutputMessage outputMsg = new OutputMessage();  
//            outputMsg.setFromUserName(servername);  
//            outputMsg.setToUserName(custermname);  
//            outputMsg.setCreateTime(returnTime);  
//            outputMsg.setMsgType(msgType);  
//            ImageMessage images = new ImageMessage();  
//            images.setMediaId(inputMsg.getMediaId());  
//            outputMsg.setImage(images);  
//            log.info("xml转换：/n" + xs.toXML(outputMsg));  
//            response.getWriter().write(xs.toXML(outputMsg));  
//  
//        }  
//    }  
}
