/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tpl.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 数据源管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ModelDsService extends BaseService {

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,String whereSql);
    

    /**
     * 
     * 描述 根据SQL获取数据列表
     * @author Rider Chen
     * @created 2015-12-8 下午02:46:33
     * @param sql
     * @param queryParams
     * @return
     */
    public List<Map<String,Object>> findBySql(String sql, Object[] queryParams);
}
