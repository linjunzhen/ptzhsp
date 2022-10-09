/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.mobile.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.CommonControl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月20日 下午3:56:30
 */
@Controller
@RequestMapping("/mobileCommonController")
public class MobileCommonController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(MobileCommonController.class);
    /**
     * 引入DictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * 
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;

    @RequestMapping("/select")
    @ResponseBody
    public void select(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String clazz = request.getParameter("clazz");
        String dataInterface = request.getParameter("dataInterface");
        String dataParams = request.getParameter("dataParams");
        String onchange = request.getParameter("onchange");
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        String value = request.getParameter("value");
        String parentEleId = request.getParameter("parentEleId");
        String required = request.getParameter("required");
        String selectStr = "";
        try {
            selectStr = CommonControl.getEveSelectString(id, name, clazz, dataInterface, dataParams, onchange,
                    defaultEmptyText, value, required);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("parentEleId", parentEleId);
        result.put("selectStr", selectStr);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * APP版本号对比
     * 
     * @param request
     * @return
     */
    @RequestMapping("/versionComparison")
    public void versionComparison(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String nativeVersion = request.getParameter("version");
        String os = request.getParameter("os");
        List<Map<String, Object>> appVersions = dictionaryService.findByTypeCode("AppVersion");
        String dicVersion = (String) appVersions.get(0).get("DIC_NAME");
        String filepath = "";
        String appPath = "";
        File fileRoot;
        if (nativeVersion.equals(dicVersion)) {
            result.put("update", "n");
        } else {
            Properties properties = FileUtil.readProperties("project.properties");
            // String appfilePath = properties.getProperty("AppfilePath");
            String appfilePath = "apk/";
            appPath = properties.getProperty("projectPath") + appfilePath;
            fileRoot = new File(appPath);
            File[] fs = fileRoot.listFiles();
            if (fs.length != 0) {
                for (int i = 0; i < fs.length; i++) {
                    int len = fs[i].getAbsolutePath().length();
                    String fsPath = fs[i].getAbsolutePath().substring(len - 3);
                    log.info(fs[i].getName());
                    if ("android".equals(os) && "apk".equals(fsPath)) {
                        filepath = appfilePath + fs[i].getName();
                    } else if ("iphone".equals(os) && "ipa".equals(fsPath)) {
                        filepath = appfilePath + fs[i].getName();
                    }
                }
                result.put("update", "y");
                result.put("FILE_PATH", filepath);
            }
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年4月17日 上午9:05:02
     * @param request
     * @param response
     */
    @RequestMapping("/needmehandle")
    @ResponseBody
    public void needmehandle(HttpServletRequest request, HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if(StringUtils.isNotEmpty(isHaveHandup)){
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = flowTaskService.findNeedHandleForMobile(filter, haveHandUp);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年4月17日 上午9:05:02
     * @param request
     * @param response
     */
    @RequestMapping("/getapplyinfo")
    @ResponseBody
    public void getapplyinfo(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("EXE_ID");
        //String taskId = request.getParameter("TASK_ID");
        String defId = request.getParameter("DEF_ID");
        Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",
                new String[]{"DEF_ID"},new Object[]{defId});
        String defKey = (String) flowDef.get("DEF_KEY");
        Map<String,Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId,"false", request);
        Map<String,Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
        String json = JSON.toJSONString(EFLOWOBJ);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年4月17日 上午9:05:02
     * @param request
     * @param response
     */
    @RequestMapping("/getnexttrans")
    @ResponseBody
    public void getnexttrans(HttpServletRequest request, HttpServletResponse response) {
        String flowSubmitInfoJson = request.getParameter("flowSubmitInfoJson");
        Map<String,Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson,Map.class);
        //获取申报号
        String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
        String defId =  (String) flowSubmitInfo.get("EFLOW_DEFID");
        String nodeName = (String) flowSubmitInfo.get("EFLOW_CUREXERUNNINGNODENAMES");
        String EFLOW_ISBACK = (String) flowSubmitInfo.get("EFLOW_ISBACK");
        List<FlowNextStep> nextTrans = null;
        if(StringUtils.isNotEmpty(EFLOW_ISBACK)&&"true".equals(EFLOW_ISBACK)){
            Map<String,Object> backFlowBbj = executionService.getBackFlowObj(flowSubmitInfoJson);
            String json = JSON.toJSONString(backFlowBbj.get("nextTrans"));
            this.setJsonString(json, response);
        }else{
            int flowVersion = 0;
            if(StringUtils.isNotEmpty(exeId)){
                Map<String,Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION",
                        new String[]{"EXE_ID"},new Object[]{exeId});
                flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
            }else{
                flowVersion = this.flowDefService.getFlowVersion(defId);
            }
            flowSubmitInfo.put("EFLOW_DEFVERSION",String.valueOf(flowVersion));
            List<Map<String,Object>> nextNodes = flowNodeService.findNextFlowNodes(defId, nodeName, flowVersion);
            nextTrans = jbpmService.findNextSteps(defId, nodeName,
                    flowVersion, flowSubmitInfo, null, nextNodes);
            String json = JSON.toJSONString(nextTrans);
            this.setJsonString(json, response);
        }
    }
    
    /**
     * 
     * 描述 获取材料列表数据
     * @author Flex Hu
     * @created 2016年4月17日 上午9:05:02
     * @param request
     * @param response
     */
    @RequestMapping("/applymaters")
    @ResponseBody
    public void applymaters(HttpServletRequest request, HttpServletResponse response) {
        //获取服务事项编码
        String itemCode  = request.getParameter("itemCode");
        //获取流程申报号
        String exeId = request.getParameter("exeId");
        Map<String,Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        //获取项目ID
        String itemId = (String) serviceItem.get("ITEM_ID");
        //获取材料信息列表
        List<Map<String,Object>> applyMaters = applyMaterService.findByItemId(itemId,exeId);
        if(StringUtils.isNotEmpty(exeId)&&!exeId.equals("null")){
            Map<String,Object> execution = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            //获取业务ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            //获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        }
        String json = JSON.toJSONString(applyMaters);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述  初始化表单时获取流程等信息
     * @author Panda Chen
     * @created 2016-11-17 下午04:51:35
     * @param request
     * @param response
     */
    @RequestMapping("/applyItem")
    @ResponseBody
    public void applyItem(HttpServletRequest request, HttpServletResponse response) {
        //获取服务事项编码
        String itemCode  = request.getParameter("itemCode");
        //获取申报号
        String exeId = request.getParameter("exeId");
        String isQueryDetail = request.getParameter("isQueryDetail");
        String YHMC = request.getParameter("YHMC");
        //获取传入的参数
        Map<String,Object> requestParams = BeanUtil.getMapFromRequest(request);
        Map<String,Object> resultMap = new HashMap<String, Object>();
        Map<String,Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        if(serviceItem!=null){
            String formKey = (String) serviceItem.get("FORM_KEY");
            log.info(formKey);
            //获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            //获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            //获取材料信息列表
            List<Map<String,Object>> applyMaters = applyMaterService.findByItemId(itemId,exeId);
            //获取流程定义对象
            Map<String,Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId,"true", request);
            Map<String,Object> flowForm = null;
            Map<String,Object> flowDef = null;
            Map<String,Object> eflowObj = null;
            Map<String,Object> flowExe= null;
            if(flowAllObj.get("EFLOW_FLOWEXE")!=null){
                flowExe = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWEXE");
                resultMap.put("EFLOW_FLOWEXE",flowExe);
            }
            if(flowAllObj.get("busRecord")!=null){
                resultMap.put("busRecord",flowAllObj.get("busRecord"));
            }
            if(flowAllObj.get("EFLOWOBJ")!=null){
                eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
                if(eflowObj.get("HJMC")==null){
                    String defId = (String) eflowObj.get("EFLOW_DEFID");
                    int flowVersion = Integer.parseInt(eflowObj.get("EFLOW_DEFVERSION").toString());
                    String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                    eflowObj.put("HJMC", startNodeName);
                    //获取当前登录用户
                    eflowObj.put("SHRMC", YHMC);
                }
                if(flowExe!=null){
                    //获取办理状态
                    String runStatus = flowExe.get("RUN_STATUS").toString();
                    if(!runStatus.equals("0")&&!runStatus.equals("1")){
                        eflowObj.put("HJMC", "已办结");
                        eflowObj.put("SHRMC",null);
                    }
                    if(eflowObj.get("SHRMC")==null){
                      //获取当前登录用户
                        Map<String,Object> loginMember = AppUtil.getLoginMember();
                        eflowObj.put("SHRMC", loginMember.get("YHMC"));
                    }
                }
                Map<String, Object> flowMappedInfo = flowMappedService.getFlowMapInfo(
                        (String) eflowObj.get("EFLOW_DEFID"),eflowObj.get("HJMC").toString(), "2");
                resultMap.put("flowMappedInfo", flowMappedInfo);
                resultMap.put("EFLOWOBJ",eflowObj);
            }
            if(flowAllObj.get("EFLOW_FLOWOBJ")!=null){
                resultMap.put("EFLOW_FLOWOBJ",flowAllObj.get("EFLOW_FLOWOBJ"));
            }
            if(flowAllObj.get("EFLOW_BUTTONRIGHTS")!=null){
                resultMap.put("EFLOW_BUTTONRIGHTS",flowAllObj.get("EFLOW_BUTTONRIGHTS"));
            }
            if(flowAllObj.get("EFLOW_FLOWDEF")!=null){
                flowDef  = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF");
                resultMap.put("EFLOW_FLOWDEF",flowDef);
            }
            if(flowAllObj.get("EFLOW_FLOWFORM")!=null){
                flowForm = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWFORM");
                resultMap.put("EFLOW_FLOWFORM",flowForm);
            }
            if(StringUtils.isNotEmpty(exeId)){
                String busRecordId = (String) eflowObj.get("EFLOW_BUSRECORDID");
                String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
                applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
            }
            serviceItem.put("APPLY_TYPE","1");
            //定义材料列表JSON
            String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters,new String[]{"uploadFiles"},true);
            resultMap.put("serviceItem", serviceItem);
            resultMap.put("applyMaters", applyMaters);
            resultMap.put("isQueryDetail", isQueryDetail);
            resultMap.put("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
            resultMap.put("requestParams", requestParams);
            Map<String, Object> e = busTypeService.getIdAndNamesByItemId(itemId);
            String busTypenames = "";
            String typeids = e.get("TYPE_IDS").toString();
            if (typeids.contains("402883494fd4f3aa014fd4fb65110001")
                    || typeids.contains("4028818c512273e70151227569a40001")
                    || typeids.contains("4028818c512273e70151229ae7e00003")
                    || typeids.contains("4028818c512273e7015122a38a130004")) {
                busTypenames += "个人";
            }
            if (typeids.contains("4028818c512273e7015122a452220005")
                    || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                    || typeids.contains("4028818c512273e7015122c81f850007")
                    || typeids.contains("4028818c512273e7015122c872dc0008")) {
                if (busTypenames.length() > 1) {
                    busTypenames += ",";
                }
                busTypenames += "企业";
            }
            resultMap.put("busTypenames", busTypenames);
            
        }
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    
    
    /**
     * 执行流程方法
     * @param request
     * @return
     */
    @RequestMapping("/submitApply")
    @ResponseBody
    public void submitApply(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try{
            variables.put("EFLOW_ASSIGNER_TYPE",AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
            //定义流程标题值
            String eflowSubject = null;
            String itemName = (String) variables.get("ITEM_NAME");
            //获取申报名称
            String sbmc = (String) variables.get("SBMC");
            StringBuffer subject = new StringBuffer("");
            if(StringUtils.isNotEmpty(sbmc)){
                subject.append(sbmc).append("(").append(itemName).append(")");
            }else{
                String projectName = (String)variables.get("PROJECT_NAME");
                subject.append(projectName).append("(").append(itemName).append(")");
            }
            eflowSubject = subject.toString();
            variables.put("EFLOW_SUBJECT",eflowSubject);
            variables = jbpmService.exeFrontFlow(variables);
            variables.put("OPER_SUCCESS", true);

            variables.put("OPER_MSG", "申报成功,申报号是:"+(String)variables.get("EFLOW_EXEID"));
        }catch(Exception e){
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系网站管理员!");
            log.error(e.getMessage(),e);
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
        
    }
}
