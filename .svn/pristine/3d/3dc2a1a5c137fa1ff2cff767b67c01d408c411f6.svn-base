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
 * 描述 特殊建筑工程消防验收Service
 * @author Keravon Feng
 * @created 2021年11月18日 下午2:57:32
 */
public interface ProjectTsXfService extends BaseService {

    /**
     * 描述 受理是否通过
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:02:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsAcceptPass(Map<String, Object> flowVars);
    
    /**
     * 描述 确认时间
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:06:08
     * @param flowVars
     * @return
     */
    public Set<String> getIsZjTimeState(Map<String, Object> flowVars);
    
    /**
     * 描述 验收是否合格
     * @author Keravon Feng
     * @created 2021年11月23日 下午4:27:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsXfYsPass(Map<String, Object> flowVars);
    
    /**
     * 描述  生成消防验收受理和不受理通知书
     * @author Keravon Feng
     * @created 2021年11月18日 下午4:51:38
     * @param flowVars
     * @return
     */
    public Map<String, Object> getAcceptResult(Map<String, Object> flowVars);
    
    /**
     * 描述 生成消防验收合格书和不合格书
     * @author Keravon Feng
     * @created 2021年11月24日 上午9:17:45
     * @param flowVars
     * @return
     */
    public Map<String, Object> getXfYsResult(Map<String, Object> flowVars);
    
    /**
     * 描述 其他建设工程备案抽查
     * @author Keravon Feng
     * @created 2021年11月30日 上午9:53:16
     * @param flowVars
     * @return
     */
    public Map<String, Object> getQtGcjsBaResult(Map<String, Object> flowVars);
}
