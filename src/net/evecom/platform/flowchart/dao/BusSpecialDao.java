/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
/**
 * 描述 业务专项
 * @author Toddle Chen
 * @created Jul 29, 2015 5:52:35 PM
 */
public interface BusSpecialDao extends BaseDao{
    /**
     * 描述 根据业务编码查询业务环节列表
     * @author Toddle Chen
     * @created Aug 5, 2015 11:07:34 AM
     * @param busCode
     * @return
     */
    public List<Map<String, Object>> findTacheByBuscode(String busCode);
    /**
     * 描述 根据sqlfilter获取到业务专项列表
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:28 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByUnitCode(String unitCode);
    /**
     * 描述 根据业务编码查询业务对象
     * @author Toddle Chen
     * @created Aug 8, 2015 10:07:54 PM
     * @param busCode
     * @return
     */
    public Map<String, Object> getBusByBuscode(String busCode);
}
