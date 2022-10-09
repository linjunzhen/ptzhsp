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
 * 描述 业务系统
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 3:10:46 PM
 */
public interface BusSysService extends BaseService {
    /**
     * 
     * 描述 根据sqlfilter获取到系统数据列表
     * 
     * @author Flex Hu
     * @created 2015年1月23日 上午9:27:00
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述 根据单位ID获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitId(String UnitId);
    /**
     * 描述 根据单位编码获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitCode(String unitCode);

    /***
     * @描述：查看所有的业务系统
     * @author Water Guo
     * @return
     * @date 2015-08-07 11:40 AM
     */
    public List<Map<String, Object>> findAllSystems();
}
