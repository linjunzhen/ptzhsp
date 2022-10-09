/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 描述 其他建设工程备案抽查service
 * @author Keravon Feng
 * @created 2021年11月26日 上午10:15:23
 */
public interface ProjectQtXfService extends BaseService {

    /**
     * 描述 受理是否通过
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:02:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsAcceptPass(Map<String, Object> flowVars);
    
    /**
     * 描述 是否需要检查决策事件
     * @author Keravon Feng
     * @created 2021年11月26日 上午10:21:17
     * @param flowVars
     * @return
     */
    public Set<String> getIsCheckPass(Map<String, Object> flowVars);
    
    /**
     * 描述 结论是否通过的决策事件
     * @author Keravon Feng
     * @created 2021年11月26日 上午10:30:48
     * @param flowVars
     * @return
     */
    public Set<String> getIsCheckResultPass(Map<String, Object> flowVars);
}
