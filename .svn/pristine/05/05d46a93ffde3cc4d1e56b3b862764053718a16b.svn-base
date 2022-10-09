/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 对接配置操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface DataAbutmentService extends BaseService {
    /**
     * 调用方式:定时调用
     */
    public static final String INVOKE_TYPE_JOB = "1";
    /**
     * 调用方式:触发调用
     */
    public static final String INVOKE_TYPE_TRIGGER = "2";
    /**
     * 网络环境:政务内网
     */
    public static final String NETWORK_TYPE_INNER = "1";
    /**
     * 网络环境:互联网
     */
    public static final String NETWORK_TYPE_WEB = "2";
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据接口调用方式获取列表数据
     * @author Flex Hu
     * @created 2015年10月12日 下午5:18:09
     * @param typeValue
     * @return
     */
    public List<Map<String,Object>> findByInvokeType(String typeValue,String networkType);
}
