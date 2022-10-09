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
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.NodeConfigService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月15日 上午7:14:53
 */
public class FlowNodeTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowNodeTestCase.class);
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private NodeConfigService nodeConfigService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月15日 上午7:15:28
     */
    @Test
    public void getKey(){
        int key = flowNodeService.getKey("财务报备","2c9084a64f05b347014f05b3fa8c0001",21);
        log.info("key:"+key);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月15日 下午12:55:12
     */
    @Test
    public void findByToNodes(){
        List<Map<String,Object>> findToNodes = flowNodeService.
                findByToNodes(null, -2,"2c9084a64f05b347014f05b3fa8c0001", 21);
        for(Map<String,Object> node:findToNodes){
            log.info(node.get("NODE_NAME"));
        }
    }
    
    @Test
    public void findNextTaskNodesForSelect(){
        List<Map<String,Object>> list = flowNodeService.
                findNextTaskNodesForSelect("402881b7511816cd0151181f36480001,竣工财务决算意见");
        log.info(list.size());
    }
    
    @Test
    public void test(){
        String nodeName ="拟核准《工程可行性研究报告+》";
        Map<String,Object> nodeConfig =nodeConfigService.getByJdbc("JBPM6_NODECONFIG",
                new String[]{"NODE_NAME","DEF_ID","DEF_VERSION"},new Object[]{"拟核准《工程可行性研究报告+》",
                    "2c9d6e84510f45ba01510f539ecb0008","8"});
        log.info(nodeConfig.toString());
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年12月22日 上午10:25:56
     */
    @Test
    public void findTaskNodeNames(){
        String defId = "4028819c51a32abf0151a3520e350069";
        int flowVersion = 5;
        List<String> nodeNames = flowNodeService.findTaskNodeNames(defId, flowVersion);
        log.info(nodeNames.toString());
    }
}
