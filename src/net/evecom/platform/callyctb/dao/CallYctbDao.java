/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyctb.dao;

import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 一窗通办操作dao
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2019年3月25日09:37:27
 */
@SuppressWarnings("rawtypes")
public interface CallYctbDao extends BaseDao {
    /**
     * 
     * 描述： 根据关联ID获取业务ID
     * 
     * @author Rider Chen
     * @created 2019年3月12日 下午3:51:04
     * @param yctbqhId
     * @return
     */
    public Map<String, Object> getBusinessCode(String yctbqhId);
}
