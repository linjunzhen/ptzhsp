/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述SysLogDao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:22:18
 */
public interface SysLogDao extends BaseDao {
    /**
     * 
     * 描述：
     * @author Water Guo
     * @created 2017-11-13 下午8:47:24
     * @param uploadUserId
     * @return
     */
    List<Map<String, Object>> getListByJdbc(String uploadUserId);
    /**
     * 
     * 描述：联合查询获取目的表变更表信息
     * @author Water Guo
     * @created 2017-11-10 下午4:29:41
     * @param indexColName 索引列
     * @param busTableName 联合查询表
     * @param colValues 查询的列值
     */
    public List<Map<String,Object>> getBusTableLogs(String indexColName,String busTableName
        ,String[] colNames,Object[] colValues );
    /**
     * 
     * 描述：查询表更新信息
     * @author Water Guo
     * @created 2017-11-15 下午5:16:34
     * @param indexColName
     * @param busTableName
     * @return
     */
    public List<Map<String,Object>> getChBusTableLogs(String indexColVal,String busTableName);
}
