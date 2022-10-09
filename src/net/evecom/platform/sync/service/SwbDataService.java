/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.sync.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-8-25 上午9:54:02
 */
public interface SwbDataService extends BaseService {

    /**
     * 
     * 描述   获取未同步办件信息
     * @author Danto Huang
     * @created 2016-8-25 上午10:55:48
     * @return
     */
    public List<Map<String,Object>> getUnSyncBjxx(SqlFilter sqlFilter);
    /**
     * 
     * 描述 省网办返回信息
     * @author Kester Chen
     * @created 2017-11-6 下午3:35:38
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> getSwbReturnInfoData(SqlFilter sqlFilter);
    /**
     * 
     * 描述 省网办返回信息统计
     * @author Kester Chen
     * @created 2020年6月8日 下午3:45:13
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSwbReturnInfoStatisData(SqlFilter filter);

    /**
     * 根据sqlfilter获取到数据列表 (省网办返回信息)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySwbReturnInfoSqlFilter(SqlFilter sqlFilter);

    /**
     * 根据sqlfilter获取到数据列表 (省网办返回信息统计)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySwbReturnInfoStatisSqlFilter(SqlFilter filter);
    /**
     * 
     * 描述   获取未同步过程信息
     * @author Danto Huang
     * @created 2016-8-25 上午10:55:48
     * @return
     */
    public List<Map<String,Object>> getUnSyncGcxx(SqlFilter sqlFilter);
    /**
     * 
     * 描述   获取未同步结果信息
     * @author Danto Huang
     * @created 2016-8-25 上午10:55:48
     * @return
     */
    public List<Map<String,Object>> getUnSyncJgxx(SqlFilter sqlFilter);
    /**
     * 
     * 描述   保存办件信息指令
     * @author Danto Huang
     * @created 2016-8-25 下午2:25:05
     * @param exeId
     */
    public void saveBjxxData(String exeId);
    /**
     * 
     * 描述   保存过程信息指令
     * @author Danto Huang
     * @created 2016-8-25 下午2:25:05
     * @param exeId
     */
    public void saveGcxxData(String exeId);
    /**
     * 
     * 描述   保存结果信息指令
     * @author Danto Huang
     * @created 2016-8-25 下午2:25:05
     * @param exeId
     */
    public void saveJgxxData(String exeId);
}
