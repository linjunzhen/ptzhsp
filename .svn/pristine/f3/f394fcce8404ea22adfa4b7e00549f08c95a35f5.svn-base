/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.MapUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.ThreadPoolUtils;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.bsfw.service.DataAbutLogService;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.dao.SysRoleDao;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.model.TzProjectRespones;
import net.evecom.platform.wsbs.model.TzProjectRespones.TzProjectData;
import net.evecom.platform.wsbs.service.SwbInterfaceService;
import net.evecom.platform.wsbs.service.ZxInterfaceService;
    
/**
 * 
 * 描述 省网办接口服务业务接口
 * 
 * @author Derek Zhang
 * @version v1.0
 * @created 2015年10月13日 上午9:12:54
 */
@SuppressWarnings("rawtypes")
@Service("swbInterfaceService")
public class SwbInterfaceServiceImpl extends BaseServiceImpl implements SwbInterfaceService {
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
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 引入Service
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/16 16:12:00
     * @param
     * @return
     */
    @Resource
    private ZxInterfaceService zxInterfaceService;

    /**
     * 获取dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * log
     */
    private static Log log = LogFactory.getLog(SwbInterfaceService.class);
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
     * 
     * 描述 保存日志数据和前置机上的数据
     * 
     * @author Derek Zhang
     * @created 2015年10月22日 上午11:49:38
     * @param dataAbutment
     * @param busDataList
     * @param abutDesc
     * @param isSend
     * @param saveMethodName
     */
    @SuppressWarnings("unchecked")
    private void sendDataSave(Map<String, Object> dataAbutment, List<Map<String, Object>> busDataList,
            String abutDesc) {
        String dbUrl = (String) dataAbutment.get("DB_URL");
        String dbUserName = (String) dataAbutment.get("DB_USERNAME");
        String dbPass = (String) dataAbutment.get("DB_PASSWORD");
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        Connection conn = null;
        try {
            
            conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
            for (Map<String, Object> xmlMap : busDataList) {
                // 构造业务 xml
                StringBuffer buffer = new StringBuffer();
                buffer.append(this.makeDataXml(xmlMap, configXml,true));
                // 获取业务标识值
                String busIdValue = (String) xmlMap.get("UNID");
                // 获取服务事项XML
                Map<String, Object> dataAbutLog = new HashMap<String, Object>();
                dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
                dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
                dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                dataAbutLog.put("ABUT_DESC", "【" + abutDesc + "】操作成功！");
                dataAbutLog.put("BUS_IDVALUE", busIdValue);
                dataAbutLog.put("ABUT_SENDDATA", buffer.toString());
                try {
                    if (buffer == null || buffer.length() == 0 || buffer.toString().equals("null")) {
                        dataAbutLog.put("ABUT_RESULT", "-1");
                    } else {
                        this.saveOrUpdateDataToProvince(conn, xmlMap, buffer.toString());
                        dataAbutLog.put("ABUT_RESULT", "1");
                    }
                    dataAbutLogService.saveOrUpdateLog(dataAbutLog);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    dataAbutLog.put("ERROR_LOG", e.getMessage());
                    dataAbutLog.put("ABUT_DESC", "【" + abutDesc + "】操作失败！");
                    dataAbutLog.put("ABUT_RESULT", "-1");
                    dataAbutLogService.saveOrUpdateLog(dataAbutLog);
                }
            }
 
        } catch (Exception e) {
            log.error(e.getMessage());
            Map<String, Object> dataAbutLog = new HashMap<String, Object>();
            dataAbutLog.put("ABUT_CODE", dataAbutment.get("AABUT_CODE"));
            dataAbutLog.put("ABUT_NAME", dataAbutment.get("AABUT_NAME"));
            dataAbutLog.put("ABUT_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dataAbutLog.put("ABUT_DESC", "【" + abutDesc + "】操作失败！");
            dataAbutLog.put("ABUT_RESULT", "-1");
            dataAbutLog.put("ERROR_LOG", e.getMessage());
            dataAbutLogService.saveOrUpdate(dataAbutLog, "T_BSFW_DATAABUTLOG", null);
        } finally {
            if (conn != null) {
                try {
                    DbUtils.close(conn);
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
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
    public StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
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
   public StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml,Boolean isLog) {
       StringBuffer sbuffer = new StringBuffer();
       sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap,isLog));
       if ((sbuffer.toString()).equals("null")) {
           return null;
       }
       return sbuffer;
   }

    /**
     * 描述 向省网办报送信息保存到省网办的数据库
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 上午11:10:07
     * @param params
     */
    public Integer saveOrUpdateDataToProvince(Connection conn, Map<String, Object> dataMap, String xmlStr) {
        
        StringBuffer str=new StringBuffer("select * from cityinfo where unid=?");
        List result=DbUtil.findBySql(conn, str.toString(),new Object[]{dataMap.get("UNID")},false);
        if(result.size()>0){
            log.info("查询前置库数据已存在，执行更新操作："+dataMap.get("UNID"));
             StringBuffer sql = new StringBuffer("update cityinfo set ");
                sql.append("type=?, createtime=to_date(?,'yyyy-mm-dd hh24:mi:ss'), xsdtype=?, copyright=?, action=?, fromareacode=?, ");
                sql.append(" fromareaname=?, toareacode=?, toareaname=?, applyfrom=?, content=? where unid=? ");
              return  DbUtil.updateNew(conn, sql.toString(), new Object[] {  dataMap.get("type"),
                        DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"), dataMap.get("xsdType"),
                        dataMap.get("copyRight"), dataMap.get("action"), 
                        ((Map) dataMap.get("case")).get("fromAreaCode"),
                        ((Map) dataMap.get("case")).get("fromAreaName"), ((Map) dataMap.get("case")).get("toAreaCode"),
                        ((Map) dataMap.get("case")).get("toAreaName"), dataMap.get("applyFrom"), xmlStr.getBytes(),dataMap.get("UNID") },false);
        }else{
            log.info("执行插入操作");
        StringBuffer sql = new StringBuffer("insert into cityinfo ");
        sql.append("(unid, type, createtime, xsdtype, copyright, action, fromareacode, ");
        sql.append(" fromareaname, toareacode, toareaname, applyfrom, content)  values ");
        sql.append("( ?, ?, to_date(?,'yyyy-mm-dd hh24:mi:ss'), ?, ?, ?, ?, ?, ?, ?, ?, ? )");
          return DbUtil.updateNew(conn, sql.toString(), new Object[] { dataMap.get("UNID"), dataMap.get("type"),
                    DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"), dataMap.get("xsdType"),
                    dataMap.get("copyRight"), dataMap.get("action"), ((Map) dataMap.get("case")).get("fromAreaCode"),
                    ((Map) dataMap.get("case")).get("fromAreaName"), ((Map) dataMap.get("case")).get("toAreaCode"),
                    ((Map) dataMap.get("case")).get("toAreaName"), dataMap.get("applyFrom"), xmlStr.getBytes() },false);
        
       
        }
      
    }

    /**
     * 描述 生成数据库UUID
     * 
     * @author Derek Zhang
     * @created 2015年12月9日 上午8:59:12
     * @return
     */
    public String getUNID() {
        Map m = this.dao.getByJdbc("select RAWTOHEX(sys_guid()) as unid from dual ", new Object[] {});
        return (String) m.get("unid");
    }

    /**
     * 
     * 描述 封装XML报文头部信息
     * 
     * @author Derek Zhang
     * @created 2015年12月7日 上午10:36:10
     * @param instanceMap
     * @param type
     * @param action
     * @param applyFrom
     * @return
     */
    private Map<String, Object> mackCaseMap(Map<String, Object> instanceMap, String type, String action,
            String applyFrom, String unid) {
        // 设置业务唯一标识
        instanceMap.put("UNID", unid);
        instanceMap.put("type", type);
        instanceMap.put("xsdType", AllConstant.SWB_DATA_XSD);
        instanceMap.put("copyRight", AllConstant.SWB_DATA_XSD);
        instanceMap.put("action", action);
        instanceMap.put("applyFrom", applyFrom);
        Map<String, Object> caseMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        // 设置数据总体分为case和body两个包
        instanceMap.put("case", caseMap);
        instanceMap.put("body", bodyMap);
        // 设置Case包
        caseMap.put("applyFrom", applyFrom);
        caseMap.put("unid", unid);
        caseMap.put("fromAreaCode", AllConstant.FROM_AREA_CODE);
        caseMap.put("fromAreaName", AllConstant.FROM_AREA_NANE);
        caseMap.put("toAreaCode", AllConstant.TO_AREA_CODE);
        caseMap.put("toAreaName", AllConstant.TO_AREA_NAME);
        caseMap.put("time", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return bodyMap;
    }

    /**
     * 
     * 描述 市县区向省网办报送办件
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    @SuppressWarnings("unchecked")
    public void sendBusItemsToProvince(Map<String, Object> swbdataRes,Map<String,Object> dataAbutment) {
        /*Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_XMXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createBusItemsToProvince(conn, swbdataRes, instanceMap, dataAbutment, "sw");
           result = sendDataSave(dataAbutment, instanceMap, "向省网办发送办件数据");
          
           Map<String,Object> returnState=new HashMap<String,Object>();
           returnState.put("oper_status",result > 0 ? 1 : 2);
           //获取当前系统时间
           String oper_time=DateTimeUtil.getNowTime(null);
           returnState.put("oper_time",oper_time);
           log.info("returnState:"+returnState);
           log.info("unid:"+unid);
           conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
           String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
          log.info("10保存本地库状态："+saveflag);     
        } catch (Exception e) {
             log.info("省网数据推送10异常错误基础数据为：" + swbdataRes);
        }  finally {
            DbUtils.closeQuietly(conn);
        }
       
    }
    /**
     * 
     * 描述 市县区向省网办报送办件
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    @SuppressWarnings("unchecked")
    public void sendGcjsBusItemsToProvince(Map<String, Object> swbdataRes,Map<String,Object> dataAbutment) {
        /*Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_XMXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createBusItemsToProvince(conn, swbdataRes, instanceMap, dataAbutment, "GCJS");
           result = sendDataSave(dataAbutment, instanceMap, "向省网办发送办件数据");
          
           Map<String,Object> returnState=new HashMap<String,Object>();
           returnState.put("oper_status",result > 0 ? 1 : 2);
           //获取当前系统时间
           String oper_time=DateTimeUtil.getNowTime(null);
           returnState.put("oper_time",oper_time);
           log.info("returnState:"+returnState);
           log.info("unid:"+unid);
           conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
           String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
          log.info("10保存本地库状态："+saveflag);     
        } catch (Exception e) {
             log.info("省网数据推送10异常错误基础数据为：" + swbdataRes);
        }  finally {
            DbUtils.closeQuietly(conn);
        }
       
    }
    
    /**
     * 
     * 描述 市县区向省网办报送办件
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    @SuppressWarnings("unchecked")
    public void sendGcjsProBusItemsToProvince(Map<String, Object> swbdataRes,Map<String,Object> dataAbutment) {
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createBusItemsToProvince(conn, swbdataRes, instanceMap, dataAbutment, "GCJS");
           result = sendDataSave(dataAbutment, instanceMap, "向省网办发送办件数据");
          
           Map<String,Object> returnState=new HashMap<String,Object>();
           returnState.put("oper_status",result > 0 ? 1 : 2);
           //获取当前系统时间
           String oper_time=DateTimeUtil.getNowTime(null);
           returnState.put("oper_time",oper_time);
           log.info("returnState:"+returnState);
           log.info("unid:"+unid);
           conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
           String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
          log.info("37保存本地库状态："+saveflag);     
        } catch (Exception e) {
             log.info("省网数据推送37异常错误基础数据为：" + swbdataRes);
        }  finally {
            DbUtils.closeQuietly(conn);
        }
       
    }    
    

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 10:46:00
     * @param
     * @return
     */
    public void createBusItemsToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment, String type) throws Exception {
        String unid = (String) swbdataRes.get("RES_ID");
        String exeid = (String) swbdataRes.get("EXE_ID");
        conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
        String tableNameSql="";
        if(type.equals("GCJS")){
            //包含归档数据
            tableNameSql= setTableNameSqlNew();
        }else{
            tableNameSql= setTableNameSql();
        }
        //Map<String, Object> m = DbUtil.getMapBySql(conn, setTableNameSql(), new Object[] { exeid }, false);
        Map<String, Object> m = DbUtil.getMapBySql(conn, tableNameSql, new Object[] { exeid }, false);
        if (m == null || m.isEmpty()) {
            if(type.equals("GCJS")){
                // 删除相应记录(工程建设包含归档数据)
                String JBPM6_EXECUTION=unionJbpmExecution();
                this.dao.deleteAttrNoInJbpm6ExecutionNew(conn,JBPM6_EXECUTION);
                this.dao.deleteInfoNoInJbpm6ExecutionNew(conn,JBPM6_EXECUTION);
                }else{
                    // 删除相应记录
                    this.dao.deleteAttrNoInJbpm6Execution(conn);
                    this.dao.deleteInfoNoInJbpm6Execution(conn);
            }
            
            return;
        }
        String tableName = (String) m.get("BUS_TABLENAME");
        String tableKey = (String) m.get("COLUMN_NAME");
       
        //String serviceSql = setSQL(tableName, tableKey);
        String serviceSql="";
        if(type.equals("GCJS")){
            //包含归档数据
             serviceSql = setSQLNew(tableName, tableKey);
        }else{
             serviceSql = setSQL(tableName, tableKey);
        }
        // 获取事项及办件相关数据
        Map<String, Object> serviceItemMap = new HashMap<String, Object>();
        serviceItemMap = DbUtil.getMapBySql(conn, serviceSql, new Object[] { exeid }, false);
       if(serviceItemMap!=null){
        serviceItemMap = encryptionService.mapDecrypt(serviceItemMap, "JBPM6_EXECUTION");
       }else{
           serviceItemMap = new HashMap<String, Object>();
       }
        /* 判断是否是工程建设项目 */
        boolean isProject = judgeProject(tableName);
        serviceItemMap.put("isProject", isProject);
        // 限制同步部门
        String departCode = serviceItemMap.get("DEPART_CODE") == null ? ""
                : serviceItemMap.get("DEPART_CODE").toString();
        String dicName = "LIMITDEPARTCODE";
        String limitDepartCodeSql = "select t.DIC_CODE from T_MSJW_SYSTEM_DICTIONARY t where t.DIC_NAME = ?";
        Map<String, Object> limitDepartCodeMap;
        limitDepartCodeMap = DbUtil.getMapBySql(conn, limitDepartCodeSql, new Object[] { dicName }, false);
        String limitDepartCodes = limitDepartCodeMap.get("DIC_CODE") == null ? ""
                : limitDepartCodeMap.get("DIC_CODE").toString();
        String[] limitDepartCode = limitDepartCodes.split(";");
        for (int i = 0; i < limitDepartCode.length; i++) {
            String codeString = limitDepartCode[i];
            if (codeString.equals(departCode)) {
                String sql = "update t_bsfw_swbdata_res r set r.oper_status = " + 331
                        + " ,r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') " + " where r.exe_id = ?";
                DbUtil.update(conn, sql, new Object[] { exeid }, false);
                return;
            }
        }
        String busRecordId = (String) serviceItemMap.get("BUS_RECORDID");
        /* 设置对接办件来源 */
        String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
        // 窗口收件、
        if ("1".equals((String) serviceItemMap.get("SQFS"))) {
            applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
        } else if ("2".equals((String) serviceItemMap.get("SQFS"))) {
            applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
        } else if ("4".equals((String) serviceItemMap.get("SQFS"))) {
            applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
            dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.INTER_CODE_SWB_SSLDBJ });
        }
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_XM,
                AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> proposer = new HashMap<String, Object>();
        Map<String, Object> operator = new HashMap<String, Object>();
        List<Map<String, Object>> expresses = new ArrayList<Map<String, Object>>();
        Map<String, Object> formInfos = new HashMap<String, Object>();
        List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
        Map<String, Object> sms = new HashMap<String, Object>();
       // Map<String, Object> extend = new HashMap<String, Object>();
        bodyMap.put("apasInfo", apasInfo);
        bodyMap.put("proposer", proposer);
        bodyMap.put("operator", operator);
        bodyMap.put("expresses", expresses);
        bodyMap.put("formInfos", formInfos);
        bodyMap.put("attrs", attrs);
        bodyMap.put("documents", documents);
        bodyMap.put("sms", sms);
       // bodyMap.put("Extend",extend);
        /* 工程建设项目表单 */
        if (isProject) {
            if (serviceItemMap.get("PROJECTCODE") != null) {
                serviceItemMap.put("PROJECT_CODE", (String) serviceItemMap.get("PROJECTCODE"));
            }
            
            //setFormInfoData(formInfos, tableName, tableKey, busRecordId);
            setFormInfoDataNew(formInfos, tableName, tableKey, busRecordId,conn);
        } else {
            serviceItemMap.put("PROJECT_CODE", "");
            Map<String, Object> itemInfo = new HashMap<String, Object>();
            formInfos.put("item", itemInfo);
            itemInfo.put("PROJECT_NAME", serviceItemMap.get("project_name"));
        }
      if (Objects.equals(type, "zx")) {
           setZxAttrInfo(swbdataRes, conn, attrs);
        }
      //处理附件信息包含附件内容
      /*if (!unid.contains("r191224")) {
          setAttrInfo(swbdataRes, dataAbutment, unid, conn, attrs);
       }*/
      //处理附件信息只传附件xml节点信息（不包含内容）
      if (!unid.contains("r191224")) {
          setAttrInfoNew(swbdataRes, unid, conn, attrs);
       }
      
      setBodyData(exeid, apasInfo, proposer, operator, sms, serviceItemMap);
    }

    
    /**
     *
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/16 15:19:00
     * @param
     * @return
     */
    private void setZxAttrInfo(Map<String, Object> swbdataRes, Connection conn, List<Map<String, Object>> attrs) {
        List<Map<String, Object>> attrList = DbUtil.findBySqlClob(conn, getAttrSql(),
                new Object[] { StringUtil.getValue(swbdataRes, "RES_ID") });
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
        String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
        String zxFileUploadUrl = dictionaryService.getDicCode("zxInterfaceConfig", "zxFileUploadUrl");
        String zxAccessToken = zxInterfaceService.getZxAccessToken();
        if (attrList != null && attrList.size() > 0) {
            for (Map<String, Object> attr : attrList) {
                Map<String, Object> attrMap = new HashMap<>();
                String filePath = StringUtil.getValue(attr, "FILE_PATH");
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(filePath)) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("url", zxFileUploadUrl);
                    param.put("method", "sendByteFilePost");
                    param.put("fileUrl", attachFileFolderPath + zxFileUploadUrl);
                    JSONObject headJson = new JSONObject();
                    headJson.put("Authorization", zxAccessToken);
                    param.put("headJson", headJson.toJSONString());
                    String result = HttpSendUtil.sendPostParams(forwardUrl, param);
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(result)) {
                        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                        if (Objects.equals(resultMap.get("code"), "1")) {
                            Map<String, Object> dataMap = (Map) resultMap.get("data");
                            attrMap.put("UNID", dataMap.get("fileId"));
                            String mode = StringUtil.getValue(attr, "Mode");
                            attrMap.put("Mode", StringUtils.isEmpty(mode) ? "paper" : mode);
                            attrMap.put("Code", StringUtil.getValue(attr, "CODE"));
                            attrMap.put("Name", StringUtil.getValue(attr, "NAME"));
                            attrMap.put("SeqNo", StringUtil.getValue(attr, "SEQ_NO"));
                            attrMap.put("FileName", StringUtil.getValue(attr, "FILE_NAME"));
                            String creaditFileid = StringUtil.getString(attr.get("CREADIT_FILEID"));
                             if (!StringUtils.isEmpty(creaditFileid)) {
                                attrMap.put("licenseIdentifier", creaditFileid);
                            }
                            attrs.add(attrMap);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        Properties properties = FileUtil.readProperties("project.properties");
//        String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
//        byte[] bytes = FileUtil.convertUrlFileToBytes(
//                attachFileFolderPath + "uploadfile/attach/20210305/wu_1f00072uqv18102ntfg4ondfr4.docx");
//        Map<String, Object> params = new HashMap<>();
//        params.put("name", "测试.doc");
//        String s = HttpRequestUtil.sendByteFilePost("http://220.160.52.148:8063/bcsp/backend/open/api/file/store",
//                bytes, "UTF-8", "", "", params);
//        log.info(s);
       
     /* Map userInfoMap =new HashMap<>();
         ((EncryptionService) AppUtil.getBean("encryptionService")).mapDecrypt(userInfoMap, "T_BSFW_USERINFO");*/

        Sm4Utils sm4 = new Sm4Utils();
        log.info(sm4.encryptDataCBC("350128197409070017"));
        log.info(sm4.decryptDataCBC("Kxh8GvruxHrt/fx6rTfNd2EYofrY1GeP13bUKZ2O0BU="));
        log.info(sm4.encryptDataCBC("18965079588"));
        log.info(sm4.decryptDataCBC("GMnwyZXFy77gErHqMB/VDw=="));
    }

    /**
     * 描述:判断是否是工程建设项目
     *
     * @author Madison You
     * @created 2020/2/27 17:07:00
     * @param
     * @return
     */
    public boolean judgeProject(String tableName) {
        String allForm = dictionaryService.getDicCode("GCJSBD", "allForm");
        if (allForm.contains(tableName)) {
            return true;
        } else {
            return false;
        }
    }
  
    public String setSQL1(String tableName, String tableKey) {
        String serviceSql = "";
        String newForm = dictionaryService.getDicCode("GCJSBD", "newForm");
        String normalForm = dictionaryService.getDicCode("GCJSBD", "normalForm");
        if (!tableName.contains("T_BDCQLC_")) { // 不动产全流程表单省网同步
            if (normalForm.contains(tableName)) { // 通用表单省网同步
                serviceSql = "select e.sqrmc,decode(e.sqrxb,'2','女','1','男') as sqrxb, e.ssbmbm,"
                        + " e.sqrzjlx,e.sqrlxdz ,e.sqrsfz, e.sqrsjh, e.sqfs,"
                        + " e.jbr_name, decode(e.jbr_sex,'2','女','1','男') as jbr_sex,e.jbr_mobile,"
                        + " e.jbr_zjlx,e.jbr_zjhm,e.jbr_lxdh,e.jbr_email, e.jbr_address,e.bsyhlx,"
                        + " e.jbr_postcode,e.bjcxmm,e.create_time,e.creator_id,s.item_id,e.SQJG_NAME,"
                        + " e.SQJG_CODE,e.SQJG_TYPE,e.SQJG_LEALPERSON,e.SQJG_ADDR,e.SQJG_TEL,e.CREATOR_NAME,"
                        + " e.SQJG_LEALPERSON_ZJLX,e.SQJG_LEALPERSON_ZJHM,"
                        + " s.item_name,s.item_code,s.swb_item_id,s.swb_item_code,s.ssqh,s.cnqxgzr,e.bus_recordid,"
                        + " d.depart_name,d.advi_code,d.org_code,d.parent_id,d.depart_code,' ' as open_way,d.usc,"
//                      + "b.tzr,b.tzxmbh,b.projectcode,b.project_name"
                        // exeid 作为 projectcode
                        + " ' ' as projectcode,e.exe_id,b.SBMC as project_name"
                        + " from t_wsbs_serviceitem s, jbpm6_execution e, " + tableName
                        + " b,t_msjw_system_department d"
                        + " where s.item_code = e.item_code  and s.ssbmbm = d.depart_code" + " and e.exe_id = ? and b."
                        + tableKey + " = e.bus_recordid ";
            } else if (newForm.contains(tableName)) { // 工程建设表单省网同步
                serviceSql = "select e.sqrmc,decode(e.sqrxb,'2','女','1','男') as sqrxb, e.ssbmbm,"
                        + "e.sqrzjlx,e.sqrlxdz ,e.sqrsfz, e.sqrsjh, e.sqfs,"
                        + "e.jbr_name, decode(e.jbr_sex,'2','女','1','男') as jbr_sex,e.jbr_mobile,"
                        + "e.jbr_zjlx,e.jbr_zjhm,e.jbr_lxdh,e.jbr_email, e.jbr_address,e.bsyhlx,"
                        + "e.jbr_postcode,e.bjcxmm,e.create_time,e.creator_id,s.item_id,e.SQJG_NAME,"
                        + "e.SQJG_CODE,e.SQJG_TYPE,e.SQJG_LEALPERSON,e.SQJG_ADDR,e.SQJG_TEL,e.CREATOR_NAME,"
                        + "e.SQJG_LEALPERSON_ZJLX,e.SQJG_LEALPERSON_ZJHM,"
                        + "s.item_name,s.item_code,s.swb_item_id,s.swb_item_code,s.ssqh,s.cnqxgzr,e.bus_recordid,"
                        + "d.depart_name,d.advi_code,d.org_code,d.depart_code,d.usc,"
                        + "d.parent_id,b.project_code,b.project_name,b.open_way,"
                        + "a.project_attributes,a.industry_structure,"
                        + "a.lerep_info,a.contribution_info,a.foreign_abroad_flag,a.total_money_explain,"
                        + "a.is_country_security,a.security_approval_number,a.investment_mode,a.total_money_dollar,"
                        + "a.total_money_dollar_rate,a.project_capital_money,a.project_capital_money_dollar,"
                        + "a.project_capital_money_rate,a.industrial_policy_type,a.industrial_policy,"
                        + "a.other_investment_apply_info, a.transaction_both_info,"
                        + "a.merger_plan,a.merger_management_mode_scope,a.get_land_mode,a.land_area,a.built_area,"
                        + "a.is_add_device,a.import_device_number_money,a.project_site,a.china_total_money"
                        + " from jbpm6_execution e " + " left join T_WSBS_SERVICEITEM s on e.ITEM_CODE = s.ITEM_CODE "
                        + " left join T_MSJW_SYSTEM_DEPARTMENT d on s.SSBMBM = d.DEPART_CODE " + " left join "
                        + tableName + " b on e.BUS_RECORDID = b." + tableKey
                        + " left join SPGL_XMJBXXB a on a.PROJECT_CODE = b.PROJECT_CODE "
                        + " left join SPGL_XMDWXXB c on c.JBXX_ID = a.id " + " where exe_id = ?";
            } else {
                serviceSql = "select e.sqrmc,decode(e.sqrxb,'2','女','1','男') as sqrxb, e.ssbmbm,"
                        + " e.sqrzjlx,e.sqrlxdz ,e.sqrsfz, e.sqrsjh, e.sqfs,"
                        + " e.jbr_name, decode(e.jbr_sex,'2','女','1','男') as jbr_sex,e.jbr_mobile,"
                        + " e.jbr_zjlx,e.jbr_zjhm,e.jbr_lxdh,e.jbr_email, e.jbr_address,e.bsyhlx,"
                        + " e.jbr_postcode,e.bjcxmm,e.create_time,e.creator_id,s.item_id,e.SQJG_NAME,"
                        + " e.SQJG_CODE,e.SQJG_TYPE,e.SQJG_LEALPERSON,e.SQJG_ADDR,e.SQJG_TEL,e.CREATOR_NAME,"
                        + " e.SQJG_LEALPERSON_ZJLX,e.SQJG_LEALPERSON_ZJHM,"
                        + " s.item_name,s.item_code,s.swb_item_id,s.swb_item_code,s.ssqh,s.cnqxgzr,e.bus_recordid,"
                        + " b.tzr,b.tzxmbh,d.depart_name,d.advi_code,d.org_code,d.depart_code,d.usc,"
                        + " d.parent_id,b.projectcode project_code,b.project_name,"
                        // 新增字段
                        + " b.project_attributes,b.industry_structure,b.open_way,"
                        + " b.lerep_info,b.contribution_info,b.foreign_abroad_flag,"
                        + " b.total_money_explain,b.the_industry,"
                        + " b.is_country_security,b.security_approval_number,b.investment_mode,b.total_money_dollar,"
                        + " b.total_money_dollar_rate,b.project_capital_money,b.project_capital_money_dollar,"
                        + "b.project_capital_money_rate,"
                        + " b.industrial_policy_type,b.industrial_policy,b.other_investment_apply_info,"
                        + "b.transaction_both_info,"
                        + " b.merger_plan,b.merger_management_mode_scope,b.get_land_mode,b.land_area,b.built_area,"
                        + " b.is_add_device,b.import_device_number_money,b.project_site,b.china_total_money,"
                        + "b.investment_mode_jw" + " from t_wsbs_serviceitem s, jbpm6_execution e, " + tableName
                        + " b,t_msjw_system_department d"
                        + " where s.item_code = e.item_code  and s.ssbmbm = d.depart_code" + " and e.exe_id = ? and b."
                        + tableKey + " = e.bus_recordid ";
            }
        } else {
            serviceSql = "select e.sqrmc,decode(e.sqrxb,'2','女','1','男') as sqrxb, e.ssbmbm,"
                    + " e.sqrzjlx,e.sqrlxdz ,e.sqrsfz, e.sqrsjh, e.sqfs,"
                    + " e.jbr_name, decode(e.jbr_sex,'2','女','1','男') as jbr_sex,e.jbr_mobile,"
                    + " e.jbr_zjlx,e.jbr_zjhm,e.jbr_lxdh,e.jbr_email, e.jbr_address,e.bsyhlx,"
                    + " e.jbr_postcode,e.bjcxmm,e.create_time,e.creator_id,s.item_id,e.SQJG_NAME,"
                    + " e.SQJG_CODE,e.SQJG_TYPE,e.SQJG_LEALPERSON,e.SQJG_ADDR,e.SQJG_TEL,e.CREATOR_NAME,"
                    + " e.SQJG_LEALPERSON_ZJLX,e.SQJG_LEALPERSON_ZJHM,"
                    + " s.item_name,s.item_code,s.swb_item_id,s.swb_item_code,s.ssqh,s.cnqxgzr,e.bus_recordid,"
                    + " d.depart_name,d.advi_code,d.org_code,d.parent_id,d.depart_code,' ' as open_way,d.usc,"
//                      + "b.tzr,b.tzxmbh,b.projectcode,b.project_name"
                    // exeid 作为 projectcode
                    + " ' ' as projectcode,e.exe_id,b.SBMC as project_name"
                    + " from t_wsbs_serviceitem s, jbpm6_execution e, " + tableName + " b,t_msjw_system_department d"
                    + " where s.item_code = e.item_code  and s.ssbmbm = d.depart_code" + " and e.exe_id = ? and b."
                    + tableKey + " = e.bus_recordid ";
        }
        return serviceSql;
    }
    /**
     * 
     * 描述
     * @author Yanisin Shi
     * @param tableName
     * @param tableKey
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#setSQL(java.lang.String, java.lang.String)
     */
    public String setSQL(String tableName, String tableKey) {
        String serviceSql = "";
        String newForm = dictionaryService.getDicCode("GCJSBD", "newForm");
        String normalForm = dictionaryService.getDicCode("GCJSBD", "normalForm");
        if (!tableName.contains("T_BDCQLC_")) { // 不动产全流程表单省网同步
            if (normalForm.contains(tableName)) { // 通用表单省网同步
                serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                        + " E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                        + " E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                        + " E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                        + " E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                        + " E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                        + " E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                        + " S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                        + " D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.PARENT_ID,D.DEPART_CODE,' ' AS OPEN_WAY,D.USC,"
//                      + "B.TZR,B.TZXMBH,B.PROJECTCODE,B.PROJECT_NAME"
                        // EXEID 作为 PROJECTCODE
                        + " ' ' AS PROJECTCODE,E.EXE_ID,B.SBMC AS PROJECT_NAME"
                        + " FROM T_WSBS_SERVICEITEM S,JBPM6_EXECUTION E, " + tableName
                        + " B,T_MSJW_SYSTEM_DEPARTMENT D"
                        + " WHERE S.ITEM_CODE = E.ITEM_CODE  AND S.SSBMBM = D.DEPART_CODE" + " AND E.EXE_ID = ? AND B."
                        + tableKey + " = E.BUS_RECORDID ";
            } else if (newForm.contains(tableName)) { // 工程建设表单省网同步
                serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                        + "E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                        + "E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                        + "E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                        + "E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                        + "E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                        + "E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                        + "S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                        + "D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.DEPART_CODE,D.USC,"
                        + "D.PARENT_ID,B.PROJECT_CODE,B.PROJECT_NAME,B.OPEN_WAY,"
                        + "A.PROJECT_ATTRIBUTES,A.INDUSTRY_STRUCTURE,"
                        + "A.LEREP_INFO,A.CONTRIBUTION_INFO,A.FOREIGN_ABROAD_FLAG,A.TOTAL_MONEY_EXPLAIN,"
                        + "A.IS_COUNTRY_SECURITY,A.SECURITY_APPROVAL_NUMBER,A.INVESTMENT_MODE,A.TOTAL_MONEY_DOLLAR,"
                        + "A.TOTAL_MONEY_DOLLAR_RATE,A.PROJECT_CAPITAL_MONEY,A.PROJECT_CAPITAL_MONEY_DOLLAR,"
                        + "A.PROJECT_CAPITAL_MONEY_RATE,A.INDUSTRIAL_POLICY_TYPE,A.INDUSTRIAL_POLICY,"
                        + "A.OTHER_INVESTMENT_APPLY_INFO, A.TRANSACTION_BOTH_INFO,"
                        + "A.MERGER_PLAN,A.MERGER_MANAGEMENT_MODE_SCOPE,A.GET_LAND_MODE,A.LAND_AREA,A.BUILT_AREA,"
                        + "A.IS_ADD_DEVICE,A.IMPORT_DEVICE_NUMBER_MONEY,A.PROJECT_SITE,A.CHINA_TOTAL_MONEY"
                        + " FROM JBPM6_EXECUTION E " + " LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE = S.ITEM_CODE "
                        + " LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON S.SSBMBM = D.DEPART_CODE " + " LEFT JOIN "
                        + tableName + " B ON E.BUS_RECORDID = B." + tableKey
                        + " LEFT JOIN SPGL_XMJBXXB A ON A.PROJECT_CODE = B.PROJECT_CODE "
                        + " LEFT JOIN SPGL_XMDWXXB C ON C.JBXX_ID = A.ID " + " WHERE EXE_ID = ?";
            } else {
                serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                        + " E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                        + " E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                        + " E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                        + " E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                        + " E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                        + " E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                        + " S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                        + " B.TZR,B.TZXMBH,D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.DEPART_CODE,D.USC,"
                        + " D.PARENT_ID,B.PROJECTCODE PROJECT_CODE,B.PROJECT_NAME,"
                        // 新增字段
                        + " B.PROJECT_ATTRIBUTES,B.INDUSTRY_STRUCTURE,B.OPEN_WAY,"
                        + " B.LEREP_INFO,B.CONTRIBUTION_INFO,B.FOREIGN_ABROAD_FLAG,"
                        + " B.TOTAL_MONEY_EXPLAIN,B.THE_INDUSTRY,"
                        + " B.IS_COUNTRY_SECURITY,B.SECURITY_APPROVAL_NUMBER,B.INVESTMENT_MODE,B.TOTAL_MONEY_DOLLAR,"
                        + " B.TOTAL_MONEY_DOLLAR_RATE,B.PROJECT_CAPITAL_MONEY,B.PROJECT_CAPITAL_MONEY_DOLLAR,"
                        + "B.PROJECT_CAPITAL_MONEY_RATE,"
                        + " B.INDUSTRIAL_POLICY_TYPE,B.INDUSTRIAL_POLICY,B.OTHER_INVESTMENT_APPLY_INFO,"
                        + "B.TRANSACTION_BOTH_INFO,"
                        + " B.MERGER_PLAN,B.MERGER_MANAGEMENT_MODE_SCOPE,B.GET_LAND_MODE,B.LAND_AREA,B.BUILT_AREA,"
                        + " B.IS_ADD_DEVICE,B.IMPORT_DEVICE_NUMBER_MONEY,B.PROJECT_SITE,B.CHINA_TOTAL_MONEY,"
                        + "B.INVESTMENT_MODE_JW" + " FROM T_WSBS_SERVICEITEM S, JBPM6_EXECUTION E, " + tableName
                        + " B,T_MSJW_SYSTEM_DEPARTMENT D"
                        + " WHERE S.ITEM_CODE = E.ITEM_CODE  AND S.SSBMBM = D.DEPART_CODE" + " AND E.EXE_ID = ? AND B."
                        + tableKey + " = E.BUS_RECORDID ";
            }
        } else {
            serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                    + " E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                    + " E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                    + " E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                    + " E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                    + " E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                    + " E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                    + " S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                    + " D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.PARENT_ID,D.DEPART_CODE,' ' AS OPEN_WAY,D.USC,"
//                      + "B.TZR,B.TZXMBH,B.PROJECTCODE,B.PROJECT_NAME"
                    // EXEID 作为 PROJECTCODE
                    + " ' ' AS PROJECTCODE,E.EXE_ID,B.SBMC AS PROJECT_NAME"
                    + " FROM T_WSBS_SERVICEITEM S, JBPM6_EXECUTION E, " + tableName + " B,T_MSJW_SYSTEM_DEPARTMENT D"
                    + " WHERE S.ITEM_CODE = E.ITEM_CODE  AND S.SSBMBM = D.DEPART_CODE" + " AND E.EXE_ID = ? AND B."
                    + tableKey + " = E.BUS_RECORDID ";
        }
        return serviceSql;
    }
    /**
     * 
     * 描述 新增归档信息处理
     * @author Yanisin Shi
     * @param tableName
     * @param tableKey
     * @return
     */
    public String setSQLNew(String tableName, String tableKey) {
        String serviceSql = "";
        String newForm = dictionaryService.getDicCode("GCJSBD", "newForm");
        String normalForm = dictionaryService.getDicCode("GCJSBD", "normalForm");
        String JBPM6_EXECUTION=unionJbpmExecution();
        if (!tableName.contains("T_BDCQLC_")) { // 不动产全流程表单省网同步
            if (normalForm.contains(tableName)) { // 通用表单省网同步
                serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                        + " E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                        + " E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                        + " E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                        + " E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                        + " E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                        + " E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                        + " S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                        + " D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.PARENT_ID,D.DEPART_CODE,' ' AS OPEN_WAY,D.USC,"
//                      + "B.TZR,B.TZXMBH,B.PROJECTCODE,B.PROJECT_NAME"
                        // EXEID 作为 PROJECTCODE
                        + " ' ' AS PROJECTCODE,E.EXE_ID,B.SBMC AS PROJECT_NAME"
                        + " FROM T_WSBS_SERVICEITEM S,"+ JBPM6_EXECUTION+" E, " + tableName
                        + " B,T_MSJW_SYSTEM_DEPARTMENT D"
                        + " WHERE S.ITEM_CODE = E.ITEM_CODE  AND S.SSBMBM = D.DEPART_CODE" + " AND E.EXE_ID = ? AND B."
                        + tableKey + " = E.BUS_RECORDID ";
            } else if (newForm.contains(tableName)) { // 工程建设表单省网同步
                serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                        + "E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                        + "E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                        + "E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                        + "E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                        + "E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                        + "E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                        + "S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                        + "D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.DEPART_CODE,D.USC,"
                        + "D.PARENT_ID,B.PROJECT_CODE,B.PROJECT_NAME,B.OPEN_WAY,"
                        + "A.PROJECT_ATTRIBUTES,A.INDUSTRY_STRUCTURE,"
                        + "A.LEREP_INFO,A.CONTRIBUTION_INFO,A.FOREIGN_ABROAD_FLAG,A.TOTAL_MONEY_EXPLAIN,"
                        + "A.IS_COUNTRY_SECURITY,A.SECURITY_APPROVAL_NUMBER,A.INVESTMENT_MODE,A.TOTAL_MONEY_DOLLAR,"
                        + "A.TOTAL_MONEY_DOLLAR_RATE,A.PROJECT_CAPITAL_MONEY,A.PROJECT_CAPITAL_MONEY_DOLLAR,"
                        + "A.PROJECT_CAPITAL_MONEY_RATE,A.INDUSTRIAL_POLICY_TYPE,A.INDUSTRIAL_POLICY,"
                        + "A.OTHER_INVESTMENT_APPLY_INFO, A.TRANSACTION_BOTH_INFO,"
                        + "A.MERGER_PLAN,A.MERGER_MANAGEMENT_MODE_SCOPE,A.GET_LAND_MODE,A.LAND_AREA,A.BUILT_AREA,"
                        + "A.IS_ADD_DEVICE,A.IMPORT_DEVICE_NUMBER_MONEY,A.PROJECT_SITE,A.CHINA_TOTAL_MONEY"
                        + " FROM "+ JBPM6_EXECUTION+" E " + " LEFT JOIN T_WSBS_SERVICEITEM S ON E.ITEM_CODE = S.ITEM_CODE "
                        + " LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON S.SSBMBM = D.DEPART_CODE " + " LEFT JOIN "
                        + tableName + " B ON E.BUS_RECORDID = B." + tableKey
                        + " LEFT JOIN SPGL_XMJBXXB A ON A.PROJECT_CODE = B.PROJECT_CODE "
                        + " LEFT JOIN SPGL_XMDWXXB C ON C.JBXX_ID = A.ID " + " WHERE EXE_ID = ?";
            } else {
                serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                        + " E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                        + " E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                        + " E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                        + " E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                        + " E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                        + " E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                        + " S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                        + " B.TZR,B.TZXMBH,D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.DEPART_CODE,D.USC,"
                        + " D.PARENT_ID,B.PROJECTCODE PROJECT_CODE,B.PROJECT_NAME,"
                        // 新增字段
                        + " B.PROJECT_ATTRIBUTES,B.INDUSTRY_STRUCTURE,B.OPEN_WAY,"
                        + " B.LEREP_INFO,B.CONTRIBUTION_INFO,B.FOREIGN_ABROAD_FLAG,"
                        + " B.TOTAL_MONEY_EXPLAIN,B.THE_INDUSTRY,"
                        + " B.IS_COUNTRY_SECURITY,B.SECURITY_APPROVAL_NUMBER,B.INVESTMENT_MODE,B.TOTAL_MONEY_DOLLAR,"
                        + " B.TOTAL_MONEY_DOLLAR_RATE,B.PROJECT_CAPITAL_MONEY,B.PROJECT_CAPITAL_MONEY_DOLLAR,"
                        + "B.PROJECT_CAPITAL_MONEY_RATE,"
                        + " B.INDUSTRIAL_POLICY_TYPE,B.INDUSTRIAL_POLICY,B.OTHER_INVESTMENT_APPLY_INFO,"
                        + "B.TRANSACTION_BOTH_INFO,"
                        + " B.MERGER_PLAN,B.MERGER_MANAGEMENT_MODE_SCOPE,B.GET_LAND_MODE,B.LAND_AREA,B.BUILT_AREA,"
                        + " B.IS_ADD_DEVICE,B.IMPORT_DEVICE_NUMBER_MONEY,B.PROJECT_SITE,B.CHINA_TOTAL_MONEY,"
                        + "B.INVESTMENT_MODE_JW" + " FROM T_WSBS_SERVICEITEM S, "+ JBPM6_EXECUTION+" E, " + tableName
                        + " B,T_MSJW_SYSTEM_DEPARTMENT D"
                        + " WHERE S.ITEM_CODE = E.ITEM_CODE  AND S.SSBMBM = D.DEPART_CODE" + " AND E.EXE_ID = ? AND B."
                        + tableKey + " = E.BUS_RECORDID ";
            }
        } else {
            serviceSql = "SELECT E.SQRMC,DECODE(E.SQRXB,'2','女','1','男') AS SQRXB, E.SSBMBM,"
                    + " E.SQRZJLX,E.SQRLXDZ ,E.SQRSFZ, E.SQRSJH, E.SQFS,"
                    + " E.JBR_NAME, DECODE(E.JBR_SEX,'2','女','1','男') AS JBR_SEX,E.JBR_MOBILE,"
                    + " E.JBR_ZJLX,E.JBR_ZJHM,E.JBR_LXDH,E.JBR_EMAIL, E.JBR_ADDRESS,E.BSYHLX,"
                    + " E.JBR_POSTCODE,E.BJCXMM,E.CREATE_TIME,E.CREATOR_ID,S.ITEM_ID,E.SQJG_NAME,"
                    + " E.SQJG_CODE,E.SQJG_TYPE,E.SQJG_LEALPERSON,E.SQJG_ADDR,E.SQJG_TEL,E.CREATOR_NAME,"
                    + " E.SQJG_LEALPERSON_ZJLX,E.SQJG_LEALPERSON_ZJHM,"
                    + " S.ITEM_NAME,S.ITEM_CODE,S.SWB_ITEM_ID,S.SWB_ITEM_CODE,S.SSQH,S.CNQXGZR,E.BUS_RECORDID,"
                    + " D.DEPART_NAME,D.ADVI_CODE,D.ORG_CODE,D.PARENT_ID,D.DEPART_CODE,' ' AS OPEN_WAY,D.USC,"
//                      + "B.TZR,B.TZXMBH,B.PROJECTCODE,B.PROJECT_NAME"
                    // EXEID 作为 PROJECTCODE
                    + " ' ' AS PROJECTCODE,E.EXE_ID,B.SBMC AS PROJECT_NAME"
                    + " FROM T_WSBS_SERVICEITEM S, "+ JBPM6_EXECUTION+" E, " + tableName + " B,T_MSJW_SYSTEM_DEPARTMENT D"
                    + " WHERE S.ITEM_CODE = E.ITEM_CODE  AND S.SSBMBM = D.DEPART_CODE" + " AND E.EXE_ID = ? AND B."
                    + tableKey + " = E.BUS_RECORDID ";
        }
        return serviceSql;
    }
    public String setUserSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select u.USER_TYPE,u.YHMC, decode(u.YHXB,'2','女','1','男') as YHXB,U.ORG_LAW_IDCARD, ");
        sql.append(" u.CSRQ,u.ZJLX,u.ZJHM,u.ZTZZ, u.YZBM,u.DZYX,u.SJHM,u.ZZJGDM,u.JGLX,u.FRDB,u.dwdz ");
        sql.append(" from t_bsfw_userinfo u where u.user_id = ? ");
        return sql.toString();
    }
    /**
     * 
     * 描述  合并流程信息表和流程信息归档表数据
     * @author Yanisin Shi
     * @return
     */
    public String unionJbpmExecution(){
        return "view_jbpm6_execution_new";
    }
    
    /**
     * 
     * 描述 流程结果信息表和流程结果信息归档表数据合并
     * @author Yanisin Shi
     * @return
     */
    public String unionJbpmFlowResult(){
        return "view_jbpm6_flow_result_new";
        
    }
    /**
     * 
     * 描述 流程环节信息表和流程环节信息归档表数据合并
     * @author Yanisin Shi
     * @return
     */
    public String unionJbpmTask(){
        
        return "view_jbpm6_task_new";
    }
    /**
     * 
     * 描述 处理附件信息
     * 
     * @author Derek Zhang
     * @created 2015年12月31日 下午2:48:31
     * @param swbdataRes
     * @param dataAbutment
     * @param unid
     * @param conn
     * @param attrs
     */
    private void setAttrInfo(Map<String, Object> swbdataRes, Map<String, Object> dataAbutment, String unid,
            Connection conn, List<Map<String, Object>> attrs) {
          Properties properties = FileUtil.readProperties("project.properties");
          String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
        if ("1".equals("" + swbdataRes.get("HAS_ATTR"))) {
            // 处理附件
            List<Map<String, Object>> attrList;
            attrList = DbUtil.findBySqlClob(conn, getAttrSql(), new Object[] { unid });
            if (attrList != null && attrList.size() > 0) {
                for (Map<String, Object> attr : attrList) {
                    byte[] bys = null;
                    boolean isSendSuccess = true;
                    try {
                        String fileMode = (String) attr.get("FILE_MODE");
                        //2021年9月16日 09:26:02 更改附件获取方式，从原来表中获取附件更改为远程下载文件服务器附件（兼容旧方式获取附件）
                        String filePath = attr.get("FILE_PATH")==null?"": attr.get("FILE_PATH").toString(); 
                        Blob blob = (Blob) attr.get("ATTR_CONTENT");
                        if ((blob == null && StringUtils.isEmpty(filePath)) || StringUtils.isEmpty(fileMode)
                                || "paper".equals(fileMode)) {
                            fileMode = "paper";
                        } else if(StringUtils.isNotEmpty(filePath)){//存在文件路径时，根据文件路径获取文件
                            bys = FileUtil.convertUrlFileToBytes(attachFileFolderPath + filePath);
                            isSendSuccess = this.sendAttrData(attr, dataAbutment, bys);
                        } else {
                            bys = blob.getBytes(1, (int) blob.length());
                            isSendSuccess = this.sendAttrData(attr, dataAbutment, bys);
                        }
                        if (isSendSuccess) {
                            Map<String, Object> tempAttr = new HashMap<String, Object>();
                            attrs.add(tempAttr);
                            tempAttr.put("unid", (String) attr.get("ATTR_ID"));
                            tempAttr.put("mode", (String) attr.get("FILE_MODE"));
                            tempAttr.put("code", (String) attr.get("CODE"));
                            tempAttr.put("name", (String) attr.get("NAME"));
                            tempAttr.put("seqNo", "" + attr.get("SEQ_NO"));
                            tempAttr.put("fileName", (String) attr.get("FILE_NAME"));
                            String creaditFileid = StringUtil.getString(attr.get("CREADIT_FILEID"));
                            if (!StringUtils.isEmpty(creaditFileid)) {
                                tempAttr.put("licenseIdentifier", creaditFileid);
                                log.info("licenseIdentifier==" + creaditFileid);
                            }
                        }
                        Blob blobUpdate = null;
                        String sql = "update t_bsfw_swbdata_attr at  " + " set at.oper_status = "
                                + (isSendSuccess ? 1 : 2) + " ,at.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') "
                                + " ,at.ATTR_CONTENT = " + blobUpdate 
                                + " where at.attr_id = ? ";
                        DbUtil.update(conn, sql, new Object[] { (String) attr.get("ATTR_ID") });
                    } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
                }
            }
        }
    }
    /**
     * 
     * 描述     只传附件节点信息 附件内容由附件定时器执行插入
     * @author Yanisin Shi
     * @param swbdataRes
     * @param dataAbutment
     * @param unid
     * @param conn
     * @param attrs
     */
    private void setAttrInfoNew(Map<String, Object> swbdataRes, String unid,
            Connection conn, List<Map<String, Object>> attrs) {
        if ("1".equals("" + swbdataRes.get("HAS_ATTR"))) {
            // 处理附件 设置附件节点信息
            List<Map<String, Object>> attrList;
            attrList = DbUtil.findBySqlClob(conn, getAttrSql(), new Object[] { unid });
            if (attrList != null && attrList.size() > 0) {
                for (Map<String, Object> attr : attrList) {
                            Map<String, Object> tempAttr = new HashMap<String, Object>();
                            attrs.add(tempAttr);
                            tempAttr.put("unid", (String) attr.get("ATTR_ID"));
                            tempAttr.put("mode", (String) attr.get("FILE_MODE"));
                            tempAttr.put("code", (String) attr.get("CODE"));
                            tempAttr.put("name", (String) attr.get("NAME"));
                            tempAttr.put("seqNo", "" + attr.get("SEQ_NO"));
                            tempAttr.put("fileName", (String) attr.get("FILE_NAME"));
                            String creaditFileid = StringUtil.getString(attr.get("CREADIT_FILEID"));
                            if (!StringUtils.isEmpty(creaditFileid)) {
                                tempAttr.put("licenseIdentifier", creaditFileid);
                                log.info("licenseIdentifier==" + creaditFileid);
                        }
                       
                    
                }
            }
        }
    }
    /**
     * 
     * 描述   处理附件内容定时器
     * @author Yanisin Shi
     * @param dataAbutment
     * @param unid
     * @param conn
     * @param attrs
     */
    public void sendAttrContentToSwb() {
        Connection conn=null;
            try {
                conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
        // 获取指令表数据;
      List<Map<String, Object>> dataAbutmentList = this.dao.findDataAbutmentList(conn);
        Map<String, Object> dataAbutment_0006=dataAbutmentList.get(0);
          Properties properties = FileUtil.readProperties("project.properties");
          String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
            // 处理附件
          List<Map<String, Object>> attrList = DbUtil.findBySqlClob(conn, getAttrSqlByTime(),null);
            if (attrList != null && attrList.size() > 0) {
                for (Map<String, Object> attr : attrList) {
                executor2.execute(new Runnable() {
                     @Override
                    public void run() {
                    byte[] bys = null;
                    boolean isSendSuccess = true;
                    try {
                        String fileMode = (String) attr.get("FILE_MODE");
                        //2021年9月16日 09:26:02 更改附件获取方式，从原来表中获取附件更改为远程下载文件服务器附件（兼容旧方式获取附件）
                        String filePath = attr.get("FILE_PATH")==null?"": attr.get("FILE_PATH").toString(); 
                        Blob blob = (Blob) attr.get("ATTR_CONTENT");
                        if ((blob == null && StringUtils.isEmpty(filePath)) || StringUtils.isEmpty(fileMode)
                                || "paper".equals(fileMode)) {
                            fileMode = "paper";
                        } else if(StringUtils.isNotEmpty(filePath)){//存在文件路径时，根据文件路径获取文件
                            bys = FileUtil.convertUrlFileToBytes(attachFileFolderPath + filePath);
                            isSendSuccess = sendAttrData(attr, dataAbutment_0006, bys);
                        } else {
                            bys = blob.getBytes(1, (int) blob.length());
                            isSendSuccess = sendAttrData(attr, dataAbutment_0006, bys);
                        }
                        if(isSendSuccess){
                        Connection conn1 = DbUtil.getConnect(getPtzhspDbUrl(), getPtzhspDbUsername(),getPtzhspDbPassword());
                        Blob blobUpdate = null;
                        String sql = "update t_bsfw_swbdata_attr at  " + " set at.oper_status = "
                                + (isSendSuccess ? 1 : 2) + " ,at.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') "
                                + " ,at.ATTR_CONTENT = " + blobUpdate 
                                + " where at.attr_id = ? ";
                        DbUtil.update(conn1, sql, new Object[] { (String) attr.get("ATTR_ID") });
                        }
                        } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
                
                    }});
            }
       
        }
            } catch (SQLException e1) {
            // TODO Auto-generated catch block
            log.error("本地数据库连接异常",e1);
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
            
  
    /**
     * 
     * 描述   审批结果附件回传省网
     * @author Yanisin Shi
     * @param swbdataRes
     * @param dataAbutment
     * @param unid
     * @param conn
     * @param attrs
     * create time 2021年8月12日
     * @param apasInfo 
     */
    private void setResultAttrInfo(Map<String, Object> swbdataRes, Map<String, Object> dataAbutment, String unid,
            List<Map<String, Object>> attrs, Map<String, Object> apasInfo) {
        if ("1".equals("" + swbdataRes.get("HAS_ATTR"))) {
            // 处理附件
          //  attrList = DbUtil.findBySqlClob(conn, getAttrSql(), new Object[] { unid });
            List<Map<String, Object>> attrList = dao.findBySql( getAttrSql() , new Object[] { unid },null);
            if (attrList != null && attrList.size() > 0) {
                for (Map<String, Object> attr : attrList) {
                   /* byte[] bys = null;
                    boolean isSendSuccess = true;
                    try {
                        String fileMode = (String) attr.get("FILE_MODE");
                        //2021年9月16日 09:26:02 更改附件获取方式，从原来表中获取附件更改为远程下载文件服务器附件（兼容旧方式获取附件）
                        String filePath = attr.get("FILE_PATH")==null?"": attr.get("FILE_PATH").toString(); 
                        Blob blob = (Blob) attr.get("ATTR_CONTENT");
                        if ((blob == null && StringUtils.isEmpty(filePath)) || StringUtils.isEmpty(fileMode)
                                || "paper".equals(fileMode)) {
                            fileMode = "paper";
                        } else if(StringUtils.isNotEmpty(filePath)){//存在文件路径时，根据文件路径获取文件
                            Properties properties = FileUtil.readProperties("project.properties");
                            String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
                            bys = FileUtil.convertUrlFileToBytes(attachFileFolderPath + filePath);
                            isSendSuccess = this.sendAttrData(attr, dataAbutment, bys);
                        } else {
                            bys = blob.getBytes(1, (int) blob.length());
                            isSendSuccess = this.sendAttrData(attr, dataAbutment, bys);
                        }
                        if (isSendSuccess) {*/
                            Map<String, Object> tempAttr = new HashMap<String, Object>();
                            attrs.add(tempAttr);
                            tempAttr.put("unid", (String) attr.get("ATTR_ID"));
                            //审批结果附件<AttrCategory>审批结果附件</AttrCategory>
                            tempAttr.put("AttrCategory", "审批结果附件");
                             //附件编码
                            tempAttr.put("AttrNumber", "" + apasInfo.get("xkfileNum"));
                             //附件文件名
                            tempAttr.put("AttrName", (String) attr.get("FILE_NAME"));
                           log.info((String) attr.get("FILE_NAME"));
                          
                       /* }
                        Blob blobUpdate = null;
                        String sql = "update t_bsfw_swbdata_attr at  " + " set at.oper_status = "
                                + (isSendSuccess ? 1 : 2) + " ,at.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') "
                                + " ,at.ATTR_CONTENT = " + blobUpdate + " where at.attr_id = ? ";
                        DbUtil.update(conn, sql, new Object[] { (String) attr.get("ATTR_ID") }, false);
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                    }*/
                }
            }
        }
    }

    /**
     * 
     * 描述 保存表单数据
     * 
     * @author Derek Zhang
     * @created 2015年12月11日 下午4:31:36
     * @param formInfos
     */
    public void setFormInfoData(Map<String, Object> formInfos, String tablename, String keyname, String keyValue) {
        Map<String, Object> itemInfo = new HashMap<String, Object>();
        String sql = setFormInfoSql(tablename, keyname);
        Connection conn;
        try {
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            itemInfo = DbUtil.getMapBySql(conn, sql, new Object[] { keyValue });
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        if (!itemInfo.isEmpty()) {
            formInfos.put("unid", keyValue);
            formInfos.put("formName", "project_info");
            formInfos.put("formAlias", "项目申报基本信息表");
            formInfos.put("item", itemInfo);
            String applyDate = StringUtil.nullToString((String) itemInfo.get("APPLY_DATE"));
            if (applyDate.length() > 10) {
                applyDate = applyDate.substring(0, 10);
            } else if (applyDate.length() < 10) {
                applyDate = DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd");
            }
            itemInfo.put("APPLY_DATE", applyDate);
            String lerepString = (String) itemInfo.get("lerep_info");
            if (lerepString != null) {
                List lereplList = JSON.parseArray(lerepString);
                List<Map<String, Object>> lereplsList = new ArrayList<Map<String, Object>>();
                if (lereplList != null && lereplList.size() > 0) {
                    for (int i = 0; i < lereplList.size(); i++) {
                        lereplsList.add(JsonUtil.parseJSON2Map(lereplList.get(i).toString()));
                    }
                }
                formInfos.put("lereps", lereplsList);
            }
//            contribution_info
            String contributionString = (String) itemInfo.get("contribution_info");
            if (contributionString != null) {
                List contributionList = JSON.parseArray(contributionString);
                List<Map<String, Object>> contributionsList = new ArrayList<Map<String, Object>>();
                if (contributionList != null && contributionList.size() > 0) {
                    for (int i = 0; i < contributionList.size(); i++) {
                        contributionsList.add(JsonUtil.parseJSON2Map(contributionList.get(i).toString()));
                    }
                }
                formInfos.put("contributions", contributionsList);
            }
        }

    }
    /**
     * 
     * 描述
     * @author Yanisin Shi
     * @param formInfos
     * @param tablename
     * @param keyname
     * @param keyValue
     */
    public void setFormInfoDataNew(Map<String, Object> formInfos, String tablename, String keyname, String keyValue,Connection conn) {
        Map<String, Object> itemInfo = new HashMap<String, Object>();
        String sql = setFormInfoSql(tablename, keyname);
            itemInfo = DbUtil.getMapBySql(conn, sql, new Object[] { keyValue },false);
        if (!itemInfo.isEmpty()) {
            formInfos.put("unid", keyValue);
            formInfos.put("formName", "project_info");
            formInfos.put("formAlias", "项目申报基本信息表");
            formInfos.put("item", itemInfo);
            String applyDate = StringUtil.nullToString((String) itemInfo.get("APPLY_DATE"));
            if (applyDate.length() > 10) {
                applyDate = applyDate.substring(0, 10);
            } else if (applyDate.length() < 10) {
                applyDate = DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd");
            }
            itemInfo.put("APPLY_DATE", applyDate);
            String lerepString = (String) itemInfo.get("lerep_info");
            if (lerepString != null) {
                List lereplList = JSON.parseArray(lerepString);
                List<Map<String, Object>> lereplsList = new ArrayList<Map<String, Object>>();
                if (lereplList != null && lereplList.size() > 0) {
                    for (int i = 0; i < lereplList.size(); i++) {
                        lereplsList.add(JsonUtil.parseJSON2Map(lereplList.get(i).toString()));
                    }
                }
                formInfos.put("lereps", lereplsList);
            }
//            contribution_info
            String contributionString = (String) itemInfo.get("contribution_info");
            if (contributionString != null) {
                List contributionList = JSON.parseArray(contributionString);
                List<Map<String, Object>> contributionsList = new ArrayList<Map<String, Object>>();
                if (contributionList != null && contributionList.size() > 0) {
                    for (int i = 0; i < contributionList.size(); i++) {
                        contributionsList.add(JsonUtil.parseJSON2Map(contributionList.get(i).toString()));
                    }
                }
                formInfos.put("contributions", contributionsList);
            }
        }

    }
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/10 11:07:00
     * @param
     * @return
     */
    @Override
    public String setTableNameSql() {
        StringBuffer tableNameSql = new StringBuffer();
        tableNameSql.append(" select e.bus_tablename, cu.column_name from jbpm6_execution e, user_cons_columns cu, ");
        tableNameSql.append(
                " user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' ");
        tableNameSql.append(" and e.bus_tablename = au.table_name and e.exe_id = ? ");
        return tableNameSql.toString();
    }
    /**
     * 
     * 描述 新增归档数据处理
     * @author Yanisin Shi
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#setTableNameSqlNew()
     */
    @Override
    public String setTableNameSqlNew() {
        String jbpm6_execution=unionJbpmExecution();
        StringBuffer tableNameSql = new StringBuffer();
        tableNameSql.append(" select e.bus_tablename, cu.column_name from (select * from "+jbpm6_execution+" a where a.exe_id = ?) e, user_cons_columns cu, ");
        tableNameSql.append(
                " user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' ");
        tableNameSql.append(" and e.bus_tablename = au.table_name  ");
        return tableNameSql.toString();
    }

    /**
     * 描述:设置工程建设项目表单信息sql
     *
     * @author Madison You
     * @created 2020/3/2 15:51:00
     * @param
     * @return
     */
    private String setFormInfoSql(String tablename, String keyname) {
        StringBuffer sql = new StringBuffer();
        /* 消防验收 */
        String newForm = dictionaryService.getDicCode("GCJSBD", "newForm");
        if (newForm.contains(tablename)) {
            sql.append(" select t.PROJECT_CODE,t.PROJECT_NAME,t.OPEN_WAY,e.DIVISION_CODE,e.PROJECT_TYPE, ");
            sql.append(" e.PROJECT_NATURE,x.ENTERPRISE_NAME,x.LEREP_CERTTYPE,x.LEREP_CERTNO,t.CREATE_TIME  ");
            sql.append(" APPLY_DATE,x.CONTACT_NAME,x.CONTACT_EMAIL,x.CONTACT_TEL,e.PLACE_CODE,e.PLACE_CODE_DETAIL, ");
            sql.append(
                    " e.lerep_info,e.FOREIGN_ABROAD_FLAG,to_char(e.TOTAL_MONEY) TOTAL_MONEY,e.TOTAL_MONEY_EXPLAIN,e.THE_INDUSTRY, ");
            sql.append(" e.IS_COUNTRY_SECURITY,e.security_approval_number,e.INVESTMENT_MODE,e.TOTAL_MONEY_DOLLAR, ");
            sql.append(" e.TOTAL_MONEY_DOLLAR_RATE,e.PROJECT_CAPITAL_MONEY,e.PROJECT_CAPITAL_MONEY_DOLLAR, ");
            sql.append(" e.total_money_dollar_rate,e.project_capital_money,e.project_capital_money_dollar, ");
            sql.append(" e.project_capital_money_rate,e.industrial_policy_type,e.industrial_policy, ");
            sql.append(" e.other_investment_apply_info,e.transaction_both_info,e.merger_plan,e.contribution_info, ");
            sql.append(" e.merger_management_mode_scope,e.get_land_mode,e.land_area,e.built_area,e.is_add_device, ");
            sql.append(" e.import_device_number_money,e.project_site,e.china_total_money,e.INDUSTRY, ");
            sql.append(" e.PROJECT_ATTRIBUTES,e.INDUSTRY_STRUCTURE,e.SCALE_CONTENT,e.START_YEAR,e.END_YEAR ");
            sql.append(" from  ").append(tablename);
            sql.append(" t left join SPGL_XMJBXXB e on t.PROJECT_CODE = e.PROJECT_CODE ");
            sql.append(" left join SPGL_XMDWXXB x on e.id = x.JBXX_ID ");
            sql.append(" where t.").append(keyname).append(" = ?");
        } else {
            sql.append(" select t.projectcode as project_code, t.project_name, ");
            sql.append(" t.DIVISION_CODE,t.PROJECT_TYPE,t.PROJECT_NATURE,t.ENTERPRISE_NAME, ");
            sql.append(" t.LEREP_CERTTYPE,t.LEREP_CERTNO,t.PROJECT_REMARK,t.APPLY_DATE,t.CONTACT_NAME, ");
            sql.append(" t.CONTACT_IDCARD,t.CONTACT_EMAIL , t.CONTACT_TEL ,t.CONTACT_MOBILE, ");
            sql.append(" t.CONTACT_ADDRESS,t.CONTACT_POSTCODE,t.PLACE_CODE,t.PLACE_CODE_DETAIL,t.IS_DE_AREA, ");
            sql.append("t.lerep_info,t.contribution_info,t.foreign_abroad_flag,t.total_money_explain,t.the_industry,");
            sql.append(" t.is_country_security,t.security_approval_number,t.investment_mode,t.total_money_dollar, ");
            sql.append(" t.total_money_dollar_rate,t.project_capital_money,t.project_capital_money_dollar, ");
            sql.append(" t.project_capital_money_rate, ");
            sql.append(" t.industrial_policy_type,t.industrial_policy,t.other_investment_apply_info, ");
            sql.append(" t.transaction_both_info, ");
            sql.append(" t.merger_plan,t.merger_management_mode_scope,t.get_land_mode,t.land_area,t.built_area, ");
            sql.append(" t.is_add_device,t.import_device_number_money,t.project_site,t.china_total_money, ");
            sql.append(" t.investment_mode_jw, ");
            sql.append(
                    " t.DE_AREA_NAME,t.INDUSTRY,t.PERMIT_INDUSTRY,t.PROJECT_STAGE,to_char(t.TOTAL_MONEY) TOTAL_MONEY, ");
            sql.append(" t.OPEN_WAY,t.PROJECT_ATTRIBUTES,t.INDUSTRY_STRUCTURE, ");
            sql.append(" t.SCALE_CONTENT,t.START_YEAR,t.END_YEAR from ").append(tablename);
            sql.append(" t where t. ").append(keyname).append(" = ?");
        }
        return sql.toString();
    }

    /**
     * 描述 将附件数据报送到 省网办前置机数据库
     * 
     * @author Derek Zhang
     * @created 2015年12月9日 上午9:19:27
     * @return
     */
     
    public boolean sendAttrData(Map<String, Object> attr, Map<String, Object> dataAbutment, byte[] bys) {
        int result=0;
        Connection conn = null;
        String dbUrl = (String) dataAbutment.get("DB_URL");
        String dbUserName = (String) dataAbutment.get("DB_USERNAME");
        String dbPass = (String) dataAbutment.get("DB_PASSWORD");
        try {
             DriverManager.setLoginTimeout(5);//设置连接超时时间，5秒超时
            conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
            StringBuffer str=new StringBuffer("select * from cityattr where unid=?");
            List attrlist=DbUtil.findBySql(conn, str.toString(),new Object[]{(String) attr.get("ATTR_ID")},false);
            if(attrlist.size()>0){
                log.info("查询前置库附件ATTR_ID数据已存在，执行更新操作ATTR_ID："+(String) attr.get("ATTR_ID"));
                 StringBuffer updateSql = new StringBuffer("update cityattr set ");
                 updateSql.append(" createtime=sysdate, fromareacode=?, fromareaname=?, toareacode=?, toareaname=?, ");
                 updateSql.append(" content=?  where unid=? ");
                    Object[] o = new Object[] { AllConstant.FROM_AREA_CODE,
                            AllConstant.FROM_AREA_NANE, AllConstant.TO_AREA_CODE, AllConstant.TO_AREA_NAME, bys,(String) attr.get("ATTR_ID") };
                    result = DbUtil.update(conn,updateSql.toString(), o,false);
                    if (result > 0){
                         log.info("更新附件成功！");
                        return true;
                    }else{
                         log.info("更新附件失败！");
                        return false;
                    }
            }else{
                log.info("执行前置库附件数据插入操作ATTR_ID:"+(String) attr.get("ATTR_ID"));
                String insertSql = "insert into cityattr "
                        + " (unid, createtime, fromareacode, fromareaname, toareacode, toareaname, content) "
                        + " values ( ? , sysdate, ? , ? , ? , ? , ? ) ";
                Object[] o = new Object[] { (String) attr.get("ATTR_ID"), AllConstant.FROM_AREA_CODE,
                        AllConstant.FROM_AREA_NANE, AllConstant.TO_AREA_CODE, AllConstant.TO_AREA_NAME, bys };
                 result = DbUtil.update(conn, insertSql, o,false);
                 if (result > 0){
                     log.info("插入附件成功！");
                     return true;
                 }else{
                     log.info("插入附件失败！");
                     return false;
                 }
            }
        } catch (Exception e) {
            log.error(e);
           return false;
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 
     * 描述 设置报言语数据
     * 
     * @author Derek Zhang
     * @created 2015年12月8日 下午2:46:04
     * @param exeid
     * @param apasInfo
     * @param proposer
     * @param operator
     * @param attrs
     * @param sms
     */
    private void setBodyData(String exeid, Map<String, Object> apasInfo, Map<String, Object> proposer,
            Map<String, Object> operator, Map<String, Object> sms, Map<String, Object> serviceItemMap) {
       

       // 设置分包 -----sms 目前短信下发不走省网办，因此数据项为空
        sms.put("receiver", "");
        sms.put("address", "");
        sms.put("content", "");
        // 设置分包 -----operator 经办人信息
        String operatorUser = StringUtil.nullORSpaceToDefault((String) serviceItemMap.get("JBR_NAME"),
                (String) serviceItemMap.get("SQRMC"));
        operatorUser = StringUtil.nullORSpaceToDefault(operatorUser, (String) serviceItemMap.get("CREATOR_NAME"));
        operator.put("name", operatorUser);
        operator.put("sex", StringUtil.nullToString((String) serviceItemMap.get("JBR_SEX")));
        operator.put("birthday", "");
        operator.put("certificateType", StringUtil.nullORSpaceToDefault((String) serviceItemMap.get("JBR_ZJLX"), "SF"));
        operator.put("certificateNumber", StringUtil.nullToString((String) serviceItemMap.get("JBR_ZJHM")));
        operator.put("address", StringUtil.nullToString((String) serviceItemMap.get("JBR_ADDRESS")));
        operator.put("postcode", StringUtil.nullToString((String) serviceItemMap.get("JBR_POSTCODE")));
        /* 如果经办人联系电话为空，那么就用申请人联系电话 */
        operator.put("mobilePhone", StringUtil.nullORSpaceToDefault((String) serviceItemMap.get("JBR_MOBILE"),
                (String) serviceItemMap.get("SQRSJH")));
        operator.put("tel", "");
        operator.put("identificationStatus", "");
        // 网上申请
        if ("1".equals((String) serviceItemMap.get("SQFS"))) {
            setProposerData(proposer, serviceItemMap);
        } else {
            // 窗口办理
            String bsyhlx = (String) serviceItemMap.get("BSYHLX");
            setProposerDataByCK(proposer, serviceItemMap, bsyhlx);
        }
        // 设置分包 -----apasInfo
        String swbItemId = serviceItemMap.get("SWB_ITEM_ID") == null ? " "
                : serviceItemMap.get("SWB_ITEM_ID").toString();
        apasInfo.put("serviceID", swbItemId);
        apasInfo.put("serviceCode", (String) serviceItemMap.get("SWB_ITEM_CODE"));
        apasInfo.put("serviceName", (String) serviceItemMap.get("ITEM_NAME"));
        apasInfo.put("areaCode", (String) serviceItemMap.get("SSQH"));
        apasInfo.put("projectCode", (String) serviceItemMap.get("PROJECT_CODE"));
        apasInfo.put("projectName", (String) serviceItemMap.get("PROJECT_NAME"));
        apasInfo.put("PROJECT_NAME", (String) serviceItemMap.get("PROJECT_NAME"));
        apasInfo.put("promiseDay", null == serviceItemMap.get("CNQXGZR") ? "0" : serviceItemMap.get("CNQXGZR"));
        apasInfo.put("SN", exeid);
        String defultPass = this.dictionaryService.getDicNames("swbdjpz", "swbDefultCXMM");
        defultPass = StringUtil.nullORSpaceToDefault(defultPass, "888888");
        apasInfo.put("PWD", StringUtils.isEmpty((String) serviceItemMap.get("BJCXMM")) ? defultPass
                : (String) serviceItemMap.get("BJCXMM"));
        apasInfo.put("declareTime", (String) serviceItemMap.get("CREATE_TIME"));
        apasInfo.put("receiveDeptName", (String) serviceItemMap.get("DEPART_NAME"));
        apasInfo.put("openWay", StringUtil.nullToString((String) serviceItemMap.get("OPEN_WAY")));
        String orgCode = (String) serviceItemMap.get("ORG_CODE");
        if (serviceItemMap.get("USC") != null) {
            apasInfo.put("receiveDeptCode", serviceItemMap.get("USC"));
        } else if (StringUtils.isEmpty(orgCode)) {
            String parentId = (String) serviceItemMap.get("PARENT_ID");
            Connection conn = null;
            try {
                conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
            String parentOrgCode = dao.getParentOrgCode(parentId, conn);
            apasInfo.put("receiveDeptCode", parentOrgCode);
        } else {
            apasInfo.put("receiveDeptCode", (String) serviceItemMap.get("ORG_CODE"));
        }
    }

    /**
     * 
     * 描述设置经办人、法人、个人信息--网上申报
     * 
     * @author Derek Zhang
     * @created 2015年12月8日 下午2:43:54
     * @param proposer
     * @param serviceItemMap
     */
    private void setProposerData(Map<String, Object> proposer, Map<String, Object> serviceItemMap) {
        //Connection conn = null;
        //try {
            /*conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String userId = (String) serviceItemMap.get("CREATOR_ID");
            String userSql = setUserSql();
            Map<String, Object> userInfoMap = DbUtil.getMapBySql(conn, userSql, new Object[] { userId });*/
            Map<String, Object> userInfoMap = getUserInfoMap(serviceItemMap);
            if(userInfoMap!=null){
                userInfoMap = encryptionService.mapDecrypt(userInfoMap, "T_BSFW_USERINFO");
            }
            proposer.put("areaCode", "350128");
            if (userInfoMap != null && "1".equals((String) userInfoMap.get("USER_TYPE"))) {
                // 个人办事
                // 设置分包 -----proposer 个人办事
                proposer.put("applyType", "0");
                // 申请人信息取办件申请人信息，不取申报办件的用户信息
                Map<String, Object> person = new HashMap<String, Object>();
                proposer.put("person", person);
                person.put("applyName", StringUtil.nullToString((String) serviceItemMap.get("SQRMC")));
                /* 如果申请人性别为空则改用身份证号码进行判断 */
                if (StringUtil.nullToString((String) serviceItemMap.get("SQRXB")) != "") {
                    person.put("sex", StringUtil.nullToString((String) serviceItemMap.get("SQRXB")));
                } else {
                    person.put("sex", findSexByCardNo(StringUtil.nullToString((String) serviceItemMap.get("SQRSFZ"))));
                }
                person.put("birthday", "");
                person.put("certificateType",
                        StringUtil.nullORSpaceToDefault(((String) serviceItemMap.get("SQRZJLX")), "SF"));
                person.put("certificateNumber", StringUtil.nullToString((String) serviceItemMap.get("SQRSFZ")));
                person.put("address", StringUtil.nullToString((String) serviceItemMap.get("SQRLXDZ")));
                person.put("postcode", StringUtil.nullToString((String) serviceItemMap.get("JBR_POSTCODE")));
                person.put("mobilePhone", StringUtil.nullToString((String) serviceItemMap.get("SQRSJH")));
                person.put("tel", StringUtil.nullToString((String) serviceItemMap.get("SQRDHH")));
                person.put("identificationStatus", "");
                /*
                 * Map<String, Object> person = new HashMap<String, Object>();
                 * proposer.put("person", person); person.put("applyName",
                 * StringUtil.nullToString((String) userInfoMap.get("YHMC"))); if
                 * (StringUtil.nullToString((String) userInfoMap.get("YHXB")) != "") {
                 * person.put("sex", StringUtil.nullToString((String) userInfoMap.get("YHXB")));
                 * }else { 如果没有性别，则用身份证号码判断 person.put("sex",
                 * findSexByCardNo(StringUtil.nullToString((String) userInfoMap.get("ZJHM"))));
                 * } person.put("birthday", StringUtil.nullToString((String)
                 * userInfoMap.get("CSRQ"))); person.put("certificateType",
                 * StringUtils.isEmpty(StringUtil.nullToString((String) userInfoMap
                 * .get("ZJLX"))) ? "SF" : (String) userInfoMap.get("ZJLX")); String zjhm =
                 * StringUtil.nullToString((String) userInfoMap.get("ZJHM")); if
                 * (!"".equals(zjhm)) { person.put("certificateNumber", zjhm); } else {
                 * person.put("certificateNumber", StringUtil.nullToString((String)
                 * serviceItemMap.get("SQRSFZ"))); } person.put("address",
                 * StringUtil.nullToString((String) userInfoMap.get("ZTZZ")));
                 * person.put("postcode", StringUtil.nullToString((String)
                 * userInfoMap.get("YZBM"))); person.put("mobilePhone",
                 * StringUtil.nullToString((String) userInfoMap.get("SJHM"))); person.put("tel",
                 * ""); person.put("identificationStatus", "");
                 */
                // 法人信息为空
                Map<String, Object> unit = new HashMap<String, Object>();
                proposer.put("unit", unit);
                unit.put("unitName", "");
                unit.put("unitCode", "");
                unit.put("unitType", "");
                unit.put("lealPerson", "");
                unit.put("address", "");
                unit.put("tel", "");
                unit.put("postcode", "");
                unit.put("identificationStatus", "");
            } else if (userInfoMap != null && "2".equals((String) userInfoMap.get("USER_TYPE"))) {
                // 法人办事
                // 设置分包 -----proposer 个人信息为空
                proposer.put("applyType", "1");
                Map<String, Object> person = new HashMap<String, Object>();
                proposer.put("person", person);
                person.put("applyName", "");
                person.put("sex", "");
                person.put("birthday", "");
                person.put("certificateType", "");
                person.put("certificateNumber", "");
                person.put("address", "");
                person.put("postcode", "");
                person.put("mobilePhone", "");
                person.put("tel", "");
                person.put("identificationStatus", "");
                // 法人办事
                /* 工程建设项目法人信息 */
                if ((boolean) serviceItemMap.get("isProject")) {
                    Map<String, Object> unit = new HashMap<String, Object>();
                    proposer.put("unit", unit);
                    unit.put("unitName", StringUtil.nullToString((String) serviceItemMap.get("SQJG_NAME")));
                    unit.put("unitCode", StringUtil.nullToString((String) serviceItemMap.get("SQJG_CODE")));
                    if (StringUtil.nullToString((String) serviceItemMap.get("SQJG_CODE")).length() == 9) {
                        unit.put("unitIdcardType", "JGDM");
                    }
                    if (StringUtil.nullToString((String) serviceItemMap.get("SQJG_CODE")).length() == 18) {
                        unit.put("unitIdcardType", "XYDM");
                    }
                    unit.put("unitType", StringUtil.nullToString((String) serviceItemMap.get("SQJG_TYPE")));
                    unit.put("lealPerson", StringUtil.nullToString((String) serviceItemMap.get("FRDB")));
                    unit.put("legalIdcard", StringUtil.nullToString((String) userInfoMap.get("ORG_LAW_IDCARD")));
                    if (userInfoMap.get("ORG_LAW_IDCARD") != null) {
                        unit.put("legalIdcardType", "SF");
                    }
                    unit.put("address", StringUtil.nullToString((String) userInfoMap.get("DWDZ")));
                    unit.put("tel", StringUtil.nullToString((String) userInfoMap.get("SJHM")));
                    unit.put("postcode", StringUtil.nullToString((String) userInfoMap.get("YZBM")));
                    unit.put("identificationStatus", "");
                } else {
                    /* 普通法人信息 */
                    Map<String, Object> unit = new HashMap<String, Object>();
                    proposer.put("unit", unit);
                    unit.put("unitName", StringUtil.nullToString((String) userInfoMap.get("YHMC")));
                    unit.put("unitCode", StringUtil.nullToString((String) userInfoMap.get("ZZJGDM")));
                    /* 2019/11/28 Madison You 解决返回信息 构证件类型为JGDM时，请填写9位组织机构代码 问题 */
                    if (StringUtil.nullToString((String) userInfoMap.get("ZZJGDM")).length() == 9) {
                        unit.put("unitIdcardType", "JGDM");
                    }
                    if (StringUtil.nullToString((String) userInfoMap.get("ZZJGDM")).length() == 18) {
                        unit.put("unitIdcardType", "XYDM");
                    }
                    unit.put("unitType", StringUtil.nullToString((String) userInfoMap.get("JGLX")));
                    unit.put("lealPerson", StringUtil.nullToString((String) userInfoMap.get("FRDB")));
                    unit.put("legalIdcard", StringUtil.nullToString((String) userInfoMap.get("ORG_LAW_IDCARD")));
                    if (userInfoMap.get("ORG_LAW_IDCARD") != null) {
                        unit.put("legalIdcardType", "SF");
                    }
                    unit.put("address", StringUtil.nullToString((String) userInfoMap.get("DWDZ")));
                    unit.put("tel", StringUtil.nullToString((String) userInfoMap.get("SJHM")));
                    unit.put("postcode", StringUtil.nullToString((String) userInfoMap.get("YZBM")));
                    unit.put("identificationStatus", "");
                }

            }
        /*} catch (SQLException e) {
        }*/
    }

    /**
     * 
     * 描述设置经办人、法人、个人信息 窗口办理
     * 
     * @author Derek Zhang
     * @created 2015年12月8日 下午2:43:54
     * @param proposer
     * @param serviceItemMap
     * @param bsyhlx
     */
    private void setProposerDataByCK(Map<String, Object> proposer, Map<String, Object> serviceItemMap, String bsyhlx) {

        proposer.put("areaCode", "350128");
        if (bsyhlx.trim().equals("1")) {
            // 个人办事

            // 设置分包 -----proposer 个人办事
            proposer.put("applyType", "0");
            Map<String, Object> person = new HashMap<String, Object>();
            proposer.put("person", person);
            person.put("applyName", StringUtil.nullToString((String) serviceItemMap.get("SQRMC")));
            /* 如果申请人性别为空则改用身份证号码进行判断 */
            if (StringUtil.nullToString((String) serviceItemMap.get("SQRXB")) != "") {
                person.put("sex", StringUtil.nullToString((String) serviceItemMap.get("SQRXB")));
            } else {
                person.put("sex", findSexByCardNo(StringUtil.nullToString((String) serviceItemMap.get("SQRSFZ"))));
            }
            person.put("birthday", "");
            person.put("certificateType",
                    StringUtil.nullORSpaceToDefault(((String) serviceItemMap.get("SQRZJLX")), "SF"));
            person.put("certificateNumber", StringUtil.nullToString((String) serviceItemMap.get("SQRSFZ")));
            person.put("address", StringUtil.nullToString((String) serviceItemMap.get("SQRLXDZ")));
            person.put("postcode", StringUtil.nullToString((String) serviceItemMap.get("JBR_POSTCODE")));
            person.put("mobilePhone", StringUtil.nullToString((String) serviceItemMap.get("SQRSJH")));
            person.put("tel", StringUtil.nullToString((String) serviceItemMap.get("SQRDHH")));
            person.put("identificationStatus", "");
            // 法人信息为空
            Map<String, Object> unit = new HashMap<String, Object>();
            proposer.put("unit", unit);
            unit.put("unitName", "");
            unit.put("unitCode", "");
            unit.put("unitType", "");
            unit.put("lealPerson", "");
            unit.put("address", "");
            unit.put("tel", "");
            unit.put("postcode", "");
            unit.put("identificationStatus", "");
        } else if (bsyhlx.trim().equals("2")) {
            // 企业办事
            // 设置分包 -----proposer 个人信息为空
            proposer.put("applyType", "1");
            Map<String, Object> person = new HashMap<String, Object>();
            proposer.put("person", person);
            person.put("applyName", "");
            person.put("sex", "");
            person.put("birthday", "");
            person.put("certificateType", "");
            person.put("certificateNumber", "");
            person.put("address", "");
            person.put("postcode", "");
            person.put("mobilePhone", "");
            person.put("tel", "");
            person.put("identificationStatus", "");
            // 法人办事
            Map<String, Object> unit = new HashMap<String, Object>();
            proposer.put("unit", unit);
            unit.put("unitName", StringUtil.nullToString((String) serviceItemMap.get("SQJG_NAME")));
            unit.put("unitCode", StringUtil.nullToString((String) serviceItemMap.get("SQJG_CODE")));
            /* 2019/11/28 Madison You 解决返回信息 构证件类型为JGDM时，请填写9位组织机构代码 问题 */
            unit.put("unitIdcardType", "XYDM");
            unit.put("unitType", StringUtil.nullToString((String) serviceItemMap.get("SQJG_TYPE")));
            unit.put("lealPerson", StringUtil.nullToString((String) serviceItemMap.get("SQJG_LEALPERSON")));
            unit.put("legalIdcard", StringUtil.nullToString((String) serviceItemMap.get("SQJG_LEALPERSON_ZJHM")));
            unit.put("legalIdcardType", StringUtil.nullToString((String) serviceItemMap.get("SQJG_LEALPERSON_ZJLX")));
            unit.put("address", StringUtil.nullToString((String) serviceItemMap.get("SQJG_ADDR")));
            unit.put("tel", StringUtil.nullToString((String) serviceItemMap.get("SQJG_TEL")));
            unit.put("postcode", "");
            unit.put("identificationStatus", "");
        }
    }

    /**
     * 
     * 描述 市县区向省网办报送办件
     * 
     * @author Derek Zhang
     * @created 2015年12月7日 上午9:56:45
     * @param dataAbutment 接口配置信息
     * @param busDataMap   业务数据包
     * @param abutDesc     接口说明
     */
    private Integer sendDataSave(Map<String, Object> dataAbutment, Map<String, Object> busDataMap, String abutDesc) {
        String dbUrl = (String) dataAbutment.get("DB_URL");
        String dbUserName = (String) dataAbutment.get("DB_USERNAME");
        String dbPass = (String) dataAbutment.get("DB_PASSWORD");
        String configXml = (String) dataAbutment.get("CONFIG_XML");
        Connection conn = null;
        Integer returnFlag =0;
        try {
            DriverManager.setLoginTimeout(5);//设置连接超时时间，5秒超时
            conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
            // 构造业务 xml
            StringBuffer buffer = new StringBuffer(); 
            log.info("busDataMap:"+busDataMap);
//            log.info("模板报文configXml:"+configXml);
            if(busDataMap!=null){ 
            buffer.append(this.makeDataXml(busDataMap, configXml));
//            log.info("数据包填充后的模板报文buffer:"+buffer.toString());
            returnFlag = this.saveOrUpdateDataToProvince(conn, busDataMap, buffer.toString());
            
            log.info("向省网办报送信息保存到省网办的数据库状态returnFlag:"+returnFlag);

            return returnFlag;
            } 
            return 0;
        } catch (Exception e) {
            log.info("保存到前置库失败："+e.getMessage());
            return 0;
            
        } finally{
                //关闭前置库数据库连接
                DbUtils.closeQuietly(conn);
            
        }
    }

    /**
     * 描述 市县区向省网办报送省市联动办件
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendSSLDBusItemsToToProvince(Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        List<Map<String, Object>> busDataList = this.testMakeData2();// this.dao.findBusBasicInfoList();
        sendDataSave(dataAbutment, busDataList, "向省网办发送省市联动办件数据");

    }

    /**
     * 描述 市县区向省网办报送办件过程信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param swbdataRes
     */
    @SuppressWarnings("unchecked")
    public void sendBusProcessToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_GCXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createBusProcessToProvince(conn, swbdataRes, instanceMap, dataAbutment,"sw");
            // 设置报文数据
             result = sendDataSave(dataAbutment, instanceMap, "向省网办发送过程信息数据");
             Map<String,Object> returnState=new HashMap<String,Object>();
             returnState.put("oper_status",result > 0 ? 1 : 2);
             //获取当前系统时间
             String oper_time=DateTimeUtil.getNowTime(null);
             returnState.put("oper_time",oper_time);
             conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
             String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
             log.info("20保存本地库状态："+saveflag);
            } catch (Exception e) {
            log.error("省网数据推送20异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送20异常错误基础数据为：", e);
        }  finally {
            DbUtils.closeQuietly(conn);
        }
       
    }
    /**
     * 描述 市县区向省网办报送办件过程信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param swbdataRes
     */
    @SuppressWarnings("unchecked")
    public void sendGcjsBusProcessToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_GCXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createBusProcessToProvince(conn, swbdataRes, instanceMap, dataAbutment,"GCJS");
            // 设置报文数据
             result = sendDataSave(dataAbutment, instanceMap, "向省网办发送过程信息数据");
             Map<String,Object> returnState=new HashMap<String,Object>();
             returnState.put("oper_status",result > 0 ? 1 : 2);
             //获取当前系统时间
             String oper_time=DateTimeUtil.getNowTime(null);
             returnState.put("oper_time",oper_time);
             conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
             String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
             log.info("20保存本地库状态："+saveflag);
            } catch (Exception e) {
            log.error("省网数据推送20异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送20异常错误基础数据为：", e);
        }  finally {
            DbUtils.closeQuietly(conn);
        }
       
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:07:00
     * @param
     * @return
     */
    public void createBusProcessToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment,String type)  {
        try {
            String unid = (String) swbdataRes.get("RES_ID");
            String exeid = (String) swbdataRes.get("EXE_ID");
            String JBPM6_EXECUTION="JBPM6_EXECUTION";
            if(type.equals("GCJS")){
                //包含归档数据
                JBPM6_EXECUTION=unionJbpmExecution();
            }
            String sqfsSql = " select e.sqfs,e.exe_id , e.create_time from "+JBPM6_EXECUTION+" e where e.exe_id = ? ";
            //conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            //Map<String, Object> sqfsMap = DbUtil.getMapBySql(conn, sqfsSql, new Object[] { exeid }, false);
            Map<String, Object> sqfsMap=dao.getByJdbc(sqfsSql, new Object[] { exeid }); 
            String serviceSql = "";
            if(type.equals("GCJS")){
                //包含归档数据
                serviceSql = this.queryNextNodeInfoSqlNew("next_node",exeid);
            }else{
                serviceSql = this.queryNextNodeInfoSql("next_node");
            }
            //String serviceSql = this.queryNextNodeInfoSqlNew("next_node");
            // 获取事项及办件相关数据
            //Map<String, Object> serviceItemMap = DbUtil.getMapBySql(conn, serviceSql, new Object[] { unid }, false);
            Map<String, Object> serviceItemMap = dao.getByJdbc(serviceSql, new Object[] { unid });
            if (serviceItemMap == null || serviceItemMap.isEmpty()) {
                 if(type.equals("GCJS")){
                     //包含归档数据
                     serviceSql = this.queryNextNodeInfoSqlNew("cur_node",exeid);
                 }else{
                     serviceSql = this.queryNextNodeInfoSql("cur_node");
                 }
                //serviceSql = this.queryNextNodeInfoSql("cur_node");
                //serviceItemMap = DbUtil.getMapBySql(conn, serviceSql, new Object[] { unid }, false);
                serviceItemMap = dao.getByJdbc( serviceSql, new Object[] { unid });
            }
            if (!(serviceItemMap == null || serviceItemMap.isEmpty())) {
                String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
                // 窗口收件、
                if ("1".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
                } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
                } else if ("3".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
                }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
                }
                // 限制同步部门
                String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
                Map<String, Object> operStatus;
               // operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);
                operStatus=dao.getByJdbc(limitOrgCodeSql, new Object[] { unid });
                String operStatusString = operStatus.get("OPER_STATUS").toString();
                if ("331".equals(operStatusString)) {
                    return;
                }
                Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_GC,
                        AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
                // 设置数据包
                Map<String, Object> apasInfo = new HashMap<String, Object>();
                Map<String, Object> node = new HashMap<String, Object>();
                bodyMap.put("apasInfo", apasInfo);
                bodyMap.put("node", node);
                node.put("code", StringUtils.isEmpty((String) serviceItemMap.get("OTHER_STATUS")) ? "3"
                        : (String) serviceItemMap.get("OTHER_STATUS"));
                node.put("name", (String) serviceItemMap.get("OTHER_NAME"));
                node.put("sName", (String) serviceItemMap.get("TASK_NODENAME"));
                node.put("nextName",
                        this.refushNodeName(StringUtils.isEmpty((String) serviceItemMap.get("TONODENAME")) ? "结束"
                                : (String) serviceItemMap.get("TONODENAME")));
                node.put("nextUser",
                        this.refushNodeName(StringUtils.isEmpty((String) serviceItemMap.get("TONAME")) ? "系统自动处理"
                                : (String) serviceItemMap.get("TONAME")));
                node.put("processedUser", (String) serviceItemMap.get("ASSIGNER_NAME"));
                /* 2019/11/28 Madison You 解决 申请时间大于受理时间 问题 */
                node.put("startTime", getNodeStartTime(sqfsMap, serviceItemMap));
                node.put("modified", (String) serviceItemMap.get("END_TIME"));
                node.put("type", getNodeOpinionType(serviceItemMap));
                if (StringUtils.isEmpty((String) serviceItemMap.get("HANDLE_OPINION"))) {
                    node.put("processedOpinion", "无意见");
                } else {
                    node.put("processedOpinion", (String) serviceItemMap.get("HANDLE_OPINION"));
                }
                node.put("promiseEndTime", (String) serviceItemMap.get("TASK_DEADTIME"));
                node.put("remark", "");
                // 单据信息目前为空
                List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
                node.put("documents", documents);
                node.put("attrs", attrs);
                // 设置分包 -----apasInfo
                apasInfo.put("SN", exeid);
                //setProcessAttrInfo(swbdataRes, dataAbutment, conn, unid, attrs);
                //先去掉原有附件内容传输
                if (!unid.contains("r191224")) {
                    setAttrInfoNew(swbdataRes, unid, conn, attrs);
                 }
               
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getMessage(),e);
        }finally{
             DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 
     * 描述 处理过程信息中的附件
     * 
     * @author Derek Zhang
     * @created 2015年12月31日 下午2:51:05
     * @param swbdataRes
     * @param dataAbutment
     * @param conn
     * @param unid
     * @param attrs
     */
    private void setProcessAttrInfo(Map<String, Object> swbdataRes, Map<String, Object> dataAbutment, Connection conn,
            String unid, List<Map<String, Object>> attrs) {
        if ("1".equals("" + swbdataRes.get("HAS_ATTR"))) {
            // 处理附件
            String attrSql = "select at.ATTR_ID,at.ATTR_CONTENT,at.FILE_PATH,at.CODE,at.NAME,"
                    + "at.SEQ_NO,at.FILE_NAME,at.FILE_MODE,at.CREADIT_FILEID  "
                    + " from t_bsfw_swbdata_attr at where at.res_id= ? and at.OPER_STATUS = 0 ";
            List<Map<String, Object>> attrList = DbUtil.findBySqlClob(conn, attrSql, new Object[] { unid });
            if (attrList != null && attrList.size() > 0) {
                for (Map<String, Object> attr : attrList) {
                    byte[] bys = null;
                    try {
                        //2021年9月16日 09:27:58 更改附件获取方式，从原来表中获取附件更改为远程下载文件服务器附件(兼容旧方式获取文件)
                        String filePath = attr.get("FILE_PATH")==null?"": attr.get("FILE_PATH").toString();
                        if(StringUtils.isNotEmpty(filePath)){
                            Properties properties = FileUtil.readProperties("project.properties");
                            String attachFileFolderPath = properties.getProperty("uploadFileUrlIn");
                            bys = FileUtil.convertUrlFileToBytes(attachFileFolderPath + filePath);
                        } else{
                            Blob blob = (Blob) attr.get("ATTR_CONTENT");
                            bys = blob.getBytes(1, (int) blob.length());
                        }
                        boolean isSendSuccess = this.sendAttrData(attr, dataAbutment, bys);
                        if (isSendSuccess) {
                            Map<String, Object> tempAttr = new HashMap<String, Object>();
                            attrs.add(tempAttr);
                            tempAttr.put("unid", (String) attr.get("ATTR_ID"));
                            tempAttr.put("mode", (String) attr.get("FILE_MODE"));
                            String creaditFileid = StringUtil.getString(attr.get("CREADIT_FILEID"));
                            if (!StringUtils.isEmpty(creaditFileid)) {
                                tempAttr.put("licenseIdentifier", creaditFileid);
                                log.info("licenseIdentifier==" + creaditFileid);
                            }
                            tempAttr.put("code", (String) attr.get("CODE"));
                            tempAttr.put("name", (String) attr.get("NAME"));
                            tempAttr.put("seqNo", "" + attr.get("SEQ_NO"));
                            tempAttr.put("fileName", (String) attr.get("FILE_NAME"));
                        }
                        String sql = "update t_bsfw_swbdata_attr at  " + " set at.oper_status = "
                                + (isSendSuccess ? 1 : 2) + " ,at.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') "
                                + " where at.attr_id = ?";
                        DbUtil.update(conn, sql, new Object[] { (String) attr.get("ATTR_ID") }, false);

                    } catch (Exception e) {
                        log.error("省网数据推送处理过程信息中的附件异常错误为：", e);
                    }
                }
            }
        }
    }

    /**
     * 
     * 描述 获得流程过程查询Sql
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午7:13:34
     * @param string
     * @return
     */
    public String queryNextNodeInfoSql(String nodeFlag) {
        String sql = "";
        //合并流程节点归档信息
        if ("cur_node".equals(nodeFlag)) {
            sql = " select n.res_id,n.data_type,n.other_status,"
                    + " decode(n.other_status,'1','预审','2','受理','3','审核','4','签发','5','缴费','6','发证（文）','审核') "
                    + " as other_name, n.task_id,n.exe_id,n.task_nodename,n.create_time,"
                    + " n.assigner_name,decode(n.task_deadtime,null,'2070-01-01',substr(n.task_deadtime,1,10)) "
                    + " as task_deadtime,n.end_time,n.handle_opinion,n.task_status,"
                    + " wm_concat(n.task_nodenameto) as tonodename,  wm_concat(n.assigner_nameto) as toname"
                    + " from ( select r.res_id,r.data_type,r.other_status,t1.task_id,t1.exe_id,"
                    + " t1.task_nodename,t1.create_time,t1.assigner_name,t1.task_deadtime,t1.end_time,"
                    + " t1.handle_opinion,t2.task_nodename as task_nodenameto,t1.task_status,"
                    + " t2.assigner_name as assigner_nameto "
                    + " from t_bsfw_swbdata_res r, jbpm6_task t1, jbpm6_task t2 "
                    + " where t2.fromtask_ids  = t1.fromtask_ids and r.exe_id = t1.exe_id "
                    + " and t1.exe_id = t2.exe_id and t1.assigner_name is not null"
                    + " and r.task_id = t1.task_id and t1.task_status in (2,3)"
                    + " and (t2.end_time is null or t1.end_time<t2.end_time)"
                    + " and t1.is_audited = 1) n  where n.data_type = '20' and n.res_id = ? "
                    + " group by n.res_id, n.data_type,n.other_status,"
                    + " n.task_id, n.exe_id, n.task_nodename, n.create_time, n.assigner_name, n.task_deadtime,"
                    + " n.end_time, n.handle_opinion,n.task_status  order by n.create_time ";
        } else if ("next_node".equals(nodeFlag)) {
            sql = " select n.res_id,n.data_type,n.other_status,"
                    + " decode(n.other_status,'1','预审','2','受理','3','审核','4','签发','5','缴费','6','发证（文）','审核') "
                    + " as other_name, n.task_id,n.exe_id,n.task_nodename,n.create_time,"
                    + " n.assigner_name,decode(n.task_deadtime,null,'2070-01-01',substr(n.task_deadtime,1,10)) "
                    + " as task_deadtime,n.end_time,n.handle_opinion,n.task_status,"
                    + " wm_concat(n.task_nodenameto) as tonodename,  wm_concat(n.assigner_nameto) as toname"
                    + " from (select r.res_id,r.data_type,r.other_status,t1.task_id,t1.exe_id,"
                    + " t1.task_nodename,t1.create_time, t1.assigner_name,t1.task_deadtime,t1.end_time,t1.task_status,"
                    + " t1.handle_opinion,t2.task_nodename as task_nodenameto,t2.assigner_name as assigner_nameto "
                    + " from t_bsfw_swbdata_res r,jbpm6_task t1, jbpm6_task t2 "
                    + " where r.exe_id = t1.exe_id  and t2.fromtask_ids like '%' || t1.task_id || '%'"
                    + " and t1.exe_id = t2.exe_id and t1.assigner_name is not null and r.task_id = t1.task_id "
                    + " and t1.task_status in (2,3) and t1.is_audited = 1 "
                    + " union select r1.res_id,r1.data_type,r1.other_status,"
                    + " t1.task_id,t1.exe_id, t1.task_nodename, t1.create_time, t1.assigner_name, t1.task_deadtime,"
                    + " t1.end_time,t1.task_status, t1.handle_opinion, '' as task_nodenameto, '' as assigner_nameto "
                    + " from t_bsfw_swbdata_res r1,jbpm6_task t1 where t1.assigner_name is not null "
                    + " and r1.exe_id = t1.exe_id and r1.task_id = t1.task_id  and t1.task_status in (2,6) "
                    + " and t1.is_audited = 1 ) n  where n.data_type = '20' and n.res_id = ? "
                    + " group by n.res_id, n.data_type,n.other_status,"
                    + " n.task_id, n.exe_id, n.task_nodename, n.create_time, n.assigner_name, n.task_deadtime,"
                    + " n.end_time, n.handle_opinion,n.task_status  order by n.create_time ";
        }
        return sql;
    }
    /**
     * 
     * 描述  获得流程过程查询Sql （包含归档数据）
     * @author Yanisin Shi
     * @param nodeFlag
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#queryNextNodeInfoSql(java.lang.String)
     */
    public String queryNextNodeInfoSqlNew(String nodeFlag,String exeid) {
        String sql = "";
        //合并流程节点归档信息
        String jbpm6_task=unionJbpmTask();
        if ("cur_node".equals(nodeFlag)) {
             sql = "with task as (select * from "+jbpm6_task+" where exe_id='"+exeid+"')"
                    +" select n.res_id,n.data_type,n.other_status,"
                    + " decode(n.other_status,'1','预审','2','受理','3','审核','4','签发','5','缴费','6','发证（文）','审核') "
                    + " as other_name, n.task_id,n.exe_id,n.task_nodename,n.create_time,"
                    + " n.assigner_name,decode(n.task_deadtime,null,'2070-01-01',substr(n.task_deadtime,1,10)) "
                    + " as task_deadtime,n.end_time,n.handle_opinion,n.task_status,"
                    + " wm_concat(n.task_nodenameto) as tonodename,  wm_concat(n.assigner_nameto) as toname"
                    + " from ( select r.res_id,r.data_type,r.other_status,t1.task_id,t1.exe_id,"
                    + " t1.task_nodename,t1.create_time,t1.assigner_name,t1.task_deadtime,t1.end_time,"
                    + " t1.handle_opinion,t2.task_nodename as task_nodenameto,t1.task_status,"
                    + " t2.assigner_name as assigner_nameto "
                    + " from t_bsfw_swbdata_res r, task t1, task t2 "
                    + " where t2.fromtask_ids  = t1.fromtask_ids and r.exe_id = t1.exe_id "
                    + " and t1.exe_id = t2.exe_id and t1.assigner_name is not null"
                    + " and r.task_id = t1.task_id and t1.task_status in (2,3)"
                    + " and (t2.end_time is null or t1.end_time<t2.end_time)"
                    + " and t1.is_audited = 1) n  where n.data_type = '20' and n.res_id = ? "
                    + " group by n.res_id, n.data_type,n.other_status,"
                    + " n.task_id, n.exe_id, n.task_nodename, n.create_time, n.assigner_name, n.task_deadtime,"
                    + " n.end_time, n.handle_opinion,n.task_status  order by n.create_time ";
        } else if ("next_node".equals(nodeFlag)) {
             sql = "with task as (select * from "+jbpm6_task+" where exe_id='"+exeid+"' )"
                    +" select n.res_id,n.data_type,n.other_status,"
                    + " decode(n.other_status,'1','预审','2','受理','3','审核','4','签发','5','缴费','6','发证（文）','审核') "
                    + " as other_name, n.task_id,n.exe_id,n.task_nodename,n.create_time,"
                    + " n.assigner_name,decode(n.task_deadtime,null,'2070-01-01',substr(n.task_deadtime,1,10)) "
                    + " as task_deadtime,n.end_time,n.handle_opinion,n.task_status,"
                    + " wm_concat(n.task_nodenameto) as tonodename,  wm_concat(n.assigner_nameto) as toname"
                    + " from (select r.res_id,r.data_type,r.other_status,t1.task_id,t1.exe_id,"
                    + " t1.task_nodename,t1.create_time, t1.assigner_name,t1.task_deadtime,t1.end_time,t1.task_status,"
                    + " t1.handle_opinion,t2.task_nodename as task_nodenameto,t2.assigner_name as assigner_nameto "
                    + " from t_bsfw_swbdata_res r,task t1, task t2 "
                    + " where r.exe_id = t1.exe_id  and t2.fromtask_ids like '%' || t1.task_id || '%'"
                    + " and t1.exe_id = t2.exe_id and t1.assigner_name is not null and r.task_id = t1.task_id "
                    + " and t1.task_status in (2,3) and t1.is_audited = 1 "
                    + " union select r1.res_id,r1.data_type,r1.other_status,"
                    + " t1.task_id,t1.exe_id, t1.task_nodename, t1.create_time, t1.assigner_name, t1.task_deadtime,"
                    + " t1.end_time,t1.task_status, t1.handle_opinion, '' as task_nodenameto, '' as assigner_nameto "
                    + " from t_bsfw_swbdata_res r1,task t1 where t1.assigner_name is not null "
                    + " and r1.exe_id = t1.exe_id and r1.task_id = t1.task_id  and t1.task_status in (2,6) "
                    + " and t1.is_audited = 1 ) n  where n.data_type = '20' and n.res_id = ? "
                    + " group by n.res_id, n.data_type,n.other_status,"
                    + " n.task_id, n.exe_id, n.task_nodename, n.create_time, n.assigner_name, n.task_deadtime,"
                    + " n.end_time, n.handle_opinion,n.task_status  order by n.create_time ";
        }
        return sql;
    }
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/11 15:45:00
     * @param
     * @return
     */
    @Override
    public String getNodeStartTime(Map<String, Object> sqfsMap, Map<String, Object> serviceItemMap) {
        String startTime = "";
        String sqfsCreateTime = (String) sqfsMap.get("CREATE_TIME");
        String nodeCreateTime = (String) serviceItemMap.get("CREATE_TIME");
        if (sqfsCreateTime != null && nodeCreateTime != null) {
            boolean flag = compareDateStr(nodeCreateTime, sqfsCreateTime);
            if (flag)
                startTime = addDateStr(sqfsCreateTime, 2000);
            else
                startTime = nodeCreateTime;
        }
        return startTime;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/11 15:49:00
     * @param
     * @return
     */
    @Override
    public String getNodeOpinionType(Map<String, Object> serviceItemMap) {
        String taskStatus = serviceItemMap.get("TASK_STATUS").toString();
        if (taskStatus.equals("2")) {
            taskStatus = "1";
        } else if (taskStatus.equals("3")) {
            taskStatus = "2";
        } else {
            taskStatus = "9";
        }
        return taskStatus;
    }

    /**
     * 
     * 描述 过滤掉环节名称中的重复数据
     * 
     * @author Derek Zhang
     * @created 2015年12月30日 下午6:34:16
     * @param string
     * @return
     */
    @SuppressWarnings("unchecked")
    public String refushNodeName(String nodeName) {
        String result = "";
        if (!StringUtils.isEmpty(nodeName)) {
            String[] nodes = nodeName.split(",");
            if (nodes.length > 1) {
                Map m = new HashMap<String, String>();
                for (String s : nodes) {
                    if (!StringUtils.isEmpty(s)) {
                        m.put(s, s);
                    }
                }
                Iterator<String> iterator = m.keySet().iterator();
                while (iterator.hasNext()) {
                    String nodetemp = iterator.next();
                    if (!StringUtils.isEmpty(nodetemp))
                        result = StringUtils.isEmpty(result) ? nodetemp : nodetemp + "," + result;
                }
            } else {
                result = nodes[0];
            }
        }
        return result;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/12 11:02:00
     * @param
     * @return
     */
    @Override
    public String getTimeStopServiceSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select n.* from (select t.*,l.link_name,l.link_basis,u.fullname,s.task_deadtime, ");
        sql.append(" s.fromtask_ids from JBPM6_HANGINFO t left join JBPM6_TASK s on s.task_id=t.task_id ");
        sql.append(" left join T_WSBS_SERVICEITEM_LINK l on l.record_id=t.link_id ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER u on u.user_id=t.userid where t.hang_Id = ? ");
        sql.append(" order by t.begin_time desc) n where rownum = 1 ");
        return sql.toString();
    }
    /**
     * 
     * 描述  合并流程节点归档信息
     * @author Yanisin Shi
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#getTimeStopServiceSqlNew()
     */
    @Override
    public String getTimeStopServiceSqlNew() {
        //合并流程节点归档信息
        String JBPM6_TASK=unionJbpmTask();
        StringBuffer sql = new StringBuffer();
        sql.append(" select n.* from (select t.*,l.link_name,l.link_basis,u.fullname,s.task_deadtime, ");
        sql.append(" s.fromtask_ids from JBPM6_HANGINFO t left join "+JBPM6_TASK+" s on s.task_id=t.task_id ");
        sql.append(" left join T_WSBS_SERVICEITEM_LINK l on l.record_id=t.link_id ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER u on u.user_id=t.userid where t.hang_Id = ? ");
        sql.append(" order by t.begin_time desc) n where rownum = 1 ");
        return sql.toString();
    }
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 10:13:00
     * @param
     * @return
     */
    @Override
    public String getResultServiceSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select n.* from (select t.*,e.bus_recordid,e.bus_tablename,e.run_status from ");
        sql.append(" jbpm6_task t  left join JBPM6_EXECUTION e on e.exe_id=t.exe_id where t.exe_id = ? ");
        sql.append(" and t.task_status in (2, 6) and t.is_real_handled = 1 and t.assigner_name is not null ");
        sql.append(" order by t.create_time desc) n where rownum = 1 ");
        return sql.toString();
    }
    /**
     * 
     * 描述  新增归档数据处理
     * @author Yanisin Shi
     * @param str
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#getResultServiceSqlNew(java.lang.String)
     */
    @Override
    public String getResultServiceSqlNew(String str,String exeid) {
        String JBPM6_EXECUTION=str;
        StringBuffer sql = new StringBuffer();
        sql.append("  with task as (select * from "+JBPM6_EXECUTION+" where exe_id='"+exeid+"' )");
        sql.append(" select n.* from (select t.*,e.bus_recordid,e.bus_tablename,e.run_status from ");
        sql.append(" (select * from view_jbpm6_task_new s where s.exe_id='"+exeid+"') t  left join task e on e.exe_id=t.exe_id where t.exe_id = ? ");
        sql.append(" and t.task_status in (2, 6) and t.is_real_handled = 1 and t.assigner_name is not null ");
        sql.append(" order by t.create_time desc) n where rownum = 1 ");
        return sql.toString();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 10:16:00
     * @param
     * @return
     */
    @Override
    public String getJgxxSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.*, t.rowid , d.USC from JBPM6_FLOW_RESULT t ");
        sql.append(" left join T_MSJW_SYSTEM_DEPARTMENT d on t.XKDEPT_ID = d.DEPART_ID where t.exe_id = ? ");
        return sql.toString();
    }
    /**
     * 
     * 描述  新增归档数据处理
     * @author Yanisin Shi
     * @param str
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#getJgxxSql(java.lang.String)
     */
    @Override
    public String getJgxxSqlNew(String str) {
        String JBPM6_FLOW_RESULT=str;
        StringBuffer sql = new StringBuffer();
        sql.append(" select t.*, d.USC from (select t.* from "+JBPM6_FLOW_RESULT+" t ");
        sql.append(" where t.exe_id = ?) t ");
        sql.append(" left join T_MSJW_SYSTEM_DEPARTMENT d on t.XKDEPT_ID = d.DEPART_ID  ");
        return sql.toString();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/16 14:58:00
     * @param
     * @return
     */
    @Override
    public String getAttrSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select at.ATTR_ID,at.ATTR_CONTENT,at.CODE,at.FILE_PATH, ");
        sql.append(" at.NAME,at.SEQ_NO,at.FILE_NAME,at.FILE_MODE,CREADIT_FILEID ");
        sql.append(" from t_bsfw_swbdata_attr at where at.res_id= ? and at.ZX_OPER_STATUS = 0 ");
        return sql.toString();
    }
    /**
     * 
     * 描述   根据时间段来推送附件
     * @author Yanisin Shi
     * @return
     * @see net.evecom.platform.wsbs.service.SwbInterfaceService#getAttrSql()
     */
    @Override
    public String getAttrSqlByTime() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select at.ATTR_ID,at.ATTR_CONTENT,at.CODE,at.FILE_PATH, ");
        sql.append(" at.NAME,at.SEQ_NO,at.FILE_NAME,at.FILE_MODE,CREADIT_FILEID ");
        sql.append(" from t_bsfw_swbdata_attr at where ");
        sql.append(" at.create_time between (select starttime from t_swb_ts_count where type='attrSendTime') "); 
        sql.append(" and (select endtime from t_swb_ts_count where type='attrSendTime' )  ");
        sql.append(" and at.ZX_OPER_STATUS = 0 and oper_status='0' and rownum<=100 order by at.create_time ");
        return sql.toString();
    }
    /**
     * 描述 市县区向省网办报送办件计时恢复信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendTimeStartToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData4();//
        // this.dao.findBusBasicInfoList();
        // sendDataSave(dataAbutment, busDataList, "向省网办发送计时恢复信息数据");
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JSHF });*/
        Connection conn = null;
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        try {
            createTimeStartToProvince(conn, swbdataRes, instanceMap, dataAbutment,"sw");
            // 设置报文数据
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送计时恢复数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("23数据保存状态"+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送23异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送23异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        } 
    }
    /**
     * 描述 市县区向省网办报送办件计时恢复信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendGcjsTimeStartToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData4();//
        // this.dao.findBusBasicInfoList();
        // sendDataSave(dataAbutment, busDataList, "向省网办发送计时恢复信息数据");
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JSHF });*/
        Connection conn = null;
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        try {
            createTimeStartToProvince(conn, swbdataRes, instanceMap, dataAbutment,"GCJS");
            // 设置报文数据
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送计时恢复数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("23数据保存状态"+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送23异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送23异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        } 
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:30:00
     * @param
     * @return
     */
    public void createTimeStartToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment,String type) throws Exception {
        String unid = (String) swbdataRes.get("RES_ID");
        String exeid = (String) swbdataRes.get("EXE_ID");
        String hangId = (String) swbdataRes.get("TASK_ID");
        //合并归档数据
        String jbpm6_execution=unionJbpmExecution();
        
        String sqfsSql = " select e.sqfs,e.exe_id,e.bus_tablename,e.bus_recordid "
                + "from "+jbpm6_execution+" e where e.exe_id = ? ";
        conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
        Map<String, Object> sqfsMap = DbUtil.getMapBySql(conn, sqfsSql, new Object[] { exeid }, false);
        String serviceSql="";
        if(type.equals("GCJS")){
            //包含归档数据
            serviceSql = this.getTimeStopServiceSqlNew();
        }else{
            serviceSql = this.getTimeStopServiceSql();
        }
        
        // 获取事项及办件相关数据
        Map<String, Object> serviceItemMap = DbUtil.getMapBySql(conn, serviceSql, new Object[] { hangId }, false);

        if (!(serviceItemMap == null || serviceItemMap.isEmpty())) {
            String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            // 窗口收件、
            if ("1".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
            }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
               
            }
            // 限制同步部门
            String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
            Map<String, Object> operStatus;
            operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);
            String operStatusString = operStatus.get("OPER_STATUS").toString();
            if ("331".equals(operStatusString)) {
                return;
            }
            Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JSHF,
                    AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
            // 设置数据包
            Map<String, Object> apasInfo = new HashMap<String, Object>();
            Map<String, Object> node = new HashMap<String, Object>();
            List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
            bodyMap.put("apasInfo", apasInfo);
            bodyMap.put("node", node);
            bodyMap.put("attrs", attrs);
            // 设置分包 -----apasInfo
            apasInfo.put("SN", exeid);
            // 设置分包 -----node
            node.put("nodeGuid", hangId);
            node.put("code", (String) swbdataRes.get("OTHER_STATUS"));
            if ("4".equals((String) swbdataRes.get("OTHER_STATUS"))) {
                node.put("name", "特殊环节结束");
                node.put("sName", serviceItemMap.get("LINK_NAME"));
                node.put("processedOpinion", serviceItemMap.get("EXPLAIN"));
            } else {
                node.put("name", "补件结束");
                node.put("sName", "收件补件");
                node.put("processedOpinion", "补件");
            }
            node.put("processedUser", serviceItemMap.get("FULLNAME"));
            node.put("processedTime", serviceItemMap.get("END_TIME"));
        }
        DbUtils.closeQuietly(conn);
    }

    /**
     * 描述 市县区向省网办报送办件计时暂停信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param swbdataRes
     */
    public void sendTimeStopToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData5();//
        // this.dao.findBusBasicInfoList();
        // sendDataSave(dataAbutment, busDataList, "向省网办发送计时暂停信息数据");
      /*  Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JSZT });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createTimeStopToProvince(conn, swbdataRes, instanceMap, dataAbutment,"sw");
            // 设置报文数据
             result = sendDataSave(dataAbutment, instanceMap, "向省网办发送计时暂停数据");
             Map<String,Object> returnState=new HashMap<String,Object>();
             returnState.put("oper_status",result > 0 ? 1 : 2);
             //获取当前系统时间
             String oper_time=DateTimeUtil.getNowTime(null);
             returnState.put("oper_time",oper_time);
             conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
             String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("21保存本地数据库状态："+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送21异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送21异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
       
        
    }
    /**
     * 描述 市县区向省网办报送办件计时暂停信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param swbdataRes
     */
    public void sendGcjsTimeStopToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData5();//
        // this.dao.findBusBasicInfoList();
        // sendDataSave(dataAbutment, busDataList, "向省网办发送计时暂停信息数据");
      /*  Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JSZT });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        Integer result=0;
        try {
            createTimeStopToProvince(conn, swbdataRes, instanceMap, dataAbutment,"GCJS");
            // 设置报文数据
             result = sendDataSave(dataAbutment, instanceMap, "向省网办发送计时暂停数据");
             Map<String,Object> returnState=new HashMap<String,Object>();
             returnState.put("oper_status",result > 0 ? 1 : 2);
             //获取当前系统时间
             String oper_time=DateTimeUtil.getNowTime(null);
             returnState.put("oper_time",oper_time);
             conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
             String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("21保存本地数据库状态："+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送21异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送21异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
       
        
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:22:00
     * @param
     * @return
     */
    public void createTimeStopToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment,String type) throws Exception {
        String unid = (String) swbdataRes.get("RES_ID");
        String exeid = (String) swbdataRes.get("EXE_ID");
        String hangId = (String) swbdataRes.get("TASK_ID");
        //合并流程表归档信息
        String JBPM6_EXECUTION=unionJbpmExecution();
        String JBPM6_TASK=unionJbpmTask();
        String sqfsSql = " select e.sqfs,e.exe_id,e.bus_tablename,e.bus_recordid "
                + "from "+JBPM6_EXECUTION+" e where e.exe_id = ? ";
        conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
        Map<String, Object> sqfsMap = DbUtil.getMapBySql(conn, sqfsSql, new Object[] { exeid }, false);
        String serviceSql = getTimeStopServiceSql();
        //String serviceSql = getTimeStopServiceSqlNew();
        // 获取事项及办件相关数据
        Map<String, Object> serviceItemMap = DbUtil.getMapBySql(conn, serviceSql, new Object[] { hangId }, false);

        if (!(serviceItemMap == null || serviceItemMap.isEmpty())) {
            String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            // 窗口收件、
            if ("1".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
            }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
               
            }
            // 限制同步部门
            String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
            Map<String, Object> operStatus;
            operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);
            String operStatusString = operStatus.get("OPER_STATUS").toString();
            if ("331".equals(operStatusString)) {
                return;
            }
            Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JSZT,
                    AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
            // 设置数据包
            Map<String, Object> apasInfo = new HashMap<String, Object>();
            Map<String, Object> node = new HashMap<String, Object>();
            List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
            bodyMap.put("apasInfo", apasInfo);
            bodyMap.put("node", node);
            bodyMap.put("attrs", attrs);
            // 设置分包 -----apasInfo
            apasInfo.put("SN", exeid);
            // 设置分包 -----node
            node.put("nodeGuid", hangId);
            node.put("code", (String) swbdataRes.get("OTHER_STATUS"));
            if ("3".equals((String) swbdataRes.get("OTHER_STATUS"))) {
                node.put("name", "特殊环节开始");
                node.put("sName", serviceItemMap.get("LINK_NAME"));
                node.put("sType", "2");
                node.put("reason", serviceItemMap.get("LINK_BASIS"));
                node.put("processedOpinion", serviceItemMap.get("LINK_NAME"));
                node.put("promiseEndTime", serviceItemMap.get("TASK_DEADTIME"));
                node.put("processedUser", serviceItemMap.get("FULLNAME"));
            } else if ("1".equals((String) swbdataRes.get("OTHER_STATUS"))) {
                node.put("name", "补件开始");
                node.put("sName", "收件补件");
                node.put("sType", "1");
                String fromTaskIds = (String) serviceItemMap.get("FROMTASK_IDS");
                
                String fromtaskSql = "select t.ASSIGNER_NAME,t.HANDLE_OPINION,t.TASK_DEADTIME "
                        + "from "+JBPM6_TASK+" t where t.task_id=? ";
                
                if (fromTaskIds != null) {
                    Map<String, Object> fromtask;
                    fromtask = DbUtil.getMapBySql(conn, fromtaskSql, new Object[] { fromTaskIds }, false);
                    if (fromtask != null) {
                        node.put("reason", fromtask.get("HANDLE_OPINION"));
                        node.put("processedOpinion", fromtask.get("HANDLE_OPINION"));
                        node.put("processedUser", fromtask.get("ASSIGNER_NAME"));
                        node.put("promiseEndTime", fromtask.get("TASK_DEADTIME"));
                    }
                }
            }
            node.put("processedTime", serviceItemMap.get("BEGIN_TIME"));
            node.put("beginTime", serviceItemMap.get("BEGIN_TIME"));
            node.put("endTime", serviceItemMap.get("LINK_END_TIME"));
        }
        DbUtils.closeQuietly(conn);
    }

    /**
     * 描述 市县区向省网办报送结果信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param swbdataRes
     */
    @SuppressWarnings("unchecked")
    public void sendResultInfoToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JGXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        try {
            createResultInfoToProvince(conn, swbdataRes, instanceMap, dataAbutment,"sw");
            // 设置报文数据
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送结果数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
             log.info("30数据saveflag:"+saveflag);
        } catch (Exception e) {
            log.error("省网办数据推送30异常错误基础数据为：" + swbdataRes);
            log.error("省网办数据推送30异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
    /**
     * 描述 市县区向省网办报送结果信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param swbdataRes
     */
    @SuppressWarnings("unchecked")
    public void sendGcjsResultInfoToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JGXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        try {
            createResultInfoToProvince(conn, swbdataRes, instanceMap, dataAbutment,"GCJS");
            // 设置报文数据
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送结果数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
             log.info("30数据saveflag:"+saveflag);
        } catch (Exception e) {
            log.error("省网办数据推送30异常错误基础数据为：" + swbdataRes);
            log.error("省网办数据推送30异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:40:00
     * @param
     * @return
     */
    public void createResultInfoToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment,String type)  {
        String unid = (String) swbdataRes.get("RES_ID");
        String exeid = (String) swbdataRes.get("EXE_ID");
        //合并归档数据
        String jbpm6_execution="jbpm6_execution";
        String jbpm6_flow_result="jbpm6_flow_result";
        if(type.equals("GCJS")){
            jbpm6_execution=unionJbpmExecution();
            jbpm6_flow_result=unionJbpmFlowResult();
        }
        String sqfsSql = " select e.sqfs,e.exe_id,e.bus_tablename,e.bus_recordid "
                + "from " + jbpm6_execution  + " e where e.exe_id = ? ";
       
             Map<String, Object> sqfsMap = dao.getByJdbc( sqfsSql.toString(), new Object[] { exeid });
             String serviceSql = "";
            if(type.equals("GCJS")){
                serviceSql = getResultServiceSqlNew(jbpm6_execution,exeid);
            }else{
                serviceSql = getResultServiceSql();
            }
            // 获取事项及办件相关数据
           // Map<String, Object> serviceItemMap = DbUtil.getMapBySql(conn, serviceSql, new Object[] { exeid }, false);
            Map<String, Object> serviceItemMap = dao.getByJdbc(serviceSql, new Object[] { exeid });
            // 获取事项及办件相关数据(增补)
            String jgxxSql = "";
            if(type.equals("GCJS")){
                jgxxSql = getJgxxSqlNew(jbpm6_flow_result);
            }else{
                jgxxSql = getJgxxSql();
            }
           // Map<String, Object> jgxxMap = DbUtil.getMapBySql(conn, jgxxSql, new Object[] { exeid }, false);
            Map<String, Object> jgxxMap =dao.getByJdbc(jgxxSql, new Object[] { exeid }); 
            if (!(serviceItemMap == null || serviceItemMap.isEmpty())) {
                String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
                // 窗口收件、
                if ("1".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
                } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
                }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
                    applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
                   
                }
                // 限制同步部门
                String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
               /* Map<String, Object> operStatus;
                operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);*/
                 Map<String, Object> operStatus = dao.getByJdbc(limitOrgCodeSql, new Object[] { unid });
                String operStatusString = operStatus.get("OPER_STATUS").toString();
                if ("331".equals(operStatusString)) {
                    return;
                }
                Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JGXX,
                        AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
                // 设置数据包
                Map<String, Object> apasInfo = new HashMap<String, Object>();
                List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> resultAttrs = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
                Map<String, Object> sms = new HashMap<String, Object>();
                bodyMap.put("apasInfo", apasInfo);
                bodyMap.put("attrs", attrs);
                bodyMap.put("resultAttrs", resultAttrs);
                bodyMap.put("documents", documents);
                bodyMap.put("sms", sms);
                // 设置分包 -----sms
                sms.put("receiver", "");
                sms.put("address", "");
                sms.put("content", "");
                // 设置分包 -----apasInfo
                if (!(jgxxMap == null || jgxxMap.isEmpty())) {
                    apasInfo.put("xkfileNum", (String) jgxxMap.get("XKFILE_NUM"));
                    apasInfo.put("closeTime", (String) jgxxMap.get("CLOSE_TIME"));
                    apasInfo.put("xkfileName", (String) jgxxMap.get("XKFILE_NAME"));
                    apasInfo.put("effectTime", (String) jgxxMap.get("EFFECT_TIME"));
                    apasInfo.put("xkdeptName", (String) jgxxMap.get("XKDEPT_NAME"));
                    apasInfo.put("xkcontent", (String) jgxxMap.get("XKCONTENT"));
                    // 证件有效期限 , 4.0新增
                    Object isLongTerm = jgxxMap.get("ISLONG_TERM");
                    String closeTime = jgxxMap.get("CLOSE_TIME") == null ? "" : (String) jgxxMap.get("CLOSE_TIME");
                    if (isLongTerm != null && isLongTerm.equals("1")) {
                        apasInfo.put("closeTime", "2099-12-31");
                    } else {
                        apasInfo.put("closeTime", closeTime);
                    }
                    // 许可机关统一社会信用代码
                    Object xkUsc = jgxxMap.get("XK_USC");
                    if (xkUsc != null) {
                        apasInfo.put("xkdeptCode", xkUsc);
                    } else {
                        String usc = jgxxMap.get("USC") == null ? "" : (String) jgxxMap.get("USC");
                        apasInfo.put("xkdeptCode", usc);
                    }
                    // 颁证时间 , 4.0新增
                    String xkdecideTime = jgxxMap.get("XKDECIDE_TIME") == null ? "" : (String) jgxxMap.get("XKDECIDE_TIME");
                    apasInfo.put("xkdecideTime", xkdecideTime);
                    // 持证者 , 4.0新增
                    String xkHolder = jgxxMap.get("XK_HOLDER") == null ? "" : (String) jgxxMap.get("XK_HOLDER");
                    apasInfo.put("xkHolder", xkHolder);
                }
                apasInfo.put("SN", exeid);
                apasInfo.put("processedUser", (String) serviceItemMap.get("ASSIGNER_NAME"));
                apasInfo.put("processedTime", (String) serviceItemMap.get("END_TIME"));

                // 投资项目公开方式必填
                String BUS_TABLENAME = serviceItemMap.get("BUS_TABLENAME") == null ? ""
                        : serviceItemMap.get("BUS_TABLENAME").toString();
                String allForm = dictionaryService.getDicCode("GCJSBD", "allForm");
                if (!StringUtils.isEmpty(BUS_TABLENAME) && allForm.contains(BUS_TABLENAME)) {
                    String primaryKeyName = (String) dao.getPrimaryKeyName(BUS_TABLENAME).get(0);
                    String BUS_RECORDID = serviceItemMap.get("BUS_RECORDID") == null ? ""
                            : serviceItemMap.get("BUS_RECORDID").toString();
                    String busInfoSql = "select t.open_way from " + BUS_TABLENAME + " t where t." + primaryKeyName + " = ?";
                    /*Map<String, Object> busInfo = DbUtil.getMapBySql(conn, busInfoSql, new Object[] { BUS_RECORDID },
                            false);*/
                    Map<String, Object> busInfo = dao.getByJdbc(busInfoSql, new Object[] { BUS_RECORDID });

                    String openWay = busInfo.get("OPEN_WAY") == null ? " " : busInfo.get("OPEN_WAY").toString();
                    apasInfo.put("openWay", openWay);
                }
 
                if (serviceItemMap.get("HANDLE_OPINION") == null) {
                    apasInfo.put("processedOpinion", "通过");
                } else {
                    apasInfo.put("processedOpinion",
                            StringUtil.nullToString((String) serviceItemMap.get("HANDLE_OPINION")));
                }
                apasInfo.put("result",
                        (StringUtil.nullToString((String) serviceItemMap.get("IS_PASS"))).equals("-1") ? "N" : "Y");
                String runStatus = serviceItemMap.get("RUN_STATUS") == null ? ""
                        : serviceItemMap.get("RUN_STATUS").toString();
                if (runStatus.equals("4")) {
                    apasInfo.put("result", "E3");
                }
                /* 快递送达结果 */

                apasInfo.put("deliveryResult", "N");
                String busTableName = (String) sqfsMap.get("BUS_TABLENAME");
                String busRecordid = (String) sqfsMap.get("BUS_RECORDID");
                DriverManager.setLoginTimeout(5);//设置连接超时时间，5秒超时
                try {
                    conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
                
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    log.info(e.getMessage(),e);
                }
                if(conn != null){
                String key = DbUtil.getPrimaryKeyName(conn, busTableName, false).get(0);
                String lqsql = "select * from " + busTableName + " where " + key + " =?";
                Map<String, Object>  record = DbUtil.getMapBySql(conn, lqsql, new Object[] { busRecordid }, false);
                if (record != null && "02".equals((String) record.get("FINISH_GETTYPE"))) {
                    apasInfo.put("deliveryResult", "Y");
                }

                apasInfo.put("makePublic", "是");
                DbUtils.closeQuietly(conn);
            }
        }
    }

    /**
     * 描述 市县区向省网办报送结果（电子证照、批文）信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendResultAttrToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData7();//
        // this.dao.findBusBasicInfoList();
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JGFJXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        try {
            createResultAttrToProvince(conn, swbdataRes, instanceMap, dataAbutment,"sw");
            // 设置报文数据
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送结果（电子证照、批文）数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("31数据saveflag："+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送31异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送31异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        } 

    }
    /**
     * 描述 市县区向省网办报送结果（电子证照、批文）信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendGcjsResultAttrToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData7();//
        // this.dao.findBusBasicInfoList();
       /* Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JGFJXX });*/
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        try {
            createResultAttrToProvince(conn, swbdataRes, instanceMap, dataAbutment,"GCJS");
            // 设置报文数据
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送结果（电子证照、批文）数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("31数据saveflag："+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送31异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送31异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        } 

    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:54:00
     * @param
     * @return
     */
    public void createResultAttrToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment,String type)  {
        String unid = (String) swbdataRes.get("RES_ID");
        String exeid = (String) swbdataRes.get("EXE_ID");
        //合并归档数据
        String jbpm6_execution="jbpm6_execution";
        String jbpm6_flow_result="jbpm6_flow_result";
        if(type.equals("GCJS")){
            jbpm6_execution=unionJbpmExecution();
            jbpm6_flow_result=unionJbpmFlowResult();
        }
        String sqfsSql = " select e.sqfs,e.exe_id,e.bus_tablename,e.bus_recordid, item_code  "
                + "from "+jbpm6_execution+" e where e.exe_id = ? ";
       // conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
        //Map<String, Object> sqfsMap = DbUtil.getMapBySql(conn, sqfsSql, new Object[] { exeid }, false);
        Map<String, Object> sqfsMap = dao.getByJdbc(sqfsSql, new Object[] { exeid });
        // 获取事项及办件结果数据
        String jgxxsString = "select t.*, d.usc from "+jbpm6_flow_result+" t "
                + "left join T_MSJW_SYSTEM_DEPARTMENT d on d.depart_id=t.xkdept_id where t.exe_id = ?";
      
        String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
        if(!(sqfsMap == null ||sqfsMap.isEmpty())){
        // 窗口收件、
        if ("1".equals((String) sqfsMap.get("SQFS"))) {
            applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
        } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
            applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
        }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
            applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
            
        }
        }
        // 限制同步部门
        String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
        //Map<String, Object> operStatus;
        //operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);
        Map<String, Object> operStatus = dao.getByJdbc(limitOrgCodeSql, new Object[] { unid });
        String operStatusString = operStatus.get("OPER_STATUS").toString();
        if ("331".equals(operStatusString)) {
            return;
        }
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JGFJ,
                AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
       
        List<Map<String, Object>> resultAttrs = new ArrayList<Map<String, Object>>();
        bodyMap.put("apasInfo", apasInfo);
        //新增结果附件20210730
        bodyMap.put("ResultAttrs",resultAttrs);
        
        //Map<String, Object> jgxxMap = DbUtil.getMapBySql(conn, jgxxsString, new Object[] { exeid }, false);
        Map<String, Object> jgxxMap = dao.getByJdbc( jgxxsString, new Object[] { exeid });
        // 设置分包 -----apasInfo , 结果信息可能为空，会导致数据中断
        if (!(jgxxMap == null || jgxxMap.isEmpty())) {
            apasInfo.put("xkfileNum", StringUtil.getValue(jgxxMap, "XKFILE_NUM"));
            apasInfo.put("closeTime", StringUtil.getValue(jgxxMap, "CLOSE_TIME"));
            apasInfo.put("xkfileName", StringUtil.getValue(jgxxMap, "XKFILE_NAME"));
            apasInfo.put("effectTime", StringUtil.getValue(jgxxMap, "EFFECT_TIME"));
            apasInfo.put("xkdeptName", StringUtil.getValue(jgxxMap, "XKDEPT_NAME"));
            // 证件有效期限 , 4.0新增
            Object isLongTerm = jgxxMap.get("ISLONG_TERM");
            String closeTime = jgxxMap.get("CLOSE_TIME") == null ? "" : (String) jgxxMap.get("CLOSE_TIME");
            if (isLongTerm != null && isLongTerm.equals("1")) {
                apasInfo.put("closeTime", "2099-12-31");
            } else {
                apasInfo.put("closeTime", closeTime);
            }
            // 许可机关统一社会信用代码
            Object xkUsc = jgxxMap.get("XK_USC");
            if (xkUsc != null) {
                apasInfo.put("xkdeptCode", xkUsc);
            } else {
                String usc = jgxxMap.get("USC") == null ? "" : (String) jgxxMap.get("USC");
                apasInfo.put("xkdeptCode", usc);
            }
            // 颁证时间 , 4.0新增
            String xkdecideTime = jgxxMap.get("XKDECIDE_TIME") == null ? "" : (String) jgxxMap.get("XKDECIDE_TIME");
            apasInfo.put("xkdecideTime", xkdecideTime);
            // 持证者 , 4.0新增
            String xkHolder = jgxxMap.get("XK_HOLDER") == null ? "" : (String) jgxxMap.get("XK_HOLDER");
            apasInfo.put("xkHolder", xkHolder);
            apasInfo.put("xkcontent", StringUtil.getValue(jgxxMap, "XKCONTENT"));
          
        }
        // 非行政类办事只有申报号必填，其余数据可为空
        apasInfo.put("SN", exeid);
        //设置审批结果附件信息数据
        if (!unid.contains("r191224")) {
          
               // setResultAttrInfo(swbdataRes, dataAbutment, unid, conn, resultAttrs,apasInfo);
            setResultAttrInfo(swbdataRes, dataAbutment, unid, resultAttrs,apasInfo);

         }

       
    }
    

    /**
     * 描述:市县区向省网办报送评价信息
     *
     * @author Madison You
     * @created 2019/10/23 14:40:00
     * @param
     * @return
     */
    public void sendEvaluationToProvince(Map<String, Object> swbdataRes) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_PJXX });
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String resId = (String) swbdataRes.get("RES_ID");
        Connection conn = null;
        try {
            createEvaluationToProvince(conn, swbdataRes, instanceMap, dataAbutment);
            Integer result = sendDataSave(dataAbutment, instanceMap, "向省网办发送评价信息数据");
            StringBuffer sql = new StringBuffer();
            sql.append(" update t_bsfw_swbdata_res r set r.oper_status = ");
            if (result > 0) {
                sql.append(1);
            } else {
                sql.append(2);
            }
            sql.append(" ,r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')  where r.res_id = ? ");
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            int resultStatue = -1;
            if(conn != null){
                resultStatue = DbUtil.update(conn, sql.toString(), new Object[] { resId }, false);
                if(resultStatue > 0){
                    log.info("省网办数据推送36成功：" + swbdataRes + "--resultStatue:" + resultStatue);
                }else{
                    log.error("省网办数据推送36失败：" + swbdataRes + "--resultStatue:" + resultStatue);                     
                }
            }else{
                log.error("省网办数据推送36数据库连接异常conn:" + conn + "swbdataRes" + swbdataRes);
            }
        } catch (Exception e) {
            log.error("省网数据推送36异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送36异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 12:03:00
     * @param
     * @return
     */
    public void createEvaluationToProvince(Connection conn, Map<String, Object> swbdataRes,
            Map<String, Object> instanceMap, Map<String, Object> dataAbutment) throws Exception {
        String exeId = (String) swbdataRes.get("EXE_ID");
        String resId = (String) swbdataRes.get("RES_ID");
        StringBuffer itemSql = new StringBuffer();
        itemSql.append(" select b.EVALUATE,b.EVALUATETIME,c.SWB_ITEM_CODE from JBPM6_EXECUTION a  ");
        itemSql.append(" left join T_CKBS_QUEUERECORD b on a.EXE_ID = b.EXE_ID ");
        itemSql.append(" left join T_WSBS_SERVICEITEM c on a.ITEM_CODE = c.ITEM_CODE ");
        itemSql.append(" where a.EXE_ID = ? ");
        conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
        Map<String, Object> itemMap = DbUtil.getMapBySql(conn, itemSql.toString(), new Object[] { exeId }, false);
        if (itemMap != null) {
            String SWB_ITEM_CODE = (String) itemMap.get("SWB_ITEM_CODE");
            String EVALUATE = (String) itemMap.get("EVALUATE");
            /* 若是没有省网事项编码，则不传评价信息 */
            if (SWB_ITEM_CODE != null && EVALUATE != null) {
                /* 评价器的信息都来源于窗口申请 */
                String applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
                Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_PJXX,
                        AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, resId);
                /* 设置数据包 */
                Map<String, Object> apasInfo = new HashMap<String, Object>();
                Map<String, Object> serviceInfo = new HashMap<>();
                Map<String, Object> evaluates = new HashMap<>();
                Map<String, Object> evaluate = new HashMap<>();
                bodyMap.put("apasInfo", apasInfo);
                bodyMap.put("serviceInfo", serviceInfo);
                bodyMap.put("evaluates", evaluates);
                apasInfo.put("SN", exeId);
                serviceInfo.put("serviceCode", SWB_ITEM_CODE);
                evaluates.put("evaluate", evaluate);
                evaluate.put("nodeName", "受理");
                evaluate.put("pf", "5");
                /* 与省网评价对应 */
                String alternate = "3";
                if (EVALUATE != null) {
                    if (EVALUATE.equals("0")) {
                        alternate = "2";
                    } else if (EVALUATE.equals("1")) {
                        alternate = "3";
                    } else if (EVALUATE.equals("2")) {
                        alternate = "4";
                    } else if (EVALUATE.equals("3")) {
                        alternate = "5";
                    }
                }
                evaluate.put("alternate", alternate);
                evaluate.put("appraisaID", "");
                evaluate.put("writingevalue", "");
                String evaluateTime = (String) itemMap.get("EVALUATETIME");
                evaluate.put("assessTime", evaluateTime);
            } else {
                StringBuffer sql1 = new StringBuffer();
                sql1.append(" update t_bsfw_swbdata_res r set r.oper_status = 2 ");
                sql1.append(" ,r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')  where r.res_id = ? ");
                DbUtil.update(conn, sql1.toString(), new Object[] { resId }, false);
                log.info("省网办推送36数据失败，无省网编码或评价：" + swbdataRes);
            }

        }
    }

    /**
     * 描述 市县区向省网办报送缴费信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendJFInfoToProvince(Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        List<Map<String, Object>> busDataList = this.testMakeData8();// this.dao.findBusBasicInfoList();
        sendDataSave(dataAbutment, busDataList, "向省网办发送缴费数据");
    }

    /**
     * 
     * 描述 向省网办发送通知缴费数据
     * 
     * @author Kester Chen
     * @created 2020年2月27日 下午3:59:49
     * @param swbdataRes
     */
    public void sendTZJFInfoToProvince(Map<String, Object> swbdataRes,Map<String, Object>dataAbutment) {
        // 获取数据库连接信息
//        List<Map<String, Object>> busDataList = this.testMakeData9();// this.dao.findBusBasicInfoList();
//        sendDataSave(dataAbutment, busDataList, "向省网办发送通知缴费数据");
        /*Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_TZJF });*/
        Connection conn = null;
        try {
            Map<String, Object> instanceMap = new HashMap<String, Object>();
            String unid = (String) swbdataRes.get("RES_ID");
            String exeid = (String) swbdataRes.get("EXE_ID");
            String sqfsSql = " select t.code,t.name,t.sname,t.processeduser,t.processedtime,  "
                    + "t.processedopinion,t.remark,sc.CHARGEDETAILCODE,sc.CHARGEDETAILNAME,"
                    + "t.id,sc.ChargeDetailOrgName,sc.ChargeDetailOrgCode,sc.Chargedetailname,"
                    + "sc.ChargeDetailCode,sc.units,sc.ChargeDetailStandard,"
                    + "t.chargedetailamount,t.chargedetailpayment from T_BSFW_PAYINFO t "
                    + "left join T_WSBS_SERVICEITEM_CHARGE sc on t.chargeid = sc.record_id where t.resid = ? ";
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            List<Map<String, Object>> sqlList = DbUtil.findBySql(conn, sqfsSql, new Object[] { unid }, false);

            Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_TZJF,
                    AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_PROWS, unid);
            // 设置数据包
            Map<String, Object> apasInfo = new HashMap<String, Object>();
            Map<String, Object> node = new HashMap<String, Object>();
            bodyMap.put("apasInfo", apasInfo);
            bodyMap.put("node", node);
            apasInfo.put("SN", exeid);
            Map<String, Object> nodeInfo = sqlList.get(0);
            node.put("code", nodeInfo.get("CODE"));
            node.put("name", nodeInfo.get("NAME"));
            node.put("sName", nodeInfo.get("SNAME"));
            node.put("processedUser", nodeInfo.get("PROCESSEDUSER"));
            node.put("processedTime", nodeInfo.get("PROCESSEDTIME"));
            node.put("processedOpinion", nodeInfo.get("PROCESSEDOPINION"));
            node.put("beginTime",
                    nodeInfo.get("BEGINTIME") == null ? DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss")
                            : nodeInfo.get("BEGINTIME"));
            node.put("endTime",
                    nodeInfo.get("ENDTIME") == null ? DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss")
                            : nodeInfo.get("ENDTIME"));
            node.put("remark", nodeInfo.get("REMARK"));
            node.put("chargeCfgName", nodeInfo.get("CHARGEDETAILNAME"));
            node.put("chargeCfgCode", nodeInfo.get("CHARGEDETAILCODE"));

            List<Map<String, Object>> chargeDetails = new ArrayList<Map<String, Object>>();
            node.put("chargeDetails", chargeDetails);

            if (sqlList != null && sqlList.size() > 0) {
                for (Map<String, Object> ChargeDetail : sqlList) {
                    try {
                        Map<String, Object> tempChargeDetail = new HashMap<String, Object>();
                        chargeDetails.add(tempChargeDetail);
                        tempChargeDetail.put("chargeDetailKey", (String) ChargeDetail.get("ID"));
                        tempChargeDetail.put("chargeDetailOrgName", (String) ChargeDetail.get("CHARGEDETAILORGNAME"));
                        tempChargeDetail.put("chargeDetailOrgCode", (String) ChargeDetail.get("CHARGEDETAILORGCODE"));
                        tempChargeDetail.put("chargeDetailName", (String) ChargeDetail.get("CHARGEDETAILNAME"));
                        tempChargeDetail.put("chargeDetailCode", (String) ChargeDetail.get("CHARGEDETAILCODE"));
                        tempChargeDetail.put("chargeDetailUnit", (String) ChargeDetail.get("UNITS"));
                        tempChargeDetail.put("chargeDetailStandard", (String) ChargeDetail.get("CHARGEDETAILSTANDARD"));
                        tempChargeDetail.put("chargeDetailAmount", (String) ChargeDetail.get("CHARGEDETAILAMOUNT"));
                        tempChargeDetail.put("chargeDetailPayment", (String) ChargeDetail.get("CHARGEDETAILPAYMENT"));

                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                }
            }
            Integer result = 0;
            // 设置报文数据
            result = sendDataSave(dataAbutment, instanceMap, "向省网办发送过程信息数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
            log.info("24数据saveflag："+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送24异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送24异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        } 
    }
    /**
     * 
     * 描述 向省网办发送通知缴费数据
     * 
     * @author Kester Chen
     * @created 2020年2月27日 下午3:59:49
     * @param swbdataRes
     */
    public void sendGcjsTZJFInfoToProvince(Map<String, Object> swbdataRes,Map<String, Object>dataAbutment) {
        // 获取数据库连接信息
//        List<Map<String, Object>> busDataList = this.testMakeData9();// this.dao.findBusBasicInfoList();
//        sendDataSave(dataAbutment, busDataList, "向省网办发送通知缴费数据");
        /*Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_TZJF });*/
        Connection conn = null;
        try {
            Map<String, Object> instanceMap = new HashMap<String, Object>();
            String unid = (String) swbdataRes.get("RES_ID");
            String exeid = (String) swbdataRes.get("EXE_ID");
            String sqfsSql = " select t.code,t.name,t.sname,t.processeduser,t.processedtime,  "
                    + "t.processedopinion,t.remark,sc.CHARGEDETAILCODE,sc.CHARGEDETAILNAME,"
                    + "t.id,sc.ChargeDetailOrgName,sc.ChargeDetailOrgCode,sc.Chargedetailname,"
                    + "sc.ChargeDetailCode,sc.units,sc.ChargeDetailStandard,"
                    + "t.chargedetailamount,t.chargedetailpayment from T_BSFW_PAYINFO t "
                    + "left join T_WSBS_SERVICEITEM_CHARGE sc on t.chargeid = sc.record_id where t.resid = ? ";
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            List<Map<String, Object>> sqlList = DbUtil.findBySql(conn, sqfsSql, new Object[] { unid }, false);

            Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_TZJF,
                    AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_PROWS, unid);
            // 设置数据包
            Map<String, Object> apasInfo = new HashMap<String, Object>();
            Map<String, Object> node = new HashMap<String, Object>();
            bodyMap.put("apasInfo", apasInfo);
            bodyMap.put("node", node);
            apasInfo.put("SN", exeid);
            Map<String, Object> nodeInfo = sqlList.get(0);
            node.put("code", nodeInfo.get("CODE"));
            node.put("name", nodeInfo.get("NAME"));
            node.put("sName", nodeInfo.get("SNAME"));
            node.put("processedUser", nodeInfo.get("PROCESSEDUSER"));
            node.put("processedTime", nodeInfo.get("PROCESSEDTIME"));
            node.put("processedOpinion", nodeInfo.get("PROCESSEDOPINION"));
            node.put("beginTime",
                    nodeInfo.get("BEGINTIME") == null ? DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss")
                            : nodeInfo.get("BEGINTIME"));
            node.put("endTime",
                    nodeInfo.get("ENDTIME") == null ? DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss")
                            : nodeInfo.get("ENDTIME"));
            node.put("remark", nodeInfo.get("REMARK"));
            node.put("chargeCfgName", nodeInfo.get("CHARGEDETAILNAME"));
            node.put("chargeCfgCode", nodeInfo.get("CHARGEDETAILCODE"));

            List<Map<String, Object>> chargeDetails = new ArrayList<Map<String, Object>>();
            node.put("chargeDetails", chargeDetails);

            if (sqlList != null && sqlList.size() > 0) {
                for (Map<String, Object> ChargeDetail : sqlList) {
                    try {
                        Map<String, Object> tempChargeDetail = new HashMap<String, Object>();
                        chargeDetails.add(tempChargeDetail);
                        tempChargeDetail.put("chargeDetailKey", (String) ChargeDetail.get("ID"));
                        tempChargeDetail.put("chargeDetailOrgName", (String) ChargeDetail.get("CHARGEDETAILORGNAME"));
                        tempChargeDetail.put("chargeDetailOrgCode", (String) ChargeDetail.get("CHARGEDETAILORGCODE"));
                        tempChargeDetail.put("chargeDetailName", (String) ChargeDetail.get("CHARGEDETAILNAME"));
                        tempChargeDetail.put("chargeDetailCode", (String) ChargeDetail.get("CHARGEDETAILCODE"));
                        tempChargeDetail.put("chargeDetailUnit", (String) ChargeDetail.get("UNITS"));
                        tempChargeDetail.put("chargeDetailStandard", (String) ChargeDetail.get("CHARGEDETAILSTANDARD"));
                        tempChargeDetail.put("chargeDetailAmount", (String) ChargeDetail.get("CHARGEDETAILAMOUNT"));
                        tempChargeDetail.put("chargeDetailPayment", (String) ChargeDetail.get("CHARGEDETAILPAYMENT"));

                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                }
            }
            Integer result = 0;
            // 设置报文数据
            result = sendDataSave(dataAbutment, instanceMap, "向省网办发送过程信息数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
            log.info("24数据saveflag："+saveflag);
        } catch (Exception e) {
            log.error("省网数据推送24异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送24异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        } 
    }

    /**
     * 描述 市县区向省网办报送补件信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendBJInfoToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData10();//
        // this.dao.findBusBasicInfoList();
        // sendDataSave(dataAbutment, busDataList, "向省网办发送补件数据");
        /*Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_BJXX });*/
        Connection conn = null;
        Integer result = 0;
        try {
            
            //合并归档数据
            String jbpm6_execution=unionJbpmExecution();
            String JBPM6_TASK=unionJbpmTask();
            
            Map<String, Object> instanceMap = new HashMap<String, Object>();
            String unid = (String) swbdataRes.get("RES_ID");
            String taskId = (String) swbdataRes.get("TASK_ID");
            String exeid = (String) swbdataRes.get("EXE_ID");
            String sqfsSql = " select e.sqfs,e.exe_id,e.bus_tablename,e.bus_recordid "
                    + "from "+jbpm6_execution+" e where e.exe_id = ? ";
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            Map<String, Object> sqfsMap = DbUtil.getMapBySql(conn, sqfsSql, new Object[] { exeid }, false);

            // 获取补件数据
            String hangString = "select t.* from JBPM6_HANGINFO t where t.hang_id=?";
            Map<String, Object> hangMap = DbUtil.getMapBySql(conn, hangString, new Object[] { taskId }, false);
            String hangTaskString = "select t.* from "+JBPM6_TASK+" t where t.task_id=?";
            Map<String, Object> hangTask = DbUtil.getMapBySql(conn, hangTaskString,
                    new Object[] { hangMap.get("TASK_ID") }, false);
//            String bjxxsql = "select t.* from T_WSBS_BJXX t where t.task_id=? and t.exe_id=?";
//            Map<String, Object> bjxxMap = DbUtil
//                    .getMapBySql(conn, bjxxsql, new Object[] { hangTask.get("FROMTASK_IDS"), exeid}, false);
            String bjxxString = "select t.*,s.ASSIGNER_NAME from T_WSBS_BJXX t left join "+JBPM6_TASK+" s "
                    + "on s.task_id = t.task_id where t.task_id=? and t.exe_id=? ";
//            Map<String, Object> bjxxMap = DbUtil
//                    .getMapBySql(conn, bjxxString, new Object[] { taskId, exeid }, false);
            Map<String, Object> bjxxMap = DbUtil.getMapBySql(conn, bjxxString,
                    new Object[] { hangTask.get("FROMTASK_IDS"), exeid }, false);
            // 获取点击“退回补件”按钮的环节办理数据
            Map<String, Object> toBackMap = DbUtil.getMapBySql(conn, hangTaskString,
                    new Object[] { hangTask.get("FROMTASK_IDS") }, false);
            String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            // 窗口收件、
            if ("1".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
            }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
               
            }
            // 限制同步部门
            String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
            Map<String, Object> operStatus;
            operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);
            String operStatusString = operStatus.get("OPER_STATUS").toString();
            if ("331".equals(operStatusString)) {
                return;
            }
            Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_BJXX,
                    AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
            // 设置数据包
            Map<String, Object> apasInfo = new HashMap<String, Object>();
            Map<String, Object> node = new HashMap<String, Object>();
            List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
            bodyMap.put("apasInfo", apasInfo);
            bodyMap.put("node", node);
            bodyMap.put("attrs", attrs);
            apasInfo.put("SN", exeid);
            if ("2".equals((String) swbdataRes.get("OTHER_STATUS"))) {
                apasInfo.put("type", "受理补件");
                node.put("sName", "受理补件");
            } else {
                apasInfo.put("type", "收件补件");
                node.put("sName", "收件补件");
            }

            node.put("nodeGuid", hangMap.get("HANG_ID"));
            node.put("code", 2);
            node.put("name", "补件恢复");
            node.put("sType", "1");

            // 部分补件表没有数据为代码bug导致，取做“退回补件”的操作环节的办结时间作为补件表数据
            if (bjxxMap != null && bjxxMap.size() > 0) {
                node.put("processedUser", bjxxMap.get("ASSIGNER_NAME"));
                node.put("processedOpinion", bjxxMap.get("BJYJ"));
                node.put("processedTime", bjxxMap.get("CREATE_TIME"));
            } else {
                node.put("processedUser", StringUtil.getValue(toBackMap, "ASSIGNER_NAME"));
                node.put("processedOpinion", StringUtil.getValue(toBackMap, "HANDLE_OPINION"));
                node.put("processedTime", StringUtil.getValue(toBackMap, "END_TIME"));
            }
            //setAttrInfo(swbdataRes, dataAbutment, unid, conn, attrs);
            if (!unid.contains("r191224")) {
                setAttrInfoNew(swbdataRes, unid, conn, attrs);
             }
            // 设置报文数据
            result = sendDataSave(dataAbutment, instanceMap, "向省网办发送补件信息数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("22数据保存状态："+saveflag);
        } catch (SQLException e) {
            log.error("省网数据推送22异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送22异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
       
    }
    /**
     * 描述 市县区向省网办报送补件信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendGcjsBJInfoToProvince(Map<String, Object> swbdataRes,Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        // List<Map<String, Object>> busDataList = this.testMakeData10();//
        // this.dao.findBusBasicInfoList();
        // sendDataSave(dataAbutment, busDataList, "向省网办发送补件数据");
        /*Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_BJXX });*/
        Connection conn = null;
        Integer result = 0;
        try {
            
            //合并归档数据
            String jbpm6_execution=unionJbpmExecution();
            String JBPM6_TASK=unionJbpmTask();
            
            Map<String, Object> instanceMap = new HashMap<String, Object>();
            String unid = (String) swbdataRes.get("RES_ID");
            String taskId = (String) swbdataRes.get("TASK_ID");
            String exeid = (String) swbdataRes.get("EXE_ID");
            String sqfsSql = " select e.sqfs,e.exe_id,e.bus_tablename,e.bus_recordid "
                    + "from "+jbpm6_execution+" e where e.exe_id = ? ";
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            Map<String, Object> sqfsMap = DbUtil.getMapBySql(conn, sqfsSql, new Object[] { exeid }, false);

            // 获取补件数据
            String hangString = "select t.* from JBPM6_HANGINFO t where t.hang_id=?";
            Map<String, Object> hangMap = DbUtil.getMapBySql(conn, hangString, new Object[] { taskId }, false);
            String hangTaskString = "select t.* from "+JBPM6_TASK+" t where t.task_id=?";
            Map<String, Object> hangTask = DbUtil.getMapBySql(conn, hangTaskString,
                    new Object[] { hangMap.get("TASK_ID") }, false);
//            String bjxxsql = "select t.* from T_WSBS_BJXX t where t.task_id=? and t.exe_id=?";
//            Map<String, Object> bjxxMap = DbUtil
//                    .getMapBySql(conn, bjxxsql, new Object[] { hangTask.get("FROMTASK_IDS"), exeid}, false);
            String bjxxString = "select t.*,s.ASSIGNER_NAME from T_WSBS_BJXX t left join "+JBPM6_TASK+" s "
                    + "on s.task_id = t.task_id where t.task_id=? and t.exe_id=? ";
//            Map<String, Object> bjxxMap = DbUtil
//                    .getMapBySql(conn, bjxxString, new Object[] { taskId, exeid }, false);
            Map<String, Object> bjxxMap = DbUtil.getMapBySql(conn, bjxxString,
                    new Object[] { hangTask.get("FROMTASK_IDS"), exeid }, false);
            // 获取点击“退回补件”按钮的环节办理数据
            Map<String, Object> toBackMap = DbUtil.getMapBySql(conn, hangTaskString,
                    new Object[] { hangTask.get("FROMTASK_IDS") }, false);
            String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            // 窗口收件、
            if ("1".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
            } else if ("2".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
            }else if ("4".equals((String) sqfsMap.get("SQFS"))) {
                applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
               
            }
            // 限制同步部门
            String limitOrgCodeSql = "select t.oper_status from T_BSFW_SWBDATA_RES t where t.res_id = ?";
            Map<String, Object> operStatus;
            operStatus = DbUtil.getMapBySql(conn, limitOrgCodeSql, new Object[] { unid }, false);
            String operStatusString = operStatus.get("OPER_STATUS").toString();
            if ("331".equals(operStatusString)) {
                return;
            }
            Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_BJXX,
                    AllConstant.SWB_DATA_OPERATOR_INSERT, applyFrom, unid);
            // 设置数据包
            Map<String, Object> apasInfo = new HashMap<String, Object>();
            Map<String, Object> node = new HashMap<String, Object>();
            List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
            bodyMap.put("apasInfo", apasInfo);
            bodyMap.put("node", node);
            bodyMap.put("attrs", attrs);
            apasInfo.put("SN", exeid);
            if ("2".equals((String) swbdataRes.get("OTHER_STATUS"))) {
                apasInfo.put("type", "受理补件");
                node.put("sName", "受理补件");
            } else {
                apasInfo.put("type", "收件补件");
                node.put("sName", "收件补件");
            }

            node.put("nodeGuid", hangMap.get("HANG_ID"));
            node.put("code", 2);
            node.put("name", "补件恢复");
            node.put("sType", "1");

            // 部分补件表没有数据为代码bug导致，取做“退回补件”的操作环节的办结时间作为补件表数据
            if (bjxxMap != null && bjxxMap.size() > 0) {
                node.put("processedUser", bjxxMap.get("ASSIGNER_NAME"));
                node.put("processedOpinion", bjxxMap.get("BJYJ"));
                node.put("processedTime", bjxxMap.get("CREATE_TIME"));
            } else {
                node.put("processedUser", StringUtil.getValue(toBackMap, "ASSIGNER_NAME"));
                node.put("processedOpinion", StringUtil.getValue(toBackMap, "HANDLE_OPINION"));
                node.put("processedTime", StringUtil.getValue(toBackMap, "END_TIME"));
            }
            //setAttrInfo(swbdataRes, dataAbutment, unid, conn, attrs);
            if (!unid.contains("r191224")) {
                setAttrInfoNew(swbdataRes, unid, conn, attrs);
             }
            // 设置报文数据
            result = sendDataSave(dataAbutment, instanceMap, "向省网办发送补件信息数据");
            Map<String,Object> returnState=new HashMap<String,Object>();
            returnState.put("oper_status",result > 0 ? 1 : 2);
            //获取当前系统时间
            String oper_time=DateTimeUtil.getNowTime(null);
            returnState.put("oper_time",oper_time);
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            String saveflag= DbUtil.saveOrUpdate(conn, "T_BSFW_SWBDATA_RES", returnState, unid, false);
           log.info("22数据保存状态："+saveflag);
        } catch (SQLException e) {
            log.error("省网数据推送22异常错误基础数据为：" + swbdataRes);
            log.error("省网数据推送22异常错误基础数据为：", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
       
    }

    /**
     * 描述 市县区向省网办报送评议信息
     * 
     * @author Derek Zhang
     * @created 2015年10月21日 下午1:28:08
     * @param dataAbutment
     */
    public void sendPYXXToProvince(Map<String, Object> dataAbutment) {
        // 获取数据库连接信息
        List<Map<String, Object>> busDataList = this.testMakeData11();// this.dao.findBusBasicInfoList();
        sendDataSave(dataAbutment, busDataList, "向省网办发送评议信息数据");
    }

    /**
     * 
     * 描述 构造测试用的接口数据
     * 
     * @author Derek Zhang
     * @created 2015年10月23日 上午10:58:44
     * @return
     */
    private List<Map<String, Object>> testMakeData2() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_BJXX,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_SSLD, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> proposer = new HashMap<String, Object>();
        Map<String, Object> operator = new HashMap<String, Object>();
        List<Map<String, Object>> expresses = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
        bodyMap.put("apasInfo", apasInfo);
        bodyMap.put("proposer", proposer);
        bodyMap.put("operator", operator);
        bodyMap.put("expresses", expresses);
        // bodyMap.put("formInfos", formInfos);
        bodyMap.put("attrs", attrs);
        bodyMap.put("documents", documents);
        // 设置分包 -----attrs
        Map<String, Object> attr1 = new HashMap<String, Object>();
        Map<String, Object> attr2 = new HashMap<String, Object>();
        attrs.add(attr1);
        attrs.add(attr2);
        attr1.put("unid", "0221EA7F80A19B8C932CE994AD8B756D");
        attr1.put("mode", "paper");
        attr1.put("code", "003591109XK00219_01");
        attr1.put("name", "《道路运输证》补办审请表");
        attr1.put("seqNo", "1");
        attr1.put("fileName", "《道路运输证》补办审请表.excel");
        attr2.put("unid", "DBAE37A6A2BCBC6A0E542A7F1CD98FAB");
        attr2.put("mode", "paper");
        attr2.put("code", "003591109XK00219_02");
        attr2.put("name", "身份证复印件");
        attr2.put("seqNo", "2");
        attr2.put("fileName", "身份证复印件.doc");
        // 设置分包 -----operator
        operator.put("name", "马云");
        operator.put("sex", "男");
        operator.put("birthday", "1975-03-06");
        operator.put("certificateType", "SF");
        operator.put("certificateNumber", "23020219750306039X");
        operator.put("address", "仓山区蒲上大道106号 马云家");
        operator.put("postcode", "350000");
        operator.put("mobilePhone", "18900009000");
        operator.put("tel", "0591-32980098");
        operator.put("identificationStatus", "已实名认证");
        // 设置分包 -----proposer
        proposer.put("applyType", "0");
        proposer.put("areaCode", "350000");
        Map<String, Object> person = new HashMap<String, Object>();
        proposer.put("person", person);
        person.put("applyName", "张三");
        person.put("sex", "男");
        person.put("birthday", "1967-09-08");
        person.put("certificateType", "SF");
        person.put("certificateNumber", "350202196709080394");
        person.put("address", "张三家地址不知道");
        person.put("postcode", "350000");
        person.put("mobilePhone", "18900009001");
        person.put("tel", "059132980097");
        person.put("identificationStatus", "已实名认证");
        Map<String, Object> unit = new HashMap<String, Object>();
        proposer.put("unit", unit);
        unit.put("unitName", "李四");
        unit.put("unitCode", "743817927");
        unit.put("unitType", "SYDW");
        unit.put("lealPerson", "张三");
        unit.put("address", "仓山区蒲上大道106号 马云家");
        unit.put("tel", "059132980098");
        unit.put("postcode", "350000");
        unit.put("identificationStatus", "已实名认证");
        // 设置分包 -----apasInfo
        apasInfo.put("serviceID", "34790162591079CDE0E0276AD3EB045B");
        apasInfo.put("serviceCode", "003591109XK00219");
        apasInfo.put("serviceName", "货物运输车辆《道路运输证》遗失补办");
        apasInfo.put("areaCode", "350000");
        apasInfo.put("projectName", "省级模拟对接测试事项测试");
        apasInfo.put("promiseDay", "1");
        apasInfo.put("SN", "ptsy02115102900009");
        apasInfo.put("PWD", "183A240");
        apasInfo.put("declareTime", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        apasInfo.put("receiveDeptName", "平潭综合实验区交通与建设局");
        apasInfo.put("receiveDeptCode", "577012559");
        return list;
    }

    /**
     * <!-- applyFrom：办件来源。枚举类型，取值为: 1、省网上办事大厅产生的办件:窗口申请：10, 网上申请：11, 移动端申请：12,
     * 省发起的并联审批：13 2、各市县区行政服务中心或省直部门业务系统产生的办件:窗口申请：20, 网上申请:21, 移动端申请：22,
     * 市县发起的并联审批：23, 省市县联动：24
     * 
     * type：xml信息类型。枚举类型，取值为数字“事项信息=100，办件信息=10，过程信息=20，计时暂停=21，补件信息=22，计时恢复=23，
     * 通知缴费=24，缴费信息=25，结果信息=30,结果信息（电子证照、批文）=31，评议信息=40，反馈信息=99” -->
     */
    // 过程数据
    @SuppressWarnings("unused")
    private List<Map<String, Object>> testMakeData3() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_GC,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_CITYWS, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> node = new HashMap<String, Object>();
        bodyMap.put("apasInfo", apasInfo);
        bodyMap.put("node", node);
        node.put("code", "2");
        node.put("name", "签发");
        node.put("sName", "区民政领导审批");
        node.put("nextName", "区民政领导签发");
        node.put("nextUser", "李厅长");
        node.put("processedUser", "李局长");
        node.put("processedTime", "2015-10-24 10:31:13");
        node.put("processedOpinion", "省级模拟对接测试事项测试材料符合要求，审核通过");
        node.put("promiseEndTime", "2015-10-30");
        node.put("remark", "");
        List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
        node.put("documents", documents);
        node.put("attrs", attrs);

        // 设置分包 -----documents
        Map<String, Object> document1 = new HashMap<String, Object>();
        Map<String, Object> document2 = new HashMap<String, Object>();
        documents.add(document1);
        documents.add(document2);
        document1.put("unid", "EDFD71C4E8D743C4803706E4DA4F6219");
        document1.put("type", "1");
        document1.put("name", "省级模拟对接测试事项测试受理通知书");
        document1.put("docNo", "闽招办【2015】0008号");
        document2.put("unid", "884CB67523AA9AA378CCCB0A90215936");
        document2.put("type", "2");
        document2.put("name", "省级模拟对接测试事项测试收件通知书");
        document2.put("docNo", "闽测办【2015】0010号");

        // 设置分包 -----attrs
        Map<String, Object> attr1 = new HashMap<String, Object>();
        Map<String, Object> attr2 = new HashMap<String, Object>();
        attrs.add(attr1);
        attrs.add(attr2);
        attr1.put("unid", "0221EA7F80A19B8C914CE994AD8B756D");
        attr1.put("mode", "upload");
        attr1.put("code", "577012559FK07968_01");
        attr1.put("name", "省级模拟对接测试事项测试计划表");
        attr1.put("seqNo", "1");
        attr1.put("fileName", "省级模拟对接测试事项测试计划表.excel");
        attr2.put("unid", "DBAE37A6A2BCBC6A0EBFEA7F1CD98FAB");
        attr2.put("mode", "upload");
        attr2.put("code", "577012559FK07968_02");
        attr2.put("name", "省级模拟对接测试事项测试范围说明");
        attr2.put("seqNo", "2");
        attr2.put("fileName", "省级模拟对接测试事项测试范围说明.doc");
        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy03115102900005");
        return list;
    }

    // 计时恢复
    private List<Map<String, Object>> testMakeData4() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JSHF,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_CITYWS, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> node = new HashMap<String, Object>();
        bodyMap.put("apasInfo", apasInfo);
        bodyMap.put("node", node);
        node.put("code", "2");
        node.put("name", "补件结束");
        node.put("sName", "收件补件");
        node.put("processedUser", "李科长");
        node.put("processedTime", "2015-10-28 10:13:54");
        node.put("processedOpinion", "省级模拟对接测试事项测试材料符合要求，审核通过");
        node.put("actualTime", "2015-10-28");
        node.put("promiseEndTime", "2015-10-30");
        node.put("remark", "");
        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy03115102900005");
        return list;
    }

    // 计时暂停
    private List<Map<String, Object>> testMakeData5() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        String unid = this.getUNID();
        list.add(instanceMap);
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JSZT,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_CITYWS, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> node = new HashMap<String, Object>();
        bodyMap.put("apasInfo", apasInfo);
        bodyMap.put("node", node);
        node.put("code", "1");
        node.put("name", "补件开始");
        node.put("sName", "收件补件");
        node.put("processedUser", "张科长");
        node.put("processedTime", "2015-10-25 15:16:46");
        node.put("processedOpinion", "省级模拟对接测试事项测试材料符合要求，审核通过");
        node.put("beginTime", "2015-10-26");
        node.put("endTime", "2015-10-27");
        node.put("promiseEndTime", "2015-10-30");
        node.put("remark", "");
        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy03115102900005");
        return list;
    }

    // 结果附件--证照
    private List<Map<String, Object>> testMakeData7() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JGFJ,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_CITYWS, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        List<Map<String, Object>> resultAttrs = new ArrayList<Map<String, Object>>();
        // List<Map<String, Object>> documents = new ArrayList<Map<String,
        // Object>>();
        Map<String, Object> sms = new HashMap<String, Object>();
        bodyMap.put("apasInfo", apasInfo);
        bodyMap.put("resultAttrs", resultAttrs);
        bodyMap.put("sms", sms);
        // 设置分包 -----sms
        sms.put("receiver", "测试1");
        sms.put("address", "仓山区蒲上大道108号");
        sms.put("content", "通知15个工作日后登录公众服务平台查询，查询码为：183A240");

        // 设置分包 -----attrs
        Map<String, Object> attr1 = new HashMap<String, Object>();
        Map<String, Object> attr2 = new HashMap<String, Object>();
        resultAttrs.add(attr1);
        resultAttrs.add(attr2);
        attr1.put("unid", "0221EA7F80A19B8C914CE994AD8B756D");
        attr1.put("attrCategory", "审批结果附件");
        attr1.put("attrName", "省级模拟对接测试事项测试计划表");
        attr1.put("attrNumber", "Kj0018DL01902");

        attr2.put("unid", "DBAE37A6A2BCBC6A0EBFEA7F1CD98FAB");
        attr2.put("attrCategory", "审批结果附件");
        attr2.put("attrName", "省级模拟对接测试事项测试资格证");
        attr2.put("attrNumber", "Kj0018DL01903");

        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy03115102900005");
        return list;
    }

    // 缴费信息
    private List<Map<String, Object>> testMakeData8() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_JFXX,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_PROBL, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> node = new HashMap<String, Object>();
        bodyMap.put("node", node);
        bodyMap.put("apasInfo", apasInfo);
        node.put("code", 4);
        node.put("name", "缴费");
        node.put("chargeName", "制作费");
        node.put("orderNumber", "299029082DD0001");
        node.put("deptAccount", "302299029082");
        node.put("paymentAmount", "52.04");
        node.put("currencyType", "人民币");
        node.put("paymentState", "支付成功");
        node.put("paymentTime", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        node.put("paymentBank", "建设银行");
        node.put("paymentRemark", "");
        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy01115102100001");
        return list;
    }

    // 通知缴费信息
    private List<Map<String, Object>> testMakeData9() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_TZJF,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_CITYWS, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        Map<String, Object> node = new HashMap<String, Object>();
        bodyMap.put("node", node);
        bodyMap.put("apasInfo", apasInfo);
        node.put("code", 4);
        node.put("name", "缴费");
        node.put("chargeName", "制作费");
        node.put("charge", 50.00);
        node.put("deptAccount", "6227001804391349057");
        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy01115102100001");
        return list;
    }

    // 补件信息
    private List<Map<String, Object>> testMakeData10() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_BJXX,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_PROBL, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();
        List<Map<String, Object>> attrs = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
        bodyMap.put("attrs", attrs);
        bodyMap.put("documents", documents);
        // 设置分包 -----documents
        Map<String, Object> document1 = new HashMap<String, Object>();
        Map<String, Object> document2 = new HashMap<String, Object>();
        documents.add(document1);
        documents.add(document2);
        document1.put("unid", "EDFD71C4E8D743C4803706E4DA401001");
        document1.put("type", "1");
        document1.put("name", "测试1");
        document1.put("docNo", "闽运办【2015】0008号");
        document2.put("unid", "EDFD71C4E8D743C4803706E4DA401002");
        document2.put("type", "2");
        document2.put("name", "测试2");
        document2.put("docNo", "闽运办【2015】0010号");

        // 设置分包 -----attrs
        Map<String, Object> attr1 = new HashMap<String, Object>();
        Map<String, Object> attr2 = new HashMap<String, Object>();
        attrs.add(attr1);
        attrs.add(attr2);
        attr1.put("unid", "EDFD71C4E8D74321206E4DA401003");
        attr1.put("mode", "upload");
        attr1.put("code", "402883fa50827df00003");
        attr1.put("name", "测试3");
        attr1.put("seqNo", "1");
        attr1.put("fileName", "《道路运输证》补办审请表.excel");
        attr2.put("unid", "EDFD71C4E8D74321206E4DA401004");
        attr2.put("mode", "upload");
        attr2.put("code", "402883fa50827df00001");
        attr2.put("name", "测试4");
        attr2.put("seqNo", "2");
        attr2.put("fileName", "身份证复印件.doc");
        bodyMap.put("apasInfo", apasInfo);

        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy01315102800002");
        apasInfo.put("type", "补齐补正");
        return list;
    }

    // 评议信息
    private List<Map<String, Object>> testMakeData11() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> instanceMap = new HashMap<String, Object>();
        list.add(instanceMap);
        String unid = this.getUNID();
        Map<String, Object> bodyMap = mackCaseMap(instanceMap, AllConstant.SWB_DATA_TYPE_PYXX,
                AllConstant.SWB_DATA_OPERATOR_INSERT, AllConstant.SWB_DATA_FROM_PROWS, unid);
        // 设置数据包
        Map<String, Object> apasInfo = new HashMap<String, Object>();

        // 设置数据包
        Map<String, Object> review = new HashMap<String, Object>();

        bodyMap.put("review", review);
        bodyMap.put("apasInfo", apasInfo);

        review.put("reviewTime", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        review.put("reviewType", 0);
        review.put("reviewContent", "非常满意");
        // 设置分包 -----apasInfo
        apasInfo.put("SN", "ptsy01115102100001");
        return list;
    }
    
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     */
    ExecutorService executor = Executors.newFixedThreadPool(10);
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     */    
    ExecutorService executor1 = Executors.newFixedThreadPool(10);
    //ExecutorService executor =ThreadPoolUtils.getInstance();
    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
     */
    ExecutorService executor2 = Executors.newFixedThreadPool(10);

    
    /**
     * 描述 向省网办报送的数据（办件、过程、结果数据）（不包含工程建设项目数据）
     * 
     * @author Derek Zhang
     * @created 2015年12月04日 下午4:28:08
     * @param dataAbutment
     */
    public void sendDataToProvince(Map<String, Object> dataAbutment) {
        //T_BSFW_DATAABUTMENT
        Connection conn = null;
        try {
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            // 获取指令表数据;
            List<Map<String, Object>> swbdataResList;
            List<Map<String, Object>> dataAbutmentList;
            swbdataResList = this.dao.findBusBasicInfoList(conn);
            dataAbutmentList = this.dao.findDataAbutmentList(conn);
            Map<String, Object> dataAbutment_0006=dataAbutmentList.get(0);
            Map<String, Object> dataAbutment_0008=dataAbutmentList.get(1);
            Map<String, Object> dataAbutment_0009=dataAbutmentList.get(2);
            Map<String, Object> dataAbutment_0010=dataAbutmentList.get(3);
            Map<String, Object> dataAbutment_0011=dataAbutmentList.get(4);
            Map<String, Object> dataAbutment_0012=dataAbutmentList.get(5);
            Map<String, Object> dataAbutment_0016=dataAbutmentList.get(6);
            Map<String, Object> dataAbutment_0024=dataAbutmentList.get(7);
            if (swbdataResList != null && swbdataResList.size() > 0) {
                log.info("开始调用省网办接口向省网办推送数据......");
                // 先更新状态避免下次调用获取重复数
                List<Object> params = new ArrayList<>();
                for(Map<String,Object>swbdataRes:swbdataResList){
                    params.add(swbdataRes.get("RES_ID"));
                }
                String resIds = "'" + StringUtils.join(params.toArray(),"','") + "'";
                String upSql = "update t_bsfw_swbdata_res set oper_status= ? where res_id in (" + resIds + ") ";
                DbUtil.update(conn, upSql, new Object[] {"99"}, false);
               
                for (Map<String, Object> swbdataRes : swbdataResList) {
                    try {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 判断报送的数据类型
                                    if (null != swbdataRes.get("DATA_TYPE")) {
                                        if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_XM)) {
                                            log.info("开始向省网办推送10数据......");
                                            sendBusItemsToProvince(swbdataRes,dataAbutment_0006);
                                        }
                                    }
                                } catch (Exception e) {
                                    log.error("省网办数据推送异常", e);
                                } 

                            }
                        });
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
                Thread.sleep(15000);
               try {
                    for (Map<String, Object> swbdataRes : swbdataResList) {
                        String exeid = (String) swbdataRes.get("EXE_ID");
                        String resid= StringUtil.getString(swbdataRes.get("RES_ID"));
                       Integer isExists= this.dao.searchTen(exeid,resid);
                        if(isExists>0){
                          executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                // 判断报送的数据类型
                                if (null != swbdataRes.get("DATA_TYPE")) {
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_GC)) {
                                        log.info("开始向省网办推送20数据......");
                                        sendBusProcessToProvince(swbdataRes,dataAbutment_0008);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JSZT)) {
                                        log.info("开始向省网办推送21数据......");
                                        sendTimeStopToProvince(swbdataRes,dataAbutment_0010);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_BJXX)) {
                                        log.info("开始向省网办推送22数据......");
                                        sendBJInfoToProvince(swbdataRes,dataAbutment_0016);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JSHF)) {
                                        log.info("开始向省网办推送23数据......");
                                        sendTimeStartToProvince(swbdataRes,dataAbutment_0009);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_TZJF)) {
                                        log.info("开始向省网办推送24数据......");
                                        sendTZJFInfoToProvince(swbdataRes,dataAbutment_0024);
                                    }
                                }
                            }
                        });
                    }
                        }
                } catch (Exception e) {
                    log.error("", e);
                }
                Thread.sleep(15000);
                try {
                    for (Map<String, Object> swbdataRes : swbdataResList) {
                        String exeid = (String) swbdataRes.get("EXE_ID");
                        String resid= StringUtil.getString(swbdataRes.get("RES_ID"));
                        Integer isExists= this.dao.searchTen(exeid,resid);
                        if(isExists>0){
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                // 判断报送的数据类型
                                if (null != swbdataRes.get("DATA_TYPE")) {
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JGXX)) {
                                        sendResultInfoToProvince(swbdataRes,dataAbutment_0011);
                                        // 附件为31数据，30不推送
                                        // this.sendResultAttrToProvince(swbdataRes);
                                    }
                                    // 结果（证照、批文）
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JGFJ)) {
                                        sendResultAttrToProvince(swbdataRes,dataAbutment_0012);
                                    }
                                    /* 评价信息（地市上报） */
                                    /*
                                     * if (swbdataRes.get("DATA_TYPE").equals(
                                     * AllConstant.SWB_DATA_TYPE_PJXX)) {
                                     * this.sendEvaluationToProvince(swbdataRes)
                                     * ; }
                                     */
                                }
                            }
                        });
                    }
                 }
                } catch (Exception e) {
                    log.error("", e);
                }
                log.info("正常结束调用省网办接口向省网办推送数据......");
            }
        } catch (Exception e) {
            log.error("省网办数据推送异常", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }
    /**
     * 
     * 描述   工程建设项目数据向省网办推送job
     * @author Yanisin Shi
     * @param dataAbutment
     */
    public void sendGcjsDataToProvince() {
        //T_BSFW_DATAABUTMENT
        Connection conn = null;
        try {
            conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(), this.getPtzhspDbPassword());
            // 获取指令表数据;
            List<Map<String, Object>> swbdataResList;
            List<Map<String, Object>> dataAbutmentList;
            swbdataResList = this.dao.findGcjsBusBasicInfoList(conn);
            dataAbutmentList = this.dao.findDataAbutmentList(conn);
            Map<String, Object> dataAbutment_0006=dataAbutmentList.get(0);
            Map<String, Object> dataAbutment_0008=dataAbutmentList.get(1);
            Map<String, Object> dataAbutment_0009=dataAbutmentList.get(2);
            Map<String, Object> dataAbutment_0010=dataAbutmentList.get(3);
            Map<String, Object> dataAbutment_0011=dataAbutmentList.get(4);
            Map<String, Object> dataAbutment_0012=dataAbutmentList.get(5);
            Map<String, Object> dataAbutment_0016=dataAbutmentList.get(6);
            Map<String, Object> dataAbutment_0024=dataAbutmentList.get(7);
            Map<String, Object> dataAbutment_0037=dataAbutmentList.get(8);
            if (swbdataResList != null && swbdataResList.size() > 0) {
                log.info("开始调用省网办接口向省网办推送数据......");
                // 先更新状态避免下次调用获取重复数
                List<Object> params = new ArrayList<>();
                for(Map<String,Object>swbdataRes:swbdataResList){
                    params.add(swbdataRes.get("RES_ID"));
                }
                String resIds = "'" + StringUtils.join(params.toArray(),"','") + "'";
                String upSql = "update t_bsfw_swbdata_res set oper_status= ? where res_id in (" + resIds + ") ";
                DbUtil.update(conn, upSql, new Object[] {"99"}, false);
               
                for (Map<String, Object> swbdataRes : swbdataResList) {
                    try {
                        executor1.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 判断报送的数据类型
                                    if (null != swbdataRes.get("DATA_TYPE")) {
                                        if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_XM)) {
                                            log.info("开始向省网办推送10数据......");
                                            sendGcjsBusItemsToProvince(swbdataRes,dataAbutment_0006);
                                            // runnable(new
                                            // SendBusItemsToProvinceRunnable(swbdataRes));
                                        }
                                    }
                                    if (null != swbdataRes.get("DATA_TYPE")) {
                                        if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_TZXM)) {
                                            log.info("开始向省网办推送37数据......");
                                            sendGcjsProBusItemsToProvince(swbdataRes,dataAbutment_0037);
                                        }
                                    }                                    
                                    
                                } catch (Exception e) {
                                    log.error("省网办数据推送异常", e);
                                } 

                                 }
                        });
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
                Thread.sleep(15000);
                try {
                    for (Map<String, Object> swbdataRes : swbdataResList) {
                        executor1.execute(new Runnable() {
                            @Override
                            public void run() {
                                // 判断报送的数据类型
                                if (null != swbdataRes.get("DATA_TYPE")) {
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_GC)) {
                                        log.info("开始向省网办推送20数据......");
                                        sendGcjsBusProcessToProvince(swbdataRes,dataAbutment_0008);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JSZT)) {
                                        log.info("开始向省网办推送21数据......");
                                        sendGcjsTimeStopToProvince(swbdataRes,dataAbutment_0010);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_BJXX)) {
                                        log.info("开始向省网办推送22数据......");
                                        sendGcjsBJInfoToProvince(swbdataRes,dataAbutment_0016);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JSHF)) {
                                        log.info("开始向省网办推送23数据......");
                                        sendGcjsTimeStartToProvince(swbdataRes,dataAbutment_0009);
                                    }
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_TZJF)) {
                                        log.info("开始向省网办推送24数据......");
                                        sendGcjsTZJFInfoToProvince(swbdataRes,dataAbutment_0024);
                                    }
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
                Thread.sleep(15000);
               try {
                    for (Map<String, Object> swbdataRes : swbdataResList) {
                      /*  executor1.execute(new Runnable() {
                            @Override
                            public void run() {*/
                                // 判断报送的数据类型
                                if (null != swbdataRes.get("DATA_TYPE")) {
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JGXX)) {
                                        sendGcjsResultInfoToProvince(swbdataRes,dataAbutment_0011);
                                        // 附件为31数据，30不推送
                                    }
                                    // 结果（证照、批文）
                                    if (swbdataRes.get("DATA_TYPE").equals(AllConstant.SWB_DATA_TYPE_JGFJ)) {
                                        //推送的时候插入附件信息
                                        String unid = (String) swbdataRes.get("RES_ID");
                                        String exeid = (String) swbdataRes.get("EXE_ID");
                                        createPFFile(unid, exeid,conn,swbdataRes);
                                        sendGcjsResultAttrToProvince(swbdataRes,dataAbutment_0012);
                                    }
                                    /* 评价信息（地市上报） */
                                    /*
                                     * if (swbdataRes.get("DATA_TYPE").equals(
                                     * AllConstant.SWB_DATA_TYPE_PJXX)) {
                                     * this.sendEvaluationToProvince(swbdataRes)
                                     * ; }
                                     */
                                }
                            }
                      // });
                     //}
                } catch (Exception e) {
                    log.error("", e);
                }
                log.info("正常结束调用省网办接口向省网办推送数据......");
            }
        } catch (Exception e) {
            log.error("省网办数据推送异常", e);
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }
    /**
     * 
     * 描述   工程建设批复文件构建
     * @author Yanisin Shi
     * @param resid
     * @param exeid
     */
    public void  createPFFile(String resid,String exeid,Connection conn,Map<String,Object> swbdataRes){
        StringBuffer sql=new StringBuffer("with aa as (select v.result_file_id,v.RESULT_FILE_URL,v.EXE_ID from jbpm6_flow_result v where exe_id='"
                +exeid+ "' union all select s.result_file_id,s.RESULT_FILE_URL,s.EXE_ID from jbpm6_flow_result_evehis s where exe_id='"
                +exeid+ "' )");
                sql.append( " select * from aa ");
        List<Map<String,Object>>filelist=dao.findBySql(sql.toString(),null, null);
        Map <String,Object> variables=new HashMap<String,Object>();
        Map <String,Object> hasAttr=new HashMap<String,Object>();
        hasAttr.put("HAS_ATTR",1);
        if(filelist.size()>0){
            swbdataRes.put("HAS_ATTR","1");
            StringBuffer upres=new StringBuffer("update t_bsfw_swbdata_res set has_attr=1 where res_id='"+resid+"'");
            DbUtil.update(conn, upres.toString(), null, false);
            for(int i=0;i<filelist.size();i++){
                if(filelist.get(i).get("result_file_id")!=null){
                String[] resultfileids= filelist.get(i).get("result_file_id").toString().split(";");
                String[] resultfileurls=filelist.get(i).get("result_file_url").toString().split(";");
                for(int j=0;j<resultfileids.length;j++){
                    Map<String,Object> map=getfileInfo(resultfileids[j]);
             StringBuffer fsql=new StringBuffer(" select 1 from t_bsfw_swbdata_attr where attr_id='"+resultfileids[j]+"'");
              List<Map<String,Object>> rlist=dao.findBySql(fsql.toString(),null, null);
              if(rlist.isEmpty()){
                  StringBuffer istSql=new StringBuffer("insert into t_bsfw_swbdata_attr (res_id,attr_content, ");
                  istSql.append("code,name,seq_no,file_name,create_time,oper_status,file_mode,");
                  istSql.append("oper_time,zx_oper_status,file_id,file_path,attr_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                  DbUtil.update(conn,istSql.toString(), new Object[] {resid,"","pf",map.get("file_name").toString(), 
                 j+1,map.get("file_name").toString(),map.get("create_time").toString(),0,"upload",DateTimeUtil.getNowTime(null),      
                 0,resultfileids[j],resultfileurls[j],resultfileids[j]},false);
                  
                  }
                }
                }
                }
        }
    }
    /**
     * 
     * 描述  根据附件id或取文件信息
     * @author Yanisin Shi
     * @param attrid
     * @return 
     */
     public Map<String, Object> getfileInfo(String attrid){
             return dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[]{"FILE_ID"},new Object[]{attrid});
     }
    
    

    /**
     * 
     * 描述 获取投资项目信息查询结果
     * 
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:56:13
     * @param dataAbutment
     * @param projectCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public TzProjectRespones getTZXMXXData(String projectCode) {
        TzProjectRespones tzProjectRes = new TzProjectRespones();
        Map<String, Object> m;
        // 二次装修项目
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(projectCode)
                && projectCode.substring(15, 17).contains("88")) {
            m = new HashMap<String, Object>();
            Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.INTER_CODE_SWB_FGDZCTZXMCX });
            String url = (String) dataAbutment.get("CONFIG_XML");
            String resultStr = HttpRequestUtil.sendGet(url,
                    "args0=350128&args1=XStesPpHdX&args2=&args3=&args4=&args5=10&args6=" + projectCode);
            Document document = XmlUtil.stringToDocument("<?xml version='1.0' encoding='UTF-8'?>" + resultStr);
            String name = document.getRootElement().selectSingleNode("return").getText();
            Map<String, Object> resultMap = JsonUtil.parseJSON2Map(name);
            List<Map<String, Object>> datas = (List<Map<String, Object>>) resultMap.get("datas");
            if (null != datas && datas.size() > 0) {
                Map<String, Object> data = datas.get(0);
                Map<String, Object> projectInfo = (Map<String, Object>) data.get("projectInfo");
                projectInfo.put("PLACE_CODE", projectInfo.get("PLACE_CODE_DETAIL"));
                projectInfo.put("PLACE_CODE_DETAIL", projectInfo.get("PLACE_CODE_DETAIL_EXTENSION"));
                projectInfo.put("APPLY_DATE", DateTimeUtil.getStrOfDate(
                        new Date(Long.valueOf(projectInfo.get("APPLY_DATE").toString())), "yyyy-MM-dd HH:mm:ss"));
                projectInfo.put("CONTRIBUTION_INFO", new ArrayList<Map<String, Object>>());
                Map<String, Object> lerepInfo = (Map<String, Object>) data.get("lerepInfo");
                lerepInfo.put("ENTERPRISE_PLACE",
                        lerepInfo.get("ENTERPRISE_PLACE") == null ? "" : lerepInfo.get("ENTERPRISE_PLACE"));
                lerepInfo.put("ENTERPRISE_NATURE",
                        lerepInfo.get("ENTERPRISE_NATURE") == null ? "" : lerepInfo.get("ENTERPRISE_NATURE"));
                lerepInfo.put("CHINA_FOREIGN_SHARE_RATIO", lerepInfo.get("CHINA_FOREIGN_SHARE_RATIO") == null ? ""
                        : lerepInfo.get("CHINA_FOREIGN_SHARE_RATIO"));
                lerepInfo.put("CONTACT_FAX", lerepInfo.get("CONTACT_FAX") == null ? "" : lerepInfo.get("CONTACT_FAX"));
                lerepInfo.put("CORRESPONDENCE_ADDRESS",
                        lerepInfo.get("CORRESPONDENCE_ADDRESS") == null ? "" : lerepInfo.get("CORRESPONDENCE_ADDRESS"));
                lerepInfo.put("BUSINESS_SCOPE",
                        lerepInfo.get("BUSINESS_SCOPE") == null ? "" : lerepInfo.get("BUSINESS_SCOPE"));
                Map<String, Object> returnDatas = new HashMap<String, Object>();
                returnDatas.putAll(MapUtil.transformUpperCase(projectInfo));
                List<Map<String, Object>> lerep_info = new ArrayList<Map<String, Object>>();
                lerep_info.add(MapUtil.transformUpperCase(lerepInfo));
                returnDatas.put("lerep_info", lerep_info);
                m.put("datas", JSON.parseObject(JSON.toJSONString(returnDatas)));
                m.put("result", resultMap.get("result"));
            }
        } else {
            Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                    new String[] { AllConstant.INTER_CODE_SWB_TZXMCX });
            String url = (String) dataAbutment.get("CONFIG_XML");
            String resultStr = HttpRequestUtil.sendGet(url, "args0=" + projectCode);
            Document document = XmlUtil.stringToDocument("<?xml version='1.0' encoding='UTF-8'?>" + resultStr);
            String name = document.getRootElement().selectSingleNode("//ns:getProjectInfoResponse/return").getText();
            m = JsonUtil.parseJSON2Map(name);
        }

        boolean result = (Boolean) m.get("result");
        if (result) {
            tzProjectRes.setResult(true);
            JSONObject data = (JSONObject) m.get("datas");
            TzProjectData tzProjectData = tzProjectRes.tzProjectDataInstance();

            tzProjectData.setForeignabroadFlag(data.getString("foreign_abroad_flag"));
            tzProjectData.setTotalMoneyExplain(data.getString("total_money_explain"));
            tzProjectData.setTheIndustry(data.getString("the_industry"));
            tzProjectData.setLerepInfo(data.getString("lerep_info"));
            tzProjectData.setContributionInfo(data.getString("contribution_info"));

            tzProjectData.setApplyDate(data.getString("apply_date"));
            tzProjectData.setContactAddress(data.getString("contact_address"));
            tzProjectData.setContactEmail(data.getString("contact_email"));
            tzProjectData.setContactIdCard(data.getString("contact_idcard"));
            tzProjectData.setContactMobile(data.getString("contact_mobile"));
            tzProjectData.setContactName(data.getString("contact_name"));
            tzProjectData.setContactPostCode(data.getString("contact_postcode"));
            tzProjectData.setDeAreaName(data.getString("de_area_name"));
            tzProjectData.setDivisionCode(data.getString("division_code"));
            tzProjectData.setEndYear(data.getString("end_year"));
            tzProjectData.setEnterpriseName(data.getString("enterprise_name"));
            tzProjectData.setIndustry(data.getString("industry"));
            tzProjectData.setIsDeArea(data.getString("is_de_area"));
            tzProjectData.setScaleContent(data.getString("scale_content"));
            tzProjectData.setLerepCertNo(data.getString("lerep_certno"));
            tzProjectData.setLerepCertType(data.getString("lerep_certtype"));
            tzProjectData.setPermitIndustry(data.getString("permit_industry"));
            tzProjectData.setPlaceCode(data.getString("place_code"));
            tzProjectData.setPlaceCodeDetail(data.getString("place_code_detail"));
            tzProjectData.setProjectName(data.getString("project_name"));
            tzProjectData.setProjectNature(data.getString("project_nature"));
            tzProjectData.setProjectRemark(data.getString("project_Remark"));
            tzProjectData.setProjectStage(data.getString("project_stage"));
            tzProjectData.setProjectType(data.getString("project_type"));
            tzProjectData.setStartYear(data.getString("start_year"));
            tzProjectData.setTotalMoney(data.getString("total_money"));
            tzProjectData.setProjectAttributes(data.getString("project_attributes"));
            tzProjectData.setIndustryStructure(data.getString("industry_structure"));
            tzProjectRes.setTzProject(tzProjectData);
        } else {
            tzProjectRes.setErrcode(m.get("errcode").toString());
            tzProjectRes.setErrmsg((String) m.get("errmsg"));
            tzProjectRes.setResult(false);
        }
        return tzProjectRes;
    }

    /**
     * 
     * 描述 获取投资项目信息查询结果
     * 
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:56:13
     * @param dataAbutment
     * @param projectCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getTZXMPLBLQKData(String projectCode) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_TZXM_BLQKPLCXJK });
        String url = (String) dataAbutment.get("CONFIG_XML");
        String resultStr = HttpRequestUtil.sendGet(url, "args0=" + projectCode);
//        String resultStr = HttpRequestUtil.sendGet(
//                "http://10.1.14.249:8080/tzxm/services/ProjectPortInfo.ProjectPortInfo/getApprovalItemInfos"
//        , "args0=" + "2016-000052-01-02-001284");
        Document document = XmlUtil.stringToDocument("<?xml version='1.0' encoding='UTF-8'?>" + resultStr);
        String approvalProcess = document.getRootElement().selectSingleNode("//ns:getApprovalItemInfosResponse/return")
                .getText();
        if (approvalProcess.indexOf("result:false") < 0) {
            Map<String, Object> m = JsonUtil.parseJSON2Map(approvalProcess);
            boolean result = (Boolean) m.get("result");
            if (result) {
                JSONArray datas = (JSONArray) m.get("datas");
                if (datas != null && datas.size() > 0) {
                    for (int i = 0; i < datas.size(); i++) {
                        String data = datas.getString(i);
                        Map<String, Object> dataMap = JsonUtil.parseJSON2Map(data);
                        resultMap.add(dataMap);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 
     * 描述 获取投资项目信息查询结果
     * 
     * @author Derek Zhang
     * @created 2015年12月12日 下午1:56:13
     * @param dataAbutment
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public void saveTZXMPLBLQKData(Log log, Map<String, Object> dataAbutment) {

        // 获取数据
        String sql = "select r.res_id,r.create_time,r.inter_type,t.data_id,t.content "
                + " from t_bsfw_swbdata_res r,t_bsfw_swbtzxmdata t "
                + " where t.data_id = r.task_id and r.oper_status = 0"
                + " and r.inter_type = ? and t.status = 0 order by t.create_date asc ";
        List<Map<String, Object>> dataList = this.dao.findBySql(sql, new Object[] { AllConstant.DATA_INTER_TYPE_TZXM },
                null);
        if (!dataList.isEmpty() && dataList.size() > 0) {
            log.info("开始调用投资项目在线鉴管平台数据处理定时任务，保存办事事项处理信息....");
            for (Map<String, Object> data : dataList) {
                String res_id = (String) data.get("RES_ID");
                String data_id = (String) data.get("DATA_ID");
                String content = "{'datas':" + (String) data.get("CONTENT") + "}";
                String username = StringUtil.nullORSpaceToDefault((String) dataAbutment.get("DB_USERNAME"),
                        "ceshiuser");
                String password = StringUtil.nullORSpaceToDefault((String) dataAbutment.get("DB_PASSWORD"), "123456");
                String url = (String) dataAbutment.get("CONFIG_XML");
                try {
                    content = URLEncoder.encode(content, "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    log.error(e1.getMessage());
                }
                String returnStr = HttpRequestUtil.sendPost(url,
                        "args0=" + username + "&args1=" + password + "&args2=" + content, "utf-8");
                Document document = XmlUtil.stringToDocument("<?xml version='1.0' encoding='UFT-8'?>" + returnStr);
                String approvalProcess = document.getRootElement()
                        .selectSingleNode("//ns:batchAddApprovalItemInfoResponse/return").getText();
                boolean result = false;
                if (approvalProcess.indexOf("result:false") < 0) {
                    Map<String, Object> m = JsonUtil.parseJSON2Map(approvalProcess);
                    result = (Boolean) m.get("result");
                }
                Connection conn;
                try {
                    conn = DbUtil.getConnect(this.getPtzhspDbUrl(), this.getPtzhspDbUsername(),
                            this.getPtzhspDbPassword());
                    String updateSql = "update t_bsfw_swbdata_res r set r.oper_status = " + (result ? 1 : 2)
                            + " ,r.oper_time = to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') " + " where r.res_id = ?";
                    DbUtil.update(conn, updateSql, new Object[] { res_id }, false);
                    updateSql = "update t_bsfw_swbtzxmdata r set r.STATUS = " + (result ? 1 : 2)
                            + " where r.data_id = ?";
                    DbUtil.update(conn, updateSql, new Object[] { data_id });
                } catch (SQLException e) {
                }
            }
            log.info("结束调用投资项目在线鉴管平台数据处理定时任务，保存办事事项处理信息....");
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
     * 描述:根据身份证号判定性别
     *
     * @author Madison You
     * @created 2019/11/27 14:31:00
     * @param
     * @return
     */
    public String findSexByCardNo(String cardNo) {
        String sex = "";
        if (cardNo != null && !cardNo.equals("") && cardNo.length() == 18) {
            int sexNum = Integer.parseInt(cardNo.substring(16, 17));
            if (sexNum % 2 == 0) {
                sex = "女";
            } else {
                sex = "男";
            }
        }
        return sex;
    }

    /**
     * 描述:比较两时间大小
     *
     * @author Madison You
     * @created 2019/11/28 10:38:00
     * @param
     * @return
     */
    private boolean compareDateStr(String dateStr1, String dateStr2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean flag = false;
        try {
            if (!dateStr1.equals("") && !dateStr2.equals("")) {
                Date parse1 = sdf.parse(dateStr1);
                Date parse2 = sdf.parse(dateStr2);
                flag = parse2.getTime() >= parse1.getTime();
            }
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return flag;
    }

    /**
     * 描述:增加时间
     *
     * @author Madison You
     * @created 2019/11/28 10:54:00
     * @param
     * @return
     */
    public String addDateStr(String dateStr, int addTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = "";
        try {
            if (!dateStr.equals("")) {
                Date parse = sdf.parse(dateStr);
                Date date = new Date(parse.getTime() + addTime);
                format = sdf.format(date);
            }
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        return format;
    }

    /**
     * 根据网上过来的数据获取用户信息
     * @param serviceItemMap
     * @return
     */
    public Map<String,Object> getUserInfoMap(Map<String, Object> serviceItemMap){
        Map<String,Object> userInfoMap = new HashMap<>();
        String userId = (String) serviceItemMap.get("CREATOR_ID");
        if(StringUtil.isNotEmpty(userId)){
            String userSql = setUserSql();
            userInfoMap = dao.getMapBySql(userSql,new Object[]{userId});
        }

        if(StringUtil.isEmpty(userInfoMap)&&StringUtil.isNotEmpty(serviceItemMap.get("EFLOW_SJHM"))){//前台用户手机号码
            Sm4Utils sm4 = new Sm4Utils();
            String EFLOW_SJHM = sm4.encryptDataCBC(serviceItemMap.get("EFLOW_SJHM").toString());
            StringJoiner stringJoiner = new StringJoiner(" ");
            stringJoiner.add("SELECT U.USER_TYPE,U.YHMC, DECODE(U.YHXB,'2','女','1','男') AS YHXB,U.ORG_LAW_IDCARD")
                    .add(",U.CSRQ,U.ZJLX,U.ZJHM,U.ZTZZ, U.YZBM,U.DZYX,U.SJHM,U.ZZJGDM,U.JGLX,U.FRDB,U.DWDZ")
                    .add("FROM T_BSFW_USERINFO U")
                    .add("WHERE U.YHZH = ? OR U.SJHM = ? OR U.DHHM = ? OR U.MOBILE_PHONE = ?");
            userInfoMap = dao.getMapBySql(stringJoiner.toString()
                    ,new Object[]{serviceItemMap.get("EFLOW_SJHM"),EFLOW_SJHM,EFLOW_SJHM,EFLOW_SJHM});
            if(StringUtil.isEmpty(userInfoMap)){//查不到，需插入一条记录
                userInfoMap = generateUserInfo(serviceItemMap);
            }
        }

        return userInfoMap;
    }

    /**
     * 根据网上过来的数据生成用户信息
     * @param serviceItemMap
     * @return
     */
    public Map<String,Object> generateUserInfo(Map<String, Object> serviceItemMap){
        Map<String,Object> userInfoMap = new HashMap<>();
        Sm4Utils sm4 = new Sm4Utils();
        String BSYHLX = (String)serviceItemMap.get("BSYHLX");//1：个人申报；2：法人申报
        userInfoMap.put("USER_TYPE",serviceItemMap.get("BSYHLX"));
        userInfoMap.put("YHZH",serviceItemMap.get("EFLOW_SJHM"));
        userInfoMap.put("DLMM",StringUtil.getMd5Encode((String)userInfoMap.get("YHZH")));//登录密码默认为手机号码
        userInfoMap.put("ZCSJ",DateTimeUtil.getNowTime(null));
        userInfoMap.put("YHZT",1);
        userInfoMap.put("YHMC",serviceItemMap.get("EFLOW_CREATORNAME"));//前台用户真实姓名（个人类型：个人姓名；法人类型：公司名称）
        userInfoMap.put("YHXB",1);//1男,2女
        userInfoMap.put("SJHM",sm4.encryptDataCBC((String)serviceItemMap.get("EFLOW_SJHM")));//手机号码
        if(BSYHLX.equals("2")){
            userInfoMap.put("YHXB",serviceItemMap.get("JBR_SEX"));//1男,2女
            userInfoMap.put("ZJLX",serviceItemMap.get("JBR_ZJLX"));//经办人证件类型
            userInfoMap.put("ZJHM",sm4.encryptDataCBC((String)serviceItemMap.get("JBR_ZJHM")));//经办人证件号码
            userInfoMap.put("ZTZZ",serviceItemMap.get("JBR_ADDRESS"));//经办人联系地址
            userInfoMap.put("YZBM",serviceItemMap.get("JBR_POSTCODE"));//经办人邮编
            userInfoMap.put("DZYX",serviceItemMap.get("JBR_EMAIL"));//经办人电子邮件
            userInfoMap.put("ZZJGDM",serviceItemMap.get("SQJG_CODE"));//机构代码
            userInfoMap.put("JGLX",serviceItemMap.get("SQJG_TYPE"));//机构类型
            userInfoMap.put("FRDB",serviceItemMap.get("SQJG_LEALPERSON"));//法人代表
            userInfoMap.put("DWDZ",serviceItemMap.get("SQJG_ADD"));//单位地址
            if(StringUtil.isNotEmpty(serviceItemMap.get("JBR_ZJHM"))){
                userInfoMap.put("DHHM",sm4.encryptDataCBC((String)serviceItemMap.get("JBR_ZJHM")));//法人联系电话
            }
            userInfoMap.put("ORG_NAME",serviceItemMap.get("SQJG_NAME"));//申请机构
            userInfoMap.put("ORG_DATE",DateTimeUtil.getNowTime("yyyy-MM-dd"));
            if(StringUtil.isNotEmpty(serviceItemMap.get("SQJG_LEALPERSON_ZJHM"))){
                userInfoMap.put("ORG_LAW_IDCARD"
                        ,sm4.encryptDataCBC((String)serviceItemMap.get("SQJG_LEALPERSON_ZJHM")));//法人证件号码
            }
        }else{
            userInfoMap.put("ZJLX",serviceItemMap.get("SQRZJLX"));//申请人证件类型
            if(StringUtil.isNotEmpty(serviceItemMap.get("SQRSFZ"))){
                userInfoMap.put("ZJHM",sm4.encryptDataCBC((String)serviceItemMap.get("SQRSFZ")));//申请人证件号码
            }
            userInfoMap.put("ZTZZ",serviceItemMap.get("SQRLXDZ"));//申请人联系地址
            userInfoMap.put("DZYX",serviceItemMap.get("SQRYJ"));//申请人电子邮件
        }
        userInfoMap.put("JBRYXDZ",serviceItemMap.get("JBR_EMAIL"));//经办人电子邮件
        userInfoMap.put("JBRXM",serviceItemMap.get("JBR_NAME"));//经办人姓名
        if(StringUtil.isNotEmpty(serviceItemMap.get("JBR_ZJHM"))){
            userInfoMap.put("JBRSFZ",sm4.encryptDataCBC((String)serviceItemMap.get("JBR_ZJHM")));//经办人证件号码
        }

        if(StringUtil.isNotEmpty(serviceItemMap.get("JBR_MOBILE"))){
            userInfoMap.put("MOBILE_PHONE",sm4.encryptDataCBC((String)serviceItemMap.get("JBR_MOBILE")));//经办人手机
        }
        userInfoMap.put("MZT_YHZH"
                ,"mzt_" + userInfoMap.get("USER_TYPE") + "_" + serviceItemMap.get("EFLOW_SJHM"));//mzt用户账号
        String USER_ID = dao.saveOrUpdate(userInfoMap,"T_BSFW_USERINFO","");
        userInfoMap.put("USER_ID",USER_ID);
        return userInfoMap;
    }


}
    