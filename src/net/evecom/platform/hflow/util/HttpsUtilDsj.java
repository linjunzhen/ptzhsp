/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.util;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

/**
 * 描述  大数据中心ectip系统信息查询接口，封装了ssl
 * @author Madison You
 * @version 1.0
 * @created 2019年10月30日 上午08:35:23
 */
public class HttpsUtilDsj {

    /**
     * 描述:请求发送工具类
     *
     * @author Madison You
     * @created 2019/10/30 8:36:00
     * @param
     * @return
     */
    public String send(String httpsURL, String jsonStr,String departName) throws Exception {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        //把密钥库文件放在服务器目录下,生产环境实际部署时路径需要进行修改 /tomcat.keystore
        String keystoreFile = properties.getProperty("KEY_STORE_FILE_DSJ");
        String keystorePass = properties.getProperty("KEY_STORE_PASS_DSJ");
        //设置可通过ip地址访问https请求
        HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifierDsj());
        // 创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = { new MyX509TrustManagerDsj(keystoreFile,keystorePass) };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        URL url = new URL(httpsURL);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setSSLSocketFactory(ssf);
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        if (StringUtils.isNotEmpty(departName)) {
            con.setRequestProperty("re-app", departName);
        }
        PrintWriter out = new PrintWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
        out.print(jsonStr);
        out.flush();
        out.close();
        // 读取请求返回值
        InputStreamReader in = new InputStreamReader(con.getInputStream(),"UTF-8");
        BufferedReader bfreader = new BufferedReader(in);
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = bfreader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}