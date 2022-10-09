/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.utils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import net.evecom.platform.evaluate.config.GatewayAutoConfiguration;
import net.evecom.platform.evaluate.constant.ErrorConstant;
import net.evecom.platform.evaluate.service.impl.EvaluateServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Http 客户端工具类
 *
 * @author Luffy Cai
 */
public class HttpClientUtils {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(HttpClientUtils.class);
    /**
     *
     */
    static final int WARN_TIME_OUT = 3000;
    /**
     * 将每个路由基础的连接增加到
     */
    public static final int DEFAULT_MAX_PER_ROUTE = 300;
    /**
     * 将最大连接数增加到
     */
    //
    public static final int MAX_TOTAL = 600;
    /**
     * 连接超时时间
     */
    public static final int REQUEST_TIMEOUT = 2 * 1000;
    /**
     * 读取数据超时时间
     */
    public static final int REQUEST_SOCKET_TIME = 30 * 1000;
    /**
    *
    */
    private static CloseableHttpClient closeableHttpClient;
    /**
    *
    */
    private static PoolingHttpClientConnectionManager connManager;

    /**
    *
    */
    static CloseableHttpClient getHttpClient() {
        if (null == closeableHttpClient) {
            synchronized (HttpClientUtils.class) {
                if (null == closeableHttpClient) {
                    // 采用绕过验证的方式处理https请求
                    Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
                    try {
                        SSLContext sslcontext = createIgnoreVerifySSL();
                        // 设置协议http和https对应的处理socket链接工厂的对象
                        socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                                .register("http", PlainConnectionSocketFactory.INSTANCE)
                                .register("https", new SSLConnectionSocketFactory(sslcontext)).build();
                    } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
                    if (null != socketFactoryRegistry) {
                        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                    } else {
                        connManager = new PoolingHttpClientConnectionManager();
                    }
                    connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
                    connManager.setMaxTotal(MAX_TOTAL);
                    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(REQUEST_SOCKET_TIME)
                            .setConnectTimeout(REQUEST_TIMEOUT).build();
                    // 创建自定义的httpclient对象
                    closeableHttpClient = HttpClients.custom().setConnectionManager(connManager)
                            .setDefaultRequestConfig(requestConfig).build();
                }
            }
        }
        return closeableHttpClient;
    }

    /**
    *
    */
    public static HttpResponse httpGet(String url) throws Exception {
        return httpGet(url, null, null, null);
    }

