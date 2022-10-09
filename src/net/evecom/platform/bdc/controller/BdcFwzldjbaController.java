/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.dao.BdcFwzldjbaDao;
import net.evecom.platform.bdc.service.BdcFwzldjbaService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysRoleService;
import net.evecom.platform.wsbs.service.PrintAttachService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.log.SysoCounter;

/**
 * 描述  房屋租赁登记备案Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/bdcFwzldjbaController")
public class BdcFwzldjbaController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcFwzldjbaController.class);
    /**
     * 引入dao
     */
    private BdcFwzldjbaDao dao;
    /**
     * 引入Service
     */
    @Resource
    private BdcFwzldjbaService BdcFwzldjbaService;
    /**
     * 引入Service
     */
    @Resource
    private PrintAttachService printAttachService;
    /**
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;    
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * sysRoleService
     */
    @Resource
    private SysRoleService sysRoleService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        BdcFwzldjbaService.remove("T_BDC_FWZLDJBA","YW_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 不动产房屋租赁登记记录",SysLogService.OPERATE_TYPE_DEL);
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
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  bdcFwzldjba = BdcFwzldjbaService.getByJdbc("T_BDC_FWZLDJBA",
                    new String[]{"YW_ID"},new Object[]{entityId});
            request.setAttribute("bdcFwzldjba", bdcFwzldjba);
        }
        return new ModelAndView("bdc/bdcFwzldjba/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("YW_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = BdcFwzldjbaService.saveOrUpdate(variables, "T_BDC_FWZLDJBA", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 不动产房屋租赁登记备案记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 不动产房屋租赁登记备案记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述
     * @author Yanisin Shi
     * @param request
     * @param data
     * @param tableId
     * @return
     * create time 2021年8月6日
     */
    @RequestMapping( "/savePersons")
    @ResponseBody
    public AjaxJson savePersonsList(HttpServletRequest request,String data,String tableId) {
        
        AjaxJson j = new AjaxJson();
        Map<String, String> fwzldjbaup = new HashMap<String,String>(); 
        String entityId ="";
        
        Map<String, Object> variables = new HashMap<String,Object>(); 
        JSONArray jsonArray=JSONArray.parseArray(data);//转成json数组
        for(int i=0;i<jsonArray.size();i++)//遍历json数组
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
           variables.put( "person_type",(String)jsonObject.get("person_type"));//根据key取值
           variables.put( "lassee_type",(String)jsonObject.get("lassee_type"));
           variables.put( "person_name",(String)jsonObject.get("person_name"));
           variables.put( "card_type",(String)jsonObject.get("card_type"));
           variables.put( "card_no",(String)jsonObject.get("card_no"));
           variables.put( "sex",(String)jsonObject.get("sex"));
           variables.put( "phone",(String)jsonObject.get("phone"));
           variables.put( "ADDRESS",(String)jsonObject.get("ADDRESS"));
           variables.put( "EMAIL",(String)jsonObject.get("EMAIL"));
           variables.put( "YW_ID",(String)jsonObject.get("YW_ID"));
           entityId=(String)jsonObject.get("YW_ID");

        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
      
        String recordId = BdcFwzldjbaService.saveOrUpdate(variables, "T_BDC_FWZLDJBA_PERSONS", "");
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 不动产房屋租赁登记备案人员表记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 不动产房屋租赁登记备案人员表记录",SysLogService.OPERATE_TYPE_ADD);
        }
        Map<String,Object>  bdcfwzldjba=null;
        
        bdcfwzldjba=  this.BdcFwzldjbaService.getByJdbc("T_BDC_FWZLDJBA",  new String[] { "YW_ID" }, new Object[] { entityId }) ; 
        if(bdcfwzldjba!=null){
        
        String bazmbh = StringUtil.getValue(bdcfwzldjba, "BAZMBH");
        //如果备案证明编号为空则插入一条备案证明编号
        if(!StringUtils.isNotEmpty(bazmbh)){
          //获取业务表记录数
            StringBuffer sql = new StringBuffer(" select rownum+1 as 'bazmbh' from t_bdc_fwzldjba");
       
           String b= dao.getByJdbc(sql.toString(), null).get("bazmbh").toString();
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR); 
            String newBazmbh=year*1000000+Integer.valueOf(b)+"";
            fwzldjbaup.put("bazmbh",newBazmbh);
        
        }else{
            fwzldjbaup.put("bazmbh",bazmbh);
        }
        //更新bazmbh
        String record = BdcFwzldjbaService.saveOrUpdate(fwzldjbaup, "T_BDC_FWZLDJBA",entityId);
       
        }
        }
        j.setMsg("成功");
        return j;
    }
    /**
     * 
     * 描述
     * @author Yanisin Shi
     * @param request
     * @param response
     * create time 2021年8月20日
     */
    @RequestMapping(params = "getPersonJson")
    public void getResultFiles(HttpServletRequest request, HttpServletResponse response) {
        String ywid = request.getParameter("YW_ID");
        List<Map<String, Object>> list = BdcFwzldjbaService.findListForResult(ywid);
        
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
   /* @SuppressWarnings("unchecked")
    @RequestMapping(params = "printTemplate")
    public ModelAndView printTemplate(HttpServletRequest request) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String, Object> templateData = new HashMap<String, Object>();
        String exeId = request.getParameter("EFLOW_EXEID");
        String typeId = request.getParameter("typeId");
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        Map<String, Object> execution = null;
        execution = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
        String defId = StringUtil.getValue(execution, "DEF_ID");
        String itemCode = StringUtil.getValue(execution, "ITEM_CODE");
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        
        // 模板表相关信息
        Map<String, String> tableInfo = new HashMap<String, String>();
        tableInfo.put("tableName", "T_WSBS_READTEMPLATE");
        tableInfo.put("idCol", "READ_ID");
        tableInfo.put("nameCol", "READ_NAME");
        tableInfo.put("docCol", "READ_DOC");
        tableInfo.put("aliasCol", "ALIAS");
        // 获取模板数据
        // 如果传进来的templateName是别名
        Map<String, Object> templateInfo = printAttachService.getByJdbc(tableInfo.get("tableName"), new String[] { tableInfo.get("aliasCol") }, new Object[] { templateName });
        if (templateInfo == null) {
            // 如果传进来的TemplateName是模板名称则根据名称取
            templateInfo = printAttachService.getByJdbc(tableInfo.get("tableName"), new String[] { tableInfo.get("nameCol") }, new Object[] { templateName });
        } else {
            templateData.put("TeplateName", StringUtil.getValue(templateData, "TemplateName"));
        }
        if (StringUtils.isBlank(StringUtil.getValue(templateData, "TemplatePath"))) {
            templateData.put("TemplatePath", StringUtil.getValue(templateInfo, tableInfo.get("docCol")));
        }
        templateData.put("tableType", StringUtil.getValue(variables, "tableType"));
        String alias = StringUtil.getValue(templateInfo, tableInfo.get("aliasCol"));
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { alias, itemCode });
        if (dictionary == null) {
            String tplName = StringUtil.getValue(templateInfo, tableInfo.get("nameCol"));
            dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { tplName, itemCode });
        }
            //房屋租赁登记备案申请表
        getTemplateDataMap(templateData, exeId);
        
        
        
     
        
        request.setAttribute("regTable", (Map<String, Object>) templateData.get("regTable"));
        templateData.remove("regTable");

        for (Map.Entry<String, Object> entry : templateData.entrySet()) {
            String valString = entry.getValue() == null ? "" : entry.getValue().toString();
            valString = replaceBlank(valString);
            templateData.put(entry.getKey(), valString);
        }        
     // 获取二维码保存路径
        String path = ExcelRedrawUtil.getSavePath("jpg");
        // 生成二维码
        String ewMurl = "http://xzfwzx.pingtan.gov.cn/cms-h5/#/approvalEvaluate?exeId=";
        ExcelRedrawUtil.encodeVersion6(ewMurl + exeId + "&evalChannel=1", path);
        templateData.put("ewm", "[image]" + path + "[/image]");

       // request.setAttribute("isSignButton", request.getParameter("isSignButton"));
        request.setAttribute("TemplateData", templateData);
        request.setAttribute("exeId", exeId);
       
           return new ModelAndView("wsbs/readtemplate/printReadTemplate");
      
        
    }*/
    /**
     * 
     * 描述 获取回填数据
     * 
     * @author Faker Li
     * @created 2016年3月3日 上午10:35:36
     * @param templateData
     * @param exeId
     * @param templatePath
     * @param templateName
     */
    public void getTemplateDataMap(Map<String, Object> templateData, String exeId) {
        this.BdcFwzldjbaService.getTemplateDataMapByExeId(templateData, exeId);
    }
    
    /**
     * 替换换行、制表符
     * 
     * @param str
     * @return
     */
    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("  ");
        }
        return dest;
    }
    
    
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String negativeListCodes = request.getParameter("negativeListCodes");
        String negativeListNames = request.getParameter("negativeListNames");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        String isYh = request.getParameter("isYh");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if(StringUtils.isNotEmpty(negativeListCodes)&&!negativeListCodes.equals("undefined")){
            request.setAttribute("negativeListCodes", negativeListCodes);
            request.setAttribute("negativeListNames", negativeListNames);
        }
        if (isYh != null && "0".equals(isYh)) {
            request.setAttribute("required", "");
            request.setAttribute("isBank", false);
        } else {
            //判断是否银行人员
            SysUser curUser = AppUtil.getLoginUser();
            boolean isBankRole = sysRoleService.hasRoleByCode(curUser.getUserId(), new String[] {"BDCBANKROLE"});
            if(isBankRole) {
                request.setAttribute("required", "required");
            }else {
                request.setAttribute("required", "");
            }
            request.setAttribute("isBank", isBankRole);
        }
        return new ModelAndView("bsdt/applyform/bdcFwzldjba/bdcdydacxSelector");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcdjzmh = variables.get("bdcdjzmh") == null ? "" : variables.get("bdcdjzmh").toString();
        if(bdcdjzmh != null && StringUtils.isNotEmpty(bdcdjzmh)){
            bdcdjzmh = bdcdjzmh.replace("&#40;", "(");
            bdcdjzmh = bdcdjzmh.replace("&#41;", ")");
            bdcdjzmh = bdcdjzmh.replace("&#42;", "*");
        }
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcdjzmh)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdyxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:查封信息数据
     *
     * @author Madison You
     * @created 2020/6/8 15:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdccfxxDatagrid")
    public void bdccfxxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String qlrmc = variables.get("qlrmc") == null ? "" : variables.get("qlrmc").toString();
        String zjhm = variables.get("zjhm") == null ? "" : variables.get("zjhm").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(bdcdyh) || StringUtils.isNotEmpty(qlrmc) || StringUtils.isNotEmpty(zjhm)) {
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdccfxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 跳转到不动产档案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcdaxxcxSelector")
    public ModelAndView bdcdaxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        String isYh = request.getParameter("isYh");
        String isRequired = request.getParameter("isRequired");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if (isYh != null && "0".equals(isYh)) {
            request.setAttribute("required", "");
            request.setAttribute("isBank", false);
        } else {
            //判断是否银行人员
            SysUser curUser = AppUtil.getLoginUser();
            boolean isBankRole = sysRoleService.hasRoleByCode(curUser.getUserId(), new String[] {"BDCBANKROLE"});
            if(isBankRole) {
                request.setAttribute("required", "required");
            }else {
                request.setAttribute("required", "");
            }
            request.setAttribute("isBank", isBankRole);
        }
        if (StringUtils.isNotEmpty(isRequired) && isRequired.equals("1")) {
            request.setAttribute("required", "required");
            request.setAttribute("isBank", true);
        }
        return new ModelAndView("bsdt/applyform/bdcFwzldjba/bdcdaxxcxSelector");
    }
    

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "bdcdaxxcxDatagrid")
    public void bdcdaxxcxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcqzh = variables.get("bdcqzh") == null ? "" : variables.get("bdcqzh").toString();
        if(bdcqzh != null && StringUtils.isNotEmpty(bdcqzh)){
            bdcqzh = bdcqzh.replace("&#40;", "(");
            bdcqzh = bdcqzh.replace("&#41;", ")");
            bdcqzh = bdcqzh.replace("&#42;", "*");
        }
        String ywh = variables.get("ywh") == null ? "" : variables.get("ywh").toString();
        String qlrmc = variables.get("qlrmc") == null ? "" : variables.get("qlrmc").toString();
        String zjhm = variables.get("zjhm") == null ? "" : variables.get("zjhm").toString();
        String fwbm = variables.get("fwbm") == null ? "" : variables.get("fwbm").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcqzh)
                ||StringUtils.isNotEmpty(ywh)||StringUtils.isNotEmpty(qlrmc)
                ||StringUtils.isNotEmpty(zjhm)||StringUtils.isNotEmpty(fwbm)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdaxxcxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    

    /**
     * 跳转到不动产档案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcygdacxSelector")
    public ModelAndView bdcygdacxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        //当noLike=1时表示页面上的不动产单元号采用精确查询
        String noLike = request.getParameter("noLike");
        if(noLike == null){
            noLike = "0";
        }
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("noLike", noLike);
        return new ModelAndView("bsdt/applyform/bdcFwzldjba/bdcygdacxSelector");
    }

    /**
     * 跳转到不动产档案信息查询选择器页面（外网）
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcygdacxSelectorWw")
    public ModelAndView bdcygdacxSelectorWw(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        //当noLike=1时表示页面上的不动产单元号采用精确查询
        String noLike = request.getParameter("noLike");
        if(noLike == null){
            noLike = "0";
        }
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("noLike", noLike);
        return new ModelAndView("website/applyforms/bdcqlc/selector/bdcygdacxSelectorWw");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "bdcygdacxDatagrid")
    public void bdcygdacxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcdjzmh = variables.get("bdcdjzmh") == null ? "" : variables.get("bdcdjzmh").toString();
        if(bdcdjzmh != null && StringUtils.isNotEmpty(bdcdjzmh)){
            bdcdjzmh = bdcdjzmh.replace("&#40;", "(");
            bdcdjzmh = bdcdjzmh.replace("&#41;", ")");
            bdcdjzmh = bdcdjzmh.replace("&#42;", "*");
        }
        String qlr = variables.get("qlr") == null ? "" : variables.get("qlr").toString();
        String qlrzjh = variables.get("qlrzjh") == null ? "" : variables.get("qlrzjh").toString();
        String ywr = variables.get("ywr") == null ? "" : variables.get("ywr").toString();
        String ywrzjh = variables.get("ywrzjh") == null ? "" : variables.get("ywrzjh").toString();
        String ywh = variables.get("ywh") == null ? "" : variables.get("ywh").toString();
        String bdczl = variables.get("bdczl") == null ? "" : variables.get("bdczl").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcdjzmh)
                ||StringUtils.isNotEmpty(qlr)||StringUtils.isNotEmpty(qlrzjh)
                ||StringUtils.isNotEmpty(ywr)||StringUtils.isNotEmpty(ywrzjh)
                ||StringUtils.isNotEmpty(ywh)||StringUtils.isNotEmpty(bdczl)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "announceUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述：常用意见选择器
     * @author Rider Chen
     * @created 2019年3月21日 下午3:41:20
     * @param request
     * @return
     */
    @RequestMapping(params = "cyyjmbSelector")
    public ModelAndView cyyjmbSelector(HttpServletRequest request) {
        String opnionId = request.getParameter("OPINION_ID");
        String businessName = request.getParameter("businessName");
        if (StringUtils.isNotEmpty(opnionId) && !opnionId.equals("undefined")) {
            request.setAttribute("OPINION_ID", opnionId);
        }
        request.setAttribute("businessName", businessName);
        return new ModelAndView("bsdt/applyform/cyyjmb/cyyjmbSelector");
    }
    
    /**
     * 描述  跳转至常用意见信息页面   
     * @author Allin Lin
     * @created 2019年3月6日 上午12:24:40
     * @param request
     * @return
     */
    @RequestMapping(params = "cyjjmbInfo")
    public ModelAndView cyjjmbInfo(HttpServletRequest request){
        String businessName = request.getParameter("businessName");
        request.setAttribute("businessName", businessName);
        return new ModelAndView("bsdt/applyform/cyyjmb/cyyjmbInfo");
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "cyyjmbMmultiDel")
    @ResponseBody
    public AjaxJson cyyjmbMmultiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        BdcFwzldjbaService.remove("T_WSBS_OPINION","OPINION_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 常用意见记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 
     * 加载数据字典自动补全数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadDicSearch")
    public void loadDicSearch(HttpServletRequest request, HttpServletResponse response) {
        String typeCode = request.getParameter("typeCode");
        List<Map<String, Object>> list = dictionaryService.findByTypeCode(typeCode);
        for (Map<String, Object> map : list) {
            map.put("PINYINSZM", StringUtil.converterToFirstSpell((String) map.get("DIC_NAME")));
            map.put("PINYIN", StringUtil.getPingYin((String) map.get("DIC_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 描述:判断是否有受理分户业务
     *
     * @author Madison You
     * @created 2020/8/26 10:12:00
     * @param
     * @return
     */
    @RequestMapping(params = "checkBdcfh")
    @ResponseBody
    public Map<String, Object> checkBdcfh(HttpServletRequest request) {
        HashMap<String, Object> returnMap = new HashMap<>();
        String bdcdyh = request.getParameter("bdcdyh");
        if (StringUtils.isNotEmpty(bdcdyh)) {
            List<Map<String, Object>> list = BdcFwzldjbaService.findBdcfhData(bdcdyh);
            if (list != null && !list.isEmpty()) {
                returnMap.put("success", true);
            } else {
                returnMap.put("success", false);
            }
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }

    
  /**
   * 
   * 描述    保存房屋租赁登记备案信息
   * @author Yanisin Shi
   * @param request
   * @param response
   * @return
   * @create 2021年7月23日
   */
    @RequestMapping(params = "saveFwzldjbaxx")
    @ResponseBody
    public Map<String, Object> saveFwzldjbaxx(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        String busTableName = (String) variables.get("busTableName");
        String busRecordId = (String) variables.get("busRecordId");
        try{
            if (StringUtils.isNotEmpty(busTableName) && StringUtils.isNotEmpty(busRecordId)) {
                BdcFwzldjbaService.saveOrUpdate(variables, busTableName, busRecordId);
                returnMap.put("success", true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return returnMap;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "fwzlStatis")
    public ModelAndView fwzlStatis(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/fwzlStatis");
    }
    /**
     * 
     * 描述    转跳房屋租赁登记备案查询页面
     * @author Yanisin Shi
     * @param request
     * @return
     * create time 2021年8月23日
     */
    
    @RequestMapping(params = "cxbdcfwzldjba")
    public ModelAndView cxbdcfwzldjba(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/cxbdcfwzldjbaStatist");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bdcfwzldjbaStatisData")
    public void bdcfwzldjbaStatisData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = BdcFwzldjbaService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

}

