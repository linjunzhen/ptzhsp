/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.flowchart.dao.BusAuditDao;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 5:53:30 PM
 */
@Repository("busAuditDao")
public class BusAuditDaoImpl extends BaseDaoImpl implements BusAuditDao {

    /**
     * 提交第一次审核 调用存储过程
     * 
     * @author John Zhang
     * @created 2015-9-1 下午03:26:11
     * @param applyId
     * @param busCode
     */
    public void firstAudit(String applyId, String busCode) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_FirstAudit('" + applyId + "','" + busCode
                + "')");
    }

    /**
     * 提交第二次审核
     * 
     * @author John Zhang
     * @created 2015-9-2 上午09:31:45
     * @param applyId
     * @param busCode
     */
    public void makeAudit(String applyId, String busCode) {
        this.jdbcTemplate
                .execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_MadeAudit('" + applyId + "','" + busCode + "')");
    }

    /**
     * 改变监察梳理审核状态
     * 
     * @author John Zhang
     * @created 2015-9-2 上午09:33:25
     * @param busCode
     * @param status
     */
    public void changeStatus(String busCode, String status) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_AuditStatus('" + busCode + "','" + status
                + "')");
    }

    /**
     * 第一阶段改变状态
     * 
     * @author John Zhang
     * @created 2015-9-23 下午04:02:17
     * @param busCode
     * @param status
     */
    public void firstStatus(String busCode, String status) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_FirstStatus('" + busCode + "','" + status
                + "')");
    }

    /**
     * 描述 通过单位编码.业务专项.审核项目编码获取该条记录主键
     * 
     * @author Toddle Chen
     * @created Sep 14, 2015 11:16:15 AM
     * @param unitCode
     * @param busCode
     * @param itemCode
     * @return
     */
    public String getAuditIdByItemcode(String unitCode, String busCode, String itemCode) {
        StringBuffer sql = new StringBuffer("select * from T_LCJC_BUS_AUDIT T ");
        sql.append(" WHERE T.UNIT_CODE=? AND T.BUS_CODE=? AND T.CONFIG_CODE=? ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), new Object[] { unitCode, busCode, itemCode },
                null);
        if (list.size() > 0) {
            Map<String, Object> auditMap = list.get(0);
            return String.valueOf(auditMap.get("AUDIT_ID"));
        } else {
            return null;
        }
    }

    /**
     * 判断新增 监察审核表
     * 
     * @author John Zhang
     * @created 2015-9-14 上午10:47:36
     * @param busCode
     */
    public void judgeAudit(String busCode) {
        this.jdbcTemplate.execute("call PCK_ESTRANSFER_LCJCJOB.P_Esuper_JudgeAudit('" + busCode + "')");
    }
}
