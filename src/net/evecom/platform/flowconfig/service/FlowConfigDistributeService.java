/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowconfig.service;

import java.util.Map;
import net.evecom.core.service.BaseService;

/**
 * 描述 工程建设项目流程分发管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
@SuppressWarnings("rawtypes")
public interface FlowConfigDistributeService extends BaseService {
    /**
     * 
     * 描述：流程分发启动
     * @author Rider Chen
     * @created 2020年9月9日 上午9:51:53
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterDistributeStart(Map<String, Object> flowDatas);
    
    /**
     * 
     * 描述：流程分发预审
     * @author Rider Chen
     * @created 2020年9月10日 上午9:32:33
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterDistributePreAudit(Map<String, Object> flowDatas);
}
