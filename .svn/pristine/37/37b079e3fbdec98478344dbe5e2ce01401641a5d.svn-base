/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.smoga.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月7日 下午4:42:20
 */
public interface PreApprovalService extends BaseService {

    /**
     * 
     * 描述    查询数据列表
     * @author Danto Huang
     * @created 2018年8月8日 上午9:00:08
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findPreApprovalsBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月8日 下午4:49:01
     * @return
     */
    public int getMaxSn();
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月2日 下午6:11:18
     * @return
     */
    public int getCurVersion(); 

    /**
     * 
     * 描述    保存变更记录到历史版本库
     * @author Danto Huang
     * @created 2018年8月9日 上午9:17:36
     * @param preId
     */
    public void copyToHis(String preId);
    /**
     * 
     * 描述    保存
     * @author Danto Huang
     * @created 2018年8月3日 上午11:38:52
     * @param variables
     * @return
     */
    public String saveOrUpdateRecord(Map<String,Object> variables);
    /**
     * 
     * 描述    获取流转日志
     * @author Danto Huang
     * @created 2018年8月13日 上午10:09:25
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findTransLog(String busTableName,String busRecordId);
    /**
     * 
     * 描述    判断是否存在历史版本
     * @author Danto Huang
     * @created 2018年8月13日 下午3:17:44
     * @param entityId
     * @return
     */
    public boolean isHavingHis(String entityId);
    /**
     * 
     * 描述    历史版本查询列表
     * @author Danto Huang
     * @created 2018年8月13日 下午3:28:27
     * @param entityId
     * @return
     */
    public List<Map<String,Object>> findHisVersionForSelect(String entityId);
    /**
     * 
     * 描述    复制
     * @author Danto Huang
     * @created 2018年8月17日 上午9:54:43
     * @param preIds
     * @param departIds
     * @param departNames
     * @param idAddress
     */
    public void copyToOtherDepart(String preIds,String departIds,String departNames,String idAddress);
}
