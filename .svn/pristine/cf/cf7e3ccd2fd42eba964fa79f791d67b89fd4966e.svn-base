/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.zhuozhengsoft.pageoffice.FileSaver;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.servlet.DownLoadServlet;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.CertificateService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 
 * 描述 权证模板配置service
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月20日 上午11:44:33
 */
@Controller
@RequestMapping("/certificateTemplateController")
public class CertificateTemplateController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CertificateTemplateController.class);
    /**
     * 引入Service
     */
    @Resource
    private CertificateService certificateService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
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
     * 引入service
     */
    @Resource
    private PrintAttachService printAttachService;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;

    /**
     * 引入Service
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * 描述 方法del
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
        certificateService.remove("T_WSBS_CERTIFICATE", "CERTIFICATE_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 打证模板记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 描述 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> certificateTemplate = certificateService.getByJdbc("T_WSBS_CERTIFICATE",
                    new String[] { "CERTIFICATE_ID" }, new Object[] { entityId });
            request.setAttribute("certificateTemplate", certificateTemplate);
        }
        return new ModelAndView("bdcqlc/certificateTemplate/certificateTemplateInfo");
    }

    /**
     * 描述 新增或保存模板信息时更新表及保存操作日志
     * 
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CERTIFICATE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = certificateService.saveOrUpdate(variables, "T_WSBS_CERTIFICATE", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 权证模板记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 权证模板记录", SysLogService.OPERATE_TYPE_ADD);
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
        return new ModelAndView("bdcqlc/certificateTemplate/certificateTemplateView");
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
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.create_time", "desc");
        List<Map<String, Object>> list = certificateService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 编辑模板
     * 
     * @author Roger Li
     * @created 2019年12月23日 上午11:22:12
     * @param request
     * @return
     */
    @RequestMapping(params = "editCertificateTemplate")
    public ModelAndView editCertificateTemplate(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> certificateTemplate = certificateService.getByJdbc("T_WSBS_CERTIFICATE",
                    new String[] { "CERTIFICATE_ID" }, new Object[] { entityId });
            request.setAttribute("certificateTemplate", certificateTemplate);
        }
        return new ModelAndView("bdcqlc/certificateTemplate/editCertificateTemplate");
    }

    /**
     * 
     * 描述 保存模板文件
     * 
     * @author Roger Li
     * @created 2019年12月23日 上午11:22:27
     * @param request
     * @param response
     */
    @RequestMapping("/savefile")
    public void savefile(HttpServletRequest request, HttpServletResponse response) {
        String str = "";
        String filename = request.getParameter("filename");
        String alias = request.getParameter("alias");
        if (StringUtils.isNotEmpty(filename) && !filename.equals("undefined")) {
            str = filename;
        } else if (StringUtils.isNotEmpty(alias) && !alias.equals("undefined")) {
            str = alias + ".xls";
        }
        FileSaver fs = new FileSaver(request, response);
        String filePath = request.getSession().getServletContext()
                .getRealPath("attachFiles//certificateTemplate//files/") + "/" + str;
        fs.saveToFile(filePath);
        fs.setCustomSaveResult(str);
        fs.close();
    }

    /**
     * 跳转到列表界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "showCertificateTemplate")
    public ModelAndView showCertificateTemplate(HttpServletRequest request, HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> certificateTemplate = certificateService.getByJdbc("T_WSBS_CERTIFICATE",
                    new String[] { "CERTIFICATE_ID" }, new Object[] { entityId });
            request.setAttribute("certificateTemplate", certificateTemplate);
        }
        return new ModelAndView("bdcqlc/certificateTemplate/showCertificateTemplate");
    }

    /**
     * 
     * 描述 调用自定义方法回填数据到Excel
     * 
     * @author Roger Li
     * @created 2019年12月24日 上午11:54:55
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "invokeCustomMethod")
    public ModelAndView invokeCustomMethod(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String exeId = request.getParameter("EFLOW_EXEID");
        String typeId = request.getParameter("typeId");
        String templatePath = request.getParameter("TemplatePath");
        String templateAlias = request.getParameter("templateAlias");
        returnMap.put("TemplatePath", templatePath);
        returnMap.put("xmsqbh", exeId);
        // returnMap.put("TemplateName", templateName);

        Map<String, Object> execution = null;
        Map<String, Object> busInfo = null;
        if (StringUtils.isNotBlank(exeId) && !exeId.equals("undefined")) {
            execution = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if (execution == null) {
                execution = this.executionService.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] { exeId });
            }
            String BUS_RECORDID = (String) execution.get("BUS_RECORDID");
            String BUS_TABLENAME = (String) execution.get("BUS_TABLENAME");
            //国有转移7个事项虚拟主表替换真实表名称
            if(BUS_TABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                BUS_TABLENAME = "T_BDCQLC_GYJSJFWZYDJ";
            }
            if (StringUtils.isNotEmpty(BUS_RECORDID) && StringUtils.isNotEmpty(BUS_TABLENAME)) {
                String primaryKeyName = certificateService.getPrimaryKeyName(BUS_TABLENAME).get(0).toString();
                busInfo = certificateService.getByJdbc(BUS_TABLENAME, new String[]{primaryKeyName},
                        new Object[]{BUS_RECORDID});
            }
        }
        if (execution == null || StringUtils.isBlank(exeId) || StringUtils.isBlank(typeId)) {
            return null;
        }
        String defId = StringUtil.getValue(execution, "DEF_ID");
        String itemCode = StringUtil.getValue(execution, "ITEM_CODE");
        // 获取流程明细数据
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        // 开始定义流程信息对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defId, exeId, "false", request);

        // 模板表相关信息
        Map<String, String> tableInfo = new HashMap<String, String>();
        tableInfo.put("tableName", "T_WSBS_CERTIFICATE");
        tableInfo.put("idCol", "CERTIFICATE_ID");
        tableInfo.put("nameCol", "TEMPLATE_NAME");
        tableInfo.put("aliasCol", "ALIAS");
        tableInfo.put("docCol", "CERTIFICATE_DOC");
        // 获取模板数据
        Map<String, Object> templateInfo = printAttachService.getByJdbc(tableInfo.get("tableName"),
                new String[] { tableInfo.get("aliasCol") }, new Object[] { templateAlias });
        if (StringUtils.isBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
            returnMap.put("TemplatePath", StringUtil.getValue(templateInfo, tableInfo.get("docCol")));
        }
        returnMap.put("TemplateName", StringUtil.getValue(templateInfo, tableInfo.get("nameCol")));
        String templateId = StringUtil.getValue(templateInfo, tableInfo.get("idCol"));
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { templateId, itemCode });
        // 如果有配置自定义方法则走自定义方法
        if (dictionary != null) {
            String handleMethod = StringUtil.getValue(dictionary, "DIC_NAME");
            String[] temp = handleMethod.split("\\.");
            if (temp.length == 2) {
                String targetClassName = temp[0];
                String targetMethod = temp[1];
                // 构造参数
                Map<String, Map<String, Object>> args = new HashMap<String, Map<String, Object>>();
                args.put("execution", execution);
                /*放入业务数据*/
                args.put("busInfo", busInfo);
                args.put("serviceItem", serviceItem);
                args.put("flowAllObj", flowAllObj);
                args.put("returnMap", returnMap);
                // 把request中的参数也放进来
                args.put("requestMap", variables);
                // 从容器中获取该类实例并传参执行指定的方法
                try {
                    Method customMethod = AppUtil.getBean(targetClassName).getClass().getMethod(targetMethod,
                            Map.class);
                    customMethod.invoke(AppUtil.getBean(targetClassName), args);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
        for (Map.Entry<String, Object> entry : returnMap.entrySet()) {
            String valString = entry.getValue() == null ? "" : entry.getValue().toString();
            valString = replaceBlank(valString);
            returnMap.put(entry.getKey(), valString);
        }
        request.setAttribute("TemplateData", returnMap);
        if (typeId.equals("1")) {
            return new ModelAndView("bdcqlc/certificateTemplate/previewCertificateTemplate");
        } else {
            return new ModelAndView("bdcqlc/certificateTemplate/printCertificateTemplate");
        }
    }
    
    /**
     * 
     * 描述    根据文件路径预览附件（pageoffice预览）
     * @author Allin Lin
     * @created 2021年7月13日 下午4:17:54
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping(params = "preViewFile")
    public ModelAndView preViewFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String view ="";
        DownLoadServlet downLoadServlet = new DownLoadServlet();
        String filePath = request.getParameter("filePath");
        String type = request.getParameter("type");//打开类型（图片/文件）
        String fileType = request.getParameter("fileType");//文件类型
        if("image".equals(type)){
            view ="bsdt/applyform/previewImage";
        }else if("file".equals(type)){
            //从远程服务器下载文件至本地
            Properties projectProperties = FileUtil.readProperties("project.properties");
            String fileServer = projectProperties.getProperty("uploadFileUrlIn");
            String localPath = projectProperties.getProperty("AttachFilePath");//本地工程路径
            // 设置日期格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String localFolder = "attachFiles\\previewFileTemp\\" + simpleDateFormat.format(new Date());
            String fileName = filePath.substring(filePath.lastIndexOf("/"), filePath.length());
            localPath += localFolder;
            localPath += fileName;
            InputStream is = downLoadServlet.getStreamDownloadOutFile(fileServer+filePath);
            //将文件流转换成文件
            if(is!=null){
                byte[] bytes = FileUtil.convertUrlFileToBytes(is);
                if(bytes.length>0){
                    FileUtil.bytesToFile(bytes, localPath);
                    filePath = localFolder + fileName;
                } 
            }
            if("pdf".equals(fileType)){
                view = "bsdt/applyform/previewPdf";
            }else{
                view = "bsdt/applyform/previewFile";
                request.setAttribute("fileType", fileType);  
            }
        }
        request.setAttribute("fileAllPath", filePath);
        return new ModelAndView(view);
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
        String certificateIds = request.getParameter("certificateIds");
        if (StringUtils.isNotEmpty(certificateIds) && !certificateIds.equals("undefined")) {
            request.setAttribute("certificateIds", certificateIds);
        }
        return new ModelAndView("bdcqlc/certificateTemplate/certificateTemplateSelector");
    }

    /**
     * 描述 easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request, HttpServletResponse response) {
        String certificateIds = request.getParameter("certificateIds");
        List<Map<String, Object>> list = certificateService.findByCertificateId(certificateIds);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 获取当前环节可以打印的权证模板
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "selectedPrint")
    public void selectedPrint(HttpServletRequest request, HttpServletResponse response) {
        String itemCode = request.getParameter("ITEM_CODE");
        String exeId = request.getParameter("EXE_ID");
        // String username = request.getParameter("username");
        String CUR_STEPNAMES = request.getParameter("CUR_STEPNAMES");// 环节
        // String BUS_TABLENAME = request.getParameter("BUS_TABLENAME");//业务表名
        List<Map<String, Object>> taskInfos = serviceItemService.getAllByJdbc("JBPM6_TASK",
                new String[] { "EXE_ID", "TASK_NODENAME" }, new Object[] { exeId, CUR_STEPNAMES });
        // String TASK_NODENAME="";
        String TASK_STATUS = "";
        if (taskInfos.size() > 0) {// 在办状态的记录为0条
            /*
             * TASK_NODENAME = taskInfos.get(0).get("TASK_NODENAME")==null?"":
             * taskInfos.get(0).get("TASK_NODENAME").toString();
             */
            TASK_STATUS = taskInfos.get(0).get("TASK_STATUS") == null ? ""
                    : taskInfos.get(0).get("TASK_STATUS").toString();// 所处状态
        }

        // 暂时在字典里配置 一对一
        Map<String, Object> dictionary1 = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { "SXYDZMB", itemCode });
        // Map<String, Object> certificateInfo = this.serviceItemService
        // .getBindReadIdANdNames((String)serviceItem.get("ITEM_ID"));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String certificateIds = StringUtil.getValue(dictionary1, "DIC_NAME");
        if (StringUtils.isNotEmpty(certificateIds)) {
            list = certificateService.findByCertificateId(certificateIds);
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            String limitStatus = map.get("LIMIT_STATUS") == null ? "" : map.get("LIMIT_STATUS").toString();
            String limitNodename = map.get("LIMIT_NODENAME") == null ? "" : map.get("LIMIT_NODENAME").toString();
            Boolean isNeedStatus = true;
            Boolean isNeedStepNames = true;
            String certificateId = map.get("certificate_ID") == null ? "" : map.get("certificate_ID").toString();
            if (taskInfos.size() == 0) {
                isNeedStatus = false;
                isNeedStepNames = false;
                // 办结后仅两单据可以打印
                if (certificateId.equals("402881ae52339704015233c1c3540018")
                        || certificateId.equals("402881ae52339704015233c94a09001e")) {
                    isNeedStatus = true;
                    isNeedStepNames = true;
                }
            }
            // 是否展示电子签证按钮
            String isSignButton = "false";
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { "isSignButton", certificateId });
            if ("是".equals(StringUtil.getValue(dictionary, "DIC_NAME"))) {
                isSignButton = "true";
            }
            map.put("IS_SIGN_BUTTON", isSignButton);
            if (StringUtils.isNotEmpty(limitStatus)) {
                String[] limitStatuses = limitStatus.split(",");
                for (String string : limitStatuses) {
                    if (TASK_STATUS.equals(string)) {
                        isNeedStatus = false;
                    }
                }
            }
            if (StringUtils.isNotEmpty(limitNodename)) {
                String[] limitNodenames = limitNodename.split(",");
                for (String string : limitNodenames) {
                    if (CUR_STEPNAMES.equals(string)) {
                        isNeedStepNames = false;
                    }
                }
            }
            if (isNeedStatus && isNeedStepNames) {
                returnList.add(map);
            }
        }
        this.setListToJsonString(returnList.size(), returnList, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 限制状态选择器
     * 
     * @author Roger Li
     * @created 2019年12月23日 下午4:22:19
     * @param request
     * @return
     */
    @RequestMapping("/limitStatusSelector")
    public ModelAndView limitStatusSelector(HttpServletRequest request) {
        String certificateIds = request.getParameter("certificateIds");
        if (StringUtils.isNotEmpty(certificateIds) && !certificateIds.equals("undefined")) {
            request.setAttribute("certificateIds", certificateIds);
        }
        return new ModelAndView("bdcqlc/certificateTemplate/limitStatusSelector");
    }

    /**
     * 描述 获取所有状态
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitStatusDatagrid")
    public void limitStatusDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.TYPE_CODE_EQ", "LimitStatus");
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 获取所有已经被选中的限制状态
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitStatusSelected")
    public void limitStatusSelected(HttpServletRequest request, HttpServletResponse response) {
        String certificateIds = request.getParameter("certificateIds");
        List<Map<String, Object>> list = dictionaryService.findByDicIds(certificateIds);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 限制环节选择器
     * 
     * @author Roger Li
     * @created 2019年12月23日 下午4:24:10
     * @param request
     * @return
     */
    @RequestMapping("/limitNodeNameSelector")
    public ModelAndView limitNodeNameSelector(HttpServletRequest request) {
        String certificateIds = request.getParameter("certificateIds");
        if (StringUtils.isNotEmpty(certificateIds) && !certificateIds.equals("undefined")) {
            request.setAttribute("certificateIds", certificateIds);
        }
        return new ModelAndView("bdcqlc/certificateTemplate/limitNodeNameSelector");
    }

    /**
     * 描述 获取所有限制环节信息
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitNodeNameDatagrid")
    public void limitNodeNameDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_T.TYPE_CODE_EQ", "LimitNodeName");
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述 获取所有已经被选中的限制环节
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "limitNodeNameSelected")
    public void limitNodeNameSelected(HttpServletRequest request, HttpServletResponse response) {
        String certificateIds = request.getParameter("certificateIds");
        List<Map<String, Object>> list = dictionaryService.findByDicIds(certificateIds);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
}