    /**
     * 发送http get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static HttpResponse httpGet(String url, Map<String, String> params) throws Exception {
        return httpGet(url, params, null, null);
    }

    /**
     * 发送http get请求
     *
     * @throws Exception
     */
    public static HttpResponse httpGet(String url, Map<String, String> params, Map<String, String> headers,
            String encode) throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpGet httpGet;
        if (null == params) {
            httpGet = new HttpGet(url);
        } else {
            URIBuilder uriBuilder = new URIBuilder(url);
            // 组织请求参数
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            uriBuilder.setParameters(paramList);
            httpGet = new HttpGet(uriBuilder.build());
        }
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return execute(httpGet, encode);
    }

    /**
     * 发送http post请求参数拼接方式
     *
     * @param url
     * @return
     */
    public static HttpResponse httpPostParams(String url) throws Exception {
        return httpPostParams(url, null, null, null);
    }

    /**
     * 发送http post请求参数拼接方式
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static HttpResponse httpPostParams(String url, Map<String, String> params) throws Exception {
        return httpPostParams(url, params, null, null);
    }

    /**
     * 发送http post请求参数拼接方式
     *
     * @throws Exception
     */
    public static HttpResponse httpPostParams(String url, Map<String, String> params, Map<String, String> headers,
            String encode) throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpPost httpPost;
        if (null == params) {
            httpPost = new HttpPost(url);
        } else {
            URIBuilder uriBuilder = new URIBuilder(url);
            // 组织请求参数
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            uriBuilder.setParameters(paramList);
            httpPost = new HttpPost(uriBuilder.build());
        }
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return execute(httpPost, encode);
    }

    /**
    *
    */
    public static HttpResponse execute(HttpRequestBase request, String encode) throws Exception {
        long startTime = System.currentTimeMillis();
        GatewayAutoConfiguration.preExecute(request);
        HttpResponse response = new HttpResponse();
        // 获取结果实体
        HttpEntity entity = null;
        try {
            // 执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse httpResponse = getHttpClient().execute(request);
            entity = httpResponse.getEntity();
            // 按指定编码转换结果实体为String类型
            String content = EntityUtils.toString(entity, encode);
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setHeaders(httpResponse.getAllHeaders());
            response.setBody(content);
            boolean success;
            success = GatewayAutoConfiguration.afterExecute(response);
            if (!success) {
                // 失败时，重新请求
                return execute(request, encode);
            }
            return response;
        } finally {
            if (null != entity) {
                EntityUtils.consume(entity);
            }
            // 释放连接，回到连接池
            request.releaseConnection();
            long spend = System.currentTimeMillis() - startTime;
            if (spend >= WARN_TIME_OUT) {

            } else {

            }
        }
    }

    /**
     * 执行http请求
     *
     * @param request
     * @return
     * @throws Exception
     */
    static CloseableHttpResponse execute(HttpUriRequest request) throws Exception {
        GatewayAutoConfiguration.preExecute(request);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse httpResponse = getHttpClient().execute(request);
        return httpResponse;
    }

    /**
     * 发送http post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpResponse httpPost(String url, Object params) throws Exception {
        return httpPost(url, params, null, null);
    }

    /**
     * 发送http post请求
     *
     * @param url
     * @param params
     * @param headers
     * @param encode
     * @return
     * @throws Exception
     */
    public static HttpResponse httpPost(String url, Object params, Map<String, String> headers, String encode)
            throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpPost httpPost = new HttpPost(url);

        // 设置header
        httpPost.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        StringEntity stringEntity = new StringEntity(JsonUtil.toJson(params), encode);
        httpPost.setEntity(stringEntity);
        return execute(httpPost, encode);
    }

    /**
    *
    */
    public static HttpResponse upload(String baseUrl, File file, Map<String, String> params) throws Exception {
        HttpPost httpPost = new HttpPost(baseUrl);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("utf-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        Iterator var5 = params.entrySet().iterator();

        while (var5.hasNext()) {
            Entry e = (Entry) var5.next();
            builder.addTextBody((String) e.getKey(), (String) e.getValue());
        }

        builder.addBinaryBody("file", new FileInputStream(file), ContentType.DEFAULT_BINARY, file.getName());
        httpPost.setEntity(builder.build());
        return HttpClientUtils.execute(httpPost, "utf-8");
    }

    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpResponse httpPostForm(String url, Map<String, String> params) throws Exception {
        return httpPostForm(url, params, null, null);
    }

    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     *
     * @throws Exception
     */
    public static HttpResponse httpPostForm(String url, Map<String, String> params, Map<String, String> headers,
            String encode) throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpPost httpPost = new HttpPost(url);
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            log.error(e1.getMessage(),e1);
        }
        return execute(httpPost, encode);
    }

    /**
     * 发送http put请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpResponse httpPutRaw(String url, Object params) throws Exception {
        return httpPutRaw(url, params, null, null);
    }

    /**
     * 发送http put请求
     *
     * @param url
     * @param encode
     * @return
     * @throws Exception
     */
    public static HttpResponse httpPutRaw(String url, Object params, Map<String, String> headers, String encode)
            throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpPut httpPut = new HttpPut(url);

        // 设置header
        httpPut.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPut.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        StringEntity stringEntity = new StringEntity(JsonUtil.toJson(params), encode);
        httpPut.setEntity(stringEntity);
        return execute(httpPut, encode);
    }

    /**
     * 发送http delete请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static HttpResponse httpDelete(String url) throws Exception {
        return httpDelete(url, null, null);
    }

    /**
     * 发送http delete请求
     *
     * @throws IOException
     * @throws ClientProtocolException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static HttpResponse httpDelete(String url, Map<String, String> headers, String encode) throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpDelete httpDelete = new HttpDelete(url);
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpDelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return execute(httpDelete, encode);
    }

    /**
     * 关闭连接池
     */
    public static void destory() {
        try {
            getHttpClient().close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        closeableHttpClient = null;
    }

    /**
     * Http请求返回值
     *
     * @author Luffy Cai
     */
    public static class HttpResponse {
        /**
         * 状态码
         */
        private int statusCode;
        /**
         * 请求头
         */
        private Header[] headers;
        /**
         * 实体内容
         */
        private String body;
        /**
         * 请求错误原因
         */
        private String reasonPhrase;

        /**
        *
        */
        public int getStatusCode() {
            return statusCode;
        }

        /**
        *
        */
        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        /**
        *
        */
        public Header[] getHeaders() {
            return headers;
        }

        /**
        *
        */
        public void setHeaders(Header[] headers) {
            this.headers = headers;
        }

        /**
        *
        */
        public String getBody() {
            return body;
        }

        /**
        *
        */
        public void setBody(String body) {
            this.body = body;
        }

        /**
        *
        */
        public String getReasonPhrase() {
            return reasonPhrase;
        }

        /**
        *
        */
        public void setReasonPhrase(String reasonPhrase) {
            this.reasonPhrase = reasonPhrase;
        }

        /**
         * 判断请求是否成功
         *
         * @return
         */
        public boolean isSuccess() {
            return statusCode == ErrorConstant.OK;
        }

        /**
         * 获取请求失败描述
         *
         * @return
         */
        public String getErrorMsg() {
            if (null != headers) {
                for (Header header : headers) {
                    if ("X-Error-Code".equalsIgnoreCase(header.getName())) {
                        return header.toString();
                    }
                }
            }
            return reasonPhrase;
        }

        /**
        *
        */
        @Override
        public String toString() {
            return "HttpResponse{" + "statusCode=" + statusCode + ", headers=" + Arrays.toString(headers) + ", body='"
                    + body + '\'' + ", reasonPhrase='" + reasonPhrase + '\'' + '}';
        }
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLSv1.2");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    /**
     * 发送http get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static HttpResponse httpGetAuth(String url, Map<String, String> params) throws Exception {
        return httpGetAuth(url, params, null, null);
    }

    /**
     * 发送http get请求
     *
     * @throws Exception
     */
    public static HttpResponse httpGetAuth(String url, Map<String, String> params, Map<String, String> headers,
            String encode) throws Exception {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpGet httpGet;
        if (null == params) {
            httpGet = new HttpGet(url);
        } else {
            URIBuilder uriBuilder = new URIBuilder(url);
            // 组织请求参数
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            if (params != null && params.size() > 0) {
                Set<String> keySet = params.keySet();
                for (String key : keySet) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
            }
            uriBuilder.setParameters(paramList);
            httpGet = new HttpGet(uriBuilder.build());
        }
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return executeAuth(httpGet, encode);
    }

    /**
    *
    */
    public static HttpResponse executeAuth(HttpRequestBase request, String encode) throws Exception {
        long startTime = System.currentTimeMillis();

        HttpResponse response = new HttpResponse();
        // 获取结果实体
        HttpEntity entity = null;
        try {
            // 执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse httpResponse = getHttpClient().execute(request);
            entity = httpResponse.getEntity();
            // 按指定编码转换结果实体为String类型
            String content = EntityUtils.toString(entity, encode);
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setHeaders(httpResponse.getAllHeaders());
            response.setBody(content);
            if (response.getStatusCode() != ErrorConstant.OK) {

            }
            boolean success;
            success = GatewayAutoConfiguration.afterExecute(response);
            if (!success) {
                // 失败时，重新请求
                return executeAuth(request, encode);
            }
            return response;
        } finally {
            if (null != entity) {
                EntityUtils.consume(entity);
            }
            // 释放连接，回到连接池
            request.releaseConnection();
            long spend = System.currentTimeMillis() - startTime;
            if (spend >= WARN_TIME_OUT) {

            } else {

            }
        }
    }
}
