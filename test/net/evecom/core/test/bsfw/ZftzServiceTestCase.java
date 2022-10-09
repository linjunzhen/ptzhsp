/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.bsfw;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.service.ZftzService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年2月17日 下午4:25:37
 */
public class ZftzServiceTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ZftzServiceTestCase.class);
    /**
     * 
     */
    @Resource
    private ZftzService zftzService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年2月17日 下午4:26:19
     */
    @Test
    public void findNeedAutoJump(){
        List<Map<String,Object>> list = zftzService.findNeedAutoJump();
        for(Map<String,Object> data:list){
            try {
                zftzService.jumpTaskForWzgs(data);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
        //log.info(list.size());
    }
}
