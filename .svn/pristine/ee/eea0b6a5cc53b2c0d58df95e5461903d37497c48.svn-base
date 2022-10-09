/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
public interface BusAuditDao extends BaseDao {

    /**
     * 提交第一次审核过程
     * 
     * @author John Zhang
     * @created 2015-9-1 下午03:26:11
     * @param applyId
     * @param busCode
     */
    public void firstAudit(String applyId, String busCode);

    /**
     * 提交第二次审核
     * 
     * @author John Zhang
     * @created 2015-9-2 上午09:31:45
     * @param applyId
     * @param busCode
     */
    public void makeAudit(String applyId, String busCode);

    /**
     * 改变监察梳理审核状态
     * 
     * @author John Zhang
     * @created 2015-9-2 上午09:33:25
     * @param busCode
     * @param status
     */
    public void changeStatus(String busCode, String status);

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
    public String getAuditIdByItemcode(String unitCode, String busCode, String itemCode);

    /**
     * 判断新增 监察审核表
     * 
     * @author John Zhang
     * @created 2015-9-14 上午10:47:36
     * @param busCode
     */
    public void judgeAudit(String busCode);

    /**
     * 第一阶段改变状态
     * 
     * @author John Zhang
     * @created 2015-9-23 下午04:02:17
     * @param busCode
     * @param status
     */
    public void firstStatus(String busCode, String status);
}
