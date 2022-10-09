/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.dao.SwbInterfaceDao;
import net.evecom.platform.wsbs.dao.ZxInterfaceDao;
import net.evecom.platform.wsbs.service.SwbInterfaceService;
import net.evecom.platform.wsbs.service.ZxInterfaceService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author Madison You
 * @created 10:25
 */
@Service("zxInterfaceService")
public class ZxInterfaceServiceImpl extends BaseServiceImpl implements ZxInterfaceService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 11:15:00
     * @param
     * @return
     */
    private Logger log = LoggerFactory.getLogger(ZxInterfaceServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 10:28:00
     * @param
     * @return
     */
    @Resource
    private ZxInterfaceDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 14:35:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:08:00
     * @param
     * @return
     */
    @Resource
    private SwbInterfaceDao swbInterfaceDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:28:00
     * @param
     * @return
     */
    @Resource
    private SwbInterfaceService swbInterfaceService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:26:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/5/7 9:38:00
     * @param
     * @return
     */
    private Properties properties = FileUtil.readProperties("project.properties");

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 10:28:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述:获取token
     *
     * @author Madison You
     * @created 2021/3/8 10:30:00
     * @param
     * @return
     */
    @Override
    public String getZxAccessToken() {
        String result = "";
        String Authorization = "";
        Map<String, Object> params = new HashMap<>();
        String grantType = dictionaryService.getDicCode("zxInterfaceConfig", "grant_type");
        String clientId = dictionaryService.getDicCode("zxInterfaceConfig", "client_id");
        String clientSecret = dictionaryService.getDicCode("zxInterfaceConfig", "client_secret");
        String zxTokenUrl = dictionaryService.getDicCode("zxInterfaceConfig", "zxTokenUrl");
        params.put("grant_type", grantType);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("url", zxTokenUrl);
        params.put("method", "sendPostParamsH");
        String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
        try {
            result = HttpSendUtil.sendPostParams(forwardUrl, params);
            if (StringUtils.isNotEmpty(result)) {
                Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                String tokenType = StringUtil.getValue(resultMap, "token_type");
                String accessToken = StringUtil.getValue(resultMap, "access_token");
                if (StringUtils.isNotEmpty(accessToken)) {
                    Authorization = tokenType + " " + accessToken;
                } else {
                    log.error("获取省网总线TOKEN失败，返回数据为:" + result);
                }
            } else {
                log.error("获取省网总线TOKEN失败，返回数据为:" + result);
            }
        } catch (Exception e) {
            log.error("获取省网总线TOKEN失败，返回数据为:" + result, e);
        }

        return Authorization;
    }

    /**
     * 描述:查询待发送得办件信息
     *
     * @author Madison You
     * @created 2021/3/8 14:27:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBusBasicInfoList() {
        StringBuffer sql = new StringBuffer();
        String sendStartTime = dictionaryService.getDicCode("zxInterfaceConfig", "sendStartTime");
        sql.append(" SELECT * FROM (SELECT A.*,ROWNUM AS ROWNUMBER FROM (SELECT R.RES_ID,R.DATA_TYPE, ");
        sql.append(" R.INTER_TYPE,R.OPER_TYPE,R.HAS_ATTR,R.EXE_ID,R.TASK_ID,R.OTHER_STATUS FROM ");
        sql.append(" T_BSFW_SWBDATA_RES R WHERE R.INTER_TYPE = ? AND R.ZX_OPER_STATUS = 0 AND R.CREATE_TIME >= ? ");
        sql.append(" AND DATA_TYPE = 10 ");
        sql.append(" ORDER BY R.DATA_TYPE ASC, R.CREATE_TIME ASC ) a ) where rownumber <= 100 ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),
                new Object[] { AllConstant.DATA_INTER_TYPE_SWB, sendStartTime }, null);
        return list;
    }

    /**
     * 描述:向总线办报送的数据（办件、过程、结果数据）
     *
     * @author Madison You
     * @created 2021/3/8 14:57:00
     * @param
     * @return
     */
    @Override
    public void sendDataToZx() {
        List<Map<String, Object>> swbdataResList = this.findBusBasicInfoList();
        String zxSendUrl = dictionaryService.getDicCode("zxInterfaceConfig", "zxSendUrl");
        String fromCode = dictionaryService.getDicCode("zxInterfaceConfig", "fromCode");
        String id = dictionaryService.getDicCode("zxInterfaceConfig", "id");
        String forwardUrl = properties.getProperty("INTERNET_FORWARD_URL");
        if (swbdataResList != null && !swbdataResList.isEmpty()) {
            for (Map<String, Object> swbdataRes : swbdataResList) {
                try {
                    String dataType = StringUtil.getValue(swbdataRes, "DATA_TYPE");
                    String wsspXml = "";
                    String nextXml = "";
                    String suspendXml = "";
                    String resumeXml = "";
                    String finishXml = "";
                    String expressXml = "";
                    String resultXml = "";
                    String resultAttrXml = "";
                    String evaluateXml = "";

                    if (Objects.equals(dataType, AllConstant.SWB_DATA_TYPE_XM)) {
                        /* 办件信息xml */
                        wsspXml = this.sendBusItemsToSwzx(swbdataRes);
                        /* 查找过程信息 */
                        List<Map<String, Object>> processInfoList = this
                                .findBusInfoList(StringUtil.getValue(swbdataRes, "EXE_ID"), "20");
                        /* 过程信息xml */
                        nextXml = this.sendBusProcessToSwzx(processInfoList);
                        /* 查找计时暂停信息 */
                        List<Map<String, Object>> timeStopInfoList = this
                                .findBusInfoList(StringUtil.getValue(swbdataRes, "EXE_ID"), "21");
                        /* 计时暂停信息xml */
                        suspendXml = this.sendBusTimeStopToSwzx(timeStopInfoList);
                        /* 查找计时恢复信息 */
                        List<Map<String, Object>> timeStartInfoList = this
                                .findBusInfoList(StringUtil.getValue(swbdataRes, "EXE_ID"), "23");
                        /* 计时恢复xml */
                        resumeXml = this.sendBusTimeStartToSwzx(timeStartInfoList);
                        /* 查找结果信息 */
                        List<Map<String, Object>> resultInfoList = this
                                .findBusInfoList(StringUtil.getValue(swbdataRes, "EXE_ID"), "30");
                        /* 结果信息xml */
                        resultXml = this.sendBusResultToSwzx(resultInfoList);
                        /* 查找电子证照信息 */
                        List<Map<String, Object>> resultAttrInfoList = this
                                .findBusInfoList(StringUtil.getValue(swbdataRes, "EXE_ID"), "31");
                        /* 结果电子证照xml */
                        resultAttrXml = this.sendBusResultAttrToSwzx(resultAttrInfoList);
                        /* 查找评价信息 */
                        List<Map<String, Object>> evaluateInfoList = this
                                .findBusInfoList(StringUtil.getValue(swbdataRes, "EXE_ID"), "36");
                        /* 评价信息xml */
                        evaluateXml = this.sendBusEvaluateAttrToSwzx(evaluateInfoList);
                        /* 拼凑办结信息 */
                        finishXml = resultXml + resultAttrXml + evaluateXml;
                    }
                    /* 发送数据 */
                    JSONObject json = new JSONObject();
                    json.put("timestamp", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd Hh:mm:ss"));
                    json.put("SN", StringUtil.getValue(swbdataRes, "EXE_ID"));
                    json.put("fromCode", fromCode);
                    json.put("wssp", wsspXml);
                    json.put("next", nextXml);
                    json.put("suspend", suspendXml);
                    json.put("resume", resumeXml);
                    json.put("finish", finishXml);
                    json.put("express", expressXml);
                    Map<String, Object> params = new HashMap<>();
                    params.put("id", id);
                    params.put("parameters", json.toJSONString());
                    params.put("url", zxSendUrl);
                    params.put("method", "sendPostParamsH");
                    JSONObject headJson = new JSONObject();
                    headJson.put("Authorization", getZxAccessToken());
                    params.put("headJson", headJson.toJSONString());
                    String result = HttpSendUtil.sendPostParams(forwardUrl, params);
                    if (StringUtils.isNotEmpty(result)) {
                        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                        String code = StringUtil.getValue(resultMap, "code");
                        Map<String, Object> variables = new HashMap<>();
                        if (Objects.equals(code, "1")) {
                            variables.put("ZX_OPER_STATUS", "1");
                            variables.put("ZX_OPER_ID", StringUtil.getValue(resultMap, "data"));
                        } else {
                            variables.put("ZX_OPER_STATUS", "2");
                        }
                        dao.saveOrUpdate(variables, "T_BSFW_SWBDATA_RES", StringUtil.getValue(swbdataRes, "RES_ID"));
                    }
                } catch (Exception e) {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("ZX_OPER_STATUS", "2");
                    dao.saveOrUpdate(variables, "T_BSFW_SWBDATA_RES", StringUtil.getValue(swbdataRes, "RES_ID"));
                    log.error("总线数据推送异常", e);
                }
            }
        }

    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 12:02:00
     * @param
     * @return
     */
    private String sendBusEvaluateAttrToSwzx(List<Map<String, Object>> evaluateInfoList) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_PJXX });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        for (Map<String, Object> swbdataRes : evaluateInfoList) {
            try {
                swbInterfaceService.createEvaluationToProvince(conn, swbdataRes, instanceMap, dataAbutment);
                xmlStr = setXmlStr(instanceMap, dataAbutment);
            } catch (Exception e) {
                log.error("总线数据推送36异常错误基础数据为：" + swbdataRes, e);
            }
        }
        return xmlStr;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:54:00
     * @param
     * @return
     */
    private String sendBusResultAttrToSwzx(List<Map<String, Object>> resultAttrInfoList) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JGFJXX });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        for (Map<String, Object> swbdataRes : resultAttrInfoList) {
            try {
                swbInterfaceService.createResultAttrToProvince(conn, swbdataRes, instanceMap, dataAbutment,"ZX");
                xmlStr = setXmlStr(instanceMap, dataAbutment);
            } catch (Exception e) {
                log.error("总线数据推送31异常错误基础数据为：" + swbdataRes, e);
            }
        }
        return xmlStr;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:37:00
     * @param
     * @return
     */
    private String sendBusResultToSwzx(List<Map<String, Object>> resultInfoList) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JGXX });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        for (Map<String, Object> swbdataRes : resultInfoList) {
            try {
                swbInterfaceService.createResultInfoToProvince(conn, swbdataRes, instanceMap, dataAbutment,"ZX");
                xmlStr = setXmlStr(instanceMap, dataAbutment);
            } catch (Exception e) {
                log.error("总线数据推送30异常错误基础数据为：" + swbdataRes, e);
            }
        }
        return xmlStr;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:31:00
     * @param
     * @return
     */
    private String sendBusTimeStartToSwzx(List<Map<String, Object>> timeStartInfoList) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JSHF });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        for (Map<String, Object> swbdataRes : timeStartInfoList) {
            try {
                swbInterfaceService.createTimeStartToProvince(conn, swbdataRes, instanceMap, dataAbutment,"ZX");
                xmlStr += setXmlStr(instanceMap, dataAbutment);
            } catch (Exception e) {
                log.error("总线数据推送23异常错误基础数据为：" + swbdataRes, e);
            }
        }
        return xmlStr;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:20:00
     * @param
     * @return
     */
    private String sendBusTimeStopToSwzx(List<Map<String, Object>> timeStopInfoList) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_JSZT });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        for (Map<String, Object> swbdataRes : timeStopInfoList) {
            try {
                swbInterfaceService.createTimeStopToProvince(conn, swbdataRes, instanceMap, dataAbutment,"ZX");
                xmlStr += setXmlStr(instanceMap, dataAbutment);
            } catch (Exception e) {
                log.error("总线数据推送21异常错误基础数据为：" + swbdataRes, e);
            }
        }
        return xmlStr;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 11:03:00
     * @param
     * @return
     */
    private String sendBusProcessToSwzx(List<Map<String, Object>> processInfoList) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_GCXX });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        for (Map<String, Object> swbdataRes : processInfoList) {
            try {
                swbInterfaceService.createBusProcessToProvince(conn, swbdataRes, instanceMap, dataAbutment,"ZX");
                xmlStr += setXmlStr(instanceMap, dataAbutment);
            } catch (Exception e) {
                log.error("总线数据推送20异常错误基础数据为：" + swbdataRes, e);
            }
        }
        return xmlStr;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/15 10:57:00
     * @param
     * @return
     */
    private String sendBusItemsToSwzx(Map<String, Object> swbdataRes) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { AllConstant.INTER_CODE_SWB_XMXX });
        Map<String, Object> instanceMap = new HashMap<>();
        Connection conn = null;
        String xmlStr = "";
        try {
            swbInterfaceService.createBusItemsToProvince(conn, swbdataRes, instanceMap, dataAbutment, "zx");
            xmlStr = setXmlStr(instanceMap, dataAbutment);
        } catch (Exception e) {
            log.error("总线数据推送10异常错误基础数据为：" + swbdataRes, e);
        }
        return xmlStr;
    }

