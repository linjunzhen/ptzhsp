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
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
public interface ApasInfoService extends BaseService{
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 根据sqlfilter获取到数据不分页列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findExBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 根据sqlfilter获取到分批导出数据
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findExByBatch(SqlFilter sqlFilter,int start,int end);
    /**
     * 根据sqlfilter获取到分批导出数据
     * @param sqlFilter
     * @return
     */
    public int getCount(SqlFilter sqlFilter);
    
}
