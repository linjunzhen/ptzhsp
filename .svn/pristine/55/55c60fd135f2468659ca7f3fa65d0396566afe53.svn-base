/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zhsp.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2015-11-23 上午9:53:14
 */
public interface ServiceHandleService extends BaseService {
    /**
     * 获取待我审批列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findNeedMeHandle(SqlFilter sqlFilter);
    /**
     * 
     * 描述  获取被某个用户办理的流程
     * @author Danto Huang
     * @created 2015-11-23 下午2:58:14
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findHandledByUser(SqlFilter sqlFilter,String userAccount);
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 描述 流程历史数据查询
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午9:20:33
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHisBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 描述 已归档流程查询
     * 
     * @author Reuben Bao
     * @created 2019年4月14日 下午9:52:20
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findHisFiledFlowBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 描述 根据办理结束时间查询流程实例表
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午9:29:57
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> getExecutionListByEndTime(String startDate, String endDate);
    
    /**
     * 描述 根据字典配置的归档周期查询流程实例表数据总数
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午10:46:22
     * @return
     */
    public int getExecutionCountByDicCode();
    
    /**
     * 描述 根据字典配置的归档周期查询流程实例表数据
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午10:46:22
     * @return
     */
    public List<Map<String, Object>> getExecutionListByDicCode();
    
    /**
     * 描述 删除已归档的流程实例
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:11:02
     */
    public void deleteExecutionByIsFiled();
    
    /**
     * 描述 删除已归档的流程实例
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:11:02
     */
    public void deleteTaskByIsFiled();
    
    /**
     * 描述 删除已归档的流程实例
     * 
     * @author Reuben Bao
     * @created 2019年4月15日 上午11:11:02
     */
    public void deleteResultByIsFiled();
}
