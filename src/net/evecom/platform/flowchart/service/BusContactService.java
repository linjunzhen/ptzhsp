/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述：联系人信息
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-5
 */
public interface BusContactService extends BaseService {

    /**
     * 
     * @param filter
     * @return
     */
    public List<Map<String, Object>> datagrid(SqlFilter filter);

}
