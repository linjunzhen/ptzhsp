/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.cms.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 文章审核操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface AuditContentService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 根据sqlfilter获取到VIEW数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findViewBySqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 根据文章ID更改审核状态
     * 
     * @author Rider Chen
     * @created 2016年5月25日 上午11:55:13
     */
    public void updateAuditStatus(String contentId, String auditOpinion, int auditStatus);
}
