/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.smoga.dao;

import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月1日 上午10:05:05
 */
public interface BillOfRightDao extends BaseDao {
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月2日 下午6:11:18
     * @return
     */
    public int getMaxSn();
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月2日 下午6:11:18
     * @return
     */
    public int getCurVersion();
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月3日 上午10:39:27
     * @param variables
     * @return
     */
    public String saveOrUpdateRecord(Map<String, Object> variables);
}
