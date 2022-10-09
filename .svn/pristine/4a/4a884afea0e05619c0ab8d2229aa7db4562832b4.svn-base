/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tzxm.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
/**
 * @author Scolder Lin
 *
 */
@SuppressWarnings("rawtypes")
public interface GovIvestService extends BaseService{
    /**
     * 获取审批平台投资列表
     * 
     * @return
     */
    public List<Map<String, Object>> getGovIvestTopList();
    /**
     * 根据ID获取投资标题列表
     * 
     * @param id
     * @return
     */
    public List<Map<String, Object>> getTitleListById(String id);
    /**
     * 根据ID获取集成流程配置ID的下级列表
     * 
     * @param categoryId
     * @return
     */
    public List<Map<String, Object>> findCategoryChildList(String categoryId);
    /**
     * 根据ID获取事项列表
     * 
     * @param configLinkId
     * @return
     */
    public List<Map<String, Object>> findItemList(String configLinkId);
    /**
     * 办理结果公示列表信息
     * 
     * @param textValue 搜索栏提交的值
     * @return
     */
    public List<Map<String, Object>> findHandleResultList(String textValue);
    /**
     * 办理结果公示列表信息(翻页)
     * @param page
     * @param rows
     * @param textValue
     * @return
     */
    public Map<String, Object> findHandleResultMap(String page, String rows, String textValue);
    /**
     * 根据栏目名称以及父级栏目名称获取栏目ID
     * 
     * @param muduleName
     * @param parentName
     * @return
     */
    public String findPoliciesRegulationsModuleId(String muduleName, String parentName);
    /**
     * 查询栏目列表
     * 
     * @param moduleId
     * @return
     */
    public List<Map<String, Object>> findMenuListByModuleId(String moduleId);
    /**
     * 查询栏目列表数据信息
     * 
     * @param page
     * @param rows
     * @param moduleId
     * @return
     */
    public Map<String, Object> findModuleInfo(String page, String rows, String moduleId);
}
