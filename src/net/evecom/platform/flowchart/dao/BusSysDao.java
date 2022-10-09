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
 * 描述 业务系统
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 3:09:01 PM
 */
public interface BusSysDao extends BaseDao {
    /**
     * 描述 根据单位ID获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitId(String UnitId);
    /**
     * 描述 根据单位编码获取系统列表
     * 
     * @author Flex Hu
     * @created 2014年10月20日 上午11:33:43
     * @param depId
     * @return
     */
    public List<Map<String, Object>> findSysByUnitCode(String unitCode);

    /**
     * 描述：（超管权限）获取系统列表
     * 
     * @author Water Guo
     * @date 2015-08-07 11:45 AM
     * @return list
     */
    public List<Map<String, Object>> findAllSystems();
}
