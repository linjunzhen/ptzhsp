/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 流程类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowTypeService extends BaseService {
    /**
     * 
     * 描述 获取下拉框的值
     * @author Flex Hu
     * @created 2015年8月7日 下午4:07:36
     * @return
     */
    public List<Map<String,Object>> findForSelect(String param);
    /**
     * 
     * 描述 获取可被发起的流程定义数据
     * @author Flex Hu
     * @created 2015年8月11日 上午9:33:09
     * @return
     */
    public List<Map<String,Object>> findDefForStart(HttpServletRequest request);
    /**
     * 
     * 描述 根据sqlfilter获取数据列表
     * @author Flex Hu
     * @created 2015年8月11日 上午9:36:52
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取类别数据
     * @author Flex Hu
     * @created 2015年8月18日 下午4:10:41
     * @return
     */
    public List<Map<String,Object>> findTypes(String parentId);
}
