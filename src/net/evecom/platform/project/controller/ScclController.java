/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

import com.alibaba.fastjson.JSON;

import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.util.GCJSWordUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.project.service.ProjectFinishManageService;
import net.evecom.platform.project.service.ProjectSgxkbgService;
import net.evecom.platform.project.service.ProjectSgxkfqzblService;
import net.evecom.platform.project.service.ScclService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.website.service.XFDesignService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述 生成材料Controller
 *
 * 描述
 * 
 * @author Adrian Bian
 * @created 2020年1月6日 下午12:14:49
 */
@Controller
@RequestMapping("/scclController")
public class ScclController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ScclController.class);
    /**
     * 引入Service
     */
    @Resource
    private ScclService scclService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectSgxkbgService projectSgxkbgService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectSgxkfqzblService projectSgxkfqzblService;
    /**
     * xfDesignService
     */
    @Resource
    private XFDesignService xfDesignService;
    
    /**
     * 引入Service
     */
    @Resource
    private ProjectFinishManageService projectFinishManageService;    

    /**
     * 跳转到信息页面
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "scclFiles")
    public ModelAndView scclFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        // List bjclList = null ;
        Map<String, Object> flowExe = null;
        String exeTable = "JBPM6_EXECUTION";
        flowExe = executionService.getByJdbc(exeTable, new String[] { "EXE_ID" }, new Object[] { exeId });

        // 材料名称
        // List<String> MatersName = new ArrayList<>();
        String matersName = "";
        if ("T_BSFW_GCJSSGXK".equals(flowExe.get("BUS_TABLENAME"))) {
            matersName = "福建省房建和市政基础设施工程施工许可申请表.docx";
        } else if ("TB_PROJECT_FINISH_MANAGE".equals(flowExe.get("BUS_TABLENAME"))) {
            matersName = "福建省房屋建筑和市政基础设施工程竣工验收备案表.docx";
        } else if ("TB_FC_PROJECT_INFO".equals(flowExe.get("BUS_TABLENAME"))) {
            matersName = "建设工程消防设计审查申报表.docx";
        } else if ("T_BSFW_GCJSXFYS".equals(flowExe.get("BUS_TABLENAME"))) {
            matersName = "建设工程消防验收申报表.docx";
        }
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        request.setAttribute("APPLYFORMFILEFONT", StringUtil.getValue(busRecord, "APPLYFORMFILEFONT"));
        request.setAttribute("matersName", matersName);
        request.setAttribute("exeId", exeId);
        request.setAttribute("busTableName", flowExe.get("BUS_TABLENAME"));

        // 消防设计流程生成材料
        String tableName = (String) flowExe.get("BUS_TABLENAME") + "_SCWS";
        List<Map<String, Object>> xfsjMap = fileAttachService.findByList(tableName,
                (String) flowExe.get("BUS_RECORDID"), "SCWS");
        request.setAttribute("xfsjMap", xfsjMap);

        return new ModelAndView("project/projectSccl/projectSccl");
    }

    /**
     * 下载
     */
    @RequestMapping("/downLoadSGXKMater")
    public void downLoadSGXKMater(HttpServletRequest request, HttpServletResponse response) {
        String templateName = "T_BSFW_GCJSSGXK";
        downLoadMater(request, response, templateName);
    }

    /**
     * 下载
     */
    @RequestMapping("/downLoadJGYS1Mater")
    public void downLoadJGYS1Mater(HttpServletRequest request, HttpServletResponse response) {
        String templateName = "TB_PROJECT_FINISH_MANAGE1";
        downLoadMater(request, response, templateName);
    }

    /**
     * 下载
     */
    @RequestMapping("/downLoadJGYS2Mater")
    public void downLoadJGYS2Mater(HttpServletRequest request, HttpServletResponse response) {
        String templateName = "TB_PROJECT_FINISH_MANAGE2";
        downLoadMater(request, response, templateName);
    }

    /**
     * 下载
     */
    @RequestMapping("/downLoadXFSJMater")
    public void downLoadXFSJMater(HttpServletRequest request, HttpServletResponse response) {
        String templateName = "TB_FC_PROJECT_INFO";
        downLoadMater(request, response, templateName);
    }

    /**
     * 下载
     */
    @RequestMapping("/downLoadXFYS1Mater")
    public void downLoadXFYS1Mater(HttpServletRequest request, HttpServletResponse response) {
        String templateName = "T_BSFW_GCJSXFYS1";
        downLoadMater(request, response, templateName);
    }

    /**
     * 下载
     */
    @RequestMapping("/downLoadXFYS2Mater")
    public void downLoadXFYS2Mater(HttpServletRequest request, HttpServletResponse response) {
        String templateName = "T_BSFW_GCJSXFYS2";
        downLoadMater(request, response, templateName);
    }

    /**
     *
     * 描述 下载材料
     * 
     * @author Danto Huang
     * @created 2017年9月19日 下午3:43:04
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    private void downLoadMater(HttpServletRequest request, HttpServletResponse response, String templateName) {
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        // 文件服务器路径
        String uploadFilePath = properties.getProperty("uploadFileUrl").replace("\\", "/");
        // 模板路径
        String tplFilePath = uploadFilePath + "attachFiles/sccl/template/";

        // 生成到本台服务器的路径
        String generatePathFloder = "attachFiles/sccl";
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File pdfFullPathFolder = new File(pdfFile + generatePathFloder);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }

        String exeId = request.getParameter("exeId");
        String fileName = request.getParameter("fileName");
        Map<String, Object> flowExe = null;
        String exeTable = "JBPM6_EXECUTION";
        flowExe = executionService.getByJdbc(exeTable, new String[] { "EXE_ID" }, new Object[] { exeId });

        // 获取业务表名称
        String busTableName = (String) flowExe.get("BUS_TABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowExe.get("BUS_RECORDID");

        tplFilePath = tplFilePath + templateName + ".docx";
        String clName = "/" + busRecordId + ".docx";
        String uploadFullPath = pdfFullPathFolder + clName;
        
        List<Map<String, Object>> fileList = fileAttachService.findByList(busTableName + "_SCCL", busRecordId, "SCCL");
        // if(fileList != null && fileList.size()>0){
        if (fileList.size() > 1000) {
            String filePath = (String) fileList.get(0).get("FILE_PATH");
            downLoadFile(response, attachFileFolderPath, filePath, uploadFullPath);
        } else {
            String primKey = (String) scclService.getPrimaryKeyName(busTableName).get(0);
            Map<String, Object> datas = scclService.getByJdbc(busTableName, new String[] { primKey },
                    new Object[] { busRecordId });
            if ("TB_FC_PROJECT_INFO".equals(busTableName)) {
                initTbFcProjectInfo(datas);
            } else if("T_BSFW_GCJSSGXK".equals(busTableName)){
                initDicToKeyAndCode(datas, "JSDW_JSON", "TBIDCARDTYPEDIC");
                initDicToKeyAndCode(datas, "DJDW_JSON", "TBIDCARDTYPEDIC");
                initDicToKeyAndCode(datas, "SGDW_JSON", "TBIDCARDTYPEDIC");
                initDicToKeyAndCode(datas, "KCDW_JSON", "TBIDCARDTYPEDIC");
                initDicToKeyAndCode(datas, "SJDW_JSON", "TBIDCARDTYPEDIC");
                initDicToKeyAndCode(datas, "JLDW_JSON", "TBIDCARDTYPEDIC");
                

                // 填充使用字典字段，便于回填
                if (datas.get("PRJFUNCTIONNUM") != null) {
                    List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBPRJFUNCTIONDIC");
                    datas.putAll(initGCJSCheckBoxValues(new String[] { (String) datas.get("PRJFUNCTIONNUM") }, dicList,
                            "PRJFUNCTIONNUM"));
                }
                // 填充使用字典字段，便于回填
                if (datas.get("STRUCTQUALTYPES") != null) {
                    List<Map<String, Object>> dicList = dictionaryService.findByTCodeAndWhere("STRUCTQUALTYPE", "1=1");
                    String STRUCTQUALTYPES =  datas.get("STRUCTQUALTYPES").toString();
                    if(StringUtils.isNotEmpty(STRUCTQUALTYPES)){
                        datas.putAll(initGCJSCheckBoxValues(STRUCTQUALTYPES.split(","), dicList,
                                "STRUCTQUALTYPES"));
                    }
                }
            }else if("TB_PROJECT_FINISH_MANAGE".equals(busTableName)) {
                Map<String, Object> exeMap = projectFinishManageService.findExeInfo(datas.get("PROJECTCODE").toString(), null);
                if (exeMap != null && exeMap.size() > 0 && exeMap.get("RUN_STATUS").toString().equals("2")) {
                    String ywid = exeMap.get("BUS_RECORDID").toString();
                    String tableName = exeMap.get("BUS_TABLENAME").toString();
                    // 获取施工许可信息
                    Map<String, Object> sgxkMap = projectFinishManageService.getByJdbc(tableName, new String[] { "YW_ID" },
                            new Object[] { ywid });
                    initDicToKeyAndCodeNew(sgxkMap, "JSDW_JSON", "TBIDCARDTYPEDIC",datas);
                    initDicToKeyAndCodeNew(sgxkMap, "DJDW_JSON", "TBIDCARDTYPEDIC",datas);
                    initDicToKeyAndCodeNew(sgxkMap, "SGDW_JSON", "TBIDCARDTYPEDIC",datas);
                    initDicToKeyAndCodeNew(sgxkMap, "KCDW_JSON", "TBIDCARDTYPEDIC",datas);
                    initDicToKeyAndCodeNew(sgxkMap, "SJDW_JSON", "TBIDCARDTYPEDIC",datas);
                    initDicToKeyAndCodeNew(sgxkMap, "JLDW_JSON", "TBIDCARDTYPEDIC",datas);

                }else {
                    String zrztJson = datas.get("CJGFZRZT_JSON")==null?"":datas.get("CJGFZRZT_JSON").toString();
                    if(StringUtils.isNotEmpty(zrztJson)){
                        //解析json
                        initZrztJson(datas,zrztJson);
                    }
                }

                // 填充使用字典字段，便于回填
                if (datas.get("PRJFUNCTIONNUM") != null) {
                    List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBPRJFUNCTIONDIC");
                    datas.putAll(initGCJSCheckBoxValues(new String[] { (String) datas.get("PRJFUNCTIONNUM") }, dicList,
                            "PRJFUNCTIONNUM"));                   
                }
                // 填充使用字典字段，便于回填
                if (datas.get("STRUCTQUALTYPES") != null) {
                    List<Map<String, Object>> dicList = dictionaryService.findByTCodeAndWhere("STRUCTQUALTYPE", "1=1");
                    String STRUCTQUALTYPES =  datas.get("STRUCTQUALTYPES").toString();
                    if(StringUtils.isNotEmpty(STRUCTQUALTYPES)){
                        datas.putAll(initGCJSCheckBoxValues(STRUCTQUALTYPES.split(","), dicList,
                                "STRUCTQUALTYPES"));
                    }
                }               
                
            }
            if(templateName.equals("TB_PROJECT_FINISH_MANAGE1")) {
                busTableName="TB_PROJECT_FINISH_MANAGE1";
            }
            if(templateName.equals("TB_PROJECT_FINISH_MANAGE2")) {
                busTableName="TB_PROJECT_FINISH_MANAGE2";
            }            
            Map<String, Object> fileInfo = GCJSWordUtil.generateWord(busTableName, tplFilePath, uploadFullPath, datas);
            if ((Boolean) fileInfo.get("generateFlag")) {
                // 保存附件信息表
                // saveFileInfo(fileInfo,clPath,busTableName,busRecordId);
                // 输出
                downLoadFile(response, attachFileFolderPath, fileName, uploadFullPath);
            }
        }
    }

    private void initTbFcProjectInfo(Map<String, Object> datas) {
        String prj_code = (String) datas.get("PRJ_CODE");
        String prj_num = "";
        if (datas.get("PRJ_NUM") != null) {
            if (StringUtils.isNotBlank((String) datas.get("PRJ_NUM"))) {
                prj_num = (String) datas.get("PRJ_NUM");
            }
        }
        //责任主体单位信息及单体建筑物信息
        List<Map<String, Object>> zrztxxList = xfDesignService.findZrztxxList(prj_code, prj_num);
        List<Map<String, Object>> dtjzwxxList = xfDesignService.findDtjzwxxList(prj_code, prj_num);
        if (zrztxxList.size() > 0) {
            datas.put("zrztxx", zrztxxList);
        }

        if(dtjzwxxList.size()>0){
            //结构类型字典
            List<Map<String, Object>> jglxDicList = dictionaryService.findByTypeCode("TBFCPRJSTRUCTURETYPEDIC");
            //耐火等级字典
            List<Map<String, Object>> nhdjDicList = dictionaryService.findByTypeCode("TBREFLEVELDIC");
            for(int i =0;i<dtjzwxxList.size();i++){
                Map<String ,Object> dtjzwxx = dtjzwxxList.get(i);
                if(dtjzwxx.get("FC_STRUCTURE_TYPE_NUM")!=null){
                    for(Map<String,Object> jglxDic : jglxDicList){
                        if(jglxDic.get("DIC_CODE").equals(dtjzwxx.get("FC_STRUCTURE_TYPE_NUM"))){
                            dtjzwxx.put("FC_STRUCTURE_TYPE_NUM",jglxDic.get("DIC_NAME"));
                        }
                    }
                }
                if(dtjzwxx.get("REFRACTORY_LEVEL_NUM")!=null){
                    for(Map<String,Object> ngdjDic : nhdjDicList){
                        if(ngdjDic.get("DIC_CODE").equals(dtjzwxx.get("REFRACTORY_LEVEL_NUM"))){
                            dtjzwxx.put("REFRACTORY_LEVEL_NUM",ngdjDic.get("DIC_NAME"));
                        }
                    }
                }

            }
            datas.put("dtjzwxx",dtjzwxxList);
        }
        // 填充使用字典字段，便于回填
        if (datas.get("FC_PRJ_TYPE") != null) {
            List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBFCPRJTYPEDIC");
            datas.putAll(initGCJSCheckBoxValues(new String[] { (String) datas.get("FC_PRJ_TYPE") }, dicList,
                    "FC_PRJ_TYPE"));
        }
        //没有用的字典的写死字段——————消防使用性质
        if(datas.get("FC_CHARACTER")!=null){
            try{
                datas.put("FC_CHARACTER"+(String)datas.get("FC_CHARACTER"),WordReplaceUtil.getCTSym("Wingdings 2", "F052"));
            }catch (Exception e){
                log.error(e);
            }

        }
        //填充使用字典字段，便于回填————设置形式
        if(datas.get("SET_TYPE")!=null){
            List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBFCSETTYPEDIC");
            datas.putAll(initGCJSCheckBoxValues(new String[]{(String)datas.get("SET_TYPE")},dicList,
                    "SET_TYPE"));
        }
        //填充使用字典字段，便于回填————存储形式
        if(datas.get("STORAGE_TYPE")!=null){
            List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBFCSTORAGETYPEDIC");
            datas.putAll(initGCJSCheckBoxValues(new String[]{(String)datas.get("STORAGE_TYPE")},dicList,
                    "STORAGE_TYPE"));
        }

        //填充使用字典字段，便于回填————材料类别
        if(datas.get("MATERIAL_CATEGORY")!=null){
            List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBFCMCDIC");
            datas.putAll(initGCJSCheckBoxValues(new String[]{(String)datas.get("MATERIAL_CATEGORY")},dicList,
                    "MATERIAL_CATEGORY"));
        }
        //填充使用字典字段，便于回填————装修部位
        if(datas.get("DECORATION_PART")!=null){
            List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBFCDPDIC");
            datas.putAll(initGCJSCheckBoxValues(new String[]{(String)datas.get("DECORATION_PART")},dicList,
                    "DECORATION_PART"));
        }
        //填充使用字典字段，便于回填————消防设施
        if(datas.get("FC_FACILITIES")!=null){
            List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("TBFCFACDIC");
            datas.putAll(initGCJSCheckBoxValues(new String[]{(String)datas.get("FC_FACILITIES")},dicList,
                    "FC_FACILITIES"));
        }
    }

    @SuppressWarnings("unchecked")
    private void initDicToKeyAndCode(Map<String, Object> datas, String key, String typeCode) {
        String json  = datas.get(key)==null?"":datas.get(key).toString();
        if(StringUtils.isNotEmpty(json)){
            List<Map<String, Object>> valueList = JSON.parseObject(json, List.class);
            for (Map<String, Object> map : valueList) {
                String IDCARDTYPENUM = map.get("IDCARDTYPENUM")==null?"":map.get("IDCARDTYPENUM").toString();
                if(StringUtils.isNotEmpty(IDCARDTYPENUM)){
                    map.put("IDCARDTYPENUM", dictionaryService.findByDicCodeAndTypeCode(IDCARDTYPENUM, typeCode));
                }
            }
            datas.put(key, JSON.toJSONString(valueList));
        }
    }

    @SuppressWarnings("unchecked")
    private void initDicToKeyAndCodeNew(Map<String, Object> datas, String key, String typeCode,Map<String, Object> data) {
        String json  = datas.get(key)==null?"":datas.get(key).toString();
        if(StringUtils.isNotEmpty(json)){
            List<Map<String, Object>> valueList = JSON.parseObject(json, List.class);
            for (Map<String, Object> map : valueList) {
                String IDCARDTYPENUM = map.get("IDCARDTYPENUM")==null?"":map.get("IDCARDTYPENUM").toString();
                if(StringUtils.isNotEmpty(IDCARDTYPENUM)){
                    map.put("IDCARDTYPENUM", dictionaryService.findByDicCodeAndTypeCode(IDCARDTYPENUM, typeCode));
                }
            }
            data.put(key, JSON.toJSONString(valueList));
        }
    }    
    
    @SuppressWarnings("unchecked")
    private void initZrztJson(Map<String, Object> datas, String zrztJson) {
        List<Map<String, Object>> JSDW_JSON= new ArrayList<Map<String, Object>>();
        //List<Map<String, Object>> DJDW_JSON=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> SGDW_JSON=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> KCDW_JSON=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> SJDW_JSON=new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> JLDW_JSON=new ArrayList<Map<String, Object>>();
        if(StringUtils.isNotEmpty(zrztJson)){
            List<Map<String, Object>> valueList = JSON.parseObject(zrztJson, List.class);
            for (int i=0;i<valueList.size();i++) {
                String cjgfzrztName = valueList.get(i).get("cjgfzrztName")==null?"":valueList.get(i).get("cjgfzrztName").toString();
                if(cjgfzrztName.equals("建设单位")){
                    JSDW_JSON.add(valueList.get(i));
                }
                /*
                 * if(cjgfzrztName.equals("代建企业")){ DJDW_JSON.add(valueList.get(i)); }
                 */
                if(cjgfzrztName.equals("施工企业")){
                    SGDW_JSON.add(valueList.get(i));
                }
                if(cjgfzrztName.equals("勘察企业")){
                    KCDW_JSON.add(valueList.get(i));
                }
                if(cjgfzrztName.equals("设计企业")){
                    SJDW_JSON.add(valueList.get(i));
                }
                if(cjgfzrztName.equals("监理企业")){
                    JLDW_JSON.add(valueList.get(i));
                }                
            }
        }
        if(JSDW_JSON!=null&& JSDW_JSON.size()>0) {
            datas.put("JSDW_JSON", JSON.toJSONString(JSDW_JSON));
        }
        if(SGDW_JSON!=null&& SGDW_JSON.size()>0) {
            datas.put("SGDW_JSON", JSON.toJSONString(SGDW_JSON));
        }
        if(KCDW_JSON!=null&& KCDW_JSON.size()>0) {
            datas.put("KCDW_JSON",JSON.toJSONString(KCDW_JSON));
        }
        if(SJDW_JSON!=null&& SJDW_JSON.size()>0) {
            datas.put("SJDW_JSON", JSON.toJSONString(SJDW_JSON));
        }
        if(JLDW_JSON!=null&& JLDW_JSON.size()>0) {
            datas.put("JLDW_JSON",JSON.toJSONString(JLDW_JSON));
        }
    }    
    
    
    
    
    /**
     *
     * @param fileInfo
     * @param uploadFullPath
     * @param busTableName
     * @param busRecordId
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private void saveFileInfo(Map<String, Object> fileInfo, String uploadFullPath, String busTableName,
            String busRecordId) {
        Long fileLength = (Long) fileInfo.get("fileLength");
        String fileSize = FileUtil.getFormatFileSize(fileLength);
        // 插入附件表
        Map<String, Object> fileAttach = new HashMap<>();
        fileAttach.put("FILE_NAME", "福建省房建和市政基础设施工程施工许可申请表.docx");
        fileAttach.put("FILE_PATH", uploadFullPath);
        fileAttach.put("FILE_TYPE", "docx");
        // fileAttach.put("UPLOADER_ID", AppUtil.getLoginUser().getUserId());
        // fileAttach.put("UPLOADER_NAME",
        // AppUtil.getLoginUser().getUsername());
        fileAttach.put("FILE_LENGTH", fileLength);
        fileAttach.put("FILE_SIZE", fileSize);
        fileAttach.put("BUS_TABLENAME", busTableName + "_SCCL");
        fileAttach.put("BUS_TABLERECORDID", busRecordId);
        fileAttach.put("ATTACH_KEY", "SCCL");
        // fileAttach.put("FLOW_EXEID", flowDatas.get("EFLOW_EXEID"));
        // fileAttach.put("FLOW_TASKID",flowDatas.get("EFLOW_TASKID"));
        // fileAttach.put("FLOW_TASKNAME",flowDatas.get("EFLOW_TASKNAME"));
        fileAttach.put("PLAT_TYPE", 0);
        fileAttachService.saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
    }

    /**
     *
     * 描述 下载文件
     *
     * @author Adrian Bian
     * @created 2020年1月30日 下午2:11:47
     * @param response
     * @param attachFileFolderPath
     * @param fileName
     * @param uploadFullPath
     */
    private void downLoadFile(HttpServletResponse response, String attachFileFolderPath, String fileName,
            String uploadFullPath) {
        OutputStream toClient = null;
        InputStream fis = null;
        try {
            String newFileName =null;
            if(StringUtils.isNotEmpty(fileName)){
                newFileName = fileName + "."
                        + uploadFullPath.substring(uploadFullPath.lastIndexOf(".") + 1);
            }else{
                newFileName =  new Date().getTime() + "."
                        + uploadFullPath.substring(uploadFullPath.lastIndexOf(".") + 1);
            }
            // path是指欲下载的文件的路径。
            // File file = new File(attachFileFolderPath + uploadFullPath);
            File file = new File(uploadFullPath);

            // 以流的形式下载文件。
            // fis = new BufferedInputStream(new
            // FileInputStream(attachFileFolderPath + uploadFullPath));
            fis = new BufferedInputStream(new FileInputStream(uploadFullPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition",
                    "attachment;filename=\"" + new String(newFileName.getBytes("gbk"), "iso8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-msdownload");

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.info("取消下载或下载异常");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
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
    @RequestMapping("/printTemplate")
    public ModelAndView printTemplate(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String exeId = request.getParameter("exeId");
        //String typeId = request.getParameter("typeId");
        //String templatePath = request.getParameter("TemplatePath");
        //String templateName = request.getParameter("TemplateName");
        templateData.put("TemplatePath", "T_BSFW_GCJSSGXk.xls");
        templateData.put("TemplateName", "施工许可");
        // templateData.put("TemplatePath", templatePath);
        // templateData.put("TemplateName", templateName);
        // 获取工程建设项目数据
        getGCJSData(templateData, exeId);
        request.setAttribute("regTable", (Map<String, Object>) templateData.get("regTable"));
        templateData.remove("regTable");

        for (Map.Entry<String, Object> entry : templateData.entrySet()) {
            String valString = entry.getValue() == null ? "" : entry.getValue().toString();
            valString = replaceBlank(valString);
            templateData.put(entry.getKey(), valString);
        }
        request.setAttribute("isSignButton", request.getParameter("isSignButton"));
        request.setAttribute("TemplateData", templateData);
        request.setAttribute("exeId", exeId);

        return new ModelAndView("wsbs/readtemplate/printGCJSTemplate");
    }

    /**
     * 跳转到信息页面
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/printTemplateBg")
    public ModelAndView printTemplateBg(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String exeId = request.getParameter("exeId");
        templateData.put("TemplatePath", "T_BSFW_GCJSSGXk.xls");
        templateData.put("TemplateName", "施工许可");

        Map<String, Object> sgxkbg = projectSgxkbgService.findSgxkbg(exeId);
        String CONSTRNUM = sgxkbg.get("CONSTRNUM") == null ? "" : sgxkbg.get("CONSTRNUM").toString();
        Map<String, Object> sgxk = projectSgxkbgService.findSgxkToConstrNum(CONSTRNUM);

        //获取工程建设项目数据
        getGCJSData(templateData, sgxk.get("EXE_ID").toString());

        request.setAttribute("regTable", (Map<String, Object>)templateData.get("regTable"));
        templateData.remove("regTable");

        for (Map.Entry<String, Object> entry : templateData.entrySet()) {
            String valString = entry.getValue() == null ? "" : entry.getValue().toString();
            valString = replaceBlank(valString);
            templateData.put(entry.getKey(), valString);
        }
        // 加载施工许可变更信息
        initSgxkbgData(templateData, sgxkbg);

        request.setAttribute("isSignButton", request.getParameter("isSignButton"));
        request.setAttribute("TemplateData", templateData);
        request.setAttribute("exeId", exeId);

        return new ModelAndView("wsbs/readtemplate/printGCJSTemplate");
    }

    /**
     *
     * 描述：初始化施工许可证变更信息
     *
     * @author Rider Chen
     * @created 2020年5月29日 下午3:32:12
     * @param templateData
     * @param sgxkbg
     */
    private void initSgxkbgData(Map<String, Object> templateData, Map<String, Object> sgxkbg) {
        StringBuffer beforeValue = new StringBuffer("变更前：");
        StringBuffer afterValue = new StringBuffer("变更后：");
        int CHANGEITEM = sgxkbg.get("CHANGEITEM") == null ? 0 : Integer.parseInt(sgxkbg.get("CHANGEITEM").toString());
        int INDEXNUM = sgxkbg.get("INDEXNUM") == null ? 0 : Integer.parseInt(sgxkbg.get("INDEXNUM").toString());
        String PERSONNAME_AFTER = sgxkbg.get("PERSONNAME_AFTER") == null ? ""
                : sgxkbg.get("PERSONNAME_AFTER").toString();
        if (CHANGEITEM == 1) {
            if (StringUtils.isNotEmpty(PERSONNAME_AFTER)) {
                String SGDW_JSON_PERSONNAME = templateData.get("SGDW_JSON_PERSONNAME") == null ? ""
                        : templateData.get("SGDW_JSON_PERSONNAME").toString();
                String[] names = SGDW_JSON_PERSONNAME.split(",");
                StringBuffer PERSONNAME = new StringBuffer();
                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    if (i == INDEXNUM) {
                        PERSONNAME.append(PERSONNAME_AFTER).append(",");
                    } else {
                        PERSONNAME.append(name).append(",");
                    }
                }
                if (StringUtils.isNotEmpty(PERSONNAME)) {
                    PERSONNAME = PERSONNAME.deleteCharAt(PERSONNAME.lastIndexOf(","));
                }
                beforeValue.append(SGDW_JSON_PERSONNAME).append("/");
                afterValue.append(PERSONNAME).append("/");
                templateData.put("SGDW_JSON_PERSONNAME", PERSONNAME.toString());
            }
        } else if (CHANGEITEM == 2) {
            if (StringUtils.isNotEmpty(PERSONNAME_AFTER)) {
                String JLDW_JSON_PERSONNAME = templateData.get("JLDW_JSON_PERSONNAME") == null ? ""
                        : templateData.get("JLDW_JSON_PERSONNAME").toString();
                String[] names = JLDW_JSON_PERSONNAME.split(",");
                StringBuffer PERSONNAME = new StringBuffer();
                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    if (i == INDEXNUM) {
                        PERSONNAME.append(PERSONNAME_AFTER).append(",");
                    } else {
                        PERSONNAME.append(name).append(",");
                    }
                }
                if (StringUtils.isNotEmpty(PERSONNAME)) {
                    PERSONNAME = PERSONNAME.deleteCharAt(PERSONNAME.lastIndexOf(","));
                }
                beforeValue.append(JLDW_JSON_PERSONNAME).append("/");
                afterValue.append(PERSONNAME).append("/");
                templateData.put("JLDW_JSON_PERSONNAME", PERSONNAME.toString());
            }
        } else {
            String PROJECT_NAME_AFTER = sgxkbg.get("PROJECT_NAME_AFTER") == null ? ""
                    : sgxkbg.get("PROJECT_NAME_AFTER").toString();
            if (StringUtils.isNotEmpty(PROJECT_NAME_AFTER)) {
                beforeValue.append(templateData.get("PROJECT_NAME")).append("/");
                afterValue.append(PROJECT_NAME_AFTER).append("/");
                templateData.put("PROJECT_NAME", PROJECT_NAME_AFTER);
            }
            String PROADDRESS_AFTER = sgxkbg.get("PROADDRESS_AFTER") == null ? ""
                    : sgxkbg.get("PROADDRESS_AFTER").toString();
            if (StringUtils.isNotEmpty(PROADDRESS_AFTER)) {
                beforeValue.append(templateData.get("PROADDRESS")).append("/");
                afterValue.append(PROADDRESS_AFTER).append("/");
                templateData.put("PROADDRESS", PROADDRESS_AFTER);
            }
            String PRJSIZE_AFTER = sgxkbg.get("PRJSIZE_AFTER") == null ? "" : sgxkbg.get("PRJSIZE_AFTER").toString();
            if (StringUtils.isNotEmpty(PRJSIZE_AFTER)) {
                beforeValue.append(templateData.get("PRJSIZE")).append("/");
                afterValue.append(PRJSIZE_AFTER).append("/");
                templateData.put("PRJSIZE", PRJSIZE_AFTER);
            }
            String INVEST_AFTER = sgxkbg.get("INVEST_AFTER") == null ? "" : sgxkbg.get("INVEST_AFTER").toString();
            if (StringUtils.isNotEmpty(INVEST_AFTER)) {
                beforeValue.append(templateData.get("INVEST")).append("/");
                afterValue.append(INVEST_AFTER).append("/");
                templateData.put("INVEST", INVEST_AFTER);
            }
            String WORKDAYS_AFTER = sgxkbg.get("WORKDAYS_AFTER") == null ? "" : sgxkbg.get("WORKDAYS_AFTER").toString();
            if (StringUtils.isNotEmpty(WORKDAYS_AFTER)) {
                if (!WORKDAYS_AFTER.contains("天")) {
                    WORKDAYS_AFTER = WORKDAYS_AFTER + "天";
                }
                beforeValue.append(templateData.get("WORKDAYS")).append("/");
                afterValue.append(WORKDAYS_AFTER).append("/");
                templateData.put("WORKDAYS", WORKDAYS_AFTER);
            }
        }
        if (StringUtils.isNotEmpty(beforeValue) && StringUtils.isNotEmpty(afterValue)) {
            String APPLYUNITREMARK = templateData.get("APPLYUNITREMARK") == null ? ""
                    : templateData.get("APPLYUNITREMARK").toString();
            templateData.put("APPLYUNITREMARK",
                    APPLYUNITREMARK + "\n" + beforeValue.deleteCharAt(beforeValue.lastIndexOf("/")) + "\n"
                            + afterValue.deleteCharAt(afterValue.lastIndexOf("/")));
        }
    }

    /**
     * 跳转到信息页面 施工许可废止再办理
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/printTemplateFqzbl")
    public ModelAndView printTemplateFqzbl(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String exeId = request.getParameter("exeId");
        templateData.put("TemplatePath", "T_BSFW_GCJSSGXk.xls");
        templateData.put("TemplateName", "施工许可");

        Map<String, Object> sgxkfqzbl = projectSgxkfqzblService.findSgxkfqzbl(exeId);
        String CONSTRNUM = sgxkfqzbl.get("CONSTRNUM") == null ? "" : sgxkfqzbl.get("CONSTRNUM").toString();
        Map<String, Object> sgxk = projectSgxkbgService.findSgxkToConstrNum(CONSTRNUM);

        // 获取工程建设项目数据
        getGCJSData(templateData, sgxk.get("EXE_ID").toString());


        request.setAttribute("regTable", (Map<String, Object>) templateData.get("regTable"));
        templateData.remove("regTable");

        for (Map.Entry<String, Object> entry : templateData.entrySet()) {
            String valString = entry.getValue() == null ? "" : entry.getValue().toString();
            valString = replaceBlank(valString);
            templateData.put(entry.getKey(), valString);
        }
        // 加载施工许可变更信息
        initSgxkfqzblData(templateData, sgxkfqzbl);

        request.setAttribute("isSignButton", request.getParameter("isSignButton"));
        request.setAttribute("TemplateData", templateData);
        request.setAttribute("exeId", exeId);

        return new ModelAndView("wsbs/readtemplate/printGCJSTemplate");
    }

    /**
     * 
     * 描述：初始化施工许可证变更信息
     * 
     * @author Rider Chen
     * @created 2020年5月29日 下午3:32:12
     * @param templateData
     * @param sgxkbg
     */
    private void initSgxkfqzblData(Map<String, Object> templateData, Map<String, Object> info) {

        StringBuffer beforeValue = new StringBuffer("变更前：");
        StringBuffer afterValue = new StringBuffer("变更后：");

        String JSDW_JSON_AFTER = info.get("JSDW_JSON_AFTER") == null ? "" : info.get("JSDW_JSON_AFTER").toString();
        String SGDW_JSON_AFTER = info.get("SGDW_JSON_AFTER") == null ? "" : info.get("SGDW_JSON_AFTER").toString();
        String JLDW_JSON_AFTER = info.get("JLDW_JSON_AFTER") == null ? "" : info.get("JLDW_JSON_AFTER").toString();

        handleGCJSJsonField(templateData, "JSDW_JSON", JSDW_JSON_AFTER,beforeValue,afterValue);
        handleGCJSJsonField(templateData, "SGDW_JSON", SGDW_JSON_AFTER,beforeValue,afterValue);
        handleGCJSJsonField(templateData, "JLDW_JSON", JLDW_JSON_AFTER,beforeValue,afterValue);

       /* if (StringUtils.isNotEmpty(beforeValue) && StringUtils.isNotEmpty(afterValue)) {
            String APPLYUNITREMARK = templateData.get("APPLYUNITREMARK") == null ? ""
                    : templateData.get("APPLYUNITREMARK").toString();
            templateData.put("APPLYUNITREMARK",
                    APPLYUNITREMARK + "\n" + beforeValue.deleteCharAt(beforeValue.lastIndexOf("/")) + "\n"
                            + afterValue.deleteCharAt(afterValue.lastIndexOf("/")));
        }*/
    }

    /**
     * 处理工程建设项目json字段并填充模板字段
     * 
     * @param templateData
     * @param jsonField
     * @param jsonFieldValue
     */
    @SuppressWarnings("unchecked")
    private void handleGCJSJsonField(Map<String, Object> templateData, String jsonField, String jsonFieldValue,
            StringBuffer beforeValue, StringBuffer afterValue) {
        if (StringUtils.isNotEmpty(jsonFieldValue)) {
            List<Map<String, Object>> valueList = JSON.parseObject(jsonFieldValue, List.class);
            StringBuffer valSb;
            if (valueList.size() > 0) {
                Map<String, Object> valueMap = valueList.get(0);
                for (Object key : valueMap.keySet()) {
                    valSb = new StringBuffer("");
                    // 同一属性逗号拼接其属性值
                    for (int i = 0; i < valueList.size(); i++) {
                        Map<String, Object> map = valueList.get(i);
                        String value = map.get(key).toString();
                        if (StringUtils.isNotBlank(value)) {
                            valSb.append(value).append(",");
                        }
                    }
                    if (valSb.length() > 0) {
                        valSb.deleteCharAt(valSb.lastIndexOf(","));
                    }
                    StringBuffer fieldName = new StringBuffer(jsonField).append("_").append(key);
                    templateData.put(fieldName.toString(), valSb.toString());
                }
            }
        } else {
            templateData.put(jsonField, "");
        }
    }
    /**
     * 获取工程建设项目相关信息填充进templateData
     * 
     * @param templateData
     * @param exeId
     */
    @SuppressWarnings("unchecked")
    private void getGCJSData(Map<String, Object> templateData, String exeId) {

        Map<String, Object> execution = null;
        if (StringUtils.isNotBlank(exeId) && !exeId.equals("undefined")) {
            execution = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if (execution == null) {
                execution = this.executionService.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] { exeId });
            }
            if (execution != null) {
                String busTableName = StringUtil.getValue(execution, "BUS_TABLENAME");
                String busRecordId = StringUtil.getValue(execution, "BUS_RECORDID");
                Map<String, Object> projectInfo = this.scclService.getByJdbc(busTableName, new String[] { "YW_ID" },
                        new Object[] { busRecordId });
                StringBuffer sb;
                String value;
                for (Map.Entry<String, Object> entry : projectInfo.entrySet()) {
                    sb = new StringBuffer("");
                    sb.append(entry.getKey());
                    // 数据空值处理 null->空字符串
                    value = entry.getValue() != null ? entry.getValue().toString() : "";
                    if (entry.getKey().indexOf("_JSON") > 0) {
                        handleGCJSJsonField(templateData, entry.getKey(), value);
                        templateData.put(entry.getKey(), entry.getValue());
                        continue;
                    }
                    templateData.put(sb.toString(), value);

                }
            }
        }
        templateData.put("APPLYUNITREMARK","");//审核意见不在模板上显示
    }

    /**
     * 处理工程建设项目json字段并填充模板字段
     * 
     * @param templateData
     * @param jsonField
     * @param jsonFieldValue
     */
    @SuppressWarnings("unchecked")
    private void handleGCJSJsonField(Map<String, Object> templateData, String jsonField, String jsonFieldValue) {
        if (StringUtils.isNotEmpty(jsonFieldValue)) {
            List<Map<String, Object>> valueList = JSON.parseObject(jsonFieldValue, List.class);
            StringBuffer valSb;
            if (valueList.size() > 0) {
                Map<String, Object> valueMap = valueList.get(0);
                for (Object key : valueMap.keySet()) {

                    valSb = new StringBuffer("");
                    // 同一属性逗号拼接其属性值
                    for (int i = 0; i < valueList.size(); i++) {
                        Map<String, Object> map = valueList.get(i);
                        String value = map.get(key).toString();
                        if (StringUtils.isNotBlank(value)) {
                            valSb.append(value).append(",");
                        }
                    }
                    if (valSb.length() > 0) {
                        valSb.deleteCharAt(valSb.lastIndexOf(","));
                    }
                    StringBuffer fieldName = new StringBuffer(jsonField).append("_").append(key);
                    templateData.put(fieldName.toString(), valSb);
                }
            }
        } else {
            templateData.put(jsonField, "");
        }
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
     * 根据传入的字典数组和选中数组初始化checkbox 使用word的特殊字段代替复选框
     *
     * @author Adrian Bian
     * @created 2019-12-17 下午4:13:52
     * @param checkValues
     * @param dicMap
     * @param preFix
     * @return
     * @throws Exception
     */
    private static Map<String, Object> initGCJSCheckBoxValues(String[] checkValues, List<Map<String, Object>> dicMap,
            String preFix) {
        Map<String, Object> resultMap = new HashMap<String, Object>(dicMap.size());
        for (int i = 0, max = dicMap.size(); i < max; i++) {
            try {
                String dicValue = (String) dicMap.get(i).get("DIC_CODE");
                StringBuffer templateKey = new StringBuffer("");
                if(preFix.equals("STRUCTQUALTYPES")){
                    String DIC_SN = dicMap.get(i).get("DIC_SN").toString();
                    templateKey.append(preFix).append(DIC_SN);
                }else{
                    templateKey.append(preFix).append(dicValue);
                }
                if (checkValues != null && ArrayUtils.indexOf(checkValues, dicValue) != -1) {
                    resultMap.put(templateKey.toString(), WordReplaceUtil.getCTSym("Wingdings 2", "F052"));// 选中
                    // resultMap.put(templateKey.toString(), "\uF052");// 选中
                } else {
                    resultMap.put(templateKey.toString(), WordReplaceUtil.getCTSym("Wingdings 2", "F0A3"));// 未选中
                    // resultMap.put(templateKey.toString(), "\uF0A3");// 未选中
                }
            } catch (Exception e) {
                log.error(e);
            }
        }

        return resultMap;
    }
}
