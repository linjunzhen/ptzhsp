/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 商事登记统计分析操作dao
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface StatistCommercialDao extends BaseDao {

    /**
     * 描述单天所需要入库数量
     * 
     * @author Water Guo
     * @param type 
     * @param thisMonth 
     * @created 2016年12月20日 上午10:19:14
     * @return
     */
    public List<Map<String, Object>> getToDayNum(String type, String thisMonth);

    /**
     * 描述单天已入库数量
     * 
     * @author Water Guo
     * @created 2016年12月20日 上午10:19:14
     * @return
     */
    public List<Map<String, Object>> getToTalNum(String dateNowStr, String type);
    
    /**
     * 描述单天个体所需要入库数量
     * 
     * @author Water Guo
     * @param thisMonth 
     * @created 2016年12月20日 上午10:19:14
     * @return
     */
    public List<Map<String, Object>> getGTToDayNum(String thisMonth);

    /**
     * 描述单天个独所需要入库数量
     * 
     * @author Water Guo
     * @param thisMonth 
     * @created 2016年12月20日 上午10:19:14
     * @return
     */
    public List<Map<String, Object>> getGDToDayNum(String thisMonth);

    public List<Map<String, Object>> getThisMonth(String thisMonth);

    public List<Map<String, Object>> getThisMonthTotal(String thisMonth);

    public List<Map<String, Object>> getTBinfo();

    public List<Map<String, Object>> getGTTBinfo();

    public List<Map<String, Object>> getGDTBinfo();

    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    public List<Map<String, Object>> countsDetailData(SqlFilter sqlFilter);

    public List<Map<String, Object>> banJSDetailData(SqlFilter sqlFilter);

    public List<Map<String, Object>> zBSDetailData(SqlFilter sqlFilter);

    public List<Map<String, Object>> tJSDetailData(SqlFilter sqlFilter);


}
