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
 * 描述 流程映射类操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowMappedService extends BaseService {
    /**
     * 
     * 描述 获取流程映射列表
     * 
     * @author Faker Li
     * @created 2015年12月22日 下午2:51:07
     * @param filter
     * @return
     */
    List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 
     * 描述 流程定义ID和节点名称获取映射列表
     * 
     * @author Faker Li
     * @created 2015年12月23日 上午9:02:50
     * @param defId
     * @param nodeName
     * @return
     */
    Map<String, Object> getByDefidAndNodeName(String defId, String nodeName);

    /**
     * 
     * 描述 流程定义ID获取映射列表
     * 
     * @author Rider Chen
     * @created 2016-2-1 上午11:27:25
     * @param defId
     * @return
     */
    List<Map<String, Object>> getByDefidAndNodeName(String defId);

    /**
     * 
     * 描述 保存流程到映射节点并且保存到映射子表
     * 
     * @author Faker Li
     * @created 2015年12月28日 下午2:17:34
     * @param variables
     * @param string
     * @return
     */
    String saveOrUpdateYs(Map<String, Object> variables, String tableName);

    /**
     * 
     * 描述 根据流程定义ID和节点名称和映射类型获取映射信息
     * 
     * @author Flex Hu
     * @created 2015年12月28日 下午3:27:16
     * @param defId
     * @param nodeName
     * @param mapType
     * @return
     */
    public Map<String, Object> getFlowMapInfo(String defId, String nodeName,
            String mapType);
}
