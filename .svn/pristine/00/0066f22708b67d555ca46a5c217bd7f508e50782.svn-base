/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.wsbs.service.DocumentTemplateService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.wsbs.service.VariableService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysUserService;

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
 * 描述  内部流程公文虚拟表管理Controller
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/variableController")
public class VariableController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(VariableController.class);
    /**
     * 引入Service
     */
    @Resource
    private VariableService variableService;
    /**
     * 引入Service
     */
    @Resource
    private DocumentTemplateService documentTemplateService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
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
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
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
        variableService.remove("T_WSBS_VARIABLE","VB_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 内部流程公文虚拟表管理记录",SysLogService.OPERATE_TYPE_DEL);
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
            Map<String,Object>  variable = variableService.getByJdbc("T_WSBS_VARIABLE",
                    new String[]{"VB_ID"},new Object[]{entityId});
            request.setAttribute("variable", variable);
        }
        return new ModelAndView("wsbs/variable/info");
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
        String entityId = request.getParameter("VB_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = variableService.saveOrUpdate(variables, "T_WSBS_VARIABLE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 内部流程公文虚拟表管理记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 内部流程公文虚拟表管理记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年5月10日 下午5:42:22
     * @param request
     * @return
     */
    @RequestMapping(params = "openHtmlTemplate")
    public ModelAndView openHtmlTemplate(HttpServletRequest request){
        String dId = request.getParameter("documentId");
        String exeId = request.getParameter("exeId");
        String toUserName = request.getParameter("toUserName");
        String nodeName = request.getParameter("nodeName");
        //String templatePath = request.getParameter("templatePath");
        String xnbId = request.getParameter("xnbId");
        if(StringUtils.isNotEmpty(dId)){
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            Map<String,Object>  documentTemplate = documentTemplateService.getByJdbc("T_WSBS_DOCUMENTTEMPLATE",
                    new String[]{"DOCUMENT_ID"},new Object[]{dId});
            String url = documentTemplateService.getUrlByDocumentTemplate(documentTemplate,variables);
            StringBuffer urlString = new StringBuffer(url);
            if(urlString.indexOf("?")!=-1){
                urlString.append("&");
            }else{
                urlString.append("?");
            }
            urlString.append("exeId").append("=").append(exeId);
            urlString.append("&");
            urlString.append("toUserName").append("=").append(toUserName);
            urlString.append("&");
            urlString.append("documentId").append("=").append(dId);
            /*if(StringUtils.isNotEmpty(templatePath)&&!templatePath.equals("undefined")){
                urlString.append("&");
                urlString.append("templatePath").append("=").append(templatePath);
            }*/
            if(StringUtils.isNotEmpty(xnbId)&&!xnbId.equals("undefined")){
                urlString.append("&");
                urlString.append("xnbId").append("=").append(xnbId);
            }
            return new ModelAndView("redirect:"+urlString.toString()).addObject("nodeName", nodeName);
        }else{
            return null;
        }
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年5月10日 下午4:51:57
     * @param request
     * @return
     */
    @RequestMapping(params = "htmlTemplate")
    public ModelAndView htmlTemplate(HttpServletRequest request) {
        String htmlName = request.getParameter("htmlName");
        String exeId = request.getParameter("exeId");
        String documentId = request.getParameter("documentId");
        String fromUserName = AppUtil.getLoginUser().getUsername();
        String toUserName = request.getParameter("toUserName");
        String nodeName = request.getParameter("nodeName");
       // String templatePath = request.getParameter("templatePath");
        String xnbId = request.getParameter("xnbId");
        String dq_time = DateTimeUtil.getStrOfDate(new Date(), "yyyy年MM月dd日");
        if(StringUtils.isNotEmpty(htmlName)){
            Map<String,Object>  dataMap = new HashMap<String, Object>();
            dataMap.put("exeId", exeId);
            dataMap.put("documentId", documentId);
            dataMap.put("fromUserName", fromUserName);
            dataMap.put("toUserName", toUserName);
            dataMap.put("nodeName", nodeName);
            dataMap.put("DQ_TIME", dq_time);
            Map<String,Object>  variableMap = null;
            /*if(StringUtils.isNotEmpty(templatePath)&&!templatePath.equals("undefined")){
                variableMap = variableService.getMapByMoreId(exeId,
                        documentId,fromUserName,toUserName,nodeName);
                if(variableMap!=null){
                    dataMap.put("variableMap", variableMap);
                }
            }*/
            if(StringUtils.isNotEmpty(xnbId)&&!xnbId.equals("undefined")){
                variableMap = variableService.getMapByXnbId(xnbId);
                if(variableMap!=null){
                    dataMap.put("variableMap", variableMap);
                }
            }
            //转换成JSON
            String dataMapJson = JSON.toJSONString(dataMap);
            request.setAttribute("dataMap", dataMap);
            request.setAttribute("dataMapJson", StringEscapeUtils.escapeHtml3(dataMapJson));
            Map<String,Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            if(flowExe!=null){
                request.setAttribute("flowExe", flowExe);
            }
            Map<String,Object> serviceItem = serviceItemService.
                    getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                            new Object[] { (String)flowExe.get("ITEM_CODE") });
            if(serviceItem!=null){
                request.setAttribute("serviceItem", serviceItem);
            }
            String tableName = (String) flowExe.get("BUS_TABLENAME");
            String colNames = (String) variableService.getPrimaryKeyName(tableName).get(0);
            String colValues = (String) flowExe.get("BUS_RECORDID");
            Map<String,Object> projectInfo = variableService.getByJdbc(tableName,
                    new String[]{colNames},new Object[]{colValues});
            if(projectInfo!=null){
                request.setAttribute("projectInfo", projectInfo);
            }
            Map<String, Object> templateData = new HashMap<String, Object>();
            if(StringUtils.isNotEmpty(toUserName)){
                Map<String ,Object> userInfo = sysUserService.getUserInfo(toUserName);
                if(userInfo!=null){
                    templateData.put("gwjsbm",(String) userInfo.get("DEPART_NAME"));
                }
            }else{
                templateData.put("gwjsbm", "");
            }
            printAttachService.getTemplateDataMapByExeId(templateData, exeId);
            request.setAttribute("SQData", templateData);
            return new ModelAndView("InternalForm/"+htmlName);
        }else{
            return null;
        }
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年5月12日 下午5:03:29
     * @param request
     */
    @RequestMapping(params = "saveHtmlData")
    @ResponseBody
    public void saveHtmlData(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String xnb_id =  UUIDGenerator.getUUID();//模拟虚拟表主键ID
        String templatePath = variableService.saveHtmlData(variables,xnb_id);

        //保存附件信息        
        String app_id = "0001";// 分配的用户名
        String password = "bingo666";// 分配的密码
        String responesCode = "UTF-8";// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "402881e65127d9ea015127d9ea830000");// 上传人ID
        param.put("uploaderName", "系统自动上传"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        param.put("busTableName", "JBPM6_FLOW_RESULT");// 业务表名
        param.put("busRecordId", xnb_id);// 业务表ID

        Properties properties = FileUtil.readProperties("project.properties");
        //生成word的路径
        String AttachFilePath=properties.getProperty("AttachFilePath").replace("\\", "/");
        //文件服务器地址
        String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn");
        String uploadFileUrl = properties.getProperty("uploadFileUrl");
        String url = uploadFileUrlIn + "upload/file";// 上传路径
        String resultMsg = HttpRequestUtil.sendFilePost(url, AttachFilePath 
                + templatePath, responesCode, app_id, password, param);
        Map<String,Object> resultMap = JSON.parseObject(resultMsg,Map.class);
        Map<String,Object> data =(Map<String, Object>) resultMap.get("data");
        templatePath = uploadFileUrl+data.get("filePath").toString();
        if(templatePath!=null&&xnb_id!=null){
            result.put("msg", "保存成功！");
            result.put("success", true);
            result.put("templatePath", templatePath);
            result.put("xnb_id", xnb_id);
        }else{
            result.put("msg", "保存失败！");
            result.put("success", false);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年5月16日 下午4:54:07
     * @param request
     * @return
     */
    @RequestMapping(params = "ylTemplate")
    public ModelAndView ylTemplate(HttpServletRequest request) {
        String templatePath = request.getParameter("templatePath");
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrl");
        String httpUrl;
        if (StringUtils.isNotEmpty(templatePath)
                && (templatePath.contains("http://") || templatePath.contains("https://"))) {
            httpUrl = templatePath;
        } else{
            httpUrl = uploadFileUrl + templatePath;
        }
        // 建立全路径目录和临时目录
        //newWordFilePath
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        //生成word的路径
        String AttachFilePath=properties.getProperty("AttachFilePath").replace("\\", "/");
        String uploadFullPath = "attachFiles/documenttemplate/" + currentDate;
        String uuId = UUIDGenerator.getUUID();

        File wordPathFolder = new File(AttachFilePath + uploadFullPath);
        if (!wordPathFolder.exists()) {
            wordPathFolder.mkdirs();
        }
        String saveFile=AttachFilePath +uploadFullPath+"/"+uuId+".doc";
        boolean isok = HttpRequestUtil.httpDownload(httpUrl, saveFile);
        if(isok){
            request.setAttribute("templatePath", uploadFullPath+"/"+uuId+".doc");
        }
        return new ModelAndView("InternalForm/ylTemplate");
    }
}

