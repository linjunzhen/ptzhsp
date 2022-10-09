/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.ems.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.util.HttpSendUtil;
import net.evecom.platform.system.service.impl.DictionaryServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述
 * 
 * @author Danto Huang
 * @created 2020年2月13日 下午1:12:43
 */
public class EmsSend {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(EmsSend.class);
    
    public static void main(String[] args) {

//        // //云上api签名
//        // 测试环境参数
//        String contentType = "application/x-www-form-urlencoded;charset=UTF-8";// post请求
//        String charset = "UTF-8";// 编码
//        String path = "http://60.205.8.187:18001/api/gateway";
////        String relayPath = new DictionaryServiceImpl().getDicCode("EMSDJCS", "relayPath");
//        String relayPath = "http://127.0.0.1:8080/ptzhsp/emsController/sendEmsHttpForHlw.do";
//        String authorization = "408a6c32e61d3ad5cb5c4e0cb3d2b089";
//        String appkey = "8bd10fea96a9343253009a00ea312207";
//        String appsecret = "45a9fdfbea2b22d8cbdf559f419d69ba";
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("authorization", authorization);
//        map.put("app_key", appkey);
//
//        map.put("method", "ems.inland.mms.mailnum.apply");
//
//        map.put("timestamp", "2020-02-13 14:03:10");
//        map.put("version", "V3.01");
//        map.put("format", "json");
//        map.put("charset", "UTF-8");
//        map.put("size","1");
//        // 获取邮件号
//        //map.put("bizcode", "06");
//        //map.put("count", "1");
//
//        String content = getSortParams(map) + appsecret;// 排序
//
//        String sign = sign(content, "UTF-8");// 加密
//        map.put("sign", sign);
//
//        String body = toKey(map);
//
//        String reslut;
//        try {
//            HashMap<String, Object> map1 = new HashMap<>();
//            HashMap<String, String> headerMap = new HashMap<>();
//            map1.putAll(map);
//            reslut = HttpSendUtil.sendPostParamsH(relayPath, headerMap, map1);
////            reslut = HttpUtil.sendHttpPost(path, body, charset, null, contentType);
//            System.out.println("返回的结果为>>>>>>" + reslut);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }

    }

