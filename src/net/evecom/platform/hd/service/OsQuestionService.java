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
 * 描述 调查问题操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface OsQuestionService extends BaseService {

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter,String whereSql);
    
    
    /**
     * 
     * 描述
     * @author Rider Chen
     * @created 2015-11-30 下午04:09:47
     * @param Ids
     * @param table
     * @param columnName
     */
    public void updateSn(String[] ids,String table,String columnName,String keyId);
}
