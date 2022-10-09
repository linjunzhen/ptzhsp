/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.service.NodeConfigService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月26日 下午3:27:51
 */
public class JbpmServiceTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(JbpmServiceTestCase.class);
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
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
     * @created 2015年8月26日 下午4:16:55
     */
    @Test
    public void testGetStepJson(){
        String defId = "402883484f62aa6e014f62acc4480004";
        String nodeName = "开始";
        int flowVersion = 1;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月26日 下午3:10:49
     */
    @Test
    public void getNodeNameForProvinceStart(){
        String nodeName = nodeConfigService.
                getNodeNameForProvinceStart("402881a6508e830c01508e920faa001c", 13);
        log.info("nodename:"+nodeName);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月26日 上午11:14:09
     */
    @Test
    public void testDoFlow(){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        flowVars.put("EFLOW_CREATORID","402883524863ab1f014863ab1f340000");
        flowVars.put("EFLOW_CREATORACCOUNT","admin");
        flowVars.put("EFLOW_CREATORNAME","超级管理员");
        flowVars.put("EFLOW_DEFKEY","002");
        flowVars.put("EFLOW_BUSTABLENAME","T_BSFW_SHTZXMGH");
        flowVars.put("EFLOW_DEFID","402881a6508e830c01508e920faa001c");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES","网上申请");
        flowVars.put("EFLOW_CURUSEROPERNODENAME","网上申请");
        flowVars.put("EFLOW_DEFVERSION",13);
        flowVars.put("EFLOW_ISQUERYDETAIL","false");
        flowVars.put("EFLOW_ISTEMPSAVE","-1");
        //分配外部的流程实例ID
        flowVars.put("EFLOW_ASSIGNED_EXEID","kk");
        flowVars.put("EFLOW_CREATETIME","2014-10-13 12:33:33");
        
        flowVars.put("BSYHLX","1");
        flowVars.put("CKSJRYXM","超级管理员");
        flowVars.put("DE_AREA_NAME","测试开发区");
        flowVars.put("DIVISION_CODE","350602");
        flowVars.put("ENTERPRISE_NAME","漳州市建筑工程有限公司");
        flowVars.put("GCLX","1");
        flowVars.put("INDUSTRY","B06");
        flowVars.put("IS_DE_AREA","1");
        flowVars.put("ITEM_CODE","350128XK00102");
        flowVars.put("ITEM_NAME","社会投资项目规划选址初步对接意见");
        flowVars.put("JBR_MOBILE","13452031733");
        flowVars.put("JBR_NAME","张三");
        flowVars.put("JBR_ZJHM","11111");
        flowVars.put("LEREP_CERTNO","156500845");
        flowVars.put("LEREP_CERTTYPE","A05201");
        flowVars.put("PLACE_CODE","350602");
        flowVars.put("PLACE_CODE_DETAIL","350602");
        flowVars.put("PROJECTCODE","2015-350602-47-03-006807");
        flowVars.put("PROJECT_NAME","瑞京路立体停车楼");
        flowVars.put("PROJECT_NATURE","0");
        flowVars.put("PROJECT_TYPE","A00003");
        flowVars.put("SCALE_CONTENT","建设1幢立体停车楼，停车位202个。");
        //收取方式
        flowVars.put("SQFS","2");
        flowVars.put("SQRMC","张三");
        flowVars.put("SQRSFZ","111111");
        flowVars.put("SQRSJH","13452031733");
        flowVars.put("SSBMBM","345071904");
        
        String nextStepJson = this.jbpmService.getNextStepsJson("402881a6508e830c01508e920faa001c", 
                13,"网上预审", flowVars);
        flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        try {
            jbpmService.doFlowJob(flowVars);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
    }
    
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月26日 上午10:10:01
     */
    @Test
    public void startFlow2(){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        flowVars.put("EFLOW_CREATORID","402883524863ab1f014863ab1f340000");
        flowVars.put("EFLOW_CREATORACCOUNT","admin");
        flowVars.put("EFLOW_CREATORNAME","超级管理员");
        flowVars.put("EFLOW_DEFKEY","002");
        flowVars.put("EFLOW_BUSTABLENAME","T_BSFW_SHTZXMGH");
        flowVars.put("EFLOW_DEFID","402881a6508e830c01508e920faa001c");
        flowVars.put("NTZXMC","省网办对接项目");
        try {
            jbpmService.exeFlow(flowVars);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月26日 下午3:28:51
     */
    @Test
    public void startFlow(){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        flowVars.put("EFLOW_CREATORID","402883524863ab1f014863ab1f340000");
        flowVars.put("EFLOW_CREATORACCOUNT","admin");
        flowVars.put("EFLOW_CREATORNAME","超级管理员");
        flowVars.put("EFLOW_DEFKEY","CxlcFlow");
        flowVars.put("BEGIN_TIME","2015-08-10 00:00:00");
        flowVars.put("END_TIME","2015-08-19 00:00:00");
        flowVars.put("KDFS","1,2");
        flowVars.put("LEAVE_DATE","2015-08-18");
        flowVars.put("LEAVE_REASON","fs");
        flowVars.put("LEAVE_TYPE","3");
        flowVars.put("PERSON_NAME","张三峰");
        flowVars.put("SEX","1");
        /*String nextStepJson = FileUtil.getContentOfFile("d:/nextstep.json");
        flowVars.put("EFLOW_NEXTSTEPSJSON",nextStepJson);*/
        try {
            jbpmService.exeFlow(flowVars);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年2月17日 下午4:37:36
     */
    @Test
    public void getNextStepsJson(){
        Map<String,Object> flowVars = new HashMap<String,Object>();
        String defId = "2c9d6e85510f1b9e01510f35c0910013";
        int flowVersion = 13;
        String swbStartNodeName = "网站公示";
        flowVars.put("EFLOW_DEFKEY","zhtz0301");
        flowVars.put("EFLOW_DEFID",defId);
        flowVars.put("EFLOW_DEFVERSION",flowVersion);
        flowVars.put("EFLOW_ISQUERYDETAIL","false");
        flowVars.put("EFLOW_ISTEMPSAVE","-1");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES",swbStartNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME",swbStartNodeName);
        flowVars.put("ITEM_CODE","350128XK00204");
        String nextStepJson = this.jbpmService.getNextStepsJson(defId, 
                flowVersion,swbStartNodeName, flowVars);
        if(StringUtils.isNotEmpty(nextStepJson)){
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        }
        log.info("next:"+nextStepJson);
    }
}
