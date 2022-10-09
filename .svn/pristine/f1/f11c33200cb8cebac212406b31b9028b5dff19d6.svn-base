/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.platform.hflow.model.FlowNextHandler;

/**
 * 
 * 描述
 * @author Faker Li
 * @created 2015年11月6日 下午12:14:49
 */
public interface BjxxService extends BaseService {
    /**
     * 
     * 描述 获取行政和公共服务审核人
     * @author Flex Hu
     * @created 2015年12月10日 下午3:52:08
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsers(Map<String,Object> flowVars,
            String varName,String handlerRule);
    /**
     * 
     * 描述 根据流程实例ID删除补件信息
     * @author Faker Li
     * @created 2015年12月16日 下午3:06:22
     * @param string
     */
    public void removeByExeid(String exeId);
    /**
     * 
     * 描述 获取行政和公共服务审核人(子部门权限控制)
     * @author Danto Huang
     * @created 2016-3-3 下午4:32:26
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsersWithChild(Map<String,Object> flowVars,
            String varName,String handlerRule);
    /**
     * 
     * 描述   获取行政和公共服务审核人(根据服务事项流程环节审核人设置)
     * @author Danto Huang
     * @created 2016-9-29 上午9:45:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsersByItemSet(Map<String,Object> flowVars,
            String varName,String handlerRule);
    /**
     * 
     * 描述   获取行政和公共服务审核人(根据服务事项流程环节审核人设置)
     * @author Danto Huang
     * @created 2016-9-29 上午9:45:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getXzfwUsersByItemSetSl(Map<String,Object> flowVars,
            String varName,String handlerRule);
    /**
     * 
     * 描述   是否需要网上预审
     * @author Danto Huang
     * @created 2016-10-20 下午4:48:27
     * @param flowVars
     * @return
     */
    public Set<String> getIsPreAudit(Map<String, Object> flowVars);

    /**
     * 描述:是否需要网上预审（小微企业）
     *
     * @author Madison You
     * @created 2019/9/17 10:54:00
     * @param
     * @return
     */
    public Set<String> getIsPreAuditXW(Map<String, Object> flowVars);

    /**
     * 描述:是否直接跳转到区行政审批局经办人填写意见（产业奖补）
     *
     * @author Madison You
     * @created 2019/11/13 17:39:00
     * @param
     * @return
     */
    public Set<String> getIsToTXYJ(Map<String, Object> flowVars);

    /**
     * 描述:是否直接跳转到区行政审批局经办人填写意见（产业奖补）
     *
     * @author Madison You
     * @created 2020/8/31 8:43:00
     * @param
     * @return
     */
    public Set<String> getIsToTXYJT(Map<String, Object> flowVars);
    /**
     * 
     * 描述 是否需四星及以上事项
     * @author Kester Chen
     * @created 2017-9-13 上午9:28:30
     * @param flowVars
     * @return
     */
    public Set<String> getIsFourStar(Map<String, Object> flowVars);
    /**
     * 
     * 描述 是否需五星及以上事项
     * @author Kester Chen
     * @created 2017-9-13 上午9:28:30
     * @param flowVars
     * @return
     */
    public Set<String> getIsFiveStar(Map<String, Object> flowVars);
    /**
     * 
     * 描述     是否并联审批通过
     * @author Danto Huang
     * @created 2016-11-11 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsParallelAuditPass(Map<String, Object> flowVars);
    /**
     *
     * 描述     是否并联审批通过
     * @author Danto Huang
     * @created 2016-11-11 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsParallelAuditPassForSign(Map<String, Object> flowVars);
    /**
     * 
     * 描述   是否面签通过
     * @author Kester Chen
     * @created 2019年6月27日 上午10:44:54
     * @param flowVars
     * @return
     */
    public Set<String> getIsFaceSignPass(Map<String, Object> flowVars);
    /**
     * 
     * 描述  商事1+N事项审核人
     * @author Danto Huang
     * @created 2017年9月27日 上午10:36:46
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getComRelatedAuditer(Map<String,Object> flowVars,
            String varName,String handlerRule);

    
    /**
     * 描述:获取退回意见
     *
     * @author Madison You
     * @created 2019/12/10 11:40:00
     * @param 
     * @return 
     */
    Map<String, Object> getBjxxInfo(String exeId);
    
    /**
     * 
     * 描述：不动产全程网办是否需要网上预审
     * @author Rider Chen
     * @created 2020年8月24日 下午4:27:51
     * @param flowVars
     * @return
     */
    public Set<String> getIsPreAuditBdcqcwb(Map<String, Object> flowVars);
    
    
    /**
     * 
     * 描述    根据日期同步办件（用户）信息至办事（取号）用户信息表中
     * @author Allin Lin
     * @created 2021年3月17日 下午2:14:07
     * @param date
     */
    public void  saveOrUpdateLineUsers (String date);
  
}
