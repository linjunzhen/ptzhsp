/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 电子监察预警反馈处理service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年3月2日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
public interface EsuperService extends BaseService {

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @author Derek Zhang
     * @version 1.0
     * @created 2016年3月2日 下午3:08:01
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
}
