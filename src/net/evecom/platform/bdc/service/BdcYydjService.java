/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述  异议登记操作service
 * @author Allin Lin
 * @created 2020年4月29日 下午2:10:41
 */
public interface BdcYydjService extends BaseService{
    
    /**
     * 描述     异议登记数据回填
     * @author Allin Lin
     * @created 2020年4月30日 下午4:51:22
     * @param args
     */
    public void setYydjData(Map<String, Map<String, Object>> args);

}
