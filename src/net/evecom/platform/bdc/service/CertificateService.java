/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 
 * 描述 权证模板操作service
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月20日 下午4:14:08
 */
public interface CertificateService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);

    /**
     * 描述 根据certificateId获取证书模板信息
     * 
     * @author Roger Li
     * @created 2019年12月18日 下午4:18:53
     * @param
     * @return
     */
    public List<Map<String, Object>> findByCertificateId(String certificateIds);

}
