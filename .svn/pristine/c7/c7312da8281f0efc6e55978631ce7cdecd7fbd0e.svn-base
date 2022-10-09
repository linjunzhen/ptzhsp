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
 * 描述 社会投资第四阶段服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月11日 上午10:42:40
 */
@SuppressWarnings("rawtypes")
public interface ShtzFourthService extends BaseService {

    /**
     * 描述 是否需要协调
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsNeedCoordination(Map<String, Object> flowVars);

    /**
     * 描述 协调结果--协调是否一致
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getCoordinationResult(Map<String, Object> flowVars);

    /**
     * 描述 领导签发结果是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getLeaderIssueResult(Map<String, Object> flowVars);

    /**
     * 描述 公示是否通过
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Set<String> getPublicityResult(Map<String, Object> flowVars);

    /**
     * 描述 保存批复意见
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveNPFYJ(Map<String, Object> flowVars);

    /**
     * 描述 保存验收申请时间
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveJGYSSQSJ(Map<String, Object> flowVars);

    /**
     * 描述 保存验收通过时间
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveJGYSJSSJ(Map<String, Object> flowVars);

    /**
     * 描述 保存规划局审查意见
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    public Map<String, Object> saveGHJSCYJ(Map<String, Object> flowVars);
}
