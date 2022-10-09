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
 * 描述 
 * @author Sundy Sun
 * @version v1.0
 *
 */
public interface ConsultService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,String whereSql);
    /**
     * 根据sqlfilter获取到咨询事项数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByItemSqlFilter(SqlFilter sqlFilter,String whereSql);
    /**
     * 获取到咨询事项数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByItemSql(String whereSql);
    /**
     * 获取到我的咨询数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findZXBySql(String whereSql);
    /**
     * 
     * 描述 获取用户中心我的咨询列表
     * @author Rider Chen
     * @created 2015-11-25 下午03:15:37
     * @param page
     * @param rows
     * @return
     */
    public Map<String,Object> findfrontList(String page, String rows);
    /**
     * 
     * 描述 获取手机APP用户中心我的咨询列表
     * @author Rider Chen
     * @created 2016-2-17 下午02:44:19
     * @param page
     * @param rows
     * @param userName
     * @return
     */
    public Map<String,Object> findfrontAppList(String page, String rows,String userName);
    /**
     * 
     * 描述 获取办事指南咨询列表
     * @author Rider Chen
     * @created 2015-12-8 下午04:12:38
     * @param page
     * @param rows
     * @param itemId
     * @return
     */
    public Map<String,Object> findfrontList(String page, String rows, String itemId);
    /**
     * 
     * 描述 利用jdbc保存或者更新业务表记录,仅仅试用于非复合主键表
     * @author Flex Hu
     * @created 2014年9月6日 上午8:46:20
     * @param colValues:列值
     * @param tableName:表名称
     * @param entityId:实体ID
     * @return
     */
    public String saveOrUpdateConsult(Map<String,Object> colValues,String tableName,String entityId);
    /**
     * 
     * 描述 获取前台绿色通道的办事咨询列表
     * @author Faker Li
     * @created 2015年12月21日 下午3:25:34
     * @param page
     * @param rows
     * @param busTypeIds
     * @return
     */
    public Map<String, Object> findbsznLstdList(String page, String rows, String busTypeIds);
}
