/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.yb.dao.YbYrdwbgDao;
import net.evecom.platform.yb.service.YbYrdwbgService;

/**
 * 描述   用人单位基本医疗保险、生育保险信息变更登记业务service
 * @author Allin Lin
 * @created 2019年11月13日 下午5:07:31
 */
@Service("ybYrdwbgService")
public class YbYrdwbgServiceImpl extends BaseServiceImpl implements YbYrdwbgService{
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbYrdwbgServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private YbYrdwbgDao dao;
    
    
    /**  
     * 描述  覆盖dao的实体方法
     * @author Allin Lin
     * @created 2019年11月13日 下午5:10:05
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

}
