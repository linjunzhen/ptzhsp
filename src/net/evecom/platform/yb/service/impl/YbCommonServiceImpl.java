/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.yb.dao.YbCommonDao;
import net.evecom.platform.yb.service.YbCommonService;

/**
 * ?????? ????????????????????????-?????????
 * @author Allin Lin
 * @created 2019???10???22??? ??????9:20:03
 */
@Service("ybCommonService")
public class YbCommonServiceImpl extends BaseServiceImpl implements YbCommonService{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbCommonServiceImpl.class);
    
    /**
     * ????????????dao
     */
    @Resource
    private YbCommonDao dao;
    
    /**
     * ????????????service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * ????????????service
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * ????????????service
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * ?????? ??????????????????dao??????
     * @author Allin Lin
     * @created 2019???10???22??? ??????9:32:02
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * ??????  ????????????????????????
     * @author Allin Lin
     * @created 2019???10???22??? ??????9:32:02
     * @param info ???????????????????????????
     * @param urlDicType ????????????????????????????????????
     * @return 200 ?????????????????????????????????
     * @see net.evecom.platform.yb.service.YbCommonService#queryAjaxJsonOfYb(java.util.Map, java.lang.String)
     */
    @Override
    public AjaxJson queryAjaxJsonOfYb(Map<String, Object> info, String urlDicType) {
        String forwardUrl = StringUtil.getString(dictionaryService.
                get("ybConfig", "INTERNET_FORWARD_URL").get("DIC_DESC"));//?????????????????????
        info.put("urlDicType", urlDicType);
        info.put("method", "sendHttpGet");
        AjaxJson json = new AjaxJson();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String response = HttpSendUtil.sendPostParams(forwardUrl, info);
            if (StringUtils.isNotEmpty(response)) {
                result = JSON.parseObject(response, Map.class);
            }
        } catch (Exception e) {
            log.info("????????????-"+urlDicType+"??????????????????:", e);
        }
        if ("error".equals(StringUtil.getString(result.get("resultMessage")))) {
            json.setMsg("??????????????????");
            json.setSuccess(false);
            log.error("????????????-"+urlDicType+":????????????,??????error???");
        } else if (!"200".equals(StringUtil.getString(result.get("resultCode")))) {
            json.setMsg(StringUtil.getString(result.get("resultMessage")));
            json.setSuccess(false);
            json.setJsonString("");
            log.error("????????????-"+urlDicType+":???????????????????????? "+StringUtil.getString(result.get("resultMessage")));
        } else if("200".equals(StringUtil.getString(result.get("resultCode")))){
            json.setMsg("????????????");
            json.setSuccess(true);
            if(result.get("resultData")!=null){
                json.setJsonString(StringUtil.getString(result.get("resultData")));  
            } 
            log.info("????????????-"+urlDicType+":???????????????");
        }
        return json;
    }
    
    /**
     * ??????  ???????????????????????????????????????GET?????????
     * @author Allin Lin
     * @created 2019???10???22??? ??????9:52:09
     * @param info
     * @param urlDicType
     * @return
     * @throws Exception
     * @see net.evecom.platform.yb.service.YbCommonService#queryDataOfYb(java.util.Map, java.lang.String)
     */
    public Map<String, Object> queryDataOfYb(Map<String,Object> info,String urlDicType) throws Exception{   
        String respContent = "";  
        String resultMessage="";
        String param="";
        Map<String, Object> result = new HashMap<String, Object>(); 
        String url = dictionaryService.get("ybConfig", urlDicType).get("DIC_DESC").toString();
        StringBuffer urlBuffer=new StringBuffer("");
        Iterator iterator=info.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Object> entry=(Map.Entry)iterator.next();
            String key=entry.getKey();
            if("aac002s".equals(key)){//?????????????????????????????????(aac002s)???????????????
                //String val=URLEncoder.encode(StringUtil.getString(entry.getValue()),"UTF-8");
                String val=StringUtil.getString(entry.getValue());
                String[] zjhmArray = val.split("/");
                for (int i = 0; i < zjhmArray.length; i++) {
                   urlBuffer.append("aac002s").append("=").append(zjhmArray[i]).append("&"); 
               }
            }else{
                String val=URLEncoder.encode(StringUtil.getString(entry.getValue()),"UTF-8");
                urlBuffer.append(key).append("=").append(val).append("&"); 
            }
            
        }
        if(urlBuffer.length() > 0){
            param = urlBuffer.substring(0, urlBuffer.length()-1);
        }                
        try {
            respContent = HttpRequestUtil.sendGet(url, param);
            /*if(urlDicType.equals("token")){
                respContent = FileUtil.getContentOfFile("d:/ybcs/token.json");//token??????
            }else if(urlDicType.equals("departQuery")||urlDicType.equals("xb_departQuery")){
                respContent = FileUtil.getContentOfFile("d:/ybcs/department.json");
            }else if(urlDicType.equals("rosterQuery")){//???????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/roster.json"), "gbk");
            }else if(urlDicType.equals("djInfosQuery")){//?????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/djInfos.json"), "gbk");
            }else if(urlDicType.equals("ydjInfosQuery")){//?????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/ydjInfos.json"), "gbk");
            }else if(urlDicType.equals("tbCbrQuery")){//??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zgTbCx.json"), "UTF-8");
            }else if(urlDicType.equals("xzxxQuery")){////??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/xzxxQuery.json"), "UTF-8");
            }else if(urlDicType.equals("grxxzhcx")){////????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/grxxzhcx.json"), "UTF-8");
            }else if(urlDicType.equals("unitInfosOfPerson")){//??????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitInfosOfPerson.json"), "utf-8");
            }else if(urlDicType.equals("movePersonsQuery")){//??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/movePersonsQuery.json"), "utf-8");
            }else if(urlDicType.equals("tbPersonInfosQuery")){//????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/tbPersonInfosQuery.json"), "utf-8");
            }else if(urlDicType.equals("unitCheckFlagQuery")){//??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitCheckFlagQuery.json"), "utf-8");
            }else if(urlDicType.equals("zxUnitQuery")){//????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zxUnitQuery.json"), "utf-8");
            }else if(urlDicType.equals("unitInsuUsersQuery")){//??????????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitInsuredUsers.json"), "gbk");
            }else if(urlDicType.equals("unitOfferQuery")){//??????????????????-?????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitOffer.json"), "gbk");
            }else if(urlDicType.equals("unitPayInfoQuery")){//??????????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitPayInfo.json"), "gbk");
            }else if(urlDicType.equals("userItemInfoQuery")){//??????????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/userItemInfo.json"), "gbk");
            }*/
            //?????????????????????json??????
            result = getResultByRespContent(respContent);
        } catch (Exception e) {
            resultMessage = "error";//??????????????????
            result.put("resultMessage", resultMessage);         
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                param,result.toString()));
        return result;
        
    }

    /**
     * ??????    ????????????????????????json??????
     * @author Allin Lin
     * @created 2019???10???22??? ??????10:48:23
     * @param respContent
     * @return
     */
    private Map<String, Object> getResultByRespContent(String respContent){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(respContent)){
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            String code = String.valueOf(resultMap.get("resultCode"));
            //200??????????????????
            if("200".equals(code)){
                if(resultMap.get("resultData")!=null){       
                    result.put("resultData", resultMap.get("resultData"));
                }
                result.put("resultCode", code);
                result.put("resultMessage", String.valueOf(resultMap.get("resultMessage")));
                return result;
            }else{
                result.put("resultCode", code);
                result.put("resultMessage", String.valueOf(resultMap.get("resultMessage")));
                return result;
            }
        }else{
            result.put("resultMessage", "error");//??????????????????
            return result;
        }
    }

    /**
     * 
     * ??????    ??????????????????????????????token???
     * @author Danto Huang
     * @created 2019???11???13??? ??????10:58:42
     * @return
     */
    public Map<String,Object> getToken(){
        Map<String,Object> tokenMap;
        Map<String,Object> tokenParams = new HashMap<String, Object>();
        tokenParams.put("username", dictionaryService.get("ybConfig", "username").get("DIC_DESC"));
        tokenParams.put("password", dictionaryService.get("ybConfig", "password").get("DIC_DESC"));
        AjaxJson tokenjson = this.queryAjaxJsonOfYb(tokenParams, "token");
        if(tokenjson.isSuccess()){
            String jsonString = tokenjson.getJsonString();
            tokenMap = (Map<String, Object>) JSON.parse(jsonString);
            tokenMap.put("success", true);
        }else{
            tokenMap = new HashMap<String, Object>();
            tokenMap.put("success", false);
        }
        return tokenMap;
    }
    
    /**
     * ??????  ?????????????????????????????????????????????POST?????????
     * @author Allin Lin
     * @created 2020???1???7??? ??????9:27:42
     * @param info
     * @param urlDicType
     * @return
     */
    public AjaxJson pushInfoOfYb(Map<String, Object>info,String urlDicType){
        String forwardUrl = StringUtil.getString(dictionaryService.
                get("ybConfig", "INTERNET_FORWARD_URL").get("DIC_DESC"));//?????????????????????
        info.put("urlDicType", urlDicType);
        info.put("method", "sendHttpPost");
        AjaxJson json = new AjaxJson();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String pushInfoJson = JSON.toJSONString(info);
            String response = HttpRequestUtil.sendPost(forwardUrl, "pushInfoJson="+pushInfoJson);
            if (StringUtils.isNotEmpty(response)) {
                result = JSON.parseObject(response, Map.class);
            }
        } catch (Exception e) {
            log.info("????????????-"+urlDicType+"??????????????????:", e);
        }
        if ("error".equals(StringUtil.getString(result.get("resultMessage")))) {
            json.setMsg("??????????????????");
            json.setSuccess(false);
            log.error("????????????-"+urlDicType+":????????????,??????error???");
        } else if (!"200".equals(StringUtil.getString(result.get("resultCode")))) {
            json.setMsg(StringUtil.getString(result.get("resultMessage")));
            json.setSuccess(false);
            json.setJsonString("");
            log.error("????????????-"+urlDicType+":???????????????????????? "+StringUtil.getString(result.get("resultMessage")));
        } else if("200".equals(StringUtil.getString(result.get("resultCode")))){
            json.setMsg("????????????");
            json.setSuccess(true);
            if(result.get("resultData")!=null){
                json.setJsonString(StringUtil.getString(result.get("resultData")));  
            } 
            log.info("????????????-"+urlDicType+":???????????????");
        }
        return json;
    }
    
    /** 
     * ??????     ???????????????????????????????????????POST?????????
     * @author Allin Lin
     * @created 2020???1???7??? ??????10:51:28
     * @param info
     * @param urlDicType
     * @return
     */
    public Map<String, Object> pushDataOfYb(Map<String,Object> info,String urlDicType){
        String respContent = "";  
        String resultMessage="";
        Map<String, Object> result = new HashMap<String, Object>(); 
        String url = dictionaryService.get("ybConfig", urlDicType).get("DIC_DESC").toString();
        try {
            respContent = sendPost(url, JSON.toJSONString(info),"UTF-8");
            /*if(urlDicType.equals("pushZgcb")){//????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zgcb.json"), "utf-8");
            }else if(urlDicType.equals("pushZgTb")){//????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zgtb.json"), "utf-8");
            }else if(urlDicType.equals("pushCxjmCb")){//???????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/cxjmcb.json"), "utf-8");
            }else if(urlDicType.equals("pushCxjmXb")){//????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/cxjmxb.json"), "utf-8");
            }else if(urlDicType.equals("pushCxjmTb")){//??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/cxjmtb.json"), "utf-8");
            }else if(urlDicType.equals("pushYrdwcb")){//????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/pushYrdwcb.json"), "utf-8");
            }else if(urlDicType.equals("pushYrdwBg")){//????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/pushYrdwcb.json"), "utf-8");
            }else if(urlDicType.equals("checkUnitFlag")){//????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/checkUnitFlag.json"), "utf-8");
            }else if(urlDicType.equals("pushUnitToRent")){//??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/pushUnitToRent.json"), "utf-8");
            }else if(urlDicType.equals("psnBasicQuery")){//????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/ryBasicQuery.json"), "gbk");
            }else if(urlDicType.equals("psnInsuQuery")){//????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/ryInsuQuery.json"), "gbk");
            }else if(urlDicType.equals("psnWaveAndAccountQuery")){//????????????-?????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnWageAndAccountQuery.json"), "gbk");
            }else if(urlDicType.equals("psnUnitHistoryQuery")){//????????????-??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnUnitHistoryQuery.json"), "gbk");
            }else if(urlDicType.equals("psnRemoteInsuQuery")){//????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnRemoteInsuQuery.json"), "gbk");
            }else if(urlDicType.equals("psnSpecialHistoryQuery")){//????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnSpecialHistoryQuery.json"), "gbk");
            }else if(urlDicType.equals("psnInfoHistoryQuery")){//????????????-??????????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnInfoHistoryQuery.json"), "gbk");
            }else if(urlDicType.equals("psnPaymentQuery")){//????????????-????????????????????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnPaymentQuery.json"), "gbk");
            }else if(urlDicType.equals("unitInfoQuery")){//????????????????????????-?????????????????????????????????&?????????
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitInfoQuery.json"), "gbk");
            }*/
            //?????????????????????json??????
            result = getResultByRespContent(respContent);
        } catch (Exception e) {
            resultMessage = "error";//??????????????????
            result.put("resultMessage", resultMessage);         
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                JSON.toJSONString(info),result.toString()));
        return result;
    }
    
    /**
     * 
     * ?????????URL??????POST???????????????
     * 
     * @author Derek Zhang
     * @created 2015???12???02??? ??????11:22:20
     * @param url
     *            ???????????????URL
     * @param param
     *            ???????????????????????????????????? name1=value1&name2=value2 ????????????
     * @return URL ????????????????????????????????????
     * @return
     */
    public static String sendPost(String url, String param, String responesCode) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // ?????????URL???????????????
            URLConnection conn = realUrl.openConnection();
            // ???????????????????????????
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ??????POST??????????????????????????????
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ??????URLConnection???????????????????????? 
            //2016???8???11??? 14:53:01 ?????????????????????Rider Chen
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), responesCode));
            // ??????????????????
            out.print(param);
            
            // flush??????????????????
            out.flush();
            // ??????BufferedReader??????????????????URL?????????
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        // ??????finally?????????????????????????????????
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }
    
    /**
     * ??????    ????????????????????????-??????????????????
     * @author Allin Lin
     * @created 2020???3???11??? ??????10:34:11
     * @return
     */
    public static synchronized long getXbPchId(){
        String pch = "";
        DictionaryService dictionaryService = (DictionaryService) AppUtil.getBean("dictionaryService");
        Map<String, Object> variables = dictionaryService.get("ybConfig", "JM_XBPCH");
        pch = variables.get("DIC_DESC").toString();
        long newPch=Long.parseLong(pch)+1;
        variables.put("DIC_DESC",newPch);
        dictionaryService.saveOrUpdate(variables, "T_MSJW_SYSTEM_DICTIONARY", variables.get("DIC_ID").toString());
        return newPch;
    }

    /**
     * 
     * ??????    ????????????????????????
     * @author Danto Huang
     * @created 2020???12???31??? ??????4:00:27
     * @param excelPath
     * @param exeId
     * @param impTableName
     * @return
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public List<Map<String,Object>> impPersonInfo(String excelPath,String exeId,String impTableName) throws Exception {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        dao.remove(impTableName, new String[]{"EXE_ID"}, new Object[]{exeId});
        if(excelPath.endsWith("xls")){
            POIFSFileSystem fs;
            HSSFWorkbook workbook = null;
            HSSFSheet sheet = null;

            fs = new POIFSFileSystem(new FileInputStream(excelPath));
            workbook = new HSSFWorkbook(fs);
            sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int k = 1; k < rowCount; k++) { // ????????????K??? HSSFRow
                HSSFRow row = sheet.getRow(k);

                Map<String, Object> variables = new HashMap<String, Object>();
                if (ExcelUtil.getCellValue(row, 0) != null && !"".equals(ExcelUtil.getCellValue(row, 0).toString().trim())) {
                    variables.put("SQRMC", ExcelUtil.getCellValue(row, 0));
                } else {
                    continue;
                }
                if (ExcelUtil.getCellValue(row, 1) != null && !"".equals(ExcelUtil.getCellValue(row, 1).toString().trim())) {
                    variables.put("SQRSFZ", ExcelUtil.getCellValue(row, 1));
                    String card = (String) ExcelUtil.getCellValue(row, 1);
                    if (Integer.parseInt(card.substring(16).substring(0, 1)) % 2 == 0) {
                        variables.put("SQRXB", 2);
                    } else {
                        variables.put("SQRXB", 1);
                    }
                } else {
                    continue;
                }
                variables.put("SQRSJH", ExcelUtil.getCellValue(row, 2));
                variables.put("SQRLXDZ", ExcelUtil.getCellValue(row, 3));
                variables.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));

                String recordId = dao.saveOrUpdate(variables, impTableName, null);
                variables.put("RECORD_ID", recordId);
                list.add(variables);
            }
        }else if(excelPath.endsWith("xlsx")){
            XSSFWorkbook workbook = null;
            XSSFSheet sheet = null;

            workbook = new XSSFWorkbook(new FileInputStream(excelPath));
            sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int k = 1; k < rowCount; k++) { // ????????????K??? HSSFRow
                XSSFRow row = sheet.getRow(k);

                Map<String, Object> variables = new HashMap<String, Object>();
                if (ExcelUtil.getCellValue(row, 0) != null && !"".equals(ExcelUtil.getCellValue(row, 0))) {
                    variables.put("SQRMC", ExcelUtil.getCellValue(row, 0));
                } else {
                    continue;
                }
                if (ExcelUtil.getCellValue(row, 1) != null && !"".equals(ExcelUtil.getCellValue(row, 1))) {
                    variables.put("SQRSFZ", ExcelUtil.getCellValue(row, 1));
                    String card = (String) ExcelUtil.getCellValue(row, 1);
                    if (Integer.parseInt(card.substring(16).substring(0, 1)) % 2 == 0) {
                        variables.put("SQRXB", 2);
                    } else {
                        variables.put("SQRXB", 1);
                    }
                } else {
                    continue;
                }
                variables.put("SQRSJH", ExcelUtil.getCellValue(row, 2));
                variables.put("SQRLXDZ", ExcelUtil.getCellValue(row, 3));
                variables.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));

                String recordId = dao.saveOrUpdate(variables, impTableName, null);
                variables.put("RECORD_ID", recordId);
                list.add(variables);
            }
        }
        return list;
    }
    
    /**
     * 
     * ??????    ????????????????????????????????????
     * @author Danto Huang
     * @created 2021???1???4??? ??????9:10:59
     * @param flowVars
     * @return
     */
    public Map<String,Object> bindPersonExeId(Map<String, Object> flowVars){
        String personIds = flowVars.get("personIds").toString();
        if(StringUtils.isNotEmpty(personIds)){
            String[] personArr = personIds.split(",");
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
            variables.put("GEN_STATUS", "0");
            for(String personId : personArr){
                dao.saveOrUpdate(variables, "T_YBQLC_PLBJRY", personId);
            }
        }
        return flowVars;
    }
    
    /**
     * 
     * ??????    
     * @author Danto Huang
     * @created 2021???1???4??? ??????10:54:08
     * @param filter
     * @return
     */
    public List<Map<String,Object>> loadPLperson(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_YBQLC_PLBJRY t ");
        sql.append("where 1=1 ");
        
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * ??????    ?????????????????????????????????????????????
     * @author Danto Huang
     * @created 2021???1???4??? ??????3:18:07
     * @param recordId
     * @return
     */
    public Map<String,Object> acceptPersonExe(String recordId){
        Map<String,Object> flowVars = new HashMap<String, Object>();
        try{
            Map<String,Object> personInfo = dao.getByJdbc("T_YBQLC_PLBJRY", new String[]{"RECORD_ID"}, new Object[]{recordId});
            Map<String,Object> exe = dao.getByJdbc("JBPM6_EXECUTION", new String[]{"EXE_ID"}, new Object[]{personInfo.get("EXE_ID")});
            Map<String,Object> ywInfo = dao.getByJdbc("T_YBQLC_PLJINFO", new String[]{"YW_ID"}, new Object[]{exe.get("BUS_RECORDID")});
            
            Map<String,Object> dic = dictionaryService.get("plys", exe.get("ITEM_CODE").toString());
            Map<String,Object> item = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"}, new Object[]{dic.get("DIC_DESC")});
            Map<String,Object> def = dao.getByJdbc("JBPM6_FLOWDEF", new String[]{"DEF_ID"}, new Object[]{item.get("BDLCDYID")});
            Map<String,Object> form = dao.getByJdbc("JBPM6_FLOWFORM", new String[]{"FORM_ID"}, new Object[]{def.get("BIND_FORMID")});
            //??????????????????
            flowVars.put("ITEM_CODE", dic.get("DIC_DESC"));
            flowVars.put("ITEM_NAME", item.get("ITEM_NAME"));
            flowVars.put("SQFS", exe.get("SQFS"));
            flowVars.put("SSBMBM", exe.get("SSBMBM"));
            flowVars.put("BELONG_DEPT", exe.get("SSBMBM"));
            flowVars.put("EFLOW_SUBJECT", personInfo.get("SQRMC")+"-"+item.get("ITEM_NAME")+"???"+item.get("ITEM_NAME")+"???");
            flowVars.put("SBMC", personInfo.get("SQRMC")+"-"+item.get("ITEM_NAME"));
            flowVars.put("SXLX", item.get("SXLX"));
            flowVars.put("SXXZ", item.get("SXXZ"));
            flowVars.put("PROMISE_DATE", item.get("CNQXGZR"));
            flowVars.put("EFLOW_DEFID", def.get("DEF_ID"));
            flowVars.put("EFLOW_DEFKEY", def.get("DEF_KEY"));
            flowVars.put("EFLOW_DEFVERSION", def.get("VERSION"));
            flowVars.put("EFLOW_ISTEMPSAVE", "-1");     
            flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", "??????");
            flowVars.put("EFLOW_CURUSEROPERNODENAME", "??????");
            String nextStepJson = this.jbpmService.getNextStepsJson(def.get("DEF_ID").toString(),
                    Integer.parseInt(def.get("VERSION").toString()), "??????",
                    flowVars);
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);   
            flowVars.put("EFLOW_BUSTABLENAME", form.get("FORM_KEY"));
    
            flowVars.put("EFLOW_CREATORID", exe.get("CREATOR_ID"));
            flowVars.put("EFLOW_CREATORACCOUNT", exe.get("CREATOR_ACCOUNT"));
            flowVars.put("EFLOW_CREATORNAME", exe.get("CREATOR_NAME"));
            flowVars.put("EFLOW_CREATORPHONE", exe.get("CREATOR_PHONE"));
            String randomNum = StringUtil.getFormatNumber(6, StringUtil.getRandomIntNumber(1000000) + "");
            flowVars.put("BJCXMM", randomNum);
            flowVars.put("OPERATOR_ID", exe.get("OPERATOR_ID"));
            //??????????????????
            flowVars.put("SFCJ", exe.get("SFCJ"));
            flowVars.put("BSYHLX", 1);
            flowVars.put("SQRMC", personInfo.get("SQRMC"));
            flowVars.put("SQRXB", personInfo.get("SQRXB"));
            flowVars.put("SQRZJLX", "SF");
            flowVars.put("SQRSFZ", personInfo.get("SQRSFZ"));
            flowVars.put("SQRSJH", personInfo.get("SQRSJH"));
            flowVars.put("SQRLXDZ", personInfo.get("SQRLXDZ"));
            flowVars.put("SFXSJBRXX", 1);
            flowVars.put("JBR_NAME", exe.get("JBR_NAME"));
            flowVars.put("JBR_SEX", exe.get("JBR_SEX"));
            flowVars.put("JBR_MOBILE", exe.get("JBR_MOBILE"));
            flowVars.put("JBR_ZJLX", exe.get("JBR_ZJLX"));
            flowVars.put("JBR_ZJHM", exe.get("JBR_ZJHM"));
            flowVars.put("JBR_LXDH", exe.get("JBR_LXDH"));
            flowVars.put("JBR_POSTCODE", exe.get("JBR_POSTCODE"));
            flowVars.put("JBR_EMAIL", exe.get("JBR_EMAIL"));
            flowVars.put("JBR_ADDRESS", exe.get("JBR_ADDRESS"));
            flowVars.put("JBR_REMARK", exe.get("JBR_REMARK"));
            //????????????????????????
            flowVars.put("FINISH_GETTYPE", ywInfo.get("FINISH_GETTYPE"));
            flowVars.put("RESULT_SEND_ADDRESSEE", ywInfo.get("RESULT_SEND_ADDRESSEE"));
            flowVars.put("RESULT_SEND_ADDR", ywInfo.get("RESULT_SEND_ADDR"));
            flowVars.put("RESULT_SEND_MOBILE", ywInfo.get("RESULT_SEND_MOBILE"));
            flowVars.put("RESULT_SEND_POSTCODE", ywInfo.get("RESULT_SEND_POSTCODE"));
            flowVars.put("RESULT_SEND_REMARKS", ywInfo.get("RESULT_SEND_REMARKS"));
            flowVars.put("RESULT_SEND_PROV", ywInfo.get("RESULT_SEND_PROV"));
            flowVars.put("RESULT_SEND_CITY", ywInfo.get("RESULT_SEND_CITY"));
            
            flowVars.put("REMARK", "?????????"+exe.get("EXE_ID")+"-"+exe.get("SUBJECT")+"???");
            
            flowVars = jbpmService.doFlowJob(flowVars);

            // ?????????????????????
            String busTableName = (String) flowVars.get("EFLOW_BUSTABLENAME");
            // ?????????????????????ID
            String busRecordId = (String) flowVars.get("EFLOW_BUSRECORDID");
            
            //??????????????????
            List<Map<String, Object>> filelist = fileAttachService.findByList(exe.get("BUS_TABLENAME").toString(),
                    exe.get("BUS_RECORDID").toString());
            //List<Map<String,Object>> newfilelist = new ArrayList<Map<String,Object>>();
            //String fileIds = "";
            for(Map<String,Object> file : filelist){
                Map<String,Object> newfile = new HashMap<String, Object>();
                Map<String,Object> mater = dao.getByJdbc("T_WSBS_APPLYMATER", new String[]{"MATER_CODE"}, new Object[]{file.get("ATTACH_KEY")});

                newfile.put("BUS_TABLENAME", busTableName);
                newfile.put("BUS_TABLERECORDID", busRecordId);
                newfile.put("ATTACH_KEY", mater.get("MATER_PARENTCODE"));//????????????????????????????????????????????????????????????
                newfile.put("FILE_NAME", file.get("FILE_NAME"));
                newfile.put("FILE_PATH", file.get("FILE_PATH"));
                newfile.put("FILE_TYPE", file.get("FILE_TYPE"));
                newfile.put("UPLOADER_ID", file.get("UPLOADER_ID"));
                newfile.put("UPLOADER_NAME", file.get("UPLOADER_NAME"));
                newfile.put("FILE_LENGTH", file.get("FILE_LENGTH"));
                newfile.put("FILE_SIZE", file.get("FILE_SIZE"));
                newfile.put("IS_IMG", file.get("IS_IMG"));
                newfile.put("SQFS", file.get("SQFS"));
                newfile.put("SFSQ", file.get("SFSQ"));
                newfile.put("PLAT_TYPE", file.get("PLAT_TYPE"));
                newfile.put("CREADIT_FILEID", file.get("CREADIT_FILEID"));
                
                String fileId = dao.saveOrUpdate(newfile, "T_MSJW_SYSTEM_FILEATTACH", null);
                //fileIds = fileIds.concat(",").concat(fileId);
                //newfile.put("FILE_ID", fileId);
                //newfilelist.add(newfile);
            }
            //flowVars.put("EFLOW_SUBMITMATERFILEJSON", JSON.toJSONString(newfilelist));
            //flowVars.put("EFLOW_FILEATTACHIDS", fileIds);
            
            
            //TODO ???????????????????????????
            
            //??????????????????????????????
            Map<String,Object> udp = new HashMap<String, Object>();
            udp.put("GEN_EXE_ID", flowVars.get("EFLOW_EXEID"));
            udp.put("GEN_STATUS", 1);
            dao.saveOrUpdate(udp, "T_YBQLC_PLBJRY", recordId);
            
            flowVars.put("OPER_SUCCESS", true);            
            flowVars.put("OPER_MSG", "????????????");
        }catch (Exception e) {
            flowVars.put("OPER_SUCCESS", false);            
            flowVars.put("OPER_MSG", "????????????,????????????????????????!");
            log.error("", e);
        }
        
        return flowVars;
    }
}
