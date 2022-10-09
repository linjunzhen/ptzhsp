/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import net.evecom.platform.bsfw.model.FlowData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhuozhengsoft.pageoffice.FileSaver;

import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.util.ExcelRedrawUtil;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.BusTypeService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 打印附件表Controller
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/printAttachController")
public class PrintAttachController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(PrintAttachController.class);
    /**
     * 引入Service
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
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    /**
     * 引入service
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
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
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * flowMappedService
     */
    @Resource
    private FlowMappedService flowMappedService;
    /**
     * 引入Service
     */
    @Resource
    private DepartServiceItemService departServiceItemService;

    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;

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
        printAttachService.remove("T_WSBS_PRINTATTACH", "FILE_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 打印附件表记录", SysLogService.OPERATE_TYPE_DEL);
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
            Map<String, Object> printAttach = printAttachService.getByJdbc("T_WSBS_PRINTATTACH", new String[] { "FILE_ID" }, new Object[] { entityId });
            request.setAttribute("printAttach", printAttach);
        }
        return new ModelAndView("wsbs/printAttach/info");
    }

    /**
     * 修改或者修改操作
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("FILE_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        SysUser sysUser = AppUtil.getLoginUser();
        String userName = "";
        String account = "";
        if (sysUser != null) {
            userName = sysUser.getUsername();
            account = sysUser.getFullname();
        }
        variables.put("USER_NAME", account);
        variables.put("ACCOUNT", userName);
        String FILE_PATH = variables.get("FILE_PATH") == null ? "" : variables.get("FILE_PATH").toString();
        if (StringUtils.isNotBlank(FILE_PATH)) {

            // 保存附件信息
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("uploaderId", userName);// 上传人ID
            param.put("uploaderName", account); // 上传人姓名
            param.put("typeId", "0");// 上传类型ID，默认0
            param.put("busTableName", "T_WSBS_PRINTATTACH");// 业务表名
            param.put("busRecordId", entityId);// 业务表ID

            // 文件服务器地址
            // String url = attachmentFilePath + "upload/file";// 上传路径
            // String AttachFilePath = properties.getProperty("AttachFilePath");
            // String resultMsg = HttpRequestUtil.sendFilePost(url,
            // AttachFilePath + FILE_PATH, responesCode, app_id,
            // password, param);
            // Map<String, Object> result = JSON.parseObject(resultMsg,
            // Map.class);
            // Map<String, Object> data = (Map<String, Object>)
            // result.get("data");
            // String filePath = data.get("filePath").toString();
            // variables.put("FILE_PATH", filePath);
            String recordId = printAttachService.saveOrUpdate(variables, "T_WSBS_PRINTATTACH", entityId);
            if (StringUtils.isNotEmpty(entityId)) {
                log.error("修改了ID为[" + entityId + "]的 打印附件表记录");
                sysLogService.saveLog("修改了ID为[" + entityId + "]的 打印附件表记录", SysLogService.OPERATE_TYPE_EDIT);
            } else {
                log.error("新增了ID为[" + recordId + "]的 打印附件表记录");
                sysLogService.saveLog("新增了ID为[" + recordId + "]的 打印附件表记录", SysLogService.OPERATE_TYPE_ADD);
            }
            j.setMsg("保存成功");
        } else {
            j.setSuccess(false);
            j.setMsg("保存失败，附件路径为空！");
        }
        return j;
    }

    /**
     * 
     * 描述 保存打印文件
     * 
     * @author Faker Li
     * @created 2016年1月12日 上午9:29:13
     * @param request
     * @param response
     */
    @RequestMapping("/savefile")
    public void savefile(HttpServletRequest request, HttpServletResponse response) {
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        String uploadPath = "PrintAttach/";
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        String uuId = UUIDGenerator.getUUID();
        String fileName = uuId + ".doc";
        FileSaver fs = new FileSaver(request, response);
        byte[] fileBytes = fs.getFileBytes();
        if (fileBytes != null && fileBytes.length > 0) {
            String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
            String url = uploadFileUrl + "upload/file";// 上传路径
            String app_id = "0001";// 分配的用户名
            String password = "bingo666";// 分配的密码
            String responesCode = "UTF-8";// 编码
            HashMap<String, Object> params = new HashMap<>();
            params.put("uploaderId", "printTemplate");// 上传人ID
            params.put("uploaderName", "printTemplate"); // 上传人姓名
            params.put("typeId", "0");// 上传类型ID，默认0
            params.put("name", fileName);
            params.put("busTableName", "");
            params.put("attachKey", "");
            params.put("busRecordId", "");
            try {
                String result = HttpRequestUtil.sendByteFilePost(url, fileBytes, responesCode, app_id, password, params);
                Map<String, Object> jsonMap = JSON.parseObject(result, Map.class);
                if ((boolean) jsonMap.get("success")) {
                    Map<String, Object> data = (Map<String, Object>) jsonMap.get("data");
                    String filePath1 = (String) data.get("filePath");
                    request.getSession().setAttribute("localFilePath", filePath1);
                    fs.saveToFile(uploadFullPath + fileName);
                    fs.setCustomSaveResult(filePath1);
                }
            } catch (Exception e) {
                log.error("上传打印日志文件出错" + e.getMessage());
            }
            fs.close();
        }

    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "printTemplate")
    public ModelAndView printTemplate(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String exeId = request.getParameter("EFLOW_EXEID");
        String typeId = request.getParameter("typeId");
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");

        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        getTemplateDataMap(templateData, exeId);
        /****************************** 不动产通用配置代码开始 ************************************/
        Map<String, Object> execution = null;
        if (StringUtils.isNotBlank(exeId) && !exeId.equals("undefined")) {
            execution = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
            if (execution == null) {
                execution = this.executionService.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" }, new Object[] { exeId });
            }
            if (execution != null) {
                String busTableName = StringUtil.getValue(execution, "BUS_TABLENAME");
                // 不动产业务的设值方法
                if (StringUtils.isNotBlank(busTableName) && busTableName.startsWith("T_BDC")) {
                    getBdcCustomData(request, execution, templateData);
                } else if (StringUtils.isNotBlank(busTableName) && Objects.equals(busTableName, "T_BSFW_LHDJ")) {
                    getLhdjCustomData(request, execution, templateData);
                }
            }
        }
        /****************************** 不动产通用配置代码结束 ************************************/
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

        request.setAttribute("isSignButton", request.getParameter("isSignButton"));
        request.setAttribute("TemplateData", templateData);
        request.setAttribute("exeId", exeId);
        if (typeId.equals("4")) { // 不动产权证打印
            return new ModelAndView("bdcqlc/certificateTemplate/printCertificateTemplate");
        } else if (typeId.equals("3")) {
            return new ModelAndView("wsbs/readtemplate/printReadTemplate");
        } else if (typeId.equals("2")) {
            return new ModelAndView("wsbs/documenttemplate/printDocumentTemplate");
        } else {
            return new ModelAndView("wsbs/tickets/showTickets");
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/7 15:57:00
     * @param
     * @return
     */
    private void getLhdjCustomData(HttpServletRequest request, Map<String, Object> execution, Map<String, Object> templateData) {
        String exeId = StringUtil.getValue(execution, "EXE_ID");
        FlowData flowData = new FlowData(exeId, FlowData.BUS_MAP);
        Map<String, Object> busMap = flowData.getBusMap();
        Map<String, Object> exeMap = flowData.getExeMap();
        String date = DateTimeUtil.getStrOfDate(DateTimeUtil.getDateOfStr(StringUtil.getValue(exeMap, "CREATE_TIME"), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日");
        Date nextDay1 = DateTimeUtil.getNextDay(DateTimeUtil.getDateOfStr(StringUtil.getValue(exeMap, "CREATE_TIME"), "yyyy-MM-dd HH:mm:ss"), 30);
        String nextDate1 = DateTimeUtil.getStrOfDate(nextDay1, "yyyy年MM月dd日");
        Date nextDay2 = DateTimeUtil.getNextDay(DateTimeUtil.getDateOfStr(StringUtil.getValue(exeMap, "CREATE_TIME"), "yyyy-MM-dd HH:mm:ss"), 60);
        String nextDate2 = DateTimeUtil.getStrOfDate(nextDay2, "yyyy年MM月dd日");
        templateData.put("nfxm", StringUtil.getValue(busMap, "MALE_NAME"));
        templateData.put("nfzjh", StringUtil.getValue(busMap, "MALE_CARD"));
        templateData.put("nfsjh", StringUtil.getValue(busMap, "MALE_PHONE"));
        templateData.put("nfxm1", StringUtil.getValue(busMap, "FEMALE_NAME"));
        templateData.put("nfzjh1", StringUtil.getValue(busMap, "FEMALE_CARD"));
        templateData.put("nfsjh1", StringUtil.getValue(busMap, "FEMALE_PHONE"));
        templateData.put("rq", date);
        templateData.put("rq1", nextDate1);
        templateData.put("rq2", nextDate2);
        templateData.put("lsh", StringUtil.getValue(busMap, "LSH"));
    }

    /**
     * 
     * 描述 获取不动产业务的相关数据
     * 
     * @author Roger Li
     * @created 2019年12月13日 上午10:57:59
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    private void getBdcCustomData(HttpServletRequest request, Map<String, Object> execution, Map<String, Object> returnMap) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String exeId = request.getParameter("EFLOW_EXEID");
        String typeId = request.getParameter("typeId");
        String templateName = request.getParameter("TemplateName");
        if (execution == null || StringUtils.isBlank(exeId) || StringUtils.isBlank(typeId)) {
            return;
        }
        String defId = StringUtil.getValue(execution, "DEF_ID");
        String itemCode = StringUtil.getValue(execution, "ITEM_CODE");
        // 获取流程明细数据
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        // 开始定义流程信息对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defId, exeId, "false", request);

        // 模板表相关信息
        Map<String, String> tableInfo = new HashMap<String, String>();
        // 4:打证模板 3:阅办模板 2:documentTemplate 1:单据模板
        if (typeId.equals("4")) {
            tableInfo.put("tableName", "T_WSBS_CERTIFICATE");
            tableInfo.put("idCol", "CERTIFICATE_ID");
            tableInfo.put("nameCol", "CERTIFICATE_NAME");
            tableInfo.put("docCol", "CERTIFICATE_DOC");
            tableInfo.put("aliasCol", "ALIAS");
        } else if (typeId.equals("3")) {
            tableInfo.put("tableName", "T_WSBS_READTEMPLATE");
            tableInfo.put("idCol", "READ_ID");
            tableInfo.put("nameCol", "READ_NAME");
            tableInfo.put("docCol", "READ_DOC");
            tableInfo.put("aliasCol", "ALIAS");
        }
        // 获取模板数据
        // 如果传进来的templateName是别名
        Map<String, Object> templateInfo = printAttachService.getByJdbc(tableInfo.get("tableName"), new String[] { tableInfo.get("aliasCol") }, new Object[] { templateName });
        if (templateInfo == null) {
            // 如果传进来的TemplateName是模板名称则根据名称取
            templateInfo = printAttachService.getByJdbc(tableInfo.get("tableName"), new String[] { tableInfo.get("nameCol") }, new Object[] { templateName });
        } else {
            returnMap.put("TeplateName", StringUtil.getValue(returnMap, "TemplateName"));
        }
        if (StringUtils.isBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
            returnMap.put("TemplatePath", StringUtil.getValue(templateInfo, tableInfo.get("docCol")));
        }
        returnMap.put("tableType", StringUtil.getValue(variables, "tableType"));
        String alias = StringUtil.getValue(templateInfo, tableInfo.get("aliasCol"));
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { alias, itemCode });
        if (dictionary == null) {
            String tplName = StringUtil.getValue(templateInfo, tableInfo.get("nameCol"));
            dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { tplName, itemCode });
        }
        // 如果有配置自定义方法则走自定义方法否则走通用方法
        if (dictionary != null) {
            String handleMethod = StringUtil.getValue(dictionary, "DIC_NAME");
            String[] temp = handleMethod.split("\\.");
            if (temp.length == 2) {
                String targetClassName = temp[0];
                String targetMethod = temp[1];
                // 构造参数
                Map<String, Map<String, Object>> args = new HashMap<String, Map<String, Object>>();
                args.put("execution", execution);
                args.put("serviceItem", serviceItem);
                args.put("flowAllObj", flowAllObj);
                args.put("returnMap", returnMap);
                // 把request中的参数也放进来
                args.put("requestMap", variables);
                // 从容器中获取该类实例并传参执行指定的方法
                try {
                    Method customMethod = AppUtil.getBean(targetClassName).getClass().getMethod(targetMethod, Map.class);
                    customMethod.invoke(AppUtil.getBean(targetClassName), args);
                } catch (Exception e) {
                    log.error("", e);
                }
            } else {
                // 自定义方法配置有问题则调用通用方法
                this.defaultRedrawMethod(returnMap, execution, flowAllObj, serviceItem, exeId);
            }
        } else {
            // 如果没有配置个性化方法 则调用通用方法
            this.defaultRedrawMethod(returnMap, execution, flowAllObj, serviceItem, exeId);
        }
    }

    /**
     * 
     * 描述 设值业务表数据
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午11:13:52
     * @param returnMap
     * @param flowAllObj
     * @return
     */
    @SuppressWarnings("unchecked")
    private void setDefaultRedrawData(Map<String, Object> returnMap, Map<String, Object> flowAllObj) {
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
            // 将业务表所有字段名转换为小写作为key,保存在returnMap中
            for (Map.Entry<String, Object> entry : busInfo.entrySet()) {
                String valString = entry.getValue() == null ? "" : entry.getValue().toString();
                returnMap.put(entry.getKey().toLowerCase(), valString);
            }
        }
    }

    /**
     * 
     * 描述 默认获取回填数据的方法
     * 
     * @author Roger Li
     * @created 2019年12月19日 上午11:48:34
     * @param
     * @return
     */
    private void defaultRedrawMethod(Map<String, Object> returnMap, Map<String, Object> execution, Map<String, Object> flowAllObj, Map<String, Object> serviceItem, String exeId) {
        String busRecordId = StringUtil.getValue(execution, "BUS_RECORDID");
        String busTableName = StringUtil.getValue(execution, "BUS_TABLENAME");
        // 初始化数据
        this.setDefualtValue(returnMap, exeId);
        // 设值业务表的数据 将业务表列名转换成小写作为returnMap的key，把数据保存在returnMap
        // this.setDefaultRedrawData(returnMap, flowAllObj);
        // 设值流程主表数据
        this.setSubmitUsrsInfo(returnMap, execution);
        // 设值权力类型数据
        this.setpowerType(returnMap, serviceItem);
        // 设值不动产基础数据
        this.setBdcData(flowAllObj, returnMap);
        printAttachService.getPowerPeopleList(busTableName, busRecordId, returnMap);
    }

    /**
     * 跳转到信息页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "printQueryApply")
    public ModelAndView printQueryApply(HttpServletRequest request) {
        Map<String, Object> templateData = BeanUtil.getMapFromRequest(request);
        templateData = printAttachService.setEstateQueryData(templateData);
        request.setAttribute("TemplateData", templateData);
        return new ModelAndView("wsbs/readtemplate/printItemDetailTemplate");
    }

    /**
     * 
     * 描述：打印一次性回执单
     * 
     * @author Water Guo
     * @created 2017-3-17 下午3:27:15
     * @param request
     */
    @RequestMapping(params = "printBack")
    public ModelAndView printBack(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String itemCode = request.getParameter("itemCode");
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String xmsqry = request.getParameter("OPER_REMARK_SQR");// 建设单位
        String cllbJson = request.getParameter("cllbJson");// 是否符合要求材料信息列表
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        // 建设单位
        if (null != xmsqry) {
            templateData.put("xmsqry", xmsqry);
        } else {
            templateData.put("xmsqry", " ");
        }
        getBackDataMap(templateData, itemCode);
        // 是否符合要求材料列表
        if (StringUtils.isNotEmpty(cllbJson)) {
            List<Map<String, Object>> cllbList = (List<Map<String, Object>>) JSON.parse(cllbJson);
            List<Map<String, Object>> ysqcllbList = new ArrayList<Map<String, Object>>();// 已收取材料列表
            List<Map<String, Object>> sqclbclbList = new ArrayList<Map<String, Object>>();// 申请材料补充列表
            if (cllbList.size() > 0) {
                for (Map<String, Object> cllb : cllbList) {
                    Map<String, Object> mater = applyMaterService.getByJdbc("T_WSBS_APPLYMATER", new String[] { "MATER_CODE" }, new Object[] { cllb.get("MATER_CODE") });
                    cllb.put("MATER_NAME", mater.get("MATER_NAME"));
                    cllb.put("MATER_DESC", mater.get("MATER_DESC"));
                    if ("1".equals(StringUtil.getString(cllb.get("SFFH")))) {// 符合
                        ysqcllbList.add(cllb);
                    } else if ("-1".equals(StringUtil.getString(cllb.get("SFFH")))) {// 不符合
                        sqclbclbList.add(cllb);
                    }
                }
            }
            if (ysqcllbList.size() > 0) {
                StringBuffer fhStr = new StringBuffer();
                for (int i = 0; i < ysqcllbList.size(); i++) {
                    int j = i + 1;
                    fhStr.append(j).append(". ").append(ysqcllbList.get(i).get("MATER_NAME").toString().replaceAll("\r|\n", "  "));
                    // 判断材料说明是否为空
                    if (StringUtils.isEmpty(StringUtil.getString(ysqcllbList.get(i).get("MATER_DESC")))) {
                        fhStr.append("\\n");
                    } else {
                        fhStr.append("(").append(StringUtil.getString(ysqcllbList.get(i).get("MATER_DESC")).replaceAll("\r|\n", "  ")).append(")").append("\\n");
                    }
                }
                templateData.put("ysqcllb", fhStr);
            } else {
                templateData.put("ysqcllb", " ");
            }
            if (sqclbclbList.size() > 0) {
                StringBuffer bfhStr = new StringBuffer();
                for (int i = 0; i < sqclbclbList.size(); i++) {
                    int j = i + 1;
                    bfhStr.append(j).append(". ").append(sqclbclbList.get(i).get("MATER_NAME").toString().replaceAll("\r|\n", "  "));
                    // 判断材料说明是否为空
                    if (StringUtils.isEmpty(StringUtil.getString(sqclbclbList.get(i).get("MATER_DESC")))) {
                        bfhStr.append("\\n");
                    } else {
                        bfhStr.append("(").append(StringUtil.getString(sqclbclbList.get(i).get("MATER_DESC")).replaceAll("\r|\n", "  ")).append(")").append("\\n");
                    }
                }
                templateData.put("sqclbclb", bfhStr);
            } else {
                templateData.put("sqclbclb", " ");
            }
        }
        request.setAttribute("TemplateData", templateData);
        return new ModelAndView("wsbs/readtemplate/printBack");
    }

    /**
     * 跳转到事项信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "printItemTemplate")
    public ModelAndView printItemTemplate(HttpServletRequest request) {
        // Map<String, Object> templateData = new HashMap<String, Object>();
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String entityId = request.getParameter("ITEM_ID");
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String serviceUrl = properties.getProperty("serviceUrl");

        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" }, new Object[] { entityId });
            // log.info(serviceItem.get("BLLC"));

            String itemCode = serviceItem.get("ITEM_CODE").toString();
            String data = serviceUrl + "/serviceItemController/bsznDetail.do?itemCode=" + itemCode;
            // System.out.println("字节数: " + data.getBytes().length);
            String path = BeanUtil.getClassPath(AppUtil.class);
            path = path.substring(0, path.indexOf("WEB-INF"));
            String returnPath = "attachFiles\\qrcodeimgs\\qrCode_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
            path = path + returnPath;
            // String qrCodeName ="E:\\imgs\\qrCode_"
            // + new SimpleDateFormat("yyyyMMddHHmmss")
            // .format(new Date()) + ".jpg";
            GucasQRCodeEncoder.encode(data, path);
            path = path.replace("/", "\\");
            serviceItem.put("QRCODE", path);

            Map<String, Object> typeItem = busTypeService.getIdAndNamesByItemId(entityId);
            serviceItem.put("BUS_TYPEIDS", typeItem.get("TYPE_IDS"));
            serviceItem.put("BUS_TYPENAMES", typeItem.get("TYPE_NAMES"));

            bljgAndsqfs(serviceItem);
            // 获取所属目录
            String standardCatalogName = StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME");
            if (StringUtils.isNotEmpty(standardCatalogName)) {
                serviceItem.put("CATALOG_NAME", standardCatalogName);
            } else {
                String catalogCode = (String) serviceItem.get("CATALOG_CODE");
                if (StringUtils.isNotEmpty(catalogCode)) {
                    Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
                    serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
                }
            }
            // 办理流程
            String defId = serviceItem.get("BDLCDYID") == null ? "" : serviceItem.get("BDLCDYID").toString();
            List<Map<String, Object>> mapped = flowMappedService.getByDefidAndNodeName(defId);
            if (mapped.size() == 2) {
                mapped.get(1).put("YS_NAME", "审查与决定");
            } else if (mapped.size() == 3) {
                mapped.get(1).put("YS_NAME", "审查");
                mapped.get(2).put("YS_NAME", "决定");
            } else if (mapped.size() == 4) {
                mapped.get(1).put("YS_NAME", "审查");
                mapped.get(2).put("YS_NAME", "决定");
                mapped.get(3).put("YS_NAME", "制证与送达");
            } else if (mapped.size() == 5) {
                mapped.get(1).put("YS_NAME", "审查");
                mapped.get(2).put("YS_NAME", "决定");
                mapped.get(3).put("YS_NAME", "制证");
                mapped.get(4).put("YS_NAME", "送达");
            }
            StringBuffer bllc = new StringBuffer();
            for (Map<String, Object> map : mapped) {
                if (StringUtils.isNotEmpty(bllc)) {
                    bllc.append(" → ");
                }
                bllc.append(map.get("YS_NAME"));
            }
            serviceItem.put("BLLC", bllc);
            sxxzAndBjlx(serviceItem);// 事项性质//办件类型
            qckcs(serviceItem);// 去窗口次数
            sxsx(serviceItem);// 事项属性
            // 是否一窗通办
            String isYctb = serviceItem.get("IS_YCTB") == null ? "否" : serviceItem.get("IS_YCTB").toString();
            if ("0".equals(isYctb)) {
                isYctb = "否";
            } else if ("1".equals(isYctb)) {
                isYctb = "是";
            }
            serviceItem.put("ISYCTB", isYctb);
            // 收费方式
            String sfsf = (String) serviceItem.get("SFSF");
            if ("0".equals(sfsf)) {
                sfsf = "否";
                serviceItem.put("SFFS", "");
                serviceItem.put("YIJU", "");
            } else if ("1".equals(sfsf)) {
                sfsf = "是";

                String sffs = (String) serviceItem.get("SFFS");
                String yiju = (String) serviceItem.get("SFBZJYJ");
                yiju = replaceBlank(yiju);
                sffs = dictionaryService.findByDicCodeAndTypeCode(sffs, "ChargeType");

                sfsf += "    收费方式：" + sffs + "    收费标准及依据：" + yiju;
                serviceItem.put("SFFS", sffs);
                serviceItem.put("YIJU", yiju);
            }
            serviceItem.put("SFSF", sfsf);
            String tshj = printAttachService.getTshj(entityId);// 特殊环节
            serviceItem.put("TSHJ", tshj);
            setApplyMater(entityId, serviceItem);
        }
        serviceItem.put("TemplatePath", templatePath);
        serviceItem.put("TemplateName", templateName);
        // 以省网办编码为主
        setServiceItemCode(serviceItem);
        request.setAttribute("TemplateData", serviceItem);
        // getTemplateDataMap(templateData, exeId);
        // request.setAttribute("TemplateData", templateData);
        // 承诺时限工作日
        setCSSJ(serviceItem);
        // 法定时限
        setServiceItemFDSX(serviceItem);
        String pdf = request.getParameter("PDF");
        if (StringUtils.isNotEmpty(pdf)) {
            return new ModelAndView("wsbs/readtemplate/printPDFItemDetailTemplate");
        }
        return new ModelAndView("wsbs/readtemplate/printItemDetailTemplate");
    }

    private void bljgAndsqfs(Map<String, Object> serviceItem) {
        String papersub = serviceItem.get("papersub") == null ? "" : serviceItem.get("papersub").toString();
        /*
         * if (StringUtils.isNotEmpty(papersub)) { papersub =
         * papersub.replace("1", "窗口收取"); papersub = papersub.replace("2",
         * "邮件收取 "); }
         */
        papersub = dictionaryService.findByDicCodeAndTypeCode(papersub, "paperMaterSub");
        serviceItem.put("papersubString", papersub);
        String finishGettype = serviceItem.get("FINISH_GETTYPE") == null ? "" : serviceItem.get("FINISH_GETTYPE").toString();
        /*
         * if (StringUtils.isNotEmpty(finishGettype)) { finishGettype =
         * finishGettype.replace("01", "申请对象窗口领取"); finishGettype =
         * finishGettype.replace("02", "邮递办理结果 "); finishGettype =
         * finishGettype.replace("03", "电子证照窗口拷取 "); finishGettype =
         * finishGettype.replace("04", "电子证照送达政务通 "); }
         */
        finishGettype = dictionaryService.findByDicCodeAndTypeCode(finishGettype, "FinishGetType");
        serviceItem.put("finishGettype", finishGettype);
        String finishType = serviceItem.get("FINISH_TYPE") == null ? "" : serviceItem.get("FINISH_TYPE").toString();
        /*
         * if (StringUtils.isNotEmpty(finishType)) { finishType =
         * finishType.replace("01", "证照"); finishType = finishType.replace("02",
         * "批文 "); finishType = finishType.replace("03", "证明 "); finishType =
         * finishType.replace("04", "其它"); }
         */
        finishType = dictionaryService.findByDicCodeAndTypeCode(finishType, "FinishType");
        serviceItem.put("finishType", finishType);
        String ckcs_1 = serviceItem.get("ckcs_1") == null ? "" : serviceItem.get("ckcs_1").toString();
        String ckcs_2 = serviceItem.get("ckcs_2") == null ? "" : serviceItem.get("ckcs_2").toString();
        String ckcs_3 = serviceItem.get("ckcs_3") == null ? "" : serviceItem.get("ckcs_3").toString();
        String ckcs_4 = serviceItem.get("ckcs_4") == null ? "" : serviceItem.get("ckcs_4").toString();
        StringBuffer ckcString = new StringBuffer("");
        if (StringUtils.isNotEmpty(ckcs_1)) {
            ckcString.append("行政服务中心窗口受理");
            ckcString.append(ckcs_1);
            ckcString.append("次；");
        }
        if (StringUtils.isNotEmpty(ckcs_2)) {
            ckcString.append("网上申请和预审，窗口纸质材料收件受理");
            ckcString.append(ckcs_2);
            ckcString.append("次；");
        }
        if (StringUtils.isNotEmpty(ckcs_3)) {
            ckcString.append("网上申请、预审和受理，窗口纸质核验办结");
            ckcString.append(ckcs_3);
            ckcString.append("次；");
        }
        if (StringUtils.isNotEmpty(ckcs_4)) {
            ckcString.append("提供全流程网上办理，申请人在办结后到窗口领取结果");
            ckcString.append(ckcs_4);
            ckcString.append("次；");
        }
        serviceItem.put("ckcString", ckcString);
    }

    /**
     * 跳转到事项信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "printItemDetailTemplate")
    public ModelAndView printItemDetailTemplate(HttpServletRequest request) {
        // Map<String, Object> templateData = new HashMap<String, Object>();
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String entityId = request.getParameter("ITEM_ID");
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" }, new Object[] { entityId });
            // log.info(serviceItem.get("BLLC"));
            Map<String, Object> typeItem = busTypeService.getIdAndNamesByItemId(entityId);
            serviceItem.put("BUS_TYPEIDS", typeItem.get("TYPE_IDS"));
            serviceItem.put("BUS_TYPENAMES", typeItem.get("TYPE_NAMES"));
            // 面向对象
            setBusType(entityId, serviceItem);
            String standardCatalogName = StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME");
            if (StringUtils.isNotEmpty(standardCatalogName)) {
                serviceItem.put("CATALOG_NAME", standardCatalogName);
            } else {
                String catalogCode = (String) serviceItem.get("CATALOG_CODE");
                if (StringUtils.isNotEmpty(catalogCode)) {
                    Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
                    serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
                }
            }
            setbllc(serviceItem);// 办理流程
            sxxzAndBjlx(serviceItem);// 事项性质//办件类型
            qckcs(serviceItem);// 去窗口次数
            sxsx(serviceItem);// 事项属性
            // 收费方式
            String sfsf = (String) serviceItem.get("SFSF");
            if ("0".equals(sfsf)) {
                sfsf = "否";
                serviceItem.put("SFFS", "");
                serviceItem.put("YIJU", "");
            } else if ("1".equals(sfsf)) {
                sfsf = "是";
                String sffs = (String) serviceItem.get("SFFS");
                String yiju = (String) serviceItem.get("SFBZJYJ");
                yiju = replaceBlank(yiju);
                sffs = dictionaryService.findByDicCodeAndTypeCode(sffs, "ChargeType");
                sfsf += "    收费方式：" + sffs + "    收费标准及依据：" + yiju;
                serviceItem.put("SFFS", sffs);
                serviceItem.put("YIJU", yiju);
            }
            serviceItem.put("SFSF", sfsf);
            // 承接信息
            String isUndertake = (String) serviceItem.get("IS_UNDERTAKE");
            if ("1".equals(isUndertake)) {
                isUndertake = "是";
                serviceItem.put("WCJLHMC", "");
                serviceItem.put("WCJLHWH", "");
            } else if ("0".equals(isUndertake)) {
                isUndertake = "否";
                String wcjlhmc = (String) serviceItem.get("WCJLHMC");
                String wcjlhwh = (String) serviceItem.get("WCJLHWH");
                isUndertake += "    来函名称：" + wcjlhmc + "    来函文号：" + wcjlhwh;
                serviceItem.put("WCJLHMC", wcjlhmc);
                serviceItem.put("WCJLHWH", wcjlhwh);
            }
            serviceItem.put("CJXX", isUndertake);
            String tshj = printAttachService.getTshj(entityId);// 特殊环节
            serviceItem.put("TSHJ", tshj);
            // 材料
            setApplyMater(entityId, serviceItem);

            setLcrypz(entityId, request, serviceItem);
        }
        String bljg = serviceItem.get("FINISH_TYPE") == null ? "" : serviceItem.get("FINISH_TYPE").toString();
        bljg = dictionaryService.findByDicCodeAndTypeCode(bljg, "FinishType");
        serviceItem.put("BLJG", bljg);
        String hqfs = serviceItem.get("FINISH_GETTYPE") == null ? "" : serviceItem.get("FINISH_GETTYPE").toString();
        hqfs = dictionaryService.findByDicCodeAndTypeCode(hqfs, "FinishGetType");
        serviceItem.put("HQFS", hqfs);
        serviceItem.put("TemplatePath", templatePath);
        serviceItem.put("TemplateName", templateName);
        // 以省网办编码为主
        setServiceItemCode(serviceItem);
        request.setAttribute("TemplateData", serviceItem);
        // getTemplateDataMap(templateData, exeId);
        // request.setAttribute("TemplateData", templateData);
        // 承诺时限工作日
        setCSSJ(serviceItem);
        // 法定时限
        setServiceItemFDSX(serviceItem);
        String pdf = request.getParameter("PDF");
        if (StringUtils.isNotEmpty(pdf)) {
            return new ModelAndView("wsbs/readtemplate/printPDFItemDetailTemplate");
        }
        return new ModelAndView("wsbs/readtemplate/printItemDetailTemplate");
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
     * 
     * 描述 流程人员信息
     * 
     * @author Danto Huang
     * @created 2018年11月30日 上午11:03:31
     * @param entityId
     * @param request
     * @param serviceItem
     */
    private void setLcrypz(String entityId, HttpServletRequest request, Map<String, Object> serviceItem) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.NODE_KEY", "asc");
        String itemId = entityId;
        if (StringUtils.isEmpty(itemId) || itemId.equals("undefined")) {
            itemId = request.getParameter("Q_T.ITEM_ID_EQ");
        }
        if (StringUtils.isNotEmpty(itemId)) {
            filter.addFilter("Q_T.ITEM_ID_=", itemId);
            filter.addSorted("T.RECORD_ID", "asc");
            List<Map<String, Object>> list = departServiceItemService.getDefNode(filter);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                String nodeName = map.get("NODE_NAME") == null ? "" : map.get("NODE_NAME").toString();
                if ("开始".equals(nodeName) || "结束".equals(nodeName) || "待受理".equals(nodeName) || "网上预审".equals(nodeName) || "受理自动跳转".equals(nodeName)) {

                } else {
                    resultList.add(map);
                }
            }
            List<Map<String, Object>> nodeInfos = resultList;
            if (nodeInfos.size() == 2) {
                nodeInfos.get(1).put("NODE_NAME", "审查与决定");
            } else if (nodeInfos.size() == 3) {
                nodeInfos.get(1).put("NODE_NAME", "审查");
                nodeInfos.get(2).put("NODE_NAME", "决定");
            } else if (nodeInfos.size() == 4) {
                nodeInfos.get(1).put("NODE_NAME", "审查");
                nodeInfos.get(2).put("NODE_NAME", "决定");
                nodeInfos.get(3).put("NODE_NAME", "制证与送达");
            } else if (nodeInfos.size() == 5) {
                nodeInfos.get(1).put("NODE_NAME", "审查");
                nodeInfos.get(2).put("NODE_NAME", "决定");
                nodeInfos.get(3).put("NODE_NAME", "制证");
                nodeInfos.get(4).put("NODE_NAME", "送达");
            }
            serviceItem.put("nodeInfos", nodeInfos);
            StringBuffer nb = new StringBuffer();
            nb.append("");
            nb.append("预审人员");
            nb.append(":");
            nb.append((String) serviceItem.get("MESSAGE_REC"));
            nb.append("\n");
            if (nodeInfos.size() > 0) {
                for (int i = 0; i < nodeInfos.size(); i++) {
                    nb.append(nodeInfos.get(i).get("NODE_NAME"));
                    nb.append(":");
                    nb.append(nodeInfos.get(i).get("USER_NAME"));
                    nb.append("\n");
                }
            }
            serviceItem.put("LCRYPZ", nb);
        }
    }

    /**
     * 
     * 描述 办理流程信息
     * 
     * @author Danto Huang
     * @created 2018年11月30日 上午10:55:37
     * @param serviceItem
     */
    private void setbllc(Map<String, Object> serviceItem) {
        // 办理流程
        String defId = serviceItem.get("BDLCDYID") == null ? "" : serviceItem.get("BDLCDYID").toString();
        List<Map<String, Object>> mapped = flowMappedService.getByDefidAndNodeName(defId);
        if (mapped.size() == 2) {
            mapped.get(1).put("YS_NAME", "审查与决定");
        } else if (mapped.size() == 3) {
            mapped.get(1).put("YS_NAME", "审查");
            mapped.get(2).put("YS_NAME", "决定");
        } else if (mapped.size() == 4) {
            mapped.get(1).put("YS_NAME", "审查");
            mapped.get(2).put("YS_NAME", "决定");
            mapped.get(3).put("YS_NAME", "制证与送达");
        } else if (mapped.size() == 5) {
            mapped.get(1).put("YS_NAME", "审查");
            mapped.get(2).put("YS_NAME", "决定");
            mapped.get(3).put("YS_NAME", "制证");
            mapped.get(4).put("YS_NAME", "送达");
        }
        StringBuffer bllc = new StringBuffer();
        for (Map<String, Object> map : mapped) {
            if (StringUtils.isNotEmpty(bllc)) {
                bllc.append(" → ");
            }
            bllc.append(map.get("YS_NAME"));
        }
        serviceItem.put("BLLC", bllc);
    }

    private void setBusType(String entityId, Map<String, Object> serviceItem) {
        List<Map<String, Object>> busTypenames = busTypeService.getParentNamesAndNamesByItemId(entityId);
        StringBuffer sb = new StringBuffer();
        sb.append("");
        if (busTypenames.size() > 0) {
            for (int i = 0; i < busTypenames.size(); i++) {
                sb.append(busTypenames.get(i).get("PNAME"));
                sb.append(":");
                sb.append(busTypenames.get(i).get("TYPE_NAME"));
                sb.append("\n");
            }
        }
        serviceItem.put("BUSTYPENAMES", sb);
    }

    /**
     * 打印的材料内容
     * 
     * @param entityId
     * @param serviceItem
     */
    private void setApplyMater(String entityId, Map<String, Object> serviceItem) {
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(entityId, null);
        StringBuffer sb = new StringBuffer();
        if (applyMaters.size() > 0) {
            String busclassName = "";
            for (int i = 0; i < applyMaters.size(); i++) {
                String name = applyMaters.get(i).get("BUSCLASS_NAME") == null ? "" : applyMaters.get(i).get("BUSCLASS_NAME").toString();
                if (!name.equals(busclassName) && !name.equals("无")) {
                    sb.append("●");
                    sb.append(name);
                    // sb.append("：");
                    sb.append("\n");
                    busclassName = name;
                }
                sb.append(i + 1 + "、");
                sb.append(applyMaters.get(i).get("MATER_NAME"));
                String materPath = applyMaters.get(i).get("MATER_PATH") == null ? "" : applyMaters.get(i).get("MATER_PATH").toString();
                String materPath2 = applyMaters.get(i).get("MATER_PATH2") == null ? "" : applyMaters.get(i).get("MATER_PATH2").toString();
                if (StringUtils.isNotEmpty(materPath)) {
                    sb.append("（存在提交材料模板）");
                }
                if (StringUtils.isNotEmpty(materPath2)) {
                    sb.append("（存在材料填写示例）");
                }
                // 份数
                String materClsmlx = applyMaters.get(i).get("MATER_CLSMLX") == null ? "" : applyMaters.get(i).get("MATER_CLSMLX").toString();
                String materClsm = applyMaters.get(i).get("MATER_CLSM") == null ? "" : applyMaters.get(i).get("MATER_CLSM").toString();
                String pageCopyNum = applyMaters.get(i).get("PAGECOPY_NUM") == null ? "" : applyMaters.get(i).get("PAGECOPY_NUM").toString();
                String pageNum = applyMaters.get(i).get("PAGE_NUM") == null ? "" : applyMaters.get(i).get("PAGE_NUM").toString();
                if (StringUtils.isNotEmpty(materClsmlx)) {
                    sb.append("(");
                    if (StringUtils.isNotEmpty(pageCopyNum) || StringUtils.isNotEmpty(pageNum)) {
                        if (materClsmlx.indexOf("复印件") > -1) {
                            sb.append("复印件" + pageCopyNum + "份,");
                        }
                        if (materClsmlx.indexOf("原件") > -1) {
                            String gatherorver = String.valueOf(applyMaters.get(i).get("GATHERORVER") == null ? "" : applyMaters.get(i).get("GATHERORVER"));
                            sb.append("原件" + pageNum + "份").append("(").append(gatherorver);
                            sb.append("),");
                        }
                        if (materClsmlx.indexOf("电子文档") > -1) {
                            sb.append("电子文档,");
                        }
                        if (sb.lastIndexOf(",") != -1) {
                            sb = new StringBuffer(sb.substring(0, sb.lastIndexOf(",")));
                        } else {
                            sb = new StringBuffer(sb);
                        }

                    } else {
                        if ("复印件".equals(materClsmlx)) {
                            sb.append("原件1份");
                        }
                        sb.append(materClsmlx + materClsm + "份");
                    }
                    String materIsneed = applyMaters.get(i).get("MATER_ISNEED") == null ? "否" : applyMaters.get(i).get("MATER_ISNEED").toString();
                    if ("0".equals(materIsneed)) {
                        sb.append("非必需");
                    } else if ("1".equals(materIsneed)) {
                        sb.append("必需");
                    }
                    sb.append(")");
                }
                sb.append("\n").append("材料说明：");
                // 材料说明
                String materDssc = applyMaters.get(i).get("MATER_DESC") == null ? "无" : applyMaters.get(i).get("MATER_DESC").toString();
                sb.append(materDssc).append("\n");
            }
            String matters = sb.substring(0, sb.lastIndexOf("\n"));
            serviceItem.put("APPLY_MATTER", matters);
        } else {
            serviceItem.put("APPLY_MATTER", "");
        }
    }

    public static void main(String[] args) {
        String sb = "1、cccccccccc(原件1份()非必需)\n" + "材料说明：无\n" + "2、11111(原件1份()非必需)\n" + "材料说明：无\n" + "3、22222(";
    }

    private void sxsx(Map<String, Object> serviceItem) {
        String sxsx = serviceItem.get("FTA_FLAG") == null ? "" : serviceItem.get("FTA_FLAG").toString();
        if (StringUtils.isNotEmpty(sxsx)) {
            String sxsxname = dictionaryService.findByDicCodeAndTypeCode(sxsx, "isFta");
            serviceItem.put("SXSX", sxsxname);
        } else {
            serviceItem.put("SXSX", "");
        }
    }

    private void sxxzAndBjlx(Map<String, Object> serviceItem) {
        // 事项性质
        String sxxz = (String) serviceItem.get("SXXZ");
        /*
         * if("XK".equals(sxxz)){ sxxz="行政许可"; }else if("FK".equals(sxxz)){
         * sxxz="非行政许可"; }else if("GF".equals(sxxz)){ sxxz="公共服务"; }else
         * if("QR".equals(sxxz)){ sxxz="行政确认"; }else if("QT".equals(sxxz)){
         * sxxz="其他"; }
         */
        sxxz = dictionaryService.findByDicCodeAndTypeCode(sxxz, "ServiceItemXz");
        serviceItem.put("SXXZ", sxxz);
        // 办件类型
        String bjlx = (String) serviceItem.get("SXLX");
        if ("1".equals(bjlx)) {
            bjlx = "即办件";
        } else if ("2".equals(bjlx)) {
            bjlx = "普通件";
        } else if ("3".equals(bjlx)) {
            bjlx = "特殊件";
        } else if ("4".equals(bjlx)) {
            bjlx = "联办件";
        } else if ("5".equals(bjlx)) {
            bjlx = "承诺上报件";
        }
        serviceItem.put("SXLX", bjlx);
    }

    private void qckcs(Map<String, Object> serviceItem) {
        String rzbsdtfs = serviceItem.get("RZBSDTFS") == null ? "" : serviceItem.get("RZBSDTFS").toString();
        String ckcs1 = serviceItem.get("CKCS_1") == null ? "" : serviceItem.get("CKCS_1").toString();
        String ckcs2 = serviceItem.get("CKCS_2") == null ? "" : serviceItem.get("CKCS_2").toString();
        String ckcs3 = serviceItem.get("CKCS_3") == null ? "" : serviceItem.get("CKCS_3").toString();
        String ckcs4 = serviceItem.get("CKCS_4") == null ? "" : serviceItem.get("CKCS_4").toString();
        String rzbsdtfsString = "";
        if (rzbsdtfs == "in01") {
            rzbsdtfsString = "指南式（一星）";
        } else if (rzbsdtfs == "in02") {
            rzbsdtfsString = "链接式（二星）";
        } else if (rzbsdtfs == "in04" && StringUtils.isEmpty(ckcs3)) {
            rzbsdtfsString = "收办分离式（三星）";
        } else if (rzbsdtfs == "in04" && StringUtils.isEmpty(ckcs2)) {
            rzbsdtfsString = "收办分离式（四星）";
        } else if (rzbsdtfs == "in05") {
            rzbsdtfsString = "全流程（五星）";
        }
        serviceItem.put("RZBSDTFSSTRING", rzbsdtfsString);
        StringBuffer sbfsqxccs = new StringBuffer();
        if (StringUtils.isNotEmpty(ckcs1)) {
            if (ckcs1.equals("-1")) {
                sbfsqxccs.append("窗口申请，去现场次数未承诺。");
                sbfsqxccs.append("\n");
            } else {
                sbfsqxccs.append("窗口申请，去现场最多跑");
                sbfsqxccs.append(ckcs1);
                sbfsqxccs.append("趟。");
                sbfsqxccs.append("\n");
            }
        }
        if (rzbsdtfs.equals("in01")) {
            sbfsqxccs.append("网上申请暂未开通。");
        } else if (ckcs2.equals("-1") && ckcs3.equals("-1") && ckcs4.equals("-1")) {
            sbfsqxccs.append("网上申请，去现场次数未承诺。");
        } else {
            if (StringUtils.isNotEmpty(ckcs2) && !ckcs2.equals("-1")) {
                sbfsqxccs.append("网上申请，去现场最多跑");
                sbfsqxccs.append(ckcs2);
                sbfsqxccs.append("趟。");
            } else if (StringUtils.isNotEmpty(ckcs3) && !ckcs3.equals("-1")) {
                sbfsqxccs.append("网上申请，去现场最多跑");
                sbfsqxccs.append(ckcs3);
                sbfsqxccs.append("趟。");
            } else if (!ckcs4.equals("0") && StringUtils.isNotEmpty(ckcs4) && !ckcs4.equals("-1")) {
                sbfsqxccs.append("网上申请，去现场最多跑");
                sbfsqxccs.append(ckcs4);
                sbfsqxccs.append("趟。");
            } else if (StringUtils.isNotEmpty(ckcs4) && ckcs4.equals("0") && !ckcs4.equals("-1")) {
                sbfsqxccs.append("网上申请，一趟不用跑。");
            }
        }
        serviceItem.put("SBFSQXCCS", sbfsqxccs);
    }

    /**
     * 描述：承诺时间
     * 
     * @author Water Guo
     * @created 2017-5-11 下午5:31:30
     * @param serviceItem
     */
    public void setCSSJ(Map<String, Object> serviceItem) {
        Object cnsj = serviceItem.get("CNQXGZR");
        if (cnsj != null) {
            if (cnsj.toString().equals("0")) {
                serviceItem.put("CNQXGZR", "当日办结");
            } else {
                serviceItem.put("CNQXGZR", cnsj.toString() + "个工作日");
            }
        }
    }

    /**
     * 
     * 描述：打印事项PDF
     * 
     * @author Water Guo
     * @created 2017-4-24 下午10:07:02
     * @param request
     */
    @RequestMapping(params = "printPDFItemTemplate")
    public ModelAndView printPDFItemTemplate(HttpServletRequest request) {
        // Map<String, Object> templateData = new HashMap<String, Object>();
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String entityId = request.getParameter("ITEM_ID");
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" }, new Object[] { entityId });
            // log.info(serviceItem.get("BLLC"));
            Map<String, Object> typeItem = busTypeService.getIdAndNamesByItemId(entityId);
            serviceItem.put("BUS_TYPEIDS", typeItem.get("TYPE_IDS"));
            serviceItem.put("BUS_TYPENAMES", typeItem.get("TYPE_NAMES"));
            String standardCatalogName = StringUtil.getValue(serviceItem, "STANDARD_CATALOG_NAME");
            if (StringUtils.isNotEmpty(standardCatalogName)) {
                serviceItem.put("CATALOG_NAME", standardCatalogName);
            } else {
                String catalogCode = (String) serviceItem.get("CATALOG_CODE");
                if (StringUtils.isNotEmpty(catalogCode)) {
                    Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
                    serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
                }
            }
            // 事项性质
            String sxxz = (String) serviceItem.get("SXXZ");
            String sxxzname = dictionaryService.findByDicCodeAndTypeCode(sxxz, "ServiceItemXz");
            serviceItem.put("SXXZ", sxxzname);
            serviceItem.put("CNQXGZR", serviceItem.get("CNQXGZR").toString());
            // 办件类型
            String bjlx = (String) serviceItem.get("SXLX");
            if ("1".equals(bjlx)) {
                bjlx = "即办件";
            } else if ("2".equals(bjlx)) {
                bjlx = "普通件";
            } else if ("3".equals(bjlx)) {
                bjlx = "特殊件";
            }
            serviceItem.put("SXLX", bjlx);
            qckcs(serviceItem);
            sxsx(serviceItem);// 事项属性
            serviceItem.put("XCCS", serviceItem.get("SBFSQXCCS").toString());
            // 收费方式
            String sfsf = (String) serviceItem.get("SFSF");
            if ("0".equals(sfsf)) {
                sfsf = "否";
                serviceItem.put("SFFS", "");
                serviceItem.put("YIJU", "");
            } else if ("1".equals(sfsf)) {
                sfsf = "是";
                String sffs = (String) serviceItem.get("SFFS");
                String yiju = (String) serviceItem.get("SFBZJYJ");
                yiju = replaceBlank(yiju);
                sffs = dictionaryService.findByDicCodeAndTypeCode(sffs, "ChargeType");
                sfsf += "    收费方式：" + sffs + "    收费标准及依据：" + yiju;
                serviceItem.put("SFFS", sffs);
                serviceItem.put("YIJU", yiju);
            }
            serviceItem.put("SFSF", sfsf);
            serviceItem.put("TRAFFIC_GUIDE", serviceItem.get("TRAFFIC_GUIDE").toString());
            String tshj = printAttachService.getTshj(entityId);// 特殊环节
            serviceItem.put("TSHJ", tshj);
            String xsyj = serviceItem.get("XSYJ").toString();
            serviceItem.put("XSYJ", strToList(xsyj));
            printPDFItemData(serviceItem, entityId);
        }
        serviceItem.put("TemplatePath", templatePath);
        serviceItem.put("TemplateName", templateName);
        // 承诺时限工作日
        setCSSJ(serviceItem);
        // 以省网办编码为主
        setServiceItemCode(serviceItem);
        // 法定时限
        setServiceItemFDSX(serviceItem);
        request.setAttribute("TemplateData", serviceItem);
        return printPDFItem(serviceItem, request);
        // getTemplateDataMap(templateData, exeId);
        // request.setAttribute("TemplateData", templateData);
    }

    /**
     *
     * 描述：打印事项PDF
     * 
     * @author Water Guo
     * @created 2017-4-24 下午10:07:02
     * @param request
     */
    public void printPDFItemData(Map<String, Object> serviceItem, String entityId) {
        List<Map<String, Object>> applyMaters = applyMaterService.findByItemId(entityId, null);
        StringBuffer sb = new StringBuffer();
        String busclassName = "";
        if (applyMaters.size() > 0) {
            for (int i = 0; i < applyMaters.size(); i++) {
                String name = applyMaters.get(i).get("BUSCLASS_NAME") == null ? "" : applyMaters.get(i).get("BUSCLASS_NAME").toString();
                if (!name.equals(busclassName) && !name.equals("无")) {
                    sb.append("●");
                    sb.append(name);
                    // sb.append("：");
                    sb.append("\n");
                    busclassName = name;
                }
                sb.append(i + 1 + "、");
                sb.append(applyMaters.get(i).get("MATER_NAME"));
                // 份数
                String materClsmlx = applyMaters.get(i).get("MATER_CLSMLX") == null ? "" : applyMaters.get(i).get("MATER_CLSMLX").toString();
                String materClsm = applyMaters.get(i).get("MATER_CLSM") == null ? "" : applyMaters.get(i).get("MATER_CLSM").toString();
                if (StringUtils.isNotEmpty(materClsmlx)) {
                    sb.append("(");
                    if ("复印件".equals(materClsmlx)) {
                        sb.append("原件1份");
                    }
                    sb.append(materClsmlx + materClsm + "份");
                    sb.append(")");
                }
                sb.append("\r\n\r\n\r\n");
                sb.append("材料说明：");
                // 材料说明
                String materDssc = applyMaters.get(i).get("MATER_DESC") == null ? "无" : applyMaters.get(i).get("MATER_DESC").toString();
                sb.append(materDssc);
                sb.append("\r\n\r\n\r\n");
            }
            String matters = sb.substring(0, sb.lastIndexOf("\n"));
            serviceItem.put("APPLY_MATTER", matters);
        } else {
            serviceItem.put("APPLY_MATTER", "");
        }
    }

    /**
     * 
     * 描述：PDF打印
     * 
     * @author Water Guo
     * @created 2017-4-24 下午10:45:36
     * @param map
     * @return
     */
    public ModelAndView printPDFItem(Map<String, Object> variables, HttpServletRequest request) {
        String fileRullPath = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        attachFileFolderPath = attachFileFolderPath + "attachFiles/itemPDF/";
        String MODELPATH = request.getParameter("MODELPATH");
        fileRullPath = attachFileFolderPath + MODELPATH;
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
        WordReplaceUtil.replaceWord(variables, fileRullPath, newWordFilePath);
        String fileType = "doc";
        // 生成PDF文件
        if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            JComPDFConverter.excel2PDF(newWordFilePath, pdfFile + uploadFullPath);
        } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
            uploadFullPath += "/" + uuId + ".pdf";
            WordToPdfUtil.word2pdf(newWordFilePath, pdfFile + uploadFullPath);
        }
        List<String> list = Icepdf.pdf2Pic(pdfFile + uploadFullPath);
        request.setAttribute("imgList", list);
        request.setAttribute("pdfFilePath", pdfFile + uploadFullPath);
        request.setAttribute("pdfPath", uploadFullPath);
        return new ModelAndView("website/zzhy/pdfServiceItemPreview");
    }

    /**
     * 
     * 描述：以省网办编码为主
     * 
     * @author Water Guo
     * @created 2017-5-10 下午1:58:16
     * @param serviceItem
     */
    public void setServiceItemCode(Map<String, Object> serviceItem) {
        if (serviceItem.get("SWB_ITEM_CODE") != null) {
            String SWB_ITEM_CODE = serviceItem.get("SWB_ITEM_CODE").toString();
            serviceItem.put("CODE", SWB_ITEM_CODE);
        } else {
            serviceItem.put("CODE", serviceItem.get("ITEM_CODE"));
        }
    }

    /**
     * 描述：设置法定时限
     * 
     * @author Water Guo
     * @created 2017-5-18 上午10:44:58
     * @param serviceItem
     */
    public void setServiceItemFDSX(Map<String, Object> serviceItem) {
        String FDSXGZR = "";
        String fdsxNum = serviceItem.get("FDSXGZR") == null ? "" : serviceItem.get("FDSXGZR").toString();
        if (!"".equals(fdsxNum) && !"0".equals(fdsxNum)) {
            FDSXGZR = fdsxNum;
            if (FDSXGZR.equals("-1")) {
                FDSXGZR = "无";
            } else {
                String FDSXLX = "";
                if (null == serviceItem.get("FDSXLX") || "".equals(serviceItem.get("FDSXLX"))) {
                    FDSXLX = "个工作日";
                } else {
                    FDSXLX = serviceItem.get("FDSXLX").toString();
                    if (FDSXLX.equals("y")) {
                        FDSXLX = "个月";
                    } else if (FDSXLX.equals("zrr")) {
                        FDSXLX = "个自然日";
                    } else if (FDSXLX.equals("gzr")) {
                        FDSXLX = "个工作日";
                    }
                }
                FDSXGZR = FDSXGZR + FDSXLX;
            }
        } else if ("0".equals(fdsxNum)) {
            FDSXGZR = "当日办结";
        } else {
            FDSXGZR = "无";
        }
        serviceItem.put("FDSXGZR", FDSXGZR);
    }

    /**
     * 
     * 描述：字符串转list
     * 
     * @author Water Guo
     * @created 2017-4-26 下午5:32:24
     * @param str
     */
    public List<String> strToList(String str) {
        String[] strArr = str.split("\n");
        List list = new LinkedList<String>();
        for (int i = 0; i < strArr.length; i++) {
            String s = strArr[i];
            list.add(s);
        }
        return list;
    }

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
        this.printAttachService.getTemplateDataMapByExeId(templateData, exeId);
    }

    /**
     * 
     * 描述：描述 获取回填数据
     * 
     * @author Water Guo
     * @created 2017-3-19 下午5:33:03
     * @param templateData
     * @param exeId
     */
    public void getBackDataMap(Map<String, Object> templateData, String itemCode) {
        this.printAttachService.getBackDataMapByItemId(templateData, itemCode);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "printFiles")
    public ModelAndView flowFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        return new ModelAndView("hflow/printTemplate/printFiles");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "printList")
    public void flowList(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> list = printAttachService.findByExeId(exeId);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "printQRcode")
    public ModelAndView printQRcode(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String exeId = request.getParameter("EFLOW_EXEID");
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        URL url;
        Map<String, Object> ecipResponse = printAttachService.getByJdbc("T_COMMERCIAL_ECIPRESPONSE", new String[] { "EXE_ID" }, new Object[] { exeId });
        if (ecipResponse != null && ecipResponse.get("RESULT_CODE").equals("OK")) {
            String qrcodeurl = ecipResponse.get("QRCODE_URL").toString();
            try {
                Properties properties = FileUtil.readProperties("project.properties");
                String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
                String attachFileUrl = properties.getProperty("AttachFileUrl") + "attachFiles/";
                String uploadPath = "QrcodeAttach/";
                // 定义二维码图片的保存的相对目录路径
                SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
                String currentDate = "DATE_" + dirSdf.format(new Date());
                String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
                // 建立全路径目录和临时目录
                File uploadFullPathFolder = new File(uploadFullPath);
                if (!uploadFullPathFolder.exists()) {
                    uploadFullPathFolder.mkdirs();
                }
                String uuId = UUIDGenerator.getUUID();
                String fileName = uuId + ".png";
                String filePath = uploadFullPath + fileName;
                filePath = filePath.replace("\\", "/");
                templateData.put("imagePath", attachFileUrl + uploadPath + currentDate + "/" + fileName);

                url = new URL(qrcodeurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                InputStream inStream = conn.getInputStream();
                FileOutputStream fout = new FileOutputStream(filePath);
                BufferedImage prevImage = ImageIO.read(inStream);
                int width = 0;
                int height = 0;
                if (templateName.contains("正本")) {
                    width = 165;
                    height = 165;
                } else if (templateName.contains("副本")) {
                    width = 145;
                    height = 145;
                }
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
                Graphics graphics = image.createGraphics();
                graphics.drawImage(prevImage, 0, 0, width, height, null);
                ImageIO.write(image, "png", fout);
                fout.flush();
                fout.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            request.setAttribute("TemplateData", templateData);
            return new ModelAndView("wsbs/readtemplate/printQRcodeTemplate");
        } else {
            return new ModelAndView("wsbs/readtemplate/printQRcodeError");
        }
    }

    /**
     * 描述 电子签证
     * 
     * @author Reuben Bao
     * @created 2019年3月29日 上午10:51:44
     * @param request
     */
    @RequestMapping(params = "toSignNames")
    @ResponseBody
    public AjaxJson signNames(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson resultJson = new AjaxJson();
        // word转pdf
        String localFilePath = request.getSession().getAttribute("localFilePath") != null ? request.getSession().getAttribute("localFilePath").toString() : "";
        // 截取word临时存储路径的UUID作为pdf文件名称
        int lastIndex = localFilePath.lastIndexOf(".");
        int startIndex = localFilePath.lastIndexOf("/");
        String pdfName = localFilePath.substring(startIndex + 1, lastIndex);
        Properties properties = FileUtil.readProperties("project.properties");
        // 创建pdf文件保存在PdfFilePath路径，用于生成pdf文件
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String uploadFullPath = "attachFiles/pdf/DATE_" + dirSdf.format(new Date()) + "/" + pdfName + ".pdf";
        // 用于保存签证后的pdf文件
        String signLoadPath = "attachFiles/pdf/DATE_" + dirSdf.format(new Date()) + "/";
        // 创建word转pdf后保存的文件夹
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        // 将word文件转换成pdf文件后保存在PdfFilePath上
        WordToPdfUtil.word2pdf(localFilePath, pdfFile + uploadFullPath);
        Map<String, Object> result = new HashMap<String, Object>();
        // 创建时pdf文件名称
        String pdfReg = UUIDGenerator.getUUID() + ".pdf";
        // 签证后pdf名称
        String pdfSignReg = UUIDGenerator.getUUID() + ".pdf";
        result.put("uploadFullPath", signLoadPath + pdfReg);
        result.put("signPdfPath", signLoadPath + pdfSignReg);
        // 将签证后返回的pdf文件保存在AttachFilePath路径用于下载
        String attachFilePath = properties.getProperty("AttachFilePath").replace("\\", "/");
        String localSignFilePath = attachFilePath + signLoadPath;
        // 创建签证后的pdf文件保存文件夹
        File localSignFilePathFolder = new File(localSignFilePath);
        if (!localSignFilePathFolder.exists()) {
            localSignFilePathFolder.mkdirs();
        }
        // 文件拷贝
        copyFile(pdfFile + uploadFullPath, localSignFilePath + pdfReg);
        // 服务器pdf文件路径，用于设备读取展示
        result.put("localSignFilePath", localSignFilePath + pdfReg);
        // 将签证后的pdf保存到uploadSignFilePath路径上
        result.put("uploadSignFilePath", localSignFilePath + pdfSignReg);
        // 根据生成的pdf文件定位电子签证坐标
        this.setSignNameLocation(result, pdfFile + uploadFullPath, request.getParameter("signNameKey"), request.getParameter("isTwoSign"),
                request.getParameter("printTemplateName"));
        resultJson.setJsonString(JSON.toJSONString(result));
        return resultJson;
    }

    /**
     * 描述 根据生成的pdf文件定位电子签证坐标
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 下午2:56:56
     * @param result
     *            返回对象
     * @param pdfPath
     *            pdf文件路径
     * @param regName
     *            查询字符串
     * @param isTwoSign
     *            是否两个签字
     * @param printTemplate
     *            模板
     */
    public void setSignNameLocation(Map<String, Object> result, String pdfPath, String regName, String isTwoSign, String printTemplate) {
        PdfRead pdfRead = new PdfRead();
        Map<String, Object> readMap = pdfRead.readPdfByTextForXandY(pdfPath, regName);
        StringBuffer location = new StringBuffer();
        try {
            if ("20190402155520.doc".equals(printTemplate)) {
                location.append("2,").append(StringUtil.getValue(readMap, "pageNum")).append(",").append(Integer.parseInt((String) readMap.get("locationX")) + 30).append(",")
                        .append(Integer.parseInt((String) readMap.get("locationY")) + 30).append(",90").append("|").append("2,").append(StringUtil.getValue(readMap, "pageNum"))
                        .append(",").append(Integer.parseInt((String) readMap.get("locationX")) - 180).append(",").append(Integer.parseInt((String) readMap.get("locationY")) + 30)
                        .append(",90");
            } else {
                location.append("2,").append(StringUtil.getValue(readMap, "pageNum")).append(",")
                        .append(Integer.parseInt((String) readMap.get("locationX")) + ("true".equals(isTwoSign) ? 30 : 80)).append(",")
                        .append(Integer.parseInt((String) readMap.get("locationY")) + 20).append(",110");
                if ("true".equals(isTwoSign)) {
                    location.append("|").append("2,").append(StringUtil.getValue(readMap, "pageNum")).append(",").append(Integer.parseInt((String) readMap.get("locationX")) - 220)
                            .append(",").append(Integer.parseInt((String) readMap.get("locationY")) + 20).append(",110");
                }
            }
        } catch (Exception e) {
            log.info("setSignNameLocation出错！");
            location.setLength(0);
            location.append("2,1,100,100,100");
        }
        result.put("location", location.toString());
    }

    /**
     * 描述 预览签证成功的pdf文件
     * 
     * @author Reuben Bao
     * @created 2019年3月29日 下午5:59:55
     */
    @RequestMapping(params = "readSignNamesFile")
    public ModelAndView readSignNamesFile(HttpServletRequest request, HttpServletResponse response) {
        // 签证后的pdf文件路径
        String downPdfFilePath = request.getParameter("downPdfFilePath");
        if (StringUtils.isNotEmpty(downPdfFilePath)) {
            // pdf转图片用于预览
            List<String> list = Icepdf.pdf2Pic(downPdfFilePath);
            request.setAttribute("imgList", list);
            return new ModelAndView("website/zzhy/pdfServiceItemPreview");
        } else {
            return null;
        }
    }

    /**
     * 描述 文件拷贝
     * 
     * @author Reuben Bao
     * @created 2019年4月3日 上午8:27:36
     * @param srcFile
     *            源文件
     * @param desFile
     *            目标文件
     */
    public static void copyFile(String srcFile, String desFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(desFile));
            byte[] b = new byte[1024];
            Integer len = 0;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        } catch (Exception e) {
            log.error("文件拷贝出错，方法：copyFile");
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                log.error("文件拷贝出错，方法：copyFile");
            }
        }
    }

    /**
     * 描述 保存电子签证后的pdf文件
     * 
     * @author Reuben Bao
     * @created 2019年4月3日 下午7:54:19
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveSignFile")
    @ResponseBody
    public AjaxJson saveSignFile(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson resultJson = new AjaxJson();
        // 获取pdf签证的64位文件刘
        String basePdf = request.getParameter("pdfBaseFile");
        String uploadSignFilePath = request.getParameter("uploadSignFilePath");
        FileUtil.decodeBase64File(basePdf.replaceAll(" ", "+"), uploadSignFilePath);
        return resultJson;
    }

    /**
     * 描述 根据客户填写表单与模板文件绑定生成word预览文件
     * 
     * @author Reuben Bao
     * @created 2019年4月3日 上午9:39:45
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "createSubmitFormFile")
    public ModelAndView createSubmitFormFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String exeId = request.getParameter("EXE_ID");
        if (StringUtils.isEmpty(exeId)) {
            return null;
        }
        // 根据exe_id获取申报数据对象
        Map<String, Object> execution = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
        // 获取事项对应的模板
        // this.getPrintTemplate(request, response, execution);
        String defId = StringUtil.getValue(execution, "DEF_ID");
        String itemCode = StringUtil.getValue(execution, "ITEM_CODE");
        // 获取申请书打印配置文件数据
        String templatePath = request.getSession().getAttribute("PRINT_TEMPLATE") != null ? request.getSession().getAttribute("PRINT_TEMPLATE").toString() : "";
        // 获取用户勾选数据
        String selectList = request.getParameter("selectList");
        returnMap.put("TemplatePath", templatePath);
        // 设值itemCode用于保存打印日志
        returnMap.put("xmsqbh", exeId);
        // 获取流程数据赋值word文档用于预览
        // 初始化数据
        this.setDefualtValue(returnMap, exeId);
        // 业务表名称、业务表主键ID
        String busRecordId = StringUtil.getValue(execution, "BUS_RECORDID");
        String busTableName = StringUtil.getValue(execution, "BUS_TABLENAME");
        // 获取流程明细数据
        Map<String, Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
        // 开始定义流程信息对象
        Map<String, Object> flowAllObj = this.jbpmService.getFlowAllObj(defId, exeId, "false", request);
        // 设值流程名称用于打印日志展示
        Map<String, Object> eflowMap = (Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF");
        returnMap.put("TemplateName", StringUtil.getValue(eflowMap, "DEF_NAME") + "-申请书");
        // 设值流程主表数据
        this.setSubmitUsrsInfo(returnMap, execution);
        // 根据业务表名和id获取该办件已收取的电子档材料附件
        this.setMaterList(returnMap, busTableName, busRecordId);
        // 获取用户勾选的数据
        this.setSelectedList(returnMap, selectList);
        // 设值权力类型数据
        this.setpowerType(returnMap, serviceItem);
        // 设值不动产基础数据
        this.setBdcData(flowAllObj, returnMap);
        // 查询业务表获取各事项明细数据
        printAttachService.getPowerPeopleList(busTableName, busRecordId, returnMap);
        // 数据去换行处理
        for (Map.Entry<String, Object> entry : returnMap.entrySet()) {
            String valString = entry.getValue() == null || "".equals(entry.getValue().toString()) ? " " : entry.getValue().toString();
            returnMap.put(entry.getKey(), replaceBlank(valString));
        }
        // 是否展示电子签证
        request.setAttribute("isSignButton", "true");
        request.setAttribute("TemplateData", returnMap);
        // 是否两次签字
        request.setAttribute("isTwoSign", "true");
        return new ModelAndView("wsbs/readtemplate/printReadTemplate");
    }

    /**
     * 描述 设值已收材料数据
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 上午11:33:00
     * @param returnMap
     * @param busTableName
     * @param busRecordId
     */
    public void setMaterList(Map<String, Object> returnMap, String busTableName, String busRecordId) {
        if (StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(busTableName)) {
            StringBuffer ysqMaters = new StringBuffer();
            List<Map<String, Object>> ysqMatersLiset = fileAttachService.findYsqByList(busTableName, busRecordId);
            if (ysqMatersLiset != null && ysqMatersLiset.size() > 0) {
                for (int i = 0; i < ysqMatersLiset.size(); i++) {
                    Map<String, Object> ysqMatersMap = ysqMatersLiset.get(i);
                    ysqMaters.append(String.valueOf(i + 1)).append(". ").append(StringUtil.getValue(ysqMatersMap, "MATER_NAME")).append(" ");
                }
            }
            returnMap.put("djyyzmwj", ysqMaters.toString());
        }
    }

    /**
     * 描述 获取流程主表数据
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 上午11:46:53
     * @param returnMap
     * @param execution
     */
    public void setSubmitUsrsInfo(Map<String, Object> returnMap, Map<String, Object> execution) {
        // 代理人姓名
        returnMap.put("dlrxm1", StringUtil.getValue(execution, "JBR_NAME"));
        // 代理人联系手机
        returnMap.put("dh2", StringUtil.getValue(execution, "JBR_MOBILE"));
        // 申请人通信地址
        returnMap.put("txdz1", StringUtil.getValue(execution, "SQRLXDZ"));
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
    }

    /**
     * 描述 设值用户勾选数据
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 上午11:49:21
     * @param returnMap
     * @param selectList
     *            设备勾选返回的数据
     */
    public void setSelectedList(Map<String, Object> returnMap, String selectList) {
        // 绑定用户勾选数据
        if (StringUtils.isNotEmpty(selectList)) {
            String[] selectArr = selectList.split(",");
            for (int j = 0; j < selectArr.length; j++) {
                int index = selectArr[j].indexOf("=");
                // 文本框数据
                if (index > -1) {
                    returnMap.put(selectArr[j].substring(0, index), selectArr[j].substring(index + 1, selectArr[j].length()));
                } else {
                    returnMap.put(selectArr[j], "√");
                }
            }
        }
    }

    /**
     * 描述 根据流程明细数据设值
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 下午1:11:16
     * @param returnMap
     * @param serviceItem
     */
    public void setpowerType(Map<String, Object> returnMap, Map<String, Object> serviceItem) {
        // 权力类型
        returnMap.put("qllx", StringUtil.getValue(serviceItem, "ITEM_NAME"));
        // 登记类型
        returnMap.put("djlx", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
        // 登记原因默认为登记类型
        returnMap.put("djyy", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
    }

    /**
     * 描述 设值不动产基础数据
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 下午1:21:27
     * @param flowAllObj
     */
    @SuppressWarnings("unchecked")
    public void setBdcData(Map<String, Object> flowAllObj, Map<String, Object> returnMap) {
        Map<String, Object> busRecord = new HashMap<String, Object>();
        if (flowAllObj.get("busRecord") != null) {
            busRecord = (Map<String, Object>) flowAllObj.get("busRecord");
            // 获取多证合一数据
            Map<String, Object> multiple = executionService.getByJdbc("T_COMMERCIAL_MULTIPLE", new String[] { "COMPANY_ID" }, new Object[] { busRecord.get("BUS_RECORDID") });
            if (null != multiple) {
                busRecord.putAll(multiple);
            }
        }
        // 不动产单元号
        returnMap.put("bdcdyh", StringUtil.getValue(busRecord, "ESTATE_NUM"));
        // 不动产类型
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { "bdclx", StringUtil.getValue((Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF"), "DEF_ID") });
        if (dictionary != null) {
            returnMap.put("bdclx", StringUtil.getValue(dictionary, "DIC_NAME"));
        }
    }

    /**
     * 描述 初始化设值
     * 
     * @author Reuben Bao
     * @created 2019年4月9日 下午3:42:27
     * @param returnMap
     * @param exeId
     */
    public void setDefualtValue(Map<String, Object> returnMap, String exeId) {
        // 数据初始化
        returnMap.put("qlrxm", "");
        returnMap.put("sfzjzl1", "");
        returnMap.put("zjh1", "");
        returnMap.put("zl", "");
        returnMap.put("dbr1", "");
        returnMap.put("dh1", "");
        returnMap.put("ywrxm", "");
        returnMap.put("txdz2", "");
        returnMap.put("sfzjzl2", "");
        returnMap.put("zjh2", "");
        returnMap.put("dbr2", "");
        returnMap.put("dljgmc1", "");
        returnMap.put("dljgmc2", "");
        returnMap.put("dh3", "");
        returnMap.put("dlrxm2", "");
        returnMap.put("dh4", "");
        returnMap.put("yt", "");
        returnMap.put("mj", "");
        returnMap.put("ybdcqszsh", "");
        returnMap.put("yhlx", "");
        returnMap.put("gzwlx", "");
        returnMap.put("lz", "");
        returnMap.put("bdbzqse", "");
        returnMap.put("zwlxqx", "");
        returnMap.put("zjjzwdyfw", "");
        returnMap.put("xydzl", "");
        returnMap.put("xydbdcdyh", "");
        returnMap.put("szfefbw1", "");
        returnMap.put("szfefbw2", "");
        returnMap.put("qtxyxw1", "");
        returnMap.put("qtxyxw2", "");
        returnMap.put("bdclx", "土地");
        returnMap.put("bh", exeId);
        returnMap.put("sjr", "");
        String date = DateTimeUtil.dateToStr(new Date());
        returnMap.put("rq", date);
        returnMap.put("nian", DateTimeUtil.getCurrentYear());
        returnMap.put("nian2", DateTimeUtil.getCurrentYear());
        returnMap.put("yue", DateTimeUtil.getCurrentMonth());
        returnMap.put("yue2", DateTimeUtil.getCurrentMonth());
        returnMap.put("ri", date.substring(date.lastIndexOf("-") + 1, date.length()));
        returnMap.put("ri2", date.substring(date.lastIndexOf("-") + 1, date.length()));
    }

    /**
     * 描述 获取打印模板
     * 
     * @author Reuben Bao
     * @created 2019年4月8日 上午12:07:18
     * @param request
     * @param response
     * @param flowMap
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getPrintTemplate")
    @ResponseBody
    public AjaxJson getPrintTemplate(HttpServletRequest request, HttpServletResponse response, Map<String, Object> flowMap) {
        AjaxJson resultJson = new AjaxJson();
        String deftId = request.getParameter("deftId");
        // 换发不动产权证书和不动产登记证明流程的子项类型：换发抵押权登记证明、换发抵押权预告登记证明 采用抵押型问卷模板
        String busRecordId = request.getParameter("BUS_RECORDID");
        if (StringUtils.isEmpty(deftId) && StringUtils.isNotEmpty(StringUtil.getValue(flowMap, "DEF_ID"))) {
            deftId = StringUtil.getValue(flowMap, "DEF_ID");
        }
        if (StringUtils.isEmpty(busRecordId) && StringUtils.isNotEmpty(StringUtil.getValue(flowMap, "BUS_RECORDID"))) {
            busRecordId = StringUtil.getValue(flowMap, "BUS_RECORDID");
        }
        // 根据业务主键ID查询换发不动产业务表，若hf_type不为空，说明当前流程为换发流程
        Map<String, Object> hfBdcMap = executionService.getByJdbc("t_bdc_hfcqdj", new String[] { "YW_ID" }, new Object[] { busRecordId });
        String hfType = StringUtil.getValue(hfBdcMap, "HF_TYPE");
        if (StringUtils.isNotEmpty(deftId)) {
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY", new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "flowSubmitPrint", deftId });
            if ("2".equals(hfType) || "4".equals(hfType)) {
                resultJson.setJsonString("20190402155521.doc");
                request.getSession().setAttribute("PRINT_TEMPLATE", "20190402155521.doc");
            } else {
                resultJson.setJsonString(StringUtil.getValue(dictionary, "DIC_DESC"));
                request.getSession().setAttribute("PRINT_TEMPLATE", StringUtil.getValue(dictionary, "DIC_DESC"));
            }
        }
        return resultJson;
    }
}
