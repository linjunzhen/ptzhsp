/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 描述  大数据中心ectip系统信息查询接口，封装了ssl
 * @author Madison You
 * @version 1.0
 * @created 2019年10月30日 上午08:35:23
 */
public class NullHostNameVerifierDsj implements HostnameVerifier {

    /**
     * 描述:ssl工具类
     *
     * @author Madison You
     * @created 2019/10/30 8:47:00
     * @param
     * @return
     */
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
