/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;

import java.util.Map;

/**
 * 
 * 描述 不动产审批表打印
 * @author Kester Chen
 * @created 2020年5月19日 上午11:33:32
 */
public interface BdcSpbdyService extends BaseService {

    /**
     * 描述:不动产全流程集体建设用地使用权转移登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 16:56:00
     * @param
     * @return
     */
    public void setJtjsydsyqzydjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:不动产全流程集体建设用地使用权变更登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 16:56:00
     * @param
     * @return
     */
    public void setJtjsydsyqbgdjData(Map<String, Map<String, Object>> args);

    /**
     * 描述:不动产全流程集体建设用地使用权首次登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 16:56:00
     * @param
     * @return
     */
    public void setJtjsydsyqscdjData(Map<String, Map<String, Object>> args);

    /**
     * 
     * 描述 不动产全流程国有建设用地使用权及房屋所有权首次登记审批表
     * @author Kester Chen
     * @created 2020年5月19日 下午2:26:01
     * @param args
     */
    public void setGyjsydsyqjfwsyqscdjData(Map<String, Map<String, Object>> args);
    
    public void setGyjsydsyqjfwsyqbgdjData(Map<String, Map<String, Object>> args);

    public void setGyjsydsyqscdjData(Map<String, Map<String, Object>> args);


}
