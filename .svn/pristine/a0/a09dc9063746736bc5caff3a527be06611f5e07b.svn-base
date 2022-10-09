/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.jkzx.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 监控中心操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2017年3月15日 09:26:34
 */
public interface JkzxService extends BaseService {
    /**
     * 
     * 获取今年各部门办件量统计
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlBmbjltj(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 获取各部门事项入驻情况
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlDepartItem(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 获取实时办件信息监测
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlSsbjxx(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 获取窗口叫号信息
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlCkjhxx(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 获取窗口叫号量
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlCkjhl(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 各部门评价器满意度统计
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlBmpjmydtj(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 窗口评价器满意度统计
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlCkpjmydtj(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 根据sqlfilter获取到数据列表 (审批事项统计表)
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByEffiItemSqlFilter(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 描述 逾期办结事项明细
     * 
     * @author Rider Chen
     * @created 2017年4月6日 上午8:47:15
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getYqbjmxStatist(SqlFilter filter, Map<String, Object> variables);

    /**
     * 
     * 描述 办件类型统计
     * 
     * @author Rider Chen
     * @created 2017年4月6日 上午9:03:35
     * @param sqlFilter
     * @param variables
     * @return
     */
    public List<Map<String, Object>> findBySqlBjlx(SqlFilter sqlFilter, Map<String, Object> variables);
    

   /**
    * 
    * 描述
    * @author Rider Chen
    * @created 2017年5月8日 上午10:37:46
    * @param sqlFilter
    * @param variables
    * @return
    */
    public List<Map<String, Object>> findBySqlLetter(SqlFilter sqlFilter, Map<String, Object> variables);

}
