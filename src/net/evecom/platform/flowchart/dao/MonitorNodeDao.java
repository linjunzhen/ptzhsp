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
 * 描述 监察点dao
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface MonitorNodeDao extends BaseDao{
    /**
     * update factor
     * 
     * @param tachecode
     * @param busCode yewu
     */
    public void factorSubmit(String busCode,String userId);
    
    /**
     * change factor
     * 
     * @param tachecode
     * @param busCode yewu
     */
    public void changeFactorSubmit(String busCode,String userId);
    /**
     * 根据过程编号查询要素
     * @param processCode
     * @return
     */
    public List<Map<String, Object>> findByProcessCode(String processCode);
}
