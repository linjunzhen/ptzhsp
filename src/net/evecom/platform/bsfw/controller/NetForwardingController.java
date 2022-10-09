/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 描述 网络转发控制器(公用网络区请求转发到互联网)
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年11月23日 上午09:11:00
 */
@Controller
@RequestMapping("netForwardingController")
public class NetForwardingController extends BaseController{

    /**
     * logger
     */
    private static Log logger = LogFactory.getLog(NetForwardingController.class);

    /**
     * 描述:转发到互联网服务器
     * request中需要带入的参数：
     *      url:请求地址，必传
     *      headJson:请求头，可为null，可不传
     *      json:如果传输方式为json，则不为空
     *      method:HttpSendUtil中的方法，必传
     * @author Madison You
     * @created 2020/11/24 8:57:00
     * @param
     * @return
     */
    @RequestMapping("/forwardInternet")
    @ResponseBody
    public Map<String, Object> forwardInternet(HttpServletRequest request) throws Exception {
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String url = StringUtil.getValue(requestMap, "url");
        String headJson = StringUtil.getValue(requestMap, "headJson");
        String method = StringUtil.getValue(requestMap, "method");
        Map<String,String> headMap = new HashMap<>();
        if (StringUtils.isNotEmpty(headJson)) {
            headMap = JSON.parseObject(headJson, Map.class);
        }
        requestMap.remove("url");
        requestMap.remove("headJson");
        requestMap.remove("method");
        Map<String, Object> returnMap = null;
        String result = "";
        if (Objects.equals(method, "sendHttpPostJson")) {
            JSONObject json = new JSONObject(requestMap);
            result = HttpSendUtil.sendHttpPostJson(url, headMap, json.toJSONString(), "UTF-8");
        } else if (Objects.equals(method, "sendPostParamsH")) {
            result = HttpSendUtil.sendPostParamsH(url, headMap, requestMap);
        } else if (Objects.equals(method, "sendByteFilePost")) {
            String fileUrl = StringUtil.getValue(requestMap, "fileUrl");
            byte[] bytes = FileUtil.convertUrlFileToBytes(fileUrl);
            result = HttpSendUtil.sendByteFilePost(url, bytes, "UTF-8", headMap, requestMap);
        }
        if (StringUtils.isNotEmpty(result)) {
            returnMap = JSON.parseObject(result, Map.class);
        }
        return returnMap;
    }
    
    /**
     * 
     * 描述  转发联通短信
     * @author Yanisin Shi
     * @param request
     * @throws Exception
     */
    @RequestMapping("/forwardInternetForMsg")
    @ResponseBody
     public Map<String, Object> forwardInternetSjjxxzx(HttpServletRequest request) throws Exception {
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String msg = StringUtil.getValue(requestMap, "msg");
        String mobiles = StringUtil.getValue(requestMap, "mobiles");
        String templetId = StringUtil.getValue(requestMap, "templetId");
        Map<String, Object> sendSmsNew = SmsSendUtil.sendSmsNew(msg, mobiles, templetId);
        logger.info("转发联通短信sendSmsNew" + sendSmsNew);
        return sendSmsNew;
    }
}
    