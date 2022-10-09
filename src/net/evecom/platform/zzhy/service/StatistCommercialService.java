/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 商事登记统计分析操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface StatistCommercialService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 
     * 描述 统计商事新增企业数量
     * 
     * @author Water Guo
     * @created 2016年12月20日 上午10:19:14
     * @param params
     */
    public void statistNewEnterpriseNum(Log log);

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> getTotalInvestmentBySqlFilter(SqlFilter filter, Map<String, Object> variables);
    
    /**
     * 
     * 描述 统计管理层报表
     * 
     * @author Water Guo
     * @created 2016年12月20日 上午10:19:14
     * @param params
     */
    public List<Map<String, Object>> getIndPerDataStatist(SqlFilter filter);

    /**
     * 
     * 描述 异常办件统计
     * 
     * @author Water Guo
     * @created 2016年12月26日 上午10:19:14
     * @param params
     */
    public List<Map<String, Object>> getAdnormalStatist(SqlFilter filter);

    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);

    /**
     * 企业办件信息
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findInfoBySqlFilter(SqlFilter filter);
    /**
     * 企业办件信息导出
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findInfoBySqlFilterForExp(SqlFilter sqlFilter) ;


    public List<Map<String, Object>> countsDetailData(SqlFilter filter);

    public List<Map<String, Object>> banJSDetailData(SqlFilter filter);

    public List<Map<String, Object>> zBSDetailData(SqlFilter filter);

    public List<Map<String, Object>> tJSDetailData(SqlFilter filter);
    
    
    /**
     * 
     * 描述 获取商事注册官办件量统计（比如一个预审，退回后，公众用户再提交预审过，就算是一家企业，也算两个办件量）
     * @author Rider Chen
     * @created 2017年5月3日 下午2:54:01
     * @param filter
     * @param variables
     * @return
     */
    public List<Map<String, Object>> getUserTaskBySqlFilter(SqlFilter filter);
}
