/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.hflow.dao.FlowTaskDao;
import net.evecom.platform.hflow.service.FlowTaskService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月24日 上午8:50:15
 */
public class FlowTaskTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowTaskTestCase.class);
    /**
     * 
     */
    @Resource
    private FlowTaskDao flowTaskDao;
    /**
     * 
     */
    @Resource
    private FlowTaskService flowTaskService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 上午8:51:00
     */
    @Test
    public void isAssignedTeamTask(){
        boolean isAssign = flowTaskDao.isAssignedTeamTask("44","财务报备","lisi");
        log.info(isAssign);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 上午9:00:55
     */
    @Test
    public void isAssignedUserTask(){
        boolean isAssign = flowTaskDao.isAssignedUserTask("44","财务报备","lisi");
        log.info(isAssign);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月24日 上午10:25:08
     */
    @Test
    public void isAllowAssignJoinNodeTask(){
        boolean result = flowTaskDao.isAllowAssignJoinNodeTask("402883484f5d7fee014f5d8323660007",
                "402883484f5d7fee014f5d8323630006","46","人力审批,上级审批");
        log.info(result);
                
    }
    
    /***
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年12月3日 下午12:17:31
     */
    @Test
    public void findWebSiteUserTask(){
        List<Map<String,Object>> list = flowTaskService.findWebSiteUserTask("lixiaoyao");
        log.info(list.size());
    }
}
