/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.business.dao.LeaveInfoDao;
import net.evecom.platform.business.service.LeaveInfoService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;

import org.springframework.stereotype.Service;

/**
 * 描述 请假信息操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("leaveInfoService")
public class LeaveInfoServiceImpl extends BaseServiceImpl implements LeaveInfoService {
    /**
     * 所引入的dao
     */
    @Resource
    private LeaveInfoDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 计算出分支结果接口
     * @author Flex Hu
     * @created 2015年8月19日 下午5:15:45
     * @param flowVars
     * @return
     */
    public Set<String> getDecideResult(Map<String,Object> flowVars){
        String personName = (String) flowVars.get("PERSON_NAME");
        Set<String> resultNodes = new HashSet<String>();
        if(personName.equals("张三")){
            resultNodes.add("人力审批");
        }else{
            resultNodes.add("财务审批");
        }
        return resultNodes;
    }
    
    /**
     * 
     * 描述 将环节审核人转换成串审
     * @author Flex Hu
     * @created 2015年9月4日 上午11:02:56
     * @param steps
     * @return
     */
    public List<FlowNextStep> setTaskOrderValues(List<FlowNextStep> steps){
        for(FlowNextStep step:steps){
            List<FlowNextHandler> handlers = step.getHandlers();
            for(FlowNextHandler handler:handlers){
                if(handler.getNextStepAssignerName().equals("王五")){
                    handler.setTaskOrder(1);
                }else if(handler.getNextStepAssignerName().equals("令狐冲")){
                    handler.setTaskOrder(2);
                }else{
                    handler.setTaskOrder(3);
                }
            }
        }
        return steps;
    }
}
