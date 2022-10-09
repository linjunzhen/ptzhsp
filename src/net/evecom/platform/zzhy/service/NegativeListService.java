/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 负面清单操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface NegativeListService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,Map<String, Object> variables);
    
    /**
     * 
     * 描述 根据codes获取列表数据
     * @author Rider Chen
     * @created 2016年12月13日 下午2:17:09
     * @param userIds
     * @return
     */
    public List<Map<String,Object>> findByNegetiveListCOde(String codes);
}
