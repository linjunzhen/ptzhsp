/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述   商事登记-行业管理service
 * @author Allin Lin
 * @created 2020年11月18日 上午11:02:36
 */
public interface IndustryService extends BaseService{
    
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    
    /**
     * 
     * 描述    获取指定主行业ID下的子行业信息
     * @author Allin Lin
     * @created 2020年11月18日 下午2:45:40
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findChildIndustryBySqlFilter(SqlFilter sqlFilter);
    
   
    /**
     * 
     * 描述    级联删除主行业相关信息
     * @author Allin Lin
     * @created 2020年11月18日 下午1:03:34
     * @param roleIds
     */
    public void removeIndustryCascade(String[] industryIds);
    /**
     *
     * 描述    获取指定主行业信息
     * @author Allin Lin
     * @created 2020年11月18日 下午2:45:40
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBussinessBySqlFilter(SqlFilter sqlFilter);
}
