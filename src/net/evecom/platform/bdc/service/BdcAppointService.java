/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述 不动产权证预约
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年2月10日 上午12:13:08
 */

public interface BdcAppointService extends BaseService {

    /**
     * 描述:不动产权证预约取证后台页面数据
     *
     * @author Madison You
     * @created 2020/2/10 12:31:00
     * @param
     * @return
     */
    List<Map<String, Object>> bdcqzAppointListData(SqlFilter filter);


}
