/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.HistDeployService;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月25日 上午10:42:53
 */
public class HistDeployTestCase extends BaseTestCase {
    /**
     * histDeployService
     */
    @Resource
    private HistDeployService histDeployService;
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月25日 上午10:54:26
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetXml(){
        Map<String,Object> deploy = histDeployService.getByJdbc("JBPM6_HIST_DEPLOY",
                new String[]{"DEF_ID","DEF_VERSION"},new Object[]{"402883484f62aa6e014f62b10a8a003d",1});
        Set<String> nodes =new HashSet<String>();
        nodes.add("财务审批");
        nodes.add("人力审批");
        String defXml = (String) deploy.get("DEF_XML");
        //log.info(defXml);
        //flowDefService.genFlowImgToDisk(defXml, nodes, "d:/1.png");
    }
}
