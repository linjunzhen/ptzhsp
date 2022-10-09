/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model;

import net.evecom.core.util.AesUtils;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.axis.components.uuid.UUIDGen;

/**
 * 描述   公安地址接口实体类
 * @author Flex Hu
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class AddrPara {
    /**
     *1.3.1　A13标准地址全文检索查询接口服务标识
     */
    public static final String A13_SERVICE_ID="dzfwpt_search";
    /**
     *1.3.1　A13标准地址全文检索查询接口服务名称
     */
    public static final String A13_SERVICE_VALUE="dzfwpt_search";
    /**
     *1.3.1　A07标准地址全文检索查询接口服务标识
     */
    public static final String A07_SERVICE_ID="xjpt_sjfw_dzxx_qwjs";
    /**
     *1.3.1　A07标准地址全文检索查询接口服务名称
     */
    public static final String A07_SERVICE_VALUE="xjpt_sjfw_dzxx_qwjs";
    /**
     * 接口地址
     */
    public static final String ADDR_URL="http://218.85.72.214/ywxzservice/dbClient.do";
    /**
     *appId
     */
    public  static final String ADDR_APP_ID="3887B6D3A524845E";
    /**
     *秘钥
     */
    public  static final String ADDR_SECRET_KEY="0B27E0563887B6D3A524845E6C9A2E54";
    /**
     *秘钥
     */
    public  static final String ADDR_AES_PSWD="0B27E0563887B6D3A524845E6C9A2E54";
    /**
     * 地市编码
     */
    public static final String ADDR_DSBM="350100";
}
