/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 投资项目申报操作dao(前端)
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年6月19日 上午17:48:12
 */
@SuppressWarnings("rawtypes")
public interface ProjectWebsiteApplyDao extends BaseDao {
    public List<Map<String, Object>> findProjectList(String textValue,
            String projectName, String projectCode);
    
    public Map<String, Object> findProjectMap(String page, String rows, 
            String textValue, String projectName, String projectCode);
    
    public List<Map<String,Object>> findMaterListByProjectCode(String projectCode, String type);
    
    public List<Map<String,Object>> findSgxkMaterListByProjectCode(String projectCode);
    
    public List<Map<String,Object>> findMaterList(String categoryId, String projectCode);
    
    public Map<String, Object> findExecuteInfo(String itemCode, String projectCode);
    
    public String findMaxTokenSortNumber();
    
    public Map<String,Object> findStageInfo(String exeId, String projectCode);
}
