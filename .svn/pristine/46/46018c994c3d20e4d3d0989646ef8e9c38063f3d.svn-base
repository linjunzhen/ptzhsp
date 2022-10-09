/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyqyz.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author Madison You
 * @created 2019-12-26 17:30:00
 */
public interface CallYqyzService extends BaseService {

    /**
     * 描述:一企一证配置数据
     *
     * @author Madison You
     * @created 2019/12/26 19:13:00
     * @param
     * @return
     */
    List<Map<String, Object>> findBySqlFilter(SqlFilter filter);

    /**
     * 描述:一企一证添加业务
     *
     * @author Madison You
     * @created 2019/12/27 8:56:00
     * @param
     * @return
     */
    Map<String, Object> getBusinessCode(String yctbqhId);

    /**
     * 描述:查找业主中是否包含营业执照和许可证件业务
     *
     * @author Madison You
     * @created 2019/12/30 16:33:00
     * @param
     * @return
     */
    boolean findBusinessCatalog(SqlFilter filter);

}
