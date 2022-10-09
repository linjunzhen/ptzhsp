/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 不动产登记操作dao
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface BdcExecutionDao extends BaseDao {
    /**
     * 
     * 描述  获取不动产被某个用户办理的流程
     * @author Rider Chen
     * @created 2017年3月4日 下午5:47:11
     * @param sqlFilter
     * @param userAccount
     * @return
     */
    public List<Map<String,Object>> findBdcHandledByUser(SqlFilter sqlFilter,String userAccount);
    
    /**
     * 
     * 描述：
     * @author Rider Chen
     * @created 2019年2月22日 上午11:35:21
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);
    
    
    
    /**
     * 
     * 描述： 办件总数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> countsDetailData(SqlFilter filter);
    /**
     * 
     * 描述： 办结总数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> banJSDetailData(SqlFilter filter);
    /**
     * 
     * 描述：在办数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> zBSDetailData(SqlFilter filter);
    /**
     * 
     * 描述： 退件数
     * @author Rider Chen
     * @created 2019年2月22日 上午11:34:28
     * @param filter
     * @return
     */
    public List<Map<String, Object>> tJSDetailData(SqlFilter filter);
}
