/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 webService服务类
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月12日 上午11:22:20
 */
public class WebServiceCallUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(WebServiceCallUtil.class);
    /**
     * COOKIE_NAME
     */
    public final static String COOKIE_NAME = "_session_cookie";
    /**
     * COOKIE_ATTR
     */
    public final static String COOKIE_ATTR = "Cookie";

    /**
     * 
     * 描述 调用webService
     * 
     * @author Derek Zhang
     * @created 2015年10月12日 下午2:45:09
     * @param wsdlPoint
     *            webService地址
     * @param namespace
     *            webService命名空间
     * @param method
     *            调用的方法
     * @param map
     *            参数
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object callService(String wsdlPoint, String namespace, String method,
            Map<String, Object[]> map, QName returnXMLType) {
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
            call.setSOAPActionURI(namespace + "/" + method);
            res = call.invoke(list.toArray());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {

        }
        return res;
    }

    /**
     * 
     * 描述 调用webService 通过webService地址 传xml报文
     * 
     * @author Derek Zhang
     * @created 2015年10月12日 下午2:45:09
     * @param wsdlPoint
     * @param namespace
     * @param method
     * @param map
     * @return
     */
    public static String callService(String wsdlUrl, String namespace, String method,
            String xmlContent, String contentType) {
        return callService(wsdlUrl, namespace, method, xmlContent, contentType, "UTF-8");
    }

    /**
     * 
     * 描述 调用webService 通过webService地址 传xml报文
     * 
     * @author Derek Zhang
     * @created 2015年10月12日 下午2:45:09
     * @param wsdlPoint
     * @param namespace
     * @param method
     * @param map
     * @return
     */
    public static String callService(String wsdlUrl, String namespace, String method,
            String xmlContent, String contentType, String charsetEncode) {
        return callService(wsdlUrl, namespace, method, xmlContent, contentType, charsetEncode,
                false, null);
    }
    /**
     * 
     * 描述 调用webService 通过webService地址 传xml报文
     * 
     * @author Derek Zhang
     * @created 2015年10月12日 下午2:45:09
     * @param wsdlPoint
     * @param namespace
     * @param method
     * @param map
     * @return
     */
    public static String callService(String wsdlUrl, String namespace, String method,
            String xmlContent, String contentType, String charsetEncode, boolean hasCookie,
            Map<String, String> cookie) {
        String result = "";
        OutputStream os = null;
        InputStreamReader in = null;
        BufferedReader bin = null;
        OutputStreamWriter osw = null;
        try {
            settrustAllHttpsCertificates();
            String soap = xmlContent;
            URL url = new URL(wsdlUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", contentType);// "text/xml; charset=utf-8"
            conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
            conn.setRequestProperty("SOAPAction", namespace + method);
            if (hasCookie) {
                if (cookie != null && cookie.get(WebServiceCallUtil.COOKIE_NAME) != null) {
                    conn.setRequestProperty(WebServiceCallUtil.COOKIE_ATTR,
                            (String) (cookie.get(WebServiceCallUtil.COOKIE_NAME)));
                }
            }
            conn.setRequestMethod("POST");
            os = conn.getOutputStream();
            osw = new OutputStreamWriter(os,charsetEncode);
            osw.write(soap);
            osw.flush();
            osw.close();
            InputStream is = conn.getInputStream();
            if (hasCookie) {
                if (cookie == null || cookie.get(WebServiceCallUtil.COOKIE_NAME) == null) {
                    cookie.put(WebServiceCallUtil.COOKIE_NAME,
                            (String) conn.getHeaderField("Set-Cookie"));
                }
            }
            in = new InputStreamReader(is,charsetEncode);
            bin = new BufferedReader(in);
            String valueString = null;
            while ((valueString = bin.readLine()) != null) {
                result = valueString;
            }
            bin.close();
            conn.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (osw!=null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (bin!=null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return result;
    }
    /**
     * 
     * 描述 调用webService 通过webService地址 传xml报文
     * 
     * @author Derek Zhang
     * @created 2015年10月12日 下午2:45:09
     * @param wsdlPoint
     * @param namespace
     * @param method
     * @param map
     * @return
     */
    public static String callServiceNotSSL(String wsdlUrl, String namespace, String method,
            String xmlContent, String contentType, String charsetEncode) {
        String result = "";
        InputStreamReader in = null;
        OutputStream os = null;
        BufferedReader bin = null;
        OutputStreamWriter osw = null;
        try {
            String soap = xmlContent;
            URL url = new URL(wsdlUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", contentType);// "text/xml; charset=utf-8"
            conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
            conn.setRequestProperty("SOAPAction", namespace + method);
            conn.setRequestMethod("POST");
            os = conn.getOutputStream();
            osw = new OutputStreamWriter(os,charsetEncode);
            osw.write(soap);
            osw.flush();
            osw.close();
            InputStream is = conn.getInputStream();
            in = new InputStreamReader(is,charsetEncode);
            bin = new BufferedReader(in);
            String valueString = null;
            while ((valueString = bin.readLine()) != null) {
                result = valueString;
            }
            bin.close();
            conn.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (osw!=null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (bin!=null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return result;
    }
    /**
     * 
     * 描述  
     * @author Derek Zhang
     * @created 2016年2月18日 下午1:37:08
     * @throws Exception
     */
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new MiTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    /**
     * 描述    
     * @author Derek Zhang
     * @created 2016年2月18日 下午1:37:08
     * @throws Exception
     */
    static class MiTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
            throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
            throws java.security.cert.CertificateException {
            return;
        }
    }
    /**
     * 描述    
     * @author Derek Zhang
     * @created 2016年2月18日 下午1:37:08
     * @throws Exception
     */
    private static void settrustAllHttpsCertificates() throws Exception {
        trustAllHttpsCertificates();
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                log.info("Warning: URL Host: " + urlHostName + " vs. "
                        + session.getPeerHost());
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    /**
     * 
     * 描述 测试
     * 
     * @author Derek Zhang
     * @created 2015年10月12日 下午3:46:14
     * @param agr0
     */
    public static void main(String[] agr0) {
        StringBuffer xml = new StringBuffer();
        String WSDL_POINT = "http://www.tyyh.fj.cn:8080/umc/services/ThirdPartySsoService?wsdl";
        String NAME_SPACE = "http://www.tyyh.fj.cn:8080/umc/services/ThirdPartySsoService";
        String methodName = "getDynamicPwdByThird"; // 单点登录
        // private final String methodName = "getUserBusinessCount"; // 获取待办统计
        // private final String methodName = "getUserBusinessList"; //获取待办业务
        Map<String, Object[]> params = new HashMap<String, Object[]>();
        xml.append("<?xml version='1.0' encoding='GB2312'?>");
        xml.append("<UMC xmlns='userInfo'>");
        xml.append("<UserInfo>");
        xml.append("  <uniqueCode>ss</uniqueCode>");
        xml.append("  <name>张三丰</name>");
        xml.append("  <sex>1</sex>");
        xml.append("  <roleCode>02</roleCode>");
        xml.append("  <roleName>部门管理员</roleName>");
        xml.append("  <mobile>18900900000</mobile>");
        xml.append("  <unitCode>66598543</unitCode>");
        xml.append("  <unitName>平潭综合实验区统计局</unitName>");
        xml.append("  <areaCode>350128</areaCode>");
        xml.append("  </UserInfo>");
        xml.append("</UMC>");
        params.put("unitAliasName", new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN,
            "350128" });
        params.put("password", new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN,
            "B24DF764D9BDEF2DFB0938ADD54F4B76" });
        params.put("userInfoXml",
                new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN, xml.toString() });
        Object o = WebServiceCallUtil.callService(WSDL_POINT, NAME_SPACE, methodName, params,
                org.apache.axis.encoding.XMLType.XSD_STRING);
        log.info(o.toString());
    }

}
