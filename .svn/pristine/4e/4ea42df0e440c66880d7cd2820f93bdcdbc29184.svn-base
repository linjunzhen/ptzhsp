/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONArray;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.project.dao.ProjectApplyDao;
import net.evecom.platform.project.service.ProjectSgxkService;

/**
 * 
 * 描述 施工许可
 * 
 * @author Rider Chen
 * @created 2021年3月16日 上午10:31:40
 */
@SuppressWarnings("rawtypes")
@Service("projectSgxkService")
public class ProjectSgxkServiceImpl extends BaseServiceImpl implements ProjectSgxkService {
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectApplyDao dao;
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectSgxkServiceImpl.class);

    @Override
    protected GenericDao getEntityDao() {
        // TODO Auto-generated method stub
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> beforeSgxkCertificateSubmit(Map<String, Object> flowDatas) throws Exception {
        // TODO Auto-generated method stub
        String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        if (StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> result = null;
                Properties properties = FileUtil.readProperties("project.properties");
                String devbaseUrl = properties.getProperty("devbaseUrl");
                String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
                // 获取公共参数
                Map<String, Object> params = getParams(exeId, "certificateData", gcjsxmGrantCode);
                // 调用电子证照数据提交接口
                params.put("interfaceName", "submitCertificateData");
                params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                params.put("certificateName", "中华人民共和国建筑工程施工许可证");
                params.put("versionNumber", "2019V7.0");
                // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                        new String[] { busRecordId });
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "SGXKZZSC" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = new HashMap<String, Object>();
                setSgxkCertificate(sgxk, "JSDW");// 设置建设单位信息
                setSgxkCertificate(sgxk, "KCDW");// 设置勘察单位信息
                setSgxkCertificate(sgxk, "SJDW");// 设置设计单位信息
                setSgxkCertificate(sgxk, "SGDW");// 设置施工单位信息
                setSgxkCertificate(sgxk, "JLDW");// 设置监理单位信息
                setSgxkCertificate(sgxk, "DJDW");// 设置代建、工程总承包（EPC）、PPP等单位信息

                String dwgc = sgxk.get("DWGC_JSON") == null ? "" : sgxk.get("DWGC_JSON").toString();// 单位工程JSON
                String zjgc = sgxk.get("ZJGC_JSON") == null ? "" : sgxk.get("ZJGC_JSON").toString();// 桩基工程JSON
                String sbgc = sgxk.get("SBGC_JSON") == null ? "" : sgxk.get("SBGC_JSON").toString();// 上部工程JSON

                List<Map<String, Object>> fjgcList = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> szgcList = new ArrayList<Map<String, Object>>();
                if (StringUtils.isNotEmpty(dwgc)) {
                    List<Map<String, Object>> gcList = JSON.parseObject(dwgc, List.class);
                    getGclist(fjgcList, szgcList, gcList);
                }
                if (StringUtils.isNotEmpty(zjgc)) {
                    List<Map<String, Object>> gcList = JSON.parseObject(zjgc, List.class);
                    getGclist(fjgcList, szgcList, gcList);
                }

                if (StringUtils.isNotEmpty(sbgc)) {
                    List<Map<String, Object>> gcList = JSON.parseObject(sbgc, List.class);
                    getGclist(fjgcList, szgcList, gcList);
                }
                sgxk.put("fjgcList", fjgcList);
                sgxk.put("szgcList", szgcList);
                sgxk.put("CONSTRNUM", flowDatas.get("CONSTRNUM"));
                sgxk.put("RELEASEDATE", flowDatas.get("RELEASEDATE"));
                sgxk.put("CERT_NUM", flowDatas.get("CERT_NUM"));
                configMap.put("sgxk", sgxk);
                StringBuffer jsonInfo = this.makeData(configMap, contentStr);
                Map<String, Object> jsonInfoMap = JSON.parseObject(jsonInfo.toString(), Map.class);
                params.put("jsonInfo", JSON.toJSONString(jsonInfoMap));
                result = TokenUtil.doPost(devbaseUrl, params);
                if (null != result && result.size() > 0) {
                    Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                    String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                    String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                    if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                        Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                        String certificateIdentifier = dataMap.get("certificateIdentifier") == null ? ""
                                : dataMap.get("certificateIdentifier").toString();
                        // 根据证照标识获取证照信息
                        params = getParams(exeId, "getShare", gcjsxmGrantCode);
                        params.put("interfaceName", "getCertificateFileContentByIdentifier");
                        params.put("certificateIdentifier", certificateIdentifier);
                        result = TokenUtil.doPost(devbaseUrl, params);
                        if (null != result && result.size() > 0) {
                            headMap = (Map<String, Object>) result.get("head");
                            status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                            message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                                dataMap = (Map<String, Object>) result.get("data");
                                String content = dataMap.get("content") == null ? ""
                                        : dataMap.get("content").toString();
                                String fileType = dataMap.get("fileType") == null ? ""
                                        : dataMap.get("fileType").toString();
                                // 上传证照文件
                                String fileResult = uploadBase64File(content, fileType, busTableName, busRecordId);
                                if (StringUtils.isNotEmpty(fileResult)) {
                                    Map<String, Object> resultMap = JSON.parseObject(fileResult, Map.class);
                                    Map<String, Object> data = (Map) resultMap.get("data");
                                    flowDatas.put("CERTIFICATEIDENTIFIER", certificateIdentifier);
                                    flowDatas.put("CERTIFICATEIDENTIFIERFILEID", data.get("fileId"));
                                    flowDatas.put("CERTIFICATEIDENTIFIERFILEPATH", data.get("filePath"));
                                    log.info("施工许可申报号：" + exeId + "-证照生成成功！");
                                }
                            } else {
                                flowDatas.put("SIGN_FLAG", false);
                                flowDatas.put("SIGN_MSG", message);
                                throw new Exception(message);
                            }
                        } else {
                            flowDatas.put("SIGN_FLAG", false);
                            flowDatas.put("SIGN_MSG", "接口返回数据为空，无法提交");
                            throw new Exception("接口返回数据为空，无法提交");
                        }
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                        throw new Exception(message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "接口返回数据为空，无法提交");
                    throw new Exception("接口返回数据为空，无法提交");
                }

            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }

    private void getGclist(List<Map<String, Object>> fjgcList, List<Map<String, Object>> szgcList,
            List<Map<String, Object>> gcList) {
        int i = 1;
        int j = 1;
        for (Map<String, Object> map : gcList) {
            String SINGLEPROMAINTYPE = map.get("SINGLEPROMAINTYPE") == null ? ""
                    : map.get("SINGLEPROMAINTYPE").toString();
            map.put("ORDERNUM", i);
            if (SINGLEPROMAINTYPE.equals("01")) {
                fjgcList.add(map);
            } else {
                map.put("SZGCORDERNUM", j);
                szgcList.add(map);
                map.put("ARCHAREA", map.get("MUNILENGTH") == null ? "": map.get("MUNILENGTH").toString());
                map.put("STRUCTUPFLOORAREA_DWGC","0");
                map.put("STRUCTDWFLOORAREA_DWGC", "0");
                map.put("STRUCTUPFLOORNUM", "0");
                map.put("STRUCTDWFLOORNUM", "0");
                map.put("ORDERNUM", i);
                fjgcList.add(map);
                j++;
            }
            i++;
        }
    }

    /**
     * 
     * 描述 设置施工许可证照相关信息
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午3:22:12
     * @param map
     * @param CERTIFICATEIDENTIFIERFILEPATH
     */
    @SuppressWarnings("unchecked")
    private void setSgxkCertificate(Map<String, Object> map, String key) {
        String json = map.get(key + "_JSON") == null ? "" : map.get(key + "_JSON").toString();
        if (StringUtils.isNotEmpty(json) && json.indexOf("<!") == -1) {
            if (json.indexOf("[") == 0) {
                List<Map<String, Object>> dwList = JSON.parseObject(json, List.class);
                StringBuffer CORPNAME = new StringBuffer();
                StringBuffer CORPCREDITCODE = new StringBuffer();
                StringBuffer PERSONNAME = new StringBuffer();
                StringBuffer PERSONIDCARD = new StringBuffer();
                for (Map<String, Object> map2 : dwList) {
                    CORPNAME.append(map2.get("CORPNAME")).append(",");
                    CORPCREDITCODE.append(map2.get("CORPCREDITCODE")).append(",");
                    PERSONNAME.append(map2.get("PERSONNAME")).append(",");
                    PERSONIDCARD.append(map2.get("PERSONIDCARD")).append(",");
                }
                map.put(key + "_CORPNAME", CORPNAME.deleteCharAt(CORPNAME.length() - 1));
                map.put(key + "_CORPCREDITCODE", CORPCREDITCODE.deleteCharAt(CORPCREDITCODE.length() - 1));
                map.put(key + "_PERSONNAME", PERSONNAME.deleteCharAt(PERSONNAME.length() - 1));
                map.put(key + "_PERSONIDCARD", PERSONIDCARD.deleteCharAt(PERSONIDCARD.length() - 1));
            } else {
                Map<String, Object> map2 = JSON.parseObject(json, Map.class);
                StringBuffer CORPNAME = new StringBuffer();
                StringBuffer CORPCREDITCODE = new StringBuffer();
                StringBuffer PERSONNAME = new StringBuffer();
                StringBuffer PERSONIDCARD = new StringBuffer();
                CORPNAME.append(map2.get("CORPNAME")).append(",");
                CORPCREDITCODE.append(map2.get("CORPCREDITCODE")).append(",");
                PERSONNAME.append(map2.get("PERSONNAME")).append(",");
                PERSONIDCARD.append(map2.get("PERSONIDCARD")).append(",");
                map.put(key + "_CORPNAME", CORPNAME.deleteCharAt(CORPNAME.length() - 1));
                map.put(key + "_CORPCREDITCODE", CORPCREDITCODE.deleteCharAt(CORPCREDITCODE.length() - 1));
                map.put(key + "_PERSONNAME", PERSONNAME.deleteCharAt(PERSONNAME.length() - 1));
                map.put(key + "_PERSONIDCARD", PERSONIDCARD.deleteCharAt(PERSONIDCARD.length() - 1));
            }
        }
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
     * 描述 获取公共的参数
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午5:59:50
     * @param exeId
     * @param servicecode
     * @param gcjsxmGrantCode
     * @return
     */
    private Map<String, Object> getParams(String exeId, String servicecode, String gcjsxmGrantCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("servicecode", servicecode);
        params.put("grantcode", gcjsxmGrantCode);
        params.put("areaCode", "35012800000");
        params.put("areaName", "平潭综合实验区");
        params.put("deptName", "平潭综合实验区行政审批局");
        params.put("deptCode", "11350128345071904J");
        params.put("operName", "evecom");
        params.put("operId", "evecom");
        params.put("systemName", "平潭综合实验区综合服务平台");
        params.put("taskCode", "11350128345071904JXK000018");
        params.put("taskName", "建设工程施工许可证核发");
        params.put("projectNo", exeId);
        return params;
    }

    /**
     * 
     * 描述 Base64文件上传
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午4:19:30
     * @param base64Code
     * @param fileType
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public String uploadBase64File(String base64Code, String fileType, String busTableName, String busRecordId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String result = "";
        String unid = UUIDGenerator.getUUID();
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "ddzzwjsc");// 上传人ID
                param.put("uploaderName", "电子证照文件上传"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", unid + "." + fileType);// 上传附件名
                param.put("attachKey", "");// 材料编码
                param.put("busTableName", busTableName);// 业务表名
                param.put("busRecordId", busRecordId);// 业务表ID
                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
                return result;
            } catch (Exception e) {
                log.error("证照文件上传错误，busRecordId：" + busRecordId);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> beforeSgxkDataSubmit(Map<String, Object> flowDatas) throws Exception {
            String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
            String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
            String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
            // 获取业务表名称
            String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
            if (StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
                if (StringUtils.isNotEmpty(exeId)) {
                    Map<String, Object> result = null;
                    Properties properties = FileUtil.readProperties("project.properties");
                    String devbaseUrl = properties.getProperty("devbaseUrl");
                    String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
                    // 获取公共参数
                    Map<String, Object> params = getParams(exeId, "certificateData", gcjsxmGrantCode);
                    // 调用电子证照数据提交接口
                    params.put("interfaceName", "submitCertificateData");
                    params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                    params.put("certificateName", "中华人民共和国建筑工程施工许可证");
                    params.put("versionNumber", "2019V7.0");
                    // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                    Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                            new String[] { busRecordId });
                    Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                            new String[] { "SGXKZZSC" });
                    String contentStr = (String) dataAbutment.get("CONFIG_XML");
                    Map<String, Object> configMap = new HashMap<String, Object>();
                    setSgxkCertificate(sgxk, "JSDW");// 设置建设单位信息
                    setSgxkCertificate(sgxk, "KCDW");// 设置勘察单位信息
                    setSgxkCertificate(sgxk, "SJDW");// 设置设计单位信息
                    setSgxkCertificate(sgxk, "SGDW");// 设置施工单位信息
                    setSgxkCertificate(sgxk, "JLDW");// 设置监理单位信息
                    setSgxkCertificate(sgxk, "DJDW");// 设置代建、工程总承包（EPC）、PPP等单位信息
                    String SGXK_SBXMLX = sgxk.get("SGXK_SBXMLX")==null?"": sgxk.get("SGXK_SBXMLX").toString();//申报项目类型

                    String dwgc = sgxk.get("DWGC_JSON") == null ? "" : sgxk.get("DWGC_JSON").toString();// 单位工程JSON
                    String zjgc = sgxk.get("ZJGC_JSON") == null ? "" : sgxk.get("ZJGC_JSON").toString();// 桩基工程JSON
                    String sbgc = sgxk.get("SBGC_JSON") == null ? "" : sgxk.get("SBGC_JSON").toString();// 上部工程JSON

                    List<Map<String, Object>> fjgcList = new ArrayList<Map<String, Object>>();
                    List<Map<String, Object>> szgcList = new ArrayList<Map<String, Object>>();
                    if(SGXK_SBXMLX.equals("1")) {
                        if (StringUtils.isNotEmpty(dwgc)) {
                            List<Map<String, Object>> gcList = JSON.parseObject(dwgc, List.class);
                            getGclist(fjgcList, szgcList, gcList);
                        }
                    }
                    if(SGXK_SBXMLX.equals("2")) {
                        if (StringUtils.isNotEmpty(zjgc)) {
                            List<Map<String, Object>> gcList = JSON.parseObject(zjgc, List.class);
                            getGclist(fjgcList, szgcList, gcList);
                        }
                    }
                    if(SGXK_SBXMLX.equals("3")) {
                        if (StringUtils.isNotEmpty(sbgc)) {
                            List<Map<String, Object>> gcList = JSON.parseObject(sbgc, List.class);
                            getGclist(fjgcList, szgcList, gcList);
                        }
                    }
                    sgxk.put("fjgcList", fjgcList);
                    sgxk.put("szgcList", szgcList);
                    sgxk.put("CONSTRNUM", flowDatas.get("CONSTRNUM"));
                    sgxk.put("RELEASEDATE", flowDatas.get("RELEASEDATE"));
                    sgxk.put("CERT_NUM", flowDatas.get("CERT_NUM"));
                    configMap.put("sgxk", sgxk);
                    StringBuffer jsonInfo = this.makeData(configMap, contentStr);
                    Map<String, Object> jsonInfoMap = JSON.parseObject(jsonInfo.toString(), Map.class);
                    params.put("jsonInfo", JSON.toJSONString(jsonInfoMap));
                    result = TokenUtil.doPost(devbaseUrl, params);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                        String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                            String certificateIdentifier = dataMap.get("certificateIdentifier") == null ? ""
                                    : dataMap.get("certificateIdentifier").toString();
                            flowDatas.put("CERTIFICATEIDENTIFIER", certificateIdentifier);
                            log.info("施工许可申报号：" + exeId + "-证照数据推送成功！");
                        } else {
                            flowDatas.put("SIGN_FLAG", false);
                            flowDatas.put("SIGN_MSG", message);
                            throw new Exception(message);
                        }
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", "接口返回数据为空，无法提交");
                        throw new Exception("接口返回数据为空，无法提交");
                    }

                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                    throw new Exception("办件编号为空，无法提交");
                }
            }            
        return flowDatas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> beforeSgxkgetCertificateFile(Map<String, Object> flowDatas) throws Exception {
        // TODO Auto-generated method stub
        String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        if (StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> result = null;
                Properties properties = FileUtil.readProperties("project.properties");
                String devbaseUrl = properties.getProperty("devbaseUrl");
                String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");

                Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                        new String[] { busRecordId });
                if (null != sgxk && sgxk.size() > 0) {
                    String certificateIdentifier = sgxk.get("CERTIFICATEIDENTIFIER") == null ? ""
                            : sgxk.get("CERTIFICATEIDENTIFIER").toString();
                    if (StringUtils.isNotEmpty(certificateIdentifier)) {
                        // 获取公共参数
                        Map<String, Object> params = getParams(exeId, "getShare", gcjsxmGrantCode);
                        params.put("interfaceName", "getCertificateFileContentByIdentifier");
                        params.put("certificateIdentifier", certificateIdentifier);
                        // 根据证照标识获取证照信息
                        result = TokenUtil.doPost(devbaseUrl, params);
                        if (null != result && result.size() > 0) {
                            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                                String content = dataMap.get("content") == null ? ""
                                        : dataMap.get("content").toString();
                                String fileType = dataMap.get("fileType") == null ? ""
                                        : dataMap.get("fileType").toString();
                                // 上传证照文件
                                String fileResult = uploadBase64File(content, fileType, busTableName, busRecordId);
                                if (StringUtils.isNotEmpty(fileResult)) {
                                    Map<String, Object> resultMap = JSON.parseObject(fileResult, Map.class);
                                    Map<String, Object> data = (Map) resultMap.get("data");
                                    flowDatas.put("CERTIFICATEIDENTIFIER", certificateIdentifier);
                                    flowDatas.put("CERTIFICATEIDENTIFIERFILEID", data.get("fileId"));
                                    flowDatas.put("CERTIFICATEIDENTIFIERFILEPATH", data.get("filePath"));
                                    log.info("施工许可申报号：" + exeId + "-证照生成成功！");
                                }
                            } else {
                                flowDatas.put("SIGN_FLAG", false);
                                flowDatas.put("SIGN_MSG", message);
                                throw new Exception(message);
                            }
                        } else {
                            flowDatas.put("SIGN_FLAG", false);
                            flowDatas.put("SIGN_MSG", "接口返回数据为空，无法提交");
                            throw new Exception("接口返回数据为空，无法提交");
                        }
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", "证照标识为空，无法提交");
                        throw new Exception("证照标识为空，无法提交");
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "查询施工许可数据为空，无法提交");
                    throw new Exception("查询施工许可数据为空，无法提交");
                }
            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCertificatePreviewUrl(Map<String, Object> flowDatas) throws Exception {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        String exeId = flowDatas.get("EXE_ID") == null ? "" : flowDatas.get("EXE_ID").toString();
        String ywId = flowDatas.get("YW_ID") == null ? "" : flowDatas.get("YW_ID").toString();
        String BUILDCORPCODE = flowDatas.get("BUILDCORPCODE") == null ? "" : flowDatas.get("BUILDCORPCODE").toString();// 机构代码
        if(StringUtils.isEmpty(BUILDCORPCODE)) {
            String jsdwJson = flowDatas.get("JSDW_JSON") == null ? "" : flowDatas.get("JSDW_JSON").toString();
            List<Map<String, Object>> jsdwList = JSON.parseObject(jsdwJson, List.class);
            Map<String,Object> jsdwMap =jsdwList.get(0);
            BUILDCORPCODE = jsdwMap.get("CORPCREDITCODE") == null ? "" : jsdwMap.get("CORPCREDITCODE").toString();
        }
        result = getCertificateFileContentByIdentifier(BUILDCORPCODE, exeId, ywId);
        return result;
    }
    
    /**
     * 
     * @Description 根据机构代码获取电子证照信息
     * @author Luffy Cai
     * @date 2021年4月18日
     * @param Identifier
     * @param exeId
     * @return
     * @throws Exception Map<String,Object>
     */
    public Map<String, Object> getCertificateFileContentByIdentifier(String Identifier, String exeId, String ywId)
            throws Exception {
        Map<String, Object> result = null;
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        Map<String, Object> params = getParams(exeId, "getShare", gcjsxmGrantCode);
        params.put("interfaceName", "getLegalPersonListByHolderCode");
        params.put("certificateHolderCode", Identifier);
        //精确查询
        params.put("certificateTypeName", "中华人民共和国建筑工程施工许可证");
        // 根据证照标识获取证照信息
        result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                String dataList = dataMap.get("dataList") == null ? "" : dataMap.get("dataList").toString();
                List<Map> certificateList = JSONArray.parseArray(dataList, Map.class);
                if (certificateList.size() > 0 && certificateList != null) {
                    Map<String, Object> certificate = certificateList.get(0);
                    String certificateIdentifier = certificate.get("certificateIdentifier") == null ? ""
                            : certificate.get("certificateIdentifier").toString();
                    // 获取公共参数
                    Map<String, Object> params2 = getParams(exeId, "getShare", gcjsxmGrantCode);
                    params2.put("interfaceName", "getCertificateFileContentByIdentifier");
                    params2.put("certificateIdentifier", certificateIdentifier);
                    // 根据证照标识获取证照信息
                    result = TokenUtil.doPost(devbaseUrl, params2);
                    if (null != result && result.size() > 0) {
                        Map<String, Object> headMap2 = (Map<String, Object>) result.get("head");
                        String status2 = headMap2.get("status") == null ? "" : headMap2.get("status").toString();
                        String message2 = headMap2.get("message") == null ? "" : headMap2.get("message").toString();
                        if (StringUtils.isNotEmpty(status2) && status2.equals("0")) {// 调用成功，返回证照标识
                            Map<String, Object> dataMap2 = (Map<String, Object>) result.get("data");
                            String content = dataMap2.get("content") == null ? "" : dataMap2.get("content").toString();
                            String fileType = dataMap2.get("fileType") == null ? ""
                                    : dataMap2.get("fileType").toString();
                            // 上传证照文件
                            String fileResult = uploadBase64File(content, fileType, "T_BSFW_GCJSSGXK", ywId);
                            if (StringUtils.isNotEmpty(fileResult)) {
                                Map<String, Object> resultMap = JSON.parseObject(fileResult, Map.class);
                                Map<String, Object> data = (Map) resultMap.get("data");
                                result.put("CERTIFICATEIDENTIFIER", certificateIdentifier);
                                result.put("CERTIFICATEIDENTIFIERFILEID", data.get("fileId"));
                                result.put("CERTIFICATEIDENTIFIERFILEPATH", data.get("filePath"));
                                result.put("CERTIFICATEIDENTIFIERFILENAME", data.get("fileName"));
                                result.put("SIGN_FLAG", true);
                                log.info("施工许可申报号：" + exeId + "-证照生成成功！");
                            }
                        } else {
                            result.put("SIGN_FLAG", false);
                            result.put("SIGN_MSG", message2);
                        }
                    } else {
                        result.put("SIGN_FLAG", false);
                        result.put("SIGN_MSG", "接口返回数据为空，无法提交");
                    }
                }
            } else {
                result.put("SIGN_FLAG", false);
                result.put("SIGN_MSG", message);
            }
        } else {
            result.put("SIGN_FLAG", false);
            result.put("SIGN_MSG", "接口返回数据为空，无法提交");
        }

        return result;
    }
    
    

}
