/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.platform.hflow.model.FlowNextHandler;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月19日 上午9:19:27
 */
public interface HandlerConfigService extends BaseService {
    /**
     * 
     * 描述 获取指定角色审核人的接口
     * @author Flex Hu
     * @created 2015年8月19日 上午9:22:17
     * @param flowVars
     * @param receiveRoleId
     * @return
     */
    public List<FlowNextHandler> getAssignRoleHandlers(Map<String,Object> flowVars,
            String receiveRoleCodes,String handlerRule);
    
    /**
     * 
     * 描述 根据流程变量值获取审核人
     * @author Flex Hu
     * @created 2015年8月20日 下午12:53:31
     * @param flowVars
     * @param varName:这个变量值的内容是用户帐号,用逗号拼接开的
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getVarsHandlers(Map<String,Object> flowVars,
            String varName,String handlerRule);
    
    /**
     * 
     * 描述 获取发起人数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:13:04
     * @param flowVars
     * @param varName
     * @param handlerRule
     * @return
     */
    public List<FlowNextHandler> getStartHandlers(Map<String,Object> flowVars,
            String varName,String handlerRule);

    /**
     * 描述:不动产全流程专用（根据表单上填写的测绘机构名称获取账号）
     *
     * @author Madison You
     * @created 2020/12/15 16:26:00
     * @param
     * @return
     */
    public List<FlowNextHandler> getDrawOrgHandlers(Map<String,Object> flowVars,
                                                    String varName,String handlerRule);
    
}
