/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.project.dao.ProjectWebsiteApplyDao;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;

/**
 * 描述 投资项目申报操作service(前端)
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年6月19日 上午17:47:12
 */

@SuppressWarnings("rawtypes")
@Service("projectWebsiteApplyService")
public class ProjectWebsiteApplyServiceImpl extends BaseServiceImpl implements ProjectWebsiteApplyService{
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectWebsiteApplyDao dao;
    
    /**
     * 覆盖获取实体dao方法 描述
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    public List<Map<String, Object>> findProjectList(
            String textValue, String projectName, String projectCode) {
        return dao.findProjectList(textValue, projectName, projectCode);
    }

    public Map<String, Object> findProjectMap(String page, String rows,
            String textValue, String projectName,String projectCode) {
        return dao.findProjectMap(page, rows, textValue, projectName, projectCode);
    }

    public List<Map<String, Object>> findMaterListByProjectCode(String projectCode, String type) {
        return dao.findMaterListByProjectCode(projectCode, type);
    }
    
    public List<Map<String, Object>> findSgxkMaterListByProjectCode(String projectCode) {
        return dao.findSgxkMaterListByProjectCode(projectCode);
    }    
    
    public List<Map<String, Object>> findMaterList(String categoryId, String projectCode) {
        return dao.findMaterList(categoryId, projectCode);
    }

    public Map<String, Object> findExecuteInfo(String itemCode, String projectCode) {
        return dao.findExecuteInfo(itemCode, projectCode);
    }
    
    public void removeUploadFile(String fileId) {
        dao.remove("T_MSJW_PROJECT_FILE", "FILE_ID", new Object[]{fileId});
    }
    
    public String findMaxTokenSortNumber() {
        return dao.findMaxTokenSortNumber();
    }

    public Map<String, Object> findStageInfo(String exeId, String projectCode) {
        return dao.findStageInfo(exeId, projectCode);
    }

    /**
     * 描述:检查工程建设项目行业是否存在
     *
     * @author Madison You
     * @created 2019/11/27 10:24:00
     * @param
     * @return
     */
    @Override
    public Map<String,Object> checkProjectIndustry(String exeId,Map<String,Object> execution) {
        Map<String, Object> executionMap = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"}, new Object[]{exeId});
        String projectCode = (String)executionMap.get("PROJECT_CODE");
        String busRecordId = (String)executionMap.get("BUS_RECORDID");
        String busTableName = (String)executionMap.get("BUS_TABLENAME");
        if (projectCode != null && busRecordId != null && busTableName != null) {
           String primaryKeyName = (String)dao.getPrimaryKeyName(busTableName).get(0);
            if (primaryKeyName != null) {
                Map<String, Object> busRecordMap = dao.getByJdbc(busTableName,
                        new String[]{primaryKeyName}, new Object[]{busRecordId});
                String industryStructure = (String)busRecordMap.get("INDUSTRY_STRUCTURE");
                if (industryStructure == null) {
                    Map<String, Object> projectMap = dao.getByJdbc("SPGL_XMJBXXB",
                            new String[]{"PROJECT_CODE"}, new Object[]{projectCode});
                    if (projectMap != null && projectMap.get("INDUSTRY_STRUCTURE") != null) {
                        HashMap<String, Object> variables = new HashMap<>();
                        variables.put("INDUSTRY_STRUCTURE", projectMap.get("INDUSTRY_STRUCTURE"));
                        dao.saveOrUpdate(variables, busTableName, busRecordId);
                        execution.put("INDUSTRY_STRUCTURE", projectMap.get("INDUSTRY_STRUCTURE"));
                    }
                }
            }
        }
        return execution;
    }
}
