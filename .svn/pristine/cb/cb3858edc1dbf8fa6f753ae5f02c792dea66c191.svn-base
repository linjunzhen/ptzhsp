/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcSsdjService;
import net.evecom.platform.call.service.CallLogService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.util.HttpsUtilDsj;
import net.evecom.platform.hflow.util.Sms4Dsj;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static net.evecom.platform.hflow.util.Sms4Dsj.decodeSMS4toString;

/**
 * 描述  大数据中心ectip系统信息查询接口
 * @author Madison You
 * @version 1.0
 * @created 2019年10月28日 上午14:57:23
 */
@Controller
@RequestMapping("/identifyMsgByDsjController")
public class IdentifyMsgByDsjController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(MaterConfigController.class);

    /**
     * 描述:审批系统固定写A002
     *
     * @author Madison You
     * @created 2019/10/29 9:04:00
     * @param
     * @return
     */
    private final String SYSTEM_ID = "A002";

    /**
     * 描述:大数据中心ectip系统账号
     *
     * @author Madison You
     * @created 2019/10/29 9:04:00
     * @param
     * @return
     */
    private final String BD_NAME = "spsystem@2019";

    /**
     * 描述:大数据中心ectip系统密码
     *
     * @author Madison You
     * @created 2019/10/29 9:04:00
     * @param
     * @return
     */
    private final String BD_PASS = "spsystem@2019.123";

    /**
     * 描述:获取guid的url
     *
     * @author Madison You
     * @created 2019/10/29 9:04:00
     * @param
     * @return
     */
    private final String GUID_URL = "https://192.168.144.82:8441/ectip/ws/authentication/getGuid?";

    /**
     * 描述:根据身份证号码获取身份信息的url
     *
     * @author Madison You
     * @created 2019/10/29 9:04:00
     * @param
     * @return
     */
    private final String IDENTIFY_URL = "https://192.168.144.82:8441/ectip/ws/idinfo/queryByID?";

    /**
     * 描述:根据统一社会信用代码获取企业信息的url
     *
     * @author Madison You
     * @created 2019/10/29 11:10:00
     * @param
     * @return
     */
    private final String SQJG_CREDIT_URL = "https://192.168.144.82:8441/ectip/ws/enterpInfo/queryByUSCC?";

    /**
     * 描述:根据企业全称查询企业基本信息的url
     *
     * @author Madison You
     * @created 2019/11/5 10:28:00
     * @param
     * @return
     */
    private final String SQJG_NAME_URL = "https://192.168.144.82:8441/ectip/ws/enterpInfo/queryByEnterp?";

    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executorService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/23 17:43:00
     * @param 
     * @return 
     */
    @Resource
    private BdcSsdjService bdcSsdjService;

    /**
     * 第三方接口调用日志
     */
    @Resource
    private CallLogService callLogService;

    /**
     * 描述:大数据中心ectip系统获取个人身份信息接口
     *
     * @author Madison You
     * @created 2019/10/28 16:10:00
     * @param
     * @return
     */
    @RequestMapping("/personalIdentifyQuery")
    public  Map<String, Object>  personalIdentifyQuery(HttpServletRequest request , HttpServletResponse response) {
        Map<String, Object> returnMap = null;
        String lineId = request.getParameter("lineId");
        String itemName = "";
        String departName = "";
        String itemCode = request.getParameter("itemCode");
        if (StringUtils.isNotEmpty(itemCode)) {
            Map<String, Object> map = executorService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"}, new Object[]{itemCode});
            itemName = map.get("ITEM_NAME")==null?
                    "":map.get("ITEM_NAME").toString();
            departName = bdcSsdjService.getItemChildDeptByItemCode(itemCode);
        }
        if (lineId != null) {
            Map<String, Object> queMap = executorService.getByJdbc("T_CKBS_QUEUERECORD",
                    new String[]{"RECORD_ID"}, new Object[]{lineId});
            if (queMap != null && queMap.get("LINE_CARDNO") != null) {
                String lineCardNo = (String) queMap.get("LINE_CARDNO");
                /*获取guid*/
                String guid = getGuidByDsj(departName);
                if (guid != null) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("CALLED_PARTY", "大数据中心");
                    String logid= executorService.saveOrUpdate(variables, "T_BSFW_DSJLOG", null);

                    String url = IDENTIFY_URL;
                    String method = "personalIdentifyQuery";
                    String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy/MM/dd HH:mm:ss");
                    JSONObject jsonObject = conposeJson(itemCode,itemName,logid,url,method,time);
                    JSONObject data = new JSONObject();
                    data.put("guid", guid);
                    data.put("id", lineCardNo);
                    jsonObject.put("data", data);
                    HttpsUtilDsj https = new HttpsUtilDsj();
                    try {
                        /*只需调用，无需对数据进行处理*/
                        String result = https.send(IDENTIFY_URL, jsonObject.toString(),departName);
                        returnMap = dealSqjgCreditCodeResult(result);

                        saveLog(itemName, itemCode, jsonObject, result, url, method,logid,time);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    if (returnMap!=null) {
                        returnMap.put("logid", logid);
                    }
                }
            }
        }

        return returnMap;
    }

    /**
     * 描述:不动产权证领取用到的大数据接口
     *
     * @author Madison You
     * @created 2020/2/11 10:27:00
     * @param
     * @return
     */
    @RequestMapping("/personalIdentifyQueryBdc")
    @ResponseBody
    public Map<String, Object> personalIdentifyQueryBdc(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = null;
        String lineCardNo = request.getParameter("lineCardNo");
        String itemName = "预约不动产权证身份验证";
        String departName = "";
        String itemCode = "YYBDCQZSFYZ";
        if (StringUtils.isNotEmpty(itemCode)) {
            departName = bdcSsdjService.getItemChildDeptByItemCode(itemCode);
        }
        String guid = getGuidByDsj(departName);
        if (guid != null) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("CALLED_PARTY", "大数据中心");
            String logid= executorService.saveOrUpdate(variables, "T_BSFW_DSJLOG", null);
            String url = IDENTIFY_URL;
            String method = "personalIdentifyQuery";
            String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy/MM/dd HH:mm:ss");
            JSONObject jsonObject = conposeJson(itemCode,itemName,logid,url,method,time);
            JSONObject data = new JSONObject();
            data.put("guid", guid);
            data.put("id", lineCardNo);
            jsonObject.put("data", data);
            HttpsUtilDsj https = new HttpsUtilDsj();
            try {
                /*只需调用，无需对数据进行处理*/
                String result = https.send(IDENTIFY_URL, jsonObject.toString(),departName);
                returnMap = dealSqjgCreditCodeResult(result);

                saveLog(itemName, itemCode, jsonObject, result, url, method,logid,time);
            } catch (Exception e) {
                log.info("不动产权证领取用到的大数据接口出错" + e.getMessage());
            }
            if (returnMap!=null) {
                returnMap.put("logid", logid);
            }
        }
        return returnMap;
    }

    /**
     * 描述:大数据中心ectip系统根据统一社会信用代码获取公司信息
     *
     * @author Madison You
     * @created 2019/10/29 14:17:00
     * @param
     * @return
     */
    @RequestMapping("/sqjgCreditCodeQuery")
    @ResponseBody
    public Map<String, Object> sqjgCreditCodeQuery(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> returnMap = null;
        String sqjgCreditCode = request.getParameter("sqjgCreditCode");
        String itemName = request.getParameter("itemName");
        String itemCode = request.getParameter("itemCode");
        String departName = "";
        if (StringUtils.isNotEmpty(itemCode)) {
            departName = bdcSsdjService.getItemChildDeptByItemCode(itemCode);
        }
        if (sqjgCreditCode != null) {
            String guid = getGuidByDsj(departName);
            if (guid != null) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("CALLED_PARTY", "大数据中心");
                String logid= executorService.saveOrUpdate(variables, "T_BSFW_DSJLOG", null);

                String url = SQJG_CREDIT_URL;
                String method = "sqjgCreditCodeQuery";
                String time = DateTimeUtil.getStrOfDate(new Date(), "yyyy/MM/dd HH:mm:ss");
                JSONObject jsonObject = conposeJson(itemCode,itemName,logid,url,method,time);
                JSONObject data = new JSONObject();
                data.put("guid", guid);
                data.put("USCC", sqjgCreditCode);
                jsonObject.put("data", data);
                HttpsUtilDsj https = new HttpsUtilDsj();
                try {
                    String result = https.send(SQJG_CREDIT_URL, jsonObject.toString(),departName);
                    returnMap = dealSqjgCreditCodeResult(result);

                    saveLog(itemName, itemCode, jsonObject, result, url, method,logid,time);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
                if (returnMap!=null) {
                    returnMap.put("logid", logid);
                }
            }
        }
        return returnMap;
    }

    /**
     * 描述:大数据中心根据企业名称查询企业基本信息
     *
     * @author Madison You
     * @created 2019/11/5 10:20:00
     * @param
     * @return
     */
    @RequestMapping("/sqjgNameQuery")
    @ResponseBody
    public Map<String, Object> sqjgNameQuery(HttpServletRequest request, HttpServletResponse response) {
        String token = this.getCreditToken("");
        Properties properties = FileUtil.readProperties("conf/messageConfig.properties");
        String sqjgNameQueryUrl = properties.getProperty("sqjgNameQueryUrl");
        String sqjgName = request.getParameter("sqjgName");
        log.info("企业基本信息查询sqjgName:" + sqjgName);
        //请求头
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", token);
        //请求参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("enterp", sqjgName);
        String result = "";
        Map<String, Object> returnMap = null;
        long LOG_START_TIME = 0l;//请求开始时间戳
        boolean isCall = false;//是否调用第三方接口
        int LOG_RESPONSE_STATUS = 0;//接口调用状态 0失败,1成功
        try {
            LOG_START_TIME = DateTimeUtil.getTimestamp();//请求开始时间戳
            isCall = true;
            result = HttpSendUtil.sendPostParamsH(sqjgNameQueryUrl, headMap, params);
            JSONArray jsonArray = null;
            Map<String,Object> parseMap = (Map) JSON.parse(result);
            if (parseMap != null && parseMap.get("code").equals("01")) {
                if(parseMap.get("data") != null){
                    jsonArray = JSONArray.parseArray(parseMap.get("data").toString());
                    LOG_RESPONSE_STATUS = 1;
                }
            }
            if(jsonArray.size() == 1){
                returnMap = (Map<String, Object>) jsonArray.get(0);
            }else if(jsonArray.size() >= 1){
                log.info("企业基本信息查询错误:jsonArray.size()=" + jsonArray.size());
            }
            log.info("企业基本信息查询result:" + result);
            log.info("企业基本信息查询returnMap:" + returnMap);
        }catch(Exception e){
            log.error("企业基本信息查询出错：" + sqjgName);
            log.error(e.getMessage(), e);
        }finally {
            if(isCall) {//1根据企业名称查询企业基本信息
                callLogService.save(LOG_START_TIME,JSON.toJSONString(params), LOG_RESPONSE_STATUS,result,1);
            }
        }

        return returnMap;
    }
    
    
    /**
     * 描述:获取token
     *
     * @author Madison You
     * @created 2020/12/10 11:13:00
     * @param
     * @return
     */
    private String getCreditToken(String itemCode) {
        String token = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String username = properties.getProperty("SSDJ_LOGIN_USERNAME");
        String password = properties.getProperty("SSDJ_LOGIN_PASSWORD");
        String tokenUrl = properties.getProperty("CREDIT_TOKEN_URL");
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("username", username);
        jsonMap.put("password", password);
        JSONObject json = new JSONObject(jsonMap);
        try{
            String result = HttpSendUtil.sendHttpPostJson(tokenUrl, null,
                    json.toJSONString(), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                Map<String,Object> bodyMap = JSON.parseObject(result, Map.class);
                token = StringUtil.getValue(bodyMap, "token");
            }
        } catch (Exception e) {
            log.error("Token接口调用失败:", e);
        }
        log.info("企业基本信息查询Token:" + token);
        return token;
    }
    
    private void saveLog(String itemName, String itemCode, JSONObject jsonObject, String result, String url,
                         String method,String logid, String time) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("CALLED_PARTY", "大数据中心");
        variables.put("IP", "192.168.144.82");
        variables.put("PORT", "8443");
        variables.put("CALL_USER", "spsystem@2019");
        variables.put("ITEM_CODE", itemName);
        variables.put("ITEM_NAME", itemCode);
        variables.put("URL", url);
        variables.put("METHOD", method);
        variables.put("REQUEST_PARAM", jsonObject.toString());
        if(StringUtils.isNotEmpty(result)){
            if(result.length()>3900){
                variables.put("RESULT", result.substring(0,3900));
                variables.put("RESULT_NEW", result.substring(3900));
            }else{
                variables.put("RESULT", result);
            }
        }
        //variables.put("RESULT", result);
        variables.put("CREAT_TIME", time);
        executorService.saveOrUpdate(variables, "T_BSFW_DSJLOG", logid);
    }

    /**
     * 描述:获取唯一识别 guid，每一次调用接口都要获取一次
     *
     * @author Madison You
     * @created 2019/10/28 16:13:00
     * @param
     * @return
     */
    private String getGuidByDsj(String departName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("systemID", SYSTEM_ID);
        JSONObject data = new JSONObject();
        data.put("bdname", BD_NAME);
        data.put("bdpass", BD_PASS);
        jsonObject.put("data", data);
        HttpsUtilDsj https = new HttpsUtilDsj();
        String guid = null;
        try {
            String result = https.send(GUID_URL, jsonObject.toString(),departName);
            Map<String, Object> resultParse = (Map) JSON.parse(result);
            if (resultParse.get("code").equals("01")) {
                guid = (String) resultParse.get("guid");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return guid;
    }

    /**
     * 描述:组成json模板
     *
     * @author Madison You
     * @param logid
     * @param itemName
     * @param itemCode
     * @param method
     * @param url
     * @param time
     * @created 2019/10/29 8:46:00
     * @param
     * @return
     */
    private JSONObject conposeJson(String itemCode,
                                   String itemName, String logid, String url, String method, String time) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workId", logid);
        jsonObject.put("matterId", itemCode);
        jsonObject.put("matterName", itemName);
        jsonObject.put("interfaceAddress", url);
        jsonObject.put("interviewweeMethodName", method);
        jsonObject.put("interviewweeRequestTime", time);
        jsonObject.put("systemID", SYSTEM_ID);
        return jsonObject;
    }

    /**
     * 描述:处理返回数据
     *
     * @author Madison You
     * @created 2019/10/29 14:24:00
     * @param
     * @return
     */
    public static Map<String,Object> dealSqjgCreditCodeResult(String result) {
        byte[] key = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab,
                (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc,
                (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32, 0x10};
        Map<String, Object> returnMap = null;
        Map<String,Object> parseMap = (Map) JSON.parse(result);
        if (parseMap != null && parseMap.get("code").equals("01")) {
            String data = (String) parseMap.get("data");
            /*解码,1bc056099be006eb4b0e25cd23d76fa5代表无数据*/
            if (!data.equals("1bc056099be006eb4b0e25cd23d76fa5")) {
                byte[] bytes = Sms4Dsj.toByteArray(data);
                String decodeStr = null;
                try {
                    decodeStr = decodeSMS4toString(bytes, key);
                } catch (UnsupportedEncodingException e) {
                    log.info(e.getMessage());
                }
                JSONArray decodeJson = JSON.parseArray(decodeStr);
                returnMap = (Map<String, Object>) JSON.parse(decodeJson.get(0).toString());
            }
        }
        return returnMap;
    }

}
