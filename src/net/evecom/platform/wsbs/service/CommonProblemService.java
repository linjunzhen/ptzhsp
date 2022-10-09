/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 常见问题操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface CommonProblemService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 保存或者更新数据库表,级联更新中间表
     * @author Flex Hu
     * @created 2015年9月18日 下午5:10:05
     * @param commomProblem
     * @return
     */
    public String saveOrUpdateCascadeMiddle(Map<String, Object> commomProblem);
   /**
    * 
    * 描述 常见问题分页
    * @author Rider Chen
    * @created 2015-12-4 下午02:56:49
    * @param page
    * @param rows
    * @return
    */
    public Map<String, Object> findfrontList(String page, String rows, String itemId);
    /**
     * 
     * 描述  所有发布事项的常见问题
     * @author Faker Li
     * @created 2015年12月10日 下午2:55:21
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> findCjwtList(String page, String rows,String problemTitle,String busTypeIds);
    /**
     * 
     * 描述 所有发布的表格下载列表
     * @author Faker Li
     * @created 2015年12月17日 上午9:54:58
     * @return
     */
    public List<Map<String, Object>> findAllCjwtList();
    /**
     * 根据sqlfilter获取到数据列表(历史记录)
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findHisBySqlFilter(SqlFilter sqlFilter);
}
