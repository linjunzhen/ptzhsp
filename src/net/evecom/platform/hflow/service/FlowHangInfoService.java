/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程挂起信息表操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowHangInfoService extends BaseService {
    /**
     * 
     * 描述 更新流程挂机结束时间
     * @author Faker Li
     * @created 2016年4月14日 上午10:39:16
     * @param selectTaskIds
     */
    public void endHangTime(String selectTaskIds);
    /**
     * 
     * 描述 更新流程挂机说明
     * @author Water Guo
     * @param fileid 
     * @created 2016年11月11日 上午10:39:16
     * @param selectTaskIds,explain
     */
    public void hangExplain(String selectTaskIds, String explain, String fileid);
    /**
     * 
     * 描述 根据流程ID获取挂起天数
     * @author Faker Li
     * @created 2016年4月14日 下午1:57:36
     * @param taskId
     * @return
     */
    public int gethangAllTimeByTaskId(String taskId);
    
}
