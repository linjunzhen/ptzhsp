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
 * 描述 商事印章信息管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface CommercialSealService extends BaseService {
    /**
     * 
     * 描述 查询列表信息
     * @author Rider Chen
     * @created 2021年7月27日 上午10:00:28
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);
    /**
     * 
     * 描述 保存流程印章后置事件
     * @author Rider Chen
     * @created 2021年7月28日 上午11:26:38
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveFlowSealData(Map<String, Object> flowDatas);
    
}
