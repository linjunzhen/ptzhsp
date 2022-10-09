/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.project.dao.ProjectXFDao;
import net.evecom.platform.project.service.ProjectQtXfService;

/**
 * 描述 其他建设工程备案抽查ServiceImpl
 * @author Keravon Feng
 * @created 2021年11月26日 上午10:16:35
 */
/**
 * 描述
 * @author Keravon Feng
 * @created 2021年11月26日 上午10:21:47
 */
@Service("projectQtXfService")
public class ProjectQtXfServiceImpl extends BaseServiceImpl implements ProjectQtXfService {

    /**
     * 所引入的dao
     */
    @Resource
    private ProjectXFDao dao;
    
    /**
     * 描述 getEntityDao
     * @author Keravon Feng
     * @created 2021年11月26日 上午10:18:39
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述 受理是否通过
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:02:40
     * @param flowVars
     * @return
     */
    public Set<String> getIsAcceptPass(Map<String, Object> flowVars){
        String GSSFTG = (String) flowVars.get("SLSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("0")) {
            resultNodes.add("开始");
        } else {            
            resultNodes.add("抽查");
        }
        return resultNodes;
    }

    /**
     * 描述 是否需要检查决策事件
     * @author Keravon Feng
     * @created 2021年11月26日 上午10:21:56
     * @param flowVars
     * @return
     */
    @Override
    public Set<String> getIsCheckPass(Map<String, Object> flowVars) {
        String ISCHECK = (String) flowVars.get("ISCHECK");
        Set<String> resultNodes = new HashSet<String>();
        if (ISCHECK.equals("0")) {
            resultNodes.add("初审");
        } else {            
            resultNodes.add("专家抽取");
        }
        return resultNodes;
    }

    /**
     * 描述 结论是否通过决策事件
     * @author Keravon Feng
     * @created 2021年11月26日 上午10:31:39
     * @param flowVars
     * @return
     */
    @Override
    public Set<String> getIsCheckResultPass(Map<String, Object> flowVars) {
        String YSSFHG = (String) flowVars.get("YSSFHG");
        Set<String> resultNodes = new HashSet<String>();
        if (YSSFHG.equals("0")) {
            resultNodes.add("开始");
        } else {            
            resultNodes.add("结束");
        }
        return resultNodes;
    }
    
    
    
    

}
