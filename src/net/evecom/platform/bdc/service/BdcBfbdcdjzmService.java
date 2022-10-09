/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述 不动产全流程补发不动产权证书和不动产登记证明
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年3月24日 下午5:13:08
 */
public interface BdcBfbdcdjzmService extends BaseService {

    /**
     * 描述:不动产全流程补发不动产权证书和不动产登记证明（补发不动产登记）数据回填
     *
     * @author Madison You
     * @created 2020/3/24 17:20:00
     * @param
     * @return
     */
    public void setBfbdcdjzmData(Map<String, Map<String, Object>> args);

    /**
     * 描述:不动产全流程补发不动产权证书和不动产登记证明数据回填
     *
     * @author Madison You
     * @created 2020/4/28 14:23:00
     * @param
     * @return
     */
    public void setBfbdcqzshdjzmData(Map<String, Map<String, Object>> args);

    /**
     * 描述:不动产全流程换发产权登记数据回填
     *
     * @author Madison You
     * @created 2020/4/28 17:00:00
     * @param
     * @return
     */
    public void setHfcqdjData(Map<String, Map<String, Object>> args);

}
