/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.evecom.core.util.*;
import net.evecom.platform.wsbs.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import icepdf.i;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowFormService;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.website.service.WebSiteService;
import net.evecom.platform.website.service.XFDesignService;
import net.evecom.platform.wsbs.model.TzProjectRespones;
import net.sf.json.JSONArray;


/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015年11月17日 下午2:44:10
 */
@Controller
@RequestMapping("/webSiteController")
public class WebSiteController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WebSiteController.class);
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;

    /**
     * 引入newCallService
     */
    @Resource
    private NewCallService newCallService;

    /**
     * commercialService
     */
    @Resource
    private CommercialService commercialService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * flowDefService
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * flowFormService
     */
    @Resource
    private FlowFormService flowFormService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * webSiteService
     */
    @Resource
    private WebSiteService webSiteService;
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 引入Service
     */
    @Resource
    private CommonProblemService commonProblemService;

    /**
     * 引入Service
     */
    @Resource
    private SwbInterfaceService swbInterfaceService;
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
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    /**
     * 引入Service
     */
    @Resource
    private UserInfoService userInfoService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 引入Service
     */
    @Resource
    private XFDesignService xfDesignService;
    /**
     * projectService
     */
    @Resource
    private ProjectService projectService;
    
    /**
     * 执行网站的跳转
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/view")
    public ModelAndView view(HttpServletRequest request) throws UnsupportedEncodingException {
        String navTarget = request.getParameter("navTarget");
        String returnUrl = request.getParameter("returnUrl");
        String key=request.getParameter("key");
        String type=request.getParameter("type");
        if(StringUtils.isNotEmpty(key)){
            request.setAttribute("key",key);
            request.setAttribute("typeCode",type);
        }
        if (StringUtils.isNotEmpty(returnUrl)) {
            request.setAttribute("returnUrl", URLDecoder.decode(returnUrl, "UTF-8"));
        }
        if (StringUtils.isNotEmpty(navTarget)) {
            if("website/yhzx/login".equals(navTarget)) {//用户中心登录入口引用闽政通登录入口
                Properties properties = FileUtil.readProperties("conf/config.properties");
                String mztUrl = properties.getProperty("MZT_URL");
                String callerCode = properties.getProperty("callerCode");
                return new ModelAndView("redirect:" + mztUrl + "?callerCode=" + callerCode);
            }
            return new ModelAndView(navTarget);
        } else {
            return new ModelAndView("website/index/index");
        }
    }
    
    /**
     * 闽政通注册入口
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/mztRegist")
    public ModelAndView mztRegist(HttpServletRequest request) throws UnsupportedEncodingException {
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String mztRegist = properties.getProperty("MZT_regist");
        return new ModelAndView("redirect:" + mztRegist);
    }

    /**
     * 跳转到用户中心
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "usercenter")
    public ModelAndView usercenter(HttpServletRequest request) {
        Map<String, Object> memeber = AppUtil.getLoginMember();
        String userAccount = (String) memeber.get("YHZH");
        List<Map<String, Object>> userTaskList = flowTaskService.findWebSiteUserTask(userAccount);
        // 获取数量MAP
        Map<String, String> mapCount = executionService.getReportCountForWebSite(userAccount);
        mapCount.put("DBLCOUNT", String.valueOf(userTaskList.size()));
        request.setAttribute("userTaskList", userTaskList);
        request.setAttribute("mapCount", mapCount);
        return new ModelAndView("website/yhzx/yhzx");
    }

    /**
     * 跳转到用户中心
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "test")
    public ModelAndView test(HttpServletRequest request) {
        // commercialService.getEicpEntInfo("FJPT17010327834");//内资
        commercialService.getEicpEntInfo("FJPT17010527837");// 个独
        return new ModelAndView("website/yhzx/yhzx");
    }

    /**
     * 执行网站的跳转
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "applyItem")
    public ModelAndView applyItem(HttpServletRequest request) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        String sssblx = request.getParameter("sssblx");//商事申报类型
        String PT_ID = request.getParameter("PT_ID");//个体平台信息ID
        request.setAttribute("sssblx", sssblx);
        request.setAttribute("PT_ID", PT_ID);
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String typeId= request.getParameter("typeId");
        String stageId=request.getParameter("stageId");
        String isFiled=request.getParameter("isFiled");
        String isQueryDetail = request.getParameter("isQueryDetail");
        //项目工程类型，该类型为2时，在线办理信息界面不显示办事指南按钮
        String projectType = request.getParameter("projectType");
        //项目编码
        String projectCode = request.getParameter("projectCode");
        String tableName = "JBPM6_EXECUTION";
        if("1".equals(isFiled)) {
            tableName = "JBPM6_EXECUTION_EVEHIS";
        }
        if(projectCode==null || "".equals(projectCode)) {
            if(exeId!=null && !"".equals(exeId)) {
                Map<String, Object> exeInfo = serviceItemService.getByJdbc(tableName,
                        new String[] { "EXE_ID" }, new Object[] { exeId });
                if(exeInfo.get("PROJECT_CODE")!=null) {
                    projectCode = exeInfo.get("PROJECT_CODE").toString();
                }
            }
        }
      
        // 获取传入的参数
        Map<String, Object> requestParams = BeanUtil.getMapFromRequest(request);
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("typeName", "zxbl");
        request.setAttribute("cnType", "serviceItem");
        request.setAttribute("typeId",typeId);
        request.setAttribute("stageId",stageId);
        if(projectCode!=null && !"".equals(projectCode)) {
            serviceItem.put("projectCode", projectCode);
            String prj_num = xfDesignService.getPrjNum(projectCode);
            request.setAttribute("PRJ_NUM", prj_num);
        }else {
            serviceItem.put("projectCode", "");
        }
        request.setAttribute("projectCode", projectCode);
        if(projectType!=null && !"".equals(projectType)) {
            request.setAttribute("projectType", projectType);
        }
        if (itemCode.equals("569262478QS00203")) {
            List<Map<String, Object>> htIndustry = webSiteService.getHTIndustry();
            request.setAttribute("htIndustry", htIndustry);
        }
        return ifstnull(request, exeId, isQueryDetail, requestParams, serviceItem);
    }



    /**
     * 执行前台项目工程网站申报的跳转
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "applyItemOfProject")
    public ModelAndView applyItemOfProject(HttpServletRequest request) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String typeId= request.getParameter("typeId");
        String stageId=request.getParameter("stageId");
        String isQueryDetail = request.getParameter("isQueryDetail");
        //项目工程类型，该类型为2时，在线办理信息界面不显示办事指南按钮
        String projectType = request.getParameter("projectType");
        //项目编码
        String projectCode = request.getParameter("projectCode");
        if(projectCode==null || "".equals(projectCode)) {
            if(exeId!=null && !"".equals(exeId)) {
                Map<String, Object> exeInfo = serviceItemService.getByJdbc("JBPM6_EXECUTION",
                        new String[] { "EXE_ID" }, new Object[] { exeId });
                if(exeInfo.get("PROJECT_CODE")!=null) {
                    projectCode = exeInfo.get("PROJECT_CODE").toString();
                }
            }
        }

        // 获取传入的参数
        Map<String, Object> requestParams = BeanUtil.getMapFromRequest(request);
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("typeName", "zxbl");
        request.setAttribute("cnType", "serviceItem");
        request.setAttribute("typeId",typeId);
        request.setAttribute("stageId",stageId);
        setSelectItemOfProject(exeId,request);
        if(projectCode!=null && !"".equals(projectCode)) {
            serviceItem.put("projectCode", projectCode);
            String prj_num = xfDesignService.getPrjNum(projectCode);
            request.setAttribute("PRJ_NUM", prj_num);
        }else {
            serviceItem.put("projectCode", "");
        }
        request.setAttribute("projectCode", projectCode);
        if(projectType!=null && !"".equals(projectType)) {
            request.setAttribute("projectType", projectType);
        }
        if (itemCode.equals("569262478QS00203")) {
            List<Map<String, Object>> htIndustry = webSiteService.getHTIndustry();
            request.setAttribute("htIndustry", htIndustry);
        }
        return ifstnull(request, exeId, isQueryDetail, requestParams, serviceItem);
    }

    /**
     * 项目工程办件查看详情
     * @param exeId
     * @param request
     */
    private void setSelectItemOfProject(String exeId,HttpServletRequest request){
        if(StringUtils.isNotEmpty(exeId)){
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String topicCode=StringUtil.getString(busRecord.get("TOPIC_CODE"));
            String stageId = StringUtil.getString(busRecord.get("STAGE_ID"));
            String typeId = StringUtil.getString(busRecord.get("TYPE_ID"));
            request.setAttribute("topicCode", topicCode);
            request.setAttribute("stageId", stageId);
            request.setAttribute("typeId", typeId);
        }
    }
    /**
     * 判断人防项目的基本信息是否填写
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "checkRF")
    public void checkRF(HttpServletRequest request, HttpServletResponse response) {
        Map <String, Object> result = new HashMap<String, Object>();
        String projectCode = request.getParameter("projectCode");
        Map<String,Object> map = webSiteService.getByJdbc("T_BSFW_RFXMXXB", 
                new String[] { "PROJ_COD" }, new Object[] { projectCode });
        boolean hasBaseInfo = false;
        if(map != null) {  //如果还未填写人防项目的基本信息，则提示填写
            hasBaseInfo = true;
        }
        result.put("hasBaseInfo", hasBaseInfo);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "rfBaseSaveOrUpdate")
    @ResponseBody
    public AjaxJson rfBaseSaveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            //variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            //String typeId = (String) variables.get("TYPE_ID");
            //int maxSn = dictionaryService.getMaxSn(typeId);
            //variables.put("DIC_SN", maxSn+1);
        }
        //获取字典类别
        //Map<String,Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_ID"},
       //         new Object[]{variables.get("TYPE_ID")});
        //variables.put("TYPE_CODE",dicType.get("TYPE_CODE"));
        webSiteService.saveOrUpdate(variables, "T_BSFW_RFXMXXB", entityId);
        j.setMsg("保存成功");
        return j;
    }

    @SuppressWarnings("unchecked")
    private ModelAndView ifstnull(HttpServletRequest request, String exeId, String isQueryDetail,
            Map<String, Object> requestParams, Map<String, Object> serviceItem) {
        String formKey = (String) serviceItem.get("FORM_KEY");
        // 获取流程KEY
        String defKey = (String) serviceItem.get("DEF_KEY");
        // 获取项目ID
        String itemId = (String) serviceItem.get("ITEM_ID");
        // 获取材料信息列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        // 获取材料业务办理子项分类列表
        List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeListE(itemId);
        // 获取流程定义对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
        Map<String, Object> flowForm = null;
        Map<String, Object> flowDef = null;
        Map<String, Object> eflowObj = null;
        Map<String, Object> flowExe = null;
        if (flowAllObj.get("EFLOW_FLOWEXE") != null) {
            flowExe = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWEXE");
            request.setAttribute("EFLOW_FLOWEXE", flowExe);
        }
        if (flowAllObj.get("busRecord") != null) {
            Map<String, Object> busRecord = (Map<String, Object>) flowAllObj.get("busRecord");
            // 获取多证合一数据
            Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                    new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("BUS_RECORDID") });
            if (null != multiple) {
                busRecord.putAll(multiple);
            }
            // 设置商事登记事项参数
            setCompany(request, busRecord);
            // 设置商事登记全程网办事项参数
            setCompanyQcwb(request, busRecord);
            // 设置工程建设施工许可事项参数
            setProjectSgxk(request, busRecord);
            
            request.setAttribute("busRecord", busRecord);
        }
        if (flowAllObj.get("EFLOWOBJ") != null) {
            eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            if (eflowObj.get("HJMC") == null) {
                String defId = (String) eflowObj.get("EFLOW_DEFID");
                int flowVersion = Integer.parseInt(eflowObj.get("EFLOW_DEFVERSION").toString());
                String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                eflowObj.put("HJMC", startNodeName);
                // 获取当前登录用户
                Map<String, Object> loginMember = AppUtil.getLoginMember();
                eflowObj.put("SHRMC", loginMember.get("YHMC"));
            }
            if (flowExe != null) {
                // 获取办理状态
                String runStatus = flowExe.get("RUN_STATUS").toString();
                if (!runStatus.equals("0") && !runStatus.equals("1")) {
                    eflowObj.put("HJMC", "已办结");
                    eflowObj.put("SHRMC", null);
                }
                if (eflowObj.get("SHRMC") == null) {
                    // 获取当前登录用户
                    Map<String, Object> loginMember = AppUtil.getLoginMember();
                    eflowObj.put("SHRMC", loginMember.get("YHMC"));
                }
                //获取发起人账号
                String CREATOR_ACCOUNT = flowExe.get("CREATOR_ACCOUNT").toString();

                Map<String, Object> loginMember = AppUtil.getLoginMember();
                String YHZH = loginMember.get("YHZH").toString();
                if(!CREATOR_ACCOUNT.equals(YHZH)){//当办件发起人不是当前用户时，无法查看办件
                    return new ModelAndView("redirect:/403filter.html");
                }
            }
            Map<String, Object> flowMappedInfo = flowMappedService.getFlowMapInfo((String) eflowObj.get("EFLOW_DEFID"),
                    eflowObj.get("HJMC").toString(), "2");
            request.setAttribute("flowMappedInfo", flowMappedInfo);
            request.setAttribute("EFLOWOBJ", eflowObj);
        }
        if (flowAllObj.get("EFLOW_FLOWOBJ") != null) {
            request.setAttribute("EFLOW_FLOWOBJ", flowAllObj.get("EFLOW_FLOWOBJ"));
        }
        if (flowAllObj.get("EFLOW_BUTTONRIGHTS") != null) {
            request.setAttribute("EFLOW_BUTTONRIGHTS", flowAllObj.get("EFLOW_BUTTONRIGHTS"));
        }
        if (flowAllObj.get("EFLOW_FLOWDEF") != null) {
            flowDef = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF");
            request.setAttribute("EFLOW_FLOWDEF", flowDef);
        }
        if (flowAllObj.get("EFLOW_FLOWFORM") != null) {
            flowForm = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWFORM");
            request.setAttribute("EFLOW_FLOWFORM", flowForm);
        }
        if (StringUtils.isNotEmpty(exeId)) {
            String busRecordId = (String) eflowObj.get("EFLOW_BUSRECORDID");
            String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
            applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        } else {
            String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
            applyMaters = applyMaterService.setHisUploadFiles(applyMaters, busTableName, null);
        }
        String projectCode = serviceItem.get("projectCode").toString();
        //开始设定工程建设项目相同附件描述智能 回填
        if(null == flowExe && StringUtils.isNotEmpty(projectCode)){
            applyMaterService.setSameUploadFiles(applyMaters, formKey, projectCode);
            applyMaterService.setSameKeyUploadFiles(applyMaters, formKey, projectCode);
        }
        serviceItem.put("APPLY_TYPE", "1");
        /*生成一个用于识别电子证照反馈信息的UUID*/
        serviceItem.put("CREDIT_FEEDBACK_MARK", UUIDGenerator.getUUID());
        /*判断是否是外网禁止申报的事项*/
        List<Map<String, Object>> wwjzsbsxList = dictionaryService.findByTypeCode("wwjzsbsx");
        String itemCode = StringUtil.getValue(requestParams, "itemCode");
        boolean prohibitedFlag = false;
        for (Map<String, Object> wwjzsbsxMap : wwjzsbsxList) {
            if (Objects.equals(itemCode, StringUtil.getValue(wwjzsbsxMap, "DIC_CODE"))) {
                prohibitedFlag = true;
                break;
            }
        }
        request.setAttribute("prohibitedItem", prohibitedFlag);

        // 定义材料列表JSON
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("handleTypes", handleTypes);
        request.setAttribute("isQueryDetail", isQueryDetail);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        request.setAttribute("requestParams", requestParams);
        // 定义商事登记自贸区划分地址信息
        List<Map<String, Object>> addrList = dictionaryService.findByTypeCode("zhqhfdz");
        StringBuffer addrs = new StringBuffer();
        for (Map<String, Object> map : addrList) {
            if (addrs.length() > 0) {
                addrs.append(",");
            }
            addrs.append(map.get("DIC_CODE"));
        }
        request.setAttribute("zhqhfdz", addrs);
        Map<String, Object> e = busTypeService.getIdAndNamesByItemId(itemId);
        String busTypenames = "";
        String typeids = e.get("TYPE_IDS").toString();
        if (typeids.contains("402883494fd4f3aa014fd4fb65110001") || typeids.contains("4028818c512273e70151227569a40001")
                || typeids.contains("4028818c512273e70151229ae7e00003")
                || typeids.contains("4028818c512273e7015122a38a130004")) {
            busTypenames += "个人";
        }
        if (typeids.contains("4028818c512273e7015122a452220005") || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                || typeids.contains("4028818c512273e7015122c81f850007")
                || typeids.contains("4028818c512273e7015122c872dc0008")) {
            if (busTypenames.length() > 1) {
                busTypenames += ",";
            }
            busTypenames += "企业";
        }
        
        request.setAttribute("busTypenames", busTypenames);
        return new ModelAndView("website/applyforms/" + formKey);
    }
    
    private void setProjectSgxk(HttpServletRequest request, Map<String, Object> busRecord) {
        // 外施工图审查合格证书编号信息
        setRequestList(request, busRecord, "CHARTREVIEWNUM_JSON", "chartreviewnumList");
        // 建设单位信息
        setRequestList(request, busRecord, "JSDW_JSON", "jsdwList");
        // 代建单位信息
        setRequestList(request, busRecord, "DJDW_JSON", "djdwList");
        // 施工单位信息
        setRequestList(request, busRecord, "SGDW_JSON", "sgdwList" ,"SGRY");
        // 监理单位信息
        setRequestList(request, busRecord, "JLDW_JSON", "jldwList" ,"JLRY");
        // 勘察单位信息
        setRequestList(request, busRecord, "KCDW_JSON", "kcdwList");
        // 设计单位信息
        setRequestList(request, busRecord, "SJDW_JSON", "sjdwList");
        // 施工图审查单位信息
        setRequestList(request, busRecord, "SGTSCDW_JSON", "sgtscdwList");
        // 控制价（预算价）计价文件编制单位信息
        setRequestList(request, busRecord, "KZJDW_JSON", "kzjdwList");
        // 招标代理单位信息
        setRequestList(request, busRecord, "ZBDW_JSON", "zbdwList");
        // 检测单位信息
        setRequestList(request, busRecord, "JCDW_JSON", "jcdwList");
        // 单位工程信息
        setRequestList(request, busRecord, "DWGC_JSON", "dwgcList");
        // 桩基工程信息
        setRequestList(request, busRecord, "ZJGC_JSON", "zjgcList");
        // 上部工程信息
        setRequestList(request, busRecord, "SBGC_JSON", "sbgcList");
    }

    @SuppressWarnings("unchecked")
    private void setRequestList(HttpServletRequest request, Map<String, Object> busRecord, String key,
            String listName) {
        String json = busRecord.get(key) == null ? ""
                : busRecord.get(key).toString();
        if (StringUtils.isNotEmpty(json)) {
            List<Map<String, Object>> list = JSON.parseObject(json, List.class);
            request.setAttribute(listName, list);
        }
    }

    @SuppressWarnings("unchecked")
    private void setRequestList(HttpServletRequest request, Map<String, Object> busRecord, String key,
            String listName,String childName) {
        String json = busRecord.get(key) == null ? ""
                : busRecord.get(key).toString();
        if (StringUtils.isNotEmpty(json)) {
            List<Map<String, Object>> list = JSON.parseObject(json, List.class);
            for (Map<String, Object> map : list) {
                String childJson = map.get(childName)==null?"":map.get(childName).toString();
                if (StringUtils.isNotEmpty(childJson)) {
                    List<Map<String, Object>> childList = JSON.parseObject(childJson, List.class);  
                    for (Map<String, Object> map2 : childList) {
                        String child2Json = map2.get(childName)==null?"":map2.get(childName).toString(); 
                        if (StringUtils.isNotEmpty(childJson)) 
                            map2.put(childName, StringEscapeUtils.escapeHtml3(child2Json));
                    }
                    map.put(childName+"LIST", childList); 
                    
                }
            }
            request.setAttribute(listName, list);
        }
    }
    /**
     * 
     * 描述 设置商事登记全程网办参数
     * @author Rider Chen
     * @created 2021年3月30日 下午3:21:57
     * @param request
     * @param busRecord
     */
    @SuppressWarnings("unchecked")
    private void setCompanyQcwb(HttpServletRequest request, Map<String, Object> busRecord) {
        // 清算组成员
        String QSZCY_JSON = busRecord.get("QSZCY_JSON") == null ? "" : busRecord.get("QSZCY_JSON").toString();
        if (StringUtils.isNotEmpty(QSZCY_JSON)) {
            List<Map<String, Object>> qszcyxxList = JSON.parseObject(QSZCY_JSON, List.class);
            request.setAttribute("qszcyxxList", qszcyxxList);
        }
        // 原董事信息
        String OLD_DIRECTOR_JSON = busRecord.get("OLD_DIRECTOR_JSON") == null ? "" : busRecord.get("OLD_DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            List<Map<String, Object>> oldDirectorList = JSON.parseObject(OLD_DIRECTOR_JSON, List.class);
            request.setAttribute("oldDirectorList", oldDirectorList);
        }
        // 原监事信息
        String OLD_SUPERVISOR_JSON = busRecord.get("OLD_SUPERVISOR_JSON") == null ? "" : busRecord.get("OLD_SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_SUPERVISOR_JSON)) {
            List<Map<String, Object>> oldSupervisorList = JSON.parseObject(OLD_SUPERVISOR_JSON, List.class);
            request.setAttribute("oldSupervisorList", oldSupervisorList);
        }
        // 原经理信息
        String OLD_MANAGER_JSON = busRecord.get("OLD_MANAGER_JSON") == null ? "" : busRecord.get("OLD_MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_MANAGER_JSON)) {
            List<Map<String, Object>> oldMangerList = JSON.parseObject(OLD_MANAGER_JSON, List.class);
            request.setAttribute("oldMangerList", oldMangerList);
        }
    }
    /**
     * 描述 设置商事登记事项参数
     * 
     * @author Rider Chen
     * @created 2016年12月22日 上午11:46:39
     * @param request
     * @param busRecord
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setCompany(HttpServletRequest request, Map<String, Object> busRecord) {
        // 外资投资者信息
        String FOREIGNINVESTOR_JSON = busRecord.get("FOREIGNINVESTOR_JSON") == null ? ""
                : busRecord.get("FOREIGNINVESTOR_JSON").toString();
        if (StringUtils.isNotEmpty(FOREIGNINVESTOR_JSON)) {
            List<Map<String, Object>> foreigninvestorList = JSON.parseObject(FOREIGNINVESTOR_JSON, List.class);
            for (Map<String, Object> map : foreigninvestorList) {
                String wftzzsjkzrJson = map.get("WFTZZSJKZR_JSON") == null ? "" : map.get("WFTZZSJKZR_JSON").toString();
                if (StringUtils.isNotEmpty(wftzzsjkzrJson)) {
                    List<Map> wftzzsjkzr = JSON.parseArray(wftzzsjkzrJson, Map.class);
                    map.put("wftzzsjkzrList", wftzzsjkzr);
                }
            }
            request.setAttribute("foreigninvestorList", foreigninvestorList);
        }
        // 中方投资者信息
        String DOMESTICINVESTOR_JSON = busRecord.get("DOMESTICINVESTOR_JSON") == null ? ""
                : busRecord.get("DOMESTICINVESTOR_JSON").toString();

        if (StringUtils.isNotEmpty(DOMESTICINVESTOR_JSON)) {
            List<Map<String, Object>> domesticinvestorList = JSON.parseObject(DOMESTICINVESTOR_JSON, List.class);
            for (Map<String, Object> map : domesticinvestorList) {
                String zftzzsjkzrJson = map.get("ZFTZZSJKZR_JSON") == null ? "" : map.get("ZFTZZSJKZR_JSON").toString();
                if (StringUtils.isNotEmpty(zftzzsjkzrJson)) {
                    List<Map> zftzzsjkzr = JSON.parseArray(zftzzsjkzrJson, Map.class);
                    map.put("zftzzsjkzrList", zftzzsjkzr);
                }
            }
            request.setAttribute("domesticinvestorList", domesticinvestorList);
        }
        String COMPANY_TYPE = busRecord.get("COMPANY_TYPE") == null ? "" : busRecord.get("COMPANY_TYPE").toString();
        String COMPANY_TYPE_CHANGE = busRecord.get("COMPANY_TYPE_CHANGE") == null ? "" : busRecord.get("COMPANY_TYPE_CHANGE").toString();
        //变更公司类型
        if(StringUtils.isNotEmpty(COMPANY_TYPE)){
            Map<String,Object> type = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[] { "TYPE_CODE" }, new Object[] { COMPANY_TYPE });
            if(type!=null){
                busRecord.put("COMPANY_TYPE_NAME", type.get("TYPE_NAME"));
            }
        }
        if(StringUtils.isNotEmpty(COMPANY_TYPE_CHANGE)){
            Map<String,Object> typeChange = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[] { "TYPE_CODE" }, new Object[] { COMPANY_TYPE_CHANGE });
            if(typeChange!=null){
                busRecord.put("COMPANY_TYPE_NAME_CHANGE", typeChange.get("TYPE_NAME"));
            }
        }
        
        //涉及变更时，最终股东列表
        List<Map<String,Object>> newHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> yHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> xHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> sameHolderList = new ArrayList<Map<String,Object>>();
        boolean isStockTransfer = false;
        // 股东信息
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            request.setAttribute("holderList", holderList);
            
            //股权变更旧股东股权判断
            for (Map<String, Object> holder : holderList) {  
                if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))){
                    if(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN"))) > 0){
                        yHolderList.add(holder);
                    }
                    if(StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS")))){
                        if(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))
                                <(Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS"))))){
                            isStockTransfer = true;
                        } 
                    }
                }
            }
        }
        // 股东信息(变更)
        String HOLDER_JSON_CHANGE = busRecord.get("HOLDER_JSON_CHANGE") == null ? ""
                : busRecord.get("HOLDER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON_CHANGE)) {
            List<Map<String, Object>> holderChangeList = JSON.parseObject(HOLDER_JSON_CHANGE, List.class);
            for(Map<String,Object> holder : holderChangeList){
                String gqlyJson = holder.get("GQLY_JSON").toString();
                List<Map<String, Object>> gqlyList = JSON.parseObject(gqlyJson, List.class);
                holder.put("gqlyList", gqlyList);
                
                xHolderList.add(holder);
            }
            request.setAttribute("holderChangeList", holderChangeList);
        }
        //剔除新旧股东同名情况
        if(yHolderList.size()>0 && xHolderList.size()>0){
            for (Map<String, Object> xHolder : xHolderList) {
                for (Map<String, Object> yHolder : yHolderList) {
                    if(StringUtil.getString(xHolder.get("SHAREHOLDER_NAME"))
                            .equals(StringUtil.getString(yHolder.get("SHAREHOLDER_NAME")))){
                        sameHolderList.add(yHolder);
                    } 
                } 
            }
            if(sameHolderList.size()>0){
                for (Map<String, Object> same : sameHolderList) {
                    yHolderList.remove(same);
                }
            }
            newHolderList.addAll(yHolderList);
            newHolderList.addAll(xHolderList);
        }else if(xHolderList.size()<=0){
            newHolderList.addAll(yHolderList);
        }else if(yHolderList.size()<=0){
            newHolderList.addAll(xHolderList);
        }
        request.setAttribute("newHolderList", newHolderList);
        
        request.setAttribute("isStockTransfer", isStockTransfer);
        
        // 董事信息
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            
            //变更职务转码
            boolean havechairman = false;//是否有董事长,有董事长代表设立董事会
            for(Map<String,Object> director : directorList){                
                if(director.get("DIRECTOR_JOB_OLD")!=null){
                    if("01".equals(director.get("DIRECTOR_JOB_OLD"))){
                        havechairman = true;
                    }
                    String jobName = dictionaryService.getDicNames("ssdjzw", director.get("DIRECTOR_JOB_OLD").toString());
                    director.put("DIRECTOR_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("havechairman", havechairman);
            
            request.setAttribute("directorList", directorList);
        }
        // 董事信息（变更）
        boolean haveNewChairman = false;//是否有董事长,有董事长代表设立董事会
        String DIRECTOR_JSON_CHANGE = busRecord.get("DIRECTOR_JSON_CHANGE") == null ? ""
                : busRecord.get("DIRECTOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON_CHANGE)) {
            List<Map<String, Object>> directorChangeList = JSON.parseObject(DIRECTOR_JSON_CHANGE, List.class);
            
            //变更职务转码
            for(Map<String,Object> director : directorChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", director.get("DIRECTOR_JOB").toString());
                director.put("DIRECTOR_JOB_NAME", jobName);
                if(director.get("DIRECTOR_JOB")!=null){
                    if("01".equals(director.get("DIRECTOR_JOB"))){
                        haveNewChairman = true;
                    }
                }
            }
            request.setAttribute("haveNewChairman", haveNewChairman);
            request.setAttribute("directorChangeList", directorChangeList);
        }
        // 监事信息
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            
            //变更职务转码
            for(Map<String,Object> supervisor : supervisorList){
                if(supervisor.get("SUPERVISOR_JOB_OLD")!=null){
                    String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB_OLD").toString());
                    supervisor.put("SUPERVISOR_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("supervisorList", supervisorList);
        }
        // 监事信息(变更)
        String SUPERVISOR_JSON_CHANGE = busRecord.get("SUPERVISOR_JSON_CHANGE") == null ? ""
                : busRecord.get("SUPERVISOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON_CHANGE)) {
            List<Map<String, Object>> supervisorChangeList = JSON.parseObject(SUPERVISOR_JSON_CHANGE, List.class);
            
            //变更职务转码
            for(Map<String,Object> supervisor : supervisorChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB").toString());
                supervisor.put("SUPERVISOR_JOB_NAME", jobName);
            }
            request.setAttribute("supervisorChangeList", supervisorChangeList);
        }
        // 经理信息
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            
            //变更职务转码
            for(Map<String,Object> manager : managerList){
                if(manager.get("MANAGER_JOB_OLD")!=null){
                    String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB_OLD").toString());
                    manager.put("MANAGER_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("managerList", managerList);
        }
        // 经理信息(变更)
        String MANAGER_JSON_CHANGE = busRecord.get("MANAGER_JSON_CHANGE") == null ? ""
                : busRecord.get("MANAGER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON_CHANGE)) {
            List<Map<String, Object>> managerChangeList = JSON.parseObject(MANAGER_JSON_CHANGE, List.class);
            
            //变更职务转码
            for(Map<String,Object> manager : managerChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB").toString());
                manager.put("MANAGER_JOB_NAME", jobName);
            }
            request.setAttribute("managerChangeList", managerChangeList);
        }
        // 外商投资企业法律文件送达授权委托书基本信息
        String ATTORNEY_JSON = busRecord.get("ATTORNEY_JSON") == null ? "" : busRecord.get("ATTORNEY_JSON").toString();
        if (StringUtils.isNotEmpty(ATTORNEY_JSON)) {
            List<Map<String, Object>> attorneyList = JSON.parseObject(ATTORNEY_JSON, List.class);
            request.setAttribute("attorneyList", attorneyList);
        }

        // 家庭成员信息
        String FAMILY_JSON = busRecord.get("FAMILY_JSON") == null ? "" : busRecord.get("FAMILY_JSON").toString();
        if (StringUtils.isNotEmpty(FAMILY_JSON)) {
            List<Map<String, Object>> familyList = JSON.parseObject(FAMILY_JSON, List.class);
            request.setAttribute("familyList", familyList);
        }

        // 外商投资企业最终实际控制人信息
        String CONTROLLER_JSON = busRecord.get("CONTROLLER_JSON") == null ? ""
                : busRecord.get("CONTROLLER_JSON").toString();
        if (StringUtils.isNotEmpty(CONTROLLER_JSON)) {
            List<Map<String, Object>> controllerList = JSON.parseObject(CONTROLLER_JSON, List.class);
            request.setAttribute("controllerList", controllerList);
        }

        // 执行事务合伙人
        String PARTNER_JSON = busRecord.get("PARTNER_JSON") == null ? "" : busRecord.get("PARTNER_JSON").toString();
        if (StringUtils.isNotEmpty(PARTNER_JSON)) {
            List<Map<String, Object>> pratnerList = JSON.parseObject(PARTNER_JSON, List.class);
            request.setAttribute("pratnerList", pratnerList);
        }
        // 获取房地产经纪机构及其分支机构备案JSON
        String FDCJJJG_JSON = busRecord.get("FDCJJJG_JSON") == null ? "" : busRecord.get("FDCJJJG_JSON").toString();
        if (StringUtils.isNotEmpty(FDCJJJG_JSON)) {
            List<Map<String, Object>> fdcjjgList = JSON.parseObject(FDCJJJG_JSON, List.class);
            request.setAttribute("fdcjjgList", fdcjjgList);
        }
        // 广告发布单位广告从业、审查人员JSON
        String GGFBDW_JSON = busRecord.get("GGFBDW_JSON") == null ? "" : busRecord.get("GGFBDW_JSON").toString();
        if (StringUtils.isNotEmpty(GGFBDW_JSON)) {
            List<Map<String, Object>> ggfbdwList = JSON.parseObject(GGFBDW_JSON, List.class);
            request.setAttribute("ggfbdwList", ggfbdwList);
        }
        // 工程造价咨询企业设立分支机构备案JSON
        String GCZJZXQYSL_JSON = busRecord.get("GCZJZXQYSL_JSON") == null ? ""
                : busRecord.get("GCZJZXQYSL_JSON").toString();
        if (StringUtils.isNotEmpty(GCZJZXQYSL_JSON)) {
            List<Map<String, Object>> gczjzxqyslList = JSON.parseObject(GCZJZXQYSL_JSON, List.class);
            request.setAttribute("gczjzxqyslList", gczjzxqyslList);
        }

        // 银行账号信息及委托扣款协议JSON
        String ACCOUNTANDAGREEMENTJSON = busRecord.get("ACCOUNTANDAGREEMENTJSON") == null ? ""
                : busRecord.get("ACCOUNTANDAGREEMENTJSON").toString();
        if (StringUtils.isNotEmpty(ACCOUNTANDAGREEMENTJSON)) {
            List<Map<String, Object>> accountAndAgreementList = JSON.parseObject(ACCOUNTANDAGREEMENTJSON, List.class);
            
            request.setAttribute("accountAndAgreementList", accountAndAgreementList);
        }
        // 申领发票JSON
        String APPLYINVOICE_JSON = busRecord.get("APPLYINVOICE_JSON") == null ? ""
                : busRecord.get("APPLYINVOICE_JSON").toString();
        if (StringUtils.isNotEmpty(APPLYINVOICE_JSON)) {
            List<Map<String, Object>> invoiceapplyList = JSON.parseObject(APPLYINVOICE_JSON, List.class);
            
            request.setAttribute("invoiceapplyList", invoiceapplyList);
        }
    }

    /**
     * 执行流程方法
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "submitApply")
    @ResponseBody
    public void submitApply(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            // 获取当前申报用户
            Map<String, Object> curMemeber = AppUtil.getLoginMember();
            // 获取申报号
            String EFLOW_CURRENTTASK_ID = request.getParameter("EFLOW_CURRENTTASK_ID");
            if (StringUtils.isEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("EFLOW_CREATORID", curMemeber.get("USER_ID"));
                variables.put("EFLOW_CREATORACCOUNT", curMemeber.get("YHZH"));
                variables.put("EFLOW_CREATORNAME", curMemeber.get("YHMC"));
                variables.put("EFLOW_CREATORPHONE", curMemeber.get("SJHM"));
                variables.put("EFLOW_ASSIGNER_TYPE", AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
            }
            // 获取当前节点
            String EFLOW_CUREXERUNNINGNODENAMES = request.getParameter("EFLOW_CUREXERUNNINGNODENAMES");
            if (StringUtils.isNotEmpty(EFLOW_CUREXERUNNINGNODENAMES) && EFLOW_CUREXERUNNINGNODENAMES.equals("开始")) {
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            // 定义流程标题值
            String eflowSubject = null;
            String itemName = (String) variables.get("ITEM_NAME");
            // 企业名称
            String COMPANY_NAME = (String) variables.get("COMPANY_NAME");
            // 获取申报名称
            String sbmc = (String) variables.get("SBMC");
            StringBuffer subject = new StringBuffer("");
            if (StringUtils.isNotEmpty(sbmc)) {
                if (StringUtils.isNotEmpty(COMPANY_NAME)) {
                    subject.append(sbmc).append("（").append(COMPANY_NAME).append("）");
                } else {
                    subject.append(sbmc).append("（").append(itemName).append("）");
                }
            } else {
                String projectName = (String) variables.get("PROJECT_NAME");
                subject.append(projectName).append("（").append(itemName).append("）");
            }

            eflowSubject = subject.toString();
            variables.put("EFLOW_SUBJECT", eflowSubject);
            variables = jbpmService.exeFrontFlow(variables);
            variables.put("OPER_SUCCESS", true);
            String isTempSave = (String) variables.get("EFLOW_ISTEMPSAVE");
            if (StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("OPER_MSG", "提交成功!");
            } else {
                String msg = "申报成功,申报号是:" + (String) variables.get("EFLOW_EXEID");
                if (isTempSave.equals("-1")) {
                    if ((boolean) variables.get("isStartFlow")) {//非暂存且首次提交进行EMS下单
                        try {
                            msg = executionService.sendEms(variables, msg);
                        } catch (Exception e) {
                            log.error("提交流程Ems生成失败" + e.getMessage());
                        }
                    }  
                }
                variables.put("OPER_MSG",msg);
            }
            // 商事在线表单处理
            String ssitemCode = "201605170002XK00101,201605170002XK00102,201605170002XK00103";
            String itemCode = request.getParameter("ITEM_CODE");
            Map<String, Object> updOnlineMater = new HashMap<String, Object>();
            updOnlineMater.put("EXE_ID", variables.get("EFLOW_EXEID"));
            if (ssitemCode.contains(itemCode)) {
                String onlineMaterJson = request.getParameter("onlineMaterIdJson");
                if (StringUtils.isNotEmpty(onlineMaterJson)) {
                    JSONArray jsonArray = JSONArray.fromObject(onlineMaterJson);

                    List<Map<String, Object>> mapListJson = (List) jsonArray;
                    for (int i = 0; i < mapListJson.size(); i++) {
                        Map<String, Object> obj = mapListJson.get(i);

                        for (Entry<String, Object> entry : obj.entrySet()) {
                            String tableName = entry.getKey();
                            String recordId = (String) entry.getValue();
                            jbpmService.saveOrUpdate(updOnlineMater, tableName, recordId);
                        }
                    }
                }
            }
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            String signMsg=StringUtil.getString(variables.get("SIGN_MSG"));//不动产全程网办-签章判断
            if(StringUtils.isNotEmpty(signMsg)){
                variables.put("OPER_MSG",signMsg);
            }else{
                variables.put("OPER_MSG", "提交失败,请联系网站管理员!");
            }
            log.error(e.getMessage(), e);
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);

    }

    /**
     * 
     * 
     * 加载角色选择器信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadbsSearch")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        getALLbsSearch(request, response);
    }

    /**
     * 执行网站的跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping("/allItemSearch")
    public ModelAndView allItemSearch(HttpServletRequest request) {
        String itemName = request.getParameter("itemName");
        request.setAttribute("itemName", itemName);
        return new ModelAndView("website/bsdt/allItemSearch");
    }

    /**
     * 
     * 描述 办事进度查询
     * 
     * @author Faker Li
     * @created 2015年12月2日 下午17:21:24
     * @param request
     * @param response
     */
    @RequestMapping("/bsjdcx")
    public void bsjdcx(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String exeId = request.getParameter("exeId");
        String bjcxmm = (String) request.getParameter("bscxmm").trim();
        // 获取流程实例信息
        Map<String, Object> flowExe = null;
        flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID", "BJCXMM" },
                new Object[] { exeId, bjcxmm });
        /*已归档流程的查看*/
        if (flowExe == null) {
            flowExe = executionService.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID", "BJCXMM" },
                    new Object[] { exeId, bjcxmm });
        }
        if (flowExe != null) {
            flowExe.put("success", true);
            if (flowExe.get("CUR_STEPNAMES") == null) {
                String defId = (String) flowExe.get("DEF_ID");
                int flowVersion = Integer.parseInt(flowExe.get("DEF_VERSION").toString());
                String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                flowExe.put("CUR_STEPNAMES", startNodeName);
            } else {
                flowExe.put("CUR_STEPNAMES", flowExe.get("CUR_STEPNAMES").toString().split(",")[0]);
            }
            // 获取办理状态
            String runStatus = flowExe.get("RUN_STATUS").toString();
            if (!runStatus.equals("0") && !runStatus.equals("1")) {
                flowExe.put("CUR_STEPNAMES", "已办结");
            }
            Map<String, Object> flowMappedInfo = flowMappedService.getFlowMapInfo((String) flowExe.get("DEF_ID"),
                    flowExe.get("CUR_STEPNAMES").toString().split(",")[0], "2");
            if (flowMappedInfo != null) {
                flowExe.put("YS_NAME", flowMappedInfo.get("YS_NAME"));
            }
        } else {
            flowExe = new HashMap<String, Object>();
            flowExe.put("success", false);
        }
        JsonUtil.printJson(response, flowExe);
    }

    /**
     * 
     * 描述 办事进度查询
     * 
     * @author Faker Li
     * @created 2015年12月2日 下午17:21:24
     * @param request
     * @param response
     */
    @RequestMapping(params = "bdcdjzlcxbr")
    @ResponseBody
    public Map<String, Object> bdcdjzlcxbr(HttpServletRequest request, HttpServletResponse response) {
        String estatePropertyWordId = request.getParameter("exeId");
        String token=" 41baf188e1f5df2517add6bc55440b09";
        String dealToken="Bearer "+token;
        String url="http://192.168.144.83:8080/ShBdcApi5_798967r/v1.0/ShBdcApi5_798967r?"
                + "ESTATE_PROPERTY_WORD_ID='"+estatePropertyWordId+"'";
        String json = "";
        try {
             json = readByget(url, dealToken);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        JSONArray decodeJson = JSONArray.fromObject(json);
        
        Map<String, Object> variables = null;
        variables = (Map<String, Object>) JSON.parse(decodeJson.get(0).toString());
        variables.put("CALLED_PARTY", "大数据中心");
        return variables;
    }


    public static String readByget(String inUrl,String token) throws IOException{
        StringBuffer sbf= new StringBuffer();
        String strRead = null;
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        try{
            URL url =new URL(inUrl);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            if(token!=null)
                connection.setRequestProperty("Authorization", token);
            connection.getInputStream();
            connection.connect();
            InputStream is =connection.getInputStream();
            reader =new BufferedReader(new InputStreamReader(is,"utf-8"));
            while((strRead=reader.readLine())!=null){
                sbf.append(strRead);
                sbf.append("\r\n");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return sbf.toString();  
    }   

    
    
    /**
     * 
     * 描述  排队查询
     * @author Kester Chen
     * @created 2019年1月24日 上午9:46:22
     * @param request
     * @param response
     */
    @RequestMapping("/bdcx")
    public void bdcx(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String lineNo = request.getParameter("lineNo");
        // 获取该排队号排队信息
        Map<String, Object> lineInfo = null;
        lineInfo = newCallService.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "LINE_NO","CALL_STATUS" },
                new Object[] { lineNo,"0" });
        if (lineInfo != null) {
            lineInfo.put("success", true);
            String businessCode = (String) lineInfo.get("BUSINESS_CODE");
            List<Map<String, Object>> list = newCallService.getBeforByLineNo(businessCode,lineNo);
            lineInfo.put("PDNUM", list.size());
        } else {
            lineInfo = new HashMap<String, Object>();
            lineInfo.put("success", false);
        }
        JsonUtil.printJson(response, lineInfo);
    }
    /**
     * 
     * 描述  业务排队查询
     * @author Kester Chen
     * @created 2019年1月24日 上午9:46:22
     * @param request
     * @param response
     */
    @RequestMapping("/ywbdcx")
    public void ywbdcx(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = newCallService.findLineUpNumBySqlFilter(filter);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 获取退回办件信息数据
     * 
     * @author Faker Li
     * @created 2015年12月3日 下午4:41:52
     * @param request
     * @return
     */
    @RequestMapping("/thbjxx")
    public ModelAndView thbjxx(HttpServletRequest request) {
        String bjxxId = request.getParameter("BJID");
        Map<String, Object> bjxx = bjxxService.getByJdbc("T_WSBS_BJXX", new String[] { "BJXX_ID" },
                new Object[] { bjxxId });
        List bjclList = JSON.parseArray((String) bjxx.get("BJCLLB"), Map.class);
        bjxx.put("bjclList", bjclList);
        request.setAttribute("bjxx", bjxx);
        return new ModelAndView("website/yhzx/bjxx");
    }
    /**
     *
     * 描述 获取退回办件信息数据
     *
     * @author Faker Li
     * @created 2015年12月3日 下午4:41:52
     * @param request
     * @return
     */
    @RequestMapping("/showOpinion")
    public ModelAndView showOpinion(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        StringBuffer sql=new StringBuffer("select * from jbpm6_task t ");
        sql.append("  where t.exe_id=? and t.handle_opinion is not null ");
        sql.append(" and t.task_nature is not null ");
        sql.append(" and t.handle_opinion !='同意' and  t.handle_opinion !='通过'  ");
        sql.append(" order by t.create_time desc ");
        List<Map<String, Object>> opinionList = exeDataService.findListBySql(sql.toString(),
                new Object[]{exeId},null);
        request.setAttribute("opinionList", opinionList);
        return new ModelAndView("website/yhzx/showOpinion");
    }
    /**
     * 
     * 描述 办事统计
     * 
     * @author Faker Li
     * @created 2015年12月2日 下午17:21:24
     * @param request
     * @param response
     */
    @RequestMapping("/bjtj")
    public void bjtj(HttpServletRequest request, HttpServletResponse response) {
        // 获取流程实例信息
        Map<String, Object> bjtj = new HashMap<String, Object>();
        String zrtime = DateTimeUtil.getStrOfDate(DateTimeUtil.getNextDay(new Date(), -1), "yyyy-MM-dd");
        int sjzs = executionService.getTjByStateAndTime("", "");
        int bjzs = executionService.getTjByStateAndTime("", "0");
        int zrsj = executionService.getTjByStateAndTime(zrtime, "");
        int zrbj = executionService.getTjByStateAndTime(zrtime, "0");
        bjtj.put("sjzs", sjzs);
        bjtj.put("bjzs", bjzs);
        bjtj.put("zrsj", zrsj);
        bjtj.put("zrbj", zrbj);
        // 新增本年、本月的收件和办结统计
        String curYear = DateTimeUtil.getCurrentYear() + "-";
        int m = DateTimeUtil.getCurrentMonth();
        String mon = "";
        if (m < 10) {
            mon = "0" + m;
        } else {
            mon = m + "";
        }
        String curMon = curYear + mon;
        int ndsj = executionService.getTjByStateAndTime(curYear, "");
        int ndbj = executionService.getTjByEndTime(curYear, "2");
        int bysj = executionService.getTjByStateAndTime(curMon, "");
        int bybj = executionService.getTjByEndTime(curMon, "2");
        bjtj.put("ndsj", ndsj);
        bjtj.put("ndbj", ndbj);
        bjtj.put("bysj", bysj);
        bjtj.put("bybj", bybj);
        JsonUtil.printJson(response, bjtj);
    }

    /**
     * 
     * 描述 跳转到回执明细
     * 
     * @author Faker Li
     * @created 2015年12月3日 下午4:41:52
     * @param request
     * @return
     */
    @RequestMapping("/hzxx")
    public ModelAndView hzxx(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> fileList = fileAttachService.findByExeId(exeId, "1");
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
        // 消防设计流程生成材料
        String tableName = (String) flowExe.get("BUS_TABLENAME") + "_SCWS";
        List<Map<String, Object>> xfsjMap = fileAttachService.findByList(tableName,
                (String) flowExe.get("BUS_RECORDID"), "SCWS");
        request.setAttribute("xfsjMap", xfsjMap);
        request.setAttribute("fileList", fileList);
        return new ModelAndView("website/yhzx/hzxx");
    }
    
    /**
     * 跳转到提示填写人防项目信息表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "rftips")
    public ModelAndView rftips(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("projectCode", request.getParameter("projectCode"));
        return new ModelAndView("website/rf/rftips");
    }

    /**
     * 
     * 描述 跳转到结果明细
     * @author Kester Chen
     * @created 2019年6月10日 上午11:08:27
     * @param request
     * @return
     */
    @RequestMapping("/jgxx")
    public ModelAndView jgxx(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> fileList = fileAttachService.findResByExeId(exeId);
        request.setAttribute("fileList", fileList);
        return new ModelAndView("website/yhzx/jgxx");
    }

    /**
     * 
     * 描述 首页我要办跳转
     * 
     * @author Faker Li
     * @created 2015年12月9日 下午3:47:14
     * @param request
     * @return
     */
    @RequestMapping("/bscxlb")
    public ModelAndView bscxlb(HttpServletRequest request) {
        String busTypeId = request.getParameter("busTypeId");
        String busTypeName = request.getParameter("busTypeName");
        request.setAttribute("busTypeIds", busTypeId);
        request.setAttribute("busTypeName", busTypeName);
        return new ModelAndView("website/bsdt/bscxlb");
    }

    /**
     * 
     * 描述 表格下载查找自动补全
     * 
     * @author Faker Li
     * @created 2015年12月17日 上午9:21:02
     * @param request
     * @param response
     */
    @RequestMapping("/loadbgxzSearch")
    public void loadbgxzSearch(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = applyMaterService.findAllBgxzList();
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("MATER_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 常见问题查找自动补全
     * 
     * @author Faker Li
     * @created 2015年12月17日 上午9:21:02
     * @param request
     * @param response
     */
    @RequestMapping("/loadcjwtSearch")
    public void loadcjwtSearch(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = commonProblemService.findAllCjwtList();
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("PROBLEM_TITLE")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 加载前台投资项目基本信息
     * 
     * @author Rider Chen
     * @created 2015-12-22 下午05:26:43
     * @param request
     * @param response
     */
    @RequestMapping("/loadTZXMXXData")
    public void loadTZXMXXData(HttpServletRequest request, HttpServletResponse response) {
        String projectCode = request.getParameter("projectCode");

        Properties projectProperties = FileUtil.readProperties("conf/config.properties");
        String isNetwork = projectProperties.getProperty("isNetwork");
        String internetAddress = projectProperties.getProperty("internetAddress");
        // 公用网络区
        if (StringUtils.isNotEmpty(isNetwork) && isNetwork.equals("1")) {
            String json = HttpRequestUtil.sendPost(internetAddress + "webSiteController/loadTZXMXXData.do",
                    "projectCode=" + projectCode, "UTF-8");
            this.setJsonString(json, response);
        } else {
            TzProjectRespones tzprojectRespones = swbInterfaceService.getTZXMXXData(projectCode);
            String json = JSON.toJSONString(tzprojectRespones);
            this.setJsonString(json, response);
        }
    }

    /**
     *
     * 描述 加载项目基本信息
     *
     * @author Rider Chen
     * @created 2015-12-22 下午05:26:43
     * @param request
     * @param response
     */
    @RequestMapping("/loadXMJBXXBData")
    public void loadXMJBXXBData(HttpServletRequest request, HttpServletResponse response) {
        String projectCode = request.getParameter("projectCode");
        Map<String,Object>  tzprojectInfo = projectService.getXMJBXXBData(projectCode);
        String json = JSON.toJSONString(tzprojectInfo);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 
     * 加载APP办事事项自动补全数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/loadAPPbsSearch")
    public void loadAPPbsSearch(HttpServletRequest request, HttpServletResponse response) {
        getALLbsSearch(request, response);
    }

    /**
     * 
     * 描述 获取所有办事的事项的补全名称
     * 
     * @author Faker Li
     * @created 2016年1月20日 上午11:28:23
     * @param request
     * @param response
     */
    public void getALLbsSearch(HttpServletRequest request, HttpServletResponse response) {
        String busType = request.getParameter("busType");
        List<Map<String, Object>> list = serviceItemService.findALLPublishItemsFront(busType);
        for (Map<String, Object> map : list) {
            map.put("PINYIN", StringUtil.getFirstLetter((String) map.get("ITEM_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述 根据部门编码获取头15条已办结数据
     * 
     * @author Faker Li
     * @created 2016年1月28日 下午2:18:20
     * @param request
     * @param response
     */
    @RequestMapping("/getEndExecutionByDepartCode")
    public void getEndExecutionByDepartCode(HttpServletRequest request, HttpServletResponse response) {
        String departCode = request.getParameter("departCode");
        if (StringUtils.isNotEmpty(departCode)) {
            List<Map<String, Object>> list = executionService.getEndExecutionByDepartCode(departCode);
            String json = "jsoncallback(" + JSON.toJSONString(list) + ")";
            this.setJsonString(json, response);
        }
    }

    /**
     * 
     * 描述 导出EXCEL
     * 
     * @author Rider Chen
     * @created 2016-3-8 上午09:27:30
     * @param request
     * @param response
     *            @RequestMapping("/exportExcel") public void
     *            exportExcel(HttpServletRequest request, HttpServletResponse
     *            response) { List<ExcelReplaceDataVO> datas = new ArrayList
     *            <ExcelReplaceDataVO>();
     * 
     *            // 找到第0行第1列的"title"，用"平潭综合实验区行政服务中心部门办件情况统计表"替换掉"title"
     *            ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
     *            vo1.setRow(0); vo1.setColumn(0); vo1.setKey("${title}");
     *            vo1.setValue("平潭综合实验区行政服务中心部门办件情况统计表");
     * 
     *            // 找到第2行第1列的times，用"2012-06-28--2016-03-07"替换掉times
     *            ExcelReplaceDataVO vo2 = new ExcelReplaceDataVO();
     *            vo2.setRow(1); vo2.setColumn(0); vo2.setKey("${times}");
     *            vo2.setValue("2012-06-28--2016-03-07");
     * 
     *            // 找到第2行第1列的times，用"2012-06-28--2016-03-07"替换掉times
     *            ExcelReplaceDataVO vo3 = new ExcelReplaceDataVO();
     *            vo3.setRow(3); vo3.setColumn(2); vo3.setKey("${1111}");
     *            vo3.setValue("即办件 (A1)");
     * 
     *            datas.add(vo1); datas.add(vo2); datas.add(vo3);
     *            ExcelUtil.replaceModelHSSF(datas,
     *            "e:\\template\\111111111111.xls",response,"test.xls"); }
     */
    /**
     * 执行流程方法
     * 
     * @param request
     * @return
     */
    @RequestMapping("/andSubmitApply")
    @ResponseBody
    public void andSubmitApply(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            String yhzh = request.getParameter("YHZH");
            // 获取用户密码
            String dlmm = request.getParameter("DLMM");
            String password = StringUtil.getMd5Encode(dlmm);
            Map<String, Object> curMemeber = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[] { "YHZH", "DLMM" }, new Object[] { yhzh, password });
            // 获取申报号
            String EFLOW_CURRENTTASK_ID = request.getParameter("EFLOW_CURRENTTASK_ID");
            if (StringUtils.isEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("EFLOW_CREATORID", curMemeber.get("USER_ID"));
                variables.put("EFLOW_CREATORACCOUNT", curMemeber.get("YHZH"));
                variables.put("EFLOW_CREATORNAME", curMemeber.get("YHMC"));
                variables.put("EFLOW_CREATORPHONE", curMemeber.get("SJHM"));
                variables.put("EFLOW_ASSIGNER_TYPE", AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
            }
            // 定义流程标题值
            String eflowSubject = null;
            String itemName = (String) variables.get("ITEM_NAME");
            // 获取申报名称
            String sbmc = (String) variables.get("SBMC");
            StringBuffer subject = new StringBuffer("");
            if (StringUtils.isNotEmpty(sbmc)) {
                subject.append(sbmc).append("(").append(itemName).append(")");
            } else {
                String projectName = (String) variables.get("PROJECT_NAME");
                subject.append(projectName).append("(").append(itemName).append(")");
            }
            eflowSubject = subject.toString();
            variables.put("EFLOW_SUBJECT", eflowSubject);
            variables = jbpmService.exeFrontFlow(variables);
            variables.put("OPER_SUCCESS", true);
            if (StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("OPER_MSG", "提交成功!");
            } else {
                variables.put("OPER_MSG", "申报成功,申报号是:" + (String) variables.get("EFLOW_EXEID"));
            }
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);           
            variables.put("OPER_MSG", "提交失败,请联系网站管理员!");          
            log.error(e.getMessage());
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);

    }

    /**
     * 执行平潭网格办事的跳转
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/ptwgView")
    public ModelAndView ptwgView(HttpServletRequest request) throws UnsupportedEncodingException {
        String returnUrl = request.getParameter("returnUrl");
        if (StringUtils.isNotEmpty(returnUrl)) {
            request.setAttribute("returnUrl", URLDecoder.decode(returnUrl, "UTF-8"));
        }
        if (returnUrl.indexOf("http://") == -1 && returnUrl.indexOf("https://") == -1) {
            returnUrl = "http://" + returnUrl;
        }
        Map<String, Object> userInfo = saveUser(request);
        if (null != userInfo) {
            HttpSession session = AppUtil.getSession();
            AppUtil.addMemberToSession(session, userInfo);
        }
        return new ModelAndView("redirect:" + returnUrl);
    }

    /**
     * 
     * 描述 注册公众用户
     * 
     * @author Rider Chen
     * @created 2016年8月12日 上午10:11:39
     * @param request
     * @return
     */
    public Map<String, Object> saveUser(HttpServletRequest request) {
        String entityId = request.getParameter("USER_ID");
        String uid = request.getParameter("uid");
        String cn = request.getParameter("cn");
        String phone = request.getParameter("phone");
        String deptid = request.getParameter("deptid");
        String password = StringUtil.getMd5Encode("123456");
        Map<String, Object> variables = new HashMap<String, Object>();
        Map<String, Object> userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                new Object[] { uid });
        if (StringUtils.isNotEmpty(uid) && null == userInfo) {
            variables.put("ZJLX", "JGDM");
            variables.put("ZJHM", deptid);
            variables.put("USER_TYPE", "1");
            variables.put("YHZH", uid);
            variables.put("DLMM", password);
            variables.put("YHZT", "1");
            variables.put("YHMC", cn);
            variables.put("YHXB", 1);
            variables.put("SJHM", phone);
            String recordId = userInfoService.saveOrUpdate(variables, "T_BSFW_USERINFO", entityId);
            userInfo = userInfoService.getByJdbc("T_BSFW_USERINFO", new String[] { "USER_ID" },
                    new Object[] { recordId });
        }
        return userInfo;
    }

    /**
     * 跳转到商事登记网上申报(五证合一)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "wssbSelect")
    public ModelAndView wssbSelect(HttpServletRequest request) {
        return new ModelAndView("website/zzhy/wssbSelect");
    }

    /**
     * 跳转到商事登记网上申报(五证合一)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(params = "zzhywssb")
    public ModelAndView zzhywssb(HttpServletRequest request) {
        String itemCodes = request.getParameter("itemCodes");
        String itemNames = request.getParameter("itemNames");
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        
        List<Map> compTypeList = GenPlatReqUtil.findDatas("pt_comptype_list",null);
        List<Map> list = new ArrayList<Map>();
        for(Map compType : compTypeList){
            if(!"bbc".equals(compType.get("COMTYPE_CODE")) 
                    && !"yxzrgs".equals(compType.get("COMTYPE_CODE"))){
                list.add(compType);
            }
        }
        request.setAttribute("compTypeList", list);
        return new ModelAndView("website/zzhy/comtype_selector");
    }
    /**
     * 跳转到商事登记网上申报(秒批)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "wssbmp")
    public ModelAndView wssbmp(HttpServletRequest request) {
        String itemCodes = request.getParameter("itemCodes");
        String itemNames = request.getParameter("itemNames");
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        return new ModelAndView("website/zzhy/wssbmp");
    }
    
    /**
     * 跳转到商事登记网上申报(变更（备案）、注销)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bgzx")
    public ModelAndView bgzx(HttpServletRequest request) {
        String itemCodes = request.getParameter("itemCodes");
        String itemNames = request.getParameter("itemNames");
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        
        List<Map> compTypeList = GenPlatReqUtil.findDatas("pt_comptype_list",null);
        List<Map> list = new ArrayList<Map>();
        for(Map compType : compTypeList){
            if("bbc".equals(compType.get("COMTYPE_CODE"))){
                list.add(compType);
            }
        }
        request.setAttribute("compTypeList", list);
        
        return new ModelAndView("website/zzhy/comtype_selector_change");
    }
    /**
     * 跳转到1+N证合一
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "relateItemSelect")
    public ModelAndView relateItemSelect(HttpServletRequest request) {
        return new ModelAndView("website/zzhy/relateItemSelect");
    }

    /**
     * 跳转到材料下载
     * 
     * @param request
     * @return
     */
    @RequestMapping("/clxz")
    public ModelAndView clxz(HttpServletRequest request) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        //下载材料类型
        String clxzlx = request.getParameter("clxzlx");
        // String isQueryDetail = request.getParameter("isQueryDetail");
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // 获取材料信息列表
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            Map<String, Object> eflowObj = null;
            if (flowAllObj.get("busRecord") != null) {
                Map<String, Object> busRecord = (Map<String, Object>) flowAllObj.get("busRecord");
                // 设置商事登记事项参数
                setCompany(request, busRecord);
                // 设置商事登记全程网办事项参数
                setCompanyQcwb(request, busRecord);
                request.setAttribute("busRecord", busRecord);
            }
            if (flowAllObj.get("EFLOWOBJ") != null) {
                eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
                if (StringUtils.isNotEmpty(exeId)) {
                    String busRecordId = (String) eflowObj.get("EFLOW_BUSRECORDID");
                    String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
                    applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
                }
                request.setAttribute("EFLOWOBJ", eflowObj);
            }
            request.setAttribute("applyMaters", applyMaters);
        }
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("exeId", exeId);
        if(StringUtils.isNotEmpty(clxzlx)&&clxzlx.equals("bg")){
            return new ModelAndView("website/applyforms/ssqcwb/change/bgclxz");
        }else{
            return new ModelAndView("website/yhzx/clxz");
        }
    }

    /**
     * 描述:商事材料下载，给devbase提供的接口
     *
     * @author Madison You
     * @created 2020/7/15 10:01:00
     * @param
     * @return
     */
    @RequestMapping("/clxzForDevbase")
    @ResponseBody
    public Map<String, Object> clxzForDevbase(HttpServletRequest request) {
        String itemCode = request.getParameter("itemCode");
        String exeId = request.getParameter("exeId");
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取项目ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // 获取材料信息列表
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            Map<String, Object> eflowObj = null;
            Map<String, Object> busRecord = new HashMap<String, Object>();
            if (flowAllObj.get("busRecord") != null) {
                busRecord = (Map<String, Object>) flowAllObj.get("busRecord");
                returnMap.put("busRecord", busRecord);
            }
            if (flowAllObj.get("EFLOWOBJ") != null) {
                eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
                if (StringUtils.isNotEmpty(exeId)) {
                    String busRecordId = (String) eflowObj.get("EFLOW_BUSRECORDID");
                    String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
                    applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
                    //设置商事变更登记参数
                    if("T_COMMERCIAL_CHANGE_DOMESTIC".equals(busTableName)){
                        setCommercialChange(busRecord);
                    }
                }
                returnMap.put("EFLOWOBJ", eflowObj);
               
            }
            returnMap.put("applyMaters", applyMaters);
        }
        return returnMap;
    }
    
    
    /**
     * 
     * 描述    商事登记变更参数
     * @author Allin Lin
     * @created 2021年8月4日 下午3:19:02
     * @param request
     * @param busRecord
     */
    private void setCommercialChange(Map<String, Object> busRecord) {

        String COMPANY_TYPE = busRecord.get("COMPANY_TYPE") == null ? "" : busRecord.get("COMPANY_TYPE").toString();
        String COMPANY_TYPE_CHANGE = busRecord.get("COMPANY_TYPE_CHANGE") == null ? ""
                : busRecord.get("COMPANY_TYPE_CHANGE").toString();
        // 变更公司类型
        if (StringUtils.isNotEmpty(COMPANY_TYPE)) {
            Map<String, Object> type = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[] { "TYPE_CODE" }, new Object[] { COMPANY_TYPE });
            if (type != null) {
                busRecord.put("COMPANY_TYPE_NAME", type.get("TYPE_NAME"));
            }
        }
        if (StringUtils.isNotEmpty(COMPANY_TYPE_CHANGE)) {
            Map<String, Object> typeChange = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",
                    new String[] { "TYPE_CODE" }, new Object[] { COMPANY_TYPE_CHANGE });
            if (typeChange != null) {
                busRecord.put("COMPANY_TYPE_NAME_CHANGE", typeChange.get("TYPE_NAME"));
            }
        }
        // 涉及变更时，最终股东列表
        List<Map<String, Object>> newHolderList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> yHolderList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> xHolderList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> sameHolderList = new ArrayList<Map<String, Object>>();
        boolean isStockTransfer = false;
        // 股东信息
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            busRecord.put("holderList", holderList);

            // 股权变更旧股东股权判断
            for (Map<String, Object> holder : holderList) {
                if (StringUtils.isNotEmpty(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN")))) {
                    if (Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN"))) > 0) {
                        yHolderList.add(holder);
                    }
                    if (Double.valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS_REMAIN"))) < (Double
                            .valueOf(StringUtil.getString(holder.get("CONTRIBUTIONS"))))) {
                        isStockTransfer = true;
                    }
                }
            }
        }

        busRecord.put("isStockTransfer", isStockTransfer);

        // 股东信息(变更)
        String HOLDER_JSON_CHANGE = busRecord.get("HOLDER_JSON_CHANGE") == null ? ""
                : busRecord.get("HOLDER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON_CHANGE)) {
            List<Map<String, Object>> holderChangeList = JSON.parseObject(HOLDER_JSON_CHANGE, List.class);
            for (Map<String, Object> holder : holderChangeList) {
                String gqlyJson = holder.get("GQLY_JSON").toString();
                List<Map<String, Object>> gqlyList = JSON.parseObject(gqlyJson, List.class);
                holder.put("gqlyList", gqlyList);

                xHolderList.add(holder);
            }
            busRecord.put("holderChangeList", holderChangeList);

        }

        // 剔除新旧股东同名情况
        if (yHolderList.size() > 0 && xHolderList.size() > 0) {
            for (Map<String, Object> xHolder : xHolderList) {
                for (Map<String, Object> yHolder : yHolderList) {
                    if (StringUtil.getString(xHolder.get("SHAREHOLDER_NAME"))
                            .equals(StringUtil.getString(yHolder.get("SHAREHOLDER_NAME")))) {
                        sameHolderList.add(yHolder);
                    }
                }
            }
            if (sameHolderList.size() > 0) {
                for (Map<String, Object> same : sameHolderList) {
                    yHolderList.remove(same);
                }
            }
            newHolderList.addAll(yHolderList);
            newHolderList.addAll(xHolderList);
        } else if (xHolderList.size() <= 0) {
            newHolderList.addAll(yHolderList);
        } else if (yHolderList.size() <= 0) {
            newHolderList.addAll(xHolderList);
        }
        busRecord.put("newHolderList", newHolderList);

        // 董事信息
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);

            // 变更职务转码
            boolean havechairman = false;// 是否有董事长,有董事长代表设立董事会
            for (Map<String, Object> director : directorList) {
                if (director.get("DIRECTOR_JOB_OLD") != null) {
                    if ("01".equals(director.get("DIRECTOR_JOB_OLD"))) {
                        havechairman = true;
                    }
                }
                String jobName = dictionaryService.getDicNames("ssdjzw",
                        StringUtil.getString(director.get("DIRECTOR_JOB_OLD")));
                director.put("DIRECTOR_JOB_OLD_NAME", jobName);
            }
            busRecord.put("havechairman", havechairman);
            busRecord.put("directorList", directorList);
        }
        // 董事信息（变更）
        boolean haveNewChairman = false;// 是否有董事长,有董事长代表设立董事会
        String DIRECTOR_JSON_CHANGE = busRecord.get("DIRECTOR_JSON_CHANGE") == null ? ""
                : busRecord.get("DIRECTOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON_CHANGE)) {
            List<Map<String, Object>> directorChangeList = JSON.parseObject(DIRECTOR_JSON_CHANGE, List.class);

            for (Map<String, Object> directorChange : directorChangeList) {
                String jobName = dictionaryService.getDicNames("ssdjzw", directorChange.get("DIRECTOR_JOB").toString());
                directorChange.put("DIRECTOR_JOB_NAME", jobName);
                if (directorChange.get("DIRECTOR_JOB") != null) {
                    if ("01".equals(directorChange.get("DIRECTOR_JOB"))) {
                        haveNewChairman = true;
                    }
                }
            }
            busRecord.put("directorChangeList", directorChangeList);
        }
        busRecord.put("haveNewChairman", haveNewChairman);

        // 监事信息
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            // 变更职务转码
            for (Map<String, Object> supervisor : supervisorList) {
                if (supervisor.get("SUPERVISOR_JOB_OLD") != null) {
                    String jobName = dictionaryService.getDicNames("ssdjzw",
                            supervisor.get("SUPERVISOR_JOB_OLD").toString());
                    supervisor.put("SUPERVISOR_JOB_OLD_NAME", jobName);
                }
            }
            busRecord.put("supervisorList", supervisorList);
        }
        // 监事信息(变更)
        String SUPERVISOR_JSON_CHANGE = busRecord.get("SUPERVISOR_JSON_CHANGE") == null ? ""
                : busRecord.get("SUPERVISOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON_CHANGE)) {
            List<Map<String, Object>> supervisorChangeList = JSON.parseObject(SUPERVISOR_JSON_CHANGE, List.class);
            // 变更职务转码
            for (Map<String, Object> supervisor : supervisorChangeList) {
                String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB").toString());
                supervisor.put("SUPERVISOR_JOB_NAME", jobName);
            }
            busRecord.put("supervisorChangeList", supervisorChangeList);
        }
        // 经理信息
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            // 变更职务转码
            for (Map<String, Object> manager : managerList) {
                if (manager.get("MANAGER_JOB_OLD") != null) {
                    String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB_OLD").toString());
                    manager.put("MANAGER_JOB_OLD_NAME", jobName);
                }
            }
            busRecord.put("managerList", managerList);

        }
        // 经理信息(变更)
        String MANAGER_JSON_CHANGE = busRecord.get("MANAGER_JSON_CHANGE") == null ? ""
                : busRecord.get("MANAGER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON_CHANGE)) {
            List<Map<String, Object>> managerChangeList = JSON.parseObject(MANAGER_JSON_CHANGE, List.class);
            // 变更职务转码
            for (Map<String, Object> manager : managerChangeList) {
                String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB").toString());
                manager.put("MANAGER_JOB_NAME", jobName);
            }
            busRecord.put("managerChangeList", managerChangeList);
        }
    }

    /**
     * 
     * 描述 获取退回办件信息数据
     * 
     * @author Faker Li
     * @created 2015年12月3日 下午4:41:52
     * @param request
     * @return
     */
    @RequestMapping("/ystjxx")
    public ModelAndView ystjxx(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
//        Map<String, Object> bjxx = bjxxService.getByJdbc("T_WSBS_BJXX", new String[] { "EXE_ID" },
//                new Object[] { exeId });
        Map<String, Object> bjxx = bjxxService.getBjxxInfo(exeId);
        List bjclList = JSON.parseArray((String) bjxx.get("BJCLLB"), Map.class);
        bjxx.put("bjclList", bjclList);
        request.setAttribute("bjxx", bjxx);
        return new ModelAndView("website/yhzx/ystjxx");
    }


    /**
     * 描述:获取环土局项目列表
     *
     * @author Madison You
     * @created 2019/8/19 11:00:00
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/getHTProject")
    public List<Map<String,Object>> getHTProject(HttpServletRequest request) {
        return webSiteService.getHTProject(request);
    }

    /**
     * 描述:获取环土局项目列表详情
     *
     * @author Madison You
     * @created 2019/8/19 12:41:00
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("getHTProjectDetail")
    public Map<String,Object> getHTProjectDetail(HttpServletRequest request) {
        return webSiteService.getHTProjectDetail(request);
    }

    /**
     * 描述:模块内容查询
     *
     * @author Madison You
     * @created 2019/10/17 17:11:00
     * @param
     * @return
     */
    @RequestMapping("/moduleSearch")
    public ModelAndView moduleSearch(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");// 关键字
        String modulId = request.getParameter("moduleId");
        request.setAttribute("key", key);
        request.setAttribute("moduleId", modulId);
        return new ModelAndView("website/index/moduleSearch");
    }

    /**
     * 描述:模块内容查询数据
     *
     * @param
     * @return
     * @author Madison You
     * @created 2019/10/17 17:14:00
     */
    @RequestMapping("/moduleSearchData")
    public void moduleSearchData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        Map<String, Object> mapList = webSiteService.findModuleSearchData(filter);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    @RequestMapping(params = "bdcLjczView")
    public ModelAndView bdcLjczView(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/video_show");
    }
}
