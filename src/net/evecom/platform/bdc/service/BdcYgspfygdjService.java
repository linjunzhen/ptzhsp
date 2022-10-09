/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述 预购商品房预告操作service
 * @author Allin Lin
 * @created 2019年3月13日 上午10:21:30
 */
public interface BdcYgspfygdjService extends BaseService{
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年8月24日 下午2:52:52
     * @param flowVars
     * @return
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars);
    
    /**
     * 
     * 描述 生成预购预告申请表
     * 
     * @author Roger Li
     * @created 2020年1月15日 上午9:51:57
     */
    public void generateSQB(Map<String, Object> returnMap,String path);
    /**
     * 
     * 描述    通用文档生成
     * @author Danto Huang
     * @created 2020年8月24日 下午10:47:59
     * @param returnMap
     * @param path
     */
    public void generateWord(Map<String, Object> returnMap,String path);
    
    /**
     * 描述    平潭通--预购商品房预告登记业务签章事件（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public Map<String, Object> ygspfygdjGenAndSignForPtt(Map<String,Object> flowVars)throws Exception;
    
    /**
     * 描述    预购商品房预告登记业务（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public void genAndSign(Map<String,Object> flowVars)throws Exception;
}
