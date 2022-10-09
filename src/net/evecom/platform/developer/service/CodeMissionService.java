/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.model.TableColumn;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 代码任务操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface CodeMissionService extends BaseService {
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter);
    /**
     * 
     * 描述 根据任务ID获取项目ID
     * @author Flex Hu
     * @created 2014年9月21日 上午8:20:50
     * @param missionId
     * @return
     */
    public String getProjectId(String missionId);
    /**
     * 
     * 描述 生成任务代码到磁盘
     * @author Flex Hu
     * @created 2014年9月21日 上午10:08:02
     * @param missionId
     */
    public void genCodeToDisk(String missionId);
    /**
     * 
     * 描述 根据任务获取代码
     * @author Flex Hu
     * @created 2014年10月3日 上午8:05:05
     * @param missionId
     * @return
     */
    public String getCodeByMissionId(String missionId);
    /**
     * 
     * 描述 获取数据库列值
     * @author Flex Hu
     * @created 2014年9月21日 下午8:06:53
     * @param missionId
     * @param isForQuery
     * @param parentId
     * @return
     */
    public List<TableColumn> findTableColumns(String missionId,String isForQuery,String parentId);
}
