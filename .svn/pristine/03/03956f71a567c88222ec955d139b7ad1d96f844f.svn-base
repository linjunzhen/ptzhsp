/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 公文配置操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface DocumentTemplateService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据documentIds获取公文模板
     * @author Faker Li
     * @created 2015年10月19日 下午2:42:07
     * @param documentIds
     * @return
     */
    public List<Map<String, Object>> findByDocumentIds(String documentIds);
    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月10日 下午5:30:34
     * @param documentTemplate
     * @param variables
     * @return
     */
    public String getUrlByDocumentTemplate(
            Map<String, Object> documentTemplate, Map<String, Object> existParams); 
    
}
