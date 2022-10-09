/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 描述  不动产全流程集体建设用地及房屋所有权
 * @author Madison You
 * @created 2020年4月21日 下午4:17:01
 */
public interface BdcJtjsydsyqjfwsyqService extends BaseService {

    /**
     * 描述:集体建设用地使用权及房屋所有权转移登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 16:56:00
     * @param 
     * @return 
     */
    public void setJtjsydsyqjfwsyqzydjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:集体建设用地使用权及房屋所有权变更登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 16:56:00
     * @param
     * @return
     */
    public void setJtjsydsyqjfwsyqbgdjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:集体建设用地使用权及房屋所有权首次登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 16:56:00
     * @param
     * @return
     */
    public void setJtjsydsyqjfwsyqscdjData(Map<String, Map<String, Object>> args);



}
