/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月22日 下午3:40:44
 */
public interface BusApplyService extends BaseService {
    /**
     * 交通与建设局运管处是否审核通过
     * 描述
     * @author Rider Chen
     * @created 2016年1月21日 下午4:01:53
     * @param flowVars
     * @return
     */
    public Set<String> getAuditPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 流程提交后置时间
     * @author Danto Huang
     * @created 2015-12-14 下午3:04:29
     * @param flowVars
     * @return
     */
    public Map<String, Object> start(Map<String,Object> flowVars);
}
