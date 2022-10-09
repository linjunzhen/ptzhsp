/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpClientUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.business.dao.OldAgeCardDao;
import net.evecom.platform.business.service.OldAgeCardService;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.WorkdayService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 老年人优待证业务管理Service实现类 
 * @author Bryce Zhang
 * @created 2017-5-15 上午9:07:13
 */
@Service("oldAgeCardService")
public class OldAgeCardServiceImpl extends BaseServiceImpl implements OldAgeCardService{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(OldAgeCardServiceImpl.class);
    
    /**
     * 老年人优待证业务管理Dao
     */
    @Resource
    private OldAgeCardDao dao;
    
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    
    /**
     * 引入Service
     */
    @Resource
    private WorkdayService workdayService;
    
    /**
     * 描述 老年人优待证业务管理Dao
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:09:02
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述 人像比对
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:07:38
     * @param name
     * @param idNum
     * @param imgBase64Code
     * @param requestIp
     * @return
     */
    public AjaxJson faceCompare(String name, String idNum, String imgBase64Code, String requestIp){
        AjaxJson j = new AjaxJson();
        CloseableHttpClient httpClient = HttpClientUtil.getPlainHttpClient();
        Properties properties = FileUtil.readProperties("project.properties");
        //1、请求人像比对结果
        String picPostUrl = properties.getProperty("faceCompareUrl") + "/sendPicture";
        Map<String, Object> paramsMap1 = new HashMap<String, Object>();
        paramsMap1.put("name", name);
        paramsMap1.put("citizenIdNumber", idNum);
        paramsMap1.put("businessId", properties.getProperty("faceCompareBusId"));
        paramsMap1.put("reqIp", requestIp);
        Map<String, Object> imgParamsMap = new HashMap<String, Object>();
        imgParamsMap.put("imgfile", imgBase64Code);
        paramsMap1.put("imgs", imgParamsMap);
        Map<String, Object> returnJson1 = doPostJson(httpClient, picPostUrl, JSON.toJSONString(paramsMap1));
        if(returnJson1 != null){
            String picPostResult = (String)returnJson1.get("result");
            if("1".equals(picPostResult)){
                //2、请求查询人像比对结果
                String queryPostUrl = properties.getProperty("faceCompareUrl") + "/getResult";
                String resultUuid = (String)returnJson1.get("uuid");
                Map<String, Object> paramsMap2 = new HashMap<String, Object>();
                paramsMap2.put("uuid", resultUuid);
                Map<String, Object> returnJson2 = doPostJson(httpClient, queryPostUrl, JSON.toJSONString(paramsMap2));
                if(returnJson2 != null){
                    String queryPostResult = (String)returnJson2.get("result");
                    if("1".equals(queryPostResult)){
                        //成功返回值1(照片非本人)0(照片是本人)-1(图片解析失败)-2(暂未获得结果)
                        String checkresult = (String)returnJson2.get("checkresult");
                        //成功返回相似度值
                        String verify = (String)returnJson2.get("verify");
                        Map<String, Object> returnJson = new HashMap<String, Object>();
                        returnJson.put("result", 1);
                        returnJson.put("checkresult", checkresult);
                        returnJson.put("verify", verify);
                        j.setJsonString(JSON.toJSONString(returnJson));
                    }else{
                        Map<String, Object> returnJson = new HashMap<String, Object>();
                        returnJson.put("result", 0);
                        j.setJsonString(JSON.toJSONString(returnJson));
                    }
                }else{
                    j.setSuccess(false);
                    j.setMsg("人像比对请求失败，调用公安人像比对接口失败。");
                }
            }else{
                Map<String, Object> returnJson = new HashMap<String, Object>();
                returnJson.put("result", 0);
                j.setJsonString(JSON.toJSONString(returnJson));
            }
        }else{
            j.setSuccess(false);
            j.setMsg("人像比对请求失败，调用公安人像比对接口失败。");
        }
        return j;
    }
    
