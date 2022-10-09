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
import net.evecom.platform.hflow.model.FlowNextHandler;

/**
 * 描述 节点审核操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface NodeAuditerService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取审核人配置
     * @author Flex Hu
     * @created 2015年8月19日 上午11:17:01
     * @param nodeName
     * @param nextNodeName
     * @param defId
     * @return
     */
    public Map<String,Object> getNodeAuditer(String nodeName,String nextNodeName,String defId,int flowVersoin);
    /**
     * 
     * 描述 根据节点审核人配置获取审核信息列表
     * @author Flex Hu
     * @created 2015年8月19日 上午11:25:52
     * @param nodeAuditer
     * @return
     */
    public List<FlowNextHandler> findNextHandler(Map<String,Object> flowVars,Map<String,Object> nodeAuditer);
    /**
     * 
     * 描述 将拷贝旧版本的流程审核人配置信息到新的版本
     * @author Flex Hu
     * @created 2015年11月11日 下午2:11:34
     * @param defId
     * @param newVersion
     * @param oldVersion
     */
    public void saveNewFlowVersionAuditer(String defId,int newVersion,int oldVersion);
    /**
     * 
     * 描述 拷贝下一环节审核人数据
     * @author Flex Hu
     * @created 2016年3月31日 下午4:04:11
     * @param targetDefId
     * @param targetVersion
     * @param newDefId
     */
    public void copyNodeAuditers(String targetDefId,int targetVersion,String newDefId);
}
