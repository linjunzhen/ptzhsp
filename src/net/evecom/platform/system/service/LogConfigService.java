/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
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
 * @created 2018年9月5日 下午4:52:34
 */
public interface LogConfigService extends BaseService {

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午5:19:35
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findConfigTableList(String parentId);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月5日 下午6:00:43
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findConfigListBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述    保存业务表维护日志
     * @author Danto Huang
     * @created 2018年9月6日 下午2:41:41
     * @param busTable
     * @param colNames
     * @param colValues
     * @param variables
     * @param entityId
     */
    public void saveLogForBusTable(String busTable, String colNames, String colValues, Map<String, Object> variables,
            String entityId);
}
