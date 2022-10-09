/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.axis.client.Call;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

/**
 * webservice鉴权
 * 
 * @author Luffy Cai
 *
 */
public class EvaluateWebService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(EvaluateWebService.class);

    /**
     * Json序列化
     * 
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Json 反序列化
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> listFromJson(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 
     * @Description 评价数据上报接口
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param c
     * @param xmlStr
     * @return String
     */
    public static String getSaveEvaluation(Call c, String xmlStr) {
        c.addParameter("appKey", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
        c.addParameter("xmlStr", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
        c.setOperationName("getSaveEvaluation");
        String appKey = "7541b31fe62a44f9bcb60a0d47100fc9";// 公钥
        String a = "";
        try {
            a = (String) c.invoke(new Object[] { appKey, xmlStr });
        } catch (Exception e) {
            String errStr = getStackTraceInfo(e);
            log.error("调用好差评评价数据上报接口错误，错误信息：" + errStr);
            if (errStr.contains("access_token_invalid")) {
                log.error("好差评系统token失效，请重新获取token并且重新请求接口");
            }
        }
        return a;
    }
    
    /**
     * 
     * @Description 评价数据上报接口
     * @author Luffy Cai
     * @date 2020年7月31日
     * @param c
     * @param xmlStr
     * @return String
     */
    public static String getEvaluationSupplement(Call c, String xmlStr) {
        c.addParameter("appKey", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
        c.addParameter("xmlStr", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
        c.setOperationName("getEvaluationSupplement");
        String appKey = "7541b31fe62a44f9bcb60a0d47100fc9";// 公钥
        String a = "";
        try {
            a = (String) c.invoke(new Object[] { appKey, xmlStr });
        } catch (Exception e) {
            String errStr = getStackTraceInfo(e);
            log.error("调用好差评评价数据上报接口错误，错误信息：" + errStr);
            if (errStr.contains("access_token_invalid")) {
                log.error("好差评系统token失效，请重新获取token并且重新请求接口");
            }
        }
        return a;
    }    

    /**
     * 获取e.printStackTrace() 的具体信息，赋值给String 变量，并返回
     * 
     * @param e Exception
     * @return e.printStackTrace() 中 的信息
     */
    public static String getStackTraceInfo(Exception e) {

        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);// 将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {

            return "发生错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    log.error(e1.getMessage());
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
    }
}
