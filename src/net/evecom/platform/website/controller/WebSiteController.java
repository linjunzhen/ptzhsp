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
 * ??????
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015???11???17??? ??????2:44:10
 */
@Controller
@RequestMapping("/webSiteController")
public class WebSiteController extends BaseController {
    /**
     * log??????
     */
    private static Log log = LogFactory.getLog(WebSiteController.class);
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;

    /**
     * ??????newCallService
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
     * ??????Service
     */
    @Resource
    private BjxxService bjxxService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * ??????Service
     */
    @Resource
    private CommonProblemService commonProblemService;

    /**
     * ??????Service
     */
    @Resource
    private SwbInterfaceService swbInterfaceService;
    /**
     * ??????Service
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * ??????Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    /**
     * ??????Service
     */
    @Resource
    private UserInfoService userInfoService;
    /**
     * ??????Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * ??????Service
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * ??????Service
     */
    @Resource
    private XFDesignService xfDesignService;
    /**
     * projectService
     */
    @Resource
    private ProjectService projectService;
    
    /**
     * ?????????????????????
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
            if("website/yhzx/login".equals(navTarget)) {//???????????????????????????????????????????????????
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
     * ?????????????????????
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
     * ?????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "usercenter")
    public ModelAndView usercenter(HttpServletRequest request) {
        Map<String, Object> memeber = AppUtil.getLoginMember();
        String userAccount = (String) memeber.get("YHZH");
        List<Map<String, Object>> userTaskList = flowTaskService.findWebSiteUserTask(userAccount);
        // ????????????MAP
        Map<String, String> mapCount = executionService.getReportCountForWebSite(userAccount);
        mapCount.put("DBLCOUNT", String.valueOf(userTaskList.size()));
        request.setAttribute("userTaskList", userTaskList);
        request.setAttribute("mapCount", mapCount);
        return new ModelAndView("website/yhzx/yhzx");
    }

    /**
     * ?????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "test")
    public ModelAndView test(HttpServletRequest request) {
        // commercialService.getEicpEntInfo("FJPT17010327834");//??????
        commercialService.getEicpEntInfo("FJPT17010527837");// ??????
        return new ModelAndView("website/yhzx/yhzx");
    }

    /**
     * ?????????????????????
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "applyItem")
    public ModelAndView applyItem(HttpServletRequest request) {
        // ????????????????????????
        String itemCode = request.getParameter("itemCode");
        String sssblx = request.getParameter("sssblx");//??????????????????
        String PT_ID = request.getParameter("PT_ID");//??????????????????ID
        request.setAttribute("sssblx", sssblx);
        request.setAttribute("PT_ID", PT_ID);
        // ???????????????
        String exeId = request.getParameter("exeId");
        String typeId= request.getParameter("typeId");
        String stageId=request.getParameter("stageId");
        String isFiled=request.getParameter("isFiled");
        String isQueryDetail = request.getParameter("isQueryDetail");
        //?????????????????????????????????2?????????????????????????????????????????????????????????
        String projectType = request.getParameter("projectType");
        //????????????
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
      
        // ?????????????????????
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
     * ?????????????????????????????????????????????
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "applyItemOfProject")
    public ModelAndView applyItemOfProject(HttpServletRequest request) {
        // ????????????????????????
        String itemCode = request.getParameter("itemCode");
        // ???????????????
        String exeId = request.getParameter("exeId");
        String typeId= request.getParameter("typeId");
        String stageId=request.getParameter("stageId");
        String isQueryDetail = request.getParameter("isQueryDetail");
        //?????????????????????????????????2?????????????????????????????????????????????????????????
        String projectType = request.getParameter("projectType");
        //????????????
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

        // ?????????????????????
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
     * ??????????????????????????????
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
     * ?????????????????????????????????????????????
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
        if(map != null) {  //???????????????????????????????????????????????????????????????
            hasBaseInfo = true;
        }
        result.put("hasBaseInfo", hasBaseInfo);
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * ????????????????????????
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
        //??????????????????
        //Map<String,Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_ID"},
       //         new Object[]{variables.get("TYPE_ID")});
        //variables.put("TYPE_CODE",dicType.get("TYPE_CODE"));
        webSiteService.saveOrUpdate(variables, "T_BSFW_RFXMXXB", entityId);
        j.setMsg("????????????");
        return j;
    }

    @SuppressWarnings("unchecked")
    private ModelAndView ifstnull(HttpServletRequest request, String exeId, String isQueryDetail,
            Map<String, Object> requestParams, Map<String, Object> serviceItem) {
        String formKey = (String) serviceItem.get("FORM_KEY");
        // ????????????KEY
        String defKey = (String) serviceItem.get("DEF_KEY");
        // ????????????ID
        String itemId = (String) serviceItem.get("ITEM_ID");
        // ????????????????????????
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        // ??????????????????????????????????????????
        List<Map<String, Object>> handleTypes = applyMaterService.findHandleTypeListE(itemId);
        // ????????????????????????
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
            // ????????????????????????
            Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE",
                    new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("BUS_RECORDID") });
            if (null != multiple) {
                busRecord.putAll(multiple);
            }
            // ??????????????????????????????
            setCompany(request, busRecord);
            // ??????????????????????????????????????????
            setCompanyQcwb(request, busRecord);
            // ??????????????????????????????????????????
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
                // ????????????????????????
                Map<String, Object> loginMember = AppUtil.getLoginMember();
                eflowObj.put("SHRMC", loginMember.get("YHMC"));
            }
            if (flowExe != null) {
                // ??????????????????
                String runStatus = flowExe.get("RUN_STATUS").toString();
                if (!runStatus.equals("0") && !runStatus.equals("1")) {
                    eflowObj.put("HJMC", "?????????");
                    eflowObj.put("SHRMC", null);
                }
                if (eflowObj.get("SHRMC") == null) {
                    // ????????????????????????
                    Map<String, Object> loginMember = AppUtil.getLoginMember();
                    eflowObj.put("SHRMC", loginMember.get("YHMC"));
                }
                //?????????????????????
                String CREATOR_ACCOUNT = flowExe.get("CREATOR_ACCOUNT").toString();

                Map<String, Object> loginMember = AppUtil.getLoginMember();
                String YHZH = loginMember.get("YHZH").toString();
                if(!CREATOR_ACCOUNT.equals(YHZH)){//????????????????????????????????????????????????????????????
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
        //?????????????????????????????????????????????????????? ??????
        if(null == flowExe && StringUtils.isNotEmpty(projectCode)){
            applyMaterService.setSameUploadFiles(applyMaters, formKey, projectCode);
            applyMaterService.setSameKeyUploadFiles(applyMaters, formKey, projectCode);
        }
        serviceItem.put("APPLY_TYPE", "1");
        /*???????????????????????????????????????????????????UUID*/
        serviceItem.put("CREDIT_FEEDBACK_MARK", UUIDGenerator.getUUID());
        /*??????????????????????????????????????????*/
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

