/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import net.evecom.core.util.FileUtil;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FromTask;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月21日 下午5:24:58
 */
public class FromTaskJsonTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FromTaskJsonTestCase.class);
    /**
     * 描述
     * @author Flex Hu
     * @created 2015年8月21日 下午5:24:58
     * @param args
     */
    public static void main(String[] args) {
        String json = FileUtil.getContentOfFile("d:/1.txt");
        Map<String,Object> map = JSON.parseObject(json,Map.class);
        log.info(map.keySet().toArray()[0]);
    }

}
