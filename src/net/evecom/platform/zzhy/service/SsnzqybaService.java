/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 商事内资企业备案操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface SsnzqybaService extends BaseService {

    /**
     * 
     * 描述 预审是否通过
     * 
     * @author Rider Chen
     * @created 2021年3月31日 下午3:38:18
     * @param flowVars
     * @return
     */
    public Set<String> getPerAuditPass(Map<String, Object> flowVars);
    /**
     * 
     * 描述 面签是否通过
     * @author Rider Chen
     * @created 2021年4月1日 下午5:57:05
     * @param flowVars
     * @return
     */
    public Set<String> getSignAuditPass(Map<String, Object> flowVars);
}
