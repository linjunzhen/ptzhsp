/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.platform.evaluate.constant.ErrorConstant;

/**
 * 描述 好差评系统通用方法
 * 
 * @author Luffy Cai
 * @created 2020年7月14日 下午10:38:45
 */
public class EvaluateCommonUtil {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(EvaluateCommonUtil.class);

    /**
     * 令牌值
     */
    public static String accessToken;

    /**
     * 过期时间点（时间戳毫秒数）
     */
    public static long expiration;

    /**
     * 刷新令牌过期时间点（时间戳毫秒数）
     */
    public static long reExpiresIn;

    /**
     * 刷新令牌token值
     */
    public static String refreshToken;

    /**
     * 描述 获取授权实时有效令牌值：token
     * 
     * @author Luffy Cai
     * @created 2020年7月15日 下午10:55:20
     * @return
     */
    public static String getRealToken() {
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("系统当前缓存令牌值：" + accessToken + ",-时间对应为：" + sdff.format(new Date(System.currentTimeMillis())) + ",过期时间点："
                + expiration + "-时间对应为：" + sdff.format(new Date(expiration)) + ",刷新令牌过期时间点:" + reExpiresIn + "-时间对应为："
                + sdff.format(new Date(reExpiresIn)) + ",刷新令牌token值：" + refreshToken);
        if (accessToken == null || "".equals(accessToken) || "undefined".equals(accessToken)) {
            // 重新调取授权获取令牌值接口
            Map<String, Object> result = getOauthToken("getToken");
            if (result.get("access_token") != null) {
                accessToken = result.get("access_token").toString();
                expiration = System.currentTimeMillis();
                reExpiresIn = System.currentTimeMillis();
                refreshToken = result.get("refresh_token").toString();
            }
        } else {
            // 判断token值是否失效
            long currentTime = System.currentTimeMillis();// 系统当前时间戳
            if (currentTime > reExpiresIn + (1 * 60 * 1000)) {
                // 重新调取授权获取令牌值接口
                Map<String, Object> result = getOauthToken("getToken");
                if (result.get("access_token") != null) {
                    accessToken = result.get("access_token").toString();
                    expiration = System.currentTimeMillis();
                    reExpiresIn = System.currentTimeMillis();
                    refreshToken = result.get("refresh_token").toString();
                }
            }
        }
        log.info("当前实时有效令牌值token为：" + accessToken);
        return accessToken;
    }

