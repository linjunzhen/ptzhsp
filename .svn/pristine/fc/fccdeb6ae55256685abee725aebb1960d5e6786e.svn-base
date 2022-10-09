/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.mobile.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * 
 * @author Panda Chen
 * @created 2016-12-13 下午04:11:22
 */
public interface PalmCommercialService extends BaseService {
    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:18:04
     * @param ctid
     * @return
     */
    public List<Map<String, Object>> findContentInfoForApp(String ctid);

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:18:01
     * @param page
     * @param rows
     * @param moduleId
     * @return
     */
    public Map<String, Object> findContentListForApp(String page, String rows, String moduleId, String title);

    /**
     * 
     * 描述根据sql获取列表
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:56:35
     * @param sql
     * @param queryParams
     * @param page
     * @param rows
     * @return
     */
    public Map<String, Object> getListBySql(String sql, Object[] queryParams, String page, String rows);

    /**
     * 
     * 描述根据sql获取列表
     * 
     * @author Panda Chen
     * @created 2016-12-19 下午04:43:25
     * @param sql
     * @param queryParams
     * @return
     */
    public List<Map<String, Object>> getListBySql(String sql, Object[] queryParams);

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2017-1-5 下午02:58:26
     * @param page
     * @param rows
     * @param title
     * @return
     */
    public Map<String, Object> getNegativeList(String page, String rows, String title);

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2017-3-12 上午11:00:23
     * @param page
     * @param rows
     * @param title
     * @return
     */
    public Map<String, Object> getTzIndustryList(String page, String rows, String title);
}
