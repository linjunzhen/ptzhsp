/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述    国有建设用地使用权变更登记业务service
 * @author Allin Lin
 * @created 2020年5月13日 下午2:42:48
 */
public interface BdcGyjsydsyqbgdjService extends BaseService{
    
    /**
     * 描述    国有建设用地使用权变更登记通用审批表
     * @author Allin Lin
     * @created 2020年5月13日 下午2:47:55
     * @param args
     */
    public void setGyjsydsyqbgdjData(Map<String, Map<String, Object>> args);
}
