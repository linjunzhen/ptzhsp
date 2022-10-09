/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.efile.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.platform.efile.dao.ElectronicFileDao;
import net.evecom.platform.efile.service.ElectronicFileService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述可信电子文件业务相关service实现类
 * 
 * @author Luffy Cai
 *
 */
@SuppressWarnings("rawtypes")
@Service("electronicFileService")
public class ElectronicFileServiceImpl extends BaseServiceImpl implements ElectronicFileService {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ElectronicFileServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ElectronicFileDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/27 11:18:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 引入fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * @Description 电子文件信息提交
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    @Override
    public void submitEfileInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findItemList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-电子文件信息提交数据量为:" + itemDatas.size());
        }
        for (Map<String, Object> itemData : itemDatas) {
            try {
                boolean flag = true;
                saveArchiveInfo(itemData, flag);
                Map<String, Object> map = new HashMap<String, Object>();
                String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? ""
                        : itemData.get("BUS_RECORDID").toString();
                String BUS_TABLENAME = itemData.get("BUS_TABLENAME") == null ? ""
                        : itemData.get("BUS_TABLENAME").toString();
                if (flag) {
                    map.put("EFILEINFO_PUSH_STATUS", 1);
                } else {
                    map.put("EFILEINFO_PUSH_STATUS", 2);
                }
                dao.saveOrUpdate(map, BUS_TABLENAME, BUS_RECORDID);
            } catch (Exception e) {
                log.error("可信电子文件信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
            }
        }
    }

    /**
     * 
     * @Description 电子文件信息提交(办理结果附件信息)
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    @Override
    public void submitResultEfileInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findResultItemList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-电子文件办理结果附件信息提交数据量为:" + itemDatas.size());
            for (Map<String, Object> itemData : itemDatas) {
                try {
                    boolean flag = true;
                    saveResultEfileInfo(itemData, flag);
                    Map<String, Object> map = new HashMap<String, Object>();
                    String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? ""
                            : itemData.get("BUS_RECORDID").toString();
                    String BUS_TABLENAME = itemData.get("BUS_TABLENAME") == null ? ""
                            : itemData.get("BUS_TABLENAME").toString();
                    if (flag) {
                        map.put("RESULTEFILE_PUSH_STATUS", 1);
                    } else {
                        map.put("RESULTEFILE_PUSH_STATUS", 2);
                    }
                    dao.saveOrUpdate(map, BUS_TABLENAME, BUS_RECORDID);
                } catch (Exception e) {
                    log.error("可信电子办理结果附件信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
                }
            }
        }
    }

    /**
     * 
     * @Description 电子文件办件信息提交
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    @Override
    public void saveEfileProjectInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findProjectInfoList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-电子文件办件信息提交数据量为:" + itemDatas.size());
            for (Map<String, Object> itemData : itemDatas) {
                try {
                    saveEfileProjectInfo(itemData);
                } catch (Exception e) {
                    log.error("可信电子文件信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
                }

            }
        }
    }

    /**
     * 
     * @Description 电子文件流程信息提交
     * @author Luffy Cai
     * @date 2021年7月22日 void
     */
    @Override
    public void saveEfileProcessInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findProcessInfoList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-电子文件流程信息提交数据量为:" + itemDatas.size());
            for (Map<String, Object> itemData : itemDatas) {
                try {
                    saveEfileProcessInfo(itemData);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 
     * @Description 不动产事项电子文件信息提交
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    @Override
    public void submitBdcEfileInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findItemList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-电子文件信息提交数据量为:" + itemDatas.size());
        }
        for (Map<String, Object> itemData : itemDatas) {
            try {
                boolean flag = true;
                saveBdcArchiveInfo(itemData, flag);
                Map<String, Object> map = new HashMap<String, Object>();
                String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? ""
                        : itemData.get("BUS_RECORDID").toString();
                String BUS_TABLENAME = itemData.get("BUS_TABLENAME") == null ? ""
                        : itemData.get("BUS_TABLENAME").toString();
                if (flag) {
                    map.put("EFILEINFO_PUSH_STATUS", 1);
                } else {
                    map.put("EFILEINFO_PUSH_STATUS", 2);
                }
                dao.saveOrUpdate(map, BUS_TABLENAME, BUS_RECORDID);
            } catch (Exception e) {
                log.error("可信电子文件信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
            }
        }
    }

    /**
     * 
     * @Description 不动产事项电子文件信息提交(办理结果附件信息)
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    @Override
    public void submitBdcResultEfileInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findResultItemList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-电子文件办理结果附件信息提交数据量为:" + itemDatas.size());
            for (Map<String, Object> itemData : itemDatas) {
                try {
                    boolean flag = true;
                    saveResultEfileInfo(itemData, flag);
                    Map<String, Object> map = new HashMap<String, Object>();
                    String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? ""
                            : itemData.get("BUS_RECORDID").toString();
                    String BUS_TABLENAME = itemData.get("BUS_TABLENAME") == null ? ""
                            : itemData.get("BUS_TABLENAME").toString();
                    if (flag) {
                        map.put("RESULTEFILE_PUSH_STATUS", 1);
                    } else {
                        map.put("RESULTEFILE_PUSH_STATUS", 2);
                    }
                    dao.saveOrUpdate(map, BUS_TABLENAME, BUS_RECORDID);
                } catch (Exception e) {
                    log.error("可信电子办理结果附件信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
                }
            }
        }
    }

    /**
     * 
     * @Description 不动产事项电子文件办件信息提交
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    @Override
    public void saveBdcEfileProjectInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findProjectInfoList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-不动产事项电子文件办件信息提交数据量为:" + itemDatas.size());
            for (Map<String, Object> itemData : itemDatas) {
                try {
                    saveEfileProjectInfo(itemData);
                } catch (Exception e) {
                    log.error("可信电子文件信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
                }

            }
        }
    }

    /**
     * 
     * @Description 不动产事项电子文件流程信息提交
     * @author Luffy Cai
     * @date 2022年1月11日 void
     */
    @Override
    public void saveBdcEfileProcessInfo() {
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findProcessInfoList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("可信电子文件平台-不动产事项电子文件流程信息提交数据量为:" + itemDatas.size());
            for (Map<String, Object> itemData : itemDatas) {
                try {
                    saveEfileProcessInfo(itemData);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * @Description 电子文件信息提交方法
     * @author Luffy Cai
     * @date 2021年7月15日
     * @param itemData void
     */
    private void saveEfileInfo(Map<String, Object> itemData, boolean flag) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? "" : itemData.get("BUS_RECORDID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_ID = itemData.get("ITEM_ID") == null ? "" : itemData.get("ITEM_ID").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE
                : itemData.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String PROJECT_CODE = itemData.get("PROJECT_CODE") == null ? "" : itemData.get("PROJECT_CODE").toString();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getParams("saveEfileInfo", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID);
        params.put("interfaceName", "saveEfileInfo");
        // 拼接文件结构化信息，按照可信电子文件提供的文件结构化信息封装 jsonInfo
        List<Map<String, Object>> materList = findItemMater(ITEM_ID);// 根据事项编码获取材料列表
        for (Map<String, Object> mater : materList) {
            String MATER_CODE = mater.get("MATER_CODE") == null ? "" : mater.get("MATER_CODE").toString();
            // params.put("state", 0);
            // 根据材料编码、业务表id获取材料
            List<Map<String, Object>> fileList = findFileList(MATER_CODE, BUS_RECORDID);
            if (null != fileList && fileList.size() > 0) {
                // 获取文件生成时间
                Map<String, Object> file = fileList.get(0);
                String CREATE_TIME = file.get("CREATE_TIME") == null ? "" : file.get("CREATE_TIME").toString();
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                jsonMap.put("CREATE_TIME", CREATE_TIME);
                setMaterInfo(jsonMap, mater, PROJECT_CODE);
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "EFILE" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = new HashMap<String, Object>();
                configMap.put("jsonInfo", jsonMap);
                StringBuffer jsonInfo = this.makeData(configMap, contentStr);
                Map<String, Object> jsonInfoMap = JSON.parseObject(jsonInfo.toString(), Map.class);
                params.put("jsonInfo", JSON.toJSONString(jsonInfoMap));
                List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
                getEFileList(fileInfoList, fileList);
                params.put("fileInfo", JSON.toJSONString(fileInfoList));
                try {
                    Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                        String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                            String certificateIdentifier = dataMap.get("fileIdentifier") == null ? ""
                                    : dataMap.get("fileIdentifier").toString();
                            String sql = "update T_MSJW_SYSTEM_FILEATTACH f set f.KZ_FILEIDENTIFIER=? where f.BUS_TABLERECORDID=? and f.ATTACH_KEY=?";
                            dao.executeSql(sql, new Object[] { certificateIdentifier, BUS_RECORDID, MATER_CODE });
                            log.info("申报号：" + EXE_ID + "可信电子文件信息推送成功！");
                        } else {// 失败
                            flag = false;
                            log.error("申报号：" + EXE_ID + "可信电子文件信息推送失败！返回错误：" + message);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @Description 归档信息提交接口
     * @author Luffy Cai
     * @date 2021年11月24日
     * @param itemData
     * @param flag     void
     */
    private void saveArchiveInfo(Map<String, Object> itemData, boolean flag) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? "" : itemData.get("BUS_RECORDID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_ID = itemData.get("ITEM_ID") == null ? "" : itemData.get("ITEM_ID").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE
                : itemData.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String PROJECT_CODE = itemData.get("PROJECT_CODE") == null ? "" : itemData.get("PROJECT_CODE").toString();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getParams("saveArchiveInfo", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID);
        params.put("interfaceName", "saveArchiveInfo");
        // 拼接文件结构化信息，按照可信电子文件提供的文件结构化信息封装 jsonInfo
        List<Map<String, Object>> materList = findItemMater(ITEM_ID);// 根据事项编码获取材料列表
        for (Map<String, Object> mater : materList) {
            String MATER_CODE = mater.get("MATER_CODE") == null ? "" : mater.get("MATER_CODE").toString();
            // params.put("state", 0);
            // 根据材料编码、业务表id获取材料
            List<Map<String, Object>> fileList = findFileList(MATER_CODE, BUS_RECORDID);
            if (null != fileList && fileList.size() > 0) {
                // 获取文件生成时间
                Map<String, Object> file = fileList.get(0);
                String CREATE_TIME = file.get("CREATE_TIME") == null ? "" : file.get("CREATE_TIME").toString();
                Map<String, Object> materMap = new HashMap<String, Object>();
                materMap.put("CREATE_TIME", CREATE_TIME);
                setMaterInfo(materMap, mater, PROJECT_CODE);
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "EFILE" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = new HashMap<String, Object>();
                List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
                getEFileList(fileInfoList, fileList);
                materMap.put("fileInfoList", fileInfoList);
                configMap.put("mater", materMap);
                StringBuffer materialInfo = this.makeData(configMap, contentStr);
                Map<String, Object> materialInfoMap = JSON.parseObject(materialInfo.toString(), Map.class);
                params.put("materialInfo", JSON.toJSONString(materialInfoMap));
                try {
                    Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                        String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                            String certificateIdentifier = dataMap.get("fileIdentifier") == null ? ""
                                    : dataMap.get("fileIdentifier").toString();
                            String sql = "update T_MSJW_SYSTEM_FILEATTACH f set f.KZ_FILEIDENTIFIER=? where f.BUS_TABLERECORDID=? and f.ATTACH_KEY=?";
                            dao.executeSql(sql, new Object[] { certificateIdentifier, BUS_RECORDID, MATER_CODE });
                            log.info("申报号：" + EXE_ID + "可信电子文件信息推送成功！");
                        } else {// 失败
                            flag = false;
                            log.error("申报号：" + EXE_ID + "可信电子文件信息推送失败！返回错误：" + message);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @Description 归档信息提交接口(不动产)
     * @author Luffy Cai
     * @date 2021年11月24日
     * @param itemData
     * @param flag     void
     */
    private void saveBdcArchiveInfo(Map<String, Object> itemData, boolean flag) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? "" : itemData.get("BUS_RECORDID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_ID = itemData.get("ITEM_ID") == null ? "" : itemData.get("ITEM_ID").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getParams("saveArchiveInfo", gcjsxmGrantCode, ITEM_CODE, ITEM_NAME, EXE_ID);
        params.put("interfaceName", "saveArchiveInfo");
        // 拼接文件结构化信息，按照可信电子文件提供的文件结构化信息封装 jsonInfo
        List<Map<String, Object>> materList = findItemMater(ITEM_ID);// 根据事项编码获取材料列表
        for (Map<String, Object> mater : materList) {
            String MATER_CODE = mater.get("MATER_CODE") == null ? "" : mater.get("MATER_CODE").toString();
            // params.put("state", 0);
            // 根据材料编码、业务表id获取材料
            List<Map<String, Object>> fileList = findFileList(MATER_CODE, BUS_RECORDID);
            if (null != fileList && fileList.size() > 0) {
                // 获取文件生成时间
                Map<String, Object> file = fileList.get(0);
                String CREATE_TIME = file.get("CREATE_TIME") == null ? "" : file.get("CREATE_TIME").toString();
                Map<String, Object> materMap = new HashMap<String, Object>();
                materMap.put("CREATE_TIME", CREATE_TIME);
                setBdcMaterInfo(materMap, mater, EXE_ID);
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "BDCEFILE" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = new HashMap<String, Object>();
                List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
                getEFileList(fileInfoList, fileList);
                materMap.put("fileInfoList", fileInfoList);
                configMap.put("mater", materMap);
                StringBuffer materialInfo = this.makeData(configMap, contentStr);
                Map<String, Object> materialInfoMap = JSON.parseObject(materialInfo.toString(), Map.class);
                params.put("materialInfo", JSON.toJSONString(materialInfoMap));
                try {
                    Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                        String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                            String certificateIdentifier = dataMap.get("fileIdentifier") == null ? ""
                                    : dataMap.get("fileIdentifier").toString();
                            String sql = "update T_MSJW_SYSTEM_FILEATTACH f set f.KZ_FILEIDENTIFIER=? where f.BUS_TABLERECORDID=? and f.ATTACH_KEY=?";
                            dao.executeSql(sql, new Object[] { certificateIdentifier, BUS_RECORDID, MATER_CODE });
                            log.info("申报号：" + EXE_ID + "可信电子文件信息推送成功！");
                        } else {// 失败
                            flag = false;
                            log.error("申报号：" + EXE_ID + "可信电子文件信息推送失败！返回错误：" + message);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @Description 电子文件办结结果附件信息提交方法
     * @author Luffy Cai
     * @date 2021年8月13日
     * @param itemData
     * @param flag     void
     */
    private void saveResultEfileInfo(Map<String, Object> itemData, boolean flag) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE
                : itemData.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String PROJECT_CODE = itemData.get("PROJECT_CODE") == null ? "" : itemData.get("PROJECT_CODE").toString();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getParams("saveArchiveInfo", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID);
        params.put("interfaceName", "saveArchiveInfo");
        // 根据申报号获取结果附件信息
        Map<String, Object> execution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { EXE_ID });
        String RESULT_FILE_ID = execution.get("RESULT_FILE_ID") == null ? ""
                : execution.get("RESULT_FILE_ID").toString();
        if (StringUtils.isNotEmpty(RESULT_FILE_ID)) {
            List<Map<String, Object>> fileList = fileAttachService.findListForResult(RESULT_FILE_ID);
            if (fileList != null && fileList.size() > 0) {
                Map<String, Object> file = fileList.get(0);
                String CREATE_TIME = file.get("CREATE_TIME") == null ? "" : file.get("CREATE_TIME").toString();
                // 拼接文件结构化信息，按照可信电子文件提供的文件结构化信息封装 jsonInfo
                Map<String, Object> materMap = new HashMap<String, Object>();
                materMap.put("CREATE_TIME", CREATE_TIME);
                setResultJsonInfo(materMap, PROJECT_CODE, SWB_ITEM_CODE);
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "EFILE" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = new HashMap<String, Object>();
                List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
                getEFileList(fileInfoList, fileList);
                materMap.put("fileInfoList", fileInfoList);
                configMap.put("mater", materMap);
                StringBuffer materialInfo = this.makeData(configMap, contentStr);
                Map<String, Object> materialInfoMap = JSON.parseObject(materialInfo.toString(), Map.class);
                params.put("materialInfo", JSON.toJSONString(materialInfoMap));
                try {
                    Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                        String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                            String certificateIdentifier = dataMap.get("fileIdentifier") == null ? ""
                                    : dataMap.get("fileIdentifier").toString();
                            StringBuffer sb = new StringBuffer();
                            String[] valuesArray = RESULT_FILE_ID.split(";");
                            for (String value : valuesArray) {
                                sb.append(",'" + value + "'");
                            }
                            sb.delete(0, 1);
                            sb.insert(0, "(");
                            sb.append(")");
                            RESULT_FILE_ID = sb.toString();
                            String sql = "update T_MSJW_SYSTEM_FILEATTACH f set f.KZ_FILEIDENTIFIER=? where f.FILE_ID IN "
                                    + RESULT_FILE_ID;
                            dao.executeSql(sql, new Object[] { certificateIdentifier });
                            log.info("申报号：" + EXE_ID + "可信电子文件办结结果附件信息推送成功！");
                        } else {// 失败
                            flag = false;
                            log.error("申报号：" + EXE_ID + "可信电子文件办结结果附件信息推送失败！返回错误：" + message);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 
     * @Description 不动产电子文件办结结果附件信息提交方法
     * @author Luffy Cai
     * @date 2021年8月13日
     * @param itemData
     * @param flag     void
     */
    private void saveBdcResultEfileInfo(Map<String, Object> itemData, boolean flag) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE
                : itemData.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        // String PROJECT_CODE = itemData.get("PROJECT_CODE") == null ? "" :
        // itemData.get("PROJECT_CODE").toString();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getParams("saveArchiveInfo", gcjsxmGrantCode, ITEM_CODE, ITEM_NAME, EXE_ID);
        params.put("interfaceName", "saveArchiveInfo");
        // 根据申报号获取结果附件信息
        Map<String, Object> execution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { EXE_ID });
        String RESULT_FILE_ID = execution.get("RESULT_FILE_ID") == null ? ""
                : execution.get("RESULT_FILE_ID").toString();
        if (StringUtils.isNotEmpty(RESULT_FILE_ID)) {
            List<Map<String, Object>> fileList = fileAttachService.findListForResult(RESULT_FILE_ID);
            if (fileList != null && fileList.size() > 0) {
                Map<String, Object> file = fileList.get(0);
                String CREATE_TIME = file.get("CREATE_TIME") == null ? "" : file.get("CREATE_TIME").toString();
                // 拼接文件结构化信息，按照可信电子文件提供的文件结构化信息封装 jsonInfo
                Map<String, Object> materMap = new HashMap<String, Object>();
                materMap.put("CREATE_TIME", CREATE_TIME);
                setBdcResultJsonInfo(materMap, EXE_ID, SWB_ITEM_CODE);
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "BDCEFILE" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = new HashMap<String, Object>();
                List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
                getEFileList(fileInfoList, fileList);
                materMap.put("fileInfoList", fileInfoList);
                configMap.put("mater", materMap);
                StringBuffer materialInfo = this.makeData(configMap, contentStr);
                Map<String, Object> materialInfoMap = JSON.parseObject(materialInfo.toString(), Map.class);
                params.put("materialInfo", JSON.toJSONString(materialInfoMap));
                try {
                    Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                        String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                            String certificateIdentifier = dataMap.get("fileIdentifier") == null ? ""
                                    : dataMap.get("fileIdentifier").toString();
                            StringBuffer sb = new StringBuffer();
                            String[] valuesArray = RESULT_FILE_ID.split(";");
                            for (String value : valuesArray) {
                                sb.append(",'" + value + "'");
                            }
                            sb.delete(0, 1);
                            sb.insert(0, "(");
                            sb.append(")");
                            RESULT_FILE_ID = sb.toString();
                            String sql = "update T_MSJW_SYSTEM_FILEATTACH f set f.KZ_FILEIDENTIFIER=? where f.FILE_ID IN "
                                    + RESULT_FILE_ID;
                            dao.executeSql(sql, new Object[] { certificateIdentifier });
                            log.info("申报号：" + EXE_ID + "可信电子文件办结结果附件信息推送成功！");
                        } else {// 失败
                            flag = false;
                            log.error("申报号：" + EXE_ID + "可信电子文件办结结果附件信息推送失败！返回错误：" + message);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * @Description 电子文件办件信息提交方法
     * @author Luffy Cai
     * @date 2021年7月15日
     * @param itemData void
     */
    private void saveEfileProjectInfo(Map<String, Object> itemData) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? "" : itemData.get("BUS_RECORDID").toString();
        String BUS_TABLENAME = itemData.get("BUS_TABLENAME") == null ? "" : itemData.get("BUS_TABLENAME").toString();
        String SUBJECT = itemData.get("SUBJECT") == null ? "" : itemData.get("SUBJECT").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE
                : itemData.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String ZBCS = itemData.get("ZBCS") == null ? "" : itemData.get("ZBCS").toString();
        String SSBMBM = itemData.get("SSBMBM") == null ? "" : itemData.get("SSBMBM").toString();
        String BSYHLX = itemData.get("BSYHLX") == null ? "" : itemData.get("BSYHLX").toString();
        String CREATE_TIME = itemData.get("CREATE_TIME") == null ? "" : itemData.get("CREATE_TIME").toString();
        String END_TIME = itemData.get("END_TIME") == null ? "" : itemData.get("END_TIME").toString();
        String SXLX = itemData.get("SXLX") == null ? "" : itemData.get("SXLX").toString();
        String C_VERSION = itemData.get("C_VERSION") == null ? "" : itemData.get("C_VERSION").toString();
        String SXXZ = itemData.get("SXXZ") == null ? "" : itemData.get("SXXZ").toString();
        String taskType = getTaskType(SXXZ);
        ArrayList<Object> param = new ArrayList<>();
        param.add(EXE_ID);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
        List<Map<String, Object>> executionList = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> decryptList = encryptionService.listDecrypt(executionList, "JBPM6_EXECUTION");
        Map<String, Object> exeMap = decryptList.get(0);
        String cardType = "";
        String contactedCard = "";
        String applyName = "";
        if (BSYHLX.equals("2")) {// 法人
            cardType = "001";
            contactedCard = exeMap.get("SQJG_CODE") == null ? "" : exeMap.get("SQJG_CODE").toString();
            applyName = exeMap.get("SQJG_NAME") == null ? "" : exeMap.get("SQJG_NAME").toString();
        } else {// 个人
            String SQRZJLX = exeMap.get("SQRZJLX") == null ? "" : exeMap.get("SQRZJLX").toString();
            cardType = getCardType(SQRZJLX);
            contactedCard = exeMap.get("SQRSFZ") == null ? "" : exeMap.get("SQRSFZ").toString();
            applyName = exeMap.get("SQRMC") == null ? "" : exeMap.get("SQRMC").toString();
        }
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getClientParams("saveEfileProjectInfo", gcjsxmGrantCode);
        params.put("interfaceName", "saveEfileProjectInfo");
        params.put("projectId", EXE_ID);
        params.put("projectName", SUBJECT);
        params.put("receiveDepartment", ZBCS);
        params.put("receiveDepartmentCode", SSBMBM);
        params.put("cardType", cardType);
        params.put("contactedCard", contactedCard);
        params.put("applyName", applyName);
        params.put("mobile", "");
        params.put("phone", "");
        params.put("address", "");
        params.put("legalMan", applyName);
        params.put("receiveTime", CREATE_TIME);
        params.put("serviceType", SXLX);
        params.put("transactTime", END_TIME);
        params.put("result", "Y");
        params.put("resultCode", EXE_ID);
        params.put("taskCode", SWB_ITEM_CODE);
        params.put("taskType", taskType);
        params.put("taskName", ITEM_NAME);
        params.put("taskVersion", C_VERSION);
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                info.put("PROJECT_PUSH_STATUS", 1);
                log.info("申报号：" + EXE_ID + "可信电子文件办件信息推送成功！");
            } else {// 失败
                info.put("PROJECT_PUSH_STATUS", 2);
                log.error("申报号：" + EXE_ID + "可信电子文件办件信息推送失败！返回错误：" + message);
            }
            dao.saveOrUpdate(info, BUS_TABLENAME, BUS_RECORDID);
        }
    }

    /**
     * 
     * @Description 电子文件流程信息提交方法
     * @author Luffy Cai
     * @date 2021年7月20日
     * @param itemData void
     */
    private void saveEfileProcessInfo(Map<String, Object> itemData) {
        List<Object> param = new ArrayList<Object>();
        String exeId = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String BUS_RECORDID = itemData.get("BUS_RECORDID") == null ? "" : itemData.get("BUS_RECORDID").toString();
        String BUS_TABLENAME = itemData.get("BUS_TABLENAME") == null ? "" : itemData.get("BUS_TABLENAME").toString();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        StringBuffer sql = new StringBuffer();
        param.add(exeId);
        sql.append(" select t.TASK_NODENAME,t.EXE_HANDLEDESC,t.HANDLE_OPINION,t.END_TIME from JBPM6_TASK t");
        sql.append(" where t.exe_id=?  and t.STEP_SEQ !='0' order by t.create_time asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            String EXE_HANDLEDESC = map.get("EXE_HANDLEDESC").toString();
            String author = getAuthor(EXE_HANDLEDESC);
            dataMap.put("type", "审核意见");
            dataMap.put("nodeName", map.get("TASK_NODENAME").toString());
            dataMap.put("author", author);
            dataMap.put("body", map.get("HANDLE_OPINION") == null ? "同意" : map.get("HANDLE_OPINION").toString());
            dataMap.put("modified", map.get("END_TIME").toString());
            dataList.add(dataMap);
        }
        // 获取公共参数
        Map<String, Object> params = getClientParams("saveEfileProcessInfo", gcjsxmGrantCode);
        params.put("projectId", exeId);
        params.put("dataList", JSON.toJSONString(dataList));
        params.put("interfaceName", "saveEfileProcessInfo");
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                info.put("PROCESS_PUSH_STATUS", 1);
                log.info("申报号：" + exeId + "可信电子文件办件信息推送成功！");
            } else {// 失败
                info.put("PROCESS_PUSH_STATUS", 2);
                log.error("申报号：" + exeId + "可信电子文件办件信息推送失败！返回错误：" + message);
            }
            dao.saveOrUpdate(info, BUS_TABLENAME, BUS_RECORDID);
        }
    }

    /**
     * 
     * @Description 根据执行动作描述获取具体办理人员名称
     * @author Luffy Cai
     * @date 2021年7月22日
     * @param str
     * @return String
     */
    private String getAuthor(String str) {
        int strStartIndex = str.indexOf("[");
        int strEndIndex = str.indexOf("]");
        return str.substring(strStartIndex + 1, strEndIndex);
    }

    /**
     * 
     * @Description 设置文件信息
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param eFileList
     * @param fileList  void
     */
    private void getEFileList(List<Map<String, Object>> eFileList, List<Map<String, Object>> fileList) {
        int i = 1;
        for (Map<String, Object> file : fileList) {
            Map<String, Object> fileMap = new HashMap<String, Object>();
            String fileId = file.get("FILE_ID") == null ? "" : file.get("FILE_ID").toString();
            String fileName = file.get("FILE_NAME") == null ? "" : file.get("FILE_NAME").toString();
            String fileFormat = file.get("FILE_TYPE") == null ? "" : file.get("FILE_TYPE").toString().toLowerCase();
            // String fileLength = file.get("FILE_LENGTH") == null ? "" :
            // file.get("FILE_LENGTH").toString();
            // String fileSize = file.get("FILE_SIZE") == null ? "" :
            // file.get("FILE_SIZE").toString();
            fileMap.put("fileFormat", fileFormat);
            fileMap.put("fileIndex", i);
            fileMap.put("fileName", fileName);
            int fileSignStatus = 0;
            if (fileFormat.equals("pdf") || fileFormat.equals("edc") || fileFormat.equals("ofd")) {
                fileSignStatus = 1;
            }
            fileMap.put("fileSignStatus", fileSignStatus);
            fileMap.put("fileContent", "http://192.168.129.178/DownLoadServlet?fileId=" + fileId);
            fileMap.put("fileContentType", 1);
            // fileMap.put("fileLength", fileLength);
            // fileMap.put("fileSize",fileSize);
            eFileList.add(fileMap);
            i++;
        }
    }

    /**
     * 
     * @Description 获取事项信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findItemList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append(
                "V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_EFILEINFO_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取事项信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findResultItemList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append(
                "V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_RESULTEFILE_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取事项办件信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findProjectInfoList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append("V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_PROJECT_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取事项流程信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findProcessInfoList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append("V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_PROCESS_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取不动产事项信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findBdcItemList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append(
                "V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_EFILEINFO_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取不动产事项信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findBdcResultItemList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append(
                "V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_RESULTEFILE_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取不动产事项办件信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findBdcProjectInfoList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append("V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_PROJECT_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取不动产事项流程信息列表
     * @author Luffy Cai
     * @date 2021年7月15日
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findBdcProcessInfoList() {
        StringBuffer sql = new StringBuffer("SELECT V.EXE_ID,V.SUBJECT,V.BUS_RECORDID,");
        sql.append("V.BUS_TABLENAME,V.PROJECT_CODE,V.BSYHLX,V.SQJG_TYPE,V.SQJG_LEALPERSON,V.SQRMC,");
        sql.append("V.CREATE_TIME,V.END_TIME,V.SQJG_CODE,V.SQRZJLX,V.SQRSFZ,V.ZBCS,V.SSBMBM,V.ITEM_ID,");
        sql.append("V.ITEM_CODE,V.C_VERSION,V.ITEM_NAME,V.SWB_ITEM_CODE,V.SXLX,V.SXXZ FROM VIEW_PROCESS_PUSH_STATUS V");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 
     * @Description 获取公共的参数
     * @author Luffy Cai
     * @date 2021年7月15日
     * @param servicecode
     * @param gcjsxmGrantCode
     * @param taskCode
     * @param taskName
     * @param exeId
     * @return Map<String,Object>
     */
    private Map<String, Object> getParams(String servicecode, String gcjsxmGrantCode, String taskCode, String taskName,
            String exeId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("servicecode", servicecode);
        params.put("grantcode", gcjsxmGrantCode);
        params.put("areaCode", "35012800000");
        params.put("areaName", "平潭综合实验区");
        params.put("deptName", "平潭综合实验区行政审批局");
        params.put("deptCode", "11350128345071904J");
        params.put("operName", "evecom");
        params.put("operId", "evecom");
        params.put("systemName", "平潭综合实验区工改业务系统");
        params.put("taskCode", taskCode);
        params.put("taskName", taskName);
        params.put("projectNo", exeId);
        return params;
    }

    /**
     * 
     * @Description 获取公共的参数
     * @author Luffy Cai
     * @date 2021年7月15日
     * @param servicecode
     * @param gcjsxmGrantCode
     * @param taskCode
     * @param taskName
     * @param exeId
     * @return Map<String,Object>
     */
    private Map<String, Object> getClientParams(String servicecode, String gcjsxmGrantCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("servicecode", servicecode);
        params.put("grantcode", gcjsxmGrantCode);
        params.put("areaCode", "35012800000");
        params.put("areaName", "平潭综合实验区");
        params.put("deptName", "平潭综合实验区行政审批局");
        params.put("deptCode", "11350128345071904J");
        params.put("operName", "evecom");
        params.put("operId", "evecom");
        params.put("systemName", "平潭综合实验区工改业务系统");
        return params;
    }

    /**
     * 
     * 描述 用数据包填充模版
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午6:36:23
     * @param map
     * @param configStr
     * @return
     */
    private StringBuffer makeData(Map<String, Object> map, String configStr) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configStr, map));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
    }

    /**
     * 
     * @Description 查找事项
     * @author Luffy Cai
     * @date 2021年7月20日
     * @param item_id
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findItemMater(String item_id) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.MATER_CLSMLX,T.KZ_WJLYL,T.KZ_MJ,");
        sql.append(" T.KZ_WJLXBM,T.KZ_XKDJ,T.KZ_WJLXMC,T.KZ_WJMLBM,T.KZ_MATERID from T_WSBS_SERVICEITEM_MATER SM");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON SM.ITEM_ID=S.ITEM_ID");
        sql.append(" LEFT JOIN T_WSBS_APPLYMATER T ON SM.MATER_ID=T.MATER_ID  WHERE S.ITEM_ID=?");
        params.add(item_id);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 
     * @Description 获取附件列表
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param materCode
     * @param busRecorId
     * @return List<Map<String,Object>>
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findFileList(String materCode, String busRecorId) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* from T_MSJW_SYSTEM_FILEATTACH T");
        sql.append(" WHERE T.BUS_TABLERECORDID=? AND T.ATTACH_KEY=?");
        params.add(busRecorId);
        params.add(materCode);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 
     * @Description 设置文件结构化信息
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param map
     * @param projectCode void
     */
    @SuppressWarnings("unchecked")
    private void setMaterInfo(Map<String, Object> map, Map<String, Object> mater, String projectCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.* from SPGL_XMDWXXB d where d.jbxx_id in(");
        sql.append(" select t.id from SPGL_XMJBXXB t where t.PROJECT_CODE=?) ");
        params.add(projectCode);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        String fileHolderName = "";
        String fileHolderCode = "";
        String fileHolderCodeType = "";
        if (null != list && list.size() > 0) {
            Map<String, Object> projectMap = list.get(0);
            fileHolderName = projectMap.get("ENTERPRISE_NAME") == null ? ""
                    : projectMap.get("ENTERPRISE_NAME").toString();
            fileHolderCode = projectMap.get("LEREP_CERTNO") == null ? "" : projectMap.get("LEREP_CERTNO").toString();
            String LEREP_CERTTYPE = projectMap.get("LEREP_CERTTYPE") == null ? ""
                    : projectMap.get("LEREP_CERTTYPE").toString();
            fileHolderCodeType = getFileHolderCodeType(LEREP_CERTTYPE);
            map.put("fileHolderName", fileHolderName);
            map.put("fileHolderCode", fileHolderCode);
            map.put("fileHolderCodeType", fileHolderCodeType);
        }
        StringBuffer sql2 = new StringBuffer("select s.PROJECT_NAME from SPGL_XMJBXXB s where s.PROJECT_CODE=?");
        List<Map<String, Object>> jbxxList = dao.findBySql(sql2.toString(), params.toArray(), null);
        Map<String, Object> jbxxMap = jbxxList.get(0);
        String PROJECT_NAME = jbxxMap.get("PROJECT_NAME") == null ? "" : jbxxMap.get("PROJECT_NAME").toString();
        String MATER_NAME = mater.get("MATER_NAME") == null ? "" : mater.get("MATER_NAME").toString();
        String MATER_CLSMLX = mater.get("MATER_CLSMLX") == null ? "" : mater.get("MATER_CLSMLX").toString();
        String KZ_WJLYL = mater.get("KZ_WJLYL") == null ? "" : mater.get("KZ_WJLYL").toString();
        String KZ_MJ = mater.get("KZ_MJ") == null ? "" : mater.get("KZ_MJ").toString();
        String KZ_WJLXBM = mater.get("KZ_WJLXBM") == null ? "" : mater.get("KZ_WJLXBM").toString();
        String KZ_XKDJ = mater.get("KZ_XKDJ") == null ? "" : mater.get("KZ_XKDJ").toString();
        String KZ_WJLXMC = mater.get("KZ_WJLXMC") == null ? "" : mater.get("KZ_WJLXMC").toString();
        String KZ_WJMLBM = mater.get("KZ_WJMLBM") == null ? "" : mater.get("KZ_WJMLBM").toString();
        String KZ_WJBGQX = mater.get("KZ_WJBGQX") == null ? "" : mater.get("KZ_WJBGQX").toString();
        String KZ_MATERID = mater.get("KZ_MATERID") == null ? "" : mater.get("KZ_MATERID").toString();
        map.put("PROJECT_CODE", projectCode);
        map.put("PROJECT_NAME", PROJECT_NAME);
        map.put("MATER_NAME", MATER_NAME);
        map.put("MATER_CLSMLX", MATER_CLSMLX);
        map.put("KZ_WJLYL", KZ_WJLYL);
        map.put("KZ_MJ", KZ_MJ);
        map.put("KZ_WJLXBM", KZ_WJLXBM);
        map.put("KZ_XKDJ", KZ_XKDJ);
        map.put("KZ_WJLXMC", KZ_WJLXMC);
        map.put("KZ_WJMLBM", KZ_WJMLBM);
        map.put("KZ_WJBGQX", KZ_WJBGQX);
        map.put("KZ_MATERID", KZ_MATERID);
    }

    /**
     * 
     * @Description 设置文件结构化信息
     * @author Luffy Cai
     * @date 2022年1月24日
     * @param map
     * @param mater
     * @param exeId void
     */
    @SuppressWarnings("unchecked")
    private void setBdcMaterInfo(Map<String, Object> map, Map<String, Object> mater, String exeId) {
        String fileHolderName = "";
        String fileHolderCode = "";
        String fileHolderCodeType = "";
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.* from JBPM6_EXECUTION d where d.exe_id=? ");
        params.add(exeId);
        List<Map<String, Object>> userList = dao.findBySql(sql.toString(), params.toArray(), null);
        List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
        if (decryptList != null && decryptList.size() > 0) {
            Map<String, Object> userInfo = decryptList.get(0);
            fileHolderName = userInfo.get("SQJG_NAME") == null ? "" : userInfo.get("SQJG_NAME").toString();
            fileHolderCode = userInfo.get("SQJG_CODE") == null ? "" : userInfo.get("SQJG_CODE").toString();
            fileHolderCodeType = userInfo.get("SQJG_TYPE") == null ? "" : userInfo.get("SQJG_TYPE").toString();
            if (StringUtils.isNotEmpty(fileHolderCodeType)) {
                if (fileHolderCodeType.equals("QY")) {// 统一社会信用代码
                    fileHolderCodeType = "001";
                } else {
                    fileHolderCodeType = "099";
                }
            }
            map.put("fileHolderName", fileHolderName);
            map.put("fileHolderCode", fileHolderCode);
            map.put("fileHolderCodeType", fileHolderCodeType);
        }
        String MATER_NAME = mater.get("MATER_NAME") == null ? "" : mater.get("MATER_NAME").toString();
        String MATER_CLSMLX = mater.get("MATER_CLSMLX") == null ? "" : mater.get("MATER_CLSMLX").toString();
        String KZ_WJLYL = mater.get("KZ_WJLYL") == null ? "" : mater.get("KZ_WJLYL").toString();
        String KZ_MJ = mater.get("KZ_MJ") == null ? "" : mater.get("KZ_MJ").toString();
        String KZ_WJLXBM = mater.get("KZ_WJLXBM") == null ? "" : mater.get("KZ_WJLXBM").toString();
        String KZ_XKDJ = mater.get("KZ_XKDJ") == null ? "" : mater.get("KZ_XKDJ").toString();
        String KZ_WJLXMC = mater.get("KZ_WJLXMC") == null ? "" : mater.get("KZ_WJLXMC").toString();
        String KZ_WJMLBM = mater.get("KZ_WJMLBM") == null ? "" : mater.get("KZ_WJMLBM").toString();
        String KZ_WJBGQX = mater.get("KZ_WJBGQX") == null ? "" : mater.get("KZ_WJBGQX").toString();
        String KZ_MATERID = mater.get("KZ_MATERID") == null ? "" : mater.get("KZ_MATERID").toString();
        map.put("MATER_NAME", MATER_NAME);
        map.put("MATER_CLSMLX", MATER_CLSMLX);
        map.put("KZ_WJLYL", KZ_WJLYL);
        map.put("KZ_MJ", KZ_MJ);
        map.put("KZ_WJLXBM", KZ_WJLXBM);
        map.put("KZ_XKDJ", KZ_XKDJ);
        map.put("KZ_WJLXMC", KZ_WJLXMC);
        map.put("KZ_WJMLBM", KZ_WJMLBM);
        map.put("KZ_WJBGQX", KZ_WJBGQX);
        map.put("KZ_MATERID", KZ_MATERID);
    }

    /**
     * 
     * @Description 设置办结结果文件结构化信息
     * @author Luffy Cai
     * @date 2021年8月22日
     * @param map
     * @param projectCode
     * @param swbCode     void
     */
    @SuppressWarnings("unchecked")
    private void setResultJsonInfo(Map<String, Object> map, String projectCode, String swbCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.* from SPGL_XMDWXXB d where d.jbxx_id in(");
        sql.append(" select t.id from SPGL_XMJBXXB t where t.PROJECT_CODE=?) ");
        params.add(projectCode);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        String fileHolderName = "";
        String fileHolderCode = "";
        String fileHolderCodeType = "";
        if (null != list && list.size() > 0) {
            Map<String, Object> projectMap = list.get(0);
            fileHolderName = projectMap.get("ENTERPRISE_NAME") == null ? ""
                    : projectMap.get("ENTERPRISE_NAME").toString();
            fileHolderCode = projectMap.get("LEREP_CERTNO") == null ? "" : projectMap.get("LEREP_CERTNO").toString();
            String LEREP_CERTTYPE = projectMap.get("LEREP_CERTTYPE") == null ? ""
                    : projectMap.get("LEREP_CERTTYPE").toString();
            fileHolderCodeType = getFileHolderCodeType(LEREP_CERTTYPE);
            map.put("fileHolderName", fileHolderName);
            map.put("fileHolderCode", fileHolderCode);
            map.put("fileHolderCodeType", fileHolderCodeType);
        }
        StringBuffer sql2 = new StringBuffer("select s.PROJECT_NAME from SPGL_XMJBXXB s where s.PROJECT_CODE=?");
        List<Map<String, Object>> jbxxList = dao.findBySql(sql2.toString(), params.toArray(), null);
        Map<String, Object> jbxxMap = jbxxList.get(0);
        String PROJECT_NAME = jbxxMap.get("PROJECT_NAME") == null ? "" : jbxxMap.get("PROJECT_NAME").toString();
        map.put("PROJECT_CODE", projectCode);
        map.put("PROJECT_NAME", PROJECT_NAME);
        // 根据省网事项编码获取归档
        StringBuffer sql3 = new StringBuffer("select s.* from T_EFILE_CATALOG s where s.SWB_ITEM_CODE=?");
        List<Object> params2 = new ArrayList<Object>();
        params2.add(swbCode);
        List<Map<String, Object>> efileCateLog = dao.findBySql(sql3.toString(), params2.toArray(), null);
        if (null != efileCateLog && efileCateLog.size() > 0) {
            Map<String, Object> materMap = efileCateLog.get(0);
            map.put("MATER_NAME", materMap.get("MATER_NAME") == null ? "" : materMap.get("MATER_NAME").toString());
            map.put("MATER_CLSMLX",
                    materMap.get("MATER_CLSMLX") == null ? "" : materMap.get("MATER_CLSMLX").toString());
            map.put("KZ_WJLYL", materMap.get("KZ_WJLYL") == null ? "" : materMap.get("KZ_WJLYL").toString());
            map.put("KZ_MJ", materMap.get("KZ_MJ") == null ? "" : materMap.get("KZ_MJ").toString());
            map.put("KZ_WJLXBM", materMap.get("KZ_WJLXBM") == null ? "" : materMap.get("KZ_WJLXBM").toString());
            map.put("KZ_XKDJ", materMap.get("KZ_XKDJ") == null ? "" : materMap.get("KZ_XKDJ").toString());
            map.put("KZ_WJLXMC", materMap.get("KZ_WJLXMC") == null ? "" : materMap.get("KZ_WJLXMC").toString());
            map.put("KZ_WJMLBM", materMap.get("KZ_WJMLBM") == null ? "" : materMap.get("KZ_WJMLBM").toString());
            map.put("KZ_WJBGQX", materMap.get("KZ_WJBGQX") == null ? "" : materMap.get("KZ_WJBGQX").toString());
            map.put("KZ_MATERID", materMap.get("KZ_MATERID") == null ? "" : materMap.get("KZ_MATERID").toString());
        }
    }

    /**
     * 
     * @Description 设置不动产办结结果文件结构化信息
     * @author Luffy Cai
     * @date 2021年8月22日
     * @param map
     * @param projectCode
     * @param swbCode     void
     */
    @SuppressWarnings("unchecked")
    private void setBdcResultJsonInfo(Map<String, Object> map, String exeId, String swbCode) {
        String fileHolderName = "";
        String fileHolderCode = "";
        String fileHolderCodeType = "";
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select d.* from JBPM6_EXECUTION d where d.exe_id=? ");
        params.add(exeId);
        List<Map<String, Object>> userList = dao.findBySql(sql.toString(), params.toArray(), null);
        List<Map<String, Object>> decryptList = encryptionService.listDecrypt(userList, "JBPM6_EXECUTION");
        if (decryptList != null && decryptList.size() > 0) {
            Map<String, Object> userInfo = decryptList.get(0);
            fileHolderName = userInfo.get("SQJG_NAME") == null ? "" : userInfo.get("SQJG_NAME").toString();
            fileHolderCode = userInfo.get("SQJG_CODE") == null ? "" : userInfo.get("SQJG_CODE").toString();
            fileHolderCodeType = userInfo.get("SQJG_TYPE") == null ? "" : userInfo.get("SQJG_TYPE").toString();
            if (StringUtils.isNotEmpty(fileHolderCodeType)) {
                if (fileHolderCodeType.equals("QY")) {// 统一社会信用代码
                    fileHolderCodeType = "001";
                } else {
                    fileHolderCodeType = "099";
                }
            }
            map.put("fileHolderName", fileHolderName);
            map.put("fileHolderCode", fileHolderCode);
            map.put("fileHolderCodeType", fileHolderCodeType);
        }
        // 根据省网事项编码获取归档
        StringBuffer sql3 = new StringBuffer("select s.* from T_EFILE_CATALOG s where s.SWB_ITEM_CODE=?");
        List<Object> params2 = new ArrayList<Object>();
        params2.add(swbCode);
        List<Map<String, Object>> efileCateLog = dao.findBySql(sql3.toString(), params2.toArray(), null);
        if (null != efileCateLog && efileCateLog.size() > 0) {
            Map<String, Object> materMap = efileCateLog.get(0);
            map.put("MATER_NAME", materMap.get("MATER_NAME") == null ? "" : materMap.get("MATER_NAME").toString());
            map.put("MATER_CLSMLX",
                    materMap.get("MATER_CLSMLX") == null ? "" : materMap.get("MATER_CLSMLX").toString());
            map.put("KZ_WJLYL", materMap.get("KZ_WJLYL") == null ? "" : materMap.get("KZ_WJLYL").toString());
            map.put("KZ_MJ", materMap.get("KZ_MJ") == null ? "" : materMap.get("KZ_MJ").toString());
            map.put("KZ_WJLXBM", materMap.get("KZ_WJLXBM") == null ? "" : materMap.get("KZ_WJLXBM").toString());
            map.put("KZ_XKDJ", materMap.get("KZ_XKDJ") == null ? "" : materMap.get("KZ_XKDJ").toString());
            map.put("KZ_WJLXMC", materMap.get("KZ_WJLXMC") == null ? "" : materMap.get("KZ_WJLXMC").toString());
            map.put("KZ_WJMLBM", materMap.get("KZ_WJMLBM") == null ? "" : materMap.get("KZ_WJMLBM").toString());
            map.put("KZ_WJBGQX", materMap.get("KZ_WJBGQX") == null ? "" : materMap.get("KZ_WJBGQX").toString());
            map.put("KZ_MATERID", materMap.get("KZ_MATERID") == null ? "" : materMap.get("KZ_MATERID").toString());
        }
    }

    /**
     * @Description 获取持有主体代码类型代码
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param fileHolderCodeType
     * @return String
     */
    private String getFileHolderCodeType(String fileHolderCodeType) {
        String type = "001";
        if (StringUtils.isNotEmpty(fileHolderCodeType)) {
            if (fileHolderCodeType.equals("A05300")) {// 统一社会信用代码
                type = "001";
            } else {
                type = "099";
            }
        }
        return type;
    }

    /**
     * 
     * @Description 获取证件类型字典表
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param code
     * @return String
     */
    private String getCardType(String type) {
        String cardType = "";
        if (StringUtils.isNotEmpty(type)) {
            if (type.equals("SF")) {// 身份证
                cardType = "111";
            } else if (type.equals("JGDM")) {// 组织机构代码证
                cardType = "099";
            } else if (type.equals("JG")) {// 军官证
                cardType = "114";
            } else if (type.equals("SB")) {// 士兵证
                cardType = "118";
            } else if (type.equals("HZ")) {// 护照
                cardType = "414";
            } else if (type.equals("TWTX")) {// 台湾居民来往大陆通行证
                cardType = "511";
            } else if (type.equals("GATX")) {// 港澳居民来往内地通行证
                cardType = "516";
            } else {// 其他
                cardType = "099";
            }
        }
        return cardType;
    }

    /**
     * 
     * @Description 获取事项类型字典表
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param type
     * @return String
     */
    private String getTaskType(String type) {
        String taskType = "";
        if (StringUtils.isNotEmpty(type)) {
            if (type.equals("XK")) {// 行政许可
                taskType = "01";
            } else if (type.equals("XF")) {// 行政处罚
                taskType = "02";
            } else if (type.equals("XZ")) {// 行政强制
                taskType = "03";
            } else if (type.equals("XS")) {// 行政征收
                taskType = "04";
            } else if (type.equals("XG")) {// 行政给付
                taskType = "05";
            } else if (type.equals("XC")) {// 行政检查
                taskType = "06";
            } else if (type.equals("QR")) {// 行政确认
                taskType = "07";
            } else if (type.equals("JL")) {// 行政奖励
                taskType = "08";
            } else if (type.equals("CJ")) {// 行政裁决
                taskType = "09";
            } else if (type.equals("GF")) {// 公共服务
                taskType = "20";
            } else {// 其他行政权力
                taskType = "10";
            }
        }
        return taskType;
    }

    /**
     * 
     * @Description 事项与材料目录获取接口
     * @author Luffy Cai
     * @date 2021年8月10日
     * @param item_id
     * @param projectName
     * @param projectCode
     * @return Map<String,Object>
     */
    public Map<String, Object> getEfileProjectCatalog(String item_id, String projectName, String projectCode) {
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getClientParams("getEfileShare", gcjsxmGrantCode);
        params.put("interfaceName", "getEfileProjectCatalog");
        params.put("projectName", projectName);
        params.put("projectCode", projectCode);
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                List<Map<String, Object>> dataList = JSON.parseObject(dataMap.get("dataList").toString(), List.class);
                // 查找事项材料列表
                List<Map<String, Object>> materList = findItemMater(item_id);
                if (materList != null && materList.size() > 0) {
                    for (Map<String, Object> mater : materList) {
                        String KZ_WJMLBM = mater.get("KZ_WJMLBM").toString();
                        String MATER_ID = mater.get("MATER_ID").toString();
                        for (Map<String, Object> data : dataList) {
                            String fileCatalogCode = data.get("fileCatalogCode").toString();
                            if (KZ_WJMLBM.equals(fileCatalogCode)) {
                                String fileTypeName = data.get("fileTypeName").toString();
                                String fileTypeCode = data.get("fileTypeCode").toString();
                                String fileCredibilityLevel = data.get("fileCredibilityLevel").toString();
                                String fileRetentionPeriod = data.get("fileRetentionPeriod").toString();
                                String fileSecurityClassification = data.get("fileSecurityClassification").toString();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("KZ_WJLXMC", fileTypeName);
                                map.put("KZ_WJLXBM", fileTypeCode);
                                map.put("KZ_XKDJ", fileCredibilityLevel);
                                map.put("KZ_MJ", fileSecurityClassification);
                                map.put("KZ_WJBGQX", fileRetentionPeriod);
                                dao.saveOrUpdate(map, "T_WSBS_APPLYMATER", MATER_ID);
                            }
                        }
                    }
                }
            } else {// 失败
                log.error("调用可信电子文件共享服务接口失败！返回错误：" + message);
            }
        }
        return result;
    }

}
