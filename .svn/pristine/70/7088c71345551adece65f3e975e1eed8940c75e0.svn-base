/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-14 上午9:28:37
 */
public interface DepartCatalogService extends BaseService {

    /**
     * 
     * 描述   获取事项目录列表
     * @author Danto Huang
     * @created 2016-9-14 上午9:31:37
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述:获取标准化事项目录列表
     *
     * @author Madison You
     * @created 2021/2/18 9:40:00
     * @param
     * @return
     */
    public List<Map<String, Object>> findStdBySqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述   删除目录
     * @author Danto Huang
     * @created 2016-9-20 下午3:40:43
     * @param selectColNames
     */
    public boolean removeCascade(String selectColNames);
    /**
     * 
     * 描述    目录划转
     * @author Danto Huang
     * @created 2018年8月20日 下午4:13:16
     * @param departId
     * @param catalogIds
     */
    public void moveCatalog(String departId,String catalogIds);
    /**
     * 
     * 描述    目录变更，关联修改事项性质
     * @author Danto Huang
     * @created 2018年10月19日 上午9:38:49
     * @param catalogCode
     * @param sxxz
     */
    public void updateSxxzbyCatalog(String catalogCode ,String sxxz);

    /**
     * 描述:查看目录事项
     *
     * @author Madison You
     * @created 2020/4/26 11:41:00
     * @param
     * @return
     */
    List<Map<String, Object>> getServiceItemByCatalog(String entityId);

    /**
     * 描述:查看标准化目录事项
     *
     * @author Madison You
     * @created 2021/2/22 9:34:00
     * @param
     * @return
     */
    List<Map<String, Object>> getStdServiceItemByCatalog(String entityId);
}