    /**
     * 描述 执行post请求
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:10:05
     * @param httpClient
     * @param postUrl
     * @param params
     * @return
     */
    private Map<String, Object> doPostJson(CloseableHttpClient httpClient, String postUrl, String params){
        Map<String, Object> respJson = null;
        HttpPost httpPost = new HttpPost(postUrl);
        try{
            //设置请求头
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            StringEntity entity = new StringEntity(params);
            entity.setContentType("text/json");
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(entity);
            //使用ResponseHandler接口,处理http响应
            ResponseHandler<Map<String, Object>> rh = new ResponseHandler<Map<String, Object>>(){
                @Override
                public Map<String, Object> handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    StatusLine statusLine = response.getStatusLine();
                    if(statusLine.getStatusCode() >= 300){
                        throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                    }
                    HttpEntity entity = response.getEntity();
                    //无响应内容
                    if(entity == null){
                        throw new ClientProtocolException("Response contains no content");
                    }
                    //读取并处理响应流
                    BufferedReader in = null;
                    StringBuffer buffer = new StringBuffer();
                    try{
                        in = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                        String line = "";  
                        while((line = in.readLine()) != null){  
                            buffer.append(line);  
                        }
                    }catch(IOException e){
                        log.error(ExceptionUtils.getStackTrace(e));
                    }finally{
                        if(in != null){
                            try{
                                in.close();
                            }catch(Exception e2){
                                log.error(ExceptionUtils.getStackTrace(e2));
                            }
                        }
                    }
                    return JSON.parseObject(buffer.toString(), Map.class);
                }
            };
            respJson = httpClient.execute(httpPost, rh);
        }catch(Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return respJson;
    }
    
    /**
     * 描述 业务受理完毕的插件代码
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:08:08
     * @param flowDatas
     * @return
     */
    public Map<String, Object> doCompleteAffair(Map<String, Object> flowDatas){
        //排除退回情况
        String isFlowBack = (String)flowDatas.get("EFLOW_ISBACK");
        if(StringUtils.isNotEmpty(isFlowBack) && "true".equals(isFlowBack)){
            return flowDatas;
        }
        String curRunNode = (String)flowDatas.get("EFLOW_CUREXERUNNINGNODENAMES");
        if(StringUtils.isNotEmpty(curRunNode)){
            if (flowDatas.get("BUSINESS_SOURCE") != null) {
                int busSource = Integer.valueOf((String)flowDatas.get("BUSINESS_SOURCE"));
                if("申请".equals(curRunNode)){
                    if(busSource == OldAgeCardService.BUSINESS_SOURCE_WINDOW){
                        String cartType = (String)flowDatas.get("CARD_TYPE");
                        StringBuffer cardNum = new StringBuffer();
                        //绿证
                        if("1".equals(cartType)){
                            String nextNum = dao.getGreenCardNextNum();
                            int supplyCount = 6 - nextNum.length();
                            cardNum.append("A13");
                            for(int i = 0; i < supplyCount; i++){
                                cardNum.append("0");
                            }
                            cardNum.append(nextNum);
                            flowDatas.put("CARD_NUM", cardNum);
                            //红证
                        }else if("2".equals(cartType)){
                            String nextNum = dao.getGreenCardNextNum();
                            int supplyCount = 5 - nextNum.length();
                            cardNum.append("A13-");
                            for(int i = 0; i < supplyCount; i++){
                                cardNum.append("0");
                            }
                            cardNum.append(nextNum);
                            flowDatas.put("CARD_NUM", cardNum);
                        }
                        flowDatas.put("CARD_STATUS", "0");
                        //注销该申请人的其他优待证
                        if(StringUtils.isNotEmpty(cardNum.toString())){
                            String idNum = (String)flowDatas.get("SQRSFZ");
                            dao.updateDeRegister(idNum, cardNum.toString());
                        }
                    }
                }
                if("网上预审".equals(curRunNode)){
                    if(busSource != OldAgeCardService.BUSINESS_SOURCE_WINDOW){
                        String cartType = (String)flowDatas.get("CARD_TYPE");
                        StringBuffer cardNum = new StringBuffer();
                        //绿证
                        if("1".equals(cartType)){
                            String nextNum = dao.getGreenCardNextNum();
                            int supplyCount = 6 - nextNum.length();
                            cardNum.append("A13");
                            for(int i = 0; i < supplyCount; i++){
                                cardNum.append("0");
                            }
                            cardNum.append(nextNum);
                            flowDatas.put("CARD_NUM", cardNum);
                            //红证
                        }else if("2".equals(cartType)){
                            String nextNum = dao.getGreenCardNextNum();
                            int supplyCount = 5 - nextNum.length();
                            cardNum.append("A13-");
                            for(int i = 0; i < supplyCount; i++){
                                cardNum.append("0");
                            }
                            cardNum.append(nextNum);
                            flowDatas.put("CARD_NUM", cardNum);
                        }
                        flowDatas.put("CARD_STATUS", "0");
                        //注销该申请人的其他优待证
                        if(StringUtils.isNotEmpty(cardNum.toString())){
                            String idNum = (String)flowDatas.get("SQRSFZ");
                            dao.updateDeRegister(idNum, cardNum.toString());
                        }
                    }
                }
            }
        }
        return flowDatas;
    }
    
