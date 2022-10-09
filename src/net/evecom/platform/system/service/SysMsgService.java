/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;
import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;


/**
 * 描述：短信
 * @author Water Guo
 * @created 2017-3-16 下午1:49:08
 */
public interface SysMsgService extends BaseService{
    List<Map<String, Object>> findBySqlFilter(SqlFilter filter);
}
