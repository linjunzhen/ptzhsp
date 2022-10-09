/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tzxm.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;
/**
 * @author Scolder Lin
 *
 */
@SuppressWarnings("rawtypes")
public interface GovIvestDao extends BaseDao{
    public List<Map<String, Object>> getGovIvestTopList();
    
    public List<Map<String, Object>> getTitleListById(String id);
    
    public List<Map<String, Object>> findCategoryChildList(String categoryId);
    
    public List<Map<String, Object>> findItemList(String configLinkId);
    
    public List<Map<String, Object>> findHandleResultList(String textValue);
    
    public Map<String, Object> findHandleResultMap(String page, String rows, String textValue);
    
    public String findPoliciesRegulationsModuleId(String muduleName, String parentId);
    
    public List<Map<String, Object>> findMenuListByModuleId(String moduleId);
    
    public Map<String, Object> findModuleInfo(String page, String rows, String moduleId);
}