    /**
     * 描述 授权获取令牌Token接口
     * 
     * @author Luffy Cai
     * @created 2020年7月14日 上午10:10:28
     * @param urlDicType 接口字典值
     * @return
     */
    public static Map<String, Object> getOauthToken(String urlDicType) {
        // 接口参数需后续配置成字典值
        String app_key = "7541b31fe62a44f9bcb60a0d47100fc9";
        String appCode = "a325f2b70f8640b381c14321c319ac59";
        String tokenUrl = "http://api.zwfw.fujian.gov.cn:71/api-gateway/gateway/get-token";
        long timestamp = System.currentTimeMillis();
        Map<String, String> params = new HashMap<>();
        String sign = Md5Util.md5(appCode + timestamp);
        StringBuffer urlBuffer=new StringBuffer("");
        //接口返回参数设置
        String respContent = "";//接口响应信息 
        Map<String, Object> result = new HashMap<String, Object>();
        urlBuffer.append("app_key=").append(app_key)
        .append("&timestamp=").append(String.valueOf(timestamp)).append("&sign=")
        .append(sign);
        try {
            String response = HttpRequestUtil.sendBdcGet(tokenUrl, urlBuffer.toString(),"UTF-8");
                // 解析返回结果的json数据
            result = getResultByToken(response);
        } catch (Exception e) {
            log.error("调用好差评接口-授权获取令牌值失败，异常信息：" + e.getMessage());
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s", urlDicType, tokenUrl,
                params.toString(), result.toString()));
        return result;
    }

    /**
     * 描述 解析授权获取令牌值接口返回结果
     * 
     * @author Luffy Cai
     * @created 2020年2月20日 下午11:28:47
     * @param respContent
     * @return
     */
    public static Map<String, Object> getResultByToken(String respContent) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(respContent)) {
            Map<String, Object> resultMap = (Map) JSON.parse(respContent);
            result.put("access_token", resultMap.get("access_token"));// 令牌值
            result.put("re_expires_in", resultMap.get("re_expires_in"));// 刷新时间戳
            result.put("refresh_token", resultMap.get("refresh_token"));// 刷新令牌值
            result.put("expires_in", resultMap.get("expires_in"));// 过期时间点
            return result;
        } else {
            result.put("message", "未正确请求到授权获取令牌值接口，服务异常");
            return result;
        }
    }

    /**
     * 描述 省网好差评评价数据上报接口
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param xmlStr
     * @return
     * Map<String,Object>
     */
    public static Map<String,Object> pushEvaluateData(String xmlStr) {
        //获取令牌token
        String token = getRealToken();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        // 接口参数需后续配置成字典值
        String getSaveEvaluation = "http://api.zwfw.fujian.gov.cn:71/api-gateway/gateway/96hyvzxl/wailian/webservice/getSaveEvaluation?wsdl";
        try {  
            Service service = new Service();  
            Call call = (Call) service.createCall();
            SOAPHeaderElement head = new SOAPHeaderElement("http://linewell.com/ws/", "Authorization", "Basic " + token);
            call.addHeader(head);
            call.setTargetEndpointAddress(getSaveEvaluation);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 
            String resultvalue = EvaluateWebService.getSaveEvaluation(call,xmlStr);
            // 给方法传递参数，并且调用方法  
            log.info("result: "+resultvalue);
            resultMap.put("resultData",resultvalue);
                Document document = DocumentHelper.parseText(resultvalue);
                Element rootElement = document.getRootElement();
                Iterator code = rootElement.elementIterator("code");
                while (code.hasNext()) {
                    Element codeNext =(Element) code.next();
                    resultMap.put("code", codeNext.getStringValue());
                }
                Iterator nextData = rootElement.elementIterator("data");
                while (nextData.hasNext()) {
                    Element next = (Element) nextData.next();
                    Iterator nextUrl = next.elementIterator("evaluateUnid");
                    while (nextUrl.hasNext()) {
                        Element link =(Element) nextUrl.next();
                        resultMap.put("evaluateUnid", link.getStringValue());
                    }
                }
        } catch (Exception e) {
            resultMap.put("resultData","省网好差评评价数据上报异常，错误："+e.getMessage());
            log.error(e.getMessage());
        }
        return resultMap;        
        
        
    }      
    
    /**
     * 描述 省网好差评评价数据上报接口
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param xmlStr
     * @return
     * Map<String,Object>
     */
    public static Map<String,Object> pushEvaluationSupplementData(String xmlStr) {
        //获取令牌token
        String token = getRealToken();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        // 接口参数需后续配置成字典值
        String getEvaluationSupplement = "http://api.zwfw.fujian.gov.cn:71/api-gateway/gateway/96hyvzxl/wailian/webservice/getEvaluationSupplement?wsdl";
        try {  
            Service service = new Service();  
            Call call = (Call) service.createCall();
            SOAPHeaderElement head = new SOAPHeaderElement("http://linewell.com/ws/", "Authorization", "Basic " + token);
            call.addHeader(head);
            call.setTargetEndpointAddress(getEvaluationSupplement);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 
            String resultvalue = EvaluateWebService.getEvaluationSupplement(call,xmlStr);
            // 给方法传递参数，并且调用方法  
            resultMap.put("resultData",resultvalue);
                Document document = DocumentHelper.parseText(resultvalue);
                Element rootElement = document.getRootElement();
                Iterator code = rootElement.elementIterator("code");
                while (code.hasNext()) {
                    Element codeNext =(Element) code.next();
                    resultMap.put("code", codeNext.getStringValue());
                }
                Iterator nextData = rootElement.elementIterator("data");
                while (nextData.hasNext()) {
                    Element next = (Element) nextData.next();
                    Iterator nextUrl = next.elementIterator("evaluateUnid");
                    while (nextUrl.hasNext()) {
                        Element link =(Element) nextUrl.next();
                        resultMap.put("evaluateUnid", link.getStringValue());
                    }
                }
        } catch (Exception e) {
            resultMap.put("resultData","省网好差评评价数据补报接口异常，错误："+e.getMessage());
            log.error(e.getMessage());
        }
        return resultMap;        
        
        
    }     
    
    
}
