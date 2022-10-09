/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.bsfw;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.service.EsuperResService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年5月4日 下午4:54:36
 */
public class EsuperResTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EsuperResTestCase.class);
    /**
     * 
     */
    @Resource
    private EsuperResService esuperResService;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 
     */
    @Test
    public void getProcessId(){
        log.info(esuperResService.getProcessId("审核","00007GF0020111"));
    }
    /**
     * 
     */
    @Test
    public void testFind(){
        Map<String,Object> dbUrlMap = dictionaryService.get("ExDataDbUrl","1");
        Map<String,Object> userMap = dictionaryService.get("ExDataDbUrl","2");
        Map<String,Object> passMap = dictionaryService.get("ExDataDbUrl","3");
        String dbUrl = dbUrlMap.get("DIC_DESC").toString();
        String username = userMap.get("DIC_DESC").toString();
        String password = passMap.get("DIC_DESC").toString();
        Connection conn;
        try {
            conn = DbUtil.getConnect(dbUrl, username, password);
            Map<String,Object> depart = new HashMap<String,Object>();
            depart.put("DEPNAME","我的测试部门33");
            depart.put("DEPLEVEL",1);
            String recordId = DbUtil.saveOrUpdate(conn, "T_SYSTEM_DEPARTMENT", depart,
                    "402848de5929a575015929a575bf0000",true);
            log.info(recordId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        
    }
    
}
