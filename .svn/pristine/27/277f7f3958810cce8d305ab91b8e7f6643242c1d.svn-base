/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.projectflow.service;

import java.util.List;
import java.util.Map;
import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 工程建设项目流程管理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ProjectFlowService extends BaseService {
    /**
     * 
     * 描述：查询列表信息
     * 
     * @author Rider Chen
     * @created 2019年6月11日 上午9:43:31
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);
    
    /**
     * 
     * 描述：查询审批流程类型管理下拉框数据
     * 
     * @author Scolder Lin
     * @created 2020年10月14日 上午11:20:31
     * @return
     */
    public List<Map<String, Object>> findConfigType();
    /**
     * 根据项目ID查询实例ID集合
     * @param projectId
     * @return
     */
    public List<Map<String, Object>> findExeIdListByProjectId(String projectId);
    /**
     * 根据工程建设项目基本信息表ID获取项目共性材料信息
     * 
     * @author Scolder Lin
     * @param entityId
     * @return
     */
    public List<Map<String, Object>> findGeneralityMaterList(String entityId);
    /**
     * 根据工程建设项目基本信息表ID获取电子证照材料信息
     * 
     * @author Scolder Lin
     * @param entityId
     * @return
     */
    public List<Map<String, Object>> findElectronicMaterList(String entityId);
    /**
     * 根据工程建设项目基本信息表ID获取申报材料信息
     * 
     * @author Scolder Lin
     * @param entityId
     * @return
     */
    public List<Map<String, Object>> findApplyMaterList(String entityId);
    /**
     * 根据工程建设项目基本信息表ID获取流转公文材料信息
     * 
     * @author Scolder Lin
     * @param entityId
     * @return
     */
    public List<Map<String, Object>> findFlowMaterList(String entityId);
}
