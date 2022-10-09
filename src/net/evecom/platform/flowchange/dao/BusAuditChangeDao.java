/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
public interface BusAuditChangeDao extends BaseDao {

    /**
     * 变更成功
     * 
     * @author John Zhang
     * @created 2015-9-2 下午03:00:00
     * @param applyId
     * @param busCode
     */
    public void changeSuccess(String applyId, String busCode);

    /**
     * 变更监察审核状态
     * 
     * @author John Zhang
     * @created 2015-9-2 下午03:00:18
     * @param applyId
     * @param busCode
     * @param status
     */
    public void changeStatus(String applyId, String busCode, String status);
}
