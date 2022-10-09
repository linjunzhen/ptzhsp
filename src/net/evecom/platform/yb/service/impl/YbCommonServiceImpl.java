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
 * 描述 医保业务通用操作-实现类
 * @author Allin Lin
 * @created 2019年10月22日 上午9:20:03
 */
@Service("ybCommonService")
public class YbCommonServiceImpl extends BaseServiceImpl implements YbCommonService{
    /**
     * log
     */
    private static Log log = LogFactory.getLog(YbCommonServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private YbCommonDao dao;
    
    /**
     * 所引入的service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 所引入的service
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 所引入的service
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * 描述 覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2019年10月22日 上午9:32:02
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述  医保通用查询接口
     * @author Allin Lin
     * @created 2019年10月22日 上午9:32:02
     * @param info 查询的信息（参数）
     * @param urlDicType 医保相关接口配置的字典值
     * @return 200 接口成功；其他接口失败
     * @see net.evecom.platform.yb.service.YbCommonService#queryAjaxJsonOfYb(java.util.Map, java.lang.String)
     */
    @Override
    public AjaxJson queryAjaxJsonOfYb(Map<String, Object> info, String urlDicType) {
        String forwardUrl = StringUtil.getString(dictionaryService.
                get("ybConfig", "INTERNET_FORWARD_URL").get("DIC_DESC"));//互联网转发地址
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
            log.info("医保接口-"+urlDicType+"获取数据异常:", e);
        }
        if ("error".equals(StringUtil.getString(result.get("resultMessage")))) {
            json.setMsg("医保接口异常");
            json.setSuccess(false);
            log.error("医保接口-"+urlDicType+":请求异常,返回error！");
        } else if (!"200".equals(StringUtil.getString(result.get("resultCode")))) {
            json.setMsg(StringUtil.getString(result.get("resultMessage")));
            json.setSuccess(false);
            json.setJsonString("");
            log.error("医保接口-"+urlDicType+":请求失败，原因： "+StringUtil.getString(result.get("resultMessage")));
        } else if("200".equals(StringUtil.getString(result.get("resultCode")))){
            json.setMsg("处理成功");
            json.setSuccess(true);
            if(result.get("resultData")!=null){
                json.setJsonString(StringUtil.getString(result.get("resultData")));  
            } 
            log.info("医保接口-"+urlDicType+":请求成功！");
        }
        return json;
    }
    
