/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.dao;

import net.evecom.core.dao.BaseDao;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年7月3日 下午3:56:27
 */
public interface NewCallDao extends BaseDao {
    
    /**
     * 
     * 描述    获取最大取号顺序
     * @author Danto Huang
     * @created 2018年7月5日 下午3:53:09
     * @param businessCode
     * @return
     */
    public int getMaxTakeSn(String businessCode);

    /**
     * 
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param businessCode
     * @return
     */
    public int getWaitingNumByBusinessCode(String businessCode);
    /**
     *
     * 描述    根据业务编号获取等待人数
     * @author Danto Huang
     * @created 2018年7月5日 下午4:06:42
     * @param roomNo
     * @return
     */
    public int getWaitingNumByRoomNo(String roomNo);
}
