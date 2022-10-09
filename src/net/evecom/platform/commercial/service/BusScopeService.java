/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJsonCode;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;

/**
 * 描述  商事-经营范围管理Service
 * @author Allin Lin
 * @created 2020年11月19日 下午3:00:03
 */
public interface BusScopeService extends BaseService{
    /**
     * 行业类别初始化主键Id
     */
    public static String INIT_INDUID="0";
    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter);
    
    /**
     * 描述    根据子行业IDS获取已选子行业列表数据
     * @author Allin Lin
     * @created 2020年11月21日 下午3:24:34
     * @param childIndustryIds
     * @return
     */
    public List<Map<String,Object>> findBychildIndustryIds(String childIndustryIds);
    /**
     * 描述    导入行业类别数据
     * @author Allin Lin
     * @created 2020年11月21日 下午3:24:34
     * @param industry
     * @return
     */
    public AjaxJsonCode importXlsDataOfIndustry(Map industry);
    /**
     * 描述    根据父id获取行业内容
     * @author Allin Lin
     * @created 2020年11月21日 下午3:24:34
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findIndustryCategory(String parentId);
    /**
     * 根据excel附件fileId导入行业分类
     * @param fileId
     * @return
     */
    public AjaxJsonCode importXlsDataByFileId(String fileId);
}
