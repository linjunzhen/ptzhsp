/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 *
 * 描述： 医保办件
 *
 * @author Madison You
 * @created 2021年2月2日 上午10:14:56
 */
public interface YbExecutionService extends BaseService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/2 10:50:00
     * @param
     * @return
     */
    List<Map<String, Object>> findYbNeedMeHandle(SqlFilter filter, boolean haveHandUp);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 17:58:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findYbHandledByUser(SqlFilter sqlFilter,String userAccount);
}
