/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyqyz.dao;

import net.evecom.core.dao.BaseDao;

import java.util.Map;

/**
 * 描述:
 *
 * @author Madison You
 * @created 2019-12-26 17:30:00
 */
public interface CallYqyzDao extends BaseDao {

    /**
     * 描述:一企一证添加业务
     *
     * @author Madison You
     * @created 2019/12/27 8:57:00
     * @param
     * @return
     */
    Map<String, Object> getYqyzBusinessCode(String yqyzqhId);
}
