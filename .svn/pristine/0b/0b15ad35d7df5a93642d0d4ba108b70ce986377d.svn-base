/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchange.dao.BusAuditChangeDao;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busAuditChangeDao")
public class BusAuditChangeDaoImpl extends BaseDaoImpl implements BusAuditChangeDao {

    /**
     * 变更成功
     * 
     * @author John Zhang
     * @created 2015-9-2 下午03:00:00
     * @param applyId
     * @param busCode
     */
    public void changeSuccess(String applyId, String busCode) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_SuccessChange('" + applyId + "','" + busCode
                + "')");
    }

    /**
     * 变更监察审核状态
     * 
     * @author John Zhang
     * @created 2015-9-2 下午03:00:18
     * @param applyId
     * @param busCode
     * @param status
     */
    public void changeStatus(String applyId, String busCode, String status) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_ChangeStatus('" + applyId + "','" + busCode
                + "','" + status + "')");
    }
}
