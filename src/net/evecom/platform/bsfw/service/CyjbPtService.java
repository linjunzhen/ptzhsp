/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 产业奖补相关的service
 * @author Keravon Feng
 * @created 2018年9月14日 下午5:42:12
 */
public interface CyjbPtService extends BaseService {

    /**
     * 描述 获取用户列表
     * @author Keravon Feng
     * @created 2018年9月14日 下午5:53:58
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);
    
    /**
     * 
     * 描述 产业奖补后置事件
     * @author Keravon Feng
     * @created 2018年9月19日 上午9:00:14
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveAfterBusData(Map<String, Object> flowDatas);

}
