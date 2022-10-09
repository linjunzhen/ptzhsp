/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述：许可service
 * 
 * @author Water Guo
 * @created 2017-4-12 上午9:10:38
 */
@SuppressWarnings("rawtypes")
public interface CommonXKService extends BaseService {
    /**
     * 
     * 描述：判断许可是否存在
     * 
     * @author Water Guo
     * @created 2017-4-12 上午9:18:25
     * @param variables
     * @return
     */
    public String getXKID(Map<String, Object> variables);

    /**
     * 
     * 描述：获得许可List
     * 
     * @author Water Guo
     * @created 2017-4-12 下午5:03:51
     * @param queryParam
     * @return
     */
    public List<Map<String, Object>> findXKList(String queryParam);

    /**
     * 
     * 描述：获得许可信息
     * 
     * @author Water Guo
     * @created 2017-4-23 上午9:41:51
     * @param xkId
     * @return
     */
    public Map<String, Object> getInfoById(String xkId);
}
