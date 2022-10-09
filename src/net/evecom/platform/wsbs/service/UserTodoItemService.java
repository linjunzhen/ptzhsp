/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月2日 下午4:55:41
 */
public interface UserTodoItemService extends BaseService {
    /**
     * 
     * 描述 保存待发起流程任务的待办事项
     * @author Flex Hu
     * @created 2015年12月2日 下午5:03:31
     * @param flowVars
     * @return
     */
    public Map<String,Object> saveToStartTask(Map<String,Object> flowVars);
}
