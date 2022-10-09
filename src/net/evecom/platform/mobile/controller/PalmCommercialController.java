/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.mobile.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.mobile.service.PalmCommercialService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述掌上商事app
 * 
 * @author Panda Chen
 * @created 2016-12-13 下午04:09:35
 */
@Controller
@RequestMapping("/palmCommercialController")
public class PalmCommercialController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(PalmCommercialController.class);
    /**
     * service
     */
    @Resource
    private PalmCommercialService service;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
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
     * 引入Service
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 引入dataSourceService
     */
    @Resource
    private DataSourceService dataSourceService;

    @RequestMapping("/index")
    public ModelAndView test(HttpServletRequest request) {
        return new ModelAndView("test");
    }

    @RequestMapping("/index1")
    public ModelAndView test1(HttpServletRequest request) {
        return new ModelAndView("test1");
    }

    /**
     * 
     * 描述 获取文章列表数据
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:15:43
     * @param request
     * @param response
     */
    @RequestMapping("/getContentList")
    public void getContentList(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String moduleId = request.getParameter("moduleId");
        String title = request.getParameter("title");
        Map<String, Object> mapList = service.findContentListForApp(page, rows, moduleId, title);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 获取文章详情
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:22:04
     * @param request
     * @param response
     */
    @RequestMapping("/getContentInfo")
    @ResponseBody
    public void getContentInfo(HttpServletRequest request, HttpServletResponse response) {
        String ctid = request.getParameter("ctid");
        Map<String, Object> content = service.getByJdbc("T_CMS_ARTICLE_CONTENT", new String[] { "CONTENT_ID" },
                new Object[] { ctid });
        List<Map<String, Object>> list = service.findContentInfoForApp(ctid);
        content.put("FILELIST", list);
        String json = JSON.toJSONString(content);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述获取办事指南列表
     * 
     * @author Panda Chen
     * @created 2016-12-13 下午04:57:07
     * @param request
     * @param response
     */
    @RequestMapping("/getServiceItemList")
    @ResponseBody
    public void getServiceItemList(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String sql = "select * from T_WSBS_SERVICEITEM t where t.SSBMBM=? and t.FWSXZT='1'";
        Map mapList = service.getListBySql(sql, new String[] { "201605170002" }, page, rows);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述getIndustryList
     * 
     * @author Panda Chen
     * @created 2016-12-19 下午04:44:43
     * @param request
     * @param response
     */
    @RequestMapping("/getIndustryList")
    @ResponseBody
    public void getIndustryList(HttpServletRequest request, HttpServletResponse response) {
        // response.setHeader("Access-Control-Allow-Origin", "*");
        // String sql =
        // "select level, t.* from t_wsbs_industry t start with parent_id='4028819d51cc6f280151cde6a3f00027' 
//        connect by prior indu_id = parent_id and level<4  and rownum<=10";
        String sql = "select level, t.*,NVL(" +
                "(SELECT 'N' FROM t_wsbs_industry B WHERE t.indu_id=B.PARENT_ID   AND ROWNUM  < 2),'Y') " +
                "IS_LEAF from t_wsbs_industry t start with parent_id='4028819d51cc6f280151cde6a3f00027' " +
                "connect by prior indu_id = parent_id ORDER SIBLINGS BY t.tree_sn";
        List list = service.getListBySql(sql, null);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2017-3-12 上午10:58:29
     * @param request
     * @param response
     */
    @RequestMapping("/getTzIndustryList")
    @ResponseBody
    public void getTzIndustryList(HttpServletRequest request, HttpServletResponse response) {

        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String title = request.getParameter("title");
        Map<String, Object> mapList = service.getTzIndustryList(page, rows, title);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2017-3-12 上午10:58:34
     * @param request
     * @param response
     */
    @RequestMapping("/getNegativeList")
    @ResponseBody
    public void getNegativeList(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String title = request.getParameter("title");
        Map<String, Object> mapList = service.getNegativeList(page, rows, title);
        this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()), (List) mapList.get("list"), null,
                JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 初始化表单时获取流程等信息
     * 
     * @author Panda Chen
     * @created 2016-11-17 下午04:51:35
     * @param request
     * @param response
     */
    @RequestMapping("/applyItem")
    @ResponseBody
    public void applyItem(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String isQueryDetail = request.getParameter("isQueryDetail");
        String YHMC = request.getParameter("YHMC");
        // 获取传入的参数
        Map<String, Object> requestParams = BeanUtil.getMapFromRequest(request);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        if (serviceItem != null) {
            serviceItemNull(request, exeId, isQueryDetail, YHMC, requestParams, resultMap, serviceItem);

        }
        // 性别字典
        List<Map<String, Object>> sex = dictionaryService.findDatasForSelect("sex");
//        System.out.println(sex);
        resultMap.put("dictionarysex", sex);
        // 民族字典
        List<Map<String, Object>> nation = dictionaryService.findDatasForSelect("nation");
        resultMap.put("dictionarynation", nation);
        // 文化程度字典
        List<Map<String, Object>> degree = dictionaryService.findDatasForSelect("degree");
        resultMap.put("dictionarydegree", degree);
        // 政治面貌字典
        List<Map<String, Object>> political = dictionaryService.findDatasForSelect("political");
        resultMap.put("dictionarypolitical", political);
        // 证件类型字典
        List<Map<String, Object>> DocumentType = dictionaryService.findDatasForSelect("DocumentType");
        resultMap.put("dictionaryDocumentType", DocumentType);
        // 机构类型字典
        List<Map<String, Object>> OrgType = dictionaryService.findDatasForSelect("OrgType");
        resultMap.put("dictionaryOrgType", OrgType);
        // 国别类型字典
        List<Map<String, Object>> ssdjgb = dictionaryService.findDatasForCountrySelect("ssdjgb");
        resultMap.put("dictionaryssdjgb", ssdjgb);
        // 企业声明类型字典
        List<Map<String, Object>> ssdjqyfl = dictionaryService.findDatasForSelect("ssdjqyfl");
        resultMap.put("dictionaryssdjqyfl", ssdjqyfl);
        // 币种类型字典
        List<Map<String, Object>> ssdjbz = dictionaryService.findDatasForSelect("ssdjbz");
        resultMap.put("dictionaryssdjbz", ssdjbz);
        // 省份类型字典
        List<Map<String, Object>> province = dictionaryService.findDatasForSelect("province");
        resultMap.put("dictionaryprovince", province);
        // 职务类型字典
        List<Map<String, Object>> ssdjzw = dictionaryService.findDatasForSelect("ssdjzw");
        resultMap.put("dictionaryssdjzw", ssdjzw);
        // 产生方式类型字典
        List<Map<String, Object>> ssdjcsfs = dictionaryService.findDatasForSelect("ssdjcsfs");
        resultMap.put("dictionaryssdjcsfs", ssdjcsfs);

        // 股东类型字典
        List<Map<String, Object>> ssdjgdlx = dictionaryService.findDatasForSelect("ssdjgdlx");
        resultMap.put("dictionaryssdjgdlx", ssdjgdlx);

        // 职业状况字典
        List<Map<String, Object>> zyzk = dictionaryService.findDatasForSelect("zyzk");
        resultMap.put("dictionaryzyzk", zyzk);

        resultMap.put("time", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd"));
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }

    private void serviceItemNull(HttpServletRequest request, String exeId, String isQueryDetail, String YHMC,
            Map<String, Object> requestParams, Map<String, Object> resultMap, Map<String, Object> serviceItem) {
        //String formKey = (String) serviceItem.get("FORM_KEY");
        // 获取流程KEY
        String defKey = (String) serviceItem.get("DEF_KEY");
        // 获取项目ID
        String itemId = (String) serviceItem.get("ITEM_ID");
        // 获取材料信息列表
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(itemId, exeId);
        // 获取流程定义对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
        Map<String, Object> flowForm = null;
        Map<String, Object> flowDef = null;
        Map<String, Object> eflowObj = null;
        Map<String, Object> flowExe = null;
        if (flowAllObj.get("EFLOW_FLOWEXE") != null) {
            flowExe = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWEXE");
            resultMap.put("EFLOW_FLOWEXE", flowExe);
        }
        if (flowAllObj.get("busRecord") != null) {
            resultMap.put("busRecord", flowAllObj.get("busRecord"));
        }
        if (flowAllObj.get("EFLOWOBJ") != null) {
            eflowObj = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            if (eflowObj.get("HJMC") == null) {
                String defId = (String) eflowObj.get("EFLOW_DEFID");
                int flowVersion = Integer.parseInt(eflowObj.get("EFLOW_DEFVERSION").toString());
                String startNodeName = flowNodeService.getNodeName(defId, flowVersion, Jbpm6Constants.START_NODE);
                eflowObj.put("HJMC", startNodeName);
                // 获取当前登录用户
                eflowObj.put("SHRMC", YHMC);
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
            }
            Map<String, Object> flowMappedInfo = flowMappedService.getFlowMapInfo((String) eflowObj
                    .get("EFLOW_DEFID"), eflowObj.get("HJMC").toString(), "2");
            resultMap.put("flowMappedInfo", flowMappedInfo);
            resultMap.put("EFLOWOBJ", eflowObj);
        }
        if (flowAllObj.get("EFLOW_FLOWOBJ") != null) {
            resultMap.put("EFLOW_FLOWOBJ", flowAllObj.get("EFLOW_FLOWOBJ"));
        }
        if (flowAllObj.get("EFLOW_BUTTONRIGHTS") != null) {
            resultMap.put("EFLOW_BUTTONRIGHTS", flowAllObj.get("EFLOW_BUTTONRIGHTS"));
        }
        if (flowAllObj.get("EFLOW_FLOWDEF") != null) {
            flowDef = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF");
            resultMap.put("EFLOW_FLOWDEF", flowDef);
        }
        if (flowAllObj.get("EFLOW_FLOWFORM") != null) {
            flowForm = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWFORM");
            resultMap.put("EFLOW_FLOWFORM", flowForm);
        }
        if (StringUtils.isNotEmpty(exeId)) {
            String busRecordId = (String) eflowObj.get("EFLOW_BUSRECORDID");
            String busTableName = (String) eflowObj.get("EFLOW_BUSTABLENAME");
            applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        }
        serviceItem.put("APPLY_TYPE", "1");
        // 定义材料列表JSON
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters, new String[] { "uploadFiles" }, true);
        resultMap.put("serviceItem", serviceItem);
        resultMap.put("applyMaters", applyMaters);
        resultMap.put("isQueryDetail", isQueryDetail);
        resultMap.put("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        resultMap.put("requestParams", requestParams);
        
        //定义商事登记自贸区划分地址信息
        List<Map<String, Object>> addrList =  dictionaryService.findByTypeCode("zhqhfdz");
        StringBuffer addrs=new StringBuffer();
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

    /**
     * 执行流程方法
     * 
     * @param request
     * @return
     */
    @RequestMapping("/submitApply")
    @ResponseBody
    public void submitApply(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            variables.put("EFLOW_ASSIGNER_TYPE", AllConstant.ASSIGNER_TYPE_WEBSITEUSER);
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

            variables.put("OPER_MSG", "申报成功,申报号是:" + (String) variables.get("EFLOW_EXEID"));
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系网站管理员!");
            log.error(e.getMessage());
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);

    }
    
    /**
     * 返回JSON数据-验证重名的公司
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/verifyCompanyNameExist")
    @ResponseBody
    public void verifyCompanyNameExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fieldValue = request.getParameter("fieldValue");
        String companyId = request.getParameter("companyId");
        String individualId = request.getParameter("individualId");
        String solelyId=request.getParameter("solelyId");
        //T_COMMERCIAL_COMPANY      商事登记企业基本信息
        //T_COMMERCIAL_INDIVIDUAL   个体工商户登记
        //T_COMMERCIAL_SOLELYINVEST 个人独资企业
        boolean isExistCompany=false;
        boolean isExistIndividual=false;
        boolean isExistSolelyinvest=false;
        if((!StringUtils.isNotEmpty(companyId)||companyId.equals("undefined"))&&
                (!StringUtils.isNotEmpty(individualId)||individualId.equals("undefined"))&&
                (!StringUtils.isNotEmpty(solelyId)||solelyId.equals("undefined"))){//说明为新注册用户
            isExistIndividual= this.dataSourceService
                    .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,null);
            isExistSolelyinvest = this.dataSourceService
                    .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,null);
            isExistCompany = this.dataSourceService
                    .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,null); 
        }else{
            //T_COMMERCIAL_COMPANY如果这张表中原先就存在这个名称
            if(StringUtils.isNotEmpty(companyId)&&!companyId.equals("undefined")){
                isExistCompany = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,companyId); 
                isExistIndividual= this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,null);
                isExistSolelyinvest = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,null);  
            //T_COMMERCIAL_INDIVIDUAL如果这张表中原先就存在这个名称
            }else if(StringUtils.isNotEmpty(individualId)&&!individualId.equals("undefined")){
                isExistCompany = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,null); 
                isExistIndividual= this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,individualId);
                isExistSolelyinvest = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,null);
            //T_COMMERCIAL_SOLELYINVEST如果这张表中原先就存在这个名称
            }else if(StringUtils.isNotEmpty(solelyId)&&!solelyId.equals("undefined")){
                isExistCompany = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,null); 
                isExistIndividual= this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,null);
                isExistSolelyinvest = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,solelyId); 
            }
        }
        String jsonStr = null; 
        Map variables = new HashMap(); 
        if (isExistCompany||isExistIndividual||isExistSolelyinvest) {
            variables.put("success", false);
            jsonStr = "[false]";
        } else {
            variables.put("success", true);
            jsonStr = "[true]";
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
    
    
    @RequestMapping("/verifyCompanyEngExist")
    @ResponseBody
    public void verifyCompanyEngExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String companyId = request.getParameter("companyId");
        String fieldValue = request.getParameter("fieldValue");
        //T_COMMERCIAL_COMPANY      商事登记企业基本信息
        boolean isExistEnglish=false;
        isExistEnglish= this.dataSourceService
                .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME_ENG", fieldValue,companyId);
        String jsonStr = null; 
        Map variables = new HashMap(); 
        if (isExistEnglish) {
            variables.put("success", false);
            jsonStr = "[false]";
        } else {
            variables.put("success", true);
            jsonStr = "[true]";
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
}
