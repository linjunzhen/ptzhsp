/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 单据配置类操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface TicketsService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据ticketsId获取票单模板
     * @author Faker Li
     * @created 2015年10月19日 上午8:55:20
     * @param ticketsIds
     * @return
     */
    public List<Map<String, Object>> findByTicketsId(String ticketsIds);    
}
