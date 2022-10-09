/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 描述 获取HttpClient工具类
 * 
 * @author Bryce Zhang
 * @created 2016-5-2 下午6:33:01
 */
public class HttpClientUtil {

    /**
     * HttpClient连接池管理
     */
    private static PoolingHttpClientConnectionManager poolingCm = null;

    /**
     * HttpClient实例
     */
    private volatile static CloseableHttpClient httpClient = null;

    /**
     * request配置
     */
    private static RequestConfig requestConfig = null;

    static {
        poolingCm = new PoolingHttpClientConnectionManager();
        //Increase max total connection to 200
        poolingCm.setMaxTotal(500);
        //Increase default max connection per route to 20
        poolingCm.setDefaultMaxPerRoute(20);
        requestConfig = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
    }

    /**
     * 构造方法
     */
    private HttpClientUtil() {

    }

    /**
     * 描述 获取HttpClient实例
     * 
     * @author Bryce Zhang
     * @created 2016-5-2 下午6:36:15
     * @return
     */
    public static CloseableHttpClient getPlainHttpClient() {
        if (httpClient == null) {
            synchronized (CloseableHttpClient.class) {
                if (httpClient == null) {
                    httpClient = HttpClients.custom().setConnectionManager(poolingCm)
                            .setDefaultRequestConfig(requestConfig).build();
                }
            }
        }
        return httpClient;
    }

}