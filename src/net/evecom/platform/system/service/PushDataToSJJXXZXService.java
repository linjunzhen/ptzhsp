/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述 推送省经济信息中心服务接口
 * @SuppressWarnings("rawtypes")
 * @author Jason Lin
 * @created 2021年12月2日 下午3:45:06
 * @version 1.0
 */
public interface PushDataToSJJXXZXService extends BaseService {
    
    /**
     * 描述  轮询推送省经济中心变动记录(进驻事项资源报送)
     * 
     * @author Jason Lin
     * @created 2021年12月2日 下午1:28:08
     * @param requestMap
     */
    public void pollingWithAddProcessItemSourceInfo();
    
    /**
     * 描述  轮询推送省经济中心变动记录(行政服务中心窗口资源报送)
     * 
     * @author Jason Lin
     * @created 2021年12月2日 下午1:28:08
     * @param dataAbutment
     */
    public void pollingWithAddCounterSourceInfo();
    
    /**
     * 描述 推送到省经济信息中心公共发送方法   供定时器调用
     * 
     * @author Jason Lin
     * @created 2021年12月2日 下午1:28:08
     */
    public void sendDataToSJJXXZX();
    
    /**
     * 描述 实时推送省经济信息中心公共发送方法   供定时器调用
     * 
     * @author Jason Lin
     * @created 2021年12月2日 下午1:28:08
     * @param sendJsonStr
     */
    public void sendDataToSJJXXZXTrueTime();

    
    /** 
     * 描述 推送数据至省经济中心
     * 
     * @author Jason Lin
     * @created 2021年12月2日 下午1:28:08
     * @param sendJsonStr
     */
    public void pushDateToSJJXXZX(Map<String,Object> variables,String operationType);
    
    /** 
     * 描述 推送生成子线程
     * 
     * @author Jason Lin
     * @created 2021年12月2日 下午1:28:08
     * @param sendJsonStr
     */
    public void startNewThread(Map<String, Object> variables, String operationType);
    
}