    /**
     * 描述  医保通用查询接口获取数据（GET请求）
     * @author Allin Lin
     * @created 2019年10月22日 上午9:52:09
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
            if("aac002s".equals(key)){//医保批量查询身份证字段(aac002s)做特殊拼接
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
                respContent = FileUtil.getContentOfFile("d:/ybcs/token.json");//token信息
            }else if(urlDicType.equals("departQuery")||urlDicType.equals("xb_departQuery")){
                respContent = FileUtil.getContentOfFile("d:/ybcs/department.json");
            }else if(urlDicType.equals("rosterQuery")){//人员花名册
                respContent = FileUtils.readFileToString(new File("d:/ybcs/roster.json"), "gbk");
            }else if(urlDicType.equals("djInfosQuery")){//可登记人员信息
                respContent = FileUtils.readFileToString(new File("d:/ybcs/djInfos.json"), "gbk");
            }else if(urlDicType.equals("ydjInfosQuery")){//已登记人员信息
                respContent = FileUtils.readFileToString(new File("d:/ybcs/ydjInfos.json"), "gbk");
            }else if(urlDicType.equals("tbCbrQuery")){//职工停保人员信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zgTbCx.json"), "UTF-8");
            }else if(urlDicType.equals("xzxxQuery")){////单位参加险种信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/xzxxQuery.json"), "UTF-8");
            }else if(urlDicType.equals("grxxzhcx")){////个人信息综合查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/grxxzhcx.json"), "UTF-8");
            }else if(urlDicType.equals("unitInfosOfPerson")){//居民单位信息
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitInfosOfPerson.json"), "utf-8");
            }else if(urlDicType.equals("movePersonsQuery")){//居民续保单位人员查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/movePersonsQuery.json"), "utf-8");
            }else if(urlDicType.equals("tbPersonInfosQuery")){//居民停保人员查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/tbPersonInfosQuery.json"), "utf-8");
            }else if(urlDicType.equals("unitCheckFlagQuery")){//单位参保核对标记查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitCheckFlagQuery.json"), "utf-8");
            }else if(urlDicType.equals("zxUnitQuery")){//注销单位信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zxUnitQuery.json"), "utf-8");
            }else if(urlDicType.equals("unitInsuUsersQuery")){//单位综合查询-单位参保清册查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitInsuredUsers.json"), "gbk");
            }else if(urlDicType.equals("unitOfferQuery")){//单位综合查询-单位通知单查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitOffer.json"), "gbk");
            }else if(urlDicType.equals("unitPayInfoQuery")){//单位综合查询-单位缴费明细查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitPayInfo.json"), "gbk");
            }else if(urlDicType.equals("userItemInfoQuery")){//单位综合查询-居民账目信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/userItemInfo.json"), "gbk");
            }*/
            //解析返回结果的json数据
            result = getResultByRespContent(respContent);
        } catch (Exception e) {
            resultMessage = "error";//请求接口失败
            result.put("resultMessage", resultMessage);         
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                param,result.toString()));
        return result;
        
    }

    /**
     * 描述    医保返回结果解析json数据
     * @author Allin Lin
     * @created 2019年10月22日 上午10:48:23
     * @param respContent
     * @return
     */
    private Map<String, Object> getResultByRespContent(String respContent){
        Map<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(respContent)){
            Map<String, Object> resultMap = (Map)JSON.parse(respContent);
            String code = String.valueOf(resultMap.get("resultCode"));
            //200查询结果正常
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
            result.put("resultMessage", "error");//调取接口异常
            return result;
        }
    }

    /**
     * 
     * 描述    获取医保接口授权码（token）
     * @author Danto Huang
     * @created 2019年11月13日 上午10:58:42
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
     * 描述  医保通用校验、推送、查询接口（POST请求）
     * @author Allin Lin
     * @created 2020年1月7日 上午9:27:42
     * @param info
     * @param urlDicType
     * @return
     */
    public AjaxJson pushInfoOfYb(Map<String, Object>info,String urlDicType){
        String forwardUrl = StringUtil.getString(dictionaryService.
                get("ybConfig", "INTERNET_FORWARD_URL").get("DIC_DESC"));//互联网转发地址
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
            log.info("医保接口-"+urlDicType+"获取数据异常:", e);
        }
        if ("error".equals(StringUtil.getString(result.get("resultMessage")))) {
            json.setMsg("医保接口异常");
            json.setSuccess(false);
            log.error("医保接口-"+urlDicType+":请求异常,返回error！");
        } else if (!"200".equals(StringUtil.getString(result.get("resultCode")))) {
            json.setMsg(StringUtil.getString(result.get("resultMessage")));
            json.setSuccess(false);
            json.setJsonString("");
            log.error("医保接口-"+urlDicType+":请求失败，原因： "+StringUtil.getString(result.get("resultMessage")));
        } else if("200".equals(StringUtil.getString(result.get("resultCode")))){
            json.setMsg("处理成功");
            json.setSuccess(true);
            if(result.get("resultData")!=null){
                json.setJsonString(StringUtil.getString(result.get("resultData")));  
            } 
            log.info("医保接口-"+urlDicType+":请求成功！");
        }
        return json;
    }
    
    /** 
     * 描述     医保校验、推送、查询接口（POST请求）
     * @author Allin Lin
     * @created 2020年1月7日 上午10:51:28
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
            /*if(urlDicType.equals("pushZgcb")){//职工参保
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zgcb.json"), "utf-8");
            }else if(urlDicType.equals("pushZgTb")){//职工停保
                respContent = FileUtils.readFileToString(new File("d:/ybcs/zgtb.json"), "utf-8");
            }else if(urlDicType.equals("pushCxjmCb")){//城乡居民新参保登记
                respContent = FileUtils.readFileToString(new File("d:/ybcs/cxjmcb.json"), "utf-8");
            }else if(urlDicType.equals("pushCxjmXb")){//城乡居民续保登记
                respContent = FileUtils.readFileToString(new File("d:/ybcs/cxjmxb.json"), "utf-8");
            }else if(urlDicType.equals("pushCxjmTb")){//城乡居民停保减员登记
                respContent = FileUtils.readFileToString(new File("d:/ybcs/cxjmtb.json"), "utf-8");
            }else if(urlDicType.equals("pushYrdwcb")){//用人单位参保登记
                respContent = FileUtils.readFileToString(new File("d:/ybcs/pushYrdwcb.json"), "utf-8");
            }else if(urlDicType.equals("pushYrdwBg")){//用人单位变更登记
                respContent = FileUtils.readFileToString(new File("d:/ybcs/pushYrdwcb.json"), "utf-8");
            }else if(urlDicType.equals("checkUnitFlag")){//参保单位信息核对
                respContent = FileUtils.readFileToString(new File("d:/ybcs/checkUnitFlag.json"), "utf-8");
            }else if(urlDicType.equals("pushUnitToRent")){//注销单位信息发送地税
                respContent = FileUtils.readFileToString(new File("d:/ybcs/pushUnitToRent.json"), "utf-8");
            }else if(urlDicType.equals("psnBasicQuery")){//个人综合-人员基本信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/ryBasicQuery.json"), "gbk");
            }else if(urlDicType.equals("psnInsuQuery")){//个人综合-人员参保信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/ryInsuQuery.json"), "gbk");
            }else if(urlDicType.equals("psnWaveAndAccountQuery")){//个人综合-工资及个账查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnWageAndAccountQuery.json"), "gbk");
            }else if(urlDicType.equals("psnUnitHistoryQuery")){//个人综合-历史参保单位信息查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnUnitHistoryQuery.json"), "gbk");
            }else if(urlDicType.equals("psnRemoteInsuQuery")){//个人综合-异地参保记录查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnRemoteInsuQuery.json"), "gbk");
            }else if(urlDicType.equals("psnSpecialHistoryQuery")){//个人综合-特殊人员记录查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnSpecialHistoryQuery.json"), "gbk");
            }else if(urlDicType.equals("psnInfoHistoryQuery")){//个人综合-人员变更历史记录查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnInfoHistoryQuery.json"), "gbk");
            }else if(urlDicType.equals("psnPaymentQuery")){//个人综合-个人缴费明细查询
                respContent = FileUtils.readFileToString(new File("d:/ybcs/psnPaymentQuery.json"), "gbk");
            }else if(urlDicType.equals("unitInfoQuery")){//单位信息综合查询-单位参保信息查询（个人&参保）
                respContent = FileUtils.readFileToString(new File("d:/ybcs/unitInfoQuery.json"), "gbk");
            }*/
            //解析返回结果的json数据
            result = getResultByRespContent(respContent);
        } catch (Exception e) {
            resultMessage = "error";//请求接口失败
            result.put("resultMessage", resultMessage);         
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                JSON.toJSONString(info),result.toString()));
        return result;
    }
    
    /**
     * 
     * 向指定URL发送POST方法的请求
     * 
     * @author Derek Zhang
     * @created 2015年12月02日 上午11:22:20
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @return
     */
    public static String sendPost(String url, String param, String responesCode) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流 
            //2016年8月11日 14:53:01 增加输出流编码Rider Chen
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), responesCode));
            // 发送请求参数
            out.print(param);
            
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), responesCode));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        // 使用finally块来关闭输出流、输入流
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
     * 描述    获取居民续保登记-新参保批次号
     * @author Allin Lin
     * @created 2020年3月11日 上午10:34:11
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
     * 描述    导入批量办件人员
     * @author Danto Huang
     * @created 2020年12月31日 下午4:00:27
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
            for (int k = 1; k < rowCount; k++) { // 获取到第K行 HSSFRow
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
            for (int k = 1; k < rowCount; k++) { // 获取到第K行 HSSFRow
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
     * 描述    批量导入人员绑定流程实例
     * @author Danto Huang
     * @created 2021年1月4日 上午9:10:59
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
     * 描述    
     * @author Danto Huang
     * @created 2021年1月4日 上午10:54:08
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
     * 描述    批量收件人员独立生成办件并受理
     * @author Danto Huang
     * @created 2021年1月4日 下午3:18:07
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
            //办件基本信息
            flowVars.put("ITEM_CODE", dic.get("DIC_DESC"));
            flowVars.put("ITEM_NAME", item.get("ITEM_NAME"));
            flowVars.put("SQFS", exe.get("SQFS"));
            flowVars.put("SSBMBM", exe.get("SSBMBM"));
            flowVars.put("BELONG_DEPT", exe.get("SSBMBM"));
            flowVars.put("EFLOW_SUBJECT", personInfo.get("SQRMC")+"-"+item.get("ITEM_NAME")+"（"+item.get("ITEM_NAME")+"）");
            flowVars.put("SBMC", personInfo.get("SQRMC")+"-"+item.get("ITEM_NAME"));
            flowVars.put("SXLX", item.get("SXLX"));
            flowVars.put("SXXZ", item.get("SXXZ"));
            flowVars.put("PROMISE_DATE", item.get("CNQXGZR"));
            flowVars.put("EFLOW_DEFID", def.get("DEF_ID"));
            flowVars.put("EFLOW_DEFKEY", def.get("DEF_KEY"));
            flowVars.put("EFLOW_DEFVERSION", def.get("VERSION"));
            flowVars.put("EFLOW_ISTEMPSAVE", "-1");     
            flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", "开始");
            flowVars.put("EFLOW_CURUSEROPERNODENAME", "开始");
            String nextStepJson = this.jbpmService.getNextStepsJson(def.get("DEF_ID").toString(),
                    Integer.parseInt(def.get("VERSION").toString()), "开始",
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
            //申报对象信息
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
            //办理结果领取方式
            flowVars.put("FINISH_GETTYPE", ywInfo.get("FINISH_GETTYPE"));
            flowVars.put("RESULT_SEND_ADDRESSEE", ywInfo.get("RESULT_SEND_ADDRESSEE"));
            flowVars.put("RESULT_SEND_ADDR", ywInfo.get("RESULT_SEND_ADDR"));
            flowVars.put("RESULT_SEND_MOBILE", ywInfo.get("RESULT_SEND_MOBILE"));
            flowVars.put("RESULT_SEND_POSTCODE", ywInfo.get("RESULT_SEND_POSTCODE"));
            flowVars.put("RESULT_SEND_REMARKS", ywInfo.get("RESULT_SEND_REMARKS"));
            flowVars.put("RESULT_SEND_PROV", ywInfo.get("RESULT_SEND_PROV"));
            flowVars.put("RESULT_SEND_CITY", ywInfo.get("RESULT_SEND_CITY"));
            
            flowVars.put("REMARK", "归属（"+exe.get("EXE_ID")+"-"+exe.get("SUBJECT")+"）");
            
            flowVars = jbpmService.doFlowJob(flowVars);

            // 获取业务表名称
            String busTableName = (String) flowVars.get("EFLOW_BUSTABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) flowVars.get("EFLOW_BUSRECORDID");
            
            //保存材料附件
            List<Map<String, Object>> filelist = fileAttachService.findByList(exe.get("BUS_TABLENAME").toString(),
                    exe.get("BUS_RECORDID").toString());
            //List<Map<String,Object>> newfilelist = new ArrayList<Map<String,Object>>();
            //String fileIds = "";
            for(Map<String,Object> file : filelist){
                Map<String,Object> newfile = new HashMap<String, Object>();
                Map<String,Object> mater = dao.getByJdbc("T_WSBS_APPLYMATER", new String[]{"MATER_CODE"}, new Object[]{file.get("ATTACH_KEY")});

                newfile.put("BUS_TABLENAME", busTableName);
                newfile.put("BUS_TABLERECORDID", busRecordId);
                newfile.put("ATTACH_KEY", mater.get("MATER_PARENTCODE"));//材料所属编码（与单独件材料编码保持一致）
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
            
            
            //TODO 自动流转到审核环节
            
            //更新人员办件生成状态
            Map<String,Object> udp = new HashMap<String, Object>();
            udp.put("GEN_EXE_ID", flowVars.get("EFLOW_EXEID"));
            udp.put("GEN_STATUS", 1);
            dao.saveOrUpdate(udp, "T_YBQLC_PLBJRY", recordId);
            
            flowVars.put("OPER_SUCCESS", true);            
            flowVars.put("OPER_MSG", "操作成功");
        }catch (Exception e) {
            flowVars.put("OPER_SUCCESS", false);            
            flowVars.put("OPER_MSG", "操作失败,请联系系统管理员!");
            log.error("", e);
        }
        
        return flowVars;
    }
}
