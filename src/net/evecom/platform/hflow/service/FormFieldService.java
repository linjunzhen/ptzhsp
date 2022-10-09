/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 表单字段操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FormFieldService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据url生成表单字段
     * @author Flex Hu
     * @created 2015年8月10日 下午4:03:37
     * @param formId
     * @param url
     * @throws IOException 
     */
    public void saveOrUpdate(String formId,String url) throws Exception;
    /**
     * 
     * 描述 根据表单ID删除掉字段数据
     * @author Flex Hu
     * @created 2015年8月10日 下午6:56:33
     * @param formId
     */
    public void deleteByFormId(String formId);
    /**
     * 
     * 描述 根据表单ID获取字段列表
     * @author Flex Hu
     * @created 2015年8月12日 上午9:27:29
     * @param formId
     * @return
     */
    public List<Map<String,Object>> findByFormId(String formId);
}
