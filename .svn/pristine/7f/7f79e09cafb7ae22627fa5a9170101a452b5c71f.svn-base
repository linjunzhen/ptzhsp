/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.service.SwbDataParseService;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 * 描述 描述 省网办接口服务业务接口 省网办数据解析服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月3日 上午10:19:14
 */
@SuppressWarnings("rawtypes")
@Service("swbDataParseService")
public class SwbDataParseServiceImpl extends BaseServiceImpl implements SwbDataParseService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SwbDataParseServiceImpl.class);
    /**
     * 引入获取省网办报送数据的处理业务的dao
     */
    @Resource
    private SwbInterfaceDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private SysRoleDao sysRoleDao;
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
     * 属性ptzhspDbUrl
     */
    private String ptzhspDbUrl;
    /**
     * 属性ptzhspDbUsername
     */
    private String ptzhspDbUsername;
    /**
     * 属性ptzhspDbPassword
     */
    private String ptzhspDbPassword;

    /**
     * 描述 获取省网办上的待办统计数据
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @return
     */
    @Override
    public Map<String, Integer> getPendingTaskCount(SysUser sysUser) {
        Map<String, Integer> returnArr = null;
        if (sysUser != null) {
            Object object = makeXmlCallService(sysUser, "getUserBusinessCount");
            returnArr = object == null ? null : this.getBusCount(object);
        }
        return returnArr;
    }

    /**
     * 描述 获取省网办上的待办任务
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @return
     */
    @Override
    public List<Map<String, String>> getPendingTaskList(SysUser sysUser) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (sysUser != null) {
            Object object = makeXmlCallService(sysUser, "getUserBusinessList");
            list = object == null ? null : this.getBusList(object);
        }
        return list;
    }

    /**
     * 
     * 描述 获取省网办上的单点登录的令牌
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @return
     */
    @Override
    public Map<String, String> getSwbLoginDynamicPwd(SysUser sysUser) {
        Map<String, String> swbLoginDynamicPwd = new HashMap<String, String>();
        if (sysUser != null) {
            Object object = makeXmlCallService(sysUser, "getDynamicPwdByThird");
            swbLoginDynamicPwd = (object == null ? swbLoginDynamicPwd : getDynamicPwd(object));
        }
        return swbLoginDynamicPwd;
    }

    /**
     * 描述 解析接口返回的报文生成任务统计数
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @param object
     * @return
     */
    private Map<String, Integer> getBusCount(Object object) {
        if (object == null)
            return null;
        String returnXml = (String) object;
        SAXReader saxReader = new SAXReader();
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Document document = saxReader.read(new ByteArrayInputStream(returnXml.getBytes("GB2312")));
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext();) {
                Element employee = (Element) i.next();
                String businessType = "";
                String count = "";
                for (Iterator j = employee.elementIterator(); j.hasNext();) {
                    Element node = (Element) j.next();
                    if (node.getName().equals("businessType")) {
                        businessType = node.getText();
                    } else if (node.getName().equals("count")) {
                        count = node.getText();
                    }
                }
                if (StringUtils.isNotBlank(businessType)) {
                    map.put(businessType, StringUtils.isNotBlank(count) ? Integer.valueOf(count) : 0);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return map;
    }

    /**
     * 描述 解析接口返回的报文生成任务列表
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @param object
     * @return
     */
    private List<Map<String, String>> getBusList(Object object) {
        if (object == null)
            return null;
        String returnXml = (String) object;
        SAXReader saxReader = new SAXReader();
        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        try {
            Document document = saxReader.read(new ByteArrayInputStream(returnXml.getBytes("GB2312")));
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext();) {
                Map<String, String> map = new HashMap<String, String>();
                Element employee = (Element) i.next();
                if (employee.getName().equals("Business")) {
                    for (Iterator j = employee.elementIterator(); j.hasNext();) {
                        map.put(employee.getName(), employee.getText());
                    }
                    mList.add(map);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return mList;
    }

    /**
     * 
     * 描述 解析接口返回的报文，获取动态令牌
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @param object
     * @return
     */
    private Map<String, String> getDynamicPwd(Object object) {
        if (object == null)
            return null;
        String returnXml = (String) object;
        Map<String, String> dynamicPwdMap = new HashMap<String, String>();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new ByteArrayInputStream(returnXml.getBytes("GB2312")));
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext();) {
                Element employee = (Element) i.next();
                String code = "";
                String msg = "";
                String data = "";
                for (Iterator j = employee.elementIterator(); j.hasNext();) {
                    Element node = (Element) j.next();
                    if (node.getName().equals("code")) {
                        code = node.getText();
                    } else if (node.getName().equals("msg")) {
                        msg = node.getText();
                    } else if (node.getName().equals("data")) {
                        data = node.getText();
                    }
                }
                dynamicPwdMap.put("code", code);
                dynamicPwdMap.put("msg", msg);
                dynamicPwdMap.put("data", data);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (StringUtils.isBlank(dynamicPwdMap.get("code"))) {
            dynamicPwdMap.put("code", "009");// 调用接口产生异常。
            dynamicPwdMap.put("msg", "调用接口产生异常：【" + returnXml + "】");
        }
        return dynamicPwdMap;
    }

    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 构造XML调用接口方法
     * 
     * @author Derek Zhang
     * @created 2015年11月3日 上午10:19:14
     * @param sysUser
     * @param methodName
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object makeXmlCallService(SysUser sysUser, String methodName) {
        String wsdlPoint = "http://www.tyyh.fj.cn:8080/umc/services/ThirdPartySsoService?wsdl";
        String namespace = "http://www.tyyh.fj.cn:8080/umc/services/ThirdPartySsoService";
        Map<String, Object[]> params = new HashMap<String, Object[]>();
        String roleCode = "";
        String roleName = "";
        if (sysRoleDao.hasRoleByCode(sysUser.getUserId(), new String[] { "swbglrr" })) {
            roleCode = this.dictionaryService.getDicCode("swbdjpz", "管理人员");
            roleName = "管理人员";
        } else {
            roleCode = this.dictionaryService.getDicCode("swbdjpz", "经办人");
            roleName = "经办人";
        }
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version='1.0' encoding='GB2312'?>");
        xml.append("<UMC xmlns='userInfo'>");
        xml.append("<UserInfo>");
        xml.append("  <uniqueCode>" + sysUser.getUsername() + "</uniqueCode>");
        xml.append("  <name>" + sysUser.getFullname() + "</name>");
        xml.append("  <sex>"
                + ((sysUser.getSex() == null || (sysUser.getSex() != 1 && sysUser.getSex() != 2)) ? 1 : sysUser
                        .getSex()) + "</sex>");
        xml.append("  <roleCode>" + roleCode + "</roleCode>");
        xml.append("  <roleName>" + roleName + "</roleName>");
        xml.append("  <mobile>" + (StringUtils.isBlank(sysUser.getMobile()) ? "18900000000" : sysUser.getMobile())
                + "</mobile>");
        Map<String, Object> dept = this.dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", "DEPART_ID".split(","), sysUser
                .getDepId().split(","));
        xml.append("  <unitCode>" + (String) dept.get("DEPART_CODE") + "</unitCode>");
        xml.append("  <unitName>" + (String) dept.get("DEPART_NAME") + "</unitName>");
        xml.append("  <areaCode>" + (String) dept.get("ADVI_CODE") + "</areaCode>");
        xml.append("  </UserInfo>");
        xml.append("</UMC>");
        String unitAliasName = this.dictionaryService.getDicCode("swbdjpz", "省网办对接单位标识");
        String password = this.dictionaryService.getDicCode("swbdjpz", "省网办接入密码");
        params.put("unitAliasName", new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN,
            unitAliasName });
        params.put("password",
                new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN, password });
        params.put("userInfoXml",
                new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN, xml.toString() });

        QName returnXMLType = org.apache.axis.encoding.XMLType.XSD_STRING;
        Object object = WebServiceCallUtil.callService(wsdlPoint, namespace, methodName, params, returnXMLType);
        return object;
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2015年11月24日 上午11:18:23
     * @param dataAbutment
    @SuppressWarnings({ "unchecked", "unused" })
    public void sendMsgByList(Map<String, Object> dataAbutment) {
        // String configxml = (String) dataAbutment.get("CONFIG_XML");
        // String serviceURL = (String) dataAbutment.get("WEBSERVICE_URL");
        String wsdlPoint = "http://10.23.251.210:8008/SMSService.asmx?WSDL";
        String namespace = "http://tempuri.org/";

        Map<String, Object[]> params = new HashMap<String, Object[]>();
        // StringBuffer xml = new StringBuffer();
        // xml.append(FreeMarkerUtil.getResultString(configxml, xmlMap));
        // if (!(xml.toString()).equals("null")) {
        params.put("sysCode",
                new Object[] { org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN, "cwpt2015" });
        params.put("username", new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN,
            "cwpt2015" });
        params.put("pwd", new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN, "123456" });
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> olist = this.findAllByTableName("t_msjw_system_msgsend");
        int i = 0;
        // for(Map m:olist){
        i++;
        Map<String, Object> vMap = new HashMap<String, Object>();
        vMap.put("SmsContent", "测试一下");
        vMap.put("PhoneNumber", "15880014266");
        vMap.put("PreTime", "");
        vMap.put("IsneedReply", false);
        vMap.put("ExpendNum", "1231");
        nodes.add(vMap);
        Map<String, Object> vMap1 = new HashMap<String, Object>();
        vMap1.put("SmsContent", "再测试一下");
        vMap1.put("PhoneNumber", "15880014266");
        vMap1.put("PreTime", "");
        vMap1.put("IsneedReply", false);
        vMap1.put("ExpendNum", "1232");
        nodes.add(vMap1);
        // }
        params.put("nodes", new Object[] { org.apache.axis.encoding.XMLType.XSD_ANYTYPE, ParameterMode.IN, nodes });
        QName returnXMLType = org.apache.axis.encoding.XMLType.XSD_STRING;
        Object object = WebServiceCallUtil.callService(wsdlPoint, namespace, "CallSMSList", params, returnXMLType);
        log.info((String) object);
        // }
    }
    */
    /**
     * 描述 从省网办同步办件数据
     * 
     * @author Derek Zhang
     * @created 2016年1月21日 下午4:04:40
     * @param log
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    public void parseSwbDataFromProa(Log log) {
        // 获取数据
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_SXFW });
        Connection wsbsdtconn = null;
        Connection ptzhspconn = null;
        try {
            wsbsdtconn = DbUtil.getConnect((String) dataAbutment.get("DB_URL"),
                    (String) dataAbutment.get("DB_USERNAME"), (String) dataAbutment.get("DB_PASSWORD"));
            ptzhspconn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(),
                    this.getPtzhspDbPassword());
            // 获取事项信息
            parsProvinceItemDatas(log, wsbsdtconn, ptzhspconn);
            // 获取反馈信息
            parsProvinceReturnDatas(log, wsbsdtconn, ptzhspconn);//省网下发乱码 暂不可用20180613 //20191028启用
            // 解析处理办件数据
            parsProvinceDatas(log, wsbsdtconn, ptzhspconn);
            // 解析处理附件数据
            parsProvinceAttrs(log, wsbsdtconn, ptzhspconn);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (wsbsdtconn != null)
                DbUtils.closeQuietly(wsbsdtconn);
            if (ptzhspconn != null)
                DbUtils.closeQuietly(ptzhspconn);
        }
    }
    /**
     * 
     * 描述  解析返回信息
     * @author Yanisin Shi
     * @param log
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    public void parseReturnInfos(Log log) {
        // 获取数据
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_SXFW });
        Connection wsbsdtconn = null;
        Connection ptzhspconn = null;
        try {
            wsbsdtconn = DbUtil.getConnect((String) dataAbutment.get("DB_URL"),
                    (String) dataAbutment.get("DB_USERNAME"), (String) dataAbutment.get("DB_PASSWORD"));
            ptzhspconn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(),
                    this.getPtzhspDbPassword());
            DriverManager.setLoginTimeout(10);
            // 获取反馈信息
            parsProvinceReturnDatasNew(log, wsbsdtconn, ptzhspconn);//省网下发乱码 暂不可用20180613 //20191028启用
          

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (wsbsdtconn != null)
                DbUtils.closeQuietly(wsbsdtconn);
            if (ptzhspconn != null)
                DbUtils.closeQuietly(ptzhspconn);
        }
    }

    /**
     * 描述 解析省网办下发的办件数据
     * 
     * @author Derek Zhang
     * @created 2016年1月22日 上午10:54:13
     * @param log
     * @param conn
     * @param ptzhspConn
     */
    private void parsProvinceDatas(Log log, Connection conn, Connection ptzhspConn) {
        String sql = "select * from (select * from provinceinfo p where not exists "
                + "(select 1 from provinceinfosync u where u.unid=p.unid)"
                + " and p.type in('10','80','81','82','85','86','25') order by p.createtime asc) where rownum<100 ";
        List<Map<String, Object>> provinceinfoList = DbUtil.findBySql(conn, sql, new Object[] {}, false);
        if (provinceinfoList != null && !provinceinfoList.isEmpty() && provinceinfoList.size() > 0) {
            log.info("开始调用【解析省网办下发的办件数据】定时任务，解析处理办件数据....");
            for (Map<String, Object> provinceinfo : provinceinfoList) {
                if("10".equals(provinceinfo.get("TYPE"))){
                    parseProvinceBj(provinceinfo, conn, ptzhspConn);
                }else if("80".equals(provinceinfo.get("TYPE"))){
                    parseProvinceGc(provinceinfo, conn, ptzhspConn);
                }else if("81".equals(provinceinfo.get("TYPE"))){
                    parseProvinceJszt(provinceinfo, conn, ptzhspConn);
                }else if("82".equals(provinceinfo.get("TYPE"))){
                    parseProvinceJshf(provinceinfo, conn, ptzhspConn);
                }else if("85".equals(provinceinfo.get("TYPE"))){
                    parseProvinceJg(provinceinfo, conn, ptzhspConn);
                }else if("86".equals(provinceinfo.get("TYPE"))){
                    parseProvinceJgfj(provinceinfo, conn, ptzhspConn);
                } else if ("25".equals(provinceinfo.get("TYPE"))) {
                    parseProvinceJfxx(provinceinfo, conn, ptzhspConn);
                }
            }
            log.info("调用【解析省网办下发的办件数据】定时任务，解析处理办件数据结束....");
        }
    }

    /**
     * 描述:解析缴费信息
     *
     * @author Madison You
     * @created 2020/2/27 11:52:00
     * @param
     * @return
     */
    private void parseProvinceJfxx(Map<String, Object> provinceinfo, Connection conn, Connection ptzhspConn) {
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String SN = root.selectSingleNode("//Case/Body/ApasInfo/SN").getStringValue();
        String PayOrdNum = root.selectSingleNode("//Case/Body/Node/PayOrdNum").getStringValue();
        String PayOrdDate = root.selectSingleNode("//Case/Body/Node/PayOrdDate").getStringValue();
        String PayOrdTime = root.selectSingleNode("//Case/Body/Node/PayOrdTime").getStringValue();
        String PayDevice = root.selectSingleNode("//Case/Body/Node/PayDevice").getStringValue();
        String PayEBillCode = root.selectSingleNode("//Case/Body/Node/PayEBillCode").getStringValue();
        String PayTranTime = root.selectSingleNode("//Case/Body/Node/PayTranTime").getStringValue();
        String PayMent = root.selectSingleNode("//Case/Body/Node/PayMent").getStringValue();
        String PayBankNo = root.selectSingleNode("//Case/Body/Node/PayBankNo").getStringValue();
        String PayBankCode = root.selectSingleNode("//Case/Body/Node/PayBankCode").getStringValue();
        String PayState = root.selectSingleNode("//Case/Body/Node/PayState").getStringValue();
        String ChargeDetails = root.selectSingleNode("//Case/Body/Node/ChargeDetails").asXML();
        String ChargeDetailsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + ChargeDetails, "UTF-8");
        JSONObject ChargeDetailsObj = JSONObject.parseObject(ChargeDetailsJson);
        String pay_status = "";
        /*缴费状态*/
        if (PayState.equals("1")) {
            pay_status = "1";
        } else if (PayState.equals("2")) {
            pay_status = "3";
        } else {
            pay_status = "4";
        }
        /*更新缴费状态*/
        StringBuffer updateSql = new StringBuffer();
        updateSql.append(" update jbpm6_execution set pay_status = ?  where exe_id = ? ");
        DbUtil.update(ptzhspConn, updateSql.toString(), new Object[]{pay_status, SN});
        /*保存缴费信息*/
        StringBuffer insertSql = new StringBuffer();
        insertSql.append(" insert into T_BSFW_SWBRETURN_PAYINFO (ID,EXE_ID,PAY_ORD_NUM,PAY_ORD_DATE,PAY_ORD_TIME,");
        insertSql.append(" PAY_DEVICE,PAY_EBILL_CODE,PAY_TRAN_TIME,PAY_MENT,PAY_BANK_NO,PAY_BANK_CODE,PAY_STATE, ");
        insertSql.append(" CHARGE_DETAILS) values (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
        DbUtil.update(ptzhspConn, insertSql.toString(), new Object[]{UUIDGenerator.getUUID(),SN,PayOrdNum,PayOrdDate,
                PayOrdTime,PayDevice,PayEBillCode,PayTranTime,PayMent,PayBankNo,PayBankCode,
                PayState,ChargeDetailsObj.toString()});
        /*更新接收状态*/
        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                conn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);
    }

    /**
     * 
     * 描述 解析省反馈数据
     * @author Kester Chen
     * @created 2017-11-1 上午10:21:57
     * @param log2
     * @param wsbsdtconn
     * @param ptzhspconn
     */
    private void parsProvinceReturnDatas(Log log, Connection conn, Connection ptzhspConn) {
        // TODO Auto-generated method stub
        String sql = "select * from (select * from provinceinfo p where not exists "
                + "(select 1 from provinceinfosync u where u.unid=p.unid)"
                + " and p.type = '99' order by p.createtime asc) where rownum<100 ";
        List<Map<String, Object>> returninfoList = DbUtil.findBySql(conn, sql, new Object[] {}, false);
        if (returninfoList != null && !returninfoList.isEmpty() && returninfoList.size() > 0) {
            log.info("开始调用【解析省网办返回数据】定时任务，解析处理办件数据....");
            for (Map<String, Object> returninfo : returninfoList) {
                log.info("解析省反馈数据" + returninfo.get("UNID"));
                parseProvinceReturnData(returninfo, conn, ptzhspConn);
            }
            log.info("调用【解析省网办返回数据】定时任务，解析处理办件数据结束....");
        }
        
    }
    /**
     * 
     * 描述  解析省网返回信息
     * @author Yanisin Shi
     * @param log
     * @param conn
     * @param ptzhspConn
     */
    private void parsProvinceReturnDatasNew(Log log, Connection conn, Connection ptzhspConn) {
        // TODO Auto-generated method stub
        String sql = "select * from (select * from provinceinfo p where not exists "
                + "(select 1 from provinceinfosync u where u.unid=p.unid)"
                + " and p.type = '99' and p.createtime>=to_date('2022-01-14 00:00:00','YYYY-MM-DD hh24:mi:ss')"
                + " ) ";
        List<Map<String, Object>> returninfoList = DbUtil.findBySql(conn, sql, new Object[] {}, false);
        if (returninfoList != null && !returninfoList.isEmpty() && returninfoList.size() > 0) {
            log.info("开始调用【解析省网办返回数据】定时任务，解析处理办件数据....");
            for (Map<String, Object> returninfo : returninfoList) {
                log.info("解析省反馈数据" + returninfo.get("UNID"));
                parseProvinceReturnData(returninfo, conn, ptzhspConn);
            }
            log.info("调用【解析省网办返回数据】定时任务，解析处理办件数据结束....");
        }
        
    }
    /**
     * 
     * 描述 获取事项信息至本地库
     * @author Kester Chen
     * @created 2017-11-16 下午3:02:14
     * @param log
     * @param conn
     * @param ptzhspConn
     */
    private void parsProvinceItemDatas(Log log, Connection conn, Connection ptzhspConn) {
        // TODO Auto-generated method stub
        String sql = "select * from (select * from provinceinfo p where not exists "
                + "(select 1 from provinceinfosync u where u.unid=p.unid)"
                + " and p.type in('100','101') order by p.createtime asc) where rownum<100 ";
        List<Map<String, Object>> itemInfoList = DbUtil.findBySql(conn, sql, new Object[] {}, false);
        log.info("开始调用【解析省网办下发事项数据】定时任务，解析处理办件数据....");
        if (itemInfoList != null && !itemInfoList.isEmpty() && itemInfoList.size() > 0) {
            for (Map<String, Object> itemInfo : itemInfoList) {
                log.info("解析省下发事项" + itemInfo.get("UNID"));
                parseProvinceItemData(itemInfo, conn, ptzhspConn);
            }
        }
        log.info("调用【解析省网办下发事项数据】定时任务，解析处理办件数据结束....");
    }


    /**
     * 描述 解析处理办件数据
     * 
     * @author Derek Zhang
     * @created 2016年1月22日 上午11:00:13
     * @param provinceinfo
     * @param conn
     * @param ptzhspConn
     */
    private void parseProvinceBj(Map<String, Object> provinceinfo, Connection wsbsdtconn, Connection ptzhspConn) {
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String proposer = root.selectSingleNode("//Case/Body/Proposer").asXML();
        String operator = root.selectSingleNode("//Case/Body/Operator").asXML();
        String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
        // 定义服务事项
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + apasInfo, "UTF-8");
        String proposerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + proposer, "UTF-8");
        String operatorJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + operator, "UTF-8");
        String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        String serviceId = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("ServiceID")).get(0);
        String serviceCode = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("ServiceCode")).get(0);
        JSONObject attrObj = JSONObject.parseObject(attrsJson);
        JSONArray arr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));

        /*Madison You 2020/4/29新增 快递信息*/
        String expressJson = null;
        Node expressesNode = root.selectSingleNode("//Case/Body/Expresses");
        if (expressesNode != null) {
            String express = expressesNode.asXML();
            expressJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + express, "UTF-8");
        }

        StringBuffer attrIds = new StringBuffer();
        if (arr!=null) {
            for (int m = 0; m < arr.size(); m++) {
                JSONObject jObj = ((JSONObject) arr.get(m));
                if (jObj != null && !jObj.isEmpty()) {
                    if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                        if (attrIds.length() != 0) {
                            attrIds.append(",");
                        }
                        attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                    }
                }
            }
        }
        String insertSql = "insert into t_bsfw_swbprovdata "
                + "  (id, swb_item_id, swb_item_code, create_time, status, "
                + "apasinfo, proposer, operator, express , attrs, attrids, type) values "
                + " ( ? , ? , ? , ? , 0 , ? , ? , ? , ? , ? , ? , ? ) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { provinceinfo.get("UNID"), serviceId, serviceCode,
            DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, proposerJson, operatorJson,
            expressJson , attrsJson, attrIds.toString(), "10" }, false);
        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);

    }
    /**
     * 
     * 描述    解析省网办下发办件过程信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:47:41
     * @param provinceinfo
     * @param wsbsdtconn
     * @param ptzhspConn
     */
    private void parseProvinceGc(Map<String, Object> provinceinfo, Connection wsbsdtconn, Connection ptzhspConn) {
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String opinions  = root.selectSingleNode("//Case/Body/Opinions").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + apasInfo, "UTF-8");
        String opinionsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + opinions, "UTF-8");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        //String unid = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("CityinfoUnid")).get(0);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);
        
        String insertSql = "insert into t_bsfw_swbprovdata "
                + "  (id, create_time, status, apasinfo, opinionsornode,exe_id,type) values "
                + " ( ? , ? , 0 , ? , ? , ?, ?) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { provinceinfo.get("UNID"), 
            DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, opinionsJson, sn, "80" }, false);

        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);
    }
    /**
     * 
     * 描述    解析省网办下发计时暂停信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:48:51
     * @param provinceinfo
     * @param wsbsdtconn
     * @param ptzhspConn
     */
    private void parseProvinceJszt(Map<String, Object> provinceinfo, Connection wsbsdtconn, Connection ptzhspConn){
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String node = root.selectSingleNode("//Case/Body/Node").asXML();
        //String pattrs = root.selectSingleNode("//Case/Body/Node/Patch/PAttrs").asXML();
        //String attrs = root.selectSingleNode("//Case/Body/Node/Attrs").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + apasInfo, "UTF-8");
        String nodeJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + node, "UTF-8");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        //String unid = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("CityinfoUnid")).get(0);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into t_bsfw_swbprovdata "
                + "  (id, create_time, status, apasinfo, opinionsornode,exe_id,type) values "
                + " ( ? , ? , 0 , ? , ? , ? , ?) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { provinceinfo.get("UNID"), 
            DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, nodeJson, sn , "81" }, false);

        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);
    }
    
    /**
     * 
     * 描述    解析省网办下发计时恢复信息
     * @author Danto Huang
     * @created 2019年5月20日 下午2:48:51
     * @param provinceinfo
     * @param wsbsdtconn
     * @param ptzhspConn
     */
    private void parseProvinceJshf(Map<String, Object> provinceinfo, Connection wsbsdtconn, Connection ptzhspConn){
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String node = root.selectSingleNode("//Case/Body/Node").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + apasInfo, "UTF-8");
        String nodeJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + node, "UTF-8");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        //String unid = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("CityinfoUnid")).get(0);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into t_bsfw_swbprovdata "
                + "  (id, create_time, status, apasinfo, opinionsornode,exe_id,type) values "
                + " ( ? , ? , 0 , ? , ? , ? , ?) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { provinceinfo.get("UNID"), 
            DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, nodeJson, sn , "82" }, false);

        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);
    }
    
    /**
     * 
     * 描述    解析省网办下发结果信息
     * @author Danto Huang
     * @created 2019年5月21日 上午9:30:54
     * @param provinceinfo
     * @param wsbsdtconn
     * @param ptzhspConn
     */
    private void parseProvinceJg(Map<String, Object> provinceinfo, Connection wsbsdtconn, Connection ptzhspConn){
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String resultattrs = root.selectSingleNode("//Case/Body/ResultAttrs").asXML();
        String attrs = root.selectSingleNode("//Case/Body/Attrs").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + apasInfo, "UTF-8");
        String attrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + attrs, "UTF-8");
        String resultattrsJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + resultattrs, "UTF-8");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        JSONObject attrObj = JSONObject.parseObject(attrsJson);
        JSONArray arr = ((JSONArray) ((JSONObject) attrObj.get("Attrs")).get("Attr"));
        JSONObject resultattrObj = JSONObject.parseObject(resultattrsJson);
        JSONArray resultattr = ((JSONArray) ((JSONObject) resultattrObj.get("ResultAttrs")).get("ResultAttr"));
        StringBuffer attrIds = new StringBuffer();
        for (int m = 0; m < arr.size(); m++) {
            JSONObject jObj = ((JSONObject) arr.get(m));
            if (jObj != null && !jObj.isEmpty()) {
                if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                    if (attrIds.length() != 0) {
                        attrIds.append(",");
                    }
                    attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                }
            }
        }
        for (int m = 0; m < resultattr.size(); m++) {
            JSONObject jObj = ((JSONObject) resultattr.get(m));
            if (jObj != null && !jObj.isEmpty()) {
                if ((jObj).get("UNID") != null && ((JSONArray) (jObj).get("UNID")).get(0) != null) {
                    if (attrIds.length() != 0) {
                        attrIds.append(",");
                    }
                    attrIds.append((String) ((JSONArray) (jObj).get("UNID")).get(0));
                }
            }
        }
        //String unid = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("CityinfoUnid")).get(0);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into t_bsfw_swbprovdata "
                + "  (id, create_time, status, attrids, apasinfo, attrs,exe_id,type,resultattrs) values "
                + " ( ? , ? , 0 , ? , ? , ? , ? , ? , ?) ";
        DbUtil.update(ptzhspConn, insertSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"),
                        attrIds, apasInfoJson, attrsJson, sn, "85", resultattrsJson },
                false);

        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);
    }
    /**
     * 
     * 描述    解析省网办下发结果信息（电子证照、批文）
     * @author Danto Huang
     * @created 2019年5月21日 上午9:32:11
     * @param provinceinfo
     * @param wsbsdtconn
     * @param ptzhspConn
     */
    private void parseProvinceJgfj(Map<String, Object> provinceinfo, Connection wsbsdtconn, Connection ptzhspConn){
        Document document = XmlUtil.stringToDocument((String) provinceinfo.get("content"));
        Element root = document.getRootElement();
        String apasInfo = root.selectSingleNode("//Case/Body/ApasInfo").asXML();
        String apasInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + apasInfo, "UTF-8");
        JSONObject obj = JSONObject.parseObject(apasInfoJson);
        //String unid = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("CityinfoUnid")).get(0);
        String sn = (String) ((JSONArray) ((JSONObject) obj.get("ApasInfo")).get("SN")).get(0);

        String insertSql = "insert into t_bsfw_swbprovdata "
                + "  (id, create_time, status, apasinfo, exe_id, type) values "
                + " ( ? , ? , 0 , ? , ? , ? ) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { provinceinfo.get("UNID"),
                DateTimeUtil.dateToStr(new Date(), "yyyy_MM-dd HH:mm:ss"), apasInfoJson, sn, "86" }, false);

        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { provinceinfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), provinceinfo.get("FROMAREACODE"),
                        provinceinfo.get("FROMAREANAME"), provinceinfo.get("TOAREACODE"),
                        provinceinfo.get("TOAREANAME") }, false);
    }

    /**
     * 
     * 描述 解析处理下发事项数据
     * @author Kester Chen
     * @created 2017-11-16 下午3:13:16
     * @param returninfo
     * @param wsbsdtconn
     * @param ptzhspConn
     */
    private void parseProvinceItemData(Map<String, Object> itemInfo, Connection wsbsdtconn, Connection ptzhspConn) {
        // TODO Auto-generated method stub
        String content = (String) itemInfo.get("content");
        Map<String,Object> map = getDataByContent(content,(String)itemInfo.get("TYPE"));
        String insertSql = "insert into PROVINCEITEMINFO "
                + "  (unid, type, createtime, xsdtype, copyright, action,fromareacode," +
                "fromareaname,toareacode,toareaname,applyfrom,content,DEPT_UNID,DEPT_NAME,SERVICE_UNID" +
                ",SERVICE_NAME,PARENT_UNID,PARENT_NAME) values "
                + " ( ? , ? , ?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { itemInfo.get("UNID"),itemInfo.get("TYPE"),
                itemInfo.get("CREATETIME"),itemInfo.get("XSDTYPE"),itemInfo.get("COPYRIGHT"),
                itemInfo.get("ACTION"),itemInfo.get("FROMAREACODE"),
                itemInfo.get("FROMAREANAME"),itemInfo.get("TOAREACODE"),
                itemInfo.get("TOAREANAME"),itemInfo.get("APPLYFROM"),
                content,map.get("DEPT_UNID"),map.get("DEPT_NAME"),map.get("SERVICE_UNID")
                ,map.get("SERVICE_NAME"),map.get("PARENT_UNID"),map.get("PARENT_NAME") }, false);
        
        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { itemInfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), itemInfo.get("FROMAREACODE"),
                        itemInfo.get("FROMAREANAME"), itemInfo.get("TOAREACODE"),
                        itemInfo.get("TOAREANAME") }, false);
        
    }
    
    /**
     * 
     * 描述 解析处理返回数据
     * @author Kester Chen
     * @created 2017-11-1 上午10:27:28
     * @param returninfo
     * @param conn
     * @param ptzhspConn
     */
    private void parseProvinceReturnData(Map<String, Object> returninfo, Connection wsbsdtconn, Connection ptzhspConn) {
        // TODO Auto-generated method stub
        Document document = XmlUtil.stringToDocument((String) returninfo.get("content"));
        Element root = document.getRootElement();
        String router = root.selectSingleNode("//Feedback/Router").asXML();
        String body = root.selectSingleNode("//Feedback/Body").asXML();
        // 定义服务事项
        String routerJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + router, "UTF-8");
        String bodyJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>" + body, "UTF-8");
        JSONObject bodyObj = JSONObject.parseObject(bodyJson);
        String caseunid = (String) ((JSONArray) ((JSONObject) bodyObj.get("Body")).get("CaseUnid")).get(0);
        String sn = ((JSONArray) ((JSONObject) bodyObj.get("Body")).get("SN"))==null?
                "":(String)((JSONArray) ((JSONObject) bodyObj.get("Body")).get("SN")).get(0);
        String result = (String) ((JSONArray) ((JSONObject) bodyObj.get("Body")).get("Result")).get(0);
        JSONObject obj = JSONObject.parseObject(routerJson);
        String sendTime = (String) ((JSONArray) ((JSONObject) obj.get("Router")).get("Time")).get(0);
        
        String insertSql = "insert into T_BSFW_SWBDATA_RETURNINFO "
                + "  (unid, type, caseunid, sn, send_time, result) values "
                + " ( ? , 99 , ?  , ? , ? , ?  ) ";
        DbUtil.update(ptzhspConn, insertSql, new Object[] { returninfo.get("UNID"),
                caseunid,sn,sendTime,result }, false);
        
        String insertSyncSql = "insert into provinceinfosync (unid, type, updatetime, content, createtime,"
                + "fromareacode, fromareaname, toareacode, toareaname,etype) values "
                + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss') ,?,?,?,?,1)";
        DbUtil.update(
                wsbsdtconn,
                insertSyncSql,
                new Object[] { returninfo.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                        DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), returninfo.get("FROMAREACODE"),
                        returninfo.get("FROMAREANAME"), returninfo.get("TOAREACODE"),
                        returninfo.get("TOAREANAME") }, false);
        
    }

    /**
     * 描述 解析省网办下发的附件数据
     * 
     * @author Derek Zhang
     * @created 2016年1月22日 上午10:53:44
     * @param log
     * @param conn
     * @param ptzhspConn
     */
    private void parsProvinceAttrs(Log log, Connection conn, Connection ptzhspConn) {
        String sql = "select * from (select * from provinceattr p"
                + " where not exists (select 1 from provinceattrsync u where u.unid= p.unid)"
                + " order by p.createtime asc) where rownum<100 ";
        List<Map<String, Object>> dataList = DbUtil.findBySqlClob(conn, sql, new Object[] {});
        if (dataList != null && !dataList.isEmpty() && dataList.size() > 0) {
            log.info("调用【解析省网办下发的办件数据】定时任务，开始解析处理附件数据....");
            for (Map<String, Object> data : dataList) {
                byte[] bys = null;
                Blob blob = (Blob) data.get("CONTENT");
                String tchar1 = data.get("TCHAR1") == null?
                        "":data.get("TCHAR1").toString();
                String tchar2 = data.get("TCHAR2") == null?
                        "":data.get("TCHAR2").toString();
                String tchar3 = data.get("TCHAR3") == null?
                        "":data.get("TCHAR3").toString();
                String tchar4 = data.get("TCHAR4") == null?
                        "":data.get("TCHAR4").toString();
                try {
                    if (blob != null) {
                        bys = blob.getBytes(1, (int) blob.length());
                    }
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
                try{
                    parseProvinceFj(data, bys,tchar1,tchar2,tchar3,tchar4,
                            conn, ptzhspConn);
                } catch (Exception e) {
                    log.error("解析处理附件数据出错" + data.get("UNID"));
                    log.error(e.getMessage(), e);
                }
            }
            log.info("调用【解析省网办下发的办件数据】定时任务，解析处理附件数据结束....");
        }
    }

    /**
     * 描述 解析处理附件数据
     * 
     * @author Derek Zhang
     * @param tchar4 
     * @param tchar3 
     * @param tchar2 
     * @param tchar1 
     * @created 2016年1月22日 上午11:00:53
     * @param data
     * @param conn
     * @param ptzhspConn
     */
    private void parseProvinceFj(Map<String, Object> attr, byte[] bys, 
            String tchar1, String tchar2, String tchar3, String tchar4, Connection wsconn, Connection ptconn) {
        log.info("解析处理附件数据" + attr.get("UNID"));
        if(bys!=null&&bys.length>0){
            String insertSql = "insert into t_bsfw_swbprovdata_attr (id, content, create_time, "
                    +"status,tchar1,tchar2,tchar3,tchar4) values "
                    + "  ( ? , ? , ? , ?, ?, ?, ?,?) ";
            DbUtil.update(ptconn, insertSql, new Object[] { attr.get("UNID"), bys
                , DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), 0, tchar1,tchar2,tchar3,tchar4},
                    false);
            String insertSyncSql = "insert into provinceattrsync (unid, type, updatetime, content,"
                    + " createtime,fromareacode, fromareaname, toareacode, toareaname,etype) values "
                    + "(?, 1 , to_date(?,'yyyy-mm-dd hh24:mi:ss') , '', to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,1) ";
            DbUtil.update(wsconn, insertSyncSql,
                    new Object[] { attr.get("UNID"), DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
                            DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"), attr.get("FROMAREACODE"),
                            attr.get("FROMAREANAME"), attr.get("TOAREACODE"), attr.get("TOAREANAME") }, false);
        }else{
            log.error("解析处理附件数据为空:" + attr.get("UNID"));
        }
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
        if (ptzhspDbUrl == null || StringUtils.isEmpty(this.ptzhspDbUrl))
            this.ptzhspDbUrl = this.dictionaryService.getDicNames("NWReadWWDB", "dbUrl");
        return ptzhspDbUrl;
    }

    /**
     * 
     * 描述 属性ptzhspDbUrl set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setPtzhspDbUrl(String ptzhspDbUrl) {
        this.ptzhspDbUrl = ptzhspDbUrl;
    }

    /**
     * 
     * 描述 属性ptzhspDbUsername get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbUsername() {
        if (ptzhspDbUsername == null || StringUtils.isEmpty(this.ptzhspDbUsername))
            this.ptzhspDbUsername = this.dictionaryService.getDicNames("NWReadWWDB", "dbUser");
        return this.ptzhspDbUsername;
    }

    /**
     * 
     * 描述 属性ptzhspDbUsername set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setPtzhspDbUsername(String ptzhspDbUsername) {
        this.ptzhspDbUsername = ptzhspDbUsername;
    }

    /**
     * 
     * 描述 属性ptzhspDbPassword get方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public String getPtzhspDbPassword() {
        if (ptzhspDbPassword == null || StringUtils.isEmpty(this.ptzhspDbPassword))
            this.ptzhspDbPassword = this.dictionaryService.getDicNames("NWReadWWDB", "dbPassword");
        return ptzhspDbPassword;
    }

    /**
     * 
     * 描述 属性ptzhspDbPassword set方法
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午1:58:01
     * @return
     */
    public void setPtzhspDbPassword(String ptzhspDbPassword) {
        this.ptzhspDbPassword = ptzhspDbPassword;
    }

    /**
     * 解析content内容获取基本字段信息
     * @param content
     * @param type
     * @return
     */
    public Map<String,Object> getDataByContent(String content,String type){
        Map<String,Object> map = new HashMap<>();
        if(content!=null&&StringUtils.isNotEmpty(content)&&type!=null&&type.equals("100")){
            Document document = XmlUtil.stringToDocument(content);
            if(document!=null){
                Element root = document.getRootElement();
                if(root!=null){
                    Node node = root.selectSingleNode("//Case/Body/ServiceInfo");
                    if(node!=null){
                        String serviceInfo = node.asXML();
                        String serviceInfoJson = XmlUtil.xml2Json("<?xml version='1.0' encoding='UTF-8'?>"
                            + serviceInfo, "UTF-8");
                        JSONObject serviceInfOobj = JSONObject.parseObject(serviceInfoJson);
                        map.put("DEPT_UNID", getObjStringInfo(serviceInfOobj,"DeptUnid"));
                        map.put("DEPT_NAME", getObjStringInfo(serviceInfOobj,"DeptName"));
                        map.put("SERVICE_UNID", getObjStringInfo(serviceInfOobj,"Unid"));
                        map.put("SERVICE_NAME", getObjStringInfo(serviceInfOobj,"ServiceName"));
                        map.put("PARENT_UNID", getObjStringInfo(serviceInfOobj,"ParentUnid"));
                        map.put("PARENT_NAME", getObjStringInfo(serviceInfOobj,"ParentName"));
                    }
                }
            }
        }

        return map;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/31 17:08:00
     * @param
     * @return
     */
    private String getObjStringInfo(JSONObject serviceInfOobj, String cs) {
        String returnInfoString = (JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)==null?
                "":(String) ((JSONArray) ((JSONObject) serviceInfOobj.get("ServiceInfo")).get(cs)).get(0);
        return returnInfoString;
    }
}
