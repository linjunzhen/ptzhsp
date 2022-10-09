/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.bsfw;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月12日 下午2:49:00
 */
public class DataAbutLogTestCase extends BaseTestCase {
    
    /**
     * 引入Service
     */
    @Resource
    private DataAbutLogService dataAbutLogService;

    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年10月12日 下午2:49:30
     */
    @Test
    public void testSave(){
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("ABUT_CODE", "1111");
        map.put("ABUT_NAME", "测试同步接口");
        map.put("ABUT_DESC", "测试同步接口");
        map.put("ABUT_SENDDATA", "测试同步接口");
        map.put("ABUT_RECEDATA", "测试同步接口");
        map.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("ABUT_RESULT", "1");
        map.put("ERROR_LOG", "失败日志");
        dataAbutLogService.saveOrUpdate(map, "T_BSFW_DATAABUTLOG",null);
    }
}
