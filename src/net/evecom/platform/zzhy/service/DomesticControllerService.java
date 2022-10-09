/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 企业登记信息操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface DomesticControllerService extends BaseService {

    /**
     * 
     * 描述 根据父亲ID获取类别数据
     * @author Rider Chen
     * @created 2016年11月28日 下午5:28:24
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId, Map<String, Object> variables);
    

    /**
     * 
     * 描述 根据ID获取拼接编码
     * @author Rider Chen
     * @created 2016年11月29日 上午9:21:22
     * @param parentId
     * @return
     */
    public String getCodeById(String id);
    
    /**
     * 
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByRelatedItemSqlFilter(SqlFilter sqlFilter, Map<String, Object> variables);
    
    /**
     * 
     * 描述 根据IDS获取列表数据
     * @author Rider Chen
     * @created 2017年7月5日 上午9:29:52
     * @param formIds
     * @return
     */
    public List<Map<String,Object>> findByRelatedItemId(String ids);
    /**
     * 
     * 描述 根据itemCodes获取在线编辑附件
     * @author Danto Huang
     * @created 2017年9月12日 下午12:43:16
     * @param itemCodes
     * @return
     */
    public List<Map<String ,Object>> findOnlineMaterByItemCodes(String itemCodes);


    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter);


    public void deleteBusinessScope(String[] split);


    public List<Map<String, Object>> findByBusinessScopeId(String ids);

    /**
     * 描述:获取公司类型
     *
     * @author Madison You
     * @created 2020/10/20 10:56:00
     * @param
     * @return
     */
    String getCompanyType(Map<String, Object> busMap);
}
