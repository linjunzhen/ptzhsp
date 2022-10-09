/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年10月23日 下午3:54:39
 */
public interface EstateWebService {

    /**
     * 
     * 描述 流程启动
     * @author Danto Huang
     * @created 2017年10月23日 下午4:03:57
     * @param flowInfo
     * @return
     */
    public String flowStart(String flowInfoJson);
    /**
     * 
     * 描述 流程环节执行
     * @author Danto Huang
     * @created 2017年10月25日 上午8:48:36
     * @param flowInfo
     * @return
     */
    public String flowExecute(String flowInfoJson);
}
