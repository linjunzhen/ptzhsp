/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.elicense.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述 电子证照业务相关service
 * 
 * @author Roy Li
 *
 */
@SuppressWarnings("rawtypes")
public interface ElectronicLicenseService extends BaseService {

    /**
     * 
     * @Description 电子证照-申报登记信息提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    void declareRegisterSubmit();
    
    /**
     * @Description 电子证照-申报登记信息提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    void declareRegisterSubmit(Map<String, Object> itemData);
    
    /**
     * 
     * @Description 电子证照-申报登记信息材料提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    void declareMaterialSubmit();
    
    /**
     * @Description 电子证照-申报登记信息材提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    void declareMaterialSubmit(Map<String, Object> itemData);
    
    /**
     * 
     * @Description 电子证照-申报信息结果提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    void declareResultSubmit();
    
    /**
     * @Description 电子证照-申报信息结果提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    void declareResultSubmit(Map<String, Object> itemData);
    
    /**
     * 
     * @Description 电子证照-申报结果证照提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    void declareResultCertificateSubmit();

    /**
     * @Description 电子证照-申报结果证照提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    void declareResultCertificateSubmit(Map<String, Object> itemData);
}
