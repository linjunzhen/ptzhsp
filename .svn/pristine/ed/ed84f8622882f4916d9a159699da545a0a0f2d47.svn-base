/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.DocumentTemplateService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.SerialNumberService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhuozhengsoft.pageoffice.FileSaver;

/**
 * 描述  公文配置Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/documentTemplateController")
public class DocumentTemplateController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DocumentTemplateController.class);
    /**
     * 引入Service
     */
    @Resource
    private DocumentTemplateService documentTemplateService;
    /**
     * 引入Service
     */
    @Resource
    private SerialNumberService serialNumberService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private PrintAttachService printAttachService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;
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
        documentTemplateService.remove("T_WSBS_DOCUMENTTEMPLATE","DOCUMENT_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 公文配置记录",SysLogService.OPERATE_TYPE_DEL);
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
            @SuppressWarnings("unchecked")
            Map<String,Object>  documentTemplate = documentTemplateService.getByJdbc("T_WSBS_DOCUMENTTEMPLATE",
                    new String[]{"DOCUMENT_ID"},new Object[]{entityId});
            request.setAttribute("documentTemplate", documentTemplate);
        }
        return new ModelAndView("wsbs/documenttemplate/documentTemplateInfo");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        String serialres;
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DOCUMENT_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = documentTemplateService.saveOrUpdate(variables, "T_WSBS_DOCUMENTTEMPLATE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            serialres = entityId;
            sysLogService.saveLog("修改了ID为["+entityId+"]的 公文配置记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            serialres = recordId;
            sysLogService.saveLog("新增了ID为["+recordId+"]的 公文配置记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("wsbs/documenttemplate/documentTemplateView");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.create_time","desc");
        List<Map<String, Object>> list = documentTemplateService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "editDocumentTemplate")
    public ModelAndView editDocumentTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            @SuppressWarnings("unchecked")
            Map<String,Object>  documentTemplate = documentTemplateService.getByJdbc("T_WSBS_DOCUMENTTEMPLATE",
                    new String[]{"DOCUMENT_ID"},new Object[]{entityId});
            request.setAttribute("documentTemplate", documentTemplate);
        }
        return new ModelAndView("wsbs/documenttemplate/editDocumentTemplate");
    }
    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showDocumentTemplate")
    public ModelAndView showDocumentTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            @SuppressWarnings("unchecked")
            Map<String,Object>  documentTemplate = documentTemplateService.getByJdbc("T_WSBS_DOCUMENTTEMPLATE",
                    new String[]{"DOCUMENT_ID"},new Object[]{entityId});
            request.setAttribute("documentTemplate", documentTemplate);
        }
        return new ModelAndView("wsbs/documenttemplate/showDocumentTemplate");
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String documentIds = request.getParameter("documentIds");
        if(StringUtils.isNotEmpty(documentIds)&&!documentIds.equals("undefined")){
            request.setAttribute("documentIds", documentIds);
        }
        return new ModelAndView("wsbs/documenttemplate/documentTemplateSelector");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String documentIds = request.getParameter("documentIds");
        List<Map<String, Object>> list = documentTemplateService.findByDocumentIds(documentIds);
        this.setListToJsonString(list.size(), list,
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
    @RequestMapping(params = "selectedPrint")
    public void selectedPrint(HttpServletRequest request,
            HttpServletResponse response) {
        String itemCode = request.getParameter("ITEM_CODE");
        Map<String, Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", 
                new String[] { "ITEM_CODE" },new Object[] { itemCode });
        Map<String, Object> documentInfo = this.serviceItemService
                .getBindDocumentIdANdNames((String)serviceItem.get("ITEM_ID"));
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        String documentIds = (String)documentInfo.get("DOCUMENT_IDS");
        if(StringUtils.isNotEmpty(documentIds)){
            list =  documentTemplateService.findByDocumentIds(documentIds);
        }
        this.setListToJsonString(list.size(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 保存模版文件
     * @author Faker Li
     * @created 2016年1月12日 上午9:29:42
     * @param request
     * @param response
     */
    @RequestMapping("/savefile")
    public void savefile(HttpServletRequest request, HttpServletResponse response) {
        FileSaver fs = new FileSaver(request, response);
        String fileName=fs.getFileName();
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        Properties properties = FileUtil.readProperties("project.properties");
        String templatePath = "";
        String filePath = "";
        String isTemplate = request.getParameter("isTemplate");
        if (StringUtils.isNotEmpty(isTemplate)
                && isTemplate.equals("1")) {
            templatePath = request.getParameter("templatePath");
            filePath = properties.getProperty("AttachFilePath")+request.getParameter("templatePath");
        } else {
            String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
            String uploadPath =  "documenttemplate/";
         // 定义上传文件的保存的相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
         // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            String str = "";
            String uuId = UUIDGenerator.getUUID();
            str = uuId+prefix;
            templatePath = "attachFiles/" + uploadPath+currentDate+"/"+ str;
            filePath = uploadFullPath + str;
        }
        fs.saveToFile(filePath);
        fs.setCustomSaveResult(templatePath);
        fs.close();
    }
    /**
     * 跳转到选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "edit")
    public ModelAndView edit(HttpServletRequest request) {
        Map<String ,Object> template = new HashMap<String, Object>();
        String templateId = request.getParameter("templateId");
        String templateTable = request.getParameter("templateTable");
        String templatePath = request.getParameter("templatePath");
        String exeId = request.getParameter("flowExeId");
        String userCode = request.getParameter("userCode");
        if(StringUtils.isNotEmpty(userCode)){
            Map<String ,Object> userInfo = sysUserService.getUserInfo(userCode);
            if(userInfo!=null){
                template.put("gwjsbm",(String) userInfo.get("DEPART_NAME"));
            }
        }else{
            template.put("gwjsbm", "");
        }
        printAttachService.getTemplateDataMapByExeId(template, exeId);
        if(StringUtils.isNotEmpty(templateId)&&!templateId.equals("undefined")){
            Map<String ,Object> templateInfo = new HashMap<String, Object>();
            if(StringUtils.isNotEmpty(templateTable)&&templateTable.equals("T_WSBS_DOCUMENTTEMPLATE")){
                templateInfo = documentTemplateService.getByJdbc(templateTable, 
                        new String[] { "DOCUMENT_ID" },new Object[] { templateId });
               // template.put("templateName", templateInfo.get("DOCUMENT_NAME"));
                if(StringUtils.isNotEmpty(templatePath)){
                    template.put("templatePath", templatePath);
                    template.put("isTemplate", 1);
                }else{
                    template.put("templatePath","attachFiles/documenttemplate/files/"+templateInfo.get("DOCUMENT_DOC"));
                    template.put("isTemplate", 0);
                }
            }else if(StringUtils.isNotEmpty(templateTable)&&templateTable.equals("T_WSBS_READTEMPLATE")){
                templateInfo = documentTemplateService.getByJdbc(templateTable, 
                        new String[] { "READ_ID" },new Object[] { templateId });
                //template.put("templateName", templateInfo.get("READ_NAME"));
                if(StringUtils.isNotEmpty(templatePath)){
                    template.put("templatePath", templatePath);
                    template.put("isTemplate", 1);
                }else{
                    template.put("templatePath", "attachFiles/readtemplate/files/"+templateInfo.get("READ_DOC"));
                    template.put("isTemplate", 0);
                }
            }else if(StringUtils.isNotEmpty(templateTable)&&templateTable.equals("T_WSBS_TICKETS")){
                templateInfo = documentTemplateService.getByJdbc(templateTable, 
                        new String[] { "TICKETS_ID" },new Object[] { templateId });
                //template.put("templateName", templateInfo.get("TICKETS_NAME"));
                if(StringUtils.isNotEmpty(templatePath)){
                    template.put("templatePath", templatePath);
                    template.put("isTemplate", 1);//不是模版
                }else{
                    template.put("templatePath", "attachFiles/tickets/files/"+templateInfo.get("TICKETS_DOC"));
                    template.put("isTemplate", 0);//是模版
                }
            }
            
        }
        request.setAttribute("template", template);
        return new ModelAndView("wsbs/documenttemplate/editTemplate");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "paramjson")
    public void paramjson(HttpServletRequest request,
            HttpServletResponse response) {
        String formId = request.getParameter("formId");
        if(StringUtils.isNotEmpty(formId)){
            Map<String,Object>  documentTemplate = documentTemplateService.getByJdbc("T_WSBS_DOCUMENTTEMPLATE",
                    new String[]{"DOCUMENT_ID"},new Object[]{formId});
            String paramJson = (String) documentTemplate.get("PARAM_JSON");
            if(StringUtils.isNotEmpty(paramJson)){
                List list = JSON.parseArray(paramJson, Map.class);
                this.setListToJsonString(list.size(), list,
                        null, JsonUtil.EXCLUDE, response);
            }
        }
    }
}

