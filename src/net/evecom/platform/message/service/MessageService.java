/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.message.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 消息提醒操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface MessageService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);
    
    /**
     * 获取到手机APP文章数据
     * 
     * @param sqlFilter
     * @return
     */
    public Map<String, Object> findMessageAppBySql(String page, String rows,String esn);
    
    /**
     * 
     * 描述
     * @author Rider Chen
     * @created 2016-4-15 下午03:55:02
     * @param esn
     * @return
     */
    public Map<String, Object> getCount(String esn);
}
