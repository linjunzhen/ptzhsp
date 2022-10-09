/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.elicense.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.elicense.dao.ElectronicLicenseDao;
import net.evecom.platform.elicense.service.ElectronicLicenseService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.thread.DeclareMaterialSubmitRunnable;
import net.evecom.platform.thread.DeclareRegisterSubmitRunnable;
import net.evecom.platform.thread.DeclareResultCertificateSubmitRunnable;
import net.evecom.platform.thread.DeclareResultSubmitRunnable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述 电子证照业务相关service实现
 * 
 * @author Roy Li
 *
 */
@SuppressWarnings("rawtypes")
@Service("electronicLicenseService")
public class ElectronicLicenseServiceImpl extends BaseServiceImpl implements ElectronicLicenseService {
    
    /**
     * devbaseUrl
     */
    public static String devbaseUrl;
    /**
     * 授权码
     */
    public static String gcjsxmGrantCode;
    static {
        Properties properties = FileUtil.readProperties("project.properties");
        devbaseUrl = properties.getProperty("devbaseUrl");
        gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
    }

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ElectronicLicenseServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ElectronicLicenseDao dao;

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
        params.put("systemName", "平潭综合实验区审批平台监管办件推送");
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
        params.put("systemName", "平潭综合实验区审批平台监管办件推送");
        return params;
    }

    /**
     * 
     * @Description 获取证件类型字典表
     * @author Luffy Cai
     * @date 2021年7月21日
     * @param type
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
     * @Description 电子证照-申报登记信息提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    @Override
    public void declareRegisterSubmit() {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findElicenseRegInfoList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("电子证照-申报登记信息提交数据量为:" + itemDatas.size());
        }
        for (Map<String, Object> itemData : itemDatas) {
            try {
                // runnable(new DeclareRegisterSubmitRunnable(itemData));
                executor.execute(new DeclareRegisterSubmitRunnable(itemData));
            } catch (Exception e) {
                log.error("电子证照-申报登记信息提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
            }
        }
        executor.shutdown();
    }
    
    /**
     * 
     * @Description 电子证照-获取申报登记信息列表
     * @author Roy Li
     * @date 2021年8月19日
     * @return List<Map<String,Object>>
     */
    private List<Map<String, Object>> findElicenseRegInfoList() {
        //只推送已办结且有办件结果信息的记录
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,E.SUBJECT,E.BUS_RECORDID,");
        sql.append(" E.BUS_TABLENAME,E.PROJECT_CODE,E.BSYHLX,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQRMC,");
        sql.append(" E.CREATE_TIME,E.END_TIME,E.SQJG_CODE,E.SQRZJLX,E.SQRSFZ,S.ZBCS,S.IMPL_DEPART,S.SSBMBM,S.ITEM_ID,");
        sql.append(" S.ITEM_CODE,S.C_VERSION,S.ITEM_NAME,S.SWB_ITEM_CODE,S.SXLX,S.SXXZ FROM JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE = S.ITEM_CODE");
        sql.append(" WHERE (E.RUN_STATUS='2' OR E.RUN_STATUS='3') AND E.DEF_ID!='2c90b38a5ca1392c015ca14c9a960018' ");
        sql.append(" AND E.EXE_ID IN (SELECT EXE_ID FROM JBPM6_FLOW_RESULT) "); // JBPM6_EXECUTION与JBPM6_FLOW_RESULT可能有一对多情况，但只是附件，证照最多只有一张
        sql.append(" AND (E.LICENSE_PUSH_STATUS=0 OR E.LICENSE_PUSH_STATUS=2) ");
        sql.append(" AND SUBSTR(E.CREATE_TIME,0,7) >='2021-09' ");
        //sql.append(" AND (E.EXE_ID='FJPT210830772022' OR E.EXE_ID='FJPT210901775151' OR E.EXE_ID='FJPT200605451359') ");
        //log.info("电子证照-申报登记信息sql:" + sql);
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * @Description 电子证照-申报登记信息提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    @SuppressWarnings("unchecked")
    public void declareRegisterSubmit(Map<String, Object> itemData) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String SUBJECT = itemData.get("SUBJECT") == null ? "" : itemData.get("SUBJECT").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE : itemData.get("SWB_ITEM_CODE").toString();
        String CREATE_TIME = itemData.get("CREATE_TIME") == null ? "" : itemData.get("CREATE_TIME").toString();
        String ZBCS = itemData.get("ZBCS") == null ? "" : itemData.get("ZBCS").toString();
        String SSBMBM = itemData.get("SSBMBM") == null ? "" : itemData.get("SSBMBM").toString();
        String IMPL_DEPART = itemData.get("IMPL_DEPART") == null ? ZBCS : itemData.get("IMPL_DEPART").toString();
        String BSYHLX = itemData.get("BSYHLX") == null ? "" : itemData.get("BSYHLX").toString();
        
        ArrayList<Object> param = new ArrayList<>();
        param.add(EXE_ID);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.*,Z.DIC_NAME JBR_ZJNAME FROM JBPM6_EXECUTION T ");
        sql.append(" LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY WHERE TYPE_CODE = 'DocumentType') Z ");
        sql.append(" ON Z.DIC_CODE = T.JBR_ZJLX ");
        sql.append(" WHERE T.EXE_ID =? ");
        List<Map<String, Object>> executionList = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> decryptList = encryptionService.listDecrypt(executionList, "JBPM6_EXECUTION");
        Map<String, Object> exeMap = decryptList.get(0);
        String cardType = "";
        String applicationObjectName = "";
        String certificateNumber="";
        if("2".equals(BSYHLX)) {  //接口定义的法人为1
            BSYHLX = "1";
            cardType = "001";
            applicationObjectName = exeMap.get("SQJG_NAME") == null ? "" : exeMap.get("SQJG_NAME").toString();
            certificateNumber = exeMap.get("SQJG_CODE") == null ? "" : exeMap.get("SQJG_CODE").toString();
        }else {
            BSYHLX = "0";
            String SQRZJLX = exeMap.get("SQRZJLX") == null ? "" : exeMap.get("SQRZJLX").toString();
            cardType = getCardType(SQRZJLX);
            applicationObjectName = exeMap.get("SQRMC") == null ? "" : exeMap.get("SQRMC").toString();
            certificateNumber = exeMap.get("SQRSFZ") == null ? "" : exeMap.get("SQRSFZ").toString();
        }
        String operatorName = exeMap.get("JBR_NAME") == null ? "" : exeMap.get("JBR_NAME").toString();
        String operatorCerType = exeMap.get("JBR_ZJLX") == null ? "" : exeMap.get("JBR_ZJLX").toString();
        operatorCerType = getCardType(operatorCerType);
        String operatorCerName = exeMap.get("JBR_ZJNAME") == null ? "" : exeMap.get("JBR_ZJNAME").toString();
        String operatorCerNumber = exeMap.get("JBR_ZJHM") == null ? "" : exeMap.get("JBR_ZJHM").toString();
        
        //生成报文参数
        Map<String, Object> params = getParams("declareRegisterSubmit", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "declareRegisterSubmit");
        params.put("fromAreaCode", "350128"); // 发送方所属地区编码
        params.put("fromAreaName", "平潭综合实验区"); // 发送方所属地区名称
        params.put("receiveSysCode", "350000"); // 接收系统代码
        params.put("receiveSysName", "福建省网上办事大厅"); // 接收系统名称
        params.put("projectItemCode", SWB_ITEM_CODE); // 审批事项编码
        params.put("projectItemName", ITEM_NAME); // 审批事项名称
        params.put("applicationName", SUBJECT); // 申报名称
        params.put("applicationNumber", EXE_ID); // 申报号
        params.put("applicationTime", CREATE_TIME); // 申报时间yyyy-MM-dd HH:mm:ss
        params.put("handleDeptCode", SSBMBM); // 实施部门编码
        params.put("handleDeptName", IMPL_DEPART); // 实施部门名称
        params.put("applicationObjectType", BSYHLX); // 申报对象类型 0：个人，1：法人
        params.put("certificateType", cardType); // 证件类型代码（企业证照类型代码）
        params.put("applicationObjectName", applicationObjectName); // 申请对象名称（机构名称）
        params.put("certificateNumber", certificateNumber); // 证件号码（机构代码：统一社会信用代码）
        params.put("operatorName", operatorName); // 经办人名称
        params.put("operatorCerType", operatorCerType); // 经办人证件类型
        params.put("operatorCerName", operatorCerName); // 经办人证件名称
        params.put("operatorCerNumber", operatorCerNumber); // 经办人证件号码
        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                info.put("LICENSE_PUSH_STATUS", 1);
                log.info("申报号：" + EXE_ID + "申报登记信息推送成功！");
            } else {// 失败
                info.put("LICENSE_PUSH_STATUS", 2);
                log.error("申报号：" + EXE_ID + "申报登记信息提交推送失败！返回错误：" + message);
            }
            dao.saveOrUpdate(info, "JBPM6_EXECUTION", EXE_ID);
        }
    }

    /**
     * 
     * @Description 电子证照-申报登记信息材料提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    @Override
    public void declareMaterialSubmit() {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findElicenseRegMaterialList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("电子证照-申报登记信息材料提交数据量为:" + itemDatas.size());
        }
        for (Map<String, Object> itemData : itemDatas) {
            try {
                // runnable(new DeclareMaterialSubmitRunnable(itemData));
                executor.execute(new DeclareMaterialSubmitRunnable(itemData));
            } catch (Exception e) {
                log.error("电子证照-申报登记信息材料提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
            }
        }
        executor.shutdown();
    }
    
    /**
     * 
     * @Description 电子证照-获取申报登记信息材料列表
     * @author Roy Li
     * @date 2021年8月19日
     * @return List<Map<String,Object>>
     */
    private List<Map<String, Object>> findElicenseRegMaterialList() {
        //只推送已办结且有办件结果信息的记录
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,E.SUBJECT,E.BUS_RECORDID,");
        sql.append(" E.BUS_TABLENAME,E.PROJECT_CODE,E.BSYHLX,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQRMC,");
        sql.append(" E.CREATE_TIME,E.END_TIME,E.SQJG_CODE,E.SQRZJLX,E.SQRSFZ,S.ZBCS,S.IMPL_DEPART,S.SSBMBM,S.ITEM_ID,");
        sql.append(" S.ITEM_CODE,S.C_VERSION,S.ITEM_NAME,S.SWB_ITEM_CODE,S.SXLX,S.SXXZ FROM JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE = S.ITEM_CODE");
        sql.append(" WHERE (E.RUN_STATUS='2' OR E.RUN_STATUS='3') AND E.DEF_ID!='2c90b38a5ca1392c015ca14c9a960018' ");
        sql.append(" AND E.EXE_ID IN (SELECT EXE_ID FROM JBPM6_FLOW_RESULT) "); // JBPM6_EXECUTION与JBPM6_FLOW_RESULT可能有一对多情况，但只是附件，证照最多只有一张
        sql.append(" AND (E.MATERIAL_PUSH_STATUS=0 OR E.MATERIAL_PUSH_STATUS=2) ");
        sql.append(" AND SUBSTR(E.CREATE_TIME,0,7) >='2021-09' ");
        //sql.append(" AND (E.EXE_ID='FJPT210830772022' OR E.EXE_ID='FJPT210901775151' OR E.EXE_ID='FJPT200605451359') ");
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * @Description 电子证照-申报登记信息材料提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    public void declareMaterialSubmit(Map<String, Object> itemData) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        //String BSYHLX = itemData.get("BSYHLX") == null ? "" : itemData.get("BSYHLX").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE : itemData.get("SWB_ITEM_CODE").toString();
        ArrayList<Object> param = new ArrayList<>();
        param.add(EXE_ID);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.EXE_ID,F.FILE_NAME,F.FILE_TYPE,F.SQFS,F.CREADIT_FILEID,M.MATER_CODE,M.MATER_NAME  ");
        sql.append(" FROM JBPM6_EXECUTION T INNER JOIN T_MSJW_SYSTEM_FILEATTACH F ");
        sql.append(" ON T.BUS_RECORDID=F.BUS_TABLERECORDID AND T.BUS_TABLENAME=F.BUS_TABLENAME ");
        sql.append(" LEFT JOIN T_WSBS_APPLYMATER M ON M.MATER_CODE=F.ATTACH_KEY ");
        sql.append(" WHERE T.EXE_ID =? AND F.SFSQ='1' ");
        List<Map<String, Object>> executionList = dao.findBySql(sql.toString(), param.toArray(), null);
        //List<Map<String, Object>> decryptList = encryptionService.listDecrypt(executionList, "JBPM6_EXECUTION");
        //Map<String, Object> exeMap = decryptList.get(0);
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : executionList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("applicationNumber", EXE_ID); // 申报号
            
            String LICENSEIDENTIFIER = "";
            String modelType = "";
            String SQFS = map.get("SQFS") == null ? "" : map.get("SQFS").toString();
            String FILE_TYPE = map.get("FILE_TYPE") == null ? "" : map.get("FILE_TYPE").toString();
            if("2".equals(SQFS)) { // 纸质
                modelType = "paper";
            }else {
                if("pdf".equals(FILE_TYPE)) {
                    modelType = "form";
                }else if("edc".equals(FILE_TYPE) || "ofd".equals(FILE_TYPE)) {
                    modelType = "license";
                    LICENSEIDENTIFIER = map.get("CREADIT_FILEID") == null ? "" : map.get("CREADIT_FILEID").toString();
                }else {
                    modelType = "upload";
                }
            }
            dataMap.put("modelType", modelType); // 材料类型（noneed:无，upload:省网办事大厅上传，paper:窗口纸质，form:pdf电子表单，license:电子证照）
            
            String MATER_CODE = map.get("MATER_CODE") == null ? "" : map.get("MATER_CODE").toString();
            String MATER_NAME = map.get("MATER_NAME") == null ? "" : map.get("MATER_NAME").toString();
            String FILE_NAME = map.get("FILE_NAME") == null ? "" : map.get("FILE_NAME").toString();
            dataMap.put("materialCode", MATER_CODE); // 材料编码
            dataMap.put("materialName", MATER_NAME); // 材料名称
            dataMap.put("seqNo", ""); // 附件序号 model_type为upload时有效
            dataMap.put("fileName", FILE_NAME); // 附件名称 model_type为upload,license时有效，当为license时，该字段为证照编号
            dataMap.put("licenseIdentifier", LICENSEIDENTIFIER); // 证照标识 model_type为license时有效
            dataMap.put("certificateTypeCode", ""); // 证照类型代码
            dataMap.put("certificateHolderCode", ""); // 持证者代码
            dataList.add(dataMap);
        }
        
        //生成报文参数，只有类型是电子证照的时候有后面的 3个字段数据，其他的都只有filename。目前审批系统材料上传电子证照时并未保存证照元数据，只作为一般附件
        Map<String, Object> params = getParams("declareMaterialSubmit", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "declareMaterialSubmit");
        params.put("total", executionList.size()); // 总记录数
        /*
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("applicationNumber", EXE_ID); // 申报号
        dataMap.put("modelType", modelType); // 材料类型（noneed:无，upload:省网办事大厅上传，paper:窗口纸质，form:pdf电子表单，license:电子证照）
        dataMap.put("materialCode", MATER_CODE); // 材料编码
        dataMap.put("materialName", MATER_NAME); // 材料名称
        dataMap.put("seqNo", ""); // 附件序号 model_type为upload时有效
        dataMap.put("fileName", FILE_NAME); // 附件名称 model_type为upload,license时有效，当为license时，该字段为证照编号
        dataMap.put("licenseIdentifier", ""); // 证照标识 model_type为license时有效
        dataMap.put("certificateTypeCode", ""); // 证照类型代码
        dataMap.put("certificateHolderCode", ""); // 持证者代码
        dataList.add(dataMap);
        */
        params.put("dataList", JSON.toJSONString(dataList));
        
        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                info.put("MATERIAL_PUSH_STATUS", 1);
                log.info("申报号：" + EXE_ID + "申报登记信息材料推送成功！");
            } else {// 失败
                info.put("MATERIAL_PUSH_STATUS", 2);
                log.error("申报号：" + EXE_ID + "申报登记信息材料提交推送失败！返回错误：" + message);
            }
            dao.saveOrUpdate(info, "JBPM6_EXECUTION", EXE_ID);
        }
    }
    
    /**
     * 
     * @Description 电子证照-申报信息结果提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    @Override
    public void declareResultSubmit() {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findElicenseRegResultList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("电子证照-申报信息结果提交数据量为:" + itemDatas.size());
        }
        for (Map<String, Object> itemData : itemDatas) {
            try {
                // runnable(new DeclareResultSubmitRunnable(itemData));
                executor.execute(new DeclareResultSubmitRunnable(itemData));
            } catch (Exception e) {
                log.error("电子证照-申报信息结果提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
            }
        }
        executor.shutdown();
    }
    
    /**
     * 
     * @Description 电子证照-获取申报信息结果列表
     * @author Roy Li
     * @date 2021年8月19日
     * @return List<Map<String,Object>>
     */
    private List<Map<String, Object>> findElicenseRegResultList() {
        //只推送已办结且有办件结果信息的记录
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,E.SUBJECT,E.BUS_RECORDID,");
        sql.append(" E.BUS_TABLENAME,E.PROJECT_CODE,E.BSYHLX,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQRMC,E.FINAL_HANDLEOPINION,");
        sql.append(" E.CREATOR_NAME,E.CREATE_TIME,E.END_TIME,E.SQJG_CODE,E.SQRZJLX,E.SQRSFZ,S.ZBCS,S.IMPL_DEPART,S.SSBMBM,S.ITEM_ID,");
        sql.append(" S.ITEM_CODE,S.C_VERSION,S.ITEM_NAME,S.SWB_ITEM_CODE,S.SXLX,S.SXXZ, ");
        sql.append(" R.XKFILE_NUM, R.XKFILE_NAME, R.XK_HOLDER ");
        sql.append(" FROM JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE = S.ITEM_CODE");
        sql.append(" INNER JOIN (");
        sql.append(" SELECT XKFILE_NUM, XKFILE_NAME, XK_HOLDER, EXE_ID FROM JBPM6_FLOW_RESULT");
        sql.append(" WHERE RESULT_ID IN (SELECT MIN(RESULT_ID) FROM JBPM6_FLOW_RESULT GROUP BY EXE_ID)");
        sql.append(" ) R ON E.EXE_ID = R.EXE_ID");
        sql.append(" WHERE (E.RUN_STATUS='2' OR E.RUN_STATUS='3') AND E.DEF_ID!='2c90b38a5ca1392c015ca14c9a960018' ");
        //sql.append(" AND E.EXE_ID IN (SELECT EXE_ID FROM JBPM6_FLOW_RESULT) "); // JBPM6_EXECUTION与JBPM6_FLOW_RESULT可能有一对多情况，但只是附件，证照最多只有一张
        sql.append(" AND (E.RESULT_PUSH_STATUS=0 OR E.RESULT_PUSH_STATUS=2) ");
        sql.append(" AND SUBSTR(E.CREATE_TIME,0,7) >='2021-09' ");
        //sql.append(" AND (E.EXE_ID='FJPT210830772022' OR E.EXE_ID='FJPT210901775151' OR E.EXE_ID='FJPT200605451359') ");
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * @Description 电子证照-申报信息结果提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    public void declareResultSubmit(Map<String, Object> itemData) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE : itemData.get("SWB_ITEM_CODE").toString();
        String CREATOR_NAME = itemData.get("CREATOR_NAME") == null ? "" : itemData.get("CREATOR_NAME").toString();
        String CREATE_TIME = itemData.get("CREATE_TIME") == null ? "" : itemData.get("CREATE_TIME").toString();
        String END_TIME = itemData.get("END_TIME") == null ? "" : itemData.get("END_TIME").toString();
        String FINAL_HANDLEOPINION = itemData.get("FINAL_HANDLEOPINION") == null ? "" : itemData.get("FINAL_HANDLEOPINION").toString();
        String XKFILE_NUM = itemData.get("XKFILE_NUM") == null ? "" : itemData.get("XKFILE_NUM").toString();
        String XKFILE_NAME = itemData.get("XKFILE_NAME") == null ? "" : itemData.get("XKFILE_NAME").toString();
        String XK_HOLDER = itemData.get("XK_HOLDER") == null ? "" : itemData.get("XK_HOLDER").toString();
        
        //生成报文参数
        Map<String, Object> params = getParams("declareResultSubmit", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "declareResultSubmit");
        params.put("applicationNumber", EXE_ID); // 申报号（唯一）
        params.put("handleUserName", CREATOR_NAME); // 办理人
        params.put("handleTime", !"".equals(END_TIME) ? END_TIME : CREATE_TIME); // 办理时间yyyy-MM-dd HH:mm:ss
        params.put("handleResult", "已办结"); // 办理结果
        params.put("handleOpinion", FINAL_HANDLEOPINION); // 办理意见
        params.put("holderName", XK_HOLDER); // 持证者名称
        params.put("licenseName", XKFILE_NAME); // 证照名称
        params.put("serialNumber", XKFILE_NUM); // 证照编号
        
        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                info.put("RESULT_PUSH_STATUS", 1);
                log.info("申报号：" + EXE_ID + "申报信息结果推送成功！");
            } else {// 失败
                info.put("RESULT_PUSH_STATUS", 2);
                log.error("申报号：" + EXE_ID + "申报信息结果提交推送失败！返回错误：" + message);
            }
            dao.saveOrUpdate(info, "JBPM6_EXECUTION", EXE_ID);
        }
    }
    
    /**
     * 
     * @Description 电子证照-申报结果证照提交
     * @author Roy Li
     * @date 2021年8月19日
     */
    @Override
    public void declareResultCertificateSubmit() {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 获取提交事项
        List<Map<String, Object>> itemDatas = this.findElicenseRegResultCertificateList();
        if (itemDatas != null && itemDatas.size() > 0) {
            log.info("电子证照-申报结果证照提交数据量为:" + itemDatas.size());
        }
        for (Map<String, Object> itemData : itemDatas) {
            try {
                // runnable(new DeclareResultCertificateSubmitRunnable(itemData));
                executor.execute(new DeclareResultCertificateSubmitRunnable(itemData));
            } catch (Exception e) {
                log.error("电子证照-申报结果证照提交错误，EXE_ID：" + itemData.get("EXE_ID").toString());
            }
        }
        executor.shutdown();
    }
    
    /**
     * 
     * @Description 电子证照-获取申报结果证照列表
     * @author Roy Li
     * @date 2021年8月19日
     * @return List<Map<String,Object>>
     */
    private List<Map<String, Object>> findElicenseRegResultCertificateList() {
        //只推送已办结且有办件结果信息的记录
        StringBuffer sql = new StringBuffer("SELECT E.EXE_ID,E.SUBJECT,E.BUS_RECORDID,");
        sql.append(" E.BUS_TABLENAME,E.PROJECT_CODE,E.BSYHLX,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQRMC,E.FINAL_HANDLEOPINION,");
        sql.append(" E.CREATOR_NAME,E.CREATE_TIME,E.END_TIME,E.SQJG_CODE,E.SQRZJLX,E.SQRSFZ,S.ZBCS,S.IMPL_DEPART,S.SSBMBM,S.ITEM_ID,");
        sql.append(" S.ITEM_CODE,S.C_VERSION,S.ITEM_NAME,S.SWB_ITEM_CODE,S.SXLX,S.SXXZ, ");
        sql.append(" R.XKFILE_NUM, R.XKFILE_NAME, R.XK_HOLDER,R.EFFECT_TIME,R.CLOSE_TIME,R.XKDEPT_NAME,R.XK_USC,R.XKCONTENT,R.XKDECIDE_TIME ");
        sql.append(" FROM JBPM6_EXECUTION E");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE = S.ITEM_CODE");
        sql.append(" INNER JOIN (");
        sql.append(" SELECT XKFILE_NUM, XKFILE_NAME, XK_HOLDER, EXE_ID,EFFECT_TIME,CLOSE_TIME,XKDEPT_NAME,XK_USC,XKCONTENT,XKDECIDE_TIME FROM JBPM6_FLOW_RESULT");
        sql.append(" WHERE RESULT_ID IN (SELECT MIN(RESULT_ID) FROM JBPM6_FLOW_RESULT GROUP BY EXE_ID)");
        sql.append(" ) R ON E.EXE_ID = R.EXE_ID");
        sql.append(" WHERE (E.RUN_STATUS='2' OR E.RUN_STATUS='3') AND E.DEF_ID!='2c90b38a5ca1392c015ca14c9a960018' ");
        //sql.append(" AND E.EXE_ID IN (SELECT EXE_ID FROM JBPM6_FLOW_RESULT) "); // JBPM6_EXECUTION与JBPM6_FLOW_RESULT可能有一对多情况，但只是附件，证照最多只有一张
        sql.append(" AND (E.CERTIFICATE_PUSH_STATUS=0 OR E.CERTIFICATE_PUSH_STATUS=2) ");
        sql.append(" AND SUBSTR(E.CREATE_TIME,0,7) >='2021-09' ");
        //sql.append(" AND (E.EXE_ID='FJPT210830772022' OR E.EXE_ID='FJPT210901775151' OR E.EXE_ID='FJPT200605451359') ");
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * @Description 电子证照-申报结果证照提交
     * @author Roy Li
     * @date 2021年8月19日
     * @param itemData
     */
    public void declareResultCertificateSubmit(Map<String, Object> itemData) {
        String EXE_ID = itemData.get("EXE_ID") == null ? "" : itemData.get("EXE_ID").toString();
        String ITEM_CODE = itemData.get("ITEM_CODE") == null ? "" : itemData.get("ITEM_CODE").toString();
        String ITEM_NAME = itemData.get("ITEM_NAME") == null ? "" : itemData.get("ITEM_NAME").toString();
        String BSYHLX = itemData.get("BSYHLX") == null ? "" : itemData.get("BSYHLX").toString();
        String SWB_ITEM_CODE = itemData.get("SWB_ITEM_CODE") == null ? ITEM_CODE : itemData.get("SWB_ITEM_CODE").toString();
        String XKFILE_NUM = itemData.get("XKFILE_NUM") == null ? "" : itemData.get("XKFILE_NUM").toString();
        String XKFILE_NAME = itemData.get("XKFILE_NAME") == null ? "" : itemData.get("XKFILE_NAME").toString();
        String XK_HOLDER = itemData.get("XK_HOLDER") == null ? "" : itemData.get("XK_HOLDER").toString();
        String EFFECT_TIME = itemData.get("EFFECT_TIME") == null ? "" : itemData.get("EFFECT_TIME").toString();
        String CLOSE_TIME = itemData.get("CLOSE_TIME") == null ? "" : itemData.get("CLOSE_TIME").toString();
        if("null".equalsIgnoreCase(CLOSE_TIME)) { // 存储的数据不规范，可能存在"null"字符串
            CLOSE_TIME = "";
        }
        String XKDEPT_NAME = itemData.get("XKDEPT_NAME") == null ? "" : itemData.get("XKDEPT_NAME").toString();
        String XK_USC = itemData.get("XK_USC") == null ? "" : itemData.get("XK_USC").toString();
        String XKCONTENT = itemData.get("XKCONTENT") == null ? "" : itemData.get("XKCONTENT").toString();
        String XKDECIDE_TIME = itemData.get("XKDECIDE_TIME") == null ? "" : itemData.get("XKDECIDE_TIME").toString();
        ArrayList<Object> param = new ArrayList<>();
        param.add(EXE_ID);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.* FROM JBPM6_EXECUTION T WHERE T.EXE_ID =? ");
        List<Map<String, Object>> executionList = dao.findBySql(sql.toString(), param.toArray(), null);
        List<Map<String, Object>> decryptList = encryptionService.listDecrypt(executionList, "JBPM6_EXECUTION");
        Map<String, Object> exeMap = decryptList.get(0);
        String certificateNumber="";
        if("2".equals(BSYHLX)) {  //接口定义的法人为1
            certificateNumber = exeMap.get("SQJG_CODE") == null ? "" : exeMap.get("SQJG_CODE").toString();
        }else {
            certificateNumber = exeMap.get("SQRSFZ") == null ? "" : exeMap.get("SQRSFZ").toString();
        }
        
        //生成报文参数
        Map<String, Object> params = getParams("declareResultCertificateSubmit", gcjsxmGrantCode, SWB_ITEM_CODE, ITEM_NAME, EXE_ID); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "declareResultCertificateSubmit");
        params.put("total", 1); // 总记录数，根据SQL查询情况必定为1
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("applicationNumber", EXE_ID); // 申报号（唯一）
        dataMap.put("holderName", XK_HOLDER); // 持证者名称
        dataMap.put("licenseName", XKFILE_NAME); // 证照名称
        dataMap.put("serialNumber", XKFILE_NUM); // 证照编号
        dataMap.put("startDate", EFFECT_TIME); // 有效期起始
        dataMap.put("validateDate", CLOSE_TIME); // 有效期截至
        dataMap.put("certUnit", XKDEPT_NAME); // 颁证机关名称
        dataMap.put("certUnitCode", XK_USC); // 许可机关代码
        dataMap.put("certContent", XKCONTENT); // 许可内容
        dataMap.put("certifiedTime", XKDECIDE_TIME); // 颁证时间
        dataMap.put("certificateHolderCode", certificateNumber); // 持证者代码
        dataList.add(dataMap);
        params.put("dataList", JSON.toJSONString(dataList));
        
        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                info.put("CERTIFICATE_PUSH_STATUS", 1);
                log.info("申报号：" + EXE_ID + "申报结果证照推送成功！");
            } else {// 失败
                info.put("CERTIFICATE_PUSH_STATUS", 2);
                log.error("申报号：" + EXE_ID + "申报结果证照推送失败！返回错误：" + message);
            }
            dao.saveOrUpdate(info, "JBPM6_EXECUTION", EXE_ID);
        }
    }
    
}
