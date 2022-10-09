/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 缓存管理操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SysEhcacheService extends BaseService {
    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Faker Li
     * @created 2015年11月17日 上午11:26:43
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据key获取ehcachekey
     * @author Faker Li
     * @created 2015年11月17日 下午3:46:46
     * @param delEhcacheKeys
     * @return
     */
    public List<Map<String, Object>> findByDelEhcacheKeys(String delEhcacheKeys);
    /**
     * 
     * 描述 根据操作类型获取缓存列表
     * @author Faker Li
     * @created 2015年11月17日 下午3:46:46
     * @param delEhcacheKeys
     * @return
     */
    public List<Map<String, Object>> findByStatue(String statue);
    
}
