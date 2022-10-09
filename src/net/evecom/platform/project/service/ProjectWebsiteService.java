/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import java.util.List;
import java.util.Map;

/**
 * 
 * 描述
 * @author Adrian Bian
 * @created 2020年1月6日 下午12:14:49
 */
public interface ProjectWebsiteService extends BaseService {
    /**
     * 查找文章列表分页
     * @param variable
     * @return
     */
    public List<Map<String,Object>> findContentForPage(Map<String,Object> variable);
    /**
     * 根据项目类型id获取第一个阶段id
     * @param typeId
     * @return
     */
    public String getFirstStageIdByTypeId(String typeId);
    /**
     * 根据阶段id获取事项信息
     * @param stageId
     * @return
     */
    public Map<String,Object> getServiceItemByStageId(String stageId);
    /**
     * 根据项目主键id获取项目信息
     * @param id
     * @return
     */
    public Map<String,Object> getProjectInfosById(String id);
    /**
     * 根据项目projectCode获取项目信息
     * @param projectCode
     * @return
     */
    public Map<String,Object> loadLocalXMJBXXBByProjectCode(String projectCode);
    /**
     * 查找当前登录账号的所有项目
     * @param variable
     * @return
     */
    public List<Map<String,Object>> findMyProjectList(Map<String,Object> variable);

    /**
     * 根据字典类别获取字典名称
     * @param dicTypeCode
     * @return
     */
    public String getDicTypeName(String dicTypeCode);
    /**
     * 查找登录账号的项目信息
     * @param variable
     * @return
     */
    public List<Map<String,Object>> findMyProjectInfo(Map<String,Object> variable);
    /**
     * 根据阶段id和主题编号获取需要办理的事项
     * @return
     */
    public List<Map<String,Object>> findServiceItemByStageIdAndTopicCode(Map<String,Object> variable);
    }
