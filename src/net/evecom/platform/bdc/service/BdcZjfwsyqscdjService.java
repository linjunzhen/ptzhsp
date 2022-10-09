/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述  宅基地使用权及房屋所有权首次登记业务Service
 * @author Allin Lin
 * @created 2020年8月12日 上午11:38:09
 */
public interface BdcZjfwsyqscdjService extends BaseService{
    
    /** 
     * 描述    宅基地使用权及房屋所有权首次登记业务审批表业务数据
     * @author Allin Lin
     * @created 2020年8月12日 上午11:42:38
     * @param args
     */
    public void setZjfwsyqscdjData(Map<String, Map<String, Object>> args);

}
