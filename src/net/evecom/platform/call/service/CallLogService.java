/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 第三方接口调用日志
 * @author Danto Huang
 * @created 2018年7月3日 下午3:58:22
 */
public interface CallLogService extends BaseService {

    /**
     * 保存第三方接口调用日志记录
     * @param LOG_START_TIME 请求开始时间戳
     * @param LOG_REQUEST_PARAM 请求参数
     * @param LOG_RESPONSE_STATUS 接口调用状态 0失败,1成功
     * @param LOG_RESPONSE_RESULT 返回结果
     * @param LOG_TYPE 接口类型:详见字典(interfaceCall)
     */
    void save(long LOG_START_TIME,String LOG_REQUEST_PARAM,int LOG_RESPONSE_STATUS
            ,String LOG_RESPONSE_RESULT,int LOG_TYPE);

    /**
     * 保存第三方接口调用日志记录
     * @param LOG_START_TIME 请求开始时间戳
     * @param LOG_REQUEST_PARAM 请求参数
     * @param LOG_RESPONSE_STATUS 接口调用状态 0失败,1成功
     * @param LOG_RESPONSE_RESULT 返回结果
     * @param LOG_TYPE 接口类型:详见字典(interfaceCall)
     * @param LOG_RETURN_ID
     * @param LOG_BUSINESS_NAME
     * @param LOG_BUSINESS_CODE
     * @param LOG_USER_NAME
     * @param LOG_DEPT_NAME
     */
    void save(long LOG_START_TIME,String LOG_REQUEST_PARAM,int LOG_RESPONSE_STATUS
            ,String LOG_RESPONSE_RESULT,int LOG_TYPE,String LOG_RETURN_ID,String LOG_BUSINESS_NAME
            ,String LOG_BUSINESS_CODE,String LOG_USER_NAME,String LOG_DEPT_NAME);

    /**
     * 保存第三方接口调用日志记录
     * @param LOG_START_TIME 请求开始时间戳
     * @param LOG_REQUEST_PARAM 请求参数
     * @param LOG_RESPONSE_STATUS 接口调用状态 0失败,1成功
     * @param LOG_RESPONSE_RESULT 返回结果
     * @param LOG_TYPE 接口类型:详见字典(interfaceCall)
     * @param EXE_ID
     */
    void save(long LOG_START_TIME,String LOG_REQUEST_PARAM,int LOG_RESPONSE_STATUS
            ,String LOG_RESPONSE_RESULT,int LOG_TYPE,String EXE_ID);

    /**
     * 根据申报号获取事项信息
     * @param EXE_ID
     * @return
     */
    Map<String,Object> getItemInfo(String EXE_ID);
}
