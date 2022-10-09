/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述 医保全流程材料签章
 * @author Madison You
 * @created 2021年01月18日 下午3:44:16
 */
public interface YbStampService extends BaseService {

    /**
     * 描述:设置腾讯网关请求头
     *
     * @author Madison You
     * @created 2021/1/18 10:31:00
     * @param
     * @return
     */
    Map<String, String> setTencentNetHeadMap();

    /**
     * 描述:基本医疗保障参保签章材料数据
     *
     * @author Madison You
     * @created 2021/1/18 10:37:00
     * @param 
     * @return 
     */
    Map<String, Object> jbylbzcbStamp(Map<String, Map<String, Object>> args);

    /**
     * 描述:生育保险参保凭证签章材料数据
     *
     * @author Madison You
     * @created 2021/1/18 14:38:00
     * @param 
     * @return 
     */
    Map<String, Object> sybxcbpzStamp(Map<String, Map<String, Object>> args);

    /**
     * 描述:医保证明打印签章
     *
     * @author Madison You
     * @created 2021/3/2 10:41:00
     * @param
     * @return
     */
    Map<String, Object> ybzmdyStamp(Map<String, Map<String, Object>> args);

    /**
     * 描述:医保参保缴费证明打印签章
     *
     * @author Madison You
     * @created 2021/3/2 15:48:00
     * @param
     * @return
     */
    Map<String, Object> ybcbjfzmdyStamp(Map<String, Map<String, Object>> args);

}
