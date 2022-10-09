/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.hflow.service.FlowFormService;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月7日 下午2:05:23
 */
public class FlowFormTestCase extends BaseTestCase {
    /**
     * 
     */
    @Resource
    private FlowFormService flowFormService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月7日 下午2:06:02
     */
    @Test
    public void save(){
        Map<String,Object> flowForm = new HashMap<String,Object>();
        flowForm.put("TYPE_ID","2c9084a64efcd10b014efcd2de7b0001");
        flowForm.put("FORM_NAME","测试表单");
        flowForm.put("FORM_KEY", "TestForm");
        flowForm.put("FORM_TYPE", "1");
        flowForm.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        flowFormService.saveOrUpdate(flowForm,"JBPM6_FLOWFORM",null);
    }
    
}
