/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.yb.dao.YbCxjmcbxbDao;
import net.evecom.platform.yb.service.YbCxjmcbxbService;

/**
 * 描述  城乡居民登记业务操作service
 * @author Allin Lin
 * @created 2019年10月29日 下午2:40:37
 */
@Service("ybCxjmcbxbService")
public class YbCxjmcbxbServiceImpl extends BaseServiceImpl implements YbCxjmcbxbService{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbCxjmcbxbServiceImpl.class);
    
    
    /**
     * 引入的dao
     */
    @Resource
    private YbCxjmcbxbDao dao;
    
    /**
     * 描述 覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2019年10月22日 上午9:32:02
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述 城乡居民登记业务-决策事件
     * @author Allin Lin
     * @created 2019年10月29日 下午2:57:42
     * @param flowVars
     * @return
     * @see net.evecom.platform.yb.service.YbCxjmcbxbService#getCoodResultA(java.util.Map)
     */
    public Set<String> getTypeResult(Map<String, Object> flowVars) {
        String cxjmType = (String) flowVars.get("CXJM_TYPE");
        Set<String> resultNodes = new HashSet<String>();
        if (cxjmType.equals("1")) {
            resultNodes.add("审查（新参保）");
        } else if(cxjmType.equals("2")){
            resultNodes.add("审查（续保）");
        }else if(cxjmType.equals("3")){
            resultNodes.add("审查（停保）");
        }
        return resultNodes;
    }
}
