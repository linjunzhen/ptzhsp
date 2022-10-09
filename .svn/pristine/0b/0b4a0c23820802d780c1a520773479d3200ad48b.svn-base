/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.bsfw.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-11-17 上午9:58:52
 */
public interface CommercialService extends BaseService {
    /**
     * 
     * 描述   获取需要自动跳转记录
     * @author Danto Huang
     * @created 2016-11-17 上午10:16:16
     * @return
     */
    public List<Map<String, Object>> findNeedAutoJump();
    /**
     * 
     * 描述   获取并审需要自动跳转记录
     * @author Danto Huang
     * @created 2016-12-13 上午10:59:28
     * @return
     */
    public List<Map<String, Object>> findBsNeedAutoJump();
    /**
     * 
     * 描述   预审意见汇总自动跳转
     * @author Danto Huang
     * @created 2016-11-17 上午10:22:56
     * @param data
     */
    public void jumpTaskForYjhz(Map<String, Object> data) throws Exception;
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:42:06
     * @param parentCode
     * @return
     */
    public List<Map<String,Object>> getIndustryByParentCode(String parentCode);
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-11-23 上午10:42:06
     * @param parentCode
     * @return
     */
    public List<Map<String,Object>> findTZIndustryBySqlFilter(SqlFilter filter);
    /**
     * 
     * 描述   商事预审自动审核通过
     * @author Danto Huang
     * @created 2016-12-8 下午3:34:57
     * @param flowVars
     * @return
     */
    public Map<String, Object> preAuditAutoPass(Map<String,Object> flowVars) throws Exception;
    /**
     * 
     * 描述   商事窗口办理新增办理人员
     * @author Danto Huang
     * @created 2016-12-8 下午3:34:57
     * @param flowVars
     * @return
     */
    public Map<String, Object> updateOperatorId(Map<String,Object> flowVars) throws Exception;
    /**
     * 
     * 描述   商事办结前同步接口获取返回信息
     * @author Danto Huang
     * @created 2016-12-8 下午3:34:57
     * @param flowVars
     * @return
     */
    public Map<String, Object> getEntInfo(Map<String,Object> flowVars) throws Exception;

    public void getEicpEntInfo(String exeId);
    /**
     * 
     * 描述   判断商事工商预审和国税预审已完成
     * @author Danto Huang
     * @created 2016-12-8 下午4:00:32
     * @param exeId
     * @return
     */
    public boolean isGsGsAudited(String exeId);
    /**
     * 
     * 描述   获取同源在办任务
     * @author Danto Huang
     * @created 2016-12-8 下午4:34:48
     * @param taskId
     * @return
     */
    public List<Map<String,Object>> findSameFromTasks(String taskId);
    /**
     * 
     * 描述   核准日期
     * @author Danto Huang
     * @created 2017-1-4 下午2:40:54
     * @param flowVars
     * @return
     */
    public Map<String, Object> approvalDate(Map<String,Object> flowVars);
    /**
     * 
     * 描述   获取字段审核记录
     * @author Danto Huang
     * @created 2017-1-9 下午2:25:28
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findFieldOpinion(SqlFilter filter);
    /**
     * 
     * 描述   更新字段审核记录状态
     * @author Danto Huang
     * @created 2017-1-9 下午3:23:00
     * @param flowVars
     * @return
     */
    public Map<String,Object> updateFieldAutitStatus(Map<String,Object> flowVars);

    /**
     * 
     * 描述   判断审批是否已完成
     * @author Danto Huang
     * @created 2016-12-8 下午4:00:32
     * @param exeId
     * @return
     */
    public boolean isAudited(String exeId);
    /**
     * 
     * 描述   商事审批自动审核通过
     * @author Danto Huang
     * @created 2016-12-8 下午3:34:57
     * @param flowVars
     * @return
     */
    public Map<String, Object> auditAutoPass(Map<String,Object> flowVars) throws Exception;
    
    /**
     * 
     * 描述   判断审批是否已完成
     * @author Danto Huang
     * @created 2016-12-8 下午4:00:32
     * @param exeId
     * @return
     */
    public void updateField(String exeId,String fieldName,String curNode);
    /**
     * 
     * 描述：定时器发送商事信息到信用平台
     * @author Water Guo
     * @created 2017-12-26 下午2:41:34
     */
    public void sendToCreditByTimer();
    /**
     * 
     * 描述 获取需要自动跳转的五星流程
     * @author Kester Chen
     * @created 2019年6月27日 上午11:45:22
     * @return
     */
    public List<Map<String, Object>> findWXNeedAutoJump();
    /**
     * 
     * 描述 获取需要自动跳转的办结环节
     * @author Kester Chen
     * @created 2019年6月27日 上午11:45:22
     * @return
     */
    public List<Map<String, Object>> findBJNeedAutoJump();
    /**
     * 
     * 描述 获取需要自动跳转的审查与决定环节
     * @author Kester Chen
     * @created 2019年6月27日 上午11:45:22
     * @return
     */
    public List<Map<String, Object>> findSCYJDNeedAutoJump();
    /**
     * 
     * 描述 获取是要跳转的面签流程
     * @author Kester Chen
     * @created 2019年6月27日 上午11:45:53
     * @return
     */
    public List<Map<String, Object>> findMQNeedAutoJump();
    /**
     * 
     * 描述   自动退件
     * @author Danto Huang
     * @created 2016-11-17 上午10:22:56
     * @param data
     */
    public void jumpTaskForZdtj(Map<String, Object> data) throws Exception;

    /**
     * 描述:住房公积金申请表格数据
     *
     * @author Madison You
     * @created 2020/11/9 15:32:00
     * @param
     * @return
     */
    public Map<String,Object> zfgjjsqTableData(Map<String, Map<String, Object>> args);
    /**
     *
     * 描述   获取需要商事身份认证自动跳转记录
     * @author Danto Huang
     * @created 2016-11-17 上午10:16:16
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMpIdIdentifyNeedAutoJump(String curStepName,String taskNodeName) ;
    
    /**
     * 
     * 描述 住房公积金申请表格数据（新）
     * @author Rider Chen
     * @created 2021年6月30日 上午9:45:02
     * @param args
     * @return
     */
    public Map<String,Object> zfgjjsqTableDataNew(Map<String, Map<String, Object>> args);
}
