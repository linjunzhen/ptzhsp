/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpSendUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.BdcDyrlxrDao;
import net.evecom.platform.bsfw.service.BdcDdjfxxService;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述 不动产-订单缴费信息service()
 * @author Allin Lin
 * @created 2020年12月16日 上午11:01:07
 */
@Service("bdcDdjfxxService")
public class BdcDdjfxxServiceImpl extends BaseServiceImpl implements BdcDdjfxxService{
    
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcDdjfxxServiceImpl.class);
    
    /**
     * 引入dao
     */
    @Resource
    private BdcDyrlxrDao dao;
    
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述  覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年12月16日 上午11:10:28
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 根据sqlfilter获取订单缴费信息数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.*,P.DZFP_FILEIDS,P.JSD_FILEIDS from T_BDC_ORDERDETAILINFO T ");
        sql.append(" LEFT JOIN T_BDC_PAYMENTRECORD P ON T.EXE_ID = P.EXE_ID ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:45:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFileterExport(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from T_BDC_ORDERDETAILINFO a");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述    获取接口授权码（token）
     * @author Danto Huang
     * @created 2019年11月13日 上午10:58:42
     * @return
     */
    @Override
    public Map<String,Object> getCreditToken(){
        Map<String,Object> tokenMap;
        Map<String,Object> tokenParams = new HashMap<String, Object>();
        JSONObject pushInfoJson = new JSONObject();
        pushInfoJson.put("appId", "SPPT");
        pushInfoJson.put("appSecret", "sp2d2f41cf97za05bcb3fa50fuj6fwed7");
        tokenParams.put("pushInfoJson", StringUtil.getString(pushInfoJson));
        tokenParams.put("urlDicType", "token_url");
        String forwardUrl = StringUtil.getString(dictionaryService.get("ddConfig", "TOKEN_INTERNET_FORWARD_URL").get("DIC_DESC"));//互联网转发地址
        tokenMap = this.queryAjaxJson(tokenParams, "token_url",forwardUrl);
        log.info("获取接口授权码请求参数pushInfoJson:" + pushInfoJson);
        log.info("获取接口授权码返回参数tokenMap:" + tokenMap);
        return tokenMap;
    }
    
    
    /**
     * 
     * 描述    支付订单信息
     * @author Danto Huang
     * @created 2019年11月13日 上午10:58:42
     * @return
     */
    @Override
    public Map<String,Object> payOrderInfoCreate(Map<String, Object> payInfo,String token){
        Map<String,Object> tokenMap;
        Map<String,Object> tokenParams = new HashMap<String, Object>();
        JSONObject pushInfoJson = new JSONObject();
        pushInfoJson.put("exeId", payInfo.get("exeId"));
        pushInfoJson.put("method", payInfo.get("method"));
        pushInfoJson.put("itemCode", payInfo.get("itemCode"));
        pushInfoJson.put("userName", payInfo.get("userName"));
        pushInfoJson.put("itemName", payInfo.get("itemName"));
        pushInfoJson.put("idType", payInfo.get("idType"));
        pushInfoJson.put("idNo", payInfo.get("idNo"));
        pushInfoJson.put("payAmount", payInfo.get("payAmount"));
        pushInfoJson.put("orderTitle", payInfo.get("orderTitle"));
        pushInfoJson.put("orderItems", payInfo.get("orderItems"));
        pushInfoJson.put("orderRemark", payInfo.get("orderRemark"));
        pushInfoJson.put("orderDetail", payInfo.get("orderDetail"));
        
        tokenParams.put("pushInfoJson", StringUtil.getString(pushInfoJson));
        tokenParams.put("token", token);
        tokenParams.put("urlDicType", "payinfo_url");
        String forwardUrl = StringUtil.getString(dictionaryService.get("ddConfig", "PAYINFO_INTERNET_FORWARD_URL").get("DIC_DESC"));//互联网转发地址
        tokenMap = this.queryAjaxJson(tokenParams, "payinfo_url",forwardUrl);
        log.info("支付订单信息请求参数payInfo:" + payInfo);
        log.info("支付订单信息返回参数tokenMap:" + tokenMap);
        return tokenMap;
    }
    
    
    /**
     * 描述 订单缴费转发通用方法
     * @author Allin Lin
     * @created 2019年10月22日 上午9:32:02
     * @param info 查询的信息（参数）
     * @param urlDicType 订单缴费相关接口配置的字典值
     * @return 
     */
    @Override
    public Map<String, Object> queryAjaxJson(Map<String, Object> info, String urlDicType,String forwardUrl) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String response = HttpSendUtil.sendPostParams(forwardUrl, info);
            if (StringUtils.isNotEmpty(response)) {
                result = JSON.parseObject(response, Map.class);
            }
        } catch (Exception e) {
            log.info("接口-"+urlDicType+"获取数据异常:", e);
        }
        return result;
    }
    
    /** 
     * 描述    订单缴费推送方法（POST请求）
     * @author Allin Lin
     * @created 2020年1月7日 上午10:51:28
     * @param info
     * @param urlDicType
     * @return
     */
    @Override
    public Map<String, Object> pushData(Map<String,Object> info,String urlDicType){
        String respContent = "";  
        String resultMessage="";
        Map<String, Object> result = new HashMap<String, Object>(); 
        String url = dictionaryService.get("ddConfig", urlDicType).get("DIC_DESC").toString();
        try {
            respContent = sendPost(url, JSON.toJSONString(info),"UTF-8");
            result = (Map)JSON.parse(respContent);
        } catch (Exception e) {
            resultMessage = "error";//请求接口失败
            result.put("resultMessage", resultMessage);         
        }
        log.info(String.format("urlDicType===%s,url===%s,param===%s,queryResult===%s",urlDicType,url,
                JSON.toJSONString(info),result.toString()));
        return result;
    }
    
    /** 
     * 描述    订单缴费推送方法（POST请求）
     * @author Allin Lin
     * @created 2020年1月7日 上午10:51:28
     * @param info
     * @param urlDicType
     * @return
     */
    @Override
    public Map<String, Object> pushDataPayinfo(Map<String,Object> info,String urlDicType,String token){
        String respContent = "";  
        String resultMessage="";
        Map<String, Object> result = new HashMap<String, Object>(); 
        String url = dictionaryService.get("ddConfig", urlDicType).get("DIC_DESC").toString();
        try {
            //请求头
            Map<String, String> headMap = new HashMap<>();
            headMap.put("token", token);
            respContent = HttpSendUtil.sendHttpsPostJson(url, headMap, JSON.toJSONString(info),"UTF-8");
            result = (Map)JSON.parse(respContent);
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
     * 
     * @Description 二手房缴费相关短信发送模块
     * @author Luffy Cai
     * @date 2021年5月31日
     * @param sendMsgMap 
     */
    @Override
    public Map<String, Object> sendSuccessMsg(Map<String, Object> sendMsgMap) {
        String exeId = StringUtil.getValue(sendMsgMap, "exeId");
        String toPhone = StringUtil.getValue(sendMsgMap, "toPhone");
        String manName = StringUtil.getValue(sendMsgMap, "manName");
        String sendType = StringUtil.getValue(sendMsgMap, "sendType");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(exeId)){
            log.error("二手房缴费相关短信"+ "faileMsg:申报号不能为空");
            returnMap.put("success", false);
            returnMap.put("msg", "二手房缴费相关短信"+ "faileMsg:申报号不能为空");
            return returnMap;
        }
        if(StringUtils.isEmpty(toPhone)){
            log.error("二手房缴费相关短信"+ "faileMsg:手机号不能为空");
            returnMap.put("success", false);
            returnMap.put("msg", "二手房缴费相关短信"+ "faileMsg:手机号不能为空");
            return returnMap;
        }
        if(StringUtils.isEmpty(sendType)){
            log.error("二手房缴费相关短信"+ "faileMsg:短信发送类型不能为空");
            returnMap.put("success", false);
            returnMap.put("msg", "二手房缴费相关短信"+ "faileMsg:短信发送类型不能为空");
            return returnMap;
        }
        Properties properties = FileUtil.readProperties("conf/messageConfig.properties");
        String templetId = properties.getProperty("secondHandHouse" + sendType);
        StringBuffer logInfo = new StringBuffer();
        String waitToSendMsg = "";
        try {
            Map<String, Object> colValues = new HashMap<String, Object>();
            String content = "";
            switch (sendType) {
                case "1":
                    content = "您好，您受理的二手房转移登记（申报号："+exeId+"）目前可通过闽政通/平潭通/行政服务中心门户网站缴纳登记费用，"
                            + "缴费前请先确保您的转移税费已缴纳，并保存相关凭证。如未收到税务短信，请您静候留意。";
                    waitToSendMsg = exeId;
                    break;
                case "2":
                    content = "申报号【" +exeId +"】，【" + manName +"】已上传缴税凭证，并已完成缴费，请及时登录审批系统进行查办";
                    waitToSendMsg = exeId+","+manName;
                    break;
                default:
                    break;
            }
            colValues.put("CONTENT", content);
            colValues.put("RECEIVER_MOB", toPhone);
            colValues.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            colValues.put("SEND_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            colValues.put("SEND_STATUS", "2");
            returnMap = SmsSendUtil.sendSms(waitToSendMsg, toPhone, templetId);
            returnMap.put("success", false);
            if (returnMap != null) {
                String resultcode = StringUtil.getString(returnMap.get("resultcode"));
                if (Objects.equals(resultcode, "0")) {
                    returnMap.put("success", true);
                    colValues.put("SEND_STATUS", "1");
                    logInfo.append("申报号：").append(exeId).append("发送短信提醒成功,手机号码为：").append(toPhone);
                } else {
                    logInfo.append("申报号：").append(exeId).append("发送短信提醒失败,错误信息为：").append(JSON.toJSONString(returnMap));
                }
            }else{
                logInfo.append("申报号：").append(exeId).append("returnMap为null,手机号码为：").append(toPhone);
            }
            log.info(logInfo);
            returnMap.put("msg", logInfo);
            String result = dao.saveOrUpdate(colValues, "T_MSJW_SYSTEM_MSGSEND", null);
            returnMap.put("MSG_ID", result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(logInfo.append("申报号：").append(exeId).append("报错e").append(e).append(",手机号码为：").append(toPhone));
            returnMap.put("msg", logInfo.append("申报号：").append(exeId).append("报错e").append(e).append(",手机号码为：").append(toPhone));
        }
        return returnMap;
    }
}
