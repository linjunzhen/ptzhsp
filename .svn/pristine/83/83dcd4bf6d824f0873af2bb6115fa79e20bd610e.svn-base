/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;

import net.evecom.platform.evaluate.utils.HttpClientUtils;
import net.evecom.platform.evaluate.utils.JsonUtil;
import net.evecom.platform.evaluate.utils.Md5Util;

/**
 * 
 * @author Luffy Cai
 *
 */
public class GatewayAutoConfiguration {
    /**
     * 
     */
    private static String getToken = "/get-token";
    /**
     * 
     */
    private static String getRefreshToken = "/refresh-token";
    /**
     * 
     */
    private static GatewayAutoConfiguration instance;
    /**
     * 
     */
    private static String url = "http://api.zwfw.fujian.gov.cn:71/api-gateway/gateway";
    /**
     * 
     */
    private static String appKey = "公钥";
    /**
     * 
     */
    private static String appCode = "私钥";
    /**
     * 
     */
    private static String accessToken = "";
    /**
     * 
     */
    private static String refreshToken;
    /**
     * 
     */
    private static String reExpiresIn;

    /**
     * Http 请求前的操作
     *
     * @param request
     * @throws URISyntaxException
     */
    public static void preExecute(HttpUriRequest request) throws Exception {
        synchronized (GatewayAutoConfiguration.class) {
            String tokenUrl = "http://api.zwfw.fujian.gov.cn:71/api-gateway/gateway/get-token";
            Map<String, String> params = new HashMap<>();
            params.put("app_key", "7541b31fe62a44f9bcb60a0d47100fc9");
            long timestamp = System.currentTimeMillis();
            params.put("timestamp", String.valueOf(timestamp));
            params.put("sign", Md5Util.md5("a325f2b70f8640b381c14321c319ac59​" + timestamp));
            HttpClientUtils.HttpResponse httpResponse = HttpClientUtils.httpGetAuth(tokenUrl, params);
            if (httpResponse.getStatusCode() == 200) {
                String json = httpResponse.getBody();
                Map map = JsonUtil.fromJson(json, Map.class);
                accessToken = (String) map.get("access_token");
                refreshToken = (String) map.get("refresh_token");
                reExpiresIn = (String) map.get("re_expires_in");
            } else {
                return;
            }
        }
        // 设置请求头的token
        request.setHeader("Authorization", "Basic " + accessToken);
    }

    /**
     * http请求后的操作
     *
     * @param response
     * @return
     */
    public static boolean afterExecute(HttpClientUtils.HttpResponse response) {
        if (null == instance) {
            return true;
        }
        boolean clearAccessToken = false;
        if (response.getStatusCode() == 400) {
            for (Header header : response.getHeaders()) {
                if ("x-error-code".equalsIgnoreCase(header.getName())) {
                    if ("access_token_invalid".equalsIgnoreCase(header.getValue())) {
                        clearAccessToken = true;
                        break;
                    }
                }
            }
        }
        if (clearAccessToken) {

            instance.accessToken = null;
            return false;
        }
        return true;
    }

    private static boolean shouldExecute(URI requestUri) throws URISyntaxException {
        
        requestUri = requestUri.normalize();
        URI uri = new URI(instance.url).normalize();
        if (!requestUri.toString().startsWith(uri.toString())) {
            return false;
        }
        uri = new URI(instance.url + getToken).normalize();
        if (requestUri.toString().startsWith(uri.toString())) {
            return false;
        }
        uri = new URI(instance.url + getRefreshToken).normalize();
        if (requestUri.toString().startsWith(uri.toString())) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "GatewayAutoConfiguration{" + "url='" + url + '\'' + ", appKey='" + appKey + '\'' + ", appCode='"
                + appCode + '\'' + '}';
    }

}
