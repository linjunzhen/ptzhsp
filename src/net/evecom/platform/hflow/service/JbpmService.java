/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月6日 下午5:41:11
 */
public interface JbpmService extends BaseService {
    /**
     * 
     * 描述 根据定义的JSON获取相同节点名称的节点
     * @author Flex Hu
     * @created 2015年8月7日 上午8:35:47
     * @param defJson
     * @return
     */
    public String getSameNode(String defJson);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月14日 下午3:01:32
     * @param flowVars
     * @return
     */
    public Map<String,Object> getStartFlowUser(Map<String,Object> flowVars);
    /**
     * 
     * 描述 进行流程动作的执行
     * @author Flex Hu
     * @created 2015年8月18日 上午10:31:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> doFlowJob(Map<String,Object> flowVars) throws Exception;
    /**
     * 
     * 描述 获取下一环节信息
     * @author Flex Hu
     * @created 2015年8月19日 下午5:03:19
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @param flowVars
     * @return
     */
    public List<FlowNextStep> findNextSteps(String defId,String nodeName,int flowVersion,
            Map<String,Object> flowVars,List<FlowNextStep> nextTrans,List<Map<String,Object>> nextNodes);
    
    /**
     * 
     * 描述 将环节审核转换成单人审核
     * @author Flex Hu
     * @created 2015年8月20日 下午4:12:52
     * @param nextSteps
     * @return
     */
    public List<FlowNextStep> convertTaskToSingle(String nodeName,List<FlowNextHandler> handlers);
    /**
     * 
     * 描述 将环节审核转换成多人审核(禁止流转)
     * @author Flex Hu
     * @created 2015年8月21日 下午2:10:16
     * @param nodeName
     * @param handlers
     * @return
     */
    public List<FlowNextStep> conventTaskToMulitNoFree(String nodeName,List<FlowNextHandler> handlers);
    /**
     * 
     * 描述  将环节审核转换成多人审核(自由流转)
     * @author Flex Hu
     * @created 2015年8月21日 下午2:19:11
     * @param nodeName
     * @param handlers
     * @return
     */
    public List<FlowNextStep> conventTaskToMulitFree(String nodeName,List<FlowNextHandler> handlers);
    /**
     * 
     * 描述 将环节审核转换成部门审批(自由流转)
     * @author Flex Hu
     * @created 2015年8月24日 下午5:54:49
     * @param nodeName
     * @param handlers
     * @return
     */
    public List<FlowNextStep> conventTaskToMulitDep(String nodeName,List<FlowNextHandler> handlers);
    /**
     * 
     * 描述 启动流程
     * @author Flex Hu
     * @created 2015年8月26日 下午3:46:22
     * @param flowVars
     * @return
     */
    public Map<String,Object> exeFlow(Map<String,Object> flowVars) throws Exception;
    /**
     * 
     * 描述 获取下一环节的相关经办人信息JSON
     * @author Flex Hu
     * @created 2016年1月26日 上午11:29:28
     * @param defId
     * @param flowVersion
     * @param nodeName
     * @return
     */
    public String getNextStepsJson(String defId,int flowVersion,String currentNodeName,Map<String,Object> flowVars);
    
    /**
     * 
     * 描述 启动流程
     * @author Flex Hu
     * @created 2015年8月26日 下午3:46:22
     * @param flowVars
     * @return
     */
    public Map<String,Object> startFlow(Map<String,Object> flowVars) throws Exception;
    
    /**
     * 
     * 描述 退回补件流程
     * @author Faker Li
     * @created 2015年11月30日 下午4:25:17
     * @param variables
     * @return
     * @throws Exception
     */
    public Map<String, Object> doBjFlowJob(Map<String, Object> flowVars)throws Exception;
    /**
     * 
     * 描述 获取流程信息对象信息
     * @author Flex Hu
     * @created 2015年12月4日 上午9:54:17
     * @param defKey
     * @param exeId
     * @param request
     * @param isQueryDetail
     * @return
     */
    public Map<String,Object> getFlowAllObj(String defKey,String exeId,String isQueryDetail,HttpServletRequest request);
    /**
      * 描述
      * @author Faker Li
      * @created 2016年5月4日 下午2:14:47
      * @param variables
      * @return
      */
     public Map<String, Object> exeFrontFlow(Map<String, Object> variables)throws Exception;
            
}
