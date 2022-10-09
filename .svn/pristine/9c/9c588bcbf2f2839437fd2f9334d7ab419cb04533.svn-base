/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.service;

import net.evecom.core.service.BaseService;

import org.apache.commons.logging.Log;

/**
 * 描述 电子监察数据报送业务处理service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年1月27日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
public interface EsuperClientService extends BaseService {
    
    /**
     * 
     * 描述   鉴权方法
     * @author Derek Zhang
     * @created 2016年3月1日 上午10:12:22
     * @return
     */
    public String serverHander();

    /**
     * 描述 定时调用的处理监察数据上报业务的服务
     * 
     * @author Derek Zhang
     * @created 2016年2月1日 下午2:36:12
     * @param log
     */
    public void sendEsupers(Log log);
}
