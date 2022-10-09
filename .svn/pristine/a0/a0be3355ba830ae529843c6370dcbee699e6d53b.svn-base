/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import net.evecom.core.poi.WordReplaceParamUtil;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.CreditService;
import net.evecom.platform.commercial.service.CommercialSetService;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.zzhy.service.*;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述 内资企业登记信息Controller
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/domesticControllerController")
public class DomesticControllerController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DomesticControllerController.class);
    /**
     * 引入Service
     */
    @Resource
    private DomesticControllerService domesticControllerService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * creditService
     */
    @Resource
    private CreditService creditService;
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;

    /**
     * 引入commercialSetService
     */
    @Resource
    private CommercialSetService commercialSetService;

    /**
     * 引入relatedGenService
     */
    @Resource
    private RelatedGenService relatedGenService;
    
    /**
     * 货物运输站（场）申请
     */
    @Resource
    private RelatedRfsApplyService rfsApplyService;

    /**
     * 道路危险货物申请
     */
    @Resource
    private RelatedRdgtApplyService rdgtApplyService;
    /**
     * 道路危险货物申请
     */
    @Resource
    private RelatedAbcrApplyService abcrApplyService;
    /**
     * 描述
     */
    @Autowired
    private RelatedTaxiApplyService taxiApplyService;
    /**
     * 描述
     */
    @Autowired
    private RelatedRotpApplyService rotpApplyService;
    /**
     * 描述
     */
    @Autowired
    private RelatedSyGenService relatedSyGenService;
    /**
     * 
     */
    @Resource
    private BankGenService bankGenService;
    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        domesticControllerService.remove("T_COMMERCIAL_DOMESTIC", "DOMESTIC_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 企业登记信息记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
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
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> domesticController = domesticControllerService.getByJdbc("T_COMMERCIAL_DOMESTIC",
                    new String[] { "DOMESTIC_ID" }, new Object[] { entityId });
            request.setAttribute("domesticController", domesticController);
        }
        return new ModelAndView("zzhy/domesticController/info");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DOMESTIC_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = domesticControllerService.saveOrUpdate(variables, "T_COMMERCIAL_DOMESTIC", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 企业登记信息记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 企业登记信息记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveScopeRemark")
    @ResponseBody
    public AjaxJson saveScopeRemark(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("COMPANY_ID");
        String SCOPE_REMARK  = request.getParameter("SCOPE_REMARK");
        String tableName = request.getParameter("tableName");
        String tableColumn = request.getParameter("tableColumn");
        Map<String, Object> variables = domesticControllerService.getByJdbc(tableName, new String[] { tableColumn },
                new Object[] { entityId });
        if (null != variables) {
            variables.put("SCOPE_REMARK", SCOPE_REMARK);
            variables.put("BUSSINESS_SCOPE", SCOPE_REMARK);
            String recordId = domesticControllerService.saveOrUpdate(variables, tableName, entityId);
            sysLogService.saveLog("修改了ID为[" + recordId + "]的 企业登记信息记录", SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("保存成功");
        }else{
            j.setSuccess(false);
            j.setMsg("保存失败");
        }
        return j;
    }
    
    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDiv")
    public ModelAndView refreshGdxxDiv(HttpServletRequest request) {
        String companyTypeCode=request.getParameter("companyTypeCode");
        request.setAttribute("companyTypeCode",companyTypeCode);
        String sssblx=request.getParameter("sssblx");
        request.setAttribute("sssblx",sssblx);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/zzhy/gdxxDiv");
    }
    /**
     * 跳转到股东信息界面秒批
     *
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDivMp")
    public ModelAndView refreshGdxxDivMp(HttpServletRequest request) {
        String companyTypeCode=request.getParameter("companyTypeCode");
        request.setAttribute("companyTypeCode",companyTypeCode);
        //是否显示删除键
        String deleKey=request.getParameter("deleKey");
        request.setAttribute("deleKey",deleKey);

        String sssblx=request.getParameter("sssblx");
        request.setAttribute("sssblx",sssblx);
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/zzhy/gdxxDiv_MP");
    }
    /**
     * 跳转到董事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshDsxxDiv")
    public ModelAndView refreshDsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String ssdjzw = request.getParameter("ssdjzw");
        request.setAttribute("ssdjzw", ssdjzw);
        return new ModelAndView("website/applyforms/zzhy/dsxxDiv");
    }
    /**
     * 跳转到董事信息界面
     *
     * @param request
     * @return
     */
    @RequestMapping("/refreshDsxxDivMp")
    public ModelAndView refreshDsxxDivMp(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String ssdjzw = request.getParameter("ssdjzw");
        //是否显示删除键
        String deleKey=request.getParameter("deleKey");
        request.setAttribute("deleKey",deleKey);
        request.setAttribute("ssdjzw", ssdjzw);
        return new ModelAndView("website/applyforms/zzhy/dsxxDiv_MP");
    }
    /**
     * 跳转到监事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJsxxDiv")
    public ModelAndView refreshJsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        //是否显示删除键
        String deleKey=request.getParameter("deleKey");
        request.setAttribute("deleKey",deleKey);
        return new ModelAndView("website/applyforms/zzhy/jsxxDiv");
    }

    /**
     * 跳转到经理信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshJlxxDiv")
    public ModelAndView refreshJlxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/zzhy/jlxxDiv");
    }

    /**
     * 跳转到填写申请表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/txsqb")
    public ModelAndView txsqb(HttpServletRequest request) {
        String COMPANY_TYPE = request.getParameter("COMPANY_TYPE");
        String COMPANY_TYPECODE = request.getParameter("COMPANY_TYPECODE");
        String COMPANY_SETNATURE = request.getParameter("COMPANY_SETNATURE");
        String IS_ACCOUNT_OPEN = request.getParameter("IS_ACCOUNT_OPEN");
        String ISNEEDSIGN = request.getParameter("ISNEEDSIGN");
        String COMPANY_SETTYPE = request.getParameter("COMPANY_SETTYPE");
        String IS_PREAPPROVAL_PASS = request.getParameter("IS_PREAPPROVAL_PASS");
        String ISFIRSTAUDIT = request.getParameter("ISFIRSTAUDIT");
        String ISSOCIALREGISTER = request.getParameter("ISSOCIALREGISTER");
        String ISMEDICALREGISTER = request.getParameter("ISMEDICALREGISTER");
        String ISFUNDSREGISTER = request.getParameter("ISFUNDSREGISTER");
        String ISGETBILL = request.getParameter("ISGETBILL");
        String ISEMAIL = request.getParameter("ISEMAIL");
        String ISTAX = request.getParameter("ISTAX");
        String ISJHYYZZ = request.getParameter("ISJHYYZZ");
        String itemCode = request.getParameter("itemCode");
        String itemCodes = request.getParameter("itemCodes");
        String itemNames = request.getParameter("itemNames");
        String itemId = request.getParameter("itemId");

        if(StringUtils.isNotEmpty(itemId)){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("COMTYPE_ID",itemId);
            Map<String,Object> data = GenPlatReqUtil.getData("pt_comptype_get",params);
            request.setAttribute("COMTYPE_ITEMDESC", data.get("COMTYPE_ITEMDESC"));
        }
        request.setAttribute("COMPANY_TYPE", COMPANY_TYPE);
        request.setAttribute("COMPANY_TYPECODE", COMPANY_TYPECODE);
        request.setAttribute("COMPANY_SETNATURE", COMPANY_SETNATURE);
        request.setAttribute("COMPANY_SETTYPE", COMPANY_SETTYPE);
        request.setAttribute("IS_ACCOUNT_OPEN", IS_ACCOUNT_OPEN);
        request.setAttribute("ISNEEDSIGN", ISNEEDSIGN);
        request.setAttribute("IS_PREAPPROVAL_PASS", IS_PREAPPROVAL_PASS);
        request.setAttribute("ISSOCIALREGISTER", ISSOCIALREGISTER);
        request.setAttribute("ISMEDICALREGISTER", ISMEDICALREGISTER);
        request.setAttribute("ISFUNDSREGISTER", ISFUNDSREGISTER);
        request.setAttribute("ISFIRSTAUDIT", ISFIRSTAUDIT);
        request.setAttribute("ISGETBILL", ISGETBILL);
        request.setAttribute("ISEMAIL", ISEMAIL);
        request.setAttribute("ISTAX", ISTAX);
        request.setAttribute("ISJHYYZZ", ISJHYYZZ);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        String IS_ENGRAVE_SEAL = request.getParameter("IS_ENGRAVE_SEAL");
        request.setAttribute("IS_ENGRAVE_SEAL", IS_ENGRAVE_SEAL);
        String IS_FREE_ENGRAVE_SEAL = request.getParameter("IS_FREE_ENGRAVE_SEAL");
        request.setAttribute("IS_FREE_ENGRAVE_SEAL", IS_FREE_ENGRAVE_SEAL);
        return new ModelAndView("website/zzhy/comptype_desc");
    }

    /**
     * 跳转到填写申请表页面(秒批）
     *
     * @param request
     * @return
     */
    @RequestMapping("/txsqbOfMp")
    public ModelAndView txsqbOfMp(HttpServletRequest request) {
        Map<String,Object> requestParam=BeanUtil.getMapFromRequest(request);
        for(Map.Entry<String,Object> entry : requestParam.entrySet()){
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        String itemId = request.getParameter("itemId");

        if(StringUtils.isNotEmpty(itemId)){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("COMTYPE_ID",itemId);
            Map<String,Object> data = GenPlatReqUtil.getData("pt_comptype_get",params);
            request.setAttribute("COMTYPE_ITEMDESC", data.get("COMTYPE_ITEMDESC"));
        }
        return new ModelAndView("website/zzhy/comptype_descOfMp");
    }
    /**
     * 跳转到平台信息选择页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/ptxxxzmp")
    public ModelAndView ptxxxzmp(HttpServletRequest request) {
        String COMPANY_TYPE = request.getParameter("COMPANY_TYPE");
        String COMPANY_TYPECODE = request.getParameter("COMPANY_TYPECODE");
        String COMPANY_SETNATURE = request.getParameter("COMPANY_SETNATURE");
        
        String itemCode = request.getParameter("itemCode");
        String itemCodes = request.getParameter("itemCodes");
        String itemNames = request.getParameter("itemNames");
        String formKey = request.getParameter("formKey");
        
        request.setAttribute("COMPANY_TYPE", COMPANY_TYPE);
        request.setAttribute("COMPANY_TYPECODE", COMPANY_TYPECODE);
        request.setAttribute("COMPANY_SETNATURE", COMPANY_SETNATURE);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        request.setAttribute("formKey", formKey);
        return new ModelAndView("website/zzhy/gtptxz");
    }
    /**
     * 跳转到填写申请表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/txsqbmp")
    public ModelAndView txsqbmp(HttpServletRequest request) {
        String COMPANY_TYPE = request.getParameter("COMPANY_TYPE");
        String COMPANY_TYPECODE = request.getParameter("COMPANY_TYPECODE");
        String COMPANY_SETNATURE = request.getParameter("COMPANY_SETNATURE");
        
        String itemCode = request.getParameter("itemCode");
        String itemCodes = request.getParameter("itemCodes");
        String itemNames = request.getParameter("itemNames");
        String formKey = request.getParameter("formKey");
        String PT_ID = request.getParameter("PT_ID");
        
        request.setAttribute("COMPANY_TYPE", COMPANY_TYPE);
        request.setAttribute("COMPANY_TYPECODE", COMPANY_TYPECODE);
        request.setAttribute("COMPANY_SETNATURE", COMPANY_SETNATURE);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        request.setAttribute("PT_ID", PT_ID);
        return new ModelAndView("website/zzhy/"+formKey);
    }
    /**
     * 
     * 描述 判断验证码是否正确
     * 
     * @author Rider Chen
     * @created 2016年11月28日 上午11:16:51
     * @param request
     * @param response
     */
    @RequestMapping("/yzmsfzq")
    @ResponseBody
    public void yzmsfzq(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String validateCode = request.getParameter("validateCode");
        String kaRandCode = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(kaRandCode)
                && validateCode.equals(kaRandCode)) {
            result.put("success", true);
            result.put("msg", "验证码正确!");
        } else {
            result.put("success", false);
            result.put("msg", "验证码填写错误!");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 跳转到经营范围选择器
     * 
     * @param request
     * @return
     */
    @RequestMapping("/selector")
    public ModelAndView selector(HttpServletRequest request) {
        String noEcho=request.getParameter("noEcho");
        if("1".equals(noEcho)){
            return new ModelAndView("website/applyforms/change/jyfwSelector");
        }
        String induIds = request.getParameter("induIds");
        String ISTZINDUSTRY = request.getParameter("ISTZINDUSTRY");
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        String checkCascadeY = request.getParameter("checkCascadeY");
        String checkCascadeN = request.getParameter("checkCascadeN");
        String callbackFn = request.getParameter("callbackFn");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("checkCascadeY", checkCascadeY);
        request.setAttribute("checkCascadeN", checkCascadeN);
        request.setAttribute("needCheckIds", induIds);
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("ISTZINDUSTRY", ISTZINDUSTRY);
        if (StringUtils.isNotEmpty(noAuth)) {
            request.setAttribute("noAuth", "true");
        } else {
            request.setAttribute("noAuth", "false");
        }
        return new ModelAndView("website/applyforms/zzhy/jyfwSelector");
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selectorNew")
    public ModelAndView selectorNew(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String noAuth = request.getParameter("noAuth");
        String ids = request.getParameter("ids");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("ids", ids);
        return new ModelAndView("website/applyforms/zzhy/businessScopeSelector");
    }
    /**
     * 跳转到选择器页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectorNewOfMp")
    public ModelAndView selectorNewOfMp(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String noAuth = request.getParameter("noAuth");
        String ids = request.getParameter("ids");
        String mainBussinessId=request.getParameter("mainBussinessId");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("ids", ids);
        request.setAttribute("mainBussinessId",mainBussinessId);
        return new ModelAndView("website/applyforms/zzhy/businessScopeSelectorOfMp");
    }
    /**
     * 跳转到经营范围选择器
     *
     * @param request
     * @return
     */
    @RequestMapping("/evidenceSelector")
    public ModelAndView evidenceSelector(HttpServletRequest request) {
        String induIds = request.getParameter("induIds");
        request.setAttribute("needCheckIds", induIds);
        int allowCount = Integer.parseInt(request.getParameter("allowCount"));
        request.setAttribute("allowCount", allowCount);
        return new ModelAndView("wsbs/applymater/evidenceSelector");
    }
    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-8 下午05:14:33
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId, Map<String, Object> variables) {

        String needCheckIds = (String) variables.get("needCheckIds");
        // 获取topType
        List<Map<String, Object>> children = domesticControllerService.findByParentId(parentId, variables);
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("INDU_ID"));
                child.put("name", child.get("INDU_NAME"));
                if (needCheckIds.contains(child.get("INDU_ID").toString())) {
                    child.put("checked", true);
                }
                // child.put("icon",
                // "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, child.get("INDU_ID").toString(), variables);
            }
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-8 下午05:14:33
     * @param parentType
     * @param parentId
     */
    public void getChildren(Map<String, Object> parentType, String parentId, Map<String, Object> variables,
            List<Map<String, Object>> alllist) {
        
        String needCheckIds = (String) variables.get("needCheckIds");
        // 获取topType
        List<Map<String, Object>> children = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : alllist) {
            String pid = map.get("PARENT_ID").toString();
            if(parentId.equals(pid)){
                children.add(map);
            }
        }        
        if (children != null && children.size() > 0) {
            parentType.put("children", children);
            for (Map<String, Object> child : children) {
                child.put("id", child.get("INDU_ID"));
                child.put("name", child.get("INDU_NAME"));
                if (needCheckIds.contains(child.get("INDU_ID").toString())) {
                    child.put("checked", true);
                }
                // child.put("icon",
                // "plug-in/easyui-1.4/themes/icons/folder_table.png");
                this.getChildren(child, child.get("INDU_ID").toString(), variables,alllist);
            }
        }
    }
    /**
     * 
     * 描述
     * 
     * @author Rider Chen
     * @created 2015-12-8 下午05:14:37
     * @param request
     * @param response
     * @see net.evecom.platform.base.controller.BaseController#tree
     *      (javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @RequestMapping("/tree")
    public void tree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String isShowRoot = (String) variables.get("isShowRoot");
        String needCheckIds = (String) variables.get("needCheckIds");
        String rootParentId = variables.get("rootParentId") == null ? "0" : variables.get("rootParentId").toString();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "类别结构树");
        root.put("open", true);
        // root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", -1);
        root.put("TREE_LEVEL", 1);
        root.put("nocheck", true);
        // 获取topType
        List<Map<String, Object>> toplist = domesticControllerService.findByParentId(rootParentId, variables);
        //获取所有的数据
        List<Map<String, Object>> alllist = domesticControllerService.findByParentId("", variables);
        for (Map<String, Object> top : toplist) {
            top.put("id", top.get("INDU_ID"));
            top.put("name", top.get("INDU_NAME"));
            if (top.get("INDU_CODE").toString().equals("0")) {
                top.put("nocheck", true);
            }
            if (needCheckIds.contains(top.get("INDU_ID").toString())) {
                top.put("checked", true);
            }
            // top.put("icon",
            // "plug-in/easyui-1.4/themes/icons/folder_table.png");
            this.getChildren(top, top.get("INDU_ID").toString(), variables,alllist);
        }
        String json = "";
        if (isShowRoot.equals("true")) {
            root.put("children", toplist);
            json = JSON.toJSONString(root);
        } else {
            json = JSON.toJSONString(toplist);
        }
        this.setJsonString(json, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("zzhy/businessManage/businessScopeView");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "businessScopeDatagrid")
    public void businessScopeDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.BUSSCOPE_CODE","asc");
        List<Map<String, Object>> list = domesticControllerService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "businessScopeSelected")
    public void businessScopeSelected(HttpServletRequest request,
            HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Map<String,Object>> list = null;
        list = domesticControllerService.findByBusinessScopeId(ids);
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "businessScopeInfo")
    public ModelAndView businessScopeInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  businessScope = domesticControllerService.getByJdbc("T_WSBS_BUSSCOPE",
                    new String[]{"ID"},new Object[]{entityId});
            request.setAttribute("businessScope", businessScope);
        }
        return new ModelAndView("zzhy/businessManage/businessScopeInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateBusinessScope")
    @ResponseBody
    public AjaxJson saveOrUpdateBusinessScope(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        domesticControllerService.saveOrUpdate(variables, "T_WSBS_BUSSCOPE", entityId);
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelBusinessScope")
    @ResponseBody
    public AjaxJson multiDelBusinessScope(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        domesticControllerService.deleteBusinessScope(selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 电子证照树
     * @param request
     * @param response
     */
    @RequestMapping("/evidenceTree")
    public void evidenceTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String needCheckIds = (String) variables.get("needCheckIds");
        String isShowRoot = (String) variables.get("isShowRoot");
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "证照结构树");
        root.put("open", true);
        // root.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
        root.put("PARENT_ID", -1);
        root.put("TREE_LEVEL", 1);
        root.put("nocheck", true);
        // 获取topType
        List<Map<String, Object>> toplist = creditService.getOrgList();

        for (Map<String, Object> top : toplist) {
            String name = top.get("NAME") == null ? "" : top.get("NAME").toString();
            top.put("id", top.get("ORG_ID"));
            top.put("code",top.get("CODE"));
            top.put("name", name);
            top.put("nocheck", true);
            if (StringUtils.isNotEmpty(name)) {
                List<Map<String, Object>> metas = creditService.getEvidenceMeta(name);
                if (metas != null && metas.size() > 0) {
                    for (Map<String, Object> meta : metas) {
                        //meta.put("id", meta.get("CODE"));
                        String metaId= meta.get("META_ID").toString();
                        meta.put("code",meta.get("CODE"));
                        meta.put("name", meta.get("NAME"));
                        meta.put("id",metaId);
                        if(needCheckIds.contains(metaId)){
                            meta.put("checked", true);
                        }
                    }
                    top.put("children", metas);
                }
            }
        }
            String json = "";
            if (isShowRoot.equals("true")) {
                root.put("children", toplist);
                json = JSON.toJSONString(root);
            } else {
                json = JSON.toJSONString(toplist);
            }
            this.setJsonString(json, response);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downLoad")
    public void downLoad(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        // 附件ID
        String fileId = request.getParameter("fileId");
        
        String index = request.getParameter("index");//股权转让协议信息(商事变更)索引值
        String stockFrom = "" ;
        if(StringUtils.isNotEmpty(index)){
            int num = Integer.valueOf(index);
            List<Map<String, Object>> stockFromList = commercialSetService.getStockFromList(exeId, fileId);
            if(stockFromList.size()>0){
                stockFrom = JSON.toJSONString(stockFromList.get(num));
            }
        }
        
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        fileName = (String) fileAttach.get("FILE_NAME");
        fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
        String fileType = fileAttach.get("FILE_TYPE").toString();
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = "";
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
            Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");

            /*if(AppUtil.getLoginMember()!=null){
                String useraccount = StringUtil.getString(AppUtil.getLoginMember().get("YHZH"));
                String exeaccount = StringUtil.getString(EFLOWOBJ.get("EFLOW_CREATORACCOUNT"));
                if(!useraccount.equals(exeaccount)){
                    return ;
                }
            }else if(AppUtil.getLoginUser()!=null){
                
            }else{
                return;
            }*/
            
            if (null != busRecord) {
                Map<String, Object> company = domesticControllerService.getByJdbc("T_COMMERCIAL_COMPANY",
                        new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("COMPANY_ID") });
                busRecord.put("FILEID", fileId);
                if (null != company) {
                    busRecord.putAll(company);
                }
                
                String BUS_TABLENAME = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
                //设置业务数据(内资企业变更登记)
                if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_CHANGE_DOMESTIC")){
                    commercialSetService.setCommercialChangeDomestic(busRecord,fileName,stockFrom);
                }else{
                    // 设置其他参数
                    new WordReplaceParamUtil().setParam(busRecord);
                }
                
                //面签信息回填到模板
                exeDataService.setSignInfo(busRecord);
            }
            // 替换模版字段
            WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        }
        // 生成PDF文件
        if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            JComPDFConverter.excel2PDF(newWordFilePath, pdfFile + uploadFullPath);
        } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            WordToPdfUtil.word2pdf(newWordFilePath, pdfFile + uploadFullPath);
        }
        downLoadFile(response, pdfFile, fileName, uploadFullPath);

    }

    /**
     * 描述:下载商事附件（提供给devbase的接口）
     *
     * @author Madison You
     * @created 2020/7/17 14:48:00
     * @param
     * @return
     */
    @RequestMapping("/downLoadForDevbase")
    @ResponseBody
    public Map<String, Object> downLoadForDevbase(HttpServletRequest request) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        // 附件ID
        String fileId = request.getParameter("fileId");
        
        String index = request.getParameter("index");//股权转让协议信息(商事变更)索引值
        String stockFrom = "" ;
        if(StringUtils.isNotEmpty(index)){
            int num = Integer.valueOf(index);
            List<Map<String, Object>> stockFromList = commercialSetService.getStockFromList(exeId, fileId);
            if(stockFromList.size()>0){
                stockFrom = JSON.toJSONString(stockFromList.get(num));
            }
        }
        
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        Map<String, Object> resMap = null;
        try{
            fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                    new Object[] { fileId });
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
            fileName = (String) fileAttach.get("FILE_NAME");
            fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
            String fileType = fileAttach.get("FILE_TYPE").toString();
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = "attachFiles/pdf/" + currentDate;
            String uuId = UUIDGenerator.getUUID();

            Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

            String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
            // 建立全路径目录和临时目录
            File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
            if (!pdfFullPathFolder.exists()) {
                pdfFullPathFolder.mkdirs();
            }
            String newWordFilePath = "";
            if (serviceItem != null) {
                // 获取流程KEY
                String defKey = (String) serviceItem.get("DEF_KEY");
                // 获取流程定义对象
                Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
                newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
                Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
                Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
                if (null != busRecord) {
                    Map<String, Object> company = domesticControllerService.getByJdbc("T_COMMERCIAL_COMPANY",
                            new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("COMPANY_ID") });
                    busRecord.put("FILEID", fileId);
                    if (null != company) {
                        busRecord.putAll(company);
                    }
                    String BUS_TABLENAME = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
                    //设置业务数据(内资企业变更登记)
                    if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_CHANGE_DOMESTIC")){
                        commercialSetService.setCommercialChangeDomestic(busRecord,fileName,stockFrom);
                    }else{
                        // 设置其他参数
                        new WordReplaceParamUtil().setParam(busRecord);
                    }
                    //面签信息回填到模板
                    exeDataService.setSignInfo(busRecord);
                }
                // 替换模版字段
                WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
            }
            // 生成PDF文件
            if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
                uploadFullPath += "/" + uuId + ".pdf";
                JComPDFConverter.excel2PDF(newWordFilePath, pdfFile + uploadFullPath);
            } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
                uploadFullPath += "/" + uuId + ".pdf";
                WordToPdfUtil.word2pdf(newWordFilePath, pdfFile + uploadFullPath);
            }
            /*上传文件到文件服务器*/
            resMap = uploadFileToServer(pdfFile + uploadFullPath, fileName, "pdf");
        } catch (Exception e) {
            log.error("下载商事附件出错");
            log.error(e.getMessage(), e);
        }

        return resMap;
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downLoadWord")
    public void downLoadWord(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        // 附件ID
        String fileId = request.getParameter("fileId");
        
        String index = request.getParameter("index");//股权转让协议信息(商事变更)索引值
        String stockFrom = "" ;
        if(StringUtils.isNotEmpty(index)){
            int num = Integer.valueOf(index);
            List<Map<String, Object>> stockFromList = commercialSetService.getStockFromList(exeId, fileId);
            if(stockFromList.size()>0){
                stockFrom = JSON.toJSONString(stockFromList.get(num));
            }
        }
        
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        fileName = (String) fileAttach.get("FILE_NAME");
        fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
        //String fileType = fileAttach.get("FILE_TYPE").toString();
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = uploadFullPath + "/" + uuId + ".doc";
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
            if (null != busRecord) {
                Map<String, Object> company = domesticControllerService.getByJdbc("T_COMMERCIAL_COMPANY",
                        new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("COMPANY_ID") });
                busRecord.put("FILEID", fileId);
                if (null != company) {
                    busRecord.putAll(company);
                }
                
                String BUS_TABLENAME = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
                //设置业务数据(内资企业变更登记)
                if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_CHANGE_DOMESTIC")){
                    commercialSetService.setCommercialChangeDomestic(busRecord,fileName,stockFrom);
                }else{
                    // 设置其他参数
                    new WordReplaceParamUtil().setParam(busRecord);
                }
                //面签信息回填到模板
                exeDataService.setSignInfo(busRecord);
            }
            // 替换模版字段
            WordReplaceUtil.replaceWord(busRecord, fileRullPath, pdfFile +newWordFilePath);
        }
        downLoadFile(response, pdfFile, fileName, newWordFilePath);

    }
    
    /**
     * 
     * 描述 下载文件
     * 
     * @author Rider Chen
     * @created 2016年11月30日 下午2:11:47
     * @param response
     * @param attachFileFolderPath
     * @param fileName
     * @param uploadFullPath
     */
    private void downLoadFile(HttpServletResponse response, String attachFileFolderPath, String fileName,
            String uploadFullPath) {
        OutputStream toClient = null;
        InputStream fis  = null;
        try {
            String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "."
                    + uploadFullPath.substring(uploadFullPath.lastIndexOf(".") + 1);
            // path是指欲下载的文件的路径。
            File file = new File(attachFileFolderPath + uploadFullPath);

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(attachFileFolderPath + uploadFullPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(newFileName.getBytes("gbk"), "iso8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-msdownload");

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.info("取消下载或下载异常");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
    /**
     * 前台用户打印开户申请书
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downloadLegalSeal")
    public void downloadLegalSeal(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String legalIdNo=request.getParameter("legalIdNo");
        String legalCompanyName=request.getParameter("legalCompanyName");
        //判断是否为商事变更、注销、备案业务
        Map<String,Object> execution = exeDataService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        Map<String,Object> busRecord=exeDataService.getByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[]{"EXE_ID","LEGAL_IDNO_PERSON","LEGAL_COMPANYNAME"},
                new Object[]{exeId,legalIdNo,legalCompanyName});
        String busTableName= StringUtil.getString(execution.get("BUS_TABLENAME"));
        List<Map<String, Object>> applyMaters ;
        if(("T_COMMERCIAL_CHANGE_DOMESTIC".equals(busTableName)) 
                || ("T_COMMERCIAL_SSNZQYBA".equals(busTableName))
                || ("T_COMMERCIAL_CANCEL".equals(busTableName))){//模板为企业用印承诺书
            applyMaters = exeDataService.findMaterFileByItemCode(itemCode,"30");
        }else{//模板为股东法人公司公章
            applyMaters = exeDataService.findMaterFileByItemCode(itemCode,"20");
        }
        String fileId="";
        if(applyMaters!=null&&applyMaters.size()>0){
            Map<String,Object> applyMater=applyMaters.get(0);
            fileId=StringUtil.getString(applyMater.get("MATER_PATH"));
        }
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        fileName = (String) fileAttach.get("FILE_NAME");
        fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
        String fileType = fileAttach.get("FILE_TYPE").toString();
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = "";
        newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
        //模板具体值
        String curDate=DateTimeUtil.getChinaDate(DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd"));
        String legalName=StringUtil.getString(busRecord.get("LEGAL_PERSON"));
        busRecord.putAll(exeDataService.setSignImgByParams(exeId,legalName,legalIdNo,"LEGAL_WRITE_PERSON"));
        busRecord.put("CURDATE",curDate);
        // 替换模版字段
        WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        // 生成PDF文件
        if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            JComPDFConverter.excel2PDF(newWordFilePath, pdfFile + uploadFullPath);
        } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            WordToPdfUtil.word2pdf(newWordFilePath, pdfFile + uploadFullPath);
        }
        downLoadFile(response, pdfFile, fileName, uploadFullPath);
    }

    /**
     * 描述:公章材料下载（给devbase提供的接口）
     *
     * @author Madison You
     * @created 2020/7/17 17:28:00
     * @param
     * @return
     */
    @RequestMapping("/downloadLegalSealForDevbase")
    @ResponseBody
    public Map<String,Object> downloadLegalSealForDevbase(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String legalIdNo=request.getParameter("legalIdNo");
        String legalCompanyName=request.getParameter("legalCompanyName");
        //判断是否为商事变更、注销、备案业务
        Map<String,Object> execution = exeDataService.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        Map<String,Object> busRecord=exeDataService.getByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[]{"EXE_ID","LEGAL_IDNO_PERSON","LEGAL_COMPANYNAME"},
                new Object[]{exeId,legalIdNo,legalCompanyName});
        String busTableName= StringUtil.getString(execution.get("BUS_TABLENAME"));
        Map<String, Object> resMap = null;
        try{
            List<Map<String, Object>> applyMaters ;
            if(("T_COMMERCIAL_CHANGE_DOMESTIC".equals(busTableName)) 
                    || ("T_COMMERCIAL_SSNZQYBA".equals(busTableName))
                    || ("T_COMMERCIAL_CANCEL".equals(busTableName))){//模板为企业用印承诺书
                applyMaters = exeDataService.findMaterFileByItemCode(itemCode,"30");
            }else{//模板为股东法人公司公章
                applyMaters = exeDataService.findMaterFileByItemCode(itemCode,"20");
            }
            String fileId="";
            if(applyMaters!=null&&applyMaters.size()>0){
                Map<String,Object> applyMater=applyMaters.get(0);
                fileId=StringUtil.getString(applyMater.get("MATER_PATH"));
            }
            Map<String, Object> fileAttach = null;
            String attachFileFolderPath = "";
            // 原文件名
            String fileName = "";
            // 原文件路径
            String fileRullPath = "";
            fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                    new Object[] { fileId });
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
            fileName = (String) fileAttach.get("FILE_NAME");
            fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
            String fileType = fileAttach.get("FILE_TYPE").toString();
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = "attachFiles/pdf/" + currentDate;
            String uuId = UUIDGenerator.getUUID();
            String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
            // 建立全路径目录和临时目录
            File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
            if (!pdfFullPathFolder.exists()) {
                pdfFullPathFolder.mkdirs();
            }
            String newWordFilePath = "";
            newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
            //模板具体值
            String curDate=DateTimeUtil.getChinaDate(DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd"));
            String legalName=StringUtil.getString(busRecord.get("LEGAL_PERSON"));
            busRecord.putAll(exeDataService.setSignImgByParams(exeId,legalName,legalIdNo,"LEGAL_WRITE_PERSON"));
            busRecord.put("CURDATE",curDate);
            // 替换模版字段
            WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
            // 生成PDF文件
            if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
                uploadFullPath += "/" + uuId + ".pdf";
                JComPDFConverter.excel2PDF(newWordFilePath, pdfFile + uploadFullPath);
            } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
                uploadFullPath += "/" + uuId + ".pdf";
                WordToPdfUtil.word2pdf(newWordFilePath, pdfFile + uploadFullPath);
            }
            /*上传文件到文件服务器*/
            resMap = uploadFileToServer(pdfFile + uploadFullPath, fileName, "pdf");
        } catch (Exception e) {
            log.error("公章材料下载出错");
            log.error(e.getMessage(), e);
        }

        return resMap;
    }


    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/htmlPreview")
    public ModelAndView htmlPreview(HttpServletRequest request) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        // 附件ID
        String fileId = request.getParameter("fileId");
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        //String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        //fileName = (String) fileAttach.get("FILE_NAME");
        fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
        String fileType = fileAttach.get("FILE_TYPE").toString();
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/shhtml/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(attachFileFolderPath + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = "";
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            newWordFilePath = attachFileFolderPath + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
            Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
            if (null != busRecord) {
                Map<String, Object> company = domesticControllerService.getByJdbc("T_COMMERCIAL_COMPANY",
                        new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("COMPANY_ID") });
                busRecord.put("FILEID", fileId);
                if (null != company) {
                    busRecord.putAll(company);
                }
                // 设置其他参数
                new WordReplaceParamUtil().setParam(busRecord);
            }
            // 替换模版字段
            WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        }
        // 生成HTML文件
        if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
            uploadFullPath += "/" + uuId + ".html";
            JComPDFConverter.excel2HTML(newWordFilePath, attachFileFolderPath + uploadFullPath);
        } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
            uploadFullPath += "/" + uuId + ".html";
            JComPDFConverter.word2Html(newWordFilePath, attachFileFolderPath + uploadFullPath);
        }
       // downLoadFile(response, pdfFile, fileName, uploadFullPath);
        return new ModelAndView("redirect:/"+uploadFullPath);
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/pdfPreview")
    public ModelAndView pdfPreview(HttpServletRequest request) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        // 附件ID
        String fileId = request.getParameter("fileId");
        
        String index = request.getParameter("index");//股权转让协议信息(商事变更)索引值
        String stockFrom = "" ;
        if(StringUtils.isNotEmpty(index)){
            int num = Integer.valueOf(index);
            List<Map<String, Object>> stockFromList = commercialSetService.getStockFromList(exeId, fileId);
            if(stockFromList.size()>0){
                stockFrom = JSON.toJSONString(stockFromList.get(num));
            }
        }
        
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        fileName = (String) fileAttach.get("FILE_NAME");
        fileRullPath = attachFileFolderPath + fileAttach.get("FILE_PATH");
        String fileType = fileAttach.get("FILE_TYPE").toString();
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = "";
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
            Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
            if (null != busRecord) {
                Map<String, Object> company = domesticControllerService.getByJdbc("T_COMMERCIAL_COMPANY",
                        new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("COMPANY_ID") });
                busRecord.put("FILEID", fileId);
                if (null != company) {
                    busRecord.putAll(company);
                }
                
                String BUS_TABLENAME = StringUtil.getString(busRecord.get("BUS_TABLENAME"));
                //设置业务数据(内资企业变更登记)
                if (StringUtils.isNotEmpty(BUS_TABLENAME) && BUS_TABLENAME.equals("T_COMMERCIAL_CHANGE_DOMESTIC")){
                    commercialSetService.setCommercialChangeDomestic(busRecord,fileName,stockFrom);
                }else{
                    // 设置其他参数
                    new WordReplaceParamUtil().setParam(busRecord);
                }
                
                //面签信息回填到模板
                exeDataService.setSignInfo(busRecord);
            }
            // 替换模版字段
            WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        }
        // 生成PDF文件
        if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            JComPDFConverter.excel2PDF(newWordFilePath, pdfFile + uploadFullPath);
        } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            WordToPdfUtil.word2pdf(newWordFilePath, pdfFile + uploadFullPath);
        }
        List<String> list = PdfToImgUtil.pdfToPng(pdfFile + uploadFullPath);
        request.setAttribute("imgList", list);
        request.setAttribute("pdfPath", uploadFullPath);
        // downLoadFile(response, pdfFile, fileName, uploadFullPath);
         //return new ModelAndView("redirect:/"+uploadFullPath);
        return new ModelAndView("website/zzhy/pdfPreview");
    }
    
    /**
     * 
     * 描述    跳转至股权转让协议界面
     * @author Allin Lin
     * @created 2021年5月10日 上午9:51:09
     * @param request
     * @param response
     */
    @RequestMapping(params="goGqzrView")
    public ModelAndView goGqzrView(HttpServletRequest request, HttpServletResponse response){
        String fileId = request.getParameter("fileId");
        String exeId = request.getParameter("exeId");
        String type = request.getParameter("type");
        String itemCode = request.getParameter("itemCode");
        List<Map<String, Object>> stockFromList = commercialSetService.getStockFromList(exeId, fileId);
        request.setAttribute("exeId", exeId);
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("fileId", fileId);
        request.setAttribute("stockFromList", stockFromList);
        
        if(StringUtils.isNotEmpty(type) && "front".equals(type)){//前台页面
            return new ModelAndView("website/applyforms/ssqcwb/change/grzrView"); 
        }
        return new ModelAndView("bsdt/applyform/ssqcwb/change/grzrView");
    }
    
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        filter.addSorted("T.SSBMBM", "desc");
        filter.addSorted("T.RELATED_ID", "desc");
        List<Map<String, Object>> list = domesticControllerService.findByRelatedItemSqlFilter(filter, variables);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/itemSelector")
    public ModelAndView itemSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String induIds = request.getParameter("induIds");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if(StringUtils.isNotEmpty(induIds)&&!induIds.equals("undefined")){
            request.setAttribute("induIds", induIds);
        }
        return new ModelAndView("website/applyforms/zzhy/itemSelector");
    }
    /**
     * 跳转到打印界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/printItem")
    public ModelAndView printItem(HttpServletRequest request) {
        String itemCodes= request.getParameter("itemCodes");
        String itemNames= request.getParameter("itemNames");
        if(StringUtils.isNotEmpty(itemCodes)&& StringUtils.isNotEmpty(itemNames)) {
            List<Map<String, Object>> itemList = new ArrayList<Map<String,Object>>();
            for(int i=0;i<itemCodes.split(",").length;i++) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("itemName", itemNames.split(",")[i]);
                map.put("itemCode", itemCodes.split(",")[i]);
                itemList.add(map);
            }
            request.setAttribute("itemList", itemList);
        }
        return new ModelAndView("website/applyforms/statement/statement");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String induIds = request.getParameter("induIds");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(induIds)){
            list = domesticControllerService.findByRelatedItemId(induIds);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * 跳转到材料信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshMaterDiv")
    public ModelAndView refreshMaterDiv(HttpServletRequest request) {
        //获取申报号
        String exeId = request.getParameter("exeId");
        //获取业务表记录ID
        String busRecordId  = request.getParameter("busRecordId");
        //获取业务表名称
        String busTableName  = request.getParameter("busTableName");
        //获取相关服务事项编码
        String itemCodes  = request.getParameter("itemCodes");
        //获取相关服务事项的材料信息列表
        //List<Map<String,Object>> applyMaters = applyMaterService.findByItemCodes(itemCodes,exeId);
        List<Map<String,Object>> applyMaters = commercialSetService.findMaterByItemCodes(itemCodes,exeId);
        String haveOnline = "";
        for(Map<String,Object> applyMater : applyMaters){
            if("1".equals(applyMater.get("IS_FORM"))){
                haveOnline = haveOnline.concat(applyMater.get("FORM_NAME").toString());
                haveOnline = haveOnline.concat(",");
            }
        }
        if(StringUtils.isNotEmpty(exeId)){
            applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        }
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        //定义材料列表JSON
        String applyMatersJson = JsonUtil.jsonStringFilter(applyMaters,new String[]{"uploadFiles"},true);
        request.setAttribute("applyMaters", applyMaters);
        request.setAttribute("applyMatersJson", StringEscapeUtils.escapeHtml3(applyMatersJson));
        request.setAttribute("applyType", "1");
        request.setAttribute("isWebsite", "1");
        request.setAttribute("haveOnline", haveOnline);
        return new ModelAndView("website/applyforms/zzhy/applyItemMaterList");
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年8月24日 上午11:01:57
     * @param request
     * @return
     */
    @RequestMapping("/onLineForm")
    public ModelAndView onLineForm(HttpServletRequest request){
        String formName = request.getParameter("formName");
        String exeId = request.getParameter("exeId");
        String recordId = request.getParameter("recordId");
        String operType = request.getParameter("operType");
        Map<String,Object> materForm = null;
        if(StringUtils.isNotEmpty(recordId)){
            materForm = domesticControllerService.getByJdbc(formName, new String[] { "RECORD_ID" },
                    new Object[] { recordId });
        }else if(StringUtils.isNotEmpty(exeId)){
            materForm = domesticControllerService.getByJdbc(formName, new String[]{"EXE_ID"}, new Object[]{exeId});
        }
        if(materForm==null){
            materForm = new HashMap<String, Object>();
        }
        materForm.put("formName", formName);
        materForm.put("operType", operType);
        request.setAttribute("materForm", materForm);
        return new ModelAndView("website/applyforms/relatedForm/"+formName);
    }
    /**
     * 
     * 保存在线编辑材料
     * @author Danto Huang
     * @created 2017年8月24日 下午3:45:31
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveRelatedMaterForm")
    @ResponseBody
    public AjaxJson saveRelatedMaterForm(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String formName = request.getParameter("formName");
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = domesticControllerService.saveOrUpdate(variables, formName, entityId);
        j.setJsonString(recordId);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 删除在线编辑材料
     * @author Danto Huang
     * @created 2017年8月24日 下午3:45:31
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/delRelatedMaterForm")
    @ResponseBody
    public AjaxJson delRelatedMaterForm(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
//        String formNames = request.getParameter("formNames");
//        String recordIds = request.getParameter("recordIds");
        return j;
    }
    /**
     * 
     * 初始化是否在线编辑材料信息
     * @author Danto Huang
     * @created 2017年9月12日 上午11:57:11
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/checkHaveOnlineMater")
    @ResponseBody
    public AjaxJson checkHaveOnlineMater(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String exeId = request.getParameter("exeId");
        String itemCodes = request.getParameter("itemCodes");
        if(itemCodes!=null){
            List<Map<String,Object>> onlineMaters = domesticControllerService.findOnlineMaterByItemCodes(itemCodes);
            List<Map<String,Object>> onlineList = null;
            if(onlineMaters!=null){
                for(Map<String,Object> onlineMater : onlineMaters){
                    if(onlineMater.get("FORM_NAME")!=null){
                        Map<String, Object> materForm = domesticControllerService.getByJdbc(
                                onlineMater.get("FORM_NAME").toString(), new String[] { "EXE_ID" },
                                new Object[] { exeId });
                        if(materForm!=null){
                            String recordId = materForm.get("RECORD_ID").toString();
                            Map<String, Object> onlineForm = new HashMap<String, Object>();
                            onlineForm.put("formName", onlineMater.get("FORM_NAME"));
                            onlineForm.put("recordId", recordId);
                            if(onlineList==null){
                                onlineList = new ArrayList<Map<String,Object>>();
                                onlineList.add(onlineForm);
                            }else{
                                onlineList.add(onlineForm);
                            }
                        }
                    }
                }
                j.setJsonString(JSON.toJSONString(onlineList));
            }
        }
        return j;
    }
    
    /**
     * 
     * 描述 下载1+N在线编辑材料
     * @author Danto Huang
     * @created 2017年9月19日 下午3:43:04
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downLoadRelatedMater")
    public void downLoadRelatedMater(HttpServletRequest request,HttpServletResponse response){
        String formName = request.getParameter("fornName");
        String recordId = request.getParameter("recordId");
        Map<String, Object> relatedMater = domesticControllerService.getByJdbc(formName, new String[] { "RECORD_ID" },
                new Object[] { recordId });
        
        String fileName = domesticControllerService.getTableInfoByName(formName).getTableComments()+".docx";
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        String fileRullPath = attachFileFolderPath + "webpage/bsdt/applyform/relatedForm/docTemplate/"+formName+".docx";
        
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/relatedMater/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = uploadFullPath + "/" + uuId + ".doc";
        if(relatedMater!=null){
            //汽车租赁经营许可申请表
            if(formName.equals("RELATED_JJ_CARRENTAL")){
                relatedGenService.genCarRental(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            } else if ("RELATED_JJ_RFSAPPLY".equals(formName)) {
                rfsApplyService.initWordParams(relatedMater); // 初始化word所需数据
             // 根据数据在目标文件夹生成word文件
                rfsApplyService.replaceWord(relatedMater, fileRullPath, pdfFile + newWordFilePath); 
            }else if(formName.equals("RELATED_JJ_RFTAPPLY")){//道路货物运输经营申请表
                relatedGenService.genRftApply(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_JJ_RDGTAPPLY")){//道路危险货物运输经营申请表
                rdgtApplyService.initWordParams(relatedMater);
                rdgtApplyService.replaceWord(relatedMater, fileRullPath, pdfFile + newWordFilePath);
            }else if(formName.equals("RELATED_JJ_RCRAPPLY")){
                relatedGenService.genCarRental(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_JJ_RTSQCBAPPLY")){
                relatedGenService.genJdcjssysRental(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_JJ_TAXIAPPLY")){//巡游出租汽车经营申请表
                taxiApplyService.initWordParams(relatedMater); // 初始化word所需数据
                // 根据数据在目标文件夹生成word文件
                taxiApplyService.replaceWord(relatedMater, fileRullPath, pdfFile + newWordFilePath);
            }else if(formName.equals("RELATED_JJ_DLLKYS")) {//道路旅客运输站经营申请表
                relatedGenService.genDLLKYSZApply(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_JJ_ABCRAPPLY")) {//一、二类汽车维修企业经营许可登记申请表
                abcrApplyService.initWordParams(relatedMater);
                abcrApplyService.replaceWord(relatedMater, fileRullPath, pdfFile + newWordFilePath);
            }else if(formName.equals("RELATED_JJ_CCRAPPLY")) {//三类汽车维修业户经营许可登记申请表

                relatedGenService.genCcrApply(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_JJ_MBRAPPLY")) {//摩托车维修业户经营许可登记申请表
                relatedGenService.genMBRAPPLYApply(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_JJ_ROTPAPPLY")){//道路旅客运输经营申请表
                rotpApplyService.initWordParams(relatedMater); // 初始化word所需数据
                // 根据数据在目标文件夹生成word文件
                rotpApplyService.replaceWord(relatedMater, fileRullPath, pdfFile + newWordFilePath);
            }else if(formName.equals("RELATED_JJ_RPTMAPPLY")) {//道路旅客运输班线经营申请表
                relatedGenService.genRPTMApply(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }else if(formName.equals("RELATED_SY_FOODLIC")) {//《食品经营许可证》申请表
                relatedSyGenService.genFoodlicApply(relatedMater, fileRullPath, pdfFile +newWordFilePath);
            }
        }
        downLoadFile(response, pdfFile, fileName, newWordFilePath);
    }


    /**
     * 后台用户打印开户申请书
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downLoadBankApply")
    public void downLoadBankApply(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        fileRullPath = attachFileFolderPath + "webpage/bsdt/applyform/banktemplate/";
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = uploadFullPath + "/" + uuId + ".doc";
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
            if (null != busRecord) {
                String tableName = EFLOWOBJ.get("EFLOW_BUSTABLENAME").toString();
                if(tableName.equals("T_COMMERCIAL_DOMESTIC")||tableName.equals("T_COMMERCIAL_FOREIGN")){
                    busRecord.put("tableName", "T_COMMERCIAL_COMPANY");
                }else{
                    busRecord.put("tableName", tableName);
                }
                String bankType = busRecord.get("BANK_TYPE").toString();
                if(bankType.equals("ccb")){
                    fileRullPath = fileRullPath + "ccb.docx";
                    fileName = "单位银行结算账户申请书.docx";
                }else if(bankType.equals("pdb")){
                    fileRullPath = fileRullPath + "pdb.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("rcb")){
                    fileRullPath = fileRullPath + "rcb.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("boc")){
                    fileRullPath = fileRullPath + "boc.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("abc")){
                    fileRullPath = fileRullPath + "abc.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("comm")){
                    fileRullPath = fileRullPath + "comm.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }
            }
            bankGenService.genBankApply(busRecord, fileRullPath, pdfFile +newWordFilePath);
        }
        downLoadFile(response, pdfFile, fileName, newWordFilePath);

    }
    
    /**
     * 前台用户打印开户申请书
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downLoadBankApplyFront")
    public void downLoadBankApplyFront(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        fileRullPath = attachFileFolderPath + "webpage/bsdt/applyform/banktemplate/";
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/pdf/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String newWordFilePath = uploadFullPath + "/" + uuId + ".doc";
        if (serviceItem != null) {
            // 获取流程KEY
            String defKey = (String) serviceItem.get("DEF_KEY");
            // 获取流程定义对象
            Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
            Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
            Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
            if (null != busRecord) {
                String tableName = EFLOWOBJ.get("EFLOW_BUSTABLENAME").toString();
                if(tableName.equals("T_COMMERCIAL_DOMESTIC")||tableName.equals("T_COMMERCIAL_FOREIGN")){
                    busRecord.put("tableName", "T_COMMERCIAL_COMPANY");
                }else{
                    busRecord.put("tableName", tableName);
                }
                String bankType = busRecord.get("BANK_TYPE").toString();
                if(bankType.equals("ccb")){
                    fileRullPath = fileRullPath + "ccbno.docx";
                    fileName = "单位银行结算账户申请书.docx";
                }else if(bankType.equals("pdb")){
                    fileRullPath = fileRullPath + "pdbno.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("rcb")){
                    fileRullPath = fileRullPath + "rcbno.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("boc")){
                    fileRullPath = fileRullPath + "bocno.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("abc")){
                    fileRullPath = fileRullPath + "abcno.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }else if(bankType.equals("comm")){
                    fileRullPath = fileRullPath + "commno.docx";
                    fileName = "开立单位银行结算账户申请书.docx";
                }
            }
            bankGenService.genBankApply(busRecord, fileRullPath, pdfFile +newWordFilePath);
        }
        downLoadFile(response, pdfFile, fileName, newWordFilePath);

    }

    /**
     * 描述:打印开户申请书（给devbase提供的接口）
     *
     * @author Madison You
     * @created 2020/7/17 16:27:00
     * @param
     * @return
     */
    @RequestMapping("/downLoadBankForDevbase")
    @ResponseBody
    public Map<String,Object> downLoadBankForDevbase(HttpServletRequest request, HttpServletResponse response) {
        // 获取服务事项编码
        String itemCode = request.getParameter("itemCode");
        // 获取申报号
        String exeId = request.getParameter("exeId");
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        Map<String, Object> resMap = null;
        try{
            Properties properties = FileUtil.readProperties("project.properties");
            attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
            fileRullPath = attachFileFolderPath + "webpage/bsdt/applyform/banktemplate/";
            // 定义相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = "attachFiles/pdf/" + currentDate;
            String uuId = UUIDGenerator.getUUID();

            Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfo(itemCode);

            String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
            // 建立全路径目录和临时目录
            File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
            if (!pdfFullPathFolder.exists()) {
                pdfFullPathFolder.mkdirs();
            }
            String newWordFilePath = uploadFullPath + "/" + uuId + ".doc";
            if (serviceItem != null) {
                // 获取流程KEY
                String defKey = (String) serviceItem.get("DEF_KEY");
                // 获取流程定义对象
                Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defKey, exeId, "true", request);
                Map<String, Object> EFLOWOBJ = (Map<String, Object>) flowAllObj.get("EFLOWOBJ");
                Map<String, Object> busRecord = (Map<String, Object>) EFLOWOBJ.get("busRecord");
                if (null != busRecord) {
                    String tableName = EFLOWOBJ.get("EFLOW_BUSTABLENAME").toString();
                    if(tableName.equals("T_COMMERCIAL_DOMESTIC")||tableName.equals("T_COMMERCIAL_FOREIGN")){
                        busRecord.put("tableName", "T_COMMERCIAL_COMPANY");
                    }else{
                        busRecord.put("tableName", tableName);
                    }
                    String bankType = busRecord.get("BANK_TYPE").toString();
                    if(bankType.equals("ccb")){
                        fileRullPath = fileRullPath + "ccbno.docx";
                        fileName = "单位银行结算账户申请书.docx";
                    }else if(bankType.equals("pdb")){
                        fileRullPath = fileRullPath + "pdbno.docx";
                        fileName = "开立单位银行结算账户申请书.docx";
                    }else if(bankType.equals("rcb")){
                        fileRullPath = fileRullPath + "rcbno.docx";
                        fileName = "开立单位银行结算账户申请书.docx";
                    }else if(bankType.equals("boc")){
                        fileRullPath = fileRullPath + "bocno.docx";
                        fileName = "开立单位银行结算账户申请书.docx";
                    }else if(bankType.equals("abc")){
                        fileRullPath = fileRullPath + "abcno.docx";
                        fileName = "开立单位银行结算账户申请书.docx";
                    }else if(bankType.equals("comm")){
                        fileRullPath = fileRullPath + "commno.docx";
                        fileName = "开立单位银行结算账户申请书.docx";
                    }
                }
                bankGenService.genBankApply(busRecord, fileRullPath, pdfFile +newWordFilePath);
            }
            /*上传文件到文件服务器*/
            resMap = uploadFileToServer(pdfFile + newWordFilePath, fileName, "docx");
        } catch (Exception e) {
            log.error("打印开户申请书出错");
            log.error(e.getMessage(), e);
        }
        return resMap;
    }

    /**
     * 描述:下载医疗保险材料
     *
     * @author Madison You
     * @created 2020/10/19 17:32:00
     * @param
     * @return
     */
    @RequestMapping("/downloadMedicialMater")
    public void downloadMedicialMater(HttpServletRequest request,HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        HashMap<String, Object> busRecord = new HashMap<>();
        Map<String, Object> busMap =exeDataService.getBuscordMap(exeId);
        busMap.putAll(exeDataService.getHhLegal(exeId));
        busRecord.put("dwmc", StringUtil.getValue(busMap, "COMPANY_NAME"));
        busRecord.put("tyshxydm", StringUtil.getValue(busMap, "SOCIAL_CREDITUNICODE"));
        busRecord.put("txdz", StringUtil.getValue(busMap, "REGISTER_ADDR"));
        busRecord.put("frxm", StringUtil.getValue(busMap, "LEGAL_NAME"));
        busRecord.put("frlxdh", StringUtil.getValue(busMap, "LEGAL_MOBILE"));
        busRecord.put("frzjhm", StringUtil.getValue(busMap, "LEGAL_IDNO"));
        busRecord.put("jbrxm", StringUtil.getValue(busMap, "MEDICAL_OPERRATOR"));
        busRecord.put("jbrlxdh", StringUtil.getValue(busMap, "MEDICAL_PHONE"));
        if("T_COMMERCIAL_BRANCH".equals(busMap.get("BUS_TABLENAME"))){
            busRecord.put("dwmc", StringUtil.getValue(busMap, "BRANCH_NAME"));
            busRecord.put("dwlx", "分公司");
        }else if("T_COMMERCIAL_SOLELYINVEST".equals(busMap.get("BUS_TABLENAME"))){
            busRecord.put("dwlx", "个人独资");
            busRecord.put("txdz", StringUtil.getValue(busMap, "COMPANY_ADDR"));
        }else if("T_COMMERCIAL_INTERNAL_STOCK".equals(busMap.get("BUS_TABLENAME"))){
            busRecord.put("dwlx", "股份公司");
        }else{
            busRecord.put("dwlx", domesticControllerService.getCompanyType(busMap));
        }
        Properties properties = FileUtil.readProperties("project.properties");
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        String uploadFullPath = "attachFiles/pdf/" + "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());;
        String unid = UUIDGenerator.getUUID();
        String uploadFullFile = uploadFullPath + "/" + unid + ".docx";
        String newWordFilePath = pdfFile + uploadFullFile;
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String fileRullPath = AppUtil.getRealPath("attachFiles//applymater//medicial//MEDICIAL.docx");
        WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        downLoadFile(response, pdfFile, "职工基本医疗、生育保险用人单位登记表.docx", uploadFullFile);
    }

    /**
     * 描述:材料上传至文件服务器
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/7/17 15:12:00
     */
    private Map<String, Object> uploadFileToServer(String uploadFullPath, String fileName, String ext) {
        Map<String, Object> resMap = null;
        Properties properties = FileUtil.readProperties("project.properties");
        String attachmentFilePath = properties.getProperty("uploadFileUrl").replace("\\", "/");
        String url = attachmentFilePath + "upload/file";
        File file = new File(uploadFullPath);
        // 文件服务器上传
        String app_id = "0001";// 分配的用户名
        String password = "bingo666";// 分配的密码
        String responesCode = "UTF-8";// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "402881e65127d9ea015127d9ea830000");// 上传人ID
        param.put("uploaderName", "商事材料系统自动上传"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + ext;
        param.put("name", newFileName);// 上传附件名
        param.put("busTableName", "T_COMMERCIAL_COMPANY");// 业务表名 param.put("busRecordId",busRecordId);// 业务表ID
        String respResult = HttpRequestUtil.sendFilePost(url, file, responesCode, app_id, password, param);
        if (StringUtils.isNotEmpty(respResult)) {
            resMap = JSON.parseObject(respResult);
        }
        return resMap;
    }
    
    /**
     * 跳转到银行账号信息及委托扣款协议信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshYhzhDiv")
    public ModelAndView refreshYhzhDiv(HttpServletRequest request) {
        
        return new ModelAndView("website/applyforms/zzhy/yhzhDiv");
    }
    
    /**
     * 
     * 描述    发票核定及申领
     * @author Danto Huang
     * @created 2021年6月24日 下午3:03:31
     * @param request
     * @return
     */
    @RequestMapping("/refreshInvoiceApplyDiv")
    public ModelAndView refreshInvoiceApplyDiv(HttpServletRequest request) {
        
        return new ModelAndView("website/applyforms/zzhy/invoiceapplyDiv");
    }
}


