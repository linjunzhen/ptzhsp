/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 个体平台信息管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface GtptxxglService extends BaseService {
    /**
     * 
     * 描述：查询列表信息
     * @author Rider Chen
     * @created 2020年11月30日 下午2:13:48
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);
    
    /**
     * 
     * 描述：  是否面签通过
     * @author Rider Chen
     * @created 2020年12月1日 下午4:34:21
     * @param flowVars
     * @return
     */
    public Set<String> getIsFaceSignPass(Map<String, Object> flowVars);
    
    
    /**
     * 
     * 描述：是否秒批
     * @author Rider Chen
     * @created 2020年12月1日 下午4:44:25
     * @param flowVars
     * @return
     */
    public Set<String> getIsAutoApproval(Map<String, Object> flowVars);

    /**
     * 
     * 描述 根据办件流水号获取任务状态
     * @author Rider Chen
     * @created 2021年2月22日 下午4:16:02
     * @param exeId
     * @return
     */
    public String getTaskStatus(String exeId);
}
