/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述  宅基地使用权及房屋所有权变更登记service
 * @author Allin Lin
 * @created 2020年5月20日 上午11:18:13
 */
public interface BdcZjfwsybgdjService extends BaseService{

    /**
     * 描述     宅基地使用权及房屋所有权变更登记审批表数据
     * @author Allin Lin
     * @created 2020年5月20日 上午11:25:55
     * @param args
     */
    public void setZjfwsybgdjData(Map<String, Map<String, Object>> args);
}
