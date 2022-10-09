/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao;

import java.util.List;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 流程挂起信息表操作dao
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FlowHangInfoDao extends BaseDao {
    /**
     * 
     * 描述 更新流程挂机结束时间
     * @author Faker Li
     * @created 2016年4月14日 上午10:41:09
     * @param taskId
     */
    public void endHangTime(String taskId,String endTime);

    /**
     * 
     * 描述 更新流程挂机说明
     * @author Water Guo
     * @param fileid 
     * @created 2016年11月11日 上午10:39:16
     * @param taskId,explain
     */
    public void hangExplain(String parentTaskId, String explain, String fileid);

}
