/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tzxm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.tzxm.dao.GovIvestDao;
import net.evecom.platform.tzxm.service.GovIvestService;
/**
 * @author Scolder Lin
 *
 */
@SuppressWarnings("rawtypes")
@Service("govIvestService")
public class GovIvestServiceImpl extends BaseServiceImpl implements GovIvestService{
    /**
     * 引入dao
     */
    @Resource
    private GovIvestDao dao;

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    public List<Map<String, Object>> getGovIvestTopList() {
        return dao.getGovIvestTopList();
    }

    public List<Map<String, Object>> getTitleListById(String id) {
        return dao.getTitleListById(id);
    }

    public List<Map<String, Object>> findCategoryChildList(String categoryId) {
        return dao.findCategoryChildList(categoryId);
    }

    public List<Map<String, Object>> findItemList(String configLinkId) {
        return dao.findItemList(configLinkId);
    }

    public List<Map<String, Object>> findHandleResultList(String textValue) {
        return dao.findHandleResultList(textValue);
    }

    public Map<String, Object> findHandleResultMap(String page, String rows, String textValue) {
        return dao.findHandleResultMap(page, rows, textValue);
    }

    public String findPoliciesRegulationsModuleId(String muduleName, String parentId) {
        return dao.findPoliciesRegulationsModuleId(muduleName, parentId);
    }

    public List<Map<String, Object>> findMenuListByModuleId(String moduleId) {
        return dao.findMenuListByModuleId(moduleId);
    }

    public Map<String, Object> findModuleInfo(String page, String rows, String moduleId) {
        return dao.findModuleInfo(page, rows, moduleId);
    }
    

}
