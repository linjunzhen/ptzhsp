/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 不动产-测绘管理service()
 * @author Allin Lin
 * @created 2020年12月16日 上午10:59:49
 */
public interface BdcSurveyService extends BaseService{
       
    /**
     * 根据sqlfilter获取测绘管理数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    public List<Map<String, Object>> findBySqlFileterExport(SqlFilter sqlFilter);

}
