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
 * 描述 社会投资二阶段决策事件
 * @author Danto Huang
 * @created 2015-11-11 上午10:31:25
 */
public interface ShtzSecondService extends BaseService {

    /**
     * 
     * 描述 是否需要协调
     * @author Danto Huang
     * @created 2015-11-11 上午10:34:06
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResult(Map<String,Object> flowVars);

    /**
     * 
     * 描述 是否需要协调
     * @author Danto Huang
     * @created 2015-11-11 上午10:34:06
     * @param flowVars
     * @return
     */
    public Set<String> getCoodResultNew(Map<String,Object> flowVars);

    /**
     * 描述:是否需要修编
     *
     * @author Madison You
     * @created 2019/8/17 17:23:00
     * @param
     * @return
     */
    public Set<String> getCoodResultX(Map<String, Object> flowVars);
    /**
     * 
     * 描述 是否协调一致的结果
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsCoodSame(Map<String,Object> flowVars);
    /**
     * 
     * 描述 签发是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsSendPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 公示是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsPublicityPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 公示是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getIsPublicityPass1(Map<String,Object> flowVars);
    /**
     * 
     * 描述 财务决算意见是否通过
     * @author Danto Huang
     * @created 2015-11-11 上午10:35:00
     * @param flowVars
     * @return
     */
    public Set<String> getCwjsPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 处室领导审核是否通过
     * @author Danto Huang
     * @created 2015-11-25 下午2:55:56
     * @param flowVars
     * @return
     */
    public Set<String> getCsshPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 分管领导审批是否通过
     * @author Danto Huang
     * @created 2015-11-25 下午2:55:56
     * @param flowVars
     * @return
     */
    public Set<String> getFgspPass(Map<String,Object> flowVars);
    
    /**
     * 
     * 描述 设置状态判断
     * @author Faker Li
     * @created 2015年12月14日 下午3:58:50
     * @param flowVars
     * @return
     */
    public Map<String,Object> setStatue(Map<String,Object> flowVars);
    /**
     * 政府投资第四阶段财务决算意见是否通过
     * 描述
     * @author Faker Li
     * @created 2015年12月15日 上午10:02:50
     * @param flowVars
     * @return
     */
    public Set<String> getCwjsyjPass(Map<String,Object> flowVars);
    /**
     * 
     * 描述 综合（专家）评审意见前置事件
     * @author Danto Huang
     * @created 2015-12-14 下午3:04:29
     * @param flowVars
     * @return
     */
    public Map<String, Object> lcjdZhpsSet(Map<String,Object> flowVars);
    /**
     * 
     * 描述 汇总意见前置事件
     * @author Danto Huang
     * @created 2015-12-29 下午5:22:07
     * @param flowVars
     * @return
     */
    public Map<String, Object> lcHzyjSet(Map<String,Object> flowVars);

    /**
     * 
     * 描述
     * @author Derek Zhang
     * @created 2016年3月22日 上午10:03:06
     * @param flowVars
     * @return
     */
    public Map<String, Object> updateFlowStage(Map<String, Object> flowVars);
}
