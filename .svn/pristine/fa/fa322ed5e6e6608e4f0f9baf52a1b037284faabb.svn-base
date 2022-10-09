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
 * 描述 业务专项
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:51:37 PM
 */
public interface BusSpecialService extends BaseService {
    /**
     * 描述 根据sqlfilter获取到业务专项列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:28 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findSpecialBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述 根据sqlfilter获取到业务环节列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findTacheBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述 根据sqlfilter获取到过程编码列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findProcessBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述 根据业务编码查询业务对象
     * 
     * @author Toddle Chen
     * @created Aug 8, 2015 10:07:54 PM
     * @param busCode
     * @return
     */
    public Map<String, Object> getBusByBuscode(String busCode);

    /**
     * 描述 根据业务编码查询业务环节列表
     * 
     * @author Toddle Chen
     * @created Aug 5, 2015 11:07:34 AM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBuscode(String busCode);

    /**
     * 根据当前用户的所属单位编码查询专项
     * 
     * @author Water Guo
     * @param unitCode
     * @return
     */
    public List<Map<String, Object>> findSysByUnitCode(String unitCode);

    /**
     * 描述 根据sqlfilter获取到业务专项列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:28 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByUnitCode(String unitCode);

    /**
     * 根据单位编码 获取专项编码最大值
     * 
     * @author John Zhang
     * @created 2015-8-31 上午09:48:12
     * @param unitCode
     * @return
     */
    public int getMaxBusCode(String unitCode);

    /**
     * 获取业务环节编码最大值
     * 
     * @author John Zhang
     * @created 2015-8-31 下午03:33:44
     * @param busCode
     * @return
     */
    public int getMaxTacheCode(String busCode);

    /**
     * 确认审核核对项
     * 
     * @author John Zhang
     * @created 2015-9-16 下午04:37:21
     * @param busCode
     * @param configCode
     */
    public void confirmAudit(String busCode, String configCode);

    /**
     * like删除数据
     * 
     * @author John Zhang
     * @created 2015-10-21 上午09:30:42
     * @param tableName
     * @param colName
     * @param values
     */
    public void removeByLike(String tableName, String colName, Object[] values);

    /**
     * like查询数据
     * 
     * @author John Zhang
     * @created 2015-10-21 上午09:30:42
     * @param tableName
     * @param colName
     * @param values
     */
    public List<Map<String, Object>> getByLike(String tableName, String colName, Object[] values);

    /**
     * 根据专项编码查询业务基本信息
     * 
     * @author John Zhang
     * @created 2015-11-12 上午10:23:28
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findBasicColumn(String busCode, SqlFilter filter);

    /**
     * 
     * 根据专项编码获取监察字段
     * 
     * @author John Zhang
     * @created 2015-11-12 下午03:35:20
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findEsuperColumn(String busCode, SqlFilter filter);
}
