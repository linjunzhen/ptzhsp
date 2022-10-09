/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 定时器操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ScheduleService extends BaseService {
    /**
     * 激活状态
     */
    public static final int ACTIVE_STATUS = 0;
    /**
     * 禁用状态
     */
    public static final int FREEZEN_STATUS = 1;
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据定时器状态获取数据列表
     * @author Flex Hu
     * @created 2014年9月29日 上午10:59:16
     * @param status
     * @return
     */
    public List<Map<String,Object>> findByStatus(int status);
    /**
     * 
     * 描述 更新定时器状态
     * @author Flex Hu
     * @created 2014年9月29日 上午11:56:46
     * @param jobIds
     * @param status
     */
    public void updateStatus(String jobIds,int status);
}
