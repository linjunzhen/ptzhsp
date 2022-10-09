/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 处理编号配置中的参数接口
 * 
 * @author Derek Zhang
 * @created 2015年9月30日 下午4:52:55
 */
@SuppressWarnings("rawtypes")
public interface SerialParamsService extends BaseService {

    /**
     * 描述 【行政区划】处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String xzqhExecute(String serialNumber, String param, Map<String, String> otherParam);

    /**
     * 描述 【年】 处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String yearExecute(String serialNumber, String param, Map<String, String> otherParam);

    /**
     * 描述 【月】 处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String monthExecute(String serialNumber, String param, Map<String, String> otherParam);

    /**
     * 描述 【日】 处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String dayExecute(String serialNumber, String param, Map<String, String> otherParam);

    /**
     * 描述 【年月日】 处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String ymdExecute(String serialNumber, String param, Map<String, String> otherParam);

    /**
     * 描述 【年月日时分秒】 处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String ymdhmsExecute(String serialNumber, String param, Map<String, String> otherParam);

    /**
     * 描述 【年月日时分秒】 处理
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param serialNumber
     *            param
     * @return String
     */
    public String seqExecute(String serialNumber, String param, Map<String, String> otherParam);
}
