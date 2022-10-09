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
 * 描述 常用意见管理service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年10月15日 下午4:04:48
 */

@SuppressWarnings("rawtypes")
public interface CommonOpinionService extends BaseService {

    /**
     * 描述  获取常用意见
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findCommonOpinionList(String queryParam);
    /**
     * 
     * 描述  判断要保存的常用意见是否已存在
     * @author Derek Zhang
     * @created 2015年10月16日 上午9:20:41
     * @param variables
     * @return
     */
    public boolean isExist(Map<String, Object> variables);
}
