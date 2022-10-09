/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import net.evecom.core.service.BaseService;
import org.apache.commons.logging.Log;

import java.util.Map;

/**
 * 描述 城建档案数据推送业务处理service
 * 
 * @author Adrian Bian
 * @version 1.0
 * @created 2019年12月27日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
public interface CjdaClientService extends BaseService {

    /**
     * 描述 定时调用的处理监察数据上报业务的服务
     * 
     * @author Derek Zhang
     * @created 2016年2月1日 下午2:36:12
     * @param log
     */
    public void sendCjda(Log log);

    /**
     * 后置事件-插入待推送信息进中间表
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveMiddleData(Map<String, Object> flowDatas);
}
