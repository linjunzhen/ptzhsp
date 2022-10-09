/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 我要纠错操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
@SuppressWarnings("rawtypes")
public interface WyjcService extends BaseService {
    /**
     * 
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, Map<String, Object> variables);

    /**
     * 描述:查询疑点难点问题列表控制器业务层
     *
     * @author Madison You
     * @created 2019/8/5 8:57:00
     * @param
     * @return
     */
    List<Map<String, Object>> findQuestionList(SqlFilter sqlFilter);
}
