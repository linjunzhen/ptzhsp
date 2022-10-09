/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.service;

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
public interface BusSpecialChangeService extends BaseService {
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
     * 获取业务环节编码最大值
     * 
     * @author John Zhang
     * @created 2015-8-31 下午03:33:44
     * @param busCode
     * @return
     */
    public int getMaxTacheCode(String busCode);

    /**
     * 发起变更
     * 
     * @author John Zhang
     * @created 2015-9-2 上午11:37:25
     * @param formData
     */
    public void goChange(Map<String, Object> specialInfo, String changeCode);

    /**
     * 获取业务专项正在发起的变更
     * 
     * @author John Zhang
     * @created 2015-9-6 下午04:56:56
     * @param busCode
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findByBusCodeAndUserId(String busCode);

    /**
     * 获取未发起变更的列表
     * 
     * @author John Zhang
     * @created 2015-9-18 上午10:00:22
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findNotChanged(String name);

    /**
     * 确认变更项
     * 
     * @author John Zhang
     * @created 2015-9-17 下午05:13:22
     * @param applyId
     * @param busCode
     * @param configName
     * @return
     */
    public boolean confirmChange(String applyId, String busCode, String configName);

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
}
