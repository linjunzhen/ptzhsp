/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.bsfw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.service.SwbProvAttrService;
import net.evecom.platform.bsfw.service.SwbProvDataService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.util.Jbpm6Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月26日 下午3:43:21
 */
public class SwbProvDataTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbProvDataTestCase.class);
    /**
     * swbProvDataService
     */
    @Resource
    private SwbProvDataService swbProvDataService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * 
     */
    @Resource
    private SwbProvAttrService swbProvAttrService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月27日 上午11:19:30
     */
    @Test
    public void genFileToDisk(){
        swbProvAttrService.genFileToDiskAndSave("599CD6594D20E1A7722AAD4E044CD09E",null);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月26日 下午3:44:00
     */
    @Test
    public void findNeedToStart(){
        List<Map<String,Object>> swbDatas = swbProvDataService.findNeedToStart();
        for(Map<String,Object> swbData:swbDatas){
            try {
                swbProvDataService.startSwbFlow(swbData);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }
}
