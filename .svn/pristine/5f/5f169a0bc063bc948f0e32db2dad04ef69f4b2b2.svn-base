/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.evecom.core.util.StringUtil;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述
 * 
 * @author Panda Chen
 * @created 2016-12-6 下午04:36:35
 */
public class WebServiceUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WebServiceUtil.class);
    /**
     * 
     * 描述 调用webservice
     * 
     * @author Panda Chen
     * @created 2016-12-6 下午04:37:40
     * @param wsdlPoint
     * @param namespace
     * @param method
     * @param map
     * @param returnXMLType
     * @return
     */
    public static Object callService(String wsdlPoint, String namespace, String method, Map<String, Object[]> map,
            QName returnXMLType) {
        Call call;
        Object res = "";
        Service service = new Service();
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(wsdlPoint));
            call.setOperationName(new QName(namespace, method));
            // 调协参数
            Iterator iterator = map.keySet().iterator();
            List<Object> list = new ArrayList<Object>();
            while (iterator.hasNext()) {
                String keyName = (String) iterator.next();
                Object[] oArray = (Object[]) map.get(keyName);
                call.addParameter(keyName, (QName) oArray[0], (ParameterMode) oArray[1]);
                list.add(oArray[2]);
            }
            // 返回信息类型
            call.setReturnType(returnXMLType);
            call.setUseSOAPAction(true);
            res = call.invoke(list.toArray());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {

        }
        return res;
    }

    /**
     * 
     * 描述 测试
     * 
     * @author Panda Chen
     * @created 2016-12-6 下午04:37:29
     * @param agr0
     */
    public static void main(String[] agr0) {
        String WSDL_POINT = "http://183.250.187.97:8081/services/syncService?wsdl";
        String NAME_SPACE = "com.rongji.uums.webservice";
        String methodName = "getPublicUserById"; // 单点登录
        Map<String, Object[]> map = new HashMap<String, Object[]>();
        Object[] objs0 = new Object[3];
        objs0[0] = XMLType.XSD_STRING;
        objs0[1] = ParameterMode.IN;
        objs0[2] = "TFUhGhfzhRWdecKuTA==";
        Object[] objs1 = new Object[3];
        objs1[0] = XMLType.XSD_STRING;
        objs1[1] = ParameterMode.IN;
        objs1[2] = "1";
        map.put("arg0", objs0);
        map.put("arg1", objs1);
        Object o = WebServiceUtil.callService(WSDL_POINT, NAME_SPACE, methodName, map, XMLType.XSD_STRING);
        log.info(o.toString());
    }
}
