/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 
 * 描述 不动产权注销登记service
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月20日 下午4:57:54
 */
public interface BdcqZxdjService extends BaseService {
    /**
     * 
     * 描述 阅办模板自定义回填数据的方法
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午9:58:25
     * @param args
     * @return
     */
    public void getRedrawData(Map<String, Map<String, Object>> args);

    /**
     * 
     * 描述 证书模板自定义设值回填数据的方法
     * 
     * @author Roger Li
     * @created 2019年12月19日 上午11:58:44
     * @param args
     * @return
     */
    public void setCertificateRedrawData(Map<String, Map<String, Object>> args);
}
