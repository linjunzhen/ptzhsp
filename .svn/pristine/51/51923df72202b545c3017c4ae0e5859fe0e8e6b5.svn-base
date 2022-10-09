/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * 描述 投资项目申报操作service(前端)
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年6月19日 上午17:46:12
 */
@SuppressWarnings("rawtypes")
public interface ProjectWebsiteApplyService extends BaseService {
    /**
     * 根据首页查询框的值，查询项目列表
     * 
     * @param textValue
     * @param projectName
     * @param projectCode
     * @return
     */
    public List<Map<String, Object>> findProjectList(
            String textValue, String projectName, String projectCode);
    /**
     * 查询首页项目信息（翻页）
     * 
     * @param page
     * @param rows
     * @param textValue
     * @param projectName
     * @param projectCode
     * @return
     */
    public Map<String, Object> findProjectMap(String page, String rows, 
            String textValue, String projectName, String projectCode);
    /**
     * 根据项目编号，获取该项目材料列表信息
     * @param projectCode
     * @param type
     * @return
     */
    public List<Map<String,Object>> findMaterListByProjectCode(String projectCode, String type);
    
    /**
     * 根据项目编号，获取该项目施工许可电子证照材料列表信息
     * @param projectCode
     * @return
     */
    public List<Map<String,Object>> findSgxkMaterListByProjectCode(String projectCode);
    /**
     * 根据项目编号和阶段编号，获取该项目材料列表信息
     * @param categoryId
     * @param projectCode
     * @return
     */
    public List<Map<String,Object>> findMaterList(String categoryId, String projectCode);
    /**
     * 查询流程实例信息
     * @param itemCode
     * @param projectCode
     * @return
     */
    public Map<String, Object> findExecuteInfo(String itemCode, String projectCode);
    /**
     * 删除文件
     * @param fileId
     */
    public void removeUploadFile(String fileId);
    /**
     * 查询最大的token值
     * @return
     */
    public String findMaxTokenSortNumber();
    /**
     * 查询集成流程阶段信息
     * @param exeId
     * @param projectCode
     * @return
     */
    public Map<String,Object> findStageInfo(String exeId, String projectCode);

    /**
     * 描述:检查工程建设项目行业是否存在
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/11/27 10:23:00
     */
    public Map<String,Object> checkProjectIndustry(String exeId, Map<String, Object> execution);
}
