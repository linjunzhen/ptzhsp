/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
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
import net.evecom.platform.esuper.dao.EsuperDao;
import net.evecom.platform.esuper.service.EsuperClientService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

/**
 * 描述 电子监察数据报送业务处理service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年1月27日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
@Service("esuperClientService")
public class EsuperClientServiceImpl extends BaseServiceImpl implements EsuperClientService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EsuperClientServiceImpl.class);
    /**
     * @Resource EsuperDao
     */
    @Resource
    private EsuperDao dao;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年1月27日 下午3:14:27
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }

    /**
     * 描述 用数据包填充模版
     * 
     * @author Derek Zhang
     * @created 2015年10月22日 上午10:34:59
     * @param xmlMap
     * @param configXml
     * @return
     */
    private StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
    }

    /**
     * 
     * 描述 省监察厅提供的加密算法
     * 
     * @author Derek Zhang
     * @created 2016年1月29日 下午2:24:54
     * @param str
     * @return
     */
    private static String makMD5(String str) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            try {
                alga.update(str.getBytes("GBK"));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            byte[] digesta = alga.digest();
            return byte2hex(digesta);
        } catch (NoSuchAlgorithmException ex) {
            log.info("没有MD5加密算法！！");
        }
        return "NULL";
    }

    /**
     * 
     * 描述 省监察厅提供的加密算法
     * 
     * @author Derek Zhang
     * @created 2016年1月29日 下午2:25:38
     * @param b
     * @return
     */

    private static String byte2hex(byte[] b) { // 二行制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + "";
            }
        }
        return hs;
    }

    /**
     * 
     * 描述 省监察厅提供的加密算法
     * 
     * @author Derek Zhang
     * @created 2016年1月29日 下午2:26:11
     * @param inputStr
     * @return
     */
    @SuppressWarnings("unused")
    private static synchronized String encryptSha256(String inputStr) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
            return new String(org.apache.axis.encoding.Base64.encode(digest));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 描述 鉴权方法
     * 
     * @author Derek Zhang
     * @created 2016年1月29日 下午2:27:37
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public String serverHander() {
        String platCode = this.dictionaryService.getDicNames("dzjc", "platCode");
        String platPwd = this.dictionaryService.getDicNames("dzjc", "platPwd");
        String webNameSpace = this.dictionaryService.getDicNames("dzjc", "webNameSpace");
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_DZJC_JQJK });
        String webserviceUrl = (String) dataAbutment.get("WEBSERVICE_URL");
        String xmlContent = (String) dataAbutment.get("CONFIG_XML");
        Map<String, Object> xmlMap = new HashMap<String, Object>();
        xmlMap.put("platCode", platCode);
        xmlMap.put("platPwd", platPwd);
        StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);
        String returnStr = WebServiceCallUtil.callService(webserviceUrl, webNameSpace, "serverHander",
                xmlBuffer.toString(), "text/xml; charset=utf-8", "utf-8");
        Object m = null;
        try {
            m = XmlUtil.xml2Map(returnStr);
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        Map body = (Map) ((Map) m).get("Body");
        Map serverHanderResponse = (Map) body.get("serverHanderResponse");
        Map result = (Map) serverHanderResponse.get("return");
        String resultCode = (String) result.get("return");
        return resultCode;
    }

    /**
     * 
     * 描述 获取对接握手码
     * 
     * @author Derek Zhang
     * @created 2016年1月29日 下午5:05:54
     * @param isServerHander
     *            true:重新发起鉴权并保存 false直接从数据库中获取
     * @return
     */
    public String getServerHander(boolean isServerHander) {
        String handerCode = "";
        if (!isServerHander) {
            Map m = this.dao.getByJdbc(
                    "select d.dic_name from t_msjw_system_dictionary d where d.type_code = ? and d.dic_code = ? ",
                    new Object[] { "dzjc", "handerCode" });
            if (m == null || m.isEmpty()) {
                handerCode = "";
            } else {
                handerCode = (String) m.get("DIC_NAME");
            }
        } else {
            handerCode = serverHander();
            saveHanderCode(handerCode, "update");
        }
        if (StringUtil.isBlank(handerCode)) {
            handerCode = serverHander();
            saveHanderCode(handerCode, "insert");
        }
        return handerCode;
    }

    /**
     * 
     * 描述 保存 handerCode
     * 
     * @author Derek Zhang
     * @created 2016年1月29日 下午5:05:54
     * @param isServerHander
     * @return
     */
    @SuppressWarnings("unchecked")
    public void saveHanderCode(String handerCode, String updateFlag) {
        if (updateFlag.equals("update")) {
            this.dao.executeSql("update t_msjw_system_dictionary d set d.dic_name = ?"
                    + " where d.type_code = ? and d.dic_code = ? ",
                    new Object[] { handerCode, "dzjc", "handerCode" });
        } else if (updateFlag.equals("insert")) {

            Map m = this.dao.getByJdbc(
                    "select d.type_code,d.type_id from t_msjw_system_dictype d where d.type_code = ? ",
                    new Object[] { "dzjc" });
            if (m != null && !m.isEmpty()) {
                String type_id = (String) m.get("TYPE_ID");
                if (!StringUtil.isBlank(type_id)) {
                    Map<String, Object> valMap = new HashMap<String, Object>();
                    valMap.put("dic_name", handerCode);
                    valMap.put("dic_code", "handerCode");
                    valMap.put("type_code", "dzjc");
                    valMap.put("dic_sn", "0");
                    valMap.put("create_time", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    valMap.put("type_id", type_id);
                    this.dao.saveOrUpdate(valMap, "t_msjw_system_dictionary", null);
                }
            }
        }
    }

    /**
     * 描述 定时调用的处理监察数据上报业务的服务
     * 
     * @author Derek Zhang
     * @created 2016年2月1日 下午2:37:25
     * @param log
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sendEsupers(Log log) {
        // 1.遍例指令表，报送数据 t_bsfw_esuper_res
        String sql = "SELECT RES_ID,BUS_ID,PROCESS_ID,DATA_TYPE,STATUS,CONTENT"
                + " FROM ( SELECT R.*,ROWNUM AS ROWNUMBER "
                + " FROM T_BSFW_ESUPER_RES R WHERE R.STATUS = ?  ORDER BY R.CREATE_TIME ASC)"
                + " B WHERE B.ROWNUMBER<=1000";
        List<Map<String, Object>> dataList = this.dao.findBySql(sql, new Object[] { "-1" }, null);
        // 2.报送数据
        if (dataList != null && !dataList.isEmpty() && dataList.size() > 0) {
            log.info("开始调用电子监察数据报送处理定时任务，向电子监管系统报送数据....");
            for (Map<String, Object> m : dataList) {
                if (m != null && m.get("DATA_TYPE") != null) {
                    Connection conn = null;
                    String dbUrl = this.dictionaryService.getDicNames("esuperMiddleDb", "dburl");
                    String dbUserName = this.dictionaryService.getDicNames("esuperMiddleDb", "dbuser");
                    String dbPass = this.dictionaryService.getDicNames("esuperMiddleDb", "dbpassword");
                    try {
                        conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
                        if (conn != null && !conn.isClosed()) {
                            String dataType = (String) m.get("DATA_TYPE");
                            if (dataType.equals(AllConstant.ESUPER_DATA_TYPE_BASIC)
                                    || dataType.equals(AllConstant.ESUPER_DATA_TYPE_LOG)
                                    || dataType.equals(AllConstant.ESUPER_DATA_TYPE_SUPERVISION)) {
                                // serverEntrance(m); webservice调用改为数据库中间度形式
                                saveToMiddleDb(conn, AllConstant.ESUPER_RECEIVE_TYPE_DATA, m);
                            } else if (dataType.equals(AllConstant.ESUPER_DATA_TYPE_WARNBACK)) {
                                saveToMiddleDb(conn, AllConstant.ESUPER_RECEIVE_TYPE_REPLY, m);
                            }
                        }
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                    }finally {
                        try {
                            if (conn != null && !conn.isClosed()) {
                                conn.close();
                            }
                        } catch (SQLException e) {
                        }
                    }
                }
            }
            log.info("结束调用电子监察数据报送处理定时任务，完成电子监管系统数据报送....");
        }
    }

    /**
     * 描述 保存数据到电子监管系统中间库库表中
     * 
     * @author Derek Zhang
     * @created 2016年2月29日 上午10:36:09
     * @param esuperReceiveTypeData
     */
    private void saveToMiddleDb(Connection conn, String esuperReceiveType, Map<String, Object> dataMap) {
        String sendTableName = this.dictionaryService.getDicNames("esuperMiddleDb", "sendTableName");
        String platCode = this.dictionaryService.getDicNames("dzjc", "platCode");
        String platId = this.dictionaryService.getDicNames("dzjc", "platId");
        String esPlatId = this.dictionaryService.getDicNames("dzjc", "esPlatId");
        String esPlatCode = this.dictionaryService.getDicNames("dzjc", "esPlatCode");
        try {
            String insertSql = "insert into " + sendTableName + " (SERIAL_ID, MESSAGE_NUM, TRANSACTION_ID,"
                    + " TRANSATCION_OID, MSG_NAME, BUSINESS_ID, SOURCES_ID, SOURCES_CODE, DESTINATION_ID,"
                    + " DESTINATION_CODE, ORIGINAL_ID, ORIGINAL_CODE, PROCESS_ID,XMLBODY, WARN_REPLYINFO,"
                    + " WARN_REPLYUSER, REPLY_TIME, STATUS, CREATE_DATE, RECEIVE_TYPE, WARN_STATUSTYPE,"
                    + " IS_PARSE) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ? )";
            String messageNum = DateTimeUtil.dateToStr(new Date(), "yyyyMMddHHmmss")
                    + StringUtils.leftPad(net.evecom.core.util.StringUtil.getRandomIntNumber(10000) + "", 4, "0");
            Object[] params = new Object[] { (String) dataMap.get("RES_ID"), messageNum,
                (String) dataMap.get("RES_ID"), (String) dataMap.get("TRANSATCION_OID"),
                (String) dataMap.get("DATA_TYPE"), (String) dataMap.get("BUS_ID"), platId, platCode, esPlatId,
                esPlatCode, platId, platCode, (String) dataMap.get("PROCESS_ID"),
                (String) dataMap.get("CONTENT"), (String) dataMap.get("WARN_REPLYINFO"),
                (String) dataMap.get("WARN_REPLYUSER"), (String) dataMap.get("REPLY_TIME"), "000",
                DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), esuperReceiveType,
                (String) dataMap.get("WARN_STATUSTYPE"), "1" };
            int result = DbUtil.update(conn, insertSql, params, false);
            if (result > 0) {
                updateEsuperRes(dataMap, "000");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 
     * 描述 数据类报送接口 webservice调用入口
     * 
     * @author Derek Zhang
     * @created 2016年2月17日 下午3:55:19
     * @param dataMap
     */
    public void serverEntrance(Map<String, Object> dataMap) {
        serverEntrance(dataMap, 0);
    }

    /**
     * 
     * 描述 数据类报送接口
     * 
     * @author Derek Zhang
     * @created 2016年2月17日 下午3:55:19
     * @param dataMap
     */
    @SuppressWarnings({ "unchecked" })
    public String serverEntrance(Map<String, Object> dataMap, int times) {
        String resultCode = "";
        if (dataMap != null && dataMap.get("DATA_TYPE") != null && dataMap.get("BUS_ID") != null) {
            String platCode = this.dictionaryService.getDicNames("dzjc", "platCode");
            String handerCode = this.dictionaryService.getDicNames("dzjc", "handerCode");
            String platId = this.dictionaryService.getDicNames("dzjc", "platId");
            String esPlatId = this.dictionaryService.getDicNames("dzjc", "esPlatId");
            String esPlatCode = this.dictionaryService.getDicNames("dzjc", "esPlatCode");
            String webNameSpace = this.dictionaryService.getDicNames("dzjc", "webNameSpace");
            Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.INTER_CODE_DZJC_JBXXSJJK });
            String webserviceUrl = (String) dataAbutment.get("WEBSERVICE_URL");
            String xmlContent = (String) dataAbutment.get("CONFIG_XML");
            Map<String, Object> xmlMap = new HashMap<String, Object>();

            String processData = ((String) dataMap.get("CONTENT")).replace("<![CDATA[", "").replace("]]>", "");
            xmlMap.put("destCode", esPlatCode);
            xmlMap.put("destID", esPlatId);
            xmlMap.put("orgCode", platCode);
            xmlMap.put("orgID", platId);
            xmlMap.put("sourceID", platId);
            xmlMap.put("sourceCode", platCode);

            xmlMap.put("loginKey", handerCode);
            xmlMap.put("msgName", (String) dataMap.get("DATA_TYPE"));
            xmlMap.put("security", makMD5(processData));
            xmlMap.put("timeStamp", DateTimeUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
            xmlMap.put("transactionID", (String) dataMap.get("RES_ID"));
            xmlMap.put("version", "1.0.0");

            xmlMap.put("businessID", (String) dataMap.get("BUS_ID"));
            xmlMap.put("processData", "<![CDATA[" + processData + "]]>");
            xmlMap.put("processID", (String) dataMap.get("PROCESS_ID"));

            StringBuffer xmlBuffer = this.makeDataXml(xmlMap, xmlContent);

            String returnStr = WebServiceCallUtil.callService(webserviceUrl, webNameSpace, "serverEntrance",
                    xmlBuffer.toString(), "text/xml; charset=utf-8", "utf-8");
            if (returnStr != null && !StringUtil.isBlank(returnStr)) {
                Object m = null;
                try {
                    m = XmlUtil.xml2Map(returnStr);
                } catch (DocumentException e) {
                    log.error(e.getMessage());
                }
                Map body = (Map) ((Map) m).get("Body");
                Map serverHanderResponse = (Map) body.get("serverEntranceResponse");
                Map result = (Map) serverHanderResponse.get("return");
                resultCode = (String) result.get("return");
                if (!resultCode.equals("0")) {
                    if (resultCode.equals("001")) {
                        if (times < 2) {
                            getServerHander(true);
                            resultCode = serverEntrance(dataMap, ++times);
                        } else {
                            updateEsuperRes(dataMap, resultCode);
                            resultCode = "0";
                        }
                    } else {
                        updateEsuperRes(dataMap, resultCode);
                        resultCode = "0";
                    }
                }
            }
        }
        return resultCode;
    }

    /**
     * 描述 更新电子监察数据报送结果返回码
     * 
     * @author Derek Zhang
     * @created 2016年2月18日 上午9:47:07
     * @param dataMap
     * @param resultCode
     */
    private void updateEsuperRes(Map<String, Object> dataMap, String resultCode) {
        String updateSql = "update t_bsfw_esuper_res r set r.status = ? ,r.send_time= ? where res_id=? ";
        this.dao.executeSql(
                updateSql,
                new Object[] { resultCode, DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        dataMap.get("RES_ID") });

    }
}