        // ??????????????????JSON
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("handleTypes", handleTypes);
        request.setAttribute("isQueryDetail", isQueryDetail);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        request.setAttribute("requestParams", requestParams);
        // ?????????????????????????????????????????????
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
            busTypenames += "??????";
        }
        if (typeids.contains("4028818c512273e7015122a452220005") || typeids.contains("402883494fd4f3aa014fd4fbe7bf0002")
                || typeids.contains("4028818c512273e7015122c81f850007")
                || typeids.contains("4028818c512273e7015122c872dc0008")) {
            if (busTypenames.length() > 1) {
                busTypenames += ",";
            }
            busTypenames += "??????";
        }
        
        request.setAttribute("busTypenames", busTypenames);
        return new ModelAndView("website/applyforms/" + formKey);
    }
    
    private void setProjectSgxk(HttpServletRequest request, Map<String, Object> busRecord) {
        // ??????????????????????????????????????????
        setRequestList(request, busRecord, "CHARTREVIEWNUM_JSON", "chartreviewnumList");
        // ??????????????????
        setRequestList(request, busRecord, "JSDW_JSON", "jsdwList");
        // ??????????????????
        setRequestList(request, busRecord, "DJDW_JSON", "djdwList");
        // ??????????????????
        setRequestList(request, busRecord, "SGDW_JSON", "sgdwList" ,"SGRY");
        // ??????????????????
        setRequestList(request, busRecord, "JLDW_JSON", "jldwList" ,"JLRY");
        // ??????????????????
        setRequestList(request, busRecord, "KCDW_JSON", "kcdwList");
        // ??????????????????
        setRequestList(request, busRecord, "SJDW_JSON", "sjdwList");
        // ???????????????????????????
        setRequestList(request, busRecord, "SGTSCDW_JSON", "sgtscdwList");
        // ??????????????????????????????????????????????????????
        setRequestList(request, busRecord, "KZJDW_JSON", "kzjdwList");
        // ????????????????????????
        setRequestList(request, busRecord, "ZBDW_JSON", "zbdwList");
        // ??????????????????
        setRequestList(request, busRecord, "JCDW_JSON", "jcdwList");
        // ??????????????????
        setRequestList(request, busRecord, "DWGC_JSON", "dwgcList");
        // ??????????????????
        setRequestList(request, busRecord, "ZJGC_JSON", "zjgcList");
        // ??????????????????
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
     * ?????? ????????????????????????????????????
     * @author Rider Chen
     * @created 2021???3???30??? ??????3:21:57
     * @param request
     * @param busRecord
     */
    @SuppressWarnings("unchecked")
    private void setCompanyQcwb(HttpServletRequest request, Map<String, Object> busRecord) {
        // ???????????????
        String QSZCY_JSON = busRecord.get("QSZCY_JSON") == null ? "" : busRecord.get("QSZCY_JSON").toString();
        if (StringUtils.isNotEmpty(QSZCY_JSON)) {
            List<Map<String, Object>> qszcyxxList = JSON.parseObject(QSZCY_JSON, List.class);
            request.setAttribute("qszcyxxList", qszcyxxList);
        }
        // ???????????????
        String OLD_DIRECTOR_JSON = busRecord.get("OLD_DIRECTOR_JSON") == null ? "" : busRecord.get("OLD_DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            List<Map<String, Object>> oldDirectorList = JSON.parseObject(OLD_DIRECTOR_JSON, List.class);
            request.setAttribute("oldDirectorList", oldDirectorList);
        }
        // ???????????????
        String OLD_SUPERVISOR_JSON = busRecord.get("OLD_SUPERVISOR_JSON") == null ? "" : busRecord.get("OLD_SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_SUPERVISOR_JSON)) {
            List<Map<String, Object>> oldSupervisorList = JSON.parseObject(OLD_SUPERVISOR_JSON, List.class);
            request.setAttribute("oldSupervisorList", oldSupervisorList);
        }
        // ???????????????
        String OLD_MANAGER_JSON = busRecord.get("OLD_MANAGER_JSON") == null ? "" : busRecord.get("OLD_MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_MANAGER_JSON)) {
            List<Map<String, Object>> oldMangerList = JSON.parseObject(OLD_MANAGER_JSON, List.class);
            request.setAttribute("oldMangerList", oldMangerList);
        }
    }
    /**
     * ?????? ??????????????????????????????
     * 
     * @author Rider Chen
     * @created 2016???12???22??? ??????11:46:39
     * @param request
     * @param busRecord
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setCompany(HttpServletRequest request, Map<String, Object> busRecord) {
        // ?????????????????????
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
        // ?????????????????????
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
        //??????????????????
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
        
        //????????????????????????????????????
        List<Map<String,Object>> newHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> yHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> xHolderList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> sameHolderList = new ArrayList<Map<String,Object>>();
        boolean isStockTransfer = false;
        // ????????????
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            request.setAttribute("holderList", holderList);
            
            //?????????????????????????????????
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
        // ????????????(??????)
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
        //??????????????????????????????
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
        
        // ????????????
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);
            
            //??????????????????
            boolean havechairman = false;//??????????????????,?????????????????????????????????
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
        // ????????????????????????
        boolean haveNewChairman = false;//??????????????????,?????????????????????????????????
        String DIRECTOR_JSON_CHANGE = busRecord.get("DIRECTOR_JSON_CHANGE") == null ? ""
                : busRecord.get("DIRECTOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON_CHANGE)) {
            List<Map<String, Object>> directorChangeList = JSON.parseObject(DIRECTOR_JSON_CHANGE, List.class);
            
            //??????????????????
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
        // ????????????
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            
            //??????????????????
            for(Map<String,Object> supervisor : supervisorList){
                if(supervisor.get("SUPERVISOR_JOB_OLD")!=null){
                    String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB_OLD").toString());
                    supervisor.put("SUPERVISOR_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("supervisorList", supervisorList);
        }
        // ????????????(??????)
        String SUPERVISOR_JSON_CHANGE = busRecord.get("SUPERVISOR_JSON_CHANGE") == null ? ""
                : busRecord.get("SUPERVISOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON_CHANGE)) {
            List<Map<String, Object>> supervisorChangeList = JSON.parseObject(SUPERVISOR_JSON_CHANGE, List.class);
            
            //??????????????????
            for(Map<String,Object> supervisor : supervisorChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB").toString());
                supervisor.put("SUPERVISOR_JOB_NAME", jobName);
            }
            request.setAttribute("supervisorChangeList", supervisorChangeList);
        }
        // ????????????
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            
            //??????????????????
            for(Map<String,Object> manager : managerList){
                if(manager.get("MANAGER_JOB_OLD")!=null){
                    String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB_OLD").toString());
                    manager.put("MANAGER_JOB_OLD_NAME", jobName);
                }
            }
            request.setAttribute("managerList", managerList);
        }
        // ????????????(??????)
        String MANAGER_JSON_CHANGE = busRecord.get("MANAGER_JSON_CHANGE") == null ? ""
                : busRecord.get("MANAGER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON_CHANGE)) {
            List<Map<String, Object>> managerChangeList = JSON.parseObject(MANAGER_JSON_CHANGE, List.class);
            
            //??????????????????
            for(Map<String,Object> manager : managerChangeList){
                String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB").toString());
                manager.put("MANAGER_JOB_NAME", jobName);
            }
            request.setAttribute("managerChangeList", managerChangeList);
        }
        // ???????????????????????????????????????????????????????????????
        String ATTORNEY_JSON = busRecord.get("ATTORNEY_JSON") == null ? "" : busRecord.get("ATTORNEY_JSON").toString();
        if (StringUtils.isNotEmpty(ATTORNEY_JSON)) {
            List<Map<String, Object>> attorneyList = JSON.parseObject(ATTORNEY_JSON, List.class);
            request.setAttribute("attorneyList", attorneyList);
        }

        // ??????????????????
        String FAMILY_JSON = busRecord.get("FAMILY_JSON") == null ? "" : busRecord.get("FAMILY_JSON").toString();
        if (StringUtils.isNotEmpty(FAMILY_JSON)) {
            List<Map<String, Object>> familyList = JSON.parseObject(FAMILY_JSON, List.class);
            request.setAttribute("familyList", familyList);
        }

        // ?????????????????????????????????????????????
        String CONTROLLER_JSON = busRecord.get("CONTROLLER_JSON") == null ? ""
                : busRecord.get("CONTROLLER_JSON").toString();
        if (StringUtils.isNotEmpty(CONTROLLER_JSON)) {
            List<Map<String, Object>> controllerList = JSON.parseObject(CONTROLLER_JSON, List.class);
            request.setAttribute("controllerList", controllerList);
        }

        // ?????????????????????
        String PARTNER_JSON = busRecord.get("PARTNER_JSON") == null ? "" : busRecord.get("PARTNER_JSON").toString();
        if (StringUtils.isNotEmpty(PARTNER_JSON)) {
            List<Map<String, Object>> pratnerList = JSON.parseObject(PARTNER_JSON, List.class);
            request.setAttribute("pratnerList", pratnerList);
        }
        // ???????????????????????????????????????????????????JSON
        String FDCJJJG_JSON = busRecord.get("FDCJJJG_JSON") == null ? "" : busRecord.get("FDCJJJG_JSON").toString();
        if (StringUtils.isNotEmpty(FDCJJJG_JSON)) {
            List<Map<String, Object>> fdcjjgList = JSON.parseObject(FDCJJJG_JSON, List.class);
            request.setAttribute("fdcjjgList", fdcjjgList);
        }
        // ?????????????????????????????????????????????JSON
        String GGFBDW_JSON = busRecord.get("GGFBDW_JSON") == null ? "" : busRecord.get("GGFBDW_JSON").toString();
        if (StringUtils.isNotEmpty(GGFBDW_JSON)) {
            List<Map<String, Object>> ggfbdwList = JSON.parseObject(GGFBDW_JSON, List.class);
            request.setAttribute("ggfbdwList", ggfbdwList);
        }
        // ????????????????????????????????????????????????JSON
        String GCZJZXQYSL_JSON = busRecord.get("GCZJZXQYSL_JSON") == null ? ""
                : busRecord.get("GCZJZXQYSL_JSON").toString();
        if (StringUtils.isNotEmpty(GCZJZXQYSL_JSON)) {
            List<Map<String, Object>> gczjzxqyslList = JSON.parseObject(GCZJZXQYSL_JSON, List.class);
            request.setAttribute("gczjzxqyslList", gczjzxqyslList);
        }

        // ???????????????????????????????????????JSON
        String ACCOUNTANDAGREEMENTJSON = busRecord.get("ACCOUNTANDAGREEMENTJSON") == null ? ""
                : busRecord.get("ACCOUNTANDAGREEMENTJSON").toString();
        if (StringUtils.isNotEmpty(ACCOUNTANDAGREEMENTJSON)) {
            List<Map<String, Object>> accountAndAgreementList = JSON.parseObject(ACCOUNTANDAGREEMENTJSON, List.class);
            
            request.setAttribute("accountAndAgreementList", accountAndAgreementList);
        }
        // ????????????JSON
        String APPLYINVOICE_JSON = busRecord.get("APPLYINVOICE_JSON") == null ? ""
                : busRecord.get("APPLYINVOICE_JSON").toString();
        if (StringUtils.isNotEmpty(APPLYINVOICE_JSON)) {
            List<Map<String, Object>> invoiceapplyList = JSON.parseObject(APPLYINVOICE_JSON, List.class);
            
            request.setAttribute("invoiceapplyList", invoiceapplyList);
        }
    }

    /**
     * ??????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "submitApply")
    @ResponseBody
    public void submitApply(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            // ????????????????????????
            Map<String, Object> curMemeber = AppUtil.getLoginMember();
            // ???????????????
            String EFLOW_CURRENTTASK_ID = request.getParameter("EFLOW_CURRENTTASK_ID");
            if (StringUtils.isEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("EFLOW_CREATORID", curMemeber.get("USER_ID"));
                variables.put("EFLOW_CREATORACCOUNT", curMemeber.get("YHZH"));
                variables.put("EFLOW_CREATORNAME", curMemeber.get("YHMC"));
                variables.put("EFLOW_CREATORPHONE", curMemeber.get("SJHM"));
                variables.put("EFLOW_ASSIGNER_TYPE", AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
            }
            // ??????????????????
            String EFLOW_CUREXERUNNINGNODENAMES = request.getParameter("EFLOW_CUREXERUNNINGNODENAMES");
            if (StringUtils.isNotEmpty(EFLOW_CUREXERUNNINGNODENAMES) && EFLOW_CUREXERUNNINGNODENAMES.equals("??????")) {
                variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
            // ?????????????????????
            String eflowSubject = null;
            String itemName = (String) variables.get("ITEM_NAME");
            // ????????????
            String COMPANY_NAME = (String) variables.get("COMPANY_NAME");
            // ??????????????????
            String sbmc = (String) variables.get("SBMC");
            StringBuffer subject = new StringBuffer("");
            if (StringUtils.isNotEmpty(sbmc)) {
                if (StringUtils.isNotEmpty(COMPANY_NAME)) {
                    subject.append(sbmc).append("???").append(COMPANY_NAME).append("???");
                } else {
                    subject.append(sbmc).append("???").append(itemName).append("???");
                }
            } else {
                String projectName = (String) variables.get("PROJECT_NAME");
                subject.append(projectName).append("???").append(itemName).append("???");
            }

            eflowSubject = subject.toString();
            variables.put("EFLOW_SUBJECT", eflowSubject);
            variables = jbpmService.exeFrontFlow(variables);
            variables.put("OPER_SUCCESS", true);
            String isTempSave = (String) variables.get("EFLOW_ISTEMPSAVE");
            if (StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("OPER_MSG", "????????????!");
            } else {
                String msg = "????????????,????????????:" + (String) variables.get("EFLOW_EXEID");
                if (isTempSave.equals("-1")) {
                    if ((boolean) variables.get("isStartFlow")) {//??????????????????????????????EMS??????
                        try {
                            msg = executionService.sendEms(variables, msg);
                        } catch (Exception e) {
                            log.error("????????????Ems????????????" + e.getMessage());
                        }
                    }  
                }
                variables.put("OPER_MSG",msg);
            }
            // ????????????????????????
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
            String signMsg=StringUtil.getString(variables.get("SIGN_MSG"));//?????????????????????-????????????
            if(StringUtils.isNotEmpty(signMsg)){
                variables.put("OPER_MSG",signMsg);
            }else{
                variables.put("OPER_MSG", "????????????,????????????????????????!");
            }
            log.error(e.getMessage(), e);
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);

    }

    /**
     * 
     * 
     * ???????????????????????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadbsSearch")
    public void load(HttpServletRequest request, HttpServletResponse response) {
        getALLbsSearch(request, response);
    }

    /**
     * ?????????????????????
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
     * ?????? ??????????????????
     * 
     * @author Faker Li
     * @created 2015???12???2??? ??????17:21:24
     * @param request
     * @param response
     */
    @RequestMapping("/bsjdcx")
    public void bsjdcx(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String exeId = request.getParameter("exeId");
        String bjcxmm = (String) request.getParameter("bscxmm").trim();
        // ????????????????????????
        Map<String, Object> flowExe = null;
        flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID", "BJCXMM" },
                new Object[] { exeId, bjcxmm });
        /*????????????????????????*/
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
            // ??????????????????
            String runStatus = flowExe.get("RUN_STATUS").toString();
            if (!runStatus.equals("0") && !runStatus.equals("1")) {
                flowExe.put("CUR_STEPNAMES", "?????????");
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
     * ?????? ??????????????????
     * 
     * @author Faker Li
     * @created 2015???12???2??? ??????17:21:24
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
        variables.put("CALLED_PARTY", "???????????????");
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
     * ??????  ????????????
     * @author Kester Chen
     * @created 2019???1???24??? ??????9:46:22
     * @param request
     * @param response
     */
    @RequestMapping("/bdcx")
    public void bdcx(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        String lineNo = request.getParameter("lineNo");
        // ??????????????????????????????
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
     * ??????  ??????????????????
     * @author Kester Chen
     * @created 2019???1???24??? ??????9:46:22
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
     * ?????? ??????????????????????????????
     * 
     * @author Faker Li
     * @created 2015???12???3??? ??????4:41:52
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
     * ?????? ??????????????????????????????
     *
     * @author Faker Li
     * @created 2015???12???3??? ??????4:41:52
     * @param request
     * @return
     */
    @RequestMapping("/showOpinion")
    public ModelAndView showOpinion(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        StringBuffer sql=new StringBuffer("select * from jbpm6_task t ");
        sql.append("  where t.exe_id=? and t.handle_opinion is not null ");
        sql.append(" and t.task_nature is not null ");
        sql.append(" and t.handle_opinion !='??????' and  t.handle_opinion !='??????'  ");
        sql.append(" order by t.create_time desc ");
        List<Map<String, Object>> opinionList = exeDataService.findListBySql(sql.toString(),
                new Object[]{exeId},null);
        request.setAttribute("opinionList", opinionList);
        return new ModelAndView("website/yhzx/showOpinion");
    }
    /**
     * 
     * ?????? ????????????
     * 
     * @author Faker Li
     * @created 2015???12???2??? ??????17:21:24
     * @param request
     * @param response
     */
    @RequestMapping("/bjtj")
    public void bjtj(HttpServletRequest request, HttpServletResponse response) {
        // ????????????????????????
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
        // ?????????????????????????????????????????????
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
     * ?????? ?????????????????????
     * 
     * @author Faker Li
     * @created 2015???12???3??? ??????4:41:52
     * @param request
     * @return
     */
    @RequestMapping("/hzxx")
    public ModelAndView hzxx(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> fileList = fileAttachService.findByExeId(exeId, "1");
        Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
        // ??????????????????????????????
        String tableName = (String) flowExe.get("BUS_TABLENAME") + "_SCWS";
        List<Map<String, Object>> xfsjMap = fileAttachService.findByList(tableName,
                (String) flowExe.get("BUS_RECORDID"), "SCWS");
        request.setAttribute("xfsjMap", xfsjMap);
        request.setAttribute("fileList", fileList);
        return new ModelAndView("website/yhzx/hzxx");
    }
    
    /**
     * ????????????????????????????????????????????????
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
     * ?????? ?????????????????????
     * @author Kester Chen
     * @created 2019???6???10??? ??????11:08:27
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
     * ?????? ?????????????????????
     * 
     * @author Faker Li
     * @created 2015???12???9??? ??????3:47:14
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
     * ?????? ??????????????????????????????
     * 
     * @author Faker Li
     * @created 2015???12???17??? ??????9:21:02
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
     * ?????? ??????????????????????????????
     * 
     * @author Faker Li
     * @created 2015???12???17??? ??????9:21:02
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
     * ?????? ????????????????????????????????????
     * 
     * @author Rider Chen
     * @created 2015-12-22 ??????05:26:43
     * @param request
     * @param response
     */
    @RequestMapping("/loadTZXMXXData")
    public void loadTZXMXXData(HttpServletRequest request, HttpServletResponse response) {
        String projectCode = request.getParameter("projectCode");

        Properties projectProperties = FileUtil.readProperties("conf/config.properties");
        String isNetwork = projectProperties.getProperty("isNetwork");
        String internetAddress = projectProperties.getProperty("internetAddress");
        // ???????????????
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
     * ?????? ????????????????????????
     *
     * @author Rider Chen
     * @created 2015-12-22 ??????05:26:43
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
     * ??????APP??????????????????????????????
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
     * ?????? ??????????????????????????????????????????
     * 
     * @author Faker Li
     * @created 2016???1???20??? ??????11:28:23
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
     * ?????? ???????????????????????????15??????????????????
     * 
     * @author Faker Li
     * @created 2016???1???28??? ??????2:18:20
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
     * ?????? ??????EXCEL
     * 
     * @author Rider Chen
     * @created 2016-3-8 ??????09:27:30
     * @param request
     * @param response
     *            @RequestMapping("/exportExcel") public void
     *            exportExcel(HttpServletRequest request, HttpServletResponse
     *            response) { List<ExcelReplaceDataVO> datas = new ArrayList
     *            <ExcelReplaceDataVO>();
     * 
     *            // ?????????0??????1??????"title"??????"??????????????????????????????????????????????????????????????????"?????????"title"
     *            ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
     *            vo1.setRow(0); vo1.setColumn(0); vo1.setKey("${title}");
     *            vo1.setValue("??????????????????????????????????????????????????????????????????");
     * 
     *            // ?????????2??????1??????times??????"2012-06-28--2016-03-07"?????????times
     *            ExcelReplaceDataVO vo2 = new ExcelReplaceDataVO();
     *            vo2.setRow(1); vo2.setColumn(0); vo2.setKey("${times}");
     *            vo2.setValue("2012-06-28--2016-03-07");
     * 
     *            // ?????????2??????1??????times??????"2012-06-28--2016-03-07"?????????times
     *            ExcelReplaceDataVO vo3 = new ExcelReplaceDataVO();
     *            vo3.setRow(3); vo3.setColumn(2); vo3.setKey("${1111}");
     *            vo3.setValue("????????? (A1)");
     * 
     *            datas.add(vo1); datas.add(vo2); datas.add(vo3);
     *            ExcelUtil.replaceModelHSSF(datas,
     *            "e:\\template\\111111111111.xls",response,"test.xls"); }
     */
    /**
     * ??????????????????
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
            // ??????????????????
            String dlmm = request.getParameter("DLMM");
            String password = StringUtil.getMd5Encode(dlmm);
            Map<String, Object> curMemeber = userInfoService.getByJdbc("T_BSFW_USERINFO",
                    new String[] { "YHZH", "DLMM" }, new Object[] { yhzh, password });
            // ???????????????
            String EFLOW_CURRENTTASK_ID = request.getParameter("EFLOW_CURRENTTASK_ID");
            if (StringUtils.isEmpty(EFLOW_CURRENTTASK_ID)) {
                variables.put("EFLOW_CREATORID", curMemeber.get("USER_ID"));
                variables.put("EFLOW_CREATORACCOUNT", curMemeber.get("YHZH"));
                variables.put("EFLOW_CREATORNAME", curMemeber.get("YHMC"));
                variables.put("EFLOW_CREATORPHONE", curMemeber.get("SJHM"));
                variables.put("EFLOW_ASSIGNER_TYPE", AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
            }
            // ?????????????????????
            String eflowSubject = null;
            String itemName = (String) variables.get("ITEM_NAME");
            // ??????????????????
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
                variables.put("OPER_MSG", "????????????!");
            } else {
                variables.put("OPER_MSG", "????????????,????????????:" + (String) variables.get("EFLOW_EXEID"));
            }
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);           
            variables.put("OPER_MSG", "????????????,????????????????????????!");          
            log.error(e.getMessage());
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);

    }

    /**
     * ?????????????????????????????????
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
     * ?????? ??????????????????
     * 
     * @author Rider Chen
     * @created 2016???8???12??? ??????10:11:39
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
     * ?????????????????????????????????(????????????)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "wssbSelect")
    public ModelAndView wssbSelect(HttpServletRequest request) {
        return new ModelAndView("website/zzhy/wssbSelect");
    }

    /**
     * ?????????????????????????????????(????????????)
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
     * ?????????????????????????????????(??????)
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
     * ?????????????????????????????????(???????????????????????????)
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
     * ?????????1+N?????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "relateItemSelect")
    public ModelAndView relateItemSelect(HttpServletRequest request) {
        return new ModelAndView("website/zzhy/relateItemSelect");
    }

    /**
     * ?????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping("/clxz")
    public ModelAndView clxz(HttpServletRequest request) {
        // ????????????????????????
        String itemCode = request.getParameter("itemCode");
        // ???????????????
        String exeId = request.getParameter("exeId");
        //??????????????????
        String clxzlx = request.getParameter("clxzlx");
        // String isQueryDetail = request.getParameter("isQueryDetail");
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        if (serviceItem != null) {
            // ????????????KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // ????????????ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // ????????????????????????
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
            // ????????????????????????
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            Map<String, Object> eflowObj = null;
            if (flowAllObj.get("busRecord") != null) {
                Map<String, Object> busRecord = (Map<String, Object>) flowAllObj.get("busRecord");
                // ??????????????????????????????
                setCompany(request, busRecord);
                // ??????????????????????????????????????????
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
     * ??????:????????????????????????devbase???????????????
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
            // ????????????KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // ????????????ID
            String itemId = (String) serviceItem.get("ITEM_ID");
            // ????????????????????????
            List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
            // ????????????????????????
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
                    //??????????????????????????????
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
     * ??????    ????????????????????????
     * @author Allin Lin
     * @created 2021???8???4??? ??????3:19:02
     * @param request
     * @param busRecord
     */
    private void setCommercialChange(Map<String, Object> busRecord) {

        String COMPANY_TYPE = busRecord.get("COMPANY_TYPE") == null ? "" : busRecord.get("COMPANY_TYPE").toString();
        String COMPANY_TYPE_CHANGE = busRecord.get("COMPANY_TYPE_CHANGE") == null ? ""
                : busRecord.get("COMPANY_TYPE_CHANGE").toString();
        // ??????????????????
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
        // ????????????????????????????????????
        List<Map<String, Object>> newHolderList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> yHolderList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> xHolderList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> sameHolderList = new ArrayList<Map<String, Object>>();
        boolean isStockTransfer = false;
        // ????????????
        String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
        if (StringUtils.isNotEmpty(HOLDER_JSON)) {
            List<Map<String, Object>> holderList = JSON.parseObject(HOLDER_JSON, List.class);
            busRecord.put("holderList", holderList);

            // ?????????????????????????????????
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

        // ????????????(??????)
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

        // ??????????????????????????????
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

        // ????????????
        String DIRECTOR_JSON = busRecord.get("DIRECTOR_JSON") == null ? "" : busRecord.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            List<Map<String, Object>> directorList = JSON.parseObject(DIRECTOR_JSON, List.class);

            // ??????????????????
            boolean havechairman = false;// ??????????????????,?????????????????????????????????
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
        // ????????????????????????
        boolean haveNewChairman = false;// ??????????????????,?????????????????????????????????
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

        // ????????????
        String SUPERVISOR_JSON = busRecord.get("SUPERVISOR_JSON") == null ? ""
                : busRecord.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            List<Map<String, Object>> supervisorList = JSON.parseObject(SUPERVISOR_JSON, List.class);
            // ??????????????????
            for (Map<String, Object> supervisor : supervisorList) {
                if (supervisor.get("SUPERVISOR_JOB_OLD") != null) {
                    String jobName = dictionaryService.getDicNames("ssdjzw",
                            supervisor.get("SUPERVISOR_JOB_OLD").toString());
                    supervisor.put("SUPERVISOR_JOB_OLD_NAME", jobName);
                }
            }
            busRecord.put("supervisorList", supervisorList);
        }
        // ????????????(??????)
        String SUPERVISOR_JSON_CHANGE = busRecord.get("SUPERVISOR_JSON_CHANGE") == null ? ""
                : busRecord.get("SUPERVISOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON_CHANGE)) {
            List<Map<String, Object>> supervisorChangeList = JSON.parseObject(SUPERVISOR_JSON_CHANGE, List.class);
            // ??????????????????
            for (Map<String, Object> supervisor : supervisorChangeList) {
                String jobName = dictionaryService.getDicNames("ssdjzw", supervisor.get("SUPERVISOR_JOB").toString());
                supervisor.put("SUPERVISOR_JOB_NAME", jobName);
            }
            busRecord.put("supervisorChangeList", supervisorChangeList);
        }
        // ????????????
        String MANAGER_JSON = busRecord.get("MANAGER_JSON") == null ? "" : busRecord.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            List<Map<String, Object>> managerList = JSON.parseObject(MANAGER_JSON, List.class);
            // ??????????????????
            for (Map<String, Object> manager : managerList) {
                if (manager.get("MANAGER_JOB_OLD") != null) {
                    String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB_OLD").toString());
                    manager.put("MANAGER_JOB_OLD_NAME", jobName);
                }
            }
            busRecord.put("managerList", managerList);

        }
        // ????????????(??????)
        String MANAGER_JSON_CHANGE = busRecord.get("MANAGER_JSON_CHANGE") == null ? ""
                : busRecord.get("MANAGER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON_CHANGE)) {
            List<Map<String, Object>> managerChangeList = JSON.parseObject(MANAGER_JSON_CHANGE, List.class);
            // ??????????????????
            for (Map<String, Object> manager : managerChangeList) {
                String jobName = dictionaryService.getDicNames("ssdjzw", manager.get("MANAGER_JOB").toString());
                manager.put("MANAGER_JOB_NAME", jobName);
            }
            busRecord.put("managerChangeList", managerChangeList);
        }
    }

    /**
     * 
     * ?????? ??????????????????????????????
     * 
     * @author Faker Li
     * @created 2015???12???3??? ??????4:41:52
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
     * ??????:???????????????????????????
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
     * ??????:?????????????????????????????????
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
     * ??????:??????????????????
     *
     * @author Madison You
     * @created 2019/10/17 17:11:00
     * @param
     * @return
     */
    @RequestMapping("/moduleSearch")
    public ModelAndView moduleSearch(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");// ?????????
        String modulId = request.getParameter("moduleId");
        request.setAttribute("key", key);
        request.setAttribute("moduleId", modulId);
        return new ModelAndView("website/index/moduleSearch");
    }

    /**
     * ??????:????????????????????????
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
