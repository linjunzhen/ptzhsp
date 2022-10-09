/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2019年2月25日 下午4:04:15
 */
public interface FdaStaticService extends BaseService {
    /**
     * 
     * 描述    逾期办件列表
     * @author Danto Huang
     * @created 2019年2月25日 下午4:09:15
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getAdnormalStatist(SqlFilter filter);
    /**
     * 
     * 描述    窗口人员办件量
     * @author Danto Huang
     * @created 2019年2月25日 下午4:28:48
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getIndPerDataStatist(SqlFilter filter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:29:07
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> countsDetailData(SqlFilter sqlFilter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:34:21
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> banJSDetailData(SqlFilter sqlFilter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:38:43
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> zBSDetailData(SqlFilter sqlFilter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2019年2月26日 上午9:38:58
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> tJSDetailData(SqlFilter sqlFilter);
}
