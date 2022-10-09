/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.call.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.call.service.CallService;
import net.evecom.platform.call.service.EstateWebService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 描述
 * 
 * @author Danto Huang
 * @created 2017年10月23日 下午3:55:44
 */
@Service("estateWebService")
public class EstateWebServiceImpl implements EstateWebService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EstateWebServiceImpl.class);
    /**
     * 引入serviceItemService
     */
    private ServiceItemService serviceItemService;
    /**
     * 引入flowNodeService
     */
    private FlowNodeService flowNodeService;
    /**
     * 引入jbpmService
     */
    private JbpmService jbpmService;
    /**
     * 引入callService
     */
    private CallService callService;

    /**
     * 
     * 描述 流程启动
     * 
     * @author Danto Huang
     * @created 2017年10月23日 下午4:03:57
     * @param flowInfo
     * @return
     */
    public String flowStart(String flowInfoJson) {
        log.info("++++++++++++接收到不动产流程发起请求++++++++++++");
        serviceItemService = (ServiceItemService) AppUtil.getBean("serviceItemService");
        flowNodeService = (FlowNodeService) AppUtil.getBean("flowNodeService");
        jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
        callService = (CallService) AppUtil.getBean("callService");
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            String bsyhlx =flowInfo.get("BSYHLX")==null?
                    "":flowInfo.get("BSYHLX").toString();
            String sqjgType =flowInfo.get("SQJG_TYPE")==null?
                    "":flowInfo.get("SQJG_TYPE").toString();
            String sqjgtype =flowInfo.get("SQRZJLX")==null?
                    "":flowInfo.get("SQRZJLX").toString();
            String sfxsjbrxx =flowInfo.get("SFXSJBRXX")==null?
                    "":flowInfo.get("SFXSJBRXX").toString();
            String jbrSex =flowInfo.get("JBR_SEX")==null?
                    "":flowInfo.get("JBR_SEX").toString();
            String jbrZjlx =flowInfo.get("JBR_ZJLX")==null?
                    "":flowInfo.get("JBR_ZJLX").toString();
            if (flowInfo.get("LINE_NO") == null || StringUtils.isEmpty((String) flowInfo.get("LINE_NO"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台排队号LINE_NO");
            } else if (flowInfo.get("LINE_DATE") == null || StringUtils.isEmpty((String) flowInfo.get("LINE_DATE"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台取号日期LINE_DATE");
//            } else if (flowInfo.get("EXECUTE_TIME") == null 
//                || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
//                result.put("resultCode", "003");
//                result.put("resultMsg", "缺失流程关键数据，办理时间EXECUTE_TIME");
            } else if (flowInfo.get("itemCode") == null || StringUtils.isEmpty((String) flowInfo.get("itemCode"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台事项编码itemCode");
            } else if (StringUtils.isNotEmpty(sfxsjbrxx)&&!"1".equals(sfxsjbrxx)) {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,SFXSJBRXX:" + sfxsjbrxx + "不符合数据规范");
            } else if (("1".equals(sfxsjbrxx)||"2".equals(bsyhlx))
                    &&!"1".equals(jbrSex)
                    &&!"2".equals(jbrSex)&&
                    (StringUtils.isEmpty(jbrZjlx)
                     ||flowInfo.get("JBR_NAME") == null
                     ||StringUtils.isEmpty(flowInfo.get("JBR_NAME").toString())
                     ||flowInfo.get("JBR_MOBILE") == null
                     ||StringUtils.isEmpty(flowInfo.get("JBR_MOBILE").toString())
                     ||flowInfo.get("JBR_ZJHM") == null
                     ||StringUtils.isEmpty(flowInfo.get("JBR_ZJHM").toString())  ) ) {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,经办人数据不符合数据规范");
            } else if (!"YYZZ".equals(jbrZjlx)
                    &&!"SF".equals(jbrZjlx)
                    &&!"JGDM".equals(jbrZjlx)
                    &&!"JG".equals(jbrZjlx)
                    &&!"SB".equals(jbrZjlx)
                    &&!"HZ".equals(jbrZjlx)
                    &&!"TWTX".equals(jbrZjlx)
                    &&!"HKSF".equals(jbrZjlx)
                    &&!"AMSF".equals(jbrZjlx)
                    &&!"TWSF".equals(jbrZjlx)
                    &&!"GATX".equals(jbrZjlx)
                    &&!"QT".equals(jbrZjlx)
                    &&!StringUtils.isEmpty(jbrZjlx)  ) {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,经办人数据不符合数据规范");
            } else if (!"1".equals(bsyhlx)&&!"2".equals(bsyhlx)) {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,BSYHLX:" + bsyhlx + "不符合数据规范");
            } else if ("2".equals(bsyhlx)
                    &&!"JG".equals(sqjgType)
                    &&!"SYDW".equals(sqjgType)
                    &&!"QY".equals(sqjgType)
                    &&!"ST".equals(sqjgType)
                    &&!"QT".equals(sqjgType)  ) {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,SQJG_TYPE:" + sqjgType + "不符合数据规范");
            } else if ("1".equals(bsyhlx)
                    &&!"YYZZ".equals(sqjgtype)
                    &&!"SF".equals(sqjgtype)
                    &&!"JGDM".equals(sqjgtype)
                    &&!"JG".equals(sqjgtype)
                    &&!"SB".equals(sqjgtype)
                    &&!"HZ".equals(sqjgtype)
                    &&!"TWTX".equals(sqjgtype)
                    &&!"HKSF".equals(sqjgtype)
                    &&!"AMSF".equals(sqjgtype)
                    &&!"TWSF".equals(sqjgtype)
                    &&!"GATX".equals(sqjgtype)
                    &&!"QT".equals(sqjgtype)  ) {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,SQRZJLX:" + sqjgtype + "不符合数据规范");
            } else if ("1".equals(bsyhlx)
                    &&(flowInfo.get("SQRZJLX") == null
                            ||StringUtils.isEmpty(flowInfo.get("SQRZJLX").toString()) 
                            ||flowInfo.get("SQRSFZ") == null
                            ||StringUtils.isEmpty(flowInfo.get("SQRSFZ").toString()) 
                            ||flowInfo.get("SQRSJH") == null
                            ||StringUtils.isEmpty(flowInfo.get("SQRSJH").toString()) 
                            ||flowInfo.get("SQRMC") == null
                            ||StringUtils.isEmpty(flowInfo.get("SQRMC").toString()) )) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，个人用户数据缺失");
            } else if ("2".equals(bsyhlx)
                    &&(flowInfo.get("SQJG_NAME") == null 
                            ||StringUtils.isEmpty(flowInfo.get("SQJG_NAME").toString())
                            ||flowInfo.get("SQJG_CODE") == null
                            ||StringUtils.isEmpty(flowInfo.get("SQJG_CODE").toString())
                            ||flowInfo.get("SQJG_TYPE") == null
                            ||StringUtils.isEmpty(flowInfo.get("SQJG_TYPE").toString()) )) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，企业用户数据缺失");
            } else {
                Map<String, Object> numRecord = callService.getRecordByNoAndDate((String) flowInfo.get("LINE_NO"),
                        (String) flowInfo.get("LINE_DATE"));
                String itemCode = flowInfo.get("itemCode").toString();
                Map<String, Object> itemInfo = serviceItemService.getItemAndDefInfo(itemCode);
                if (itemInfo == null) {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应事项");
                } else if (numRecord == null) {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,LINE_NO:" + flowInfo.get("LINE_NO") + ",LINE_DATE:"
                            + flowInfo.get("LINE_DATE") + ",在审批平台未查询到对应取号记录");
                } else if (numRecord.get("EXE_ID") != null && numRecord.get("EXE_ID") != "") {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,取号记录LINE_NO:" + flowInfo.get("LINE_NO") + ",LINE_DATE:"
                            + flowInfo.get("LINE_DATE") + ",在审批平台已存在对应办件流程");
                } else {
                    Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                            new String[] { "DEF_ID" }, new Object[] { itemInfo.get("BDLCDYID") });
                    if (flowInfo.get("BSYHLX") == null || StringUtils.isEmpty((String) flowInfo.get("BSYHLX"))) {
                        result.put("resultCode", "003");
                        result.put("resultMsg", "缺失流程关键数据，办事用户类型BSYHLX");
                    } else if (flowInfo.get("SBMC") == null || StringUtils.isEmpty((String) flowInfo.get("SBMC"))) {
                        result.put("resultCode", "003");
                        result.put("resultMsg", "缺失流程关键数据，申报流程名称SBMC");
                    } else if (flowInfo.get("EFLOW_CREATORACCOUNT") == null
                            || StringUtils.isEmpty((String) flowInfo.get("EFLOW_CREATORACCOUNT"))) {
                        result.put("resultCode", "003");
                        result.put("resultMsg", "缺失流程关键数据，审批平台收件用户账号EFLOW_CREATORACCOUNT");
                    } else if (flowInfo.get("EFLOW_CREATORNAME") == null
                            || StringUtils.isEmpty((String) flowInfo.get("EFLOW_CREATORNAME"))) {
                        result.put("resultCode", "003");
                        result.put("resultMsg", "缺失流程关键数据，审批平台收件用户姓名EFLOW_CREATORNAME");
                    } else {
                        Map<String, Object> user = jbpmService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                                new String[] { "USERNAME" }, new Object[] { flowInfo.get("EFLOW_CREATORACCOUNT") });
                        if (user == null || !user.get("FULLNAME").equals(flowInfo.get("EFLOW_CREATORNAME"))) {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误:用户账号："+flowInfo.get("EFLOW_CREATORACCOUNT")+
                                    "和用户姓名："+flowInfo.get("EFLOW_CREATORNAME")+"在审批平台无匹配用户");
                        } else {
                            scbj(result, flowInfo, numRecord, itemInfo, flowDef);
                        }
                    }
                }
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "未提供流程相关数据");
        }
        log.info("不动产流程发起请求处理结果：" + result.get("resultMsg"));
        log.info("++++++++++++结束不动产流程发起请求处理++++++++++++");
        return JSON.toJSONString(result);
    }

    private void scbj(Map<String, Object> result, Map<String, Object> flowInfo, Map<String, Object> numRecord,
            Map<String, Object> itemInfo, Map<String, Object> flowDef) {
        Map<String, Object> flowVars = new HashMap<String, Object>();
        flowVars.put("EFLOW_CREATORACCOUNT", flowInfo.get("EFLOW_CREATORACCOUNT"));
        flowVars.put("EFLOW_CREATORNAME", flowInfo.get("EFLOW_CREATORNAME"));
        flowVars.put("EFLOW_SUBJECT", flowInfo.get("SBMC"));
        flowInfo.put("SUBJECT", flowInfo.get("SBMC"));
        if (flowInfo.get("EXECUTE_TIME") == null || 
                StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            flowVars.put("CREATE_TIME", df.format(new Date()));
        }else {
            flowVars.put("CREATE_TIME", flowInfo.get("EXECUTE_TIME"));
        }
//        flowVars.put("CREATE_TIME", "2018-06-05 15:31:10");
        flowVars.put("EFLOW_DEFKEY", itemInfo.get("DEF_KEY"));
        flowVars.put("EFLOW_BUSTABLENAME", itemInfo.get("FORM_KEY"));
        flowVars.put("EFLOW_DEFID", itemInfo.get("BDLCDYID"));
        flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false"); flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("ITEM_NAME", itemInfo.get("ITEM_NAME"));
        flowVars.put("ITEM_CODE", itemInfo.get("ITEM_CODE"));
        flowVars.put("SSBMBM", itemInfo.get("SSBMBM")); flowVars.put("SQFS", "2");
        flowVars.put("SOURCE", "不动产");
        String randomNum =StringUtil.getFormatNumber(6, StringUtil.getRandomIntNumber(1000000)+"") ;
        flowVars.put("BJCXMM",randomNum);
        // 获取当前运行节点
        String currentOperNodeName = flowNodeService.getNodeName(
                itemInfo.get("BDLCDYID").toString(),
                Integer.valueOf(flowDef.get("VERSION").toString()), Jbpm6Constants.START_NODE);
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", currentOperNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);
        // 办件基本信息
        // 申报对象信息
        // 经办人信息
        flowVars.putAll(flowInfo);
        
        //用于判断是否来源与接口调用的办件过程执行
        flowVars.put("DATAFROM", "webservice");
        if (flowInfo.get("EXECUTE_TIME") == null || 
                StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            flowVars.put("EXECUTE_TIME", df.format(new Date()));
        }else {
            flowVars.put("EXECUTE_TIME", flowInfo.get("EXECUTE_TIME"));
        }
//        flowVars.put("EXECUTE_TIME", "2018-06-05 15:31:10");
        String nextStepJson = jbpmService.getNextStepsJson(itemInfo.get("BDLCDYID").toString(),
                Integer.valueOf(flowDef.get("VERSION").toString()), currentOperNodeName, flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        }
        try {
            Map<String, Object> resultMap = jbpmService.doFlowJob(flowVars);
            String exeId = (String) resultMap.get("EFLOW_EXEID");
            Map<String, Object> record = new HashMap<String, Object>();
            record.put("EXE_ID", exeId);
            jbpmService.saveOrUpdate(record, "T_CKBS_NUMRECORD",
                    (String) numRecord.get("RECORD_ID"));
            result.put("resultCode", "001");
            result.put("resultMsg", "保存成功，在审批平台流程实例ID：" + exeId);
        } catch (Exception e) {
            result.put("resultCode", "000"); result.put("resultMsg", "发起流程异常");
            log.error("", e);
        }
    }

    /**
     * 
     * 描述 流程环节执行
     * 
     * @author Danto Huang
     * @created 2017年10月25日 上午8:48:36
     * @param flowInfo
     * @return
     */
    public String flowExecute(String flowInfoJson) {
        log.info("++++++++++++接收到不动产流程执行请求++++++++++++");
        serviceItemService = (ServiceItemService) AppUtil.getBean("serviceItemService");
        flowNodeService = (FlowNodeService) AppUtil.getBean("flowNodeService");
        jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
        callService = (CallService) AppUtil.getBean("callService");
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            if (flowInfo.get("LINE_NO") == null || StringUtils.isEmpty((String) flowInfo.get("LINE_NO"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台排队号LINE_NO");
            } else if (flowInfo.get("LINE_DATE") == null || StringUtils.isEmpty((String) flowInfo.get("LINE_DATE"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台取号日期LINE_DATE");
//            } else if (flowInfo.get("EXECUTE_TIME") == null 
//                || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
//                result.put("resultCode", "003");
//                result.put("resultMsg", "缺失流程关键数据，办理时间EXECUTE_TIME");
            } else if (flowInfo.get("itemCode") == null || StringUtils.isEmpty((String) flowInfo.get("itemCode"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台事项编码itemCode");
            }else if (flowInfo.get("EFLOW_HANDLE_OPINION") == null 
                    || StringUtils.isEmpty((String) flowInfo.get("EFLOW_HANDLE_OPINION"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审核意见EFLOW_HANDLE_OPINION");
            }  else {
                Map<String, Object> numRecord = callService.getRecordByNoAndDate((String) flowInfo.get("LINE_NO"),
                        (String) flowInfo.get("LINE_DATE"));
                String itemCode = flowInfo.get("itemCode").toString();
                Map<String, Object> itemInfo = serviceItemService.getItemAndDefInfo(itemCode);
                if (itemInfo == null) {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应事项");
                } else if (numRecord == null) {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,LINE_NO:" + flowInfo.get("LINE_NO") + ",LINE_DATE:"
                            + flowInfo.get("LINE_DATE") + ",在审批平台未查询到对应取号记录");
                } else if (numRecord.get("EXE_ID") == null || numRecord.get("EXE_ID") == "") {
                    result.put("resultCode", "005");
                    result.put("resultMsg", "数据有误,取号记录LINE_NO:" + flowInfo.get("LINE_NO") + ",LINE_DATE:"
                            + flowInfo.get("LINE_DATE") + ",还未在审批平台发起办件流程");
                } else if (flowInfo.get("TASK_NODENAME") == null
                        || StringUtils.isEmpty((String) flowInfo.get("TASK_NODENAME"))) {
                    result.put("resultCode", "003");
                    result.put("resultMsg", "缺失流程关键数据，任务节点名称TASK_NODENAME");
                } else if (flowInfo.get("TASK_NODENAME").equals("开始")) {
                    result.put("resultCode", "006");
                    result.put("resultMsg", "提交数据环节有误，【开始】环节请走发起流程接口");
                } else if (flowInfo.get("ASSIGNER_CODE") == null
                        || StringUtils.isEmpty((String) flowInfo.get("ASSIGNER_CODE"))) {
                    result.put("resultCode", "003");
                    result.put("resultMsg", "缺失流程关键数据，审批平台流程审核人账号ASSIGNER_CODE");
                } else if (flowInfo.get("ASSIGNER_NAME") == null
                        || StringUtils.isEmpty((String) flowInfo.get("ASSIGNER_NAME"))) {
                    result.put("resultCode", "003");
                    result.put("resultMsg", "缺失流程关键数据，审批平台流程审核人姓名ASSIGNER_NAME");
                } else {
                    String exeId = (String) numRecord.get("EXE_ID");
                    Map<String, Object> flowexe = jbpmService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                            new Object[] { exeId });
                    Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                            new String[] { "DEF_ID" }, new Object[] { flowexe.get("DEF_ID") });
                    String curStepNames = (String) flowexe.get("CUR_STEPNAMES");
                    String taskNodeName = (String) flowInfo.get("TASK_NODENAME");
                    if (StringUtils.isNotEmpty(curStepNames)
                            &&serviceItemService.checkItemFlowNodes(itemCode, taskNodeName,
                            flowInfo.get("ASSIGNER_CODE")==null?"":flowInfo.get("ASSIGNER_CODE").toString()) 
                            && curStepNames.contains(taskNodeName)) {
                        Map<String, Object> flowTask = jbpmService.getByJdbc("JBPM6_TASK",
                                new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE",
                                        "ASSIGNER_NAME" ,"TASK_STATUS"},
                                new Object[] { exeId, taskNodeName, flowInfo.get("ASSIGNER_CODE"),
                                        flowInfo.get("ASSIGNER_NAME"),"1"});
                        if(flowTask==null||!(flowTask.get("TASK_STATUS").toString()).equals("1")){
                            result.put("resultCode", "005");
                            result.put("resultMsg", "数据有误,流程环节名称TASK_NODENAME:" + taskNodeName + 
                                    "    审批系统中流程环节名称curStepNames:"+curStepNames+
                                    ",审核人账号ASSIGNER_CODE："+ flowInfo.get("ASSIGNER_CODE") + "未能查询到匹配的流程待办任务");
                        }else{
                            Map<String, Object> flowVars = new HashMap<String, Object>();
                            flowVars.put("EFLOW_EXEID", exeId);
                            flowVars.put("EFLOW_DEFKEY", flowDef.get("DEF_KEY"));
                            flowVars.put("EFLOW_DEFID", flowDef.get("DEF_ID"));
                            flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));
                            flowVars.put("EFLOW_ISQUERYDETAIL", "false");
                            flowVars.put("EFLOW_ISTEMPSAVE", "-1");
                            flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", flowexe.get("CUR_STEPNAMES"));
                            flowVars.put("EFLOW_CURUSEROPERNODENAME", flowexe.get("CUR_STEPNAMES"));
                            flowVars.put("ITEM_CODE", itemCode);
                            flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
                            flowVars.put("EFLOW_CURRENTTASK_ID", flowTask.get("TASK_ID"));
                            flowVars.put("EFLOW_HANDLE_OPINION", flowInfo.get("EFLOW_HANDLE_OPINION"));
                            
                            //用于判断是否来源与接口调用的办件过程执行
                            flowVars.put("DATAFROM", "webservice");
                            if (flowInfo.get("EXECUTE_TIME") == null || 
                                    StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                                flowVars.put("EXECUTE_TIME", df.format(new Date()));
                            }else {
                                flowVars.put("EXECUTE_TIME", flowInfo.get("EXECUTE_TIME"));
                            }
//                            flowVars.put("EXECUTE_TIME", "2018-06-05 15:31:10");

                            try {
                                String nextStepJson = jbpmService.getNextStepsJson((String) flowDef.get("DEF_ID"),
                                        Integer.valueOf(flowDef.get("VERSION").toString()),
                                        (String) flowexe.get("CUR_STEPNAMES"), flowVars);
                                if (StringUtils.isNotEmpty(nextStepJson)
                                        ||flowInfo.get("TASK_NODENAME").equals("办结")) {
                                    flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                                    jbpmService.doFlowJob(flowVars);
                                    if (flowInfo.get("TASK_NODENAME").equals("办结")){
                                        Map<String,Object> data=new HashMap<String, Object>();
                                        data.put("XKFILE_NUM", flowInfo.get("XKFILE_NUM"));
                                        data.put("XKFILE_NAME", flowInfo.get("XKFILE_NAME"));
                                        data.put("XKDEPT_NAME", flowInfo.get("XKDEPT_NAME"));
                                        data.put("XKDEPT_ID", flowInfo.get("XKDEPT_ID"));
                                        data.put("EFFECT_TIME", flowInfo.get("EFFECT_TIME"));
                                        data.put("CLOSE_TIME", flowInfo.get("CLOSE_TIME"));
                                        data.put("ISLONG_TERM", flowInfo.get("ISLONG_TERM"));
                                        data.put("XKCONTENT", flowInfo.get("XKCONTENT"));
                                        data.put("SDCONTENT", flowInfo.get("SDCONTENT"));
                                        data.put("RESULT_FILE_ID", flowInfo.get("RESULT_FILE_ID"));
                                        data.put("RESULT_FILE_URL", flowInfo.get("RESULT_FILE_URL"));
                                        data.put("RUN_MODE", flowInfo.get("RUN_MODE"));
                                        data.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
                                        data.put("CUR_NODE", flowVars.get("EFLOW_CURUSEROPERNODENAME"));
                                        jbpmService.saveOrUpdate(data, "JBPM6_FLOW_RESULT", null);
                                    }
                                }
                                result.put("resultCode", "001");
                                result.put("resultMsg", "保存成功");
                            } catch (Exception e) {
                                result.put("resultCode", "000");
                                result.put("resultMsg", "执行流程异常");
                                log.error("", e);
                            }
                        }
                    } else {
                        result.put("resultCode", "005");
                        result.put("resultMsg", "数据有误,流程环节名称TASK_NODENAME:" + taskNodeName + 
                                "审批系统中流程环节名称curStepNames:"+curStepNames+
                                ",审核人账号ASSIGNER_CODE：" + flowInfo.get("ASSIGNER_CODE") + "与事项流程配置信息不匹配");
                    }
                }
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "未提供流程相关数据");
        }
        log.info("不动产流程执行请求处理结果：" + result.get("resultMsg"));
        log.info("++++++++++++结束不动产流程发起请求处理++++++++++++");
        return JSON.toJSONString(result);
    }
}
