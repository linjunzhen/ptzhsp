/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

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
public interface BusAuditService extends BaseService {
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
     * 提交第一次审核过程
     * 
     * @author John Zhang
     * @created 2015-9-1 下午03:26:11
     * @param applyId
     * @param busCode
     */
    public void firstAudit(String applyId, String busCode);

    /**
     * 改变监察梳理审核状态
     * 
     * @author John Zhang
     * @created 2015-9-10 上午09:21:09
     * @param busCode
     */
    public void changeStatus(String busCode, String status);

    /**
     * 提交第二次审核 调用存储过程
     * 
     * @author John Zhang
     * @created 2015-9-1 下午03:26:11
     * @param applyId
     * @param busCode
     */
    public void secondSubmit(String applyId, String busCode);

    /**
     * 判断新增 监察审核表
     * 
     * @author John Zhang
     * @created 2015-9-14 上午10:47:36
     * @param busCode
     */
    public void judgeAudit(String busCode);

    /**
     * 流程审核后置事件
     * 
     * @author John Zhang
     * @created 2015-9-15 下午12:52:54
     * @param flowDatas
     * @return
     */
    public Map<String, Object> afterAudit(Map<String, Object> flowDatas);

    /**
     * 
     * 第一阶段审核改变状态
     * 
     * @author John Zhang
     * @created 2015-9-23 下午04:10:12
     * @param busCode
     * @param status
     */
    public void firstStatus(String busCode, String status);
    
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
     * 
     * 开始节点分支判断
     * @author John Zhang
     * @created 2015年11月25日 下午3:11:26
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForStart(Map<String,Object> flowVars);
    
    /**
     * 
     * 开始节点分支判断
     * @author John Zhang
     * @created 2015年11月25日 下午3:11:26
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResultForJxStart(Map<String,Object> flowVars);
}
