/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.dao;


import net.evecom.core.dao.BaseDao;

/**
 * 
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 下午3:16:01
 */
public interface ExtraDictionaryDao extends BaseDao {
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:28:12
     * @param typeId
     * @param dicCode
     * @return
     */
    public boolean isExist(String typeId, String dicCode);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午3:30:36
     * @param typeId
     * @return
     */
    public int getMaxSn(String typeId);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年3月30日 下午4:29:13
     * @param dicIds
     */
    public void updateSn(String[] dicIds);
}
