/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年6月22日 上午11:34:17
 */
public interface GlobalUrlDao extends BaseDao {
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 下午3:36:29
     * @return
     */
    public List<String> findByFilterType(String filterType);
}
