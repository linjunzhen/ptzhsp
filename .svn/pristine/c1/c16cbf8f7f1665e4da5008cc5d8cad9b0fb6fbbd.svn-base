/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 对接日志操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface DataAbutLogService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 新增或者保存日志
     * @author Flex Hu
     * @created 2015年10月13日 下午3:10:46
     * @param dataAbutLog
     */
    public void saveOrUpdateLog(Map<String,Object> dataAbutLog);
}
