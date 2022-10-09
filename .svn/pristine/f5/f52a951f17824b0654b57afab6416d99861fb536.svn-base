/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 成品油业务相关方法
 * 
 * @author Scolder Lin
 * @created 2021年9月7日 上午10:45:59
 */
public interface OilApplyService extends BaseService {
    
    /**
     * 成品油批准证书  办结后置事件
     * 
     * @author Scolder Lin
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    public Map<String, Object> saveAfterBusDataCpy(Map<String, Object> flowDatas) throws Exception;
    
    /**
     * 获取证照在线签章地址
     * 
     * @author Roy Li
     * @param exeId
     * @param certificateIdentifier
     * @created 2021年9月26日 上午10:26:05
     * @return
     */
    public Map<String, Object> getQzUrl(String exeId, String certificateIdentifier);
    
}