    /**
     * 
     * 描述    下单
     * @author Danto Huang
     * @created 2020年2月17日 下午3:43:55
     * @param map
     * @return
     */
    public static Map<String,Object> emsSend(Map<String, String> map) {
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        // 测试环境参数
//        String contentType = "application/x-www-form-urlencoded;charset=UTF-8";// post请求
//        String charset = "UTF-8";// 编码
        /*String path = "http://60.205.8.187:18001/api/gateway";
        String authorization = "408a6c32e61d3ad5cb5c4e0cb3d2b089";
        String appkey = "8bd10fea96a9343253009a00ea312207";
        String appsecret = "45a9fdfbea2b22d8cbdf559f419d69ba";*/
//        String path = dictionaryService.getDicCode("EMSDJCS", "path");
        String relayPath = dictionaryService.getDicCode("EMSDJCS", "relayPath");
        String authorization = dictionaryService.getDicCode("EMSDJCS", "authorization");
        String appkey = dictionaryService.getDicCode("EMSDJCS", "appkey");
        String appsecret = dictionaryService.getDicCode("EMSDJCS", "appsecret");

        // Map<String, String> map = new HashMap<String, String>();
        map.put("authorization", authorization);
        map.put("app_key", appkey);

        map.put("method", "ems.inland.order.create.normal");

        map.put("timestamp", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("version", "V3.01");
        map.put("format", "json");
        map.put("charset", "UTF-8");
        String content = getSortParams(map) + appsecret;// 排序

        String sign = sign(content, "UTF-8");// 加密
        map.put("sign", sign);

//        String body = toKey(map);
        //System.out.println("拼接的字符串>>>>>>>>>>>>>>>" + body);
//        log.info("调用EMS接口，参数"+body);
        Map<String,Object> result = new HashMap<String, Object>();
        try {
//            String respContent = HttpUtil.sendHttpPost(path, body, charset, null, contentType);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.putAll(map);
            String respContent = HttpSendUtil.sendPostParams(relayPath, paramMap);
            //System.out.println("返回的结果为>>>>>>" + respContent);
            log.info("接口返回结果："+respContent);
            if(StringUtils.isNotEmpty(respContent)){
                Map<String, Object> resultMap = (Map)JSON.parse(respContent);
                String success = resultMap.get("success").toString();
                if("T".equals(success)){
                    /*List<Map<String, Object>> ResponseOrders = (List<Map<String, Object>>) JSON
                            .parse((String) resultMap.get("responseOrders"));
                    Map<String,Object> ResponseOrder = ResponseOrders.get(0);*/
                    JSONArray responseOrders = (JSONArray) resultMap.get("responseOrders");
                    Map<String,Object> ResponseOrder = (Map<String, Object>) responseOrders.get(0);
                    if("T".equals(ResponseOrder.get("success"))){
                        result.put("mailNo", ResponseOrder.get("mailNo"));
                        result.put("txLogisticID", ResponseOrder.get("txLogisticID"));
                        result.put("success", true);
                    }else{
                        result.put("success", false);
                        result.put("errorMsg", ResponseOrder.get("errorMsg"));
                    }
                }else{
                    result.put("success", false);
                    result.put("errorMsg", resultMap.get("errorMsg"));
                    log.error("调用EMS订单推送接口失败,错误码:"+resultMap.get("errorCode")+","+resultMap.get("errorMsg"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 
     * 描述    运单状态查询
     * @author Danto Huang
     * @created 2020年2月17日 下午3:43:55
     * @param map
     * @return
     */
    public static Map<String,Object> tracesQuery(Map<String, String> map) {
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        // 测试环境参数
//        String contentType = "application/x-www-form-urlencoded;charset=UTF-8";// post请求
//        String charset = "UTF-8";// 编码
        String relayPath = dictionaryService.getDicCode("EMSDJCS", "relayPath");
        String authorization = dictionaryService.getDicCode("EMSDJCS", "authorization");
        String appkey = dictionaryService.getDicCode("EMSDJCS", "appkey");
        String appsecret = dictionaryService.getDicCode("EMSDJCS", "appsecret");

        map.put("authorization", authorization);
        map.put("app_key", appkey);
        map.put("method", "ems.inland.trace.query");

        map.put("timestamp", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("version", "V3.01");
        map.put("format", "json");
        map.put("charset", "UTF-8");
        String content = getSortParams(map) + appsecret;// 排序

        String sign = sign(content, "UTF-8");// 加密
        map.put("sign", sign);

//        String body = toKey(map);
//        log.info("调用EMS接口，参数"+body);
        Map<String,Object> result = new HashMap<String, Object>();
        try {
//            String respContent = HttpUtil.sendHttpPost(path, body, charset, null, contentType);
            HashMap<String, String> headerMap = new HashMap<>();
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.putAll(map);
            String respContent = HttpSendUtil.sendPostParamsH(relayPath, headerMap, paramMap);
            //System.out.println("返回的结果为>>>>>>" + respContent);
            log.info("接口返回结果："+respContent);
            if(StringUtils.isNotEmpty(respContent)){
                Map<String, Object> resultMap = (Map)JSON.parse(respContent);
                if("T".equals(resultMap.get("success"))){
                    JSONArray traces = (JSONArray) resultMap.get("traces");
                    if(traces==null){
                        result.put("tracelist", null);
                    }else{
                        List<Map> tracelist = JSONObject.parseArray(traces.toJSONString(), Map.class);
                        result.put("tracelist", tracelist);
                    }
                    result.put("success", true);
                }else{
                    result.put("success", false);
                    result.put("errorMsg", resultMap.get("errorMsg"));
                    log.error("调用EMS运单状态查询接口失败,错误码:"+resultMap.get("errorCode")+","+resultMap.get("errorMsg"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * 
     * 描述    撤单
     * @author Danto Huang
     * @created 2020年2月17日 下午3:43:45
     * @param map
     * @return
     */
    public static Map<String,Object> emsCancel(Map<String, String> map) {
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        // 测试环境参数
        String contentType = "application/x-www-form-urlencoded;charset=UTF-8";// post请求
        String charset = "UTF-8";// 编码
        String path = dictionaryService.getDicCode("EMSDJCS", "path");
        String authorization = dictionaryService.getDicCode("EMSDJCS", "authorization");
        String appkey = dictionaryService.getDicCode("EMSDJCS", "appkey");
        String appsecret = dictionaryService.getDicCode("EMSDJCS", "appsecret");

        map.put("authorization", authorization);
        map.put("app_key", appkey);
        map.put("method", "ems.inland.waybill.got.cancellation");
        map.put("timestamp", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("version", "V3.01");
        map.put("format", "json");
        map.put("charset", "UTF-8");
        String content = getSortParams(map) + appsecret;// 排序

        String sign = sign(content, "UTF-8");// 加密
        //System.out.println(sign);
        map.put("sign", sign);

        String body = toKey(map);
        //System.out.println("拼接的字符串>>>>>>>>>>>>>>>" + body);
        log.info("调用EMS接口，参数"+body);
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            String respContent = HttpUtil.sendHttpPost(path, body, charset, null, contentType);
            //System.out.println("返回的结果为>>>>>>" + respContent);
            log.info("接口返回结果："+respContent);
            if(StringUtils.isNotEmpty(respContent)){
                Map<String, Object> resultMap = (Map)JSON.parse(respContent);
                String success = resultMap.get("success").toString();
                if("T".equals(success)){
                    result.put("success", true);
                }else{
                    result.put("success", false);
                    result.put("errorMsg", resultMap.get("errorMsg"));
                    log.error("调用EMS退单推送接口失败,错误码:"+resultMap.get("errorCode")+","+resultMap.get("errorMsg"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    public static String sign(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }
        String sign = "";
        try {
            //content = new String(content.getBytes(), charset);
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");// MD5

            sign = BASE64Encoder.encode(md5.digest(content.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        }
        return sign;
    }

    public static String toKey(Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        // 这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
            /*
             * if (!StringUtil.isNull(value)) { keyList.add(key); }
             */
            keyList.add(key);
        }

        // 将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if (i == keyList.size() - 1) {
                content.append(key).append("=").append(params.get(key));
                return content.toString();
            }
            content.append(key).append("=").append(params.get(key)).append("&");

        }
        return content.toString();
    }

    public static String getSortParams(Map<String, String> params) {
        params.remove("sign");
        StringBuffer contnt = new StringBuffer();
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        // 这个接口做sign签名时，值为空的参数也传
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
            /*
             * if (!StringUtil.isNull(value)) { keyList.add(key); }
             */
            keyList.add(key);
        }
        Collections.sort(keyList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        // 将参数和参数值按照排序顺序拼装成字符串
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt.append(key).append(params.get(key));
        }
        return contnt.toString();
    }
}
