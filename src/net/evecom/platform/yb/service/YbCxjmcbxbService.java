/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service;

import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;


/**
 * 描述  城乡居民登记业务操作service
 * @author Allin Lin
 * @created 2019年10月29日 下午2:40:07
 */
public interface YbCxjmcbxbService extends  BaseService{
    
    /**
     * 描述    城乡居民登记业务-决策事件
     * @author Allin Lin
     * @created 2019年10月29日 下午2:56:35
     * @param flowVars
     * @return
     */
    public Set<String> getTypeResult(Map<String, Object> flowVars);
    

}
