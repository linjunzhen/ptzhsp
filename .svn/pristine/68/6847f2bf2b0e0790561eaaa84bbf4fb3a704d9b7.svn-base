/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;

/**
 * 描述 请假信息操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface LeaveInfoService extends BaseService {
    /**
     * 
     * 描述 计算出分支结果接口
     * @author Flex Hu
     * @created 2015年8月19日 下午5:15:45
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResult(Map<String,Object> flowVars);
    
    /**
     * 
     * 描述 将环节审核人转换成串审
     * @author Flex Hu
     * @created 2015年9月4日 上午11:02:56
     * @param steps
     * @return
     */
    public List<FlowNextStep> setTaskOrderValues(List<FlowNextStep> steps);
}
