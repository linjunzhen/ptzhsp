/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

/**
 * 
 * 描述 编号管理操作dao
 * 
 * @author Derek Zhang
 * @created 2015年10月7日 下午2:45:38
 */

@SuppressWarnings("rawtypes")
public interface SerialNumberDao extends BaseDao {

    /**
     * 描述获取编号配置数据
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:03:11
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findSerialNumbers(SqlFilter filter);

    /**
     * 
     * 描述 获取业务对应的编号配置
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:34
     * @param busName
     * @param busType
     * @param otherParam
     * @return
     */
    public Map<String, Object> getSerialNumberByBus(String busName, String busType, Map<String, String> otherParam);

    /**
     * 
     * 描述 获取编号参数过滤信息
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:26
     * @return
     */
    public List<Map<String, Object>> getSerialParamDicList();

    /**
     * 
     * 描述 获取编号序号配置信息
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:26
     * @return
     */
    public List<Map<String, Object>> getSequenceTypeDicList(String sequenceType);
    /**
     * 
     * 描述 获取省网办接口对接配置信息
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:26
     * @return
     */
    public Map<String, Object>  findSwbjkConfig(String dictName);
    /**
     * 
     * 描述 根据serialid,busname,bustype判断是否存在记录
     * @author Faker Li
     * @created 2015年11月3日 上午10:24:33
     * @param serialid
     * @param busname
     * @param bustype
     * @return
     */
    public boolean isExistBySerialidAndBusnameAndBustype(String serialid, String busname, String bustype);
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年11月3日 上午10:49:34
     * @param serialNumber
     */
    public void insertserialRes(Map<String, Object> serialNumber);
  
}
