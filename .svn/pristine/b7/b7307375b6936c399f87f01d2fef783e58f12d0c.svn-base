/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyctb.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 一窗通办操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface CallYctbService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述： 根据关联ID获取业务ID
     * 
     * @author Rider Chen
     * @created 2019年3月12日 下午3:51:04
     * @param yctbqhId
     * @return
     */
    public Map<String, Object> getBusinessCode(String yctbqhId);
}
