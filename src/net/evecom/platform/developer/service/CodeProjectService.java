/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.developer.model.CodeTableInfo;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface CodeProjectService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 新增或者修改代码建模项目
     * @author Flex Hu
     * @created 2014年9月16日 上午9:21:03
     * @param request
     */
    public void saveOrUpdateCodeProject(HttpServletRequest request);
    /**
     * 
     * 描述 获取所有的建模包
     * @author Flex Hu
     * @created 2014年9月17日 上午8:56:12
     * @return
     */
    public List<Map<String,Object>> findAllProject();
    /**
     * 
     * 描述 根据项目ID删除掉数据
     * @author Flex Hu
     * @created 2014年9月19日 上午10:42:26
     * @param projectId
     */
    public void removeByProjectId(String projectId);
    /**
     * 
     * 描述 根据项目ID获取到配置的数据库信息
     * @author Flex Hu
     * @created 2014年9月20日 上午10:53:32
     * @param projectId
     * @return
     */
    public List<Map<String,Object>> findTableInfos(String projectId);
    /**
     * 
     * 描述 根据项目ID获取所配置的表格信息
     * @author Flex Hu
     * @created 2014年9月20日 下午8:51:12
     * @param projectId
     * @return
     */
    public List<CodeTableInfo> findByProject(String projectId);
    /**
     * 
     * 描述 获取配置的主表信息
     * @author Flex Hu
     * @created 2014年9月20日 下午8:57:27
     * @param projectId
     * @return
     */
    public CodeTableInfo getMainTableInfo(String projectId);
}
