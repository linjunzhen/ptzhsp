/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.WebServiceCallUtil;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.service.LicenseService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;
import org.tempuri.ExchangeSoapProxy;
import org.w3c.dom.NodeList;

import sun.misc.BASE64Decoder;

/**
 * 描述 证照对接业务服务
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年12月16日 下午5:03:15
 */
@SuppressWarnings("rawtypes")
@Service("licenseService")
public class LicenseServiceImpl extends BaseServiceImpl implements LicenseService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(LicenseServiceImpl.class);
    /**
     * 引入获取省网办报送数据的处理业务的dao
     */
    @Resource
    private SwbInterfaceDao dao;
    /**
     * 引入接口日志启记录服务
     */
    @Resource
    private DataAbutLogService dataAbutLogService;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 获取dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 向证照中心发送证照数据
     * 
     * @author Derek Zhang
     * @created 2015年12月16日 下午5:03:15
     * @param dataMap
     */
    @SuppressWarnings({ "unused", "unchecked" })
    @Override
    public void sendLicenseDataToZZZX(Log log) {
        // 获取数据
        String sql = "select r.res_id,r.exe_id,e.bus_tablename,e.bus_recordid,e.SQRSJH,e.SQRZJLX,e.SQRMC,e.SQRDHH,"
                + "r.other_status , k.dealitem_code,k.license_name, e.SQJG_LEALPERSON, e.sqjg_addr, e.sqrsfz,"
                + "e.BSYHLX,e.SQRXB " + " from t_bsfw_swbdata_res r,jbpm6_execution e, jbpm6_checkdealitem k "
                + " where r.inter_type = ? and r.oper_status = 0 "
                + " and r.exe_id = e.exe_id and e.item_code = k.dealitem_code ";
        // String sql = "select
        // r.res_id,r.exe_id,e.bus_tablename,e.bus_recordid,"
        // + "r.other_status , e.item_name as dealitem_name, e.SQJG_LEALPERSON,
        // e.sqjg_addr, e.sqrsfz"
        // + " from t_bsfw_swbdata_res r,jbpm6_execution e "
        // + " where r.inter_type = '10' and r.oper_status = '0' "
        // + " and r.exe_id = e.exe_id ";
        // List<Map<String, Object>> dataList =
        // this.dao.findBySql(sql,null,null);
        List<Map<String, Object>> dataList = this.dao.findBySql(sql, 
                new Object[] { AllConstant.DATA_INTER_TYPE_ZZZX },null);
//        List<Map<String, Object>> dataList = this.dao.findBySql(sql, 
//                new Object[] { 10 },null);
        if (dataList != null && !dataList.isEmpty() && dataList.size() > 0) {
            log.info("开始调用电子证照对接定时任务....");
            for (Map<String, Object> data : dataList) {
                String res_id = (String) data.get("RES_ID"); // 主键id
                String bus_tablename = (String) data.get("BUS_TABLENAME"); // 业务记录表名
                String bus_recordid = (String) data.get("BUS_RECORDID"); // 业务记录ID
                String exeId = (String) data.get("EXE_ID"); // 流程实例
                String qyfddbr = String.valueOf(data.get("SQJG_LEALPERSON")); // 申请机构法人
                String qyaddr = String.valueOf(data.get("sqjg_addr")); // 申请机构联系地址
                String item_code = String.valueOf(data.get("dealitem_code")); // 事项编码（）
                String item_name = String.valueOf(data.get("license_name")); // 证照名称
                String other_status = String.valueOf(data.get("other_status")); // 补充业务状态
                                                                                // 受理=2
                                                                                // 审核=3
                                                                                // 签发=4
                                                                                // 发文=6
                String sqrmc = String.valueOf(data.get("SQRMC")); // 申请人名称
                String sqrsjh = (String) data.get("SQRSJH"); // 申请人手机号
                String sqrdhh = (String) data.get("SQRDHH"); // 申请人电话号码
                String sqrzjlx = (String) data.get("SQRZJLX"); // 申请人证件类型
                String sqrlxdz = String.valueOf(data.get("SQRLXDZ")); // 申请人联系地址
                String sqrsfz = String.valueOf(data.get("sqrsfz")); // 申请人身份证号
                String sqrxb = String.valueOf(data.get("SQRXB")); // 申请人性别
                String bzyhlx = String.valueOf(data.get("BSYHLX")); // 办证用户类型
                                                                    // 企业用户 /
                                                                    // 个人用户
                log.info("后端得到处理的证件名称 ::: " + item_name);
                String primaryKeyName = (String) this.dao.getPrimaryKeyName(bus_tablename).get(0);
                String licenseNumber = ""; // 证照编号
                // String dataSql = "select t.BELONG_DEPTNAME as orgName,"
                // + "t.serialnumber,t.owner,t.publisher,"
                // + "t.publishdate,t.validfromdate,t.validuntildate,t.jyfs,"
                // + "t.xkfw,t.OWNERTYPE,t.filenumber,t.czr,t.xkwh from "
                // + bus_tablename + " t where t." + primaryKeyName
                // + " = ? " + " and t.is_send = 1 ";
                String dataSql = "select t.BELONG_DEPTNAME  as orgName," + "t.serialnumber,t.owner,t.publisher,"
                        + "t.publishdate,t.validfromdate,t.validuntildate,t.jyfs,"
                        + "t.xkfw,t.OWNERTYPE,t.filenumber from " + bus_tablename + " t where t." + primaryKeyName
                        + " = ? ";
                Map<String, Object> dataMap = this.dao.getByJdbc(dataSql, new Object[] { bus_recordid });
                if (dataMap != null) {
                    dataMap.put("sqrmc", sqrmc);
                    dataMap.put("res_id", res_id);
                    dataMap.put("licenseName", item_name);
                    dataMap.put("itemCode", item_code);
                    dataMap.put("sqrzjlx", sqrzjlx);
                    dataMap.put("exeId", exeId);
                    dataMap.put("sqrsjh", sqrsjh);
                    dataMap.put("QYFDDBR", qyfddbr);
                    dataMap.put("qyaddr", qyaddr);
                    dataMap.put("sqrsfz", sqrsfz);
                    dataMap.put("sqrsfz", sqrdhh);
                    dataMap.put("sqrsfz", sqrlxdz);
                    dataMap.put("bzyhlx", bzyhlx);
                    dataMap.put("sqrxb", sqrxb);
                    licenseNumber = this.sendLicenseByMap(dataMap);
                }
            }
            log.info("结束调用电子证照对接定时任务....");
        }
    }

    /**
     * 
     * 描述 发送单条数据给证照中心
     * 
     * @author Derek Zhang
     * @created 2015年12月18日 下午3:02:43
     * @param dataMap
     * @return
     * @throws RemoteException
     */
    private String sendLicenseByMap(Map<String, Object> dataMap) {
        this.makeDataMap(dataMap);
        // 调用鉴权接口，返回guid
        String guid = this.authorization();

        //String userName = this.dictionaryService.getDicNames("licenseInterface", "username");
        //String password = this.dictionaryService.getDicNames("licenseInterface", "password");

        String licenseNumber = "";
        if (StringUtil.isBlank(guid) || guid.startsWith("ERR")) {
            return "";
        }
        String orgName = (String) dataMap.get("orgName");
        String licenseName = (String) dataMap.get("licenseName");
        String itemCode = (String) dataMap.get("itemCode");
        String licenseVersion = "";
        if (orgName.startsWith("区")){
            orgName = "平潭综合实验" + orgName;
            dataMap.put("orgName", orgName);
            dataMap.put("Publisher", orgName);
        }
        // 确定证照所属区局（根据证照名称获取证照所属区局）
        String returnOrgNanme = this.validateDepartment(itemCode);
        Map<String, String> metaData = null; // 元数据（证照的信息）
        if (returnOrgNanme != null) {
            dataMap.put("orgName", returnOrgNanme);
            dataMap.put("Publisher", returnOrgNanme);
            metaData = this.getMetadataList(guid, returnOrgNanme, licenseName);
            // String data="";
            // try {
            // data = proxy.getMetadata(guid, licenseName, returnOrgNanme);
            // } catch (RemoteException e) {
            // // TODO Auto-generated catch block
            // log.error(e.getMessage());
            // }
        } else {
            metaData = this.getMetadataList(guid, orgName, licenseName);
        }

        // 获取证照目录
        if (metaData == null) {
            return "";
        }
        // 用来存储生成系统鉴权信息
        Map<String, String> cookieMap = new HashMap<String, String>();//

        // boolean isLogin = this.authorizationExchange(cookieMap, itemCode);
        ExchangeSoapProxy proxy = new ExchangeSoapProxy();
        Map<String, String> accountInfoMap = this.findLicenseToAreaAccount(itemCode);
        boolean isLogin = false;
        try {
            // 生成系统鉴权
            isLogin = proxy.authorization(accountInfoMap.get("validate_account"),
                    accountInfoMap.get("validate_password"));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        if (isLogin) {
            // 获取xmlSchema
            // String xmlSchema = this.getLicenseDataXsd(licenseName,
            // metaData, cookieMap);
            String xmlSchema = this.getDataXsd(licenseName, metaData, proxy);
            log.info("+++++++++++++++++++++++++++++++++++++++\n" + licenseName + ":   " + xmlSchema
                    + "\n++++++++++++++++++++++++++++++");
            // XSD 校检 xml
            try {
                // 提交证照数据并自动签章

                // licenseNumber =
                // this.submitLicenseDataAndAutoSeal(licenseName,
                // licenseVersion, cookieMap, dataMap,itemCode);
                Map<String, String> licenseMap = this.findLicenseToAreaAccount(itemCode);
                String xml = licenseMap.get("CONFIG_XML");
                xml = FreeMarkerUtil.getResultString(xml, dataMap);
                try {
                    licenseVersion = metaData.get("VersionNum");
                    licenseNumber = proxy.submitLicenseDataAndAutoSeal(licenseName, licenseVersion, xml);
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    log.error(e1.getMessage());
                }
                if (!StringUtil.isBlank(licenseNumber)) {
                    if ("20,21,22,23,24,25,26,27,28,29".indexOf(licenseNumber) >= 0) {
                        String sql = "update t_bsfw_swbdata_res r set r.oper_status = 2 ,"
                                + " r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),"
                                + " r.return_msg = '生成证照失败【返回码：" + licenseNumber + "】;'"
                                + "  where r.res_id = ?";
                        this.dao.executeSql(sql, new Object[] { dataMap.get("res_id") });
                        licenseNumber = "";
                    } else {
                        String sql = "update t_bsfw_swbdata_res r set r.oper_status = 3 ,"
                                + " r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),"
                                + " r.return_msg = '生成证照成功【返回唯一编号：" + licenseNumber + "】;'"
                                + "  where r.res_id = ?";
                        this.dao.executeSql(sql, new Object[] { dataMap.get("res_id") });
                        log.info("updateSql:" + sql + "&&&" + dataMap.get("res_id"));
                        if (!StringUtil.isBlank(licenseNumber)) {
                            this.getLicenseFile(licenseNumber, (String) dataMap.get("OTHER_STATUS"),
                                    (String) dataMap.get("res_id"), cookieMap, licenseName, proxy);
                        }
                    }
                }
                return licenseNumber;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return "";
    }

    /**
     * 获取xmlSchema
     * 
     * @param licenseName
     * @param metaData
     * @param proxy
     * @return
     */
    private String getDataXsd(String licenseName, Map<String, String> metaData, ExchangeSoapProxy proxy) {
        // 证照类型（证照/批文）
        String licenseType = metaData.get("licenseType");
        String returnStr = "";
        if ("证照".equals(licenseType)) {
            try {
                // 获取证照xmlSchema
                returnStr = proxy.getLicenseDataXsd(licenseName, metaData.get("VersionNum"));
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        } else if ("批文".equals(licenseType)) {
            try {
                // 获取批文xmlSchema
                returnStr = proxy.getApprovalDataXsd(licenseName, metaData.get("Format"));
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
        return returnStr;
    }

    /**
     * 根据证照名称获取所属区局
     * 
     * @param licenseName
     * @return
     */
    private String validateDepartment(String licenseName) {
        String sql = "select * from jbpm6_validateaccount v where v.item_code = ?";
        Map map = this.dao.getByJdbc(sql, new Object[] { licenseName });
        String orgName = null;
        try {
            orgName = (String) map.get("belong_area");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return orgName;
    }

    /**
     * 描述
     * 
     * @author Watson Zheng
     * @created 2016年12月14日 下午11:02:56
     * @param dataMap
     */
    private void makeDataMap(Map<String, Object> dataMap) {
        if (dataMap != null) {
            dataMap.put("licenseName", dataMap.get("LICENSENAME"));
            dataMap.put("orgName", dataMap.get("ORGNAME"));
            dataMap.put("serialNumber", dataMap.get("SERIALNUMBER"));
            dataMap.put("owner", dataMap.get("OWNER"));
            dataMap.put("publisher", dataMap.get("PUBLISHER"));
            dataMap.put("QYFDDBR", dataMap.get("QYFDDBR"));// 页面需要改成必填字段 --
                                                           // 企业法定代表人
            dataMap.put("publishDate", dataMap.get("PUBLISHDATE"));
            dataMap.put("validFromDate", dataMap.get("VALIDFROMDATE"));
            dataMap.put("validUntilDate", dataMap.get("VALIDUNTILDATE"));
            dataMap.put("JYFS", dataMap.get("JYFS"));
            dataMap.put("XKFW", dataMap.get("XKFW"));
            dataMap.put("QYZS", dataMap.get("QYZS"));
            dataMap.put("fileNumber", dataMap.get("FILENUMBER"));
            dataMap.put("ownerType", dataMap.get("OWNERTYPE"));
        }
    }

    /**
     * 
     * 描述 生成电子证照自动盖章接口调用
     * 
     * @author Derek Zhang
     * @created 2015年12月18日 下午2:17:55
     * @param licenseName
     * @param licenseVersion
     * @param cookieMap
     */
    @SuppressWarnings("unchecked")
    private String submitLicenseDataAndAutoSeal(String licenseName, String licenseVersion,
            Map<String, String> cookieMap, Map<String, Object> dataMap, String itemCode) {
        String licenseNumber = "";
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZZZX_ZDGZJK });
        Map<String, String> licenseMap = this.findLicenseToAreaAccount(itemCode);
        String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
        // 修改
        // String configXml = (String) dataAbutment.get("CONFIG_XML");
        String configXml = licenseMap.get("CONFIG_XML");
        String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");
        StringBuffer sbuffer = new StringBuffer();
        String xml = "";
        String exeId = (String) dataMap.get("exeId");
        String jgSql = "select sqjg_addr,sqjg_code from jbpm6_execution e where e.exe_id=?";
        Connection conn;
        try {
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            Map<String, Object> jgMap = DbUtil.getMapBySql(conn, jgSql, new Object[] { exeId });
            if (jgMap != null && jgMap.get("SQJG_ADDR") != null) {
                String jgdz = (String) jgMap.get("SQJG_ADDR");

                dataMap.put("QYZS", net.evecom.core.util.StringUtil.nullToString(jgdz));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        log.info("dataMap:::" + dataMap.toString());
        sbuffer.append(configXml);
        // sbuffer.append(FreeMarkerUtil.getResultString(configXml, dataMap));
        xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
        //
        String sendXml = "<?xml version='1.0' encoding='utf-8'?>"
                + " <soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'"
                + " xmlns:xsd='http://www.w3.org/2001/XMLSchema'"
                + " xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" + "  <soap:Body>"
                + "    <SubmitLicenseDataAndAutoSeal xmlns='http://tempuri.org/'>" + "      <licenseName>" + licenseName
                + "</licenseName>" + "      <version>2015V1.1</version>" + "      <licenseDataXml><![CDATA[" + xml
                + "]]></licenseDataXml> </SubmitLicenseDataAndAutoSeal></soap:Body>" + "</soap:Envelope>";
        log.info("准备传送的报文:  " + sendXml);
        String returnStr = WebServiceCallUtil.callService(wsdlUrl, targetNamespace, "SubmitLicenseDataAndAutoSeal",
                sendXml, "text/xml; charset=utf-8", "utf-8", true, cookieMap);
        if (!StringUtils.isBlank(returnStr)) {
            Object m;
            Map<String, Object> dataAbutLog = new HashMap<String, Object>();
            dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
            dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
            dataAbutLog.put("ABUT_SENDDATA", sendXml);
            dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            try {
                m = XmlUtil.xml2Map(returnStr);
                Map body = (Map) ((Map) m).get("Body");
                Map submitLicenseResponse = (Map) body.get("SubmitLicenseDataAndAutoSealResponse");
                Map submitLicenseResult = (Map) submitLicenseResponse.get("SubmitLicenseDataAndAutoSealResult");
                licenseNumber = (String) submitLicenseResult.get("SubmitLicenseDataAndAutoSealResult");
                log.info("licenseNumber ::: " + licenseNumber);
                if (licenseNumber.equals("") || licenseNumber.length() < 3) {
                    dataAbutLog.put("ABUT_DESC", "【调用电子证照自动盖单接口】操作失败！");
                    dataAbutLog.put("ABUT_RESULT", "-1");
                } else {
                    dataAbutLog.put("ABUT_DESC", "【调用电子证照自动盖单接口】操作成功！");
                    dataAbutLog.put("ABUT_RESULT", "1");
                }
                dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
            } catch (DocumentException e) {

            }
        }
        return licenseNumber;
    }

    /**
     * 
     * 描述 电子证照生成系统用户校验
     * 
     * @author Derek Zhang
     * @created 2015年12月17日 下午2:23:02
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean authorizationExchange(Map<String, String> cookieMap, String itemCode) {
        // String userName =
        // this.dictionaryService.getDicNames("licenseInterface",
        // "usernameExchange");
        // String password =
        // this.dictionaryService.getDicNames("licenseInterface",
        // "passwordExchange");
        // 新增方法获取对应区局的账号密码
        Map<String, String> accountInfoMap = this.findLicenseToAreaAccount(itemCode);
        String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZZZX_SCJQJK });
        String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        // 修改
        xmlMap.put("account", accountInfoMap.get("validate_account"));
        xmlMap.put("password", accountInfoMap.get("validate_password"));
        StringBuffer sbuffer = new StringBuffer();
        String xml = "";
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
        String returnStr = WebServiceCallUtil.callService(wsdlUrl, targetNamespace, "Authorization", xml,
                "text/xml; charset=utf-8", "utf-8", true, cookieMap);
        String returnResult = "";
        boolean isOk = false;
        if (!StringUtils.isBlank(returnStr)) {
            Object m;
            Map<String, Object> dataAbutLog = new HashMap<String, Object>();
            dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
            dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
            dataAbutLog.put("ABUT_SENDDATA", xml);
            dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            try {
                m = XmlUtil.xml2Map(returnStr);
                Map body = (Map) ((Map) m).get("Body");
                Map authorizationResponse = (Map) body.get("AuthorizationResponse");
                Map authorizationResult = (Map) authorizationResponse.get("AuthorizationResult");
                returnResult = (String) authorizationResult.get("AuthorizationResult");
                if (returnResult.equals("true")) {
                    dataAbutLog.put("ABUT_DESC", "【调用电子证照鉴权接口】操作成功！");
                    dataAbutLog.put("ABUT_RESULT", "1");
                    isOk = true;
                } else {
                    dataAbutLog.put("ABUT_DESC", "【调用电子证照生成系统鉴权接口】操作失败！");
                    dataAbutLog.put("ABUT_RESULT", "-1");
                }
                dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
        }
        return isOk;
    }

    /**
     * 描述 查找证照对应的部门在证照系统中对应的账号密码
     * 
     * @return
     */
    private Map<String, String> findLicenseToAreaAccount(String itemCode) {
        Map<String, String> map = this.dao.getByJdbc("jbpm6_validateaccount", new String[] { "item_code" },
                new String[] { itemCode });
        return map;
    }

    /**
     * 
     * 描述 调用电子证照生成系统获取xmlSchema文件接口
     * 
     * @author Derek Zhang
     * @created 2015年12月17日 上午11:24:49
     * @param licenseName
     * @param licenseVersion
     * @return
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private String getLicenseDataXsd(String licenseName, Map<String, String> map, Map<String, String> cookieMap) {
        // 证照类型（证照/批文）
        String licenseType = map.get("licenseType");
        String returnStr1 = "";
        String methodType = "";
        if ("证照".equals(licenseType)) {
            methodType = "GetLicenseDataXsd";
            Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.INTER_CODE_ZZZX_XMLHQJK });
            String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
            String configXml = (String) dataAbutment.get("CONFIG_XML");
            Map<String, String> cookieMap1 = new HashMap<String, String>();
            String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");
            Map<String, Object> xmlMap = new HashMap<String, Object>();
            StringBuffer sbuffer = new StringBuffer();
            xmlMap.put("licenseName", licenseName);
            xmlMap.put("version", map.get("VersionNum"));
            String xml = "";
            sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
            xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
            log.info("configxml ::: " + xml);
            cookieMap1.put(WebServiceCallUtil.COOKIE_NAME, (String) cookieMap.get(WebServiceCallUtil.COOKIE_NAME));
            returnStr1 = WebServiceCallUtil.callService(wsdlUrl, targetNamespace, "GetLicenseDataXsd", xml,
                    "text/xml; charset=utf-8", "utf-8", true, cookieMap1);
        } else if ("批文".equals(licenseType)) {
            methodType = "GetApprovalDataXsd";
            Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.INTER_CODE_ZZZX_PWXMLHQJK });
            String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
            String configXml = (String) dataAbutment.get("CONFIG_XML");
            Map<String, String> cookieMap1 = new HashMap<String, String>();
            String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");
            Map<String, Object> xmlMap = new HashMap<String, Object>();
            StringBuffer sbuffer = new StringBuffer();
            xmlMap.put("approvalName", licenseName);
            xmlMap.put("serialNumberFormat", map.get("Format"));
            String xml = "";
            sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
            xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
            log.info("configxml ::: " + xml);
            cookieMap1.put(WebServiceCallUtil.COOKIE_NAME, (String) cookieMap.get(WebServiceCallUtil.COOKIE_NAME));
            returnStr1 = WebServiceCallUtil.callService(wsdlUrl, targetNamespace, "GetApprovalDataXsd", xml,
                    "text/xml; charset=utf-8", "utf-8", true, cookieMap);
        }

        log.info("=============end==============");
        Object m;
        String resultCode = "";
        try {
            m = XmlUtil.xml2Map(returnStr1);
            Map body = (Map) ((Map) m).get("Body");
            Map getLicenseDataXsdResponse = (Map) body.get(methodType + "Response");
            Map getLicenseDataXsdResult = (Map) getLicenseDataXsdResponse.get(methodType + "Result");
            resultCode = (String) getLicenseDataXsdResult.get(methodType + "Result");
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        return resultCode;
    }

    /**
     * 
     * 描述 调用获取目录列表接口
     * 
     * @author Derek Zhang
     * @created 2015年12月16日 下午5:18:39
     * @param guid
     * @param orgName
     * @param licenseName
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> getMetadataList(String guid, String orgName, String licenseName) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZZZX_MLHQJK });
        String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("guid", guid);
        xmlMap.put("orgName", orgName);
        StringBuffer sbuffer = new StringBuffer();
        String xml = "";
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
        String returnStr = WebServiceCallUtil.callService(wsdlUrl, // 调用接口，发送xml
                targetNamespace, "GetMetadataList", xml, "text/xml; charset=utf-8", "utf-8", false,
                new HashMap<String, String>());
        // String returnStr="";
        // try {
        // returnStr = proxy.getMetadata(guid, licenseName, orgName);
        // } catch (RemoteException e1) {
        // // TODO Auto-generated catch block
        // log.error(e1.getMessage());
        // }
        String returnResult = "";
        log.info("step1:" + returnStr);
        if (!StringUtils.isBlank(returnStr)) {
            Object m;
            try {
                m = XmlUtil.xml2Map(returnStr);
                Map body = (Map) ((Map) m).get("Body");
                Map getMetadataListResponse = (Map) body.get("GetMetadataListResponse");
                Map getMetadataListResult = (Map) getMetadataListResponse.get("GetMetadataListResult");
                returnResult = (String) getMetadataListResult.get("GetMetadataListResult");
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
        }
        Map<String, Object> dataAbutLog = new HashMap<String, Object>();
        dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
        dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
        dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        dataAbutLog.put("ABUT_SENDDATA", xml);
        if (StringUtil.isBlank(returnResult) || returnResult.equals("10") || returnResult.equals("11")
                || returnResult.equals("12") || returnResult.equals("13") || returnResult.equals("14")) {
            dataAbutLog.put("ABUT_DESC", "【调用电子证照共享系统目录获取接口】操作失败！");
            dataAbutLog.put("ABUT_RESULT", "-1");
            dataAbutLog.put("ERROR_LOG", returnResult);
            dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
        } else {
            dataAbutLog.put("ABUT_DESC", "【调用电子证照共享系统目录获取接口】操作成功！");
            dataAbutLog.put("ABUT_RESULT", "1");
            dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
            resultMap = this.paresMetadata(returnResult, licenseName);
        }
        return resultMap;
    }

    /**
     * 
     * 描述 解析目录获取接口返回的信息，提取对应证照类型的属性
     * 
     * @author Derek Zhang
     * @created 2015年12月17日 上午11:09:21
     * @param returnResult
     * @param licenseName
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> paresMetadata(String returnResult, String licenseName) {
        Map<String, String> result = new HashMap<String, String>();
        // 用来接收证照类型（证照/批文）
        String licenseType = "";
        Document doc = XmlUtil.stringToDocument(returnResult);
        List<Element> list = doc.getRootElement().elements("Nodes");
        if (list != null) {
            for (Element e : list) {
                Attribute attribute = null;
                attribute = e.attribute("Type");
                if (attribute != null) {
                    Iterator iterator = e.elementIterator();
                    while (iterator.hasNext()) {
                        Element node = (Element) iterator.next();
                        // 判断证照名称是否与当前证照名称相同
                        if (licenseName.equals(node.attribute("Name").getStringValue())) {
                            result.put("ID", node.attribute("ID").getStringValue());
                            result.put("Format", node.attribute("Format").getStringValue());
                            result.put("VersionNum", node.attribute("VersionNum").getStringValue());
                            result.put("Name", node.attribute("Name").getStringValue());
                            // 设置证照类型（证照/批文）
                            licenseType = attribute.getStringValue();
                            result.put("licenseType", licenseType);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 描述 调用鉴权接口
     * 
     * @author Derek Zhang
     * @created 2015年12月16日 下午5:03:15
     * @param dataMap
     */
    @SuppressWarnings("unchecked")
    private String authorization() {
        String userName = this.dictionaryService.getDicNames("licenseInterface", "username");
        String password = this.dictionaryService.getDicNames("licenseInterface", "password");

        String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");

        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZZZX_JQJK });
        String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("username", userName);
        xmlMap.put("password", password);
        StringBuffer sbuffer = new StringBuffer();
        String xml = "";
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
        String returnStr = WebServiceCallUtil.callService(wsdlUrl, targetNamespace, "Authorization", xml,
                "text/xml; charset=utf-8", "utf-8");
        String returnResult = "";
        if (!StringUtils.isBlank(returnStr)) {
            Object m;
            try {
                m = XmlUtil.xml2Map(returnStr);
                Map body = (Map) ((Map) m).get("Body");
                Map authorizationResponse = (Map) body.get("AuthorizationResponse");
                Map authorizationResult = (Map) authorizationResponse.get("AuthorizationResult");
                returnResult = (String) authorizationResult.get("AuthorizationResult");
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
        }
        Map<String, Object> dataAbutLog = new HashMap<String, Object>();
        dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
        dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
        dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        dataAbutLog.put("ABUT_SENDDATA", xml);
        if (StringUtil.isBlank(returnResult) || returnResult.startsWith("ERR")) {
            dataAbutLog.put("ABUT_DESC", "【调用电子证照鉴权接口】操作失败！");
            dataAbutLog.put("ABUT_RESULT", "-1");
            dataAbutLog.put("ERROR_LOG", returnResult);
            dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
        } else {
            dataAbutLog.put("ABUT_DESC", "【调用电子证照鉴权接口】操作成功！");
            dataAbutLog.put("ABUT_RESULT", "1");
            dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
        }
        return returnResult;
    }

    /**
     * 描述 证照文件获取接口
     * 
     * @author Derek Zhang
     * @param proxy
     * @created 2015年12月16日 下午5:37:02
     * @see net.evecom.platform.wsbs.service.LicenseService#getLicenseFile()
     */
    public void getLicenseFile(String licenseFileNumber, String licenseType, String resId,
            Map<String, String> cookieMap, String licenseName, ExchangeSoapProxy proxy) {
        // String guid = this.authorization();
        // 调用证照电文文件获取接口
        // String resultCode = this.getLicenseFileByFileNumber(guid,
        // licenseName,
        // licenseFileNumber, cookieMap);
        String resultCode = "";
        try {
            resultCode = proxy.getEdcFile(licenseFileNumber);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        log.info("获取证照返回的resultCode:" + resultCode);
        String sql = "";
        if (resultCode.contains("20,21,22,23,31")) {
            String msg = "获取证照文件失败【返回码" + resultCode + ":";
            if (resultCode.equals("20")) {
                msg += "服务器内部错误";
            } else if (resultCode.equals("21")) {
                msg += "身份未验证";
            } else if (resultCode.equals("22")) {
                msg += "用户权限不足";
            } else if (resultCode.equals("23")) {
                msg += "证照类型（证照版本）、批文类别不存在或未注册";
            } else if (resultCode.equals("31")) {
                msg += "请求的数据不存在";
            }
            msg += "】;";
            sql = "update t_bsfw_swbdata_res r set r.oper_status = 1 ,"
                    + " r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')," + " r.return_msg = r.return_msg || '"
                    + msg + "' where r.res_id = ?";
        } else {
            byte[] buffer = null;
            String querySql = "select u.user_id,u.fullname,r.exe_id,t.task_id,t.task_nodename,d.depart_id,"
                    + "d.depart_name from t_bsfw_swbdata_res r,jbpm6_execution e,jbpm6_task t,"
                    + "t_msjw_system_department d,t_wsbs_serviceitem s,t_msjw_system_sysuser u"
                    + " where t.assigner_code = u.username and t.exe_id = e.exe_id"
                    + " and t.task_id = r.task_id and r.exe_id = e.exe_id"
                    + " and s.item_code = e.item_code and d.depart_code = s.ssbmbm and r.res_id = ?";
            String insertSql = "insert into t_msjw_system_fileattach (file_id, file_name, "
                    + "  create_time, file_type, uploader_id, uploader_name, file_length, file_size,"
                    + " is_img, flow_exeid, flow_taskid, flow_taskname, uploader_depid,"
                    + " uploader_depname, sfhzd, file_content) values (sys_guid()," + " '" + licenseName
                    + ".edc', ? , 'edc', ? , ? , ? , ?, '-1',  ? , ? , ? ," + " ? , ? , '5', ? )";
            Connection conn;
            FileOutputStream fos=null;
            try {
                buffer = new BASE64Decoder().decodeBuffer(resultCode);
                String saveFileName = "e:/saveLecense/" + licenseName + "_" + new Date().getTime() + ".edc";
                fos = new FileOutputStream(saveFileName); // 输出到e盘
                fos.write(buffer, 0, buffer.length);
                fos.flush();
                fos.close();
                conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
                Map<String, Object> queryMap = DbUtil.getMapBySql(conn, querySql, new Object[] { resId }, false);
                if (queryMap != null && !queryMap.isEmpty()) {
                    String size = "";
                    Double d = 0d;
                    if (buffer.length > 1024) {
                        d = buffer.length / 1024d;
                        if (d > 1024) {
                            size = net.evecom.core.util.StringUtil.formatDouble(d / 1024d) + "M";
                        } else {
                            size = net.evecom.core.util.StringUtil.formatDouble(d) + "K";
                        }
                    } else {
                        size = buffer.length + "B";
                    }
                    DbUtil.update(conn, insertSql,
                            new Object[] { DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                                    queryMap.get("USER_ID"), queryMap.get("FULLNAME"), buffer.length, size,
                                    queryMap.get("EXE_ID"), queryMap.get("TASK_ID"), queryMap.get("TASK_NODENAME"),
                                    queryMap.get("DEPART_ID"), queryMap.get("DEPART_NAME"), buffer });
                }
            } catch (SQLException e) {
            } catch (IOException e) {
                log.error(e.getMessage());
            }finally{
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }

            String msg = "获取证照文件成功;";
            sql = "update t_bsfw_swbdata_res r set r.oper_status = 1 ,"
                    + " r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')," + " r.return_msg = r.return_msg || '"
                    + msg + "' where r.res_id = ?";
        }
        this.dao.executeSql(sql, new Object[] { resId });

    }

    /**
     * 
     * 描述 调用文件获取接口获取文件
     * 
     * @author Derek Zhang
     * @created 2015年12月16日 下午5:40:28
     * @param guid
     * @param licenseType
     * @param licenseFileNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getLicenseFileByFileNumber(String guid, String licenseName, String licenseFileNumber,
            Map<String, String> cookieMap) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_ZZZX_ZZWJHQJK });
        String wsdlUrl = (String) dataAbutment.get("WEBSERVICE_URL");
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        String targetNamespace = this.dictionaryService.getDicNames("licenseInterface", "targetNamespace");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("guid", guid);
        xmlMap.put("licenseType", licenseName);
        xmlMap.put("licenseFileNumber", licenseFileNumber);
        StringBuffer sbuffer = new StringBuffer();
        String xml = "";
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        xml = (sbuffer.toString()).equals("null") ? "" : sbuffer.toString();
        String returnStr = WebServiceCallUtil.callService(wsdlUrl, targetNamespace, "GetEdcFile", xml,
                "text/xml; charset=utf-8", "utf-8", true, cookieMap);
        log.info("调用GetEdcFile方法获得的字符串 ::: " + returnStr);
        // 调用接口失败
        String resultCode = "";
        if (!StringUtils.isBlank(returnStr)) {
            Object m;
            try {
                m = XmlUtil.xml2Map(returnStr);
                Map body = (Map) ((Map) m).get("Body");
                Map getEdcFileResponse = (Map) body.get("GetEdcFileResponse");
                Map getEdcFileResult = (Map) getEdcFileResponse.get("GetEdcFileResult");
                resultCode = (String) getEdcFileResult.get("GetEdcFileResult");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        // bytes = new BASE64Decoder().decodeBuffer(resultCode);
        return resultCode;
    }

    /**
     * 
     * 描述 属性ptzhspDbUrl get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbUrl() {
        return this.dictionaryService.getDicNames("NWReadWWDB", "dbUrl");
    }

    /**
     * 
     * 描述 属性ptzhspDbUrl get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbUsername() {
        return this.dictionaryService.getDicNames("NWReadWWDB", "dbUser");
    }

    /**
     * 
     * 描述 属性ptzhspDbUrl get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbPassword() {
        return this.dictionaryService.getDicNames("NWReadWWDB", "dbPassword");
    }

}
