/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;

/**
 * 描述 Excel导出配置操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ExcelConfigService extends BaseService {
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 获取配置的查询条件
     * @author Flex Hu
     * @created 2014年10月4日 下午7:57:36
     * @param configId
     * @return
     */
    public List<Map<String,Object>> findQuerys(String configId);
    /**
     * 生成EXCEL文件,返回路径
     * 
     * @author Flex Hu
     * @param excelConfig
     * @return
     */
    public String genExcel(Map<String,Object> excelConfig, HttpServletRequest request,
            PagingBean pb,HttpServletResponse response);
    
    /**
     * 
     * 描述：生成EXCEL文件,返回路径
     * @author Water Guo
     * @created 2017-3-1 上午10:21:46
     * @param excelConfig
     * @param request
     * @param pb
     * @param response
     * @param exportType
     * @return
     */
    public String genExcel(Map<String,Object> excelConfig, HttpServletRequest request,
            PagingBean pb,HttpServletResponse response,String exportType);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 11:55:00
     * @param 
     * @return 
     */
    List<Map<String, Object>> querySqlExportConfigData(SqlFilter filter);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/24 14:21:00
     * @param 
     * @return 
     */
    List<Map<String, Object>> exportSqlExportConfig(Map<String, Object> sqlMap);
}
