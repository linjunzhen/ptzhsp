/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service;

import net.evecom.core.service.BaseService;
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
public interface SbExecutionService extends BaseService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 10:18:00
     * @param
     * @return
     */
    List<Map<String, Object>> findSbNeedMeHandle(SqlFilter filter, boolean haveHandUp);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/3 17:48:00
     * @param
     * @return
     */
    public List<Map<String,Object>> findSbHandledByUser(SqlFilter sqlFilter,String userAccount);
}
