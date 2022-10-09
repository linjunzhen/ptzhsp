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
 * 描述：BusDeployService
 * 
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-9
 */
public interface BusDeployService extends BaseService {

    /**
     * 列表数据查询
     * 
     * @param filter
     * @return
     */
    public List<Map<String, Object>> datagrid(SqlFilter filter);

    /**
     * 描述(通过部门和操作申报号获得部署图相关的信息)
     * @author Water Guo
     * @created 2015-9-10 下午2:55:21
     * @param unitCode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> getListHistoryByApply(String unitCode, String applyId);

}
