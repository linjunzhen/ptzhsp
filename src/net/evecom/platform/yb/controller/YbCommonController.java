/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.yb.service.YbCommonService;


/**
 * 描述 医保业务通用操作控制类
 * @author Allin Lin
 * @created 2019年10月21日 下午9:36:04
 */
@Controller
@RequestMapping("/ybCommonController")
public class YbCommonController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(YbCommonController.class);
    
    /**
     * 引入的service
     */
    @Resource
    private YbCommonService ybCommonService;
    
    /**
     * 描述:转发到互联网服务器(医保专网接口（GET请求和POST请求）)
     * request中需要带入的参数：
     *      url:请求地址，必传
     *      headJson:请求头，可为null，可不传
     *      json:如果传输方式为json，则不为空
     *      method:HttpSendUtil中的方法，必传
     * @author Allin Lin
     * @created 2020年12月25日 上午9:31:28
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/forwardInternet")
    @ResponseBody
    public Map<String, Object> forwardInternet(HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = null;
        Map<String, Object> postMap = null;
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String pushInfoJson = StringUtil.getValue(requestMap, "pushInfoJson");
        String method = "";
        String urlDicType = "";
        if(StringUtils.isNotEmpty(pushInfoJson)){
            postMap = JSON.parseObject(pushInfoJson, Map.class);
            method = StringUtil.getValue(postMap, "method");
            urlDicType = StringUtil.getValue(postMap, "urlDicType");
            postMap.remove("method");
            postMap.remove("urlDicType");
        }else{
            method = StringUtil.getValue(requestMap, "method");
            urlDicType = StringUtil.getValue(requestMap, "urlDicType");
            requestMap.remove("method");
            requestMap.remove("urlDicType");
        }
        if (Objects.equals(method, "sendHttpGet")) {//医保GET请求(查询)
            returnMap = ybCommonService.queryDataOfYb(requestMap, urlDicType);
        }else if(Objects.equals(method, "sendHttpPost")){//医保POST请求(校验、推送、查询)
            returnMap = ybCommonService.pushDataOfYb(postMap, urlDicType);
        }
        return returnMap;
    }
    
    
    /**
     * 
     * 描述    跳转至个人综合信息查询界面
     * @author Allin Lin
     * @created 2020年12月29日 下午3:35:20
     * @param request
     * @return
     */
    @RequestMapping(params = "grxxzhcxSelector")
    public ModelAndView grxxzhcxSelector(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/grxxzhcxtab");
    }
    
    /**
     * 
     * 描述    跳转至单位信息综合查询界面
     * @author Allin Lin
     * @created 2021年1月7日 下午4:24:53
     * @param request
     * @return
     */
    @RequestMapping(params = "dwxxzhcxSelector")
    public ModelAndView dwxxzhcxSelector(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/dwxxzhcxtab");
    }
    
    
    /**
     * 
     * 描述   跳转至 个人综合信息查询-人员基本信息查询页面
     * @author Allin Lin
     * @created 2020年12月29日 下午3:44:02
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnBasic")
    public ModelAndView goPsnBasic(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnBasic");
    }
    
    
    /**
     * 
     * 描述     跳转至 个人综合信息查询-职工参保信息查询页面
     * @author Allin Lin
     * @created 2020年12月30日 下午2:54:19
     * @param request
     * @return
     */
    @RequestMapping(params = "goZgInsu")
    public ModelAndView goZgInsu(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/zgInsu");
    }
    
    /**
     * 描述    跳转至个人综合信息查询-工资及个账信息查询页面
     * @author Allin Lin
     * @created 2020年12月31日 上午9:58:40
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnWaveAndAccount")
    public ModelAndView goPsnWaveAndAccount(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnWaveAndAccount");
    }
    
    /**
     * 
     * 描述     跳转至 个人综合信息查询-居民参保信息查询页面
     * @author Allin Lin
     * @created 2020年12月30日 下午2:54:19
     * @param request
     * @return
     */
    @RequestMapping(params = "goJmInsu")
    public ModelAndView goJmInsu(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/jmInsu");
    }
    
    
    /**
     * 
     * 描述    跳转至个人综合信息查询-历史参保单位信息查询界面
     * @author Allin Lin
     * @created 2021年1月5日 上午9:29:29
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnUnitHistory")
    public ModelAndView goPsnUnitHistory(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnUnitHistory");
    }
    
    
    /**
     * 
     * 描述     跳转至个人综合信息查询-异地参保记录查询界面
     * @author Allin Lin
     * @created 2021年1月5日 上午9:43:03
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnRemoteInsu")
    public ModelAndView goPsnRemoteInsu(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnRemoteInsu");
    }
    
    
    /**
     * 
     * 描述    跳转至个人综合信息查询-特殊人员记录查询界面
     * @author Allin Lin
     * @created 2021年1月5日 上午10:14:34
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnSpecialHistory")
    public ModelAndView goPsnSpecialHistory(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnSpecialHistory");
    }
    
    /**
     * 
     *  描述    跳转至个人综合信息查询-人员变更历史记录查询界面    
     * @author Allin Lin
     * @created 2021年1月5日 上午10:32:35
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnInfoHistory")
    public ModelAndView goPsnInfoHistory(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnInfoHistory");
    }
    
    /**
     * 
     * 描述     跳转至个人综合信息查询-个人缴费明细查询界面   
     * @author Allin Lin
     * @created 2021年1月5日 下午2:26:00
     * @param request
     * @return
     */
    @RequestMapping(params = "goPsnPaymentQuery")
    public ModelAndView goPsnPaymentQuery(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/psnPayment");
    }
    
    /**
     * 
     * 描述     跳转至单位信息综合查询-单位基本信息查询界面   
     * @author Allin Lin
     * @created 2021年1月7日 下午4:32:50
     * @param request
     * @return
     */
    @RequestMapping(params = "goUnitBasic")
    public ModelAndView goUnitBasic(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/unitBasic");
    }
    
    
    /**
     * 
     * 描述    跳转至单位信息综合查询-单位参保信息查询界面   
     * @author Allin Lin
     * @created 2021年1月8日 下午4:25:45
     * @param request
     * @return
     */
    @RequestMapping(params = "goUnitInsu")
    public ModelAndView goUnitInsu(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/unitInsu");
    }
    
    
    /**
     * 
     * 描述    跳转至单位信息综合查询-单位参保清册查询界面
     * @author Allin Lin
     * @created 2021年1月26日 上午9:48:49
     * @param request
     * @return
     */
    @RequestMapping(params = "goUnitInsuUsers")
    public ModelAndView goUnitInsuUsers(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/unitInsuUsers");
    }
    
    /**
     * 
     * 描述    跳转至单位信息综合查询-单位通知单信息查询界面
     * @author Allin Lin
     * @created 2021年1月27日 上午10:25:36
     * @param request
     * @return
     */
    @RequestMapping(params = "goUnitOffer")
    public ModelAndView goUnitOffer(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/unitOffer");
    }
    
    
    /**
     * 
     * 
     * 描述     跳转至单位信息综合查询-单位缴费明细查询界面
     * @author Allin Lin
     * @created 2021年1月27日 下午2:37:13
     * @param request
     * @return
     */
    @RequestMapping(params = "goUnitPayInfo")
    public ModelAndView goUnitPayInfo(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/unitPayInfo");
    }
    
    
    /**
     * 
     * 
     * 描述     跳转至单位信息综合查询-居民账目信息查询界面
     * @author Allin Lin
     * @created 2021年1月27日 下午2:37:13
     * @param request
     * @return
     */
    @RequestMapping(params = "goUserItemInfo")
    public ModelAndView goUserItemInfo(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/ybqlc/common/userItemInfo");
    }
    
    /**
     *  个人综合信息查询-人员基本信息查询 
     * @param request
     * @return
     */
    @RequestMapping(params = "psnBasicGrid")
    public void psnBasicGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> ryBasicList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnBasicGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("人员基本信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnBasicQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    ryBasicList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(ryBasicList.size(), ryBasicList, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 
     * 描述    个人综合查询-历史参保单位信息查询
     * @author Allin Lin
     * @created 2021年1月5日 上午9:36:02
     * @param request
     * @param response
     */
    @RequestMapping(params = "psnUnitHistoryGrid")
    public void psnUnitHistoryGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnUnitHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnUnitHistoryGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("历史参保单位信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnUnitHistoryQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnUnitHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnUnitHistoryList.size(), psnUnitHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    个人综合查询-异地参保记录查询
     * @author Allin Lin
     * @created 2021年1月5日 上午9:47:46
     * @param request
     * @param response
     */
    @RequestMapping(params = "psnRemoteInsuGrid")
    public void psnRemoteInsuGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnRemoteInsuList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnRemoteInsuGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("异地参保记录查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnRemoteInsuQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnRemoteInsuList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnRemoteInsuList.size(), psnRemoteInsuList, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 
     * 描述    个人综合查询-特殊人员记录查询
     * @author Allin Lin
     * @created 2021年1月5日 上午9:47:46
     * @param request
     * @param response
     */
    @RequestMapping(params = "psnSpecialHistoryGrid")
    public void psnSpecialHistoryGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnSpecialHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnSpecialHistoryGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("特殊人员记录查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnSpecialHistoryQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnSpecialHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnSpecialHistoryList.size(), psnSpecialHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 
     * 描述    个人综合查询-人员变更历史记录查询
     * @author Allin Lin
     * @created 2021年1月5日 上午10:39:59
     * @param request
     * @param response
     */
    @RequestMapping(params = "psnInfoHistoryGrid")
    public void psnInfoHistoryGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnInfoHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnInfoHistoryGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("人员变更历史记录查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnInfoHistoryQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInfoHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnInfoHistoryList.size(), psnInfoHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     *  个人综合信息查询-人员参保信息查询 (职工参保信息)
     * @param request
     * @return
     */
    @RequestMapping(params = "zgInsuGrid")
    public void zgInsuGrid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> psnInsuMap;
        List<Map<String, Object>> insuList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("zgInsuGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("人员参保信息查询（职工参保信息）参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnInsuQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInsuMap = (Map<String, Object>)JSON.parse(jsonString); 
                    //参保身份（1：职工 2：居民）
                    int psnType = (int)psnInsuMap.get("psnType");
                    if(psnType==1){
                        insuList = (List<Map<String, Object>>) JSON.parse(
                                StringUtil.getString(psnInsuMap.get("insu")));
                    }
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(insuList.size(), insuList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     *  个人综合信息查询-人员参保信息查询 (居民参保信息)
     * @param request
     * @return
     */
    @RequestMapping(params = "jmInsuGrid")
    public void jmInsuGrid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> psnInsuMap;
        List<Map<String, Object>> insuList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("jmInsuGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("人员参保信息查询（居民参保信息）参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnInsuQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInsuMap = (Map<String, Object>)JSON.parse(jsonString); 
                    //参保身份（1：职工 2：居民）
                    int psnType = (int)psnInsuMap.get("psnType");
                    if(psnType==2){
                        insuList = (List<Map<String,Object>>)JSON.parse(
                                StringUtil.getString(psnInsuMap.get("insu")));   
                    }
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(insuList.size(), insuList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    个人综合信息查询-个人缴费明细查询
     * @author Allin Lin
     * @created 2021年1月5日 下午2:43:41
     * @param request
     * @param response
     */
    @RequestMapping(params = "psnPaymentGrid")
    public void psnPaymentGrid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> psnPaymentMap;
        List<Map<String, Object>> psnPaymentList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnPaymentGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("个人缴费明细查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnPaymentQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnPaymentMap = (Map<String, Object>)JSON.parse(jsonString); 
                    //参保身份（1：职工 2：居民）
                    //int psnType = (int)psnPaymentMap.get("psnType");
                    psnPaymentList = (List<Map<String,Object>>)JSON.parse(
                            StringUtil.getString(psnPaymentMap.get("payment")));   
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnPaymentList.size(), psnPaymentList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述     人员基本信息查询-工资及个账信息查询
     * @author Allin Lin
     * @created 2021年1月3日 下午8:38:07
     * @param request
     * @param response
     */
    @RequestMapping(params = "psnWaveAndAccountDatagrid")
    public void psnWaveAndAccountDatagrid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> waveAndAccountMap;
        List<Map<String, Object>> waveAndAccountList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("psnWaveAndAccountDatagrid");
            params.put("token", tokenMap.get("token"));
            //log.info("工资及个账查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "psnWaveAndAccountQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    waveAndAccountMap   = (Map<String, Object>) JSON.parse(jsonString); 
                    waveAndAccountList.add(waveAndAccountMap);
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(waveAndAccountList.size(), waveAndAccountList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     *  单位信息综合查询-单位基本信息查询 
     * @param request
     * @return
     */
    @RequestMapping(params = "unitBasicGrid")
    public void unitBasicGrid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> unitMap ;
        List<Map<String, Object>> unitBasicList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitBasicGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("单位参保信息查询（单位基本信息）参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "unitInfoQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    unitMap = (Map<String, Object>)JSON.parse(jsonString); 
                    Map<String, Object> unitBasicMap;
                    unitBasicMap = (Map<String,Object>)JSON.parse(
                                StringUtil.getString(unitMap.get("basic")));   
                    unitBasicList.add(unitBasicMap);
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(unitBasicList.size(), unitBasicList, null, JsonUtil.EXCLUDE, response);
    }
    
    
    
    /**
     *  单位信息综合查询-单位参保信息查询 
     * @param request
     * @return
     */
    @RequestMapping(params = "unitInsuGrid")
    public void unitInsuGrid(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> unitInsuMap;
        List<Map<String, Object>> unitInsuList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitInsuGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("单位参保信息查询（单位参保信息）参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.pushInfoOfYb(params, "unitInfoQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    unitInsuMap = (Map<String, Object>)JSON.parse(jsonString); 
                    unitInsuList = (List<Map<String,Object>>)JSON.parse(
                                StringUtil.getString(unitInsuMap.get("insu")));   
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(unitInsuList.size(), unitInsuList, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 
     * 描述    单位综合查询-单位参保清册查询
     * @author Allin Lin
     * @created 2021年1月27日 上午9:39:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "unitInsuUsersGrid")
    public void unitInsuUsersGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnInfoHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitInsuUsersGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("单位参保清册查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "unitInsuUsersQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInfoHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnInfoHistoryList.size(), psnInfoHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 
     * 描述    单位综合查询-单位通知单查询
     * @author Allin Lin
     * @created 2021年1月27日 上午9:39:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "unitOfferGrid")
    public void unitOfferGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnInfoHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitOfferGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("单位通知单查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "unitOfferQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInfoHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnInfoHistoryList.size(), psnInfoHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    单位综合查询-单位通知单查询
     * @author Allin Lin
     * @created 2021年1月27日 上午9:39:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "unitPayInfoGrid")
    public void unitPayInfoGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnInfoHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("unitPayInfoGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("单位缴费明细查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "unitPayInfoQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInfoHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnInfoHistoryList.size(), psnInfoHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    单位综合查询-居民账目信息查询
     * @author Allin Lin
     * @created 2021年1月27日 上午9:39:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "userItemInfoGrid")
    public void userItemInfoGrid(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> psnInfoHistoryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> tokenMap = ybCommonService.getToken();
        if((boolean) tokenMap.get("success")){
            Map<String,Object> params = BeanUtil.getMapFromRequest(request);
            params.remove("userItemInfoGrid");
            params.put("token", tokenMap.get("token"));
            //log.info("居民账目信息查询参数："+JSON.toJSONString(params));
            AjaxJson dataJson = ybCommonService.queryAjaxJsonOfYb(params, "userItemInfoQuery");
            if(dataJson.isSuccess()){
                String jsonString = dataJson.getJsonString();
                if(jsonString.length() > 0){
                    psnInfoHistoryList = (List<Map<String, Object>>) JSON.parse(jsonString); 
                }                       
            }else{
                log.info(dataJson.getMsg());
            }
        }else{
            log.info("获取token值失败！");
        }
        this.setListToJsonString(psnInfoHistoryList.size(), psnInfoHistoryList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年12月31日 下午2:27:26
     * @param request
     * @return
     */
    @RequestMapping(params="toImpPerson")
    public ModelAndView toImpPerson(HttpServletRequest request){
        String exeId = request.getParameter("exeId");
        String impTableName = request.getParameter("impTableName");
        request.setAttribute("exeId", exeId);
        request.setAttribute("impTableName", impTableName);
        return new ModelAndView("bsdt/applyform/ybqlc/common/impperson");
    }
    
    /**
     * 
     * 描述    批量办件人员导入
     * @author Danto Huang
     * @created 2020年12月31日 上午11:01:19
     * @param request
     * @param upload
     * @return
     * @throws Exception
     */
    @RequestMapping(params="impPersonInfo")
    @ResponseBody
    public AjaxJson impPersonInfo(HttpServletRequest request,@RequestParam MultipartFile upload){
        AjaxJson j = new AjaxJson();
        String exeId = request.getParameter("exeId");
        String impTableName = request.getParameter("impTableName");
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        // 定义缓存临时文件的路径
        String tempFileFolderPath = attachFileFolderPath + "/temp/";
        File tempFileFolder = new File(tempFileFolderPath);
        if (!tempFileFolder.exists()) {
            tempFileFolder.mkdirs();
        }
        // 获取上传文件的名称
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid+"_"+filename;
        // 完成文件上传
        try {
            upload.transferTo(new File(tempFileFolder,filename));
            //excel临时路径
            String excelPath = tempFileFolder + "/" + filename;
            List<Map<String,Object>> list = ybCommonService.impPersonInfo(excelPath,exeId,impTableName);
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("rows", list);
            result.put("total", list.size());
            j.setJsonString(JSON.toJSONString(result));
            j.setMsg("导入成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("导入失败");
            j.setSuccess(false);
            log.error(e.getMessage(),e);
        } 
        
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年1月4日 上午9:37:23
     * @param request
     * @return
     */
    @RequestMapping(params="loadImpPerson")
    @ResponseBody
    public void loadImpPersonData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("GEN_STATUS", "asc");
        /*if(filter.getQueryParams().get("Q_t.EXE_ID_EQ")==null){
            filter.addFilter("Q_t.EXE_ID_EQ", "0");
        }*/
        List<Map<String, Object>> list = ybCommonService.loadPLperson(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 
     * 描述    批量收件人员独立生成办件并受理
     * @author Danto Huang
     * @created 2021年1月4日 下午2:59:17
     * @param request
     * @return
     */
    @RequestMapping(params="acceptPersonExe")
    @ResponseBody
    public AjaxJson acceptPersonExe(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        Map<String,Object> result = ybCommonService.acceptPersonExe(recordId);
        
        j.setSuccess((boolean) result.get("OPER_SUCCESS"));
        j.setMsg((String) result.get("OPER_MSG"));
        return j;
    }
}
