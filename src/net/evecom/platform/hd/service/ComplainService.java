/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述 投诉监督操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ComplainService extends BaseService {

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,String whereSql);
    
    /**
     * 
     * 描述 获取用户中心我的投诉列表
     * @author Rider Chen
     * @created 2015-11-25 下午03:15:37
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findfrontList(String page, String rows);
    /**
     * 问题列表datagrid
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findQuestionBySqlFilter(SqlFilter sqlFilter,String whereSql);
    
    /**
     * 
     * 描述 获取手机APP用户中心我的投诉列表
     * @author Rider Chen
     * @created 2016-2-17 下午02:44:19
     * @param page
     * @param rows
     * @param userName
     * @return
     */
    public Map<String,Object> findfrontAppList(String page, String rows,String userName);
}