    /**
     * 描述 根据sqlfilter，查询经我审批列表
     * @author Bryce Zhang
     * @created 2017-5-15 下午5:15:29
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findNeedMeHandleBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.TASK_ID,T.TASK_NODENAME,T.FROMTASK_IDS, ");
        sql.append("T.CREATE_TIME AS TASK_CTIME,T.TASK_DEADTIME,T.TASK_STATUS, ");
        sql.append("E.* FROM JBPM6_TASK T LEFT JOIN JBPM6_EXECUTION E ON ");
        sql.append("T.EXE_ID = E.EXE_ID WHERE T.IS_AUDITED = 1 AND T.ASSIGNER_TYPE = 1 ");
        sql.append("AND E.BUS_TABLENAME = 'T_BSFW_OLDAGECARD' AND T.TASK_STATUS IN('-1','1') ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), null);
        return list;
    }
    
    /**
     * 描述 根据sqlfilter，获取某用户办理的流程
     * @author Bryce Zhang
     * @created 2017-5-16 上午9:53:35
     * @param filter
     * @param userAccount
     * @return
     */
    public List<Map<String, Object>> findHandledByUser(SqlFilter filter, String userAccount){
        return dao.findHandledByUser(filter, userAccount);
    }
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth(SqlFilter filter, SysUser loginUser){
        return dao.findListByAuth(filter, loginUser);
    }
    
