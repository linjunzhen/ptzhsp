/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.bsfw.model.PtspFile;
import net.evecom.platform.project.dao.ProjectXFDao;
import net.evecom.platform.project.service.ProjectTsXfService;
import net.evecom.platform.project.service.ProjectXFService;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述 特殊建筑工程消防验收ServiceImpl
 * @author Keravon Feng
 * @created 2021年11月18日 下午2:58:43
 */
@Service("projectTsXfService")
public class ProjectTsXfServiceImpl extends BaseServiceImpl implements ProjectTsXfService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ProjectTsXfServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectXFDao dao;
    
    /**
     * projectXFService
     */
    @Resource
    private ProjectXFService projectXFService;
    
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 描述 getEntityDao
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:00:01
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述 受理是否通过的决策事件
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:02:57
     * @param flowVars
     * @return
     */
    public Set<String> getIsAcceptPass(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("SLSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("0")) {
            resultNodes.add("开始");
        } else {            
            resultNodes.add("专家抽取");
        }
        return resultNodes;
    }
    
    /**
     * 描述 专家是否确认时间
     * @author Keravon Feng
     * @created 2021年11月18日 下午3:07:45
     * @param flowVars
     * @return
     */
    public Set<String> getIsZjTimeState(Map<String, Object> flowVars){
        String GSSFTG = (String) flowVars.get("ZJSFQR");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("0")) {
            resultNodes.add("专家抽取");
        } else {            
            resultNodes.add("消防验收");
        }
        return resultNodes;
    }
    
    
    
    /**
     * 描述 消防验收是否合格
     * @author Keravon Feng
     * @created 2021年11月23日 下午4:28:16
     * @param flowVars
     * @return
     */
    @Override
    public Set<String> getIsXfYsPass(Map<String, Object> flowVars) {
        String YSSFHG = (String) flowVars.get("YSSFHG");
        Set<String> resultNodes = new HashSet<String>();
        if (YSSFHG.equals("0")) {
            resultNodes.add("开始");
        } else {            
            resultNodes.add("结束");
        }
        return resultNodes;
    }

    /**
     * 描述  生成消防验收受理通知书或不受理通知书
     * @author Keravon Feng
     * @created 2021年11月18日 下午5:01:06
     * @param flowVars
     * @return
     */
    public Map<String, Object> getAcceptResult(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();        
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = projectXFService.getBuscordByIdAndTableName(busRecordId, busTableName);
            if (isBack.equals("true") && isBack != null) {// 退回
                getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSTSXFYS_NSL.docx");
            } else {
                String SLSFTG = (String) flowVars.get("SLSFTG");
                if (SLSFTG.equals("0")) {
                    getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSTSXFYS_NSL.docx");
                }else {
                    getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSTSXFYS_SL.docx");
                }
            }
        }
        return flowVars;
    }
    
    /**
     * 描述 生成消防验收合格书和不合格书
     * @author Keravon Feng
     * @created 2021年11月24日 上午9:19:36
     * @param flowVars
     * @return
     */
    public Map<String, Object> getXfYsResult(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();        
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = projectXFService.getBuscordByIdAndTableName(busRecordId, busTableName);
            if (!"true".equals(isBack)) {
                String YSSFHG = (String) flowVars.get("YSSFHG");
                if (YSSFHG.equals("0")) {
                    getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSTSXFYS_NHG.docx");
                }else {
                    getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSTSXFYS_HG.docx");
                }
            }
        }
        return flowVars;
    }
    
    /**
     * 描述 其他建设工程备案抽查
     * @author Keravon Feng
     * @created 2021年11月30日 上午10:15:19
     * @param flowVars
     * @return
     */
    public Map<String, Object> getQtGcjsBaResult(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();        
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = projectXFService.getBuscordByIdAndTableName(busRecordId, busTableName);
            if(busRecord != null) {
                busRecord.put("EXE_ID", exeId);
            }
            if (!"true".equals(isBack)) {
                String ISCHECK = String.valueOf(flowVars.get("ISCHECK"));
                String YSSFHG = String.valueOf(flowVars.get("YSSFHG"));
                if("1".equals(ISCHECK)) {
                    if ("0".equals(YSSFHG)) {
                        //不通过
                        getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSQTXFYS_NHG.docx");
                    }else {
                        //通过
                        getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSQTXFYS_HG.docx");
                    }
                }else {
                    //未抽中
                    getDocument(busRecord, busTableName, busRecordId, "T_BSFW_GCJSQTXFYS_WCZ.docx");
                }
            }
        }
        return flowVars;
    }
    
    /**
     * 生成附件
     * 
     * @param busRecord
     * @param resultId
     */
    @SuppressWarnings("unchecked")
    private void getDocument(Map<String, Object> busRecord, String busTableName, String busRecordId, String dicName) {
        String APPLY_DATE = busRecord.get("APPLYDATE") != null ? 
                String.valueOf(busRecord.get("APPLYDATE")) :
                    DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
        String[] strArr = APPLY_DATE.split("\\-");
        for (int j = 0; j< strArr.length; j++) {
            if(j==0) {
                busRecord.put("YEAR", strArr[j]);
            }else if(j==1) {
                busRecord.put("MOUTH", strArr[j]);
            }else if(j==2) {
                busRecord.put("DAY", strArr[j]);
            }
        }
        Properties properties = FileUtil.readProperties("project.properties");
        String attachmentFilePath = properties.getProperty("uploadFileUrl").replace("\\", "/");
        // 模板路径
        String filefullPath = attachmentFilePath + "attachFiles/sccl/template/"+dicName;
        
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/scws/" + currentDate;
        //String uuId = UUIDGenerator.getUUID();
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File docFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!docFullPathFolder.exists()) {
            docFullPathFolder.mkdirs();
        }
        String newWordFilePath = "";
        newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
        // 替换模版字段,生成word
        WordReplaceUtil.replaceWord(busRecord, filefullPath, newWordFilePath);
        File file = new File(newWordFilePath);
        String url = attachmentFilePath + "upload/file";
        // 将文件上传至文件服务器
        PtspFile fileInfo = projectXFService.uploadAndGetFileInfo(url, dicName, file, busTableName);
        // 插入系统附件表
        Map<String, Object> fileAttach = new HashMap<>();
        if(dicName.startsWith("T_BSFW_GCJSTSXFYS_NSL")) {
            fileAttach.put("FILE_NAME", "特殊建设工程消防验收申请不予受理凭证.doc");
        }else if(dicName.startsWith("T_BSFW_GCJSTSXFYS_SL")) {
            fileAttach.put("FILE_NAME", "特殊建设工程消防验收申请受理凭证.doc");
        }else if(dicName.startsWith("T_BSFW_GCJSTSXFYS_NHG")) {
            fileAttach.put("FILE_NAME", "验收不合格出具消防验收意见书（不合格）.doc");
        }else if(dicName.startsWith("T_BSFW_GCJSTSXFYS_HG")) {
            fileAttach.put("FILE_NAME", "验收合格出具消防验收意见书（合格）.doc");
        }else if(dicName.startsWith("T_BSFW_GCJSQTXFYS_HG")) {
            fileAttach.put("FILE_NAME", "其他建设工程备案抽查通知书.doc");
        }else if(dicName.startsWith("T_BSFW_GCJSQTXFYS_NHG")) {
            fileAttach.put("FILE_NAME", "其他建设工程备案抽（复）查通知书.doc");
        }else if(dicName.startsWith("T_BSFW_GCJSQTXFYS_WCZ")) {
            fileAttach.put("FILE_NAME", "其他建设工程备案抽查未抽中通知书.doc");
        }
        fileAttach.put("FILE_PATH", fileInfo.getFilePath());
        fileAttach.put("FILE_TYPE", fileInfo.getFileType());
        fileAttach.put("BUS_TABLENAME", busTableName + "_SCWS");
        fileAttach.put("BUS_TABLERECORDID", busRecordId);
        fileAttach.put("ATTACH_KEY", "SCWS");
        fileAttach.put("PLAT_TYPE", 1);
        dao.saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
    }

}
