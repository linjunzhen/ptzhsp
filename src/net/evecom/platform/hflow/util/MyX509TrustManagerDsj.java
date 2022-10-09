/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.util;

import net.evecom.core.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 描述  大数据中心ectip系统信息查询接口，封装了ssl
 * @author Madison You
 * @version 1.0
 * @created 2019年10月30日 上午08:35:23
 */
public class MyX509TrustManagerDsj implements X509TrustManager{

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FileUtil.class);

    /**
     * bean
     */
    X509TrustManager sunJSSEX509TrustManager;

    /**
     * 描述:ssl工具类
     *
     * @author Madison You
     * @created 2019/10/30 8:43:00
     * @param
     * @return
     */
    MyX509TrustManagerDsj(String keystoreFile, String pass) {
        FileInputStream fileInputStream = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            fileInputStream = new FileInputStream(keystoreFile);
            ks.load(fileInputStream, pass.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
            tmf.init(ks);
            TrustManager tms[] = tmf.getTrustManagers();
            for (int i = 0; i < tms.length; i++) {
                if (tms[i] instanceof X509TrustManager) {
                    sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                    return;
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
    }

    /**
     * 描述:ssl工具类
     *
     * @author Madison You
     * @created 2019/10/30 8:43:00
     * @param
     * @return
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        try {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException excep) {
            log.info(excep.getMessage());
        }
    }

    /**
     * 描述:ssl工具类
     *
     * @author Madison You
     * @created 2019/10/30 8:43:00
     * @param
     * @return
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        try {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException excep) {
            log.info(excep.getMessage());
        }
    }

    /**
     * 描述:ssl工具类
     *
     * @author Madison You
     * @created 2019/10/30 8:43:00
     * @param
     * @return
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return sunJSSEX509TrustManager.getAcceptedIssuers();
    }
}