    /**
     * 描述 根据身份证号和优待证类别，查询业务 
     * @author Bryce Zhang
     * @created 2017-5-26 上午11:25:25
     * @param idNum
     * @param cardType
     * @return
     */
    public Map<String, Object> getByIdnumAndCardType(String idNum, int cardType){
        StringBuffer sql = new StringBuffer("select t.* from JBPM6_EXECUTION e left join T_BSFW_OLDAGECARD t ");
        sql.append("on e.bus_recordid = t.business_id where e.bus_tablename = 'T_BSFW_OLDAGECARD' ");
        sql.append("and e.SQRSFZ = ? and t.CARD_TYPE = ? and t.CARD_STATUS = 0 ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{idNum, cardType}, null);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 描述 根据业务id，查询
     * @author Bryce Zhang
     * @created 2017-5-27 上午9:31:42
     * @param busId
     * @return
     */
    public Map<String, Object> getByBusId(String busId){
        StringBuffer sql = new StringBuffer("select * from JBPM6_EXECUTION e left join T_BSFW_OLDAGECARD t ");
        sql.append("on e.bus_recordid = t.business_id where e.bus_tablename = 'T_BSFW_OLDAGECARD' ");
        sql.append("and e.bus_recordid = ? ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{busId}, null);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 描述 更新证件为注销
     * @author Bryce Zhang
     * @created 2017-5-27 下午5:19:51
     * @param busId
     * @param lostStateId
     * @param lostStateName
     * @param lostStatePath
     */
    public void updateUnregister(String busId, String lostStateId, String lostStateName, String lostStatePath){
        dao.updateUnregister(busId, lostStateId, lostStateName, lostStatePath);
    }
    
    /**
     * 描述 根据sqlfilter，获取授权数据列表
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:53:41
     * @param filter
     * @param loginUser
     * @return
     */
    public List<Map<String, Object>> findListByAuth4Exp(SqlFilter filter, SysUser loginUser){
        return dao.findListByAuth4Exp(filter, loginUser);
    }
    
    /**
     * 描述 老年人优待证业务数据报表生成
     * @author Bryce Zhang
     * @created 2017-5-29 下午8:42:22
     * @param list
     * @return
     */
    public HSSFWorkbook generateExcel(List<Map<String, Object>> list){
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet("老年人优待证");
        //默认列宽和行高
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(15);
        //列头样式
        HSSFCellStyle headCellStyle = workBook.createCellStyle();
        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //列名数组
        String[] colNames = new String[]{"申报号", "事项来源渠道", "姓名", "身份证号", "申请时间", "老年证编号", 
                 "住址", "联系手机", "老年优待证类型", "窗口收件人员", "所属乡镇", "采集的网格员", "备注"};
        String[] colCodes = new String[]{"EXE_ID", "BUSINESS_SOURCE", "SQRMC", "SQRSFZ", "CREATE_TIME",
                 "CARD_NUM", "APPLICANT_ADDR", "SQRSJH", "CARD_TYPE", "OPERATOR_NAME", "ACCEPTDEPT_NAME", 
                 "GRIDMAN_NAME", "BUSINESS_REMARK"};
        //操作行索引
        int operateRowIndex = 0;
        //赋值列头
        HSSFRow operateRow = sheet.createRow(operateRowIndex);
        for(int i = 0; i < colNames.length; i++){
            HSSFCell operateCell = operateRow.createCell(i);
            operateCell.setCellStyle(headCellStyle);
            operateCell.setCellValue(colNames[i]);
        }
        operateRowIndex ++;
        //赋值内容
        Iterator<Map<String, Object>> iterator = list.iterator();
        while(iterator.hasNext()){
            Map<String, Object> data = iterator.next();
            HSSFRow currentRow = sheet.createRow(operateRowIndex);
            for(int i = 0; i < colNames.length; i++){
                HSSFCell operateCell = currentRow.createCell(i);
                if(data.get(colCodes[i]) != null){
                    operateCell.setCellValue((String)data.get(colCodes[i]));
                }else{
                    operateCell.setCellValue("");
                }
            }
            operateRowIndex ++;
        }
        return workBook;
    }
    
    /**
     * 描述 是否网上预审-流程决策插件代码
     * @author Bryce Zhang
     * @created 2017-8-1 下午5:39:57
     * @param flowVars
     * @return
     */
    public Set<String> getIsPreAudit(Map<String,Object> flowVars){
        Set<String> resultNodes = new HashSet<String>();
        if (flowVars.get("BUSINESS_SOURCE") != null) {
            int busSource = Integer.valueOf((String) flowVars.get("BUSINESS_SOURCE"));
            if (busSource == OldAgeCardService.BUSINESS_SOURCE_WINDOW) {
                resultNodes.add("审核制证");
            } else {
                resultNodes.add("网上预审");
            }
        } else {
            resultNodes.add("网上预审");
        }
        return resultNodes;
    }
    
    /**
     * 描述 姓名身份证比对
     * @author Bryce Zhang
     * @created 2017-8-2 下午6:05:09
     * @param name
     * @param idNum
     * @param requestIp
     * @return
     */
    public AjaxJson idCompare(String name, String idNum, String requestIp){
        AjaxJson j = new AjaxJson();
        CloseableHttpClient httpClient = HttpClientUtil.getPlainHttpClient();
        Properties properties = FileUtil.readProperties("project.properties");
        //姓名身份证比对
        String idcheckPostUrl = properties.getProperty("idCompareUrl");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", name);
        paramsMap.put("citizenIdNumber", idNum);
        paramsMap.put("businessId", properties.getProperty("faceCompareBusId"));
        paramsMap.put("reqIp", requestIp);
        Map<String, Object> returnJson = doPostJson(httpClient, idcheckPostUrl, JSON.toJSONString(paramsMap));
        if(returnJson != null){
            String postResult = (String)returnJson.get("result");
            if("0".equals(postResult)){
                j.setSuccess(false);
                j.setMsg((String)returnJson.get("msg"));
            }
        }else{
            j.setSuccess(false);
            j.setMsg("姓名身份证比对请求失败，调用公安接口异常。");
        }
        return j;
    }

}
