/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 节点配置操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface NodeConfigService extends BaseService {
    /**
     * 
     * 描述 根据流程定义ID和流程节点名称获取配置的决策代码
     * @author Flex Hu
     * @created 2015年8月20日 下午4:31:56
     * @param defId
     * @param nodeName
     * @return
     */
    public String getTaskDecideCode(String defId,String nodeName,int flowVersion);
    /**
     * 
     * 描述 获取截止日期
     * @author Flex Hu
     * @created 2015年11月7日 下午12:35:31
     * @param nodeName
     * @param flowVars
     * @return
     */
    public String getDeadLineDate(String nodeName,Map<String,Object> flowVars);
    
    /**
     * 
     * 描述 将拷贝旧版本的流配置信息到新的版本
     * @author Flex Hu
     * @created 2015年11月11日 下午2:11:34
     * @param defId
     * @param newVersion
     * @param oldVersion
     */
    public void saveNewFlowVersionConfig(String defId,int newVersion,int oldVersion);
    /**
     * 
     * 描述 获取省网办的发起节点的名称
     * @author Flex Hu
     * @created 2016年1月26日 下午3:06:05
     * @param defId
     * @param flowVersion
     * @return
     */
    public String getNodeNameForProvinceStart(String defId,int flowVersion);
    /**
     * 
     * 描述 拷贝节点配置信息
     * @author Flex Hu
     * @created 2016年3月31日 下午4:12:25
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyNodeConfigs(String targetDefId,int targetFlowVersion,String newDefId);
}
