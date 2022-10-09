/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.elicensejsoninfo.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.elicensejsoninfo.dao.ElectronicLicenseJsonInfoDao;
import net.evecom.platform.elicensejsoninfo.service.ElectronicLicenseJsonInfoService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述 电子证照业务相关service实现
 * 
 * @author Roy Li
 *
 */
@SuppressWarnings("rawtypes")
@Service("electronicLicenseJsonInfoService")
public class ElectronicLicenseJsonInfoServiceImpl extends BaseServiceImpl implements ElectronicLicenseJsonInfoService {
    
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
    private static Log log = LogFactory.getLog(ElectronicLicenseJsonInfoServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ElectronicLicenseJsonInfoDao dao;

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
        params.put("systemName", "平潭综合实验区综合服务平台");
        params.put("taskCode", taskCode);
        params.put("taskName", taskName);
        params.put("projectNo", exeId);
        return params;
    }

    /**
     * 
     * @Description 电子证照-城镇污水排入排水管网许可证-证照照面范例
     * @author Roy Li
     * @date 2021年8月19日
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getCzwsJsonInfo() {
        //生成报文参数
        Map<String, Object> params = getParams("getCzwsJsonInfo", gcjsxmGrantCode, 
                "11350128345071904JXK001185", "城镇污水排入排水管网许可XX30-10-09", "FJPT210901771111"); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "getCertificateDataSubmitExample");
        
        params.put("defineAuthorityName", "平潭综合实验区行政审批局"); // 定义机构名称（登记单位名称）
        params.put("certificateName", "城镇污水排入排水管网许可证"); // 证照名称
        params.put("versionNumber", "2016V2.0"); // 证照版本号

        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            //String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            //Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                log.info("电子证照-城镇污水排入排水管网许可证-证照照面范例获取成功！");
            } else {// 失败
                log.error("电子证照-城镇污水排入排水管网许可证-证照照面范例获取失败！");
            }
        }
        
    }

    /**
     * 
     * @Description 电子证照-林木采伐许可证-证照照面范例
     * @author Roy Li
     * @date 2021年8月19日
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getLmcfJsonInfo() {
      //生成报文参数
        Map<String, Object> params = getParams("getLmcfJsonInfo", gcjsxmGrantCode, 
                "11350128345071904JXK000064", "林木采伐许可证县级核发XX30-02-04", "FJPT210901772222"); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "getCertificateDataSubmitExample");
        
        params.put("defineAuthorityName", "平潭综合实验区行政审批局"); // 定义机构名称（登记单位名称）
        params.put("certificateName", "林木采伐许可证"); // 证照名称
        params.put("versionNumber", "2021V7.0"); // 证照版本号

        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            //String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            //Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                log.info("电子证照-林木采伐许可证-证照照面范例获取成功！");
            } else {// 失败
                log.error("电子证照-林木采伐许可证-证照照面范例获取失败！");
            }
        }
        
    }

    /**
     * 
     * @Description 电子证照-商品房预售许可证-证照照面范例
     * @author Roy Li
     * @date 2021年8月19日
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getSpfysJsonInfo() {
      //生成报文参数
        Map<String, Object> params = getParams("getSpfysJsonInfo", gcjsxmGrantCode, 
                "11350128345071904JXK000143", "商品房预售许可XX30-04-05", "FJPT210901773333"); // 获取公共参数，包括clientInfo，projectInfo
        params.put("interfaceName", "getCertificateDataSubmitExample");
        
        params.put("defineAuthorityName", "平潭综合实验区行政审批局"); // 定义机构名称（登记单位名称）
        params.put("certificateName", "商品房预售许可证"); // 证照名称
        params.put("versionNumber", "2015V4.0"); // 证照版本号

        //发送到devbase
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            //String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            //Map<String, Object> info = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                log.info("电子证照-商品房预售许可证-证照照面范例获取成功！");
            } else {// 失败
                log.error("电子证照-商品房预售许可证-证照照面范例获取失败！");
            }
        }
        
    }
    
}
