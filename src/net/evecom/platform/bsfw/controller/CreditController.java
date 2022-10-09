/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunnada.crypto.utils.Sm2Utils;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcSsdjService;
import net.evecom.platform.bdc.service.impl.BdcSpbPrintConfigServiceImpl;
import net.evecom.platform.bsfw.service.CreditService;
import net.evecom.platform.bsfw.util.MaterDownloadUtil;
import net.evecom.platform.bsfw.util.ReturnInfoUtil;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.SignatureException;
import java.util.*;

/**
 * 描述
 * 
 * @author Water Guo
 * @version 1.0
 * @created 2016年06月16日 下午3:14:27
 */
@Controller
@RequestMapping("/creditController")
public class CreditController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CreditController.class);
    /**
     * serviceItemService
     */
    @Resource
    private CreditService creditService;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;

    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/23 9:48:00
     * @param
     * @return
     */
    @Resource
    private BdcSsdjService bdcSsdjService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/12 10:43:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService spbPrintConfigService;

    /**
     * 描述:配置信息
     *
     * @author Madison You
     * @created 2020/11/30 14:20:00
     * @param
     * @return
     */
    private Properties projectProperties = FileUtil.readProperties("project.properties");

    /**
     * 描述:电子证照反馈  tokenURL
     *
     * @author Madison You
     * @created 2020/11/30 14:20:00
     * @param
     * @return
     */
    private String tokenUrl = projectProperties.getProperty("CREDIT_FEEDBACK_TOKEN_URL");

    /**
     * 描述:电子证照反馈  缺失登记URL
     *
     * @author Madison You
     * @created 2020/11/30 14:20:00
     * @param
     * @return
     */
    private String lackUrl = projectProperties.getProperty("CREDIT_FEEDBACK_LACK_URL");

    /**
     * 描述:电子证照反馈  缺失登记子系统URL
     *
     * @author Madison You
     * @created 2021/1/14 15:33:00
     * @param
     * @return
     */
    private String lackChildUrl = projectProperties.getProperty("CREDIT_FEEDBACK_LACK_CHILDURL");

    /**
     * 描述:电子证照反馈  错误登记URL
     *
     * @author Madison You
     * @created 2020/11/30 14:21:00
     * @param
     * @return
     */
    private String errorUrl = projectProperties.getProperty("CREDIT_FEEDBACK_ERROR_URL");

    /**
     * 描述:电子证照反馈  错误登记URL
     *
     * @author Madison You
     * @created 2020/11/30 14:21:00
     * @param
     * @return
     */
    private String creditNameUrl = projectProperties.getProperty("CREDIT_FEEDBACK_CREDITNAME_URL");

    /**
     * 描述:电子证照反馈  加载失败URL
     *
     * @author Madison You
     * @created 2021/1/14 16:19:00
     * @param
     * @return
     */
    private String loadFailureUrl = projectProperties.getProperty("CREDIT_FEEDBACK_NOTNORMAL_URL");
    /**
     * 描述:电子证照反馈  accountId
     *
     * @author Madison You
     * @created 2020/11/30 14:21:00
     * @param
     * @return
     */
    private String accountId = projectProperties.getProperty("CREDIT_ACCOUNT_ID");
    /**
     * 描述:电子证照反馈  加密文件路径
     *
     * @author Madison You
     * @created 2020/11/30 14:21:00
     * @param
     * @return
     */
    private String keyPath = projectProperties.getProperty("CREDIT_KEY_PATH");

    /**
     * 获取主体信息个数
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "findCredits")
    @ResponseBody
    public AjaxJson findCredits(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String codes = request.getParameter("codes");
        String names = request.getParameter("names");
        Map<String, Object> result;
        if(StringUtils.isBlank(codes)&&StringUtils.isBlank(names)){
            j.setSuccess(false);
            j.setMsg("机构名称和机构代码为空！");      
        }else{
            result = this.creditService.findCreditList(codes,names);
            j.setJsonString(JSON.toJSONString(result));      
            j.setMsg("操作成功");
            j.setSuccess(true);
        }
        return j;
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String codes = request.getParameter("codes");
        String names = request.getParameter("names");
        request.setAttribute("codes", codes);
        request.setAttribute("names", names);
        return new ModelAndView("bsdt/applyform/creditQueryList");
    }
    /**
     * easyui AJAX请求数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter sqlFilter = new SqlFilter(request);
        String codes =request.getParameter("CORP_CODE");
        String names = request.getParameter("CORP_NAME");
        sqlFilter.addSorted("T.CREATE_TIME","DESC");
        List<Map<String, Object>> list = this.creditService.findBySqlFilter(sqlFilter,codes,names);
        this.setListToJsonString(sqlFilter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "licenseInfo")
    public ModelAndView licenseInfo(HttpServletRequest request) {
        Map<String,Object>  variables=BeanUtil.getMapFromRequest(request);
        Iterator<Map.Entry<String,Object>> it = variables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Object> entry = it.next();
            log.info("key = " + entry.getKey() + ", value = " + entry.getValue());
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        //variables.forEach((key,value)->{
        //    request.setAttribute(key,value);
        //});
        return new ModelAndView("bsdt/applyform/licenseInfoList");
    }
    
    /**
     * 跳转到电子文件信息页面
     * @author Scolder Lin
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "electDocumentInfo")
    public ModelAndView electDocumentInfo(HttpServletRequest request) {
        Map<String,Object>  variables=BeanUtil.getMapFromRequest(request);
        Iterator<Map.Entry<String,Object>> it = variables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Object> entry = it.next();
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        return new ModelAndView("bsdt/applyform/electDocumentInfo");
    }
    
    /**
     * easyui 获取可信文件列表信息
     * @author Scolder Lin
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "electDocumentDatagrid")
    public void electDocumentDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> returnList=creditService.findElectDocumentDatagrid(variables);
        this.setListToJsonString(returnList.size(), returnList,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * easyui AJAX请求数据
     * @param request
     * @param response
     */
    @RequestMapping(params = "licenseDatagrid")
    public void licenseDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> returnList=creditService.findDatagrid(variables);
        String creditCode = StringUtil.getValue(variables, "creditCode");
        String creditName = StringUtil.getValue(variables, "creditName");
        if (StringUtils.isNotEmpty(creditCode) || StringUtils.isNotEmpty(creditName)) {
            List<Map<String,Object>> returnSearchList = new ArrayList<>();
            if (returnList != null && !returnList.isEmpty()) {
                for (Map<String, Object> returnMap : returnList) {
                    String name = StringUtil.getValue(returnMap, "Name");
                    String licenseID = StringUtil.getValue(returnMap, "LicenseID");
                    if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(licenseID)) {
                        if (licenseID.contains(creditCode) && name.contains(creditName)) {
                            returnSearchList.add(returnMap);
                        }
                    }
                    if (StringUtils.isNotEmpty(name) && StringUtils.isEmpty(licenseID)) {
                        if (name.contains(creditName)) {
                            returnSearchList.add(returnMap);
                        }
                    }
                    if (StringUtils.isEmpty(name) && StringUtils.isNotEmpty(licenseID)) {
                        if (licenseID.contains(creditCode)) {
                            returnSearchList.add(returnMap);
                        }
                    }
                }
            }
            this.setListToJsonString(returnSearchList.size(), returnSearchList,
                    null, JsonUtil.EXCLUDE, response);
        } else {
            this.setListToJsonString(returnList.size(), returnList,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    /**
     * 电子证照树
     * @param request
     * @param response
     */
    @RequestMapping("/evidenceTree")
    public void evidenceTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String json=creditService.getEviditTreeJson(variables);
        this.setJsonString(json, response);
    }
    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "showFile")
    public ModelAndView showFile(HttpServletRequest request,
                                HttpServletResponse response) {
        Map<String,Object> variable=BeanUtil.getMapFromRequest(request);
        String filePath=creditService.downCertFile(variable).getFilePath();
        request.setAttribute("filePath", filePath);
        String noprint=request.getParameter("noprint");
        request.setAttribute("noprint",noprint);
        return new ModelAndView("bsdt/applyform/evidence/show");
    }
    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "showFileWithFilePath")
    public ModelAndView showFileWithFilePath(HttpServletRequest request,
                                 HttpServletResponse response) {
        String filePath=request.getParameter("filePath");
        String noprint=request.getParameter("noprint");
        request.setAttribute("filePath", filePath);
        request.setAttribute("noprint",noprint);
        return new ModelAndView("bsdt/applyform/evidence/show");
    }
    /**
     * 上传电子证照
     * @param request
     * @return
     */
    @RequestMapping(params = "uploadFile")
    @ResponseBody
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> list=creditService.uploadFile(variables);
        String json = JSONArray.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 上传可信文件
     * @param request
     * @return
     */
    @RequestMapping(params = "uploadElectDocumentFile")
    @ResponseBody
    public void uploadElectDocumentFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> variables=BeanUtil.getMapFromRequest(request);
        List<Map<String,Object>> list=creditService.uploadElectDocumentFile(variables);
        String json = JSONArray.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 描述:电子证照反馈页面
     *
     * @author Madison You
     * @created 2020/11/25 10:15:00
     * @param
     * @return
     */
    @RequestMapping(params = "creditFeedBackView")
    public ModelAndView creditFeedBackView(HttpServletRequest request) {
        Map<String,Object>  variables=BeanUtil.getMapFromRequest(request);
        Iterator<Map.Entry<String,Object>> it = variables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Object> entry = it.next();
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        return new ModelAndView("bsdt/applyform/evidence/creditFeedBackView");
    }

    /**
     * 描述:获取证照名称列表
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/12/29 17:49:00
     */
    @RequestMapping(params = "getCreditNameList")
    public void getCreditNameList(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> list = getCreditNameListByInterface(request);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    
/**
 * 
 * @Description 根据查询条件获取证照类型列表
 * @author Luffy Cai
 * @date 2021年6月30日
 * @param request
 * @param response
 * void
 */
    @RequestMapping(params = "getCertificateType")
    public void getCertificateType(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object>  variables=BeanUtil.getMapFromRequest(request);
        List<Map<String, Object>> list = creditService.getCertificateType(variables);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }    
    

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/29 17:54:00
     * @param
     * @return
     */
    private List<Map<String, Object>> getCreditNameListByInterface(HttpServletRequest request) {
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String itemCode = StringUtil.getValue(requestMap, "ITEM_ID");
        String id = UUIDGenerator.getUUID();
        String dateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss");
        String accessId = dateTime + accountId + id;
        String token = getCreditFeedBackAccessToken(id);
        /*设置数据主体*/
        Map<String,Object> bodyMap = new HashMap<>();
        Map<String,Object> headMap = new HashMap<>();
        Map<String,Object> projectInfoMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        Map<String,Object> clientInfoMap = new HashMap<>();
        headMap.put("clientInfo", clientInfoMap);
        bodyMap.put("head", headMap);
        headMap.put("projectInfo", projectInfoMap);
        bodyMap.put("data", dataMap);
        /*组装通用数据*/
        headMap.put("accessToken", token);
        headMap.put("accessId", accessId);
        headMap.put("accountId", accountId);
        headMap.put("reqTime", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        clientInfoMap.put("areaCode", "350128000000");
        clientInfoMap.put("areaName", "平潭综合实验区");
        clientInfoMap.put("systemName", "平潭综合实验区综合审批平台");
        /*根据用户ID查询对应信息*/
        creditService.getUserInfoByUserId(clientInfoMap,requestMap);
        Map<String, Object> itemMap = creditService.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"},
                new Object[]{itemCode});
        projectInfoMap.put("taskCode", StringUtil.getValue(itemMap, "ITEM_CODE"));
        projectInfoMap.put("taskName", StringUtil.getValue(itemMap, "ITEM_NAME"));
        projectInfoMap.put("projectNo", "无");
        setCreditFeedBackCreditNameInfo(dataMap,requestMap);
        JSONObject jsonObject = new JSONObject(bodyMap);
        /*设置请求头*/
        Map<String, String> header = setCreditFeedBackHeader(jsonObject.toJSONString());
        String result = HttpSendUtil.sendHttpPostJson(creditNameUrl, header, jsonObject.toJSONString(), "UTF-8");
        return parseCreditFeedBackCreditNameResult(result);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/29 18:11:00
     * @param
     * @return
     */
    private List<Map<String, Object>> parseCreditFeedBackCreditNameResult(String result) {
        List<Map<String,Object>> creditNameList = new ArrayList<>();
        if (StringUtils.isNotEmpty(result)) {
            Map<String, Object> bodyMap = JSON.parseObject(result, Map.class);
            Map<String, Object> headMap = (Map) bodyMap.get("head");
            String status = StringUtil.getValue(headMap, "status");
            if (Objects.equals(status, "0")) {
                Map<String, Object> dataMap = (Map) bodyMap.get("data");
                List<Map<String, Object>> dataList = (List)dataMap.get("dataList");
                for (Map<String, Object> map : dataList) {
                    String type = StringUtil.getValue(map, "certificateType");
                    Map<String,Object> creditNameMap = new HashMap<>();
                    creditNameMap.put("DIC_CODE", type);
                    creditNameMap.put("DIC_NAME", type);
                    creditNameList.add(creditNameMap);
                }
            }
        }
        return creditNameList;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/29 18:04:00
     * @param
     * @return
     */
    private void setCreditFeedBackCreditNameInfo(Map<String, Object> dataMap, Map<String, Object> requestMap) {
        dataMap.put("name", "");
        dataMap.put("page", "1");
        dataMap.put("size", "1000");
    }

    /**
     * 描述:保存电子证照反馈
     *
     * @author Madison You
     * @created 2020/11/25 11:47:00
     * @param
     * @return
     */
    @RequestMapping(params = "saveOrUpdateCreditFeedBack")
    @ResponseBody
    public Map<String,Object> saveOrUpdateCreditFeedBack(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        returnMap.put("msg", "系统错误，请联系管理员！");
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String id = creditService.saveOrUpdate(requestMap, "T_BSFW_CREDIT_FEEDBACK", null);
        try{
            returnMap = sendCreditFeedBackInfo(id, requestMap);
        } catch (Exception e) {
            returnMap.put("success", false);
            returnMap.put("msg", "系统错误，请联系管理员");
            log.error("发送证照反馈信息错误，id为：" + id + "，请求参数为：" + requestMap.toString(), e);
        }
        return returnMap;
    }

    /**
     * 描述:发送证照反馈信息
     *
     * @author Madison You
     * @created 2020/11/25 15:42:00
     * @param
     * @return
     */
    private Map<String,Object> sendCreditFeedBackInfo(String id, Map<String, Object> requestMap) {
        String itemCode = StringUtil.getValue(requestMap, "ITEM_ID");
        String creditType = StringUtil.getValue(requestMap, "CREDIT_TYPE");
        String dateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss");
        String accessId = dateTime + accountId + id;
        String token = getCreditFeedBackAccessToken(id);
        String result = "";
        if (StringUtils.isEmpty(token)) {
            return ReturnInfoUtil.returnErrMsg("身份认证失败，请联系管理员");
        }
        /*设置数据主体*/
        Map<String,Object> bodyMap = new HashMap<>();
        Map<String,Object> headMap = new HashMap<>();
        Map<String,Object> projectInfoMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        Map<String,Object> clientInfoMap = new HashMap<>();
        headMap.put("clientInfo", clientInfoMap);
        bodyMap.put("head", headMap);
        headMap.put("projectInfo", projectInfoMap);
        bodyMap.put("data", dataMap);
        /*组装通用数据*/
        headMap.put("accessToken", token);
        headMap.put("accessId", accessId);
        headMap.put("accountId", accountId);
        headMap.put("reqTime", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        clientInfoMap.put("areaCode", "350128000000");
        clientInfoMap.put("areaName", "平潭综合实验区");
        clientInfoMap.put("systemName", "平潭综合实验区综合审批平台");
        /*根据用户ID查询对应信息*/
        creditService.getUserInfoByUserId(clientInfoMap,requestMap);
        /*查询事项信息*/
        Map<String, Object> itemMap = creditService.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"},
                new Object[]{itemCode});
        projectInfoMap.put("taskCode", StringUtil.getValue(itemMap, "ITEM_CODE"));
        projectInfoMap.put("taskName", StringUtil.getValue(itemMap, "ITEM_NAME"));
        projectInfoMap.put("projectNo", "无");
        /*设置数据主体*/
        if (Objects.equals(creditType, "1")) { //证照缺失接口
            setCreditFeedBackLackInfo(dataMap,requestMap);
            JSONObject jsonObject = new JSONObject(bodyMap);
            /*设置请求头*/
            Map<String, String> header = setCreditFeedBackHeader(jsonObject.toJSONString());
            String creditNameFlag = StringUtil.getValue(requestMap, "CREDIT_NAME_FLAG");
            if (StringUtils.isNotEmpty(creditNameFlag) && Objects.equals(creditNameFlag, "1")) {
                result = HttpSendUtil.sendHttpPostJson(lackChildUrl, header, jsonObject.toJSONString(), "UTF-8");
            } else {
                result = HttpSendUtil.sendHttpPostJson(lackUrl, header, jsonObject.toJSONString(), "UTF-8");
            }
        } else if (Objects.equals(creditType, "2")) { //证照错误接口
            setCreditFeedBackErrorInfo(dataMap,requestMap);
            JSONObject jsonObject = new JSONObject(bodyMap);
            Map<String, String> header = setCreditFeedBackHeader(jsonObject.toJSONString());
            result = HttpSendUtil.sendHttpPostJson(errorUrl, header, jsonObject.toJSONString(), "UTF-8");
        } else if (Objects.equals(creditType, "3")) {
            setCreditFeedBackLoadFailureInfo(dataMap,requestMap);
            JSONObject jsonObject = new JSONObject(bodyMap);
            Map<String, String> header = setCreditFeedBackHeader(jsonObject.toJSONString());
            result = HttpSendUtil.sendHttpPostJson(loadFailureUrl, header, jsonObject.toJSONString(), "UTF-8");
        }
        return parseCreditFeedBackResult(result, id);
    }

    /**
     * 描述:设置请求头
     *
     * @author Madison You
     * @created 2020/11/27 11:24:00
     * @param
     * @return
     */
    private Map<String, String> setCreditFeedBackHeader(String jsonStr) {
        Map<String,String> header = new HashMap<>();
        header.put("X-Account-Id", accountId);
        try {
            header.put("X-Sign", getDevbaseCreditSM2Signature(jsonStr));
        } catch (Exception e) {
            log.error(e);
        }
        return header;
    }

    /**
     * 描述:解析返回数据
     *
     * @author Madison You
     * @created 2020/11/26 11:58:00
     * @param
     * @return
     */
    private Map<String,Object> parseCreditFeedBackResult(String result , String id) {
        Map<String, Object> returnMap = new HashMap<>();
        if (StringUtils.isNotEmpty(result)) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("RESULT_JSON", result);
            Map<String, Object> bodyMap = JSON.parseObject(result, Map.class);
            Map<String, Object> headMap = (Map) bodyMap.get("head");
            String status = StringUtil.getValue(headMap, "status");
            if (Objects.equals(status, "0")) {
                Map<String, Object> dataMap = (Map) bodyMap.get("data");
                String receiptNumber = StringUtil.getValue(dataMap, "receiptNumber");
                variables.put("RECEIPT_NUMBER", receiptNumber);
                returnMap.put("success", true);
                returnMap.put("msg", "反馈成功");
            } else {
                returnMap.put("msg", "反馈失败:" + StringUtil.getValue(headMap, "message"));
            }
            creditService.saveOrUpdate(variables, "T_BSFW_CREDIT_FEEDBACK", id);
        }
        return returnMap;
    }

    /**
     * 描述:设置错误证照数据
     *
     * @author Madison You
     * @created 2020/11/26 11:32:00
     * @param
     * @return
     */
    private void setCreditFeedBackErrorInfo(Map<String, Object> dataMap, Map<String, Object> requestMap) {
        dataMap.put("certificateIdentifier", StringUtil.getValue(requestMap, "LICENSE_FILE_ID"));
        dataMap.put("problemRemark", StringUtil.getValue(requestMap, "CREDIT_DESCRIBE"));
        dataMap.put("telephone", StringUtil.getValue(requestMap, "CREDIT_PHONE"));
        setCreditFeedBackLackFile(dataMap, requestMap);
    }

    /**
     * 描述:设置加载失败数据
     *
     * @author Madison You
     * @created 2021/1/14 16:16:00
     * @param
     * @return
     */
    private void setCreditFeedBackLoadFailureInfo(Map<String, Object> dataMap, Map<String, Object> requestMap) {
        dataMap.put("certificateIdentifier", StringUtil.getValue(requestMap, "LICENSE_FILE_ID"));
        dataMap.put("telephone", "");
    }

    /**
     * 描述:设置缺失证照数据
     *
     * @author Madison You
     * @created 2020/11/26 11:02:00
     * @param
     * @return
     */
    private void setCreditFeedBackLackInfo(Map<String, Object> dataMap, Map<String, Object> requestMap) {
        dataMap.put("certificateTypeName", StringUtil.getValue(requestMap, "CREDIT_NAME"));
        dataMap.put("certificateIssuingAuthorityName", StringUtil.getValue(requestMap, "CREDIT_ORGANIZATION"));
        dataMap.put("certificateIssuedDate", spbPrintConfigService.bdcDateFormat(
                StringUtil.getValue(requestMap, "CREDIT_TIME"), "yyyy-MM-dd", "yyyy年MM月dd日"));
        dataMap.put("certificateNumber", StringUtil.getValue(requestMap, "CREDIT_NUM"));
        dataMap.put("certificateHolderName", StringUtil.getValue(requestMap, "CREDIT_HOLDER_NAME"));
        dataMap.put("certificateHolderCode", StringUtil.getValue(requestMap, "CREDIT_HOLDER_CODE"));
        dataMap.put("certificateHolderTypeName", StringUtil.getValue(requestMap, "CREDIT_HOLDER_TYPE"));
        dataMap.put("telephone", StringUtil.getValue(requestMap, "CREDIT_PHONE"));
        setCreditFeedBackLackFile(dataMap, requestMap);
    }

    /**
     * 描述:设置附件数据
     *
     * @author Madison You
     * @created 2020/11/26 11:14:00
     * @param
     * @return
     */
    private void setCreditFeedBackLackFile(Map<String, Object> dataMap, Map<String, Object> requestMap) {
        List<Map<String, Object>> fileList = new ArrayList<>();
        String fileIds = StringUtil.getValue(requestMap, "CREDIT_FILE_ID");
        if (StringUtils.isNotEmpty(fileIds)) {
            String[] fileIdArr = fileIds.split(";");
            for (int i = 0; i < fileIdArr.length ; i++) {
                Map<String,Object> fileMap = new HashMap<>();
                fileMap.put("seqNo", String.valueOf(i + 1));
                /*获取文件信息*/
                setCreditFeedBackLackFileInfo(fileMap, fileIdArr[i]);
                fileList.add(fileMap);
            }
        }
        dataMap.put("fileList", fileList);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/26 11:25:00
     * @param
     * @return
     */
    private void setCreditFeedBackLackFileInfo(Map<String, Object> fileMap, String fileId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn");
        Map<String, Object> map = creditService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"}, new Object[]{fileId});
        fileMap.put("fileName", StringUtil.getValue(map, "FILE_NAME"));
        fileMap.put("fileContent", MaterDownloadUtil.getBase64CodeByUrlFile(uploadFileUrlIn +
                StringUtil.getValue(map, "FILE_PATH")));
    }

    /**
     * 描述:获取证照反馈token
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/11/25 14:27:00
     */
    private String getCreditFeedBackAccessToken(String id) {
        String dateTime = DateTimeUtil.getStrOfDate(new Date(), "yyyyMMddHHmmss");
        String accessId = dateTime + accountId + id;
        String sign = "";
        try {
            sign = getDevbaseCreditSM2Signature(accessId);
        } catch (Exception e) {
            log.error(e);
        }
        String accessToken = "";
        JSONObject json = new JSONObject();
        json.put("accessId", accessId);
        json.put("accountId", accountId);
        json.put("sign", sign);
        Map<String,String> headerMap = new HashMap<>();
        String result = HttpSendUtil.sendHttpPostJson(tokenUrl, headerMap, json.toJSONString(), "UTF-8");
        if (StringUtils.isNotEmpty(result)) {
            Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
            Map<String, Object> headMap = (Map) resultMap.get("head");
            String status = StringUtil.getValue(headMap, "status");
            if (Objects.equals(status, "0")) {
                Map<String, Object> dataMap = (Map) resultMap.get("data");
                accessToken = StringUtil.getValue(dataMap, "accessToken");
            } else {
                log.info("获取电子证照反馈接口accessToken错误，错误信息：" + StringUtil.getValue(headMap, "message")
                        + "，错误ID：" + id);
            }
        }
        return accessToken;
    }

    /**
     * 描述:获取devbase上的电子证照sm2加密字符串
     *
     * @author Madison You
     * @created 2021/3/18 15:44:00
     * @param
     * @return
     */
    public String getDevbaseCreditSM2Signature(String accessId){
        String sign = "";
        try{
            String devbaseUrl = projectProperties.getProperty("devbaseUrl");
            String base64File = FileUtil.encodeBase64File(keyPath);
            Map<String,Object> param = new HashMap<>();
            param.put("type", "2");
            param.put("priKeyBase64", base64File);
            param.put("content", accessId);
            param.put("servicecode", "createSM2Signature");
            param.put("grantcode", "BFbhXSKZXvjii1bMaLWg");
            String result = HttpSendUtil.sendPostParams(devbaseUrl, param);
            if (StringUtils.isNotEmpty(result)) {
                Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
                if (Objects.equals(StringUtil.getValue(resultMap, "invokeResultCode"), "000")) {
                    sign = StringUtil.getValue(resultMap, "data");
                }
            }
        } catch (Exception e) {
            log.error("获取devbase上的电子证照sm2加密字符串失败", e);
        }
        return sign;
    }

}
