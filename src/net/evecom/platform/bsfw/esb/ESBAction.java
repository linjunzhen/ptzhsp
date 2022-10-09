/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.esb;

import com.ylzinfo.esb.bas.EsbResponse;
import com.ylzinfo.esb.client.XMLRequest;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 ESB服务相关操作
 * @author Allin Lin
 * @created 2021年1月25日 下午3:40:09
 */
public class ESBAction {
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ESBAction.class);
  
    /**
     * 调用ESB服务
     *
     * @param serviceId 服务号
     * @param params    参数列表
     */
    public static ESBResult doAction(String serviceId, ESBParams params) {
        String[] paramsArray = params.getParams();
        String[] valuesArray = params.getValues();
        //2、 解析ESB返回的XML
        return parseAction(getStringData(serviceId, paramsArray, valuesArray));
    }

    /**
     * 调用ESB服务
     *
     * @param serviceId 服务号
     * @param params    参数列表
     */
    public static ESBResult doAction(String serviceId, Map<String, String> params) {
        String[] paramsArray = params.keySet().toArray(new String[]{});
        String[] valuesArray = params.values().toArray(new String[]{});
        //解析ESB返回的XML
        return parseAction(getStringData(serviceId, paramsArray, valuesArray));
    }

    /**
     * 调用ESB服务（不解析）
     *
     * @param serviceId 服务号
     * @param params    参数列表
     */
    public static String doAction2Xml(String serviceId, ESBParams params) {
        String[] paramsArray = params.getParams();
        String[] valuesArray = params.getValues();
        return getStringData(serviceId, paramsArray, valuesArray);
    }

    private static String getStringData(String serviceId, String[] paramsArray, String[] valuesArray) {
        DictionaryService dictionaryService= (DictionaryService) AppUtil.getBean("dictionaryService");
        String esbUrl = StringUtil.getString(dictionaryService.get("sbPayConfig",
                "esbUrl").get("DIC_DESC"));
        String esbUser = StringUtil.getString(dictionaryService.get("sbPayConfig",
                "esbUser").get("DIC_DESC"));
        String esbPassword = StringUtil.getString(dictionaryService.get("sbPayConfig",
                "esbPassword").get("DIC_DESC"));
        //请求ESB
        XMLRequest xmlRequest = new XMLRequest();
        // esbUrlESB访问路径
        xmlRequest.setEsbUrl(esbUrl);
        // sbUserPwdESB访问用户名及密码
        xmlRequest.setEsbUserPwd(new String[]{esbUser, esbPassword});
        // svid服务ID
        xmlRequest.setSvid(serviceId);
        // 参数名
        xmlRequest.setParam(paramsArray);
        // 参数值
        xmlRequest.setParamValue(valuesArray);
        EsbResponse response = xmlRequest.postXmlRequest();
        log.info("单位员工社保近6个月缴纳情况接口请求地址："+esbUrl+"--ESB用户名："+esbUser+"--ESB密码："+esbPassword);
        return response.getResponseData();
    }

    /**
     * 解析ESB返回的XML
     *
     * @param xml 待解析XML
     */
    private static ESBResult parseAction(String xml) {
        ESBResult result = new ESBResult();
        List<?> rows;
        Element element;

        /*
         * 1、判定是否有错误信息
         */
        element = queryElement(xml, ESBXPath.xPathToError());
        if (element != null) {
            result.setResultcode(false);
            result.setResultstring(element.attributeValue("msg"));
            return result;
        }
        /*
         * 2、判定是否有提示信息
         */
        element = queryElement(xml, ESBXPath.xPathToInformation());
        if (element != null) {
            result.setResultcode(false);
            result.setResultstring(element.attributeValue("information"));
        }
        /*
         * 3、解析结果集
         */
        element = queryElement(xml, ESBXPath.xPathToResultSet());
        if (element != null) {
            result.setResultcode(true);
            List<Map<String, String>> content = new ArrayList<>();
            rows = element.selectNodes("row");
            for (Object obj : rows) {
                element = (Element) obj;
                Map<String, String> json = new HashMap<>(16);
                List<Attribute> listAttr = element.attributes();
                // 遍历当前节点的所有属性
                for (Attribute attr : listAttr) {
                    // 属性名称
                    String name = attr.getName();
                    // 属性的值
                    String value = attr.getValue();
                    json.put(name, value);
                }
                content.add(json);
            }
            result.setContent(content);
            result.setContentsize(content.size());
        }
        /*
         * 4、解析总记录数
         */
        element = queryElement(xml, ESBXPath.xPathToRowcount());
        if (element != null) {
            result.setTotal(element.attributeValue("rowCount"));
        }
        /*
         * 5、解析总页数
         */
        element = queryElement(xml, ESBXPath.xPathToPages());
        if (element != null) {
            result.setTotalPages(element.attributeValue("pages"));
        }
        return result;
    }

    /**
     * 根据XPath查找ESB返回值对应的element
     *
     * @param xml   ESB服务返回值
     * @param xpath 查找的XPath
     * @return 查找的element，如果esb调用出错或者xpath没有配置好，返回null
     */
    private static Element queryElement(String xml, String xpath) {
        Element element = null;
        try {
            Map<String, String> map = new HashMap<>(16);
            if (xml.indexOf("http://www.molss.gov.cn/") > 0) {
                map.put("out", "http://www.molss.gov.cn/");
            } else if (xml.indexOf("http://www.ylzinfo.com/") > 0) {
                map.put("out", "http://www.ylzinfo.com/");
            }
            map.put("soap", "http://schemas.xmlsoap.org/soap/envelope/");
            Document doc = DocumentHelper.parseText(xml);
            XPath xPath = DocumentHelper.createXPath(xpath);
            xPath.setNamespaceURIs(map);
            element = (Element) xPath.selectSingleNode(doc);
        } catch (Exception e) {
           log.error(e.getMessage());
        }
        return element;
    }

}