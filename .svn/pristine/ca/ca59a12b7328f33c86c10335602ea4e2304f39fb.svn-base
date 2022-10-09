/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import com.alibaba.fastjson.JSON;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.*;


import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.bsfw.model.MaterDownload;
import net.evecom.platform.bsfw.service.KtStampService;
import net.evecom.platform.bsfw.util.MaterDownloadUtil;
import net.evecom.platform.hflow.controller.MaterConfigController;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
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
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 描述  凯特签章系统
 * @author Madison You
 * @version 1.0
 * @created 2019年11月21日 上午09:17:23
 */
@Controller
@RequestMapping("/ktStampController")
public class KtStampController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);
    /**
     * 卡特默认签章页面
     */
    private static String ktStampUploadView="bsdt/applyform/ktStamp/ktStampUploadView";
    /**
     * 工改签章页面
     */
    private static String projectSignView="bsdt/applyform/projectSign/projectSignView";

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/2 10:29:00
     * @param
     * @return
     */
    @Resource
    private KtStampService stampService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/2 11:05:00
     * @param
     * @return
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/6 14:41:00
     * @param
     * @return
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
   

//    /**
//     * 描述:测试自动盖章
//     *
//     * @author Madison You
//     * @created 2019/11/21 20:24:00
//     * @param
//     * @return
//     */
//    @RequestMapping("/testKtStamp")
//    @ResponseBody
//    public Map<String,Object> testSeal(HttpServletRequest request, HttpServletResponse response) {
//        Properties properties = FileUtil.readProperties("project.properties");
//        String KT_SEAL_URL = properties.getProperty("KT_SEAL_URL");
//        String KT_SEAL_USERNAME = properties.getProperty("KT_SEAL_USERNAME");
//        String KT_SEAL_PASSWORD = properties.getProperty("KT_SEAL_PASSWORD");
//        String KT_SEAL_ID = properties.getProperty("KT_SEAL_ID");
//        String param1 = request.getParameter("param1");
//        String param2 = request.getParameter("param2");
//        String param3 = request.getParameter("param3");
//        String param4 = request.getParameter("param4");
//        Map<String,Object> returnMap = new HashMap<>();
//        returnMap.put("success", true);
//        try{
//            KTAutoSealJavaClt clt = new KTAutoSealJavaClt( KT_SEAL_URL, KT_SEAL_USERNAME, KT_SEAL_PASSWORD, true );
//            log.error(clt.get_last_error());
//            returnMap.put("init", clt.get_last_error());
//            if( !clt.first_add_seal_on_paper( KT_SEAL_ID, 0, 200, 200, true ) )
//            {
//                log.error(clt.get_last_error());
//                returnMap.put("errorMsg", clt.get_last_error());
//                returnMap.put("success", false);
//                clt.finally_free();
//            }
//            int nRet= clt.final_convert_document(param1 ,Integer.parseInt(param2), Boolean.parseBoolean(param3), param4  );
//            if( 0 != nRet )
//            {
//                log.error(nRet + "[" + clt.get_last_error() + "]");
//                returnMap.put("errorMsg", clt.get_last_error());
//                returnMap.put("success", false);
//                clt.finally_free();
//            }
//            clt.finally_free();
//        } catch (Exception e) {
//            log.error("凯特签章错误");
//            log.error(e.getMessage(), e);
//            returnMap.put("errorMsg", "凯特签章错误");
//        }
//        return returnMap;
//    }
//
//    /**
//     * 描述:测试自动盖章
//     *
//     * @author Madison You
//     * @created 2019/11/21 20:24:00
//     * @param
//     * @return
//     */
//    @RequestMapping("/testKtStamp1")
//    @ResponseBody
//    public Map<String,Object> testSeal1(HttpServletRequest request, HttpServletResponse response) {
//        Properties properties = FileUtil.readProperties("project.properties");
//        String KT_SEAL_URL = properties.getProperty("KT_SEAL_URL");
//        String KT_SEAL_USERNAME = properties.getProperty("KT_SEAL_USERNAME");
//        String KT_SEAL_PASSWORD = properties.getProperty("KT_SEAL_PASSWORD");
//        String KT_SEAL_ID = properties.getProperty("KT_SEAL_ID");
//        String param1 = request.getParameter("param1");
//        String param2 = request.getParameter("param2");
//        String param3 = request.getParameter("param3");
//        String param4 = request.getParameter("param4");
//        Map<String,Object> returnMap = new HashMap<>();
//        returnMap.put("success", true);
//        try{
//            KTAutoSealJavaClt clt = new KTAutoSealJavaClt( KT_SEAL_URL, KT_SEAL_USERNAME, KT_SEAL_PASSWORD, true );
//            log.error(clt.get_last_error());
//            returnMap.put("init", clt.get_last_error());
//            if( !clt.first_add_seal_on_paper( KT_SEAL_ID, 0, 200, 200, true ) )
//            {
//                log.error(clt.get_last_error());
//                returnMap.put("errorMsg", clt.get_last_error());
//                returnMap.put("success", false);
//                clt.finally_free();
//            }
//            int nRet= clt.final_convert_document_ex(param1 , null, null, Boolean.parseBoolean(param2), Boolean.parseBoolean(param3), param4 );
//            if( 0 != nRet )
//            {
//                log.error(nRet + "[" + clt.get_last_error() + "]");
//                returnMap.put("errorMsg", clt.get_last_error());
//                returnMap.put("success", false);
//                clt.finally_free();
//            }
//            clt.finally_free();
//        } catch (Exception e) {
//            log.error("凯特签章错误");
//            log.error(e.getMessage(), e);
//            returnMap.put("errorMsg", "凯特签章错误");
//        }
//        return returnMap;
//    }

    /**
     * 描述:手动盖章上传页面
     *
     * @author Madison You
     * @created 2019/11/21 20:24:00
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/ktStampUploadView")
    public ModelAndView ktStampUploadView(HttpServletRequest request, HttpServletResponse response) {
        boolean isKtView = true;
        String itemCode = request.getParameter("itemCode");
        if(StringUtils.isNotEmpty(itemCode)) {
            List<Map<String,Object>> resultList = stampService.getAllByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"},new Object[]{itemCode});
            if(resultList!=null && resultList.size()>0) {
                Map<String,Object> itemInfo = resultList.get(0);
                if(itemInfo.get("SFGCJSXM")!=null) {
                    String sfgcjsxm = (String)itemInfo.get("SFGCJSXM");
                    if("1".equals(sfgcjsxm)) {
                        isKtView = false;
                    }
                }
            }
        }
        String busTableName = request.getParameter("busTableName");
        String uploadUserId = request.getParameter("uploadUserId");
        String uploadUserName = request.getParameter("uploadUserName");
        String attachKey = request.getParameter("attachKey");
        String fileId=request.getParameter("fileId");
        String signFileType=request.getParameter("signFileType");
        request.setAttribute("attachKey",attachKey);
        request.setAttribute("busTableName",busTableName);
        request.setAttribute("uploadUserId",uploadUserId);
        request.setAttribute("uploadUserName",uploadUserName);
        request.setAttribute("fileId",fileId);
        request.setAttribute("signFileType",signFileType);
        //String modelView = ktStampUploadView;//默认卡特签章页面
        String modelView = projectSignView;//默认工改新签章页面
        /*if(!isKtView) {
            modelView = projectSignView;//工改签章页面
        }*/
        return new ModelAndView(modelView);
    }

    /**
     * 描述:上传签章后的base64文件
     *
     * @author Madison You
     * @created 2019/11/21 20:24:00
     * @param
     * @return
     */
    @RequestMapping("/uploadKtStampBase64File")
    @ResponseBody
    public Map<String, Object> decodeSealStr(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String uploadUserId = request.getParameter("uploadUserId");// 上传人ID
        String uploadUserName = request.getParameter("uploadUserName");// 上传人姓名
        String busTableName = request.getParameter("busTableName");// 获取业务表名称
        String busRecordId = request.getParameter("busRecordId");// 获取业务表记录ID
        String attachKey = request.getParameter("attachKey");// 材料编码
        String FLOW_EXEID = request.getParameter("FLOW_EXEID");// 流程实例ID
        String FLOW_TASKID = request.getParameter("FLOW_TASKID");// 流程任务ID
        String FLOW_TASKNAME = request.getParameter("FLOW_TASKNAME");// 流程任务名称
        String UPLOADER_DEPID = request.getParameter("UPLOADER_DEPID");// 上传人员部门ID
        String UPLOADER_DEPNAME = request.getParameter("UPLOADER_DEPNAME");// 上传人员部门名称
        String name = request.getParameter("name");// 文件名称
        String SFHZD = "-1";// 是否回执单
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String base64Code = request.getParameter("base64Code");// 文件base64
        // formData参数
        Map<String, Object> resultMap = null;
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            String result = "";
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                String uuId = StringUtils.isNotEmpty(name) ? name : UUIDGenerator.getUUID();
                String fileName = uuId + ".edc";// 文件名称
                param = new HashMap<String, Object>();
                param.put("uploaderId", uploadUserId);// 上传人ID
                param.put("uploaderName", uploadUserName); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName);// 上传附件名
                param.put("attachKey", attachKey);// 材料编码
                param.put("busTableName", busTableName);// 业务表名
                param.put("busRecordId", busRecordId);// 业务表ID
                param.put("flowExeId", FLOW_EXEID);// 流程实例ID
                param.put("flowTaskId", FLOW_TASKID);// 流程任务ID
                param.put("flowTaskName", FLOW_TASKNAME);// 流程任务名称
                param.put("uploaderDepId", UPLOADER_DEPID);// 上传人部门ID
                param.put("uploaderDepName", UPLOADER_DEPNAME);// 上传人部门名称
                param.put("SFHZD", SFHZD);// 是否回执单
                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
                if (StringUtils.isNotEmpty(result)) {
                    resultMap = JSON.parseObject(result, Map.class);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return resultMap;
    }

    /**
     * 描述:签章管理页面
     *
     * @author Madison You
     * @created 2020/11/2 10:10:00
     * @param
     * @return
     */
    @RequestMapping(params = "stampManageView")
    public ModelAndView stampManageView(HttpServletRequest request) {
        return new ModelAndView("bsfw/stamp/stampManageView");
    }

    /**
     * 描述:签章管理数据
     *
     * @author Madison You
     * @created 2020/11/2 10:21:00
     * @param
     * @return
     */
    @RequestMapping(params = "stampManageData")
    @ResponseBody
    public void stampManageData(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = stampService.getStampManageList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:签章管理编辑界面
     *
     * @author Madison You
     * @created 2020/11/2 11:08:00
     * @param
     * @return
     */
    @RequestMapping(params = "stampManageEditView")
    public ModelAndView stampManageEditView(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  stampManage = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            stampManage = stampService.getByJdbc("T_BSFW_STAMPMANAGE",
                    new String[]{"STAMP_ID"}, new Object[]{entityId});
            if (stampManage != null) {
                String templateFileId = StringUtil.getValue(stampManage, "TEMPLATE_FILE_ID");
                if (StringUtils.isNotEmpty(templateFileId)) {
                    Map<String, Object> fileMap = stampService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                            new String[]{"FILE_ID"}, new Object[]{templateFileId});
                    if (fileMap != null) {
                        stampManage.put("TEMPLATE_FILE_NAME", fileMap.get("FILE_NAME"));
                    }
                }
            }
        } else {
            stampManage = new HashMap<String,Object>();
        }
        request.setAttribute("stampManage", stampManage);
        return new ModelAndView("bsfw/stamp/stampManageEditView");
    }

    /**
     * 描述:删除签章
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/11/2 11:03:00
     */
    @RequestMapping(params = "multiDelStamp")
    @ResponseBody
    public AjaxJson multiDelStamp(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        stampService.remove("T_BSFW_STAMPMANAGE","STAMP_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 签章管理信息", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }

    /**
     * 描述:保存签章信息
     *
     * @author Madison You
     * @created 2020/11/2 11:18:00
     * @param
     * @return
     */
    @RequestMapping(params = "saveOrUpdateStamp")
    @ResponseBody
    public AjaxJson saveOrUpdateStamp(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajaxJson = new AjaxJson();
        String entityId = request.getParameter("STAMP_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = stampService.saveOrUpdate(variables, "T_BSFW_STAMPMANAGE", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 签章管理记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 签章管理记录",SysLogService.OPERATE_TYPE_ADD);
        }
        ajaxJson.setMsg("保存成功");
        return ajaxJson;
    }

    /**
     *描述:下载签章文件()
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/11/2 15:02:00
     */
    @RequestMapping(params="downLoadStampFile")
    @ResponseBody
    public Map<String,Object> downLoadStampFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        Map<String, Object> uploadMap = null;
        /*申报号*/
        String exeId = StringUtil.getValue(requestMap, "exeId");
        /*签章编码*/
        String stampCode = StringUtil.getValue(requestMap, "stampCode");
        if (StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(stampCode)) {
            /*获取基本信息*/
            Map<String, Object> stampMap = stampService.getByJdbc("T_BSFW_STAMPMANAGE",
                    new String[]{"STAMP_CODE"}, new Object[]{stampCode});
            FlowData flowData = new FlowData(exeId, FlowData.ALL_MAP);
            Map<String, Object> exeMap = flowData.getExeMap();
            Map<String, Object> returnMap = null;
            if (stampMap == null) {
                return returnErrMsg("签章编码填写错误");
            }
            if (exeMap == null) {
                return returnErrMsg("查无此办件");
            }
            /*获取需要签章的模板*/
            String templateGettype = StringUtil.getValue(stampMap, "TEMPLATE_FILE_GETTYPE");
            String signFileId = stampService.getSignFileId(exeMap, stampMap);
            MaterDownload materDownload = new MaterDownload(signFileId,
                    MaterDownload.MATER_DOWNLOAD);
            Map<String, Object> fileMap = materDownload.getFileMap();
            if (fileMap == null && Objects.equals(templateGettype, "upload")) {
                return returnErrMsg("此签章编码无对应的签章模板数据");
            }
            /*判断签章状态*/
            String stampStatus = StringUtil.getValue(stampMap, "STAMP_STATUS");
            if ("0".equals(stampStatus)) {
                return returnErrMsg("此签章已被禁用");
            }
            /*签章事项限制,环节限制*/
            if (!stampService.stampItemLimit(stampMap,flowData.getItemMap())) {
                return returnErrMsg("此办件的事项无法进行签章");
            } else if (!stampService.stampNodeLimit(stampMap, exeMap)) {
                return returnErrMsg("此办件当前环节无法进行签章");
            }
            if (StringUtils.isEmpty(signFileId)) {
                return returnErrMsg("此办件未上传签章附件");
            }
            /*填充模板*/
            if (Objects.equals(templateGettype, "upload")) {
                String stampInterface = StringUtil.getValue(stampMap, "STAMP_INTERFACE");
                String[] interFaceSp = stampInterface.split("\\.");
                if (interFaceSp.length == 2) {
                    String targetClassName = interFaceSp[0];
                    String targetMethod = interFaceSp[1];
                    Map<String,Map<String,Object>> args = new HashMap<>();
                    args.put("requestMap", requestMap);
                    args.put("exeMap", exeMap);
                    args.put("stampMap", stampMap);
                    args.put("busMap", flowData.getBusMap());
                    args.put("itemMap", flowData.getItemMap());
                    try{
                        Method method = AppUtil.getBean(targetClassName).getClass().getMethod(targetMethod, Map.class);
                        returnMap = (Map) method.invoke(AppUtil.getBean(targetClassName), args);
                    } catch (Exception e) {
                        log.error("下载签章文件，模板字段回填错误，申报号为：" + exeId + "，签章编码为：" + stampCode);
                        log.error(e.getMessage(), e);
                        return returnErrMsg("签章模板填充数据出错");
                    }
                }
            }
            if (returnMap != null) {
                String errMsg = StringUtil.getValue(returnMap, "errMsg");
                if (StringUtils.isNotEmpty(errMsg)) {
                    return returnErrMsg(errMsg);
                }
            }
            uploadMap = createStampFile(returnMap,materDownload,stampMap,uploadMap,response);
        }
        return uploadMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/7 10:14:00
     * @param
     * @return
     */
    @RequestMapping("/ybDownLoadStampFile")
    @ResponseBody
    public Map<String, Object> ybDownLoadStampFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        Map<String, Object> uploadMap = null;
        String stampCode = StringUtil.getValue(requestMap, "stampCode");
        Map<String, Object> stampMap = stampService.getByJdbc("T_BSFW_STAMPMANAGE",
                new String[]{"STAMP_CODE"}, new Object[]{stampCode});
        Map<String, Object> returnMap = null;
        if (stampMap == null) {
            return returnErrMsg("签章编码填写错误");
        }
        /*获取需要签章的模板*/
        String templateGettype = StringUtil.getValue(stampMap, "TEMPLATE_FILE_GETTYPE");
        String signFileId = StringUtil.getValue(stampMap, "TEMPLATE_FILE_ID");
        MaterDownload materDownload = new MaterDownload(signFileId,
                MaterDownload.MATER_DOWNLOAD);
        Map<String, Object> fileMap = materDownload.getFileMap();
        if (fileMap == null && Objects.equals(templateGettype, "upload")) {
            return returnErrMsg("此签章编码无对应的签章模板数据");
        }
        /*判断签章状态*/
        String stampStatus = StringUtil.getValue(stampMap, "STAMP_STATUS");
        if ("0".equals(stampStatus)) {
            return returnErrMsg("此签章已被禁用");
        }
        if (StringUtils.isEmpty(signFileId)) {
            return returnErrMsg("此办件未上传签章附件");
        }
        /*填充模板*/
        if (Objects.equals(templateGettype, "upload")) {
            String stampInterface = StringUtil.getValue(stampMap, "STAMP_INTERFACE");
            String[] interFaceSp = stampInterface.split("\\.");
            if (interFaceSp.length == 2) {
                String targetClassName = interFaceSp[0];
                String targetMethod = interFaceSp[1];
                Map<String,Map<String,Object>> args = new HashMap<>();
                args.put("requestMap", requestMap);
                args.put("stampMap", stampMap);
                try{
                    Method method = AppUtil.getBean(targetClassName).getClass().getMethod(targetMethod, Map.class);
                    returnMap = (Map) method.invoke(AppUtil.getBean(targetClassName), args);
                } catch (Exception e) {
                    log.error("下载医保签章文件，模板字段回填错误，签章编码为：" + stampCode);
                    log.error(e.getMessage(), e);
                    return returnErrMsg("签章模板填充数据出错");
                }
            }
        }
        if (returnMap != null) {
            String errMsg = StringUtil.getValue(returnMap, "errMsg");
            if (StringUtils.isNotEmpty(errMsg)) {
                return returnErrMsg(errMsg);
            }
        }
        uploadMap = createStampFile(returnMap,materDownload,stampMap,uploadMap,response);
        return uploadMap;
    }

    /**
     * 描述:生成签章文件
     *
     * @author Madison You
     * @created 2021/2/7 10:07:00
     * @param
     * @return
     */
    private Map<String, Object> createStampFile(Map<String, Object> returnMap, MaterDownload materDownload,
                                 Map<String, Object> stampMap, Map<String, Object> uploadMap,
                                 HttpServletResponse response) {
        String templateGettype = StringUtil.getValue(stampMap, "TEMPLATE_FILE_GETTYPE");
        String stampCode = StringUtil.getValue(stampMap, "STAMP_CODE");
        log.info("returnMap信息："+returnMap);
        System.out.println("returnMap信息："+returnMap);
        if (returnMap != null || Objects.equals(templateGettype,"mater")) {
            String fileName = materDownload.getFileName();
            String fileType = materDownload.getFileType();
            String oldWordPath = materDownload.getOldWordPath();
            String newWordPath = materDownload.getNewWordPath();
            String newPdfPath = materDownload.getNewPdfPath();
            String newStampFilePath = materDownload.getNewStampFilePath();
            try{
                /*生成pdf文档*/
                if (StringUtils.isNotEmpty(fileType) && (fileType.equals("xls") || fileType.equals("xlsx"))) {
                    /*生成excel文档*/
                    if (Objects.equals(templateGettype, "upload")) {
                        WordReplaceUtil.replaceExcel(returnMap, oldWordPath, newWordPath);
                    } else {
                        MaterDownloadUtil.saveFileToDisk(oldWordPath, newWordPath);
                    }
                    WordToPdfUtil.excel2Pdf(newWordPath, newPdfPath);
                } else if (StringUtils.isNotEmpty(fileType) && (fileType.equals("doc") || fileType.equals("docx"))) {
                    /*生成word文档*/
                    if (Objects.equals(templateGettype, "upload")) {
                        WordReplaceUtil.replaceWord(returnMap, oldWordPath, newWordPath);
                    } else {
                        MaterDownloadUtil.saveFileToDisk(oldWordPath, newWordPath);
                    }
                    WordToPdfUtil.word2pdf(newWordPath, newPdfPath);
                }
                String stampIssign = StringUtil.getValue(stampMap, "STAMP_ISSIGN");
                if (!Objects.equals("0", stampIssign)) {
                    stampService.ktAutoStamp(stampMap, newPdfPath, newStampFilePath);
                } else {
                    newStampFilePath = newPdfPath;
                }
                /*判断文件获取方式*/
                String fileGetType = StringUtil.getValue(stampMap, "STAMP_FILEGETTYPE");
                if (Objects.equals("interface", fileGetType)) {
                    uploadMap = MaterDownloadUtil.uploadFileToServer(newStampFilePath, fileName);
                } else {
                    MaterDownloadUtil.downLoadFile(response, newStampFilePath, fileName);
                }
            } catch (Exception e) {
                log.error("生成签章文件错误，签章编码为：" + stampCode);
                log.error(e.getMessage(), e);
            }
        }
        return uploadMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/5 10:10:00
     * @param
     * @return
     */
    private Map<String, Object> returnErrMsg(String errMsg) {
        Map<String,Object> errMap = new HashMap<>();
        errMap.put("errMsg", errMsg);
        return errMap;
    }

}
