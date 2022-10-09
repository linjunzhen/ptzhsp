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
 * 描述 辖区分局管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface JurisdictionService extends BaseService {
    /**
     * 
     * 描述：查询列表信息
     * @author Rider Chen
     * @created 2020年12月2日 上午10:26:30
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);
    /**
     * 
     * 描述：
     * @author Rider Chen
     * @created 2020年12月2日 上午11:00:00
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasForSelect(String status);

    /**
     * 获取辖区管理局
     * @param registerAddr
     * @return
     */
    public String getLocalArea(String registerAddr);
}
