/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.util;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.zzhy.model.AddrPara;
import net.evecom.platform.zzhy.model.Channel;
import net.evecom.platform.zzhy.model.TaskData;
import net.evecom.platform.zzhy.service.CreatTaskInterService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 公安地址接口查询工具类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月11日 上午9:13:33
 */
public class RobotUtils {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(RobotUtils.class);
    /**
     *CHANNEL_NAME
     */
    public static  final String  QUERY_CHANNEL_NAME="ITSC";
    /**
     *QUERY_VERSION_LOW
     */
    public static  final String   QUERY_VERSION="V1";
    /**
     *ID
     *
     */
    public static  final String  RENT_ID="1002";
    /**
     *QUERY_VERSION
    */
    public static  final String   QUERY_CREATOR="admin";
    /**
     *用户名
     */
    public static  final String  RENT_USER_NAME="1002|J6j6R0sqLFNIvXUZbNU8";
    /**
     *密码
     */
    public static  final String  RENT_PASS_WORD="c^})?pe:Mji4KdI8<>Hte&*Et|6AnfNon.d70NE/EX+vAt[:pLHQ%RTg,*EG45sB";
    /**
     *json对象类型
     */
    public static String paramTypeObject ="object";
    /**
     * 字符串类型
     */
    public static String paramTypeString ="string";
    /**
     * 名称查重单线程池
     */
    public static ExecutorService checkNameExecutor = Executors.newSingleThreadExecutor();
    /**
     * 业务推送单线程池
     */
    public static ExecutorService pushDataExecutor = Executors.newSingleThreadExecutor();
    /**
     * 解析名称查重机器人状态
     * @param responseContent
     * @return
     */

    public static boolean queryRobotStatusByResult(String responseContent){
        Map<String,Object> resp= (Map<String, Object>) JSON.parse(responseContent);
        String result= StringUtil.getString(resp.get("result"));
        Map resultMap1=(Map<String, Object>) JSON.parse(result);
        String result2=StringUtil.getString(resultMap1.get("result"));
        List<Map<String,Object>> resultList3=(List) JSON.parse(result2);
        if(CollectionUtils.isNotEmpty(resultList3)){
            Map<String,Object> resultMap4=resultList3.get(0);
            String status=StringUtil.getString(resultMap4.get("status"));
            if(Objects.equals(status,"online")){
                return true;
            }
        }
        return false;
    }
    /**
     * 推送数据httpClient
     * @param taskData
     * @return
     */
    public static String   pushTaskData(TaskData taskData,String pushUrl) {
        log.info("商事推送到工商专网推送的URl是" + pushUrl);
        log.info("商事推送到工商专网推送的数据是" + JSON.toJSONString(taskData));
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(pushUrl);
        StringEntity myEntity = new StringEntity(JSON.toJSONString(taskData), ContentType.APPLICATION_JSON); // 构造请求数据
        post.addHeader("Authorization", getHeader());
        post.addHeader("Content-Type", "application/json");
        post.setEntity(myEntity); // 设置请求体
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
                log.info("商事推送到工商专网返回的数据是:"+responseContent);
            }
        } catch(Exception e) {
            log.error(e.getMessage(),e);
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        return responseContent;
    }

    /**
     *
     * @return
     */
    public static String getHeader() {
        String auth = RENT_USER_NAME + ":" +RENT_PASS_WORD ;
        byte[] encodedAuth = new byte[0];
        try {
            encodedAuth = Base64.encodeBase64(auth.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
        }
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }
    /**
     * 获取taskData对象
     * @param params
     * @return
     */
    public  static TaskData getTaskData(Map<String,Object> params){
        TaskData queryData=new TaskData();
        Channel channel=new Channel();
        channel.setName(QUERY_CHANNEL_NAME);
        //queryData对象
        queryData.setVersion(QUERY_VERSION);
        queryData.setChannel(channel);
        queryData.setParams(params);
        return queryData;
    }
    /**
     * 通过接口返回值获取推送数据状态
     * @param responseContent
     * @return
     */
    public static boolean getSubmitDataByResult(String responseContent){
        Map<String,Object> result=(Map)JSON.parse(responseContent);
        String result1=StringUtil.getString(result.get("result"));
        Map<String,Object> result2=(Map)JSON.parse(result1);
        String success=StringUtil.getString(result2.get("success"));
        if(Objects.equals(success,"true")){
            return true;
        }
        return false;
    }

    /**
     * 回调日志保存数据
     * @param exeId
     * @param status
     */
    public static void saveLogByExeId(String exeId,String status){
        //获取业务数据
        ExeDataService exeDataService= (ExeDataService) AppUtil.getBean("exeDataService");
        Map<String,Object> data=exeDataService.getExeAndBuscordMap(exeId);

        SysLogService sysLogService= (SysLogService) AppUtil.getBean("sysLogServiceImpl");
        String companyName=StringUtil.getString(data.get("COMPANY_NAME"));
        if(StringUtils.isEmpty(companyName)){
            companyName=StringUtil.getString(data.get("INDIVIDUAL_NAME"));
        }
        String logContent=String.format("接收开普云回调%s申报号为%s,公司名称为%s",status,exeId,companyName);
        Map<String,Object> person=new HashMap<>();
        person.put("PERSONNEL_NAME","开普云回调");
        person.put("USERNAME",status);
        person.put("PERSONNEL_ID","商事秒批");
        sysLogService.saveLog(logContent,1,person);
    }
}
