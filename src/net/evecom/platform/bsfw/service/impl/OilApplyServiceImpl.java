/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.HashMap;
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
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.BdcApplyDao;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.bsfw.service.OilApplyService;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 成品油相关业务相关方法(实现类)
 * 
 * @author Keravon Feng
 * @created 2019年10月8日 上午10:48:14
 */
@Service("oilApplyService")
public class OilApplyServiceImpl extends BaseServiceImpl implements OilApplyService {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(OilApplyServiceImpl.class);
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
      *  成品油批准证书后置事件   
      * 
      * @author Scolder Lin
      * @param flowDatas
      * @created 2021年9月7日 上午10:26:05
      * @return
      * @throws Exception 
      */
     @SuppressWarnings("unchecked")
     @Override
     public Map<String, Object> saveAfterBusDataCpy(Map<String, Object> flowDatas) throws Exception {
         String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
         String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
         String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
         String item_code = StringUtil.getString(flowDatas.get("item_code"));// 获取加油船成品油事项编码
         //判断如果不是加油船事项，直接return flowDatas;
         if(StringUtils.isEmpty(item_code) || !"577010975XK03006".equals(item_code)){
             return flowDatas;
         }
         // 获取业务表名称
         String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
         // 获取业务表记录ID
         String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
         // 获取证照编码，为空则不调用
         String zzbm = (String) flowDatas.get("ZZBM");
         if (StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
             if (StringUtils.isNotEmpty(exeId) && StringUtils.isNotBlank(zzbm)) {
                 Map<String, Object> result = null;
                 Properties properties = FileUtil.readProperties("project.properties");
                 String devbaseUrl = properties.getProperty("devbaseUrl");
                 String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
                 // 获取公共参数
                 String taskCode = "11350128345071904JXK000526";
                 String taskName = "加油船成品油零售经营批准证书变更";
                 Map<String, Object> params = getParams(exeId, "certificateData", gcjsxmGrantCode, taskCode, taskName);
                 // 调用电子证照数据提交接口
                 params.put("interfaceName", "submitCertificateData");
                 params.put("defineAuthorityName", "平潭综合实验区行政审批局");
                 params.put("certificateName", "成品油零售经营批准证书");
                 params.put("versionNumber", "2015V1.0");
                 
                 // 拼接证照信息，按照证照系统提供的证照信息结构封装 jsonInfo
                 Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                         new String[] { "JYCCPYLSJYPZZSBG" }); // SGXKZZSC
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
                         log.info("成品油零售经营批准证书申报号：" + exeId + "-证照数据推送成功！");
                     } else {
                         flowDatas.put("SIGN_FLAG", false);
                         flowDatas.put("SIGN_MSG", message);
                         //throw new Exception(message);
                         log.info("成品油零售经营批准证书申报号：" + exeId + "-接口返回异常状态，message：" + message);
                     }
                 } else {
                     flowDatas.put("SIGN_FLAG", false);
                     flowDatas.put("SIGN_MSG", "电子证照生成接口返回数据为空，无法提交");
                     // throw new Exception("电子证照生成接口返回数据为空，无法提交");
                     log.info("成品油零售经营批准证书申报号：" + exeId + "-电子证照生成接口返回数据为空" );
                 }

             } else {
                 flowDatas.put("SIGN_FLAG", false);
                 flowDatas.put("SIGN_MSG", "办件编号为空，无法提交");
                 // throw new Exception("办件编号为空，无法提交");
                 log.info("成品油零售经营批准证书-请求数据申报号或证照编码为空，无法提交" );
             }
         }
         return flowDatas;
     }
     
     /**
      *  获取证照在线签章地址   
      * 
      * @author Roy Li
      * @param exeId
      * @param certificateIdentifier
      * @created 2021年9月26日 上午10:26:05
      * @return
      */
     @SuppressWarnings("unchecked")
     @Override
    public Map<String, Object> getQzUrl(String exeId, String certificateIdentifier) {
        Map<String, Object> resultData = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(exeId)) {
            Map<String, Object> result = null;
            Properties properties = FileUtil.readProperties("project.properties");
            String devbaseUrl = properties.getProperty("devbaseUrl");
            String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
            // 获取公共参数
            String taskCode = "11350128345071904JXK000526";
            String taskName = "加油船成品油零售经营批准证书变更";
            Map<String, Object> params = getParams(exeId, "getCertificateOnLineSignUrl", gcjsxmGrantCode, taskCode, taskName);
            // 调用电子证照数据提交接口
            params.put("interfaceName", "getCertificateOnLineSignatureUrl");
            params.put("certificateIdentifier", certificateIdentifier); // certificateIdentifier、accessId二选一，这里使用certificateIdentifier

            result = TokenUtil.doPost(devbaseUrl, params);
            if (null != result && result.size() > 0) {
                Map<String, Object> headMap = (Map<String, Object>) result.get("head");
                String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
                String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
                if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                    Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                    String content = dataMap.get("content") == null ? "" : dataMap.get("content").toString();
                    resultData.put("status", status);
                    resultData.put("message", message);
                    resultData.put("url", content); // 在线签章地址，格式如 http://ip:port/licenseonlineservice/dataSignature?repId=xxx
                    log.info("成品油零售经营批准证书申报号：" + exeId + "-获取证照在线签章地址成功！");
                } else {
                    resultData.put("status", status);
                    resultData.put("message", message);
                }
            } else {
                resultData.put("status", "-1");
                resultData.put("message", "获取证照在线签章地址接口返回数据为空");
            }

        } else {
            resultData.put("status", "-1");
            resultData.put("message", "申报号不能为空");
        }
        return resultData;
    }
}
