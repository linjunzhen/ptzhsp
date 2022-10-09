/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import net.evecom.core.service.BaseService;
import net.evecom.platform.wsbs.model.TzProjectRespones;
import org.apache.commons.logging.Log;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 省网办接口服务业务接口(推送总线数据）
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月13日 上午9:12:54
 */
@SuppressWarnings("rawtypes")
public interface SwbInterOfBusService extends BaseService {
    /**
     * 转发总线数据到省网办
     */
    public void sendToProvince();
}
