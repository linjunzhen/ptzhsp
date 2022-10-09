/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.model;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.hflow.service.ExecutionService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 描述:
 *
 * @author Madison You
 * @created 11:00
 */
public class MaterDownload {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 9:47:00
     * @param
     * @return
     */
    public static final String FILE_SPIT = ".";

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 9:47:00
     * @param
     * @return
     */
    public static final String PDF_SUFFIX = "pdf";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 9:48:00
     * @param
     * @return
     */
    public static final String DOC_SUFFIX = "doc";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 9:48:00
     * @param
     * @return
     */
    public static final String DOCX_SUFFIX = "docx";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 9:48:00
     * @param
     * @return
     */
    public static final String XLS_SUFFIX = "xls";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 10:09:00
     * @param
     * @return
     */
    public static final String XLSX_SUFFIX = "xlsx";

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:29:00
     * @param
     * @return
     */
    public static final Integer MATER_DOWNLOAD = 1;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private ExecutionService executionService = (ExecutionService) AppUtil.getBean("executionService");

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:16:00
     * @param
     * @return
     */
    private Properties properties = FileUtil.readProperties("project.properties");

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:22:00
     * @param 
     * @return 
     */
    private String unid = UUIDGenerator.getUUID();


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private Map<String, Object> fileMap = new HashMap<>();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private String fileName;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private String filePath;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private String fileType;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private String busTableName;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    private String busTableRecordId;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:27:00
     * @param
     * @return
     */
    private String oldWordPath;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:27:00
     * @param
     * @return
     */
    private String newWordPath;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:27:00
     * @param
     * @return
     */
    private String newPdfPath;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/4 9:55:00
     * @param
     * @return
     */
    private String newStampFilePath;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public Map<String, Object> getFileMap() {
        return fileMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public void setFileMap(Map<String, Object> fileMap) {
        this.fileMap = fileMap;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public String getBusTableName() {
        return busTableName;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public void setBusTableName(String busTableName) {
        this.busTableName = busTableName;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public String getBusTableRecordId() {
        return busTableRecordId;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public void setBusTableRecordId(String busTableRecordId) {
        this.busTableRecordId = busTableRecordId;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:33:00
     * @param
     * @return
     */
    public String getOldWordPath() {
        return oldWordPath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:34:00
     * @param
     * @return
     */
    public void setOldWordPath(String oldWordPath) {
        this.oldWordPath = oldWordPath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:34:00
     * @param
     * @return
     */
    public String getNewWordPath() {
        return newWordPath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:34:00
     * @param
     * @return
     */
    public void setNewWordPath(String newWordPath) {
        this.newWordPath = newWordPath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:34:00
     * @param
     * @return
     */
    public String getNewPdfPath() {
        return newPdfPath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:34:00
     * @param
     * @return
     */
    public void setNewPdfPath(String newPdfPath) {
        this.newPdfPath = newPdfPath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/4 9:56:00
     * @param
     * @return
     */
    public String getNewStampFilePath() {
        return newStampFilePath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/4 9:56:00
     * @param
     * @return
     */
    public void setNewStampFilePath(String newStampFilePath) {
        this.newStampFilePath = newStampFilePath;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:09:00
     * @param
     * @return
     */
    public MaterDownload(String fileId) {
        initFileMap(fileId);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:21:00
     * @param 
     * @return 
     */
    public MaterDownload(String fileId, Integer type) {
        initFileMap(fileId);
        switch (type) {
            case 1:
                initMaterDownloadPath();
            default:
                initMaterDownloadPath();
        }
    }


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:12:00
     * @param
     * @return
     */
    private void initFileMap(String fileId){
        this.fileMap = executionService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"}, new Object[]{fileId});
        if (this.fileMap != null) {
            this.fileName = (String) fileMap.get("FILE_NAME");
            this.filePath = (String) fileMap.get("FILE_PATH");
            this.fileType = (String) fileMap.get("FILE_TYPE");
            this.busTableName = (String) fileMap.get("BUS_TABLENAME");
            this.busTableRecordId = (String) fileMap.get("BUS_TABLERECORDID");
        }
    }
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/3 11:23:00
     * @param 
     * @return 
     */
    private void initMaterDownloadPath(){
        String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        String pdfFilePath = properties.getProperty("PdfFilePath").replace("\\", "/");
        String currentDate = "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        String pdfFileDirectory = "attachFiles/pdf/" + currentDate;
        File pdfFile = new File(pdfFilePath + pdfFileDirectory);
        if (!pdfFile.exists()) {
            pdfFile.mkdirs();
        }
        this.oldWordPath = uploadFileUrlIn + filePath;
        this.newWordPath = pdfFilePath + pdfFileDirectory + File.separator + unid + MaterDownload.FILE_SPIT + fileType;
        this.newPdfPath = pdfFilePath + pdfFileDirectory + File.separator + unid +
                MaterDownload.FILE_SPIT + MaterDownload.PDF_SUFFIX;
        this.newStampFilePath = pdfFilePath + pdfFileDirectory + File.separator + unid + "-stamp" +
                MaterDownload.FILE_SPIT + MaterDownload.PDF_SUFFIX;
    }

}
