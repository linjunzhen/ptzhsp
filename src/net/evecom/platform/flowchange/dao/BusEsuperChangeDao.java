/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
/**
 * 描述 监察字段变更
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
public interface BusEsuperChangeDao extends BaseDao{
    /**
     * 描述 通过过程ID获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessID(String processID);
    /**
     * 描述 通过过程编码获取过程相关信息
     * @author Toddle Chen
     * @created Aug 6, 2015 3:16:52 PM
     * @param processID
     * @return
     */
    public Map<String, Object> findMapByProcessCode(String processCode);
    /**
     * 描述 根据条件获取到监察字段列表
     * @author Toddle Chen
     * @created Aug 7, 2015 11:25:41 AM
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findListByProcessCode(String processCode);
    /**
     * 描述 获取最大排序
     * @author Toddle Chen
     * @created Aug 6, 2015 9:32:17 PM
     * @param processCode
     * @return
     */
    public int getMaxSn(String processCode);
    /**
     * 描述 根据过程编码获取最新申报号
     * @author Toddle Chen
     * @created Sep 21, 2015 3:43:20 PM
     * @param processCode
     * @return
     */
    public String getApplyId(String processCode);
    /**
     * 描述 通过过程编码获取单位编码
     * @author Toddle Chen
     * @created Sep 21, 2015 4:14:46 PM
     * @param processCode
     * @return
     */
    public String getUnitCode(String processCode);
    /**
     * 描述 通过过程编码获取单位编码
     * @author Toddle Chen
     * @created Sep 21, 2015 4:14:46 PM
     * @param processCode
     * @return
     */
    public String getUnitCode(String processCode,String applyId);
    /**
     * 描述 更新监察字段
     * @author Toddle Chen
     * @created Aug 6, 2015 8:49:27 PM
     * @param jsonDatas
     */
    public void saveEditColumn(String processCode,String jsonDatas,String applyId);
    /**
     * 描述 提交审核
     * @author Toddle Chen
     * @created Oct 13, 2015 5:02:41 PM
     * @param processCode
     * @param applyId
     */
    public void submitColumnChange(String processCode,String applyId);
    /**
     * 描述 通过过程删除监察字段
     * @author Toddle Chen
     * @created Oct 8, 2015 4:55:37 PM
     * @param selectNames
     */
    public void removeColumnByProcess(String selectNames);
    /**
     * 描述 通过业务专项编码获取所属监察字段是否完整
     * @author Toddle Chen
     * @created Sep 14, 2015 6:11:05 PM
     * @param tacheCode
     * @return
     */    
    public boolean checkColumnByBusCode(String busCode,String applyId);
    /**
     * 描述 根据条件获取到监察字段列表
     * @author Toddle Chen
     * @created Aug 7, 2015 11:25:41 AM
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findColumnByProcessCode(String processCode,String applyId);
}