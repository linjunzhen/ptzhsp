/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;
import org.apache.commons.logging.Log;

import java.util.List;
import java.util.Map;

/**
 * 描述:不动产获取省外网数据
 *
 * @author Madison You
 * @created 2020/04/15 11:19
 */
public interface BdcGetWwDataService extends BaseService {

    /**
     * 描述:不动产获取省外网数据
     *
     * @author Madison You
     * @created 2020/4/15 11:33:00
     * @param
     * @return
     */
    public void bdcGetWwData(Log log);

    /**
     * 描述:不动产省外网材料列表
     *
     * @author Madison You
     * @created 2020/4/16 18:10:00
     * @param
     * @return
     */
    List<Map<String, Object>> findBdcWwClList(String bdcWwsqbh);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/20 8:55:00
     * @param 
     * @return 
     */
    List<Map<String, Object>> getSqrzbData(String bdcWwsqbh);
    /**
     * 
     * 描述    获取待启动流程数据
     * @author Danto Huang
     * @created 2020年8月17日 上午9:49:40
     * @return
     */
    public List<Map<String,Object>> findNeedStart();
    /**
     * 
     * 描述    创建办件流程
     * @author Danto Huang
     * @created 2020年8月17日 上午9:46:05
     * @param wwData
     * @return
     */
    public Map<String,Object> startWwDataFlow(Map<String,Object> wwData) throws Exception;
}
