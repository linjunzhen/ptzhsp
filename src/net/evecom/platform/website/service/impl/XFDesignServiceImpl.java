/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.website.dao.XFDesignDao;
import net.evecom.platform.website.service.XFDesignService;
/**
 * 描述 消防设计服务层
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月9日 上午11:23:10
 */
@Service("xfDesignService")
@SuppressWarnings("rawtypes")
public class XFDesignServiceImpl extends BaseServiceImpl implements XFDesignService {
    /**
     * 所引入的dao
     */
    @Resource
    private XFDesignDao dao;
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @Override
    public List<Map<String, Object>> findZrztxxList(String prj_code, String prj_num) {
        return dao.findZrztxxList(prj_code, prj_num);
    }
    
    @Override
    public List<Map<String, Object>> findDtjzwxxList(String prj_code, String prj_num) {
        return dao.findDtjzwxxList(prj_code, prj_num);
    }

    @Override
    public String getPrjNum(String prjCode) {
        return dao.getPrjNum(prjCode);
    }

    @Override
    public Map<String, Object> findXfProjectInfo(String projectCode, String prjNum) {
        return dao.findXfProjectInfo(projectCode, prjNum);
    }
}
