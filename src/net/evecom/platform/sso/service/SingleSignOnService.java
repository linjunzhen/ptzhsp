/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sso.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 单点登录操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SingleSignOnService extends BaseService {
    /**
     * 
     * 描述 查询列表
     * @author Rider Chen
     * @created 2021年6月7日 下午4:17:30
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);
    /**
     * 
     * 描述 单点登录保存日志
     * 
     * @author Rider Chen
     * @created 2021年6月10日 上午11:26:34
     * @param info
     * @param param
     * @param result
     * @param logContent
     */
    public void saveLog(Map<String, Object> info, Map<String, Object> param, Map<String, Object> result,
            String logContent,int type);
}
