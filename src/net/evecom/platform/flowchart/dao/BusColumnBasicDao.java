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
 * 描述：
 * @author Water Guo
 * @version 1.0.0
 * @date 2015-8-5
 */
public interface BusColumnBasicDao extends BaseDao{

    /**
     * 描述 获取最大的排序编码
     * @author Water Guo
     * @created 2015-8-18 下午3:44:32
     * @param processCode
     * @return
     */
    public int getMaxSn(String processCode,String platCode,String tabeName);

    /**
     * 描述 根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-8-28 上午11:36:00
     * @param busCode,applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCode(String busCode,String applyId);

    /**
     * 描述 （变更业务）根据专项编码与操作申报号获取该专项基本信息涉及的相关业务系统
     * @author Water Guo
     * @created 2015-10-9 下午5:11:59
     * @param buscode
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> getListSysByBusCodeChange(String buscode, String applyId);

}
