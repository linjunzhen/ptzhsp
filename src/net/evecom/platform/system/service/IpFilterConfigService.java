/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年9月11日 上午9:56:39
 */
public interface IpFilterConfigService extends BaseService {
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:32:46
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:41:57
     * @param userIds
     * @param status
     */
    public void updateConfigStatus(String userIds, int status);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 下午3:00:01
     * @return
     */
    public List<Map<String,Object>> findEnableIpFilter();
}
