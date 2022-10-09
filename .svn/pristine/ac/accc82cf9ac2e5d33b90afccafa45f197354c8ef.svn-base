/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.platform.hflow.dao.QueryButtonAuthDao;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.QueryButtonAuthService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月12日 上午10:08:59
 */
public class QueryButtonAuthTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(QueryButtonAuthTestCase.class);
    /**
     * queryButtonAuthService
     */
    @Resource
    private QueryButtonAuthService queryButtonAuthService;
    /**
     * queryButtonAuthDao
     */
    @Resource
    private QueryButtonAuthDao queryButtonAuthDao;
    
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    
    @Test
    public void testGet(){
        String defId = "402881a6508e830c01508e920faa001c";
        String buttonKey = "SubmitFlow";
        int flowVersion= 14;
      //获取权限配置信息
        Map<String,Object> queryButtonAuth = queryButtonAuthService.getByJdbc("JBPM6_QUERYBUTTONAUTH",
                new String[]{"DEF_ID","BUTTON_KEY","FLOW_VERSION"},
                new Object[]{defId,buttonKey,flowVersion});
        if(queryButtonAuth==null){
            log.info("dddno");
        }else{
            log.info("yes");
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月14日 上午9:51:24
     */
    @Test
    public void initQueryButton(){
        List<Map<String,Object>> flowDefList = flowDefService.findAllByTableName("JBPM6_FLOWDEF");
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_QUERYBUTTONAUTH J");
        sql.append(" SET J.IS_AUTH=? WHERE J.DEF_ID=? AND J.BUTTON_KEY=? AND J.FLOW_VERSION=?");
        for(Map<String,Object> flowDef:flowDefList){
            String defId = (String) flowDef.get("DEF_ID");
            int flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            queryButtonAuthDao.executeSql(sql.toString(), new Object[]{1,defId,"PrintTemplate",flowVersion});
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月14日 上午10:02:42
     */
    @Test
    public void initButtonRight(){
        List<Map<String,Object>> flowDefList = flowDefService.findAllByTableName("JBPM6_FLOWDEF");
        for(Map<String,Object> flowDef:flowDefList){
            String defId = (String) flowDef.get("DEF_ID");
            int flowVersion = Integer.parseInt(flowDef.get("VERSION").toString());
            List<String> taskNodeNames = flowNodeService.findTaskNodeNames(defId, flowVersion);
            for(String taskName:taskNodeNames){
                Map<String,Object> buttonRight = new HashMap<String,Object>();
                buttonRight.put("DEF_ID", defId);
                buttonRight.put("NODE_NAME", taskName);
                buttonRight.put("BUTTON_KEY","PrintTemplate");
                buttonRight.put("IS_AUTH",1);
                buttonRight.put("DEF_VERSION",flowVersion);
                flowNodeService.saveOrUpdate(buttonRight, "JBPM6_BUTTONRIGHT",null);
            }
        }
    }
}
