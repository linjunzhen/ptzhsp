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
 * 描述 编号维护操作service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年9月22日 下午6:02:44
 */

@SuppressWarnings("rawtypes")
public interface SerialNumberService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 加载编号配置数据
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findSerialNumbers(SqlFilter filter);

    /**
     * 
     * 描述 生成最新编号
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param busName
     *            busType otherParam
     * @return
     */
    public String generateSerialNumber(String busName, String busType, Map<String, String> otherParam);
    /**
     * 
     * 描述 根据serialid,busname,bustype判断是否存在记录
     * @author Faker Li
     * @created 2015年11月3日 上午10:21:17
     * @param serialid
     * @param busname
     * @param bustype
     * @return
     */
    public boolean isExistBySerialidAndBusnameAndBustype(String serialid,String busname,String bustype);
    /**
     * 
     * 描述 插入T_WSBS_SERIALNUMBER_RES表
     * @author Faker Li
     * @created 2015年11月3日 上午10:48:32
     * @param serialNumber
     */
    public void insertserialRes(Map<String, Object> serialNumber);
}
