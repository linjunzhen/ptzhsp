/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

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
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.bsfw.dao.BdcApplyDao;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.bsfw.service.GcjsApplyService;
import net.evecom.platform.bsfw.util.MaterDownloadUtil;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 不动产全流程申请业务相关方法(实现类)
 * 
 * @author Keravon Feng
 * @created 2019年10月8日 上午10:48:14
 */
@Service("gcjsApplyService")
public class GcjsApplyServiceImpl extends BaseServiceImpl implements GcjsApplyService {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(GcjsApplyServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private BdcApplyDao dao;

    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;

    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    
    /**
     * commercialService
     */
    @Resource
    private CommercialService commercialService;
    
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    
    /**
     * executionService
     */
    @Resource
    private StatisticsService statisticsService;
    
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    
    /**
     * 根据证照标识请求附件轮询时间间隔，5秒
     */
    private int waitMillis = 5000;
    
    /**
     * 最大轮询次数
     */
    private int maxlx = 10;
    
    /**
     * 
     * 描述 GenericDao
     * 
     * @author Keravon Feng
     * @created 2019年10月8日 上午10:49:07
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 获取 电子证照数据提交/电子证照文件获取 接口的公共参数
     * 
     * @author Roy Li
     * @created 2021年9月10日 下午5:59:50
     * @param exeId
     * @param servicecode
     * @param gcjsxmGrantCode
     * @return
     */
    private Map<String, Object> getParams(String exeId, String servicecode, String gcjsxmGrantCode, String taskCode, String taskName) {
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
        params.put("taskCode", taskCode);
        params.put("taskName", taskName);
        params.put("projectNo", exeId);
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
    private String uploadBase64File(String base64Code, String fileType, String busTableName, String busRecordId) {
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
    
    /**
     * 
     * 描述 轮询附件接口
     * 
     * @author Roy Li
     * @created 2021年10月14日 下午4:19:30
     * @param haslx 已轮询次数
     * @param flowDatas
     * @return
     */
    private void pollingAttach(int haslx, Map<String, Object> flowDatas) throws Exception {
        if (haslx < maxlx) {
            haslx ++;
            Thread.sleep(waitMillis);
            getCertificateFileContentByIdentifier(flowDatas);
            boolean signFlag = (boolean)flowDatas.get("SIGN_FLAG");
            if(!signFlag) { // 如果未请求到附件，继续轮询直到最大轮询次数
                pollingAttach(haslx, flowDatas);
            }
        }
   }

     /**
      *  商品房预售许可证前置事件
      * 
      * @author Roy Li
      * @param flowDatas
      * @created 2021年9月7日 上午10:26:05
      * @return
      * @throws Exception 
      */
     @SuppressWarnings("unchecked")
     @Override
     public Map<String, Object> saveBeforeBusDataSpf(Map<String, Object> flowDatas) throws Exception {
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
                 String taskCode = "11350128345071904JXK000143";
                 String taskName = "商品房预售许可XX30-04-05";
                 Map<String, Object> params = getParams(exeId, "certificateData", gcjsxmGrantCode, taskCode, taskName);
                 // 调用电子证照数据提交接口
                 params.put("interfaceName", "submitCertificateData");
                 params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                 params.put("certificateName", "商品房预售许可证");
                 params.put("versionNumber", "2015V4.0");
                 
                 // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                 //Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                 //        new String[] { busRecordId });
                 Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                         new String[] { "SPFYSXKZ" }); // SGXKZZSC
                 String contentStr = (String) dataAbutment.get("CONFIG_XML");
                 Map<String, Object> configMap = this.getByJdbc(busTableName, new String[] { "YW_ID" },new String[] { busRecordId });
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
                         flowDatas.put("taskCode", taskCode);
                         flowDatas.put("taskName", taskName);
                         flowDatas.put("EXE_ID", exeId);
                         flowDatas.put("YW_ID", busRecordId);
                         log.info("商品房预售许可申报号：" + exeId + "-证照数据推送成功！");
                         log.info("许可申报号：" + exeId + "-开始根据证照标识获取附件！");
                         int haslx = 0; // 请求附件已轮询次数
                         pollingAttach(haslx, flowDatas);
                     } else {
                         flowDatas.put("SIGN_FLAG", false);
                         flowDatas.put("SIGN_MSG", message);
                         throw new Exception(message);
                     }
                 } else {
                     flowDatas.put("SIGN_FLAG", false);
                     flowDatas.put("SIGN_MSG", "电子证照生成接口返回数据为空，无法提交");
                     throw new Exception("电子证照生成接口返回数据为空，无法提交");
                 }

             } else {
                 flowDatas.put("SIGN_FLAG", false);
                 flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                 throw new Exception("办件编号为空，无法提交");
             }
         }
         return flowDatas;
     }
     
     /**
      *  城镇污水排入排水管网许可证前置事件
      * 
      * @author Roy Li
      * @param flowDatas
      * @created 2021年9月7日 上午10:26:05
      * @return
      * @throws Exception 
      */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveBeforeBusDataCzws(Map<String, Object> flowDatas) throws Exception {
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
                String taskCode = "11350128345071904JXK001185";
                String taskName = "城镇污水排入排水管网许可XX30-10-09";
                Map<String, Object> params = getParams(exeId, "certificateData", gcjsxmGrantCode, taskCode, taskName);
                // 调用电子证照数据提交接口
                params.put("interfaceName", "submitCertificateData");
                params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                params.put("certificateName", "城镇污水排入排水管网许可证");
                params.put("versionNumber", "2016V2.0");
                
                // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                //Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                //        new String[] { busRecordId });
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "CZWSXKZ" }); // SGXKZZSC
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = this.getByJdbc(busTableName, new String[] { "YW_ID" },new String[] { busRecordId });
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
                        flowDatas.put("taskCode", taskCode);
                        flowDatas.put("taskName", taskName);
                        flowDatas.put("EXE_ID", exeId);
                        flowDatas.put("YW_ID", busRecordId);
                        log.info("城镇污水排入排水管网许可申报号：" + exeId + "-证照数据推送成功！");
                        log.info("许可申报号：" + exeId + "-开始根据证照标识获取附件！");
                        int haslx = 0; // 请求附件已轮询次数
                        pollingAttach(haslx, flowDatas);
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                        throw new Exception(message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "电子证照生成接口返回数据为空，无法提交");
                    throw new Exception("电子证照生成接口返回数据为空，无法提交");
                }

            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }
    
    /**
     * 林木采伐许可证前置事件
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年7月22日 上午10:26:05
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveBeforeBusDataLmcf(Map<String, Object> flowDatas) throws Exception {
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
                String taskCode = "11350128345071904JXK000064";
                String taskName = "林木采伐许可证县级核发XX30-02-04";
                Map<String, Object> params = getParams(exeId, "certificateData", gcjsxmGrantCode, taskCode, taskName);
                // 调用电子证照数据提交接口
                params.put("interfaceName", "submitCertificateData");
                params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                params.put("certificateName", "林木采伐许可证");
                params.put("versionNumber", "2021V7.0");
                
                // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                //Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                //        new String[] { busRecordId });
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "LMCFXKZ" }); // SGXKZZSC
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = this.getByJdbc(busTableName, new String[] { "YW_ID" },new String[] { busRecordId });
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
                        flowDatas.put("taskCode", taskCode);
                        flowDatas.put("taskName", taskName);
                        flowDatas.put("EXE_ID", exeId);
                        flowDatas.put("YW_ID", busRecordId);
                        log.info("林木采伐许可申报号：" + exeId + "-证照数据推送成功！");
                        log.info("许可申报号：" + exeId + "-开始根据证照标识获取附件！");
                        int haslx = 0; // 请求附件已轮询次数
                        pollingAttach(haslx, flowDatas);
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                        throw new Exception(message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "电子证照生成接口返回数据为空，无法提交");
                    throw new Exception("电子证照生成接口返回数据为空，无法提交");
                }

            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }
    
    /**
     *  易地修建防空地下室审批前置事件(新：城市新建民用建筑易地修建防空地下室审批)
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年11月11日 下午15:32:55
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveBeforeBusDataYdxj(Map<String, Object> flowDatas) throws Exception {
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
                String taskCode = "11350128345071904JXK000961";
                String taskName = "城市新建民用建筑易地修建防空地下室审批";
                Map<String, Object> params = getParams(exeId, "submitApprovalData", gcjsxmGrantCode, taskCode, taskName);
                // 调用电子批文数据提交接口
                params.put("interfaceName", "submitApprovalData");
                params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                params.put("certificateName", "城市新建民用建筑易地修建防空地下室审批");
                params.put("certificateNumberFormat", "岚审批人防建审〔XXXX〕XX号");
                
                // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                //Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                //        new String[] { busRecordId });
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "YDXJFKDXSSP" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = this.getByJdbc(busTableName, new String[] { "YW_ID" },new String[] { busRecordId });
                String pwContentBase64 = getBase64CodeByFileUrl(flowDatas);  // 获取批文附件的Base64编码
                configMap.put("PWCONTENT", pwContentBase64);
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
                        flowDatas.put("taskCode", taskCode);
                        flowDatas.put("taskName", taskName);
                        flowDatas.put("EXE_ID", exeId);
                        flowDatas.put("YW_ID", busRecordId);
                        log.info("城市新建民用建筑易地修建防空地下室审批申报号：" + exeId + "-证照批文数据推送成功！");
                        log.info("许可申报号：" + exeId + "-开始根据证照标识获取附件！");
                        int haslx = 0; // 请求附件已轮询次数
                        pollingAttach(haslx, flowDatas);
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                        throw new Exception(message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "电子证照批文生成接口返回数据为空，无法提交");
                    throw new Exception("电子证照批文生成接口返回数据为空，无法提交");
                }

            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }
    
    /**
     *  结合民用建筑修建防空地下室设计审核前置事件(新：城市新建民用建筑修建防空地下室审批)
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年11月11日 下午15:33:55
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveBeforeBusDataJhmy(Map<String, Object> flowDatas) throws Exception {
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
                String taskCode = "11350128345071904JXK000017";
                String taskName = "城市新建民用建筑修建防空地下室审批";
                Map<String, Object> params = getParams(exeId, "submitApprovalData", gcjsxmGrantCode, taskCode, taskName);
                // 调用电子批文数据提交接口
                params.put("interfaceName", "submitApprovalData");
                params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                params.put("certificateName", "城市新建民用建筑修建防空地下室审批");
                params.put("certificateNumberFormat", "岚审批人防建审〔XXXX〕XX号");
                
                // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                //Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                //        new String[] { busRecordId });
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "JHMYJZXJFKDXSSJ" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = this.getByJdbc(busTableName, new String[] { "YW_ID" },new String[] { busRecordId });
                String pwContentBase64 = getBase64CodeByFileUrl(flowDatas);  // 获取批文附件的Base64编码
                configMap.put("PWCONTENT", pwContentBase64);
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
                        flowDatas.put("taskCode", taskCode);
                        flowDatas.put("taskName", taskName);
                        flowDatas.put("EXE_ID", exeId);
                        flowDatas.put("YW_ID", busRecordId);
                        log.info("城市新建民用建筑修建防空地下室审批申报号：" + exeId + "-证照批文数据推送成功！");
                        log.info("许可申报号：" + exeId + "-开始根据证照标识获取附件！");
                        int haslx = 0; // 请求附件已轮询次数
                        pollingAttach(haslx, flowDatas);
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                        throw new Exception(message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "电子证照批文生成接口返回数据为空，无法提交");
                    throw new Exception("电子证照批文生成接口返回数据为空，无法提交");
                }

            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }
    
    /**
     *  防空地下室竣工验收备案前置事件(新：人防工程竣工验收备案（就地）)
     * 
     * @author Roy Li
     * @param flowDatas
     * @created 2021年11月11日 下午15:34:55
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> saveBeforeBusDataFkdxs(Map<String, Object> flowDatas) throws Exception {
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
                String taskCode = "11350128345071904JXK001095";
                String taskName = "人防工程竣工验收备案（就地）";
                Map<String, Object> params = getParams(exeId, "submitApprovalData", gcjsxmGrantCode, taskCode, taskName);
                // 调用电子批文数据提交接口
                params.put("interfaceName", "submitApprovalData");
                params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                params.put("certificateName", "人防工程竣工验收备案（就地）");
                params.put("certificateNumberFormat", "岚审批人防备案〔XXXX〕XX号");
                
                // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                //Map<String, Object> sgxk = this.getByJdbc(busTableName, new String[] { "YW_ID" },
                //        new String[] { busRecordId });
                Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                        new String[] { "FKDXSJGYSBA" });
                String contentStr = (String) dataAbutment.get("CONFIG_XML");
                Map<String, Object> configMap = this.getByJdbc(busTableName, new String[] { "YW_ID" },new String[] { busRecordId });
                String pwContentBase64 = getBase64CodeByFileUrl(flowDatas);  // 获取批文附件的Base64编码
                configMap.put("PWCONTENT", pwContentBase64);
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
                        flowDatas.put("taskCode", taskCode);
                        flowDatas.put("taskName", taskName);
                        flowDatas.put("EXE_ID", exeId);
                        flowDatas.put("YW_ID", busRecordId);
                        log.info("人防工程竣工验收备案（就地）申报号：" + exeId + "-证照批文数据推送成功！");
                        log.info("许可申报号：" + exeId + "-开始根据证照标识获取附件！");
                        int haslx = 0; // 请求附件已轮询次数
                        pollingAttach(haslx, flowDatas);
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                        throw new Exception(message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "电子证照批文生成接口返回数据为空，无法提交");
                    throw new Exception("电子证照批文生成接口返回数据为空，无法提交");
                }

            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                throw new Exception("办件编号为空，无法提交");
            }
        }
        return flowDatas;
    }
    
    /**
     * 
     * 描述  根据电子证照数据标识获取电子证照文件
     * 
     * @author Roy Li
     * @created 2021年9月10日 上午9:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getCertificateFileContentByIdentifier(Map<String, Object> flowDatas) throws Exception {
            Map<String, Object> result = null;
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            String certificateIdentifier = flowDatas.get("CERTIFICATEIDENTIFIER")==null?"":flowDatas.get("CERTIFICATEIDENTIFIER").toString();
            String exeId = flowDatas.get("EXE_ID")==null?"":flowDatas.get("EXE_ID").toString();
            String ywId = flowDatas.get("YW_ID")==null?"":flowDatas.get("YW_ID").toString();
            String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
            //String BUILDCORPCODE = flowDatas.get("BUILDCORPCODE")==null?"":flowDatas.get("BUILDCORPCODE").toString();//机构代码
            if (StringUtils.isNotEmpty(certificateIdentifier)) {
                // 获取公共参数
                String taskCode = flowDatas.get("taskCode")==null?"":flowDatas.get("taskCode").toString();
                String taskName = flowDatas.get("taskName")==null?"":flowDatas.get("taskName").toString();
                Map<String, Object> params = getParams(exeId, "getShare", gcjsxmGrantCode, taskCode, taskName);
                params.put("interfaceName", "getCertificateFileContentByIdentifier");
                params.put("certificateIdentifier", certificateIdentifier);
                // 根据证照标识获取证照信息
                result = TokenUtil.doPost(devbaseUrl, params);
                if (null != result && result.size() > 0) {
                    Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                    String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                    String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                    if (StringUtils.isNotEmpty(status) && status.equals("0")) { // 调用成功，返回证照标识
                        Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                        String content = dataMap.get("content") == null ? ""
                                : dataMap.get("content").toString();
                        String fileType = dataMap.get("fileType") == null ? ""
                                : dataMap.get("fileType").toString();
                        // 上传证照文件
                        String fileResult = uploadBase64File(content, fileType, busTableName, ywId);
                        if (StringUtils.isNotEmpty(fileResult)) {
                            Map<String, Object> resultMap = JSON.parseObject(fileResult, Map.class);
                            Map<String, Object> data = (Map) resultMap.get("data");
                            flowDatas.put("CERTIFICATEIDENTIFIER", certificateIdentifier);
                            flowDatas.put("CERTIFICATEIDENTIFIERFILEID", data.get("fileId"));
                            flowDatas.put("CERTIFICATEIDENTIFIERFILEPATH", data.get("filePath"));
                            flowDatas.put("CERTIFICATEIDENTIFIERFILENAME", data.get("fileName"));
                            flowDatas.put("SIGN_FLAG", true);
                            log.info("许可申报号：" + exeId + "-根据证照标识获取附件成功！");
                        }
                    } else { // 这种情况说明接口有返回数据，但有异常
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", message);
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "电子证照文件获取接口返回数据为空，无法提交");
                    throw new Exception("电子证照文件获取接口返回数据为空，无法提交");
                }
       }
    }
    
    /**
     * 
     * 描述  根据附件URL获取附件base64编码
     * 
     * @author Roy Li
     * @created 2021年11月17日 上午16:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getBase64CodeByFileUrl(Map<String, Object> flowDatas) {
        String materId = this.queryMaterId(flowDatas);
        flowDatas.put("ATTACH_KEY", materId);
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        String attachKey = (String) flowDatas.get("ATTACH_KEY");
        if(StringUtils.isBlank(attachKey)) 
            return "";
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrlIn = properties.getProperty("uploadFileUrlIn");
        Map<String, Object> map = this.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[] { "BUS_TABLENAME", "BUS_TABLERECORDID", "ATTACH_KEY" }, 
                new Object[] { busTableName, busRecordId, attachKey } );
        return MaterDownloadUtil.getBase64CodeByUrlFile(uploadFileUrlIn + StringUtil.getValue(map, "FILE_PATH"));
    }
    
    
    /**
     * 
     * 描述  事项编码查询材料编码
     * 
     * @author Roy Li
     * @created 2021年11月17日 上午16:02:05
     * @param flowDatas
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public String queryMaterId(Map<String, Object> flowDatas) {
        String itemCode = (String) flowDatas.get("ITEM_CODE");
        if(StringUtils.isBlank(itemCode)) 
            return "";
        //预约信息报送
        String sql = " select * from t_wsbs_serviceitem a  "
                + " left join t_wsbs_serviceitem_mater b on a.item_id=b.item_id  "
                + " left join t_wsbs_applymater c on b.mater_id=c.mater_id  "
                + " where a.item_code = ? ";
        Map<String, Object> queryMap = this.dao.getByJdbc(sql, new Object[]{itemCode});
        return queryMap.get("MATER_CODE").toString();
    }

}
