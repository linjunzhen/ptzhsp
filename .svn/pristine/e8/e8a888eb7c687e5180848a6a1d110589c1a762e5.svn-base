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
 * 描述 流程表单操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FlowFormService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 级联删除掉流程表单
     * @author Flex Hu
     * @created 2015年8月10日 上午10:48:29
     * @param formIds
     */
    public void removeCascade(String[] formIds);
    /**
     * 
     * 描述 更新流程表单信息并且级联更新字段
     * @author Flex Hu
     * @created 2015年8月10日 下午4:13:32
     * @param flowForm
     */
    public void saveOrUpdateCascadeField(Map<String, Object> flowForm)throws Exception;
    /**
     * 
     * 描述 根据流程表单获取最终的URL
     * @author Flex Hu
     * @created 2015年12月11日 上午10:14:45
     * @param flowForm
     * @return
     */
    public String getUrlByFlowForm(Map<String,Object> flowForm,Map<String,Object> existParams);
}