//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBusItemsToZx(Map<String, Object> swbdataRes) {
//        String xmlStr = "";
//        Map<String, Object> bodyMap = null;
//        String exeId = StringUtil.getValue(swbdataRes, "EXE_ID");
//
//        try{
//            Map<String,Object> tableNameMap = dao.getMapBySql(swbInterfaceService.setTableNameSql(),
//                    new Object[]{exeId});
//            if (tableNameMap == null || tableNameMap.isEmpty()) {
//                //删除相应记录
//                this.dao.deleteAttrNoInJbpm6Execution();
//                this.dao.deleteInfoNoInJbpm6Execution();
//                return "";
//            }
//            /*获取业务数据*/
//            String tableName = StringUtil.getValue(tableNameMap, "BUS_TABLENAME");
//            String tableKey = StringUtil.getValue(tableNameMap, "COLUMN_NAME");
//            String serviceSql = swbInterfaceService.setSQL(tableName, tableKey);
//            Map<String,Object> serviceItemMap = dao.getMapBySql(serviceSql, new Object[]{exeId});
//            serviceItemMap = encryptionService.mapDecrypt(serviceItemMap, "JBPM6_EXECUTION");
//
//            /*设置数据包*/
//            bodyMap = setBusItemBodyData(serviceItemMap, swbdataRes ,tableNameMap);
//            /*封装xml*/
//            xmlStr = setXmlStr(bodyMap, AllConstant.INTER_CODE_ZX_XMXX);
//
//        } catch (Exception e) {
//            log.error("总线数据推送10异常错误基础数据为：" + swbdataRes, e);
//        }
//        return xmlStr;
//    }
//
    /**
     * 描述:查找过程信息
     *
     * @author Madison You
     * @created 2021/3/11 14:52:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBusInfoList(String exeId, String dataType) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM T_BSFW_SWBDATA_RES WHERE EXE_ID = ? AND DATA_TYPE = ? ");
        sql.append(" AND ZX_OPER_STATUS = 0 ");
        return dao.findBySql(sql.toString(), new Object[] { exeId, dataType }, null);
    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/11 14:54:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBusProcessToZx(List<Map<String, Object>> processInfoList) {
//        String xmlStr = "";
//        Map<String, Object> bodyMap = null;
//        try{
//            if (processInfoList != null && !processInfoList.isEmpty()) {
//                for (Map<String, Object> processInfoMap : processInfoList) {
//                    /*设置数据包*/
//                    bodyMap = setBusProcessBodyData(processInfoMap);
//                    /*封装xml*/
//                    xmlStr += setXmlStr(bodyMap, AllConstant.INTER_CODE_ZX_GCXX);
//                }
//            }
//        } catch (Exception e) {
//            log.error("总线数据推送20异常错误基础数据为：" + processInfoList, e);
//        }
//        return xmlStr;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/12 10:52:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBusTimeStopToZx(List<Map<String, Object>> timeStopInfoList) {
//        String xmlStr = "";
//        Map<String, Object> bodyMap = null;
//        try{
//            if (timeStopInfoList != null && !timeStopInfoList.isEmpty()) {
//                for (Map<String, Object> timeStopInfoMap : timeStopInfoList) {
//                    /*设置数据包*/
//                    bodyMap = setBusTimeStopBodyData(timeStopInfoMap);
//                    /*封装xml*/
//                    xmlStr += setXmlStr(bodyMap, AllConstant.INTER_CODE_ZX_JSZT);
//                }
//            }
//        } catch (Exception e) {
//            log.error("总线数据推送21异常错误基础数据为：" + timeStopInfoList, e);
//        }
//        return xmlStr;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/12 15:28:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBusTimeStartToZx(List<Map<String, Object>> timeStartInfoList) {
//        String xmlStr = "";
//        Map<String, Object> bodyMap = null;
//        try{
//            if (timeStartInfoList != null && !timeStartInfoList.isEmpty()) {
//                for (Map<String, Object> timeStartInfoMap : timeStartInfoList) {
//                    /*设置数据包*/
//                    bodyMap = setBusTimeStartBodyData(timeStartInfoMap);
//                    /*封装xml*/
//                    xmlStr += setXmlStr(bodyMap, AllConstant.INTER_CODE_ZX_JSHF);
//                }
//            }
//        } catch (Exception e) {
//            log.error("总线数据推送23异常错误基础数据为：" + timeStartInfoList, e);
//        }
//        return xmlStr;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/15 9:46:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBusResultToZx(List<Map<String, Object>> resultInfoList) {
//        String xmlStr = "";
//        Map<String, Object> bodyMap = null;
//        try{
//            if (resultInfoList != null && !resultInfoList.isEmpty()) {
//                for (Map<String, Object> resultInfoMap : resultInfoList) {
//                    /*设置数据包*/
//                    bodyMap = setBusResultBodyData(resultInfoMap);
//                    /*封装xml*/
//                    xmlStr = setXmlStr(bodyMap, AllConstant.INTER_CODE_ZX_JGXX);
//                }
//            }
//        } catch (Exception e) {
//            log.error("总线数据推送30异常错误基础数据为：" + resultInfoList, e);
//        }
//        return xmlStr;
//    }

