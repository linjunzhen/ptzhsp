/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.internalstock.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.poi.WordReplaceParamUtil;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.GenPlatReqUtil;
import net.evecom.core.util.JComPDFConverter;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.PdfToImgUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WordToPdfUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.CommercialSetService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import net.evecom.platform.zzhy.controller.DomesticControllerController;
import net.evecom.platform.zzhy.service.BankGenService;
import net.evecom.platform.zzhy.service.DomesticControllerService;
import net.evecom.platform.zzhy.service.RelatedAbcrApplyService;
import net.evecom.platform.zzhy.service.RelatedGenService;
import net.evecom.platform.zzhy.service.RelatedRdgtApplyService;
import net.evecom.platform.zzhy.service.RelatedRfsApplyService;
import net.evecom.platform.zzhy.service.RelatedRotpApplyService;
import net.evecom.platform.zzhy.service.RelatedSyGenService;
import net.evecom.platform.zzhy.service.RelatedTaxiApplyService;

/**
 * 描述
 * @author Panda Chen
 * @created 2018年6月6日 下午4:41:50
 */
@Controller
@RequestMapping("/internalStockController")
public class InternalStockController  extends BaseController {
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
        return new ModelAndView("internalStock/domesticController/info");
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
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/internalStock/gdxxDiv");
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
        return new ModelAndView("website/applyforms/internalStock/dsxxDiv");
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
        return new ModelAndView("website/applyforms/internalStock/jsxxDiv");
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
        return new ModelAndView("website/applyforms/internalStock/jlxxDiv");
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
        String COMPANY_SETTYPE = request.getParameter("COMPANY_SETTYPE");
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
        request.setAttribute("itemCode", itemCode);
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("itemNames", itemNames);
        return new ModelAndView("website/internalStock/comptype_desc");
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
        return new ModelAndView("website/applyforms/internalStock/jyfwSelector");
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
            this.getChildren(top, top.get("INDU_ID").toString(), variables);
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
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
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
                // 设置其他参数
                new WordReplaceParamUtil().setParam(busRecord);
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
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
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
                // 设置其他参数
                new WordReplaceParamUtil().setParam(busRecord);
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
        attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
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
        Map<String, Object> fileAttach = null;
        String attachFileFolderPath = "";
        // 原文件名
        //String fileName = "";
        // 原文件路径
        String fileRullPath = "";
        fileAttach = domesticControllerService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                new Object[] { fileId });
        Properties properties = FileUtil.readProperties("project.properties");
        attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        //fileName = (String) fileAttach.get("FILE_NAME");
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
                // 设置其他参数
                new WordReplaceParamUtil().setParam(busRecord);
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
        return new ModelAndView("website/internalStock/pdfPreview");
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
        return new ModelAndView("website/applyforms/internalStock/itemSelector");
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
        return new ModelAndView("website/applyforms/internalStock/applyItemMaterList");
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
}
