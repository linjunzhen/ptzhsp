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
 * 描述 流程按钮操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowButtonService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月13日 下午3:09:30
     * @return
     */
    public List<Map<String,Object>> findList();
    
    /**
     * 
     * 描述 根据按钮IDS删除掉记录
     * @author Flex Hu
     * @created 2015年8月13日 下午3:49:10
     * @param buttonIds
     */
    public void deleteButton(String[] buttonIds);
    /**
     * 
     * 描述 获取新配置的按钮数据
     * @author Flex Hu
     * @created 2015年12月3日 上午9:43:54
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findNewConfigButtons(String defId,String nodeName,int flowVersion);
}