//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBusProcessToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendTimeStopToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendBJInfoToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendTimeStartToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendTZJFInfoToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendResultInfoToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendResultAttrToZx(Map<String, Object> swbdataRes) {
//
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/8 15:16:00
//     * @param
//     * @return
//     */
//    @Override
//    public String sendEvaluationToZx(Map<String, Object> swbdataRes) {
//
//    }

//    /**
//     * 描述:设置办件来源
//     *
//     * @author Madison You
//     * @created 2021/3/9 9:43:00
//     * @param
//     * @return
//     */
//    private String setApplyForm(String sqfs) {
//        String applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
//        if ("1".equals(sqfs)) {
//            applyFrom = AllConstant.SWB_DATA_FROM_CITYWS;
//        } else if ("2".equals(sqfs)) {
//            applyFrom = AllConstant.SWB_DATA_FROM_CITYCK;
//        } else if ("4".equals(sqfs)) {
//            applyFrom = AllConstant.SWB_DATA_FROM_SSLD;
//        }
//        return applyFrom;
//    }
//
//    /**
//     * 描述:设置业务数据包
//     *
//     * @author Madison You
//     * @created 2021/3/9 10:44:00
//     * @param
//     * @return
//     */
//    public Map<String, Object> setBusItemBodyData(Map<String,Object> serviceItemMap , Map<String,Object> swbdataRes ,
//                                                  Map<String,Object> tableNameMap) {
//        Map<String,Object> instanceMap = new HashMap<>();
//        Map<String,Object> Proposer = new HashMap<>();
//        Map<String,Object> ApasInfoExtend = new HashMap<>();
//        Map<String,Object> formInfos = new HashMap<>();
//        instanceMap.put("Proposer", Proposer);
//        instanceMap.put("ApasInfoExtend", ApasInfoExtend);
//        instanceMap.put("formInfos", formInfos);
//        String sqfs = StringUtil.getValue(serviceItemMap, "SQFS");
//        String tableName = StringUtil.getValue(tableNameMap, "BUS_TABLENAME");
//        String tableKey = StringUtil.getValue(tableNameMap, "COLUMN_NAME");
//        String busRecordId = StringUtil.getValue(serviceItemMap,"BUS_RECORDID");
//        /*判断是否是工程建设项目*/
//        boolean isProject = swbInterfaceService.judgeProject(tableName);
//        serviceItemMap.put("isProject", isProject);
//        String applyform = setApplyForm(sqfs);
//        String OperatorName = StringUtil.nullORSpaceToDefault(StringUtil.getValue(serviceItemMap, "JBR_NAME"),
//                StringUtil.getValue(serviceItemMap, "SQRMC"));
//        OperatorName = StringUtil.nullORSpaceToDefault(OperatorName, StringUtil.getValue(serviceItemMap,"CREATOR_NAME"));
//        String jbrSex = StringUtil.getValue(serviceItemMap, "JBR_SEX");
//        if (StringUtils.isEmpty(jbrSex)) {
//            String jbrZjhm = StringUtil.getValue(serviceItemMap, "JBR_ZJHM");
//            if (StringUtils.isNotEmpty(jbrZjhm) && jbrZjhm.length() == 18) {
//                jbrSex = swbInterfaceService.findSexByCardNo(jbrZjhm);
//            }
//        }
//        /*设置办件基本信息*/
//        instanceMap.put("RowGuid", StringUtil.getValue(swbdataRes,"RES_ID"));
//        instanceMap.put("SN", StringUtil.getValue(swbdataRes,"EXE_ID"));
//        instanceMap.put("DeclareTime", StringUtil.getValue(serviceItemMap,"CREATE_TIME"));
//        instanceMap.put("ITEMCODE", StringUtil.getValue(serviceItemMap,"SWB_ITEM_CODE"));
//        instanceMap.put("ProjectName", StringUtil.getValue(serviceItemMap,"PROJECT_NAME"));
//        instanceMap.put("ProjectCode", StringUtil.getValue(serviceItemMap,"PROJECT_CODE"));
//        instanceMap.put("OpenWay", StringUtil.getValue(serviceItemMap,"OPEN_WAY"));
//        instanceMap.put("ApplyFrom", applyform);
//        instanceMap.put("PromiseDay", StringUtil.getValue(serviceItemMap, "CNQXGZR"));
//        instanceMap.put("CentralApprovalItemid", "");
//        instanceMap.put("RelItemFlag", "");
//        instanceMap.put("RelDeptCode", "");
//        instanceMap.put("RelCentralItemCode", "");
//        /*设置办件经办人信息*/
//        Proposer.put("OperatorName", OperatorName);
//        Proposer.put("OperatorSex", jbrSex);
//        Proposer.put("OperatorCertificateType", StringUtil.nullORSpaceToDefault(StringUtil.getValue(
//                serviceItemMap,"JBR_ZJLX"),"SF"));
//        Proposer.put("OperatorCertificateNumber", StringUtil.getValue(serviceItemMap,"JBR_ZJHM"));
//        Proposer.put("OperatorMobilePhone", StringUtil.nullORSpaceToDefault(StringUtil.getValue(
//                serviceItemMap,"JBR_MOBILE"),StringUtil.getValue(serviceItemMap,"SQRSJH")));
//        Proposer.put("OperatorTel", StringUtil.getValue(serviceItemMap,"JBR_LXDH"));
//        Proposer.put("OperatorMail", StringUtil.getValue(serviceItemMap,"JBR_EMAIL"));
//        Proposer.put("OperatorPostcode", StringUtil.getValue(serviceItemMap,"JBR_POSTCODE"));
//        Proposer.put("OperatorAddress", StringUtil.getValue(serviceItemMap,"JBR_ADDRESS"));
//        Proposer.put("OperatorMemo", "");
//        /*网上申请   设置个人与法人信息*/
//        if (Objects.equals(sqfs, "1")) {
//
//            String userId = StringUtil.getValue(serviceItemMap, "CREATOR_ID");
//            String userSql = swbInterfaceService.setUserSql();
//            Map<String,Object> userInfoMap = dao.getMapBySql(userSql, new Object[]{userId});
//            userInfoMap = encryptionService.mapDecrypt(userInfoMap, "T_BSFW_USERINFO");
//            if (userInfoMap != null) {
//                String userType = StringUtil.getValue(userInfoMap, "USER_TYPE");
//                if (Objects.equals(userType, "1")) {
//                    String yhxb = StringUtil.getValue(userInfoMap, "YHXB");
//                    if (StringUtils.isEmpty(yhxb)) {
//                        yhxb = swbInterfaceService.findSexByCardNo(StringUtil.getValue(userInfoMap, "ZJHM"));
//                    }
//                    Proposer.put("ApplyType", "0");
//                    Proposer.put("Name", StringUtil.getValue(userInfoMap,"YHMC"));
//                    Proposer.put("IDType", StringUtil.nullORSpaceToDefault(StringUtil.getValue(
//                            userInfoMap,"ZJLX"),"SF"));
//                    Proposer.put("IDName", "");
//                    Proposer.put("ID", StringUtil.nullORSpaceToDefault(StringUtil.getValue(serviceItemMap,"SQRSFZ"),
//                            StringUtil.getValue(userInfoMap,"ZJHM")));
//                    Proposer.put("UnitType", "");
//                    Proposer.put("UnitLealPerson", "");
//                    Proposer.put("FRIDType", "");
//                    Proposer.put("FRID", "");
//                    Proposer.put("Sex", yhxb);
//                    Proposer.put("Postcode", StringUtil.getValue(userInfoMap,"YZBM"));
//                    Proposer.put("MobilePhone", StringUtil.getValue(userInfoMap,"SJHM"));
//                    Proposer.put("Addr", StringUtil.getValue(userInfoMap,"ZTZZ"));
//                    Proposer.put("Email", "");
//                    Proposer.put("Tel", "");
//                } else if (Objects.equals(userType, "2")) {
//                    Proposer.put("ApplyType", "1");
//                    if ((boolean) serviceItemMap.get("isProject")) {
//                        String sqjgCode = StringUtil.getValue(serviceItemMap, "SQJG_CODE");
//                        String IDType = "";
//                        if (StringUtils.isNotEmpty(sqjgCode) && sqjgCode.length() == 9) {
//                            IDType = "JGDM";
//                        } else {
//                            IDType = "XYDM";
//                        }
//                        Proposer.put("Name", StringUtil.getValue(serviceItemMap,"SQJG_NAME"));
//                        Proposer.put("IDType", IDType);
//                        Proposer.put("IDName", "");
//                        Proposer.put("ID", StringUtil.getValue(serviceItemMap,"SQJG_CODE"));
//                        Proposer.put("UnitType", StringUtil.getValue(serviceItemMap,"SQJG_TYPE"));
//                        Proposer.put("UnitLealPerson", StringUtil.getValue(serviceItemMap,"FRDB"));
//                        Proposer.put("FRIDType", "SF");
//                        Proposer.put("FRID", StringUtil.getValue(userInfoMap,"ORG_LAW_IDCARD"));
//                        Proposer.put("Sex", "");
//                        Proposer.put("Postcode", StringUtil.getValue(userInfoMap,"YZBM"));
//                        Proposer.put("MobilePhone", StringUtil.getValue(userInfoMap,"SJHM"));
//                        Proposer.put("Addr", StringUtil.getValue(userInfoMap,"DWDZ"));
//                        Proposer.put("Email", "");
//                        Proposer.put("Tel", "");
//                    } else {
//                        String zzjgdm = StringUtil.getValue(serviceItemMap, "ZZJGDM");
//                        String IDType = "";
//                        if (StringUtils.isNotEmpty(zzjgdm) && zzjgdm.length() == 9) {
//                            IDType = "JGDM";
//                        } else {
//                            IDType = "XYDM";
//                        }
//                        Proposer.put("Name", StringUtil.getValue(userInfoMap,"YHMC"));
//                        Proposer.put("IDType", IDType);
//                        Proposer.put("IDName", "");
//                        Proposer.put("ID", StringUtil.getValue(userInfoMap,"ZZJGDM"));
//                        Proposer.put("UnitType", StringUtil.getValue(userInfoMap,"JGLX"));
//                        Proposer.put("UnitLealPerson", StringUtil.getValue(userInfoMap,"FRDB"));
//                        Proposer.put("FRIDType", "SF");
//                        Proposer.put("FRID", StringUtil.getValue(userInfoMap,"ORG_LAW_IDCARD"));
//                        Proposer.put("Sex", "");
//                        Proposer.put("Postcode", StringUtil.getValue(userInfoMap,"YZBM"));
//                        Proposer.put("MobilePhone", StringUtil.getValue(userInfoMap,"SJHM"));
//                        Proposer.put("Addr", StringUtil.getValue(userInfoMap,"DWDZ"));
//                        Proposer.put("Email", "");
//                        Proposer.put("Tel", "");
//                    }
//                }
//            }
//            /*窗口申请   设置个人与法人信息*/
//        } else {
//            String bsyhlx = StringUtil.getValue(serviceItemMap, "BSYHLX");
//            if (Objects.equals(bsyhlx.trim(), "1")) {
//                String yhxb = StringUtil.getValue(serviceItemMap, "SQRXB");
//                if (StringUtils.isEmpty(yhxb)) {
//                    yhxb = swbInterfaceService.findSexByCardNo(StringUtil.getValue(serviceItemMap, "SQRSFZ"));
//                }
//                Proposer.put("ApplyType", "0");
//                Proposer.put("Name", StringUtil.getValue(serviceItemMap,"SQRMC"));
//                Proposer.put("IDType", StringUtil.nullORSpaceToDefault(StringUtil.getValue(serviceItemMap,"SQRZJLX"),"SF"));
//                Proposer.put("IDName", "");
//                Proposer.put("ID", StringUtil.getValue(serviceItemMap,"SQRSFZ"));
//                Proposer.put("UnitType", "");
//                Proposer.put("UnitLealPerson", "");
//                Proposer.put("FRIDType", "");
//                Proposer.put("FRID", "");
//                Proposer.put("Sex", yhxb);
//                Proposer.put("Postcode", StringUtil.getValue(serviceItemMap,"JBR_POSTCODE"));
//                Proposer.put("MobilePhone", StringUtil.getValue(serviceItemMap,"SQRSJH"));
//                Proposer.put("Addr", StringUtil.getValue(serviceItemMap,"SQRLXDZ"));
//                Proposer.put("Email", "");
//                Proposer.put("Tel", StringUtil.getValue(serviceItemMap,"SQRDHH"));
//            } else {
//                Proposer.put("ApplyType", "1");
//                Proposer.put("Name", StringUtil.getValue(serviceItemMap,"SQJG_NAME"));
//                Proposer.put("IDType", StringUtil.getValue(serviceItemMap,"XYDM"));
//                Proposer.put("IDName", "");
//                Proposer.put("ID", StringUtil.getValue(serviceItemMap,"SQJG_CODE"));
//                Proposer.put("UnitType", StringUtil.getValue(serviceItemMap,"SQJG_TYPE"));
//                Proposer.put("UnitLealPerson", StringUtil.getValue(serviceItemMap,"SQJG_LEALPERSON"));
//                Proposer.put("FRIDType", StringUtil.getValue(serviceItemMap,"SQJG_LEALPERSON_ZJLX"));
//                Proposer.put("FRID", StringUtil.getValue(serviceItemMap,"SQJG_LEALPERSON_ZJHM"));
//                Proposer.put("Sex", "");
//                Proposer.put("Postcode", StringUtil.getValue(serviceItemMap,"JBR_POSTCODE"));
//                Proposer.put("MobilePhone", StringUtil.getValue(serviceItemMap,"SQJG_TEL"));
//                Proposer.put("Addr", StringUtil.getValue(serviceItemMap,"SQJG_ADDR"));
//                Proposer.put("Email", "");
//                Proposer.put("Tel", StringUtil.getValue(serviceItemMap,"SQJG_TEL"));
//            }
//        }
//        /*设置办件扩展信息*/
//        ApasInfoExtend.put("IsAgentService", "");
//        ApasInfoExtend.put("AgentServiceCharge", "");
//        ApasInfoExtend.put("GzcnspFlag", "");
//        ApasInfoExtend.put("GzcnspFileInfo", "");
//        ApasInfoExtend.put("AgentSourceArea", "");
//        ApasInfoExtend.put("CertifiedIsWriteOff", "");
//        /*设置在线投资项目信息*/
//        if (isProject) {
//            if (serviceItemMap.get("PROJECTCODE") != null) {
//                serviceItemMap.put("PROJECT_CODE", StringUtil.getValue(serviceItemMap,"PROJECTCODE"));
//            }
//            swbInterfaceService.setFormInfoData(formInfos, tableName, tableKey, busRecordId);
//        } else {
//            serviceItemMap.put("PROJECT_CODE", "");
//            Map<String, Object> itemInfo = new HashMap<String, Object>();
//            formInfos.put("item", itemInfo);
//            itemInfo.put("PROJECT_NAME", serviceItemMap.get("project_name"));
//        }
//        return instanceMap;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/11 15:22:00
//     * @param
//     * @return
//     */
//    private Map<String, Object> setBusProcessBodyData(Map<String, Object> processInfoMap) {
//        Map<String,Object> bodyMap = new HashMap<>();
//        Map<String,Object> flowInfo = new HashMap<>();
//        bodyMap.put("FlowInfo", flowInfo);
//        String exeId = StringUtil.getValue(processInfoMap, "EXE_ID");
//        String resId = StringUtil.getValue(processInfoMap, "RES_ID");
//        Map<String, Object> sqfsMap = dao.getMapBySql("select e.sqfs,e.exe_id , e.create_time " +
//                "from jbpm6_execution e where e.exe_id = ?", new Object[]{exeId});
//        String serviceSql = swbInterfaceService.queryNextNodeInfoSql("next_node");
//        Map<String, Object> serviceItemMap = dao.getMapBySql(serviceSql, new Object[]{resId});
//        if (serviceItemMap == null || serviceItemMap.isEmpty()) {
//            serviceSql = swbInterfaceService.queryNextNodeInfoSql("cur_node");
//            serviceItemMap = dao.getMapBySql(serviceSql, new Object[]{resId});
//        }
//        String createTime = StringUtil.getValue(serviceItemMap, "CREATE_TIME");
//        String handleOpinion = StringUtil.getValue(serviceItemMap, "HANDLE_OPINION");
//        String tonodename = StringUtil.getValue(serviceItemMap, "TONODENAME");
//        String toname = StringUtil.getValue(serviceItemMap, "TONAME");
//        /*设置基本信息*/
//        bodyMap.put("RowGuid", resId);
//        bodyMap.put("SN", exeId);
//        bodyMap.put("DeclareTime", createTime);
//        /*设置流程信息*/
//        flowInfo.put("finishnode", StringUtil.getValue(serviceItemMap,"OTHER_NAME"));
//        flowInfo.put("transactor", StringUtil.getValue(serviceItemMap,"ASSIGNER_NAME"));
//        flowInfo.put("StartTime", swbInterfaceService.getNodeStartTime(sqfsMap, serviceItemMap));
//        flowInfo.put("OpinionType", swbInterfaceService.getNodeOpinionType(serviceItemMap));
//        flowInfo.put("opinion",  StringUtils.isNotEmpty(handleOpinion) ? handleOpinion :"无意见");
//        flowInfo.put("NextName", swbInterfaceService.refushNodeName(StringUtils.isNotEmpty(tonodename)
//                ? tonodename : "结束"));
//        flowInfo.put("NextUser", swbInterfaceService.refushNodeName(StringUtils.isNotEmpty(toname)
//                ? toname : "系统自动处理"));
//        flowInfo.put("PromiseEndTime", StringUtil.getValue(serviceItemMap,"TASK_DEADTIME"));
//        return bodyMap;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/12 11:06:00
//     * @param
//     * @return
//     */
//    private Map<String, Object> setBusTimeStopBodyData(Map<String, Object> timeStopInfoMap) {
//        Map<String,Object> bodyMap = new HashMap<>();
//        String exeId = StringUtil.getValue(timeStopInfoMap, "EXE_ID");
//        String resId = StringUtil.getValue(timeStopInfoMap, "RES_ID");
//        String hangId = StringUtil.getValue(timeStopInfoMap, "TASK_ID");
//        Map<String, Object> serviceItemMap = dao.getMapBySql(swbInterfaceService.getTimeStopServiceSql(),
//                new Object[]{hangId});
//        String otherStatus = StringUtil.getValue(timeStopInfoMap, "OTHER_STATUS");
//        /*设置基本信息*/
//        bodyMap.put("RowGuid", resId);
//        bodyMap.put("SN", exeId);
//        bodyMap.put("DeclareTime", StringUtil.getValue(serviceItemMap,"BEGIN_TIME"));
//        if (Objects.equals(otherStatus, "3")) {
//            bodyMap.put("reason", StringUtil.getValue(serviceItemMap,"LINK_NAME"));
//            bodyMap.put("stype", "2");
//            bodyMap.put("ratifier", StringUtil.getValue(serviceItemMap,"FULLNAME"));
//            bodyMap.put("basis", StringUtil.getValue(serviceItemMap,"LINK_BASIS"));
//            bodyMap.put("gist", StringUtil.getValue(serviceItemMap,"LINK_NAME"));
//            bodyMap.put("PromiseEndTime", StringUtil.getValue(serviceItemMap,"TASK_DEADTIME"));
//        } else if (Objects.equals(otherStatus,"1")){
//            bodyMap.put("reason", "收件补件");
//            bodyMap.put("stype", "1");
//            bodyMap.put("ratifier", "");
//            bodyMap.put("basis", "");
//            bodyMap.put("gist", "");
//            bodyMap.put("beginTime", "");
//            bodyMap.put("endTime", "");
//            bodyMap.put("desc", "");
//            bodyMap.put("PromiseEndTime", "");
//            String fromTaskIds = (String) serviceItemMap.get("FROMTASK_IDS");
//            if (fromTaskIds != null) {
//                Map<String, Object> fromtask = dao.getMapBySql("select t.ASSIGNER_NAME,t.HANDLE_OPINION,t.TASK_DEADTIME " +
//                        "from JBPM6_TASK t where t.task_id=? ", new Object[]{fromTaskIds});
//                if (fromtask != null) {
//                    bodyMap.put("basis", StringUtil.getValue(fromtask,"HANDLE_OPINION"));
//                    bodyMap.put("ratifier", StringUtil.getValue(fromtask,"ASSIGNER_NAME"));
//                    bodyMap.put("gist", StringUtil.getValue(fromtask,"HANDLE_OPINION"));
//                    bodyMap.put("PromiseEndTime", StringUtil.getValue(fromtask,"TASK_DEADTIME"));
//                }
//            }
//        }
//        bodyMap.put("beginTime", StringUtil.getValue(serviceItemMap,"BEGIN_TIME"));
//        bodyMap.put("endTime", StringUtil.getValue(serviceItemMap,"LINK_END_TIME"));
//        bodyMap.put("desc", "");
//        return bodyMap;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/12 15:30:00
//     * @param
//     * @return
//     */
//    private Map<String, Object> setBusTimeStartBodyData(Map<String, Object> timeStartInfoMap) {
//        Map<String,Object> bodyMap = new HashMap<>();
//        String exeId = StringUtil.getValue(timeStartInfoMap, "EXE_ID");
//        String resId = StringUtil.getValue(timeStartInfoMap, "RES_ID");
//        String hangId = StringUtil.getValue(timeStartInfoMap, "TASK_ID");
//        Map<String, Object> serviceItemMap = dao.getMapBySql(swbInterfaceService.getTimeStopServiceSql(),
//                new Object[]{hangId});
//        String otherStatus = StringUtil.getValue(timeStartInfoMap, "OTHER_STATUS");
//        /*设置基本信息*/
//        bodyMap.put("RowGuid", resId);
//        bodyMap.put("SN", exeId);
//        bodyMap.put("DeclareTime", StringUtil.getValue(serviceItemMap,"END_TIME"));
//        if (Objects.equals(otherStatus, "4")) {
//            bodyMap.put("reason", StringUtil.getValue(serviceItemMap,"LINK_NAME"));
//            bodyMap.put("opinion", StringUtil.getValue(serviceItemMap,"EXPLAIN"));
//        } else {
//            bodyMap.put("reason", "收件补件");
//            bodyMap.put("opinion", "补件");
//        }
//        bodyMap.put("endTime", StringUtil.getValue(serviceItemMap,"END_TIME"));
//        bodyMap.put("ratifier", StringUtil.getValue(serviceItemMap,"FULLNAME"));
//        return bodyMap;
//    }
//
//    /**
//     * 描述:
//     *
//     * @author Madison You
//     * @created 2021/3/15 9:47:00
//     * @param
//     * @return
//     */
//    private Map<String, Object> setBusResultBodyData(Map<String, Object> resultInfoMap) {
//        Map<String,Object> bodyMap = new HashMap<>();
//        String exeId = StringUtil.getValue(resultInfoMap, "EXE_ID");
//        String resId = StringUtil.getValue(resultInfoMap, "RES_ID");
//        Map<String, Object> serviceItemMap = dao.getMapBySql(swbInterfaceService.getResultServiceSql(),
//                new Object[]{exeId});
//        Map<String, Object> jgxxMap = dao.getMapBySql(swbInterfaceService.getJgxxSql(),
//                new Object[]{exeId});
//        if (serviceItemMap != null && !serviceItemMap.isEmpty()) {
//            bodyMap.put("RowGuid", resId);
//            bodyMap.put("SN", exeId);
//            bodyMap.put("DeclareTime", StringUtil.getValue(serviceItemMap,"END_TIME"));
//            if (jgxxMap != null && !jgxxMap.isEmpty()) {
//
//            }
//        }
//        return bodyMap;
//    }
//
    /**
     * 描述:设置xml
     *
     * @author Madison You
     * @created 2021/3/9 10:51:00
     * @param
     * @return
     */
    private String setXmlStr(Map<String, Object> bodyMap, String interCode) {
        Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT", new String[] { "AABUT_CODE" },
                new String[] { interCode });
        String configXml = StringUtil.getValue(dataAbutment, "CONFIG_XML");
        StringBuffer buffer = new StringBuffer();
        buffer.append(swbInterfaceService.makeDataXml(bodyMap, configXml));
        return buffer.toString();
    }

    /**
     * 描述:设置xml
     *
     * @author Madison You
     * @created 2021/3/9 10:51:00
     * @param
     * @return
     */
    private String setXmlStr(Map<String, Object> bodyMap, Map<String, Object> dataAbutment) {
        String configXml = StringUtil.getValue(dataAbutment, "CONFIG_XML");
        StringBuffer buffer = new StringBuffer();
        buffer.append(swbInterfaceService.makeDataXml(bodyMap, configXml));
        return buffer.toString();
    }

}
