/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchange.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 审核核对
 * 
 * @author Toddle Chen
 * @created Jul 29, 2015 4:47:46 PM
 */
public interface BusAuditChangeService extends BaseService {

    /**
     * 描述 根据sqlfilter获取到审核核对状态列表
     * 
     * @author Toddle Chen
     * @created Jul 30, 2015 5:16:36 PM
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 修改变更审核状态
     * 
     * @author John Zhang
     * @created 2015-9-18 下午04:09:16
     * @param applyId
     * @param busCode
     * @param status
     */
    public void changeStatus(String applyId, String busCode, String status);

    /**
     * 变更审核后置事件
     * 
     * @author John Zhang
     * @created 2015-9-18 下午04:28:47
     * @param flowVars
     * @return
     */
    public Map<String, Object> afterAudit(Map<String, Object> flowVars);

    /**
     * 描述 根据操作申报号获取变更业务的材料类别
     * 
     * @author Water Guo
     * @created 2015-9-24 下午2:47:05
     * @param applyId
     * @return
     */
    public List<Map<String, Object>> listByApplyId(String applyId);
    
    /**
     * 编办审核分支判断
     * @author John Zhang
     * @created 2015年11月25日 上午9:11:58
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForBb(Map<String,Object> flowVars);
    
    /**
     * 监察厅审核分支判断
     * @author John Zhang
     * @created 2015年11月25日 上午9:11:58
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForJct(Map<String,Object> flowVars);
    
    /**
     * 省经信中心审核分支判断
     * @author John Zhang
     * @created 2015年11月25日 上午9:11:58
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForJx(Map<String,Object> flowVars);
    
    /**
     * 
     * 开始节点分支判断
     * @author John Zhang
     * @created 2015年11月25日 下午3:11:26
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForStart(Map<String,Object> flowVars);

}
