/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 监察点service
 * @author Sundy Sun
 * @version v2.0
 *
 */
public interface MonitorNodeService extends BaseService{
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 根据过程编码查询要素
     * @param processCode
     * @return
     */
    public List<Map<String,Object>> findByProcessCode(String processCode);
    /**
     * 批量保存
     * @param vars
     */
    public void saveOrUpdateBatch(Map<String,Object> vars);
    
    /**
     * 提交要素审核
     * 
     * @param tacheCode环节编码
     * @param busCode业务专项
     */
    public void factorSubmit(String busCode,String userId);
    
    /**
     * 根据过程编码查询要素
     * @param processCode
     * @return
     */
    public List<Map<String,Object>> findByTacheCode(String tacheCode);
    /**
     * 根据环节编码查询过程
     * @param processCode
     * @return
     */
    public List<Map<String,Object>> findMonitorNodeByTCode(String tacheCode);
    /**
     * change factor
     * 
     * @param tachecode
     * @param busCode yewu
     */
    public void changeFactorSubmit(String busCode,String userId);
}
