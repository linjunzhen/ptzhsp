/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 全测合一service
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2021年6月18日 上午9:37:08
 */
@SuppressWarnings("rawtypes")
public interface ProjectFullTestService extends BaseService {
    /**
     * 
     * 描述：查询列表信息
     * 
     * @author Scolder Lin
     * @created 2021年6月18日 上午10:18:15
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
}
