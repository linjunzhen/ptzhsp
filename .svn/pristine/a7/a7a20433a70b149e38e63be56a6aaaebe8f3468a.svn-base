/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
public interface MsgTemplateService extends BaseService{

    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Water Guo
     * @created 2015年1月23日 上午9:27:00
     * @param sqlFilter
     * @return
     */
     public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
     /**
      * 
      * 描述   删除
      * @param selectColNames
      */
     public void removeCascade(String selectColNames);
}
