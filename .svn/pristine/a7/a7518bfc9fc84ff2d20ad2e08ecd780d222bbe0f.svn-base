/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.esb.ESBAction;
import net.evecom.platform.bsfw.esb.ESBParams;
import net.evecom.platform.bsfw.esb.ESBResult;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述   交建-单位员工社保缴纳情况Controller
 * @author Allin Lin
 * @created 2021年1月25日 上午9:51:59
 */
@Controller
@RequestMapping("/sbPayController")
public class SbPayController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(SbPayController.class);
    
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
        
    /**
     * 
     * 描述 页面跳转(单位员工社保缴纳情况)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "payView")
    public ModelAndView payView(HttpServletRequest request) {
        return new ModelAndView("bsfw/sbPay/sbPayView");
    }
    
    /**
     * 描述 获取指定单位员工社保近6个月缴费情况（调接口）
     * 
     * @author Allin Lin
     * @created 2020年11月18日 下午2:42:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        String aab003 = request.getParameter("aab003");//单位信用代码
        String type = request.getParameter("type");//险种类型 110 养老,410 工伤
        String serviceId = "";//服务ID
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        Map<String, Object> info = new HashMap<String, Object>();
        Map<String, Object> result;
        if(StringUtils.isNotEmpty(aab003) && StringUtils.isNotEmpty(type)){
            //接口做转发至178.179服务器上
            String forwardUrl = StringUtil.getString(dictionaryService.
                    get("sbPayConfig", "INTERNET_FORWARD_URL").get("DIC_DESC"));//互联网转发地址
            info.put("aab003", aab003);
            if("110".equals(type)){
                serviceId = StringUtil.getString(dictionaryService.get("sbPayConfig",
                        "ylServiceId").get("DIC_DESC"));
            }else if("410".equals(type)){
                serviceId = StringUtil.getString(dictionaryService.get("sbPayConfig",
                        "gsServiceId").get("DIC_DESC"));
            }
            info.put("serviceId", serviceId);
            try {
                String res = HttpSendUtil.sendPostParams(forwardUrl, info);
                if (StringUtils.isNotEmpty(res)) {
                    result = JSON.parseObject(res, Map.class);
                    if ("true".equals(StringUtil.getString(result.get("resultCode")))){
                        list =(List<Map<String, String>>) result.get("content");
                        log.info("单位员工社保近6个月缴纳情况接口服务ID:"+serviceId+"--请求成功，返回参数："+JSON.toJSONString(result));
                    }else {
                        log.info("单位员工社保近6个月缴纳情况接口服务ID:"+serviceId+"--请求失败，返回信息："+StringUtil.
                                getString(result.get("resultString")));
                    }
                }
            } catch (Exception e) {
                log.info("单位员工社保近6个月缴纳情况接口服务ID-"+serviceId+"获取数据异常:", e);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述:转发到互联网服务器(社保专网接口（GET请求和POST请求）)
     * request中需要带入的参数：
     *      url:请求地址，必传
     *      headJson:请求头，可为null，可不传
     *      json:如果传输方式为json，则不为空
     *      method:HttpSendUtil中的方法，可不传
     * @author Allin Lin
     * @created 2020年12月25日 上午9:31:28
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/forwardInternet")
    @ResponseBody
    public Map<String, Object> forwardInternet(HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String serviceId = StringUtil.getString(requestMap.get("serviceId"));
        ESBParams esbParams = new ESBParams();
        esbParams.add("aab003", StringUtil.getString(requestMap.get("aab003")));
        ESBResult esbResult = ESBAction.doAction(serviceId,esbParams);// 服务id,参数
        if (esbResult.isResultcode()){
            returnMap.put("resultCode", "true");
            returnMap.put("content", esbResult.getContent());
        }else {
            returnMap.put("resultCode", "false");
            returnMap.put("resultString", esbResult.getResultstring());
        }
        return returnMap;
    }

}
