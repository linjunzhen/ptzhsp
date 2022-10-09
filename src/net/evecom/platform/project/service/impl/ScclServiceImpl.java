/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.project.dao.ScclDao;
import net.evecom.platform.project.service.ScclService;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.service.FlowTaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 生成材料
 * 描述
 * @author Adrian Bian
 * @created 2020年1月6日 下午12:15:43
 */
@Service("scclService")
public class ScclServiceImpl extends BaseServiceImpl implements ScclService {

    /**
     * 所引入的dao
     */
    @Resource
    private ScclDao dao;

    /**
     * 覆盖获取实体dao方法
     * 描述
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }


}
