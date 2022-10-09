/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.ptwg.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.mobile.controller.MobileCommonController;
import net.evecom.platform.ptwg.service.OnlineApplyService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-5-22 上午8:44:08
 */
@Controller
@RequestMapping("/onlineApplyController")
public class OnlineApplyController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(OnlineApplyController.class);
    
    /**
     * 引入onlineApplyService
     */
    @Resource
    private OnlineApplyService onlineApplyService;
    /**
     * 引入serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 引入flowMappedService
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 引入busTypeService
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;

    /**
     * 
     * 跳转审批平台事项在线办事
     * @author Danto Huang
     * @created 2017-5-22 上午9:23:13
     * @param request
     * @param response
     */
    @RequestMapping("/applyItem")
    @ResponseBody
    public void applyItem(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String itemCode  = request.getParameter("itemCode");
        String exeId = request.getParameter("exeId");
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(exeId)){
            String userId = onlineApplyService.checkGridUser(request);
            resultMap.put("uploadUserId", userId);
        }
        resultMap.put("uploadUserName", request.getParameter("userName"));
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
                    eflowObj.put("SHRMC", request.getParameter("userName"));
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
                        //Map<String,Object> loginMember = AppUtil.getLoginMember();
                        //eflowObj.put("SHRMC", loginMember.get("YHMC"));
                        eflowObj.put("SHRMC", request.getParameter("userName"));
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
            }else{
                String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
                applyMaters = applyMaterService.setHisUploadFiles(applyMaters,busTableName,resultMap);
            }
            serviceItem.put("APPLY_TYPE","1");
            //定义材料列表JSON
            String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters,new String[]{"uploadFiles"},true);
            resultMap.put("serviceItem", serviceItem);
            resultMap.put("applyMaters", applyMaters);
            resultMap.put("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
            
        }
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 我的申请列表
     * @author Danto Huang
     * @created 2017-5-25 下午4:23:12
     * @param request
     * @param response
     */
    @RequestMapping("/getMyApplyList")
    public void getMyApplyList(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String userCode = request.getParameter("userCode");
        String page = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
        String rows = request.getParameter("rows") == null ? "5" : request.getParameter("rows");
        String type = request.getParameter("type");
        List<Map<String, Object>> mapList = onlineApplyService.findfrontWdbjList(page,rows,userCode,type);

        String json = JSON.toJSONString(mapList);
        this.setJsonString(json, response);;
    }
    /**
     * 
     * 发表评价   
     * @author Danto Huang
     * @created 2017-6-9 下午3:19:25
     * @param request
     * @return
     */
    @RequestMapping("/savePjxx")
    @ResponseBody
    public void savePjxx(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            String yhzh = request.getParameter("YHZH");
            String exeId = request.getParameter("EXE_ID");
            Map<String,Object>  bspj = onlineApplyService.getByJdbc("T_WSBS_BSPJ",
                    new String[]{"EXE_ID","YHZH"},new Object[]{exeId,yhzh});
            if(bspj==null){
                Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                onlineApplyService.saveOrUpdate(variables, "T_WSBS_BSPJ", null);
                result.put("OPER_MSG","评价成功!");
            }else{
                result.put("OPER_MSG","此办事记录已经评价!");
            }
            result.put("OPER_SUCCESS", true);
        }catch(Exception e){
            result.put("OPER_SUCCESS", false);
            result.put("OPER_MSG", "提交失败,请联系网站管理员!");
            log.error(e.getMessage(),e);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述   退回信息
     * @author Danto Huang
     * @created 2017-6-21 下午6:40:35
     * @param request
     * @param response
     */
    @RequestMapping("/backInfo")
    public void backInfo(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String bjxxId  = request.getParameter("bjid");
        Map<String,Object> bjxx = bjxxService.getByJdbc("T_WSBS_BJXX",
                new String[]{"BJXX_ID"},new Object[]{bjxxId});
        List bjclList = JSON.parseArray((String)bjxx.get("BJCLLB"), Map.class);
        bjxx.put("bjclList", bjclList);
        String json = JSON.toJSONString(bjxx);
        this.setJsonString(json, response);
    }
}
