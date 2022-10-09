/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.dao;

import net.evecom.core.dao.BaseDao;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 *
 * 描述： 社保办件
 *
 * @author Madison You
 * @created 2021年2月2日 上午10:14:56
 */
public interface SbExecutionDao extends BaseDao {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 17:50:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findSbHandledByUser(SqlFilter sqlFilter, String userAccount);
}
