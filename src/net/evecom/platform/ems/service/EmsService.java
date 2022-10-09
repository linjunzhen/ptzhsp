/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.ems.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年2月11日 下午4:01:41
 */
public interface EmsService extends BaseService {

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月11日 下午4:06:41
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findBySqlfilter(SqlFilter filter);
}
