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
 * 描述：业务数量估算管理
 * 
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-6
 */
public interface EstimateService extends BaseService {

    /**
     * 表格数据
     * 
     * @param filter
     * @return
     */
    public List<Map<String, Object>> datagrid(SqlFilter filter);

    /**
     * 描述通过部门和操作申报号获得业务估算信息相关的信息
     * @author Water Guo
     * @created 2015-9-10 下午3:08:53
     * @param unitCode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> getListHistoryByApply(String unitCode, String applyId);

}
